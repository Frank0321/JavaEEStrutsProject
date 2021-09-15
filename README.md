# 飲料販賣機 - 以 Struts2 實作 JavaEE

- 此作品主要分成三個部分 : 登入畫面、前台、後台
- 以 Struts 2  Framework 的方式進行撰寫



## 登入

功能說明 :

1. 明確判斷使用者是否輸入帳號、是否有該帳號資料、與資料庫連線判別密碼是否正確。
2. 登入成功後，並儲存使用者資料到 session 裡面，並顯示前臺購物平台的畫面。
3. 藉由 filter 確保在前端與後端操作的下，皆有保持使用者登入的狀況。若有已有登出後，則無法單獨使用 url 的方法進到前後端的頁面。

![image](https://user-images.githubusercontent.com/61242521/133470889-2e98d3ea-42a7-4f4b-bcba-c7d063988487.png)



## 前台

功能說明 :

1. 登入後，動態顯示使用者名稱在頁面上。
2. 販售商品種類、價錢、庫存、圖片、皆從資料庫回傳顯示，並過濾掉 ”下架”、” 庫存為0” 的商品，且購買數量最大值不會超過庫存
3. 回傳資料並以一頁顯示最多六筆資料為主，動態產生分頁總共分頁數，可藉由頁碼或是左右鍵進行切換。
4. 購買好商品種類與數量後，會計算支付金額是否足夠，若足夠則會進行購買，並依剩餘庫存量更新顯示商品，與儲存訂單到資料庫裏面。

![image](https://user-images.githubusercontent.com/61242521/133470977-de1fb525-63b3-401b-9b31-55aaa28d7454.png)



## 後台

後臺有如下所示的五項功能：



### 商品表列

功能介紹 :

1. 為後端預設的首頁，連接到後端的任何一個頁面時，都會經過一個 filter 判斷是否有暫存的全部商品資料，沒有則利用 Dao 產生，避免資料庫多次連線。
2. 該表列僅需要此連結網址，則會自動產生全部商品資訊的狀態。

![image](https://user-images.githubusercontent.com/61242521/133471005-ec205659-6cb0-4781-ae65-a38130baed50.png)


### 商品刪除下架

功能介紹 :

1. 點選刪除後，則會將此筆資料從資料庫中移除掉
2. 在原先資料庫的建立中，訂單資料表與商品資料表中的產品 ID 有利用 FOREIGN KEY，綁再一起，因此已購買過的商品，則無法進行刪除。

![image](https://user-images.githubusercontent.com/61242521/133471026-7adb27e2-d9be-4db7-82b9-9a22fab264bc.png)


### 商品修改與補貨作業

功能介紹 : 

1. 利用下拉式選單選擇需要更新資料的產品。
2. 選擇好之後，會利用 JavaScript 將資料動態匯進網頁中。
3. 選擇要更改的價格、數量、狀態後，則會寫入資料庫中，若呼叫到此筆資料，則會顯示更新後的資訊

![image](https://user-images.githubusercontent.com/61242521/133471055-c7b225c8-b820-4ddc-a67c-ad63a79cf5d0.png)


### 商品新增上架

功能介紹 :

1. 依序輸入欄位資料且上傳圖片後，則可以在前端商品的頁面中，看到新增的商品與圖片

![image](https://user-images.githubusercontent.com/61242521/133471101-560838ae-f7f9-469a-8956-127cbc9816ea.png)
![image](https://user-images.githubusercontent.com/61242521/133471114-8bdaa3b2-dea8-4b07-8c40-3f3f512a4600.png)


### 銷售報表

功能介紹 :

1. 選擇日期的期間，則會顯示該段區間的購買成功的紀錄(訂單)

![image](https://user-images.githubusercontent.com/61242521/133471129-99154a16-19bf-4ef8-bc00-75bb1a8d286e.png)




