import { useEffect, useState } from "react";

import type { Account } from "../../types/Account";

import AccountCard from "../../components/accounts/AccountCard";

import { getAccounts } from "../../services/accountService";

function AccountsPage() {

  const [accounts, setAccounts] =
    useState<Account[]>([]);

  useEffect(() => {
    loadAccounts();
  }, []);

  const loadAccounts = async () => {

    try {

      const data =
        await getAccounts();

      setAccounts(data);

    } catch {

      alert(
        "Failed to load accounts"
      );

    }
  };

  return (

    <div>

      <h1>

        My Accounts

      </h1>

      {

        accounts.map(
          account => (

            <AccountCard
              key={account.id}
              account={account}
            />

          )
        )

      }

    </div>

  );
}

export default AccountsPage;