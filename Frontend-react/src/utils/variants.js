import { easeIn } from 'framer-motion';

export const fadeIn = (delay, duration, direction, type) => ({
  hidden: {
    opacity: 0,
    y: direction === 'up' ? -150 : direction === 'down' ? 150 : 0,
    x: direction === 'left' ? -150 : direction === 'right' ? 150 : 0,
  },
  show: {
    opacity: 1,
    y: 0,
    x: 0,
    transtion: {
      type: type,
      delay: delay,
      duration: duration,
      ease: easeIn,
    },
  },
});

export const generateState = () => {
  const charset =
    'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
  const tokenLength = 32;
  let csrfToken = '';

  for (let i = 0; i < tokenLength; i++) {
    const randomIndex = Math.floor(Math.random() * charset.length);
    csrfToken += charset[randomIndex];
  }

  return csrfToken;
};

export const generateCodeVerifier = () => {
  var randomByteArray = new Uint8Array(32);
  window.crypto.getRandomValues(randomByteArray);
  return base64urlencode(randomByteArray);
};

export const base64urlencode = (sourceValue) => {
  var base64Encoded = btoa(String.fromCharCode.apply(null, sourceValue));
  return base64Encoded
    .replace(/\+/g, '-')
    .replace(/\//g, '_')
    .replace(/=+$/, '');
};

export const generateCodeChallange = async (codeVerifiedValue) => {
  var textEncoder = new TextEncoder('US-ASCII');
  var encodedValue = textEncoder.encode(codeVerifiedValue);
  var digest = await window.crypto.subtle.digest('SHA-256', encodedValue);
  return base64urlencode(Array.from(new Uint8Array(digest)));
};

export const getAuthCode = async () => {
  var codeChallange = await generateCodeChallange(generateCodeVerifier());

  const clientId = encodeURIComponent('pkcee-blog-client');
  const responseType = encodeURIComponent('code');
  const scope = encodeURIComponent('openid');
  const redirectUri = encodeURIComponent('http://localhost:5173/login');
  const state2 = encodeURIComponent('E7R4UyEgPTeMhCodhcpzmwXCk0dcSycO');
  const codeChallengeMethod = encodeURIComponent('S256');

  const url = `http://localhost:7080/realms/blogrealm/protocol/openid-connect/auth?client_id=${clientId}&response_type=${responseType}&scope=${scope}&redirect_uri=${redirectUri}&state=${state2}&code_challenge=${encodeURIComponent(
    codeChallange
  )}&code_challenge_method=${codeChallengeMethod}`;
  window.location.href = url;
};
