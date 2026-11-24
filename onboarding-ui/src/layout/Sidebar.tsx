import { NavLink } from "react-router-dom";

const navItems = [
  { to: "/dashboard", label: "Dashboard" },
  { to: "/accounts", label: "Accounts" },
  { to: "/transfers", label: "Transfers" },
  { to: "/transactions", label: "Transactions" },
  { to: "/statement", label: "Account Statement" },
  { to: "/mini-statement", label: "Mini Statement" },
  { to: "/profile", label: "Profile" },
  { to: "/kyc", label: "KYC Status" },
];

function Sidebar() {
  return (
    <aside
      style={{
        width: "250px",
        minWidth: "250px",
        background: "#1E293B",
        color: "#F8FAFC",
        padding: "24px 16px",
        display: "flex",
        flexDirection: "column",
        boxSizing: "border-box",
        borderRight: "1px solid #334155",
      }}
    >
      <div style={{ paddingBottom: "16px", borderBottom: "1px solid #334155", marginBottom: "16px" }}>
        <h2 style={{ margin: 0, fontSize: "1.25rem", fontWeight: 700, color: "#FFFFFF", letterSpacing: "0.5px" }}>
          Banking Platform
        </h2>
        <span style={{ fontSize: "0.75rem", color: "#94A3B8" }}>Retail Digital Banking</span>
      </div>

      <nav style={{ display: "flex", flexDirection: "column", gap: "6px" }}>
        {navItems.map((item) => (
          <NavLink
            key={item.to}
            to={item.to}
            style={({ isActive }) => ({
              display: "block",
              padding: "10px 14px",
              borderRadius: "6px",
              textDecoration: "none",
              fontSize: "0.95rem",
              fontWeight: 500,
              color: isActive ? "#FFFFFF" : "#CBD5E1",
              backgroundColor: isActive ? "#3B82F6" : "transparent",
              transition: "all 0.15s ease-in-out",
            })}
          >
            {item.label}
          </NavLink>
        ))}
      </nav>
    </aside>
  );
}

export default Sidebar;
