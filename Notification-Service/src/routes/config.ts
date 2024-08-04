export interface MomoConfig {
    accessKey: string;
    secretKey: string;
    orderInfo: string;
    partnerCode: string;
    redirectUrl: string;
    ipnUrl: string;
    requestType: string;
    extraData: string;
    orderGroupId: string;
    autoCapture: boolean;
    lang: string;
  }
  
  const config: MomoConfig = {
    accessKey: 'F8BBA842ECF85',
    secretKey: 'K951B6PE1waDMi640xX08PD3vg6EkVlz',
    orderInfo: 'pay with MoMo',
    partnerCode: 'MOMO',
    redirectUrl: 'http://localhost:3000/user/purchase',
    ipnUrl: 'https://76d3-2001-ee1-df0b-c3d0-a09a-1941-d599-8826.ngrok-free.app/api/v1/notifications/callback/',
    requestType: 'payWithMethod',
    extraData: '',
    orderGroupId: '',
    autoCapture: true,
    lang: 'vi',
  };
  
  export default config;
  