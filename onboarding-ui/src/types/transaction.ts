export interface Transaction {

  referenceNumber: string;

  sourceAccount: string;

  destinationAccount: string;

  amount: number;

  status: string;

  createdAt: string;
}
