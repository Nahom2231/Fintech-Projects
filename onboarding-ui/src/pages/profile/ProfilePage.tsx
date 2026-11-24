import { useEffect, useState } from "react";
import type { Profile } from "../../types/Profile";
import { getProfile } from "../../services/profileService";

function ProfilePage() {
  const [profile, setProfile] = useState<Profile | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    loadProfile();
  }, []);

  const loadProfile = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await getProfile(1);
      setProfile(data);
    } catch (err) {
      console.error("Failed to load profile:", err);
      setError("Unable to load customer profile. Please ensure the backend is running and customer records exist.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ maxWidth: "700px" }}>
      <h1 style={{ fontSize: "1.75rem", color: "#1E293B", marginBottom: "8px" }}>
        Customer Profile
      </h1>
      <p style={{ color: "#64748B", marginBottom: "20px" }}>
        Account holder personal identification and contact information.
      </p>

      {loading && (
        <div style={{ padding: "24px", background: "#F1F5F9", borderRadius: "8px", color: "#475569" }}>
          <p style={{ margin: 0, fontWeight: 500 }}>⏳ Loading Profile...</p>
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
            onClick={loadProfile}
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

      {profile && !loading && (
        <div
          style={{
            background: "#FFFFFF",
            border: "1px solid #E2E8F0",
            borderRadius: "8px",
            padding: "24px",
            boxShadow: "0 1px 3px rgba(0,0,0,0.05)",
          }}
        >
          <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: "16px" }}>
            <div>
              <span style={{ fontSize: "0.85rem", color: "#64748B", display: "block" }}>Full Name</span>
              <strong style={{ fontSize: "1.1rem", color: "#0F172A" }}>{profile.fullName}</strong>
            </div>

            <div>
              <span style={{ fontSize: "0.85rem", color: "#64748B", display: "block" }}>Phone Number</span>
              <strong style={{ fontSize: "1.1rem", color: "#0F172A" }}>{profile.phoneNumber}</strong>
            </div>

            <div>
              <span style={{ fontSize: "0.85rem", color: "#64748B", display: "block" }}>Email Address</span>
              <strong style={{ fontSize: "1.1rem", color: "#0F172A" }}>{profile.email}</strong>
            </div>

            <div>
              <span style={{ fontSize: "0.85rem", color: "#64748B", display: "block" }}>National ID</span>
              <strong style={{ fontSize: "1.1rem", color: "#0F172A" }}>{profile.nationalId}</strong>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default ProfilePage;
