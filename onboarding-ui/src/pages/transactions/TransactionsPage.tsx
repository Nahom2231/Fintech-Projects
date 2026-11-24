import { useState } from "react";
import type { Transaction } from "../../types/transaction";
import { getTransactionHistory } from "../../services/transactionService";

function TransactionsPage() {
  const [accountNumber, setAccountNumber] = useState("");
  const [transactions, setTransactions] = useState<Transaction[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [searched, setSearched] = useState(false);

  const loadTransactions = async () => {
    if (!accountNumber.trim()) {
      setError("Please enter a valid account number to search.");
      return;
    }

    setLoading(true);
    setError(null);
    setSearched(true);

    try {
      const data = await getTransactionHistory(accountNumber.trim());
      setTransactions(data);
    } catch (err) {
      console.error("Failed to load transactions:", err);
      setError("Failed to retrieve transaction history. Verify the account number and backend server.");
      setTransactions([]);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ maxWidth: "800px" }}>
      <div style={{ marginBottom: "20px" }}>
        <h1 style={{ fontSize: "1.75rem", color: "#1E293B", margin: "0 0 6px 0" }}>
          Transaction History
        </h1>
        <p style={{ color: "#64748B", margin: 0 }}>
          Search and review completed debit and credit transactions by account number.
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
          placeholder="Enter Account Number (e.g. 1001234567)"
          value={accountNumber}
          onChange={(e) => setAccountNumber(e.target.value)}
          onKeyDown={(e) => e.key === "Enter" && loadTransactions()}
          style={{
            flex: 1,
            padding: "10px 14px",
            border: "1px solid #CBD5E1",
            borderRadius: "6px",
            fontSize: "0.95rem",
          }}
        />

        <button
          onClick={loadTransactions}
          disabled={loading}
          style={{
            background: "#2563EB",
            color: "white",
            border: "none",
            padding: "10px 20px",
            borderRadius: "6px",
            cursor: loading ? "not-allowed" : "pointer",
            fontWeight: 600,
            fontSize: "0.95rem",
          }}
        >
          {loading ? "Searching..." : "Search"}
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
          <p style={{ margin: 0, fontWeight: 500 }}>⏳ Loading transaction history...</p>
        </div>
      )}

      {!loading && searched && transactions.length === 0 && !error && (
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
            No Transactions Found
          </p>
          <p style={{ fontSize: "0.9rem", margin: 0 }}>
            No debit or credit records found for account: {accountNumber}
          </p>
        </div>
      )}

      {!loading && transactions.length > 0 && (
        <div style={{ display: "flex", flexDirection: "column", gap: "12px" }}>
          {transactions.map((transaction) => (
            <div
              key={transaction.referenceNumber}
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
                <span style={{ fontSize: "0.8rem", color: "#64748B", display: "block" }}>
                  Ref: {transaction.referenceNumber}
                </span>
                <p style={{ margin: "4px 0", fontSize: "0.95rem", color: "#1E293B" }}>
                  From: <strong>{transaction.sourceAccount}</strong> → To: <strong>{transaction.destinationAccount}</strong>
                </p>
                <span style={{ fontSize: "0.8rem", color: "#94A3B8" }}>
                  {transaction.createdAt ? new Date(transaction.createdAt).toLocaleString() : ""}
                </span>
              </div>

              <div style={{ textAlign: "right" }}>
                <span style={{ fontSize: "1.2rem", fontWeight: 700, color: "#0F172A", display: "block" }}>
                  ETB {Number(transaction.amount).toLocaleString(undefined, { minimumFractionDigits: 2 })}
                </span>
                <span
                  style={{
                    display: "inline-block",
                    marginTop: "4px",
                    padding: "3px 8px",
                    borderRadius: "4px",
                    fontSize: "0.75rem",
                    fontWeight: 600,
                    backgroundColor: transaction.status === "COMPLETED" ? "#ECFDF5" : "#FEF2F2",
                    color: transaction.status === "COMPLETED" ? "#065F46" : "#991B1B",
                  }}
                >
                  {transaction.status}
                </span>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default TransactionsPage;
