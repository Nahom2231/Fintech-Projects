import api from "../api/axios";
import type { KycDocument } from "../types/KycDocument";

export const getKycDocuments =
  async (
    customerId: number
  ): Promise<KycDocument[]> => {

    const response =
      await api.get<KycDocument[]>(
        `/api/kyc/customer/${customerId}`
      );

    return response.data;
  };