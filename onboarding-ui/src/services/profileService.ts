import api from "../api/axios";
import type { Profile } from "../types/Profile";

export const getProfile =
    async (
        customerId: number
    ): Promise<Profile> => {

        const response =
            await api.get<Profile>(
                `/api/customers/${customerId}`
            );

        return response.data;
    };