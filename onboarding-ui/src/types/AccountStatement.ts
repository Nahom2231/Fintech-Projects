export interface AccountStatement {

  referenceNumber: string;

  amount: number;

  sourceAccount: string;

  destinationAccount: string;

  status: string;

  createdAt: string;
}