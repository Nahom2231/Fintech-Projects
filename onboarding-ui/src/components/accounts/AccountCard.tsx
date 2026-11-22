import type { Account } from "../../types/Account";

interface Props {
  account: Account;
}

function AccountCard(
  { account }: Props
) {
  return (
    <div
      style={{
        border: "1px solid #ddd",
        padding: "20px",
        marginBottom: "10px",
        borderRadius: "8px"
      }}
    >
      <h3>{account.accountType}</h3>

      <p>
        Account Number:
        {" "}
        {account.accountNumber}
      </p>

      <p>
        Available Balance:
        {" "}
        ETB {account.availableBalance}
      </p>

      <p>
        Ledger Balance:
        {" "}
        ETB {account.ledgerBalance}
      </p>
    </div>
  );
}

export default AccountCard;