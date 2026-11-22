import api from "../api/axios";
import type { Account } from "../types/Account";

export const getAccounts =
  async (): Promise<Account[]> => {

    const response =
      await api.get<Account[]>(
        "/api/accounts"
      );

    return response.data;
};