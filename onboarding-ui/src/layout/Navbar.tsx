import {
  useNavigate
} from "react-router-dom";

import {
  removeTokens
} from "../services/tokenService";

function Navbar() {

  const navigate =
    useNavigate();

  const logout =
    () => {

      removeTokens();

      navigate("/");
    };

  return (

    <div
      style={{
        height: "70px",
        borderBottom: "1px solid #ddd",
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
        padding: "0 20px"
      }}
    >

      <h2>

        Digital Banking Dashboard

      </h2>

      <button
        onClick={logout}
      >

        Logout

      </button>

    </div>

  );
}

export default Navbar;