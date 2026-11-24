import { useEffect, useState } from "react";
import type { Account } from "../../types/Account";
import AccountCard from "../../components/accounts/AccountCard";
import { getAccounts } from "../../services/accountService";

function AccountsPage() {
  const [accounts, setAccounts] = useState<Account[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    loadAccounts();
  }, []);

  const loadAccounts = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await getAccounts();
      setAccounts(data);
    } catch (err) {
      console.error("Failed to load accounts:", err);
      setError("Failed to load customer accounts. Ensure the backend server is running.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ maxWidth: "800px" }}>
      <div style={{ marginBottom: "20px" }}>
        <h1 style={{ fontSize: "1.75rem", color: "#1E293B", margin: "0 0 6px 0" }}>
          My Bank Accounts
        </h1>
        <p style={{ color: "#64748B", margin: 0 }}>
          Manage your savings, checking, and current deposit accounts.
        </p>
      </div>

      {loading && (
        <div style={{ padding: "24px", background: "#F1F5F9", borderRadius: "8px", color: "#475569" }}>
          <p style={{ margin: 0, fontWeight: 500 }}>⏳ Loading Accounts...</p>
        </div>
      )}

      {error && !loading && (
        <div
          style={{
            padding: "16px",
            background: "#FEF2F2",
            border: "1px solid #F87171",
            borderRadius: "8px",
            color: "#991B1B",
            marginBottom: "20px",
          }}
        >
          <p style={{ margin: "0 0 12px 0", fontWeight: 500 }}>{error}</p>
          <button
            onClick={loadAccounts}
            style={{
              background: "#DC2626",
              color: "white",
              border: "none",
              padding: "8px 16px",
              borderRadius: "4px",
              cursor: "pointer",
              fontWeight: 500,
            }}
          >
            Retry
          </button>
        </div>
      )}

      {!loading && !error && accounts.length === 0 && (
        <div
          style={{
            padding: "32px",
            textAlign: "center",
            background: "#F8FAFC",
            border: "2px dashed #CBD5E1",
            borderRadius: "8px",
            color: "#64748B",
          }}
        >
          <p style={{ fontSize: "1.1rem", fontWeight: 600, color: "#334155", margin: "0 0 8px 0" }}>
            No Accounts Found
          </p>
          <p style={{ fontSize: "0.9rem", margin: 0 }}>
            There are no active accounts registered for this profile yet.
          </p>
        </div>
      )}

      {!loading && !error && accounts.length > 0 && (
        <div style={{ display: "flex", flexDirection: "column", gap: "12px" }}>
          {accounts.map((account) => (
            <AccountCard key={account.id} account={account} />
          ))}
        </div>
      )}
    </div>
  );
}

export default AccountsPage;
