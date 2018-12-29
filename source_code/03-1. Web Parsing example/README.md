
# 시카고 샌드위치 맛집 분석


## 웹 데이터를 가져오는 Beautiful Soup 익히기


```python
from bs4 import BeautifulSoup
```


```python
page = open("../data/03. test_first.html",'r').read()
soup = BeautifulSoup(page, 'html.parser')
print(soup.prettify())
```

    <!DOCTYPE html>
    <html>
     <head>
      <title>
       Very Simple HTML Code by PinkWink
      </title>
     </head>
     <body>
      <div>
       <p class="inner-text first-item" id="first">
        Happy PinkWink.
        <a href="http://www.pinkwink.kr" id="pw-link">
         PinkWink
        </a>
       </p>
       <p class="inner-text second-item">
        Happy Data Science.
        <a href="https://www.python.org" id="py-link">
         Python
        </a>
       </p>
      </div>
      <p class="outer-text first-item" id="second">
       <b>
        Data Science is funny.
       </b>
      </p>
      <p class="outer-text">
       <b>
        All I need is Love.
       </b>
      </p>
     </body>
    </html>
    


```python
list(soup.children)
```




    ['html', '\n', <html>
     <head>
     <title>Very Simple HTML Code by PinkWink</title>
     </head>
     <body>
     <div>
     <p class="inner-text first-item" id="first">
                     Happy PinkWink.
                     <a href="http://www.pinkwink.kr" id="pw-link">PinkWink</a>
     </p>
     <p class="inner-text second-item">
                     Happy Data Science.
                     <a href="https://www.python.org" id="py-link">Python</a>
     </p>
     </div>
     <p class="outer-text first-item" id="second">
     <b>
                     Data Science is funny.
                 </b>
     </p>
     <p class="outer-text">
     <b>
                     All I need is Love.
                 </b>
     </p>
     </body>
     </html>]




```python
html = list(soup.children)[2]
html
```




    <html>
    <head>
    <title>Very Simple HTML Code by PinkWink</title>
    </head>
    <body>
    <div>
    <p class="inner-text first-item" id="first">
                    Happy PinkWink.
                    <a href="http://www.pinkwink.kr" id="pw-link">PinkWink</a>
    </p>
    <p class="inner-text second-item">
                    Happy Data Science.
                    <a href="https://www.python.org" id="py-link">Python</a>
    </p>
    </div>
    <p class="outer-text first-item" id="second">
    <b>
                    Data Science is funny.
                </b>
    </p>
    <p class="outer-text">
    <b>
                    All I need is Love.
                </b>
    </p>
    </body>
    </html>




```python
list(html.children)
```




    ['\n', <head>
     <title>Very Simple HTML Code by PinkWink</title>
     </head>, '\n', <body>
     <div>
     <p class="inner-text first-item" id="first">
                     Happy PinkWink.
                     <a href="http://www.pinkwink.kr" id="pw-link">PinkWink</a>
     </p>
     <p class="inner-text second-item">
                     Happy Data Science.
                     <a href="https://www.python.org" id="py-link">Python</a>
     </p>
     </div>
     <p class="outer-text first-item" id="second">
     <b>
                     Data Science is funny.
                 </b>
     </p>
     <p class="outer-text">
     <b>
                     All I need is Love.
                 </b>
     </p>
     </body>, '\n']




```python
body = list(html.children)[3]
body
```




    <body>
    <div>
    <p class="inner-text first-item" id="first">
                    Happy PinkWink.
                    <a href="http://www.pinkwink.kr" id="pw-link">PinkWink</a>
    </p>
    <p class="inner-text second-item">
                    Happy Data Science.
                    <a href="https://www.python.org" id="py-link">Python</a>
    </p>
    </div>
    <p class="outer-text first-item" id="second">
    <b>
                    Data Science is funny.
                </b>
    </p>
    <p class="outer-text">
    <b>
                    All I need is Love.
                </b>
    </p>
    </body>




```python
soup.body
```




    <body>
    <div>
    <p class="inner-text first-item" id="first">
                    Happy PinkWink.
                    <a href="http://www.pinkwink.kr" id="pw-link">PinkWink</a>
    </p>
    <p class="inner-text second-item">
                    Happy Data Science.
                    <a href="https://www.python.org" id="py-link">Python</a>
    </p>
    </div>
    <p class="outer-text first-item" id="second">
    <b>
                    Data Science is funny.
                </b>
    </p>
    <p class="outer-text">
    <b>
                    All I need is Love.
                </b>
    </p>
    </body>




```python
list(body.children)
```




    ['\n', <div>
     <p class="inner-text first-item" id="first">
                     Happy PinkWink.
                     <a href="http://www.pinkwink.kr" id="pw-link">PinkWink</a>
     </p>
     <p class="inner-text second-item">
                     Happy Data Science.
                     <a href="https://www.python.org" id="py-link">Python</a>
     </p>
     </div>, '\n', <p class="outer-text first-item" id="second">
     <b>
                     Data Science is funny.
                 </b>
     </p>, '\n', <p class="outer-text">
     <b>
                     All I need is Love.
                 </b>
     </p>, '\n']




```python
len(list(body.children))
```




    7




```python
soup.find_all('p')
```




    [<p class="inner-text first-item" id="first">
                     Happy PinkWink.
                     <a href="http://www.pinkwink.kr" id="pw-link">PinkWink</a>
     </p>, <p class="inner-text second-item">
                     Happy Data Science.
                     <a href="https://www.python.org" id="py-link">Python</a>
     </p>, <p class="outer-text first-item" id="second">
     <b>
                     Data Science is funny.
                 </b>
     </p>, <p class="outer-text">
     <b>
                     All I need is Love.
                 </b>
     </p>]




```python
soup.find('p')
```




    <p class="inner-text first-item" id="first">
                    Happy PinkWink.
                    <a href="http://www.pinkwink.kr" id="pw-link">PinkWink</a>
    </p>




```python
soup.find_all('p', class_='outer-text')
```




    [<p class="outer-text first-item" id="second">
     <b>
                     Data Science is funny.
                 </b>
     </p>, <p class="outer-text">
     <b>
                     All I need is Love.
                 </b>
     </p>]




```python
soup.find_all(class_="outer-text")
```




    [<p class="outer-text first-item" id="second">
     <b>
                     Data Science is funny.
                 </b>
     </p>, <p class="outer-text">
     <b>
                     All I need is Love.
                 </b>
     </p>]




```python
soup.find_all(id="first")
```




    [<p class="inner-text first-item" id="first">
                     Happy PinkWink.
                     <a href="http://www.pinkwink.kr" id="pw-link">PinkWink</a>
     </p>]




```python
soup.find('p')
```




    <p class="inner-text first-item" id="first">
                    Happy PinkWink.
                    <a href="http://www.pinkwink.kr" id="pw-link">PinkWink</a>
    </p>




```python
list(soup.children)
```




    ['html', '\n', <html>
     <head>
     <title>Very Simple HTML Code by PinkWink</title>
     </head>
     <body>
     <div>
     <p class="inner-text first-item" id="first">
                     Happy PinkWink.
                     <a href="http://www.pinkwink.kr" id="pw-link">PinkWink</a>
     </p>
     <p class="inner-text second-item">
                     Happy Data Science.
                     <a href="https://www.python.org" id="py-link">Python</a>
     </p>
     </div>
     <p class="outer-text first-item" id="second">
     <b>
                     Data Science is funny.
                 </b>
     </p>
     <p class="outer-text">
     <b>
                     All I need is Love.
                 </b>
     </p>
     </body>
     </html>]




```python
soup.head
```




    <head>
    <title>Very Simple HTML Code by PinkWink</title>
    </head>




```python
soup.head.next_sibling
```




    '\n'




```python
soup.head.previous_sibling
```




    '\n'




```python
soup.head.next_sibling.next_sibling
```




    <body>
    <div>
    <p class="inner-text first-item" id="first">
                    Happy PinkWink.
                    <a href="http://www.pinkwink.kr" id="pw-link">PinkWink</a>
    </p>
    <p class="inner-text second-item">
                    Happy Data Science.
                    <a href="https://www.python.org" id="py-link">Python</a>
    </p>
    </div>
    <p class="outer-text first-item" id="second">
    <b>
                    Data Science is funny.
                </b>
    </p>
    <p class="outer-text">
    <b>
                    All I need is Love.
                </b>
    </p>
    </body>




```python
body.p
```




    <p class="inner-text first-item" id="first">
                    Happy PinkWink.
                    <a href="http://www.pinkwink.kr" id="pw-link">PinkWink</a>
    </p>




```python
body.p.next_sibling.next_sibling
```




    <p class="inner-text second-item">
                    Happy Data Science.
                    <a href="https://www.python.org" id="py-link">Python</a>
    </p>




```python
for each_tag in soup.find_all('p'):
    print(each_tag.get_text())
```

    
                    Happy PinkWink.
                    PinkWink
    
    
                    Happy Data Science.
                    Python
    
    
    
                    Data Science is funny.
                
    
    
    
                    All I need is Love.
                
    
    


```python
body.get_text()
```




    '\n\n\n                Happy PinkWink.\n                PinkWink\n\n\n                Happy Data Science.\n                Python\n\n\n\n\n                Data Science is funny.\n            \n\n\n\n                All I need is Love.\n            \n\n'




```python
links = soup.find_all('a')
links
```




    [<a href="http://www.pinkwink.kr" id="pw-link">PinkWink</a>,
     <a href="https://www.python.org" id="py-link">Python</a>]




```python
for each in links:
    href = each['href']
    text = each.string
    print(text + ' -> ' + href)
```

    PinkWink -> http://www.pinkwink.kr
    Python -> https://www.python.org
    

## 크롬 개발자 도구를 이용해서 원하는 태그 찾기


```python
from urllib.request import urlopen
```


```python
url = "http://info.finance.naver.com/marketindex/"
page = urlopen(url)

soup = BeautifulSoup(page, "html.parser")

print(soup.prettify())
```

    <script language="javascript" src="https://finance.naver.com/template/head_js.nhn?referer=info.finance.naver.com&amp;menu=marketindex&amp;submenu=market">
    </script>
    <script src="/js/info/jindo.min.ns.1.5.3.euckr.js" type="text/javascript">
    </script>
    <script src="/js/jindo.1.5.3.element-text-patch.js" type="text/javascript">
    </script>
    <div id="container" style="padding-bottom:0px;">
     <script language="JavaScript" src="/js/flashObject.js?20181220153244">
     </script>
     <div class="market_include">
      <div class="market_data">
       <div class="market1">
        <div class="title">
         <h2 class="h_market1">
          <span>
           환전 고시 환율
          </span>
         </h2>
        </div>
        <!-- data -->
        <div class="data">
         <ul class="data_lst" id="exchangeList">
          <li class="on">
           <a class="head usd" href="/marketindex/exchangeDetail.nhn?marketindexCd=FX_USDKRW" onclick="clickcr(this, 'fr1.usdt', '', '', event);">
            <h3 class="h_lst">
             <span class="blind">
              미국 USD
             </span>
            </h3>
            <div class="head_info point_dn">
             <span class="value">
              1,117.00
             </span>
             <span class="txt_krw">
              <span class="blind">
               원
              </span>
             </span>
             <span class="change">
              5.00
             </span>
             <span class="blind">
              하락
             </span>
            </div>
           </a>
           <a class="graph_img" href="/marketindex/exchangeDetail.nhn?marketindexCd=FX_USDKRW" onclick="clickcr(this, 'fr1.usdc', '', '', event);">
            <img alt="" height="153" src="https://ssl.pstatic.net/imgfinance/chart/marketindex/FX_USDKRW.png" width="295"/>
           </a>
           <div class="graph_info">
            <span class="time">
             2018.12.28 22:00
            </span>
            <span class="source">
             KEB하나은행 기준
            </span>
            <span class="count">
             고시회차
             <span class="num">
              261
             </span>
             회
            </span>
           </div>
          </li>
          <li class="">
           <a class="head jpy" href="/marketindex/exchangeDetail.nhn?marketindexCd=FX_JPYKRW" onclick="clickcr(this, 'fr1.jpyt', '', '', event);">
            <h3 class="h_lst">
             <span class="blind">
              일본 JPY(100엔)
             </span>
            </h3>
            <div class="head_info point_dn">
             <span class="value">
              1,012.37
             </span>
             <span class="txt_krw">
              <span class="blind">
               원
              </span>
             </span>
             <span class="change">
              0.13
             </span>
             <span class="blind">
              하락
             </span>
            </div>
           </a>
           <a class="graph_img" href="/marketindex/exchangeDetail.nhn?marketindexCd=FX_JPYKRW" onclick="clickcr(this, 'fr1.jpyc', '', '', event);">
            <img alt="" height="153" src="https://ssl.pstatic.net/imgfinance/chart/marketindex/FX_JPYKRW.png" width="295"/>
           </a>
           <div class="graph_info">
            <span class="time">
             2018.12.28 22:00
            </span>
            <span class="source">
             KEB하나은행 기준
            </span>
            <span class="count">
             고시회차
             <span class="num">
              261
             </span>
             회
            </span>
           </div>
          </li>
          <li class="">
           <a class="head eur" href="/marketindex/exchangeDetail.nhn?marketindexCd=FX_EURKRW" onclick="clickcr(this, 'fr1.eurt', '', '', event);">
            <h3 class="h_lst">
             <span class="blind">
              유럽연합 EUR
             </span>
            </h3>
            <div class="head_info point_up">
             <span class="value">
              1,278.97
             </span>
             <span class="txt_krw">
              <span class="blind">
               원
              </span>
             </span>
             <span class="change">
              2.69
             </span>
             <span class="blind">
              상승
             </span>
            </div>
           </a>
           <a class="graph_img" href="/marketindex/exchangeDetail.nhn?marketindexCd=FX_EURKRW" onclick="clickcr(this, 'fr1.eurc', '', '', event);">
            <img alt="" height="153" src="https://ssl.pstatic.net/imgfinance/chart/marketindex/FX_EURKRW.png" width="295"/>
           </a>
           <div class="graph_info">
            <span class="time">
             2018.12.28 22:00
            </span>
            <span class="source">
             KEB하나은행 기준
            </span>
            <span class="count">
             고시회차
             <span class="num">
              261
             </span>
             회
            </span>
           </div>
          </li>
          <li class="">
           <a class="head cny" href="/marketindex/exchangeDetail.nhn?marketindexCd=FX_CNYKRW" onclick="clickcr(this, 'fr1.cnyt', '', '', event);">
            <h3 class="h_lst">
             <span class="blind">
              중국 CNY
             </span>
            </h3>
            <div class="head_info point_dn">
             <span class="value">
              162.31
             </span>
             <span class="txt_krw">
              <span class="blind">
               원
              </span>
             </span>
             <span class="change">
              0.70
             </span>
             <span class="blind">
              하락
             </span>
            </div>
           </a>
           <a class="graph_img" href="/marketindex/exchangeDetail.nhn?marketindexCd=FX_CNYKRW" onclick="clickcr(this, 'fr1.cnyc', '', '', event);">
            <img alt="" height="153" src="https://ssl.pstatic.net/imgfinance/chart/marketindex/FX_CNYKRW.png" width="295"/>
           </a>
           <div class="graph_info">
            <span class="time">
             2018.12.28 22:00
            </span>
            <span class="source">
             KEB하나은행 기준
            </span>
            <span class="count">
             고시회차
             <span class="num">
              261
             </span>
             회
            </span>
           </div>
          </li>
         </ul>
        </div>
        <!-- //data -->
       </div>
       <div class="market2">
        <div class="title">
         <h2 class="h_market2">
          <span>
           국제 시장 환율
          </span>
         </h2>
        </div>
        <!-- data -->
        <div class="data">
         <ul class="data_lst" id="worldExchangeList">
          <li class="on">
           <a class="head jpy_usd" href="/marketindex/worldExchangeDetail.nhn?marketindexCd=FX_USDJPY" onclick="clickcr(this, 'fr2.jpyut', '', '', event);">
            <h3 class="h_lst">
             <span class="blind">
              일본 엔/달러
             </span>
            </h3>
            <div class="head_info point_dn">
             <span class="value">
              110.3100
             </span>
             <span class="txt_jpy">
              <span class="blind">
               엔
              </span>
             </span>
             <span class="change">
              0.4100
             </span>
             <span class="blind">
              하락
             </span>
            </div>
           </a>
           <a class="graph_img" href="/marketindex/worldExchangeDetail.nhn?marketindexCd=FX_USDJPY" onclick="clickcr(this, 'fr2.jpyuc', '', '', event);">
            <img alt="" height="153" src="https://ssl.pstatic.net/imgfinance/chart/marketindex/FX_USDJPY.png" width="295"/>
           </a>
           <div class="graph_info">
            <span class="time">
             2018.12.28
            </span>
            <span class="source">
             모닝스타 기준
            </span>
           </div>
          </li>
          <li class="">
           <a class="head usd_eur" href="/marketindex/worldExchangeDetail.nhn?marketindexCd=FX_EURUSD" onclick="clickcr(this, 'fr2.eurdt', '', '', event);">
            <h3 class="h_lst">
             <span class="blind">
              달러/유로
             </span>
            </h3>
            <div class="head_info point_up">
             <span class="value">
              1.1451
             </span>
             <span class="txt_usd">
              <span class="blind">
               달러
              </span>
             </span>
             <span class="change">
              0.0030
             </span>
             <span class="blind">
              상승
             </span>
            </div>
           </a>
           <a class="graph_img" href="/marketindex/worldExchangeDetail.nhn?marketindexCd=FX_EURUSD" onclick="clickcr(this, 'fr2.eurdc', '', '', event);">
            <img alt="" height="153" src="https://ssl.pstatic.net/imgfinance/chart/marketindex/FX_EURUSD.png" width="295"/>
           </a>
           <div class="graph_info">
            <span class="time">
             2018.12.28
            </span>
            <span class="source">
             모닝스타 기준
            </span>
           </div>
          </li>
          <li class="">
           <a class="head usd_gbp" href="/marketindex/worldExchangeDetail.nhn?marketindexCd=FX_GBPUSD" onclick="clickcr(this, 'fr2.gbpdt', '', '', event);">
            <h3 class="h_lst">
             <span class="blind">
              달러/영국파운드
             </span>
            </h3>
            <div class="head_info point_up">
             <span class="value">
              1.2686
             </span>
             <span class="txt_usd">
              <span class="blind">
               달러
              </span>
             </span>
             <span class="change">
              0.0040
             </span>
             <span class="blind">
              상승
             </span>
            </div>
           </a>
           <a class="graph_img" href="/marketindex/worldExchangeDetail.nhn?marketindexCd=FX_GBPUSD" onclick="clickcr(this, 'fr2.gbpdc', '', '', event);">
            <img alt="" height="153" src="https://ssl.pstatic.net/imgfinance/chart/marketindex/FX_GBPUSD.png" width="295"/>
           </a>
           <div class="graph_info">
            <span class="time">
             2018.12.28
            </span>
            <span class="source">
             모닝스타 기준
            </span>
           </div>
          </li>
          <li class="">
           <a class="head usd_idx" href="/marketindex/worldExchangeDetail.nhn?marketindexCd=FX_USDX" onclick="clickcr(this, 'fr2.indt', '', '', event);">
            <h3 class="h_lst">
             <span class="blind">
              달러인덱스
             </span>
            </h3>
            <div class="head_info point_dn">
             <span class="value">
              95.9600
             </span>
             <span class="change">
              0.0400
             </span>
             <span class="blind">
              하락
             </span>
            </div>
           </a>
           <a class="graph_img" href="/marketindex/worldExchangeDetail.nhn?marketindexCd=FX_USDX" onclick="clickcr(this, 'fr2.indc', '', '', event);">
            <img alt="" height="153" src="https://ssl.pstatic.net/imgfinance/chart/marketindex/FX_USDX.png" width="295"/>
           </a>
           <div class="graph_info">
            <span class="time">
             2018.12.28
            </span>
            <span class="source">
             ICE 기준
            </span>
           </div>
          </li>
         </ul>
        </div>
        <!-- //data -->
       </div>
       <div class="market3">
        <div class="title">
         <h2 class="h_market3">
          <span>
           유가·금시세
          </span>
         </h2>
        </div>
        <!-- data -->
        <div class="data">
         <ul class="data_lst" id="oilGoldList">
          <li class="on">
           <a class="head wti" href="/marketindex/worldOilDetail.nhn?marketindexCd=OIL_CL&amp;fdtc=2" onclick="clickcr(this, 'fr3.wtit', '', '', event);">
            <h3 class="h_lst">
             <span class="blind">
              WTI
             </span>
            </h3>
            <div class="head_info point_up">
             <span class="value">
              45.33
             </span>
             <span class="txt_usd">
              <span class="blind">
               달러
              </span>
             </span>
             <span class="change">
              0.72
             </span>
             <span class="blind">
              상승
             </span>
            </div>
           </a>
           <a class="graph_img" href="/marketindex/worldOilDetail.nhn?marketindexCd=OIL_CL&amp;fdtc=2" onclick="clickcr(this, 'fr3.wtic', '', '', event);">
            <img alt="" height="153" src="https://ssl.pstatic.net/imgfinance/chart/marketindex/OIL_CL.png" width="295"/>
           </a>
           <div class="graph_info">
            <span class="time">
             2018.12.28
            </span>
            <span class="source">
             NYMEX(뉴욕상업거래소) 기준
            </span>
           </div>
          </li>
          <li class="">
           <a class="head gasoline" href="/marketindex/oilDetail.nhn?marketindexCd=OIL_GSL" onclick="clickcr(this, 'fr3.oilt', '', '', event);">
            <h3 class="h_lst">
             <span class="blind">
              휘발유
             </span>
            </h3>
            <div class="head_info point_dn">
             <span class="value">
              1383.4
             </span>
             <span class="txt_krw">
              <span class="blind">
               원
              </span>
             </span>
             <span class="change">
              2.50
             </span>
             <span class="blind">
              하락
             </span>
            </div>
           </a>
           <a class="graph_img" href="/marketindex/oilDetail.nhn?marketindexCd=OIL_GSL" onclick="clickcr(this, 'fr3.oilc', '', '', event);">
            <img alt="" height="153" src="https://ssl.pstatic.net/imgfinance/chart/marketindex/OIL_GSL.png" width="295"/>
           </a>
           <div class="graph_info">
            <span class="time">
             2018.12.29
            </span>
            <span class="source">
             한국석유공사 Opinet 기준
            </span>
           </div>
          </li>
          <li class="">
           <a class="head gold_inter" href="/marketindex/worldGoldDetail.nhn?marketindexCd=CMDT_GC&amp;fdtc=2" onclick="clickcr(this, 'fr3.agoldt', '', '', event);">
            <h3 class="h_lst">
             <span class="blind">
              국제 금
             </span>
            </h3>
            <div class="head_info point_up">
             <span class="value">
              1279.9
             </span>
             <span class="txt_usd">
              <span class="blind">
               달러
              </span>
             </span>
             <span class="change">
              2.50
             </span>
             <span class="blind">
              상승
             </span>
            </div>
           </a>
           <a class="graph_img" href="/marketindex/worldGoldDetail.nhn?marketindexCd=CMDT_GC&amp;fdtc=2" onclick="clickcr(this, 'fr3.agoldc', '', '', event);">
            <img alt="" height="153" src="https://ssl.pstatic.net/imgfinance/chart/marketindex/CMDT_GC.png" width="295"/>
           </a>
           <div class="graph_info">
            <span class="time">
             2018.12.28
            </span>
            <span class="source">
             COMEX(뉴욕상품거래소) 기준
            </span>
           </div>
          </li>
          <li class="">
           <a class="head gold_domestic" href="/marketindex/goldDetail.nhn" onclick="clickcr(this, 'fr3.kgoldt', '', '', event);">
            <h3 class="h_lst">
             <span class="blind">
              국내 금
             </span>
            </h3>
            <div class="head_info point_up">
             <span class="value">
              45856.22
             </span>
             <span class="txt_krw">
              <span class="blind">
               원
              </span>
             </span>
             <span class="change">
              20.32
             </span>
             <span class="blind">
              상승
             </span>
            </div>
           </a>
           <a class="graph_img" href="/marketindex/goldDetail.nhn" onclick="clickcr(this, 'fr3.kgoldc', '', '', event);">
            <img alt="" height="153" src="https://ssl.pstatic.net/imgfinance/chart/marketindex/CMDT_GD.png" width="295"/>
           </a>
           <div class="graph_info">
            <span class="time">
             2018.12.28 18:00
            </span>
            <span class="source">
             신한은행 기준
            </span>
            (
            <span class="num">
             62
            </span>
            회차)
           </div>
          </li>
         </ul>
        </div>
        <!-- //data -->
       </div>
      </div>
     </div>
     <div class="marketindex_content" id="content">
      <div class="section_news">
       <h3 class="h_news2">
        <span>
         주요 뉴스
        </span>
       </h3>
       <div class="list_area">
        <ul>
         <li>
          <p>
           <a href="/marketindex/news/newsRead.nhn?officeId=014&amp;articleId=0004151814&amp;category=" onclick="clickcr(this, 'mhn.list', '014_0004151814', '0', event);">
            [금융용어] 코픽스(COFIX)란?...'
           </a>
           <img alt="NEW" height="8" src="https://ssl.pstatic.net/static/nfinance/ico_new.gif" width="8"/>
          </p>
          <span>
           12.29 11:53
          </span>
         </li>
         <li>
          <p>
           <a href="/marketindex/news/newsRead.nhn?officeId=018&amp;articleId=0004281837&amp;category=" onclick="clickcr(this, 'mhn.list', '018_0004281837', '1', event);">
            국제유가 바닥은?..WTI, 배럴당 40달
           </a>
           <img alt="NEW" height="8" src="https://ssl.pstatic.net/static/nfinance/ico_new.gif" width="8"/>
          </p>
          <span>
           12.29 11:00
          </span>
         </li>
         <li>
          <p>
           <a href="/marketindex/news/newsRead.nhn?officeId=014&amp;articleId=0004151783&amp;category=" onclick="clickcr(this, 'mhn.list', '014_0004151783', '2', event);">
            [원자재시황] 국제유가, 美 원유재고 감소
           </a>
           <img alt="NEW" height="8" src="https://ssl.pstatic.net/static/nfinance/ico_new.gif" width="8"/>
          </p>
          <span>
           12.29 09:09
          </span>
         </li>
         <li>
          <p>
           <a href="/marketindex/news/newsRead.nhn?officeId=018&amp;articleId=0004281750&amp;category=" onclick="clickcr(this, 'mhn.list', '018_0004281750', '3', event);">
            국제유가 강세..WTI 1.6%↑
           </a>
           <img alt="NEW" height="8" src="https://ssl.pstatic.net/static/nfinance/ico_new.gif" width="8"/>
          </p>
          <span>
           12.29 06:57
          </span>
         </li>
         <li>
          <p>
           <a href="/marketindex/news/newsRead.nhn?officeId=009&amp;articleId=0004279736&amp;category=" onclick="clickcr(this, 'mhn.list', '009_0004279736', '4', event);">
            국제유가 오름세 지속…WTI 1.6%↑
           </a>
           <img alt="NEW" height="8" src="https://ssl.pstatic.net/static/nfinance/ico_new.gif" width="8"/>
          </p>
          <span>
           12.29 06:47
          </span>
         </li>
         <li>
          <p>
           <a href="/marketindex/news/newsRead.nhn?officeId=008&amp;articleId=0004153303&amp;category=" onclick="clickcr(this, 'mhn.list', '008_0004153303', '5', event);">
            유가, 美원유재고량 감소세에 상승...WT
           </a>
           <img alt="NEW" height="8" src="https://ssl.pstatic.net/static/nfinance/ico_new.gif" width="8"/>
          </p>
          <span>
           12.29 06:37
          </span>
         </li>
         <li>
          <p>
           <a href="/marketindex/news/newsRead.nhn?officeId=008&amp;articleId=0004153297&amp;category=" onclick="clickcr(this, 'mhn.list', '008_0004153297', '6', event);">
            금값, 달러약세에 4일 연속 상승...온스
           </a>
           <img alt="NEW" height="8" src="https://ssl.pstatic.net/static/nfinance/ico_new.gif" width="8"/>
          </p>
          <span>
           12.29 05:41
          </span>
         </li>
         <li>
          <p>
           <a href="/marketindex/news/newsRead.nhn?officeId=001&amp;articleId=0010550724&amp;category=" onclick="clickcr(this, 'mhn.list', '001_0010550724', '7', event);">
            국고채 금리 대체로 상승…3년물 연 1.8
           </a>
           <img alt="NEW" height="8" src="https://ssl.pstatic.net/static/nfinance/ico_new.gif" width="8"/>
          </p>
          <span>
           12.28 17:29
          </span>
         </li>
         <li>
          <p>
           <a href="/marketindex/news/newsRead.nhn?officeId=032&amp;articleId=0002913956&amp;category=" onclick="clickcr(this, 'mhn.list', '032_0002913956', '8', event);">
            내년도 강달러 이어지나…올해 환율 ‘상저하
           </a>
           <img alt="NEW" height="8" src="https://ssl.pstatic.net/static/nfinance/ico_new.gif" width="8"/>
          </p>
          <span>
           12.28 17:04
          </span>
         </li>
         <li>
          <p>
           <a href="/marketindex/news/newsRead.nhn?officeId=018&amp;articleId=0004281489&amp;category=" onclick="clickcr(this, 'mhn.list', '018_0004281489', '9', event);">
            [채권마감]연말 장세 속 외국인 매도세…금
           </a>
           <img alt="NEW" height="8" src="https://ssl.pstatic.net/static/nfinance/ico_new.gif" width="8"/>
          </p>
          <span>
           12.28 17:02
          </span>
         </li>
        </ul>
       </div>
       <a class="btn_more" href="/marketindex/news/newsList.nhn" onclick="clickcr(this, 'mhn.more', '', '', event);">
        <img alt="더보기" height="18" src="https://ssl.pstatic.net/static/nfinance/btn_more.gif" width="34"/>
       </a>
      </div>
      <div class="tab_default1" id="tab_section">
       <ul>
        <li class="on">
         <a href="./?tabSel=exchange#tab_section" onclick="clickcr(this, 'mtb.exnoti', '', '', event);">
          <span class="tab1">
           환전고시 환율
          </span>
         </a>
        </li>
        <li class="">
         <a href="./?tabSel=worldExchange#tab_section" onclick="clickcr(this, 'mtb.interex', '', '', event);">
          <span class="tab2">
           국제시장 환율
          </span>
         </a>
        </li>
        <li class="">
         <a href="./?tabSel=gold#tab_section" onclick="clickcr(this, 'mtb.oilgold', '', '', event);">
          <span class="tab3">
           유가 금시세
          </span>
         </a>
        </li>
        <li class="">
         <a href="./?tabSel=materials#tab_section" onclick="clickcr(this, 'mtb.material', '', '', event);">
          <span class="tab4">
           원자재
          </span>
         </a>
        </li>
       </ul>
       <h2 class="blind">
        환전고시 환율
       </h2>
      </div>
      <div class="section_calculator">
       <h3 class="h_calculator">
        <span>
         환율계산기 (매매기준율 기준)
        </span>
       </h3>
       <div class="calculator">
        <div class="inner">
         <div class="input">
          <div class="selectbox-noscript" id="sInput">
           <select class="selectbox-source" id="select_from">
            <option label="1" value="1">
             대한민국 원 KRW
            </option>
            <option class="selectbox-default" label="1" selected="selected" value="1117.0">
             미국 달러 USD
            </option>
            <option label="1" value="1117.0">
             미국 달러 USD
            </option>
            <option label="1" value="1278.97">
             유럽연합 유로 EUR
            </option>
            <option label="100" value="10.1237">
             일본 엔 JPY
            </option>
            <option label="1" value="162.31">
             중국 위안 CNY
            </option>
            <option label="1" value="142.62">
             홍콩 달러 HKD
            </option>
            <option label="1" value="36.46">
             대만 달러 TWD
            </option>
            <option label="1" value="1416.8">
             영국 파운드 GBP
            </option>
            <option label="1" value="2901.3">
             오만 리알 OMR
            </option>
            <option label="1" value="820.24">
             캐나다 달러 CAD
            </option>
            <option label="1" value="1138.75">
             스위스 프랑 CHF
            </option>
            <option label="1" value="124.55">
             스웨덴 크로나 SEK
            </option>
            <option label="1" value="789.1">
             호주 달러 AUD
            </option>
            <option label="1" value="749.9">
             뉴질랜드 달러 NZD
            </option>
            <option label="1" value="49.61">
             체코 코루나 CZK
            </option>
            <option label="1" value="1.61">
             칠레 페소 CLP
            </option>
            <option label="1" value="211.95">
             터키 리라 TRY
            </option>
            <option label="1" value="0.42">
             몽골 투그릭 MNT
            </option>
            <option label="1" value="296.57">
             이스라엘 세켈 ILS
            </option>
            <option label="1" value="171.29">
             덴마크 크로네 DKK
            </option>
            <option label="1" value="128.03">
             노르웨이 크로네 NOK
            </option>
            <option label="1" value="297.75">
             사우디아라비아 리얄 SAR
            </option>
            <option label="1" value="3678.46">
             쿠웨이트 디나르 KWD
            </option>
            <option label="1" value="2963.41">
             바레인 디나르 BHD
            </option>
            <option label="1" value="304.1">
             아랍에미리트 디르함 AED
            </option>
            <option label="1" value="1572.69">
             요르단 디나르 JOD
            </option>
            <option label="1" value="62.44">
             이집트 파운드 EGP
            </option>
            <option label="1" value="34.31">
             태국 바트 THB
            </option>
            <option label="1" value="817.96">
             싱가포르 달러 SGD
            </option>
            <option label="1" value="268.99">
             말레이시아 링깃 MYR
            </option>
            <option label="100" value="0.07690000000000001">
             인도네시아 루피아 IDR
            </option>
            <option label="1" value="306.78">
             카타르 리얄 QAR
            </option>
            <option label="1" value="2.93">
             카자흐스탄 텡게 KZT
            </option>
            <option label="1" value="817.96">
             브루나이 달러 BND
            </option>
            <option label="1" value="15.95">
             인도 루피 INR
            </option>
            <option label="1" value="7.98">
             파키스탄 루피 PKR
            </option>
            <option label="1" value="13.34">
             방글라데시 타카 BDT
            </option>
            <option label="1" value="21.22">
             필리핀 페소 PHP
            </option>
            <option label="1" value="56.61">
             멕시코 페소 MXN
            </option>
            <option label="1" value="286.83">
             브라질 레알 BRL
            </option>
            <option label="100" value="0.0482">
             베트남 동 VND
            </option>
            <option label="1" value="77.76">
             남아프리카 공화국 랜드 ZAR
            </option>
            <option label="1" value="16.09">
             러시아 루블 RUB
            </option>
            <option label="1" value="3.98">
             헝가리 포린트 HUF
            </option>
            <option label="1" value="297.17">
             폴란드 즈워티 PLN
            </option>
           </select>
           <div class="selectbox-box" onclick="clickcr(this, 'exl*c.clist1', '', '', event);">
            <div class="selectbox-label">
            </div>
           </div>
           <div class="selectbox-layer ly_calculator">
            <div class="selectbox-list">
            </div>
           </div>
          </div>
          <div class="input_area" id="input_from">
           <input id="input_from_money" maxlength="10" onclick="clickcr(this, 'exl*c.unit1', '', '', event);" onfocus="changeActiveInput('from', 'to'); return false;" onkeyup="calcExchange('from', 'to', 'on', event);" type="text" value="1"/>
           <img alt="" height="13" id="img_from_monetary" src="https://ssl.pstatic.net/static/nfinance/img_money_USD.gif" width="26"/>
          </div>
         </div>
         <div class="same">
          <img alt="" height="15" src="https://ssl.pstatic.net/static/nfinance/ico_same.gif" width="28"/>
         </div>
         <div class="view">
          <div class="selectbox-noscript" id="sView">
           <select class="selectbox-source" id="select_to">
            <option class="selectbox-default" label="1" value="1">
             대한민국 원 KRW
            </option>
            <option label="1" value="1">
             대한민국 원 KRW
            </option>
            <option label="1" value="1117.0">
             미국 달러 USD
            </option>
            <option label="1" value="1278.97">
             유럽연합 유로 EUR
            </option>
            <option label="100" value="10.1237">
             일본 엔 JPY
            </option>
            <option label="1" value="162.31">
             중국 위안 CNY
            </option>
            <option label="1" value="142.62">
             홍콩 달러 HKD
            </option>
            <option label="1" value="36.46">
             대만 달러 TWD
            </option>
            <option label="1" value="1416.8">
             영국 파운드 GBP
            </option>
            <option label="1" value="2901.3">
             오만 리알 OMR
            </option>
            <option label="1" value="820.24">
             캐나다 달러 CAD
            </option>
            <option label="1" value="1138.75">
             스위스 프랑 CHF
            </option>
            <option label="1" value="124.55">
             스웨덴 크로나 SEK
            </option>
            <option label="1" value="789.1">
             호주 달러 AUD
            </option>
            <option label="1" value="749.9">
             뉴질랜드 달러 NZD
            </option>
            <option label="1" value="49.61">
             체코 코루나 CZK
            </option>
            <option label="1" value="1.61">
             칠레 페소 CLP
            </option>
            <option label="1" value="211.95">
             터키 리라 TRY
            </option>
            <option label="1" value="0.42">
             몽골 투그릭 MNT
            </option>
            <option label="1" value="296.57">
             이스라엘 세켈 ILS
            </option>
            <option label="1" value="171.29">
             덴마크 크로네 DKK
            </option>
            <option label="1" value="128.03">
             노르웨이 크로네 NOK
            </option>
            <option label="1" value="297.75">
             사우디아라비아 리얄 SAR
            </option>
            <option label="1" value="3678.46">
             쿠웨이트 디나르 KWD
            </option>
            <option label="1" value="2963.41">
             바레인 디나르 BHD
            </option>
            <option label="1" value="304.1">
             아랍에미리트 디르함 AED
            </option>
            <option label="1" value="1572.69">
             요르단 디나르 JOD
            </option>
            <option label="1" value="62.44">
             이집트 파운드 EGP
            </option>
            <option label="1" value="34.31">
             태국 바트 THB
            </option>
            <option label="1" value="817.96">
             싱가포르 달러 SGD
            </option>
            <option label="1" value="268.99">
             말레이시아 링깃 MYR
            </option>
            <option label="100" value="0.07690000000000001">
             인도네시아 루피아 IDR
            </option>
            <option label="1" value="306.78">
             카타르 리얄 QAR
            </option>
            <option label="1" value="2.93">
             카자흐스탄 텡게 KZT
            </option>
            <option label="1" value="817.96">
             브루나이 달러 BND
            </option>
            <option label="1" value="15.95">
             인도 루피 INR
            </option>
            <option label="1" value="7.98">
             파키스탄 루피 PKR
            </option>
            <option label="1" value="13.34">
             방글라데시 타카 BDT
            </option>
            <option label="1" value="21.22">
             필리핀 페소 PHP
            </option>
            <option label="1" value="56.61">
             멕시코 페소 MXN
            </option>
            <option label="1" value="286.83">
             브라질 레알 BRL
            </option>
            <option label="100" value="0.0482">
             베트남 동 VND
            </option>
            <option label="1" value="77.76">
             남아프리카 공화국 랜드 ZAR
            </option>
            <option label="1" value="16.09">
             러시아 루블 RUB
            </option>
            <option label="1" value="3.98">
             헝가리 포린트 HUF
            </option>
            <option label="1" value="297.17">
             폴란드 즈워티 PLN
            </option>
           </select>
           <div class="selectbox-box" onclick="clickcr(this, 'exl*c.clist2', '', '', event);">
            <div class="selectbox-label">
            </div>
           </div>
           <div class="selectbox-layer ly_calculator">
            <div class="selectbox-list">
            </div>
           </div>
          </div>
          <div class="input_area" id="input_to">
           <input id="input_to_money" maxlength="10" onclick="clickcr(this, 'exl*c.unit2', '', '', event);" onfocus="changeActiveInput('to', 'from'); return false;" onkeyup="calcExchange('to', 'from', 'on', event);" type="text"/>
           <img alt="" height="13" id="img_to_monetary" src="https://ssl.pstatic.net/static/nfinance/img_money_KRW.gif" width="26"/>
          </div>
         </div>
        </div>
       </div>
      </div>
      <div class="section_exchange">
       <div id="section_ex1">
        <h3 class="blind" id="exchange1">
         환전 고시 환율
        </h3>
        <div class="exchange_info">
         <span class="date">
          2018.12.28 22:00
         </span>
         <span class="standard">
          KEB하나은행 기준
         </span>
         <span class="round">
          고시회차
          <em>
           261
          </em>
          회
         </span>
        </div>
        <iframe frameborder="0" height="1378px" id="frame_ex1" marginheight="0" marginwidth="0" scrolling="no" src="/marketindex/exchangeList.nhn" title="환전 고시 환율" width="100%">
        </iframe>
       </div>
       <ul class="tbl_info">
        <li>
         대부분의 통화명은 1달러가 다른 통화 기준으로 얼마인가를 나타내기 위해 국가명+화폐명/달러로 표기했습니다.
         <br/>
         예외로 유로, 영국파운드 등은 1유로가 달러로 얼마인가를 표기하는 경우가 많기 때문에 달러/유로로 표기했습니다.
        </li>
        <li>
         심볼은 앞에 위치한 통화에 1이 생략된 형태로 USDJPY인 경우 1USD(달러)가 JPY(일본 엔)으로 얼마인가를 의미합니다.
        </li>
        <li>
         통화명과 심볼로 네이버 금융검색을 하실 경우 해당 환율을 바로 조회하실 수 있습니다.
        </li>
       </ul>
      </div>
      <script language="javascript" src="/js/jindoComponent/jindo.Component.1.0.3.js?20181220153244" type="text/javascript">
      </script>
      <script language="javascript" src="/js/jindoComponent/jindo.UIComponent.1.0.1.js?20181220153244" type="text/javascript">
      </script>
      <script language="javascript" src="/js/jindoComponent/jindo.HTMLComponent.1.0.1.js?20181220153244" type="text/javascript">
      </script>
      <script language="javascript" src="/js/jindoComponent/jindo.Timer.1.0.3.js?20181220153244" type="text/javascript">
      </script>
      <script language="javascript" src="/js/jindoComponent/jindo.LayerManager.1.0.1.js?20181220153244" type="text/javascript">
      </script>
      <script language="javascript" src="/js/jindoComponent/jindo.LayerPosition.1.0.1.js?20181220153244" type="text/javascript">
      </script>
      <script language="javascript" src="/js/jindoComponent/jindo.RolloverArea.1.0.1.js?20181220153244" type="text/javascript">
      </script>
      <script language="javascript" src="/js/jindoComponent/jindo.RolloverClick.1.0.1.js?20181220153244" type="text/javascript">
      </script>
      <script language="javascript" src="/js/jindoComponent/jindo.SelectBox.1.0.1.js?20181220153244" type="text/javascript">
      </script>
      <script charset="euckr" language="javascript" src="/js/jindoComponent/jindo.TextRange.1.0.1.js?20181220153244" type="text/javascript">
      </script>
      <script charset="euckr" language="javascript" src="/js/jindoComponent/jindo.WatchInput.1.0.1.js?20181220153244" type="text/javascript">
      </script>
      <script charset="euckr" language="javascript" src="/js/jindoComponent/jindo.Formatter.1.1.0.js?20181220153244" type="text/javascript">
      </script>
      <script charset="euckr" language="javascript" src="/js/jindoComponent/jindo.NumberFormatter.1.0.1.js?20181220153244" type="text/javascript">
      </script>
      <script language="javascript" src="/js/exchange.js?20181220153244" type="text/javascript">
      </script>
      <script type="text/javascript">
       // exchangeDetail.jsp에도 동일 스크립트 있음 - 로딩시 디자인이 0.5초간 디자인이 안뜬다는 이슈로 include하지 않고 같은 스크립트를 두벌로 관리함
    
    //환율 계산
    function calcExchange(str1, str2, cssOn, event) {
    	var oEvent = event || window.event;
        var sKeyCode = (undefined === oEvent) ? undefined : jindo.$Event(oEvent).key().keyCode;
    	
    	if (sKeyCode != 13 && sKeyCode != 27 && sKeyCode != 37 && sKeyCode != 38 && sKeyCode != 110 && sKeyCode != 229) {
    		if (str1 == '' && str2 == '') {
    			if (jindo.$('input_to').className == 'input_area') {
    				str1 = 'from';
    				str2 = 'to';
    			} else {
    				str1 = 'to';
    				str2 = 'from';
    			}
    		}
    		
    		var regExp = new RegExp("[^0-9\.\,]");
    	    var sInputValue = jindo.$('input_' + str1 + '_money').value;
    		
    	    if(regExp.test(sInputValue)){
    			alert('숫자, 소수점(.)만 입력하실 수 있습니다.');
    			jindo.$('input_' + str1 + '_money').value = revertInputText(sInputValue);
    			return false;
    		}
    		
    		// 사용자가 입력한 콤마(,) 제거
    	    if (sInputValue.substr(sInputValue.length - 1, 1) == ',') {
    	    	jindo.$('input_' + str1 + '_money').value = revertInputText(sInputValue);
    	    	return false;
    	    }
    		
    		// 맨 앞에 있는 숫자 0 없애기
    		if (sInputValue == '0' || sInputValue == '.' || sInputValue == ',') {
    			jindo.$('input_' + str1 + '_money').value = revertInputText(sInputValue);
    			return false;
    		}
    		
    		// 소수점 2개가 없도록
    		var match = sInputValue.match(/\./g);
    		if (match != null && match.length >= 2) {
    			jindo.$('input_' + str1 + '_money').value = revertInputText(sInputValue);
    			return false;
    		}
    		
    		// 소수점을 입력한 경우, 일단 대기
    		var pointPosIndex = sInputValue.indexOf('.');
    		if (pointPosIndex != -1 && pointPosIndex == sInputValue.length - 1) {
    			return false;
    		}
    		
    		// 소수점 2자리 유지
    		if (pointPosIndex != -1 && sInputValue.substring(pointPosIndex + 1, sInputValue.length).length > 2) {
    			jindo.$('input_' + str1 + '_money').value = revertInputText(sInputValue);
    			return false;
    		}
    		
    		var e_select_from	= jindo.$('select_'+str1);
    		var e_select_to	= jindo.$('select_'+str2);
    	
    		var input_from_money	= jindo.$('input_'+str1+'_money').value ;
    		var input_from		= e_select_from.options[e_select_from.selectedIndex].value ;
    		var input_to		= e_select_to.options[e_select_to.selectedIndex].value ;
    		var unit_from	= e_select_from.options[e_select_from.selectedIndex].text ;
    		var unit_to		= e_select_to.options[e_select_to.selectedIndex].text ;
    		input_from_money = input_from_money.replace(/ |,|\t/g, "") ;
    		input_from	= input_from.replace(/ |,|\t/g, "") ;
    		input_to	= input_to.replace(/ |,|\t/g, "") ;
    		unit_from = unit_from.substr(unit_from.length-3, 3);
    		unit_to = unit_to.substr(unit_to.length-3, 3);
    	
    		var str1_result = input_from_money * 1 ;
    		var str1_str	= str1_result.toFixed(0) ;
    		var str1_len = str1_str.length ;
    		var str1_result_str = new String("") ;
    	
    		if(str1_str > str1_result)
    			str1_str = new String(str1_str-1) ;
    	
    		if (str1_len >= 3) {
    			for (i = str1_len - 3; i > 0; i-=3)
    				str1_result_str = "," + str1_str.substr(i, 3) + str1_result_str ;
    	
    			str1_result_str = str1_str.substr(0, 3+i) + str1_result_str ;
    	
    			for (var i=0; i<input_from_money.length; i++) {
    				if (input_from_money.substr(i,1) != '0') {
    					input_from_money = input_from_money.substring(i,input_from_money.length) ;
    					break;
    				}
    			}
    	
    			if (str1_str != input_from_money) {
    				str1_result_str += str1_result.toFixed(input_from_money.split(".")[1].length).substr(str1_len, input_from_money.split(".")[1].length + 1) ;
    			}
    		} else
    			str1_result_str = input_from_money ;
    	
    		var str2_result = input_from_money * input_from / input_to ;
    		var str2_str	= str2_result.toFixed(0) ;
    		var str2_len = str2_str.length ;
    		var str2_result_str = new String("") ;
    	
    		if (str2_str > str2_result)
    			str2_str = new String(str2_str-1) ;
    	
    		if (str2_len >= 3) {
    			for (i = str2_len - 3; i > 0; i-=3)
    				str2_result_str = "," + str2_str.substr(i, 3) + str2_result_str ;
    	
    			str2_result_str = str2_str.substr(0, 3+i) + str2_result_str ;
    			str2_result_str += str2_result.toFixed(2).substr(str2_len, 3) ;
    		} else
    			str2_result_str = str2_result.toFixed(2) ;
    	
    		jindo.$('input_'+str1+'_money').value = str1_result_str;
    		jindo.$('input_'+str2+'_money').value = str2_result_str;
    		jindo.$('img_'+str1+'_monetary').src = "https://ssl.pstatic.net/static/nfinance/img_money_"+unit_from+".gif";
    		jindo.$('img_'+str2+'_monetary').src = "https://ssl.pstatic.net/static/nfinance/img_money_"+unit_to+".gif";
    		jindo.$Element(jindo.$('input_'+str1)).addClass("on");
    		jindo.$Element(jindo.$('input_'+str2)).removeClass("on");
    		if (cssOn == "off"){
    			jindo.$Element(jindo.$('input_'+str1)).removeClass("on");
    		}
    	}
    }
    
    function revertInputText(sOrgText) {
    	var sResult = sOrgText;
    	
    	if (sOrgText != '') {
    		sResult = sOrgText.substr(0, sOrgText.length - 1);
    	}
    	
    	return sResult;
    }
    
    jindo.$Fn(function(){
    	chartMouseOverEvent('exchangeList');		
       }).attach(document, "domready");
      </script>
     </div>
     <script language="javascript" src="/js/jindo_ellipse.js?20181220153244">
     </script>
     <script language="javascript" src="/js/util.js?20181220153244">
     </script>
     <script type="text/javascript">
      function ellipseTopNewsTitle() {
    	jindo.EllipseText.factory(jindo.$$(".news_list ul li"), {nAssistWidth: 10});
    }
    
    
    var basicDates;
    
    function initDate() {
    	basicDates = jindo.$H();
    	basicDates.add('', '');
    	basicDates.add('', '');
    	basicDates.add('', '');
    	basicDates.add('', '');
    	basicDates.add('', '');
    	basicDates.add('', '');
    }
    
    var basicDescriptions;
    
    function initDescription() {
    	basicDescriptions = jindo.$H();
    	basicDescriptions.add('CMDT_CDY','<em></em>  기준<span>단위:</span>');
    	basicDescriptions.add('CMDT_PDY','<em></em>  기준<span>단위:</span>');
    	basicDescriptions.add('CMDT_NG','<em></em>  기준<span>단위:</span>');
    	basicDescriptions.add('CMDT_S','<em></em>  기준<span>단위:</span>');
    	basicDescriptions.add('CMDT_C','<em></em>  기준<span>단위:</span>');
    	basicDescriptions.add('CMDT_SB','<em></em>  기준<span>단위:</span>');
    }
    
    function setEvent(num, imgChartUrl){	
    	var parentElemnt = jindo.$$('.tbl_market')[num];
    	var elementList = jindo.$$('tr',parentElemnt);
    	
    	jindo.$Fn(function(e){	
    		var elementListSize = elementList.length;
    		
    		for(var i = 0 ; i < elementListSize ; i ++)
    		{
    			var selectedElement = jindo.$Element(elementList[i]);
    			var selectedClassName = selectedElement.className();
    			if (selectedClassName != "") {
    				selectedElement.removeClass(selectedClassName);
    				selectedElement.addClass(selectedClassName.replace("on",""));
    			}
    		} 
    		
    		var ele = jindo.$Element(jindo.$Event(e).currentElement);
    		var linkUrl = jindo.$$.getSingle('a' , ele.$value()).href;
    		var marketindexCd = linkUrl.split('=')[1];
    		var className = ele.className();
    		ele.removeClass(className);
    		ele.addClass(className+" on");
    		var graphElement = jindo.$Element(jindo.$$('.thum')[num]);
    		graphElement.html('<a href=\"'+linkUrl+'\"><img src=\"https://ssl.pstatic.net/imgfinance/chart/marketindex/' + marketindexCd + '.png\" width=\"295\" height=\"153\" alt=""></a>');
    		
    		if (basicDates.hasKey(marketindexCd)) {
    			jindo.$Element("basicDate").text(basicDates.$(marketindexCd));
    		}
    		if (basicDescriptions.hasKey(marketindexCd)) {
    			jindo.$Element("basicDescription").html(basicDescriptions.$(marketindexCd));
    		}
    	
    	}).attach(elementList, "mouseover");	
    }
    
    jindo.$Fn(function(){
    		initDate();
    		initDescription();
    		chartMouseOverEvent('exchangeList');
    		chartMouseOverEvent('worldExchangeList');
    		chartMouseOverEvent('oilGoldList');
    		//setEvent(0);
    		//setEvent(1);
    		jindo.$$('title')[0].innerHTML = "시장지표 : 네이버 금융";
    }).attach(document, "domready");
     </script>
     <div class="aside" id="marketindex_aside">
      <div class="section_aside">
       <h3 class="h_interest">
        <span>
         국내시장금리
        </span>
       </h3>
       <table class="tbl_exchange market" summary="국제시장 환율 리스트">
        <caption>
         국내시장금리
        </caption>
        <colgroup>
         <col width="94"/>
         <col width="50"/>
         <col width="75"/>
        </colgroup>
        <thead>
         <tr>
          <th>
           구분
          </th>
          <th>
           금리
          </th>
          <th>
           등락율
          </th>
         </tr>
        </thead>
        <tbody>
         <tr class="up">
          <th class="th_inter4">
           <a href="/marketindex/interestDetail.nhn?marketindexCd=IRR_CD91" onclick="clickcr(this, 'rid.local', 'IRR_CD91', '1', event);">
            <span>
             CD금리(91일)
            </span>
           </a>
          </th>
          <td>
           1.93
          </td>
          <td>
           <img alt="상승" height="6" src="https://ssl.pstatic.net/static/nfinance/ico_up.gif" width="7"/>
           0.01
          </td>
         </tr>
         <tr class="up">
          <th class="th_inter1">
           <a href="/marketindex/interestDetail.nhn?marketindexCd=IRR_CALL" onclick="clickcr(this, 'met.8', 'IRR_CALL', '2', event);">
            <span>
             콜 금리
            </span>
           </a>
          </th>
          <td>
           1.82
          </td>
          <td>
           <img alt="상승" height="6" src="https://ssl.pstatic.net/static/nfinance/ico_up.gif" width="7"/>
           0.04
          </td>
         </tr>
         <tr class="up">
          <th class="th_inter2">
           <a href="/marketindex/interestDetail.nhn?marketindexCd=IRR_GOVT03Y" onclick="clickcr(this, 'met.8', 'IRR_GOVT03Y', '3', event);">
            <span>
             국고채 (3년)
            </span>
           </a>
          </th>
          <td>
           1.82
          </td>
          <td>
           <img alt="상승" height="6" src="https://ssl.pstatic.net/static/nfinance/ico_up.gif" width="7"/>
           0.01
          </td>
         </tr>
         <tr class="up">
          <th class="th_inter3">
           <a href="/marketindex/interestDetail.nhn?marketindexCd=IRR_CORP03Y" onclick="clickcr(this, 'met.8', 'IRR_CORP03Y', '4', event);">
            <span>
             회사채 (3년)
            </span>
           </a>
          </th>
          <td>
           2.29
          </td>
          <td>
           <img alt="상승" height="6" src="https://ssl.pstatic.net/static/nfinance/ico_up.gif" width="7"/>
           0.01
          </td>
         </tr>
         <tr class="up">
          <th class="th_inter5">
           <a href="/marketindex/interestDetail.nhn?marketindexCd=IRR_COFIXBAL" onclick="clickcr(this, 'met.8', 'IRR_COFIXBAL', '5', event);">
            <span>
             COFIX 잔액
            </span>
           </a>
          </th>
          <td>
           1.95
          </td>
          <td>
           <img alt="상승" height="6" src="https://ssl.pstatic.net/static/nfinance/ico_up.gif" width="7"/>
           0.02
          </td>
         </tr>
         <tr class="up">
          <th class="th_inter6">
           <a href="/marketindex/interestDetail.nhn?marketindexCd=IRR_COFIXNEW" onclick="clickcr(this, 'met.8', 'IRR_COFIXNEW', '6', event);">
            <span>
             COFIX 신규취급액
            </span>
           </a>
          </th>
          <td>
           1.96
          </td>
          <td>
           <img alt="상승" height="6" src="https://ssl.pstatic.net/static/nfinance/ico_up.gif" width="7"/>
           0.03
          </td>
         </tr>
        </tbody>
       </table>
       <table class="tbl_exchange tbl_exchange_v1 market" style="margin-top:10px;" summary="달러 인덱스">
        <caption>
         달러 인덱스
        </caption>
        <colgroup>
         <col width="88"/>
         <col width="46"/>
         <col width="66"/>
        </colgroup>
        <thead>
         <tr>
          <th>
           구분
          </th>
          <th>
           금리
          </th>
          <th>
           등락율
          </th>
         </tr>
        </thead>
        <tbody>
         <tr class="other_t1 down">
          <th class="th_inter7">
           <a href="/marketindex/worldExchangeDetail.nhn?marketindexCd=FX_USDX&amp;fdtc=4" onclick="clickcr(this, 'rid.dollar', 'FX_USDX', '', event);">
            <span>
             달러 인덱스
            </span>
           </a>
          </th>
          <td>
           95.9600
          </td>
          <td>
           <img alt="하락" height="6" src="https://ssl.pstatic.net/static/nfinance/ico_down.gif" width="7"/>
           0.0400
          </td>
         </tr>
        </tbody>
       </table>
      </div>
      <div class="section_aside">
       <div class="tab_search search3">
        <a href="#" onclick="clickcr(this, 'rch.1', '', '', event); return false;">
         <span>
          최근검색
         </span>
        </a>
        <a href="#" onclick="clickcr(this, 'rch.3', '', '', event); return false;">
         <span>
          MY STOCK
         </span>
        </a>
       </div>
       <h3 class="blind">
        최근검색
       </h3>
       <table class="tbl_search" summary="최근검색 리스트">
        <caption>
         최근검색
        </caption>
        <colgroup>
         <col/>
         <col width="55"/>
         <col width="87"/>
        </colgroup>
        <thead>
         <tr>
          <th>
           업체명
          </th>
          <th>
           거래량
          </th>
          <th>
           전일비
          </th>
         </tr>
        </thead>
        <tbody>
        </tbody>
       </table>
       <div class="more_info">
        <span>
         <a href="#" onclick="clickcr(this, 'rch.5', '', '', event); return false;">
          <img alt="이전" height="15" src="https://ssl.pstatic.net/static/nfinance/btn_prev2.gif" width="17"/>
         </a>
         <a href="#" onclick="clickcr(this, 'rch.6', '', '', event); return false;">
          <img alt="다음" height="15" src="https://ssl.pstatic.net/static/nfinance/btn_next2.gif" width="17"/>
         </a>
        </span>
        <a class="btn_more" href="#" onclick="javascript:openMyitemNew('https://finance.naver.com'); clickcr(this, 'rch.7', '', '', event);">
         <img alt="더보기" height="10" src="https://ssl.pstatic.net/static/nfinance/btn_more5.gif" width="33"/>
        </a>
       </div>
      </div>
      <div class="section_aside">
       <h3 class="h_report">
        <span>
         경제분석 리포트
        </span>
       </h3>
       <ul class="list_report">
        <li>
         <a href="/research/economy_read.nhn?nid=5828" onclick="clickcr(this, 'rer.list', '', '', event);">
          국내외 경제이슈
         </a>
        </li>
        <li>
         <a href="/research/economy_read.nhn?nid=5827" onclick="clickcr(this, 'rer.list', '', '', event);">
          국내외 경제이슈
         </a>
        </li>
        <li>
         <a href="/research/economy_read.nhn?nid=5826" onclick="clickcr(this, 'rer.list', '', '', event);">
          소매판매 증가율 하락한 반면 소비...
         </a>
        </li>
        <li>
         <a href="/research/economy_read.nhn?nid=5825" onclick="clickcr(this, 'rer.list', '', '', event);">
          국내외 경제이슈
         </a>
        </li>
        <li>
         <a href="/research/economy_read.nhn?nid=5824" onclick="clickcr(this, 'rer.list', '', '', event);">
          중국 경제정책, 안정화에 방점
         </a>
        </li>
       </ul>
       <a class="btn_more" href="/research/economy_list.nhn" onclick="clickcr(this, 'rer.more', '', '', event);">
        <img alt="경제분석 리포트 더보기" height="18" src="https://ssl.pstatic.net/static/nfinance/btn_more.gif" width="34"/>
       </a>
      </div>
     </div>
     <script language="javascript" src="/js/info/myStock.js?20181220153244" type="text/javascript">
     </script>
     <script language="javascript" src="/js/util.js?20181220153244" type="text/javascript">
     </script>
     <script language="javascript">
      function toggleTabSearch(e){
    	var currentElement = jindo.$Element(jindo.$Event(e).currentElement);
    	var parentElement = currentElement.parent();
    	var id = currentElement.className();
    	var order = parentElement.indexOf(currentElement) + 3;
    	parentElement.className("tab_search search"+ order);
    
    	// 페이징을 위해서 order값에 따라 최근검색인지 mystock인지 type을 저장
    	if (order == 3) {
    		currentType = "recent";
    	} else {
    		currentType = "mystock";
    	}	
    }
    
    jindo.$Fn(function(){
    	if ('true' == 'true') {
    		getStockRightMenuData('000000', 'recent', 1, 'https://finance.naver.com', 'https://ssl.pstatic.net/static/nfinance');
    		
    		var tabSearchArea = jindo.$$.getSingle('.tab_search');
    		var moreInfoArea = jindo.$Element(jindo.$$.getSingle('.tbl_search')).next().$value();
    		
    		jindo.$Fn(function(e) {toggleTabSearch(e); getStockRightMenuData('000000', 'recent', 1, 'https://finance.naver.com', 'https://ssl.pstatic.net/static/nfinance'); }).attach(jindo.$$.getSingle('a' ,tabSearchArea), 'click');
    		jindo.$Fn(function(e) {toggleTabSearch(e); getStockRightMenuData('000000', 'mystock', 1, 'https://finance.naver.com', 'https://ssl.pstatic.net/static/nfinance'); }).attach(jindo.$$('a' ,tabSearchArea)[1], 'click');
    		jindo.$Fn(function(e) {updatePageForPaging (currentType, 'up', 'https://finance.naver.com', 'https://ssl.pstatic.net/static/nfinance'); }).attach(jindo.$$.getSingle('a', moreInfoArea), 'click');
    		jindo.$Fn(function(e) {updatePageForPaging (currentType, 'down', 'https://finance.naver.com', 'https://ssl.pstatic.net/static/nfinance'); }).attach(jindo.$$('a', moreInfoArea)[1], 'click');
    	} 
    
    }).attach(document,"domready");
    
    function openMyitemNew(stockHost) {
    	var selectedElement = jindo.$Element(jindo.$$.getSingle(".tab_search"));
    	if (selectedElement != null) {
    		if (selectedElement.hasClass("search1")) {
    			document.location.href = stockHost + '/mystock/recentSearchItemList.nhn';
    		} else {
    			document.location.href = stockHost + '/mystock/itemList.nhn';
    		}
    	}
    }
     </script>
    </div>
    <div id="footer">
     <ul>
      <li class="first">
       <a href="https://www.naver.com/rules/service.html" onclick="clickcr(this, 'fot.service', '', '', event);" target="_blank">
        이용약관
       </a>
      </li>
      <li>
       <a href="/rules.nhn" onclick="clickcr(this, 'fot.policy', '', '', event);" target="_blank">
        금융게시판 운영원칙
       </a>
      </li>
      <li>
       <a href="https://www.naver.com/rules/privacy.html" onclick="clickcr(this, 'fot.privacy', '', '', event);" target="_blank">
        <strong>
         개인정보처리방침
        </strong>
       </a>
      </li>
      <li>
       <a href="https://www.naver.com/rules/disclaimer.html" onclick="clickcr(this, 'fot.limit', '', '', event);" target="_blank">
        책임의 한계와 법적고지
       </a>
      </li>
      <li>
       <a href="https://help.naver.com/support/alias/contents2/finance/finance_1.naver" onclick="clickcr(this, 'fot.help', '', '', event);" target="_blank">
        금융 고객센터
       </a>
      </li>
     </ul>
     <p class="desc">
      네이버(주)에서 제공하는 금융정보는 투자 참고 사항이며, 오류가 발생할 수 있고 지연될 수 있습니다.
      <br/>
      네이버(주)는 제공된 정보에 의한 투자결과에 대한 법적인 책임을 지지 않습니다. 또한 게시된 정보를 무단으로 배포할 수 없습니다.
     </p>
     <address>
      <a class="logo" href="https://www.navercorp.com/" onclick="clickcr(this, 'fot.nhn', '', '', event);" target="_blank">
       <img alt="NAVER" height="11" src="https://ssl.pstatic.net/static/common/footer/ci_naver.gif" width="63"/>
      </a>
      <em>
       Copyright ©
      </em>
      <a href="https://www.navercorp.com/" onclick="clickcr(this, 'fot.nhn', '', '', event);" target="_blank">
       NAVER Corp.
      </a>
      <span>
       All Rights Reserved.
      </span>
     </address>
    </div>
    <script type="text/javascript">
     function isVisible(obj) {
        if (obj == document) return true
     
        if (!obj) return false
        if (!obj.parentNode) return false
        if (obj.style) {
            if (obj.style.display == 'none') return false
            if (obj.style.visibility == 'hidden') return false
        }
     
        if (window.getComputedStyle) {
            var style = window.getComputedStyle(obj, "")
            if (style.display == 'none') return false
            if (style.visibility == 'hidden') return false
        }
     
        var style = obj.currentStyle
        if (style) {
            if (style['display'] == 'none') return false
            if (style['visibility'] == 'hidden') return false
        }
     
        return isVisible(obj.parentNode)
    }
    
    function isChildOf(myobj, containerObj) {
    	while(myobj != undefined) {
    		if (myobj == document.body) {
    			break;
    		} 
    		if (myobj == containerObj) {
    			return true;
    		}
    		myobj = myobj.parentElement;
    	}
    	return false;	
    }
    
    function gnbLayerClose(e){
    	var target = e.target ? e.target : e.srcElement;
    	if (isVisible(document.getElementById('gnb_service_lyr')) || isVisible(document.getElementById('gnb_notice_lyr')) ||isVisible(document.getElementById('gnb_my_lyr')) ) {
    		if (!isChildOf(target, document.getElementById('gnb'))) {
    			gnbAllLayerClose();
    		}
    	}	
    }
    
    var isIE = (navigator.userAgent.toLowerCase().indexOf("msie")!=-1 && window.document.all) ? true:false;
    if (isIE) {
    	document.attachEvent('onmousedown', gnbLayerClose);
    } else {
    	window.addEventListener('mousedown', gnbLayerClose);
    }
    </script>
    
    


```python
soup.find_all('span', 'value')[0].string
```




    '1,117.00'



## 실전: 시카고 샌드위치 맛집 소개 사이트에 접근하기


```python
from bs4 import BeautifulSoup 
from urllib.request import urlopen

url_base = 'http://www.chicagomag.com'
url_sub = '/Chicago-Magazine/November-2012/Best-Sandwiches-Chicago/'
url = url_base + url_sub

html = urlopen(url)
soup = BeautifulSoup(html, "html.parser")

soup
```




    
    <!DOCTYPE doctype html>
    
    <html lang="en">
    <head>
    <!-- Urbis magnitudo. Fabulas magnitudo. -->
    <meta charset="utf-8"/>
    <style>a.edit_from_site {display: none !important;}</style>
    <title>
      The 50 Best Sandwiches in Chicago |
      Chicago magazine
          |  November 2012
        </title>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable = no" name="viewport">
    <meta content="Our list of Chicago’s 50 best sandwiches, ranked in order of deliciousness" name="description"/>
    <!-- <meta name="description" content="Our list of Chicago’s 50 best sandwiches, ranked in order of deliciousness"> -->
    <meta content="sandwiches, dining" name="keywords"/>
    <meta content="37873197144" property="fb:pages">
    <link href="//www.googletagservices.com" rel="dns-prefetch"/>
    <link href="//ajax.googleapis.com" rel="dns-prefetch"/>
    <link href="//securepubads.g.doubleclick.net" rel="dns-prefetch"/>
    <link href="//media.chicagomag.com" rel="dns-prefetch"/>
    <link href="//ox-d.godengo.com/" rel="dns-prefetch"/>
    <link href="//www.google-analytics.com" rel="dns-prefetch"/>
    <link href="//ping.chartbeat.net" rel="dns-prefetch"/>
    <link href="//static.chartbeat.com" rel="dns-prefetch"/>
    <link href="//www.google.com" rel="dns-prefetch"/>
    <link href="//cse.google.com" rel="dns-prefetch"/>
    <link href="//www.googleapis.com" rel="dns-prefetch"/>
    <link href="//maps.googleapis.com" rel="dns-prefetch"/>
    <link href="//csi.gstatic.com" rel="dns-prefetch"/>
    <link href="//www.facebook.com" rel="dns-prefetch"/>
    <link href="//staticxx.facebook.com" rel="dns-prefetch"/>
    <link href="//connect.facebook.net" rel="dns-prefetch"/>
    <link href="//contentz.mkt51.net" rel="dns-prefetch"/>
    <link href="//www.pages05.net" rel="dns-prefetch"/>
    <link href="//fbcdn.net" rel="dns-prefetch"/>
    <link href="//players.brightcove.net" rel="dns-prefetch"/>
    <link href="//edge.api.brightcove.com" rel="dns-prefetch"/>
    <link href="//metrics.brightcove.com" rel="dns-prefetch"/>
    <link href="//c.brightcove.com" rel="dns-prefetch"/>
    <link href="//pvp.trb.com" rel="dns-prefetch"/>
    <link href="//s.ntv.io" rel="dns-prefetch"/>
    <!-- For Citi sponsorship (move these to above ^^ links after Nov 2018) -->
    <!-- -->
    <!-- DOES NOT HAVE REAL ESTATE CATEGORY OR IS NOT REAL ESTATE HOMEPAGE -->
    <link href="//chicagomag.disqus.com" rel="dns-prefetch"/>
    <link href="//www.disqus.com" rel="dns-prefetch"/>
    <link href="//a.disquscdn.com" rel="dns-prefetch"/>
    <!-- -->
    <link href="https://use.typekit.net/vdh5vgk.css" rel="stylesheet"/>
    <meta content="www.pages05.net,chicagomag.com" name="com.silverpop.brandeddomains">
    <script>
    (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
    (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
    m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
    })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
    
    ga('create', 'UA-297666-1', {'siteSpeedSampleRate': 100});
    
    
    
    ga('set', 'dimension1', 'restaurant')
    
    
    
      ga('set', 'dimension4', '2012-10-09 18:12:00');
    
      ga('set', 'dimension5', 'article.tpl');
    
    ga('send', 'pageview');
    
    
    var trackOutboundLink = function(url) {
       ga('send', 'event', 'outbound', 'click', url, {
         'transport': 'beacon',
         'hitCallback': function(){document.location = url;}
       });
    }
    
    
    
    
    
    </script>
    <link href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-Chicago/" rel="canonical">
    <meta content="The 50 Best Sandwiches in Chicago" name="title"/>
    <meta content="all" name="robots"/>
    <meta content="sandwiches, dining" name="news_keywords"/>
    <!-- -->
    <link href="/images/cache/cache_2/cache_9/cache_3/sandwich-old-oak-08ecb392.jpeg?ver=1482330047&amp;aspectratio=1.5" rel="image_src">
    <meta content="/images/cache/cache_2/cache_9/cache_3/sandwich-old-oak-08ecb392.jpeg?ver=1482330047&amp;aspectratio=1.5" name="thumbnail"/>
    <script type="text/javascript">var _sf_startpt=(new Date()).getTime()</script>
    <link href="http://media.chicagomag.com/favicon.ico" rel="shortcut icon"/>
    <meta content="summary_large_image" name="twitter:card"/>
    <meta content="/images/cache/cache_4/cache_a/cache_0/sandwich-old-oak-c8b2a0a4.jpeg?ver=1482330047&amp;aspectratio=1.5" name="twitter:image"/>
    <meta content="/images/cache/cache_b/cache_7/cache_4/sandwich-old-oak-bc2dc47b.jpeg?ver=1482330047&amp;aspectratio=1.5" property="og:image"/>
    <meta content="Chicago magazine" name="twitter:app:name:ipad"/>
    <meta content="567851357" name="twitter:app:id:ipad"/>
    <meta content="Chicago magazine" name="twitter:app:name:googleplay"/>
    <meta content="com.chicagomag.chicagomag" name="twitter:app:id:googleplay"/>
    <meta content="The 50 Best Sandwiches in Chicago" property="og:title">
    <meta content="The 50 Best Sandwiches in Chicago" name="twitter:title">
    <meta content="article" property="og:type">
    <meta content="bikesarefun" property="fb:admins">
    <meta content="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-Chicago/" property="og:url">
    <meta content="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-Chicago/" name="twitter:url">
    <meta content="Chicago magazine" property="og:site_name">
    <meta content="Our list of Chicago’s 50 best sandwiches, ranked in order of deliciousness" property="og:description"/>
    <meta content="Our list of Chicago’s 50 best sandwiches, ranked in order of deliciousness" name="twitter:description"/>
    <meta content="@chicagomag" name="twitter:site"/>
    <script type="text/javascript">
          var articleID = '19493';
        var adcat = "restaurant";
    
      // Set the video variables
      var videoID = "Inside Best Restaurants";// Fallback
      
        // adcat: restaurant
                var videoID = "Inside Best Restaurants";
          
      
    </script>
    <script type="text/javascript">
    
      // Asynchronous
      var googletag = googletag || {};
          googletag.cmd = googletag.cmd || [];
    
      // Set some global variables so we can refresh these slots asynchronously
      var cube2 = '',
          leaderboard1 = '',
          leaderboard6 = '';
      (function() {
        var gads = document.createElement("script");
        gads.async = true;
        gads.type = "text/javascript";
          var useSSL = "https:" == document.location.protocol;
          gads.src = (useSSL ? "https:" : "http:") + "//www.googletagservices.com/tag/js/gpt.js";
          var node =document.getElementsByTagName("script")[0];
          node.parentNode.insertBefore(gads, node);
        })();
    
    </script>
    <script type="text/javascript">
      googletag.cmd.push(function() {
      googletag.defineSlot('/4011/trb.chicagomag/hp', [[300,250]],'div-gpt-ad-cube-1').setTargeting('pos',['1']).addService(googletag.pubads());
      cube2 = googletag.defineSlot('/4011/trb.chicagomag/hp', [[300,250]],'div-gpt-ad-cube-2').setTargeting('pos',['2']).addService(googletag.pubads());
      googletag.defineSlot('/4011/trb.chicagomag/hp', [[300,250]],'div-gpt-ad-cube-3').setTargeting('pos',['3']).addService(googletag.pubads());
      googletag.defineSlot('/4011/trb.chicagomag/hp', [[300,250]],'div-gpt-ad-cube-4').setTargeting('pos',['4']).addService(googletag.pubads());
      googletag.defineSlot('/4011/trb.chicagomag/hp', [[300,250]],'div-gpt-ad-cube-5').setTargeting('pos',['5']).addService(googletag.pubads());
      googletag.defineSlot('/4011/trb.chicagomag/hp', [[300,250]],'div-gpt-ad-cube-6').setTargeting('pos',['6']).addService(googletag.pubads());
    
      if (window.innerWidth > 727) {
        leaderboard1 = googletag.defineSlot('/4011/trb.chicagomag/hp', [[728,90]],'div-gpt-ad-leaderboard-1').setTargeting('pos',['1']).addService(googletag.pubads());
        googletag.defineSlot('/4011/trb.chicagomag/hp', [[728,90]],'div-gpt-ad-leaderboard-2').setTargeting('pos',['2']).addService(googletag.pubads());
        googletag.defineSlot('/4011/trb.chicagomag/hp', [[728,90]],'div-gpt-ad-leaderboard-3').setTargeting('pos',['3']).addService(googletag.pubads());
        googletag.defineSlot('/4011/trb.chicagomag/hp', [[728,90]],'div-gpt-ad-leaderboard-4').setTargeting('pos',['4']).addService(googletag.pubads());
        googletag.defineSlot('/4011/trb.chicagomag/hp', [[728,90]],'div-gpt-ad-leaderboard-5').setTargeting('pos',['5']).addService(googletag.pubads());
        leaderboard6 = googletag.defineSlot('/4011/trb.chicagomag/hp', [[728,90]],'div-gpt-ad-leaderboard-6').setTargeting('pos',['6']).addService(googletag.pubads());
      }
      else {
        googletag.defineSlot('/4011/trb.chicagomag/hp', [[320,50]],'div-gpt-ad-mobile-leaderboard-1').setTargeting('pos',['1']).addService(googletag.pubads());
        googletag.defineSlot('/4011/trb.chicagomag/hp', [[320,50]],'div-gpt-ad-mobile-leaderboard-2').setTargeting('pos',['2']).addService(googletag.pubads());
      }
    
      // googletag.defineSlot('/4011/trb.chicagomag/hp', [[160,600]],'div-gpt-ad-999449422057474470-6').setTargeting('pos',['1']).addService(googletag.pubads());
      // googletag.defineSlot('/4011/trb.chicagomag/hp', [[160,600]],'div-gpt-ad-999449422057474470-7').setTargeting('pos',['2']).addService(googletag.pubads());
    
      if (window.innerWidth > 599) {
        googletag.defineSlot('/4011/trb.chicagomag/hp', [[600,100]],'div-gpt-ad-600-100').setTargeting('pos',['1']).addService(googletag.pubads());
        googletag.defineSlot('/4011/trb.chicagomag/hp', [[600,400]],'div-gpt-ad-600-400-1').setTargeting('pos',['1']).addService(googletag.pubads());
        googletag.defineSlot('/4011/trb.chicagomag/hp', [[600,400]],'div-gpt-ad-600-400-2').setTargeting('pos',['2']).addService(googletag.pubads());
        googletag.defineSlot('/4011/trb.chicagomag/hp', [[600,400]],'div-gpt-ad-600-400-3').setTargeting('pos',['3']).addService(googletag.pubads());
      }
    
    
      googletag.defineSlot('/4011/trb.chicagomag/hp', [[336,392]],'div-gpt-ad-999449422057474470-9').setTargeting('pos',['1']).addService(googletag.pubads());
    
      googletag.defineSlot('/4011/trb.chicagomag/hp', [[300,100]],'div-gpt-ad-video-companion').addService(googletag.pubads());
    
      googletag.defineOutOfPageSlot('/4011/trb.chicagomag/hp', 'div-out-of-page').addService(googletag.pubads());
    
      // Custom chicago mag targeting
    
      googletag.pubads().setTargeting("adcat", "restaurant");
      googletag.pubads().setTargeting("pub", "chi");
      googletag.pubads().setTargeting("pubID", "100");
      googletag.pubads().setTargeting("videoID", videoID);
      googletag.pubads().collapseEmptyDivs();
      
                googletag.pubads().setTargeting("articleID", "19493");
          
                        googletag.pubads().setTargeting("articleTag", "602"); 
                            googletag.pubads().setTargeting("articleTag", "608"); 
                            googletag.pubads().setTargeting("articleTag", "636"); 
                            googletag.pubads().setTargeting("articleTag", "286"); 
                          
      googletag.enableServices();
    
      });// End googletag.cmd.push()
    
    </script>
    <!-- End: GPT -->
    <link href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-Chicago/" rel="standout">
    <!-- responsive -->
    <script language="Javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.8.1/jquery.min.js" type="text/javascript"></script>
    <script type="text/javascript">
    if (typeof jQuery == 'undefined') {
    	document.write(unescape("%3Cscript src='/core/media/js/jquery-1.8.1.min.js' type='text/javascript'%3E%3C/script%3E"));
    }
    </script>
    <script type="text/javascript">
    var rjQuery = jQuery.noConflict(); // leave the $ variable alone
    </script>
    <script language="JavaScript" src="/theme_overrides/Respond/js/site-search.js?ver=1386069772" type="text/javascript"></script>
    <script language="JavaScript" src="/core/media/js/base.js?ver=1473876728" type="text/javascript"></script>
    <!--[if lt IE 9]>
    <script  type="text/javascript" language="JavaScript" src="/core/media/themes/Respond/js/html5_shiv.js?ver=1473876729"></script>
    <![endif]-->
    <script language="JavaScript" src="/core/media/js/jquery.browser.js?ver=1473876728" type="text/javascript"></script>
    <script language="JavaScript" src="/theme_overrides/Respond/js/jquery.iframe-auto-height.plugin.1.8.0.js?ver=1446756854" type="text/javascript"></script>
    <link href="/lessc/35a3627424516b7ccf4314a41b92358c.one.css?ver=1540232190" rel="stylesheet" type="text/css"/>
    <link href="/lessc/35a3627424516b7ccf4314a41b92358c.two.css?ver=1540232190" rel="stylesheet" type="text/css"/>
    <script language="JavaScript" src="/theme_overrides/Respond/js/functions.js?ver=1483891934" type="text/javascript"></script>
    <link href="/core/media/css/responsive_ads.css?ver=1473876728" rel="stylesheet" type="text/css"/>
    <link href="/theme_overrides/Respond/css/sandwiches.css?ver=1473559134" rel="stylesheet" type="text/css"/>
    <!--[if IE 9]>
    <link rel="stylesheet" type="text/css" href="/core/media/themes/Respond/css/ie9.css?ver=1473876729">
    <![endif]-->
    <!--[if lt IE 9]>
    <link rel="stylesheet" type="text/css" href="/core/media/themes/Respond/css/ie8.css?ver=1473876729">
    <![endif]-->
    <script type="text/javascript">RivistaJSString.put({
    
    
    
    
    "_":""});</script>
    <!--[if lt IE 9]>
    <link rel="stylesheet" type="text/css" href="/theme_overrides/Respond/css/chimag-ie8.css?ver=1443881828">
    <![endif]-->
    <!--[if gt IE 7]>
    <link rel="stylesheet" type="text/css" href="/theme_overrides/Respond/css/chimag-ie.css?ver=1381349768">
    <![endif]-->
    <script>
    var ad_category = "Restaurants and Bars";
    </script>
    <link href="http://www.chicagomag.com/feeds/Chicago+magazine%3A+Dining+and+Drinking" rel="alternate" title=" Chicago magazine: Dining and Drinking Feed" type="application/atom+xml">
    <script>
    window.editorIdMap = new Object;
    editorIdMap['120'] = {'mid': 1425,'view': 'edit_report'};
    editorIdMap['120'] = {'mid': 1426,'view': 'edit_report'};
    editorIdMap['122'] = {'mid': 1431,'view': 'edit_gateway'};
    editorIdMap['77'] = {'mid': 1350,'view': 'edit_config'};
    editorIdMap['11'] = {'mid': 1387,'view': 'edit_user'};
    editorIdMap['80'] = {'mid': 1423,'view': ''};
    editorIdMap['80'] = {'mid': 1421,'view': ''};
    editorIdMap['60'] = {'mid': 1328,'view': ''};
    editorIdMap['60'] = {'mid': 1327,'view': ''};
    editorIdMap['136'] = {'mid': 1491,'view': 'edit_comment'};
    editorIdMap['60'] = {'mid': 1322,'view': ''};
    editorIdMap['138'] = {'mid': 1492,'view': ''};
    editorIdMap['133'] = {'mid': 1488,'view': ''};
    editorIdMap['19'] = {'mid': 81,'view': 'edit_moduledefinition'};
    editorIdMap['21'] = {'mid': 694,'view': 'edit_module'};
    editorIdMap['16'] = {'mid': 1386,'view': 'edit_group'};
    editorIdMap['128'] = {'mid': 1479,'view': 'edit_issueperiod'};
    editorIdMap['79'] = {'mid': 1333,'view': 'edit_publication'};
    editorIdMap['78'] = {'mid': 1334,'view': 'edit_issue'};
    editorIdMap['97'] = {'mid': 1357,'view': 'edit_content'};
    editorIdMap['94'] = {'mid': 1388,'view': ''};
    editorIdMap['17'] = {'mid': 1389,'view': 'edit_menu'};
    editorIdMap['72'] = {'mid': 1422,'view': 'edit_product'};
    editorIdMap['2'] = {'mid': 1332,'view': ''};
    editorIdMap['2'] = {'mid': 1339,'view': ''};
    editorIdMap['60'] = {'mid': 1329,'view': ''};
    editorIdMap['98'] = {'mid': 1336,'view': 'edit_survey'};
    editorIdMap['85'] = {'mid': 1338,'view': 'edit_newsletter'};
    editorIdMap['86'] = {'mid': 1337,'view': 'edit_gallery'};
    editorIdMap['123'] = {'mid': 1473,'view': 'edit_calendar'};
    editorIdMap['140'] = {'mid': 1493,'view': 'edit_menu'};
    editorIdMap['116'] = {'mid': 1406,'view': 'edit_wrapper'};
    editorIdMap['118'] = {'mid': 1419,'view': 'edit_form'};
    editorIdMap['132'] = {'mid': 1489,'view': 'edit_geobase'};
    editorIdMap['134'] = {'mid': 1490,'view': 'edit_videochannel'};
    editorIdMap['130'] = {'mid': 1480,'view': 'edit_pageposition'};
    editorIdMap['117'] = {'mid': 1418,'view': 'edit_campaign'};
    editorIdMap['69'] = {'mid': 1424,'view': 'edit_category'};
    editorIdMap['115'] = {'mid': 1335,'view': 'edit_page'};
    editorIdMap['60'] = {'mid': 1325,'view': ''};
    editorIdMap['121'] = {'mid': 1430,'view': 'edit_shipcarrier'};
    editorIdMap['2'] = {'mid': 1340,'view': ''};
    editorIdMap['144'] = {'mid': 1495,'view': 'edit_recurring'};
    editorIdMap['146'] = {'mid': 1499,'view': 'edit_section'};
    editorIdMap['148'] = {'mid': 1501,'view': ''};
    editorIdMap['153'] = {'mid': 1505,'view': ''};
    editorIdMap['154'] = {'mid': 1506,'view': ''};
    editorIdMap['155'] = {'mid': 1507,'view': ''};
    editorIdMap['115'] = {'mid': 1360,'view': 'edit_page'};
    editorIdMap['126'] = {'mid': 1478,'view': 'edit_customtable'};
    editorIdMap['147'] = {'mid': 1500,'view': ''};
    editorIdMap['150'] = {'mid': 1502,'view': ''};
    editorIdMap['149'] = {'mid': 1503,'view': ''};
    editorIdMap['131'] = {'mid': 1481,'view': 'edit_tag'};
    editorIdMap['145'] = {'mid': 1496,'view': 'edit_redirect'};
    editorIdMap['143'] = {'mid': 1494,'view': 'edit_creditcardtype'};
    editorIdMap['156'] = {'mid': 1510,'view': ''};
    editorIdMap['119'] = {'mid': 1420,'view': 'edit_user'};
    editorIdMap['126'] = {'mid': 1512,'view': 'edit_customtable'};
    editorIdMap['158'] = {'mid': 1513,'view': ''};
    editorIdMap['157'] = {'mid': 1511,'view': ''};
    var pagetype = 'article';
    var admin_edit_itemid = '19493';
    var admin_edit_text = 'Edit Article';
    var admin_edit_link_extras = '';
    editorIdMap['article'] = {'mid': 1335,'view': 'edit_page'};
    editorIdMap['content'] = {'mid': 1360,'view': 'edit_page'};
    editorIdMap['cde'] = {'mid': 1512,'view': 'edit_customtable'};
    editorIdMap['cde_record'] = {'mid': 1512,'view': 'edit_customdata'};
    editorIdMap['evergreen'] = {'mid': 1335,'view': 'edit_page'};
    editorIdMap['issue'] = {'mid': 1360,'view': 'edit_page'};
    editorIdMap['publication'] = {'mid': 1360,'view': 'edit_page'};
    </script>
    <script src="/theme_overrides/Respond/js/edit_from_site.js?ver=1446828087"></script><link href="/core/media/css/edit_from_site.css" rel="stylesheet" type="text/css"/></link></link></meta></meta></meta></meta></meta></meta></meta></link></link></meta></meta></meta></head>
    <body class="land long tpl-article din " id="page_htmlid_19493">
    <script>
      var timestamp = 'Dec%2029%202018%2000:44';
    </script>
    <style>
    /*--- Mobile Reveal bar ---*/
    #reveal-bar-mobile {
      background: rgba(255,255,255,.95);
      border-bottom: 1px solid #aaa;
      display: none;
      padding: 11px 10px 9px 0;
      position: fixed;
      width: 100%;
      -webkit-transform: translate3d(0,-80px,0);
      -moz-transform   : translate3d(0,-80px,0);
      -o-transform     : translate3d(0,-80px,0);
      -mstransform     : translate3d(0,-80px,0);
      transform        : translate3d(0,-80px,0);
      transition       : 1s ease-in-out;
      z-index          : 99999;
    }
    #reveal-bar-mobile.reveal-bar-show {
      -webkit-transform: translate3d(0,0,0);
      -moz-transform: translate3d(0,0,0);
      -o-transform: translate3d(0,0,0);
      -mstransform: translate3d(0,0,0);
      transform: translate3d(0,0,0);
    }
    #reveal-bar-mobile .reveal-share-buttons {
      float: right;
      margin-right: 10px;
      /*width: 72px;*/
    }
    #reveal-bar-mobile .reveal-share-buttons li {
      margin: 0 5px;
    }
    #reveal-bar-mobile .reveal-share-buttons li a {
      height: 30px;
      width: 30px;
      border-radius: 15px;
      padding-top: 6px;
    }
    #reveal-bar-mobile .reveal-share-buttons li i {
      font-size: 18px;
    }
    
    button#reveal-bar-mobile-sections-button {
      background: none;
      color: #666;
      font-size: 16px;
      font-style: normal;
    
      margin: 0;
      padding: 8px 10px 5px;
    }
    
    .reveal-bar-mobile-section {
      width: 100%;
    }
    
    .reveal-bar-mobile-section-link,
    .reveal-bar-mobile-section-link:hover,
    .reveal-bar-mobile-section-link:active,
    .reveal-bar-mobile-section-link:visited {
      background: #222;
      border-bottom: 1px solid #aaa;
      -moz-box-sizing: border-box; -webkit-box-sizing: border-box; box-sizing: border-box;
      color: #f0f0f0;
      display: table-cell;
      font-family: "proxima-nova",sans-serif;
      font-size: 20px;
      padding: 0 10px;
      vertical-align: middle;
      width: 100%;
    }
    ..reveal-bar-mobile-section-link:on
    .reveal-bar-mobile-section-link.reveal-bar-mobile-section-link-close {
      font-size: 16px;
      font-weight: 100%;
      padding-right: 20px;
      /*text-align: right;*/
      text-transform: uppercase;
    }
    
    #reveal-bar-mobile-sections {
      display: none;
      position: fixed;
      top: 20px;
      -webkit-transform: translate3d(0,-800px,0);
      -moz-transform   : translate3d(0,-800px,0);
      -o-transform     : translate3d(0,-800px,0);
      -mstransform     : translate3d(0,-800px,0);
      transform        : translate3d(0,-800px,0);
      transition       : .5s ease-in-out;
      width: 100%;
      z-index: 998;
    }
    #reveal-bar-mobile-sections.show {
      -webkit-transform: translate3d(0,0,0);
      -moz-transform   : translate3d(0,0,0);
      -o-transform     : translate3d(0,0,0);
      -mstransform     : translate3d(0,0,0);
      transform        : translate3d(0,0,0);
    }
    
    @media only screen and (max-width : 450px) {
      #reveal-bar-mobile {
        display: block;
      }
    }
    </style>
    <div id="reveal-bar-mobile">
    <button id="reveal-bar-mobile-sections-button">More →</button>
    <ul class="reveal-share-buttons list-no-bullets clearfix">
    <li class="fb"><a href="https://www.facebook.com/sharer/sharer.php?u=http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-Chicago/%3Futm_source=facebook.com%26utm_medium=referral%26utm_campaign=Facebook%20mobile%20reveal%20bar%2019493%20Dec%2029%202018%2000:44" onclick="ga('send',  'event', 'Sharing', 'Facebook share from mobile reveal bar','The 50 Best Sandwiches in Chicago'); return socialPopup('https://www.facebook.com/sharer/sharer.php?u=http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-Chicago/%3Futm_source=facebook.com%26utm_medium=referral%26utm_campaign=Facebook%20mobile%20reveal%20bar%2019493%20Dec%2029%202018%2000:44');" target="_blank"><i class="icon-facebook"></i></a>
    </li>
    <li class="tw"><a href="https://twitter.com/share?text=The+50+Best+Sandwiches+in+Chicago&amp;url=http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-Chicago/" onclick="ga('send',  'event', 'Sharing', 'Tweet story from mobile reveal bar','The 50 Best Sandwiches in Chicago'); return tweetPopup('The+50+Best+Sandwiches+in+Chicago','/Chicago-Magazine/November-2012/Best-Sandwiches-Chicago/','mobile+reveal+bar+19493');" target="_blank"><i class="icon-twitter"></i></a></li>
    </ul>
    </div>
    <ul class="list-no-bullets" id="reveal-bar-mobile-sections">
    <li class="reveal-bar-mobile-section"><a class="reveal-bar-mobile-section-link" href="/dining-drinking" onclick="ga('send',  'event', 'Mobile reveal bar', 'Dining');">Dining &amp; Drinking</a></li>
    <li class="reveal-bar-mobile-section"><a class="reveal-bar-mobile-section-link" href="/city-life" onclick="ga('send',  'event', 'Mobile reveal bar', 'City Life');">Politics &amp; City Life</a></li>
    <li class="reveal-bar-mobile-section"><a class="reveal-bar-mobile-section-link" href="/real-estate" onclick="ga('send',  'event', 'Mobile reveal bar', 'Real Estate');">Real Estate</a></li>
    <li class="reveal-bar-mobile-section"><a class="reveal-bar-mobile-section-link" href="/Chicago-Home" onclick="ga('send',  'event', 'Mobile reveal bar', 'Home and Garden');">Home &amp; Garden</a></li>
    <li class="reveal-bar-mobile-section"><a class="reveal-bar-mobile-section-link" href="/style-shopping" onclick="ga('send',  'event', 'Mobile reveal bar', 'Style Shopping');">Style &amp; Shopping</a></li>
    <li class="reveal-bar-mobile-section"><a class="reveal-bar-mobile-section-link" href="/video" onclick="ga('send',  'event', 'Mobile reveal bar', 'Video');">Video</a></li>
    <li class="reveal-bar-mobile-section"><a class="reveal-bar-mobile-section-link reveal-bar-mobile-section-link-close" href="javascript:void(0);" onclick="jQuery('#reveal-bar-mobile-sections').removeClass('show');"><i class="icon-remove"></i></a></li>
    </ul>
    <script>
      var mobile_sections_el = jQuery('#reveal-bar-mobile-sections');
      if (document.documentElement.clientWidth <= 400) {
        mobile_sections_el.show();
      }
      mobile_sections_el.css('top',jQuery('#reveal-bar-mobile').outerHeight() - 1)
      jQuery('.reveal-bar-mobile-section-link')
        .height((jQuery(window).height() - jQuery('#reveal-bar-mobile').height() - 50) / 7 - 2)
        .width(mobile_sections_el.width())
      jQuery('#reveal-bar-mobile-sections-button').on('click', function(){
        mobile_sections_el.toggleClass('show');
      })
    </script>
    <div class="hidden-phone" id="reveal-bar">
    <div class="container">
    <a class="logo background-logo-svg" href="/">Chicago magazine</a>
    <div class="input-append">
    <form action="http://www.chicagomag.com/search?from=revealbar&amp;" id="reveal-bar-search" name="revealBarSearch"><input class="text clean" id="reveal-bar-input" name="q" placeholder="Search" type="text"/>
    <button id="reveal-bar-button"><i class="icon-search"></i></button>
    </form>
    </div>
    <div id="article-reveal-share">
    <span class="article-reveal-share-intro">Share this story</span>
    <ul class="reveal-share-buttons list-no-bullets clearfix">
    <li class="fb"><a href="https://www.facebook.com/sharer/sharer.php?u=http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-Chicago/%3Futm_source=facebook.com%26utm_medium=referral%26utm_campaign=Facebook%20reveal%20bar%2019493%20Dec%2029%202018%2000:44" onclick="ga('send', 'event','Sharing', 'Facebook share from reveal bar','The 50 Best Sandwiches in Chicago'); return socialPopup('https://www.facebook.com/sharer/sharer.php?u=http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-Chicago/%3Futm_source=facebook.com%26utm_medium=referral%26utm_campaign=Facebook%20share%20reveal%20bar%2019493%20Dec%2029%202018%2000:44');" target="_blank"><i class="icon-facebook"></i></a>
    </li>
    <li class="tw"><a href="https://twitter.com/share?text=The+50+Best+Sandwiches+in+Chicago&amp;url=http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-Chicago/" onclick="ga('send', 'event','Sharing', 'Tweet story from reveal bar','The 50 Best Sandwiches in Chicago'); return tweetPopup('The+50+Best+Sandwiches+in+Chicago','/Chicago-Magazine/November-2012/Best-Sandwiches-Chicago/','reveal+bar+19493');" target="_blank"><i class="icon-twitter"></i></a></li>
    <li class="eml"><a href="#" onclick="window.open('/core/pagetools.php?url=%2FChicago-Magazine%2FNovember-2012%2FBest-Sandwiches-Chicago%2Findex.php&amp;mode=email','emailThisPage','status=no,scrollbars=yes,resizable=yes,width=420,height=540'); ga('send', 
          'event','Sharing', 'Email story from reveal bar','The 50 Best Sandwiches in Chicago'); return false;" url="email this page"><i class="icon-envelope"></i></a></li>
    </ul>
    </div>
    </div>
    </div>
    <a class="edit_from_site" content_id="interstitial-newsletter" href="#" media_id="83">Edit Module</a>
    <style>
      
    
    #cta-backdrop {
      background: #fff;
      opacity: .97;
      display: none;
    }
    .modal#cta-modal {
      background: #eee;
      margin-top: 0;
      display: none;
      box-shadow: 3px 3px 12px rgba(0,0,0,0.2);
      overflow: auto;
      border-top: 8px solid #000;
    }
    .modal-close {
      background:none;
      color: #ff0000;
      font-weight: 300;
      font-size: 20px;
      font-style: normal;
      opacity: 1;
      text-align: right;
      width: 100%;
    }
    .modal-close:hover {
      background: none;
      color: black;
      opacity: 1;
    }
    #cta-modal p.cta-p {
      color: #333 !important;
      font-size: 18px;
      line-height: normal;
      text-align: center;
      margin-bottom: 15px;
    }
    .btn-nothanks, .btn-nothanks:hover {
      border: none !important;
      background: none !important;
      color: #666 !important;
    }
    #cta-modal .modal-body {
      padding: 30px;
      height: auto;
      max-height: none;
    }
    #cta-modal label {
      color: #333;
      text-align: center;
      margin-top: 5px;
      margin-bottom: 5px;
    }
    h2.cta-h2 {
      font-size: 45px;
      text-align: center;
      margin-bottom: 10px;
    }
    #cta-modal .btn-large {
      width: 200px;
      -moz-box-sizing: border-box; -webkit-box-sizing: border-box; box-sizing: border-box;
    }
    .cta-buttons {
      width: 200px;
      margin: 15px auto 0;
    }
    #cta-form {
      /*text-align: center;*/
      width: 200px;
      margin: 10px auto;
    }
    #cta-form input[type=text] {
      -moz-box-sizing: border-box; -webkit-box-sizing: border-box; box-sizing: border-box;
      height: 40px;
      width: 200px;
    }
    .cta-neveragain {
      text-align: right;
      font-size: 10px;
      text-transform: uppercase;
      text-decoration: none;
    }
    .cta-neveragain a {
      color: #999;
    }
    .cta-neveragain a:hover {
      color: #666;
    }
    #cta-newsletters-outer {
      max-width: 300px;
      margin: 0 auto;
    }
    a.cta-checkbox {
      padding-left: 25px;
      padding-bottom: 10px;
      background-repeat: no-repeat;
      background-position: 0 2px;
      background-image: url('http://media.chicagomag.com/img/checkbox-check.png');
      cursor: pointer;
      color: #000;
      display: block;
      font-size: 16px;
      margin-bottom: 5px;
      transition: none;
    }
    a.cta-checkbox:hover {
      color: #ff0000;
    }
    a.cta-checkbox-yes {
      background-position: 0px -83px;
      color: #666;
    }
    a.cta-checkbox-yes:hover {
      color: #666;
    }
    #cta-modal .form-error {
      margin-bottom: 15px;
    }
    #cta-modal .checkbox input[type="checkbox"] {
      /*top: 7px;*/
      margin-left: initial;
    }
    
    @media screen and (max-width: 420px) {
      .modal#cta-modal {
        border-top: none;
      }
      #cta-modal .modal-body {
        padding: 8px;
      }
      h2.cta-h2 {
        font-size: 30px;
      }
      a.cta-checkbox {
        padding-bottom: 3px;
      }
    }
    
    
    @media screen and (min-width: 420px) {
      .modal#cta-modal {
        /*Logo, 300x60, #eee background*/
        background-image: url('data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAASwAAABQCAMAAACUNLhJAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAutQTFRF7+7uIx8gwcHD6urqJiIj7Ovr5eTl5uXl1tbXTExOSEhKiYqN7ezs2dnZcnN1SkpMTU1PKCUmmpud4uLidXZ4t7i6l5ia5OPjjpCS2trb6OjoRERGJCEiQkFDOTg6JyQl4ODhzc7PKSYn5+bmnZ6ghYeJ5eTkNTM1lJaY4eHhKygp1tXWyMnKzMzOmJmbnZ+hl5mbfX+B0NDRvr/A6enp39/f0tLSp6iqm5yeRkZIUlNVQ0JELCkqQUBC5+fn3NzdQ0NF1NTVu7y+0dHSp6mrq62uy8zNSUlLiImL1tfXq6uts7S1xsbHhIaI29vcubq7nJ2fn6CjWlteLSssjY+RUlJUNzY3wsLD19fYlJWY4+PjODc4Pz5AuLm7cHF0yMjJ3t7fPDs8qaqssbK0fX6Azs/Q3d3esLGzkZOVTk5QwsPET09Rr7Gyx8fJV1haU1RWcXJ0q6yu1dXVzM3OrK2v09LT4+PiwMHCVVVXiIqMkZKVr7CyqqutxMTGra6w3d3doKKkVFVWo6SmXV5gMzEyamttb3Bylpea2draysvMY2Nl2NjZpaepRUVHh4iKqKqsLy0vmZqcycrLxsfIrq+xu72+Pj0+oKGjhoeKtre4R0dJlZeZQD9BpKaokpSWdHV3uru9X2Fji4yOycnLZmdpv8DBsrO1WltdVldZYGFk09PUUVFTjY6QnqCiWVpctLW3uru8goSGUFBSfn+Cg4SHOjk7eHp8ZWZpe31/X2BiLiwtSktNpKWnz8/Rdnd6kJGUbG1wgIGDPTw9S0xOjI2PoqSms7S24ODgMTAxpqepbG1vbm9xaGpsxcXGgIKEj5GTk5SXgYOFt7i5NjU2tba4dXd5MC8wg4WHd3h6Xl9ha2xui42PYWJkgIKFvr7AZGVneXp9eHl7bW5xaGlrW1xeVVZYvb7AWFlbw8TFYmJkcnR2XF1fiouNc3V3f4CCoaOlZGRmZ2hqc3R3b3FzNDIzent9vL2/e3x+aWptop1pYQAADf1JREFUeNrsW2dYU8kanhBiVIJKKIkYJUKAoPQioAJSpUhTkCZiBwULKBZEFFCx9y5i77p2V117W1d37W1t23svt/y85wQyM6cfDD73uZe8vzhnvsx8552Zr80AgBlmmGGGGWaYYYYZZphhhhlmmGGGGWaYYYYZZpjx34DLmFVHa9TJx+L1iWtHfyg1vlZIZQZIZcJdyIyyCtbm3J4Whd5Wx55l6JJmJag4e2kcUXg0x2FeUTENGx4kq3VJWWUeCj7ZwKDRa2OKXB9YKZMGWLcxmSu372N2nLk9MPP49O2T/C5VxRk1L/fqboBXdCCT36mqRrgZuHWMNsr2YvvWtvbKY3aLvjl+cO+ui8m6bRFcmvhP7t59cndHwcktSDw0av6nE4szB+66eHJlkj/fLK6KvTTqTm3v4n2fXXRdH2Br2qpKvVt9SoLBKWx/gz/5xfKBYV0NCPtpAf1X0g0HexuQWe1JPidA2b2MyUvwedF+BT7C3HZbS9zYdHnTxdDHb/wKRz2bsE6D99e336tLO1lXl8Lzeu0QS0z00cETRx3fliprn314X0YMqiDJ2mR87LKYQdZtKBtAPvfsanwMpZH1id9glgE0mV5MZaTzGhunu3Er3Dl9ghNLf5Jzt+MYsvLHL3owJZ2+XhL0NlSp1mdK2PEjMVHyTsanjtaM7+pgbLO0MOyz9sbndhSyVp1ewTHCt0x1AuY2Njkv51xVNdUSLoTQhb+dxyVaO7P5XI37lHPkUaAlyFIoh3AN4Dyeqc8oY+MujqXV/SsJNz6iytq6unPL9njSs5lcpQ/m7u1kS5CVPYl7gL6jGfpcGA77jGJVeE8/Hq4kU6iuYr+EF2OzmkOV4w6+vtQtQNbob3gGmMuc2uuo9S82jdev4NPYMgmXnfWBRADr9jSDLDVvVz6mkxWZyTdA2DB6l0F9sG3yCVPhj5x5NbZMxWTjQiWCcD8qmqs5tJ8+uvbd2e2134ytP2d4DDaZLNvtNP/XtfdP288OnNixcfMfd6F3qcSlOzAigSP0dbWumND40wnFYYMMjzYWSDZlOlXUpkt17f6BxVepb5cuFMlVFsX6Of3bNyCFCD8U8qCFj/VDCascaypZgVcoivXzK52sIoK3QFX2tOXJX1tK3qPHuVrKB7oPoDX3otqr/idGFGhJjV20XkcchoYRixHFIvKhFNlOJ2f6t1EAqXb1lq0UM71XK4orj+/wH328luLupf7eEwtNJavEBhvg1L1yyq+HBTyZRw8Nl1Pjvde06PJLvLFLCNXiSaPXT3eKRMYNl613SMAkd57Bw1lXUWT54qZ2JTNByfUwkazcYmyEgZ5MDWbTBg1/n7pH6qMpzVU4989Z/L7teZhD9WyPye6ie92ZXbD1u1sEV4v7Y7OexCVlClkrMX0vR4hQKaoHiz+GmPoKi9DuC3SVgfXyxIPRvBCbxw4uwprpML/DHcyaQJYW84Q/eIhZ60MZ7nI21roR26NFAj2lYIHwHbYqw2S0tvp+L6iYtBZ1ZwXeBVkj0Tq56imGq7i+DNfujTWfRq+HNsPPd0xglViDgpANgvWgVUizg2PEkHWNmXna8ZJ1TPxCaAT0nYv2Gf+ajnzVsHoUMEwWKqyhlMj5BrtIuB3yqpEC3bXBDIoOiCGr381ZFhSMm/YZH1lB78EBQkW552hYtMi/AeOZYNj8uBkOLBJtsvfkHDIWMHDSxAp01xalbKHZosiSONnQoeEjK+5v+MuXohZWBYyJPCJhKPQ7/NZDyH9ZCHVVgvaYklMIud4TAt1ZTEDeAIgjiy/NYCHrJvIf08RwlTLWKL8MBP4Jd5G9sf1L4cXCEhXNPcApFAKF9gt0F9Oe1Yi2IFmHkYnNFkMW3Hp/E50lwuLeC2POj2bXD3cyszvTYE04Xj8oO0HKOWABXH79BVQ7lCdQCjGZrHiUHIg5IXCBmc4iIiUcA5eZU1MZRQvfSPS4ofvAvRsVg/yAAsUg/+IeMQIW0pcK6HZmEEwwPd8NWSdg60UxC6sEis8hH5Pho11TSA7r25IYvP6Vx1DmFxD4Aj7M4Pk0SH+ogG5PnHkqcC1CFix5Sv4hgisZNLdXDbH+apjaDG9Mji/AJNryCF7fG85Qxg6Ef8yeA9DCxL1iyfoCRow9vN41WadFkJUEw774RvJ+oC2OaBgNaNYIkeWICu87uIcM7y2WrOS5cBvOejdkoZi0g/CJqcuP0H011eW3oJKTIWVegMqeaUJkKebDh7vcYwaFiSVLCSU1ieLICqvcZk9B6sbtfGTdR65+mCBZA6AJvd1Utmm7FP7e13AIlclqh1jJAl/Ah0XcExV9zii0SUA7++Owv2RxZLXTNi/dqYQDdBstSBb0Bpbnja9+RekImYUr0CnUV/hZjzMbWRUoK+WeqClQaJ+AdpMXoUpTG1FkNTeR3o3OQYOFuCp/ZBT9oDOczhWUwwDwOaq4YoFmyoZRfiTuPs/DyEJFRE0Jd0AAO9wqoN6HrlB0xTYgxr82lyzM1/8hRFY8W9p3Fr7MHEMpz1vOYbNA0zGyCoYLFyiwep5SqEKDFqHkcx65qf3flqw2yCU5C+Q7neEPbaaU92pC2TOkIbk3s9BK/YGlXJcyESPLDZYtJHkDBOt5c1cLTWYEIv9WKreYbb+3rmfp0cd+puLVJQRl5F37GNEPM92vphIWHvllzRJmHwt641GsFVYIDWcdMwEtrMtTBW0qVksby5282XZ5a7I88yRiYkMik9krFJo4bSTEZmAnYN0FyFqICo+W7JtsK+otVpArEIWV/xfNfgdkgTtYIHZYVKbDiY8p4QWRH/fkJwvMxy4JsFwxkSErKakeI0yW2zyBsxeTyQrApqOHmrOuIl0kTJbzbiJw/QV7UW3BZ7OIsZ8i2aeMtaV9jc2jj5jUddst/GjXm34dLvBojItpZIGL+PfumkY/YJ6dbjigj7IUkSNMIgnAD4XzGqhl7rJrFLJkfvhB+FZKHVqxrRa/eiTu1uSvFHWOO8TlGltUbY+q9zrNl5pIVnk95YrPl2uyjdUlaZBX8Bf1koesZzpsGEw6tQeUV+3qLhj9htto5UQNtUxRRrkV4v7a4kOj6z0yCQ9kz40Td3wfMZF2S+L3K/d16bq6Q5+/b5im5yZuQwBqaPF1p6FWKw/7KBtcJ/2UZ6xVjR8shixDABZxkPru1K6Tel16SM7rP9ZRI3gSI6lnRc5nr/j+VpNe8Zx2E65O7MUQT967TobLbKaRhdtRNktUQKlc8aIPadJnDREhacdy4s4JP6noazRRvIOfNJksoLjCN8AgImmMRBO2z4EOPTabOWR/+afEkwWKhI3haTkQjyy+CkyG6WSBwB0a7gFuRQJQhx7XMPW7h1pDDQb9zSbxZIGQW/ySGtfmXYk/cJm7r7QWIAuASu47ZUMigAc6OBnbmaneQnf65drJ+8WTBY7yVuPyzoNmwtGnP1dnW1qELBBp95RjgGtuIF3CWy53xILLiY1Zia36qmiyQIprN04bsDUONB8J9zj4H0Ehqz8PWZpGsjaxX+0G+ZP6sg6QKVOhY2t3Vh++BKXPNsaryKv92B3o5fksBZfvz7BeWNbMuwneDglp+wfRe7Op1pMnBx5wZpYyLlgE3qFeaI+EF0L70K5lSwPuhjKsrfukVKx4LHnF6pesMRu1HZYfd85oZ0NfKO9XtlloyEb/pAW/Bcv60//FoJtdqhy8NaRePqcndByywsZS49S3W+jx+RVZjb255CyzMsC1jvEfL7LKZ41t8VZlhhrZZtfG52UOzP/20ObvuFy8dHAPJ42lzdP6jgNHBfckPqrygVUTlnEcXya6QomX2NrO3V0x/2Bot75Ed86Dl/b+URknI3xvlVKn82bWUDzWvpxX3OWRM/Fx54Z0/PpE4mJgKsKzV9mvKS2tShp3wAO8E2jLp+2Zkhg7M3+VtbQFurM9MG5PVWJs4e7yXBFjR78pjC2terzTWgbM+L+CYS0ZC3G5Y7RNE2zY7eFyuVwlJ20N/EdDmZtc7tjaOJrSdPU/hsyEp+oMhbMxSd5FdZVeJDmdlSR/G0N85myJyfXcKJ96mPAj2j09QYFu/Ur7VrZjZOd9DbW11Rnkf9mN9yXjA+vNVsH/rMqoIclKTSOjHYvYksPLHjquafDQKkeEgwXq8cB+Q3BplqKVbb4k8uvBYt8tswhvlbhheTiQ3vh5FWmeyVBEPif5SOPymVk3DIz0trUdsTkKBCk9gX2RW6uzVIqSv9RrgTz4ZLwnANm+ys1tQXTRSHRGmuOtNByD+qvzAUGWh1vIMe+2KoKsbRW7B5S1NrKmvFyT0StVXXh/PABROSnKm2DAdX/iPQEZcAxOs75H3rVwHJGjIsmydVMeqZkz28cTvNns0LCxtZEVqw5Kz/jZPvvSeKByKAH2l6SrD3mBwErd4ZoRqojrs8AIJbEPo+NJk0aS5RuwIOejtJ0gX12matPqyPoZRKp1uZGEzfbPqLH30S+0LloCFHuCE11DcqdlBNv7OJQB2fp0FyNZqWBavDoO5BdpW5/NKrVyBBaRIDpjNYipS1OmKZeAqhkj5TLpONcAxco0nTLEYSbYeWi8QioDhb62bg4biT2ZWADyK2ZLW1usrVjuaggt/a0KFidHSWWym9d7SYN3qEP0M2Jl4+I9ZTJpjD7bR71ZqX8IZuZ4qBoKiThMvY0IHXw3F0pbGVtr5xj+qXDxlvLuPuTtl143LoCUmBy9fkkECAgmPeGA0jhdeogy5yGwmKJyKSWrPUvWgix9mr4w0Jz4/A/gPwIMAPwpG5QV/gbUAAAAAElFTkSuQmCC');
        background-position: top center;
        background-repeat: no-repeat;
        background-size: 200px;
      }
      #cta-modal-content {
        padding-top: 40px;
      }
    }
    
    
      
    </style>
    <script>
    var which = '';
    </script>
    <div aria-hidden="true" class="modal-backdrop" id="cta-backdrop" style="display: none;"></div>
    <div class="modal" id="cta-modal" style="display: none;">
    <div class="modal-body">
    <button aria-hidden="true" class="close modal-close cta-close" data-dismiss="modal" id="cta-close" type="button">X</button>
    <div id="cta-modal-content"></div>
    </div><!--/.modal-body-->
    </div><!--/.modal-->
    <style>#print-only-logo {display: none;}</style>
    <img alt="" height="129" id="print-only-logo" src="http://media.chicagomag.com/img/chicagomag-logo-black-with-magazine.svg" width="573"/>
    <!-- Temporary peeking refer — use sparingly, for last-minute promos sitewide. Also removes red "punch" from #global-nav-subscribe Subscribe button, so that this call to action is more defined. -->
    <!-- 
    <style>
    a#peek {
      box-sizing: border-box;
      display: block;
      background: red;
      height: 0;
    
      color: red;
      text-align: center;
      font-weight: 400;
    }
    
    a#peek:hover {
      color: white !important;
      cursor: pointer;
      cursor: hand;
      background: #dd0000;
    }
    
    a#peek.show {
      height: 48px;
      padding: 10px 5px;
      border-bottom: 8px solid black;
      color: white !important;
    }
    
    #global-nav-subscribe {
      background    : none;
      border-radius : 0 !important;
      color         : #aaa !important;
      display       : initial !important;
      padding       : initial !important;
    }
    
    ul.member-nav li a#global-nav-subscribe:hover {
      border-bottom: 1px solid #ff0000;
      background: initial !important;
    }
    
    @media (max-width: 767px) {
      a#peek {
        display: none;
      }
    }
    
    </style>
     -->
    <!-- 
    <a id="peek" href="/Chicago-Magazine/Promotion/Secret-Supper/Purchase-Tickets/" onclick="ga('send', 'event', 'Global nav', 'Top peeking refer');">
      Last call for Secret Supper tickets! Click here for more info.
    </a>
    
     -->
    <!-- slide down temporary peeking refer -->
    <!-- 
    <script>
    
    jQuery( document ).ready(function() {
      if (document.documentElement.clientWidth > 600)  {
        jQuery("#peek").addClass("show");
      } else {
        jQuery("#peek").hide();
      }
    });
    
    </script>
     -->
    <header class="global">
    <nav>
    <!--============================================
        =            Top part of global nav            =
        =============================================-->
    <div class="top3">
    <div class="cell one">
    <a class="search-open" href="javascript:;"><i class="icon-search"></i></a>
    <div class="search">
    <gcse:searchbox-only></gcse:searchbox-only>
    </div>
    </div>
    <div class="cell two">
    <h1 class="din land">
    <a href="/dining-drinking">Dining<span class="new-amp">&amp;</span>Drinking</a>
    </h1>
    <h1 class="dg land">
    <a href="/Chicago-Magazine/Dining/Dining-Guide/">Chicago’s Best Restaurants</a>
    </h1>
    <h1 class="art land">
    <a href="/arts-culture">Arts<span class="new-amp">&amp;</span>Culture</a>
    </h1>
    <h1 class="pcl land">
    <!-- <a href="/city-life">P<span style="display: inline-block; margin-right: 0px;">o</span>litics<i class="amp"><span>&amp;</span></i>City<span style="display: inline-block; margin-right: -2px;">&nbsp; </span>Life<span style="display: inline-block; width: 12px;"></span></a> -->
    <a href="/city-life">Politics<span class="new-amp">&amp;</span>City Life</a>
    </h1>
    <h1 class="ren land">
    <a href="/real-estate">Real Estate</a>
    </h1>
    <h1 class="sty land">
    <a href="/style-shopping">Style<span class="new-amp">&amp;</span>Shopping</a>
    </h1>
    <h1 class="hg land">
    <a href="/Chicago-Home">Home Design</a>
    </h1>
    <h1 class="hel land">
    <a href="/Health-Wellness">Health<span class="new-amp">&amp;</span>Wellness</a>
    </h1>
    <h1 class="trv land">
    <a href="/travel">Travel</a>
    </h1>
    <h1 class="vid land">
    <a href="/video">Video</a>
    </h1>
    <h1 class="boc land">
    <a href="http://www.chicagomag.com/Chicago-Magazine/August-2018/Best-of-Chicago/">Best of Chicago</a>
    </h1>
    <h1 class="mkt land">
    <a href="/Chicago-Magazine/Classifieds/">Marketplace<span class="new-amp">&amp;</span>Classifieds</a>
    </h1>
    <h1>
    <a class="logo background-logo-svg" href="/" onclick="ga('send', 'event', 'Global nav', 'Home from main logo');">Chicago</a>
    </h1>
    </div>
    <div class="cell three">
    <ul class="member-nav">
    <li><a href="/subscribe" id="global-nav-subscribe" onclick="ga('send', 'event', 'Global nav', 'Subscribe');">Subscribe</a>
    <span class="red">/</span>
    <a href="/customerservice" onclick="ga('send', 'event', 'Global nav', 'Customer Service');">Customer Service</a></li>
    <li><a href="/newsletters" onclick="ga('send', 'event', 'Global nav', 'Newsletters');">Newsletters</a> </li>
    </ul>
    </div>
    </div>
    <!--====  End of Top part of global nav  ====-->
    <!--=====================================
        =            Global nav menus           =
        ======================================-->
    <ul id="page-nav">
    <li style="white-space: nowrap;"><a class="pcl" href="/city-life" onclick="ga('send', 'event', 'Global nav', 'Politics and city life');">Politics <span class="new-amp">&amp;</span><br/> City Life</a>
    <ul class="sub">
    <li><a href="/Chicago-Magazine/September-2018/Best-Public-Schools/" onclick="ga('send', 'event', 'Global nav', 'Best Public Schools');">Best Public Schools</a>
    <li><a href="/Chicago-Magazine/September-2017/Best-Private-Schools/" onclick="ga('send', 'event', 'Global nav', 'Best Private Schools');">Best Private Schools</a>
    <li><a href="/Chicago-Magazine/June-2017/Whos-Got-Next/" onclick="ga('send', 'event', 'Global nav', 'Power 50');">Emerging Power List</a>
    <!-- <li><a href="/topdocs" onclick="ga('send', 'event', 'Global nav', 'Top Docs');">Top Docs</a></li> -->
    <!-- <li><a href="/Chicago-Magazine/January-2017/Top-Cancer-Doctors/" onclick="ga('send', 'event', 'Global nav', 'Top Cancer Docs');">Top Cancer Docs</a></li> -->
    <li><a href="/topic/?archive=crime-law" onclick="ga('send', 'event', 'Global nav', 'Crime and Law');">Crime &amp; Law</a></li>
    <li><a href="/topic/?archive=politics" onclick="ga('send', 'event', 'Global nav', 'Politics');">Politics</a></li>
    <li><a href="/topic/?archive=the-urbanist" onclick="ga('send', 'event', 'Global nav', 'The Urbanist');">The Urbanist</a></li>
    <li><a href="/Chicago-Magazine/Felsenthal-Files/" onclick="ga('send', 'event', 'Global nav', 'Felsenthal');" style="font-size: 13px; letter-spacing: -0.1px;">Articles by Carol Felsenthal</a></li>
    <!-- <li><a href="/topic/?archive=whet-moser" onclick="ga('send', 'event', 'Global nav', 'Whet');">Articles by Whet Moser</a></li> -->
    </li></li></li></ul>
    </li>
    <!-- Dining & Drinking -->
    <li><a class="din" href="/dining-drinking" onclick="ga('send', 'event', 'Global nav', 'Dining');">Dining <span class="new-amp">&amp;</span><br/> Drinking</a>
    <ul class="sub">
    <li><a href="http://www.chicagomag.com/secretsupper" onclick="ga('send', 'event', 'Global nav', 'Secret Supper');">Secret Supper</a></li>
    <li><a href="/topic/?archive=restaurant-reviews" onclick="ga('send', 'event', 'Global nav', 'Restaurant reviews');">Restaurant Reviews</a></li>
    <li><a href="/hotlist" onclick="ga('send', 'event', 'Global nav', 'Hot list');">The Hot List</a></li>
    <li><a href="/brunch" onclick="ga('send', 'event', 'Global nav', 'Brunch');">Brunch</a></li>
    <li><a href="/topic/?archive=bars-drinking" onclick="ga('send', 'event', 'Global nav', 'Bars and drinking');">Bars &amp; Drinking</a></li>
    <li><a href="/Chicago-Magazine/February-2017/Best-Bars/" onclick="ga('send', 'event', 'Global nav', 'Best Bars');">Best Bars</a>
    <li><a href="/Chicago-Magazine/April-2018/Best-New-Restaurants/" onclick="ga('send', 'event', 'Global nav', 'Best New Restaurants');">Best New Restaurants</a></li>
    <li><a href="/Chicago-Magazine/July-2010/Photos-Best-Pizzas-in-Chicago/" onclick="ga('send', 'event', 'Global nav', 'Best Pizza');">Best Pizzas</a></li>
    <li><a href="/steakhouses" onclick="ga('send', 'event', 'Global nav', 'Best steakhouses');">Best Steakhouses</a></li>
    <li><a href="/Chicago-Magazine/November-2014/Cheap-Eats-Chicago/" onclick="ga('send', 'event', 'Global nav', 'Best cheap eats');">Best Cheap Eats</a></li>
    <li><a href="/takeout" onclick="ga('send', 'event', 'Global nav', 'Best takeout');">Best Takeout</a></li>
    <li><a href="/dining-drinking/July-2018/The-50-Best-Restaurants-in-Chicago/" onclick="ga('send', 'event', 'Global nav', '50 Best Restaurants');">Chicago’s Best Restaurants</a>
    <!-- <li><a href="/video/Inside-Chicagos-Best-Restaurants/" onclick="ga('send', 'event', 'Global nav', 'Dining video series');">Video: Inside Chicago’s Best Restaurants</a> -->
    <!-- <li><a href="/video/Inside-Chicagos-Best-Bars/" onclick="ga('send', 'event', 'Global nav', 'Dining video series bars');">Video: Inside Chicago’s Best Bars</a> -->
    <!-- <li><a href="http://www.chicagomag.com/video/In-the-Kitchen/" onclick="ga('send', 'event', 'Global nav', 'Dining video series In Season');">Video: In the Kitchen</a> -->
    <li class="nav-sub-search clearfix"><div class="margin-bottom-5">Search Chicago’s Best Restaurants:</div>
    <form action="/Chicago-Magazine/Dining/Dining-Guide/index.php?" method="get">
    <input name="search" type="text"/>
    <button onclick="ga('send', 'event', 'Global nav', 'Search Dining Guide');">Go</button>
    </form>
    </li>
    </li></li></ul>
    </li>
    <!-- Arts & Culture -->
    <li><a class="art" href="/arts-culture" onclick="ga('send', 'event', 'Global nav', 'Arts');">Arts <span class="new-amp">&amp;</span><br> Culture</br></a>
    <ul class="sub">
    <li><a href="/go" onclick="ga('send', 'event', 'Global nav', 'Go Listings');">The Go Listings</a></li>
    <li><a href="/topic/?archive=architecture" onclick="ga('send', 'event', 'Global nav', 'Architecture');">Architecture</a></li>
    <li><a href="/topic/?archive=art" onclick="ga('send', 'event', 'Global nav', 'Art');">Art</a></li>
    <li><a href="/topic/?archive=books" onclick="ga('send', 'event', 'Global nav', 'Books');">Books &amp; Readings</a></li>
    <li><a href="/topic/?archive=comedy" onclick="ga('send', 'event', 'Global nav', 'Comedy');">Comedy</a></li>
    <li><a href="/topic/?archive=dance" onclick="ga('send', 'event', 'Global nav', 'Dance');">Dance</a></li>
    <li><a href="/topic/?archive=lollapalooza" onclick="ga('send', 'event', 'Global nav', 'Lollapalooza');">Lollapalooza</a></li>
    <li><a href="/topic/?archive=pitchfork" onclick="ga('send', 'event', 'Global nav', 'Pitchfork');">Pitchfork </a></li>
    <li><a href="/topic/?archive=museums" onclick="ga('send', 'event', 'Global nav', 'Museums');">Museums</a></li>
    <li><a href="/topic/?archive=music" onclick="ga('send', 'event', 'Global nav', 'Music');">Music</a></li>
    <!-- <li><a href="/summer-music-festivals/"  onclick="ga('send', 'event', 'Global nav', 'Summer music fests');">Summer Music Festivals</a></li> -->
    <li><a href="/topic/?archive=theater" onclick="ga('send', 'event', 'Global nav', 'Theater');">Theater</a></li>
    <!-- <li><a href="/video/Inside-Chicagos-Best-Theater/" onclick="ga('send', 'event', 'Global nav', 'Video Theater');">Video: Inside Chicago’s Best Theater</a> -->
    <!-- <li><a href="/video/In-the-Studio/" onclick="ga('send', 'event', 'Global nav', 'Video Inside the Studio');">Video: Inside the Studio</a></li> -->
    </ul>
    </li>
    <!-- Real Estate -->
    <li><a class="ren" href="/real-estate" onclick="ga('send', 'event', 'Global nav', 'Real Estate + Neighborhoods');">Home <span class="new-amp">&amp;</span><br/> Real Estate</a>
    <ul class="sub">
    <li><a href="/Chicago-Magazine/April-2017/Where-to-Buy-Now-2017/" onclick="ga('send', 'event', 'Global nav', 'Best neighborhoods');">Where to Buy Now</a>
    <li><a href="/Field-Guides" onclick="ga('send', 'event', 'Global nav', 'Field Guides');">Neighborhood Field Guides</a></li>
    <li><a href="/real-estate" onclick="ga('send', 'event', 'Global nav', 'Real Estate News');">Real Estate News</a></li>
    <li><a href="/Chicago-Home" onclick="ga('send', 'event', 'Global nav', 'Home + Garden');">Home Design</a></li>
    </li></ul>
    </li>
    <!-- Style & Shopping -->
    <li><a class="sty" href="/style-shopping" onclick="ga('send', 'event', 'Global nav', 'Style + Shopping');">Style <span class="new-amp">&amp;</span> Shopping</a>
    <ul class="sub">
    <li><a href="/topic/?archive=fashion" onclick="ga('send', 'event', 'Global nav', 'Fashion');">Fashion</a></li>
    <li><a href="/topic/?archive=sales-shopping" onclick="ga('send', 'event', 'Global nav', 'Sales + Shopping News (under S+S)');">Sales &amp; Shopping News</a></li>
    <li><a href="/topic/?archive=tastemakers" onclick="ga('send', 'event', 'Global nav', 'Tastemakers');">Taste Makers</a></li>
    </ul>
    </li>
    <!-- Health & Wellness  -->
    <li><a class="hg" href="/Health-Wellness" onclick="ga('send', 'event', 'Global nav', 'Health + Wellness');">Health <span class="new-amp">&amp;</span><br/> Wellness</a>
    <ul class="sub">
    <li><a href="/Chicago-Magazine/Promotion/Fit-Society/" onclick="ga('send', 'event', 'Global nav', 'Fit Society');">Fit Society</a></li>
    <li><a href="/Health-Wellness" onclick="ga('send', 'event', 'Global nav', 'Health + Wellness');">Health News</a></li>
    <li><a href="/topdocs" onclick="ga('send', 'event', 'Global nav', 'Top Docs');">Top Docs</a></li>
    </ul>
    </li>
    <!-- Video -->
    <li id="red-hack-item">
    <a href="https://www.youtube.com/user/chicagomag" onclick="ga('send', 'event', 'Global nav', 'Video');" target='_blank"'>
             <br/>
    <i id="red-hack" style="
            background-image: url(http://getbootstrap.com/2.3.2/assets/img/glyphicons-halflings.png); 
            display: inline-block;
            filter: brightness(0) invert(1);
            width: 18px;
            height: 10px;
            background-position: -335px -99px;
            "></i>Video</a>
    </li>
    <style>
          i#red-hack.hover {
            filter: sepia() saturate(10000%) hue-rotate(10deg) !important;
          }
          </style>
    <script>
          jQuery('#red-hack-item').hover(
            function() {
              jQuery(this).children("a").children("i#red-hack").addClass( "hover" );
            }, function() {
              jQuery(this).children("a").children("i#red-hack").removeClass( "hover" );
            }
          );
          </script>
    <!-- OLD VIDEO -->
    <!-- <li><a href="/video" class="tra" onclick="ga('send', 'event', 'Global nav', 'Video');">&nbsp;<br>Video</a>
            <ul class="sub">
              <li><a href="http://www.chicagomag.com/video/Chicagos-Great-Neighborhoods/" onclick="ga('send', 'event', 'Global nav', 'Chicago’s Great Neighborhoods');">Chicago’s Great Neighborhoods</a></li>
              <li><a href="http://www.chicagomag.com/video/Inside-Chicagos-Best-Bars/" onclick="ga('send', 'event', 'Global nav', 'Inside Chicago’s Best Bars');">Inside Chicago’s Best Bars</a></li>
              <li><a href="http://www.chicagomag.com/video/Inside-Chicagos-Best-Restaurants/" onclick="ga('send', 'event', 'Global nav', 'Inside Chicago’s Best Restaurants');">Inside Chicago’s Best Restaurants</a></li>
              <li><a href="http://www.chicagomag.com/video/Inside-Chicagos-Best-Theater/" onclick="ga('send', 'event', 'Global nav', 'Inside Chicago’s Best Theater');">Inside Chicago’s Best Theater</a></li>
              <li><a href="http://www.chicagomag.com/video/In-the-Studio" onclick="ga('send', 'event', 'Global nav', 'Inside the Studio');">Inside the Studio</a></li>
              <li><a href="http://www.chicagomag.com/video/In-the-Kitchen/" onclick="ga('send', 'event', 'Global nav', 'In Season');">In the Kitchen</a></li>
            </ul>
          </li> -->
    <!-- Visitors Guide -->
    <li><a class="tra" href="/Visitors-Guide/" onclick="ga('send', 'event', 'Global nav', 'Visitors Guide');">Visitor's <br/>Guide</a>
    <ul class="sub">
    <li><a href="/Visitors-Guide/" onclick="ga('send', 'event', 'Global nav', 'Visitors Guide');">Visitor’s Guide</a></li>
    <li><a href="/travel" onclick="ga('send', 'event', 'Global nav', 'Travel News');">Travel News &amp; Features</a></li>
    </ul>
    </li>
    <!-- Best of Chicago -->
    <li style="flex-grow: 0;">
    <a class="boc" href="/Chicago-Magazine/August-2018/Best-of-Chicago/" onclick="ga('send', 'event', 'Global nav', 'Best of Chicago');">Best of<br> Chicago</br></a>
    </li>
    </ul>
    </nav>
    <!--====  End of Global nav menus ====-->
    </header>
    <!-- Begin Content Wrap -->
    <a class="edit_from_site" content_id="leaderboard-1" href="#" media_id="83">Edit Module</a>
    <style type="text/css">
          @media (max-width:735px) {
        #div-gpt-ad-leaderboard-1 {
              display:none !important;
            }
       }
    </style>
    <div class="ad ban" id="div-gpt-ad-leaderboard-1">
    <script>
    if (document.documentElement.clientWidth > 735) {
    googletag.cmd.push(function() {
        googletag.display('div-gpt-ad-leaderboard-1');
    });
    }
      </script>
    </div>
    <a class="edit_from_site" content_id="mobile-leaderboard-1" href="#" media_id="83">Edit Module</a>
    <style type="text/css">
          @media (min-width:736px) {
        #div-gpt-ad-mobile-leaderboard-1 {
              display:none !important;
            }
       }
    </style>
    <div class="mobile-ban-wrapper mobile-ban-fixed">
    <div id="div-gpt-ad-mobile-leaderboard-1" style="height: 50px; width:320px;margin: 0 auto;">
    <script>
    if (document.documentElement.clientWidth < 736) {
    googletag.cmd.push(function() {
        googletag.display('div-gpt-ad-mobile-leaderboard-1');
    })
    }
      </script>
    </div>
    </div>
    <a class="edit_from_site" content_id="mobile-leaderboard-2" href="#" media_id="83">Edit Module</a>
    <style type="text/css">
          @media (min-width:736px) {
        #div-gpt-ad-mobile-leaderboard-2 {
              display:none !important;
            }
       }
    </style>
    <div class="mobile-ban-wrapper" id="mobile-leaderboard-2">
    <div id="div-gpt-ad-mobile-leaderboard-2" style="width: 320px; height: 50px; margin: 0 auto;">
    <script>
    if (document.documentElement.clientWidth < 736) {
    googletag.cmd.push(function() {
        googletag.display('div-gpt-ad-mobile-leaderboard-2');
    })
    }
      </script>
    </div>
    </div>
    <div id="content-wrap">
    <!--- special treatment for stacked galleries in regular articles, removes first couple leaderboard ads in between -->
    <style>
    div.content.post div.super-gallery-stacked-photo-outer ~ div.ad.ban {
      display: none;
    }
    </style>
    <!--- Begin Page -->
    <div class="long" id="page">
    <article class="container long" id="article-19493">
    <a class="edit_from_site" content_id="550" href="#" media_id="97">Edit Module</a>
    <div id="article-header-image-container">
    <header class=" clearfix">
    <!--- special toppers for repeat features -->
    <!--- special treatment for Urbanist -->
    <h1 class="headline ">The 50 Best Sandwiches in Chicago</h1>
    <h2 class="deck ">Our list of Chicago’s 50 best sandwiches, ranked in order of deliciousness</h2>
    <div class="article-meta clearfix" id="article-meta">
    <p class="timestamp">Published Oct. 9, 2012</p>
    <section class="clearfix" id="share-mini">
    <ul class="story-share-buttons story-share-buttons-mini list-no-bullets clearfix">
    <li class="fb"><a href="https://www.facebook.com/sharer/sharer.php?u=http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-Chicago/?utm_source=facebook.com%26utm_medium=referral%26utm_campaign=Facebook%20above%20story%2019493%20Dec%2029%202018%2000:44" onclick="ga('send', 'event', 'Sharing', 'Facebook share from top of article','The 50 Best Sandwiches in Chicago');
      return socialPopup('https://www.facebook.com/sharer/sharer.php?u=http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-Chicago/%3Futm_source=facebook.com%26utm_medium=referral%26utm_campaign=Facebook%20above%20story%2019493%20Dec%2029%202018%2000:44');" target="_blank"><i class="icon-facebook"></i></a>
    </li>
    <li class="tw"><a href="https://twitter.com/share?text=The+50+Best+Sandwiches+in+Chicago&amp;url=http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-Chicago/" onclick="ga('send', 'event', 'Sharing', 'Tweet story from top of article','The 50 Best Sandwiches in Chicago');
        return tweetPopup('The+50+Best+Sandwiches+in+Chicago','/Chicago-Magazine/November-2012/Best-Sandwiches-Chicago/','19493');
      " target="_blank"><i class="icon-twitter"></i></a></li>
    <li class="eml"><a href="#" onclick="window.open('/core/pagetools.php?pageID=19493&amp;url=%2FChicago-Magazine%2FNovember-2012%2FBest-Sandwiches-Chicago%2Findex.php&amp;mode=email','emailThisPage','status=no,scrollbars=yes,resizable=yes,width=420,height=540'); ga('send', 'event', 'Sharing', 'Email story (top of story)','The 50 Best Sandwiches in Chicago'); return false;" url="email this page"><i class="icon-envelope"></i></a></li>
    <li class="prnt"><a href="#" onclick="window.open('/core/pagetools.php?pageid=19493&amp;url=%2FChicago-Magazine%2FNovember-2012%2FBest-Sandwiches-Chicago%2Findex.php&amp;mode=print','printThisPage','status=no,toolbar=yes,menubar=yes,scrollbars=yes,resizable=yes,width=570'); ga('send', 'event', 'Sharing', 'Print story (top of story)' ,'The 50 Best Sandwiches in Chicago'); return false;" target="_blank" url="print this page"><i class="icon-print"></i></a></li>
    <li class="cmmnt"><a href="javascript:void(0);" onclick="jumpto('#comment',150);"><i class="icon-comment"></i></a></li>
    <li class="comment-count"><a data-disqus-identifier="19493" href="javascript:void(0);" id="comment-count" onclick="jumpto('#comment',150);"></a></li>
    </ul>
    </section> </div> </header>
    <section class="caption main">
    <figure>
    <div class="position-relative image-launch-wrapper">
    <picture>
    <source media="(max-width: 320px)" srcset="/images/cache/cache_6/cache_8/cache_f/sandwich-old-oak-032fff86.jpeg?ver=1483657051&amp;aspectratio=1.4634146341463, /images/cache/cache_e/cache_3/cache_0/sandwich-old-oak-5138903e.jpeg?ver=1483657051&amp;aspectratio=1.4634146341463 2x"/>
    <source media="(max-width: 470px)" srcset="/images/cache/cache_b/cache_2/cache_1/sandwich-old-oak-3cfe412b.jpeg?ver=1483657051&amp;aspectratio=1.4754098360656, /images/cache/cache_6/cache_6/cache_a/sandwich-old-oak-198f4a66.jpeg?ver=1483657051&amp;aspectratio=1.4754098360656 2x"/>
    <source srcset="/images/cache/cache_5/cache_a/cache_7/sandwich-old-oak-752cb7a5.jpeg?ver=1482330047&amp;aspectratio=1.4761904761905, /images/cache/cache_1/cache_3/cache_1/sandwich-old-oak-2fa7d131.jpeg?ver=1483649262&amp;aspectratio=1.4761904761905 2x"/>
    <img alt="BLT at Old Oak Tap, the No. 1 sandwich in Chicago." height="420" id="article-lead-image" src="/images/cache/cache_5/cache_a/cache_7/sandwich-old-oak-752cb7a5.jpeg?ver=1482330047&amp;aspectratio=1.4761904761905" width="620">
    </img></picture>
    <a class="launch bottom-right" href="/Chicago-Magazine/November-2012/Best-Sandwiches-Chicago/index.php?mode=popup&amp;cp=1&amp;view=slideshow&amp;play=0" id="gallery-link" style="display: none;">Launch gallery</a>
    </div>
    <figcaption> <span class="caption">BLT at Old Oak Tap, the No. 1 sandwich in Chicago.</span>              <span class="photo-credit">Photograph: Anna Knott; Food Stylist: Lisa Kuehl</span>
    </figcaption>
    </figure>
    </section>
    </div><!-- /#article-header-image-container -->
    <div class="content-and-sidebar">
    <div class="content post ">
    <div class="fb-like fb-like-top" data-href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-Chicago/" data-layout="box_count" data-send="false" data-share="true" data-show-faces="false" data-width="50"></div>
    <p><span class="dropcap">F</span>or generations, sandwiches were the ultimate guilty pleasure of subcultures that had no patience for guilt: hungry bachelors, school kids, working stiffs, old men in delis. To fridge-foraging rubes like Dagwood, quality wasn’t half as important as quantity. The sandwich was one of the only snacks you were allowed to pile as high as you wanted with anything you desired and cram into your face with both hands—a meal so inelegant and blithely proud of its inelegance that it came in six-foot segments for parties. And we ate it. Standing up.</p>
    <section class="related-content pull-right">
    <h3>Related Content</h3>
    <ul>
    <li><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-
    Chicago-The-Best-Sliders-in-Town">The Best Sliders in Town</a></li>
    <li><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-
    Chicago-Where-to-Get-Your-Fix-Around-the-Clock">Where to Get Your Fix Around the Clock</a></li>
    </ul>
    </section>
    <p>Now we’ve got French dips made with shaved prime rib, po’ boys with organic shrimp, and grilled cheese with fancy pimiento cheese. Hell, you can get a buttered ciabatta layered with local eggs, house-cured speck, and fontina for breakfast at Balsan if the idea of spending $19 for a ham and egg sandwich does not scandalize you. What in the name of John Montagu is going on here?</p>
    <p>The sandwich pendulum has always swung erratically from the treat of the nobility to the fuel of the proletariat. But what we’re witnessing now is the sharpest swerve yet toward the land of fine dining—a shift that overlaps, not coincidentally, with the great democratization of Chicago’s restaurants. Ground zero for the boom is Publican Quality Meats, where Paul Kahan regards sandwiches as serious dishes. So does Acadia’s Ryan McCaskey, who makes a mean lobster roll, and Rick Bayless, who offers up a vegetarian stunner at Xoco.</p>
    <p>To guide you through the bustling sandscape, we fanned out across the city and suburbs, hitting spots high and low in search of anything delicious between two slices of bread. For the purposes of this story, we defined “sandwich” in the strictest of terms: no wraps, dumplings, or open-faced pretenders. Hamburgers and hot dogs didn’t qualify. Italian beef sandwiches did, but not one made this list. (Face facts: Chicago’s spongy grease bomb is not among the better contributions to the genre.) We gave points to the well crafted, the fresh, and the robust, anchored by bread with enough distinct character to bolster the proceedings without overshadowing or interfering.</p>
    <p>The result: our list of Chicago’s 50 best sandwiches, ranked in order of deliciousness. Some are ingenious, such as Scofflaw’s layered masterpiece of braised brisket, pork belly, and pork loin. Others are blunt and glorious classics, done simply and done right. (Meatball sub from Bari, take a bow.)</p>
    <p>In our research, we learned that the sandwich is a wily chameleon, soaking up and synthesizing every trend, be it the resurgence of house-cured charcuterie or the sudden ubiquity of arugula. We learned to ask for extra napkins ahead of time. And we learned, above all, that quality and quantity can intersect in restaurants, and there’s no shame in that. Only joy.</p>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">1</div>
    <div class="sammyListing"><a href="/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Old-Oak-Tap-BLT/"><b>BLT</b><br>
    Old Oak Tap<br>
    <em>Read more</em> </br></br></a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">2</div>
    <div class="sammyListing"><a href="/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Au-Cheval-Fried-Bologna/"><b>Fried Bologna</b><br/>
    Au Cheval<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">3</div>
    <div class="sammyListing"><a href="/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Xoco-Woodland-Mushroom/"><b>Woodland Mushroom</b><br/>
    Xoco<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">4</div>
    <div class="sammyListing"><a href="/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Als-Deli-Roast-Beef/"><b>Roast Beef</b><br/>
    Al’s Deli<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">5</div>
    <div class="sammyListing"><a href="/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Publican-Quality-Meats-PB-L/"><b>PB&amp;L</b><br/>
    Publican Quality Meats<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">6</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Hendrickx-Belgian-Bread-Crafter-Belgian-Chicken-Curry-Salad/"><b>Belgian Chicken Curry Salad</b><br/>
    Hendrickx Belgian Bread Crafter<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">7</div>
    <div class="sammyListing"><a href="/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Acadia-Lobster-Roll/"><b>Lobster Roll</b><br/>
    Acadia<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">8</div>
    <div class="sammyListing"><a href="/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Birchwood-Kitchen-Smoked-Salmon-Salad/"><b>Smoked Salmon Salad</b><br/>
    Birchwood Kitchen<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">9</div>
    <div class="sammyListing"><a href="/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Cemitas-Puebla-Atomica-Cemitas/"><b>Atomica Cemitas</b><br/>
    Cemitas Puebla<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">10</div>
    <div class="sammyListing"><a href="/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Nana-Grilled-Laughing-Bird-Shrimp-and-Fried-Oyster-Po-Boy/"><b>Grilled Laughing Bird Shrimp and Fried Po’ Boy</b><br/>
    Nana<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">11</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Lula-Cafe-Ham-and-Raclette-Panino/"><b>Ham and Raclette Panino</b><br/>
    Lula Cafe<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">12</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Ricobenes-Breaded-Steak/"><b>Breaded Steak</b><br/>
    Ricobene’s<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">13</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Frog-n-Snail-The-Hawkeye/"><b>The Hawkeye</b><br/>
    Frog n Snail<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">14</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Crosbys-Kitchen-Chicken-Dip/"><b>Chicken Dip</b><br/>
    Crosby’s Kitchen<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">15</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Longman-and-Eagle-Wild-Boar-Sloppy-Joe/"><b>Wild Boar Sloppy Joe</b><br/>
    Longman &amp; Eagle<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">16</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Bari-Meatball-Sub/"><b>Meatball Sub</b><br/>
    Bari<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">17</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Mannys-Corned-Beef/"><b>Corned Beef</b><br/>
    Manny’s<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">18</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Eggys-Turkey-Club/"><b>Turkey Club</b><br/>
    Eggy’s<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">19</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Old-Jerusalem-Falafel/"><b>Falafel</b><br/>
    Old Jerusalem<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">20</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Mindys-HotChocolate-Crab-Cake/"><b>Crab Cake</b><br/>
    Mindy’s HotChocolate<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">21</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Olgas-Delicatessen-Chicken-Schnitzel/"><b>Chicken Schnitzel</b><br/>
    Olga’s Delicatessen<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">22</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Dawali-Mediterranean-Kitchen-Shawarma/"><b>Shawarma</b><br/>
    Dawali Mediterranean Kitchen<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">23</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Big-Jones-Toasted-Pimiento-Cheese/"><b>Toasted Pimiento Cheese</b><br/>
    Big Jones<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">24</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-La-Pane-Vegetarian-Panino/"><b>Vegetarian Panino</b><br/>
    La Pane<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">25</div>
    <div class="sammyListing"><a href="/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Pastoral-Cali-Chevre/"><b>Cali Chèvre</b><br/>
    Pastoral<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">26</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Maxs-Deli-Pastrami/"><b>Pastrami</b><br/>
    Max’s Deli<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">27</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Luckys-Sandwich-Co-The-Fredo/"><b>The Fredo</b><br/>
    Lucky’s Sandwich Co.<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">28</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-City-Provisions-Smoked-Ham/"><b>Smoked Ham</b><br/>
    City Provisions<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">29</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Papas-Cache-Sabroso-Jibarito/"><b>Jibarito</b><br/>
    Papa’s Cache Sabroso<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">30</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Bavettes-Bar-and-Boeuf-Shaved-Prime-Rib/"><b>Shaved Prime Rib</b><br/>
    Bavette’s Bar &amp; Boeuf<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">31</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Hannahs-Bretzel-Serrano-Ham-and-Manchego-Cheese/"><b>Serrano Ham and Manchego Cheese</b><br/>
    Hannah’s Bretzel<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">32</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-La-Fournette-Tuna-Salad/"><b>Tuna Salad</b><br/>
    La Fournette<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">33</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Paramount-Room-Paramount-Reuben/"><b>Paramount Reuben</b><br/>
    Paramount Room<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">34</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Melt-Sandwich-Shoppe-The-Istanbul/"><b>The Istanbul</b><br/>
    Melt Sandwich Shoppe<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">35</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Floriole-Cafe-and-Bakery-BAD/"><b>B.A.D.</b><br/>
    Floriole Cafe &amp; Bakery<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">36</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-First-Slice-Pie-Cafe-Duck-Confit-and-Mozzarella/"><b>Duck Confit and Mozzarella</b><br/>
    First Slice Pie Café<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">37</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Troquet-Croque-Monsieur/"><b>Croque Monsieur</b><br/>
    Troquet<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">38</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Grahamwich-Green-Garbanzo/"><b>Green Garbanzo</b><br/>
    Grahamwich<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">39</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Saigon-Sisters-The-Hen-House/"><b>The Hen House</b><br/>
    Saigon Sisters<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">40</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Rosalias-Deli-Tuscan-Chicken/"><b>Tuscan Chicken</b><br/>
    Rosalia’s Deli<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">41</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Z-and-H-MarketCafe-The-Marty/"><b>The Marty </b><br/>
    Z&amp;H MarketCafe<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">42</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Market-House-on-the-Square-Whitefish/"><b>Whitefish</b><br/>
    Market House on the Square<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">43</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Elaines-Coffee-Call-Oat-Bread-Pecan-Butter-and-Fruit-Jam/"><b>Oat Bread, Pecan Butter, and Fruit Jam</b><br/>
    Elaine’s Coffee Call<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">44</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Marion-Street-Cheese-Market-Cauliflower-Melt/"><b>Cauliflower Melt</b><br/>
    Marion Street Cheese Market<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">45</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Cafecito-Cubano/"><b>Cubana</b><br/>
    Cafecito<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">46</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Chickpea-Kufta/"><b>Kufta</b><br/>
    Chickpea<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">47</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-The-Goddess-and-Grocer-Debbies-Egg-Salad/"><b>Debbie’s Egg Salad</b><br/>
    The Goddess and Grocer<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">48</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Zenwich-Beef-Curry/"><b>Beef Curry</b><br/>
    Zenwich<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative;">
    <div class="sammyRank">49</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Toni-Patisserie-Le-Vegetarien/"><b>Le Végétarien</b><br/>
    Toni Patisserie<br/>
    <em>Read more</em> </a></div>
    </div>
    <div class="sammy" style="position: relative; border-bottom: 0">
    <div class="sammyRank">50</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Phoebes-Bakery-The-Gatsby/"><b>The Gatsby</b><br/>
    Phoebe’s Bakery<br/>
    <em>Read more</em> </a></div>
    </div>
    <!-- /#sandwichFront -->
    <footer>Edited by Carly Boers, Penny Pollack, and Jeff Ruby; Contributors: Cassie Walker Burke, Elly Fishman, Peter Gianopulos, Noah Isackson, Maryanne Johnson, Esther Kang, Megan Lovejoy, Graham Meyer, Matt Schur, Lena Singer, Emmet Sullivan, Jennifer Tanaka, Joanne Trestrail</footer>
    <script type="text/javascript">
    var feedicon=document.getElementById('__atomfeed__'); if(feedicon) feedicon.style.display='inline';</script>
    <footer>This article appears in the <a href="/Chicago-Magazine/November-2012/">November 2012</a> issue of <em> Chicago</em> magazine. <a href="http://www.chicagomag.com/subscribe" onclick="ga('send',  'event','Refer clicks', 'Subscribe, article footer');">Subscribe to <em>Chicago</em> magazine</a>.</footer>
    <div class="clearfix"></div>
    <script>
      var newsletterChoice = "Dish";
    </script>
    <div class="margin-top-10 margin-bottom-40" id="footer-newsletter-subscribe">
    <div class="lazyload" data-url="/lazyloads/furniture/dish-subscribe-footer.html?2" id="lazy-newsletter-footer"> </div>
    </div>
    <script>
      jQuery(function(){
        // If the user has already subscribed to this newsletter, hide the footer form.
        // In its stead, reveal the related articles and popular stories. (Super articles only.)
        if (getCookie('subscribe-Dish') == 'true') {
          jQuery('#footer-newsletter-subscribe').hide();
          // jQuery('.show-if-no-newsletter-cta').show();
        }
        // else {
        //   jQuery('.show-if-no-newsletter-cta').hide();
        // }
      })
    
    
    
    </script>
    <section class="clearfix" id="share">
    <h3>Share</h3>
    <ul class="story-share-buttons list-no-bullets clearfix">
    <li class="fb"><a href="https://www.facebook.com/sharer/sharer.php?u=http://www.chicagomag.com//Chicago-Magazine/November-2012/Best-Sandwiches-Chicago/%3Futm_source=facebook.com%26utm_medium=referral%26utm_campaign=Facebook%20below%20story%2019493%20Dec%2029%202018%2000:44" onclick="ga('send', 'event', 'Sharing', 'Facebook share from article','The 50 Best Sandwiches in Chicago');
        return socialPopup('https://www.facebook.com/sharer/sharer.php?u=http://www.chicagomag.com//Chicago-Magazine/November-2012/Best-Sandwiches-Chicago/%3Futm_source=facebook.com%26utm_medium=referral%26utm_campaign=Facebook%20below%20story%2019493%20Dec%2029%202018%2000:44');" target="_blank"><i class="icon-facebook"></i></a>
    </li>
    <li class="tw"><a href="https://twitter.com/share?text=The+50+Best+Sandwiches+in+Chicago&amp;url=http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-Chicago/" onclick="ga('send', 'event', 'Sharing', 'Tweet story from article','The 50 Best Sandwiches in Chicago');
        return tweetPopup('The+50+Best+Sandwiches+in+Chicago','/Chicago-Magazine/November-2012/Best-Sandwiches-Chicago/','19493');
        " target="_blank"><i class="icon-twitter"></i></a></li>
    <li class="eml"><a href="#" onclick="window.open('/core/pagetools.php?pageID=19493&amp;url=%2FChicago-Magazine%2FNovember-2012%2FBest-Sandwiches-Chicago%2Findex.php&amp;mode=email','emailThisPage','status=no,scrollbars=yes,resizable=yes,width=420,height=540'); ga('send', 'event', 'Sharing', 'Email story','The 50 Best Sandwiches in Chicago'); return false;" url="email this page"><i class="icon-envelope"></i></a></li>
    <li class="prnt"><a href="#" onclick="window.open('/core/pagetools.php?pageid=19493&amp;url=%2FChicago-Magazine%2FNovember-2012%2FBest-Sandwiches-Chicago%2Findex.php&amp;mode=print','printThisPage','status=no,toolbar=yes,menubar=yes,scrollbars=yes,resizable=yes,width=570'); ga('send', 'event', 'Sharing', 'Print story' ,'The 50 Best Sandwiches in Chicago'); return false;" target="_blank" url="print this page"><i class="icon-print"></i></a></li>
    <li class="cmmnt"><a href="javascript:void(0);" onclick="jumpto('#comment',150);"><i class="icon-comment"></i></a></li>
    </ul>
    </section>
    <a class="edit_from_site" content_id="ad-600-100" href="#" media_id="83">Edit Module</a>
    <style type="text/css">
          @media (max-width:599px) {
        #ad-600-100-outer {
              display:none !important;
            }
       }
    </style>
    <div class="ad ban-600" id="ad-600-100-outer">
    <h2>Advertisement</h2>
    <div id="div-gpt-ad-600-100"></div>
    <script>
    
    if (document.documentElement.clientWidth > 600) {
    googletag.cmd.push(function() {
        googletag.display('div-gpt-ad-600-100');
    });
    }
      </script>
    </div>
    <section id="related-articles">
    <a class="title" href="/dining-drinking">Other Dining &amp; Drinking stories</a>
    <div class="related-row clearfix">
    <div class="related related-1 ">
    <a class="related-thumb" href="/Chicago-Magazine/November-2018/How-to-Make-Holiday-Granola/" onclick="ga('send',  'event', 'Refer clicks', 'Related (photo), slot ' + 1, '/Chicago-Magazine/November-2018/How-to-Make-Holiday-Granola/');" style="background-image: url('/images/cache/cache_5/cache_e/cache_c/C201811-T-Holiday-Granola-eea4dce5.jpeg?ver=1545411225&amp;aspectratio=1.5'); background-size: cover;"> </a>
    <div class="content">
    <h2><a href="/Chicago-Magazine/November-2018/How-to-Make-Holiday-Granola/" onclick="ga('send',  'event', 'Refer clicks', 'Related (head), slot ' + 1, '/Chicago-Magazine/November-2018/How-to-Make-Holiday-Granola/');">How to Make Holiday Granola</a></h2>
    <p class="time">1 week ago</p>
    </div><!-- /.content -->
    </div><!-- /.related -->
    <div class="related related-2 related-nativo">
    <a class="related-thumb" href="/dining-drinking/December-2018/Swill-Inn/" onclick="ga('send',  'event', 'Refer clicks', 'Related (photo), slot ' + 2, '/dining-drinking/December-2018/Swill-Inn/');" style="background-image: url('/images/cache/cache_3/cache_1/cache_5/SWILL-5-9ed5a513.jpeg?ver=1545321612&amp;aspectratio=1.5'); background-size: cover;"> </a>
    <div class="content">
    <h2><a href="/dining-drinking/December-2018/Swill-Inn/" onclick="ga('send',  'event', 'Refer clicks', 'Related (head), slot ' + 2, '/dining-drinking/December-2018/Swill-Inn/');">There Are No Frills at Swill Inn</a></h2>
    <p class="time">1 week ago</p>
    </div><!-- /.content -->
    </div><!-- /.related -->
    </div><!-- /.related-row -->
    <div class="related-row clearfix">
    <div class="related related-3 ">
    <a class="related-thumb" href="/dining-drinking/December-2018/Parsons-Chicken-and-Fish-Lincoln-Park-Dining-News/" onclick="ga('send',  'event', 'Refer clicks', 'Related (photo), slot ' + 3, '/dining-drinking/December-2018/Parsons-Chicken-and-Fish-Lincoln-Park-Dining-News/');" style="background-image: url('/images/cache/cache_9/cache_a/cache_0/CTcheap-chicken006-ec4b60a9.jpeg?ver=1545239802&amp;aspectratio=1.5364583333333'); background-size: cover;"> </a>
    <div class="content">
    <h2><a href="/dining-drinking/December-2018/Parsons-Chicken-and-Fish-Lincoln-Park-Dining-News/" onclick="ga('send',  'event', 'Refer clicks', 'Related (head), slot ' + 3, '/dining-drinking/December-2018/Parsons-Chicken-and-Fish-Lincoln-Park-Dining-News/');">Parson’s Chicken and Fish Opens in Lincoln Park and More Dining News</a></h2>
    <p class="time">1 week ago</p>
    </div><!-- /.content -->
    </div><!-- /.related -->
    <div class="related related-4 ">
    <a class="related-thumb" href="/dining-drinking/December-2018/Four-New-Years-Eve-Dining-Specials-That-You-Can-Still-Reserve/" onclick="ga('send',  'event', 'Refer clicks', 'Related (photo), slot ' + 4, '/dining-drinking/December-2018/Four-New-Years-Eve-Dining-Specials-That-You-Can-Still-Reserve/');" style="background-image: url('/images/cache/cache_2/cache_6/cache_0/DMK_AdaSt._PRESS-f03c4062.jpeg?ver=1545160179&amp;aspectratio=1.7291666666667'); background-size: cover;"> </a>
    <div class="content">
    <h2><a href="/dining-drinking/December-2018/Four-New-Years-Eve-Dining-Specials-That-You-Can-Still-Reserve/" onclick="ga('send',  'event', 'Refer clicks', 'Related (head), slot ' + 4, '/dining-drinking/December-2018/Four-New-Years-Eve-Dining-Specials-That-You-Can-Still-Reserve/');">Four New Year’s Eve Dining Specials (That You Can Still Reserve)</a></h2>
    <p class="time">1 week ago</p>
    </div><!-- /.content -->
    </div><!-- /.related -->
    </div><!-- /.related-row -->
    </section>
    <!-- End Related Articles -->
    <div id="comment" style="clear: both; height: 1px;"></div>
    <a class="edit_from_site" content_id="comments-disqus" href="#" media_id="83">Edit Module</a><div class="comments-block" id="commentFormContainer">
    <h5 class="add-comment">Submit your comment</h5>
    <p class="comments-disclaimer">Comments  are moderated. We review them in an effort to remove foul language, commercial messages, abuse, and irrelevancies.</p>
    <div id="disqus_thread" style="max-width: 600px;"></div>
    <script type="text/javascript">
    </script>
    <noscript>Enable JavaScript to view the <a href="http://disqus.com/?ref_noscript">comments powered by Disqus.</a></noscript>
    </div>
    </div>
    <!-- Begin Sidebar -->
    <aside class="article long clearfix">
    <div class="tablet-sidebar-left">
    <a class="edit_from_site" content_id="widget-marketplace-sponsor" href="#" media_id="83">Edit Module</a>
    <style>
    
    .btn-widget-small {
     margin-bottom: 5px;}
    
    .amp {
    color: white;
    webkit-transition: none .2s ease; 
    }
    
    ul.menu {
    background: black;
       position:absolute;
       /*height: 0px;*/
       visibility: hidden;
       margin-top: 0px;
      background-color: black;
      width: 300px;
      padding: 0px 0px 10px;
    z-index: 9999999;
    }
    
    ul.menu li a {
       font-size: .75em;
       font-style: italic;
       text-align:center;
    }
    #btn-sponsored-features {
    position: relative;
    }
    
    #btn-sponsored-features:hover .menu {
      /*position: relative;*/
      visibility: visible;
    
      /*height: 80%;*/
    
    }
    
    ul.menu a {
    -webkit-transition: none;-moz-transition:none;transition:none;
    }
    #marketing-widget {
    margin-bottom: 20px;
    }
    </style>
    <div id="marketing-widget">
    <div class="btn-widget-small btn-widget-small-reverse">
    <a href="/marketplace" onclick="ga('send',  'event','M&amp;C widget', 'Marketplace');" style="display: inline-block; margin-right: 2px;">Marketplace</a>
    </div>
    <div class="btn-widget-small btn-widget-small-reverse">
    <a href="/Chicago-Magazine/Promotion/Events-and-Party-Pix/" onclick="ga('send',  'event','M&amp;C widget', 'Events and Party Pix');" style="display: inline-block; margin-right: 2px;">Events <span class="new-amp">&amp;</span> Party Pix</a>
    </div>
    <div class="btn-widget-small btn-widget-small-reverse" id="btn-sponsored-features"><span>Resource Guide</span>
    <ul class="menu">
    <li><a href="http://www.chicagomag.com/Chicago-Magazine/Promotion/Dental-Profiles-2018/" onclick="ga('send',  'event','M&amp;C widget', 'Dental Profiles');">Dental Profiles</a></li>
    <li><a href="http://www.chicagomag.com/Chicago-Magazine/Promotion/Experience-Chicago-2018-2/" onclick="ga('send',  'event','M&amp;C widget', 'Experience Chicago 2018');">Experience Chicago</a></li>
    <li><a href="http://www.chicagomag.com/Chicago-Magazine/Promotion/Give-Chicago-2018" onclick="ga('send',  'event','M&amp;C widget', '2018 Give Chicago');">Give Chicago</a></li>
    <li><a href="http://www.chicagomag.com/Chicago-Magazine/Promotion/2018-Go-West/" onclick="ga('send',  'event','M&amp;C widget', '2018 Go West');">Go West</a></li>
    <li><a href="http://www.chicagomag.com/Chicago-Magazine/Promotion/Make-it-Lincoln-Park-2018/" onclick="ga('send',  'event','M&amp;C widget', 'Make It Lincoln Park');">Make it Lincoln Park</a></li>
    <li><a href="http://www.chicagomag.com/Chicago-Magazine/Promotion/Prime-of-Your-Life-2018/" onclick="ga('send',  'event','M&amp;C widget', 'Prime of Your Life');">Prime of Your Life</a></li>
    <li><a href="http://www.chicagomag.com/Chicago-Magazine/Promotion/Chicagoland-Professional-Profiles-2018/?4" onclick="ga('send',  'event','M&amp;C widget', 'Professional Profiles');">Professional Profiles</a></li>
    <li><a href="/Chicago-Magazine/Promotion/School-Open-House-Guide-2018/" onclick="ga('send',  'event','M&amp;C widget', 'School Open House Guide');">School Open House Guide</a></li>
    <li><a href="/Chicago-Magazine/Promotion/Vin-De-France/" onclick="ga('send',  'event','M&amp;C widget', 'Vin De France');">Vin De France</a></li>
    </ul>
    </div>
    </div>
    <a class="edit_from_site" content_id="cube-1" href="#" media_id="83">Edit Module</a>
    <div class="ad sq">
    <h2>Advertisement</h2>
    <div id="div-gpt-ad-cube-1" style="height: 250px; width: 300px;"></div>
    <script type="text/javascript">
    googletag.cmd.push(function() {
    		googletag.display('div-gpt-ad-cube-1');
    });
    	</script>
    </div>
    <a class="edit_from_site" content_id="cube-3" href="#" media_id="83">Edit Module</a>
    <div class="ad sq">
    <h2>Advertisement</h2>
    <div id="div-gpt-ad-cube-3" style="width: 300px; height: 250px; margin: 0 auto;">
    <script>
    googletag.cmd.push(function() {
        googletag.display('div-gpt-ad-cube-3');
    });
      </script>
    </div>
    </div>
    </div>
    <div class="tablet-sidebar-right">
    <form action="" method="post" name="newsletter" onsubmit="validateNewsletter(); return false;">
    <section class="newsletters widget" id="category-specific-newsletter">
    <h2 class="title red-border" id="newsletterTitle">Get Our Newsletters</h2>
    <div class="newsletter-reveal clearfix" style="display: block;">
    <p class="newsletter-description"></p>
    <input class="clean white" name="email" placeholder="Your email address" type="text"/>
    <div class="form-error" id="error-email">
              Please enter your email address
            </div>
    <label class="checkbox " for="over21"> <input checked="" id="over21" name="over21" type="checkbox" value="yes"/>Are you 21 or older?</label>
    <input name="source" type="hidden" value="/Chicago-Magazine/November-2012/Best-Sandwiches-Chicago/"/>
    <input id="newsletterChoice" name="newsletter" type="hidden" value=""/>
    <br style="clear:both;"/>
    <button type="submit">Subscribe</button>
    </div>
    </section>
    </form>
    <script>
          var newsletterChoice = "Dish";
      
      
    
        var cookieValue = getCookie('subscribe-'+newsletterChoice.replace(' ','-'));
        if (cookieValue == 'true') {
          jQuery('#category-specific-newsletter').hide();
        }
    
        else {
          var description = '';
          switch (newsletterChoice) {
            case 'Home & Real Estate':
              description = "<span class='leadin'>Fridays:</span> Get the latest real estate news, keep an eye on the city’s most beautiful and unique listings, and learn about the best home & design events locally.";
              break;
            case 'The 312':
              description = "<span class='leadin'>Tuesdays:</span> Get up to speed on the week’s most important stories and must-reads.";
              break;
            case 'Chicago Guide':
              description = "<span class='leadin'>Wednesdays:</span> <em>Chicago</em>’s culture critics bring you a weekly list of the top five events worth your time and money.";
              break;
            case 'Go Travel':
              description = "<span class='leadin'>Monthly:</span> Be the first to know about exclusive travel discounts and offers to help you plan your next big adventure, courtesy of our sponsors.";
              break;
            case 'Dish':
              description = "<span class='leadin'>Wednesdays:</span> Want the latest news from the Chicagoland dining scene served up fresh in your email inbox? Sign up for the weekly newsletter and get inside access to the city’s most plugged-in food and dining coverage. ";
              break;
            case 'Sales Check':
              description = "<span class='leadin'>Thursdays:</span> Hear about the great shopping experiences in the Chicago area, plus the latest news about hot store openings, sales, trends, and local retail news. Shop-a-holic? We know just how you feel. ";
              break;
            case 'VIP Newsletter':
              description = "<span class='leadin'>Mondays, Wednesdays, and Fridays: </span><em>Chicago</em> magazine hosts various events throughout the year. Sign up and join the party!";
              break;
            case 'Special Offers':
              description = 'Receive special offers and discounts from <em>Chicago</em> magazine’s selected partners.';
              break;
            case 'Health & Wellness':
              description = "<span class='leadin'>Third Mondays: </span>The top local stories in health care innovation, nutrition, exercise, wellness, and more—plus, local classes and workshops worth attending. Our editors will bring you the latest on how to live life, well.";
              break;
            case 'Select all':
              description = 'Subscribe to <em>Chicago Guide</em>, <em>Chicago Travel</em>, <em>Dish</em>, <em>Health & Wellness</em>, <em>Sales Check</em>, <em>Home & Real Estate</em>, <em>VIP Newsletter</em>, and <em>Special Offers</em>.'
          }
    
    
          jQuery('.newsletter-description').html(description);
          jQuery('.newsletter-reveal').slideDown();
          jQuery('#newsletterChoice').val(newsletterChoice);
          jQuery('#newsletterTitle').html('Subscribe to ' + newsletterChoice);
        }
    
    </script>
    <div class="lazyload" data-url="/lazyloads/most-popular/" id="lazy-popular"></div>
    <div id="fixie-side-position-finder">
    <div class="clearfix" id="fixie-side">
    <a class="edit_from_site" content_id="cube-2" href="#" media_id="83">Edit Module</a>
    <div class="ad sq">
    <h2>Advertisement</h2>
    <div id="div-gpt-ad-cube-2" style="width: 300px; height: 250px; margin: 0 auto;">
    <script>
    googletag.cmd.push(function() {
        googletag.display('div-gpt-ad-cube-2');
    });
      </script>
    </div>
    </div>
    <script>
              // Make the fixed slot refresh every 2 minutes (on desktops; ad is not fixed elsewhere)
              setInterval(function(){
                if (jQuery('#fixie-side').css('position') == 'fixed') {
                  googletag.pubads().refresh([cube2]);
                  }
                }, 120000);
            </script>
    <a class="edit_from_site" content_id="widget-current-issue" href="#" media_id="83">Edit Module</a>
    <section class="subscribe widget">
    <a class="button" href="javascript:void(0);"><span class="title">Subscribe</span><span class="plus">+</span><span class="minus">-</span></a>
    <div class="issue">
    <a href="/Chicago-Magazine/January-2019/" onclick="ga('send',  'event', 'Current issue widget', 'Current issue');"><img alt="Current issue of Chicago magazine" height="145" src="/images/cache/cache_9/cache_6/cache_8/CMAG0119-4ec81869.jpeg?ver=1545933501&amp;aspectratio=0.75862068965517" width="110"/></a>
    <a class="btn" href="/subscribe" onclick="ga('send',  'event', 'Current issue widget', 'Subscribe (red button)');">Subscribe</a>
    </div>
    <ul>
    <li>
    <a href="/subscribe" onclick="ga('send',  'event', 'Current issue widget', 'Subscribe');">
    <span class="lead"><span class="red">Subscribe</span> to the</span>
    <span class="title">Print &amp; Tablet Edition</span>
    </a>
    </li>
    <li>
    <a href="http://www.chicagomag.com/digitaledition" onclick="ga('send',  'event', 'Current issue widget', 'Digital edition');">
    <span class="lead"><span class="red">Subscribe</span> to the</span>
    <span class="title">Digital Edition</span>
    </a>
    </li>
    <li>
    <a class="ipad" href="/ipad" onclick="ga('send',  'event', 'Current issue widget', 'iPad');">
    <span class="lead"><span class="red">Download</span> our tablet app for</span>
    <span class="title">iPad</span>
    </a>
    <a class="android" href="/android" onclick="ga('send',  'event', 'Current issue widget', 'Android');">
    <span class="title">Android</span>
    </a>
    </li>
    <li>
    <a class="single" href="/single-issues" onclick="ga('send',  'event', 'Current issue widget', 'Single Issues');">
    <span class="lead"><span class="red">Purchase</span> copies of</span>
    <span class="title">Single Issues</span>
    </a>
    <a class="gift" href="/gifts" onclick="ga('send',  'event', 'Current issue widget', 'Gift');">Give a <span class="red">gift</span> subscription</a>
    </li>
    </ul>
    </section>
    </div>
    </div>
    </div>
    </aside>
    <!-- End Sidebar -->
    </div> <!-- End .content-and-sidebar -->
    </article>
    <!-- End Main Article Container -->
    </div><!--- End Page -->
    <a class="edit_from_site" content_id="leaderboard-2" href="#" media_id="83">Edit Module</a>
    <style type="text/css">
          @media (max-width:735px) {
        #div-gpt-ad-leaderboard-2 {
              display:none !important;
            }
       }
    </style>
    <div class="ad ban" id="div-gpt-ad-leaderboard-2">
    <script type="text/javascript">
    if (document.documentElement.clientWidth > 735) {
    googletag.cmd.push(function() {
        googletag.display('div-gpt-ad-leaderboard-2');
    });
    }
      </script>
    </div>
    </div><!-- End Content Wrap -->
    <div id="div-out-of-page">
    <script>
      googletag.cmd.push(function() {
        googletag.display('div-out-of-page');
      });
      </script>
    </div>
    <script>(function(d, s, id) {
      var js, fjs = d.getElementsByTagName(s)[0];
      if (d.getElementById(id)) return;
      js = d.createElement(s); js.id = id;
      js.src = "//connect.facebook.net/en_US/all.js#xfbml=1&appId=432224116825361";
      fjs.parentNode.insertBefore(js, fjs);
    }(document, 'script', 'facebook-jssdk'));</script>
    <!-- LSC tracking pixel -->
    <script>
        document.write('<script id="tbw_pixel" type="text/javascript" src="//d26x5ounzdjojj.cloudfront.net/tbw/tbw_analytics_v1.0.js?' + (new Date()).getHours() + '" onload="tbw_parse(this);" tbw_site_id="Ldd6b4994ab6844a0" tbw_segment="Retargeting" defer><\/script>');
    </script>
    <!-- fix for gallery height bug -->
    <script>
    jQuery( document ).ready(function() {
      jQuery('.photoset-grid-basic .photoset-row').css( "height", "initial" );
    });
    </script>
    <!-- force removal of bottom nav in gallery view -->
    <style>
    body.gallery.no-nav aside.story-nav-aside {
      display: none !important;
    }
    </style>
    <footer class="page-footer">
    <nav>
    <a class="logo background-logo-svg" href="/" onclick="ga('send', 'event', 'Global footer', 'Logo to home');">Chicago magazine</a>
    <select class="clean wide nav" id="page-nav-mobile" onchange="location = this.options[this.selectedIndex].value;">
    <option value="/Chicago-Magazine/Advertise">Advertise</option>
    <option value="/Chicago-Magazine/Contact-Us">Contact Us</option>
    <option value="/Chicago-Magazine/Classifieds/">Marketplace</option>
    <option value="http://www.tronc.com/privacy-policy/">Privacy &amp; Terms (Updated)</option>
    <option value="/Chicago-Magazine/archive-index-test">Archive</option>
    <option value="/customerservice">Customer Service</option>
    <option value="/newsletters">Newsletters</option>
    <option value="/subscribe">Subscribe</option>
    </select>
    <section class="links">
    <ul>
    <li><a href="/Chicago-Magazine/Advertise" onclick="ga('send', 'event','Global footer', 'Advertise');">Advertise</a></li>
    <li><a href="/Chicago-Magazine/Contact-Us" onclick="ga('send', 'event','Global footer', 'Contact us');">Contact Us</a></li>
    <li><a href="/Chicago-Magazine/Classifieds/" onclick="ga('send', 'event','Global footer', 'Marketplace');">Marketplace</a></li>
    <li><a href="http://www.tronc.com/privacy-policy/" onclick="ga('send', 'event','Global footer', 'Privacy');">Privacy (Updated)</a></li>
    <li><a href="http://www.tronc.com/central-terms-of-service/" onclick="ga('send', 'event','Global footer', 'Terms');">Terms</a></li>
    </ul>
    <ul>
    <li><a href="/Chicago-Magazine/archive-index-test" onclick="ga('send', 'event','Global footer', 'Archive');">Archive</a></li>
    <li><a href="/customerservice" onclick="ga('send', 'event','Global footer', 'Customer service');">Customer Service</a></li>
    <li><a href="/newsletters" onclick="ga('send', 'event','Global footer', 'Newsletters');">Newsletters</a></li>
    <li><a href="/subscribe" onclick="ga('send', 'event','Global footer', 'Subscribe');">Subscribe</a></li>
    </ul>
    </section>
    <div class="credits">
    <p class="copyright">©<span id="copyright-year"></span> <em>Chicago</em> magazine / A Chicago Tribune Media Group website</p>
    </div>
    </nav>
    </footer>
    <!--[if lt IE 9]>
    <script type="text/javascript" language"Javascript">
    console.log('Mutha ucking IE8');
    jQuery('html').addClass('old-ie');
    </script>
    <![endif]-->
    <script>
    document.getElementById('copyright-year').innerHTML = new Date().getFullYear()
    </script>
    <!-- Chartbeat -->
    <script type="text/javascript">
    var _sf_async_config={uid:3096,domain:"chicagomag.com"}; // ,useCanonical: true
    
      _sf_async_config.sections = "Dining & Drinking";
    
    
    (function(){
      function loadChartbeat() {
        window._sf_endpt=(new Date()).getTime();
        var e = document.createElement('script');
        e.setAttribute('language', 'javascript');
        e.setAttribute('type', 'text/javascript');
        e.setAttribute('src',
           (("https:" == document.location.protocol) ? "https://a248.e.akamai.net/chartbeat.download.akamai.com/102508/" : "http://static.chartbeat.com/") +
           "js/chartbeat.js");
        document.body.appendChild(e);
      }
      var oldonload = window.onload;
      window.onload = (typeof window.onload != 'function') ?
         loadChartbeat : function() { oldonload(); loadChartbeat(); };
    })();
    </script>
    <!-- Google Custom Search Engine -->
    <script type="text/javascript">
      (function() {
        var cx = '010330565828956611806:7mfjr5payok'; //PROD
        // var cx = '012184964769571027538:4alkqopmlde'; // DEV
        var gcse = document.createElement('script');
        gcse.type = 'text/javascript';
        gcse.async = true;
        gcse.src = (document.location.protocol == 'https:' ? 'https:' : 'http:') +
            '//www.google.com/cse/cse.js?cx=' + cx;
        var s = document.getElementsByTagName('script')[0];
        s.parentNode.insertBefore(gcse, s);
      })();
    </script>
    <script src="https://www.sc.pages05.net/lp/static/js/iMAWebCookie.js?5d52baad-1411358527b-d7c8ec57ae636c7258d3eb0ef0e531f2&amp;h=www.pages05.net" type="text/javascript"></script>
    <!-- Disqus -->
    <!-- For Citi sponsorship (remove this conditional after Nov 2018) -->
    <!-- -->
    <script>
      var disqus_shortname = 'chicagomag';
      var disqus_identifier = '19493';
      var disqus_title = 'The 50 Best Sandwiches in Chicago';
      var disqus_url = 'http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-Chicago/';
    
      /* * * DON'T EDIT BELOW THIS LINE * * */
      (function() {
          var dsq = document.createElement('script'); dsq.type = 'text/javascript'; dsq.async = true;
          dsq.src = '//' + disqus_shortname + '.disqus.com/embed.js';
          (document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(dsq);
      })();
    
      (function () {
      var s = document.createElement('script'); s.async = true;
      s.type = 'text/javascript';
      s.src = 'http://' + disqus_shortname + '.disqus.com/count.js';
      (document.getElementsByTagName('HEAD')[0] || document.getElementsByTagName('BODY')[0]).appendChild(s);
      }());
    </script>
    <!-- -->
    <!-- Clean up the lame Rivista pagination -->
    <script type="text/javascript">
      var pb = jQuery('.page-break');
      if (pb.length) {
        var pagebreak = pb.html();
        // Remove it from Page 1
        if (pagebreak.substring(0,10) == "(page 1 of") {
          pb.hide();
        }
        else if (pagebreak.substring(0,13) == "<em>Page 1 of") {
           pb.hide();
        }
        else {
          pagebreak = pagebreak.replace("(page","<em>Page");
          pagebreak = pagebreak.replace(")","</em>");
          pb.html(pagebreak);
          pb.css('display','inline-block');
        }
      }
    </script>
    <!--[if lt IE 9]>
    <script  type="text/javascript" language="JavaScript" src="/core/media/themes/Respond/js/respond.js?ver=1473876729"></script>
    <![endif]-->
    <script language="JavaScript" src="/core/media/js/base.js?ver=1473876728" type="text/javascript"></script>
    <script language="JavaScript" src="/core/media/themes/Respond/js/bootstrap.min.js?ver=1473876729" type="text/javascript"></script>
    <script language="JavaScript" src="//maps.googleapis.com/maps/api/js?v=3.exp&amp;sensor=false" type="text/javascript"></script>
    <script language="JavaScript" src="/theme_overrides/Respond/js/interstitial.js?ver=1524154906" type="text/javascript"></script>
    <script language="JavaScript" src="/theme_overrides/Respond/js/newsletter-subscribe.js?ver=1524850607" type="text/javascript"></script>
    <script language="JavaScript" src="/theme_overrides/Respond/js/RivistaGoogleDFP.js?ver=1447178886" type="text/javascript"></script>
    <!-- godengo-monitor --></body>
    </html>




```python
print(soup.find_all('div', 'sammy'))
```

    [<div class="sammy" style="position: relative;">
    <div class="sammyRank">1</div>
    <div class="sammyListing"><a href="/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Old-Oak-Tap-BLT/"><b>BLT</b><br>
    Old Oak Tap<br>
    <em>Read more</em> </br></br></a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">2</div>
    <div class="sammyListing"><a href="/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Au-Cheval-Fried-Bologna/"><b>Fried Bologna</b><br/>
    Au Cheval<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">3</div>
    <div class="sammyListing"><a href="/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Xoco-Woodland-Mushroom/"><b>Woodland Mushroom</b><br/>
    Xoco<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">4</div>
    <div class="sammyListing"><a href="/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Als-Deli-Roast-Beef/"><b>Roast Beef</b><br/>
    Al’s Deli<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">5</div>
    <div class="sammyListing"><a href="/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Publican-Quality-Meats-PB-L/"><b>PB&amp;L</b><br/>
    Publican Quality Meats<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">6</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Hendrickx-Belgian-Bread-Crafter-Belgian-Chicken-Curry-Salad/"><b>Belgian Chicken Curry Salad</b><br/>
    Hendrickx Belgian Bread Crafter<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">7</div>
    <div class="sammyListing"><a href="/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Acadia-Lobster-Roll/"><b>Lobster Roll</b><br/>
    Acadia<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">8</div>
    <div class="sammyListing"><a href="/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Birchwood-Kitchen-Smoked-Salmon-Salad/"><b>Smoked Salmon Salad</b><br/>
    Birchwood Kitchen<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">9</div>
    <div class="sammyListing"><a href="/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Cemitas-Puebla-Atomica-Cemitas/"><b>Atomica Cemitas</b><br/>
    Cemitas Puebla<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">10</div>
    <div class="sammyListing"><a href="/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Nana-Grilled-Laughing-Bird-Shrimp-and-Fried-Oyster-Po-Boy/"><b>Grilled Laughing Bird Shrimp and Fried Po’ Boy</b><br/>
    Nana<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">11</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Lula-Cafe-Ham-and-Raclette-Panino/"><b>Ham and Raclette Panino</b><br/>
    Lula Cafe<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">12</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Ricobenes-Breaded-Steak/"><b>Breaded Steak</b><br/>
    Ricobene’s<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">13</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Frog-n-Snail-The-Hawkeye/"><b>The Hawkeye</b><br/>
    Frog n Snail<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">14</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Crosbys-Kitchen-Chicken-Dip/"><b>Chicken Dip</b><br/>
    Crosby’s Kitchen<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">15</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Longman-and-Eagle-Wild-Boar-Sloppy-Joe/"><b>Wild Boar Sloppy Joe</b><br/>
    Longman &amp; Eagle<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">16</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Bari-Meatball-Sub/"><b>Meatball Sub</b><br/>
    Bari<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">17</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Mannys-Corned-Beef/"><b>Corned Beef</b><br/>
    Manny’s<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">18</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Eggys-Turkey-Club/"><b>Turkey Club</b><br/>
    Eggy’s<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">19</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Old-Jerusalem-Falafel/"><b>Falafel</b><br/>
    Old Jerusalem<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">20</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Mindys-HotChocolate-Crab-Cake/"><b>Crab Cake</b><br/>
    Mindy’s HotChocolate<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">21</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Olgas-Delicatessen-Chicken-Schnitzel/"><b>Chicken Schnitzel</b><br/>
    Olga’s Delicatessen<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">22</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Dawali-Mediterranean-Kitchen-Shawarma/"><b>Shawarma</b><br/>
    Dawali Mediterranean Kitchen<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">23</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Big-Jones-Toasted-Pimiento-Cheese/"><b>Toasted Pimiento Cheese</b><br/>
    Big Jones<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">24</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-La-Pane-Vegetarian-Panino/"><b>Vegetarian Panino</b><br/>
    La Pane<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">25</div>
    <div class="sammyListing"><a href="/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Pastoral-Cali-Chevre/"><b>Cali Chèvre</b><br/>
    Pastoral<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">26</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Maxs-Deli-Pastrami/"><b>Pastrami</b><br/>
    Max’s Deli<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">27</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Luckys-Sandwich-Co-The-Fredo/"><b>The Fredo</b><br/>
    Lucky’s Sandwich Co.<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">28</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-City-Provisions-Smoked-Ham/"><b>Smoked Ham</b><br/>
    City Provisions<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">29</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Papas-Cache-Sabroso-Jibarito/"><b>Jibarito</b><br/>
    Papa’s Cache Sabroso<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">30</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Bavettes-Bar-and-Boeuf-Shaved-Prime-Rib/"><b>Shaved Prime Rib</b><br/>
    Bavette’s Bar &amp; Boeuf<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">31</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Hannahs-Bretzel-Serrano-Ham-and-Manchego-Cheese/"><b>Serrano Ham and Manchego Cheese</b><br/>
    Hannah’s Bretzel<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">32</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-La-Fournette-Tuna-Salad/"><b>Tuna Salad</b><br/>
    La Fournette<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">33</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Paramount-Room-Paramount-Reuben/"><b>Paramount Reuben</b><br/>
    Paramount Room<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">34</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Melt-Sandwich-Shoppe-The-Istanbul/"><b>The Istanbul</b><br/>
    Melt Sandwich Shoppe<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">35</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Floriole-Cafe-and-Bakery-BAD/"><b>B.A.D.</b><br/>
    Floriole Cafe &amp; Bakery<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">36</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-First-Slice-Pie-Cafe-Duck-Confit-and-Mozzarella/"><b>Duck Confit and Mozzarella</b><br/>
    First Slice Pie Café<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">37</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Troquet-Croque-Monsieur/"><b>Croque Monsieur</b><br/>
    Troquet<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">38</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Grahamwich-Green-Garbanzo/"><b>Green Garbanzo</b><br/>
    Grahamwich<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">39</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Saigon-Sisters-The-Hen-House/"><b>The Hen House</b><br/>
    Saigon Sisters<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">40</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Rosalias-Deli-Tuscan-Chicken/"><b>Tuscan Chicken</b><br/>
    Rosalia’s Deli<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">41</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Z-and-H-MarketCafe-The-Marty/"><b>The Marty </b><br/>
    Z&amp;H MarketCafe<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">42</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Market-House-on-the-Square-Whitefish/"><b>Whitefish</b><br/>
    Market House on the Square<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">43</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Elaines-Coffee-Call-Oat-Bread-Pecan-Butter-and-Fruit-Jam/"><b>Oat Bread, Pecan Butter, and Fruit Jam</b><br/>
    Elaine’s Coffee Call<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">44</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Marion-Street-Cheese-Market-Cauliflower-Melt/"><b>Cauliflower Melt</b><br/>
    Marion Street Cheese Market<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">45</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Cafecito-Cubano/"><b>Cubana</b><br/>
    Cafecito<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">46</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Chickpea-Kufta/"><b>Kufta</b><br/>
    Chickpea<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">47</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-The-Goddess-and-Grocer-Debbies-Egg-Salad/"><b>Debbie’s Egg Salad</b><br/>
    The Goddess and Grocer<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">48</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Zenwich-Beef-Curry/"><b>Beef Curry</b><br/>
    Zenwich<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative;">
    <div class="sammyRank">49</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Toni-Patisserie-Le-Vegetarien/"><b>Le Végétarien</b><br/>
    Toni Patisserie<br/>
    <em>Read more</em> </a></div>
    </div>, <div class="sammy" style="position: relative; border-bottom: 0">
    <div class="sammyRank">50</div>
    <div class="sammyListing"><a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Phoebes-Bakery-The-Gatsby/"><b>The Gatsby</b><br/>
    Phoebe’s Bakery<br/>
    <em>Read more</em> </a></div>
    </div>]
    


```python
len(soup.find_all('div', 'sammy'))
```




    50




```python
print(soup.find_all('div', 'sammy')[0])
```

    <div class="sammy" style="position: relative;">
    <div class="sammyRank">1</div>
    <div class="sammyListing"><a href="/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Old-Oak-Tap-BLT/"><b>BLT</b><br>
    Old Oak Tap<br>
    <em>Read more</em> </br></br></a></div>
    </div>
    

## 접근한 웹 페이지에서 원하는 데이터 추출하고 정리하기


```python
tmp_one = soup.find_all('div', 'sammy')[0]
type(tmp_one)
```




    bs4.element.Tag




```python
tmp_one.find(class_='sammyRank')
```




    <div class="sammyRank">1</div>




```python
tmp_one.find(class_='sammyRank').get_text()
```




    '1'




```python
tmp_one.find(class_='sammyListing').get_text()
```




    'BLT\r\nOld Oak Tap\nRead more '




```python
tmp_one.find('a')['href']
```




    '/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Old-Oak-Tap-BLT/'




```python
import re

tmp_string = tmp_one.find(class_='sammyListing').get_text()

re.split(('\n|\r\n'), tmp_string)

print(re.split(('\n|\r\n'), tmp_string)[0])
print(re.split(('\n|\r\n'), tmp_string)[1])
```

    BLT
    Old Oak Tap
    


```python
from urllib.parse import urljoin
```


```python
rank = []
main_menu = []
cafe_name = []
url_add = []

list_soup = soup.find_all('div', 'sammy')

for item in list_soup:
    rank.append(item.find(class_='sammyRank').get_text())
    
    tmp_string = item.find(class_='sammyListing').get_text()

    main_menu.append(re.split(('\n|\r\n'), tmp_string)[0])
    cafe_name.append(re.split(('\n|\r\n'), tmp_string)[1])
    
    url_add.append(urljoin(url_base, item.find('a')['href']))
```
from tqdm import tqdm_notebook
import time

rank = []
main_menu = []
cafe_name = []
url_add = []

list_soup = soup.find_all('div', 'sammy')
bar_total = tqdm_notebook(list_soup)

for item in bar_total:
    rank.append(item.find(class_='sammyRank').get_text())
    
    tmp_string = item.find(class_='sammyListing').get_text()

    main_menu.append(re.split(('\n|\r\n'), tmp_string)[0])
    cafe_name.append(re.split(('\n|\r\n'), tmp_string)[1])
    
    url_add.append(urljoin(url_base, item.find('a')['href']))
    
    time.sleep(0.05)

```python
rank[:5]
```




    ['1', '2', '3', '4', '5']




```python
main_menu[:5]
```




    ['BLT', 'Fried Bologna', 'Woodland Mushroom', 'Roast Beef', 'PB&L']




```python
cafe_name[:5]
```




    ['Old Oak Tap', 'Au Cheval', 'Xoco', 'Al’s Deli', 'Publican Quality Meats']




```python
url_add[:5]
```




    ['http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Old-Oak-Tap-BLT/',
     'http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Au-Cheval-Fried-Bologna/',
     'http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Xoco-Woodland-Mushroom/',
     'http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Als-Deli-Roast-Beef/',
     'http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Publican-Quality-Meats-PB-L/']




```python
len(rank), len(main_menu), len(cafe_name), len(url_add)
```




    (50, 50, 50, 50)



---


```python
import pandas as pd

data = {'Rank':rank, 'Menu':main_menu, 'Cafe':cafe_name, 'URL':url_add}
df = pd.DataFrame(data)
df.head()
```




<div>
<style scoped>
    .dataframe tbody tr th:only-of-type {
        vertical-align: middle;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }

    .dataframe thead th {
        text-align: right;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>Rank</th>
      <th>Menu</th>
      <th>Cafe</th>
      <th>URL</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>1</td>
      <td>BLT</td>
      <td>Old Oak Tap</td>
      <td>http://www.chicagomag.com/Chicago-Magazine/Nov...</td>
    </tr>
    <tr>
      <th>1</th>
      <td>2</td>
      <td>Fried Bologna</td>
      <td>Au Cheval</td>
      <td>http://www.chicagomag.com/Chicago-Magazine/Nov...</td>
    </tr>
    <tr>
      <th>2</th>
      <td>3</td>
      <td>Woodland Mushroom</td>
      <td>Xoco</td>
      <td>http://www.chicagomag.com/Chicago-Magazine/Nov...</td>
    </tr>
    <tr>
      <th>3</th>
      <td>4</td>
      <td>Roast Beef</td>
      <td>Al’s Deli</td>
      <td>http://www.chicagomag.com/Chicago-Magazine/Nov...</td>
    </tr>
    <tr>
      <th>4</th>
      <td>5</td>
      <td>PB&amp;L</td>
      <td>Publican Quality Meats</td>
      <td>http://www.chicagomag.com/Chicago-Magazine/Nov...</td>
    </tr>
  </tbody>
</table>
</div>




```python
df = pd.DataFrame(data, columns=['Rank','Cafe','Menu','URL'])
df.head(5)
```




<div>
<style scoped>
    .dataframe tbody tr th:only-of-type {
        vertical-align: middle;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }

    .dataframe thead th {
        text-align: right;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>Rank</th>
      <th>Cafe</th>
      <th>Menu</th>
      <th>URL</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>1</td>
      <td>Old Oak Tap</td>
      <td>BLT</td>
      <td>http://www.chicagomag.com/Chicago-Magazine/Nov...</td>
    </tr>
    <tr>
      <th>1</th>
      <td>2</td>
      <td>Au Cheval</td>
      <td>Fried Bologna</td>
      <td>http://www.chicagomag.com/Chicago-Magazine/Nov...</td>
    </tr>
    <tr>
      <th>2</th>
      <td>3</td>
      <td>Xoco</td>
      <td>Woodland Mushroom</td>
      <td>http://www.chicagomag.com/Chicago-Magazine/Nov...</td>
    </tr>
    <tr>
      <th>3</th>
      <td>4</td>
      <td>Al’s Deli</td>
      <td>Roast Beef</td>
      <td>http://www.chicagomag.com/Chicago-Magazine/Nov...</td>
    </tr>
    <tr>
      <th>4</th>
      <td>5</td>
      <td>Publican Quality Meats</td>
      <td>PB&amp;L</td>
      <td>http://www.chicagomag.com/Chicago-Magazine/Nov...</td>
    </tr>
  </tbody>
</table>
</div>




```python
df.to_csv('../data/03. best_sandwiches_list_chicago.csv', sep=',', 
          encoding='UTF-8')
```

## 다수의 웹 페이지에 자동으로 접근해서 원하는 정보 가져오기


```python
from bs4 import BeautifulSoup 
from urllib.request import urlopen

import pandas as pd
```


```python
df = pd.read_csv('../data/03. best_sandwiches_list_chicago.csv', index_col=0)
df.head()
```




<div>
<style scoped>
    .dataframe tbody tr th:only-of-type {
        vertical-align: middle;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }

    .dataframe thead th {
        text-align: right;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>Rank</th>
      <th>Cafe</th>
      <th>Menu</th>
      <th>URL</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>1</td>
      <td>Old Oak Tap</td>
      <td>BLT</td>
      <td>http://www.chicagomag.com/Chicago-Magazine/Nov...</td>
    </tr>
    <tr>
      <th>1</th>
      <td>2</td>
      <td>Au Cheval</td>
      <td>Fried Bologna</td>
      <td>http://www.chicagomag.com/Chicago-Magazine/Nov...</td>
    </tr>
    <tr>
      <th>2</th>
      <td>3</td>
      <td>Xoco</td>
      <td>Woodland Mushroom</td>
      <td>http://www.chicagomag.com/Chicago-Magazine/Nov...</td>
    </tr>
    <tr>
      <th>3</th>
      <td>4</td>
      <td>Al’s Deli</td>
      <td>Roast Beef</td>
      <td>http://www.chicagomag.com/Chicago-Magazine/Nov...</td>
    </tr>
    <tr>
      <th>4</th>
      <td>5</td>
      <td>Publican Quality Meats</td>
      <td>PB&amp;L</td>
      <td>http://www.chicagomag.com/Chicago-Magazine/Nov...</td>
    </tr>
  </tbody>
</table>
</div>




```python
df['URL'][0]
```




    'http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Old-Oak-Tap-BLT/'




```python
html = urlopen(df['URL'][0])
soup_tmp = BeautifulSoup(html, "html.parser")
soup_tmp
```




    
    <!DOCTYPE doctype html>
    
    <html lang="en">
    <head>
    <!-- Urbis magnitudo. Fabulas magnitudo. -->
    <meta charset="utf-8"/>
    <style>a.edit_from_site {display: none !important;}</style>
    <title>
      1. Old Oak Tap BLT |
      Chicago magazine
          |  November 2012
        </title>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable = no" name="viewport">
    <meta content="“Truly inspired.”" name="description"/>
    <!-- <meta name="description" content="&#8220;Truly inspired.&#8221;"> -->
    <meta content="" name="keywords"/>
    <meta content="37873197144" property="fb:pages">
    <link href="//www.googletagservices.com" rel="dns-prefetch"/>
    <link href="//ajax.googleapis.com" rel="dns-prefetch"/>
    <link href="//securepubads.g.doubleclick.net" rel="dns-prefetch"/>
    <link href="//media.chicagomag.com" rel="dns-prefetch"/>
    <link href="//ox-d.godengo.com/" rel="dns-prefetch"/>
    <link href="//www.google-analytics.com" rel="dns-prefetch"/>
    <link href="//ping.chartbeat.net" rel="dns-prefetch"/>
    <link href="//static.chartbeat.com" rel="dns-prefetch"/>
    <link href="//www.google.com" rel="dns-prefetch"/>
    <link href="//cse.google.com" rel="dns-prefetch"/>
    <link href="//www.googleapis.com" rel="dns-prefetch"/>
    <link href="//maps.googleapis.com" rel="dns-prefetch"/>
    <link href="//csi.gstatic.com" rel="dns-prefetch"/>
    <link href="//www.facebook.com" rel="dns-prefetch"/>
    <link href="//staticxx.facebook.com" rel="dns-prefetch"/>
    <link href="//connect.facebook.net" rel="dns-prefetch"/>
    <link href="//contentz.mkt51.net" rel="dns-prefetch"/>
    <link href="//www.pages05.net" rel="dns-prefetch"/>
    <link href="//fbcdn.net" rel="dns-prefetch"/>
    <link href="//players.brightcove.net" rel="dns-prefetch"/>
    <link href="//edge.api.brightcove.com" rel="dns-prefetch"/>
    <link href="//metrics.brightcove.com" rel="dns-prefetch"/>
    <link href="//c.brightcove.com" rel="dns-prefetch"/>
    <link href="//pvp.trb.com" rel="dns-prefetch"/>
    <link href="//s.ntv.io" rel="dns-prefetch"/>
    <!-- For Citi sponsorship (move these to above ^^ links after Nov 2018) -->
    <!-- -->
    <!-- DOES NOT HAVE REAL ESTATE CATEGORY OR IS NOT REAL ESTATE HOMEPAGE -->
    <link href="//chicagomag.disqus.com" rel="dns-prefetch"/>
    <link href="//www.disqus.com" rel="dns-prefetch"/>
    <link href="//a.disquscdn.com" rel="dns-prefetch"/>
    <!-- -->
    <link href="https://use.typekit.net/vdh5vgk.css" rel="stylesheet"/>
    <meta content="www.pages05.net,chicagomag.com" name="com.silverpop.brandeddomains">
    <script>
    (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
    (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
    m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
    })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
    
    ga('create', 'UA-297666-1', {'siteSpeedSampleRate': 100});
    
    
    
    ga('set', 'dimension1', 'restaurant')
    
    
    
      ga('set', 'dimension4', '2012-10-09 18:13:00');
    
      ga('set', 'dimension5', 'article.tpl');
    
    ga('send', 'pageview');
    
    
    var trackOutboundLink = function(url) {
       ga('send', 'event', 'outbound', 'click', url, {
         'transport': 'beacon',
         'hitCallback': function(){document.location = url;}
       });
    }
    
    
    
    
    
    </script>
    <link href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Old-Oak-Tap-BLT/" rel="canonical">
    <meta content="1. Old Oak Tap BLT" name="title"/>
    <meta content="all" name="robots"/>
    <meta content="chicago magazine, chicago magazine online, chicago guide, chicago recreation, chicago restaurants, chicago events, chicago entertainment, chicago attractions, chicago outdoor, chicago dining, chicago bars, chicago nightlife, chicago shopping, chicago fashion, chicago arts, chicago culture" name="news_keywords"/>
    <!-- -->
    <link href="/images/cache/cache_3/cache_9/cache_8/01-Old-Oak-Trap-BLT-500-79e34893.jpeg?ver=1482330137&amp;aspectratio=1.3333333333333" rel="image_src">
    <meta content="/images/cache/cache_3/cache_9/cache_8/01-Old-Oak-Trap-BLT-500-79e34893.jpeg?ver=1482330137&amp;aspectratio=1.3333333333333" name="thumbnail"/>
    <script type="text/javascript">var _sf_startpt=(new Date()).getTime()</script>
    <link href="http://media.chicagomag.com/favicon.ico" rel="shortcut icon"/>
    <meta content="summary_large_image" name="twitter:card"/>
    <meta content="/images/cache/cache_b/cache_b/cache_5/01-Old-Oak-Trap-BLT-500-ef23e5bb.jpeg?ver=1482330137&amp;aspectratio=1.3333333333333" name="twitter:image"/>
    <meta content="/images/cache/cache_d/cache_4/cache_e/01-Old-Oak-Trap-BLT-500-a09efe4d.jpeg?ver=1482330137&amp;aspectratio=1.3333333333333" property="og:image"/>
    <meta content="Chicago magazine" name="twitter:app:name:ipad"/>
    <meta content="567851357" name="twitter:app:id:ipad"/>
    <meta content="Chicago magazine" name="twitter:app:name:googleplay"/>
    <meta content="com.chicagomag.chicagomag" name="twitter:app:id:googleplay"/>
    <meta content="1. Old Oak Tap BLT" property="og:title">
    <meta content="1. Old Oak Tap BLT" name="twitter:title">
    <meta content="article" property="og:type">
    <meta content="bikesarefun" property="fb:admins">
    <meta content="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Old-Oak-Tap-BLT/" property="og:url">
    <meta content="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Old-Oak-Tap-BLT/" name="twitter:url">
    <meta content="Chicago magazine" property="og:site_name">
    <meta content='"Truly inspired."' property="og:description"/>
    <meta content='"Truly inspired."' name="twitter:description"/>
    <meta content="@chicagomag" name="twitter:site"/>
    <script type="text/javascript">
          var articleID = '19495';
        var adcat = "restaurant";
    
      // Set the video variables
      var videoID = "Inside Best Restaurants";// Fallback
      
        // adcat: restaurant
                var videoID = "Inside Best Restaurants";
          
      
    </script>
    <script type="text/javascript">
    
      // Asynchronous
      var googletag = googletag || {};
          googletag.cmd = googletag.cmd || [];
    
      // Set some global variables so we can refresh these slots asynchronously
      var cube2 = '',
          leaderboard1 = '',
          leaderboard6 = '';
      (function() {
        var gads = document.createElement("script");
        gads.async = true;
        gads.type = "text/javascript";
          var useSSL = "https:" == document.location.protocol;
          gads.src = (useSSL ? "https:" : "http:") + "//www.googletagservices.com/tag/js/gpt.js";
          var node =document.getElementsByTagName("script")[0];
          node.parentNode.insertBefore(gads, node);
        })();
    
    </script>
    <script type="text/javascript">
      googletag.cmd.push(function() {
      googletag.defineSlot('/4011/trb.chicagomag/hp', [[300,250]],'div-gpt-ad-cube-1').setTargeting('pos',['1']).addService(googletag.pubads());
      cube2 = googletag.defineSlot('/4011/trb.chicagomag/hp', [[300,250]],'div-gpt-ad-cube-2').setTargeting('pos',['2']).addService(googletag.pubads());
      googletag.defineSlot('/4011/trb.chicagomag/hp', [[300,250]],'div-gpt-ad-cube-3').setTargeting('pos',['3']).addService(googletag.pubads());
      googletag.defineSlot('/4011/trb.chicagomag/hp', [[300,250]],'div-gpt-ad-cube-4').setTargeting('pos',['4']).addService(googletag.pubads());
      googletag.defineSlot('/4011/trb.chicagomag/hp', [[300,250]],'div-gpt-ad-cube-5').setTargeting('pos',['5']).addService(googletag.pubads());
      googletag.defineSlot('/4011/trb.chicagomag/hp', [[300,250]],'div-gpt-ad-cube-6').setTargeting('pos',['6']).addService(googletag.pubads());
    
      if (window.innerWidth > 727) {
        leaderboard1 = googletag.defineSlot('/4011/trb.chicagomag/hp', [[728,90]],'div-gpt-ad-leaderboard-1').setTargeting('pos',['1']).addService(googletag.pubads());
        googletag.defineSlot('/4011/trb.chicagomag/hp', [[728,90]],'div-gpt-ad-leaderboard-2').setTargeting('pos',['2']).addService(googletag.pubads());
        googletag.defineSlot('/4011/trb.chicagomag/hp', [[728,90]],'div-gpt-ad-leaderboard-3').setTargeting('pos',['3']).addService(googletag.pubads());
        googletag.defineSlot('/4011/trb.chicagomag/hp', [[728,90]],'div-gpt-ad-leaderboard-4').setTargeting('pos',['4']).addService(googletag.pubads());
        googletag.defineSlot('/4011/trb.chicagomag/hp', [[728,90]],'div-gpt-ad-leaderboard-5').setTargeting('pos',['5']).addService(googletag.pubads());
        leaderboard6 = googletag.defineSlot('/4011/trb.chicagomag/hp', [[728,90]],'div-gpt-ad-leaderboard-6').setTargeting('pos',['6']).addService(googletag.pubads());
      }
      else {
        googletag.defineSlot('/4011/trb.chicagomag/hp', [[320,50]],'div-gpt-ad-mobile-leaderboard-1').setTargeting('pos',['1']).addService(googletag.pubads());
        googletag.defineSlot('/4011/trb.chicagomag/hp', [[320,50]],'div-gpt-ad-mobile-leaderboard-2').setTargeting('pos',['2']).addService(googletag.pubads());
      }
    
      // googletag.defineSlot('/4011/trb.chicagomag/hp', [[160,600]],'div-gpt-ad-999449422057474470-6').setTargeting('pos',['1']).addService(googletag.pubads());
      // googletag.defineSlot('/4011/trb.chicagomag/hp', [[160,600]],'div-gpt-ad-999449422057474470-7').setTargeting('pos',['2']).addService(googletag.pubads());
    
      if (window.innerWidth > 599) {
        googletag.defineSlot('/4011/trb.chicagomag/hp', [[600,100]],'div-gpt-ad-600-100').setTargeting('pos',['1']).addService(googletag.pubads());
        googletag.defineSlot('/4011/trb.chicagomag/hp', [[600,400]],'div-gpt-ad-600-400-1').setTargeting('pos',['1']).addService(googletag.pubads());
        googletag.defineSlot('/4011/trb.chicagomag/hp', [[600,400]],'div-gpt-ad-600-400-2').setTargeting('pos',['2']).addService(googletag.pubads());
        googletag.defineSlot('/4011/trb.chicagomag/hp', [[600,400]],'div-gpt-ad-600-400-3').setTargeting('pos',['3']).addService(googletag.pubads());
      }
    
    
      googletag.defineSlot('/4011/trb.chicagomag/hp', [[336,392]],'div-gpt-ad-999449422057474470-9').setTargeting('pos',['1']).addService(googletag.pubads());
    
      googletag.defineSlot('/4011/trb.chicagomag/hp', [[300,100]],'div-gpt-ad-video-companion').addService(googletag.pubads());
    
      googletag.defineOutOfPageSlot('/4011/trb.chicagomag/hp', 'div-out-of-page').addService(googletag.pubads());
    
      // Custom chicago mag targeting
    
      googletag.pubads().setTargeting("adcat", "restaurant");
      googletag.pubads().setTargeting("pub", "chi");
      googletag.pubads().setTargeting("pubID", "100");
      googletag.pubads().setTargeting("videoID", videoID);
      googletag.pubads().collapseEmptyDivs();
      
                googletag.pubads().setTargeting("articleID", "19495");
          
                      
      googletag.enableServices();
    
      });// End googletag.cmd.push()
    
    </script>
    <!-- End: GPT -->
    <!-- responsive -->
    <script language="Javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.8.1/jquery.min.js" type="text/javascript"></script>
    <script type="text/javascript">
    if (typeof jQuery == 'undefined') {
    	document.write(unescape("%3Cscript src='/core/media/js/jquery-1.8.1.min.js' type='text/javascript'%3E%3C/script%3E"));
    }
    </script>
    <script type="text/javascript">
    var rjQuery = jQuery.noConflict(); // leave the $ variable alone
    </script>
    <script language="JavaScript" src="/theme_overrides/Respond/js/site-search.js?ver=1386069772" type="text/javascript"></script>
    <script language="JavaScript" src="/core/media/js/base.js?ver=1473876728" type="text/javascript"></script>
    <!--[if lt IE 9]>
    <script  type="text/javascript" language="JavaScript" src="/core/media/themes/Respond/js/html5_shiv.js?ver=1473876729"></script>
    <![endif]-->
    <script language="JavaScript" src="/core/media/js/jquery.browser.js?ver=1473876728" type="text/javascript"></script>
    <script language="JavaScript" src="/theme_overrides/Respond/js/jquery.iframe-auto-height.plugin.1.8.0.js?ver=1446756854" type="text/javascript"></script>
    <link href="/lessc/35a3627424516b7ccf4314a41b92358c.one.css?ver=1540232190" rel="stylesheet" type="text/css"/>
    <link href="/lessc/35a3627424516b7ccf4314a41b92358c.two.css?ver=1540232190" rel="stylesheet" type="text/css"/>
    <script language="JavaScript" src="/theme_overrides/Respond/js/functions.js?ver=1483891934" type="text/javascript"></script>
    <link href="/core/media/css/responsive_ads.css?ver=1473876728" rel="stylesheet" type="text/css"/>
    <link href="/theme_overrides/Respond/css/sandwiches.css?ver=1473559134" rel="stylesheet" type="text/css"/>
    <!--[if IE 9]>
    <link rel="stylesheet" type="text/css" href="/core/media/themes/Respond/css/ie9.css?ver=1473876729">
    <![endif]-->
    <!--[if lt IE 9]>
    <link rel="stylesheet" type="text/css" href="/core/media/themes/Respond/css/ie8.css?ver=1473876729">
    <![endif]-->
    <script type="text/javascript">RivistaJSString.put({
    
    
    
    
    "_":""});</script>
    <!--[if lt IE 9]>
    <link rel="stylesheet" type="text/css" href="/theme_overrides/Respond/css/chimag-ie8.css?ver=1443881828">
    <![endif]-->
    <!--[if gt IE 7]>
    <link rel="stylesheet" type="text/css" href="/theme_overrides/Respond/css/chimag-ie.css?ver=1381349768">
    <![endif]-->
    <script>
    var ad_category = "Restaurants and Bars";
    </script>
    <script>
    window.editorIdMap = new Object;
    editorIdMap['120'] = {'mid': 1425,'view': 'edit_report'};
    editorIdMap['120'] = {'mid': 1426,'view': 'edit_report'};
    editorIdMap['122'] = {'mid': 1431,'view': 'edit_gateway'};
    editorIdMap['77'] = {'mid': 1350,'view': 'edit_config'};
    editorIdMap['11'] = {'mid': 1387,'view': 'edit_user'};
    editorIdMap['80'] = {'mid': 1423,'view': ''};
    editorIdMap['80'] = {'mid': 1421,'view': ''};
    editorIdMap['60'] = {'mid': 1328,'view': ''};
    editorIdMap['60'] = {'mid': 1327,'view': ''};
    editorIdMap['136'] = {'mid': 1491,'view': 'edit_comment'};
    editorIdMap['60'] = {'mid': 1322,'view': ''};
    editorIdMap['138'] = {'mid': 1492,'view': ''};
    editorIdMap['133'] = {'mid': 1488,'view': ''};
    editorIdMap['19'] = {'mid': 81,'view': 'edit_moduledefinition'};
    editorIdMap['21'] = {'mid': 694,'view': 'edit_module'};
    editorIdMap['16'] = {'mid': 1386,'view': 'edit_group'};
    editorIdMap['128'] = {'mid': 1479,'view': 'edit_issueperiod'};
    editorIdMap['79'] = {'mid': 1333,'view': 'edit_publication'};
    editorIdMap['78'] = {'mid': 1334,'view': 'edit_issue'};
    editorIdMap['97'] = {'mid': 1357,'view': 'edit_content'};
    editorIdMap['94'] = {'mid': 1388,'view': ''};
    editorIdMap['17'] = {'mid': 1389,'view': 'edit_menu'};
    editorIdMap['72'] = {'mid': 1422,'view': 'edit_product'};
    editorIdMap['2'] = {'mid': 1332,'view': ''};
    editorIdMap['2'] = {'mid': 1339,'view': ''};
    editorIdMap['60'] = {'mid': 1329,'view': ''};
    editorIdMap['98'] = {'mid': 1336,'view': 'edit_survey'};
    editorIdMap['85'] = {'mid': 1338,'view': 'edit_newsletter'};
    editorIdMap['86'] = {'mid': 1337,'view': 'edit_gallery'};
    editorIdMap['123'] = {'mid': 1473,'view': 'edit_calendar'};
    editorIdMap['140'] = {'mid': 1493,'view': 'edit_menu'};
    editorIdMap['116'] = {'mid': 1406,'view': 'edit_wrapper'};
    editorIdMap['118'] = {'mid': 1419,'view': 'edit_form'};
    editorIdMap['132'] = {'mid': 1489,'view': 'edit_geobase'};
    editorIdMap['134'] = {'mid': 1490,'view': 'edit_videochannel'};
    editorIdMap['130'] = {'mid': 1480,'view': 'edit_pageposition'};
    editorIdMap['117'] = {'mid': 1418,'view': 'edit_campaign'};
    editorIdMap['69'] = {'mid': 1424,'view': 'edit_category'};
    editorIdMap['115'] = {'mid': 1335,'view': 'edit_page'};
    editorIdMap['60'] = {'mid': 1325,'view': ''};
    editorIdMap['121'] = {'mid': 1430,'view': 'edit_shipcarrier'};
    editorIdMap['2'] = {'mid': 1340,'view': ''};
    editorIdMap['144'] = {'mid': 1495,'view': 'edit_recurring'};
    editorIdMap['146'] = {'mid': 1499,'view': 'edit_section'};
    editorIdMap['148'] = {'mid': 1501,'view': ''};
    editorIdMap['153'] = {'mid': 1505,'view': ''};
    editorIdMap['154'] = {'mid': 1506,'view': ''};
    editorIdMap['155'] = {'mid': 1507,'view': ''};
    editorIdMap['115'] = {'mid': 1360,'view': 'edit_page'};
    editorIdMap['126'] = {'mid': 1478,'view': 'edit_customtable'};
    editorIdMap['147'] = {'mid': 1500,'view': ''};
    editorIdMap['150'] = {'mid': 1502,'view': ''};
    editorIdMap['149'] = {'mid': 1503,'view': ''};
    editorIdMap['131'] = {'mid': 1481,'view': 'edit_tag'};
    editorIdMap['145'] = {'mid': 1496,'view': 'edit_redirect'};
    editorIdMap['143'] = {'mid': 1494,'view': 'edit_creditcardtype'};
    editorIdMap['156'] = {'mid': 1510,'view': ''};
    editorIdMap['119'] = {'mid': 1420,'view': 'edit_user'};
    editorIdMap['126'] = {'mid': 1512,'view': 'edit_customtable'};
    editorIdMap['158'] = {'mid': 1513,'view': ''};
    editorIdMap['157'] = {'mid': 1511,'view': ''};
    var pagetype = 'article';
    var admin_edit_itemid = '19495';
    var admin_edit_text = 'Edit Article';
    var admin_edit_link_extras = '';
    editorIdMap['article'] = {'mid': 1335,'view': 'edit_page'};
    editorIdMap['content'] = {'mid': 1360,'view': 'edit_page'};
    editorIdMap['cde'] = {'mid': 1512,'view': 'edit_customtable'};
    editorIdMap['cde_record'] = {'mid': 1512,'view': 'edit_customdata'};
    editorIdMap['evergreen'] = {'mid': 1335,'view': 'edit_page'};
    editorIdMap['issue'] = {'mid': 1360,'view': 'edit_page'};
    editorIdMap['publication'] = {'mid': 1360,'view': 'edit_page'};
    </script>
    <script src="/theme_overrides/Respond/js/edit_from_site.js?ver=1446828087"></script><link href="/core/media/css/edit_from_site.css" rel="stylesheet" type="text/css"/></meta></meta></meta></meta></meta></meta></meta></link></link></meta></meta></meta></head>
    <body class="land long tpl-article din " id="page_htmlid_19495">
    <script>
      var timestamp = 'Dec%2029%202018%2000:44';
    </script>
    <style>
    /*--- Mobile Reveal bar ---*/
    #reveal-bar-mobile {
      background: rgba(255,255,255,.95);
      border-bottom: 1px solid #aaa;
      display: none;
      padding: 11px 10px 9px 0;
      position: fixed;
      width: 100%;
      -webkit-transform: translate3d(0,-80px,0);
      -moz-transform   : translate3d(0,-80px,0);
      -o-transform     : translate3d(0,-80px,0);
      -mstransform     : translate3d(0,-80px,0);
      transform        : translate3d(0,-80px,0);
      transition       : 1s ease-in-out;
      z-index          : 99999;
    }
    #reveal-bar-mobile.reveal-bar-show {
      -webkit-transform: translate3d(0,0,0);
      -moz-transform: translate3d(0,0,0);
      -o-transform: translate3d(0,0,0);
      -mstransform: translate3d(0,0,0);
      transform: translate3d(0,0,0);
    }
    #reveal-bar-mobile .reveal-share-buttons {
      float: right;
      margin-right: 10px;
      /*width: 72px;*/
    }
    #reveal-bar-mobile .reveal-share-buttons li {
      margin: 0 5px;
    }
    #reveal-bar-mobile .reveal-share-buttons li a {
      height: 30px;
      width: 30px;
      border-radius: 15px;
      padding-top: 6px;
    }
    #reveal-bar-mobile .reveal-share-buttons li i {
      font-size: 18px;
    }
    
    button#reveal-bar-mobile-sections-button {
      background: none;
      color: #666;
      font-size: 16px;
      font-style: normal;
    
      margin: 0;
      padding: 8px 10px 5px;
    }
    
    .reveal-bar-mobile-section {
      width: 100%;
    }
    
    .reveal-bar-mobile-section-link,
    .reveal-bar-mobile-section-link:hover,
    .reveal-bar-mobile-section-link:active,
    .reveal-bar-mobile-section-link:visited {
      background: #222;
      border-bottom: 1px solid #aaa;
      -moz-box-sizing: border-box; -webkit-box-sizing: border-box; box-sizing: border-box;
      color: #f0f0f0;
      display: table-cell;
      font-family: "proxima-nova",sans-serif;
      font-size: 20px;
      padding: 0 10px;
      vertical-align: middle;
      width: 100%;
    }
    ..reveal-bar-mobile-section-link:on
    .reveal-bar-mobile-section-link.reveal-bar-mobile-section-link-close {
      font-size: 16px;
      font-weight: 100%;
      padding-right: 20px;
      /*text-align: right;*/
      text-transform: uppercase;
    }
    
    #reveal-bar-mobile-sections {
      display: none;
      position: fixed;
      top: 20px;
      -webkit-transform: translate3d(0,-800px,0);
      -moz-transform   : translate3d(0,-800px,0);
      -o-transform     : translate3d(0,-800px,0);
      -mstransform     : translate3d(0,-800px,0);
      transform        : translate3d(0,-800px,0);
      transition       : .5s ease-in-out;
      width: 100%;
      z-index: 998;
    }
    #reveal-bar-mobile-sections.show {
      -webkit-transform: translate3d(0,0,0);
      -moz-transform   : translate3d(0,0,0);
      -o-transform     : translate3d(0,0,0);
      -mstransform     : translate3d(0,0,0);
      transform        : translate3d(0,0,0);
    }
    
    @media only screen and (max-width : 450px) {
      #reveal-bar-mobile {
        display: block;
      }
    }
    </style>
    <div id="reveal-bar-mobile">
    <button id="reveal-bar-mobile-sections-button">More →</button>
    <ul class="reveal-share-buttons list-no-bullets clearfix">
    <li class="fb"><a href="https://www.facebook.com/sharer/sharer.php?u=http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Old-Oak-Tap-BLT/%3Futm_source=facebook.com%26utm_medium=referral%26utm_campaign=Facebook%20mobile%20reveal%20bar%2019495%20Dec%2029%202018%2000:44" onclick="ga('send',  'event', 'Sharing', 'Facebook share from mobile reveal bar','1. Old Oak Tap BLT'); return socialPopup('https://www.facebook.com/sharer/sharer.php?u=http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Old-Oak-Tap-BLT/%3Futm_source=facebook.com%26utm_medium=referral%26utm_campaign=Facebook%20mobile%20reveal%20bar%2019495%20Dec%2029%202018%2000:44');" target="_blank"><i class="icon-facebook"></i></a>
    </li>
    <li class="tw"><a href="https://twitter.com/share?text=1.+Old+Oak+Tap+BLT&amp;url=http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Old-Oak-Tap-BLT/" onclick="ga('send',  'event', 'Sharing', 'Tweet story from mobile reveal bar','1. Old Oak Tap BLT'); return tweetPopup('1.+Old+Oak+Tap+BLT','/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Old-Oak-Tap-BLT/','mobile+reveal+bar+19495');" target="_blank"><i class="icon-twitter"></i></a></li>
    </ul>
    </div>
    <ul class="list-no-bullets" id="reveal-bar-mobile-sections">
    <li class="reveal-bar-mobile-section"><a class="reveal-bar-mobile-section-link" href="/dining-drinking" onclick="ga('send',  'event', 'Mobile reveal bar', 'Dining');">Dining &amp; Drinking</a></li>
    <li class="reveal-bar-mobile-section"><a class="reveal-bar-mobile-section-link" href="/city-life" onclick="ga('send',  'event', 'Mobile reveal bar', 'City Life');">Politics &amp; City Life</a></li>
    <li class="reveal-bar-mobile-section"><a class="reveal-bar-mobile-section-link" href="/real-estate" onclick="ga('send',  'event', 'Mobile reveal bar', 'Real Estate');">Real Estate</a></li>
    <li class="reveal-bar-mobile-section"><a class="reveal-bar-mobile-section-link" href="/Chicago-Home" onclick="ga('send',  'event', 'Mobile reveal bar', 'Home and Garden');">Home &amp; Garden</a></li>
    <li class="reveal-bar-mobile-section"><a class="reveal-bar-mobile-section-link" href="/style-shopping" onclick="ga('send',  'event', 'Mobile reveal bar', 'Style Shopping');">Style &amp; Shopping</a></li>
    <li class="reveal-bar-mobile-section"><a class="reveal-bar-mobile-section-link" href="/video" onclick="ga('send',  'event', 'Mobile reveal bar', 'Video');">Video</a></li>
    <li class="reveal-bar-mobile-section"><a class="reveal-bar-mobile-section-link reveal-bar-mobile-section-link-close" href="javascript:void(0);" onclick="jQuery('#reveal-bar-mobile-sections').removeClass('show');"><i class="icon-remove"></i></a></li>
    </ul>
    <script>
      var mobile_sections_el = jQuery('#reveal-bar-mobile-sections');
      if (document.documentElement.clientWidth <= 400) {
        mobile_sections_el.show();
      }
      mobile_sections_el.css('top',jQuery('#reveal-bar-mobile').outerHeight() - 1)
      jQuery('.reveal-bar-mobile-section-link')
        .height((jQuery(window).height() - jQuery('#reveal-bar-mobile').height() - 50) / 7 - 2)
        .width(mobile_sections_el.width())
      jQuery('#reveal-bar-mobile-sections-button').on('click', function(){
        mobile_sections_el.toggleClass('show');
      })
    </script>
    <div class="hidden-phone" id="reveal-bar">
    <div class="container">
    <a class="logo background-logo-svg" href="/">Chicago magazine</a>
    <div class="input-append">
    <form action="http://www.chicagomag.com/search?from=revealbar&amp;" id="reveal-bar-search" name="revealBarSearch"><input class="text clean" id="reveal-bar-input" name="q" placeholder="Search" type="text"/>
    <button id="reveal-bar-button"><i class="icon-search"></i></button>
    </form>
    </div>
    <div id="article-reveal-share">
    <span class="article-reveal-share-intro">Share this story</span>
    <ul class="reveal-share-buttons list-no-bullets clearfix">
    <li class="fb"><a href="https://www.facebook.com/sharer/sharer.php?u=http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Old-Oak-Tap-BLT/%3Futm_source=facebook.com%26utm_medium=referral%26utm_campaign=Facebook%20reveal%20bar%2019495%20Dec%2029%202018%2000:44" onclick="ga('send', 'event','Sharing', 'Facebook share from reveal bar','1. Old Oak Tap BLT'); return socialPopup('https://www.facebook.com/sharer/sharer.php?u=http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Old-Oak-Tap-BLT/%3Futm_source=facebook.com%26utm_medium=referral%26utm_campaign=Facebook%20share%20reveal%20bar%2019495%20Dec%2029%202018%2000:44');" target="_blank"><i class="icon-facebook"></i></a>
    </li>
    <li class="tw"><a href="https://twitter.com/share?text=1.+Old+Oak+Tap+BLT&amp;url=http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Old-Oak-Tap-BLT/" onclick="ga('send', 'event','Sharing', 'Tweet story from reveal bar','1. Old Oak Tap BLT'); return tweetPopup('1.+Old+Oak+Tap+BLT','/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Old-Oak-Tap-BLT/','reveal+bar+19495');" target="_blank"><i class="icon-twitter"></i></a></li>
    <li class="eml"><a href="#" onclick="window.open('/core/pagetools.php?url=%2FChicago-Magazine%2FNovember-2012%2FBest-Sandwiches-in-Chicago-Old-Oak-Tap-BLT%2Findex.php&amp;mode=email','emailThisPage','status=no,scrollbars=yes,resizable=yes,width=420,height=540'); ga('send', 
          'event','Sharing', 'Email story from reveal bar','1. Old Oak Tap BLT'); return false;" url="email this page"><i class="icon-envelope"></i></a></li>
    </ul>
    </div>
    </div>
    </div>
    <a class="edit_from_site" content_id="interstitial-newsletter" href="#" media_id="83">Edit Module</a>
    <style>
      
    
    #cta-backdrop {
      background: #fff;
      opacity: .97;
      display: none;
    }
    .modal#cta-modal {
      background: #eee;
      margin-top: 0;
      display: none;
      box-shadow: 3px 3px 12px rgba(0,0,0,0.2);
      overflow: auto;
      border-top: 8px solid #000;
    }
    .modal-close {
      background:none;
      color: #ff0000;
      font-weight: 300;
      font-size: 20px;
      font-style: normal;
      opacity: 1;
      text-align: right;
      width: 100%;
    }
    .modal-close:hover {
      background: none;
      color: black;
      opacity: 1;
    }
    #cta-modal p.cta-p {
      color: #333 !important;
      font-size: 18px;
      line-height: normal;
      text-align: center;
      margin-bottom: 15px;
    }
    .btn-nothanks, .btn-nothanks:hover {
      border: none !important;
      background: none !important;
      color: #666 !important;
    }
    #cta-modal .modal-body {
      padding: 30px;
      height: auto;
      max-height: none;
    }
    #cta-modal label {
      color: #333;
      text-align: center;
      margin-top: 5px;
      margin-bottom: 5px;
    }
    h2.cta-h2 {
      font-size: 45px;
      text-align: center;
      margin-bottom: 10px;
    }
    #cta-modal .btn-large {
      width: 200px;
      -moz-box-sizing: border-box; -webkit-box-sizing: border-box; box-sizing: border-box;
    }
    .cta-buttons {
      width: 200px;
      margin: 15px auto 0;
    }
    #cta-form {
      /*text-align: center;*/
      width: 200px;
      margin: 10px auto;
    }
    #cta-form input[type=text] {
      -moz-box-sizing: border-box; -webkit-box-sizing: border-box; box-sizing: border-box;
      height: 40px;
      width: 200px;
    }
    .cta-neveragain {
      text-align: right;
      font-size: 10px;
      text-transform: uppercase;
      text-decoration: none;
    }
    .cta-neveragain a {
      color: #999;
    }
    .cta-neveragain a:hover {
      color: #666;
    }
    #cta-newsletters-outer {
      max-width: 300px;
      margin: 0 auto;
    }
    a.cta-checkbox {
      padding-left: 25px;
      padding-bottom: 10px;
      background-repeat: no-repeat;
      background-position: 0 2px;
      background-image: url('http://media.chicagomag.com/img/checkbox-check.png');
      cursor: pointer;
      color: #000;
      display: block;
      font-size: 16px;
      margin-bottom: 5px;
      transition: none;
    }
    a.cta-checkbox:hover {
      color: #ff0000;
    }
    a.cta-checkbox-yes {
      background-position: 0px -83px;
      color: #666;
    }
    a.cta-checkbox-yes:hover {
      color: #666;
    }
    #cta-modal .form-error {
      margin-bottom: 15px;
    }
    #cta-modal .checkbox input[type="checkbox"] {
      /*top: 7px;*/
      margin-left: initial;
    }
    
    @media screen and (max-width: 420px) {
      .modal#cta-modal {
        border-top: none;
      }
      #cta-modal .modal-body {
        padding: 8px;
      }
      h2.cta-h2 {
        font-size: 30px;
      }
      a.cta-checkbox {
        padding-bottom: 3px;
      }
    }
    
    
    @media screen and (min-width: 420px) {
      .modal#cta-modal {
        /*Logo, 300x60, #eee background*/
        background-image: url('data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAASwAAABQCAMAAACUNLhJAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAutQTFRF7+7uIx8gwcHD6urqJiIj7Ovr5eTl5uXl1tbXTExOSEhKiYqN7ezs2dnZcnN1SkpMTU1PKCUmmpud4uLidXZ4t7i6l5ia5OPjjpCS2trb6OjoRERGJCEiQkFDOTg6JyQl4ODhzc7PKSYn5+bmnZ6ghYeJ5eTkNTM1lJaY4eHhKygp1tXWyMnKzMzOmJmbnZ+hl5mbfX+B0NDRvr/A6enp39/f0tLSp6iqm5yeRkZIUlNVQ0JELCkqQUBC5+fn3NzdQ0NF1NTVu7y+0dHSp6mrq62uy8zNSUlLiImL1tfXq6uts7S1xsbHhIaI29vcubq7nJ2fn6CjWlteLSssjY+RUlJUNzY3wsLD19fYlJWY4+PjODc4Pz5AuLm7cHF0yMjJ3t7fPDs8qaqssbK0fX6Azs/Q3d3esLGzkZOVTk5QwsPET09Rr7Gyx8fJV1haU1RWcXJ0q6yu1dXVzM3OrK2v09LT4+PiwMHCVVVXiIqMkZKVr7CyqqutxMTGra6w3d3doKKkVFVWo6SmXV5gMzEyamttb3Bylpea2draysvMY2Nl2NjZpaepRUVHh4iKqKqsLy0vmZqcycrLxsfIrq+xu72+Pj0+oKGjhoeKtre4R0dJlZeZQD9BpKaokpSWdHV3uru9X2Fji4yOycnLZmdpv8DBsrO1WltdVldZYGFk09PUUVFTjY6QnqCiWVpctLW3uru8goSGUFBSfn+Cg4SHOjk7eHp8ZWZpe31/X2BiLiwtSktNpKWnz8/Rdnd6kJGUbG1wgIGDPTw9S0xOjI2PoqSms7S24ODgMTAxpqepbG1vbm9xaGpsxcXGgIKEj5GTk5SXgYOFt7i5NjU2tba4dXd5MC8wg4WHd3h6Xl9ha2xui42PYWJkgIKFvr7AZGVneXp9eHl7bW5xaGlrW1xeVVZYvb7AWFlbw8TFYmJkcnR2XF1fiouNc3V3f4CCoaOlZGRmZ2hqc3R3b3FzNDIzent9vL2/e3x+aWptop1pYQAADf1JREFUeNrsW2dYU8kanhBiVIJKKIkYJUKAoPQioAJSpUhTkCZiBwULKBZEFFCx9y5i77p2V117W1d37W1t23svt/y85wQyM6cfDD73uZe8vzhnvsx8552Zr80AgBlmmGGGGWaYYYYZZphhhhlmmGGGGWaYYYYZZpjx34DLmFVHa9TJx+L1iWtHfyg1vlZIZQZIZcJdyIyyCtbm3J4Whd5Wx55l6JJmJag4e2kcUXg0x2FeUTENGx4kq3VJWWUeCj7ZwKDRa2OKXB9YKZMGWLcxmSu372N2nLk9MPP49O2T/C5VxRk1L/fqboBXdCCT36mqRrgZuHWMNsr2YvvWtvbKY3aLvjl+cO+ui8m6bRFcmvhP7t59cndHwcktSDw0av6nE4szB+66eHJlkj/fLK6KvTTqTm3v4n2fXXRdH2Br2qpKvVt9SoLBKWx/gz/5xfKBYV0NCPtpAf1X0g0HexuQWe1JPidA2b2MyUvwedF+BT7C3HZbS9zYdHnTxdDHb/wKRz2bsE6D99e336tLO1lXl8Lzeu0QS0z00cETRx3fliprn314X0YMqiDJ2mR87LKYQdZtKBtAPvfsanwMpZH1id9glgE0mV5MZaTzGhunu3Er3Dl9ghNLf5Jzt+MYsvLHL3owJZ2+XhL0NlSp1mdK2PEjMVHyTsanjtaM7+pgbLO0MOyz9sbndhSyVp1ewTHCt0x1AuY2Njkv51xVNdUSLoTQhb+dxyVaO7P5XI37lHPkUaAlyFIoh3AN4Dyeqc8oY+MujqXV/SsJNz6iytq6unPL9njSs5lcpQ/m7u1kS5CVPYl7gL6jGfpcGA77jGJVeE8/Hq4kU6iuYr+EF2OzmkOV4w6+vtQtQNbob3gGmMuc2uuo9S82jdev4NPYMgmXnfWBRADr9jSDLDVvVz6mkxWZyTdA2DB6l0F9sG3yCVPhj5x5NbZMxWTjQiWCcD8qmqs5tJ8+uvbd2e2134ytP2d4DDaZLNvtNP/XtfdP288OnNixcfMfd6F3qcSlOzAigSP0dbWumND40wnFYYMMjzYWSDZlOlXUpkt17f6BxVepb5cuFMlVFsX6Of3bNyCFCD8U8qCFj/VDCascaypZgVcoivXzK52sIoK3QFX2tOXJX1tK3qPHuVrKB7oPoDX3otqr/idGFGhJjV20XkcchoYRixHFIvKhFNlOJ2f6t1EAqXb1lq0UM71XK4orj+/wH328luLupf7eEwtNJavEBhvg1L1yyq+HBTyZRw8Nl1Pjvde06PJLvLFLCNXiSaPXT3eKRMYNl613SMAkd57Bw1lXUWT54qZ2JTNByfUwkazcYmyEgZ5MDWbTBg1/n7pH6qMpzVU4989Z/L7teZhD9WyPye6ie92ZXbD1u1sEV4v7Y7OexCVlClkrMX0vR4hQKaoHiz+GmPoKi9DuC3SVgfXyxIPRvBCbxw4uwprpML/DHcyaQJYW84Q/eIhZ60MZ7nI21roR26NFAj2lYIHwHbYqw2S0tvp+L6iYtBZ1ZwXeBVkj0Tq56imGq7i+DNfujTWfRq+HNsPPd0xglViDgpANgvWgVUizg2PEkHWNmXna8ZJ1TPxCaAT0nYv2Gf+ajnzVsHoUMEwWKqyhlMj5BrtIuB3yqpEC3bXBDIoOiCGr381ZFhSMm/YZH1lB78EBQkW552hYtMi/AeOZYNj8uBkOLBJtsvfkHDIWMHDSxAp01xalbKHZosiSONnQoeEjK+5v+MuXohZWBYyJPCJhKPQ7/NZDyH9ZCHVVgvaYklMIud4TAt1ZTEDeAIgjiy/NYCHrJvIf08RwlTLWKL8MBP4Jd5G9sf1L4cXCEhXNPcApFAKF9gt0F9Oe1Yi2IFmHkYnNFkMW3Hp/E50lwuLeC2POj2bXD3cyszvTYE04Xj8oO0HKOWABXH79BVQ7lCdQCjGZrHiUHIg5IXCBmc4iIiUcA5eZU1MZRQvfSPS4ofvAvRsVg/yAAsUg/+IeMQIW0pcK6HZmEEwwPd8NWSdg60UxC6sEis8hH5Pho11TSA7r25IYvP6Vx1DmFxD4Aj7M4Pk0SH+ogG5PnHkqcC1CFix5Sv4hgisZNLdXDbH+apjaDG9Mji/AJNryCF7fG85Qxg6Ef8yeA9DCxL1iyfoCRow9vN41WadFkJUEw774RvJ+oC2OaBgNaNYIkeWICu87uIcM7y2WrOS5cBvOejdkoZi0g/CJqcuP0H011eW3oJKTIWVegMqeaUJkKebDh7vcYwaFiSVLCSU1ieLICqvcZk9B6sbtfGTdR65+mCBZA6AJvd1Utmm7FP7e13AIlclqh1jJAl/Ah0XcExV9zii0SUA7++Owv2RxZLXTNi/dqYQDdBstSBb0Bpbnja9+RekImYUr0CnUV/hZjzMbWRUoK+WeqClQaJ+AdpMXoUpTG1FkNTeR3o3OQYOFuCp/ZBT9oDOczhWUwwDwOaq4YoFmyoZRfiTuPs/DyEJFRE0Jd0AAO9wqoN6HrlB0xTYgxr82lyzM1/8hRFY8W9p3Fr7MHEMpz1vOYbNA0zGyCoYLFyiwep5SqEKDFqHkcx65qf3flqw2yCU5C+Q7neEPbaaU92pC2TOkIbk3s9BK/YGlXJcyESPLDZYtJHkDBOt5c1cLTWYEIv9WKreYbb+3rmfp0cd+puLVJQRl5F37GNEPM92vphIWHvllzRJmHwt641GsFVYIDWcdMwEtrMtTBW0qVksby5282XZ5a7I88yRiYkMik9krFJo4bSTEZmAnYN0FyFqICo+W7JtsK+otVpArEIWV/xfNfgdkgTtYIHZYVKbDiY8p4QWRH/fkJwvMxy4JsFwxkSErKakeI0yW2zyBsxeTyQrApqOHmrOuIl0kTJbzbiJw/QV7UW3BZ7OIsZ8i2aeMtaV9jc2jj5jUddst/GjXm34dLvBojItpZIGL+PfumkY/YJ6dbjigj7IUkSNMIgnAD4XzGqhl7rJrFLJkfvhB+FZKHVqxrRa/eiTu1uSvFHWOO8TlGltUbY+q9zrNl5pIVnk95YrPl2uyjdUlaZBX8Bf1koesZzpsGEw6tQeUV+3qLhj9htto5UQNtUxRRrkV4v7a4kOj6z0yCQ9kz40Td3wfMZF2S+L3K/d16bq6Q5+/b5im5yZuQwBqaPF1p6FWKw/7KBtcJ/2UZ6xVjR8shixDABZxkPru1K6Tel16SM7rP9ZRI3gSI6lnRc5nr/j+VpNe8Zx2E65O7MUQT967TobLbKaRhdtRNktUQKlc8aIPadJnDREhacdy4s4JP6noazRRvIOfNJksoLjCN8AgImmMRBO2z4EOPTabOWR/+afEkwWKhI3haTkQjyy+CkyG6WSBwB0a7gFuRQJQhx7XMPW7h1pDDQb9zSbxZIGQW/ySGtfmXYk/cJm7r7QWIAuASu47ZUMigAc6OBnbmaneQnf65drJ+8WTBY7yVuPyzoNmwtGnP1dnW1qELBBp95RjgGtuIF3CWy53xILLiY1Zia36qmiyQIprN04bsDUONB8J9zj4H0Ehqz8PWZpGsjaxX+0G+ZP6sg6QKVOhY2t3Vh++BKXPNsaryKv92B3o5fksBZfvz7BeWNbMuwneDglp+wfRe7Op1pMnBx5wZpYyLlgE3qFeaI+EF0L70K5lSwPuhjKsrfukVKx4LHnF6pesMRu1HZYfd85oZ0NfKO9XtlloyEb/pAW/Bcv60//FoJtdqhy8NaRePqcndByywsZS49S3W+jx+RVZjb255CyzMsC1jvEfL7LKZ41t8VZlhhrZZtfG52UOzP/20ObvuFy8dHAPJ42lzdP6jgNHBfckPqrygVUTlnEcXya6QomX2NrO3V0x/2Bot75Ed86Dl/b+URknI3xvlVKn82bWUDzWvpxX3OWRM/Fx54Z0/PpE4mJgKsKzV9mvKS2tShp3wAO8E2jLp+2Zkhg7M3+VtbQFurM9MG5PVWJs4e7yXBFjR78pjC2terzTWgbM+L+CYS0ZC3G5Y7RNE2zY7eFyuVwlJ20N/EdDmZtc7tjaOJrSdPU/hsyEp+oMhbMxSd5FdZVeJDmdlSR/G0N85myJyfXcKJ96mPAj2j09QYFu/Ur7VrZjZOd9DbW11Rnkf9mN9yXjA+vNVsH/rMqoIclKTSOjHYvYksPLHjquafDQKkeEgwXq8cB+Q3BplqKVbb4k8uvBYt8tswhvlbhheTiQ3vh5FWmeyVBEPif5SOPymVk3DIz0trUdsTkKBCk9gX2RW6uzVIqSv9RrgTz4ZLwnANm+ys1tQXTRSHRGmuOtNByD+qvzAUGWh1vIMe+2KoKsbRW7B5S1NrKmvFyT0StVXXh/PABROSnKm2DAdX/iPQEZcAxOs75H3rVwHJGjIsmydVMeqZkz28cTvNns0LCxtZEVqw5Kz/jZPvvSeKByKAH2l6SrD3mBwErd4ZoRqojrs8AIJbEPo+NJk0aS5RuwIOejtJ0gX12matPqyPoZRKp1uZGEzfbPqLH30S+0LloCFHuCE11DcqdlBNv7OJQB2fp0FyNZqWBavDoO5BdpW5/NKrVyBBaRIDpjNYipS1OmKZeAqhkj5TLpONcAxco0nTLEYSbYeWi8QioDhb62bg4biT2ZWADyK2ZLW1usrVjuaggt/a0KFidHSWWym9d7SYN3qEP0M2Jl4+I9ZTJpjD7bR71ZqX8IZuZ4qBoKiThMvY0IHXw3F0pbGVtr5xj+qXDxlvLuPuTtl143LoCUmBy9fkkECAgmPeGA0jhdeogy5yGwmKJyKSWrPUvWgix9mr4w0Jz4/A/gPwIMAPwpG5QV/gbUAAAAAElFTkSuQmCC');
        background-position: top center;
        background-repeat: no-repeat;
        background-size: 200px;
      }
      #cta-modal-content {
        padding-top: 40px;
      }
    }
    
    
      
    </style>
    <script>
    var which = '';
    </script>
    <div aria-hidden="true" class="modal-backdrop" id="cta-backdrop" style="display: none;"></div>
    <div class="modal" id="cta-modal" style="display: none;">
    <div class="modal-body">
    <button aria-hidden="true" class="close modal-close cta-close" data-dismiss="modal" id="cta-close" type="button">X</button>
    <div id="cta-modal-content"></div>
    </div><!--/.modal-body-->
    </div><!--/.modal-->
    <style>#print-only-logo {display: none;}</style>
    <img alt="" height="129" id="print-only-logo" src="http://media.chicagomag.com/img/chicagomag-logo-black-with-magazine.svg" width="573"/>
    <!-- Temporary peeking refer — use sparingly, for last-minute promos sitewide. Also removes red "punch" from #global-nav-subscribe Subscribe button, so that this call to action is more defined. -->
    <!-- 
    <style>
    a#peek {
      box-sizing: border-box;
      display: block;
      background: red;
      height: 0;
    
      color: red;
      text-align: center;
      font-weight: 400;
    }
    
    a#peek:hover {
      color: white !important;
      cursor: pointer;
      cursor: hand;
      background: #dd0000;
    }
    
    a#peek.show {
      height: 48px;
      padding: 10px 5px;
      border-bottom: 8px solid black;
      color: white !important;
    }
    
    #global-nav-subscribe {
      background    : none;
      border-radius : 0 !important;
      color         : #aaa !important;
      display       : initial !important;
      padding       : initial !important;
    }
    
    ul.member-nav li a#global-nav-subscribe:hover {
      border-bottom: 1px solid #ff0000;
      background: initial !important;
    }
    
    @media (max-width: 767px) {
      a#peek {
        display: none;
      }
    }
    
    </style>
     -->
    <!-- 
    <a id="peek" href="/Chicago-Magazine/Promotion/Secret-Supper/Purchase-Tickets/" onclick="ga('send', 'event', 'Global nav', 'Top peeking refer');">
      Last call for Secret Supper tickets! Click here for more info.
    </a>
    
     -->
    <!-- slide down temporary peeking refer -->
    <!-- 
    <script>
    
    jQuery( document ).ready(function() {
      if (document.documentElement.clientWidth > 600)  {
        jQuery("#peek").addClass("show");
      } else {
        jQuery("#peek").hide();
      }
    });
    
    </script>
     -->
    <header class="global">
    <nav>
    <!--============================================
        =            Top part of global nav            =
        =============================================-->
    <div class="top3">
    <div class="cell one">
    <a class="search-open" href="javascript:;"><i class="icon-search"></i></a>
    <div class="search">
    <gcse:searchbox-only></gcse:searchbox-only>
    </div>
    </div>
    <div class="cell two">
    <h1 class="din land">
    <a href="/dining-drinking">Dining<span class="new-amp">&amp;</span>Drinking</a>
    </h1>
    <h1 class="dg land">
    <a href="/Chicago-Magazine/Dining/Dining-Guide/">Chicago’s Best Restaurants</a>
    </h1>
    <h1 class="art land">
    <a href="/arts-culture">Arts<span class="new-amp">&amp;</span>Culture</a>
    </h1>
    <h1 class="pcl land">
    <!-- <a href="/city-life">P<span style="display: inline-block; margin-right: 0px;">o</span>litics<i class="amp"><span>&amp;</span></i>City<span style="display: inline-block; margin-right: -2px;">&nbsp; </span>Life<span style="display: inline-block; width: 12px;"></span></a> -->
    <a href="/city-life">Politics<span class="new-amp">&amp;</span>City Life</a>
    </h1>
    <h1 class="ren land">
    <a href="/real-estate">Real Estate</a>
    </h1>
    <h1 class="sty land">
    <a href="/style-shopping">Style<span class="new-amp">&amp;</span>Shopping</a>
    </h1>
    <h1 class="hg land">
    <a href="/Chicago-Home">Home Design</a>
    </h1>
    <h1 class="hel land">
    <a href="/Health-Wellness">Health<span class="new-amp">&amp;</span>Wellness</a>
    </h1>
    <h1 class="trv land">
    <a href="/travel">Travel</a>
    </h1>
    <h1 class="vid land">
    <a href="/video">Video</a>
    </h1>
    <h1 class="boc land">
    <a href="http://www.chicagomag.com/Chicago-Magazine/August-2018/Best-of-Chicago/">Best of Chicago</a>
    </h1>
    <h1 class="mkt land">
    <a href="/Chicago-Magazine/Classifieds/">Marketplace<span class="new-amp">&amp;</span>Classifieds</a>
    </h1>
    <h1>
    <a class="logo background-logo-svg" href="/" onclick="ga('send', 'event', 'Global nav', 'Home from main logo');">Chicago</a>
    </h1>
    </div>
    <div class="cell three">
    <ul class="member-nav">
    <li><a href="/subscribe" id="global-nav-subscribe" onclick="ga('send', 'event', 'Global nav', 'Subscribe');">Subscribe</a>
    <span class="red">/</span>
    <a href="/customerservice" onclick="ga('send', 'event', 'Global nav', 'Customer Service');">Customer Service</a></li>
    <li><a href="/newsletters" onclick="ga('send', 'event', 'Global nav', 'Newsletters');">Newsletters</a> </li>
    </ul>
    </div>
    </div>
    <!--====  End of Top part of global nav  ====-->
    <!--=====================================
        =            Global nav menus           =
        ======================================-->
    <ul id="page-nav">
    <li style="white-space: nowrap;"><a class="pcl" href="/city-life" onclick="ga('send', 'event', 'Global nav', 'Politics and city life');">Politics <span class="new-amp">&amp;</span><br/> City Life</a>
    <ul class="sub">
    <li><a href="/Chicago-Magazine/September-2018/Best-Public-Schools/" onclick="ga('send', 'event', 'Global nav', 'Best Public Schools');">Best Public Schools</a>
    <li><a href="/Chicago-Magazine/September-2017/Best-Private-Schools/" onclick="ga('send', 'event', 'Global nav', 'Best Private Schools');">Best Private Schools</a>
    <li><a href="/Chicago-Magazine/June-2017/Whos-Got-Next/" onclick="ga('send', 'event', 'Global nav', 'Power 50');">Emerging Power List</a>
    <!-- <li><a href="/topdocs" onclick="ga('send', 'event', 'Global nav', 'Top Docs');">Top Docs</a></li> -->
    <!-- <li><a href="/Chicago-Magazine/January-2017/Top-Cancer-Doctors/" onclick="ga('send', 'event', 'Global nav', 'Top Cancer Docs');">Top Cancer Docs</a></li> -->
    <li><a href="/topic/?archive=crime-law" onclick="ga('send', 'event', 'Global nav', 'Crime and Law');">Crime &amp; Law</a></li>
    <li><a href="/topic/?archive=politics" onclick="ga('send', 'event', 'Global nav', 'Politics');">Politics</a></li>
    <li><a href="/topic/?archive=the-urbanist" onclick="ga('send', 'event', 'Global nav', 'The Urbanist');">The Urbanist</a></li>
    <li><a href="/Chicago-Magazine/Felsenthal-Files/" onclick="ga('send', 'event', 'Global nav', 'Felsenthal');" style="font-size: 13px; letter-spacing: -0.1px;">Articles by Carol Felsenthal</a></li>
    <!-- <li><a href="/topic/?archive=whet-moser" onclick="ga('send', 'event', 'Global nav', 'Whet');">Articles by Whet Moser</a></li> -->
    </li></li></li></ul>
    </li>
    <!-- Dining & Drinking -->
    <li><a class="din" href="/dining-drinking" onclick="ga('send', 'event', 'Global nav', 'Dining');">Dining <span class="new-amp">&amp;</span><br/> Drinking</a>
    <ul class="sub">
    <li><a href="http://www.chicagomag.com/secretsupper" onclick="ga('send', 'event', 'Global nav', 'Secret Supper');">Secret Supper</a></li>
    <li><a href="/topic/?archive=restaurant-reviews" onclick="ga('send', 'event', 'Global nav', 'Restaurant reviews');">Restaurant Reviews</a></li>
    <li><a href="/hotlist" onclick="ga('send', 'event', 'Global nav', 'Hot list');">The Hot List</a></li>
    <li><a href="/brunch" onclick="ga('send', 'event', 'Global nav', 'Brunch');">Brunch</a></li>
    <li><a href="/topic/?archive=bars-drinking" onclick="ga('send', 'event', 'Global nav', 'Bars and drinking');">Bars &amp; Drinking</a></li>
    <li><a href="/Chicago-Magazine/February-2017/Best-Bars/" onclick="ga('send', 'event', 'Global nav', 'Best Bars');">Best Bars</a>
    <li><a href="/Chicago-Magazine/April-2018/Best-New-Restaurants/" onclick="ga('send', 'event', 'Global nav', 'Best New Restaurants');">Best New Restaurants</a></li>
    <li><a href="/Chicago-Magazine/July-2010/Photos-Best-Pizzas-in-Chicago/" onclick="ga('send', 'event', 'Global nav', 'Best Pizza');">Best Pizzas</a></li>
    <li><a href="/steakhouses" onclick="ga('send', 'event', 'Global nav', 'Best steakhouses');">Best Steakhouses</a></li>
    <li><a href="/Chicago-Magazine/November-2014/Cheap-Eats-Chicago/" onclick="ga('send', 'event', 'Global nav', 'Best cheap eats');">Best Cheap Eats</a></li>
    <li><a href="/takeout" onclick="ga('send', 'event', 'Global nav', 'Best takeout');">Best Takeout</a></li>
    <li><a href="/dining-drinking/July-2018/The-50-Best-Restaurants-in-Chicago/" onclick="ga('send', 'event', 'Global nav', '50 Best Restaurants');">Chicago’s Best Restaurants</a>
    <!-- <li><a href="/video/Inside-Chicagos-Best-Restaurants/" onclick="ga('send', 'event', 'Global nav', 'Dining video series');">Video: Inside Chicago’s Best Restaurants</a> -->
    <!-- <li><a href="/video/Inside-Chicagos-Best-Bars/" onclick="ga('send', 'event', 'Global nav', 'Dining video series bars');">Video: Inside Chicago’s Best Bars</a> -->
    <!-- <li><a href="http://www.chicagomag.com/video/In-the-Kitchen/" onclick="ga('send', 'event', 'Global nav', 'Dining video series In Season');">Video: In the Kitchen</a> -->
    <li class="nav-sub-search clearfix"><div class="margin-bottom-5">Search Chicago’s Best Restaurants:</div>
    <form action="/Chicago-Magazine/Dining/Dining-Guide/index.php?" method="get">
    <input name="search" type="text"/>
    <button onclick="ga('send', 'event', 'Global nav', 'Search Dining Guide');">Go</button>
    </form>
    </li>
    </li></li></ul>
    </li>
    <!-- Arts & Culture -->
    <li><a class="art" href="/arts-culture" onclick="ga('send', 'event', 'Global nav', 'Arts');">Arts <span class="new-amp">&amp;</span><br> Culture</br></a>
    <ul class="sub">
    <li><a href="/go" onclick="ga('send', 'event', 'Global nav', 'Go Listings');">The Go Listings</a></li>
    <li><a href="/topic/?archive=architecture" onclick="ga('send', 'event', 'Global nav', 'Architecture');">Architecture</a></li>
    <li><a href="/topic/?archive=art" onclick="ga('send', 'event', 'Global nav', 'Art');">Art</a></li>
    <li><a href="/topic/?archive=books" onclick="ga('send', 'event', 'Global nav', 'Books');">Books &amp; Readings</a></li>
    <li><a href="/topic/?archive=comedy" onclick="ga('send', 'event', 'Global nav', 'Comedy');">Comedy</a></li>
    <li><a href="/topic/?archive=dance" onclick="ga('send', 'event', 'Global nav', 'Dance');">Dance</a></li>
    <li><a href="/topic/?archive=lollapalooza" onclick="ga('send', 'event', 'Global nav', 'Lollapalooza');">Lollapalooza</a></li>
    <li><a href="/topic/?archive=pitchfork" onclick="ga('send', 'event', 'Global nav', 'Pitchfork');">Pitchfork </a></li>
    <li><a href="/topic/?archive=museums" onclick="ga('send', 'event', 'Global nav', 'Museums');">Museums</a></li>
    <li><a href="/topic/?archive=music" onclick="ga('send', 'event', 'Global nav', 'Music');">Music</a></li>
    <!-- <li><a href="/summer-music-festivals/"  onclick="ga('send', 'event', 'Global nav', 'Summer music fests');">Summer Music Festivals</a></li> -->
    <li><a href="/topic/?archive=theater" onclick="ga('send', 'event', 'Global nav', 'Theater');">Theater</a></li>
    <!-- <li><a href="/video/Inside-Chicagos-Best-Theater/" onclick="ga('send', 'event', 'Global nav', 'Video Theater');">Video: Inside Chicago’s Best Theater</a> -->
    <!-- <li><a href="/video/In-the-Studio/" onclick="ga('send', 'event', 'Global nav', 'Video Inside the Studio');">Video: Inside the Studio</a></li> -->
    </ul>
    </li>
    <!-- Real Estate -->
    <li><a class="ren" href="/real-estate" onclick="ga('send', 'event', 'Global nav', 'Real Estate + Neighborhoods');">Home <span class="new-amp">&amp;</span><br/> Real Estate</a>
    <ul class="sub">
    <li><a href="/Chicago-Magazine/April-2017/Where-to-Buy-Now-2017/" onclick="ga('send', 'event', 'Global nav', 'Best neighborhoods');">Where to Buy Now</a>
    <li><a href="/Field-Guides" onclick="ga('send', 'event', 'Global nav', 'Field Guides');">Neighborhood Field Guides</a></li>
    <li><a href="/real-estate" onclick="ga('send', 'event', 'Global nav', 'Real Estate News');">Real Estate News</a></li>
    <li><a href="/Chicago-Home" onclick="ga('send', 'event', 'Global nav', 'Home + Garden');">Home Design</a></li>
    </li></ul>
    </li>
    <!-- Style & Shopping -->
    <li><a class="sty" href="/style-shopping" onclick="ga('send', 'event', 'Global nav', 'Style + Shopping');">Style <span class="new-amp">&amp;</span> Shopping</a>
    <ul class="sub">
    <li><a href="/topic/?archive=fashion" onclick="ga('send', 'event', 'Global nav', 'Fashion');">Fashion</a></li>
    <li><a href="/topic/?archive=sales-shopping" onclick="ga('send', 'event', 'Global nav', 'Sales + Shopping News (under S+S)');">Sales &amp; Shopping News</a></li>
    <li><a href="/topic/?archive=tastemakers" onclick="ga('send', 'event', 'Global nav', 'Tastemakers');">Taste Makers</a></li>
    </ul>
    </li>
    <!-- Health & Wellness  -->
    <li><a class="hg" href="/Health-Wellness" onclick="ga('send', 'event', 'Global nav', 'Health + Wellness');">Health <span class="new-amp">&amp;</span><br/> Wellness</a>
    <ul class="sub">
    <li><a href="/Chicago-Magazine/Promotion/Fit-Society/" onclick="ga('send', 'event', 'Global nav', 'Fit Society');">Fit Society</a></li>
    <li><a href="/Health-Wellness" onclick="ga('send', 'event', 'Global nav', 'Health + Wellness');">Health News</a></li>
    <li><a href="/topdocs" onclick="ga('send', 'event', 'Global nav', 'Top Docs');">Top Docs</a></li>
    </ul>
    </li>
    <!-- Video -->
    <li id="red-hack-item">
    <a href="https://www.youtube.com/user/chicagomag" onclick="ga('send', 'event', 'Global nav', 'Video');" target='_blank"'>
             <br/>
    <i id="red-hack" style="
            background-image: url(http://getbootstrap.com/2.3.2/assets/img/glyphicons-halflings.png); 
            display: inline-block;
            filter: brightness(0) invert(1);
            width: 18px;
            height: 10px;
            background-position: -335px -99px;
            "></i>Video</a>
    </li>
    <style>
          i#red-hack.hover {
            filter: sepia() saturate(10000%) hue-rotate(10deg) !important;
          }
          </style>
    <script>
          jQuery('#red-hack-item').hover(
            function() {
              jQuery(this).children("a").children("i#red-hack").addClass( "hover" );
            }, function() {
              jQuery(this).children("a").children("i#red-hack").removeClass( "hover" );
            }
          );
          </script>
    <!-- OLD VIDEO -->
    <!-- <li><a href="/video" class="tra" onclick="ga('send', 'event', 'Global nav', 'Video');">&nbsp;<br>Video</a>
            <ul class="sub">
              <li><a href="http://www.chicagomag.com/video/Chicagos-Great-Neighborhoods/" onclick="ga('send', 'event', 'Global nav', 'Chicago’s Great Neighborhoods');">Chicago’s Great Neighborhoods</a></li>
              <li><a href="http://www.chicagomag.com/video/Inside-Chicagos-Best-Bars/" onclick="ga('send', 'event', 'Global nav', 'Inside Chicago’s Best Bars');">Inside Chicago’s Best Bars</a></li>
              <li><a href="http://www.chicagomag.com/video/Inside-Chicagos-Best-Restaurants/" onclick="ga('send', 'event', 'Global nav', 'Inside Chicago’s Best Restaurants');">Inside Chicago’s Best Restaurants</a></li>
              <li><a href="http://www.chicagomag.com/video/Inside-Chicagos-Best-Theater/" onclick="ga('send', 'event', 'Global nav', 'Inside Chicago’s Best Theater');">Inside Chicago’s Best Theater</a></li>
              <li><a href="http://www.chicagomag.com/video/In-the-Studio" onclick="ga('send', 'event', 'Global nav', 'Inside the Studio');">Inside the Studio</a></li>
              <li><a href="http://www.chicagomag.com/video/In-the-Kitchen/" onclick="ga('send', 'event', 'Global nav', 'In Season');">In the Kitchen</a></li>
            </ul>
          </li> -->
    <!-- Visitors Guide -->
    <li><a class="tra" href="/Visitors-Guide/" onclick="ga('send', 'event', 'Global nav', 'Visitors Guide');">Visitor's <br/>Guide</a>
    <ul class="sub">
    <li><a href="/Visitors-Guide/" onclick="ga('send', 'event', 'Global nav', 'Visitors Guide');">Visitor’s Guide</a></li>
    <li><a href="/travel" onclick="ga('send', 'event', 'Global nav', 'Travel News');">Travel News &amp; Features</a></li>
    </ul>
    </li>
    <!-- Best of Chicago -->
    <li style="flex-grow: 0;">
    <a class="boc" href="/Chicago-Magazine/August-2018/Best-of-Chicago/" onclick="ga('send', 'event', 'Global nav', 'Best of Chicago');">Best of<br> Chicago</br></a>
    </li>
    </ul>
    </nav>
    <!--====  End of Global nav menus ====-->
    </header>
    <!-- Begin Content Wrap -->
    <a class="edit_from_site" content_id="leaderboard-1" href="#" media_id="83">Edit Module</a>
    <style type="text/css">
          @media (max-width:735px) {
        #div-gpt-ad-leaderboard-1 {
              display:none !important;
            }
       }
    </style>
    <div class="ad ban" id="div-gpt-ad-leaderboard-1">
    <script>
    if (document.documentElement.clientWidth > 735) {
    googletag.cmd.push(function() {
        googletag.display('div-gpt-ad-leaderboard-1');
    });
    }
      </script>
    </div>
    <a class="edit_from_site" content_id="mobile-leaderboard-1" href="#" media_id="83">Edit Module</a>
    <style type="text/css">
          @media (min-width:736px) {
        #div-gpt-ad-mobile-leaderboard-1 {
              display:none !important;
            }
       }
    </style>
    <div class="mobile-ban-wrapper mobile-ban-fixed">
    <div id="div-gpt-ad-mobile-leaderboard-1" style="height: 50px; width:320px;margin: 0 auto;">
    <script>
    if (document.documentElement.clientWidth < 736) {
    googletag.cmd.push(function() {
        googletag.display('div-gpt-ad-mobile-leaderboard-1');
    })
    }
      </script>
    </div>
    </div>
    <a class="edit_from_site" content_id="mobile-leaderboard-2" href="#" media_id="83">Edit Module</a>
    <style type="text/css">
          @media (min-width:736px) {
        #div-gpt-ad-mobile-leaderboard-2 {
              display:none !important;
            }
       }
    </style>
    <div class="mobile-ban-wrapper" id="mobile-leaderboard-2">
    <div id="div-gpt-ad-mobile-leaderboard-2" style="width: 320px; height: 50px; margin: 0 auto;">
    <script>
    if (document.documentElement.clientWidth < 736) {
    googletag.cmd.push(function() {
        googletag.display('div-gpt-ad-mobile-leaderboard-2');
    })
    }
      </script>
    </div>
    </div>
    <div id="content-wrap">
    <!--- special treatment for stacked galleries in regular articles, removes first couple leaderboard ads in between -->
    <style>
    div.content.post div.super-gallery-stacked-photo-outer ~ div.ad.ban {
      display: none;
    }
    </style>
    <!--- Begin Page -->
    <div class="long" id="page">
    <article class="container long" id="article-19495">
    <a class="edit_from_site" content_id="789" href="#" media_id="97">Edit Module</a><div class="story-label story-label-link">
    <a href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-Chicago">2012 Best Sandwiches</a>
    </div><a class="edit_from_site" content_id="550" href="#" media_id="97">Edit Module</a>
    <div id="article-header-image-container">
    <header class=" clearfix">
    <!--- special toppers for repeat features -->
    <!--- special treatment for Urbanist -->
    <h1 class="headline ">1. Old Oak Tap BLT</h1>
    <h2 class="deck ">“Truly inspired.”</h2>
    <div class="article-meta clearfix" id="article-meta">
    <p class="timestamp">Published Oct. 9, 2012</p>
    <section class="clearfix" id="share-mini">
    <ul class="story-share-buttons story-share-buttons-mini list-no-bullets clearfix">
    <li class="fb"><a href="https://www.facebook.com/sharer/sharer.php?u=http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Old-Oak-Tap-BLT/?utm_source=facebook.com%26utm_medium=referral%26utm_campaign=Facebook%20above%20story%2019495%20Dec%2029%202018%2000:44" onclick="ga('send', 'event', 'Sharing', 'Facebook share from top of article','1. Old Oak Tap BLT');
      return socialPopup('https://www.facebook.com/sharer/sharer.php?u=http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Old-Oak-Tap-BLT/%3Futm_source=facebook.com%26utm_medium=referral%26utm_campaign=Facebook%20above%20story%2019495%20Dec%2029%202018%2000:44');" target="_blank"><i class="icon-facebook"></i></a>
    </li>
    <li class="tw"><a href="https://twitter.com/share?text=1.+Old+Oak+Tap+BLT&amp;url=http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Old-Oak-Tap-BLT/" onclick="ga('send', 'event', 'Sharing', 'Tweet story from top of article','1. Old Oak Tap BLT');
        return tweetPopup('1.+Old+Oak+Tap+BLT','/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Old-Oak-Tap-BLT/','19495');
      " target="_blank"><i class="icon-twitter"></i></a></li>
    <li class="eml"><a href="#" onclick="window.open('/core/pagetools.php?pageID=19495&amp;url=%2FChicago-Magazine%2FNovember-2012%2FBest-Sandwiches-in-Chicago-Old-Oak-Tap-BLT%2Findex.php&amp;mode=email','emailThisPage','status=no,scrollbars=yes,resizable=yes,width=420,height=540'); ga('send', 'event', 'Sharing', 'Email story (top of story)','1. Old Oak Tap BLT'); return false;" url="email this page"><i class="icon-envelope"></i></a></li>
    <li class="prnt"><a href="#" onclick="window.open('/core/pagetools.php?pageid=19495&amp;url=%2FChicago-Magazine%2FNovember-2012%2FBest-Sandwiches-in-Chicago-Old-Oak-Tap-BLT%2Findex.php&amp;mode=print','printThisPage','status=no,toolbar=yes,menubar=yes,scrollbars=yes,resizable=yes,width=570'); ga('send', 'event', 'Sharing', 'Print story (top of story)' ,'1. Old Oak Tap BLT'); return false;" target="_blank" url="print this page"><i class="icon-print"></i></a></li>
    <li class="cmmnt"><a href="javascript:void(0);" onclick="jumpto('#comment',150);"><i class="icon-comment"></i></a></li>
    <li class="comment-count"><a data-disqus-identifier="19495" href="javascript:void(0);" id="comment-count" onclick="jumpto('#comment',150);"></a></li>
    </ul>
    </section> </div> </header>
    <section class="caption main">
    <figure>
    <div class="position-relative image-launch-wrapper">
    <picture>
    <source media="(max-width: 320px)" srcset="/images/cache/cache_6/cache_5/cache_a/01-Old-Oak-Trap-BLT-500-737a6a56.jpeg?ver=1483658044&amp;aspectratio=1.4634146341463, /images/cache/cache_9/cache_1/cache_0/01-Old-Oak-Trap-BLT-500-87972019.jpeg?ver=1483658044&amp;aspectratio=1.4619883040936 2x"/>
    <source media="(max-width: 470px)" srcset="/images/cache/cache_a/cache_9/cache_c/01-Old-Oak-Trap-BLT-500-64a12c9a.jpeg?ver=1483658044&amp;aspectratio=1.4754098360656, /images/cache/cache_b/cache_c/cache_0/01-Old-Oak-Trap-BLT-500-d13040cb.jpeg?ver=1483658044&amp;aspectratio=1.4749262536873 2x"/>
    <source srcset="/images/cache/cache_2/cache_b/cache_6/01-Old-Oak-Trap-BLT-500-604ed6b2.jpeg?ver=1482330137&amp;aspectratio=1.4749262536873, /images/cache/cache_4/cache_0/cache_a/01-Old-Oak-Trap-BLT-500-dcc14a04.jpeg?ver=1483658044&amp;aspectratio=1.4749262536873 2x"/>
    <img alt="" height="420" id="article-lead-image" src="/images/cache/cache_2/cache_b/cache_6/01-Old-Oak-Trap-BLT-500-604ed6b2.jpeg?ver=1482330137&amp;aspectratio=1.4749262536873" width="620">
    </img></picture>
    <a class="launch bottom-right" href="/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Old-Oak-Tap-BLT/index.php?mode=popup&amp;cp=1&amp;view=slideshow&amp;play=0" id="gallery-link" style="display: none;">Launch gallery</a>
    </div>
    <figcaption> <span class="photo-credit">Photo: Anna Knott; Food stylist: Lisa Kuehl</span>
    </figcaption>
    </figure>
    </section>
    </div><!-- /#article-header-image-container -->
    <div class="content-and-sidebar">
    <div class="content post ">
    <div class="fb-like fb-like-top" data-href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Old-Oak-Tap-BLT/" data-layout="box_count" data-send="false" data-share="true" data-show-faces="false" data-width="50"></div>
    <p>
    	The B is applewood smoked—nice and snappy. The L is arugula—fresh and peppery. The T is a fried green slice—jacketed in cornmeal and greaseless. Slathered with pimiento cheese, the grilled ciabatta somehow stays crisp, providing three distinct layers of crunch. Truly inspired.</p>
    <p class="addy">
    <em>$10. 2109 W. Chicago Ave., 773-772-0406, <a href="http://www.theoldoaktap.com/">theoldoaktap.com</a></em></p>
    <aside class="story-nav-aside">
    <a class="story-nav" href="http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Phoebes-Bakery-The-Gatsby/" id="story-nav-left">
    <span class="previous">Previous</span>
    <span class="story-nav-head">50. The Gatsby</span>
            Phoebe’s Bakery
          </a>
    <a class="story-nav" href="/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Au-Cheval-Fried-Bologna/" id="story-nav-right">
    <span class="next">Next</span>
    <span class="story-nav-head">2. Fried Bologna</span>
            Au Cheval
            </a>
    </aside>
    <footer>This article appears in the <a href="/Chicago-Magazine/November-2012/">November 2012</a> issue of <em> Chicago</em> magazine. <a href="http://www.chicagomag.com/subscribe" onclick="ga('send',  'event','Refer clicks', 'Subscribe, article footer');">Subscribe to <em>Chicago</em> magazine</a>.</footer>
    <div class="clearfix"></div>
    <script>
      var newsletterChoice = "Dish";
    </script>
    <div class="margin-top-10 margin-bottom-40" id="footer-newsletter-subscribe">
    <div class="lazyload" data-url="/lazyloads/furniture/dish-subscribe-footer.html?2" id="lazy-newsletter-footer"> </div>
    </div>
    <script>
      jQuery(function(){
        // If the user has already subscribed to this newsletter, hide the footer form.
        // In its stead, reveal the related articles and popular stories. (Super articles only.)
        if (getCookie('subscribe-Dish') == 'true') {
          jQuery('#footer-newsletter-subscribe').hide();
          // jQuery('.show-if-no-newsletter-cta').show();
        }
        // else {
        //   jQuery('.show-if-no-newsletter-cta').hide();
        // }
      })
    
    
    
    </script>
    <section class="clearfix" id="share">
    <h3>Share</h3>
    <ul class="story-share-buttons list-no-bullets clearfix">
    <li class="fb"><a href="https://www.facebook.com/sharer/sharer.php?u=http://www.chicagomag.com//Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Old-Oak-Tap-BLT/%3Futm_source=facebook.com%26utm_medium=referral%26utm_campaign=Facebook%20below%20story%2019495%20Dec%2029%202018%2000:44" onclick="ga('send', 'event', 'Sharing', 'Facebook share from article','1. Old Oak Tap BLT');
        return socialPopup('https://www.facebook.com/sharer/sharer.php?u=http://www.chicagomag.com//Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Old-Oak-Tap-BLT/%3Futm_source=facebook.com%26utm_medium=referral%26utm_campaign=Facebook%20below%20story%2019495%20Dec%2029%202018%2000:44');" target="_blank"><i class="icon-facebook"></i></a>
    </li>
    <li class="tw"><a href="https://twitter.com/share?text=1.+Old+Oak+Tap+BLT&amp;url=http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Old-Oak-Tap-BLT/" onclick="ga('send', 'event', 'Sharing', 'Tweet story from article','1. Old Oak Tap BLT');
        return tweetPopup('1.+Old+Oak+Tap+BLT','/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Old-Oak-Tap-BLT/','19495');
        " target="_blank"><i class="icon-twitter"></i></a></li>
    <li class="eml"><a href="#" onclick="window.open('/core/pagetools.php?pageID=19495&amp;url=%2FChicago-Magazine%2FNovember-2012%2FBest-Sandwiches-in-Chicago-Old-Oak-Tap-BLT%2Findex.php&amp;mode=email','emailThisPage','status=no,scrollbars=yes,resizable=yes,width=420,height=540'); ga('send', 'event', 'Sharing', 'Email story','1. Old Oak Tap BLT'); return false;" url="email this page"><i class="icon-envelope"></i></a></li>
    <li class="prnt"><a href="#" onclick="window.open('/core/pagetools.php?pageid=19495&amp;url=%2FChicago-Magazine%2FNovember-2012%2FBest-Sandwiches-in-Chicago-Old-Oak-Tap-BLT%2Findex.php&amp;mode=print','printThisPage','status=no,toolbar=yes,menubar=yes,scrollbars=yes,resizable=yes,width=570'); ga('send', 'event', 'Sharing', 'Print story' ,'1. Old Oak Tap BLT'); return false;" target="_blank" url="print this page"><i class="icon-print"></i></a></li>
    <li class="cmmnt"><a href="javascript:void(0);" onclick="jumpto('#comment',150);"><i class="icon-comment"></i></a></li>
    </ul>
    </section>
    <a class="edit_from_site" content_id="ad-600-100" href="#" media_id="83">Edit Module</a>
    <style type="text/css">
          @media (max-width:599px) {
        #ad-600-100-outer {
              display:none !important;
            }
       }
    </style>
    <div class="ad ban-600" id="ad-600-100-outer">
    <h2>Advertisement</h2>
    <div id="div-gpt-ad-600-100"></div>
    <script>
    
    if (document.documentElement.clientWidth > 600) {
    googletag.cmd.push(function() {
        googletag.display('div-gpt-ad-600-100');
    });
    }
      </script>
    </div>
    <section id="related-articles">
    <a class="title" href="/dining-drinking">Other Dining &amp; Drinking stories</a>
    <div class="related-row clearfix">
    <div class="related related-1 ">
    <a class="related-thumb" href="/Chicago-Magazine/November-2018/How-to-Make-Holiday-Granola/" onclick="ga('send',  'event', 'Refer clicks', 'Related (photo), slot ' + 1, '/Chicago-Magazine/November-2018/How-to-Make-Holiday-Granola/');" style="background-image: url('/images/cache/cache_5/cache_e/cache_c/C201811-T-Holiday-Granola-eea4dce5.jpeg?ver=1545411225&amp;aspectratio=1.5'); background-size: cover;"> </a>
    <div class="content">
    <h2><a href="/Chicago-Magazine/November-2018/How-to-Make-Holiday-Granola/" onclick="ga('send',  'event', 'Refer clicks', 'Related (head), slot ' + 1, '/Chicago-Magazine/November-2018/How-to-Make-Holiday-Granola/');">How to Make Holiday Granola</a></h2>
    <p class="time">1 week ago</p>
    </div><!-- /.content -->
    </div><!-- /.related -->
    <div class="related related-2 related-nativo">
    <a class="related-thumb" href="/dining-drinking/December-2018/Swill-Inn/" onclick="ga('send',  'event', 'Refer clicks', 'Related (photo), slot ' + 2, '/dining-drinking/December-2018/Swill-Inn/');" style="background-image: url('/images/cache/cache_3/cache_1/cache_5/SWILL-5-9ed5a513.jpeg?ver=1545321612&amp;aspectratio=1.5'); background-size: cover;"> </a>
    <div class="content">
    <h2><a href="/dining-drinking/December-2018/Swill-Inn/" onclick="ga('send',  'event', 'Refer clicks', 'Related (head), slot ' + 2, '/dining-drinking/December-2018/Swill-Inn/');">There Are No Frills at Swill Inn</a></h2>
    <p class="time">1 week ago</p>
    </div><!-- /.content -->
    </div><!-- /.related -->
    </div><!-- /.related-row -->
    <div class="related-row clearfix">
    <div class="related related-3 ">
    <a class="related-thumb" href="/dining-drinking/December-2018/Parsons-Chicken-and-Fish-Lincoln-Park-Dining-News/" onclick="ga('send',  'event', 'Refer clicks', 'Related (photo), slot ' + 3, '/dining-drinking/December-2018/Parsons-Chicken-and-Fish-Lincoln-Park-Dining-News/');" style="background-image: url('/images/cache/cache_9/cache_a/cache_0/CTcheap-chicken006-ec4b60a9.jpeg?ver=1545239802&amp;aspectratio=1.5364583333333'); background-size: cover;"> </a>
    <div class="content">
    <h2><a href="/dining-drinking/December-2018/Parsons-Chicken-and-Fish-Lincoln-Park-Dining-News/" onclick="ga('send',  'event', 'Refer clicks', 'Related (head), slot ' + 3, '/dining-drinking/December-2018/Parsons-Chicken-and-Fish-Lincoln-Park-Dining-News/');">Parson’s Chicken and Fish Opens in Lincoln Park and More Dining News</a></h2>
    <p class="time">1 week ago</p>
    </div><!-- /.content -->
    </div><!-- /.related -->
    <div class="related related-4 ">
    <a class="related-thumb" href="/dining-drinking/December-2018/Four-New-Years-Eve-Dining-Specials-That-You-Can-Still-Reserve/" onclick="ga('send',  'event', 'Refer clicks', 'Related (photo), slot ' + 4, '/dining-drinking/December-2018/Four-New-Years-Eve-Dining-Specials-That-You-Can-Still-Reserve/');" style="background-image: url('/images/cache/cache_2/cache_6/cache_0/DMK_AdaSt._PRESS-f03c4062.jpeg?ver=1545160179&amp;aspectratio=1.7291666666667'); background-size: cover;"> </a>
    <div class="content">
    <h2><a href="/dining-drinking/December-2018/Four-New-Years-Eve-Dining-Specials-That-You-Can-Still-Reserve/" onclick="ga('send',  'event', 'Refer clicks', 'Related (head), slot ' + 4, '/dining-drinking/December-2018/Four-New-Years-Eve-Dining-Specials-That-You-Can-Still-Reserve/');">Four New Year’s Eve Dining Specials (That You Can Still Reserve)</a></h2>
    <p class="time">1 week ago</p>
    </div><!-- /.content -->
    </div><!-- /.related -->
    </div><!-- /.related-row -->
    </section>
    <!-- End Related Articles -->
    <div id="comment" style="clear: both; height: 1px;"></div>
    <a class="edit_from_site" content_id="comments-disqus" href="#" media_id="83">Edit Module</a><div class="comments-block" id="commentFormContainer">
    <h5 class="add-comment">Submit your comment</h5>
    <p class="comments-disclaimer">Comments  are moderated. We review them in an effort to remove foul language, commercial messages, abuse, and irrelevancies.</p>
    <div id="disqus_thread" style="max-width: 600px;"></div>
    <script type="text/javascript">
    </script>
    <noscript>Enable JavaScript to view the <a href="http://disqus.com/?ref_noscript">comments powered by Disqus.</a></noscript>
    </div>
    </div>
    <!-- Begin Sidebar -->
    <aside class="article long clearfix">
    <div class="tablet-sidebar-left">
    <a class="edit_from_site" content_id="widget-marketplace-sponsor" href="#" media_id="83">Edit Module</a>
    <style>
    
    .btn-widget-small {
     margin-bottom: 5px;}
    
    .amp {
    color: white;
    webkit-transition: none .2s ease; 
    }
    
    ul.menu {
    background: black;
       position:absolute;
       /*height: 0px;*/
       visibility: hidden;
       margin-top: 0px;
      background-color: black;
      width: 300px;
      padding: 0px 0px 10px;
    z-index: 9999999;
    }
    
    ul.menu li a {
       font-size: .75em;
       font-style: italic;
       text-align:center;
    }
    #btn-sponsored-features {
    position: relative;
    }
    
    #btn-sponsored-features:hover .menu {
      /*position: relative;*/
      visibility: visible;
    
      /*height: 80%;*/
    
    }
    
    ul.menu a {
    -webkit-transition: none;-moz-transition:none;transition:none;
    }
    #marketing-widget {
    margin-bottom: 20px;
    }
    </style>
    <div id="marketing-widget">
    <div class="btn-widget-small btn-widget-small-reverse">
    <a href="/marketplace" onclick="ga('send',  'event','M&amp;C widget', 'Marketplace');" style="display: inline-block; margin-right: 2px;">Marketplace</a>
    </div>
    <div class="btn-widget-small btn-widget-small-reverse">
    <a href="/Chicago-Magazine/Promotion/Events-and-Party-Pix/" onclick="ga('send',  'event','M&amp;C widget', 'Events and Party Pix');" style="display: inline-block; margin-right: 2px;">Events <span class="new-amp">&amp;</span> Party Pix</a>
    </div>
    <div class="btn-widget-small btn-widget-small-reverse" id="btn-sponsored-features"><span>Resource Guide</span>
    <ul class="menu">
    <li><a href="http://www.chicagomag.com/Chicago-Magazine/Promotion/Dental-Profiles-2018/" onclick="ga('send',  'event','M&amp;C widget', 'Dental Profiles');">Dental Profiles</a></li>
    <li><a href="http://www.chicagomag.com/Chicago-Magazine/Promotion/Experience-Chicago-2018-2/" onclick="ga('send',  'event','M&amp;C widget', 'Experience Chicago 2018');">Experience Chicago</a></li>
    <li><a href="http://www.chicagomag.com/Chicago-Magazine/Promotion/Give-Chicago-2018" onclick="ga('send',  'event','M&amp;C widget', '2018 Give Chicago');">Give Chicago</a></li>
    <li><a href="http://www.chicagomag.com/Chicago-Magazine/Promotion/2018-Go-West/" onclick="ga('send',  'event','M&amp;C widget', '2018 Go West');">Go West</a></li>
    <li><a href="http://www.chicagomag.com/Chicago-Magazine/Promotion/Make-it-Lincoln-Park-2018/" onclick="ga('send',  'event','M&amp;C widget', 'Make It Lincoln Park');">Make it Lincoln Park</a></li>
    <li><a href="http://www.chicagomag.com/Chicago-Magazine/Promotion/Prime-of-Your-Life-2018/" onclick="ga('send',  'event','M&amp;C widget', 'Prime of Your Life');">Prime of Your Life</a></li>
    <li><a href="http://www.chicagomag.com/Chicago-Magazine/Promotion/Chicagoland-Professional-Profiles-2018/?4" onclick="ga('send',  'event','M&amp;C widget', 'Professional Profiles');">Professional Profiles</a></li>
    <li><a href="/Chicago-Magazine/Promotion/School-Open-House-Guide-2018/" onclick="ga('send',  'event','M&amp;C widget', 'School Open House Guide');">School Open House Guide</a></li>
    <li><a href="/Chicago-Magazine/Promotion/Vin-De-France/" onclick="ga('send',  'event','M&amp;C widget', 'Vin De France');">Vin De France</a></li>
    </ul>
    </div>
    </div>
    <a class="edit_from_site" content_id="cube-1" href="#" media_id="83">Edit Module</a>
    <div class="ad sq">
    <h2>Advertisement</h2>
    <div id="div-gpt-ad-cube-1" style="height: 250px; width: 300px;"></div>
    <script type="text/javascript">
    googletag.cmd.push(function() {
    		googletag.display('div-gpt-ad-cube-1');
    });
    	</script>
    </div>
    <a class="edit_from_site" content_id="cube-3" href="#" media_id="83">Edit Module</a>
    <div class="ad sq">
    <h2>Advertisement</h2>
    <div id="div-gpt-ad-cube-3" style="width: 300px; height: 250px; margin: 0 auto;">
    <script>
    googletag.cmd.push(function() {
        googletag.display('div-gpt-ad-cube-3');
    });
      </script>
    </div>
    </div>
    </div>
    <div class="tablet-sidebar-right">
    <form action="" method="post" name="newsletter" onsubmit="validateNewsletter(); return false;">
    <section class="newsletters widget" id="category-specific-newsletter">
    <h2 class="title red-border" id="newsletterTitle">Get Our Newsletters</h2>
    <div class="newsletter-reveal clearfix" style="display: block;">
    <p class="newsletter-description"></p>
    <input class="clean white" name="email" placeholder="Your email address" type="text"/>
    <div class="form-error" id="error-email">
              Please enter your email address
            </div>
    <label class="checkbox " for="over21"> <input checked="" id="over21" name="over21" type="checkbox" value="yes"/>Are you 21 or older?</label>
    <input name="source" type="hidden" value="/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Old-Oak-Tap-BLT/"/>
    <input id="newsletterChoice" name="newsletter" type="hidden" value=""/>
    <br style="clear:both;"/>
    <button type="submit">Subscribe</button>
    </div>
    </section>
    </form>
    <script>
          var newsletterChoice = "Dish";
      
      
    
        var cookieValue = getCookie('subscribe-'+newsletterChoice.replace(' ','-'));
        if (cookieValue == 'true') {
          jQuery('#category-specific-newsletter').hide();
        }
    
        else {
          var description = '';
          switch (newsletterChoice) {
            case 'Home & Real Estate':
              description = "<span class='leadin'>Fridays:</span> Get the latest real estate news, keep an eye on the city’s most beautiful and unique listings, and learn about the best home & design events locally.";
              break;
            case 'The 312':
              description = "<span class='leadin'>Tuesdays:</span> Get up to speed on the week’s most important stories and must-reads.";
              break;
            case 'Chicago Guide':
              description = "<span class='leadin'>Wednesdays:</span> <em>Chicago</em>’s culture critics bring you a weekly list of the top five events worth your time and money.";
              break;
            case 'Go Travel':
              description = "<span class='leadin'>Monthly:</span> Be the first to know about exclusive travel discounts and offers to help you plan your next big adventure, courtesy of our sponsors.";
              break;
            case 'Dish':
              description = "<span class='leadin'>Wednesdays:</span> Want the latest news from the Chicagoland dining scene served up fresh in your email inbox? Sign up for the weekly newsletter and get inside access to the city’s most plugged-in food and dining coverage. ";
              break;
            case 'Sales Check':
              description = "<span class='leadin'>Thursdays:</span> Hear about the great shopping experiences in the Chicago area, plus the latest news about hot store openings, sales, trends, and local retail news. Shop-a-holic? We know just how you feel. ";
              break;
            case 'VIP Newsletter':
              description = "<span class='leadin'>Mondays, Wednesdays, and Fridays: </span><em>Chicago</em> magazine hosts various events throughout the year. Sign up and join the party!";
              break;
            case 'Special Offers':
              description = 'Receive special offers and discounts from <em>Chicago</em> magazine’s selected partners.';
              break;
            case 'Health & Wellness':
              description = "<span class='leadin'>Third Mondays: </span>The top local stories in health care innovation, nutrition, exercise, wellness, and more—plus, local classes and workshops worth attending. Our editors will bring you the latest on how to live life, well.";
              break;
            case 'Select all':
              description = 'Subscribe to <em>Chicago Guide</em>, <em>Chicago Travel</em>, <em>Dish</em>, <em>Health & Wellness</em>, <em>Sales Check</em>, <em>Home & Real Estate</em>, <em>VIP Newsletter</em>, and <em>Special Offers</em>.'
          }
    
    
          jQuery('.newsletter-description').html(description);
          jQuery('.newsletter-reveal').slideDown();
          jQuery('#newsletterChoice').val(newsletterChoice);
          jQuery('#newsletterTitle').html('Subscribe to ' + newsletterChoice);
        }
    
    </script>
    <div class="lazyload" data-url="/lazyloads/most-popular/" id="lazy-popular"></div>
    <div id="fixie-side-position-finder">
    <div class="clearfix" id="fixie-side">
    <a class="edit_from_site" content_id="cube-2" href="#" media_id="83">Edit Module</a>
    <div class="ad sq">
    <h2>Advertisement</h2>
    <div id="div-gpt-ad-cube-2" style="width: 300px; height: 250px; margin: 0 auto;">
    <script>
    googletag.cmd.push(function() {
        googletag.display('div-gpt-ad-cube-2');
    });
      </script>
    </div>
    </div>
    <script>
              // Make the fixed slot refresh every 2 minutes (on desktops; ad is not fixed elsewhere)
              setInterval(function(){
                if (jQuery('#fixie-side').css('position') == 'fixed') {
                  googletag.pubads().refresh([cube2]);
                  }
                }, 120000);
            </script>
    <a class="edit_from_site" content_id="widget-current-issue" href="#" media_id="83">Edit Module</a>
    <section class="subscribe widget">
    <a class="button" href="javascript:void(0);"><span class="title">Subscribe</span><span class="plus">+</span><span class="minus">-</span></a>
    <div class="issue">
    <a href="/Chicago-Magazine/January-2019/" onclick="ga('send',  'event', 'Current issue widget', 'Current issue');"><img alt="Current issue of Chicago magazine" height="145" src="/images/cache/cache_9/cache_6/cache_8/CMAG0119-4ec81869.jpeg?ver=1545933501&amp;aspectratio=0.75862068965517" width="110"/></a>
    <a class="btn" href="/subscribe" onclick="ga('send',  'event', 'Current issue widget', 'Subscribe (red button)');">Subscribe</a>
    </div>
    <ul>
    <li>
    <a href="/subscribe" onclick="ga('send',  'event', 'Current issue widget', 'Subscribe');">
    <span class="lead"><span class="red">Subscribe</span> to the</span>
    <span class="title">Print &amp; Tablet Edition</span>
    </a>
    </li>
    <li>
    <a href="http://www.chicagomag.com/digitaledition" onclick="ga('send',  'event', 'Current issue widget', 'Digital edition');">
    <span class="lead"><span class="red">Subscribe</span> to the</span>
    <span class="title">Digital Edition</span>
    </a>
    </li>
    <li>
    <a class="ipad" href="/ipad" onclick="ga('send',  'event', 'Current issue widget', 'iPad');">
    <span class="lead"><span class="red">Download</span> our tablet app for</span>
    <span class="title">iPad</span>
    </a>
    <a class="android" href="/android" onclick="ga('send',  'event', 'Current issue widget', 'Android');">
    <span class="title">Android</span>
    </a>
    </li>
    <li>
    <a class="single" href="/single-issues" onclick="ga('send',  'event', 'Current issue widget', 'Single Issues');">
    <span class="lead"><span class="red">Purchase</span> copies of</span>
    <span class="title">Single Issues</span>
    </a>
    <a class="gift" href="/gifts" onclick="ga('send',  'event', 'Current issue widget', 'Gift');">Give a <span class="red">gift</span> subscription</a>
    </li>
    </ul>
    </section>
    </div>
    </div>
    </div>
    </aside>
    <!-- End Sidebar -->
    </div> <!-- End .content-and-sidebar -->
    </article>
    <!-- End Main Article Container -->
    </div><!--- End Page -->
    <a class="edit_from_site" content_id="leaderboard-2" href="#" media_id="83">Edit Module</a>
    <style type="text/css">
          @media (max-width:735px) {
        #div-gpt-ad-leaderboard-2 {
              display:none !important;
            }
       }
    </style>
    <div class="ad ban" id="div-gpt-ad-leaderboard-2">
    <script type="text/javascript">
    if (document.documentElement.clientWidth > 735) {
    googletag.cmd.push(function() {
        googletag.display('div-gpt-ad-leaderboard-2');
    });
    }
      </script>
    </div>
    </div><!-- End Content Wrap -->
    <div id="div-out-of-page">
    <script>
      googletag.cmd.push(function() {
        googletag.display('div-out-of-page');
      });
      </script>
    </div>
    <script>(function(d, s, id) {
      var js, fjs = d.getElementsByTagName(s)[0];
      if (d.getElementById(id)) return;
      js = d.createElement(s); js.id = id;
      js.src = "//connect.facebook.net/en_US/all.js#xfbml=1&appId=432224116825361";
      fjs.parentNode.insertBefore(js, fjs);
    }(document, 'script', 'facebook-jssdk'));</script>
    <!-- LSC tracking pixel -->
    <script>
        document.write('<script id="tbw_pixel" type="text/javascript" src="//d26x5ounzdjojj.cloudfront.net/tbw/tbw_analytics_v1.0.js?' + (new Date()).getHours() + '" onload="tbw_parse(this);" tbw_site_id="Ldd6b4994ab6844a0" tbw_segment="Retargeting" defer><\/script>');
    </script>
    <!-- fix for gallery height bug -->
    <script>
    jQuery( document ).ready(function() {
      jQuery('.photoset-grid-basic .photoset-row').css( "height", "initial" );
    });
    </script>
    <!-- force removal of bottom nav in gallery view -->
    <style>
    body.gallery.no-nav aside.story-nav-aside {
      display: none !important;
    }
    </style>
    <footer class="page-footer">
    <nav>
    <a class="logo background-logo-svg" href="/" onclick="ga('send', 'event', 'Global footer', 'Logo to home');">Chicago magazine</a>
    <select class="clean wide nav" id="page-nav-mobile" onchange="location = this.options[this.selectedIndex].value;">
    <option value="/Chicago-Magazine/Advertise">Advertise</option>
    <option value="/Chicago-Magazine/Contact-Us">Contact Us</option>
    <option value="/Chicago-Magazine/Classifieds/">Marketplace</option>
    <option value="http://www.tronc.com/privacy-policy/">Privacy &amp; Terms (Updated)</option>
    <option value="/Chicago-Magazine/archive-index-test">Archive</option>
    <option value="/customerservice">Customer Service</option>
    <option value="/newsletters">Newsletters</option>
    <option value="/subscribe">Subscribe</option>
    </select>
    <section class="links">
    <ul>
    <li><a href="/Chicago-Magazine/Advertise" onclick="ga('send', 'event','Global footer', 'Advertise');">Advertise</a></li>
    <li><a href="/Chicago-Magazine/Contact-Us" onclick="ga('send', 'event','Global footer', 'Contact us');">Contact Us</a></li>
    <li><a href="/Chicago-Magazine/Classifieds/" onclick="ga('send', 'event','Global footer', 'Marketplace');">Marketplace</a></li>
    <li><a href="http://www.tronc.com/privacy-policy/" onclick="ga('send', 'event','Global footer', 'Privacy');">Privacy (Updated)</a></li>
    <li><a href="http://www.tronc.com/central-terms-of-service/" onclick="ga('send', 'event','Global footer', 'Terms');">Terms</a></li>
    </ul>
    <ul>
    <li><a href="/Chicago-Magazine/archive-index-test" onclick="ga('send', 'event','Global footer', 'Archive');">Archive</a></li>
    <li><a href="/customerservice" onclick="ga('send', 'event','Global footer', 'Customer service');">Customer Service</a></li>
    <li><a href="/newsletters" onclick="ga('send', 'event','Global footer', 'Newsletters');">Newsletters</a></li>
    <li><a href="/subscribe" onclick="ga('send', 'event','Global footer', 'Subscribe');">Subscribe</a></li>
    </ul>
    </section>
    <div class="credits">
    <p class="copyright">©<span id="copyright-year"></span> <em>Chicago</em> magazine / A Chicago Tribune Media Group website</p>
    </div>
    </nav>
    </footer>
    <!--[if lt IE 9]>
    <script type="text/javascript" language"Javascript">
    console.log('Mutha ucking IE8');
    jQuery('html').addClass('old-ie');
    </script>
    <![endif]-->
    <script>
    document.getElementById('copyright-year').innerHTML = new Date().getFullYear()
    </script>
    <!-- Chartbeat -->
    <script type="text/javascript">
    var _sf_async_config={uid:3096,domain:"chicagomag.com"}; // ,useCanonical: true
    
      _sf_async_config.sections = "Dining & Drinking";
    
    
    (function(){
      function loadChartbeat() {
        window._sf_endpt=(new Date()).getTime();
        var e = document.createElement('script');
        e.setAttribute('language', 'javascript');
        e.setAttribute('type', 'text/javascript');
        e.setAttribute('src',
           (("https:" == document.location.protocol) ? "https://a248.e.akamai.net/chartbeat.download.akamai.com/102508/" : "http://static.chartbeat.com/") +
           "js/chartbeat.js");
        document.body.appendChild(e);
      }
      var oldonload = window.onload;
      window.onload = (typeof window.onload != 'function') ?
         loadChartbeat : function() { oldonload(); loadChartbeat(); };
    })();
    </script>
    <!-- Google Custom Search Engine -->
    <script type="text/javascript">
      (function() {
        var cx = '010330565828956611806:7mfjr5payok'; //PROD
        // var cx = '012184964769571027538:4alkqopmlde'; // DEV
        var gcse = document.createElement('script');
        gcse.type = 'text/javascript';
        gcse.async = true;
        gcse.src = (document.location.protocol == 'https:' ? 'https:' : 'http:') +
            '//www.google.com/cse/cse.js?cx=' + cx;
        var s = document.getElementsByTagName('script')[0];
        s.parentNode.insertBefore(gcse, s);
      })();
    </script>
    <script src="https://www.sc.pages05.net/lp/static/js/iMAWebCookie.js?5d52baad-1411358527b-d7c8ec57ae636c7258d3eb0ef0e531f2&amp;h=www.pages05.net" type="text/javascript"></script>
    <!-- Disqus -->
    <!-- For Citi sponsorship (remove this conditional after Nov 2018) -->
    <!-- -->
    <script>
      var disqus_shortname = 'chicagomag';
      var disqus_identifier = '19495';
      var disqus_title = '1. Old Oak Tap BLT';
      var disqus_url = 'http://www.chicagomag.com/Chicago-Magazine/November-2012/Best-Sandwiches-in-Chicago-Old-Oak-Tap-BLT/';
    
      /* * * DON'T EDIT BELOW THIS LINE * * */
      (function() {
          var dsq = document.createElement('script'); dsq.type = 'text/javascript'; dsq.async = true;
          dsq.src = '//' + disqus_shortname + '.disqus.com/embed.js';
          (document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(dsq);
      })();
    
      (function () {
      var s = document.createElement('script'); s.async = true;
      s.type = 'text/javascript';
      s.src = 'http://' + disqus_shortname + '.disqus.com/count.js';
      (document.getElementsByTagName('HEAD')[0] || document.getElementsByTagName('BODY')[0]).appendChild(s);
      }());
    </script>
    <!-- -->
    <!-- Clean up the lame Rivista pagination -->
    <script type="text/javascript">
      var pb = jQuery('.page-break');
      if (pb.length) {
        var pagebreak = pb.html();
        // Remove it from Page 1
        if (pagebreak.substring(0,10) == "(page 1 of") {
          pb.hide();
        }
        else if (pagebreak.substring(0,13) == "<em>Page 1 of") {
           pb.hide();
        }
        else {
          pagebreak = pagebreak.replace("(page","<em>Page");
          pagebreak = pagebreak.replace(")","</em>");
          pb.html(pagebreak);
          pb.css('display','inline-block');
        }
      }
    </script>
    <!--[if lt IE 9]>
    <script  type="text/javascript" language="JavaScript" src="/core/media/themes/Respond/js/respond.js?ver=1473876729"></script>
    <![endif]-->
    <script language="JavaScript" src="/core/media/js/base.js?ver=1473876728" type="text/javascript"></script>
    <script language="JavaScript" src="/core/media/themes/Respond/js/bootstrap.min.js?ver=1473876729" type="text/javascript"></script>
    <script language="JavaScript" src="//maps.googleapis.com/maps/api/js?v=3.exp&amp;sensor=false" type="text/javascript"></script>
    <script language="JavaScript" src="/theme_overrides/Respond/js/interstitial.js?ver=1524154906" type="text/javascript"></script>
    <script language="JavaScript" src="/theme_overrides/Respond/js/newsletter-subscribe.js?ver=1524850607" type="text/javascript"></script>
    <script language="JavaScript" src="/theme_overrides/Respond/js/RivistaGoogleDFP.js?ver=1447178886" type="text/javascript"></script>
    <!-- godengo-monitor --></body>
    </html>




```python
print(soup_tmp.find('p', 'addy'))
```

    <p class="addy">
    <em>$10. 2109 W. Chicago Ave., 773-772-0406, <a href="http://www.theoldoaktap.com/">theoldoaktap.com</a></em></p>
    


```python
price_tmp = soup_tmp.find('p', 'addy').get_text()
price_tmp
```




    '\n$10. 2109 W. Chicago Ave., 773-772-0406, theoldoaktap.com'




```python
price_tmp.split()
```




    ['$10.', '2109', 'W.', 'Chicago', 'Ave.,', '773-772-0406,', 'theoldoaktap.com']




```python
price_tmp.split()[0]
```




    '$10.'




```python
price_tmp.split()[0][:-1]
```




    '$10'




```python
' '.join(price_tmp.split()[1:-2])
```




    '2109 W. Chicago Ave.,'




```python
price = []
address = []

for n in df.index[:3]:
    html = urlopen(df['URL'][n])
    soup_tmp = BeautifulSoup(html, 'lxml')
    
    gettings = soup_tmp.find('p', 'addy').get_text()
    
    price.append(gettings.split()[0][:-1])
    address.append(' '.join(gettings.split()[1:-2]))
```


```python
price
```




    ['$10', '$9', '$9.50']




```python
address
```




    ['2109 W. Chicago Ave.,', '800 W. Randolph St.,', '445 N. Clark St.,']



## Jupyter Notebook에서 상태 진행바를 쉽게 만들어주는 tqdm 모듈
## 상태 진행바까지 적용하고 다시 샌드위치페이지 50개에 접근하기


```python
from tqdm import tqdm_notebook

price = []
address = []

for n in tqdm_notebook(df.index):
    html = urlopen(df['URL'][n])
    soup_tmp = BeautifulSoup(html, 'lxml')
    
    gettings = soup_tmp.find('p', 'addy').get_text()
    
    price.append(gettings.split()[0][:-1])
    address.append(' '.join(gettings.split()[1:-2]))
```


    HBox(children=(IntProgress(value=0, max=50), HTML(value='')))


    
    

## 50개 웹 페이지에 대한 정보 가져오기


```python
price
```




    ['$10',
     '$9',
     '$9.50',
     '$9.40',
     '$10',
     '$7.25',
     '$16',
     '$10',
     '$9',
     '$17',
     '$11',
     '$5.49',
     '$14',
     '$10',
     '$13',
     '$4.50',
     '$11.95',
     '$11.50',
     '$6.25',
     '$15',
     '$5',
     '$6',
     '$8',
     '$5.99',
     '$7.52',
     '$11.95',
     '$7.50',
     '$12.95',
     '$7',
     '$21',
     '$9.79',
     '$9.75',
     '$13',
     '$7.95',
     '$9',
     '$9',
     '$8',
     '$8',
     '$7',
     '$6',
     '$7.25',
     '$11',
     '$6',
     '$9',
     '$5.49',
     '$8',
     '$6.50',
     '$7.50',
     '$8.75',
     '$6.85']




```python
address
```




    ['2109 W. Chicago Ave.,',
     '800 W. Randolph St.,',
     '445 N. Clark St.,',
     '914 Noyes St., Evanston,',
     '825 W. Fulton Mkt.,',
     '100 E. Walton',
     '1639 S. Wabash Ave.,',
     '2211 W. North Ave.,',
     '3619 W. North Ave.,',
     '3267 S. Halsted St.,',
     '2537 N. Kedzie Blvd.,',
     'Multiple',
     '3124 N. Broadway,',
     '3455 N. Southport Ave.,',
     '2657 N. Kedzie Ave.,',
     '1120 W. Grand Ave.,',
     '1141 S. Jefferson St.,',
     '333 E. Benton Pl.,',
     '1411 N. Wells St.,',
     '1747 N. Damen Ave.,',
     '3209 W. Irving Park',
     'Multiple',
     '5347 N. Clark St.,',
     '2954 W. Irving Park Rd.,',
     'Multiple',
     '191 Skokie Valley Rd., Highland Park,',
     'Multiple',
     '1818 W. Wilson Ave.,',
     '2517 W. Division St.,',
     '218 W. Kinzie',
     'Multiple',
     '1547 N. Wells St.,',
     '415 N. Milwaukee Ave.,',
     '1840 N. Damen Ave.,',
     '1220 W. Webster Ave.,',
     '5357 N. Ashland Ave.,',
     '1834 W. Montrose Ave.,',
     '615 N. State St.,',
     'Multiple',
     '241 N. York Rd., Elmhurst,',
     '1323 E. 57th St.,',
     '655 Forest Ave., Lake Forest,',
     'Hotel Lincoln, 1816 N. Clark St.,',
     '100 S. Marion St., Oak Park,',
     '26 E. Congress Pkwy.,',
     '2018 W. Chicago Ave.,',
     '25 E. Delaware Pl.,',
     '416 N. York St., Elmhurst,',
     '65 E. Washington St.,',
     '3351 N. Broadway,']




```python
df.head(10)
```




<div>
<style scoped>
    .dataframe tbody tr th:only-of-type {
        vertical-align: middle;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }

    .dataframe thead th {
        text-align: right;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>Rank</th>
      <th>Cafe</th>
      <th>Menu</th>
      <th>URL</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>1</td>
      <td>Old Oak Tap</td>
      <td>BLT</td>
      <td>http://www.chicagomag.com/Chicago-Magazine/Nov...</td>
    </tr>
    <tr>
      <th>1</th>
      <td>2</td>
      <td>Au Cheval</td>
      <td>Fried Bologna</td>
      <td>http://www.chicagomag.com/Chicago-Magazine/Nov...</td>
    </tr>
    <tr>
      <th>2</th>
      <td>3</td>
      <td>Xoco</td>
      <td>Woodland Mushroom</td>
      <td>http://www.chicagomag.com/Chicago-Magazine/Nov...</td>
    </tr>
    <tr>
      <th>3</th>
      <td>4</td>
      <td>Al’s Deli</td>
      <td>Roast Beef</td>
      <td>http://www.chicagomag.com/Chicago-Magazine/Nov...</td>
    </tr>
    <tr>
      <th>4</th>
      <td>5</td>
      <td>Publican Quality Meats</td>
      <td>PB&amp;L</td>
      <td>http://www.chicagomag.com/Chicago-Magazine/Nov...</td>
    </tr>
    <tr>
      <th>5</th>
      <td>6</td>
      <td>Hendrickx Belgian Bread Crafter</td>
      <td>Belgian Chicken Curry Salad</td>
      <td>http://www.chicagomag.com/Chicago-Magazine/Nov...</td>
    </tr>
    <tr>
      <th>6</th>
      <td>7</td>
      <td>Acadia</td>
      <td>Lobster Roll</td>
      <td>http://www.chicagomag.com/Chicago-Magazine/Nov...</td>
    </tr>
    <tr>
      <th>7</th>
      <td>8</td>
      <td>Birchwood Kitchen</td>
      <td>Smoked Salmon Salad</td>
      <td>http://www.chicagomag.com/Chicago-Magazine/Nov...</td>
    </tr>
    <tr>
      <th>8</th>
      <td>9</td>
      <td>Cemitas Puebla</td>
      <td>Atomica Cemitas</td>
      <td>http://www.chicagomag.com/Chicago-Magazine/Nov...</td>
    </tr>
    <tr>
      <th>9</th>
      <td>10</td>
      <td>Nana</td>
      <td>Grilled Laughing Bird Shrimp and Fried Po’ Boy</td>
      <td>http://www.chicagomag.com/Chicago-Magazine/Nov...</td>
    </tr>
  </tbody>
</table>
</div>




```python
len(price), len(address), len(df)
```




    (50, 50, 50)




```python
df['Price'] = price
df['Address'] = address

df = df.loc[:, ['Rank', 'Cafe', 'Menu', 'Price', 'Address']]
df.set_index('Rank', inplace=True)
df.head()
```




<div>
<style scoped>
    .dataframe tbody tr th:only-of-type {
        vertical-align: middle;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }

    .dataframe thead th {
        text-align: right;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>Cafe</th>
      <th>Menu</th>
      <th>Price</th>
      <th>Address</th>
    </tr>
    <tr>
      <th>Rank</th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>1</th>
      <td>Old Oak Tap</td>
      <td>BLT</td>
      <td>$10</td>
      <td>2109 W. Chicago Ave.,</td>
    </tr>
    <tr>
      <th>2</th>
      <td>Au Cheval</td>
      <td>Fried Bologna</td>
      <td>$9</td>
      <td>800 W. Randolph St.,</td>
    </tr>
    <tr>
      <th>3</th>
      <td>Xoco</td>
      <td>Woodland Mushroom</td>
      <td>$9.50</td>
      <td>445 N. Clark St.,</td>
    </tr>
    <tr>
      <th>4</th>
      <td>Al’s Deli</td>
      <td>Roast Beef</td>
      <td>$9.40</td>
      <td>914 Noyes St., Evanston,</td>
    </tr>
    <tr>
      <th>5</th>
      <td>Publican Quality Meats</td>
      <td>PB&amp;L</td>
      <td>$10</td>
      <td>825 W. Fulton Mkt.,</td>
    </tr>
  </tbody>
</table>
</div>




```python
df.to_csv('../data/03. best_sandwiches_list_chicago2.csv', sep=',', 
          encoding='UTF-8')
```

## 맛집 위치를 지도에 표기하기


```python
import folium
import pandas as pd
import googlemaps
import numpy as np
```


```python
df = pd.read_csv('../data/03. best_sandwiches_list_chicago2.csv', index_col=0)
df.head(5)
```




<div>
<style scoped>
    .dataframe tbody tr th:only-of-type {
        vertical-align: middle;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }

    .dataframe thead th {
        text-align: right;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>Cafe</th>
      <th>Menu</th>
      <th>Price</th>
      <th>Address</th>
    </tr>
    <tr>
      <th>Rank</th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>1</th>
      <td>Old Oak Tap</td>
      <td>BLT</td>
      <td>$10</td>
      <td>2109 W. Chicago Ave.,</td>
    </tr>
    <tr>
      <th>2</th>
      <td>Au Cheval</td>
      <td>Fried Bologna</td>
      <td>$9</td>
      <td>800 W. Randolph St.,</td>
    </tr>
    <tr>
      <th>3</th>
      <td>Xoco</td>
      <td>Woodland Mushroom</td>
      <td>$9.50</td>
      <td>445 N. Clark St.,</td>
    </tr>
    <tr>
      <th>4</th>
      <td>Al’s Deli</td>
      <td>Roast Beef</td>
      <td>$9.40</td>
      <td>914 Noyes St., Evanston,</td>
    </tr>
    <tr>
      <th>5</th>
      <td>Publican Quality Meats</td>
      <td>PB&amp;L</td>
      <td>$10</td>
      <td>825 W. Fulton Mkt.,</td>
    </tr>
  </tbody>
</table>
</div>




```python
gmaps_key = "AIzaSyB_id_BNZoD_1_QSXufdkIPD7muNK9Wjlc" # 자신의 key를 사용합니다.
gmaps = googlemaps.Client(key=gmaps_key)
```


```python
lat = []
lng = []

for n in tqdm_notebook(df.index):
    if df['Address'][n] != 'Multiple':
        target_name = df['Address'][n]+', '+'Chicago'
        gmaps_output = gmaps.geocode(target_name)
        location_output = gmaps_output[0].get('geometry')
        lat.append(location_output['location']['lat'])
        lng.append(location_output['location']['lng'])
        
    else:
        lat.append(np.nan)
        lng.append(np.nan)
```


    HBox(children=(IntProgress(value=0, max=50), HTML(value='')))


    
    


```python
len(lat), len(lng)
```




    (50, 50)




```python
df['lat'] = lat
df['lng'] = lng
df.head()
```




<div>
<style scoped>
    .dataframe tbody tr th:only-of-type {
        vertical-align: middle;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }

    .dataframe thead th {
        text-align: right;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>Cafe</th>
      <th>Menu</th>
      <th>Price</th>
      <th>Address</th>
      <th>lat</th>
      <th>lng</th>
    </tr>
    <tr>
      <th>Rank</th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>1</th>
      <td>Old Oak Tap</td>
      <td>BLT</td>
      <td>$10</td>
      <td>2109 W. Chicago Ave.,</td>
      <td>41.895605</td>
      <td>-87.679961</td>
    </tr>
    <tr>
      <th>2</th>
      <td>Au Cheval</td>
      <td>Fried Bologna</td>
      <td>$9</td>
      <td>800 W. Randolph St.,</td>
      <td>41.884658</td>
      <td>-87.647667</td>
    </tr>
    <tr>
      <th>3</th>
      <td>Xoco</td>
      <td>Woodland Mushroom</td>
      <td>$9.50</td>
      <td>445 N. Clark St.,</td>
      <td>41.890618</td>
      <td>-87.630933</td>
    </tr>
    <tr>
      <th>4</th>
      <td>Al’s Deli</td>
      <td>Roast Beef</td>
      <td>$9.40</td>
      <td>914 Noyes St., Evanston,</td>
      <td>42.058322</td>
      <td>-87.683748</td>
    </tr>
    <tr>
      <th>5</th>
      <td>Publican Quality Meats</td>
      <td>PB&amp;L</td>
      <td>$10</td>
      <td>825 W. Fulton Mkt.,</td>
      <td>41.886606</td>
      <td>-87.648494</td>
    </tr>
  </tbody>
</table>
</div>




```python
mapping = folium.Map(location=[df['lat'].mean(), df['lng'].mean()], 
                                      zoom_start=11)
folium.Marker([df['lat'].mean(), df['lng'].mean()], 
                                      popup='center').add_to(mapping)
mapping
```




<div style="width:100%;"><div style="position:relative;width:100%;height:0;padding-bottom:60%;"><iframe src="data:text/html;charset=utf-8;base64,PCFET0NUWVBFIGh0bWw+CjxoZWFkPiAgICAKICAgIDxtZXRhIGh0dHAtZXF1aXY9ImNvbnRlbnQtdHlwZSIgY29udGVudD0idGV4dC9odG1sOyBjaGFyc2V0PVVURi04IiAvPgogICAgPHNjcmlwdD5MX1BSRUZFUl9DQU5WQVM9ZmFsc2U7IExfTk9fVE9VQ0g9ZmFsc2U7IExfRElTQUJMRV8zRD1mYWxzZTs8L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmpzIj48L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2FqYXguZ29vZ2xlYXBpcy5jb20vYWpheC9saWJzL2pxdWVyeS8xLjExLjEvanF1ZXJ5Lm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvanMvYm9vdHN0cmFwLm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9jZG5qcy5jbG91ZGZsYXJlLmNvbS9hamF4L2xpYnMvTGVhZmxldC5hd2Vzb21lLW1hcmtlcnMvMi4wLjIvbGVhZmxldC5hd2Vzb21lLW1hcmtlcnMuanMiPjwvc2NyaXB0PgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL21heGNkbi5ib290c3RyYXBjZG4uY29tL2Jvb3RzdHJhcC8zLjIuMC9jc3MvYm9vdHN0cmFwLm1pbi5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvY3NzL2Jvb3RzdHJhcC10aGVtZS5taW4uY3NzIi8+CiAgICA8bGluayByZWw9InN0eWxlc2hlZXQiIGhyZWY9Imh0dHBzOi8vbWF4Y2RuLmJvb3RzdHJhcGNkbi5jb20vZm9udC1hd2Vzb21lLzQuNi4zL2Nzcy9mb250LWF3ZXNvbWUubWluLmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2NkbmpzLmNsb3VkZmxhcmUuY29tL2FqYXgvbGlicy9MZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy8yLjAuMi9sZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9yYXdjZG4uZ2l0aGFjay5jb20vcHl0aG9uLXZpc3VhbGl6YXRpb24vZm9saXVtL21hc3Rlci9mb2xpdW0vdGVtcGxhdGVzL2xlYWZsZXQuYXdlc29tZS5yb3RhdGUuY3NzIi8+CiAgICA8c3R5bGU+aHRtbCwgYm9keSB7d2lkdGg6IDEwMCU7aGVpZ2h0OiAxMDAlO21hcmdpbjogMDtwYWRkaW5nOiAwO308L3N0eWxlPgogICAgPHN0eWxlPiNtYXAge3Bvc2l0aW9uOmFic29sdXRlO3RvcDowO2JvdHRvbTowO3JpZ2h0OjA7bGVmdDowO308L3N0eWxlPgogICAgCiAgICA8bWV0YSBuYW1lPSJ2aWV3cG9ydCIgY29udGVudD0id2lkdGg9ZGV2aWNlLXdpZHRoLAogICAgICAgIGluaXRpYWwtc2NhbGU9MS4wLCBtYXhpbXVtLXNjYWxlPTEuMCwgdXNlci1zY2FsYWJsZT1ubyIgLz4KICAgIDxzdHlsZT4jbWFwX2I0YWQ4NWY4NTkxNzRjMjg4N2VhNGQ0ZDFlOTI5NmM4IHsKICAgICAgICBwb3NpdGlvbjogcmVsYXRpdmU7CiAgICAgICAgd2lkdGg6IDEwMC4wJTsKICAgICAgICBoZWlnaHQ6IDEwMC4wJTsKICAgICAgICBsZWZ0OiAwLjAlOwogICAgICAgIHRvcDogMC4wJTsKICAgICAgICB9CiAgICA8L3N0eWxlPgo8L2hlYWQ+Cjxib2R5PiAgICAKICAgIAogICAgPGRpdiBjbGFzcz0iZm9saXVtLW1hcCIgaWQ9Im1hcF9iNGFkODVmODU5MTc0YzI4ODdlYTRkNGQxZTkyOTZjOCIgPjwvZGl2Pgo8L2JvZHk+CjxzY3JpcHQ+ICAgIAogICAgCiAgICAKICAgICAgICB2YXIgYm91bmRzID0gbnVsbDsKICAgIAoKICAgIHZhciBtYXBfYjRhZDg1Zjg1OTE3NGMyODg3ZWE0ZDRkMWU5Mjk2YzggPSBMLm1hcCgKICAgICAgICAnbWFwX2I0YWQ4NWY4NTkxNzRjMjg4N2VhNGQ0ZDFlOTI5NmM4JywgewogICAgICAgIGNlbnRlcjogWzQxLjkyNDI0NjYwNjgxODE4LCAtODcuNjgxMDA5NjcwNDU0NTFdLAogICAgICAgIHpvb206IDExLAogICAgICAgIG1heEJvdW5kczogYm91bmRzLAogICAgICAgIGxheWVyczogW10sCiAgICAgICAgd29ybGRDb3B5SnVtcDogZmFsc2UsCiAgICAgICAgY3JzOiBMLkNSUy5FUFNHMzg1NywKICAgICAgICB6b29tQ29udHJvbDogdHJ1ZSwKICAgICAgICB9KTsKCiAgICAKICAgIAogICAgdmFyIHRpbGVfbGF5ZXJfY2M4MGM5MDQ3ZjQzNDkxMThkZWE3ZThkODEzODMzZDQgPSBMLnRpbGVMYXllcigKICAgICAgICAnaHR0cHM6Ly97c30udGlsZS5vcGVuc3RyZWV0bWFwLm9yZy97en0ve3h9L3t5fS5wbmcnLAogICAgICAgIHsKICAgICAgICAiYXR0cmlidXRpb24iOiBudWxsLAogICAgICAgICJkZXRlY3RSZXRpbmEiOiBmYWxzZSwKICAgICAgICAibWF4TmF0aXZlWm9vbSI6IDE4LAogICAgICAgICJtYXhab29tIjogMTgsCiAgICAgICAgIm1pblpvb20iOiAwLAogICAgICAgICJub1dyYXAiOiBmYWxzZSwKICAgICAgICAib3BhY2l0eSI6IDEsCiAgICAgICAgInN1YmRvbWFpbnMiOiAiYWJjIiwKICAgICAgICAidG1zIjogZmFsc2UKfSkuYWRkVG8obWFwX2I0YWQ4NWY4NTkxNzRjMjg4N2VhNGQ0ZDFlOTI5NmM4KTsKICAgIAogICAgICAgIHZhciBtYXJrZXJfMjk4OTJhNzY2YjI0NGYxNTgyNGI1NDIzYTIwY2VjNjEgPSBMLm1hcmtlcigKICAgICAgICAgICAgWzQxLjkyNDI0NjYwNjgxODE4LCAtODcuNjgxMDA5NjcwNDU0NTFdLAogICAgICAgICAgICB7CiAgICAgICAgICAgICAgICBpY29uOiBuZXcgTC5JY29uLkRlZmF1bHQoKQogICAgICAgICAgICAgICAgfQogICAgICAgICAgICApLmFkZFRvKG1hcF9iNGFkODVmODU5MTc0YzI4ODdlYTRkNGQxZTkyOTZjOCk7CiAgICAgICAgCiAgICAKICAgICAgICAgICAgdmFyIHBvcHVwX2IwM2NiMWQ4ZDMzYjQ5NWVhMDNhMjBiYTg2ZmQ1NWM2ID0gTC5wb3B1cCh7bWF4V2lkdGg6ICczMDAnCiAgICAgICAgICAgIAogICAgICAgICAgICB9KTsKCiAgICAgICAgICAgIAogICAgICAgICAgICAgICAgdmFyIGh0bWxfOTVhOGNiZTBlMGFmNDMyOTlkYzk5NTAyOTgwN2UwNzEgPSAkKGA8ZGl2IGlkPSJodG1sXzk1YThjYmUwZTBhZjQzMjk5ZGM5OTUwMjk4MDdlMDcxIiBzdHlsZT0id2lkdGg6IDEwMC4wJTsgaGVpZ2h0OiAxMDAuMCU7Ij5jZW50ZXI8L2Rpdj5gKVswXTsKICAgICAgICAgICAgICAgIHBvcHVwX2IwM2NiMWQ4ZDMzYjQ5NWVhMDNhMjBiYTg2ZmQ1NWM2LnNldENvbnRlbnQoaHRtbF85NWE4Y2JlMGUwYWY0MzI5OWRjOTk1MDI5ODA3ZTA3MSk7CiAgICAgICAgICAgIAoKICAgICAgICAgICAgbWFya2VyXzI5ODkyYTc2NmIyNDRmMTU4MjRiNTQyM2EyMGNlYzYxLmJpbmRQb3B1cChwb3B1cF9iMDNjYjFkOGQzM2I0OTVlYTAzYTIwYmE4NmZkNTVjNikKICAgICAgICAgICAgOwoKICAgICAgICAgICAgCiAgICAgICAgCjwvc2NyaXB0Pg==" style="position:absolute;width:100%;height:100%;left:0;top:0;border:none !important;" allowfullscreen webkitallowfullscreen mozallowfullscreen></iframe></div></div>




```python
mapping = folium.Map(location=[df['lat'].mean(), df['lng'].mean()], 
                     zoom_start=11)

for n in df.index:
    if df['Address'][n] != 'Multiple':
        folium.Marker([df['lat'][n], df['lng'][n]], 
                                      popup=df['Cafe'][n]).add_to(mapping)

mapping
```




<div style="width:100%;"><div style="position:relative;width:100%;height:0;padding-bottom:60%;"><iframe src="data:text/html;charset=utf-8;base64,PCFET0NUWVBFIGh0bWw+CjxoZWFkPiAgICAKICAgIDxtZXRhIGh0dHAtZXF1aXY9ImNvbnRlbnQtdHlwZSIgY29udGVudD0idGV4dC9odG1sOyBjaGFyc2V0PVVURi04IiAvPgogICAgPHNjcmlwdD5MX1BSRUZFUl9DQU5WQVM9ZmFsc2U7IExfTk9fVE9VQ0g9ZmFsc2U7IExfRElTQUJMRV8zRD1mYWxzZTs8L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmpzIj48L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2FqYXguZ29vZ2xlYXBpcy5jb20vYWpheC9saWJzL2pxdWVyeS8xLjExLjEvanF1ZXJ5Lm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvanMvYm9vdHN0cmFwLm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9jZG5qcy5jbG91ZGZsYXJlLmNvbS9hamF4L2xpYnMvTGVhZmxldC5hd2Vzb21lLW1hcmtlcnMvMi4wLjIvbGVhZmxldC5hd2Vzb21lLW1hcmtlcnMuanMiPjwvc2NyaXB0PgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL21heGNkbi5ib290c3RyYXBjZG4uY29tL2Jvb3RzdHJhcC8zLjIuMC9jc3MvYm9vdHN0cmFwLm1pbi5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvY3NzL2Jvb3RzdHJhcC10aGVtZS5taW4uY3NzIi8+CiAgICA8bGluayByZWw9InN0eWxlc2hlZXQiIGhyZWY9Imh0dHBzOi8vbWF4Y2RuLmJvb3RzdHJhcGNkbi5jb20vZm9udC1hd2Vzb21lLzQuNi4zL2Nzcy9mb250LWF3ZXNvbWUubWluLmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2NkbmpzLmNsb3VkZmxhcmUuY29tL2FqYXgvbGlicy9MZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy8yLjAuMi9sZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9yYXdjZG4uZ2l0aGFjay5jb20vcHl0aG9uLXZpc3VhbGl6YXRpb24vZm9saXVtL21hc3Rlci9mb2xpdW0vdGVtcGxhdGVzL2xlYWZsZXQuYXdlc29tZS5yb3RhdGUuY3NzIi8+CiAgICA8c3R5bGU+aHRtbCwgYm9keSB7d2lkdGg6IDEwMCU7aGVpZ2h0OiAxMDAlO21hcmdpbjogMDtwYWRkaW5nOiAwO308L3N0eWxlPgogICAgPHN0eWxlPiNtYXAge3Bvc2l0aW9uOmFic29sdXRlO3RvcDowO2JvdHRvbTowO3JpZ2h0OjA7bGVmdDowO308L3N0eWxlPgogICAgCiAgICA8bWV0YSBuYW1lPSJ2aWV3cG9ydCIgY29udGVudD0id2lkdGg9ZGV2aWNlLXdpZHRoLAogICAgICAgIGluaXRpYWwtc2NhbGU9MS4wLCBtYXhpbXVtLXNjYWxlPTEuMCwgdXNlci1zY2FsYWJsZT1ubyIgLz4KICAgIDxzdHlsZT4jbWFwX2E1OTM2ZWJmODA4MjQzZDBiYWFmOTlmNTJmNzQxYTE3IHsKICAgICAgICBwb3NpdGlvbjogcmVsYXRpdmU7CiAgICAgICAgd2lkdGg6IDEwMC4wJTsKICAgICAgICBoZWlnaHQ6IDEwMC4wJTsKICAgICAgICBsZWZ0OiAwLjAlOwogICAgICAgIHRvcDogMC4wJTsKICAgICAgICB9CiAgICA8L3N0eWxlPgo8L2hlYWQ+Cjxib2R5PiAgICAKICAgIAogICAgPGRpdiBjbGFzcz0iZm9saXVtLW1hcCIgaWQ9Im1hcF9hNTkzNmViZjgwODI0M2QwYmFhZjk5ZjUyZjc0MWExNyIgPjwvZGl2Pgo8L2JvZHk+CjxzY3JpcHQ+ICAgIAogICAgCiAgICAKICAgICAgICB2YXIgYm91bmRzID0gbnVsbDsKICAgIAoKICAgIHZhciBtYXBfYTU5MzZlYmY4MDgyNDNkMGJhYWY5OWY1MmY3NDFhMTcgPSBMLm1hcCgKICAgICAgICAnbWFwX2E1OTM2ZWJmODA4MjQzZDBiYWFmOTlmNTJmNzQxYTE3JywgewogICAgICAgIGNlbnRlcjogWzQxLjkyNDI0NjYwNjgxODE4LCAtODcuNjgxMDA5NjcwNDU0NTFdLAogICAgICAgIHpvb206IDExLAogICAgICAgIG1heEJvdW5kczogYm91bmRzLAogICAgICAgIGxheWVyczogW10sCiAgICAgICAgd29ybGRDb3B5SnVtcDogZmFsc2UsCiAgICAgICAgY3JzOiBMLkNSUy5FUFNHMzg1NywKICAgICAgICB6b29tQ29udHJvbDogdHJ1ZSwKICAgICAgICB9KTsKCiAgICAKICAgIAogICAgdmFyIHRpbGVfbGF5ZXJfYjhhODg3M2NiZjUwNDY2Y2IzZmM4Y2UwMTgzMTI3M2IgPSBMLnRpbGVMYXllcigKICAgICAgICAnaHR0cHM6Ly97c30udGlsZS5vcGVuc3RyZWV0bWFwLm9yZy97en0ve3h9L3t5fS5wbmcnLAogICAgICAgIHsKICAgICAgICAiYXR0cmlidXRpb24iOiBudWxsLAogICAgICAgICJkZXRlY3RSZXRpbmEiOiBmYWxzZSwKICAgICAgICAibWF4TmF0aXZlWm9vbSI6IDE4LAogICAgICAgICJtYXhab29tIjogMTgsCiAgICAgICAgIm1pblpvb20iOiAwLAogICAgICAgICJub1dyYXAiOiBmYWxzZSwKICAgICAgICAib3BhY2l0eSI6IDEsCiAgICAgICAgInN1YmRvbWFpbnMiOiAiYWJjIiwKICAgICAgICAidG1zIjogZmFsc2UKfSkuYWRkVG8obWFwX2E1OTM2ZWJmODA4MjQzZDBiYWFmOTlmNTJmNzQxYTE3KTsKICAgIAogICAgICAgIHZhciBtYXJrZXJfYjdiNjAwMDJhNzJkNGRmMGFlMGFmYzA4NDkyMzdiZmEgPSBMLm1hcmtlcigKICAgICAgICAgICAgWzQxLjg5NTYwNDksIC04Ny42Nzk5NjE0OTk5OTk5OV0sCiAgICAgICAgICAgIHsKICAgICAgICAgICAgICAgIGljb246IG5ldyBMLkljb24uRGVmYXVsdCgpCiAgICAgICAgICAgICAgICB9CiAgICAgICAgICAgICkuYWRkVG8obWFwX2E1OTM2ZWJmODA4MjQzZDBiYWFmOTlmNTJmNzQxYTE3KTsKICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgcG9wdXBfOGY4NjYxYmVhMGFkNDE3MGJhNjU3Y2NhYzBkNzFiN2UgPSBMLnBvcHVwKHttYXhXaWR0aDogJzMwMCcKICAgICAgICAgICAgCiAgICAgICAgICAgIH0pOwoKICAgICAgICAgICAgCiAgICAgICAgICAgICAgICB2YXIgaHRtbF9lNTcwMDI2ODQxNmY0OTA5ODhkZGM5MjllYzU5N2IwNiA9ICQoYDxkaXYgaWQ9Imh0bWxfZTU3MDAyNjg0MTZmNDkwOTg4ZGRjOTI5ZWM1OTdiMDYiIHN0eWxlPSJ3aWR0aDogMTAwLjAlOyBoZWlnaHQ6IDEwMC4wJTsiPk9sZCBPYWsgVGFwPC9kaXY+YClbMF07CiAgICAgICAgICAgICAgICBwb3B1cF84Zjg2NjFiZWEwYWQ0MTcwYmE2NTdjY2FjMGQ3MWI3ZS5zZXRDb250ZW50KGh0bWxfZTU3MDAyNjg0MTZmNDkwOTg4ZGRjOTI5ZWM1OTdiMDYpOwogICAgICAgICAgICAKCiAgICAgICAgICAgIG1hcmtlcl9iN2I2MDAwMmE3MmQ0ZGYwYWUwYWZjMDg0OTIzN2JmYS5iaW5kUG9wdXAocG9wdXBfOGY4NjYxYmVhMGFkNDE3MGJhNjU3Y2NhYzBkNzFiN2UpCiAgICAgICAgICAgIDsKCiAgICAgICAgICAgIAogICAgICAgIAogICAgCiAgICAgICAgdmFyIG1hcmtlcl80ZTM3ZGQ1ZmRkNjM0M2EzODU3YzNiYTgxOWY0Y2M1ZSA9IEwubWFya2VyKAogICAgICAgICAgICBbNDEuODg0NjU4MiwgLTg3LjY0NzY2NjhdLAogICAgICAgICAgICB7CiAgICAgICAgICAgICAgICBpY29uOiBuZXcgTC5JY29uLkRlZmF1bHQoKQogICAgICAgICAgICAgICAgfQogICAgICAgICAgICApLmFkZFRvKG1hcF9hNTkzNmViZjgwODI0M2QwYmFhZjk5ZjUyZjc0MWExNyk7CiAgICAgICAgCiAgICAKICAgICAgICAgICAgdmFyIHBvcHVwX2M0MGY2MjBmMzA4MDRkN2JiYjRiYWFhZTJiZjJkYjc2ID0gTC5wb3B1cCh7bWF4V2lkdGg6ICczMDAnCiAgICAgICAgICAgIAogICAgICAgICAgICB9KTsKCiAgICAgICAgICAgIAogICAgICAgICAgICAgICAgdmFyIGh0bWxfMTk2Y2VlNGM4NTZjNGE5YjllOWE2YjE3YjZlYWJmNzIgPSAkKGA8ZGl2IGlkPSJodG1sXzE5NmNlZTRjODU2YzRhOWI5ZTlhNmIxN2I2ZWFiZjcyIiBzdHlsZT0id2lkdGg6IDEwMC4wJTsgaGVpZ2h0OiAxMDAuMCU7Ij5BdSBDaGV2YWw8L2Rpdj5gKVswXTsKICAgICAgICAgICAgICAgIHBvcHVwX2M0MGY2MjBmMzA4MDRkN2JiYjRiYWFhZTJiZjJkYjc2LnNldENvbnRlbnQoaHRtbF8xOTZjZWU0Yzg1NmM0YTliOWU5YTZiMTdiNmVhYmY3Mik7CiAgICAgICAgICAgIAoKICAgICAgICAgICAgbWFya2VyXzRlMzdkZDVmZGQ2MzQzYTM4NTdjM2JhODE5ZjRjYzVlLmJpbmRQb3B1cChwb3B1cF9jNDBmNjIwZjMwODA0ZDdiYmI0YmFhYWUyYmYyZGI3NikKICAgICAgICAgICAgOwoKICAgICAgICAgICAgCiAgICAgICAgCiAgICAKICAgICAgICB2YXIgbWFya2VyXzk4MDkzZWYzMjZjYjRmZmJiNWMxMGZhZmMxODhjMjIxID0gTC5tYXJrZXIoCiAgICAgICAgICAgIFs0MS44OTA2MTgyLCAtODcuNjMwOTMyODk5OTk5OTldLAogICAgICAgICAgICB7CiAgICAgICAgICAgICAgICBpY29uOiBuZXcgTC5JY29uLkRlZmF1bHQoKQogICAgICAgICAgICAgICAgfQogICAgICAgICAgICApLmFkZFRvKG1hcF9hNTkzNmViZjgwODI0M2QwYmFhZjk5ZjUyZjc0MWExNyk7CiAgICAgICAgCiAgICAKICAgICAgICAgICAgdmFyIHBvcHVwXzA5ZDlkYTU4ZTU1NTRiMGM4MWVkZTI2YjI1ODJjZmU0ID0gTC5wb3B1cCh7bWF4V2lkdGg6ICczMDAnCiAgICAgICAgICAgIAogICAgICAgICAgICB9KTsKCiAgICAgICAgICAgIAogICAgICAgICAgICAgICAgdmFyIGh0bWxfNmQwZmRmMjI0ODQxNGZiMDg0Y2I1Mjk5ZGQzZDdjYjIgPSAkKGA8ZGl2IGlkPSJodG1sXzZkMGZkZjIyNDg0MTRmYjA4NGNiNTI5OWRkM2Q3Y2IyIiBzdHlsZT0id2lkdGg6IDEwMC4wJTsgaGVpZ2h0OiAxMDAuMCU7Ij5Yb2NvPC9kaXY+YClbMF07CiAgICAgICAgICAgICAgICBwb3B1cF8wOWQ5ZGE1OGU1NTU0YjBjODFlZGUyNmIyNTgyY2ZlNC5zZXRDb250ZW50KGh0bWxfNmQwZmRmMjI0ODQxNGZiMDg0Y2I1Mjk5ZGQzZDdjYjIpOwogICAgICAgICAgICAKCiAgICAgICAgICAgIG1hcmtlcl85ODA5M2VmMzI2Y2I0ZmZiYjVjMTBmYWZjMTg4YzIyMS5iaW5kUG9wdXAocG9wdXBfMDlkOWRhNThlNTU1NGIwYzgxZWRlMjZiMjU4MmNmZTQpCiAgICAgICAgICAgIDsKCiAgICAgICAgICAgIAogICAgICAgIAogICAgCiAgICAgICAgdmFyIG1hcmtlcl80YzE2NDAzODBkZjA0MDg1YWFmNzFjNGZmOTdkMTQxYiA9IEwubWFya2VyKAogICAgICAgICAgICBbNDIuMDU4MzIxNywgLTg3LjY4Mzc0NzldLAogICAgICAgICAgICB7CiAgICAgICAgICAgICAgICBpY29uOiBuZXcgTC5JY29uLkRlZmF1bHQoKQogICAgICAgICAgICAgICAgfQogICAgICAgICAgICApLmFkZFRvKG1hcF9hNTkzNmViZjgwODI0M2QwYmFhZjk5ZjUyZjc0MWExNyk7CiAgICAgICAgCiAgICAKICAgICAgICAgICAgdmFyIHBvcHVwXzI3NTk2YTQwZjNiOTQ3ZTA5MTIwZTAzOGM0ZGEwMzBlID0gTC5wb3B1cCh7bWF4V2lkdGg6ICczMDAnCiAgICAgICAgICAgIAogICAgICAgICAgICB9KTsKCiAgICAgICAgICAgIAogICAgICAgICAgICAgICAgdmFyIGh0bWxfNDViY2ExNmM3ODY4NGRmMzllZjhkODQzNDI2OGY0NTEgPSAkKGA8ZGl2IGlkPSJodG1sXzQ1YmNhMTZjNzg2ODRkZjM5ZWY4ZDg0MzQyNjhmNDUxIiBzdHlsZT0id2lkdGg6IDEwMC4wJTsgaGVpZ2h0OiAxMDAuMCU7Ij5BbOKAmXMgRGVsaTwvZGl2PmApWzBdOwogICAgICAgICAgICAgICAgcG9wdXBfMjc1OTZhNDBmM2I5NDdlMDkxMjBlMDM4YzRkYTAzMGUuc2V0Q29udGVudChodG1sXzQ1YmNhMTZjNzg2ODRkZjM5ZWY4ZDg0MzQyNjhmNDUxKTsKICAgICAgICAgICAgCgogICAgICAgICAgICBtYXJrZXJfNGMxNjQwMzgwZGYwNDA4NWFhZjcxYzRmZjk3ZDE0MWIuYmluZFBvcHVwKHBvcHVwXzI3NTk2YTQwZjNiOTQ3ZTA5MTIwZTAzOGM0ZGEwMzBlKQogICAgICAgICAgICA7CgogICAgICAgICAgICAKICAgICAgICAKICAgIAogICAgICAgIHZhciBtYXJrZXJfNWIxMjMwYTEzMWVhNDZhYTliNTJmZGNiZWYzMDk1NWQgPSBMLm1hcmtlcigKICAgICAgICAgICAgWzQxLjg4NjYwNTYsIC04Ny42NDg0OTM1XSwKICAgICAgICAgICAgewogICAgICAgICAgICAgICAgaWNvbjogbmV3IEwuSWNvbi5EZWZhdWx0KCkKICAgICAgICAgICAgICAgIH0KICAgICAgICAgICAgKS5hZGRUbyhtYXBfYTU5MzZlYmY4MDgyNDNkMGJhYWY5OWY1MmY3NDFhMTcpOwogICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBwb3B1cF8wZTdmZGUyNGM0Mjg0ZTAzOTNjNDEyN2JkZjY3MzE3ZCA9IEwucG9wdXAoe21heFdpZHRoOiAnMzAwJwogICAgICAgICAgICAKICAgICAgICAgICAgfSk7CgogICAgICAgICAgICAKICAgICAgICAgICAgICAgIHZhciBodG1sXzA3YmRkNjY3ZTJkYzRiODg5YzU3MGMxODM1YmM2MDdmID0gJChgPGRpdiBpZD0iaHRtbF8wN2JkZDY2N2UyZGM0Yjg4OWM1NzBjMTgzNWJjNjA3ZiIgc3R5bGU9IndpZHRoOiAxMDAuMCU7IGhlaWdodDogMTAwLjAlOyI+UHVibGljYW4gUXVhbGl0eSBNZWF0czwvZGl2PmApWzBdOwogICAgICAgICAgICAgICAgcG9wdXBfMGU3ZmRlMjRjNDI4NGUwMzkzYzQxMjdiZGY2NzMxN2Quc2V0Q29udGVudChodG1sXzA3YmRkNjY3ZTJkYzRiODg5YzU3MGMxODM1YmM2MDdmKTsKICAgICAgICAgICAgCgogICAgICAgICAgICBtYXJrZXJfNWIxMjMwYTEzMWVhNDZhYTliNTJmZGNiZWYzMDk1NWQuYmluZFBvcHVwKHBvcHVwXzBlN2ZkZTI0YzQyODRlMDM5M2M0MTI3YmRmNjczMTdkKQogICAgICAgICAgICA7CgogICAgICAgICAgICAKICAgICAgICAKICAgIAogICAgICAgIHZhciBtYXJrZXJfN2RjODQ4OGVlYjkxNGNkN2E2MDdiZDAwNzIwNjNmZGUgPSBMLm1hcmtlcigKICAgICAgICAgICAgWzQxLjkwMDI0NiwgLTg3LjYyNTE2MzM5OTk5OTk5XSwKICAgICAgICAgICAgewogICAgICAgICAgICAgICAgaWNvbjogbmV3IEwuSWNvbi5EZWZhdWx0KCkKICAgICAgICAgICAgICAgIH0KICAgICAgICAgICAgKS5hZGRUbyhtYXBfYTU5MzZlYmY4MDgyNDNkMGJhYWY5OWY1MmY3NDFhMTcpOwogICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBwb3B1cF80YTAxZTQzNGU3Y2Q0YWE5YWNkNWE1ZjM1NDEwOGQ0YyA9IEwucG9wdXAoe21heFdpZHRoOiAnMzAwJwogICAgICAgICAgICAKICAgICAgICAgICAgfSk7CgogICAgICAgICAgICAKICAgICAgICAgICAgICAgIHZhciBodG1sXzdjYTE3MmFlNTljZjRkOWQ4MjcyZGVmZjVlMjViNTc2ID0gJChgPGRpdiBpZD0iaHRtbF83Y2ExNzJhZTU5Y2Y0ZDlkODI3MmRlZmY1ZTI1YjU3NiIgc3R5bGU9IndpZHRoOiAxMDAuMCU7IGhlaWdodDogMTAwLjAlOyI+SGVuZHJpY2t4IEJlbGdpYW4gQnJlYWQgQ3JhZnRlcjwvZGl2PmApWzBdOwogICAgICAgICAgICAgICAgcG9wdXBfNGEwMWU0MzRlN2NkNGFhOWFjZDVhNWYzNTQxMDhkNGMuc2V0Q29udGVudChodG1sXzdjYTE3MmFlNTljZjRkOWQ4MjcyZGVmZjVlMjViNTc2KTsKICAgICAgICAgICAgCgogICAgICAgICAgICBtYXJrZXJfN2RjODQ4OGVlYjkxNGNkN2E2MDdiZDAwNzIwNjNmZGUuYmluZFBvcHVwKHBvcHVwXzRhMDFlNDM0ZTdjZDRhYTlhY2Q1YTVmMzU0MTA4ZDRjKQogICAgICAgICAgICA7CgogICAgICAgICAgICAKICAgICAgICAKICAgIAogICAgICAgIHZhciBtYXJrZXJfY2QyNTdiZGNjNGQwNDE4NzllZGI3ZTJlYmE0ZTQyZWIgPSBMLm1hcmtlcigKICAgICAgICAgICAgWzQxLjg1OTA2NTgsIC04Ny42MjUyMDQ5XSwKICAgICAgICAgICAgewogICAgICAgICAgICAgICAgaWNvbjogbmV3IEwuSWNvbi5EZWZhdWx0KCkKICAgICAgICAgICAgICAgIH0KICAgICAgICAgICAgKS5hZGRUbyhtYXBfYTU5MzZlYmY4MDgyNDNkMGJhYWY5OWY1MmY3NDFhMTcpOwogICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBwb3B1cF9hZDI2YzU2ZDNmOTE0ZDU3YmY5MTgwMjE4ZWJiY2E2YiA9IEwucG9wdXAoe21heFdpZHRoOiAnMzAwJwogICAgICAgICAgICAKICAgICAgICAgICAgfSk7CgogICAgICAgICAgICAKICAgICAgICAgICAgICAgIHZhciBodG1sX2YwMTg3NDQ3NTM4OTRhZDZhOWM1ZDQzNDQ5OWFhMGZjID0gJChgPGRpdiBpZD0iaHRtbF9mMDE4NzQ0NzUzODk0YWQ2YTljNWQ0MzQ0OTlhYTBmYyIgc3R5bGU9IndpZHRoOiAxMDAuMCU7IGhlaWdodDogMTAwLjAlOyI+QWNhZGlhPC9kaXY+YClbMF07CiAgICAgICAgICAgICAgICBwb3B1cF9hZDI2YzU2ZDNmOTE0ZDU3YmY5MTgwMjE4ZWJiY2E2Yi5zZXRDb250ZW50KGh0bWxfZjAxODc0NDc1Mzg5NGFkNmE5YzVkNDM0NDk5YWEwZmMpOwogICAgICAgICAgICAKCiAgICAgICAgICAgIG1hcmtlcl9jZDI1N2JkY2M0ZDA0MTg3OWVkYjdlMmViYTRlNDJlYi5iaW5kUG9wdXAocG9wdXBfYWQyNmM1NmQzZjkxNGQ1N2JmOTE4MDIxOGViYmNhNmIpCiAgICAgICAgICAgIDsKCiAgICAgICAgICAgIAogICAgICAgIAogICAgCiAgICAgICAgdmFyIG1hcmtlcl9kN2ZhZjZjYzY0MmE0ODg3OWY2YWFkNTRkMmU5NzRjNiA9IEwubWFya2VyKAogICAgICAgICAgICBbNDEuOTEwMjAzMSwgLTg3LjY4Mjg3NTI5OTk5OTk5XSwKICAgICAgICAgICAgewogICAgICAgICAgICAgICAgaWNvbjogbmV3IEwuSWNvbi5EZWZhdWx0KCkKICAgICAgICAgICAgICAgIH0KICAgICAgICAgICAgKS5hZGRUbyhtYXBfYTU5MzZlYmY4MDgyNDNkMGJhYWY5OWY1MmY3NDFhMTcpOwogICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBwb3B1cF9kNDE0MGQ2YWY4OGU0MDYxODMyNTRmM2I4ZDBlODRkMSA9IEwucG9wdXAoe21heFdpZHRoOiAnMzAwJwogICAgICAgICAgICAKICAgICAgICAgICAgfSk7CgogICAgICAgICAgICAKICAgICAgICAgICAgICAgIHZhciBodG1sXzBkZjIxMWFhNDcwYTQxYTVhMWQ5MzliNjQ0NWEzYzFmID0gJChgPGRpdiBpZD0iaHRtbF8wZGYyMTFhYTQ3MGE0MWE1YTFkOTM5YjY0NDVhM2MxZiIgc3R5bGU9IndpZHRoOiAxMDAuMCU7IGhlaWdodDogMTAwLjAlOyI+QmlyY2h3b29kIEtpdGNoZW48L2Rpdj5gKVswXTsKICAgICAgICAgICAgICAgIHBvcHVwX2Q0MTQwZDZhZjg4ZTQwNjE4MzI1NGYzYjhkMGU4NGQxLnNldENvbnRlbnQoaHRtbF8wZGYyMTFhYTQ3MGE0MWE1YTFkOTM5YjY0NDVhM2MxZik7CiAgICAgICAgICAgIAoKICAgICAgICAgICAgbWFya2VyX2Q3ZmFmNmNjNjQyYTQ4ODc5ZjZhYWQ1NGQyZTk3NGM2LmJpbmRQb3B1cChwb3B1cF9kNDE0MGQ2YWY4OGU0MDYxODMyNTRmM2I4ZDBlODRkMSkKICAgICAgICAgICAgOwoKICAgICAgICAgICAgCiAgICAgICAgCiAgICAKICAgICAgICB2YXIgbWFya2VyXzk1MTgyZGY4MDEzNDRjNjA5NzgwYjBkN2E5Mzg1ZGU5ID0gTC5tYXJrZXIoCiAgICAgICAgICAgIFs0MS45MDk3NTU4LCAtODcuNzE3NjcyN10sCiAgICAgICAgICAgIHsKICAgICAgICAgICAgICAgIGljb246IG5ldyBMLkljb24uRGVmYXVsdCgpCiAgICAgICAgICAgICAgICB9CiAgICAgICAgICAgICkuYWRkVG8obWFwX2E1OTM2ZWJmODA4MjQzZDBiYWFmOTlmNTJmNzQxYTE3KTsKICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgcG9wdXBfMGEzZTQ0MGU4Zjk0NDIwYmFmYjAyOWMzYTU2MjFlMTMgPSBMLnBvcHVwKHttYXhXaWR0aDogJzMwMCcKICAgICAgICAgICAgCiAgICAgICAgICAgIH0pOwoKICAgICAgICAgICAgCiAgICAgICAgICAgICAgICB2YXIgaHRtbF8zNzU5NDYwYjM0ODc0MDljYjY1MmIxZjAwNGYyMDFjZCA9ICQoYDxkaXYgaWQ9Imh0bWxfMzc1OTQ2MGIzNDg3NDA5Y2I2NTJiMWYwMDRmMjAxY2QiIHN0eWxlPSJ3aWR0aDogMTAwLjAlOyBoZWlnaHQ6IDEwMC4wJTsiPkNlbWl0YXMgUHVlYmxhPC9kaXY+YClbMF07CiAgICAgICAgICAgICAgICBwb3B1cF8wYTNlNDQwZThmOTQ0MjBiYWZiMDI5YzNhNTYyMWUxMy5zZXRDb250ZW50KGh0bWxfMzc1OTQ2MGIzNDg3NDA5Y2I2NTJiMWYwMDRmMjAxY2QpOwogICAgICAgICAgICAKCiAgICAgICAgICAgIG1hcmtlcl85NTE4MmRmODAxMzQ0YzYwOTc4MGIwZDdhOTM4NWRlOS5iaW5kUG9wdXAocG9wdXBfMGEzZTQ0MGU4Zjk0NDIwYmFmYjAyOWMzYTU2MjFlMTMpCiAgICAgICAgICAgIDsKCiAgICAgICAgICAgIAogICAgICAgIAogICAgCiAgICAgICAgdmFyIG1hcmtlcl9jYTExNmVlZDY2OTM0MjkxYjBhNjdkMDczMzFkMTM4ZSA9IEwubWFya2VyKAogICAgICAgICAgICBbNDEuODM0NTQwNSwgLTg3LjY0NTgzODVdLAogICAgICAgICAgICB7CiAgICAgICAgICAgICAgICBpY29uOiBuZXcgTC5JY29uLkRlZmF1bHQoKQogICAgICAgICAgICAgICAgfQogICAgICAgICAgICApLmFkZFRvKG1hcF9hNTkzNmViZjgwODI0M2QwYmFhZjk5ZjUyZjc0MWExNyk7CiAgICAgICAgCiAgICAKICAgICAgICAgICAgdmFyIHBvcHVwX2Q2ZDk5MjVkOGNmNTQ1NzNiZmY1MmMzZmQ0NTdjNDM3ID0gTC5wb3B1cCh7bWF4V2lkdGg6ICczMDAnCiAgICAgICAgICAgIAogICAgICAgICAgICB9KTsKCiAgICAgICAgICAgIAogICAgICAgICAgICAgICAgdmFyIGh0bWxfMTJiOWVjNjFjYzljNDFkODgyNDJjZWJjYzkyYTM1MGYgPSAkKGA8ZGl2IGlkPSJodG1sXzEyYjllYzYxY2M5YzQxZDg4MjQyY2ViY2M5MmEzNTBmIiBzdHlsZT0id2lkdGg6IDEwMC4wJTsgaGVpZ2h0OiAxMDAuMCU7Ij5OYW5hPC9kaXY+YClbMF07CiAgICAgICAgICAgICAgICBwb3B1cF9kNmQ5OTI1ZDhjZjU0NTczYmZmNTJjM2ZkNDU3YzQzNy5zZXRDb250ZW50KGh0bWxfMTJiOWVjNjFjYzljNDFkODgyNDJjZWJjYzkyYTM1MGYpOwogICAgICAgICAgICAKCiAgICAgICAgICAgIG1hcmtlcl9jYTExNmVlZDY2OTM0MjkxYjBhNjdkMDczMzFkMTM4ZS5iaW5kUG9wdXAocG9wdXBfZDZkOTkyNWQ4Y2Y1NDU3M2JmZjUyYzNmZDQ1N2M0MzcpCiAgICAgICAgICAgIDsKCiAgICAgICAgICAgIAogICAgICAgIAogICAgCiAgICAgICAgdmFyIG1hcmtlcl83NDA4NTVmNTE2ZWE0NWQ5YTk5NDU1MGZhMjEyODMxYSA9IEwubWFya2VyKAogICAgICAgICAgICBbNDEuOTI3NjIwNywgLTg3LjcwNjc5Ml0sCiAgICAgICAgICAgIHsKICAgICAgICAgICAgICAgIGljb246IG5ldyBMLkljb24uRGVmYXVsdCgpCiAgICAgICAgICAgICAgICB9CiAgICAgICAgICAgICkuYWRkVG8obWFwX2E1OTM2ZWJmODA4MjQzZDBiYWFmOTlmNTJmNzQxYTE3KTsKICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgcG9wdXBfMTNhZGQ2NzhjZWUxNDkxNjhjZjdiZTk5ZTVlZDEyOWUgPSBMLnBvcHVwKHttYXhXaWR0aDogJzMwMCcKICAgICAgICAgICAgCiAgICAgICAgICAgIH0pOwoKICAgICAgICAgICAgCiAgICAgICAgICAgICAgICB2YXIgaHRtbF81ZTcxMWZkYmRjMDc0YjQzYjU3MGI1ODAzMjgxYWM2ZSA9ICQoYDxkaXYgaWQ9Imh0bWxfNWU3MTFmZGJkYzA3NGI0M2I1NzBiNTgwMzI4MWFjNmUiIHN0eWxlPSJ3aWR0aDogMTAwLjAlOyBoZWlnaHQ6IDEwMC4wJTsiPkx1bGEgQ2FmZTwvZGl2PmApWzBdOwogICAgICAgICAgICAgICAgcG9wdXBfMTNhZGQ2NzhjZWUxNDkxNjhjZjdiZTk5ZTVlZDEyOWUuc2V0Q29udGVudChodG1sXzVlNzExZmRiZGMwNzRiNDNiNTcwYjU4MDMyODFhYzZlKTsKICAgICAgICAgICAgCgogICAgICAgICAgICBtYXJrZXJfNzQwODU1ZjUxNmVhNDVkOWE5OTQ1NTBmYTIxMjgzMWEuYmluZFBvcHVwKHBvcHVwXzEzYWRkNjc4Y2VlMTQ5MTY4Y2Y3YmU5OWU1ZWQxMjllKQogICAgICAgICAgICA7CgogICAgICAgICAgICAKICAgICAgICAKICAgIAogICAgICAgIHZhciBtYXJrZXJfMjA2NjRmOGQ1N2YwNGZkOWI0NzQ0NzJjNjViZTJkNzEgPSBMLm1hcmtlcigKICAgICAgICAgICAgWzQxLjkzODQ0MTksIC04Ny42NDQ2NDM2OTk5OTk5OV0sCiAgICAgICAgICAgIHsKICAgICAgICAgICAgICAgIGljb246IG5ldyBMLkljb24uRGVmYXVsdCgpCiAgICAgICAgICAgICAgICB9CiAgICAgICAgICAgICkuYWRkVG8obWFwX2E1OTM2ZWJmODA4MjQzZDBiYWFmOTlmNTJmNzQxYTE3KTsKICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgcG9wdXBfMTZlNTZlNjFmMTlkNGE5NmFlODI0Njc0NzNkMGQzZGUgPSBMLnBvcHVwKHttYXhXaWR0aDogJzMwMCcKICAgICAgICAgICAgCiAgICAgICAgICAgIH0pOwoKICAgICAgICAgICAgCiAgICAgICAgICAgICAgICB2YXIgaHRtbF85NjY5MGM2OTI4YzA0NDYzYjlhMGU4ZDU1ZDNlZjhmOSA9ICQoYDxkaXYgaWQ9Imh0bWxfOTY2OTBjNjkyOGMwNDQ2M2I5YTBlOGQ1NWQzZWY4ZjkiIHN0eWxlPSJ3aWR0aDogMTAwLjAlOyBoZWlnaHQ6IDEwMC4wJTsiPkZyb2cgbiBTbmFpbDwvZGl2PmApWzBdOwogICAgICAgICAgICAgICAgcG9wdXBfMTZlNTZlNjFmMTlkNGE5NmFlODI0Njc0NzNkMGQzZGUuc2V0Q29udGVudChodG1sXzk2NjkwYzY5MjhjMDQ0NjNiOWEwZThkNTVkM2VmOGY5KTsKICAgICAgICAgICAgCgogICAgICAgICAgICBtYXJrZXJfMjA2NjRmOGQ1N2YwNGZkOWI0NzQ0NzJjNjViZTJkNzEuYmluZFBvcHVwKHBvcHVwXzE2ZTU2ZTYxZjE5ZDRhOTZhZTgyNDY3NDczZDBkM2RlKQogICAgICAgICAgICA7CgogICAgICAgICAgICAKICAgICAgICAKICAgIAogICAgICAgIHZhciBtYXJrZXJfZjU0NDljZWRhMzYyNDY2Mzg0NGExNzFiYWFmMWEyOTEgPSBMLm1hcmtlcigKICAgICAgICAgICAgWzQxLjk0NTEwNDQsIC04Ny42NjM2ODEyXSwKICAgICAgICAgICAgewogICAgICAgICAgICAgICAgaWNvbjogbmV3IEwuSWNvbi5EZWZhdWx0KCkKICAgICAgICAgICAgICAgIH0KICAgICAgICAgICAgKS5hZGRUbyhtYXBfYTU5MzZlYmY4MDgyNDNkMGJhYWY5OWY1MmY3NDFhMTcpOwogICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBwb3B1cF82NmJhMTZhYjBkYTE0ZmRhODZkYTJhMmVkYzkyYjNhYiA9IEwucG9wdXAoe21heFdpZHRoOiAnMzAwJwogICAgICAgICAgICAKICAgICAgICAgICAgfSk7CgogICAgICAgICAgICAKICAgICAgICAgICAgICAgIHZhciBodG1sX2U4NTE1OWIyOGU5NDQyODA5Y2Y1YTM0YjdjNGFiMzA2ID0gJChgPGRpdiBpZD0iaHRtbF9lODUxNTliMjhlOTQ0MjgwOWNmNWEzNGI3YzRhYjMwNiIgc3R5bGU9IndpZHRoOiAxMDAuMCU7IGhlaWdodDogMTAwLjAlOyI+Q3Jvc2J54oCZcyBLaXRjaGVuPC9kaXY+YClbMF07CiAgICAgICAgICAgICAgICBwb3B1cF82NmJhMTZhYjBkYTE0ZmRhODZkYTJhMmVkYzkyYjNhYi5zZXRDb250ZW50KGh0bWxfZTg1MTU5YjI4ZTk0NDI4MDljZjVhMzRiN2M0YWIzMDYpOwogICAgICAgICAgICAKCiAgICAgICAgICAgIG1hcmtlcl9mNTQ0OWNlZGEzNjI0NjYzODQ0YTE3MWJhYWYxYTI5MS5iaW5kUG9wdXAocG9wdXBfNjZiYTE2YWIwZGExNGZkYTg2ZGEyYTJlZGM5MmIzYWIpCiAgICAgICAgICAgIDsKCiAgICAgICAgICAgIAogICAgICAgIAogICAgCiAgICAgICAgdmFyIG1hcmtlcl8zMTZlODVjYWYwZjY0ZjljOGZlNDQwZjlmZmFhYjM5YyA9IEwubWFya2VyKAogICAgICAgICAgICBbNDEuOTMwMDU1OSwgLTg3LjcwNzAzMzc5OTk5OTk5XSwKICAgICAgICAgICAgewogICAgICAgICAgICAgICAgaWNvbjogbmV3IEwuSWNvbi5EZWZhdWx0KCkKICAgICAgICAgICAgICAgIH0KICAgICAgICAgICAgKS5hZGRUbyhtYXBfYTU5MzZlYmY4MDgyNDNkMGJhYWY5OWY1MmY3NDFhMTcpOwogICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBwb3B1cF9lYjEwN2YxNTc1MDA0NmU3YTk1N2ZhODEyZDkzNTFjNiA9IEwucG9wdXAoe21heFdpZHRoOiAnMzAwJwogICAgICAgICAgICAKICAgICAgICAgICAgfSk7CgogICAgICAgICAgICAKICAgICAgICAgICAgICAgIHZhciBodG1sX2M4YjQzNDZkNjMyNjQ4NmNiN2E1MTBkOGFhOTI1ZGZhID0gJChgPGRpdiBpZD0iaHRtbF9jOGI0MzQ2ZDYzMjY0ODZjYjdhNTEwZDhhYTkyNWRmYSIgc3R5bGU9IndpZHRoOiAxMDAuMCU7IGhlaWdodDogMTAwLjAlOyI+TG9uZ21hbiAmIEVhZ2xlPC9kaXY+YClbMF07CiAgICAgICAgICAgICAgICBwb3B1cF9lYjEwN2YxNTc1MDA0NmU3YTk1N2ZhODEyZDkzNTFjNi5zZXRDb250ZW50KGh0bWxfYzhiNDM0NmQ2MzI2NDg2Y2I3YTUxMGQ4YWE5MjVkZmEpOwogICAgICAgICAgICAKCiAgICAgICAgICAgIG1hcmtlcl8zMTZlODVjYWYwZjY0ZjljOGZlNDQwZjlmZmFhYjM5Yy5iaW5kUG9wdXAocG9wdXBfZWIxMDdmMTU3NTAwNDZlN2E5NTdmYTgxMmQ5MzUxYzYpCiAgICAgICAgICAgIDsKCiAgICAgICAgICAgIAogICAgICAgIAogICAgCiAgICAgICAgdmFyIG1hcmtlcl82YzM3MGY2ZjYzNmE0MmNjYjJmZGUyZGNlZDRhYzc4YSA9IEwubWFya2VyKAogICAgICAgICAgICBbNDEuODkxMjkwMDAwMDAwMDEsIC04Ny42NTU1NTddLAogICAgICAgICAgICB7CiAgICAgICAgICAgICAgICBpY29uOiBuZXcgTC5JY29uLkRlZmF1bHQoKQogICAgICAgICAgICAgICAgfQogICAgICAgICAgICApLmFkZFRvKG1hcF9hNTkzNmViZjgwODI0M2QwYmFhZjk5ZjUyZjc0MWExNyk7CiAgICAgICAgCiAgICAKICAgICAgICAgICAgdmFyIHBvcHVwXzY4ZmFhN2FiZGQyZjQyZmM4OWM1NmRlNzNkMmIzYjM2ID0gTC5wb3B1cCh7bWF4V2lkdGg6ICczMDAnCiAgICAgICAgICAgIAogICAgICAgICAgICB9KTsKCiAgICAgICAgICAgIAogICAgICAgICAgICAgICAgdmFyIGh0bWxfOWM2MGQ0YjlmODRlNDRmNzgyZTBjYjVlZjQ1MjlkYjAgPSAkKGA8ZGl2IGlkPSJodG1sXzljNjBkNGI5Zjg0ZTQ0Zjc4MmUwY2I1ZWY0NTI5ZGIwIiBzdHlsZT0id2lkdGg6IDEwMC4wJTsgaGVpZ2h0OiAxMDAuMCU7Ij5CYXJpPC9kaXY+YClbMF07CiAgICAgICAgICAgICAgICBwb3B1cF82OGZhYTdhYmRkMmY0MmZjODljNTZkZTczZDJiM2IzNi5zZXRDb250ZW50KGh0bWxfOWM2MGQ0YjlmODRlNDRmNzgyZTBjYjVlZjQ1MjlkYjApOwogICAgICAgICAgICAKCiAgICAgICAgICAgIG1hcmtlcl82YzM3MGY2ZjYzNmE0MmNjYjJmZGUyZGNlZDRhYzc4YS5iaW5kUG9wdXAocG9wdXBfNjhmYWE3YWJkZDJmNDJmYzg5YzU2ZGU3M2QyYjNiMzYpCiAgICAgICAgICAgIDsKCiAgICAgICAgICAgIAogICAgICAgIAogICAgCiAgICAgICAgdmFyIG1hcmtlcl84YTNlYmY0ZmY0NGI0MmI3YmZjMGU2NWQxOWUwMDhkMSA9IEwubWFya2VyKAogICAgICAgICAgICBbNDEuODY3NzgxNSwgLTg3LjY0MTkwNjldLAogICAgICAgICAgICB7CiAgICAgICAgICAgICAgICBpY29uOiBuZXcgTC5JY29uLkRlZmF1bHQoKQogICAgICAgICAgICAgICAgfQogICAgICAgICAgICApLmFkZFRvKG1hcF9hNTkzNmViZjgwODI0M2QwYmFhZjk5ZjUyZjc0MWExNyk7CiAgICAgICAgCiAgICAKICAgICAgICAgICAgdmFyIHBvcHVwX2RiYTcxMjc5YTMwZjRjOGRhYTNmYzIwMzFiN2Y4Yjc3ID0gTC5wb3B1cCh7bWF4V2lkdGg6ICczMDAnCiAgICAgICAgICAgIAogICAgICAgICAgICB9KTsKCiAgICAgICAgICAgIAogICAgICAgICAgICAgICAgdmFyIGh0bWxfYWE0NGQ2ODdjYmY2NDA2ZjllMWM2MzlmNDFjNDBjZWEgPSAkKGA8ZGl2IGlkPSJodG1sX2FhNDRkNjg3Y2JmNjQwNmY5ZTFjNjM5ZjQxYzQwY2VhIiBzdHlsZT0id2lkdGg6IDEwMC4wJTsgaGVpZ2h0OiAxMDAuMCU7Ij5NYW5ueeKAmXM8L2Rpdj5gKVswXTsKICAgICAgICAgICAgICAgIHBvcHVwX2RiYTcxMjc5YTMwZjRjOGRhYTNmYzIwMzFiN2Y4Yjc3LnNldENvbnRlbnQoaHRtbF9hYTQ0ZDY4N2NiZjY0MDZmOWUxYzYzOWY0MWM0MGNlYSk7CiAgICAgICAgICAgIAoKICAgICAgICAgICAgbWFya2VyXzhhM2ViZjRmZjQ0YjQyYjdiZmMwZTY1ZDE5ZTAwOGQxLmJpbmRQb3B1cChwb3B1cF9kYmE3MTI3OWEzMGY0YzhkYWEzZmMyMDMxYjdmOGI3NykKICAgICAgICAgICAgOwoKICAgICAgICAgICAgCiAgICAgICAgCiAgICAKICAgICAgICB2YXIgbWFya2VyXzFmNTY4MWEzOTU2NTQxZGZhYjZjYzk3M2E5ZjJhNjQxID0gTC5tYXJrZXIoCiAgICAgICAgICAgIFs0MS44ODUyNjkxLCAtODcuNjE4NDgzN10sCiAgICAgICAgICAgIHsKICAgICAgICAgICAgICAgIGljb246IG5ldyBMLkljb24uRGVmYXVsdCgpCiAgICAgICAgICAgICAgICB9CiAgICAgICAgICAgICkuYWRkVG8obWFwX2E1OTM2ZWJmODA4MjQzZDBiYWFmOTlmNTJmNzQxYTE3KTsKICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgcG9wdXBfN2Q5N2YyYTU2YWRhNGNlMmI1OWZlYmFiMDMwNzllNzAgPSBMLnBvcHVwKHttYXhXaWR0aDogJzMwMCcKICAgICAgICAgICAgCiAgICAgICAgICAgIH0pOwoKICAgICAgICAgICAgCiAgICAgICAgICAgICAgICB2YXIgaHRtbF82YjdhMjJlOWI2ZDg0ZWE1YjU1MTRlMGY5YjFmN2FmZiA9ICQoYDxkaXYgaWQ9Imh0bWxfNmI3YTIyZTliNmQ4NGVhNWI1NTE0ZTBmOWIxZjdhZmYiIHN0eWxlPSJ3aWR0aDogMTAwLjAlOyBoZWlnaHQ6IDEwMC4wJTsiPkVnZ3nigJlzPC9kaXY+YClbMF07CiAgICAgICAgICAgICAgICBwb3B1cF83ZDk3ZjJhNTZhZGE0Y2UyYjU5ZmViYWIwMzA3OWU3MC5zZXRDb250ZW50KGh0bWxfNmI3YTIyZTliNmQ4NGVhNWI1NTE0ZTBmOWIxZjdhZmYpOwogICAgICAgICAgICAKCiAgICAgICAgICAgIG1hcmtlcl8xZjU2ODFhMzk1NjU0MWRmYWI2Y2M5NzNhOWYyYTY0MS5iaW5kUG9wdXAocG9wdXBfN2Q5N2YyYTU2YWRhNGNlMmI1OWZlYmFiMDMwNzllNzApCiAgICAgICAgICAgIDsKCiAgICAgICAgICAgIAogICAgICAgIAogICAgCiAgICAgICAgdmFyIG1hcmtlcl9lM2E2NjBkMGQzNWU0MzA4YjRjYjJjNmRmNzhlMjQ1ZCA9IEwubWFya2VyKAogICAgICAgICAgICBbNDEuOTA4MDUzOSwgLTg3LjYzNDMxMTNdLAogICAgICAgICAgICB7CiAgICAgICAgICAgICAgICBpY29uOiBuZXcgTC5JY29uLkRlZmF1bHQoKQogICAgICAgICAgICAgICAgfQogICAgICAgICAgICApLmFkZFRvKG1hcF9hNTkzNmViZjgwODI0M2QwYmFhZjk5ZjUyZjc0MWExNyk7CiAgICAgICAgCiAgICAKICAgICAgICAgICAgdmFyIHBvcHVwXzRiYThiNDQ2NjgzNDRmOGZiYzY3MzQ4MTlhZjJjNTYxID0gTC5wb3B1cCh7bWF4V2lkdGg6ICczMDAnCiAgICAgICAgICAgIAogICAgICAgICAgICB9KTsKCiAgICAgICAgICAgIAogICAgICAgICAgICAgICAgdmFyIGh0bWxfMWRmMDQyOTM3OGI2NDUzMmI4ZWM2YjU0NTgyZGFlNDYgPSAkKGA8ZGl2IGlkPSJodG1sXzFkZjA0MjkzNzhiNjQ1MzJiOGVjNmI1NDU4MmRhZTQ2IiBzdHlsZT0id2lkdGg6IDEwMC4wJTsgaGVpZ2h0OiAxMDAuMCU7Ij5PbGQgSmVydXNhbGVtPC9kaXY+YClbMF07CiAgICAgICAgICAgICAgICBwb3B1cF80YmE4YjQ0NjY4MzQ0ZjhmYmM2NzM0ODE5YWYyYzU2MS5zZXRDb250ZW50KGh0bWxfMWRmMDQyOTM3OGI2NDUzMmI4ZWM2YjU0NTgyZGFlNDYpOwogICAgICAgICAgICAKCiAgICAgICAgICAgIG1hcmtlcl9lM2E2NjBkMGQzNWU0MzA4YjRjYjJjNmRmNzhlMjQ1ZC5iaW5kUG9wdXAocG9wdXBfNGJhOGI0NDY2ODM0NGY4ZmJjNjczNDgxOWFmMmM1NjEpCiAgICAgICAgICAgIDsKCiAgICAgICAgICAgIAogICAgICAgIAogICAgCiAgICAgICAgdmFyIG1hcmtlcl9lYzRlNjMxZjVjMjE0NTYyOTYxMTAyMTE2MzRiMzI5ZCA9IEwubWFya2VyKAogICAgICAgICAgICBbNDEuOTEzNjk1Mzk5OTk5OTksIC04Ny42NzcxMjczXSwKICAgICAgICAgICAgewogICAgICAgICAgICAgICAgaWNvbjogbmV3IEwuSWNvbi5EZWZhdWx0KCkKICAgICAgICAgICAgICAgIH0KICAgICAgICAgICAgKS5hZGRUbyhtYXBfYTU5MzZlYmY4MDgyNDNkMGJhYWY5OWY1MmY3NDFhMTcpOwogICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBwb3B1cF9kZjM1MjUyMzc3OWQ0NzBkOTBmODRkZTczMDEyY2U1MCA9IEwucG9wdXAoe21heFdpZHRoOiAnMzAwJwogICAgICAgICAgICAKICAgICAgICAgICAgfSk7CgogICAgICAgICAgICAKICAgICAgICAgICAgICAgIHZhciBodG1sX2M2ZTA5NzczZTliOTQ3MGE4MTg2M2EzNDk2NDIxY2JiID0gJChgPGRpdiBpZD0iaHRtbF9jNmUwOTc3M2U5Yjk0NzBhODE4NjNhMzQ5NjQyMWNiYiIgc3R5bGU9IndpZHRoOiAxMDAuMCU7IGhlaWdodDogMTAwLjAlOyI+TWluZHnigJlzIEhvdENob2NvbGF0ZTwvZGl2PmApWzBdOwogICAgICAgICAgICAgICAgcG9wdXBfZGYzNTI1MjM3NzlkNDcwZDkwZjg0ZGU3MzAxMmNlNTAuc2V0Q29udGVudChodG1sX2M2ZTA5NzczZTliOTQ3MGE4MTg2M2EzNDk2NDIxY2JiKTsKICAgICAgICAgICAgCgogICAgICAgICAgICBtYXJrZXJfZWM0ZTYzMWY1YzIxNDU2Mjk2MTEwMjExNjM0YjMyOWQuYmluZFBvcHVwKHBvcHVwX2RmMzUyNTIzNzc5ZDQ3MGQ5MGY4NGRlNzMwMTJjZTUwKQogICAgICAgICAgICA7CgogICAgICAgICAgICAKICAgICAgICAKICAgIAogICAgICAgIHZhciBtYXJrZXJfOTEwYTQ1Yjc1NmZjNDFmZGE2ZDk3YTc4MjdlZjg3N2QgPSBMLm1hcmtlcigKICAgICAgICAgICAgWzQxLjk1MzcwNTQsIC04Ny43MDg0NTAyXSwKICAgICAgICAgICAgewogICAgICAgICAgICAgICAgaWNvbjogbmV3IEwuSWNvbi5EZWZhdWx0KCkKICAgICAgICAgICAgICAgIH0KICAgICAgICAgICAgKS5hZGRUbyhtYXBfYTU5MzZlYmY4MDgyNDNkMGJhYWY5OWY1MmY3NDFhMTcpOwogICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBwb3B1cF81YzlkYzM0N2M1ZWM0NGIxOTg3MTIzNGE1ZGFlYjgzYiA9IEwucG9wdXAoe21heFdpZHRoOiAnMzAwJwogICAgICAgICAgICAKICAgICAgICAgICAgfSk7CgogICAgICAgICAgICAKICAgICAgICAgICAgICAgIHZhciBodG1sXzNmNGI5ZWYzYWZmNDQ0NWM4NmQyYTk4MTdmZTU3ZDk3ID0gJChgPGRpdiBpZD0iaHRtbF8zZjRiOWVmM2FmZjQ0NDVjODZkMmE5ODE3ZmU1N2Q5NyIgc3R5bGU9IndpZHRoOiAxMDAuMCU7IGhlaWdodDogMTAwLjAlOyI+T2xnYeKAmXMgRGVsaWNhdGVzc2VuPC9kaXY+YClbMF07CiAgICAgICAgICAgICAgICBwb3B1cF81YzlkYzM0N2M1ZWM0NGIxOTg3MTIzNGE1ZGFlYjgzYi5zZXRDb250ZW50KGh0bWxfM2Y0YjllZjNhZmY0NDQ1Yzg2ZDJhOTgxN2ZlNTdkOTcpOwogICAgICAgICAgICAKCiAgICAgICAgICAgIG1hcmtlcl85MTBhNDViNzU2ZmM0MWZkYTZkOTdhNzgyN2VmODc3ZC5iaW5kUG9wdXAocG9wdXBfNWM5ZGMzNDdjNWVjNDRiMTk4NzEyMzRhNWRhZWI4M2IpCiAgICAgICAgICAgIDsKCiAgICAgICAgICAgIAogICAgICAgIAogICAgCiAgICAgICAgdmFyIG1hcmtlcl81MDJkMjljMmE5NDc0MjhmYmQyNjUzZTFkNTU3MzJmNyA9IEwubWFya2VyKAogICAgICAgICAgICBbNDEuOTc5NDQ5NiwgLTg3LjY2Nzk1NzE5OTk5OTk5XSwKICAgICAgICAgICAgewogICAgICAgICAgICAgICAgaWNvbjogbmV3IEwuSWNvbi5EZWZhdWx0KCkKICAgICAgICAgICAgICAgIH0KICAgICAgICAgICAgKS5hZGRUbyhtYXBfYTU5MzZlYmY4MDgyNDNkMGJhYWY5OWY1MmY3NDFhMTcpOwogICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBwb3B1cF9lMGI4YzhiZTRiNTg0NGE3YmUwYTFlNzUzNTJhMTFkNiA9IEwucG9wdXAoe21heFdpZHRoOiAnMzAwJwogICAgICAgICAgICAKICAgICAgICAgICAgfSk7CgogICAgICAgICAgICAKICAgICAgICAgICAgICAgIHZhciBodG1sXzM4NTEwMjA4OGU0NzQ2MTVhMWI0N2ZhNGM3ZTkwZTQ3ID0gJChgPGRpdiBpZD0iaHRtbF8zODUxMDIwODhlNDc0NjE1YTFiNDdmYTRjN2U5MGU0NyIgc3R5bGU9IndpZHRoOiAxMDAuMCU7IGhlaWdodDogMTAwLjAlOyI+QmlnIEpvbmVzPC9kaXY+YClbMF07CiAgICAgICAgICAgICAgICBwb3B1cF9lMGI4YzhiZTRiNTg0NGE3YmUwYTFlNzUzNTJhMTFkNi5zZXRDb250ZW50KGh0bWxfMzg1MTAyMDg4ZTQ3NDYxNWExYjQ3ZmE0YzdlOTBlNDcpOwogICAgICAgICAgICAKCiAgICAgICAgICAgIG1hcmtlcl81MDJkMjljMmE5NDc0MjhmYmQyNjUzZTFkNTU3MzJmNy5iaW5kUG9wdXAocG9wdXBfZTBiOGM4YmU0YjU4NDRhN2JlMGExZTc1MzUyYTExZDYpCiAgICAgICAgICAgIDsKCiAgICAgICAgICAgIAogICAgICAgIAogICAgCiAgICAgICAgdmFyIG1hcmtlcl82OTcyODFiYThmMjQ0YTYxOGZjMGNjNzM0YzczNzJlMyA9IEwubWFya2VyKAogICAgICAgICAgICBbNDEuOTU0MTYyLCAtODcuNzAyNzEwNzk5OTk5OTldLAogICAgICAgICAgICB7CiAgICAgICAgICAgICAgICBpY29uOiBuZXcgTC5JY29uLkRlZmF1bHQoKQogICAgICAgICAgICAgICAgfQogICAgICAgICAgICApLmFkZFRvKG1hcF9hNTkzNmViZjgwODI0M2QwYmFhZjk5ZjUyZjc0MWExNyk7CiAgICAgICAgCiAgICAKICAgICAgICAgICAgdmFyIHBvcHVwX2E2N2E4MDNhMGRiMDRjZjU5Y2M1NWZlNTRiZmQ1MmY3ID0gTC5wb3B1cCh7bWF4V2lkdGg6ICczMDAnCiAgICAgICAgICAgIAogICAgICAgICAgICB9KTsKCiAgICAgICAgICAgIAogICAgICAgICAgICAgICAgdmFyIGh0bWxfMTMyZTI3OWE4Y2RiNDhhNWE5ZTg4NzJhODI4NzFlMDQgPSAkKGA8ZGl2IGlkPSJodG1sXzEzMmUyNzlhOGNkYjQ4YTVhOWU4ODcyYTgyODcxZTA0IiBzdHlsZT0id2lkdGg6IDEwMC4wJTsgaGVpZ2h0OiAxMDAuMCU7Ij5MYSBQYW5lPC9kaXY+YClbMF07CiAgICAgICAgICAgICAgICBwb3B1cF9hNjdhODAzYTBkYjA0Y2Y1OWNjNTVmZTU0YmZkNTJmNy5zZXRDb250ZW50KGh0bWxfMTMyZTI3OWE4Y2RiNDhhNWE5ZTg4NzJhODI4NzFlMDQpOwogICAgICAgICAgICAKCiAgICAgICAgICAgIG1hcmtlcl82OTcyODFiYThmMjQ0YTYxOGZjMGNjNzM0YzczNzJlMy5iaW5kUG9wdXAocG9wdXBfYTY3YTgwM2EwZGIwNGNmNTljYzU1ZmU1NGJmZDUyZjcpCiAgICAgICAgICAgIDsKCiAgICAgICAgICAgIAogICAgICAgIAogICAgCiAgICAgICAgdmFyIG1hcmtlcl81N2IyZDdmODg1M2Q0ZmQyYjMwYTgyMGY4MmI4YmZlNSA9IEwubWFya2VyKAogICAgICAgICAgICBbNDIuMTU2NzA3MywgLTg3LjgwMzYzNDVdLAogICAgICAgICAgICB7CiAgICAgICAgICAgICAgICBpY29uOiBuZXcgTC5JY29uLkRlZmF1bHQoKQogICAgICAgICAgICAgICAgfQogICAgICAgICAgICApLmFkZFRvKG1hcF9hNTkzNmViZjgwODI0M2QwYmFhZjk5ZjUyZjc0MWExNyk7CiAgICAgICAgCiAgICAKICAgICAgICAgICAgdmFyIHBvcHVwXzY0MjQ1NzAyYTMyNzRlMDRhMTMyMDU2ZDhhYWIyYTNhID0gTC5wb3B1cCh7bWF4V2lkdGg6ICczMDAnCiAgICAgICAgICAgIAogICAgICAgICAgICB9KTsKCiAgICAgICAgICAgIAogICAgICAgICAgICAgICAgdmFyIGh0bWxfMjUwZWM3MzViZDQ0NDMwNzk0ZTgwOTkxZjU1YmMzODEgPSAkKGA8ZGl2IGlkPSJodG1sXzI1MGVjNzM1YmQ0NDQzMDc5NGU4MDk5MWY1NWJjMzgxIiBzdHlsZT0id2lkdGg6IDEwMC4wJTsgaGVpZ2h0OiAxMDAuMCU7Ij5NYXjigJlzIERlbGk8L2Rpdj5gKVswXTsKICAgICAgICAgICAgICAgIHBvcHVwXzY0MjQ1NzAyYTMyNzRlMDRhMTMyMDU2ZDhhYWIyYTNhLnNldENvbnRlbnQoaHRtbF8yNTBlYzczNWJkNDQ0MzA3OTRlODA5OTFmNTViYzM4MSk7CiAgICAgICAgICAgIAoKICAgICAgICAgICAgbWFya2VyXzU3YjJkN2Y4ODUzZDRmZDJiMzBhODIwZjgyYjhiZmU1LmJpbmRQb3B1cChwb3B1cF82NDI0NTcwMmEzMjc0ZTA0YTEzMjA1NmQ4YWFiMmEzYSkKICAgICAgICAgICAgOwoKICAgICAgICAgICAgCiAgICAgICAgCiAgICAKICAgICAgICB2YXIgbWFya2VyX2IwYzY4Y2I2MDA4ODQyYWQ5OWUwY2U5NDA4ZjM0MWRiID0gTC5tYXJrZXIoCiAgICAgICAgICAgIFs0MS45NjUyOTg3LCAtODcuNjc1NTc5NTk5OTk5OTldLAogICAgICAgICAgICB7CiAgICAgICAgICAgICAgICBpY29uOiBuZXcgTC5JY29uLkRlZmF1bHQoKQogICAgICAgICAgICAgICAgfQogICAgICAgICAgICApLmFkZFRvKG1hcF9hNTkzNmViZjgwODI0M2QwYmFhZjk5ZjUyZjc0MWExNyk7CiAgICAgICAgCiAgICAKICAgICAgICAgICAgdmFyIHBvcHVwXzQ2ZTExODNiNGVhYTQ1ZDdhYjBjZDExNjdmYTk4OTljID0gTC5wb3B1cCh7bWF4V2lkdGg6ICczMDAnCiAgICAgICAgICAgIAogICAgICAgICAgICB9KTsKCiAgICAgICAgICAgIAogICAgICAgICAgICAgICAgdmFyIGh0bWxfMWVkY2EyN2JiZjZjNDk5NmE3YjRlZTliYTU5ZTg4MDMgPSAkKGA8ZGl2IGlkPSJodG1sXzFlZGNhMjdiYmY2YzQ5OTZhN2I0ZWU5YmE1OWU4ODAzIiBzdHlsZT0id2lkdGg6IDEwMC4wJTsgaGVpZ2h0OiAxMDAuMCU7Ij5DaXR5IFByb3Zpc2lvbnM8L2Rpdj5gKVswXTsKICAgICAgICAgICAgICAgIHBvcHVwXzQ2ZTExODNiNGVhYTQ1ZDdhYjBjZDExNjdmYTk4OTljLnNldENvbnRlbnQoaHRtbF8xZWRjYTI3YmJmNmM0OTk2YTdiNGVlOWJhNTllODgwMyk7CiAgICAgICAgICAgIAoKICAgICAgICAgICAgbWFya2VyX2IwYzY4Y2I2MDA4ODQyYWQ5OWUwY2U5NDA4ZjM0MWRiLmJpbmRQb3B1cChwb3B1cF80NmUxMTgzYjRlYWE0NWQ3YWIwY2QxMTY3ZmE5ODk5YykKICAgICAgICAgICAgOwoKICAgICAgICAgICAgCiAgICAgICAgCiAgICAKICAgICAgICB2YXIgbWFya2VyXzcyYjc3ODI0YzYwZjQyNDU5ZDI5YjM5ZTBmMTY2ZDAyID0gTC5tYXJrZXIoCiAgICAgICAgICAgIFs0MS45MDI3MjE3OTk5OTk5OSwgLTg3LjY5MDIxMzVdLAogICAgICAgICAgICB7CiAgICAgICAgICAgICAgICBpY29uOiBuZXcgTC5JY29uLkRlZmF1bHQoKQogICAgICAgICAgICAgICAgfQogICAgICAgICAgICApLmFkZFRvKG1hcF9hNTkzNmViZjgwODI0M2QwYmFhZjk5ZjUyZjc0MWExNyk7CiAgICAgICAgCiAgICAKICAgICAgICAgICAgdmFyIHBvcHVwXzYxYjZhMjkwOGM1YTQ0Y2U5M2I0MGU1MzYwMTRmNTNlID0gTC5wb3B1cCh7bWF4V2lkdGg6ICczMDAnCiAgICAgICAgICAgIAogICAgICAgICAgICB9KTsKCiAgICAgICAgICAgIAogICAgICAgICAgICAgICAgdmFyIGh0bWxfZWU3N2JjODljZmVhNGI3MDljZjJkMTkwMjUxYmExMTQgPSAkKGA8ZGl2IGlkPSJodG1sX2VlNzdiYzg5Y2ZlYTRiNzA5Y2YyZDE5MDI1MWJhMTE0IiBzdHlsZT0id2lkdGg6IDEwMC4wJTsgaGVpZ2h0OiAxMDAuMCU7Ij5QYXBh4oCZcyBDYWNoZSBTYWJyb3NvPC9kaXY+YClbMF07CiAgICAgICAgICAgICAgICBwb3B1cF82MWI2YTI5MDhjNWE0NGNlOTNiNDBlNTM2MDE0ZjUzZS5zZXRDb250ZW50KGh0bWxfZWU3N2JjODljZmVhNGI3MDljZjJkMTkwMjUxYmExMTQpOwogICAgICAgICAgICAKCiAgICAgICAgICAgIG1hcmtlcl83MmI3NzgyNGM2MGY0MjQ1OWQyOWIzOWUwZjE2NmQwMi5iaW5kUG9wdXAocG9wdXBfNjFiNmEyOTA4YzVhNDRjZTkzYjQwZTUzNjAxNGY1M2UpCiAgICAgICAgICAgIDsKCiAgICAgICAgICAgIAogICAgICAgIAogICAgCiAgICAgICAgdmFyIG1hcmtlcl85Yzk3NmE0YWIxYTY0MzQ3YjM3ZWVkM2VhNWY1ZjNhNyA9IEwubWFya2VyKAogICAgICAgICAgICBbNDEuODg5MzY4MywgLTg3LjYzNDk0ODddLAogICAgICAgICAgICB7CiAgICAgICAgICAgICAgICBpY29uOiBuZXcgTC5JY29uLkRlZmF1bHQoKQogICAgICAgICAgICAgICAgfQogICAgICAgICAgICApLmFkZFRvKG1hcF9hNTkzNmViZjgwODI0M2QwYmFhZjk5ZjUyZjc0MWExNyk7CiAgICAgICAgCiAgICAKICAgICAgICAgICAgdmFyIHBvcHVwX2M4NjMwNTQwYzNlMzQ2NjZiMGZhYTZlZjZiNzg1NDg3ID0gTC5wb3B1cCh7bWF4V2lkdGg6ICczMDAnCiAgICAgICAgICAgIAogICAgICAgICAgICB9KTsKCiAgICAgICAgICAgIAogICAgICAgICAgICAgICAgdmFyIGh0bWxfNWIyODEyMGFlNTJiNDZiNWFkYzcyOTU2YTE0ZWFiZjEgPSAkKGA8ZGl2IGlkPSJodG1sXzViMjgxMjBhZTUyYjQ2YjVhZGM3Mjk1NmExNGVhYmYxIiBzdHlsZT0id2lkdGg6IDEwMC4wJTsgaGVpZ2h0OiAxMDAuMCU7Ij5CYXZldHRl4oCZcyBCYXIgJiBCb2V1ZjwvZGl2PmApWzBdOwogICAgICAgICAgICAgICAgcG9wdXBfYzg2MzA1NDBjM2UzNDY2NmIwZmFhNmVmNmI3ODU0ODcuc2V0Q29udGVudChodG1sXzViMjgxMjBhZTUyYjQ2YjVhZGM3Mjk1NmExNGVhYmYxKTsKICAgICAgICAgICAgCgogICAgICAgICAgICBtYXJrZXJfOWM5NzZhNGFiMWE2NDM0N2IzN2VlZDNlYTVmNWYzYTcuYmluZFBvcHVwKHBvcHVwX2M4NjMwNTQwYzNlMzQ2NjZiMGZhYTZlZjZiNzg1NDg3KQogICAgICAgICAgICA7CgogICAgICAgICAgICAKICAgICAgICAKICAgIAogICAgICAgIHZhciBtYXJrZXJfYjgyM2E4MmIwYmZkNDNiOWI1NmFiN2RiODgxZDk1OWIgPSBMLm1hcmtlcigKICAgICAgICAgICAgWzQxLjkxMDUyNTgsIC04Ny42MzQzNzc1XSwKICAgICAgICAgICAgewogICAgICAgICAgICAgICAgaWNvbjogbmV3IEwuSWNvbi5EZWZhdWx0KCkKICAgICAgICAgICAgICAgIH0KICAgICAgICAgICAgKS5hZGRUbyhtYXBfYTU5MzZlYmY4MDgyNDNkMGJhYWY5OWY1MmY3NDFhMTcpOwogICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBwb3B1cF8zNDQxYzE1YTEyNzg0NTc1OTJmOThiNDZjMzI2ZjcwMSA9IEwucG9wdXAoe21heFdpZHRoOiAnMzAwJwogICAgICAgICAgICAKICAgICAgICAgICAgfSk7CgogICAgICAgICAgICAKICAgICAgICAgICAgICAgIHZhciBodG1sX2E5OGY3N2FlZDFhNTRiN2NhMzAwZDBmMDUyNjNlYWRmID0gJChgPGRpdiBpZD0iaHRtbF9hOThmNzdhZWQxYTU0YjdjYTMwMGQwZjA1MjYzZWFkZiIgc3R5bGU9IndpZHRoOiAxMDAuMCU7IGhlaWdodDogMTAwLjAlOyI+TGEgRm91cm5ldHRlPC9kaXY+YClbMF07CiAgICAgICAgICAgICAgICBwb3B1cF8zNDQxYzE1YTEyNzg0NTc1OTJmOThiNDZjMzI2ZjcwMS5zZXRDb250ZW50KGh0bWxfYTk4Zjc3YWVkMWE1NGI3Y2EzMDBkMGYwNTI2M2VhZGYpOwogICAgICAgICAgICAKCiAgICAgICAgICAgIG1hcmtlcl9iODIzYTgyYjBiZmQ0M2I5YjU2YWI3ZGI4ODFkOTU5Yi5iaW5kUG9wdXAocG9wdXBfMzQ0MWMxNWExMjc4NDU3NTkyZjk4YjQ2YzMyNmY3MDEpCiAgICAgICAgICAgIDsKCiAgICAgICAgICAgIAogICAgICAgIAogICAgCiAgICAgICAgdmFyIG1hcmtlcl82MTBkNGEyZGFhYzg0ZDJkODQzYmMzMGEyYzA2ZGFhYSA9IEwubWFya2VyKAogICAgICAgICAgICBbNDEuODg5NjE4OCwgLTg3LjY0NDg0MjU5OTk5OTk5XSwKICAgICAgICAgICAgewogICAgICAgICAgICAgICAgaWNvbjogbmV3IEwuSWNvbi5EZWZhdWx0KCkKICAgICAgICAgICAgICAgIH0KICAgICAgICAgICAgKS5hZGRUbyhtYXBfYTU5MzZlYmY4MDgyNDNkMGJhYWY5OWY1MmY3NDFhMTcpOwogICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBwb3B1cF9iNmQyZjY0MjM2OGM0ZTkxYjdkNzQ3ZTRiZDdmMDcyNyA9IEwucG9wdXAoe21heFdpZHRoOiAnMzAwJwogICAgICAgICAgICAKICAgICAgICAgICAgfSk7CgogICAgICAgICAgICAKICAgICAgICAgICAgICAgIHZhciBodG1sXzUwMDUwZWU1NWE0MjQ1ZDE4MWZlZmNkY2MwYjFiNjg5ID0gJChgPGRpdiBpZD0iaHRtbF81MDA1MGVlNTVhNDI0NWQxODFmZWZjZGNjMGIxYjY4OSIgc3R5bGU9IndpZHRoOiAxMDAuMCU7IGhlaWdodDogMTAwLjAlOyI+UGFyYW1vdW50IFJvb208L2Rpdj5gKVswXTsKICAgICAgICAgICAgICAgIHBvcHVwX2I2ZDJmNjQyMzY4YzRlOTFiN2Q3NDdlNGJkN2YwNzI3LnNldENvbnRlbnQoaHRtbF81MDA1MGVlNTVhNDI0NWQxODFmZWZjZGNjMGIxYjY4OSk7CiAgICAgICAgICAgIAoKICAgICAgICAgICAgbWFya2VyXzYxMGQ0YTJkYWFjODRkMmQ4NDNiYzMwYTJjMDZkYWFhLmJpbmRQb3B1cChwb3B1cF9iNmQyZjY0MjM2OGM0ZTkxYjdkNzQ3ZTRiZDdmMDcyNykKICAgICAgICAgICAgOwoKICAgICAgICAgICAgCiAgICAgICAgCiAgICAKICAgICAgICB2YXIgbWFya2VyXzAwZjYzMTRjZDg4MTQ2OGI5YWNjY2RlMzkxMjAxY2NiID0gTC5tYXJrZXIoCiAgICAgICAgICAgIFs0MS45MTUwNDk5MDAwMDAwMSwgLTg3LjY3NzgwNDddLAogICAgICAgICAgICB7CiAgICAgICAgICAgICAgICBpY29uOiBuZXcgTC5JY29uLkRlZmF1bHQoKQogICAgICAgICAgICAgICAgfQogICAgICAgICAgICApLmFkZFRvKG1hcF9hNTkzNmViZjgwODI0M2QwYmFhZjk5ZjUyZjc0MWExNyk7CiAgICAgICAgCiAgICAKICAgICAgICAgICAgdmFyIHBvcHVwXzZiZTFiN2ViZjE2NzQxODM5MjQ2YzgxZmVmYjMyNTA3ID0gTC5wb3B1cCh7bWF4V2lkdGg6ICczMDAnCiAgICAgICAgICAgIAogICAgICAgICAgICB9KTsKCiAgICAgICAgICAgIAogICAgICAgICAgICAgICAgdmFyIGh0bWxfMGI2MDdlYTZkNDY4NDE3YWExNzRiNjdhNmRlZWE3NDIgPSAkKGA8ZGl2IGlkPSJodG1sXzBiNjA3ZWE2ZDQ2ODQxN2FhMTc0YjY3YTZkZWVhNzQyIiBzdHlsZT0id2lkdGg6IDEwMC4wJTsgaGVpZ2h0OiAxMDAuMCU7Ij5NZWx0IFNhbmR3aWNoIFNob3BwZTwvZGl2PmApWzBdOwogICAgICAgICAgICAgICAgcG9wdXBfNmJlMWI3ZWJmMTY3NDE4MzkyNDZjODFmZWZiMzI1MDcuc2V0Q29udGVudChodG1sXzBiNjA3ZWE2ZDQ2ODQxN2FhMTc0YjY3YTZkZWVhNzQyKTsKICAgICAgICAgICAgCgogICAgICAgICAgICBtYXJrZXJfMDBmNjMxNGNkODgxNDY4YjlhY2NjZGUzOTEyMDFjY2IuYmluZFBvcHVwKHBvcHVwXzZiZTFiN2ViZjE2NzQxODM5MjQ2YzgxZmVmYjMyNTA3KQogICAgICAgICAgICA7CgogICAgICAgICAgICAKICAgICAgICAKICAgIAogICAgICAgIHZhciBtYXJrZXJfMGU1YWUxOWFmYzQ4NGE4ZGEwNGIxZDEyODg3Mzc1ODAgPSBMLm1hcmtlcigKICAgICAgICAgICAgWzQxLjkyMTg1MjEsIC04Ny42NTkyMTI0XSwKICAgICAgICAgICAgewogICAgICAgICAgICAgICAgaWNvbjogbmV3IEwuSWNvbi5EZWZhdWx0KCkKICAgICAgICAgICAgICAgIH0KICAgICAgICAgICAgKS5hZGRUbyhtYXBfYTU5MzZlYmY4MDgyNDNkMGJhYWY5OWY1MmY3NDFhMTcpOwogICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBwb3B1cF9iNTM3OGUyMzEwNTc0MGUxODcwMDBhNWQ1NzJiOTRmZSA9IEwucG9wdXAoe21heFdpZHRoOiAnMzAwJwogICAgICAgICAgICAKICAgICAgICAgICAgfSk7CgogICAgICAgICAgICAKICAgICAgICAgICAgICAgIHZhciBodG1sXzkzZGU1ZGYxMzZjYTQ0OWU4YzRkMzczNTMxYTRmMWI0ID0gJChgPGRpdiBpZD0iaHRtbF85M2RlNWRmMTM2Y2E0NDllOGM0ZDM3MzUzMWE0ZjFiNCIgc3R5bGU9IndpZHRoOiAxMDAuMCU7IGhlaWdodDogMTAwLjAlOyI+RmxvcmlvbGUgQ2FmZSAmIEJha2VyeTwvZGl2PmApWzBdOwogICAgICAgICAgICAgICAgcG9wdXBfYjUzNzhlMjMxMDU3NDBlMTg3MDAwYTVkNTcyYjk0ZmUuc2V0Q29udGVudChodG1sXzkzZGU1ZGYxMzZjYTQ0OWU4YzRkMzczNTMxYTRmMWI0KTsKICAgICAgICAgICAgCgogICAgICAgICAgICBtYXJrZXJfMGU1YWUxOWFmYzQ4NGE4ZGEwNGIxZDEyODg3Mzc1ODAuYmluZFBvcHVwKHBvcHVwX2I1Mzc4ZTIzMTA1NzQwZTE4NzAwMGE1ZDU3MmI5NGZlKQogICAgICAgICAgICA7CgogICAgICAgICAgICAKICAgICAgICAKICAgIAogICAgICAgIHZhciBtYXJrZXJfNGYxYmRkNjAwOWI3NGZkZGFkM2E5NTg0MTA2YWU4YjAgPSBMLm1hcmtlcigKICAgICAgICAgICAgWzQxLjk3OTcwOTksIC04Ny42NjkzNDQwOTk5OTk5OV0sCiAgICAgICAgICAgIHsKICAgICAgICAgICAgICAgIGljb246IG5ldyBMLkljb24uRGVmYXVsdCgpCiAgICAgICAgICAgICAgICB9CiAgICAgICAgICAgICkuYWRkVG8obWFwX2E1OTM2ZWJmODA4MjQzZDBiYWFmOTlmNTJmNzQxYTE3KTsKICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgcG9wdXBfODNkZmU2ODQ5YWQ4NDlhNzliZTBkNmNjMjZkOGU1NTMgPSBMLnBvcHVwKHttYXhXaWR0aDogJzMwMCcKICAgICAgICAgICAgCiAgICAgICAgICAgIH0pOwoKICAgICAgICAgICAgCiAgICAgICAgICAgICAgICB2YXIgaHRtbF9iNTcwYjhiNDUxOTE0ZTAxODFmNDdmMjZiOWMyNWJmZSA9ICQoYDxkaXYgaWQ9Imh0bWxfYjU3MGI4YjQ1MTkxNGUwMTgxZjQ3ZjI2YjljMjViZmUiIHN0eWxlPSJ3aWR0aDogMTAwLjAlOyBoZWlnaHQ6IDEwMC4wJTsiPkZpcnN0IFNsaWNlIFBpZSBDYWbDqTwvZGl2PmApWzBdOwogICAgICAgICAgICAgICAgcG9wdXBfODNkZmU2ODQ5YWQ4NDlhNzliZTBkNmNjMjZkOGU1NTMuc2V0Q29udGVudChodG1sX2I1NzBiOGI0NTE5MTRlMDE4MWY0N2YyNmI5YzI1YmZlKTsKICAgICAgICAgICAgCgogICAgICAgICAgICBtYXJrZXJfNGYxYmRkNjAwOWI3NGZkZGFkM2E5NTg0MTA2YWU4YjAuYmluZFBvcHVwKHBvcHVwXzgzZGZlNjg0OWFkODQ5YTc5YmUwZDZjYzI2ZDhlNTUzKQogICAgICAgICAgICA7CgogICAgICAgICAgICAKICAgICAgICAKICAgIAogICAgICAgIHZhciBtYXJrZXJfMmI5MGJiZWE2NjY0NGM0YWE3NTU0NWVlYTlkODhkODIgPSBMLm1hcmtlcigKICAgICAgICAgICAgWzQxLjk2MTcxMjIsIC04Ny42NzU4MTYyMDAwMDAwMV0sCiAgICAgICAgICAgIHsKICAgICAgICAgICAgICAgIGljb246IG5ldyBMLkljb24uRGVmYXVsdCgpCiAgICAgICAgICAgICAgICB9CiAgICAgICAgICAgICkuYWRkVG8obWFwX2E1OTM2ZWJmODA4MjQzZDBiYWFmOTlmNTJmNzQxYTE3KTsKICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgcG9wdXBfZGNhZGY3ZmNlOTViNDZlN2ExYjU1OTQzY2FkNDAwMWUgPSBMLnBvcHVwKHttYXhXaWR0aDogJzMwMCcKICAgICAgICAgICAgCiAgICAgICAgICAgIH0pOwoKICAgICAgICAgICAgCiAgICAgICAgICAgICAgICB2YXIgaHRtbF9mZWY5NGI0M2E5OTg0MWVhOGM0NmEwZDc5ZWIzZTk1OCA9ICQoYDxkaXYgaWQ9Imh0bWxfZmVmOTRiNDNhOTk4NDFlYThjNDZhMGQ3OWViM2U5NTgiIHN0eWxlPSJ3aWR0aDogMTAwLjAlOyBoZWlnaHQ6IDEwMC4wJTsiPlRyb3F1ZXQ8L2Rpdj5gKVswXTsKICAgICAgICAgICAgICAgIHBvcHVwX2RjYWRmN2ZjZTk1YjQ2ZTdhMWI1NTk0M2NhZDQwMDFlLnNldENvbnRlbnQoaHRtbF9mZWY5NGI0M2E5OTg0MWVhOGM0NmEwZDc5ZWIzZTk1OCk7CiAgICAgICAgICAgIAoKICAgICAgICAgICAgbWFya2VyXzJiOTBiYmVhNjY2NDRjNGFhNzU1NDVlZWE5ZDg4ZDgyLmJpbmRQb3B1cChwb3B1cF9kY2FkZjdmY2U5NWI0NmU3YTFiNTU5NDNjYWQ0MDAxZSkKICAgICAgICAgICAgOwoKICAgICAgICAgICAgCiAgICAgICAgCiAgICAKICAgICAgICB2YXIgbWFya2VyXzA1MzdlNTBiMjcwZDRmMzdiOTI0NmE3NGI1MTIxYzg3ID0gTC5tYXJrZXIoCiAgICAgICAgICAgIFs0MS44OTI5NjExOTk5OTk5OSwgLTg3LjYyNzgyMTRdLAogICAgICAgICAgICB7CiAgICAgICAgICAgICAgICBpY29uOiBuZXcgTC5JY29uLkRlZmF1bHQoKQogICAgICAgICAgICAgICAgfQogICAgICAgICAgICApLmFkZFRvKG1hcF9hNTkzNmViZjgwODI0M2QwYmFhZjk5ZjUyZjc0MWExNyk7CiAgICAgICAgCiAgICAKICAgICAgICAgICAgdmFyIHBvcHVwXzk5YWQyYzRiNWMwMzRiOWE5Y2UzYmI2M2JlZjIzYTc2ID0gTC5wb3B1cCh7bWF4V2lkdGg6ICczMDAnCiAgICAgICAgICAgIAogICAgICAgICAgICB9KTsKCiAgICAgICAgICAgIAogICAgICAgICAgICAgICAgdmFyIGh0bWxfNmQ0NGZmZDNkMTY2NDAxZjhkOWU5ZDQ3NWFlNGJmMGEgPSAkKGA8ZGl2IGlkPSJodG1sXzZkNDRmZmQzZDE2NjQwMWY4ZDllOWQ0NzVhZTRiZjBhIiBzdHlsZT0id2lkdGg6IDEwMC4wJTsgaGVpZ2h0OiAxMDAuMCU7Ij5HcmFoYW13aWNoPC9kaXY+YClbMF07CiAgICAgICAgICAgICAgICBwb3B1cF85OWFkMmM0YjVjMDM0YjlhOWNlM2JiNjNiZWYyM2E3Ni5zZXRDb250ZW50KGh0bWxfNmQ0NGZmZDNkMTY2NDAxZjhkOWU5ZDQ3NWFlNGJmMGEpOwogICAgICAgICAgICAKCiAgICAgICAgICAgIG1hcmtlcl8wNTM3ZTUwYjI3MGQ0ZjM3YjkyNDZhNzRiNTEyMWM4Ny5iaW5kUG9wdXAocG9wdXBfOTlhZDJjNGI1YzAzNGI5YTljZTNiYjYzYmVmMjNhNzYpCiAgICAgICAgICAgIDsKCiAgICAgICAgICAgIAogICAgICAgIAogICAgCiAgICAgICAgdmFyIG1hcmtlcl8xNGIwYjg3MGJiYjI0NmE5YTI4MDY2ZjQ5NmRkMTNkMiA9IEwubWFya2VyKAogICAgICAgICAgICBbNDEuOTA0NzU1MSwgLTg3LjkzOTY0NTldLAogICAgICAgICAgICB7CiAgICAgICAgICAgICAgICBpY29uOiBuZXcgTC5JY29uLkRlZmF1bHQoKQogICAgICAgICAgICAgICAgfQogICAgICAgICAgICApLmFkZFRvKG1hcF9hNTkzNmViZjgwODI0M2QwYmFhZjk5ZjUyZjc0MWExNyk7CiAgICAgICAgCiAgICAKICAgICAgICAgICAgdmFyIHBvcHVwX2RiMDljNmE0ODUxYTQ5NzBhMjZmNWUwMzcwMTY2MzlhID0gTC5wb3B1cCh7bWF4V2lkdGg6ICczMDAnCiAgICAgICAgICAgIAogICAgICAgICAgICB9KTsKCiAgICAgICAgICAgIAogICAgICAgICAgICAgICAgdmFyIGh0bWxfYzk5ZDYwNjE2ZjQwNDQ0NDkxMjMyNzM1MzVmNDEwN2YgPSAkKGA8ZGl2IGlkPSJodG1sX2M5OWQ2MDYxNmY0MDQ0NDQ5MTIzMjczNTM1ZjQxMDdmIiBzdHlsZT0id2lkdGg6IDEwMC4wJTsgaGVpZ2h0OiAxMDAuMCU7Ij5Sb3NhbGlh4oCZcyBEZWxpPC9kaXY+YClbMF07CiAgICAgICAgICAgICAgICBwb3B1cF9kYjA5YzZhNDg1MWE0OTcwYTI2ZjVlMDM3MDE2NjM5YS5zZXRDb250ZW50KGh0bWxfYzk5ZDYwNjE2ZjQwNDQ0NDkxMjMyNzM1MzVmNDEwN2YpOwogICAgICAgICAgICAKCiAgICAgICAgICAgIG1hcmtlcl8xNGIwYjg3MGJiYjI0NmE5YTI4MDY2ZjQ5NmRkMTNkMi5iaW5kUG9wdXAocG9wdXBfZGIwOWM2YTQ4NTFhNDk3MGEyNmY1ZTAzNzAxNjYzOWEpCiAgICAgICAgICAgIDsKCiAgICAgICAgICAgIAogICAgICAgIAogICAgCiAgICAgICAgdmFyIG1hcmtlcl81ZDlkNGNkZjZjMzM0ZDRjYWU1ZDVlYTdiYjQzODZjMyA9IEwubWFya2VyKAogICAgICAgICAgICBbNDEuNzkxMzE4NSwgLTg3LjU5Mzg0NTZdLAogICAgICAgICAgICB7CiAgICAgICAgICAgICAgICBpY29uOiBuZXcgTC5JY29uLkRlZmF1bHQoKQogICAgICAgICAgICAgICAgfQogICAgICAgICAgICApLmFkZFRvKG1hcF9hNTkzNmViZjgwODI0M2QwYmFhZjk5ZjUyZjc0MWExNyk7CiAgICAgICAgCiAgICAKICAgICAgICAgICAgdmFyIHBvcHVwXzNlM2VkMzgxZjliYjRjNWFiODc2MGQ3YWJlYjY1MWMzID0gTC5wb3B1cCh7bWF4V2lkdGg6ICczMDAnCiAgICAgICAgICAgIAogICAgICAgICAgICB9KTsKCiAgICAgICAgICAgIAogICAgICAgICAgICAgICAgdmFyIGh0bWxfYWEyYjA5MTk4NTMwNGQwM2E0NWI1NjFkZmUwNjAxYjMgPSAkKGA8ZGl2IGlkPSJodG1sX2FhMmIwOTE5ODUzMDRkMDNhNDViNTYxZGZlMDYwMWIzIiBzdHlsZT0id2lkdGg6IDEwMC4wJTsgaGVpZ2h0OiAxMDAuMCU7Ij5aJkggTWFya2V0Q2FmZTwvZGl2PmApWzBdOwogICAgICAgICAgICAgICAgcG9wdXBfM2UzZWQzODFmOWJiNGM1YWI4NzYwZDdhYmViNjUxYzMuc2V0Q29udGVudChodG1sX2FhMmIwOTE5ODUzMDRkMDNhNDViNTYxZGZlMDYwMWIzKTsKICAgICAgICAgICAgCgogICAgICAgICAgICBtYXJrZXJfNWQ5ZDRjZGY2YzMzNGQ0Y2FlNWQ1ZWE3YmI0Mzg2YzMuYmluZFBvcHVwKHBvcHVwXzNlM2VkMzgxZjliYjRjNWFiODc2MGQ3YWJlYjY1MWMzKQogICAgICAgICAgICA7CgogICAgICAgICAgICAKICAgICAgICAKICAgIAogICAgICAgIHZhciBtYXJrZXJfNjJiODkxMDA5MTk1NGJkZmJlMTA2NmVkZWUwZjQ0OTggPSBMLm1hcmtlcigKICAgICAgICAgICAgWzQyLjI1MTc5NywgLTg3Ljg0MTM1MjNdLAogICAgICAgICAgICB7CiAgICAgICAgICAgICAgICBpY29uOiBuZXcgTC5JY29uLkRlZmF1bHQoKQogICAgICAgICAgICAgICAgfQogICAgICAgICAgICApLmFkZFRvKG1hcF9hNTkzNmViZjgwODI0M2QwYmFhZjk5ZjUyZjc0MWExNyk7CiAgICAgICAgCiAgICAKICAgICAgICAgICAgdmFyIHBvcHVwX2FkNzdhZjYzYTg4ZTQwYzk5MWU2ZmMzMTNmODk1NzcwID0gTC5wb3B1cCh7bWF4V2lkdGg6ICczMDAnCiAgICAgICAgICAgIAogICAgICAgICAgICB9KTsKCiAgICAgICAgICAgIAogICAgICAgICAgICAgICAgdmFyIGh0bWxfOGQxZDAwMGFiMzU1NDg1MmI0OTc4ZjE3NDNlMzA3ZjcgPSAkKGA8ZGl2IGlkPSJodG1sXzhkMWQwMDBhYjM1NTQ4NTJiNDk3OGYxNzQzZTMwN2Y3IiBzdHlsZT0id2lkdGg6IDEwMC4wJTsgaGVpZ2h0OiAxMDAuMCU7Ij5NYXJrZXQgSG91c2Ugb24gdGhlIFNxdWFyZTwvZGl2PmApWzBdOwogICAgICAgICAgICAgICAgcG9wdXBfYWQ3N2FmNjNhODhlNDBjOTkxZTZmYzMxM2Y4OTU3NzAuc2V0Q29udGVudChodG1sXzhkMWQwMDBhYjM1NTQ4NTJiNDk3OGYxNzQzZTMwN2Y3KTsKICAgICAgICAgICAgCgogICAgICAgICAgICBtYXJrZXJfNjJiODkxMDA5MTk1NGJkZmJlMTA2NmVkZWUwZjQ0OTguYmluZFBvcHVwKHBvcHVwX2FkNzdhZjYzYTg4ZTQwYzk5MWU2ZmMzMTNmODk1NzcwKQogICAgICAgICAgICA7CgogICAgICAgICAgICAKICAgICAgICAKICAgIAogICAgICAgIHZhciBtYXJrZXJfZWRhMTFhN2I5Y2YzNGI1YzlhODA0MzRkNjRlZTg0MjMgPSBMLm1hcmtlcigKICAgICAgICAgICAgWzQxLjkxNTMzNjgsIC04Ny42MzQ2MTYxXSwKICAgICAgICAgICAgewogICAgICAgICAgICAgICAgaWNvbjogbmV3IEwuSWNvbi5EZWZhdWx0KCkKICAgICAgICAgICAgICAgIH0KICAgICAgICAgICAgKS5hZGRUbyhtYXBfYTU5MzZlYmY4MDgyNDNkMGJhYWY5OWY1MmY3NDFhMTcpOwogICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBwb3B1cF9kNzQ5NGY3YjU5Njg0NTI1YmVjYjdiMWVkYjQ3M2Y4YSA9IEwucG9wdXAoe21heFdpZHRoOiAnMzAwJwogICAgICAgICAgICAKICAgICAgICAgICAgfSk7CgogICAgICAgICAgICAKICAgICAgICAgICAgICAgIHZhciBodG1sXzc2NWRkNmM4NTA1OTQwYTI5NWZjNjIxOTA5ZTNkNTIyID0gJChgPGRpdiBpZD0iaHRtbF83NjVkZDZjODUwNTk0MGEyOTVmYzYyMTkwOWUzZDUyMiIgc3R5bGU9IndpZHRoOiAxMDAuMCU7IGhlaWdodDogMTAwLjAlOyI+RWxhaW5l4oCZcyBDb2ZmZWUgQ2FsbDwvZGl2PmApWzBdOwogICAgICAgICAgICAgICAgcG9wdXBfZDc0OTRmN2I1OTY4NDUyNWJlY2I3YjFlZGI0NzNmOGEuc2V0Q29udGVudChodG1sXzc2NWRkNmM4NTA1OTQwYTI5NWZjNjIxOTA5ZTNkNTIyKTsKICAgICAgICAgICAgCgogICAgICAgICAgICBtYXJrZXJfZWRhMTFhN2I5Y2YzNGI1YzlhODA0MzRkNjRlZTg0MjMuYmluZFBvcHVwKHBvcHVwX2Q3NDk0ZjdiNTk2ODQ1MjViZWNiN2IxZWRiNDczZjhhKQogICAgICAgICAgICA7CgogICAgICAgICAgICAKICAgICAgICAKICAgIAogICAgICAgIHZhciBtYXJrZXJfNGE3YjZmMjY0MjE5NDgyYjlmZTJmMjQ0MzE0NWU0YWQgPSBMLm1hcmtlcigKICAgICAgICAgICAgWzQxLjg4NjM2MjIsIC04Ny44MDIyMjk3XSwKICAgICAgICAgICAgewogICAgICAgICAgICAgICAgaWNvbjogbmV3IEwuSWNvbi5EZWZhdWx0KCkKICAgICAgICAgICAgICAgIH0KICAgICAgICAgICAgKS5hZGRUbyhtYXBfYTU5MzZlYmY4MDgyNDNkMGJhYWY5OWY1MmY3NDFhMTcpOwogICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBwb3B1cF9lY2JmYzRhZWNhMTc0NmEwOGZlYTYzYTY3NWI4YTY1YiA9IEwucG9wdXAoe21heFdpZHRoOiAnMzAwJwogICAgICAgICAgICAKICAgICAgICAgICAgfSk7CgogICAgICAgICAgICAKICAgICAgICAgICAgICAgIHZhciBodG1sXzk5Y2Y0NDg5YTExMTQwMGU4M2I2ZGY5MWFkNWQ5NjUwID0gJChgPGRpdiBpZD0iaHRtbF85OWNmNDQ4OWExMTE0MDBlODNiNmRmOTFhZDVkOTY1MCIgc3R5bGU9IndpZHRoOiAxMDAuMCU7IGhlaWdodDogMTAwLjAlOyI+TWFyaW9uIFN0cmVldCBDaGVlc2UgTWFya2V0PC9kaXY+YClbMF07CiAgICAgICAgICAgICAgICBwb3B1cF9lY2JmYzRhZWNhMTc0NmEwOGZlYTYzYTY3NWI4YTY1Yi5zZXRDb250ZW50KGh0bWxfOTljZjQ0ODlhMTExNDAwZTgzYjZkZjkxYWQ1ZDk2NTApOwogICAgICAgICAgICAKCiAgICAgICAgICAgIG1hcmtlcl80YTdiNmYyNjQyMTk0ODJiOWZlMmYyNDQzMTQ1ZTRhZC5iaW5kUG9wdXAocG9wdXBfZWNiZmM0YWVjYTE3NDZhMDhmZWE2M2E2NzViOGE2NWIpCiAgICAgICAgICAgIDsKCiAgICAgICAgICAgIAogICAgICAgIAogICAgCiAgICAgICAgdmFyIG1hcmtlcl81ZGVlMzZlMjAxNTY0ODY5YTg5ZDJhMTYyY2ZmZTYzYiA9IEwubWFya2VyKAogICAgICAgICAgICBbNDEuODc1NjAwNywgLTg3LjYyNzI4MzQ5OTk5OTk5XSwKICAgICAgICAgICAgewogICAgICAgICAgICAgICAgaWNvbjogbmV3IEwuSWNvbi5EZWZhdWx0KCkKICAgICAgICAgICAgICAgIH0KICAgICAgICAgICAgKS5hZGRUbyhtYXBfYTU5MzZlYmY4MDgyNDNkMGJhYWY5OWY1MmY3NDFhMTcpOwogICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBwb3B1cF82YTkzZGE2N2VjNDU0NDI1ODM5M2Y4ZmU3ZTA2ODk4YiA9IEwucG9wdXAoe21heFdpZHRoOiAnMzAwJwogICAgICAgICAgICAKICAgICAgICAgICAgfSk7CgogICAgICAgICAgICAKICAgICAgICAgICAgICAgIHZhciBodG1sX2I0ZTdhNTVlM2NjYTRhNDY4MGQ3Zjc5YjAzOGQwOTdhID0gJChgPGRpdiBpZD0iaHRtbF9iNGU3YTU1ZTNjY2E0YTQ2ODBkN2Y3OWIwMzhkMDk3YSIgc3R5bGU9IndpZHRoOiAxMDAuMCU7IGhlaWdodDogMTAwLjAlOyI+Q2FmZWNpdG88L2Rpdj5gKVswXTsKICAgICAgICAgICAgICAgIHBvcHVwXzZhOTNkYTY3ZWM0NTQ0MjU4MzkzZjhmZTdlMDY4OThiLnNldENvbnRlbnQoaHRtbF9iNGU3YTU1ZTNjY2E0YTQ2ODBkN2Y3OWIwMzhkMDk3YSk7CiAgICAgICAgICAgIAoKICAgICAgICAgICAgbWFya2VyXzVkZWUzNmUyMDE1NjQ4NjlhODlkMmExNjJjZmZlNjNiLmJpbmRQb3B1cChwb3B1cF82YTkzZGE2N2VjNDU0NDI1ODM5M2Y4ZmU3ZTA2ODk4YikKICAgICAgICAgICAgOwoKICAgICAgICAgICAgCiAgICAgICAgCiAgICAKICAgICAgICB2YXIgbWFya2VyXzMwNGVmZjQzMzRiYjQ0ZjNhZTRlOTMzYTNkYTk1MjlmID0gTC5tYXJrZXIoCiAgICAgICAgICAgIFs0MS44OTYwNzM4LCAtODcuNjc3ODE4NV0sCiAgICAgICAgICAgIHsKICAgICAgICAgICAgICAgIGljb246IG5ldyBMLkljb24uRGVmYXVsdCgpCiAgICAgICAgICAgICAgICB9CiAgICAgICAgICAgICkuYWRkVG8obWFwX2E1OTM2ZWJmODA4MjQzZDBiYWFmOTlmNTJmNzQxYTE3KTsKICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgcG9wdXBfOWJmMGUyZjFlMTFjNGEzOTlmNzE5ODVmMjRlNDc2NWQgPSBMLnBvcHVwKHttYXhXaWR0aDogJzMwMCcKICAgICAgICAgICAgCiAgICAgICAgICAgIH0pOwoKICAgICAgICAgICAgCiAgICAgICAgICAgICAgICB2YXIgaHRtbF9hN2RkMWUyNWQxOGU0ZmJiYmEzZDZmOWE4YmNiNDYxOSA9ICQoYDxkaXYgaWQ9Imh0bWxfYTdkZDFlMjVkMThlNGZiYmJhM2Q2ZjlhOGJjYjQ2MTkiIHN0eWxlPSJ3aWR0aDogMTAwLjAlOyBoZWlnaHQ6IDEwMC4wJTsiPkNoaWNrcGVhPC9kaXY+YClbMF07CiAgICAgICAgICAgICAgICBwb3B1cF85YmYwZTJmMWUxMWM0YTM5OWY3MTk4NWYyNGU0NzY1ZC5zZXRDb250ZW50KGh0bWxfYTdkZDFlMjVkMThlNGZiYmJhM2Q2ZjlhOGJjYjQ2MTkpOwogICAgICAgICAgICAKCiAgICAgICAgICAgIG1hcmtlcl8zMDRlZmY0MzM0YmI0NGYzYWU0ZTkzM2EzZGE5NTI5Zi5iaW5kUG9wdXAocG9wdXBfOWJmMGUyZjFlMTFjNGEzOTlmNzE5ODVmMjRlNDc2NWQpCiAgICAgICAgICAgIDsKCiAgICAgICAgICAgIAogICAgICAgIAogICAgCiAgICAgICAgdmFyIG1hcmtlcl9iNWRiZWU0M2VlZDc0MzQ1ODgwOWE2YjQ2YTQwYjBmOCA9IEwubWFya2VyKAogICAgICAgICAgICBbNDEuODk4OTc4NTAwMDAwMDEsIC04Ny42MjczOTI2XSwKICAgICAgICAgICAgewogICAgICAgICAgICAgICAgaWNvbjogbmV3IEwuSWNvbi5EZWZhdWx0KCkKICAgICAgICAgICAgICAgIH0KICAgICAgICAgICAgKS5hZGRUbyhtYXBfYTU5MzZlYmY4MDgyNDNkMGJhYWY5OWY1MmY3NDFhMTcpOwogICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBwb3B1cF84MDE4YTIzYThhYWE0MTIxYjcxNzdlNTNmNTZjZTgwMyA9IEwucG9wdXAoe21heFdpZHRoOiAnMzAwJwogICAgICAgICAgICAKICAgICAgICAgICAgfSk7CgogICAgICAgICAgICAKICAgICAgICAgICAgICAgIHZhciBodG1sXzJlMDAwM2Y5YjhhODRkOGM4MWQ1ZThmMTVkNmUzMjU4ID0gJChgPGRpdiBpZD0iaHRtbF8yZTAwMDNmOWI4YTg0ZDhjODFkNWU4ZjE1ZDZlMzI1OCIgc3R5bGU9IndpZHRoOiAxMDAuMCU7IGhlaWdodDogMTAwLjAlOyI+VGhlIEdvZGRlc3MgYW5kIEdyb2NlcjwvZGl2PmApWzBdOwogICAgICAgICAgICAgICAgcG9wdXBfODAxOGEyM2E4YWFhNDEyMWI3MTc3ZTUzZjU2Y2U4MDMuc2V0Q29udGVudChodG1sXzJlMDAwM2Y5YjhhODRkOGM4MWQ1ZThmMTVkNmUzMjU4KTsKICAgICAgICAgICAgCgogICAgICAgICAgICBtYXJrZXJfYjVkYmVlNDNlZWQ3NDM0NTg4MDlhNmI0NmE0MGIwZjguYmluZFBvcHVwKHBvcHVwXzgwMThhMjNhOGFhYTQxMjFiNzE3N2U1M2Y1NmNlODAzKQogICAgICAgICAgICA7CgogICAgICAgICAgICAKICAgICAgICAKICAgIAogICAgICAgIHZhciBtYXJrZXJfM2Q5ODcyMjE2MTlhNGQ2MTg0OTRjNmY1ODQ1MTdjOGYgPSBMLm1hcmtlcigKICAgICAgICAgICAgWzQxLjkxMDU4MzIsIC04Ny45NDA0ODgzOTk5OTk5OV0sCiAgICAgICAgICAgIHsKICAgICAgICAgICAgICAgIGljb246IG5ldyBMLkljb24uRGVmYXVsdCgpCiAgICAgICAgICAgICAgICB9CiAgICAgICAgICAgICkuYWRkVG8obWFwX2E1OTM2ZWJmODA4MjQzZDBiYWFmOTlmNTJmNzQxYTE3KTsKICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgcG9wdXBfOTZmNTVjZmE1YjZjNGQ3YmE5YTM5MDcyYjg1N2I0NjcgPSBMLnBvcHVwKHttYXhXaWR0aDogJzMwMCcKICAgICAgICAgICAgCiAgICAgICAgICAgIH0pOwoKICAgICAgICAgICAgCiAgICAgICAgICAgICAgICB2YXIgaHRtbF80ZTk4MjU0ZmNmOTU0MjBlODA0YjNjNjE5MzRjYzFkNiA9ICQoYDxkaXYgaWQ9Imh0bWxfNGU5ODI1NGZjZjk1NDIwZTgwNGIzYzYxOTM0Y2MxZDYiIHN0eWxlPSJ3aWR0aDogMTAwLjAlOyBoZWlnaHQ6IDEwMC4wJTsiPlplbndpY2g8L2Rpdj5gKVswXTsKICAgICAgICAgICAgICAgIHBvcHVwXzk2ZjU1Y2ZhNWI2YzRkN2JhOWEzOTA3MmI4NTdiNDY3LnNldENvbnRlbnQoaHRtbF80ZTk4MjU0ZmNmOTU0MjBlODA0YjNjNjE5MzRjYzFkNik7CiAgICAgICAgICAgIAoKICAgICAgICAgICAgbWFya2VyXzNkOTg3MjIxNjE5YTRkNjE4NDk0YzZmNTg0NTE3YzhmLmJpbmRQb3B1cChwb3B1cF85NmY1NWNmYTViNmM0ZDdiYTlhMzkwNzJiODU3YjQ2NykKICAgICAgICAgICAgOwoKICAgICAgICAgICAgCiAgICAgICAgCiAgICAKICAgICAgICB2YXIgbWFya2VyXzZjMTgzZWQ0N2FmNjQ2MjE4MjA3OGNlYWU2MGE2Yzc5ID0gTC5tYXJrZXIoCiAgICAgICAgICAgIFs0MS44ODMxMDYxLCAtODcuNjI1NDM4MV0sCiAgICAgICAgICAgIHsKICAgICAgICAgICAgICAgIGljb246IG5ldyBMLkljb24uRGVmYXVsdCgpCiAgICAgICAgICAgICAgICB9CiAgICAgICAgICAgICkuYWRkVG8obWFwX2E1OTM2ZWJmODA4MjQzZDBiYWFmOTlmNTJmNzQxYTE3KTsKICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgcG9wdXBfNWExMzMzMTMwODFmNDI3ZmI0YTc0MGM1YzM5NDQ3OWEgPSBMLnBvcHVwKHttYXhXaWR0aDogJzMwMCcKICAgICAgICAgICAgCiAgICAgICAgICAgIH0pOwoKICAgICAgICAgICAgCiAgICAgICAgICAgICAgICB2YXIgaHRtbF83OTUzNjNmOWQ5Njc0MzA0OTY2MmI2M2ZlNmYxMWRhZCA9ICQoYDxkaXYgaWQ9Imh0bWxfNzk1MzYzZjlkOTY3NDMwNDk2NjJiNjNmZTZmMTFkYWQiIHN0eWxlPSJ3aWR0aDogMTAwLjAlOyBoZWlnaHQ6IDEwMC4wJTsiPlRvbmkgUGF0aXNzZXJpZTwvZGl2PmApWzBdOwogICAgICAgICAgICAgICAgcG9wdXBfNWExMzMzMTMwODFmNDI3ZmI0YTc0MGM1YzM5NDQ3OWEuc2V0Q29udGVudChodG1sXzc5NTM2M2Y5ZDk2NzQzMDQ5NjYyYjYzZmU2ZjExZGFkKTsKICAgICAgICAgICAgCgogICAgICAgICAgICBtYXJrZXJfNmMxODNlZDQ3YWY2NDYyMTgyMDc4Y2VhZTYwYTZjNzkuYmluZFBvcHVwKHBvcHVwXzVhMTMzMzEzMDgxZjQyN2ZiNGE3NDBjNWMzOTQ0NzlhKQogICAgICAgICAgICA7CgogICAgICAgICAgICAKICAgICAgICAKICAgIAogICAgICAgIHZhciBtYXJrZXJfMzQ0MTc5MjRhMzQwNGJmZDlhMmI5ZWMwYjc1YTMwY2UgPSBMLm1hcmtlcigKICAgICAgICAgICAgWzQxLjk0MzE2MzIsIC04Ny42NDQ1MDcxXSwKICAgICAgICAgICAgewogICAgICAgICAgICAgICAgaWNvbjogbmV3IEwuSWNvbi5EZWZhdWx0KCkKICAgICAgICAgICAgICAgIH0KICAgICAgICAgICAgKS5hZGRUbyhtYXBfYTU5MzZlYmY4MDgyNDNkMGJhYWY5OWY1MmY3NDFhMTcpOwogICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBwb3B1cF9lOTZiOWMwNWJmMzA0ZjZjYTU0MjMyY2Q2MDY0ZGM2NCA9IEwucG9wdXAoe21heFdpZHRoOiAnMzAwJwogICAgICAgICAgICAKICAgICAgICAgICAgfSk7CgogICAgICAgICAgICAKICAgICAgICAgICAgICAgIHZhciBodG1sX2Y0YTU5Y2JjOWYwMTQwYTlhNzBjOGRkOGJkMzFjYmE5ID0gJChgPGRpdiBpZD0iaHRtbF9mNGE1OWNiYzlmMDE0MGE5YTcwYzhkZDhiZDMxY2JhOSIgc3R5bGU9IndpZHRoOiAxMDAuMCU7IGhlaWdodDogMTAwLjAlOyI+UGhvZWJl4oCZcyBCYWtlcnk8L2Rpdj5gKVswXTsKICAgICAgICAgICAgICAgIHBvcHVwX2U5NmI5YzA1YmYzMDRmNmNhNTQyMzJjZDYwNjRkYzY0LnNldENvbnRlbnQoaHRtbF9mNGE1OWNiYzlmMDE0MGE5YTcwYzhkZDhiZDMxY2JhOSk7CiAgICAgICAgICAgIAoKICAgICAgICAgICAgbWFya2VyXzM0NDE3OTI0YTM0MDRiZmQ5YTJiOWVjMGI3NWEzMGNlLmJpbmRQb3B1cChwb3B1cF9lOTZiOWMwNWJmMzA0ZjZjYTU0MjMyY2Q2MDY0ZGM2NCkKICAgICAgICAgICAgOwoKICAgICAgICAgICAgCiAgICAgICAgCjwvc2NyaXB0Pg==" style="position:absolute;width:100%;height:100%;left:0;top:0;border:none !important;" allowfullscreen webkitallowfullscreen mozallowfullscreen></iframe></div></div>




```python

```
