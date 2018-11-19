package br.edu.ifspsaocarlos.sdm.postcardcollection.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import br.edu.ifspsaocarlos.sdm.postcardcollection.R;
import br.edu.ifspsaocarlos.sdm.postcardcollection.utils.SessionUtils;
import br.edu.ifspsaocarlos.sdm.postcardcollection.utils.FieldValidUtils;
import br.edu.ifspsaocarlos.sdm.postcardcollection.view.MainActivity;

/*** A sign up screen that offers authentication via email/password. */
public class CreateAccountActivity extends AppCompatActivity {

    private final String TAG = SessionUtils.TAG;
    private FirebaseAuth mAuth;

    // UI references
    private EditText mNameView;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mPasswordConfirmView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mLoginFormView = findViewById(R.id.signup_form);
        mProgressView = findViewById(R.id.login_progress2);

        mNameView = findViewById(R.id.person_name);
        mEmailView = findViewById(R.id.email2);
        mPasswordView = findViewById(R.id.password2);
        mPasswordConfirmView = findViewById(R.id.password_confirm);

        mNameView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_NEXT) {
                    mEmailView.requestFocus();
                    return true;
                }
                return false;
            }
        });

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
                if (id == EditorInfo.IME_ACTION_NEXT) {
                    mPasswordConfirmView.requestFocus();
                    return true;
                }
                return false;
            }
        });

        mPasswordConfirmView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE) {
                    if(validateForm())
                        createUser();
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
        if (i == R.id.email_sign_up_button) {
            createUser();
        } else if (i == R.id.email_sign_in_link) {
            signIn();
        }
    }

    private void signIn() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void updateUI(FirebaseUser currentUser) {
        Toast.makeText(this, "Usuario logado ", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    /* Mostra ou esconde o Spinner de progresso e faz o contrário com o form de Login */
    private void showProgress(final boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    // Tenta registrar a conta com os dados fornecidos no form
    public void createUser() {
        if (!validateForm()) {
            return;
        }

        String mName = mNameView.getText().toString();
        String mEmail = mEmailView.getText().toString();
        String mPassword = mPasswordView.getText().toString();

        Log.d(TAG, "Trying createAccount:" + mEmail);
        showProgress(true);

        Task<AuthResult> task = mAuth.createUserWithEmailAndPassword(mEmail, mPassword);
        task.addOnCompleteListener(CreateAccountActivity.this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Cadastro e autenticação bem sucedidos...
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = task.getResult().getUser();
                    // TODO: salvar o nome do usuário no bd
                    if (user != null)
                        updateUI(user);

                } else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                    showProgress(false);
                    Log.w(TAG, "createUserWithEmail:failed", task.getException());
                    Toast.makeText(CreateAccountActivity.this, getString(R.string.registration_failure_user_exists), Toast.LENGTH_LONG).show();
                } else {
                    // Cadastro falhou...
                    showProgress(false);
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(CreateAccountActivity.this, getString(R.string.registration_failure), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Testa a consistência dos dados (email inválido, campos em branco) e mostra erros
    boolean validateForm() {
        // Resetando tudo para começar...
        mNameView.setError(null);
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mPasswordConfirmView.setError(null);
        View focusView = null;
        boolean cancel = false;

        // Pega os valores informados pelo usuário - Email e Senha
        String name = mNameView.getText().toString();
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String confPassword = mPasswordConfirmView.getText().toString();

        // Verifica se o campo de CONFIRMAÇÃO DE SENHA difere da SENHA
        if (!confPassword.equals(password)) {
            mPasswordConfirmView.setError(getString(R.string.error_password_differ));
            focusView = mPasswordConfirmView;
            cancel = true;
        }

        // Verifica se a SENHA é vazia ou inválida
        if (TextUtils.isEmpty(password) || !FieldValidUtils.isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Verifica se o E-MAIL é vazio ou inválido
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!FieldValidUtils.isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        // Verifica se NOME é vazio
        if (TextUtils.isEmpty(name)) {
            mNameView.setError(getString(R.string.error_field_required));
            focusView = mNameView;
            cancel = true;
        }

        if (cancel) {
            // Algum erro foi detectado; foca no 1º campo com problema
            focusView.requestFocus();
        }
        return !cancel;
    }

}