import {
  Navigate
} from "react-router-dom";

import {
  getAccessToken
} from "../services/tokenService";

interface Props {

  children:
    React.ReactNode;
}

function ProtectedRoute(
  { children }: Props
) {

  const token =
    getAccessToken();

  if (!token) {

    return (
      <Navigate
        to="/"
      />
    );
  }

  return children;
}

export default ProtectedRoute;