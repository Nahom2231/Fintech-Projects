import api from "../api/axios";

import type {
  DashboardSummary
} from "../types/DashboardSummary";

export const getDashboardSummary =
  async (): Promise<DashboardSummary> => {

    const response =
      await api.get<DashboardSummary>(
        "/api/dashboard/summary"
      );

    return response.data;
  };