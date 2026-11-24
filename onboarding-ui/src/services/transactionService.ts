import api from "../api/axios";

import type { Transaction }
from "../types/transaction";

export const getTransactionHistory =
  async (
    accountNumber: string
  ): Promise<Transaction[]> => {

    const response =
      await api.get<
        Transaction[]
      >(
        `/api/transfers/history/${accountNumber}`
      );

    return response.data;
};