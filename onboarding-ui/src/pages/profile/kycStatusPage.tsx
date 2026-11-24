import { useEffect, useState } from "react";
import { getKycDocuments } from "../../services/kycService";
import type { KycDocument } from "../../types/KycDocument";

function KycStatusPage() {
  const [documents, setDocuments] = useState<KycDocument[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    loadKycDocuments();
  }, []);

  const loadKycDocuments = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await getKycDocuments(1);
      setDocuments(data);
    } catch (err) {
      console.error("Failed to load KYC documents:", err);
      setError("Unable to load KYC verification status. Make sure the backend server is running.");
    } finally {
      setLoading(false);
    }
  };

  const getStatusBadge = (status: string) => {
    switch (status) {
      case "VERIFIED":
        return { text: "✅ VERIFIED", bg: "#ECFDF5", color: "#065F46", border: "#A7F3D0" };
      case "PENDING":
        return { text: "⏳ PENDING REVIEW", bg: "#FFFBEB", color: "#92400E", border: "#FDE68A" };
      case "REJECTED":
        return { text: "❌ REJECTED", bg: "#FEF2F2", color: "#991B1B", border: "#FECACA" };
      default:
        return { text: status, bg: "#F1F5F9", color: "#475569", border: "#CBD5E1" };
    }
  };

  return (
    <div style={{ maxWidth: "700px" }}>
      <h1 style={{ fontSize: "1.75rem", color: "#1E293B", marginBottom: "8px" }}>
        KYC Verification Status
      </h1>
      <p style={{ color: "#64748B", marginBottom: "20px" }}>
        Review your submitted verification documents and account compliance status.
      </p>

      {loading && (
        <div style={{ padding: "24px", background: "#F1F5F9", borderRadius: "8px", color: "#475569" }}>
          <p style={{ margin: 0, fontWeight: 500 }}>⏳ Loading KYC Status...</p>
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
            onClick={loadKycDocuments}
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

      {!loading && !error && documents.length === 0 && (
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
            No KYC Documents Found
          </p>
          <p style={{ fontSize: "0.9rem", margin: 0 }}>
            There are currently no verification documents submitted for this customer account.
          </p>
        </div>
      )}

      {!loading && !error && documents.length > 0 && (
        <div style={{ display: "flex", flexDirection: "column", gap: "12px" }}>
          {documents.map((document) => {
            const badge = getStatusBadge(document.status);
            return (
              <div
                key={document.id}
                style={{
                  background: "#FFFFFF",
                  border: "1px solid #E2E8F0",
                  borderRadius: "8px",
                  padding: "20px",
                  boxShadow: "0 1px 3px rgba(0,0,0,0.05)",
                  display: "flex",
                  justifyContent: "space-between",
                  alignItems: "center",
                }}
              >
                <div>
                  <h3 style={{ margin: "0 0 6px 0", color: "#0F172A", fontSize: "1.1rem" }}>
                    {document.documentType}
                  </h3>
                  <p style={{ margin: 0, color: "#64748B", fontSize: "0.9rem" }}>
                    Document Number: <strong>{document.documentNumber}</strong>
                  </p>
                </div>

                <span
                  style={{
                    backgroundColor: badge.bg,
                    color: badge.color,
                    border: `1px solid ${badge.border}`,
                    padding: "6px 12px",
                    borderRadius: "9999px",
                    fontWeight: 600,
                    fontSize: "0.85rem",
                  }}
                >
                  {badge.text}
                </span>
              </div>
            );
          })}
        </div>
      )}
    </div>
  );
}

export default KycStatusPage;
