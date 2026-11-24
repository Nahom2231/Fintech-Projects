import { useState } from "react";
import { getMiniStatement } from "../../services/statementService";
import type { MiniStatement } from "../../types/MiniStatement";

function MiniStatementPage() {
  const [accountNumber, setAccountNumber] = useState("");
  const [records, setRecords] = useState<MiniStatement[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [searched, setSearched] = useState(false);

  const loadStatement = async () => {
    if (!accountNumber.trim()) {
      setError("Please enter an account number.");
      return;
    }

    setLoading(true);
    setError(null);
    setSearched(true);

    try {
      const data = await getMiniStatement(accountNumber.trim());
      setRecords(data);
    } catch (err) {
      console.error("Mini statement error:", err);
      setError("Failed to load mini statement. Check the account number and backend server.");
      setRecords([]);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ maxWidth: "800px" }}>
      <div style={{ marginBottom: "20px" }}>
        <h1 style={{ fontSize: "1.75rem", color: "#1E293B", margin: "0 0 6px 0" }}>
          Mini Statement (Last 5 Transactions)
        </h1>
        <p style={{ color: "#64748B", margin: 0 }}>
          Quick overview of the latest 5 transactions posted to the specified account.
        </p>
      </div>

      <div
        style={{
          display: "flex",
          gap: "12px",
          marginBottom: "20px",
          background: "#FFFFFF",
          padding: "16px",
          borderRadius: "8px",
          border: "1px solid #E2E8F0",
        }}
      >
        <input
          placeholder="Enter Account Number"
          value={accountNumber}
          onChange={(e) => setAccountNumber(e.target.value)}
          onKeyDown={(e) => e.key === "Enter" && loadStatement()}
          style={{
            flex: 1,
            padding: "10px 14px",
            border: "1px solid #CBD5E1",
            borderRadius: "6px",
            fontSize: "0.95rem",
          }}
        />

        <button
          onClick={loadStatement}
          disabled={loading}
          style={{
            background: "#2563EB",
            color: "white",
            border: "none",
            padding: "10px 20px",
            borderRadius: "6px",
            cursor: loading ? "not-allowed" : "pointer",
            fontWeight: 600,
          }}
        >
          {loading ? "Loading..." : "Get Mini Statement"}
        </button>
      </div>

      {error && (
        <div
          style={{
            padding: "14px 16px",
            background: "#FEF2F2",
            border: "1px solid #F87171",
            borderRadius: "8px",
            color: "#991B1B",
            marginBottom: "20px",
          }}
        >
          <p style={{ margin: 0, fontWeight: 500 }}>{error}</p>
        </div>
      )}

      {loading && (
        <div style={{ padding: "24px", background: "#F1F5F9", borderRadius: "8px", color: "#475569" }}>
          <p style={{ margin: 0, fontWeight: 500 }}>⏳ Loading recent transactions...</p>
        </div>
      )}

      {!loading && searched && records.length === 0 && !error && (
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
            No Recent Transactions
          </p>
          <p style={{ fontSize: "0.9rem", margin: 0 }}>
            No recent transaction history recorded for account: {accountNumber}
          </p>
        </div>
      )}

      {!loading && records.length > 0 && (
        <div style={{ display: "flex", flexDirection: "column", gap: "12px" }}>
          {records.map((record) => (
            <div
              key={record.referenceNumber}
              style={{
                background: "#FFFFFF",
                border: "1px solid #E2E8F0",
                borderRadius: "8px",
                padding: "16px 20px",
                boxShadow: "0 1px 3px rgba(0,0,0,0.05)",
                display: "flex",
                justifyContent: "space-between",
                alignItems: "center",
              }}
            >
              <div>
                <span style={{ fontSize: "0.8rem", color: "#64748B" }}>Ref: {record.referenceNumber}</span>
                <p style={{ margin: "4px 0", fontSize: "0.85rem", color: "#64748B" }}>
                  {record.createdAt ? new Date(record.createdAt).toLocaleString() : ""}
                </p>
              </div>

              <strong style={{ fontSize: "1.15rem", color: "#0F172A" }}>
                ETB {Number(record.amount).toLocaleString(undefined, { minimumFractionDigits: 2 })}
              </strong>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default MiniStatementPage;
