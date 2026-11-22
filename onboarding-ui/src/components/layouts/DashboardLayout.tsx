import { Outlet } from "react-router-dom";
import Sidebar from "../../layout/Sidebar";
import Navbar from "../../layout/Navbar";

function DashboardLayout() {

  return (

    <div
      style={{
        display: "flex",
        height: "100vh"
      }}
    >

      <Sidebar />

      <div
        style={{
          flex: 1
        }}
      >

        <Navbar />

        <div
          style={{
            padding: "20px"
          }}
        >

          <Outlet />

        </div>

      </div>

    </div>

  );
}

export default DashboardLayout;