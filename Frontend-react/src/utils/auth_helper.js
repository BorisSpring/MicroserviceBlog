import { UserManager, WebStorageStateStore } from 'oidc-client';

const settings = {
  authority: 'http://localhost:7080/realms/blogrealm/',
  client_id: 'pkcee-blog-client',
  redirect_uri: 'http://localhost:5173/dashboard',
  response_type: 'code',
  scope: 'openid profile email',
  userStore: new WebStorageStateStore({ store: window.localStorage }),
};

const userManager = new UserManager(settings);

export const getUser = () => {
  return userManager.getUser();
};

export const login = () => {
  return userManager.signinRedirect();
};

export const logout = () => {
  userManager.signoutRedirect();
  localStorage.removeItem('access_token');
  localStorage.removeItem('id_token');
};

export const handleAuthenticationCallback = async () => {
  try {
    const user = await userManager.signinRedirectCallback();
    console.log(user);
    localStorage.setItem('access_token', user.access_token);
    localStorage.setItem('id_token', user.id_token);
  } catch (error) {
    console.error('Error during authentication callback:', error);
  }
};
