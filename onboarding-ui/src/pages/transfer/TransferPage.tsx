import { useState } from "react";
import { transferMoney } from "../../services/transferService";

function TransferPage() {
  const [sourceAccount, setSourceAccount] = useState("");
  const [destinationAccount, setDestinationAccount] = useState("");
  const [amount, setAmount] = useState("");
  const [submitting, setSubmitting] = useState(false);
  const [feedback, setFeedback] = useState<{ type: "success" | "error"; text: string } | null>(null);

  const handleTransfer = async () => {
    setFeedback(null);

    const trimmedSource = sourceAccount.trim();
    const trimmedDest = destinationAccount.trim();
    const numAmount = Number(amount);

    if (!trimmedSource || !trimmedDest || !amount) {
      setFeedback({ type: "error", text: "Please fill in all transfer fields." });
      return;
    }

    if (isNaN(numAmount) || numAmount <= 0) {
      setFeedback({ type: "error", text: "Please enter a valid transfer amount greater than 0." });
      return;
    }

    if (trimmedSource === trimmedDest) {
      setFeedback({ type: "error", text: "Source and destination accounts must be different." });
      return;
    }

    setSubmitting(true);
    try {
      const response = await transferMoney({
        sourceAccount: trimmedSource,
        destinationAccount: trimmedDest,
        amount: numAmount,
      });

      setFeedback({
        type: "success",
        text: `${response.message || "Transfer completed successfully!"} (Ref: ${response.referenceNumber})`,
      });

      // Clear amount after successful transfer
      setAmount("");
    } catch (err: any) {
      console.error("Transfer error:", err);
      const errorMsg =
        err.response?.data?.message ||
        "Transfer failed. Please check account balances and ensure backend is reachable.";
      setFeedback({ type: "error", text: errorMsg });
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div style={{ maxWidth: "600px" }}>
      <div style={{ marginBottom: "20px" }}>
        <h1 style={{ fontSize: "1.75rem", color: "#1E293B", margin: "0 0 6px 0" }}>
          Transfer Money
        </h1>
        <p style={{ color: "#64748B", margin: 0 }}>
          Initiate an internal or intra-bank funds transfer securely.
        </p>
      </div>

      <div
        style={{
          background: "#FFFFFF",
          border: "1px solid #E2E8F0",
          borderRadius: "8px",
          padding: "24px",
          boxShadow: "0 1px 3px rgba(0,0,0,0.05)",
        }}
      >
        <div style={{ marginBottom: "16px" }}>
          <label style={{ display: "block", fontSize: "0.85rem", fontWeight: 600, color: "#334155", marginBottom: "6px" }}>
            Source Account Number
          </label>
          <input
            placeholder="e.g. 1001234567"
            value={sourceAccount}
            onChange={(e) => setSourceAccount(e.target.value)}
            style={{
              width: "100%",
              boxSizing: "border-box",
              padding: "10px 14px",
              border: "1px solid #CBD5E1",
              borderRadius: "6px",
              fontSize: "0.95rem",
            }}
          />
        </div>

        <div style={{ marginBottom: "16px" }}>
          <label style={{ display: "block", fontSize: "0.85rem", fontWeight: 600, color: "#334155", marginBottom: "6px" }}>
            Destination Account Number
          </label>
          <input
            placeholder="e.g. 1007654321"
            value={destinationAccount}
            onChange={(e) => setDestinationAccount(e.target.value)}
            style={{
              width: "100%",
              boxSizing: "border-box",
              padding: "10px 14px",
              border: "1px solid #CBD5E1",
              borderRadius: "6px",
              fontSize: "0.95rem",
            }}
          />
        </div>

        <div style={{ marginBottom: "20px" }}>
          <label style={{ display: "block", fontSize: "0.85rem", fontWeight: 600, color: "#334155", marginBottom: "6px" }}>
            Amount (ETB)
          </label>
          <input
            type="number"
            step="0.01"
            placeholder="0.00"
            value={amount}
            onChange={(e) => setAmount(e.target.value)}
            style={{
              width: "100%",
              boxSizing: "border-box",
              padding: "10px 14px",
              border: "1px solid #CBD5E1",
              borderRadius: "6px",
              fontSize: "0.95rem",
            }}
          />
        </div>

        <button
          onClick={handleTransfer}
          disabled={submitting}
          style={{
            width: "100%",
            background: "#2563EB",
            color: "white",
            border: "none",
            padding: "12px",
            borderRadius: "6px",
            fontSize: "1rem",
            fontWeight: 600,
            cursor: submitting ? "not-allowed" : "pointer",
            transition: "background 0.2s",
          }}
        >
          {submitting ? "Processing Transfer..." : "Confirm & Transfer"}
        </button>

        {feedback && (
          <div
            style={{
              marginTop: "20px",
              padding: "12px 16px",
              borderRadius: "6px",
              fontSize: "0.9rem",
              fontWeight: 500,
              backgroundColor: feedback.type === "success" ? "#ECFDF5" : "#FEF2F2",
              color: feedback.type === "success" ? "#065F46" : "#991B1B",
              border: `1px solid ${feedback.type === "success" ? "#A7F3D0" : "#F87171"}`,
            }}
          >
            {feedback.text}
          </div>
        )}
      </div>
    </div>
  );
}

export default TransferPage;
