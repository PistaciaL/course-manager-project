import JSEncrypt from 'jsencrypt';

const public_key = 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCYHB+b9FEz7tMOmccxCQNNcN5LhxgG3jPbD/NdZ6phSUT95axhjwXQG3omTdbpjCjry96tLW9K+xKdFcohkqjrBsWt4S9mT8AXv4ZGJtnNLXaFvbGQf5m7GLVyhsE35BnsmkL2SDQ0S6zGX9lawai+IPIMQl/l72Pa5OzO9eTQ5QIDAQAB'

let RSA = {
    encrypt(str) {
        let encrypt = new JSEncrypt();
        encrypt.setPublicKey(public_key);
        let encrypted = encrypt.encrypt(str);
        return encrypted
    }
};

export default RSA;