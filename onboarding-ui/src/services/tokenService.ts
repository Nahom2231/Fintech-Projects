export const saveAccessToken = (
  token: string
) => {

  localStorage.setItem(
    "accessToken",
    token
  );
};

export const saveRefreshToken = (
  token: string
) => {

  localStorage.setItem(
    "refreshToken",
    token
  );
};

export const getAccessToken = () => {

  return localStorage.getItem(
    "accessToken"
  );
};

export const removeTokens = () => {

  localStorage.removeItem(
    "accessToken"
  );

  localStorage.removeItem(
    "refreshToken"
  );
};