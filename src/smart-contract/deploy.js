const Web3 = require("web3");
const HDWalletProvider = require("truffle-hdwallet-provider");
require("dotenv").config();

const {abi, evm} = require("./build/ERC20Key.json"); // .json file path

const provider = new HDWalletProvider(
    "imitate paddle symbol begin region coconut resist wheel response more cabin bachelor",
    "https://artio.rpc.berachain.com"
);

const web3 = new Web3(provider);

const deploy = async() => {
    const accounts = await web3.eth.getAccounts();
    console.log(accounts);

    const contract = await new web3.eth.Contract(abi)
        .deploy({data: evm.bytecode.object, arguments: ["NMToken", 
"NMT"]})  // add arguments to constructor inside array
        .send({from: accounts[0], gas: "4000000"});
    console.log(contract.options.address);

    console.log(`name of our token is: ${await contract.methods.name().call()}`);
}

deploy();
