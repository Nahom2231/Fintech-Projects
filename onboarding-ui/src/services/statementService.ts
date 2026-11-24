import api from "../api/axios";
import type { AccountStatement } from "../types/AccountStatement";
import type { MiniStatement } from "../types/MiniStatement";

export const getMiniStatement =
    async (
        accountNumber: string
    ): Promise<MiniStatement[]> => {
        const response = await api.get<MiniStatement[]>(
            `/api/transfers/mini-statement/${accountNumber}`
        );

        return response.data;
    };

export const getAccountStatement =
    async (
        accountNumber: string
    ): Promise<AccountStatement[]> => {
        const response = await api.get<AccountStatement[]>(
            `/api/transfers/statement/${accountNumber}`
        );

        return response.data;
    };