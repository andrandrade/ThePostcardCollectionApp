package br.edu.ifspsaocarlos.sdm.postcardcollection.View;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import br.edu.ifspsaocarlos.sdm.postcardcollection.R;
import br.edu.ifspsaocarlos.sdm.postcardcollection.Utils.FieldValidUtils;

/*** A login screen that offers login via email/password. */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "PostcardApp";
    private FirebaseAuth mAuth;

    /* Keep track of the login task to ensure we can cancel it if requested. */
    private UserAuthTask mAuthTask = null;

    // UI references
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        mEmailView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);

        mEmailView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_NEXT) {
                    mPasswordView.requestFocus();
                    return true;
                }
                return false;
            }
        });
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE) {
                    if(validateForm())
                        signIn();
                    return true;
                }
                return false;
            }
        });

        // Autenticação
        this.mAuth = FirebaseAuth.getInstance();

    }

    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.email_sign_in_button) {
            signIn();
        } else if (i == R.id.email_create_account_link) {
            createAccount();
        } else if (i == R.id.verify_email_button) {
            //sendEmailVerification();
        }
    }

    private void updateUI(FirebaseUser currentUser) {
        Toast.makeText(this, "Usuario logado ", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    // Abre a tela de criação de conta
    private void createAccount() {
        startActivity(new Intent(this, CreateAccountActivity.class));
        finish();
    }

    private void signIn() {
        if (!validateForm() || (mAuthTask != null)) {
            return;
        }
        // Inicia a tarefa AsyncTask em background para tentar o Login
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        Log.d(TAG, "signIn:" + email);
        mAuthTask = new UserAuthTask(email, password, 1);
        mAuthTask.execute((Void) null);
    }

    // Testa a consistência dos dados (email inválido, campos em branco) e mostra erros
    boolean validateForm() {
        // Resetando tudo para começar...
        mEmailView.setError(null);
        mPasswordView.setError(null);
        View focusView = null;
        boolean cancel = false;

        // Pega os valores informados pelo usuário - Email e Senha
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        // Verifica se a senha é vazia ou inválida
        if (TextUtils.isEmpty(password) || !FieldValidUtils.isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Verifica se o email é vazio ou inválido
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!FieldValidUtils.isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // Algum erro foi detectado; foca no 1º campo com problema
            focusView.requestFocus();
        }
        return !cancel;
    }

    /* Mostra ou esconde o Spinner de progresso e faz o contrário com o form de Login */
    private void showProgress(final boolean show) {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    /* AsyncTask de Registro para autenticar o usuário */
    public class UserAuthTask extends AsyncTask<Void, Void, Boolean> {
        private final String mEmail;
        private final String mPassword;
        private final int mTask;
        private final boolean result;
        // 0 = register new credentials
        // 1 = login with existing credentials

        UserAuthTask(String email, String password, int task) {
            mEmail = email;
            mPassword = password;
            mTask = task;
            result = false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress(true);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            if (mTask == 0) {
                // create User
                mAuth.createUserWithEmailAndPassword(mEmail, mPassword)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Cadastro e autenticação bem sucedidos...
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);

                            } else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(LoginActivity.this, getString(R.string.registration_failure_user_exists), Toast.LENGTH_LONG).show();
                            } else {
                                // Cadastro falhou...
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, getString(R.string.registration_failure), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            } else {
                // SignIn user
                mAuth.signInWithEmailAndPassword(mEmail, mPassword)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in bem sucedido...
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);

                            } else if (task.getException() instanceof FirebaseAuthInvalidUserException ||
                                    task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                                // Se falhou por login não encontrado
                                Log.w(TAG, "signInWithEmail:invalidAuth", task.getException());
                                Toast.makeText(LoginActivity.this, getString(R.string.authentication_failure_wrong_credentials),Toast.LENGTH_SHORT).show();
                            } else {
                                // Se o login falhou por erro alheio ao usuario
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, getString(R.string.authentication_failure), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            if (!success) {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

