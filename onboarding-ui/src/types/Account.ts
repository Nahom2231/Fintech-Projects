export interface Account {
  id: number;
  accountNumber: string;
  accountType: string;
  status: string;
  availableBalance: number;
  ledgerBalance: number;
}