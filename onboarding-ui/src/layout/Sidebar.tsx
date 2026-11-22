import {
  Link
} from "react-router-dom";

function Sidebar() {

  return (

    <div
      style={{
        width: "250px",
        background: "#1E293B",
        color: "white",
        padding: "20px"
      }}
    >

      <h2>
        Banking Platform
      </h2>

      <hr />

      <div>

        <p>
          <Link
            to="/dashboard"
            style={{
              color: "white"
            }}
          >
            Dashboard
          </Link>
        </p>

        <p>
          <Link
            to="/accounts"
            style={{
              color: "white"
            }}
          >
            Accounts
          </Link>
        </p>

        <p>
          <Link
            to="/transfers"
            style={{
              color: "white"
            }}
          >
            Transfers
          </Link>
        </p>

        <p>
          <Link
            to="/transactions"
            style={{
              color: "white"
            }}
          >
            Transactions
          </Link>
        </p>

        <p>
          <Link
            to="/profile"
            style={{
              color: "white"
            }}
          >
            Profile
          </Link>
        </p>

      </div>

    </div>

  );
}

export default Sidebar;