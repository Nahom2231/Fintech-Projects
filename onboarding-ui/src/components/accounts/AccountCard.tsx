import type { Account } from "../../types/Account";

interface Props {
  account: Account;
}

function AccountCard({ account }: Props) {
  return (
    <div
      style={{
        background: "#FFFFFF",
        border: "1px solid #E2E8F0",
        borderRadius: "8px",
        padding: "20px",
        boxShadow: "0 1px 3px rgba(0,0,0,0.05)",
      }}
    >
      <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center", marginBottom: "12px" }}>
        <h3 style={{ margin: 0, fontSize: "1.15rem", color: "#0F172A" }}>
          {account.accountType} Account
        </h3>
        <span
          style={{
            fontSize: "0.75rem",
            fontWeight: 600,
            padding: "4px 8px",
            borderRadius: "4px",
            backgroundColor: account.status === "ACTIVE" ? "#ECFDF5" : "#F1F5F9",
            color: account.status === "ACTIVE" ? "#065F46" : "#475569",
          }}
        >
          {account.status}
        </span>
      </div>

      <p style={{ margin: "0 0 16px 0", color: "#64748B", fontSize: "0.9rem" }}>
        Account Number: <strong style={{ color: "#334155" }}>{account.accountNumber}</strong>
      </p>

      <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: "16px", paddingTop: "12px", borderTop: "1px solid #F1F5F9" }}>
        <div>
          <span style={{ fontSize: "0.8rem", color: "#64748B", display: "block" }}>Available Balance</span>
          <strong style={{ fontSize: "1.1rem", color: "#059669" }}>
            ETB {Number(account.availableBalance ?? 0).toLocaleString(undefined, { minimumFractionDigits: 2, maximumFractionDigits: 2 })}
          </strong>
        </div>

        <div>
          <span style={{ fontSize: "0.8rem", color: "#64748B", display: "block" }}>Ledger Balance</span>
          <strong style={{ fontSize: "1.1rem", color: "#334155" }}>
            ETB {Number(account.ledgerBalance ?? 0).toLocaleString(undefined, { minimumFractionDigits: 2, maximumFractionDigits: 2 })}
          </strong>
        </div>
      </div>
    </div>
  );
}

export default AccountCard;
