package com.calvindo.aldi.sutanto.tubes.UnitTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {
    @Mock
    private LoginView view;

    @Mock
    private LoginService service;
    private LoginPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new LoginPresenter(view,service);
    }

    @Test
    public void isEmptyEmail() throws Exception{
        when(view.getEmail()).thenReturn("");
        System.out.println("Email    : " + view.getEmail());

        presenter.onLoginClicked();

        verify(view).showNimError("Email Tidak Boleh Kosong");
    }

    @Test
    public void isPasswordEmpty() throws Exception {
        when(view.getEmail()).thenReturn("");
        System.out.println("Email : "+ view.getEmail());
        when(view.getPassword()).thenReturn("");
        System.out.println("Password : "+view.getPassword());
        presenter.onLoginClicked();
        verify(view).showPasswordError("Password Tidak Boleh Kosong");
    }

    @Test
    public void ValidEmailANDPassword() throws
            Exception {
        when(view.getEmail()).thenReturn("calvindoaldy@gmail.com");
        System.out.println("Email : "+view.getEmail());
        when(view.getPassword()).thenReturn("admins");
        System.out.println("password : "+view.getPassword());
        when(service.getValid(view, view.getEmail(),
                view.getPassword())).thenReturn(true);
        System.out.println("Hasil : "+service.getValid(view,view.getEmail(),
                view.getPassword()));
        presenter.onLoginClicked();
        //verify(view).showLoginError(R.string.login_failed);
    }

    @Test
    public void invalidEmail() throws Exception{
        when(view.getEmail()).thenReturn("calvindo");
        System.out.println("Email    : " + view.getEmail());
        when(view.getPassword()).thenReturn("admins");
        System.out.println("password : "+view.getPassword());
        presenter.onLoginClicked();

        verify(view).showNimError("Email Invalid");
    }

    @Test
    public void invalidPassword() throws Exception{
        when(view.getEmail()).thenReturn("calvindoaldy@gmail.com");
        System.out.println("Email    : " + view.getEmail());
        when(view.getPassword()).thenReturn("111");
        System.out.println("Password    : " + view.getPassword());
        presenter.onLoginClicked();
        verify(view).showNimError("Password Kurang dari 6 karakter");
    }

    @Test
    public void ValidLogin() throws
            Exception {
        when(view.getEmail()).thenReturn("calvindoaldy@gmail.com");
        System.out.println("Email : "+view.getEmail());
        when(view.getPassword()).thenReturn("1234567");
        System.out.println("Password : "+view.getPassword());
        when(service.getValid(view, view.getEmail(),
                view.getPassword())).thenReturn(true);
        System.out.println("Hasil : "+service.getValid(view,view.getEmail(),
                view.getPassword()));
        presenter.onLoginClicked();
        //verify(view).startMainActivity();
    }

    @Test
    public void LoginInvalid() throws
            Exception {
        when(view.getEmail()).thenReturn("calvindoaldy@gmail.com");
        System.out.println("Email : "+view.getEmail());
        when(view.getPassword()).thenReturn("admins");
        System.out.println("Password : "+view.getPassword());
        when(service.getValid(view, view.getEmail(),
                view.getPassword())).thenReturn(false);
        System.out.println("Hasil : "+service.getValid(view,view.getEmail(),
                view.getPassword()));
        presenter.onLoginClicked();
        //verify(view).showLoginError(R.string.login_failed);
    }
//??????????????????????????????????????????????????????????????????????????????????????????????????



}