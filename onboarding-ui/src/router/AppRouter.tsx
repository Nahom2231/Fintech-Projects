import { BrowserRouter, Route, Routes } from "react-router-dom";
import LoginPage from "../pages/auth/LoginPage";
import DashboardPage from "../pages/dashboard/DashboardPage";
import AccountsPage from "../pages/accounts/AccountsPage";
import TransferPage from "../pages/transfer/TransferPage";
import TransactionsPage from "../pages/transactions/TransactionsPage";
import KycStatusPage from "../pages/profile/kycStatusPage";
import ProfilePage from "../pages/profile/ProfilePage";
import AccountStatementPage from "../pages/statements/AccountStatementPage";
import MiniStatementPage from "../pages/statements/MiniStatementPage";
import DashboardLayout from "../components/layouts/DashboardLayout";
import ProtectedRoute from "./ProtectedRoute";

function AppRouter() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Public Route */}
        <Route path="/" element={<LoginPage />} />

        {/* Protected Routes */}
        <Route
          element={
            <ProtectedRoute>
              <DashboardLayout />
            </ProtectedRoute>
          }
        >
          <Route path="/dashboard" element={<DashboardPage />} />
          <Route path="/accounts" element={<AccountsPage />} />
          <Route path="/transfers" element={<TransferPage />} />
          <Route path="/transactions" element={<TransactionsPage />} />
          <Route path="/statement" element={<AccountStatementPage />} />
          <Route path="/mini-statement" element={<MiniStatementPage />} />
          <Route path="/profile" element={<ProfilePage />} />
          <Route path="/kyc" element={<KycStatusPage />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default AppRouter;
