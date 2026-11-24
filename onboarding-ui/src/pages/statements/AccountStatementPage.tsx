import { useState } from "react";
import type { AccountStatement } from "../../types/AccountStatement";
import { getAccountStatement } from "../../services/statementService";

function AccountStatementPage() {
  const [accountNumber, setAccountNumber] = useState("");
  const [records, setRecords] = useState<AccountStatement[]>([]);
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
      const data = await getAccountStatement(accountNumber.trim());
      setRecords(data);
    } catch (err) {
      console.error("Failed to load statement:", err);
      setError("Failed to load account statement. Verify account number and backend server.");
      setRecords([]);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ maxWidth: "800px" }}>
      <div style={{ marginBottom: "20px" }}>
        <h1 style={{ fontSize: "1.75rem", color: "#1E293B", margin: "0 0 6px 0" }}>
          Full Account Statement
        </h1>
        <p style={{ color: "#64748B", margin: 0 }}>
          Generate and review comprehensive debit and credit account statements.
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
          onChange={(event) => setAccountNumber(event.target.value)}
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
          {loading ? "Loading..." : "Generate Statement"}
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
          <p style={{ margin: 0, fontWeight: 500 }}>⏳ Generating statement...</p>
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
            No Statement Records
          </p>
          <p style={{ fontSize: "0.9rem", margin: 0 }}>
            No recorded activities found for account: {accountNumber}
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
                <p style={{ margin: "4px 0", fontSize: "0.95rem", color: "#1E293B" }}>
                  Source: <strong>{record.sourceAccount}</strong> → Destination: <strong>{record.destinationAccount}</strong>
                </p>
                <span style={{ fontSize: "0.8rem", color: "#94A3B8" }}>
                  {record.createdAt ? new Date(record.createdAt).toLocaleString() : ""}
                </span>
              </div>

              <div style={{ textAlign: "right" }}>
                <span style={{ fontSize: "1.15rem", fontWeight: 700, color: "#0F172A", display: "block" }}>
                  ETB {Number(record.amount).toLocaleString(undefined, { minimumFractionDigits: 2 })}
                </span>
                <span
                  style={{
                    display: "inline-block",
                    marginTop: "4px",
                    padding: "3px 8px",
                    borderRadius: "4px",
                    fontSize: "0.75rem",
                    fontWeight: 600,
                    backgroundColor: record.status === "COMPLETED" ? "#ECFDF5" : "#FEF2F2",
                    color: record.status === "COMPLETED" ? "#065F46" : "#991B1B",
                  }}
                >
                  {record.status}
                </span>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default AccountStatementPage;
