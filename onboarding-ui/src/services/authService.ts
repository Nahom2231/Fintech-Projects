import api from "../api/axios";
import type { LoginRequest } from "../types/LoginRequest";
import type { LoginResponse } from "../types/LoginResponse";

export const login = async (
  request: LoginRequest
): Promise<LoginResponse> => {

  const response =
    await api.post<LoginResponse>(
      "/api/login",
      request
    );

  return response.data;
};