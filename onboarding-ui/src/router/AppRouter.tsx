import {
  BrowserRouter,
  Route,
  Routes
} from "react-router-dom";

import LoginPage from "../pages/auth/LoginPage";

import DashboardPage from "../dashboard/DashboardPage";

import AccountsPage from "../pages/accounts/AccountsPage";

import TransferPage from "../pages/transfer/TransferPage";

import TransactionsPage from "../pages/transactions/TransactionsPage";

import ProfilePage from "../pages/profile/ProfilePage";

import DashboardLayout from "../components/layouts/DashboardLayout";

import ProtectedRoute from "./ProtectedRoute";

function AppRouter() {

  return (

    <BrowserRouter>

      <Routes>

        {/* Public Route */}

        <Route
          path="/"
          element={<LoginPage />}
        />

        {/* Protected Routes */}

        <Route
          element={
            <ProtectedRoute>

              <DashboardLayout />

            </ProtectedRoute>
          }
        >

          <Route
            path="/dashboard"
            element={<DashboardPage />}
          />

          <Route
            path="/accounts"
            element={<AccountsPage />}
          />

          <Route
            path="/transfers"
            element={<TransferPage />}
          />

          <Route
            path="/transactions"
            element={<TransactionsPage />}
          />

          <Route
            path="/profile"
            element={<ProfilePage />}
          />

        </Route>

      </Routes>

    </BrowserRouter>

  );
}

export default AppRouter;