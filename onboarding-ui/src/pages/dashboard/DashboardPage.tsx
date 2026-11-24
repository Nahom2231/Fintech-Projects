import { useEffect, useState } from "react";
import type { DashboardSummary } from "../../types/DashboardSummary";
import { getDashboardSummary } from "../../services/dashboardService";

function DashboardPage() {
  const [summary, setSummary] = useState<DashboardSummary | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    loadDashboard();
  }, []);

  const loadDashboard = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await getDashboardSummary();
      setSummary(data);
    } catch (err) {
      console.error("Dashboard error:", err);
      setError("Failed to load dashboard metrics. Ensure the backend server is running.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ maxWidth: "900px" }}>
      <div style={{ marginBottom: "24px" }}>
        <h1 style={{ fontSize: "1.75rem", color: "#1E293B", margin: "0 0 6px 0" }}>
          Digital Banking Overview
        </h1>
        <p style={{ color: "#64748B", margin: 0 }}>
          High-level snapshot of your retail accounts, ledger balances, and transactions.
        </p>
      </div>

      {loading && (
        <div style={{ padding: "24px", background: "#F1F5F9", borderRadius: "8px", color: "#475569" }}>
          <p style={{ margin: 0, fontWeight: 500 }}>⏳ Loading Dashboard Metrics...</p>
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
            onClick={loadDashboard}
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

      {summary && !loading && (
        <div
          style={{
            display: "grid",
            gridTemplateColumns: "repeat(auto-fit, minmax(200px, 1fr))",
            gap: "20px",
          }}
        >
          <div
            style={{
              background: "#FFFFFF",
              border: "1px solid #E2E8F0",
              borderRadius: "8px",
              padding: "20px",
              boxShadow: "0 1px 3px rgba(0,0,0,0.05)",
            }}
          >
            <span style={{ fontSize: "0.85rem", color: "#64748B", fontWeight: 500 }}>Total Accounts</span>
            <h2 style={{ fontSize: "2rem", margin: "10px 0 0 0", color: "#0F172A" }}>
              {summary.totalAccounts}
            </h2>
          </div>

          <div
            style={{
              background: "#FFFFFF",
              border: "1px solid #E2E8F0",
              borderRadius: "8px",
              padding: "20px",
              boxShadow: "0 1px 3px rgba(0,0,0,0.05)",
            }}
          >
            <span style={{ fontSize: "0.85rem", color: "#64748B", fontWeight: 500 }}>Total Available Balance</span>
            <h2 style={{ fontSize: "1.75rem", margin: "10px 0 0 0", color: "#059669" }}>
              ETB {Number(summary.totalBalance).toLocaleString(undefined, { minimumFractionDigits: 2, maximumFractionDigits: 2 })}
            </h2>
          </div>

          <div
            style={{
              background: "#FFFFFF",
              border: "1px solid #E2E8F0",
              borderRadius: "8px",
              padding: "20px",
              boxShadow: "0 1px 3px rgba(0,0,0,0.05)",
            }}
          >
            <span style={{ fontSize: "0.85rem", color: "#64748B", fontWeight: 500 }}>Total Transactions</span>
            <h2 style={{ fontSize: "2rem", margin: "10px 0 0 0", color: "#2563EB" }}>
              {summary.totalTransactions}
            </h2>
          </div>

          <div
            style={{
              background: "#FFFFFF",
              border: "1px solid #E2E8F0",
              borderRadius: "8px",
              padding: "20px",
              boxShadow: "0 1px 3px rgba(0,0,0,0.05)",
            }}
          >
            <span style={{ fontSize: "0.85rem", color: "#64748B", fontWeight: 500 }}>KYC Compliance</span>
            <div style={{ marginTop: "12px" }}>
              <span
                style={{
                  display: "inline-block",
                  padding: "6px 12px",
                  borderRadius: "9999px",
                  fontSize: "0.85rem",
                  fontWeight: 600,
                  backgroundColor: summary.kycStatus === "VERIFIED" ? "#ECFDF5" : "#FFFBEB",
                  color: summary.kycStatus === "VERIFIED" ? "#065F46" : "#92400E",
                  border: `1px solid ${summary.kycStatus === "VERIFIED" ? "#A7F3D0" : "#FDE68A"}`,
                }}
              >
                {summary.kycStatus}
              </span>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default DashboardPage;
