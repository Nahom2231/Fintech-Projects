import api from "../api/axios";

import type { TransferRequest }
from "../types/TransferRequest";

import type { TransferResponse }
from "../types/TransferResponse";

export const transferMoney =
  async (
    request: TransferRequest
  ): Promise<TransferResponse> => {

    const response =
      await api.post<
        TransferResponse
      >(
        "/api/transfers",
        request
      );

    return response.data;
};