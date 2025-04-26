# grpc-client-streaming
![0_UA-3y-3o6gO-IxrP](https://github.com/user-attachments/assets/7ef2d587-bee8-4011-9e47-aacb85b65020)

How to install `grpcurl` on **both Mac and Windows**. step-by-step! guide 🚀

---

### 👉 For **Mac** (using Homebrew — easiest way):

```bash
brew install grpcurl
```
✅ Done!

> If you don't have Homebrew, first install it with:
> ```bash
> /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
> ```

---

### 👉 For **Windows** (two easy options):

#### Option 1: Using **Scoop** (recommended)

If you have Scoop installed:
```bash
scoop install grpcurl
```

> If you don't have Scoop yet:
> ```powershell
> Set-ExecutionPolicy RemoteSigned -Scope CurrentUser
> irm get.scoop.sh | iex
> ```
> Then install `grpcurl`!

---

#### Option 2: Download the **.exe** directly

1. Go to the releases page:  
   👉 [https://github.com/fullstorydev/grpcurl/releases](https://github.com/fullstorydev/grpcurl/releases)
   
2. Find the latest release, and download `grpcurl_windows_x86_64.exe`.

3. Rename it to `grpcurl.exe` (optional but clean).

4. Add the folder containing `grpcurl.exe` to your system's **PATH**.

✅ Done!

---

### ⚡ Quick Test (after install):

Try:
```bash
grpcurl --help
```
If you see the help text, it's working perfectly!

---

### ⚡ placeBulkOrder Test (Client Streaming validation):

```bash
grpcurl -d @ \
  -plaintext \
  -import-path ~/Downloads/stock-trading-server/src/main/proto \
  -proto stock_trading.proto \
  localhost:9090 \
  stocktrading.StockTradingService/bulkStockOrder < order.txt
```
