import { useState } from "react";
import { useNavigate } from "react-router-dom";

import {
  saveAccessToken,
  saveRefreshToken
} from "../../services/tokenService";

import { login } from "../../services/authService";

function LoginPage() {

  const navigate =
    useNavigate();

  const [phoneNumber,
         setPhoneNumber] =
    useState("");

  const [deviceId,
         setDeviceId] =
    useState("");

  const [ipAddress,
         setIpAddress] =
    useState("");

  const handleLogin =
    async () => {

      try {

        const response =
          await login({

            phoneNumber,

            deviceId,

            ipAddress

          });

        saveAccessToken(
          response.accessToken
        );

        saveRefreshToken(
          response.refreshToken
        );

        navigate(
          "/dashboard"
        );

      } catch {

        alert(
          "Login failed"
        );
      }
    };

  return (

    <div>

      <h1>
        Digital Banking Login
      </h1>

      <input
        placeholder="Phone Number"
        value={phoneNumber}
        onChange={(event) =>
          setPhoneNumber(
            event.target.value
          )
        }
      />

      <br />
      <br />

      <input
        placeholder="Device ID"
        value={deviceId}
        onChange={(event) =>
          setDeviceId(
            event.target.value
          )
        }
      />

      <br />
      <br />

      <input
        placeholder="IP Address"
        value={ipAddress}
        onChange={(event) =>
          setIpAddress(
            event.target.value
          )
        }
      />

      <br />
      <br />

      <button
        onClick={
          handleLogin
        }
      >

        Login

      </button>

    </div>

  );
}

export default LoginPage;