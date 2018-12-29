
# 강남 3구는 안전한가?



* 강남3구의 주민들이 자신들이 거주하는 구의 체감 안전도를 높게 생각한다는 기사를 확인해 보도록 한다
* 기사 원문 http://news1.kr/articles/?1911504


* Matplotlib의 heatmap 등을 그릴때 cmap의 디폴트 설정이 변경되어 heatmap 등에서 cmap을 적용할 때 옵션을 잡아주어야 교재와 동일한 효과가 나타난다. (소스코드에 모두 반영됨)
* Folium이 0.4.0으로 버전업 되면서 choropleth 명령에서 geo_str 옵션명이 geo_data 옵션명으로 변경됨. (소스코드에 모두 반영)
* Folium이 0.4.0으로 버전업 되면서 circle marker 적용할때, fill=True 옵션을 반듯이 사용해야 함. (소스코드에 모두 반영)
* googleMaps라이브러리 설치 해애주어야 한다. pip install googlemaps
* foluim설치 해주어야 한다. pip install folium



# 데이터 정리하기

* 필요한 모듈을 import한다.


```python
import numpy as np
import pandas as pd
```

* 받은 데이터(csv) 파일을 읽는다, 콤마(,)로 천단위가 구분되어 있고, 한글 엔코딩은 **euc-kr**이다
* 교재에서 접근하는 방식으로 데이터를 얻으로 가면 교재 집필하던 때와 데이터의 형식이 변경되어 있다.
* 지금은 데이터를 얻는 것이 실제 데이터라는 것에 대한 증명일 뿐이므로, 해당 데이터를 Github에서 배포하는 데이터를 다운받는 것으로 한다.


```python
crime_anal_police = pd.read_csv('../data/02. crime_in_Seoul.csv', thousands=',', 
                                encoding='euc-kr')
crime_anal_police.head()
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
      <th>관서명</th>
      <th>살인 발생</th>
      <th>살인 검거</th>
      <th>강도 발생</th>
      <th>강도 검거</th>
      <th>강간 발생</th>
      <th>강간 검거</th>
      <th>절도 발생</th>
      <th>절도 검거</th>
      <th>폭력 발생</th>
      <th>폭력 검거</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>중부서</td>
      <td>2</td>
      <td>2</td>
      <td>3</td>
      <td>2</td>
      <td>105</td>
      <td>65</td>
      <td>1395</td>
      <td>477</td>
      <td>1355</td>
      <td>1170</td>
    </tr>
    <tr>
      <th>1</th>
      <td>종로서</td>
      <td>3</td>
      <td>3</td>
      <td>6</td>
      <td>5</td>
      <td>115</td>
      <td>98</td>
      <td>1070</td>
      <td>413</td>
      <td>1278</td>
      <td>1070</td>
    </tr>
    <tr>
      <th>2</th>
      <td>남대문서</td>
      <td>1</td>
      <td>0</td>
      <td>6</td>
      <td>4</td>
      <td>65</td>
      <td>46</td>
      <td>1153</td>
      <td>382</td>
      <td>869</td>
      <td>794</td>
    </tr>
    <tr>
      <th>3</th>
      <td>서대문서</td>
      <td>2</td>
      <td>2</td>
      <td>5</td>
      <td>4</td>
      <td>154</td>
      <td>124</td>
      <td>1812</td>
      <td>738</td>
      <td>2056</td>
      <td>1711</td>
    </tr>
    <tr>
      <th>4</th>
      <td>혜화서</td>
      <td>3</td>
      <td>2</td>
      <td>5</td>
      <td>4</td>
      <td>96</td>
      <td>63</td>
      <td>1114</td>
      <td>424</td>
      <td>1015</td>
      <td>861</td>
    </tr>
  </tbody>
</table>
</div>



* 구글 맵스를 사용해서 경찰서의 위치(위도, 경도) 정보를 받아온다


```python
import googlemaps
```


```python
gmaps_key = "AIzaSyB_id_BNZoD_1_QSXufdkIPD7muNK9Wjlc" # 자신의 key를 사용합니다.
gmaps = googlemaps.Client(key=gmaps_key)
```


```python
gmaps.geocode('서울중부경찰서', language='ko')
```




    [{'address_components': [{'long_name': '２７',
        'short_name': '２７',
        'types': ['premise']},
       {'long_name': '수표로',
        'short_name': '수표로',
        'types': ['political', 'sublocality', 'sublocality_level_4']},
       {'long_name': '을지로동',
        'short_name': '을지로동',
        'types': ['political', 'sublocality', 'sublocality_level_2']},
       {'long_name': '중구',
        'short_name': '중구',
        'types': ['political', 'sublocality', 'sublocality_level_1']},
       {'long_name': '서울특별시',
        'short_name': '서울특별시',
        'types': ['administrative_area_level_1', 'political']},
       {'long_name': '대한민국',
        'short_name': 'KR',
        'types': ['country', 'political']},
       {'long_name': '100-032',
        'short_name': '100-032',
        'types': ['postal_code']}],
      'formatted_address': '대한민국 서울특별시 중구 을지로동 수표로 27',
      'geometry': {'location': {'lat': 37.5636465, 'lng': 126.9895796},
       'location_type': 'ROOFTOP',
       'viewport': {'northeast': {'lat': 37.56499548029149,
         'lng': 126.9909285802915},
        'southwest': {'lat': 37.56229751970849, 'lng': 126.9882306197085}}},
      'place_id': 'ChIJc-9q5uSifDURLhQmr5wkXmc',
      'plus_code': {'compound_code': 'HX7Q+FR 대한민국 서울특별시',
       'global_code': '8Q98HX7Q+FR'},
      'types': ['establishment', 'point_of_interest', 'police']}]




```python
station_name = []

for name in crime_anal_police['관서명']:
    station_name.append('서울' + str(name[:-1]) + '경찰서')

station_name
```




    ['서울중부경찰서',
     '서울종로경찰서',
     '서울남대문경찰서',
     '서울서대문경찰서',
     '서울혜화경찰서',
     '서울용산경찰서',
     '서울성북경찰서',
     '서울동대문경찰서',
     '서울마포경찰서',
     '서울영등포경찰서',
     '서울성동경찰서',
     '서울동작경찰서',
     '서울광진경찰서',
     '서울서부경찰서',
     '서울강북경찰서',
     '서울금천경찰서',
     '서울중랑경찰서',
     '서울강남경찰서',
     '서울관악경찰서',
     '서울강서경찰서',
     '서울강동경찰서',
     '서울종암경찰서',
     '서울구로경찰서',
     '서울서초경찰서',
     '서울양천경찰서',
     '서울송파경찰서',
     '서울노원경찰서',
     '서울방배경찰서',
     '서울은평경찰서',
     '서울도봉경찰서',
     '서울수서경찰서']




```python
station_addreess = []
station_lat = []
station_lng = []

for name in station_name:
    tmp = gmaps.geocode(name, language='ko')
    station_addreess.append(tmp[0].get("formatted_address"))
    
    tmp_loc = tmp[0].get("geometry")

    station_lat.append(tmp_loc['location']['lat'])
    station_lng.append(tmp_loc['location']['lng'])
    
    print(name + '-->' + tmp[0].get("formatted_address"))
```

    서울중부경찰서-->대한민국 서울특별시 중구 을지로동 수표로 27
    서울종로경찰서-->대한민국 서울특별시 종로구 종로1.2.3.4가동 율곡로 46
    서울남대문경찰서-->대한민국 서울특별시 중구 회현동 한강대로 410
    서울서대문경찰서-->대한민국 서울특별시 서대문구 충현동 통일로 113
    서울혜화경찰서-->대한민국 서울특별시 종로구 인의동 창경궁로 112-16
    서울용산경찰서-->대한민국 서울특별시 용산구 원효로1가 백범로 329
    서울성북경찰서-->대한민국 서울특별시 성북구 삼선동5가 301
    서울동대문경찰서-->대한민국 서울특별시 동대문구 청량리동 약령시로21길 29
    서울마포경찰서-->대한민국 서울특별시 마포구 아현동 618-1
    서울영등포경찰서-->대한민국 서울특별시 영등포구 영등포동 영등포로46길 14
    서울성동경찰서-->대한민국 서울특별시 성동구 행당동 왕십리광장로 9
    서울동작경찰서-->대한민국 서울특별시 동작구 노량진1동 노량진로 148
    서울광진경찰서-->대한민국 서울특별시 광진구 구의1동 자양로 167
    서울서부경찰서-->대한민국 서울특별시 은평구 대조동 통일로 757
    서울강북경찰서-->대한민국 서울특별시 강북구 번1동 오패산로 406
    서울금천경찰서-->대한민국 서울특별시 금천구 시흥동 190
    서울중랑경찰서-->대한민국 서울특별시 중랑구 신내1동 신내역로3길 40-10
    서울강남경찰서-->대한민국 서울특별시 강남구 대치동 998
    서울관악경찰서-->대한민국 서울특별시 관악구 봉천동
    서울강서경찰서-->대한민국 서울특별시 양천구 신월동 화곡로 73
    서울강동경찰서-->대한민국 서울특별시 강동구 성내1동 성내로 57
    서울종암경찰서-->대한민국 서울특별시 성북구 종암동 종암로 135
    서울구로경찰서-->대한민국 서울특별시 구로구 가마산로 235
    서울서초경찰서-->대한민국 서울특별시 서초구 서초3동 반포대로 179
    서울양천경찰서-->대한민국 서울특별시 양천구 신정6동 목동동로 99
    서울송파경찰서-->대한민국 서울특별시 송파구 가락본동 9
    서울노원경찰서-->대한민국 서울특별시 노원구 하계동 노원로 283
    서울방배경찰서-->대한민국 서울특별시 서초구 방배본동 동작대로 204
    서울은평경찰서-->대한민국 서울특별시 은평구 불광동 연서로 365
    서울도봉경찰서-->대한민국 서울특별시 도봉구 창4동 노해로 403
    서울수서경찰서-->대한민국 서울특별시 강남구 개포동 개포로 617
    


```python
station_addreess
```




    ['대한민국 서울특별시 중구 을지로동 수표로 27',
     '대한민국 서울특별시 종로구 종로1.2.3.4가동 율곡로 46',
     '대한민국 서울특별시 중구 회현동 한강대로 410',
     '대한민국 서울특별시 서대문구 충현동 통일로 113',
     '대한민국 서울특별시 종로구 인의동 창경궁로 112-16',
     '대한민국 서울특별시 용산구 원효로1가 백범로 329',
     '대한민국 서울특별시 성북구 삼선동5가 301',
     '대한민국 서울특별시 동대문구 청량리동 약령시로21길 29',
     '대한민국 서울특별시 마포구 아현동 618-1',
     '대한민국 서울특별시 영등포구 영등포동 영등포로46길 14',
     '대한민국 서울특별시 성동구 행당동 왕십리광장로 9',
     '대한민국 서울특별시 동작구 노량진1동 노량진로 148',
     '대한민국 서울특별시 광진구 구의1동 자양로 167',
     '대한민국 서울특별시 은평구 대조동 통일로 757',
     '대한민국 서울특별시 강북구 번1동 오패산로 406',
     '대한민국 서울특별시 금천구 시흥동 190',
     '대한민국 서울특별시 중랑구 신내1동 신내역로3길 40-10',
     '대한민국 서울특별시 강남구 대치동 998',
     '대한민국 서울특별시 관악구 봉천동',
     '대한민국 서울특별시 양천구 신월동 화곡로 73',
     '대한민국 서울특별시 강동구 성내1동 성내로 57',
     '대한민국 서울특별시 성북구 종암동 종암로 135',
     '대한민국 서울특별시 구로구 가마산로 235',
     '대한민국 서울특별시 서초구 서초3동 반포대로 179',
     '대한민국 서울특별시 양천구 신정6동 목동동로 99',
     '대한민국 서울특별시 송파구 가락본동 9',
     '대한민국 서울특별시 노원구 하계동 노원로 283',
     '대한민국 서울특별시 서초구 방배본동 동작대로 204',
     '대한민국 서울특별시 은평구 불광동 연서로 365',
     '대한민국 서울특별시 도봉구 창4동 노해로 403',
     '대한민국 서울특별시 강남구 개포동 개포로 617']




```python
station_lat
```




    [37.5636465,
     37.5755578,
     37.5547584,
     37.5647848,
     37.5718529,
     37.5387099,
     37.5897482,
     37.58506149999999,
     37.550814,
     37.5179839,
     37.5617309,
     37.5130866,
     37.542873,
     37.6128611,
     37.63730390000001,
     37.4568722,
     37.618692,
     37.5094352,
     37.4743789,
     37.5397827,
     37.528511,
     37.6020592,
     37.494931,
     37.4956054,
     37.5165667,
     37.5019065,
     37.6423605,
     37.4945959,
     37.6283597,
     37.6533589,
     37.49349]




```python
station_lng
```




    [126.9895796,
     126.9848674,
     126.9734981,
     126.9667762,
     126.9989143,
     126.9659183,
     127.0161353,
     127.0457679,
     126.954028,
     126.9075056,
     127.0363806,
     126.9428498,
     127.083821,
     126.9274951,
     127.0273399,
     126.8970429,
     127.1047136,
     127.0669578,
     126.9509748,
     126.8299968,
     127.1268224,
     127.0321577,
     126.886731,
     127.0052504,
     126.8656763,
     127.1271513,
     127.0714027,
     126.9831279,
     126.9287226,
     127.052682,
     127.0772119]




```python
gu_name = []

for name in station_addreess:
    tmp = name.split()
    
    tmp_gu = [gu for gu in tmp if gu[-1] == '구'][0]
    
    gu_name.append(tmp_gu)
    
crime_anal_police['구별'] = gu_name
crime_anal_police.head()
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
      <th>관서명</th>
      <th>살인 발생</th>
      <th>살인 검거</th>
      <th>강도 발생</th>
      <th>강도 검거</th>
      <th>강간 발생</th>
      <th>강간 검거</th>
      <th>절도 발생</th>
      <th>절도 검거</th>
      <th>폭력 발생</th>
      <th>폭력 검거</th>
      <th>구별</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>중부서</td>
      <td>2</td>
      <td>2</td>
      <td>3</td>
      <td>2</td>
      <td>105</td>
      <td>65</td>
      <td>1395</td>
      <td>477</td>
      <td>1355</td>
      <td>1170</td>
      <td>중구</td>
    </tr>
    <tr>
      <th>1</th>
      <td>종로서</td>
      <td>3</td>
      <td>3</td>
      <td>6</td>
      <td>5</td>
      <td>115</td>
      <td>98</td>
      <td>1070</td>
      <td>413</td>
      <td>1278</td>
      <td>1070</td>
      <td>종로구</td>
    </tr>
    <tr>
      <th>2</th>
      <td>남대문서</td>
      <td>1</td>
      <td>0</td>
      <td>6</td>
      <td>4</td>
      <td>65</td>
      <td>46</td>
      <td>1153</td>
      <td>382</td>
      <td>869</td>
      <td>794</td>
      <td>중구</td>
    </tr>
    <tr>
      <th>3</th>
      <td>서대문서</td>
      <td>2</td>
      <td>2</td>
      <td>5</td>
      <td>4</td>
      <td>154</td>
      <td>124</td>
      <td>1812</td>
      <td>738</td>
      <td>2056</td>
      <td>1711</td>
      <td>서대문구</td>
    </tr>
    <tr>
      <th>4</th>
      <td>혜화서</td>
      <td>3</td>
      <td>2</td>
      <td>5</td>
      <td>4</td>
      <td>96</td>
      <td>63</td>
      <td>1114</td>
      <td>424</td>
      <td>1015</td>
      <td>861</td>
      <td>종로구</td>
    </tr>
  </tbody>
</table>
</div>




```python
crime_anal_police[crime_anal_police['관서명']=='금천서']
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
      <th>관서명</th>
      <th>살인 발생</th>
      <th>살인 검거</th>
      <th>강도 발생</th>
      <th>강도 검거</th>
      <th>강간 발생</th>
      <th>강간 검거</th>
      <th>절도 발생</th>
      <th>절도 검거</th>
      <th>폭력 발생</th>
      <th>폭력 검거</th>
      <th>구별</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>15</th>
      <td>금천서</td>
      <td>3</td>
      <td>4</td>
      <td>6</td>
      <td>6</td>
      <td>151</td>
      <td>122</td>
      <td>1567</td>
      <td>888</td>
      <td>2054</td>
      <td>1776</td>
      <td>금천구</td>
    </tr>
  </tbody>
</table>
</div>




```python
crime_anal_police.loc[crime_anal_police['관서명']=='금천서', ['구별']] = '금천구'
crime_anal_police[crime_anal_police['관서명']=='금천서']
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
      <th>관서명</th>
      <th>살인 발생</th>
      <th>살인 검거</th>
      <th>강도 발생</th>
      <th>강도 검거</th>
      <th>강간 발생</th>
      <th>강간 검거</th>
      <th>절도 발생</th>
      <th>절도 검거</th>
      <th>폭력 발생</th>
      <th>폭력 검거</th>
      <th>구별</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>15</th>
      <td>금천서</td>
      <td>3</td>
      <td>4</td>
      <td>6</td>
      <td>6</td>
      <td>151</td>
      <td>122</td>
      <td>1567</td>
      <td>888</td>
      <td>2054</td>
      <td>1776</td>
      <td>금천구</td>
    </tr>
  </tbody>
</table>
</div>




```python
crime_anal_police.to_csv('../data/02. crime_in_Seoul_include_gu_name.csv',
                         sep=',', encoding='utf-8')
```


```python
crime_anal_police.head()
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
      <th>관서명</th>
      <th>살인 발생</th>
      <th>살인 검거</th>
      <th>강도 발생</th>
      <th>강도 검거</th>
      <th>강간 발생</th>
      <th>강간 검거</th>
      <th>절도 발생</th>
      <th>절도 검거</th>
      <th>폭력 발생</th>
      <th>폭력 검거</th>
      <th>구별</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>중부서</td>
      <td>2</td>
      <td>2</td>
      <td>3</td>
      <td>2</td>
      <td>105</td>
      <td>65</td>
      <td>1395</td>
      <td>477</td>
      <td>1355</td>
      <td>1170</td>
      <td>중구</td>
    </tr>
    <tr>
      <th>1</th>
      <td>종로서</td>
      <td>3</td>
      <td>3</td>
      <td>6</td>
      <td>5</td>
      <td>115</td>
      <td>98</td>
      <td>1070</td>
      <td>413</td>
      <td>1278</td>
      <td>1070</td>
      <td>종로구</td>
    </tr>
    <tr>
      <th>2</th>
      <td>남대문서</td>
      <td>1</td>
      <td>0</td>
      <td>6</td>
      <td>4</td>
      <td>65</td>
      <td>46</td>
      <td>1153</td>
      <td>382</td>
      <td>869</td>
      <td>794</td>
      <td>중구</td>
    </tr>
    <tr>
      <th>3</th>
      <td>서대문서</td>
      <td>2</td>
      <td>2</td>
      <td>5</td>
      <td>4</td>
      <td>154</td>
      <td>124</td>
      <td>1812</td>
      <td>738</td>
      <td>2056</td>
      <td>1711</td>
      <td>서대문구</td>
    </tr>
    <tr>
      <th>4</th>
      <td>혜화서</td>
      <td>3</td>
      <td>2</td>
      <td>5</td>
      <td>4</td>
      <td>96</td>
      <td>63</td>
      <td>1114</td>
      <td>424</td>
      <td>1015</td>
      <td>861</td>
      <td>종로구</td>
    </tr>
  </tbody>
</table>
</div>



# pandas의 pivot_table 익히기


```python
import pandas as pd
import numpy as np
```


```python
df = pd.read_excel("../data/02. sales-funnel.xlsx")
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
      <th>Account</th>
      <th>Name</th>
      <th>Rep</th>
      <th>Manager</th>
      <th>Product</th>
      <th>Quantity</th>
      <th>Price</th>
      <th>Status</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>714466</td>
      <td>Trantow-Barrows</td>
      <td>Craig Booker</td>
      <td>Debra Henley</td>
      <td>CPU</td>
      <td>1</td>
      <td>30000</td>
      <td>presented</td>
    </tr>
    <tr>
      <th>1</th>
      <td>714466</td>
      <td>Trantow-Barrows</td>
      <td>Craig Booker</td>
      <td>Debra Henley</td>
      <td>Software</td>
      <td>1</td>
      <td>10000</td>
      <td>presented</td>
    </tr>
    <tr>
      <th>2</th>
      <td>714466</td>
      <td>Trantow-Barrows</td>
      <td>Craig Booker</td>
      <td>Debra Henley</td>
      <td>Maintenance</td>
      <td>2</td>
      <td>5000</td>
      <td>pending</td>
    </tr>
    <tr>
      <th>3</th>
      <td>737550</td>
      <td>Fritsch, Russel and Anderson</td>
      <td>Craig Booker</td>
      <td>Debra Henley</td>
      <td>CPU</td>
      <td>1</td>
      <td>35000</td>
      <td>declined</td>
    </tr>
    <tr>
      <th>4</th>
      <td>146832</td>
      <td>Kiehn-Spinka</td>
      <td>Daniel Hilton</td>
      <td>Debra Henley</td>
      <td>CPU</td>
      <td>2</td>
      <td>65000</td>
      <td>won</td>
    </tr>
  </tbody>
</table>
</div>




```python
pd.pivot_table(df,index=["Name"])
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
      <th>Account</th>
      <th>Price</th>
      <th>Quantity</th>
    </tr>
    <tr>
      <th>Name</th>
      <th></th>
      <th></th>
      <th></th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>Barton LLC</th>
      <td>740150</td>
      <td>35000</td>
      <td>1.000000</td>
    </tr>
    <tr>
      <th>Fritsch, Russel and Anderson</th>
      <td>737550</td>
      <td>35000</td>
      <td>1.000000</td>
    </tr>
    <tr>
      <th>Herman LLC</th>
      <td>141962</td>
      <td>65000</td>
      <td>2.000000</td>
    </tr>
    <tr>
      <th>Jerde-Hilpert</th>
      <td>412290</td>
      <td>5000</td>
      <td>2.000000</td>
    </tr>
    <tr>
      <th>Kassulke, Ondricka and Metz</th>
      <td>307599</td>
      <td>7000</td>
      <td>3.000000</td>
    </tr>
    <tr>
      <th>Keeling LLC</th>
      <td>688981</td>
      <td>100000</td>
      <td>5.000000</td>
    </tr>
    <tr>
      <th>Kiehn-Spinka</th>
      <td>146832</td>
      <td>65000</td>
      <td>2.000000</td>
    </tr>
    <tr>
      <th>Koepp Ltd</th>
      <td>729833</td>
      <td>35000</td>
      <td>2.000000</td>
    </tr>
    <tr>
      <th>Kulas Inc</th>
      <td>218895</td>
      <td>25000</td>
      <td>1.500000</td>
    </tr>
    <tr>
      <th>Purdy-Kunde</th>
      <td>163416</td>
      <td>30000</td>
      <td>1.000000</td>
    </tr>
    <tr>
      <th>Stokes LLC</th>
      <td>239344</td>
      <td>7500</td>
      <td>1.000000</td>
    </tr>
    <tr>
      <th>Trantow-Barrows</th>
      <td>714466</td>
      <td>15000</td>
      <td>1.333333</td>
    </tr>
  </tbody>
</table>
</div>




```python
pd.pivot_table(df,index=["Name","Rep","Manager"])
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
      <th></th>
      <th></th>
      <th>Account</th>
      <th>Price</th>
      <th>Quantity</th>
    </tr>
    <tr>
      <th>Name</th>
      <th>Rep</th>
      <th>Manager</th>
      <th></th>
      <th></th>
      <th></th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>Barton LLC</th>
      <th>John Smith</th>
      <th>Debra Henley</th>
      <td>740150</td>
      <td>35000</td>
      <td>1.000000</td>
    </tr>
    <tr>
      <th>Fritsch, Russel and Anderson</th>
      <th>Craig Booker</th>
      <th>Debra Henley</th>
      <td>737550</td>
      <td>35000</td>
      <td>1.000000</td>
    </tr>
    <tr>
      <th>Herman LLC</th>
      <th>Cedric Moss</th>
      <th>Fred Anderson</th>
      <td>141962</td>
      <td>65000</td>
      <td>2.000000</td>
    </tr>
    <tr>
      <th>Jerde-Hilpert</th>
      <th>John Smith</th>
      <th>Debra Henley</th>
      <td>412290</td>
      <td>5000</td>
      <td>2.000000</td>
    </tr>
    <tr>
      <th>Kassulke, Ondricka and Metz</th>
      <th>Wendy Yule</th>
      <th>Fred Anderson</th>
      <td>307599</td>
      <td>7000</td>
      <td>3.000000</td>
    </tr>
    <tr>
      <th>Keeling LLC</th>
      <th>Wendy Yule</th>
      <th>Fred Anderson</th>
      <td>688981</td>
      <td>100000</td>
      <td>5.000000</td>
    </tr>
    <tr>
      <th>Kiehn-Spinka</th>
      <th>Daniel Hilton</th>
      <th>Debra Henley</th>
      <td>146832</td>
      <td>65000</td>
      <td>2.000000</td>
    </tr>
    <tr>
      <th>Koepp Ltd</th>
      <th>Wendy Yule</th>
      <th>Fred Anderson</th>
      <td>729833</td>
      <td>35000</td>
      <td>2.000000</td>
    </tr>
    <tr>
      <th>Kulas Inc</th>
      <th>Daniel Hilton</th>
      <th>Debra Henley</th>
      <td>218895</td>
      <td>25000</td>
      <td>1.500000</td>
    </tr>
    <tr>
      <th>Purdy-Kunde</th>
      <th>Cedric Moss</th>
      <th>Fred Anderson</th>
      <td>163416</td>
      <td>30000</td>
      <td>1.000000</td>
    </tr>
    <tr>
      <th>Stokes LLC</th>
      <th>Cedric Moss</th>
      <th>Fred Anderson</th>
      <td>239344</td>
      <td>7500</td>
      <td>1.000000</td>
    </tr>
    <tr>
      <th>Trantow-Barrows</th>
      <th>Craig Booker</th>
      <th>Debra Henley</th>
      <td>714466</td>
      <td>15000</td>
      <td>1.333333</td>
    </tr>
  </tbody>
</table>
</div>




```python
pd.pivot_table(df,index=["Manager","Rep"])
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
      <th></th>
      <th>Account</th>
      <th>Price</th>
      <th>Quantity</th>
    </tr>
    <tr>
      <th>Manager</th>
      <th>Rep</th>
      <th></th>
      <th></th>
      <th></th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th rowspan="3" valign="top">Debra Henley</th>
      <th>Craig Booker</th>
      <td>720237.0</td>
      <td>20000.000000</td>
      <td>1.250000</td>
    </tr>
    <tr>
      <th>Daniel Hilton</th>
      <td>194874.0</td>
      <td>38333.333333</td>
      <td>1.666667</td>
    </tr>
    <tr>
      <th>John Smith</th>
      <td>576220.0</td>
      <td>20000.000000</td>
      <td>1.500000</td>
    </tr>
    <tr>
      <th rowspan="2" valign="top">Fred Anderson</th>
      <th>Cedric Moss</th>
      <td>196016.5</td>
      <td>27500.000000</td>
      <td>1.250000</td>
    </tr>
    <tr>
      <th>Wendy Yule</th>
      <td>614061.5</td>
      <td>44250.000000</td>
      <td>3.000000</td>
    </tr>
  </tbody>
</table>
</div>




```python
pd.pivot_table(df,index=["Manager","Rep"],values=["Price"])
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
      <th></th>
      <th>Price</th>
    </tr>
    <tr>
      <th>Manager</th>
      <th>Rep</th>
      <th></th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th rowspan="3" valign="top">Debra Henley</th>
      <th>Craig Booker</th>
      <td>20000.000000</td>
    </tr>
    <tr>
      <th>Daniel Hilton</th>
      <td>38333.333333</td>
    </tr>
    <tr>
      <th>John Smith</th>
      <td>20000.000000</td>
    </tr>
    <tr>
      <th rowspan="2" valign="top">Fred Anderson</th>
      <th>Cedric Moss</th>
      <td>27500.000000</td>
    </tr>
    <tr>
      <th>Wendy Yule</th>
      <td>44250.000000</td>
    </tr>
  </tbody>
</table>
</div>




```python
pd.pivot_table(df,index=["Manager","Rep"],values=["Price"],aggfunc=np.sum)
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
      <th></th>
      <th>Price</th>
    </tr>
    <tr>
      <th>Manager</th>
      <th>Rep</th>
      <th></th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th rowspan="3" valign="top">Debra Henley</th>
      <th>Craig Booker</th>
      <td>80000</td>
    </tr>
    <tr>
      <th>Daniel Hilton</th>
      <td>115000</td>
    </tr>
    <tr>
      <th>John Smith</th>
      <td>40000</td>
    </tr>
    <tr>
      <th rowspan="2" valign="top">Fred Anderson</th>
      <th>Cedric Moss</th>
      <td>110000</td>
    </tr>
    <tr>
      <th>Wendy Yule</th>
      <td>177000</td>
    </tr>
  </tbody>
</table>
</div>




```python
pd.pivot_table(df,index=["Manager","Rep"],values=["Price"],aggfunc=[np.mean,len])
```




<div>
<style scoped>
    .dataframe tbody tr th:only-of-type {
        vertical-align: middle;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }

    .dataframe thead tr th {
        text-align: left;
    }

    .dataframe thead tr:last-of-type th {
        text-align: right;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr>
      <th></th>
      <th></th>
      <th>mean</th>
      <th>len</th>
    </tr>
    <tr>
      <th></th>
      <th></th>
      <th>Price</th>
      <th>Price</th>
    </tr>
    <tr>
      <th>Manager</th>
      <th>Rep</th>
      <th></th>
      <th></th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th rowspan="3" valign="top">Debra Henley</th>
      <th>Craig Booker</th>
      <td>20000.000000</td>
      <td>4</td>
    </tr>
    <tr>
      <th>Daniel Hilton</th>
      <td>38333.333333</td>
      <td>3</td>
    </tr>
    <tr>
      <th>John Smith</th>
      <td>20000.000000</td>
      <td>2</td>
    </tr>
    <tr>
      <th rowspan="2" valign="top">Fred Anderson</th>
      <th>Cedric Moss</th>
      <td>27500.000000</td>
      <td>4</td>
    </tr>
    <tr>
      <th>Wendy Yule</th>
      <td>44250.000000</td>
      <td>4</td>
    </tr>
  </tbody>
</table>
</div>




```python
pd.pivot_table(df,index=["Manager","Rep"],values=["Price"],
               columns=["Product"],aggfunc=[np.sum])
```




<div>
<style scoped>
    .dataframe tbody tr th:only-of-type {
        vertical-align: middle;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }

    .dataframe thead tr th {
        text-align: left;
    }

    .dataframe thead tr:last-of-type th {
        text-align: right;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr>
      <th></th>
      <th></th>
      <th colspan="4" halign="left">sum</th>
    </tr>
    <tr>
      <th></th>
      <th></th>
      <th colspan="4" halign="left">Price</th>
    </tr>
    <tr>
      <th></th>
      <th>Product</th>
      <th>CPU</th>
      <th>Maintenance</th>
      <th>Monitor</th>
      <th>Software</th>
    </tr>
    <tr>
      <th>Manager</th>
      <th>Rep</th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th rowspan="3" valign="top">Debra Henley</th>
      <th>Craig Booker</th>
      <td>65000.0</td>
      <td>5000.0</td>
      <td>NaN</td>
      <td>10000.0</td>
    </tr>
    <tr>
      <th>Daniel Hilton</th>
      <td>105000.0</td>
      <td>NaN</td>
      <td>NaN</td>
      <td>10000.0</td>
    </tr>
    <tr>
      <th>John Smith</th>
      <td>35000.0</td>
      <td>5000.0</td>
      <td>NaN</td>
      <td>NaN</td>
    </tr>
    <tr>
      <th rowspan="2" valign="top">Fred Anderson</th>
      <th>Cedric Moss</th>
      <td>95000.0</td>
      <td>5000.0</td>
      <td>NaN</td>
      <td>10000.0</td>
    </tr>
    <tr>
      <th>Wendy Yule</th>
      <td>165000.0</td>
      <td>7000.0</td>
      <td>5000.0</td>
      <td>NaN</td>
    </tr>
  </tbody>
</table>
</div>




```python
pd.pivot_table(df,index=["Manager","Rep"],values=["Price"],
               columns=["Product"],aggfunc=[np.sum],fill_value=0)
```




<div>
<style scoped>
    .dataframe tbody tr th:only-of-type {
        vertical-align: middle;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }

    .dataframe thead tr th {
        text-align: left;
    }

    .dataframe thead tr:last-of-type th {
        text-align: right;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr>
      <th></th>
      <th></th>
      <th colspan="4" halign="left">sum</th>
    </tr>
    <tr>
      <th></th>
      <th></th>
      <th colspan="4" halign="left">Price</th>
    </tr>
    <tr>
      <th></th>
      <th>Product</th>
      <th>CPU</th>
      <th>Maintenance</th>
      <th>Monitor</th>
      <th>Software</th>
    </tr>
    <tr>
      <th>Manager</th>
      <th>Rep</th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th rowspan="3" valign="top">Debra Henley</th>
      <th>Craig Booker</th>
      <td>65000</td>
      <td>5000</td>
      <td>0</td>
      <td>10000</td>
    </tr>
    <tr>
      <th>Daniel Hilton</th>
      <td>105000</td>
      <td>0</td>
      <td>0</td>
      <td>10000</td>
    </tr>
    <tr>
      <th>John Smith</th>
      <td>35000</td>
      <td>5000</td>
      <td>0</td>
      <td>0</td>
    </tr>
    <tr>
      <th rowspan="2" valign="top">Fred Anderson</th>
      <th>Cedric Moss</th>
      <td>95000</td>
      <td>5000</td>
      <td>0</td>
      <td>10000</td>
    </tr>
    <tr>
      <th>Wendy Yule</th>
      <td>165000</td>
      <td>7000</td>
      <td>5000</td>
      <td>0</td>
    </tr>
  </tbody>
</table>
</div>




```python
pd.pivot_table(df,index=["Manager","Rep","Product"],
               values=["Price","Quantity"],aggfunc=[np.sum],fill_value=0)
```




<div>
<style scoped>
    .dataframe tbody tr th:only-of-type {
        vertical-align: middle;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }

    .dataframe thead tr th {
        text-align: left;
    }

    .dataframe thead tr:last-of-type th {
        text-align: right;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr>
      <th></th>
      <th></th>
      <th></th>
      <th colspan="2" halign="left">sum</th>
    </tr>
    <tr>
      <th></th>
      <th></th>
      <th></th>
      <th>Price</th>
      <th>Quantity</th>
    </tr>
    <tr>
      <th>Manager</th>
      <th>Rep</th>
      <th>Product</th>
      <th></th>
      <th></th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th rowspan="7" valign="top">Debra Henley</th>
      <th rowspan="3" valign="top">Craig Booker</th>
      <th>CPU</th>
      <td>65000</td>
      <td>2</td>
    </tr>
    <tr>
      <th>Maintenance</th>
      <td>5000</td>
      <td>2</td>
    </tr>
    <tr>
      <th>Software</th>
      <td>10000</td>
      <td>1</td>
    </tr>
    <tr>
      <th rowspan="2" valign="top">Daniel Hilton</th>
      <th>CPU</th>
      <td>105000</td>
      <td>4</td>
    </tr>
    <tr>
      <th>Software</th>
      <td>10000</td>
      <td>1</td>
    </tr>
    <tr>
      <th rowspan="2" valign="top">John Smith</th>
      <th>CPU</th>
      <td>35000</td>
      <td>1</td>
    </tr>
    <tr>
      <th>Maintenance</th>
      <td>5000</td>
      <td>2</td>
    </tr>
    <tr>
      <th rowspan="6" valign="top">Fred Anderson</th>
      <th rowspan="3" valign="top">Cedric Moss</th>
      <th>CPU</th>
      <td>95000</td>
      <td>3</td>
    </tr>
    <tr>
      <th>Maintenance</th>
      <td>5000</td>
      <td>1</td>
    </tr>
    <tr>
      <th>Software</th>
      <td>10000</td>
      <td>1</td>
    </tr>
    <tr>
      <th rowspan="3" valign="top">Wendy Yule</th>
      <th>CPU</th>
      <td>165000</td>
      <td>7</td>
    </tr>
    <tr>
      <th>Maintenance</th>
      <td>7000</td>
      <td>3</td>
    </tr>
    <tr>
      <th>Monitor</th>
      <td>5000</td>
      <td>2</td>
    </tr>
  </tbody>
</table>
</div>




```python
pd.pivot_table(df,index=["Manager","Rep","Product"],
               values=["Price","Quantity"],
               aggfunc=[np.sum,np.mean],fill_value=0,margins=True)
```




<div>
<style scoped>
    .dataframe tbody tr th:only-of-type {
        vertical-align: middle;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }

    .dataframe thead tr th {
        text-align: left;
    }

    .dataframe thead tr:last-of-type th {
        text-align: right;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr>
      <th></th>
      <th></th>
      <th></th>
      <th colspan="2" halign="left">sum</th>
      <th colspan="2" halign="left">mean</th>
    </tr>
    <tr>
      <th></th>
      <th></th>
      <th></th>
      <th>Price</th>
      <th>Quantity</th>
      <th>Price</th>
      <th>Quantity</th>
    </tr>
    <tr>
      <th>Manager</th>
      <th>Rep</th>
      <th>Product</th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th rowspan="7" valign="top">Debra Henley</th>
      <th rowspan="3" valign="top">Craig Booker</th>
      <th>CPU</th>
      <td>65000</td>
      <td>2</td>
      <td>32500</td>
      <td>1.000000</td>
    </tr>
    <tr>
      <th>Maintenance</th>
      <td>5000</td>
      <td>2</td>
      <td>5000</td>
      <td>2.000000</td>
    </tr>
    <tr>
      <th>Software</th>
      <td>10000</td>
      <td>1</td>
      <td>10000</td>
      <td>1.000000</td>
    </tr>
    <tr>
      <th rowspan="2" valign="top">Daniel Hilton</th>
      <th>CPU</th>
      <td>105000</td>
      <td>4</td>
      <td>52500</td>
      <td>2.000000</td>
    </tr>
    <tr>
      <th>Software</th>
      <td>10000</td>
      <td>1</td>
      <td>10000</td>
      <td>1.000000</td>
    </tr>
    <tr>
      <th rowspan="2" valign="top">John Smith</th>
      <th>CPU</th>
      <td>35000</td>
      <td>1</td>
      <td>35000</td>
      <td>1.000000</td>
    </tr>
    <tr>
      <th>Maintenance</th>
      <td>5000</td>
      <td>2</td>
      <td>5000</td>
      <td>2.000000</td>
    </tr>
    <tr>
      <th rowspan="6" valign="top">Fred Anderson</th>
      <th rowspan="3" valign="top">Cedric Moss</th>
      <th>CPU</th>
      <td>95000</td>
      <td>3</td>
      <td>47500</td>
      <td>1.500000</td>
    </tr>
    <tr>
      <th>Maintenance</th>
      <td>5000</td>
      <td>1</td>
      <td>5000</td>
      <td>1.000000</td>
    </tr>
    <tr>
      <th>Software</th>
      <td>10000</td>
      <td>1</td>
      <td>10000</td>
      <td>1.000000</td>
    </tr>
    <tr>
      <th rowspan="3" valign="top">Wendy Yule</th>
      <th>CPU</th>
      <td>165000</td>
      <td>7</td>
      <td>82500</td>
      <td>3.500000</td>
    </tr>
    <tr>
      <th>Maintenance</th>
      <td>7000</td>
      <td>3</td>
      <td>7000</td>
      <td>3.000000</td>
    </tr>
    <tr>
      <th>Monitor</th>
      <td>5000</td>
      <td>2</td>
      <td>5000</td>
      <td>2.000000</td>
    </tr>
    <tr>
      <th>All</th>
      <th></th>
      <th></th>
      <td>522000</td>
      <td>30</td>
      <td>30705</td>
      <td>1.764706</td>
    </tr>
  </tbody>
</table>
</div>



# 범죄 데이터 구별로 정리하기


```python
crime_anal_raw = pd.read_csv('../data/02. crime_in_Seoul_include_gu_name.csv', 
                             encoding='utf-8')
crime_anal_raw.head()
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
      <th>Unnamed: 0</th>
      <th>관서명</th>
      <th>살인 발생</th>
      <th>살인 검거</th>
      <th>강도 발생</th>
      <th>강도 검거</th>
      <th>강간 발생</th>
      <th>강간 검거</th>
      <th>절도 발생</th>
      <th>절도 검거</th>
      <th>폭력 발생</th>
      <th>폭력 검거</th>
      <th>구별</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>0</td>
      <td>중부서</td>
      <td>2</td>
      <td>2</td>
      <td>3</td>
      <td>2</td>
      <td>105</td>
      <td>65</td>
      <td>1395</td>
      <td>477</td>
      <td>1355</td>
      <td>1170</td>
      <td>중구</td>
    </tr>
    <tr>
      <th>1</th>
      <td>1</td>
      <td>종로서</td>
      <td>3</td>
      <td>3</td>
      <td>6</td>
      <td>5</td>
      <td>115</td>
      <td>98</td>
      <td>1070</td>
      <td>413</td>
      <td>1278</td>
      <td>1070</td>
      <td>종로구</td>
    </tr>
    <tr>
      <th>2</th>
      <td>2</td>
      <td>남대문서</td>
      <td>1</td>
      <td>0</td>
      <td>6</td>
      <td>4</td>
      <td>65</td>
      <td>46</td>
      <td>1153</td>
      <td>382</td>
      <td>869</td>
      <td>794</td>
      <td>중구</td>
    </tr>
    <tr>
      <th>3</th>
      <td>3</td>
      <td>서대문서</td>
      <td>2</td>
      <td>2</td>
      <td>5</td>
      <td>4</td>
      <td>154</td>
      <td>124</td>
      <td>1812</td>
      <td>738</td>
      <td>2056</td>
      <td>1711</td>
      <td>서대문구</td>
    </tr>
    <tr>
      <th>4</th>
      <td>4</td>
      <td>혜화서</td>
      <td>3</td>
      <td>2</td>
      <td>5</td>
      <td>4</td>
      <td>96</td>
      <td>63</td>
      <td>1114</td>
      <td>424</td>
      <td>1015</td>
      <td>861</td>
      <td>종로구</td>
    </tr>
  </tbody>
</table>
</div>




```python
crime_anal_raw = pd.read_csv('../data/02. crime_in_Seoul_include_gu_name.csv', 
                             encoding='utf-8', index_col=0)

crime_anal = pd.pivot_table(crime_anal_raw, index='구별', aggfunc=np.sum)
crime_anal.head()
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
      <th>강간 검거</th>
      <th>강간 발생</th>
      <th>강도 검거</th>
      <th>강도 발생</th>
      <th>살인 검거</th>
      <th>살인 발생</th>
      <th>절도 검거</th>
      <th>절도 발생</th>
      <th>폭력 검거</th>
      <th>폭력 발생</th>
    </tr>
    <tr>
      <th>구별</th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
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
      <th>강남구</th>
      <td>349</td>
      <td>449</td>
      <td>18</td>
      <td>21</td>
      <td>10</td>
      <td>13</td>
      <td>1650</td>
      <td>3850</td>
      <td>3705</td>
      <td>4284</td>
    </tr>
    <tr>
      <th>강동구</th>
      <td>123</td>
      <td>156</td>
      <td>8</td>
      <td>6</td>
      <td>3</td>
      <td>4</td>
      <td>789</td>
      <td>2366</td>
      <td>2248</td>
      <td>2712</td>
    </tr>
    <tr>
      <th>강북구</th>
      <td>126</td>
      <td>153</td>
      <td>13</td>
      <td>14</td>
      <td>8</td>
      <td>7</td>
      <td>618</td>
      <td>1434</td>
      <td>2348</td>
      <td>2649</td>
    </tr>
    <tr>
      <th>관악구</th>
      <td>221</td>
      <td>320</td>
      <td>14</td>
      <td>12</td>
      <td>8</td>
      <td>9</td>
      <td>827</td>
      <td>2706</td>
      <td>2642</td>
      <td>3298</td>
    </tr>
    <tr>
      <th>광진구</th>
      <td>220</td>
      <td>240</td>
      <td>26</td>
      <td>14</td>
      <td>4</td>
      <td>4</td>
      <td>1277</td>
      <td>3026</td>
      <td>2180</td>
      <td>2625</td>
    </tr>
  </tbody>
</table>
</div>




```python
crime_anal['강간검거율'] = crime_anal['강간 검거']/crime_anal['강간 발생']*100
crime_anal['강도검거율'] = crime_anal['강도 검거']/crime_anal['강도 발생']*100
crime_anal['살인검거율'] = crime_anal['살인 검거']/crime_anal['살인 발생']*100
crime_anal['절도검거율'] = crime_anal['절도 검거']/crime_anal['절도 발생']*100
crime_anal['폭력검거율'] = crime_anal['폭력 검거']/crime_anal['폭력 발생']*100

del crime_anal['강간 검거']
del crime_anal['강도 검거']
del crime_anal['살인 검거']
del crime_anal['절도 검거']
del crime_anal['폭력 검거']

crime_anal.head()
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
      <th>강간 발생</th>
      <th>강도 발생</th>
      <th>살인 발생</th>
      <th>절도 발생</th>
      <th>폭력 발생</th>
      <th>강간검거율</th>
      <th>강도검거율</th>
      <th>살인검거율</th>
      <th>절도검거율</th>
      <th>폭력검거율</th>
    </tr>
    <tr>
      <th>구별</th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
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
      <th>강남구</th>
      <td>449</td>
      <td>21</td>
      <td>13</td>
      <td>3850</td>
      <td>4284</td>
      <td>77.728285</td>
      <td>85.714286</td>
      <td>76.923077</td>
      <td>42.857143</td>
      <td>86.484594</td>
    </tr>
    <tr>
      <th>강동구</th>
      <td>156</td>
      <td>6</td>
      <td>4</td>
      <td>2366</td>
      <td>2712</td>
      <td>78.846154</td>
      <td>133.333333</td>
      <td>75.000000</td>
      <td>33.347422</td>
      <td>82.890855</td>
    </tr>
    <tr>
      <th>강북구</th>
      <td>153</td>
      <td>14</td>
      <td>7</td>
      <td>1434</td>
      <td>2649</td>
      <td>82.352941</td>
      <td>92.857143</td>
      <td>114.285714</td>
      <td>43.096234</td>
      <td>88.637222</td>
    </tr>
    <tr>
      <th>관악구</th>
      <td>320</td>
      <td>12</td>
      <td>9</td>
      <td>2706</td>
      <td>3298</td>
      <td>69.062500</td>
      <td>116.666667</td>
      <td>88.888889</td>
      <td>30.561715</td>
      <td>80.109157</td>
    </tr>
    <tr>
      <th>광진구</th>
      <td>240</td>
      <td>14</td>
      <td>4</td>
      <td>3026</td>
      <td>2625</td>
      <td>91.666667</td>
      <td>185.714286</td>
      <td>100.000000</td>
      <td>42.200925</td>
      <td>83.047619</td>
    </tr>
  </tbody>
</table>
</div>




```python
con_list = ['강간검거율', '강도검거율', '살인검거율', '절도검거율', '폭력검거율']

for column in con_list:
    crime_anal.loc[crime_anal[column] > 100, column] = 100
    
crime_anal.head()
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
      <th>강간 발생</th>
      <th>강도 발생</th>
      <th>살인 발생</th>
      <th>절도 발생</th>
      <th>폭력 발생</th>
      <th>강간검거율</th>
      <th>강도검거율</th>
      <th>살인검거율</th>
      <th>절도검거율</th>
      <th>폭력검거율</th>
    </tr>
    <tr>
      <th>구별</th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
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
      <th>강남구</th>
      <td>449</td>
      <td>21</td>
      <td>13</td>
      <td>3850</td>
      <td>4284</td>
      <td>77.728285</td>
      <td>85.714286</td>
      <td>76.923077</td>
      <td>42.857143</td>
      <td>86.484594</td>
    </tr>
    <tr>
      <th>강동구</th>
      <td>156</td>
      <td>6</td>
      <td>4</td>
      <td>2366</td>
      <td>2712</td>
      <td>78.846154</td>
      <td>100.000000</td>
      <td>75.000000</td>
      <td>33.347422</td>
      <td>82.890855</td>
    </tr>
    <tr>
      <th>강북구</th>
      <td>153</td>
      <td>14</td>
      <td>7</td>
      <td>1434</td>
      <td>2649</td>
      <td>82.352941</td>
      <td>92.857143</td>
      <td>100.000000</td>
      <td>43.096234</td>
      <td>88.637222</td>
    </tr>
    <tr>
      <th>관악구</th>
      <td>320</td>
      <td>12</td>
      <td>9</td>
      <td>2706</td>
      <td>3298</td>
      <td>69.062500</td>
      <td>100.000000</td>
      <td>88.888889</td>
      <td>30.561715</td>
      <td>80.109157</td>
    </tr>
    <tr>
      <th>광진구</th>
      <td>240</td>
      <td>14</td>
      <td>4</td>
      <td>3026</td>
      <td>2625</td>
      <td>91.666667</td>
      <td>100.000000</td>
      <td>100.000000</td>
      <td>42.200925</td>
      <td>83.047619</td>
    </tr>
  </tbody>
</table>
</div>




```python
crime_anal.rename(columns = {'강간 발생':'강간', 
                             '강도 발생':'강도', 
                             '살인 발생':'살인', 
                             '절도 발생':'절도', 
                             '폭력 발생':'폭력'}, inplace=True)
crime_anal.head()
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
      <th>강간</th>
      <th>강도</th>
      <th>살인</th>
      <th>절도</th>
      <th>폭력</th>
      <th>강간검거율</th>
      <th>강도검거율</th>
      <th>살인검거율</th>
      <th>절도검거율</th>
      <th>폭력검거율</th>
    </tr>
    <tr>
      <th>구별</th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
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
      <th>강남구</th>
      <td>449</td>
      <td>21</td>
      <td>13</td>
      <td>3850</td>
      <td>4284</td>
      <td>77.728285</td>
      <td>85.714286</td>
      <td>76.923077</td>
      <td>42.857143</td>
      <td>86.484594</td>
    </tr>
    <tr>
      <th>강동구</th>
      <td>156</td>
      <td>6</td>
      <td>4</td>
      <td>2366</td>
      <td>2712</td>
      <td>78.846154</td>
      <td>100.000000</td>
      <td>75.000000</td>
      <td>33.347422</td>
      <td>82.890855</td>
    </tr>
    <tr>
      <th>강북구</th>
      <td>153</td>
      <td>14</td>
      <td>7</td>
      <td>1434</td>
      <td>2649</td>
      <td>82.352941</td>
      <td>92.857143</td>
      <td>100.000000</td>
      <td>43.096234</td>
      <td>88.637222</td>
    </tr>
    <tr>
      <th>관악구</th>
      <td>320</td>
      <td>12</td>
      <td>9</td>
      <td>2706</td>
      <td>3298</td>
      <td>69.062500</td>
      <td>100.000000</td>
      <td>88.888889</td>
      <td>30.561715</td>
      <td>80.109157</td>
    </tr>
    <tr>
      <th>광진구</th>
      <td>240</td>
      <td>14</td>
      <td>4</td>
      <td>3026</td>
      <td>2625</td>
      <td>91.666667</td>
      <td>100.000000</td>
      <td>100.000000</td>
      <td>42.200925</td>
      <td>83.047619</td>
    </tr>
  </tbody>
</table>
</div>




```python
from sklearn import preprocessing

col = ['강간', '강도', '살인', '절도', '폭력']

x = crime_anal[col].values
min_max_scaler = preprocessing.MinMaxScaler()

x_scaled = min_max_scaler.fit_transform(x.astype(float))
crime_anal_norm = pd.DataFrame(x_scaled, columns = col, index = crime_anal.index)

col2 = ['강간검거율', '강도검거율', '살인검거율', '절도검거율', '폭력검거율']
crime_anal_norm[col2] = crime_anal[col2]
crime_anal_norm.head()
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
      <th>강간</th>
      <th>강도</th>
      <th>살인</th>
      <th>절도</th>
      <th>폭력</th>
      <th>강간검거율</th>
      <th>강도검거율</th>
      <th>살인검거율</th>
      <th>절도검거율</th>
      <th>폭력검거율</th>
    </tr>
    <tr>
      <th>구별</th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
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
      <th>강남구</th>
      <td>1.000000</td>
      <td>0.941176</td>
      <td>0.916667</td>
      <td>0.953472</td>
      <td>0.661386</td>
      <td>77.728285</td>
      <td>85.714286</td>
      <td>76.923077</td>
      <td>42.857143</td>
      <td>86.484594</td>
    </tr>
    <tr>
      <th>강동구</th>
      <td>0.155620</td>
      <td>0.058824</td>
      <td>0.166667</td>
      <td>0.445775</td>
      <td>0.289667</td>
      <td>78.846154</td>
      <td>100.000000</td>
      <td>75.000000</td>
      <td>33.347422</td>
      <td>82.890855</td>
    </tr>
    <tr>
      <th>강북구</th>
      <td>0.146974</td>
      <td>0.529412</td>
      <td>0.416667</td>
      <td>0.126924</td>
      <td>0.274769</td>
      <td>82.352941</td>
      <td>92.857143</td>
      <td>100.000000</td>
      <td>43.096234</td>
      <td>88.637222</td>
    </tr>
    <tr>
      <th>관악구</th>
      <td>0.628242</td>
      <td>0.411765</td>
      <td>0.583333</td>
      <td>0.562094</td>
      <td>0.428234</td>
      <td>69.062500</td>
      <td>100.000000</td>
      <td>88.888889</td>
      <td>30.561715</td>
      <td>80.109157</td>
    </tr>
    <tr>
      <th>광진구</th>
      <td>0.397695</td>
      <td>0.529412</td>
      <td>0.166667</td>
      <td>0.671570</td>
      <td>0.269094</td>
      <td>91.666667</td>
      <td>100.000000</td>
      <td>100.000000</td>
      <td>42.200925</td>
      <td>83.047619</td>
    </tr>
  </tbody>
</table>
</div>




```python
result_CCTV = pd.read_csv('../data/01. CCTV_result.csv', encoding='UTF-8', 
                          index_col='구별')
crime_anal_norm[['인구수', 'CCTV']] = result_CCTV[['인구수', '소계']]
crime_anal_norm.head()
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
      <th>강간</th>
      <th>강도</th>
      <th>살인</th>
      <th>절도</th>
      <th>폭력</th>
      <th>강간검거율</th>
      <th>강도검거율</th>
      <th>살인검거율</th>
      <th>절도검거율</th>
      <th>폭력검거율</th>
      <th>인구수</th>
      <th>CCTV</th>
    </tr>
    <tr>
      <th>구별</th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
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
      <th>강남구</th>
      <td>1.000000</td>
      <td>0.941176</td>
      <td>0.916667</td>
      <td>0.953472</td>
      <td>0.661386</td>
      <td>77.728285</td>
      <td>85.714286</td>
      <td>76.923077</td>
      <td>42.857143</td>
      <td>86.484594</td>
      <td>570500.0</td>
      <td>2780</td>
    </tr>
    <tr>
      <th>강동구</th>
      <td>0.155620</td>
      <td>0.058824</td>
      <td>0.166667</td>
      <td>0.445775</td>
      <td>0.289667</td>
      <td>78.846154</td>
      <td>100.000000</td>
      <td>75.000000</td>
      <td>33.347422</td>
      <td>82.890855</td>
      <td>453233.0</td>
      <td>773</td>
    </tr>
    <tr>
      <th>강북구</th>
      <td>0.146974</td>
      <td>0.529412</td>
      <td>0.416667</td>
      <td>0.126924</td>
      <td>0.274769</td>
      <td>82.352941</td>
      <td>92.857143</td>
      <td>100.000000</td>
      <td>43.096234</td>
      <td>88.637222</td>
      <td>330192.0</td>
      <td>748</td>
    </tr>
    <tr>
      <th>관악구</th>
      <td>0.628242</td>
      <td>0.411765</td>
      <td>0.583333</td>
      <td>0.562094</td>
      <td>0.428234</td>
      <td>69.062500</td>
      <td>100.000000</td>
      <td>88.888889</td>
      <td>30.561715</td>
      <td>80.109157</td>
      <td>525515.0</td>
      <td>1496</td>
    </tr>
    <tr>
      <th>광진구</th>
      <td>0.397695</td>
      <td>0.529412</td>
      <td>0.166667</td>
      <td>0.671570</td>
      <td>0.269094</td>
      <td>91.666667</td>
      <td>100.000000</td>
      <td>100.000000</td>
      <td>42.200925</td>
      <td>83.047619</td>
      <td>372164.0</td>
      <td>707</td>
    </tr>
  </tbody>
</table>
</div>




```python
col = ['강간','강도','살인','절도','폭력']
crime_anal_norm['범죄'] = np.sum(crime_anal_norm[col], axis=1)
crime_anal_norm.head()
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
      <th>강간</th>
      <th>강도</th>
      <th>살인</th>
      <th>절도</th>
      <th>폭력</th>
      <th>강간검거율</th>
      <th>강도검거율</th>
      <th>살인검거율</th>
      <th>절도검거율</th>
      <th>폭력검거율</th>
      <th>인구수</th>
      <th>CCTV</th>
      <th>범죄</th>
    </tr>
    <tr>
      <th>구별</th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
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
      <th>강남구</th>
      <td>1.000000</td>
      <td>0.941176</td>
      <td>0.916667</td>
      <td>0.953472</td>
      <td>0.661386</td>
      <td>77.728285</td>
      <td>85.714286</td>
      <td>76.923077</td>
      <td>42.857143</td>
      <td>86.484594</td>
      <td>570500.0</td>
      <td>2780</td>
      <td>4.472701</td>
    </tr>
    <tr>
      <th>강동구</th>
      <td>0.155620</td>
      <td>0.058824</td>
      <td>0.166667</td>
      <td>0.445775</td>
      <td>0.289667</td>
      <td>78.846154</td>
      <td>100.000000</td>
      <td>75.000000</td>
      <td>33.347422</td>
      <td>82.890855</td>
      <td>453233.0</td>
      <td>773</td>
      <td>1.116551</td>
    </tr>
    <tr>
      <th>강북구</th>
      <td>0.146974</td>
      <td>0.529412</td>
      <td>0.416667</td>
      <td>0.126924</td>
      <td>0.274769</td>
      <td>82.352941</td>
      <td>92.857143</td>
      <td>100.000000</td>
      <td>43.096234</td>
      <td>88.637222</td>
      <td>330192.0</td>
      <td>748</td>
      <td>1.494746</td>
    </tr>
    <tr>
      <th>관악구</th>
      <td>0.628242</td>
      <td>0.411765</td>
      <td>0.583333</td>
      <td>0.562094</td>
      <td>0.428234</td>
      <td>69.062500</td>
      <td>100.000000</td>
      <td>88.888889</td>
      <td>30.561715</td>
      <td>80.109157</td>
      <td>525515.0</td>
      <td>1496</td>
      <td>2.613667</td>
    </tr>
    <tr>
      <th>광진구</th>
      <td>0.397695</td>
      <td>0.529412</td>
      <td>0.166667</td>
      <td>0.671570</td>
      <td>0.269094</td>
      <td>91.666667</td>
      <td>100.000000</td>
      <td>100.000000</td>
      <td>42.200925</td>
      <td>83.047619</td>
      <td>372164.0</td>
      <td>707</td>
      <td>2.034438</td>
    </tr>
  </tbody>
</table>
</div>




```python
col = ['강간검거율','강도검거율','살인검거율','절도검거율','폭력검거율']
crime_anal_norm['검거'] = np.sum(crime_anal_norm[col], axis=1)
crime_anal_norm.head()
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
      <th>강간</th>
      <th>강도</th>
      <th>살인</th>
      <th>절도</th>
      <th>폭력</th>
      <th>강간검거율</th>
      <th>강도검거율</th>
      <th>살인검거율</th>
      <th>절도검거율</th>
      <th>폭력검거율</th>
      <th>인구수</th>
      <th>CCTV</th>
      <th>범죄</th>
      <th>검거</th>
    </tr>
    <tr>
      <th>구별</th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
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
      <th>강남구</th>
      <td>1.000000</td>
      <td>0.941176</td>
      <td>0.916667</td>
      <td>0.953472</td>
      <td>0.661386</td>
      <td>77.728285</td>
      <td>85.714286</td>
      <td>76.923077</td>
      <td>42.857143</td>
      <td>86.484594</td>
      <td>570500.0</td>
      <td>2780</td>
      <td>4.472701</td>
      <td>369.707384</td>
    </tr>
    <tr>
      <th>강동구</th>
      <td>0.155620</td>
      <td>0.058824</td>
      <td>0.166667</td>
      <td>0.445775</td>
      <td>0.289667</td>
      <td>78.846154</td>
      <td>100.000000</td>
      <td>75.000000</td>
      <td>33.347422</td>
      <td>82.890855</td>
      <td>453233.0</td>
      <td>773</td>
      <td>1.116551</td>
      <td>370.084431</td>
    </tr>
    <tr>
      <th>강북구</th>
      <td>0.146974</td>
      <td>0.529412</td>
      <td>0.416667</td>
      <td>0.126924</td>
      <td>0.274769</td>
      <td>82.352941</td>
      <td>92.857143</td>
      <td>100.000000</td>
      <td>43.096234</td>
      <td>88.637222</td>
      <td>330192.0</td>
      <td>748</td>
      <td>1.494746</td>
      <td>406.943540</td>
    </tr>
    <tr>
      <th>관악구</th>
      <td>0.628242</td>
      <td>0.411765</td>
      <td>0.583333</td>
      <td>0.562094</td>
      <td>0.428234</td>
      <td>69.062500</td>
      <td>100.000000</td>
      <td>88.888889</td>
      <td>30.561715</td>
      <td>80.109157</td>
      <td>525515.0</td>
      <td>1496</td>
      <td>2.613667</td>
      <td>368.622261</td>
    </tr>
    <tr>
      <th>광진구</th>
      <td>0.397695</td>
      <td>0.529412</td>
      <td>0.166667</td>
      <td>0.671570</td>
      <td>0.269094</td>
      <td>91.666667</td>
      <td>100.000000</td>
      <td>100.000000</td>
      <td>42.200925</td>
      <td>83.047619</td>
      <td>372164.0</td>
      <td>707</td>
      <td>2.034438</td>
      <td>416.915211</td>
    </tr>
  </tbody>
</table>
</div>




```python
crime_anal_norm
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
      <th>강간</th>
      <th>강도</th>
      <th>살인</th>
      <th>절도</th>
      <th>폭력</th>
      <th>강간검거율</th>
      <th>강도검거율</th>
      <th>살인검거율</th>
      <th>절도검거율</th>
      <th>폭력검거율</th>
      <th>인구수</th>
      <th>CCTV</th>
      <th>범죄</th>
      <th>검거</th>
    </tr>
    <tr>
      <th>구별</th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
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
      <th>강남구</th>
      <td>1.000000</td>
      <td>0.941176</td>
      <td>0.916667</td>
      <td>0.953472</td>
      <td>0.661386</td>
      <td>77.728285</td>
      <td>85.714286</td>
      <td>76.923077</td>
      <td>42.857143</td>
      <td>86.484594</td>
      <td>570500.0</td>
      <td>2780</td>
      <td>4.472701</td>
      <td>369.707384</td>
    </tr>
    <tr>
      <th>강동구</th>
      <td>0.155620</td>
      <td>0.058824</td>
      <td>0.166667</td>
      <td>0.445775</td>
      <td>0.289667</td>
      <td>78.846154</td>
      <td>100.000000</td>
      <td>75.000000</td>
      <td>33.347422</td>
      <td>82.890855</td>
      <td>453233.0</td>
      <td>773</td>
      <td>1.116551</td>
      <td>370.084431</td>
    </tr>
    <tr>
      <th>강북구</th>
      <td>0.146974</td>
      <td>0.529412</td>
      <td>0.416667</td>
      <td>0.126924</td>
      <td>0.274769</td>
      <td>82.352941</td>
      <td>92.857143</td>
      <td>100.000000</td>
      <td>43.096234</td>
      <td>88.637222</td>
      <td>330192.0</td>
      <td>748</td>
      <td>1.494746</td>
      <td>406.943540</td>
    </tr>
    <tr>
      <th>관악구</th>
      <td>0.628242</td>
      <td>0.411765</td>
      <td>0.583333</td>
      <td>0.562094</td>
      <td>0.428234</td>
      <td>69.062500</td>
      <td>100.000000</td>
      <td>88.888889</td>
      <td>30.561715</td>
      <td>80.109157</td>
      <td>525515.0</td>
      <td>1496</td>
      <td>2.613667</td>
      <td>368.622261</td>
    </tr>
    <tr>
      <th>광진구</th>
      <td>0.397695</td>
      <td>0.529412</td>
      <td>0.166667</td>
      <td>0.671570</td>
      <td>0.269094</td>
      <td>91.666667</td>
      <td>100.000000</td>
      <td>100.000000</td>
      <td>42.200925</td>
      <td>83.047619</td>
      <td>372164.0</td>
      <td>707</td>
      <td>2.034438</td>
      <td>416.915211</td>
    </tr>
    <tr>
      <th>구로구</th>
      <td>0.515850</td>
      <td>0.588235</td>
      <td>0.500000</td>
      <td>0.435169</td>
      <td>0.359423</td>
      <td>58.362989</td>
      <td>73.333333</td>
      <td>75.000000</td>
      <td>38.072805</td>
      <td>80.877951</td>
      <td>447874.0</td>
      <td>1561</td>
      <td>2.398678</td>
      <td>325.647079</td>
    </tr>
    <tr>
      <th>금천구</th>
      <td>0.141210</td>
      <td>0.058824</td>
      <td>0.083333</td>
      <td>0.172426</td>
      <td>0.134074</td>
      <td>80.794702</td>
      <td>100.000000</td>
      <td>100.000000</td>
      <td>56.668794</td>
      <td>86.465433</td>
      <td>255082.0</td>
      <td>1015</td>
      <td>0.589867</td>
      <td>423.928929</td>
    </tr>
    <tr>
      <th>노원구</th>
      <td>0.273775</td>
      <td>0.117647</td>
      <td>0.666667</td>
      <td>0.386589</td>
      <td>0.292268</td>
      <td>61.421320</td>
      <td>100.000000</td>
      <td>100.000000</td>
      <td>36.525308</td>
      <td>85.530665</td>
      <td>569384.0</td>
      <td>1265</td>
      <td>1.736946</td>
      <td>383.477292</td>
    </tr>
    <tr>
      <th>도봉구</th>
      <td>0.000000</td>
      <td>0.235294</td>
      <td>0.083333</td>
      <td>0.000000</td>
      <td>0.000000</td>
      <td>100.000000</td>
      <td>100.000000</td>
      <td>100.000000</td>
      <td>44.967074</td>
      <td>87.626093</td>
      <td>348646.0</td>
      <td>485</td>
      <td>0.318627</td>
      <td>432.593167</td>
    </tr>
    <tr>
      <th>동대문구</th>
      <td>0.204611</td>
      <td>0.470588</td>
      <td>0.250000</td>
      <td>0.314061</td>
      <td>0.250887</td>
      <td>84.393064</td>
      <td>100.000000</td>
      <td>100.000000</td>
      <td>41.090358</td>
      <td>87.401884</td>
      <td>369496.0</td>
      <td>1294</td>
      <td>1.490147</td>
      <td>412.885306</td>
    </tr>
    <tr>
      <th>동작구</th>
      <td>0.527378</td>
      <td>0.235294</td>
      <td>0.250000</td>
      <td>0.274376</td>
      <td>0.100024</td>
      <td>48.771930</td>
      <td>55.555556</td>
      <td>100.000000</td>
      <td>35.442359</td>
      <td>83.089005</td>
      <td>412520.0</td>
      <td>1091</td>
      <td>1.387071</td>
      <td>322.858850</td>
    </tr>
    <tr>
      <th>마포구</th>
      <td>0.553314</td>
      <td>0.529412</td>
      <td>0.500000</td>
      <td>0.510434</td>
      <td>0.353748</td>
      <td>84.013605</td>
      <td>71.428571</td>
      <td>100.000000</td>
      <td>31.819961</td>
      <td>84.445189</td>
      <td>389649.0</td>
      <td>574</td>
      <td>2.446908</td>
      <td>371.707327</td>
    </tr>
    <tr>
      <th>서대문구</th>
      <td>0.149856</td>
      <td>0.000000</td>
      <td>0.000000</td>
      <td>0.256244</td>
      <td>0.134547</td>
      <td>80.519481</td>
      <td>80.000000</td>
      <td>100.000000</td>
      <td>40.728477</td>
      <td>83.219844</td>
      <td>327163.0</td>
      <td>962</td>
      <td>0.540647</td>
      <td>384.467802</td>
    </tr>
    <tr>
      <th>서초구</th>
      <td>0.838617</td>
      <td>0.235294</td>
      <td>0.500000</td>
      <td>0.537804</td>
      <td>0.215654</td>
      <td>63.358779</td>
      <td>66.666667</td>
      <td>75.000000</td>
      <td>41.404175</td>
      <td>87.453105</td>
      <td>450310.0</td>
      <td>1930</td>
      <td>2.327368</td>
      <td>333.882725</td>
    </tr>
    <tr>
      <th>성동구</th>
      <td>0.069164</td>
      <td>0.235294</td>
      <td>0.166667</td>
      <td>0.186110</td>
      <td>0.029558</td>
      <td>94.444444</td>
      <td>88.888889</td>
      <td>100.000000</td>
      <td>37.149969</td>
      <td>86.538462</td>
      <td>311244.0</td>
      <td>1062</td>
      <td>0.686793</td>
      <td>407.021764</td>
    </tr>
    <tr>
      <th>성북구</th>
      <td>0.138329</td>
      <td>0.000000</td>
      <td>0.250000</td>
      <td>0.247007</td>
      <td>0.170726</td>
      <td>82.666667</td>
      <td>80.000000</td>
      <td>100.000000</td>
      <td>41.512605</td>
      <td>83.974649</td>
      <td>461260.0</td>
      <td>1464</td>
      <td>0.806061</td>
      <td>388.153921</td>
    </tr>
    <tr>
      <th>송파구</th>
      <td>0.340058</td>
      <td>0.470588</td>
      <td>0.750000</td>
      <td>0.744441</td>
      <td>0.427524</td>
      <td>80.909091</td>
      <td>76.923077</td>
      <td>90.909091</td>
      <td>34.856437</td>
      <td>84.552352</td>
      <td>667483.0</td>
      <td>618</td>
      <td>2.732611</td>
      <td>368.150048</td>
    </tr>
    <tr>
      <th>양천구</th>
      <td>0.806916</td>
      <td>0.823529</td>
      <td>0.666667</td>
      <td>1.000000</td>
      <td>1.000000</td>
      <td>77.486911</td>
      <td>84.210526</td>
      <td>100.000000</td>
      <td>48.469644</td>
      <td>83.065080</td>
      <td>479978.0</td>
      <td>2034</td>
      <td>4.297113</td>
      <td>393.232162</td>
    </tr>
    <tr>
      <th>영등포구</th>
      <td>0.556196</td>
      <td>1.000000</td>
      <td>1.000000</td>
      <td>0.650359</td>
      <td>0.493024</td>
      <td>62.033898</td>
      <td>90.909091</td>
      <td>85.714286</td>
      <td>32.995951</td>
      <td>82.894737</td>
      <td>402985.0</td>
      <td>904</td>
      <td>3.699580</td>
      <td>354.547963</td>
    </tr>
    <tr>
      <th>용산구</th>
      <td>0.265130</td>
      <td>0.529412</td>
      <td>0.250000</td>
      <td>0.169004</td>
      <td>0.133128</td>
      <td>89.175258</td>
      <td>100.000000</td>
      <td>100.000000</td>
      <td>37.700706</td>
      <td>83.121951</td>
      <td>244203.0</td>
      <td>1624</td>
      <td>1.346674</td>
      <td>409.997915</td>
    </tr>
    <tr>
      <th>은평구</th>
      <td>0.184438</td>
      <td>0.235294</td>
      <td>0.083333</td>
      <td>0.291139</td>
      <td>0.275715</td>
      <td>84.939759</td>
      <td>66.666667</td>
      <td>100.000000</td>
      <td>37.147335</td>
      <td>86.920467</td>
      <td>494388.0</td>
      <td>1873</td>
      <td>1.069920</td>
      <td>375.674229</td>
    </tr>
    <tr>
      <th>종로구</th>
      <td>0.314121</td>
      <td>0.352941</td>
      <td>0.333333</td>
      <td>0.383510</td>
      <td>0.190589</td>
      <td>76.303318</td>
      <td>81.818182</td>
      <td>83.333333</td>
      <td>38.324176</td>
      <td>84.212822</td>
      <td>162820.0</td>
      <td>1002</td>
      <td>1.574494</td>
      <td>363.991830</td>
    </tr>
    <tr>
      <th>중구</th>
      <td>0.195965</td>
      <td>0.235294</td>
      <td>0.083333</td>
      <td>0.508040</td>
      <td>0.174273</td>
      <td>65.294118</td>
      <td>66.666667</td>
      <td>66.666667</td>
      <td>33.712716</td>
      <td>88.309353</td>
      <td>133240.0</td>
      <td>671</td>
      <td>1.196905</td>
      <td>320.649519</td>
    </tr>
    <tr>
      <th>중랑구</th>
      <td>0.244957</td>
      <td>0.352941</td>
      <td>0.916667</td>
      <td>0.366746</td>
      <td>0.321589</td>
      <td>79.144385</td>
      <td>81.818182</td>
      <td>92.307692</td>
      <td>38.829040</td>
      <td>84.545135</td>
      <td>414503.0</td>
      <td>660</td>
      <td>2.202900</td>
      <td>376.644434</td>
    </tr>
  </tbody>
</table>
</div>



# seaborn


```python
import matplotlib.pyplot as plt
%matplotlib inline

import seaborn as sns

x = np.linspace(0, 14, 100)
y1 = np.sin(x)
y2 = 2*np.sin(x+0.5)
y3 = 3*np.sin(x+1.0)
y4 = 4*np.sin(x+1.5)

plt.figure(figsize=(10,6))
plt.plot(x,y1, x,y2, x,y3, x,y4)
plt.show()
```


![png](output_44_0.png)



```python
sns.set_style("white")

plt.figure(figsize=(10,6))
plt.plot(x,y1, x,y2, x,y3, x,y4)

sns.despine()

plt.show()
```


![png](output_45_0.png)



```python
sns.set_style("dark")

plt.figure(figsize=(10,6))
plt.plot(x,y1, x,y2, x,y3, x,y4)
plt.show()
```


![png](output_46_0.png)



```python
sns.set_style("whitegrid")

plt.figure(figsize=(10,6))
plt.plot(x,y1, x,y2, x,y3, x,y4)
plt.show()
```


![png](output_47_0.png)



```python
plt.figure(figsize=(10,6))
plt.plot(x,y1, x,y2, x,y3, x,y4)

sns.despine(offset=10)

plt.show()
```


![png](output_48_0.png)



```python
import matplotlib.pyplot as plt
import numpy as np
import seaborn as sns

sns.set_style("whitegrid")
%matplotlib inline
```


```python
tips = sns.load_dataset("tips")
tips.head(5)
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
      <th>total_bill</th>
      <th>tip</th>
      <th>sex</th>
      <th>smoker</th>
      <th>day</th>
      <th>time</th>
      <th>size</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>16.99</td>
      <td>1.01</td>
      <td>Female</td>
      <td>No</td>
      <td>Sun</td>
      <td>Dinner</td>
      <td>2</td>
    </tr>
    <tr>
      <th>1</th>
      <td>10.34</td>
      <td>1.66</td>
      <td>Male</td>
      <td>No</td>
      <td>Sun</td>
      <td>Dinner</td>
      <td>3</td>
    </tr>
    <tr>
      <th>2</th>
      <td>21.01</td>
      <td>3.50</td>
      <td>Male</td>
      <td>No</td>
      <td>Sun</td>
      <td>Dinner</td>
      <td>3</td>
    </tr>
    <tr>
      <th>3</th>
      <td>23.68</td>
      <td>3.31</td>
      <td>Male</td>
      <td>No</td>
      <td>Sun</td>
      <td>Dinner</td>
      <td>2</td>
    </tr>
    <tr>
      <th>4</th>
      <td>24.59</td>
      <td>3.61</td>
      <td>Female</td>
      <td>No</td>
      <td>Sun</td>
      <td>Dinner</td>
      <td>4</td>
    </tr>
  </tbody>
</table>
</div>




```python
sns.set_style("whitegrid")

plt.figure(figsize=(8,6))
sns.boxplot(x=tips["total_bill"])
plt.show()
```


![png](output_51_0.png)



```python
plt.figure(figsize=(8,6))
sns.boxplot(x="day", y="total_bill", data=tips)
plt.show()
```


![png](output_52_0.png)



```python
plt.figure(figsize=(8,6))
sns.boxplot(x="day", y="total_bill", hue="smoker", data=tips, palette="Set3")
plt.show()
```


![png](output_53_0.png)



```python
plt.figure(figsize=(8,6))
sns.swarmplot(x="day", y="total_bill", data=tips, color=".5")
plt.show()
```


![png](output_54_0.png)



```python
plt.figure(figsize=(8,6))
sns.boxplot(x="day", y="total_bill", data=tips)
sns.swarmplot(x="day", y="total_bill", data=tips, color=".25")
plt.show()
```


![png](output_55_0.png)



```python
import matplotlib.pyplot as plt
import seaborn as sns
%matplotlib inline

tips = sns.load_dataset("tips")
tips.head(5)
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
      <th>total_bill</th>
      <th>tip</th>
      <th>sex</th>
      <th>smoker</th>
      <th>day</th>
      <th>time</th>
      <th>size</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>16.99</td>
      <td>1.01</td>
      <td>Female</td>
      <td>No</td>
      <td>Sun</td>
      <td>Dinner</td>
      <td>2</td>
    </tr>
    <tr>
      <th>1</th>
      <td>10.34</td>
      <td>1.66</td>
      <td>Male</td>
      <td>No</td>
      <td>Sun</td>
      <td>Dinner</td>
      <td>3</td>
    </tr>
    <tr>
      <th>2</th>
      <td>21.01</td>
      <td>3.50</td>
      <td>Male</td>
      <td>No</td>
      <td>Sun</td>
      <td>Dinner</td>
      <td>3</td>
    </tr>
    <tr>
      <th>3</th>
      <td>23.68</td>
      <td>3.31</td>
      <td>Male</td>
      <td>No</td>
      <td>Sun</td>
      <td>Dinner</td>
      <td>2</td>
    </tr>
    <tr>
      <th>4</th>
      <td>24.59</td>
      <td>3.61</td>
      <td>Female</td>
      <td>No</td>
      <td>Sun</td>
      <td>Dinner</td>
      <td>4</td>
    </tr>
  </tbody>
</table>
</div>




```python
sns.set_style("darkgrid")
sns.lmplot(x="total_bill", y="tip", data=tips, size=7)
plt.show()
```

    C:\ProgramData\Anaconda3\lib\site-packages\seaborn\regression.py:546: UserWarning: The `size` paramter has been renamed to `height`; please update your code.
      warnings.warn(msg, UserWarning)
    C:\ProgramData\Anaconda3\lib\site-packages\scipy\stats\stats.py:1713: FutureWarning: Using a non-tuple sequence for multidimensional indexing is deprecated; use `arr[tuple(seq)]` instead of `arr[seq]`. In the future this will be interpreted as an array index, `arr[np.array(seq)]`, which will result either in an error or a different result.
      return np.add.reduce(sorted[indexer] * weights, axis=axis) / sumval
    


![png](output_57_1.png)



```python
sns.lmplot(x="total_bill", y="tip", hue="smoker", data=tips, size=7)
plt.show()
```

    C:\ProgramData\Anaconda3\lib\site-packages\seaborn\regression.py:546: UserWarning: The `size` paramter has been renamed to `height`; please update your code.
      warnings.warn(msg, UserWarning)
    C:\ProgramData\Anaconda3\lib\site-packages\scipy\stats\stats.py:1713: FutureWarning: Using a non-tuple sequence for multidimensional indexing is deprecated; use `arr[tuple(seq)]` instead of `arr[seq]`. In the future this will be interpreted as an array index, `arr[np.array(seq)]`, which will result either in an error or a different result.
      return np.add.reduce(sorted[indexer] * weights, axis=axis) / sumval
    


![png](output_58_1.png)



```python
sns.lmplot(x="total_bill", y="tip", hue="smoker", data=tips, palette="Set1", size=7)
plt.show()
```

    C:\ProgramData\Anaconda3\lib\site-packages\seaborn\regression.py:546: UserWarning: The `size` paramter has been renamed to `height`; please update your code.
      warnings.warn(msg, UserWarning)
    C:\ProgramData\Anaconda3\lib\site-packages\scipy\stats\stats.py:1713: FutureWarning: Using a non-tuple sequence for multidimensional indexing is deprecated; use `arr[tuple(seq)]` instead of `arr[seq]`. In the future this will be interpreted as an array index, `arr[np.array(seq)]`, which will result either in an error or a different result.
      return np.add.reduce(sorted[indexer] * weights, axis=axis) / sumval
    


![png](output_59_1.png)



```python
uniform_data = np.random.rand(10, 12)
uniform_data
```




    array([[0.53331489, 0.44745443, 0.89418159, 0.23564992, 0.79823628,
            0.74843718, 0.81212166, 0.35700139, 0.67061374, 0.12617332,
            0.32158688, 0.59386202],
           [0.06230694, 0.00880028, 0.01291525, 0.97048984, 0.28271372,
            0.80579476, 0.62673657, 0.85773911, 0.29975113, 0.35959475,
            0.14404762, 0.62342522],
           [0.75282011, 0.11339008, 0.37582975, 0.84045592, 0.56852731,
            0.16362408, 0.51086988, 0.70279749, 0.74060518, 0.08370579,
            0.66003177, 0.56383343],
           [0.84760977, 0.89728492, 0.09597355, 0.04653329, 0.10606645,
            0.47326474, 0.32151219, 0.22032226, 0.4004861 , 0.13598373,
            0.71596228, 0.54618009],
           [0.19409168, 0.14092995, 0.80070254, 0.72948141, 0.14947089,
            0.32743687, 0.55375557, 0.14828551, 0.91614094, 0.26149153,
            0.28293536, 0.46140877],
           [0.23750588, 0.01823421, 0.5057017 , 0.00443343, 0.7077155 ,
            0.34181305, 0.91321385, 0.57775751, 0.56708849, 0.89596035,
            0.87934389, 0.80138761],
           [0.12309513, 0.8291798 , 0.0271375 , 0.81260434, 0.62339342,
            0.18312912, 0.83256504, 0.04149679, 0.0052803 , 0.90048171,
            0.38275537, 0.41754459],
           [0.61572165, 0.27760897, 0.57835024, 0.50237474, 0.2336374 ,
            0.15172152, 0.7068441 , 0.88704739, 0.39547658, 0.74380174,
            0.21837076, 0.8675885 ],
           [0.63830282, 0.21415767, 0.40963589, 0.13453167, 0.09384878,
            0.15813118, 0.54664556, 0.89518736, 0.04316569, 0.20142448,
            0.32251027, 0.15514833],
           [0.57447908, 0.7510854 , 0.0659655 , 0.99032925, 0.12030522,
            0.24268336, 0.72527318, 0.21612635, 0.91196264, 0.01749622,
            0.17801757, 0.00100838]])




```python
sns.heatmap(uniform_data)
plt.show()
```


![png](output_61_0.png)



```python
sns.heatmap(uniform_data, vmin=0, vmax=1)
plt.show()
```


![png](output_62_0.png)



```python
flights = sns.load_dataset("flights")
flights.head(5)
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
      <th>year</th>
      <th>month</th>
      <th>passengers</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>1949</td>
      <td>January</td>
      <td>112</td>
    </tr>
    <tr>
      <th>1</th>
      <td>1949</td>
      <td>February</td>
      <td>118</td>
    </tr>
    <tr>
      <th>2</th>
      <td>1949</td>
      <td>March</td>
      <td>132</td>
    </tr>
    <tr>
      <th>3</th>
      <td>1949</td>
      <td>April</td>
      <td>129</td>
    </tr>
    <tr>
      <th>4</th>
      <td>1949</td>
      <td>May</td>
      <td>121</td>
    </tr>
  </tbody>
</table>
</div>




```python
flights = flights.pivot("month", "year", "passengers")
flights.head(5)
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
      <th>year</th>
      <th>1949</th>
      <th>1950</th>
      <th>1951</th>
      <th>1952</th>
      <th>1953</th>
      <th>1954</th>
      <th>1955</th>
      <th>1956</th>
      <th>1957</th>
      <th>1958</th>
      <th>1959</th>
      <th>1960</th>
    </tr>
    <tr>
      <th>month</th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
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
      <th>January</th>
      <td>112</td>
      <td>115</td>
      <td>145</td>
      <td>171</td>
      <td>196</td>
      <td>204</td>
      <td>242</td>
      <td>284</td>
      <td>315</td>
      <td>340</td>
      <td>360</td>
      <td>417</td>
    </tr>
    <tr>
      <th>February</th>
      <td>118</td>
      <td>126</td>
      <td>150</td>
      <td>180</td>
      <td>196</td>
      <td>188</td>
      <td>233</td>
      <td>277</td>
      <td>301</td>
      <td>318</td>
      <td>342</td>
      <td>391</td>
    </tr>
    <tr>
      <th>March</th>
      <td>132</td>
      <td>141</td>
      <td>178</td>
      <td>193</td>
      <td>236</td>
      <td>235</td>
      <td>267</td>
      <td>317</td>
      <td>356</td>
      <td>362</td>
      <td>406</td>
      <td>419</td>
    </tr>
    <tr>
      <th>April</th>
      <td>129</td>
      <td>135</td>
      <td>163</td>
      <td>181</td>
      <td>235</td>
      <td>227</td>
      <td>269</td>
      <td>313</td>
      <td>348</td>
      <td>348</td>
      <td>396</td>
      <td>461</td>
    </tr>
    <tr>
      <th>May</th>
      <td>121</td>
      <td>125</td>
      <td>172</td>
      <td>183</td>
      <td>229</td>
      <td>234</td>
      <td>270</td>
      <td>318</td>
      <td>355</td>
      <td>363</td>
      <td>420</td>
      <td>472</td>
    </tr>
  </tbody>
</table>
</div>




```python
plt.figure(figsize=(10,8))
sns.heatmap(flights)
plt.show()
```


![png](output_65_0.png)



```python
plt.figure(figsize=(10,8))
sns.heatmap(flights, annot=True, fmt="d")
plt.show()
```


![png](output_66_0.png)



```python
sns.set(style="ticks")
iris = sns.load_dataset("iris")
iris.head(10)
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
      <th>sepal_length</th>
      <th>sepal_width</th>
      <th>petal_length</th>
      <th>petal_width</th>
      <th>species</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>5.1</td>
      <td>3.5</td>
      <td>1.4</td>
      <td>0.2</td>
      <td>setosa</td>
    </tr>
    <tr>
      <th>1</th>
      <td>4.9</td>
      <td>3.0</td>
      <td>1.4</td>
      <td>0.2</td>
      <td>setosa</td>
    </tr>
    <tr>
      <th>2</th>
      <td>4.7</td>
      <td>3.2</td>
      <td>1.3</td>
      <td>0.2</td>
      <td>setosa</td>
    </tr>
    <tr>
      <th>3</th>
      <td>4.6</td>
      <td>3.1</td>
      <td>1.5</td>
      <td>0.2</td>
      <td>setosa</td>
    </tr>
    <tr>
      <th>4</th>
      <td>5.0</td>
      <td>3.6</td>
      <td>1.4</td>
      <td>0.2</td>
      <td>setosa</td>
    </tr>
    <tr>
      <th>5</th>
      <td>5.4</td>
      <td>3.9</td>
      <td>1.7</td>
      <td>0.4</td>
      <td>setosa</td>
    </tr>
    <tr>
      <th>6</th>
      <td>4.6</td>
      <td>3.4</td>
      <td>1.4</td>
      <td>0.3</td>
      <td>setosa</td>
    </tr>
    <tr>
      <th>7</th>
      <td>5.0</td>
      <td>3.4</td>
      <td>1.5</td>
      <td>0.2</td>
      <td>setosa</td>
    </tr>
    <tr>
      <th>8</th>
      <td>4.4</td>
      <td>2.9</td>
      <td>1.4</td>
      <td>0.2</td>
      <td>setosa</td>
    </tr>
    <tr>
      <th>9</th>
      <td>4.9</td>
      <td>3.1</td>
      <td>1.5</td>
      <td>0.1</td>
      <td>setosa</td>
    </tr>
  </tbody>
</table>
</div>




```python
sns.pairplot(iris)
plt.show()
```


![png](output_68_0.png)



```python
sns.pairplot(iris, hue="species")
plt.show()
```

    C:\ProgramData\Anaconda3\lib\site-packages\scipy\stats\stats.py:1713: FutureWarning: Using a non-tuple sequence for multidimensional indexing is deprecated; use `arr[tuple(seq)]` instead of `arr[seq]`. In the future this will be interpreted as an array index, `arr[np.array(seq)]`, which will result either in an error or a different result.
      return np.add.reduce(sorted[indexer] * weights, axis=axis) / sumval
    


![png](output_69_1.png)



```python
sns.pairplot(iris, vars=["sepal_width", "sepal_length"])
plt.show()
```


![png](output_70_0.png)



```python
sns.pairplot(iris, x_vars=["sepal_width", "sepal_length"], 
             y_vars=["petal_width", "petal_length"])
plt.show()
```


![png](output_71_0.png)



```python
anscombe = sns.load_dataset("anscombe")
anscombe.head(5)
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
      <th>dataset</th>
      <th>x</th>
      <th>y</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>I</td>
      <td>10.0</td>
      <td>8.04</td>
    </tr>
    <tr>
      <th>1</th>
      <td>I</td>
      <td>8.0</td>
      <td>6.95</td>
    </tr>
    <tr>
      <th>2</th>
      <td>I</td>
      <td>13.0</td>
      <td>7.58</td>
    </tr>
    <tr>
      <th>3</th>
      <td>I</td>
      <td>9.0</td>
      <td>8.81</td>
    </tr>
    <tr>
      <th>4</th>
      <td>I</td>
      <td>11.0</td>
      <td>8.33</td>
    </tr>
  </tbody>
</table>
</div>




```python
sns.set_style("darkgrid")

sns.lmplot(x="x", y="y", data=anscombe.query("dataset == 'I'"),  ci=None, size=7)
plt.show()
```

    C:\ProgramData\Anaconda3\lib\site-packages\seaborn\regression.py:546: UserWarning: The `size` paramter has been renamed to `height`; please update your code.
      warnings.warn(msg, UserWarning)
    


![png](output_73_1.png)



```python
sns.lmplot(x="x", y="y", data=anscombe.query("dataset == 'I'"),
           ci=None, scatter_kws={"s": 80}, size=7)
plt.show()
```

    C:\ProgramData\Anaconda3\lib\site-packages\seaborn\regression.py:546: UserWarning: The `size` paramter has been renamed to `height`; please update your code.
      warnings.warn(msg, UserWarning)
    


![png](output_74_1.png)



```python
sns.lmplot(x="x", y="y", data=anscombe.query("dataset == 'II'"),
           order=1, ci=None, scatter_kws={"s": 80}, size=7)
plt.show()
```

    C:\ProgramData\Anaconda3\lib\site-packages\seaborn\regression.py:546: UserWarning: The `size` paramter has been renamed to `height`; please update your code.
      warnings.warn(msg, UserWarning)
    


![png](output_75_1.png)



```python
sns.lmplot(x="x", y="y", data=anscombe.query("dataset == 'II'"),
           order=2, ci=None, scatter_kws={"s": 80}, size=7)
plt.show()
```

    C:\ProgramData\Anaconda3\lib\site-packages\seaborn\regression.py:546: UserWarning: The `size` paramter has been renamed to `height`; please update your code.
      warnings.warn(msg, UserWarning)
    


![png](output_76_1.png)



```python
sns.lmplot(x="x", y="y", data=anscombe.query("dataset == 'III'"),
           ci=None, scatter_kws={"s": 80}, size=7)
plt.show()
```

    C:\ProgramData\Anaconda3\lib\site-packages\seaborn\regression.py:546: UserWarning: The `size` paramter has been renamed to `height`; please update your code.
      warnings.warn(msg, UserWarning)
    


![png](output_77_1.png)



```python
sns.lmplot(x="x", y="y", data=anscombe.query("dataset == 'III'"),
           robust=True, ci=None, scatter_kws={"s": 80}, size=7)
plt.show()
```

    C:\ProgramData\Anaconda3\lib\site-packages\seaborn\regression.py:546: UserWarning: The `size` paramter has been renamed to `height`; please update your code.
      warnings.warn(msg, UserWarning)
    


![png](output_78_1.png)


# Visualization using seaborn


```python
import matplotlib.pyplot as plt
import seaborn as sns

%matplotlib inline

import platform

path = "c:/Windows/Fonts/malgun.ttf"
from matplotlib import font_manager, rc
if platform.system() == 'Darwin':
    rc('font', family='AppleGothic')
elif platform.system() == 'Windows':
    font_name = font_manager.FontProperties(fname=path).get_name()
    rc('font', family=font_name)
else:
    print('Unknown system... sorry~~~~') 
```


```python
crime_anal_norm.head()
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
      <th>강간</th>
      <th>강도</th>
      <th>살인</th>
      <th>절도</th>
      <th>폭력</th>
      <th>강간검거율</th>
      <th>강도검거율</th>
      <th>살인검거율</th>
      <th>절도검거율</th>
      <th>폭력검거율</th>
      <th>인구수</th>
      <th>CCTV</th>
      <th>범죄</th>
      <th>검거</th>
    </tr>
    <tr>
      <th>구별</th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
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
      <th>강남구</th>
      <td>1.000000</td>
      <td>0.941176</td>
      <td>0.916667</td>
      <td>0.953472</td>
      <td>0.661386</td>
      <td>77.728285</td>
      <td>85.714286</td>
      <td>76.923077</td>
      <td>42.857143</td>
      <td>86.484594</td>
      <td>570500.0</td>
      <td>2780</td>
      <td>4.472701</td>
      <td>369.707384</td>
    </tr>
    <tr>
      <th>강동구</th>
      <td>0.155620</td>
      <td>0.058824</td>
      <td>0.166667</td>
      <td>0.445775</td>
      <td>0.289667</td>
      <td>78.846154</td>
      <td>100.000000</td>
      <td>75.000000</td>
      <td>33.347422</td>
      <td>82.890855</td>
      <td>453233.0</td>
      <td>773</td>
      <td>1.116551</td>
      <td>370.084431</td>
    </tr>
    <tr>
      <th>강북구</th>
      <td>0.146974</td>
      <td>0.529412</td>
      <td>0.416667</td>
      <td>0.126924</td>
      <td>0.274769</td>
      <td>82.352941</td>
      <td>92.857143</td>
      <td>100.000000</td>
      <td>43.096234</td>
      <td>88.637222</td>
      <td>330192.0</td>
      <td>748</td>
      <td>1.494746</td>
      <td>406.943540</td>
    </tr>
    <tr>
      <th>관악구</th>
      <td>0.628242</td>
      <td>0.411765</td>
      <td>0.583333</td>
      <td>0.562094</td>
      <td>0.428234</td>
      <td>69.062500</td>
      <td>100.000000</td>
      <td>88.888889</td>
      <td>30.561715</td>
      <td>80.109157</td>
      <td>525515.0</td>
      <td>1496</td>
      <td>2.613667</td>
      <td>368.622261</td>
    </tr>
    <tr>
      <th>광진구</th>
      <td>0.397695</td>
      <td>0.529412</td>
      <td>0.166667</td>
      <td>0.671570</td>
      <td>0.269094</td>
      <td>91.666667</td>
      <td>100.000000</td>
      <td>100.000000</td>
      <td>42.200925</td>
      <td>83.047619</td>
      <td>372164.0</td>
      <td>707</td>
      <td>2.034438</td>
      <td>416.915211</td>
    </tr>
  </tbody>
</table>
</div>




```python
sns.pairplot(crime_anal_norm, vars=["강도", "살인", "폭력"], kind='reg', size=3)
plt.show()
```

    C:\ProgramData\Anaconda3\lib\site-packages\seaborn\axisgrid.py:2065: UserWarning: The `size` parameter has been renamed to `height`; pleaes update your code.
      warnings.warn(msg, UserWarning)
    C:\ProgramData\Anaconda3\lib\site-packages\scipy\stats\stats.py:1713: FutureWarning: Using a non-tuple sequence for multidimensional indexing is deprecated; use `arr[tuple(seq)]` instead of `arr[seq]`. In the future this will be interpreted as an array index, `arr[np.array(seq)]`, which will result either in an error or a different result.
      return np.add.reduce(sorted[indexer] * weights, axis=axis) / sumval
    


![png](output_82_1.png)



```python
sns.pairplot(crime_anal_norm, x_vars=["인구수", "CCTV"], 
             y_vars=["살인", "강도"], kind='reg', size=3)
plt.show()
```

    C:\ProgramData\Anaconda3\lib\site-packages\seaborn\axisgrid.py:2065: UserWarning: The `size` parameter has been renamed to `height`; pleaes update your code.
      warnings.warn(msg, UserWarning)
    C:\ProgramData\Anaconda3\lib\site-packages\scipy\stats\stats.py:1713: FutureWarning: Using a non-tuple sequence for multidimensional indexing is deprecated; use `arr[tuple(seq)]` instead of `arr[seq]`. In the future this will be interpreted as an array index, `arr[np.array(seq)]`, which will result either in an error or a different result.
      return np.add.reduce(sorted[indexer] * weights, axis=axis) / sumval
    


![png](output_83_1.png)



```python
sns.pairplot(crime_anal_norm, x_vars=["인구수", "CCTV"], 
             y_vars=["살인검거율", "폭력검거율"], kind='reg', size=3)
plt.show()
```

    C:\ProgramData\Anaconda3\lib\site-packages\seaborn\axisgrid.py:2065: UserWarning: The `size` parameter has been renamed to `height`; pleaes update your code.
      warnings.warn(msg, UserWarning)
    C:\ProgramData\Anaconda3\lib\site-packages\scipy\stats\stats.py:1713: FutureWarning: Using a non-tuple sequence for multidimensional indexing is deprecated; use `arr[tuple(seq)]` instead of `arr[seq]`. In the future this will be interpreted as an array index, `arr[np.array(seq)]`, which will result either in an error or a different result.
      return np.add.reduce(sorted[indexer] * weights, axis=axis) / sumval
    


![png](output_84_1.png)



```python
sns.pairplot(crime_anal_norm, x_vars=["인구수", "CCTV"], 
             y_vars=["절도검거율", "강도검거율"], kind='reg', size=3)
plt.show()
```

    C:\ProgramData\Anaconda3\lib\site-packages\seaborn\axisgrid.py:2065: UserWarning: The `size` parameter has been renamed to `height`; pleaes update your code.
      warnings.warn(msg, UserWarning)
    C:\ProgramData\Anaconda3\lib\site-packages\scipy\stats\stats.py:1713: FutureWarning: Using a non-tuple sequence for multidimensional indexing is deprecated; use `arr[tuple(seq)]` instead of `arr[seq]`. In the future this will be interpreted as an array index, `arr[np.array(seq)]`, which will result either in an error or a different result.
      return np.add.reduce(sorted[indexer] * weights, axis=axis) / sumval
    


![png](output_85_1.png)



```python
tmp_max = crime_anal_norm['검거'].max()
crime_anal_norm['검거'] = crime_anal_norm['검거'] / tmp_max * 100
crime_anal_norm_sort = crime_anal_norm.sort_values(by='검거', ascending=False)
crime_anal_norm_sort.head()
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
      <th>강간</th>
      <th>강도</th>
      <th>살인</th>
      <th>절도</th>
      <th>폭력</th>
      <th>강간검거율</th>
      <th>강도검거율</th>
      <th>살인검거율</th>
      <th>절도검거율</th>
      <th>폭력검거율</th>
      <th>인구수</th>
      <th>CCTV</th>
      <th>범죄</th>
      <th>검거</th>
    </tr>
    <tr>
      <th>구별</th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
      <th></th>
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
      <th>도봉구</th>
      <td>0.000000</td>
      <td>0.235294</td>
      <td>0.083333</td>
      <td>0.000000</td>
      <td>0.000000</td>
      <td>100.000000</td>
      <td>100.0</td>
      <td>100.0</td>
      <td>44.967074</td>
      <td>87.626093</td>
      <td>348646.0</td>
      <td>485</td>
      <td>0.318627</td>
      <td>100.000000</td>
    </tr>
    <tr>
      <th>금천구</th>
      <td>0.141210</td>
      <td>0.058824</td>
      <td>0.083333</td>
      <td>0.172426</td>
      <td>0.134074</td>
      <td>80.794702</td>
      <td>100.0</td>
      <td>100.0</td>
      <td>56.668794</td>
      <td>86.465433</td>
      <td>255082.0</td>
      <td>1015</td>
      <td>0.589867</td>
      <td>97.997139</td>
    </tr>
    <tr>
      <th>광진구</th>
      <td>0.397695</td>
      <td>0.529412</td>
      <td>0.166667</td>
      <td>0.671570</td>
      <td>0.269094</td>
      <td>91.666667</td>
      <td>100.0</td>
      <td>100.0</td>
      <td>42.200925</td>
      <td>83.047619</td>
      <td>372164.0</td>
      <td>707</td>
      <td>2.034438</td>
      <td>96.375820</td>
    </tr>
    <tr>
      <th>동대문구</th>
      <td>0.204611</td>
      <td>0.470588</td>
      <td>0.250000</td>
      <td>0.314061</td>
      <td>0.250887</td>
      <td>84.393064</td>
      <td>100.0</td>
      <td>100.0</td>
      <td>41.090358</td>
      <td>87.401884</td>
      <td>369496.0</td>
      <td>1294</td>
      <td>1.490147</td>
      <td>95.444250</td>
    </tr>
    <tr>
      <th>용산구</th>
      <td>0.265130</td>
      <td>0.529412</td>
      <td>0.250000</td>
      <td>0.169004</td>
      <td>0.133128</td>
      <td>89.175258</td>
      <td>100.0</td>
      <td>100.0</td>
      <td>37.700706</td>
      <td>83.121951</td>
      <td>244203.0</td>
      <td>1624</td>
      <td>1.346674</td>
      <td>94.776790</td>
    </tr>
  </tbody>
</table>
</div>




```python
target_col = ['강간검거율', '강도검거율', '살인검거율', '절도검거율', '폭력검거율']

crime_anal_norm_sort = crime_anal_norm.sort_values(by='검거', ascending=False)

plt.figure(figsize = (10,10))
sns.heatmap(crime_anal_norm_sort[target_col], annot=True, fmt='f', 
                    linewidths=.5, cmap='RdPu')
plt.title('범죄 검거 비율 (정규화된 검거의 합으로 정렬)')
plt.show()
```


![png](output_87_0.png)



```python
target_col = ['강간', '강도', '살인', '절도', '폭력', '범죄']

crime_anal_norm['범죄'] = crime_anal_norm['범죄'] / 5
crime_anal_norm_sort = crime_anal_norm.sort_values(by='범죄', ascending=False)

plt.figure(figsize = (10,10))
sns.heatmap(crime_anal_norm_sort[target_col], annot=True, fmt='f', linewidths=.5,
                       cmap='RdPu')
plt.title('범죄비율 (정규화된 발생 건수로 정렬)')
plt.show()
```


![png](output_88_0.png)



```python
crime_anal_norm.to_csv('../data/02. crime_in_Seoul_final.csv', sep=',', 
                       encoding='utf-8')
```

# Folium 


```python
import folium
```


```python
map_osm = folium.Map(location=[45.5236, -122.6750])
map_osm
```




<div style="width:100%;"><div style="position:relative;width:100%;height:0;padding-bottom:60%;"><iframe src="data:text/html;charset=utf-8;base64,PCFET0NUWVBFIGh0bWw+CjxoZWFkPiAgICAKICAgIDxtZXRhIGh0dHAtZXF1aXY9ImNvbnRlbnQtdHlwZSIgY29udGVudD0idGV4dC9odG1sOyBjaGFyc2V0PVVURi04IiAvPgogICAgPHNjcmlwdD5MX1BSRUZFUl9DQU5WQVM9ZmFsc2U7IExfTk9fVE9VQ0g9ZmFsc2U7IExfRElTQUJMRV8zRD1mYWxzZTs8L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmpzIj48L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2FqYXguZ29vZ2xlYXBpcy5jb20vYWpheC9saWJzL2pxdWVyeS8xLjExLjEvanF1ZXJ5Lm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvanMvYm9vdHN0cmFwLm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9jZG5qcy5jbG91ZGZsYXJlLmNvbS9hamF4L2xpYnMvTGVhZmxldC5hd2Vzb21lLW1hcmtlcnMvMi4wLjIvbGVhZmxldC5hd2Vzb21lLW1hcmtlcnMuanMiPjwvc2NyaXB0PgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL21heGNkbi5ib290c3RyYXBjZG4uY29tL2Jvb3RzdHJhcC8zLjIuMC9jc3MvYm9vdHN0cmFwLm1pbi5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvY3NzL2Jvb3RzdHJhcC10aGVtZS5taW4uY3NzIi8+CiAgICA8bGluayByZWw9InN0eWxlc2hlZXQiIGhyZWY9Imh0dHBzOi8vbWF4Y2RuLmJvb3RzdHJhcGNkbi5jb20vZm9udC1hd2Vzb21lLzQuNi4zL2Nzcy9mb250LWF3ZXNvbWUubWluLmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2NkbmpzLmNsb3VkZmxhcmUuY29tL2FqYXgvbGlicy9MZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy8yLjAuMi9sZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9yYXdjZG4uZ2l0aGFjay5jb20vcHl0aG9uLXZpc3VhbGl6YXRpb24vZm9saXVtL21hc3Rlci9mb2xpdW0vdGVtcGxhdGVzL2xlYWZsZXQuYXdlc29tZS5yb3RhdGUuY3NzIi8+CiAgICA8c3R5bGU+aHRtbCwgYm9keSB7d2lkdGg6IDEwMCU7aGVpZ2h0OiAxMDAlO21hcmdpbjogMDtwYWRkaW5nOiAwO308L3N0eWxlPgogICAgPHN0eWxlPiNtYXAge3Bvc2l0aW9uOmFic29sdXRlO3RvcDowO2JvdHRvbTowO3JpZ2h0OjA7bGVmdDowO308L3N0eWxlPgogICAgCiAgICA8bWV0YSBuYW1lPSJ2aWV3cG9ydCIgY29udGVudD0id2lkdGg9ZGV2aWNlLXdpZHRoLAogICAgICAgIGluaXRpYWwtc2NhbGU9MS4wLCBtYXhpbXVtLXNjYWxlPTEuMCwgdXNlci1zY2FsYWJsZT1ubyIgLz4KICAgIDxzdHlsZT4jbWFwXzM2YWYxNDkxYzE3ZDQ4NDlhMjMyYmYwMjM4M2NiYmUyIHsKICAgICAgICBwb3NpdGlvbjogcmVsYXRpdmU7CiAgICAgICAgd2lkdGg6IDEwMC4wJTsKICAgICAgICBoZWlnaHQ6IDEwMC4wJTsKICAgICAgICBsZWZ0OiAwLjAlOwogICAgICAgIHRvcDogMC4wJTsKICAgICAgICB9CiAgICA8L3N0eWxlPgo8L2hlYWQ+Cjxib2R5PiAgICAKICAgIAogICAgPGRpdiBjbGFzcz0iZm9saXVtLW1hcCIgaWQ9Im1hcF8zNmFmMTQ5MWMxN2Q0ODQ5YTIzMmJmMDIzODNjYmJlMiIgPjwvZGl2Pgo8L2JvZHk+CjxzY3JpcHQ+ICAgIAogICAgCiAgICAKICAgICAgICB2YXIgYm91bmRzID0gbnVsbDsKICAgIAoKICAgIHZhciBtYXBfMzZhZjE0OTFjMTdkNDg0OWEyMzJiZjAyMzgzY2JiZTIgPSBMLm1hcCgKICAgICAgICAnbWFwXzM2YWYxNDkxYzE3ZDQ4NDlhMjMyYmYwMjM4M2NiYmUyJywgewogICAgICAgIGNlbnRlcjogWzQ1LjUyMzYsIC0xMjIuNjc1XSwKICAgICAgICB6b29tOiAxMCwKICAgICAgICBtYXhCb3VuZHM6IGJvdW5kcywKICAgICAgICBsYXllcnM6IFtdLAogICAgICAgIHdvcmxkQ29weUp1bXA6IGZhbHNlLAogICAgICAgIGNyczogTC5DUlMuRVBTRzM4NTcsCiAgICAgICAgem9vbUNvbnRyb2w6IHRydWUsCiAgICAgICAgfSk7CgogICAgCiAgICAKICAgIHZhciB0aWxlX2xheWVyXzlkYTAzZDdjNzI0NDQ1YjA4ZTA3NjlhMWJlNGNmOGYwID0gTC50aWxlTGF5ZXIoCiAgICAgICAgJ2h0dHBzOi8ve3N9LnRpbGUub3BlbnN0cmVldG1hcC5vcmcve3p9L3t4fS97eX0ucG5nJywKICAgICAgICB7CiAgICAgICAgImF0dHJpYnV0aW9uIjogbnVsbCwKICAgICAgICAiZGV0ZWN0UmV0aW5hIjogZmFsc2UsCiAgICAgICAgIm1heE5hdGl2ZVpvb20iOiAxOCwKICAgICAgICAibWF4Wm9vbSI6IDE4LAogICAgICAgICJtaW5ab29tIjogMCwKICAgICAgICAibm9XcmFwIjogZmFsc2UsCiAgICAgICAgIm9wYWNpdHkiOiAxLAogICAgICAgICJzdWJkb21haW5zIjogImFiYyIsCiAgICAgICAgInRtcyI6IGZhbHNlCn0pLmFkZFRvKG1hcF8zNmFmMTQ5MWMxN2Q0ODQ5YTIzMmJmMDIzODNjYmJlMik7Cjwvc2NyaXB0Pg==" style="position:absolute;width:100%;height:100%;left:0;top:0;border:none !important;" allowfullscreen webkitallowfullscreen mozallowfullscreen></iframe></div></div>




```python
stamen = folium.Map(location=[45.5236, -122.6750], zoom_start=13)
stamen
```




<div style="width:100%;"><div style="position:relative;width:100%;height:0;padding-bottom:60%;"><iframe src="data:text/html;charset=utf-8;base64,PCFET0NUWVBFIGh0bWw+CjxoZWFkPiAgICAKICAgIDxtZXRhIGh0dHAtZXF1aXY9ImNvbnRlbnQtdHlwZSIgY29udGVudD0idGV4dC9odG1sOyBjaGFyc2V0PVVURi04IiAvPgogICAgPHNjcmlwdD5MX1BSRUZFUl9DQU5WQVM9ZmFsc2U7IExfTk9fVE9VQ0g9ZmFsc2U7IExfRElTQUJMRV8zRD1mYWxzZTs8L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmpzIj48L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2FqYXguZ29vZ2xlYXBpcy5jb20vYWpheC9saWJzL2pxdWVyeS8xLjExLjEvanF1ZXJ5Lm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvanMvYm9vdHN0cmFwLm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9jZG5qcy5jbG91ZGZsYXJlLmNvbS9hamF4L2xpYnMvTGVhZmxldC5hd2Vzb21lLW1hcmtlcnMvMi4wLjIvbGVhZmxldC5hd2Vzb21lLW1hcmtlcnMuanMiPjwvc2NyaXB0PgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL21heGNkbi5ib290c3RyYXBjZG4uY29tL2Jvb3RzdHJhcC8zLjIuMC9jc3MvYm9vdHN0cmFwLm1pbi5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvY3NzL2Jvb3RzdHJhcC10aGVtZS5taW4uY3NzIi8+CiAgICA8bGluayByZWw9InN0eWxlc2hlZXQiIGhyZWY9Imh0dHBzOi8vbWF4Y2RuLmJvb3RzdHJhcGNkbi5jb20vZm9udC1hd2Vzb21lLzQuNi4zL2Nzcy9mb250LWF3ZXNvbWUubWluLmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2NkbmpzLmNsb3VkZmxhcmUuY29tL2FqYXgvbGlicy9MZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy8yLjAuMi9sZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9yYXdjZG4uZ2l0aGFjay5jb20vcHl0aG9uLXZpc3VhbGl6YXRpb24vZm9saXVtL21hc3Rlci9mb2xpdW0vdGVtcGxhdGVzL2xlYWZsZXQuYXdlc29tZS5yb3RhdGUuY3NzIi8+CiAgICA8c3R5bGU+aHRtbCwgYm9keSB7d2lkdGg6IDEwMCU7aGVpZ2h0OiAxMDAlO21hcmdpbjogMDtwYWRkaW5nOiAwO308L3N0eWxlPgogICAgPHN0eWxlPiNtYXAge3Bvc2l0aW9uOmFic29sdXRlO3RvcDowO2JvdHRvbTowO3JpZ2h0OjA7bGVmdDowO308L3N0eWxlPgogICAgCiAgICA8bWV0YSBuYW1lPSJ2aWV3cG9ydCIgY29udGVudD0id2lkdGg9ZGV2aWNlLXdpZHRoLAogICAgICAgIGluaXRpYWwtc2NhbGU9MS4wLCBtYXhpbXVtLXNjYWxlPTEuMCwgdXNlci1zY2FsYWJsZT1ubyIgLz4KICAgIDxzdHlsZT4jbWFwXzI5MWE1OGZmOTdjNjQ4ZTg4NmQyMDc5Nzc5YTQ3YTRjIHsKICAgICAgICBwb3NpdGlvbjogcmVsYXRpdmU7CiAgICAgICAgd2lkdGg6IDEwMC4wJTsKICAgICAgICBoZWlnaHQ6IDEwMC4wJTsKICAgICAgICBsZWZ0OiAwLjAlOwogICAgICAgIHRvcDogMC4wJTsKICAgICAgICB9CiAgICA8L3N0eWxlPgo8L2hlYWQ+Cjxib2R5PiAgICAKICAgIAogICAgPGRpdiBjbGFzcz0iZm9saXVtLW1hcCIgaWQ9Im1hcF8yOTFhNThmZjk3YzY0OGU4ODZkMjA3OTc3OWE0N2E0YyIgPjwvZGl2Pgo8L2JvZHk+CjxzY3JpcHQ+ICAgIAogICAgCiAgICAKICAgICAgICB2YXIgYm91bmRzID0gbnVsbDsKICAgIAoKICAgIHZhciBtYXBfMjkxYTU4ZmY5N2M2NDhlODg2ZDIwNzk3NzlhNDdhNGMgPSBMLm1hcCgKICAgICAgICAnbWFwXzI5MWE1OGZmOTdjNjQ4ZTg4NmQyMDc5Nzc5YTQ3YTRjJywgewogICAgICAgIGNlbnRlcjogWzQ1LjUyMzYsIC0xMjIuNjc1XSwKICAgICAgICB6b29tOiAxMywKICAgICAgICBtYXhCb3VuZHM6IGJvdW5kcywKICAgICAgICBsYXllcnM6IFtdLAogICAgICAgIHdvcmxkQ29weUp1bXA6IGZhbHNlLAogICAgICAgIGNyczogTC5DUlMuRVBTRzM4NTcsCiAgICAgICAgem9vbUNvbnRyb2w6IHRydWUsCiAgICAgICAgfSk7CgogICAgCiAgICAKICAgIHZhciB0aWxlX2xheWVyXzJjMzQ5ZWY4MTNmZjRiNGJhYmYzYzE5ZjhiMTRmNTg4ID0gTC50aWxlTGF5ZXIoCiAgICAgICAgJ2h0dHBzOi8ve3N9LnRpbGUub3BlbnN0cmVldG1hcC5vcmcve3p9L3t4fS97eX0ucG5nJywKICAgICAgICB7CiAgICAgICAgImF0dHJpYnV0aW9uIjogbnVsbCwKICAgICAgICAiZGV0ZWN0UmV0aW5hIjogZmFsc2UsCiAgICAgICAgIm1heE5hdGl2ZVpvb20iOiAxOCwKICAgICAgICAibWF4Wm9vbSI6IDE4LAogICAgICAgICJtaW5ab29tIjogMCwKICAgICAgICAibm9XcmFwIjogZmFsc2UsCiAgICAgICAgIm9wYWNpdHkiOiAxLAogICAgICAgICJzdWJkb21haW5zIjogImFiYyIsCiAgICAgICAgInRtcyI6IGZhbHNlCn0pLmFkZFRvKG1hcF8yOTFhNThmZjk3YzY0OGU4ODZkMjA3OTc3OWE0N2E0Yyk7Cjwvc2NyaXB0Pg==" style="position:absolute;width:100%;height:100%;left:0;top:0;border:none !important;" allowfullscreen webkitallowfullscreen mozallowfullscreen></iframe></div></div>




```python
stamen = folium.Map(location=[45.5236, -122.6750], tiles='Stamen Toner', 
                    zoom_start=13)
stamen
```




<div style="width:100%;"><div style="position:relative;width:100%;height:0;padding-bottom:60%;"><iframe src="data:text/html;charset=utf-8;base64,PCFET0NUWVBFIGh0bWw+CjxoZWFkPiAgICAKICAgIDxtZXRhIGh0dHAtZXF1aXY9ImNvbnRlbnQtdHlwZSIgY29udGVudD0idGV4dC9odG1sOyBjaGFyc2V0PVVURi04IiAvPgogICAgPHNjcmlwdD5MX1BSRUZFUl9DQU5WQVM9ZmFsc2U7IExfTk9fVE9VQ0g9ZmFsc2U7IExfRElTQUJMRV8zRD1mYWxzZTs8L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmpzIj48L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2FqYXguZ29vZ2xlYXBpcy5jb20vYWpheC9saWJzL2pxdWVyeS8xLjExLjEvanF1ZXJ5Lm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvanMvYm9vdHN0cmFwLm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9jZG5qcy5jbG91ZGZsYXJlLmNvbS9hamF4L2xpYnMvTGVhZmxldC5hd2Vzb21lLW1hcmtlcnMvMi4wLjIvbGVhZmxldC5hd2Vzb21lLW1hcmtlcnMuanMiPjwvc2NyaXB0PgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL21heGNkbi5ib290c3RyYXBjZG4uY29tL2Jvb3RzdHJhcC8zLjIuMC9jc3MvYm9vdHN0cmFwLm1pbi5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvY3NzL2Jvb3RzdHJhcC10aGVtZS5taW4uY3NzIi8+CiAgICA8bGluayByZWw9InN0eWxlc2hlZXQiIGhyZWY9Imh0dHBzOi8vbWF4Y2RuLmJvb3RzdHJhcGNkbi5jb20vZm9udC1hd2Vzb21lLzQuNi4zL2Nzcy9mb250LWF3ZXNvbWUubWluLmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2NkbmpzLmNsb3VkZmxhcmUuY29tL2FqYXgvbGlicy9MZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy8yLjAuMi9sZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9yYXdjZG4uZ2l0aGFjay5jb20vcHl0aG9uLXZpc3VhbGl6YXRpb24vZm9saXVtL21hc3Rlci9mb2xpdW0vdGVtcGxhdGVzL2xlYWZsZXQuYXdlc29tZS5yb3RhdGUuY3NzIi8+CiAgICA8c3R5bGU+aHRtbCwgYm9keSB7d2lkdGg6IDEwMCU7aGVpZ2h0OiAxMDAlO21hcmdpbjogMDtwYWRkaW5nOiAwO308L3N0eWxlPgogICAgPHN0eWxlPiNtYXAge3Bvc2l0aW9uOmFic29sdXRlO3RvcDowO2JvdHRvbTowO3JpZ2h0OjA7bGVmdDowO308L3N0eWxlPgogICAgCiAgICA8bWV0YSBuYW1lPSJ2aWV3cG9ydCIgY29udGVudD0id2lkdGg9ZGV2aWNlLXdpZHRoLAogICAgICAgIGluaXRpYWwtc2NhbGU9MS4wLCBtYXhpbXVtLXNjYWxlPTEuMCwgdXNlci1zY2FsYWJsZT1ubyIgLz4KICAgIDxzdHlsZT4jbWFwXzNiYjJkNDg5ZjcwZDRjZjVhYmUzYWQzZWQxOTEwNWFlIHsKICAgICAgICBwb3NpdGlvbjogcmVsYXRpdmU7CiAgICAgICAgd2lkdGg6IDEwMC4wJTsKICAgICAgICBoZWlnaHQ6IDEwMC4wJTsKICAgICAgICBsZWZ0OiAwLjAlOwogICAgICAgIHRvcDogMC4wJTsKICAgICAgICB9CiAgICA8L3N0eWxlPgo8L2hlYWQ+Cjxib2R5PiAgICAKICAgIAogICAgPGRpdiBjbGFzcz0iZm9saXVtLW1hcCIgaWQ9Im1hcF8zYmIyZDQ4OWY3MGQ0Y2Y1YWJlM2FkM2VkMTkxMDVhZSIgPjwvZGl2Pgo8L2JvZHk+CjxzY3JpcHQ+ICAgIAogICAgCiAgICAKICAgICAgICB2YXIgYm91bmRzID0gbnVsbDsKICAgIAoKICAgIHZhciBtYXBfM2JiMmQ0ODlmNzBkNGNmNWFiZTNhZDNlZDE5MTA1YWUgPSBMLm1hcCgKICAgICAgICAnbWFwXzNiYjJkNDg5ZjcwZDRjZjVhYmUzYWQzZWQxOTEwNWFlJywgewogICAgICAgIGNlbnRlcjogWzQ1LjUyMzYsIC0xMjIuNjc1XSwKICAgICAgICB6b29tOiAxMywKICAgICAgICBtYXhCb3VuZHM6IGJvdW5kcywKICAgICAgICBsYXllcnM6IFtdLAogICAgICAgIHdvcmxkQ29weUp1bXA6IGZhbHNlLAogICAgICAgIGNyczogTC5DUlMuRVBTRzM4NTcsCiAgICAgICAgem9vbUNvbnRyb2w6IHRydWUsCiAgICAgICAgfSk7CgogICAgCiAgICAKICAgIHZhciB0aWxlX2xheWVyX2I0OGRjYzQ4ZWE5NDQxODJhNDhmMDM3YWEzNTI0ZmY2ID0gTC50aWxlTGF5ZXIoCiAgICAgICAgJ2h0dHBzOi8vc3RhbWVuLXRpbGVzLXtzfS5hLnNzbC5mYXN0bHkubmV0L3RvbmVyL3t6fS97eH0ve3l9LnBuZycsCiAgICAgICAgewogICAgICAgICJhdHRyaWJ1dGlvbiI6IG51bGwsCiAgICAgICAgImRldGVjdFJldGluYSI6IGZhbHNlLAogICAgICAgICJtYXhOYXRpdmVab29tIjogMTgsCiAgICAgICAgIm1heFpvb20iOiAxOCwKICAgICAgICAibWluWm9vbSI6IDAsCiAgICAgICAgIm5vV3JhcCI6IGZhbHNlLAogICAgICAgICJvcGFjaXR5IjogMSwKICAgICAgICAic3ViZG9tYWlucyI6ICJhYmMiLAogICAgICAgICJ0bXMiOiBmYWxzZQp9KS5hZGRUbyhtYXBfM2JiMmQ0ODlmNzBkNGNmNWFiZTNhZDNlZDE5MTA1YWUpOwo8L3NjcmlwdD4=" style="position:absolute;width:100%;height:100%;left:0;top:0;border:none !important;" allowfullscreen webkitallowfullscreen mozallowfullscreen></iframe></div></div>




```python
stamen = folium.Map(location=[45.5236, -122.6750], 
                    tiles='Stamen Terrain', zoom_start=13)
stamen
```




<div style="width:100%;"><div style="position:relative;width:100%;height:0;padding-bottom:60%;"><iframe src="data:text/html;charset=utf-8;base64,PCFET0NUWVBFIGh0bWw+CjxoZWFkPiAgICAKICAgIDxtZXRhIGh0dHAtZXF1aXY9ImNvbnRlbnQtdHlwZSIgY29udGVudD0idGV4dC9odG1sOyBjaGFyc2V0PVVURi04IiAvPgogICAgPHNjcmlwdD5MX1BSRUZFUl9DQU5WQVM9ZmFsc2U7IExfTk9fVE9VQ0g9ZmFsc2U7IExfRElTQUJMRV8zRD1mYWxzZTs8L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmpzIj48L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2FqYXguZ29vZ2xlYXBpcy5jb20vYWpheC9saWJzL2pxdWVyeS8xLjExLjEvanF1ZXJ5Lm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvanMvYm9vdHN0cmFwLm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9jZG5qcy5jbG91ZGZsYXJlLmNvbS9hamF4L2xpYnMvTGVhZmxldC5hd2Vzb21lLW1hcmtlcnMvMi4wLjIvbGVhZmxldC5hd2Vzb21lLW1hcmtlcnMuanMiPjwvc2NyaXB0PgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL21heGNkbi5ib290c3RyYXBjZG4uY29tL2Jvb3RzdHJhcC8zLjIuMC9jc3MvYm9vdHN0cmFwLm1pbi5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvY3NzL2Jvb3RzdHJhcC10aGVtZS5taW4uY3NzIi8+CiAgICA8bGluayByZWw9InN0eWxlc2hlZXQiIGhyZWY9Imh0dHBzOi8vbWF4Y2RuLmJvb3RzdHJhcGNkbi5jb20vZm9udC1hd2Vzb21lLzQuNi4zL2Nzcy9mb250LWF3ZXNvbWUubWluLmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2NkbmpzLmNsb3VkZmxhcmUuY29tL2FqYXgvbGlicy9MZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy8yLjAuMi9sZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9yYXdjZG4uZ2l0aGFjay5jb20vcHl0aG9uLXZpc3VhbGl6YXRpb24vZm9saXVtL21hc3Rlci9mb2xpdW0vdGVtcGxhdGVzL2xlYWZsZXQuYXdlc29tZS5yb3RhdGUuY3NzIi8+CiAgICA8c3R5bGU+aHRtbCwgYm9keSB7d2lkdGg6IDEwMCU7aGVpZ2h0OiAxMDAlO21hcmdpbjogMDtwYWRkaW5nOiAwO308L3N0eWxlPgogICAgPHN0eWxlPiNtYXAge3Bvc2l0aW9uOmFic29sdXRlO3RvcDowO2JvdHRvbTowO3JpZ2h0OjA7bGVmdDowO308L3N0eWxlPgogICAgCiAgICA8bWV0YSBuYW1lPSJ2aWV3cG9ydCIgY29udGVudD0id2lkdGg9ZGV2aWNlLXdpZHRoLAogICAgICAgIGluaXRpYWwtc2NhbGU9MS4wLCBtYXhpbXVtLXNjYWxlPTEuMCwgdXNlci1zY2FsYWJsZT1ubyIgLz4KICAgIDxzdHlsZT4jbWFwXzEzMTU5MWQ2Y2E0ZTQxYjhhZTJlYjIzNDY3NDkyYzMwIHsKICAgICAgICBwb3NpdGlvbjogcmVsYXRpdmU7CiAgICAgICAgd2lkdGg6IDEwMC4wJTsKICAgICAgICBoZWlnaHQ6IDEwMC4wJTsKICAgICAgICBsZWZ0OiAwLjAlOwogICAgICAgIHRvcDogMC4wJTsKICAgICAgICB9CiAgICA8L3N0eWxlPgo8L2hlYWQ+Cjxib2R5PiAgICAKICAgIAogICAgPGRpdiBjbGFzcz0iZm9saXVtLW1hcCIgaWQ9Im1hcF8xMzE1OTFkNmNhNGU0MWI4YWUyZWIyMzQ2NzQ5MmMzMCIgPjwvZGl2Pgo8L2JvZHk+CjxzY3JpcHQ+ICAgIAogICAgCiAgICAKICAgICAgICB2YXIgYm91bmRzID0gbnVsbDsKICAgIAoKICAgIHZhciBtYXBfMTMxNTkxZDZjYTRlNDFiOGFlMmViMjM0Njc0OTJjMzAgPSBMLm1hcCgKICAgICAgICAnbWFwXzEzMTU5MWQ2Y2E0ZTQxYjhhZTJlYjIzNDY3NDkyYzMwJywgewogICAgICAgIGNlbnRlcjogWzQ1LjUyMzYsIC0xMjIuNjc1XSwKICAgICAgICB6b29tOiAxMywKICAgICAgICBtYXhCb3VuZHM6IGJvdW5kcywKICAgICAgICBsYXllcnM6IFtdLAogICAgICAgIHdvcmxkQ29weUp1bXA6IGZhbHNlLAogICAgICAgIGNyczogTC5DUlMuRVBTRzM4NTcsCiAgICAgICAgem9vbUNvbnRyb2w6IHRydWUsCiAgICAgICAgfSk7CgogICAgCiAgICAKICAgIHZhciB0aWxlX2xheWVyXzkzNzA5NDM3M2FlMDRmYzc5OGI0NzNiYTg1Zjc1ODM2ID0gTC50aWxlTGF5ZXIoCiAgICAgICAgJ2h0dHBzOi8vc3RhbWVuLXRpbGVzLXtzfS5hLnNzbC5mYXN0bHkubmV0L3RlcnJhaW4ve3p9L3t4fS97eX0uanBnJywKICAgICAgICB7CiAgICAgICAgImF0dHJpYnV0aW9uIjogbnVsbCwKICAgICAgICAiZGV0ZWN0UmV0aW5hIjogZmFsc2UsCiAgICAgICAgIm1heE5hdGl2ZVpvb20iOiAxOCwKICAgICAgICAibWF4Wm9vbSI6IDE4LAogICAgICAgICJtaW5ab29tIjogMCwKICAgICAgICAibm9XcmFwIjogZmFsc2UsCiAgICAgICAgIm9wYWNpdHkiOiAxLAogICAgICAgICJzdWJkb21haW5zIjogImFiYyIsCiAgICAgICAgInRtcyI6IGZhbHNlCn0pLmFkZFRvKG1hcF8xMzE1OTFkNmNhNGU0MWI4YWUyZWIyMzQ2NzQ5MmMzMCk7Cjwvc2NyaXB0Pg==" style="position:absolute;width:100%;height:100%;left:0;top:0;border:none !important;" allowfullscreen webkitallowfullscreen mozallowfullscreen></iframe></div></div>




```python
map_1 = folium.Map(location=[45.372, -121.6972], zoom_start=12,
                   tiles='Stamen Terrain')
folium.Marker([45.3288, -121.6625], popup='Mt. Hood Meadows', 
              icon=folium.Icon(icon='cloud')).add_to(map_1)
folium.Marker([45.3311, -121.7113], popup='Timberline Lodge', 
              icon=folium.Icon(icon='cloud')).add_to(map_1)
map_1
```




<div style="width:100%;"><div style="position:relative;width:100%;height:0;padding-bottom:60%;"><iframe src="data:text/html;charset=utf-8;base64,PCFET0NUWVBFIGh0bWw+CjxoZWFkPiAgICAKICAgIDxtZXRhIGh0dHAtZXF1aXY9ImNvbnRlbnQtdHlwZSIgY29udGVudD0idGV4dC9odG1sOyBjaGFyc2V0PVVURi04IiAvPgogICAgPHNjcmlwdD5MX1BSRUZFUl9DQU5WQVM9ZmFsc2U7IExfTk9fVE9VQ0g9ZmFsc2U7IExfRElTQUJMRV8zRD1mYWxzZTs8L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmpzIj48L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2FqYXguZ29vZ2xlYXBpcy5jb20vYWpheC9saWJzL2pxdWVyeS8xLjExLjEvanF1ZXJ5Lm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvanMvYm9vdHN0cmFwLm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9jZG5qcy5jbG91ZGZsYXJlLmNvbS9hamF4L2xpYnMvTGVhZmxldC5hd2Vzb21lLW1hcmtlcnMvMi4wLjIvbGVhZmxldC5hd2Vzb21lLW1hcmtlcnMuanMiPjwvc2NyaXB0PgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL21heGNkbi5ib290c3RyYXBjZG4uY29tL2Jvb3RzdHJhcC8zLjIuMC9jc3MvYm9vdHN0cmFwLm1pbi5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvY3NzL2Jvb3RzdHJhcC10aGVtZS5taW4uY3NzIi8+CiAgICA8bGluayByZWw9InN0eWxlc2hlZXQiIGhyZWY9Imh0dHBzOi8vbWF4Y2RuLmJvb3RzdHJhcGNkbi5jb20vZm9udC1hd2Vzb21lLzQuNi4zL2Nzcy9mb250LWF3ZXNvbWUubWluLmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2NkbmpzLmNsb3VkZmxhcmUuY29tL2FqYXgvbGlicy9MZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy8yLjAuMi9sZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9yYXdjZG4uZ2l0aGFjay5jb20vcHl0aG9uLXZpc3VhbGl6YXRpb24vZm9saXVtL21hc3Rlci9mb2xpdW0vdGVtcGxhdGVzL2xlYWZsZXQuYXdlc29tZS5yb3RhdGUuY3NzIi8+CiAgICA8c3R5bGU+aHRtbCwgYm9keSB7d2lkdGg6IDEwMCU7aGVpZ2h0OiAxMDAlO21hcmdpbjogMDtwYWRkaW5nOiAwO308L3N0eWxlPgogICAgPHN0eWxlPiNtYXAge3Bvc2l0aW9uOmFic29sdXRlO3RvcDowO2JvdHRvbTowO3JpZ2h0OjA7bGVmdDowO308L3N0eWxlPgogICAgCiAgICA8bWV0YSBuYW1lPSJ2aWV3cG9ydCIgY29udGVudD0id2lkdGg9ZGV2aWNlLXdpZHRoLAogICAgICAgIGluaXRpYWwtc2NhbGU9MS4wLCBtYXhpbXVtLXNjYWxlPTEuMCwgdXNlci1zY2FsYWJsZT1ubyIgLz4KICAgIDxzdHlsZT4jbWFwXzMxMWRlZWZhOWQ1NzRmMjM4OTI2ZTFjMGJiMDBiM2MxIHsKICAgICAgICBwb3NpdGlvbjogcmVsYXRpdmU7CiAgICAgICAgd2lkdGg6IDEwMC4wJTsKICAgICAgICBoZWlnaHQ6IDEwMC4wJTsKICAgICAgICBsZWZ0OiAwLjAlOwogICAgICAgIHRvcDogMC4wJTsKICAgICAgICB9CiAgICA8L3N0eWxlPgo8L2hlYWQ+Cjxib2R5PiAgICAKICAgIAogICAgPGRpdiBjbGFzcz0iZm9saXVtLW1hcCIgaWQ9Im1hcF8zMTFkZWVmYTlkNTc0ZjIzODkyNmUxYzBiYjAwYjNjMSIgPjwvZGl2Pgo8L2JvZHk+CjxzY3JpcHQ+ICAgIAogICAgCiAgICAKICAgICAgICB2YXIgYm91bmRzID0gbnVsbDsKICAgIAoKICAgIHZhciBtYXBfMzExZGVlZmE5ZDU3NGYyMzg5MjZlMWMwYmIwMGIzYzEgPSBMLm1hcCgKICAgICAgICAnbWFwXzMxMWRlZWZhOWQ1NzRmMjM4OTI2ZTFjMGJiMDBiM2MxJywgewogICAgICAgIGNlbnRlcjogWzQ1LjM3MiwgLTEyMS42OTcyXSwKICAgICAgICB6b29tOiAxMiwKICAgICAgICBtYXhCb3VuZHM6IGJvdW5kcywKICAgICAgICBsYXllcnM6IFtdLAogICAgICAgIHdvcmxkQ29weUp1bXA6IGZhbHNlLAogICAgICAgIGNyczogTC5DUlMuRVBTRzM4NTcsCiAgICAgICAgem9vbUNvbnRyb2w6IHRydWUsCiAgICAgICAgfSk7CgogICAgCiAgICAKICAgIHZhciB0aWxlX2xheWVyXzQ1ZDk2MzgyODk0MDQwYTBhMzA4ZDM0NGRkNzhiYmI1ID0gTC50aWxlTGF5ZXIoCiAgICAgICAgJ2h0dHBzOi8vc3RhbWVuLXRpbGVzLXtzfS5hLnNzbC5mYXN0bHkubmV0L3RlcnJhaW4ve3p9L3t4fS97eX0uanBnJywKICAgICAgICB7CiAgICAgICAgImF0dHJpYnV0aW9uIjogbnVsbCwKICAgICAgICAiZGV0ZWN0UmV0aW5hIjogZmFsc2UsCiAgICAgICAgIm1heE5hdGl2ZVpvb20iOiAxOCwKICAgICAgICAibWF4Wm9vbSI6IDE4LAogICAgICAgICJtaW5ab29tIjogMCwKICAgICAgICAibm9XcmFwIjogZmFsc2UsCiAgICAgICAgIm9wYWNpdHkiOiAxLAogICAgICAgICJzdWJkb21haW5zIjogImFiYyIsCiAgICAgICAgInRtcyI6IGZhbHNlCn0pLmFkZFRvKG1hcF8zMTFkZWVmYTlkNTc0ZjIzODkyNmUxYzBiYjAwYjNjMSk7CiAgICAKICAgICAgICB2YXIgbWFya2VyXzNmYmY1ZGUwMWJjMDRmZjJiYjljNzZjY2U1NzA0MjUwID0gTC5tYXJrZXIoCiAgICAgICAgICAgIFs0NS4zMjg4LCAtMTIxLjY2MjVdLAogICAgICAgICAgICB7CiAgICAgICAgICAgICAgICBpY29uOiBuZXcgTC5JY29uLkRlZmF1bHQoKQogICAgICAgICAgICAgICAgfQogICAgICAgICAgICApLmFkZFRvKG1hcF8zMTFkZWVmYTlkNTc0ZjIzODkyNmUxYzBiYjAwYjNjMSk7CiAgICAgICAgCiAgICAKCiAgICAgICAgICAgICAgICB2YXIgaWNvbl8yYTRjNGE2MGVhMWU0NTUzOWI5YjU1MmQzNzYzZDVmZSA9IEwuQXdlc29tZU1hcmtlcnMuaWNvbih7CiAgICAgICAgICAgICAgICAgICAgaWNvbjogJ2Nsb3VkJywKICAgICAgICAgICAgICAgICAgICBpY29uQ29sb3I6ICd3aGl0ZScsCiAgICAgICAgICAgICAgICAgICAgbWFya2VyQ29sb3I6ICdibHVlJywKICAgICAgICAgICAgICAgICAgICBwcmVmaXg6ICdnbHlwaGljb24nLAogICAgICAgICAgICAgICAgICAgIGV4dHJhQ2xhc3NlczogJ2ZhLXJvdGF0ZS0wJwogICAgICAgICAgICAgICAgICAgIH0pOwogICAgICAgICAgICAgICAgbWFya2VyXzNmYmY1ZGUwMWJjMDRmZjJiYjljNzZjY2U1NzA0MjUwLnNldEljb24oaWNvbl8yYTRjNGE2MGVhMWU0NTUzOWI5YjU1MmQzNzYzZDVmZSk7CiAgICAgICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBwb3B1cF9jMzdhM2MzYjZmNTk0MWUxODIyMGRkZjk1N2IxNTg1ZiA9IEwucG9wdXAoe21heFdpZHRoOiAnMzAwJwogICAgICAgICAgICAKICAgICAgICAgICAgfSk7CgogICAgICAgICAgICAKICAgICAgICAgICAgICAgIHZhciBodG1sXzU1NWZhM2U0NmE0YTQ4YjZhNjA4ZmRhOTYzYTE4OTdhID0gJChgPGRpdiBpZD0iaHRtbF81NTVmYTNlNDZhNGE0OGI2YTYwOGZkYTk2M2ExODk3YSIgc3R5bGU9IndpZHRoOiAxMDAuMCU7IGhlaWdodDogMTAwLjAlOyI+TXQuIEhvb2QgTWVhZG93czwvZGl2PmApWzBdOwogICAgICAgICAgICAgICAgcG9wdXBfYzM3YTNjM2I2ZjU5NDFlMTgyMjBkZGY5NTdiMTU4NWYuc2V0Q29udGVudChodG1sXzU1NWZhM2U0NmE0YTQ4YjZhNjA4ZmRhOTYzYTE4OTdhKTsKICAgICAgICAgICAgCgogICAgICAgICAgICBtYXJrZXJfM2ZiZjVkZTAxYmMwNGZmMmJiOWM3NmNjZTU3MDQyNTAuYmluZFBvcHVwKHBvcHVwX2MzN2EzYzNiNmY1OTQxZTE4MjIwZGRmOTU3YjE1ODVmKQogICAgICAgICAgICA7CgogICAgICAgICAgICAKICAgICAgICAKICAgIAogICAgICAgIHZhciBtYXJrZXJfNDU1NDMyMDQwMzdhNGYxZmFkMmE4MzUwYTMxYWFjYTkgPSBMLm1hcmtlcigKICAgICAgICAgICAgWzQ1LjMzMTEsIC0xMjEuNzExM10sCiAgICAgICAgICAgIHsKICAgICAgICAgICAgICAgIGljb246IG5ldyBMLkljb24uRGVmYXVsdCgpCiAgICAgICAgICAgICAgICB9CiAgICAgICAgICAgICkuYWRkVG8obWFwXzMxMWRlZWZhOWQ1NzRmMjM4OTI2ZTFjMGJiMDBiM2MxKTsKICAgICAgICAKICAgIAoKICAgICAgICAgICAgICAgIHZhciBpY29uX2U4ODhjNDI5MzIxNTRlMmJhYTRkZjVkMWE2ODcwMTkyID0gTC5Bd2Vzb21lTWFya2Vycy5pY29uKHsKICAgICAgICAgICAgICAgICAgICBpY29uOiAnY2xvdWQnLAogICAgICAgICAgICAgICAgICAgIGljb25Db2xvcjogJ3doaXRlJywKICAgICAgICAgICAgICAgICAgICBtYXJrZXJDb2xvcjogJ2JsdWUnLAogICAgICAgICAgICAgICAgICAgIHByZWZpeDogJ2dseXBoaWNvbicsCiAgICAgICAgICAgICAgICAgICAgZXh0cmFDbGFzc2VzOiAnZmEtcm90YXRlLTAnCiAgICAgICAgICAgICAgICAgICAgfSk7CiAgICAgICAgICAgICAgICBtYXJrZXJfNDU1NDMyMDQwMzdhNGYxZmFkMmE4MzUwYTMxYWFjYTkuc2V0SWNvbihpY29uX2U4ODhjNDI5MzIxNTRlMmJhYTRkZjVkMWE2ODcwMTkyKTsKICAgICAgICAgICAgCiAgICAKICAgICAgICAgICAgdmFyIHBvcHVwX2MzZDg1YmZmNmNmZTRmMmRiMTNhMjc1MTM5OGRjODUyID0gTC5wb3B1cCh7bWF4V2lkdGg6ICczMDAnCiAgICAgICAgICAgIAogICAgICAgICAgICB9KTsKCiAgICAgICAgICAgIAogICAgICAgICAgICAgICAgdmFyIGh0bWxfOWVlMjUyOTYyNDM5NDc0YWFmNWZiZDdlNTFmZWQ5N2EgPSAkKGA8ZGl2IGlkPSJodG1sXzllZTI1Mjk2MjQzOTQ3NGFhZjVmYmQ3ZTUxZmVkOTdhIiBzdHlsZT0id2lkdGg6IDEwMC4wJTsgaGVpZ2h0OiAxMDAuMCU7Ij5UaW1iZXJsaW5lIExvZGdlPC9kaXY+YClbMF07CiAgICAgICAgICAgICAgICBwb3B1cF9jM2Q4NWJmZjZjZmU0ZjJkYjEzYTI3NTEzOThkYzg1Mi5zZXRDb250ZW50KGh0bWxfOWVlMjUyOTYyNDM5NDc0YWFmNWZiZDdlNTFmZWQ5N2EpOwogICAgICAgICAgICAKCiAgICAgICAgICAgIG1hcmtlcl80NTU0MzIwNDAzN2E0ZjFmYWQyYTgzNTBhMzFhYWNhOS5iaW5kUG9wdXAocG9wdXBfYzNkODViZmY2Y2ZlNGYyZGIxM2EyNzUxMzk4ZGM4NTIpCiAgICAgICAgICAgIDsKCiAgICAgICAgICAgIAogICAgICAgIAo8L3NjcmlwdD4=" style="position:absolute;width:100%;height:100%;left:0;top:0;border:none !important;" allowfullscreen webkitallowfullscreen mozallowfullscreen></iframe></div></div>




```python
map_1 = folium.Map(location=[45.372, -121.6972], zoom_start=12, 
                   tiles='Stamen Terrain')
folium.Marker([45.3288, -121.6625], popup='Mt. Hood Meadows', 
              icon=folium.Icon(icon='cloud')).add_to(map_1)
folium.Marker([45.3311, -121.7113], popup='Timberline Lodge', 
              icon=folium.Icon(color='green')).add_to(map_1)
folium.Marker([45.3300, -121.6823], popup='Some Other Location', 
              icon=folium.Icon(color='red',icon='info-sign')).add_to(map_1)
map_1
```




<div style="width:100%;"><div style="position:relative;width:100%;height:0;padding-bottom:60%;"><iframe src="data:text/html;charset=utf-8;base64,PCFET0NUWVBFIGh0bWw+CjxoZWFkPiAgICAKICAgIDxtZXRhIGh0dHAtZXF1aXY9ImNvbnRlbnQtdHlwZSIgY29udGVudD0idGV4dC9odG1sOyBjaGFyc2V0PVVURi04IiAvPgogICAgPHNjcmlwdD5MX1BSRUZFUl9DQU5WQVM9ZmFsc2U7IExfTk9fVE9VQ0g9ZmFsc2U7IExfRElTQUJMRV8zRD1mYWxzZTs8L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmpzIj48L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2FqYXguZ29vZ2xlYXBpcy5jb20vYWpheC9saWJzL2pxdWVyeS8xLjExLjEvanF1ZXJ5Lm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvanMvYm9vdHN0cmFwLm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9jZG5qcy5jbG91ZGZsYXJlLmNvbS9hamF4L2xpYnMvTGVhZmxldC5hd2Vzb21lLW1hcmtlcnMvMi4wLjIvbGVhZmxldC5hd2Vzb21lLW1hcmtlcnMuanMiPjwvc2NyaXB0PgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL21heGNkbi5ib290c3RyYXBjZG4uY29tL2Jvb3RzdHJhcC8zLjIuMC9jc3MvYm9vdHN0cmFwLm1pbi5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvY3NzL2Jvb3RzdHJhcC10aGVtZS5taW4uY3NzIi8+CiAgICA8bGluayByZWw9InN0eWxlc2hlZXQiIGhyZWY9Imh0dHBzOi8vbWF4Y2RuLmJvb3RzdHJhcGNkbi5jb20vZm9udC1hd2Vzb21lLzQuNi4zL2Nzcy9mb250LWF3ZXNvbWUubWluLmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2NkbmpzLmNsb3VkZmxhcmUuY29tL2FqYXgvbGlicy9MZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy8yLjAuMi9sZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9yYXdjZG4uZ2l0aGFjay5jb20vcHl0aG9uLXZpc3VhbGl6YXRpb24vZm9saXVtL21hc3Rlci9mb2xpdW0vdGVtcGxhdGVzL2xlYWZsZXQuYXdlc29tZS5yb3RhdGUuY3NzIi8+CiAgICA8c3R5bGU+aHRtbCwgYm9keSB7d2lkdGg6IDEwMCU7aGVpZ2h0OiAxMDAlO21hcmdpbjogMDtwYWRkaW5nOiAwO308L3N0eWxlPgogICAgPHN0eWxlPiNtYXAge3Bvc2l0aW9uOmFic29sdXRlO3RvcDowO2JvdHRvbTowO3JpZ2h0OjA7bGVmdDowO308L3N0eWxlPgogICAgCiAgICA8bWV0YSBuYW1lPSJ2aWV3cG9ydCIgY29udGVudD0id2lkdGg9ZGV2aWNlLXdpZHRoLAogICAgICAgIGluaXRpYWwtc2NhbGU9MS4wLCBtYXhpbXVtLXNjYWxlPTEuMCwgdXNlci1zY2FsYWJsZT1ubyIgLz4KICAgIDxzdHlsZT4jbWFwX2UxYzI4MjA1NjQ5YjRhMTA4NTg2OGVlZDRlZDk3NDYwIHsKICAgICAgICBwb3NpdGlvbjogcmVsYXRpdmU7CiAgICAgICAgd2lkdGg6IDEwMC4wJTsKICAgICAgICBoZWlnaHQ6IDEwMC4wJTsKICAgICAgICBsZWZ0OiAwLjAlOwogICAgICAgIHRvcDogMC4wJTsKICAgICAgICB9CiAgICA8L3N0eWxlPgo8L2hlYWQ+Cjxib2R5PiAgICAKICAgIAogICAgPGRpdiBjbGFzcz0iZm9saXVtLW1hcCIgaWQ9Im1hcF9lMWMyODIwNTY0OWI0YTEwODU4NjhlZWQ0ZWQ5NzQ2MCIgPjwvZGl2Pgo8L2JvZHk+CjxzY3JpcHQ+ICAgIAogICAgCiAgICAKICAgICAgICB2YXIgYm91bmRzID0gbnVsbDsKICAgIAoKICAgIHZhciBtYXBfZTFjMjgyMDU2NDliNGExMDg1ODY4ZWVkNGVkOTc0NjAgPSBMLm1hcCgKICAgICAgICAnbWFwX2UxYzI4MjA1NjQ5YjRhMTA4NTg2OGVlZDRlZDk3NDYwJywgewogICAgICAgIGNlbnRlcjogWzQ1LjM3MiwgLTEyMS42OTcyXSwKICAgICAgICB6b29tOiAxMiwKICAgICAgICBtYXhCb3VuZHM6IGJvdW5kcywKICAgICAgICBsYXllcnM6IFtdLAogICAgICAgIHdvcmxkQ29weUp1bXA6IGZhbHNlLAogICAgICAgIGNyczogTC5DUlMuRVBTRzM4NTcsCiAgICAgICAgem9vbUNvbnRyb2w6IHRydWUsCiAgICAgICAgfSk7CgogICAgCiAgICAKICAgIHZhciB0aWxlX2xheWVyXzc5ODYxMjI1ZWRjOTQzZWI4NjQyNjRiYTRmN2NlNmQ5ID0gTC50aWxlTGF5ZXIoCiAgICAgICAgJ2h0dHBzOi8vc3RhbWVuLXRpbGVzLXtzfS5hLnNzbC5mYXN0bHkubmV0L3RlcnJhaW4ve3p9L3t4fS97eX0uanBnJywKICAgICAgICB7CiAgICAgICAgImF0dHJpYnV0aW9uIjogbnVsbCwKICAgICAgICAiZGV0ZWN0UmV0aW5hIjogZmFsc2UsCiAgICAgICAgIm1heE5hdGl2ZVpvb20iOiAxOCwKICAgICAgICAibWF4Wm9vbSI6IDE4LAogICAgICAgICJtaW5ab29tIjogMCwKICAgICAgICAibm9XcmFwIjogZmFsc2UsCiAgICAgICAgIm9wYWNpdHkiOiAxLAogICAgICAgICJzdWJkb21haW5zIjogImFiYyIsCiAgICAgICAgInRtcyI6IGZhbHNlCn0pLmFkZFRvKG1hcF9lMWMyODIwNTY0OWI0YTEwODU4NjhlZWQ0ZWQ5NzQ2MCk7CiAgICAKICAgICAgICB2YXIgbWFya2VyXzZhNDMyNjM0NGUwYzRiMGI4NDJiNWFjYjM4YmYxMzhjID0gTC5tYXJrZXIoCiAgICAgICAgICAgIFs0NS4zMjg4LCAtMTIxLjY2MjVdLAogICAgICAgICAgICB7CiAgICAgICAgICAgICAgICBpY29uOiBuZXcgTC5JY29uLkRlZmF1bHQoKQogICAgICAgICAgICAgICAgfQogICAgICAgICAgICApLmFkZFRvKG1hcF9lMWMyODIwNTY0OWI0YTEwODU4NjhlZWQ0ZWQ5NzQ2MCk7CiAgICAgICAgCiAgICAKCiAgICAgICAgICAgICAgICB2YXIgaWNvbl9lMGJkODNjZTg2YTA0MmUzYjA4ZDJkYjZkMmYyNTBlMyA9IEwuQXdlc29tZU1hcmtlcnMuaWNvbih7CiAgICAgICAgICAgICAgICAgICAgaWNvbjogJ2Nsb3VkJywKICAgICAgICAgICAgICAgICAgICBpY29uQ29sb3I6ICd3aGl0ZScsCiAgICAgICAgICAgICAgICAgICAgbWFya2VyQ29sb3I6ICdibHVlJywKICAgICAgICAgICAgICAgICAgICBwcmVmaXg6ICdnbHlwaGljb24nLAogICAgICAgICAgICAgICAgICAgIGV4dHJhQ2xhc3NlczogJ2ZhLXJvdGF0ZS0wJwogICAgICAgICAgICAgICAgICAgIH0pOwogICAgICAgICAgICAgICAgbWFya2VyXzZhNDMyNjM0NGUwYzRiMGI4NDJiNWFjYjM4YmYxMzhjLnNldEljb24oaWNvbl9lMGJkODNjZTg2YTA0MmUzYjA4ZDJkYjZkMmYyNTBlMyk7CiAgICAgICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBwb3B1cF9jZWI3NWZiMjM1Nzk0ODc2YWZhZWFiMDA4OTE2NTczMSA9IEwucG9wdXAoe21heFdpZHRoOiAnMzAwJwogICAgICAgICAgICAKICAgICAgICAgICAgfSk7CgogICAgICAgICAgICAKICAgICAgICAgICAgICAgIHZhciBodG1sX2JjMDAxNjFhNmEyZDQ0NTk5ZTYwYTg0NmY2OWQ3YmVkID0gJChgPGRpdiBpZD0iaHRtbF9iYzAwMTYxYTZhMmQ0NDU5OWU2MGE4NDZmNjlkN2JlZCIgc3R5bGU9IndpZHRoOiAxMDAuMCU7IGhlaWdodDogMTAwLjAlOyI+TXQuIEhvb2QgTWVhZG93czwvZGl2PmApWzBdOwogICAgICAgICAgICAgICAgcG9wdXBfY2ViNzVmYjIzNTc5NDg3NmFmYWVhYjAwODkxNjU3MzEuc2V0Q29udGVudChodG1sX2JjMDAxNjFhNmEyZDQ0NTk5ZTYwYTg0NmY2OWQ3YmVkKTsKICAgICAgICAgICAgCgogICAgICAgICAgICBtYXJrZXJfNmE0MzI2MzQ0ZTBjNGIwYjg0MmI1YWNiMzhiZjEzOGMuYmluZFBvcHVwKHBvcHVwX2NlYjc1ZmIyMzU3OTQ4NzZhZmFlYWIwMDg5MTY1NzMxKQogICAgICAgICAgICA7CgogICAgICAgICAgICAKICAgICAgICAKICAgIAogICAgICAgIHZhciBtYXJrZXJfYjNhODgwYzU2YTVmNDgxOGIzNmI2MTE0MDJiZDJjNmMgPSBMLm1hcmtlcigKICAgICAgICAgICAgWzQ1LjMzMTEsIC0xMjEuNzExM10sCiAgICAgICAgICAgIHsKICAgICAgICAgICAgICAgIGljb246IG5ldyBMLkljb24uRGVmYXVsdCgpCiAgICAgICAgICAgICAgICB9CiAgICAgICAgICAgICkuYWRkVG8obWFwX2UxYzI4MjA1NjQ5YjRhMTA4NTg2OGVlZDRlZDk3NDYwKTsKICAgICAgICAKICAgIAoKICAgICAgICAgICAgICAgIHZhciBpY29uXzJhMDFjMjkwNDA2ODRiMjhhY2ZjYmQ1ZjAxOTdkMTYzID0gTC5Bd2Vzb21lTWFya2Vycy5pY29uKHsKICAgICAgICAgICAgICAgICAgICBpY29uOiAnaW5mby1zaWduJywKICAgICAgICAgICAgICAgICAgICBpY29uQ29sb3I6ICd3aGl0ZScsCiAgICAgICAgICAgICAgICAgICAgbWFya2VyQ29sb3I6ICdncmVlbicsCiAgICAgICAgICAgICAgICAgICAgcHJlZml4OiAnZ2x5cGhpY29uJywKICAgICAgICAgICAgICAgICAgICBleHRyYUNsYXNzZXM6ICdmYS1yb3RhdGUtMCcKICAgICAgICAgICAgICAgICAgICB9KTsKICAgICAgICAgICAgICAgIG1hcmtlcl9iM2E4ODBjNTZhNWY0ODE4YjM2YjYxMTQwMmJkMmM2Yy5zZXRJY29uKGljb25fMmEwMWMyOTA0MDY4NGIyOGFjZmNiZDVmMDE5N2QxNjMpOwogICAgICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgcG9wdXBfZGEyZjhkNDlkNGNhNGE2OTg4N2QyNDU3ODU1ZTdiZjggPSBMLnBvcHVwKHttYXhXaWR0aDogJzMwMCcKICAgICAgICAgICAgCiAgICAgICAgICAgIH0pOwoKICAgICAgICAgICAgCiAgICAgICAgICAgICAgICB2YXIgaHRtbF9iZTdjOWY3ZmFiMGU0YTFjYmVjNWU5MzBmYTk0NmFlNCA9ICQoYDxkaXYgaWQ9Imh0bWxfYmU3YzlmN2ZhYjBlNGExY2JlYzVlOTMwZmE5NDZhZTQiIHN0eWxlPSJ3aWR0aDogMTAwLjAlOyBoZWlnaHQ6IDEwMC4wJTsiPlRpbWJlcmxpbmUgTG9kZ2U8L2Rpdj5gKVswXTsKICAgICAgICAgICAgICAgIHBvcHVwX2RhMmY4ZDQ5ZDRjYTRhNjk4ODdkMjQ1Nzg1NWU3YmY4LnNldENvbnRlbnQoaHRtbF9iZTdjOWY3ZmFiMGU0YTFjYmVjNWU5MzBmYTk0NmFlNCk7CiAgICAgICAgICAgIAoKICAgICAgICAgICAgbWFya2VyX2IzYTg4MGM1NmE1ZjQ4MThiMzZiNjExNDAyYmQyYzZjLmJpbmRQb3B1cChwb3B1cF9kYTJmOGQ0OWQ0Y2E0YTY5ODg3ZDI0NTc4NTVlN2JmOCkKICAgICAgICAgICAgOwoKICAgICAgICAgICAgCiAgICAgICAgCiAgICAKICAgICAgICB2YXIgbWFya2VyX2RjZDQ4OGEyMGI4ZjQyMDZiMDQ3OGUxMTgzMTg1MjRjID0gTC5tYXJrZXIoCiAgICAgICAgICAgIFs0NS4zMywgLTEyMS42ODIzXSwKICAgICAgICAgICAgewogICAgICAgICAgICAgICAgaWNvbjogbmV3IEwuSWNvbi5EZWZhdWx0KCkKICAgICAgICAgICAgICAgIH0KICAgICAgICAgICAgKS5hZGRUbyhtYXBfZTFjMjgyMDU2NDliNGExMDg1ODY4ZWVkNGVkOTc0NjApOwogICAgICAgIAogICAgCgogICAgICAgICAgICAgICAgdmFyIGljb25fN2NjNWEzNTg1NGM1NDI1ZTk1MGEwNzVjZWYyOTQyN2IgPSBMLkF3ZXNvbWVNYXJrZXJzLmljb24oewogICAgICAgICAgICAgICAgICAgIGljb246ICdpbmZvLXNpZ24nLAogICAgICAgICAgICAgICAgICAgIGljb25Db2xvcjogJ3doaXRlJywKICAgICAgICAgICAgICAgICAgICBtYXJrZXJDb2xvcjogJ3JlZCcsCiAgICAgICAgICAgICAgICAgICAgcHJlZml4OiAnZ2x5cGhpY29uJywKICAgICAgICAgICAgICAgICAgICBleHRyYUNsYXNzZXM6ICdmYS1yb3RhdGUtMCcKICAgICAgICAgICAgICAgICAgICB9KTsKICAgICAgICAgICAgICAgIG1hcmtlcl9kY2Q0ODhhMjBiOGY0MjA2YjA0NzhlMTE4MzE4NTI0Yy5zZXRJY29uKGljb25fN2NjNWEzNTg1NGM1NDI1ZTk1MGEwNzVjZWYyOTQyN2IpOwogICAgICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgcG9wdXBfZDg2NTYxOGRiY2M4NDk0OWI3ZGIyYjE0M2VlNTdjMjkgPSBMLnBvcHVwKHttYXhXaWR0aDogJzMwMCcKICAgICAgICAgICAgCiAgICAgICAgICAgIH0pOwoKICAgICAgICAgICAgCiAgICAgICAgICAgICAgICB2YXIgaHRtbF8zZWFmMDQzMTJiNmM0ZjEzOGYyYWYyNzI2NjQwMzJmYiA9ICQoYDxkaXYgaWQ9Imh0bWxfM2VhZjA0MzEyYjZjNGYxMzhmMmFmMjcyNjY0MDMyZmIiIHN0eWxlPSJ3aWR0aDogMTAwLjAlOyBoZWlnaHQ6IDEwMC4wJTsiPlNvbWUgT3RoZXIgTG9jYXRpb248L2Rpdj5gKVswXTsKICAgICAgICAgICAgICAgIHBvcHVwX2Q4NjU2MThkYmNjODQ5NDliN2RiMmIxNDNlZTU3YzI5LnNldENvbnRlbnQoaHRtbF8zZWFmMDQzMTJiNmM0ZjEzOGYyYWYyNzI2NjQwMzJmYik7CiAgICAgICAgICAgIAoKICAgICAgICAgICAgbWFya2VyX2RjZDQ4OGEyMGI4ZjQyMDZiMDQ3OGUxMTgzMTg1MjRjLmJpbmRQb3B1cChwb3B1cF9kODY1NjE4ZGJjYzg0OTQ5YjdkYjJiMTQzZWU1N2MyOSkKICAgICAgICAgICAgOwoKICAgICAgICAgICAgCiAgICAgICAgCjwvc2NyaXB0Pg==" style="position:absolute;width:100%;height:100%;left:0;top:0;border:none !important;" allowfullscreen webkitallowfullscreen mozallowfullscreen></iframe></div></div>




```python
map_2 = folium.Map(location=[45.5236, -122.6750], tiles='Stamen Toner', 
                   zoom_start=13)
folium.Marker([45.5244, -122.6699], popup='The Waterfront' ).add_to(map_2)
folium.CircleMarker([45.5215, -122.6261], radius=50, 
                    popup='Laurelhurst Park', color='#3186cc', 
                    fill_color='#3186cc', ).add_to(map_2)
map_2
```




<div style="width:100%;"><div style="position:relative;width:100%;height:0;padding-bottom:60%;"><iframe src="data:text/html;charset=utf-8;base64,PCFET0NUWVBFIGh0bWw+CjxoZWFkPiAgICAKICAgIDxtZXRhIGh0dHAtZXF1aXY9ImNvbnRlbnQtdHlwZSIgY29udGVudD0idGV4dC9odG1sOyBjaGFyc2V0PVVURi04IiAvPgogICAgPHNjcmlwdD5MX1BSRUZFUl9DQU5WQVM9ZmFsc2U7IExfTk9fVE9VQ0g9ZmFsc2U7IExfRElTQUJMRV8zRD1mYWxzZTs8L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmpzIj48L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2FqYXguZ29vZ2xlYXBpcy5jb20vYWpheC9saWJzL2pxdWVyeS8xLjExLjEvanF1ZXJ5Lm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvanMvYm9vdHN0cmFwLm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9jZG5qcy5jbG91ZGZsYXJlLmNvbS9hamF4L2xpYnMvTGVhZmxldC5hd2Vzb21lLW1hcmtlcnMvMi4wLjIvbGVhZmxldC5hd2Vzb21lLW1hcmtlcnMuanMiPjwvc2NyaXB0PgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL21heGNkbi5ib290c3RyYXBjZG4uY29tL2Jvb3RzdHJhcC8zLjIuMC9jc3MvYm9vdHN0cmFwLm1pbi5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvY3NzL2Jvb3RzdHJhcC10aGVtZS5taW4uY3NzIi8+CiAgICA8bGluayByZWw9InN0eWxlc2hlZXQiIGhyZWY9Imh0dHBzOi8vbWF4Y2RuLmJvb3RzdHJhcGNkbi5jb20vZm9udC1hd2Vzb21lLzQuNi4zL2Nzcy9mb250LWF3ZXNvbWUubWluLmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2NkbmpzLmNsb3VkZmxhcmUuY29tL2FqYXgvbGlicy9MZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy8yLjAuMi9sZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9yYXdjZG4uZ2l0aGFjay5jb20vcHl0aG9uLXZpc3VhbGl6YXRpb24vZm9saXVtL21hc3Rlci9mb2xpdW0vdGVtcGxhdGVzL2xlYWZsZXQuYXdlc29tZS5yb3RhdGUuY3NzIi8+CiAgICA8c3R5bGU+aHRtbCwgYm9keSB7d2lkdGg6IDEwMCU7aGVpZ2h0OiAxMDAlO21hcmdpbjogMDtwYWRkaW5nOiAwO308L3N0eWxlPgogICAgPHN0eWxlPiNtYXAge3Bvc2l0aW9uOmFic29sdXRlO3RvcDowO2JvdHRvbTowO3JpZ2h0OjA7bGVmdDowO308L3N0eWxlPgogICAgCiAgICA8bWV0YSBuYW1lPSJ2aWV3cG9ydCIgY29udGVudD0id2lkdGg9ZGV2aWNlLXdpZHRoLAogICAgICAgIGluaXRpYWwtc2NhbGU9MS4wLCBtYXhpbXVtLXNjYWxlPTEuMCwgdXNlci1zY2FsYWJsZT1ubyIgLz4KICAgIDxzdHlsZT4jbWFwXzljN2RhOTk5MDk3ODQ3MjU4NTQwOGUzY2M0NjNiYTY2IHsKICAgICAgICBwb3NpdGlvbjogcmVsYXRpdmU7CiAgICAgICAgd2lkdGg6IDEwMC4wJTsKICAgICAgICBoZWlnaHQ6IDEwMC4wJTsKICAgICAgICBsZWZ0OiAwLjAlOwogICAgICAgIHRvcDogMC4wJTsKICAgICAgICB9CiAgICA8L3N0eWxlPgo8L2hlYWQ+Cjxib2R5PiAgICAKICAgIAogICAgPGRpdiBjbGFzcz0iZm9saXVtLW1hcCIgaWQ9Im1hcF85YzdkYTk5OTA5Nzg0NzI1ODU0MDhlM2NjNDYzYmE2NiIgPjwvZGl2Pgo8L2JvZHk+CjxzY3JpcHQ+ICAgIAogICAgCiAgICAKICAgICAgICB2YXIgYm91bmRzID0gbnVsbDsKICAgIAoKICAgIHZhciBtYXBfOWM3ZGE5OTkwOTc4NDcyNTg1NDA4ZTNjYzQ2M2JhNjYgPSBMLm1hcCgKICAgICAgICAnbWFwXzljN2RhOTk5MDk3ODQ3MjU4NTQwOGUzY2M0NjNiYTY2JywgewogICAgICAgIGNlbnRlcjogWzQ1LjUyMzYsIC0xMjIuNjc1XSwKICAgICAgICB6b29tOiAxMywKICAgICAgICBtYXhCb3VuZHM6IGJvdW5kcywKICAgICAgICBsYXllcnM6IFtdLAogICAgICAgIHdvcmxkQ29weUp1bXA6IGZhbHNlLAogICAgICAgIGNyczogTC5DUlMuRVBTRzM4NTcsCiAgICAgICAgem9vbUNvbnRyb2w6IHRydWUsCiAgICAgICAgfSk7CgogICAgCiAgICAKICAgIHZhciB0aWxlX2xheWVyX2JmOTU0ZDE0NzVkNzQ2YzhiZWFhMzZmZjM2YmU0MWI3ID0gTC50aWxlTGF5ZXIoCiAgICAgICAgJ2h0dHBzOi8vc3RhbWVuLXRpbGVzLXtzfS5hLnNzbC5mYXN0bHkubmV0L3RvbmVyL3t6fS97eH0ve3l9LnBuZycsCiAgICAgICAgewogICAgICAgICJhdHRyaWJ1dGlvbiI6IG51bGwsCiAgICAgICAgImRldGVjdFJldGluYSI6IGZhbHNlLAogICAgICAgICJtYXhOYXRpdmVab29tIjogMTgsCiAgICAgICAgIm1heFpvb20iOiAxOCwKICAgICAgICAibWluWm9vbSI6IDAsCiAgICAgICAgIm5vV3JhcCI6IGZhbHNlLAogICAgICAgICJvcGFjaXR5IjogMSwKICAgICAgICAic3ViZG9tYWlucyI6ICJhYmMiLAogICAgICAgICJ0bXMiOiBmYWxzZQp9KS5hZGRUbyhtYXBfOWM3ZGE5OTkwOTc4NDcyNTg1NDA4ZTNjYzQ2M2JhNjYpOwogICAgCiAgICAgICAgdmFyIG1hcmtlcl9iMmIxYmVlN2UzNDk0MWRhODAwNjMyZjVkZWI3NWRkMiA9IEwubWFya2VyKAogICAgICAgICAgICBbNDUuNTI0NCwgLTEyMi42Njk5XSwKICAgICAgICAgICAgewogICAgICAgICAgICAgICAgaWNvbjogbmV3IEwuSWNvbi5EZWZhdWx0KCkKICAgICAgICAgICAgICAgIH0KICAgICAgICAgICAgKS5hZGRUbyhtYXBfOWM3ZGE5OTkwOTc4NDcyNTg1NDA4ZTNjYzQ2M2JhNjYpOwogICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBwb3B1cF9jYTJjNTFlMGVhOTU0MDUyODc3OGZiZTQ4MDdmZWZjNSA9IEwucG9wdXAoe21heFdpZHRoOiAnMzAwJwogICAgICAgICAgICAKICAgICAgICAgICAgfSk7CgogICAgICAgICAgICAKICAgICAgICAgICAgICAgIHZhciBodG1sXzhmNGJlNDAxOGNjNTQ2OWQ4ZTJiZGVkMTFmYTJiYmE1ID0gJChgPGRpdiBpZD0iaHRtbF84ZjRiZTQwMThjYzU0NjlkOGUyYmRlZDExZmEyYmJhNSIgc3R5bGU9IndpZHRoOiAxMDAuMCU7IGhlaWdodDogMTAwLjAlOyI+VGhlIFdhdGVyZnJvbnQ8L2Rpdj5gKVswXTsKICAgICAgICAgICAgICAgIHBvcHVwX2NhMmM1MWUwZWE5NTQwNTI4Nzc4ZmJlNDgwN2ZlZmM1LnNldENvbnRlbnQoaHRtbF84ZjRiZTQwMThjYzU0NjlkOGUyYmRlZDExZmEyYmJhNSk7CiAgICAgICAgICAgIAoKICAgICAgICAgICAgbWFya2VyX2IyYjFiZWU3ZTM0OTQxZGE4MDA2MzJmNWRlYjc1ZGQyLmJpbmRQb3B1cChwb3B1cF9jYTJjNTFlMGVhOTU0MDUyODc3OGZiZTQ4MDdmZWZjNSkKICAgICAgICAgICAgOwoKICAgICAgICAgICAgCiAgICAgICAgCiAgICAKICAgICAgICAgICAgdmFyIGNpcmNsZV9tYXJrZXJfZTA1ODYyMTZmNDM0NDIxZWE1YzY5Yjk1ZmQxNDNiZTIgPSBMLmNpcmNsZU1hcmtlcigKICAgICAgICAgICAgICAgIFs0NS41MjE1LCAtMTIyLjYyNjFdLAogICAgICAgICAgICAgICAgewogICJidWJibGluZ01vdXNlRXZlbnRzIjogdHJ1ZSwKICAiY29sb3IiOiAiIzMxODZjYyIsCiAgImRhc2hBcnJheSI6IG51bGwsCiAgImRhc2hPZmZzZXQiOiBudWxsLAogICJmaWxsIjogdHJ1ZSwKICAiZmlsbENvbG9yIjogIiMzMTg2Y2MiLAogICJmaWxsT3BhY2l0eSI6IDAuMiwKICAiZmlsbFJ1bGUiOiAiZXZlbm9kZCIsCiAgImxpbmVDYXAiOiAicm91bmQiLAogICJsaW5lSm9pbiI6ICJyb3VuZCIsCiAgIm9wYWNpdHkiOiAxLjAsCiAgInJhZGl1cyI6IDUwLAogICJzdHJva2UiOiB0cnVlLAogICJ3ZWlnaHQiOiAzCn0KICAgICAgICAgICAgICAgICkKICAgICAgICAgICAgICAgIC5hZGRUbyhtYXBfOWM3ZGE5OTkwOTc4NDcyNTg1NDA4ZTNjYzQ2M2JhNjYpOwogICAgICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgcG9wdXBfZDIyZmMxY2JhMDEzNDQ3OWJkYjgyNmE5YjkzMTcwMzQgPSBMLnBvcHVwKHttYXhXaWR0aDogJzMwMCcKICAgICAgICAgICAgCiAgICAgICAgICAgIH0pOwoKICAgICAgICAgICAgCiAgICAgICAgICAgICAgICB2YXIgaHRtbF80MDUwNmRmNTE5YmE0NjRjYjViMzBlN2YyYzBjNTM3OSA9ICQoYDxkaXYgaWQ9Imh0bWxfNDA1MDZkZjUxOWJhNDY0Y2I1YjMwZTdmMmMwYzUzNzkiIHN0eWxlPSJ3aWR0aDogMTAwLjAlOyBoZWlnaHQ6IDEwMC4wJTsiPkxhdXJlbGh1cnN0IFBhcms8L2Rpdj5gKVswXTsKICAgICAgICAgICAgICAgIHBvcHVwX2QyMmZjMWNiYTAxMzQ0NzliZGI4MjZhOWI5MzE3MDM0LnNldENvbnRlbnQoaHRtbF80MDUwNmRmNTE5YmE0NjRjYjViMzBlN2YyYzBjNTM3OSk7CiAgICAgICAgICAgIAoKICAgICAgICAgICAgY2lyY2xlX21hcmtlcl9lMDU4NjIxNmY0MzQ0MjFlYTVjNjliOTVmZDE0M2JlMi5iaW5kUG9wdXAocG9wdXBfZDIyZmMxY2JhMDEzNDQ3OWJkYjgyNmE5YjkzMTcwMzQpCiAgICAgICAgICAgIDsKCiAgICAgICAgICAgIAogICAgICAgIAo8L3NjcmlwdD4=" style="position:absolute;width:100%;height:100%;left:0;top:0;border:none !important;" allowfullscreen webkitallowfullscreen mozallowfullscreen></iframe></div></div>




```python
map_5 = folium.Map(location=[45.5236, -122.6750], zoom_start=13)
folium.RegularPolygonMarker([45.5012, -122.6655], 
                            popup='Ross Island Bridge', fill_color='#132b5e', 
                            number_of_sides=3, radius=10).add_to(map_5)
folium.RegularPolygonMarker([45.5132, -122.6708], 
                            popup='Hawthorne Bridge', fill_color='#45647d', 
                            number_of_sides=4, radius=10).add_to(map_5)
folium.RegularPolygonMarker([45.5275, -122.6692], 
                            popup='Steel Bridge', fill_color='#769d96', 
                            number_of_sides=6, radius=10).add_to(map_5)
folium.RegularPolygonMarker([45.5318, -122.6745], 
                            popup='Broadway Bridge', fill_color='#769d96', 
                            number_of_sides=8, radius=10).add_to(map_5)
map_5
```




<div style="width:100%;"><div style="position:relative;width:100%;height:0;padding-bottom:60%;"><iframe src="data:text/html;charset=utf-8;base64,PCFET0NUWVBFIGh0bWw+CjxoZWFkPiAgICAKICAgIDxtZXRhIGh0dHAtZXF1aXY9ImNvbnRlbnQtdHlwZSIgY29udGVudD0idGV4dC9odG1sOyBjaGFyc2V0PVVURi04IiAvPgogICAgPHNjcmlwdD5MX1BSRUZFUl9DQU5WQVM9ZmFsc2U7IExfTk9fVE9VQ0g9ZmFsc2U7IExfRElTQUJMRV8zRD1mYWxzZTs8L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmpzIj48L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2FqYXguZ29vZ2xlYXBpcy5jb20vYWpheC9saWJzL2pxdWVyeS8xLjExLjEvanF1ZXJ5Lm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvanMvYm9vdHN0cmFwLm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9jZG5qcy5jbG91ZGZsYXJlLmNvbS9hamF4L2xpYnMvTGVhZmxldC5hd2Vzb21lLW1hcmtlcnMvMi4wLjIvbGVhZmxldC5hd2Vzb21lLW1hcmtlcnMuanMiPjwvc2NyaXB0PgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL21heGNkbi5ib290c3RyYXBjZG4uY29tL2Jvb3RzdHJhcC8zLjIuMC9jc3MvYm9vdHN0cmFwLm1pbi5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvY3NzL2Jvb3RzdHJhcC10aGVtZS5taW4uY3NzIi8+CiAgICA8bGluayByZWw9InN0eWxlc2hlZXQiIGhyZWY9Imh0dHBzOi8vbWF4Y2RuLmJvb3RzdHJhcGNkbi5jb20vZm9udC1hd2Vzb21lLzQuNi4zL2Nzcy9mb250LWF3ZXNvbWUubWluLmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2NkbmpzLmNsb3VkZmxhcmUuY29tL2FqYXgvbGlicy9MZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy8yLjAuMi9sZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9yYXdjZG4uZ2l0aGFjay5jb20vcHl0aG9uLXZpc3VhbGl6YXRpb24vZm9saXVtL21hc3Rlci9mb2xpdW0vdGVtcGxhdGVzL2xlYWZsZXQuYXdlc29tZS5yb3RhdGUuY3NzIi8+CiAgICA8c3R5bGU+aHRtbCwgYm9keSB7d2lkdGg6IDEwMCU7aGVpZ2h0OiAxMDAlO21hcmdpbjogMDtwYWRkaW5nOiAwO308L3N0eWxlPgogICAgPHN0eWxlPiNtYXAge3Bvc2l0aW9uOmFic29sdXRlO3RvcDowO2JvdHRvbTowO3JpZ2h0OjA7bGVmdDowO308L3N0eWxlPgogICAgCiAgICA8bWV0YSBuYW1lPSJ2aWV3cG9ydCIgY29udGVudD0id2lkdGg9ZGV2aWNlLXdpZHRoLAogICAgICAgIGluaXRpYWwtc2NhbGU9MS4wLCBtYXhpbXVtLXNjYWxlPTEuMCwgdXNlci1zY2FsYWJsZT1ubyIgLz4KICAgIDxzdHlsZT4jbWFwXzBmNmZkZjE5MmM2ZTRkN2U4OGU1YTM2ZDI5MmM5MmI2IHsKICAgICAgICBwb3NpdGlvbjogcmVsYXRpdmU7CiAgICAgICAgd2lkdGg6IDEwMC4wJTsKICAgICAgICBoZWlnaHQ6IDEwMC4wJTsKICAgICAgICBsZWZ0OiAwLjAlOwogICAgICAgIHRvcDogMC4wJTsKICAgICAgICB9CiAgICA8L3N0eWxlPgogICAgPHNjcmlwdCBzcmM9Imh0dHBzOi8vY2RuanMuY2xvdWRmbGFyZS5jb20vYWpheC9saWJzL2xlYWZsZXQtZHZmLzAuMy4wL2xlYWZsZXQtZHZmLm1hcmtlcnMubWluLmpzIj48L3NjcmlwdD4KPC9oZWFkPgo8Ym9keT4gICAgCiAgICAKICAgIDxkaXYgY2xhc3M9ImZvbGl1bS1tYXAiIGlkPSJtYXBfMGY2ZmRmMTkyYzZlNGQ3ZTg4ZTVhMzZkMjkyYzkyYjYiID48L2Rpdj4KPC9ib2R5Pgo8c2NyaXB0PiAgICAKICAgIAogICAgCiAgICAgICAgdmFyIGJvdW5kcyA9IG51bGw7CiAgICAKCiAgICB2YXIgbWFwXzBmNmZkZjE5MmM2ZTRkN2U4OGU1YTM2ZDI5MmM5MmI2ID0gTC5tYXAoCiAgICAgICAgJ21hcF8wZjZmZGYxOTJjNmU0ZDdlODhlNWEzNmQyOTJjOTJiNicsIHsKICAgICAgICBjZW50ZXI6IFs0NS41MjM2LCAtMTIyLjY3NV0sCiAgICAgICAgem9vbTogMTMsCiAgICAgICAgbWF4Qm91bmRzOiBib3VuZHMsCiAgICAgICAgbGF5ZXJzOiBbXSwKICAgICAgICB3b3JsZENvcHlKdW1wOiBmYWxzZSwKICAgICAgICBjcnM6IEwuQ1JTLkVQU0czODU3LAogICAgICAgIHpvb21Db250cm9sOiB0cnVlLAogICAgICAgIH0pOwoKICAgIAogICAgCiAgICB2YXIgdGlsZV9sYXllcl84N2YxNTExZWZmOTc0ODZmYmE0NmRkYjBhOGY0M2RhNSA9IEwudGlsZUxheWVyKAogICAgICAgICdodHRwczovL3tzfS50aWxlLm9wZW5zdHJlZXRtYXAub3JnL3t6fS97eH0ve3l9LnBuZycsCiAgICAgICAgewogICAgICAgICJhdHRyaWJ1dGlvbiI6IG51bGwsCiAgICAgICAgImRldGVjdFJldGluYSI6IGZhbHNlLAogICAgICAgICJtYXhOYXRpdmVab29tIjogMTgsCiAgICAgICAgIm1heFpvb20iOiAxOCwKICAgICAgICAibWluWm9vbSI6IDAsCiAgICAgICAgIm5vV3JhcCI6IGZhbHNlLAogICAgICAgICJvcGFjaXR5IjogMSwKICAgICAgICAic3ViZG9tYWlucyI6ICJhYmMiLAogICAgICAgICJ0bXMiOiBmYWxzZQp9KS5hZGRUbyhtYXBfMGY2ZmRmMTkyYzZlNGQ3ZTg4ZTVhMzZkMjkyYzkyYjYpOwogICAgCiAgICAgICAgICAgIHZhciByZWd1bGFyX3BvbHlnb25fbWFya2VyXzgxN2MyN2Q2MjkwZDQ1YTY4YTk3MzhkNzBjNDRhMjBiID0gbmV3IEwuUmVndWxhclBvbHlnb25NYXJrZXIoCiAgICAgICAgICAgICAgICBuZXcgTC5MYXRMbmcoNDUuNTAxMiwtMTIyLjY2NTUpLAogICAgICAgICAgICAgICAgewogICAgICAgICAgICAgICAgICAgIGljb24gOiBuZXcgTC5JY29uLkRlZmF1bHQoKSwKICAgICAgICAgICAgICAgICAgICBjb2xvcjogJ2JsYWNrJywKICAgICAgICAgICAgICAgICAgICBvcGFjaXR5OiAxLAogICAgICAgICAgICAgICAgICAgIHdlaWdodDogMiwKICAgICAgICAgICAgICAgICAgICBmaWxsQ29sb3I6ICcjMTMyYjVlJywKICAgICAgICAgICAgICAgICAgICBmaWxsT3BhY2l0eTogMSwKICAgICAgICAgICAgICAgICAgICBudW1iZXJPZlNpZGVzOiAzLAogICAgICAgICAgICAgICAgICAgIHJvdGF0aW9uOiAwLAogICAgICAgICAgICAgICAgICAgIHJhZGl1czogMTAKICAgICAgICAgICAgICAgICAgICB9CiAgICAgICAgICAgICAgICApLmFkZFRvKG1hcF8wZjZmZGYxOTJjNmU0ZDdlODhlNWEzNmQyOTJjOTJiNik7CiAgICAgICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBwb3B1cF83NzI0MWIxYzIyOTI0YmY5OGEwODUzMjQ2MTAwM2FhZCA9IEwucG9wdXAoe21heFdpZHRoOiAnMzAwJwogICAgICAgICAgICAKICAgICAgICAgICAgfSk7CgogICAgICAgICAgICAKICAgICAgICAgICAgICAgIHZhciBodG1sXzBiNmU0MDE2NDUyNDQ0Y2E4M2EwMjA1Y2IwMzRmNDdhID0gJChgPGRpdiBpZD0iaHRtbF8wYjZlNDAxNjQ1MjQ0NGNhODNhMDIwNWNiMDM0ZjQ3YSIgc3R5bGU9IndpZHRoOiAxMDAuMCU7IGhlaWdodDogMTAwLjAlOyI+Um9zcyBJc2xhbmQgQnJpZGdlPC9kaXY+YClbMF07CiAgICAgICAgICAgICAgICBwb3B1cF83NzI0MWIxYzIyOTI0YmY5OGEwODUzMjQ2MTAwM2FhZC5zZXRDb250ZW50KGh0bWxfMGI2ZTQwMTY0NTI0NDRjYTgzYTAyMDVjYjAzNGY0N2EpOwogICAgICAgICAgICAKCiAgICAgICAgICAgIHJlZ3VsYXJfcG9seWdvbl9tYXJrZXJfODE3YzI3ZDYyOTBkNDVhNjhhOTczOGQ3MGM0NGEyMGIuYmluZFBvcHVwKHBvcHVwXzc3MjQxYjFjMjI5MjRiZjk4YTA4NTMyNDYxMDAzYWFkKQogICAgICAgICAgICA7CgogICAgICAgICAgICAKICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgcmVndWxhcl9wb2x5Z29uX21hcmtlcl9hNjNiN2M1MzA0MWY0M2Q5ODVhNzBmYTlhOTBlZjkwMCA9IG5ldyBMLlJlZ3VsYXJQb2x5Z29uTWFya2VyKAogICAgICAgICAgICAgICAgbmV3IEwuTGF0TG5nKDQ1LjUxMzIsLTEyMi42NzA4KSwKICAgICAgICAgICAgICAgIHsKICAgICAgICAgICAgICAgICAgICBpY29uIDogbmV3IEwuSWNvbi5EZWZhdWx0KCksCiAgICAgICAgICAgICAgICAgICAgY29sb3I6ICdibGFjaycsCiAgICAgICAgICAgICAgICAgICAgb3BhY2l0eTogMSwKICAgICAgICAgICAgICAgICAgICB3ZWlnaHQ6IDIsCiAgICAgICAgICAgICAgICAgICAgZmlsbENvbG9yOiAnIzQ1NjQ3ZCcsCiAgICAgICAgICAgICAgICAgICAgZmlsbE9wYWNpdHk6IDEsCiAgICAgICAgICAgICAgICAgICAgbnVtYmVyT2ZTaWRlczogNCwKICAgICAgICAgICAgICAgICAgICByb3RhdGlvbjogMCwKICAgICAgICAgICAgICAgICAgICByYWRpdXM6IDEwCiAgICAgICAgICAgICAgICAgICAgfQogICAgICAgICAgICAgICAgKS5hZGRUbyhtYXBfMGY2ZmRmMTkyYzZlNGQ3ZTg4ZTVhMzZkMjkyYzkyYjYpOwogICAgICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgcG9wdXBfZWJhMzY1MDE1ZTg1NDIzY2IzZDIwYTMyZDliOTUyMzcgPSBMLnBvcHVwKHttYXhXaWR0aDogJzMwMCcKICAgICAgICAgICAgCiAgICAgICAgICAgIH0pOwoKICAgICAgICAgICAgCiAgICAgICAgICAgICAgICB2YXIgaHRtbF80ODg1OWU0NGI2Njk0MTk5OWVlN2I5ZWI3ZWEyMGJmYiA9ICQoYDxkaXYgaWQ9Imh0bWxfNDg4NTllNDRiNjY5NDE5OTllZTdiOWViN2VhMjBiZmIiIHN0eWxlPSJ3aWR0aDogMTAwLjAlOyBoZWlnaHQ6IDEwMC4wJTsiPkhhd3Rob3JuZSBCcmlkZ2U8L2Rpdj5gKVswXTsKICAgICAgICAgICAgICAgIHBvcHVwX2ViYTM2NTAxNWU4NTQyM2NiM2QyMGEzMmQ5Yjk1MjM3LnNldENvbnRlbnQoaHRtbF80ODg1OWU0NGI2Njk0MTk5OWVlN2I5ZWI3ZWEyMGJmYik7CiAgICAgICAgICAgIAoKICAgICAgICAgICAgcmVndWxhcl9wb2x5Z29uX21hcmtlcl9hNjNiN2M1MzA0MWY0M2Q5ODVhNzBmYTlhOTBlZjkwMC5iaW5kUG9wdXAocG9wdXBfZWJhMzY1MDE1ZTg1NDIzY2IzZDIwYTMyZDliOTUyMzcpCiAgICAgICAgICAgIDsKCiAgICAgICAgICAgIAogICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciByZWd1bGFyX3BvbHlnb25fbWFya2VyXzAwN2VlMjgyZjlmZjRiMjE5ODNlODk1Zjc5ZTAyYTk4ID0gbmV3IEwuUmVndWxhclBvbHlnb25NYXJrZXIoCiAgICAgICAgICAgICAgICBuZXcgTC5MYXRMbmcoNDUuNTI3NSwtMTIyLjY2OTIpLAogICAgICAgICAgICAgICAgewogICAgICAgICAgICAgICAgICAgIGljb24gOiBuZXcgTC5JY29uLkRlZmF1bHQoKSwKICAgICAgICAgICAgICAgICAgICBjb2xvcjogJ2JsYWNrJywKICAgICAgICAgICAgICAgICAgICBvcGFjaXR5OiAxLAogICAgICAgICAgICAgICAgICAgIHdlaWdodDogMiwKICAgICAgICAgICAgICAgICAgICBmaWxsQ29sb3I6ICcjNzY5ZDk2JywKICAgICAgICAgICAgICAgICAgICBmaWxsT3BhY2l0eTogMSwKICAgICAgICAgICAgICAgICAgICBudW1iZXJPZlNpZGVzOiA2LAogICAgICAgICAgICAgICAgICAgIHJvdGF0aW9uOiAwLAogICAgICAgICAgICAgICAgICAgIHJhZGl1czogMTAKICAgICAgICAgICAgICAgICAgICB9CiAgICAgICAgICAgICAgICApLmFkZFRvKG1hcF8wZjZmZGYxOTJjNmU0ZDdlODhlNWEzNmQyOTJjOTJiNik7CiAgICAgICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBwb3B1cF80YmU1NjlmZTE2NTk0OWI2YjhhOTkzZDMxOWYxODJiZSA9IEwucG9wdXAoe21heFdpZHRoOiAnMzAwJwogICAgICAgICAgICAKICAgICAgICAgICAgfSk7CgogICAgICAgICAgICAKICAgICAgICAgICAgICAgIHZhciBodG1sXzljMGVlNTRhOTRhZDQ0NTY4YTJmNThjOWEyODU3NDRhID0gJChgPGRpdiBpZD0iaHRtbF85YzBlZTU0YTk0YWQ0NDU2OGEyZjU4YzlhMjg1NzQ0YSIgc3R5bGU9IndpZHRoOiAxMDAuMCU7IGhlaWdodDogMTAwLjAlOyI+U3RlZWwgQnJpZGdlPC9kaXY+YClbMF07CiAgICAgICAgICAgICAgICBwb3B1cF80YmU1NjlmZTE2NTk0OWI2YjhhOTkzZDMxOWYxODJiZS5zZXRDb250ZW50KGh0bWxfOWMwZWU1NGE5NGFkNDQ1NjhhMmY1OGM5YTI4NTc0NGEpOwogICAgICAgICAgICAKCiAgICAgICAgICAgIHJlZ3VsYXJfcG9seWdvbl9tYXJrZXJfMDA3ZWUyODJmOWZmNGIyMTk4M2U4OTVmNzllMDJhOTguYmluZFBvcHVwKHBvcHVwXzRiZTU2OWZlMTY1OTQ5YjZiOGE5OTNkMzE5ZjE4MmJlKQogICAgICAgICAgICA7CgogICAgICAgICAgICAKICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgcmVndWxhcl9wb2x5Z29uX21hcmtlcl8wMWUyMTBhODIyZDY0YmU4YWQxMTdlMjhiYWIxNGEyOSA9IG5ldyBMLlJlZ3VsYXJQb2x5Z29uTWFya2VyKAogICAgICAgICAgICAgICAgbmV3IEwuTGF0TG5nKDQ1LjUzMTgsLTEyMi42NzQ1KSwKICAgICAgICAgICAgICAgIHsKICAgICAgICAgICAgICAgICAgICBpY29uIDogbmV3IEwuSWNvbi5EZWZhdWx0KCksCiAgICAgICAgICAgICAgICAgICAgY29sb3I6ICdibGFjaycsCiAgICAgICAgICAgICAgICAgICAgb3BhY2l0eTogMSwKICAgICAgICAgICAgICAgICAgICB3ZWlnaHQ6IDIsCiAgICAgICAgICAgICAgICAgICAgZmlsbENvbG9yOiAnIzc2OWQ5NicsCiAgICAgICAgICAgICAgICAgICAgZmlsbE9wYWNpdHk6IDEsCiAgICAgICAgICAgICAgICAgICAgbnVtYmVyT2ZTaWRlczogOCwKICAgICAgICAgICAgICAgICAgICByb3RhdGlvbjogMCwKICAgICAgICAgICAgICAgICAgICByYWRpdXM6IDEwCiAgICAgICAgICAgICAgICAgICAgfQogICAgICAgICAgICAgICAgKS5hZGRUbyhtYXBfMGY2ZmRmMTkyYzZlNGQ3ZTg4ZTVhMzZkMjkyYzkyYjYpOwogICAgICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgcG9wdXBfNDc5ODJmMjFjY2VkNDU5NDkzNDEwMDZmNTlkNWU1YWUgPSBMLnBvcHVwKHttYXhXaWR0aDogJzMwMCcKICAgICAgICAgICAgCiAgICAgICAgICAgIH0pOwoKICAgICAgICAgICAgCiAgICAgICAgICAgICAgICB2YXIgaHRtbF8xYTA0YWE2MDUzOWQ0Y2ZjOGQzZjIyMGQ1MjBhNzgyMSA9ICQoYDxkaXYgaWQ9Imh0bWxfMWEwNGFhNjA1MzlkNGNmYzhkM2YyMjBkNTIwYTc4MjEiIHN0eWxlPSJ3aWR0aDogMTAwLjAlOyBoZWlnaHQ6IDEwMC4wJTsiPkJyb2Fkd2F5IEJyaWRnZTwvZGl2PmApWzBdOwogICAgICAgICAgICAgICAgcG9wdXBfNDc5ODJmMjFjY2VkNDU5NDkzNDEwMDZmNTlkNWU1YWUuc2V0Q29udGVudChodG1sXzFhMDRhYTYwNTM5ZDRjZmM4ZDNmMjIwZDUyMGE3ODIxKTsKICAgICAgICAgICAgCgogICAgICAgICAgICByZWd1bGFyX3BvbHlnb25fbWFya2VyXzAxZTIxMGE4MjJkNjRiZThhZDExN2UyOGJhYjE0YTI5LmJpbmRQb3B1cChwb3B1cF80Nzk4MmYyMWNjZWQ0NTk0OTM0MTAwNmY1OWQ1ZTVhZSkKICAgICAgICAgICAgOwoKICAgICAgICAgICAgCiAgICAgICAgCjwvc2NyaXB0Pg==" style="position:absolute;width:100%;height:100%;left:0;top:0;border:none !important;" allowfullscreen webkitallowfullscreen mozallowfullscreen></iframe></div></div>




```python
import folium
import pandas as pd
```


```python
state_unemployment = '../data/02. folium_US_Unemployment_Oct2012.csv'

state_data = pd.read_csv(state_unemployment)
state_data.head()
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
      <th>State</th>
      <th>Unemployment</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>AL</td>
      <td>7.1</td>
    </tr>
    <tr>
      <th>1</th>
      <td>AK</td>
      <td>6.8</td>
    </tr>
    <tr>
      <th>2</th>
      <td>AZ</td>
      <td>8.1</td>
    </tr>
    <tr>
      <th>3</th>
      <td>AR</td>
      <td>7.2</td>
    </tr>
    <tr>
      <th>4</th>
      <td>CA</td>
      <td>10.1</td>
    </tr>
  </tbody>
</table>
</div>




```python
state_geo = '../data/02. folium_us-states.json'

map = folium.Map(location=[40, -98], zoom_start=4)
map.choropleth(geo_data=state_geo, data=state_data,
             columns=['State', 'Unemployment'],
             key_on='feature.id',
             fill_color='YlGn',
             legend_name='Unemployment Rate (%)')
map
```

    C:\ProgramData\Anaconda3\lib\site-packages\folium\folium.py:432: FutureWarning: The choropleth  method has been deprecated. Instead use the new Choropleth class, which has the same arguments. See the example notebook 'GeoJSON_and_choropleth' for how to do this.
      FutureWarning
    




<div style="width:100%;"><div style="position:relative;width:100%;height:0;padding-bottom:60%;"><iframe src="data:text/html;charset=utf-8;base64,PCFET0NUWVBFIGh0bWw+CjxoZWFkPiAgICAKICAgIDxtZXRhIGh0dHAtZXF1aXY9ImNvbnRlbnQtdHlwZSIgY29udGVudD0idGV4dC9odG1sOyBjaGFyc2V0PVVURi04IiAvPgogICAgPHNjcmlwdD5MX1BSRUZFUl9DQU5WQVM9ZmFsc2U7IExfTk9fVE9VQ0g9ZmFsc2U7IExfRElTQUJMRV8zRD1mYWxzZTs8L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmpzIj48L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2FqYXguZ29vZ2xlYXBpcy5jb20vYWpheC9saWJzL2pxdWVyeS8xLjExLjEvanF1ZXJ5Lm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvanMvYm9vdHN0cmFwLm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9jZG5qcy5jbG91ZGZsYXJlLmNvbS9hamF4L2xpYnMvTGVhZmxldC5hd2Vzb21lLW1hcmtlcnMvMi4wLjIvbGVhZmxldC5hd2Vzb21lLW1hcmtlcnMuanMiPjwvc2NyaXB0PgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL21heGNkbi5ib290c3RyYXBjZG4uY29tL2Jvb3RzdHJhcC8zLjIuMC9jc3MvYm9vdHN0cmFwLm1pbi5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvY3NzL2Jvb3RzdHJhcC10aGVtZS5taW4uY3NzIi8+CiAgICA8bGluayByZWw9InN0eWxlc2hlZXQiIGhyZWY9Imh0dHBzOi8vbWF4Y2RuLmJvb3RzdHJhcGNkbi5jb20vZm9udC1hd2Vzb21lLzQuNi4zL2Nzcy9mb250LWF3ZXNvbWUubWluLmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2NkbmpzLmNsb3VkZmxhcmUuY29tL2FqYXgvbGlicy9MZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy8yLjAuMi9sZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9yYXdjZG4uZ2l0aGFjay5jb20vcHl0aG9uLXZpc3VhbGl6YXRpb24vZm9saXVtL21hc3Rlci9mb2xpdW0vdGVtcGxhdGVzL2xlYWZsZXQuYXdlc29tZS5yb3RhdGUuY3NzIi8+CiAgICA8c3R5bGU+aHRtbCwgYm9keSB7d2lkdGg6IDEwMCU7aGVpZ2h0OiAxMDAlO21hcmdpbjogMDtwYWRkaW5nOiAwO308L3N0eWxlPgogICAgPHN0eWxlPiNtYXAge3Bvc2l0aW9uOmFic29sdXRlO3RvcDowO2JvdHRvbTowO3JpZ2h0OjA7bGVmdDowO308L3N0eWxlPgogICAgCiAgICA8bWV0YSBuYW1lPSJ2aWV3cG9ydCIgY29udGVudD0id2lkdGg9ZGV2aWNlLXdpZHRoLAogICAgICAgIGluaXRpYWwtc2NhbGU9MS4wLCBtYXhpbXVtLXNjYWxlPTEuMCwgdXNlci1zY2FsYWJsZT1ubyIgLz4KICAgIDxzdHlsZT4jbWFwXzU0MTcxMDljZjExOTQ3YWY5Njg3OWQwMGU0OGQ4MjE2IHsKICAgICAgICBwb3NpdGlvbjogcmVsYXRpdmU7CiAgICAgICAgd2lkdGg6IDEwMC4wJTsKICAgICAgICBoZWlnaHQ6IDEwMC4wJTsKICAgICAgICBsZWZ0OiAwLjAlOwogICAgICAgIHRvcDogMC4wJTsKICAgICAgICB9CiAgICA8L3N0eWxlPgogICAgPHNjcmlwdCBzcmM9Imh0dHBzOi8vY2RuanMuY2xvdWRmbGFyZS5jb20vYWpheC9saWJzL2QzLzMuNS41L2QzLm1pbi5qcyI+PC9zY3JpcHQ+CjwvaGVhZD4KPGJvZHk+ICAgIAogICAgCiAgICA8ZGl2IGNsYXNzPSJmb2xpdW0tbWFwIiBpZD0ibWFwXzU0MTcxMDljZjExOTQ3YWY5Njg3OWQwMGU0OGQ4MjE2IiA+PC9kaXY+CjwvYm9keT4KPHNjcmlwdD4gICAgCiAgICAKICAgIAogICAgICAgIHZhciBib3VuZHMgPSBudWxsOwogICAgCgogICAgdmFyIG1hcF81NDE3MTA5Y2YxMTk0N2FmOTY4NzlkMDBlNDhkODIxNiA9IEwubWFwKAogICAgICAgICdtYXBfNTQxNzEwOWNmMTE5NDdhZjk2ODc5ZDAwZTQ4ZDgyMTYnLCB7CiAgICAgICAgY2VudGVyOiBbNDAsIC05OF0sCiAgICAgICAgem9vbTogNCwKICAgICAgICBtYXhCb3VuZHM6IGJvdW5kcywKICAgICAgICBsYXllcnM6IFtdLAogICAgICAgIHdvcmxkQ29weUp1bXA6IGZhbHNlLAogICAgICAgIGNyczogTC5DUlMuRVBTRzM4NTcsCiAgICAgICAgem9vbUNvbnRyb2w6IHRydWUsCiAgICAgICAgfSk7CgogICAgCiAgICAKICAgIHZhciB0aWxlX2xheWVyXzEzN2NjYTg1YjM3MDQwOTQ4ZTg5OTg1MTY3ZWJkMDVhID0gTC50aWxlTGF5ZXIoCiAgICAgICAgJ2h0dHBzOi8ve3N9LnRpbGUub3BlbnN0cmVldG1hcC5vcmcve3p9L3t4fS97eX0ucG5nJywKICAgICAgICB7CiAgICAgICAgImF0dHJpYnV0aW9uIjogbnVsbCwKICAgICAgICAiZGV0ZWN0UmV0aW5hIjogZmFsc2UsCiAgICAgICAgIm1heE5hdGl2ZVpvb20iOiAxOCwKICAgICAgICAibWF4Wm9vbSI6IDE4LAogICAgICAgICJtaW5ab29tIjogMCwKICAgICAgICAibm9XcmFwIjogZmFsc2UsCiAgICAgICAgIm9wYWNpdHkiOiAxLAogICAgICAgICJzdWJkb21haW5zIjogImFiYyIsCiAgICAgICAgInRtcyI6IGZhbHNlCn0pLmFkZFRvKG1hcF81NDE3MTA5Y2YxMTk0N2FmOTY4NzlkMDBlNDhkODIxNik7CiAgICAKICAgICAgICAgICAgdmFyIGNob3JvcGxldGhfODNkYmNmYTFiOTA4NGQ1ZmI1ZTgwOTgzNGI3Mzc5ZWMgPSBMLmZlYXR1cmVHcm91cCgKICAgICAgICAgICAgICAgICkuYWRkVG8obWFwXzU0MTcxMDljZjExOTQ3YWY5Njg3OWQwMGU0OGQ4MjE2KTsKICAgICAgICAKICAgIAogICAgICAgIAogICAgICAgIHZhciBnZW9fanNvbl9jYjgwOWE4MDhiODU0NWVjODExMWI0YmE3YzA4OTdlYiA9IEwuZ2VvSnNvbigKICAgICAgICAgICAgeyJmZWF0dXJlcyI6IFt7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWy04Ny4zNTkyOTYsIDM1LjAwMTE4XSwgWy04NS42MDY2NzUsIDM0Ljk4NDc0OV0sIFstODUuNDMxNDEzLCAzNC4xMjQ4NjldLCBbLTg1LjE4NDk1MSwgMzIuODU5Njk2XSwgWy04NS4wNjk5MzUsIDMyLjU4MDM3Ml0sIFstODQuOTYwMzk3LCAzMi40MjE1NDFdLCBbLTg1LjAwNDIxMiwgMzIuMzIyOTU2XSwgWy04NC44ODkxOTYsIDMyLjI2MjcwOV0sIFstODUuMDU4OTgxLCAzMi4xMzY3NF0sIFstODUuMDUzNTA0LCAzMi4wMTA3N10sIFstODUuMTQxMTM2LCAzMS44NDA5ODVdLCBbLTg1LjA0MjU1MSwgMzEuNTM5NzUzXSwgWy04NS4xMTM3NTEsIDMxLjI3Njg2XSwgWy04NS4wMDQyMTIsIDMxLjAwMzAxM10sIFstODUuNDk3MTM3LCAzMC45OTc1MzZdLCBbLTg3LjYwMDI4MiwgMzAuOTk3NTM2XSwgWy04Ny42MzMxNDMsIDMwLjg2NjA5XSwgWy04Ny40MDg1ODksIDMwLjY3NDM5N10sIFstODcuNDQ2OTI3LCAzMC41MTAwODhdLCBbLTg3LjM3MDI1LCAzMC40Mjc5MzRdLCBbLTg3LjUxODEyOCwgMzAuMjgwMDU3XSwgWy04Ny42NTUwNTEsIDMwLjI0NzE5NV0sIFstODcuOTA2OTksIDMwLjQxMTUwNF0sIFstODcuOTM0Mzc1LCAzMC42NTc5NjZdLCBbLTg4LjAxMTA1MiwgMzAuNjg1MzUxXSwgWy04OC4xMDQxNiwgMzAuNDk5MTM1XSwgWy04OC4xMzcwMjIsIDMwLjMxODM5Nl0sIFstODguMzk0NDM4LCAzMC4zNjc2ODhdLCBbLTg4LjQ3MTExNSwgMzEuODk1NzU0XSwgWy04OC4yNDEwODQsIDMzLjc5NjI1M10sIFstODguMDk4NjgzLCAzNC44OTE2NDFdLCBbLTg4LjIwMjc0NSwgMzQuOTk1NzAzXSwgWy04Ny4zNTkyOTYsIDM1LjAwMTE4XV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJBTCIsICJwcm9wZXJ0aWVzIjogeyJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiQWxhYmFtYSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiM3OGM2NzkiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbW1stMTMxLjYwMjAyMSwgNTUuMTE3OTgyXSwgWy0xMzEuNTY5MTU5LCA1NS4yODIyOV0sIFstMTMxLjM1NTU1OCwgNTUuMTgzNzA1XSwgWy0xMzEuMzg4NDIsIDU1LjAxMzkyXSwgWy0xMzEuNjQ1ODM2LCA1NS4wMzU4MjddLCBbLTEzMS42MDIwMjEsIDU1LjExNzk4Ml1dXSwgW1tbLTEzMS44MzIwNTIsIDU1LjQyNDY5XSwgWy0xMzEuNjQ1ODM2LCA1NS4zMDQxOTddLCBbLTEzMS43NDk4OTgsIDU1LjEyODkzNV0sIFstMTMxLjgzMjA1MiwgNTUuMTg5MTgyXSwgWy0xMzEuODMyMDUyLCA1NS40MjQ2OV1dXSwgW1tbLTEzMi45NzY3MzMsIDU2LjQzNzkyNF0sIFstMTMyLjczNTc0NywgNTYuNDU5ODMyXSwgWy0xMzIuNjMxNjg1LCA1Ni40MjE0OTNdLCBbLTEzMi42NjQ1NDcsIDU2LjI3MzYxNl0sIFstMTMyLjg3ODE0OCwgNTYuMjQwNzU0XSwgWy0xMzMuMDY5ODQxLCA1Ni4zMzM4NjJdLCBbLTEzMi45NzY3MzMsIDU2LjQzNzkyNF1dXSwgW1tbLTEzMy41OTU2MjcsIDU2LjM1MDI5M10sIFstMTMzLjE2Mjk0OSwgNTYuMzE3NDMxXSwgWy0xMzMuMDUzNDEsIDU2LjEyNTczOV0sIFstMTMyLjYyMDczMiwgNTUuOTEyMTM4XSwgWy0xMzIuNDcyODU0LCA1NS43ODA2OTFdLCBbLTEzMi40NjE5LCA1NS42NzExNTJdLCBbLTEzMi4zNTc4MzgsIDU1LjY0OTI0NV0sIFstMTMyLjM0MTQwOCwgNTUuNTA2ODQ0XSwgWy0xMzIuMTY2MTQ2LCA1NS4zNjQ0NDRdLCBbLTEzMi4xNDQyMzgsIDU1LjIzODQ3NF0sIFstMTMyLjAyOTIyMiwgNTUuMjc2ODEzXSwgWy0xMzEuOTc5OTMsIDU1LjE3ODIyOF0sIFstMTMxLjk1ODAyMiwgNTQuNzg5MzY1XSwgWy0xMzIuMDI5MjIyLCA1NC43MDE3MzRdLCBbLTEzMi4zMDg1NDYsIDU0LjcxODE2NV0sIFstMTMyLjM4NTIyMywgNTQuOTE1MzM1XSwgWy0xMzIuNDgzODA4LCA1NC44OTg5MDRdLCBbLTEzMi42ODY0NTUsIDU1LjA0Njc4MV0sIFstMTMyLjc0NjcwMSwgNTQuOTk3NDg5XSwgWy0xMzIuOTE2NDg2LCA1NS4wNDY3ODFdLCBbLTEzMi44ODkxMDIsIDU0Ljg5ODkwNF0sIFstMTMyLjczMDI3LCA1NC45MzcyNDJdLCBbLTEzMi42MjYyMDksIDU0Ljg4MjQ3M10sIFstMTMyLjY3NTUwMSwgNTQuNjc5ODI2XSwgWy0xMzIuODY3MTk0LCA1NC43MDE3MzRdLCBbLTEzMy4xNTc0NzIsIDU0Ljk1OTE1XSwgWy0xMzMuMjM5NjI2LCA1NS4wOTA1OTddLCBbLTEzMy4yMjMxOTUsIDU1LjIyNzUyXSwgWy0xMzMuNDUzMjI3LCA1NS4yMTY1NjZdLCBbLTEzMy40NTMyMjcsIDU1LjMyMDYyOF0sIFstMTMzLjI3Nzk2NCwgNTUuMzMxNTgyXSwgWy0xMzMuMTAyNzAyLCA1NS40MjQ2OV0sIFstMTMzLjE3OTM4LCA1NS41ODg5OThdLCBbLTEzMy4zODc1MDMsIDU1LjYyMTg2XSwgWy0xMzMuNDIwMzY1LCA1NS44ODQ3NTNdLCBbLTEzMy40OTcwNDIsIDU2LjAxNjJdLCBbLTEzMy42Mzk0NDIsIDU1LjkyMzA5Ml0sIFstMTMzLjY5NDIxMiwgNTYuMDcwOTY5XSwgWy0xMzMuNTQ2MzM1LCA1Ni4xNDIxNjldLCBbLTEzMy42NjY4MjcsIDU2LjMxMTk1NV0sIFstMTMzLjU5NTYyNywgNTYuMzUwMjkzXV1dLCBbW1stMTMzLjczODAyNywgNTUuNTU2MTM3XSwgWy0xMzMuNTQ2MzM1LCA1NS40OTA0MTNdLCBbLTEzMy40MTQ4ODgsIDU1LjU3MjU2OF0sIFstMTMzLjI4MzQ0MSwgNTUuNTM0MjI5XSwgWy0xMzMuNDIwMzY1LCA1NS4zODYzNTJdLCBbLTEzMy42MzM5NjYsIDU1LjQzMDE2N10sIFstMTMzLjczODAyNywgNTUuNTU2MTM3XV1dLCBbW1stMTMzLjkwNzgxMywgNTYuOTMwODQ5XSwgWy0xMzQuMDUwMjEzLCA1Ny4wMjk0MzRdLCBbLTEzMy44ODU5MDUsIDU3LjA5NTE1N10sIFstMTMzLjM0MzY4OCwgNTcuMDAyMDQ5XSwgWy0xMzMuMTAyNzAyLCA1Ny4wMDc1MjZdLCBbLTEzMi45MzI5MTcsIDU2LjgyMTMxXSwgWy0xMzIuNjIwNzMyLCA1Ni42Njc5NTZdLCBbLTEzMi42NTM1OTMsIDU2LjU1Mjk0XSwgWy0xMzIuODE3OTAxLCA1Ni40OTI2OTRdLCBbLTEzMy4wNDI0NTYsIDU2LjUyMDA3OF0sIFstMTMzLjIwMTI4NywgNTYuNDQ4ODc4XSwgWy0xMzMuNDIwMzY1LCA1Ni40OTI2OTRdLCBbLTEzMy42NjEzNSwgNTYuNDQ4ODc4XSwgWy0xMzMuNzEwNjQzLCA1Ni42ODQzODZdLCBbLTEzMy42ODg3MzUsIDU2LjgzNzc0MV0sIFstMTMzLjg2OTQ3NCwgNTYuODQzMjE4XSwgWy0xMzMuOTA3ODEzLCA1Ni45MzA4NDldXV0sIFtbWy0xMzQuMTE1OTM2LCA1Ni40ODE3NF0sIFstMTM0LjI1Mjg2LCA1Ni41NTg0MTddLCBbLTEzNC40MDA3MzcsIDU2LjcyMjcyNV0sIFstMTM0LjQxNzE2OCwgNTYuODQ4Njk1XSwgWy0xMzQuMjk2Njc1LCA1Ni45MDg5NDFdLCBbLTEzNC4xNzA3MDYsIDU2Ljg0ODY5NV0sIFstMTM0LjE0MzMyMSwgNTYuOTUyNzU3XSwgWy0xMzMuNzQ4OTgxLCA1Ni43NzIwMTddLCBbLTEzMy43MTA2NDMsIDU2LjU5Njc1NV0sIFstMTMzLjg0NzU2NiwgNTYuNTc0ODQ4XSwgWy0xMzMuOTM1MTk3LCA1Ni4zNzc2NzhdLCBbLTEzMy44MzY2MTIsIDU2LjMyMjkwOF0sIFstMTMzLjk1NzEwNSwgNTYuMDkyODc3XSwgWy0xMzQuMTEwNDU5LCA1Ni4xNDIxNjldLCBbLTEzNC4xMzIzNjcsIDU1Ljk5OTc2OV0sIFstMTM0LjIzMDk1MiwgNTYuMDcwOTY5XSwgWy0xMzQuMjkxMTk4LCA1Ni4zNTAyOTNdLCBbLTEzNC4xMTU5MzYsIDU2LjQ4MTc0XV1dLCBbW1stMTM0LjYzNjI0NiwgNTYuMjg0NTddLCBbLTEzNC42NjkxMDcsIDU2LjE2OTU1NF0sIFstMTM0LjgwNjAzMSwgNTYuMjM1Mjc3XSwgWy0xMzUuMTc4NDYzLCA1Ni42Nzg5MV0sIFstMTM1LjQxMzk3MSwgNTYuODEwMzU2XSwgWy0xMzUuMzMxODE3LCA1Ni45MTQ0MThdLCBbLTEzNS40MjQ5MjUsIDU3LjE2NjM1N10sIFstMTM1LjY4NzgxOCwgNTcuMzY5MDA0XSwgWy0xMzUuNDE5NDQ4LCA1Ny41NjYxNzRdLCBbLTEzNS4yOTg5NTUsIDU3LjQ4NDAyXSwgWy0xMzUuMDYzNDQ3LCA1Ny40MTgyOTZdLCBbLTEzNC44NDk4NDYsIDU3LjQwNzM0M10sIFstMTM0Ljg0NDM2OSwgNTcuMjQ4NTExXSwgWy0xMzQuNjM2MjQ2LCA1Ni43MjgyMDJdLCBbLTEzNC42MzYyNDYsIDU2LjI4NDU3XV1dLCBbW1stMTM0LjcxMjkyMywgNTguMjIzNDA3XSwgWy0xMzQuMzczMzUzLCA1OC4xNDY3M10sIFstMTM0LjE3NjE4MywgNTguMTU3NjgzXSwgWy0xMzQuMTg3MTM3LCA1OC4wODEwMDZdLCBbLTEzMy45MDIzMzYsIDU3LjgwNzE1OV0sIFstMTM0LjA5OTUwNSwgNTcuODUwOTc1XSwgWy0xMzQuMTQ4Nzk4LCA1Ny43NTc4NjddLCBbLTEzMy45MzUxOTcsIDU3LjYxNTQ2Nl0sIFstMTMzLjg2OTQ3NCwgNTcuMzYzNTI3XSwgWy0xMzQuMDgzMDc1LCA1Ny4yOTc4MDRdLCBbLTEzNC4xNTQyNzUsIDU3LjIxMDE3M10sIFstMTM0LjQ5OTMyMiwgNTcuMDI5NDM0XSwgWy0xMzQuNjAzMzg0LCA1Ny4wMzQ5MTFdLCBbLTEzNC42NDcyLCA1Ny4yMjY2MDRdLCBbLTEzNC41NzU5OTksIDU3LjM0MTYxOV0sIFstMTM0LjYwODg2MSwgNTcuNTExNDA0XSwgWy0xMzQuNzI5MzU0LCA1Ny43MTk1MjhdLCBbLTEzNC43MDc0NDYsIDU3LjgyOTA2N10sIFstMTM0Ljc4NDEyMywgNTguMDk3NDM3XSwgWy0xMzQuOTE1NTcsIDU4LjIxMjQ1M10sIFstMTM0Ljk1MzkwOCwgNTguNDA5NjIzXSwgWy0xMzQuNzEyOTIzLCA1OC4yMjM0MDddXV0sIFtbWy0xMzUuODU3NjAzLCA1Ny4zMzA2NjVdLCBbLTEzNS43MTUyMDMsIDU3LjMzMDY2NV0sIFstMTM1LjU2NzMyNiwgNTcuMTQ5OTI2XSwgWy0xMzUuNjMzMDQ5LCA1Ny4wMjM5NTddLCBbLTEzNS44NTc2MDMsIDU2Ljk5NjU3Ml0sIFstMTM1LjgyNDc0MiwgNTcuMTkzNzQyXSwgWy0xMzUuODU3NjAzLCA1Ny4zMzA2NjVdXV0sIFtbWy0xMzYuMjc5MzI4LCA1OC4yMDY5NzZdLCBbLTEzNS45NzgwOTYsIDU4LjIwMTQ5OV0sIFstMTM1Ljc4MDkyNiwgNTguMjg5MTNdLCBbLTEzNS40OTYxMjUsIDU4LjE2ODYzN10sIFstMTM1LjY0OTQ4LCA1OC4wMzcxOTFdLCBbLTEzNS41OTQ3MSwgNTcuOTg3ODk4XSwgWy0xMzUuNDUyMzEsIDU4LjEzNTc3Nl0sIFstMTM1LjEwNzI2MywgNTguMDg2NDgzXSwgWy0xMzQuOTE1NTcsIDU3Ljk3Njk0NF0sIFstMTM1LjAyNTEwOCwgNTcuNzc5Nzc1XSwgWy0xMzQuOTM3NDc3LCA1Ny43NjMzNDRdLCBbLTEzNC44MjI0NjIsIDU3LjUwMDQ1MV0sIFstMTM1LjA4NTM1NSwgNTcuNDYyMTEyXSwgWy0xMzUuNTcyODAyLCA1Ny42NzU3MTNdLCBbLTEzNS41NTYzNzIsIDU3LjQ1NjYzNV0sIFstMTM1LjcwOTcyNiwgNTcuMzY5MDA0XSwgWy0xMzUuODkwNDY1LCA1Ny40MDczNDNdLCBbLTEzNi4wMDAwMDQsIDU3LjU0NDI2Nl0sIFstMTM2LjIwODEyOCwgNTcuNjM3Mzc0XSwgWy0xMzYuMzY2OTU5LCA1Ny44MjkwNjddLCBbLTEzNi41Njk2MDYsIDU3LjkxNjY5OF0sIFstMTM2LjU1ODY1MiwgNTguMDc1NTI5XSwgWy0xMzYuNDIxNzI4LCA1OC4xMzAyOTldLCBbLTEzNi4zNzc5MTMsIDU4LjI2NzIyMl0sIFstMTM2LjI3OTMyOCwgNTguMjA2OTc2XV1dLCBbW1stMTQ3LjA3OTg1NCwgNjAuMjAwNTgyXSwgWy0xNDcuNTAxNTc5LCA1OS45NDg2NDNdLCBbLTE0Ny41MzQ0NCwgNTkuODUwMDU4XSwgWy0xNDcuODc0MDExLCA1OS43ODQzMzVdLCBbLTE0Ny44MDI4MSwgNTkuOTM3Njg5XSwgWy0xNDcuNDM1ODU1LCA2MC4wOTY1Ml0sIFstMTQ3LjIwNTgyNCwgNjAuMjcxNzgyXSwgWy0xNDcuMDc5ODU0LCA2MC4yMDA1ODJdXV0sIFtbWy0xNDcuNTYxODI1LCA2MC41Nzg0OTFdLCBbLTE0Ny42MTY1OTQsIDYwLjM3MDM2N10sIFstMTQ3Ljc1ODk5NSwgNjAuMTU2NzY3XSwgWy0xNDcuOTU2MTY1LCA2MC4yMjc5NjddLCBbLTE0Ny43OTE4NTYsIDYwLjQ3NDQyOV0sIFstMTQ3LjU2MTgyNSwgNjAuNTc4NDkxXV1dLCBbW1stMTQ3Ljc4NjM3OSwgNzAuMjQ1MjkxXSwgWy0xNDcuNjgyMzE4LCA3MC4yMDE0NzVdLCBbLTE0Ny4xNjIwMDgsIDcwLjE1NzY2XSwgWy0xNDYuODg4MTYxLCA3MC4xODUwNDRdLCBbLTE0Ni41MTAyNTIsIDcwLjE4NTA0NF0sIFstMTQ2LjA5OTQ4MiwgNzAuMTQ2NzA2XSwgWy0xNDUuODU4NDk2LCA3MC4xNjg2MTRdLCBbLTE0NS42MjI5ODgsIDcwLjA4NjQ2XSwgWy0xNDUuMTk1Nzg3LCA2OS45OTMzNTJdLCBbLTE0NC42MjA3MDgsIDY5Ljk3MTQ0NF0sIFstMTQ0LjQ2MTg3NywgNzAuMDI2MjEzXSwgWy0xNDQuMDc4NDkxLCA3MC4wNTkwNzVdLCBbLTE0My45MTQxODMsIDcwLjEzMDI3NV0sIFstMTQzLjQ5NzkzNSwgNzAuMTQxMjI5XSwgWy0xNDMuNTAzNDEyLCA3MC4wOTE5MzZdLCBbLTE0My4yNTY5NSwgNzAuMTE5MzIxXSwgWy0xNDIuNzQ3NTk0LCA3MC4wNDI2NDRdLCBbLTE0Mi40MDI1NDcsIDY5LjkxNjY3NF0sIFstMTQyLjA3OTQwOCwgNjkuODU2NDI4XSwgWy0xNDIuMDA4MjA3LCA2OS44MDE2NTldLCBbLTE0MS43MTI0NTMsIDY5Ljc5MDcwNV0sIFstMTQxLjQzMzEyOSwgNjkuNjk3NTk3XSwgWy0xNDEuMzc4MzU5LCA2OS42MzczNV0sIFstMTQxLjIwODU3NCwgNjkuNjg2NjQzXSwgWy0xNDEuMDAwNDUsIDY5LjY0ODMwNF0sIFstMTQxLjAwMDQ1LCA2MC4zMDQ2NDRdLCBbLTE0MC41MzQ5MSwgNjAuMjIyNDldLCBbLTE0MC40NzQ2NjQsIDYwLjMxMDEyMV0sIFstMTM5Ljk4NzIxNiwgNjAuMTg0MTUxXSwgWy0xMzkuNjk2OTM5LCA2MC4zNDI5ODNdLCBbLTEzOS4wODg5OTgsIDYwLjM1OTQxM10sIFstMTM5LjE5ODUzNywgNjAuMDkxMDQzXSwgWy0xMzkuMDQ1MTgzLCA1OS45OTc5MzVdLCBbLTEzOC43MDAxMzUsIDU5LjkxMDMwNF0sIFstMTM4LjYyMzQ1OCwgNTkuNzY3OTA0XSwgWy0xMzcuNjA0NzQ3LCA1OS4yNDIxMThdLCBbLTEzNy40NDU5MTYsIDU4LjkwODAyNF0sIFstMTM3LjI2NTE3NywgNTkuMDAxMTMyXSwgWy0xMzYuODI3MDIyLCA1OS4xNTk5NjNdLCBbLTEzNi41ODA1NTksIDU5LjE2NTQ0XSwgWy0xMzYuNDY1NTQ0LCA1OS4yODU5MzNdLCBbLTEzNi40NzY0OTgsIDU5LjQ2NjY3Ml0sIFstMTM2LjMwMTIzNiwgNTkuNDY2NjcyXSwgWy0xMzYuMjU3NDIsIDU5LjYyNTUwM10sIFstMTM1Ljk0NTIzNCwgNTkuNjYzODQyXSwgWy0xMzUuNDc5Njk0LCA1OS44MDA3NjZdLCBbLTEzNS4wMjUxMDgsIDU5LjU2NTI1N10sIFstMTM1LjA2ODkyNCwgNTkuNDIyODU3XSwgWy0xMzQuOTU5Mzg1LCA1OS4yODA0NTZdLCBbLTEzNC43MDE5NjksIDU5LjI0NzU5NV0sIFstMTM0LjM3ODgyOSwgNTkuMDMzOTk0XSwgWy0xMzQuNDAwNzM3LCA1OC45NzM3NDhdLCBbLTEzNC4yNTI4NiwgNTguODU4NzMyXSwgWy0xMzMuODQyMDg5LCA1OC43MjcyODVdLCBbLTEzMy4xNzM5MDMsIDU4LjE1MjIwNl0sIFstMTMzLjA3NTMxOCwgNTcuOTk4ODUyXSwgWy0xMzIuODY3MTk0LCA1Ny44NDU0OThdLCBbLTEzMi41NjA0ODUsIDU3LjUwNTkyOF0sIFstMTMyLjI1Mzc3NywgNTcuMjE1NjVdLCBbLTEzMi4zNjg3OTIsIDU3LjA5NTE1N10sIFstMTMyLjA1MTEzLCA1Ny4wNTEzNDFdLCBbLTEzMi4xMjc4MDcsIDU2Ljg3NjA3OV0sIFstMTMxLjg3MDM5MSwgNTYuODA0ODc5XSwgWy0xMzEuODM3NTI5LCA1Ni42MDIyMzJdLCBbLTEzMS41ODAxMTMsIDU2LjYxMzE4Nl0sIFstMTMxLjA4NzE4OCwgNTYuNDA1MDYyXSwgWy0xMzAuNzgwNDgsIDU2LjM2NjcyNF0sIFstMTMwLjYyMTY0OCwgNTYuMjY4MTM5XSwgWy0xMzAuNDY4Mjk0LCA1Ni4yNDA3NTRdLCBbLTEzMC40MjQ0NzgsIDU2LjE0MjE2OV0sIFstMTMwLjEwMTMzOSwgNTYuMTE0Nzg1XSwgWy0xMzAuMDAyNzU0LCA1NS45OTQyOTJdLCBbLTEzMC4xNTA2MzEsIDU1Ljc2OTczN10sIFstMTMwLjEyODcyNCwgNTUuNTgzNTIxXSwgWy0xMjkuOTg2MzIzLCA1NS4yNzY4MTNdLCBbLTEzMC4wOTU4NjIsIDU1LjIwMDEzNl0sIFstMTMwLjMzNjg0NywgNTQuOTIwODEyXSwgWy0xMzAuNjg3MzcyLCA1NC43MTgxNjVdLCBbLTEzMC43ODU5NTcsIDU0LjgyMjIyN10sIFstMTMwLjkxNzQwMywgNTQuNzg5MzY1XSwgWy0xMzEuMDEwNTExLCA1NC45OTc0ODldLCBbLTEzMC45ODMxMjYsIDU1LjA4NTEyXSwgWy0xMzEuMDkyNjY1LCA1NS4xODkxODJdLCBbLTEzMC44NjI2MzQsIDU1LjI5ODcyMV0sIFstMTMwLjkyODM1NywgNTUuMzM3MDU5XSwgWy0xMzEuMTU4Mzg5LCA1NS4yMDAxMzZdLCBbLTEzMS4yODQzNTgsIDU1LjI4Nzc2N10sIFstMTMxLjQyNjc1OSwgNTUuMjM4NDc0XSwgWy0xMzEuODQzMDA2LCA1NS40NTc1NTJdLCBbLTEzMS43MDA2MDYsIDU1LjY5ODUzN10sIFstMTMxLjk2MzQ5OSwgNTUuNjE2MzgzXSwgWy0xMzEuOTc0NDUzLCA1NS40OTU4OV0sIFstMTMyLjE4MjU3NiwgNTUuNTg4OTk4XSwgWy0xMzIuMjI2MzkyLCA1NS43MDQwMTRdLCBbLTEzMi4wODM5OTEsIDU1LjgyOTk4NF0sIFstMTMyLjEyNzgwNywgNTUuOTU1OTUzXSwgWy0xMzIuMzI0OTc3LCA1NS44NTE4OTJdLCBbLTEzMi41MjIxNDcsIDU2LjA3NjQ0Nl0sIFstMTMyLjY0MjYzOSwgNTYuMDMyNjMxXSwgWy0xMzIuNzE5MzE3LCA1Ni4yMTg4NDddLCBbLTEzMi41Mjc2MjQsIDU2LjMzOTMzOV0sIFstMTMyLjM0MTQwOCwgNTYuMzM5MzM5XSwgWy0xMzIuMzk2MTc3LCA1Ni40ODcyMTddLCBbLTEzMi4yOTc1OTIsIDU2LjY3ODkxXSwgWy0xMzIuNDUwOTQ2LCA1Ni42NzM0MzNdLCBbLTEzMi43Njg2MDksIDU2LjgzNzc0MV0sIFstMTMyLjk5MzE2NCwgNTcuMDM0OTExXSwgWy0xMzMuNTE4OTUsIDU3LjE3NzMxMV0sIFstMTMzLjUwNzk5NiwgNTcuNTc3MTI4XSwgWy0xMzMuNjc3NzgxLCA1Ny42MjY0Ml0sIFstMTMzLjYzOTQ0MiwgNTcuNzkwNzI4XSwgWy0xMzMuODE0NzA1LCA1Ny44MzQ1NDRdLCBbLTEzNC4wNzIxMjEsIDU4LjA1MzYyMl0sIFstMTM0LjE0MzMyMSwgNTguMTY4NjM3XSwgWy0xMzQuNTg2OTUzLCA1OC4yMDY5NzZdLCBbLTEzNS4wNzQ0MDEsIDU4LjUwMjczMV0sIFstMTM1LjI4MjUyNSwgNTkuMTkyODI1XSwgWy0xMzUuMzgxMTEsIDU5LjAzMzk5NF0sIFstMTM1LjMzNzI5NCwgNTguODkxNTkzXSwgWy0xMzUuMTQwMTI0LCA1OC42MTc3NDZdLCBbLTEzNS4xODk0MTcsIDU4LjU3MzkzMV0sIFstMTM1LjA1Nzk3LCA1OC4zNDkzNzZdLCBbLTEzNS4wODUzNTUsIDU4LjIwMTQ5OV0sIFstMTM1LjI3NzA0OCwgNTguMjM0MzYxXSwgWy0xMzUuNDMwNDAyLCA1OC4zOTg2NjldLCBbLTEzNS42MzMwNDksIDU4LjQyNjA1M10sIFstMTM1LjkxNzg1LCA1OC4zODIyMzhdLCBbLTEzNS45MTIzNzMsIDU4LjYxNzc0Nl0sIFstMTM2LjA4NzYzNSwgNTguODE0OTE2XSwgWy0xMzYuMjQ2NDY2LCA1OC43NTQ2N10sIFstMTM2Ljg3NjMxNCwgNTguOTYyNzk0XSwgWy0xMzYuOTMxMDg0LCA1OC45MDI1NDddLCBbLTEzNi41ODYwMzYsIDU4LjgzNjgyNF0sIFstMTM2LjMxNzY2NiwgNTguNjcyNTE2XSwgWy0xMzYuMjEzNjA0LCA1OC42NjcwMzldLCBbLTEzNi4xODA3NDMsIDU4LjUzNTU5Ml0sIFstMTM2LjA0MzgxOSwgNTguMzgyMjM4XSwgWy0xMzYuMzg4ODY3LCA1OC4yOTQ2MDddLCBbLTEzNi41OTE1MTMsIDU4LjM0OTM3Nl0sIFstMTM2LjU5Njk5LCA1OC4yMTI0NTNdLCBbLTEzNi44NTk4ODMsIDU4LjMxNjUxNV0sIFstMTM2Ljk0NzUxNCwgNTguMzkzMTkyXSwgWy0xMzcuMTExODIzLCA1OC4zOTMxOTJdLCBbLTEzNy41NjY0MDksIDU4LjU5MDM2Ml0sIFstMTM3LjkwMDUwMiwgNTguNzY1NjI0XSwgWy0xMzcuOTMzMzY0LCA1OC44Njk2ODZdLCBbLTEzOC4xMTk1OCwgNTkuMDIzMDRdLCBbLTEzOC42MzQ0MTIsIDU5LjEzMjU3OV0sIFstMTM4LjkxOTIxMywgNTkuMjQ3NTk1XSwgWy0xMzkuNDE3NjE1LCA1OS4zNzkwNDFdLCBbLTEzOS43NDYyMzEsIDU5LjUwNTAxMV0sIFstMTM5LjcxODg0NiwgNTkuNjQxOTM0XSwgWy0xMzkuNjI1NzM4LCA1OS41OTgxMTldLCBbLTEzOS41MTYyLCA1OS42ODU3NV0sIFstMTM5LjYyNTczOCwgNTkuODgyOTJdLCBbLTEzOS40ODg4MTUsIDU5Ljk5MjQ1OF0sIFstMTM5LjU1NDUzOCwgNjAuMDQxNzUxXSwgWy0xMzkuODAxLCA1OS44MzM2MjddLCBbLTE0MC4zMTU4MzMsIDU5LjY5NjcwNF0sIFstMTQwLjkyOTI1LCA1OS43NDU5OTZdLCBbLTE0MS40NDQwODMsIDU5Ljg3MTk2Nl0sIFstMTQxLjQ2NTk5LCA1OS45NzA1NTFdLCBbLTE0MS43MDY5NzYsIDU5Ljk0ODY0M10sIFstMTQxLjk2NDM5MiwgNjAuMDE5ODQzXSwgWy0xNDIuNTM5NDcxLCA2MC4wODU1NjZdLCBbLTE0Mi44NzM1NjQsIDYwLjA5MTA0M10sIFstMTQzLjYyMzkwNSwgNjAuMDM2Mjc0XSwgWy0xNDMuODkyMjc1LCA1OS45OTc5MzVdLCBbLTE0NC4yMzE4NDUsIDYwLjE0MDMzNl0sIFstMTQ0LjY1MzU3LCA2MC4yMDYwNTldLCBbLTE0NC43ODUwMTYsIDYwLjI5MzY5XSwgWy0xNDQuODM0MzA5LCA2MC40NDE1NjhdLCBbLTE0NS4xMjQ1ODYsIDYwLjQzMDYxNF0sIFstMTQ1LjIyMzE3MSwgNjAuMjk5MTY3XSwgWy0xNDUuNzM4MDA0LCA2MC40NzQ0MjldLCBbLTE0NS44MjAxNTgsIDYwLjU1MTEwNl0sIFstMTQ2LjM1MTQyMSwgNjAuNDA4NzA2XSwgWy0xNDYuNjA4ODM3LCA2MC4yMzg5MjFdLCBbLTE0Ni43MTgzNzYsIDYwLjM5Nzc1Ml0sIFstMTQ2LjYwODgzNywgNjAuNDg1MzgzXSwgWy0xNDYuNDU1NDgzLCA2MC40NjM0NzVdLCBbLTE0NS45NTE2MDQsIDYwLjU3ODQ5MV0sIFstMTQ2LjAxNzMyOCwgNjAuNjY2MTIyXSwgWy0xNDYuMjUyODM2LCA2MC42MjIzMDddLCBbLTE0Ni4zNDU5NDQsIDYwLjczNzMyMl0sIFstMTQ2LjU2NTAyMiwgNjAuNzUzNzUzXSwgWy0xNDYuNzg0MDk5LCA2MS4wNDQwMzFdLCBbLTE0Ni44NjYyNTMsIDYwLjk3MjgzMV0sIFstMTQ3LjE3Mjk2MiwgNjAuOTM0NDkyXSwgWy0xNDcuMjcxNTQ3LCA2MC45NzI4MzFdLCBbLTE0Ny4zNzU2MDksIDYwLjg3OTcyM10sIFstMTQ3Ljc1ODk5NSwgNjAuOTEyNTg0XSwgWy0xNDcuNzc1NDI2LCA2MC44MDg1MjNdLCBbLTE0OC4wMzI4NDIsIDYwLjc4MTEzOF0sIFstMTQ4LjE1MzMzNCwgNjAuODE5NDc2XSwgWy0xNDguMDY1NzAzLCA2MS4wMDU2OTJdLCBbLTE0OC4xNzUyNDIsIDYxLjAwMDIxNV0sIFstMTQ4LjM1MDUwNCwgNjAuODAzMDQ2XSwgWy0xNDguMTA5NTE5LCA2MC43MzczMjJdLCBbLTE0OC4wODc2MTEsIDYwLjU5NDkyMl0sIFstMTQ3LjkzOTczNCwgNjAuNDQxNTY4XSwgWy0xNDguMDI3MzY1LCA2MC4yNzcyNTldLCBbLTE0OC4yMTkwNTgsIDYwLjMzMjAyOV0sIFstMTQ4LjI3MzgyNywgNjAuMjQ5ODc1XSwgWy0xNDguMDg3NjExLCA2MC4yMTcwMTNdLCBbLTE0Ny45ODM1NDksIDU5Ljk5NzkzNV0sIFstMTQ4LjI1MTkxOSwgNTkuOTU0MTJdLCBbLTE0OC4zOTk3OTcsIDU5Ljk5NzkzNV0sIFstMTQ4LjYzNTMwNSwgNTkuOTM3Njg5XSwgWy0xNDguNzU1Nzk4LCA1OS45ODY5ODFdLCBbLTE0OS4wNjc5ODQsIDU5Ljk4MTUwNV0sIFstMTQ5LjA1NzAzLCA2MC4wNjM2NTldLCBbLTE0OS4yMDQ5MDcsIDYwLjAwODg4OV0sIFstMTQ5LjI4NzA2MSwgNTkuOTA0ODI3XSwgWy0xNDkuNDE4NTA4LCA1OS45OTc5MzVdLCBbLTE0OS41ODI4MTYsIDU5Ljg2NjQ4OV0sIFstMTQ5LjUxMTYxNiwgNTkuODA2MjQyXSwgWy0xNDkuNzQxNjQ3LCA1OS43Mjk1NjVdLCBbLTE0OS45NDk3NzEsIDU5LjcxODYxMV0sIFstMTUwLjAzMTkyNSwgNTkuNjE0NTVdLCBbLTE1MC4yNTY0OCwgNTkuNTIxNDQyXSwgWy0xNTAuNDA5ODM0LCA1OS41NTQzMDNdLCBbLTE1MC41Nzk2MTksIDU5LjQ0NDc2NF0sIFstMTUwLjcxNjU0MywgNTkuNDUwMjQxXSwgWy0xNTEuMDAxMzQzLCA1OS4yMjU2ODddLCBbLTE1MS4zMDgwNTIsIDU5LjIwOTI1Nl0sIFstMTUxLjQwNjYzNywgNTkuMjgwNDU2XSwgWy0xNTEuNTkyODUzLCA1OS4xNTk5NjNdLCBbLTE1MS45NzYyMzksIDU5LjI1MzA3MV0sIFstMTUxLjg4ODYwOCwgNTkuNDIyODU3XSwgWy0xNTEuNjM2NjY5LCA1OS40ODMxMDNdLCBbLTE1MS40NzIzNiwgNTkuNDcyMTQ5XSwgWy0xNTEuNDIzMDY4LCA1OS41Mzc4NzJdLCBbLTE1MS4xMjczMTMsIDU5LjY2OTMxOV0sIFstMTUxLjExNjM1OSwgNTkuNzc4ODU4XSwgWy0xNTEuNTA1MjIyLCA1OS42MzA5OF0sIFstMTUxLjgyODM2MSwgNTkuNzE4NjExXSwgWy0xNTEuODY2NywgNTkuNzc4ODU4XSwgWy0xNTEuNzAyMzkyLCA2MC4wMzA3OTddLCBbLTE1MS40MjMwNjgsIDYwLjIxMTUzNl0sIFstMTUxLjM3OTI1MiwgNjAuMzU5NDEzXSwgWy0xNTEuMjk3MDk4LCA2MC4zODY3OThdLCBbLTE1MS4yNjQyMzcsIDYwLjU0NTYyOV0sIFstMTUxLjQwNjYzNywgNjAuNzIwODkyXSwgWy0xNTEuMDYxNTksIDYwLjc4NjYxNV0sIFstMTUwLjQwNDM1NywgNjEuMDM4NTU0XSwgWy0xNTAuMjQ1NTI2LCA2MC45Mzk5NjldLCBbLTE1MC4wNDI4NzksIDYwLjkxMjU4NF0sIFstMTQ5Ljc0MTY0NywgNjEuMDE2NjQ2XSwgWy0xNTAuMDc1NzQxLCA2MS4xNTM1N10sIFstMTUwLjIwNzE4NywgNjEuMjU3NjMyXSwgWy0xNTAuNDcwMDgsIDYxLjI0NjY3OF0sIFstMTUwLjY1NjI5NiwgNjEuMjk1OTddLCBbLTE1MC43MTEwNjYsIDYxLjI1MjE1NV0sIFstMTUxLjAyMzI1MSwgNjEuMTgwOTU0XSwgWy0xNTEuMTY1NjUyLCA2MS4wNDQwMzFdLCBbLTE1MS40Nzc4MzcsIDYxLjAxMTE2OV0sIFstMTUxLjgwMDk3NywgNjAuODUyMzM4XSwgWy0xNTEuODMzODM4LCA2MC43NDgyNzZdLCBbLTE1Mi4wODAzMDEsIDYwLjY5MzUwN10sIFstMTUyLjEzNTA3LCA2MC41Nzg0OTFdLCBbLTE1Mi4zMTAzMzIsIDYwLjUwNzI5MV0sIFstMTUyLjM5MjQ4NiwgNjAuMzA0NjQ0XSwgWy0xNTIuNzMyMDU3LCA2MC4xNzMxOTddLCBbLTE1Mi41Njc3NDgsIDYwLjA2OTEzNl0sIFstMTUyLjcwNDY3MiwgNTkuOTE1NzgxXSwgWy0xNTMuMDIyMzM0LCA1OS44ODgzOTddLCBbLTE1My4wNDk3MTksIDU5LjY5MTIyN10sIFstMTUzLjM0NTQ3NCwgNTkuNjIwMDI2XSwgWy0xNTMuNDM4NTgyLCA1OS43MDIxODFdLCBbLTE1My41ODY0NTksIDU5LjU0ODgyNl0sIFstMTUzLjc2MTcyMSwgNTkuNTQzMzQ5XSwgWy0xNTMuNzI4ODYsIDU5LjQzMzgxMV0sIFstMTU0LjExNzcyMywgNTkuMzY4MDg3XSwgWy0xNTQuMTk0NCwgNTkuMDY2ODU2XSwgWy0xNTMuNzUwNzY4LCA1OS4wNTA0MjVdLCBbLTE1My40MDAyNDMsIDU4Ljk2ODI3MV0sIFstMTUzLjMwMTY1OCwgNTguODY5Njg2XSwgWy0xNTMuNDQ0MDU5LCA1OC43MTA4NTRdLCBbLTE1My42Nzk1NjcsIDU4LjYxMjI2OV0sIFstMTUzLjg5ODY0NSwgNTguNjA2NzkzXSwgWy0xNTMuOTIwNTUzLCA1OC41MTkxNjFdLCBbLTE1NC4wNjI5NTMsIDU4LjQ4NjNdLCBbLTE1My45OTcyMywgNTguMzc2NzYxXSwgWy0xNTQuMTQ1MTA3LCA1OC4yMTI0NTNdLCBbLTE1NC40NjI3NywgNTguMDU5MDk4XSwgWy0xNTQuNjQzNTA5LCA1OC4wNTkwOThdLCBbLTE1NC44MTg3NzEsIDU4LjAwNDMyOV0sIFstMTU0Ljk4ODU1NiwgNTguMDE1MjgzXSwgWy0xNTUuMTIwMDAzLCA1Ny45NTUwMzddLCBbLTE1NS4wODE2NjQsIDU3Ljg3Mjg4M10sIFstMTU1LjMyODEyNiwgNTcuODI5MDY3XSwgWy0xNTUuMzc3NDE5LCA1Ny43MDg1NzRdLCBbLTE1NS41NDcyMDQsIDU3Ljc4NTI1MV0sIFstMTU1LjczMzQyLCA1Ny41NDk3NDNdLCBbLTE1Ni4wNDU2MDYsIDU3LjU2NjE3NF0sIFstMTU2LjAyMzY5OCwgNTcuNDQwMjA0XSwgWy0xNTYuMjA5OTE0LCA1Ny40NzMwNjZdLCBbLTE1Ni4zNDEzNiwgNTcuNDE4Mjk2XSwgWy0xNTYuMzQxMzYsIDU3LjI0ODUxMV0sIFstMTU2LjU0OTQ4NCwgNTYuOTg1NjE4XSwgWy0xNTYuODgzNTc3LCA1Ni45NTI3NTddLCBbLTE1Ny4xNTc0MjQsIDU2LjgzMjI2NF0sIFstMTU3LjIwMTI0LCA1Ni43NjY1NDFdLCBbLTE1Ny4zNzY1MDIsIDU2Ljg1OTY0OV0sIFstMTU3LjY3MjI1NywgNTYuNjA3NzA5XSwgWy0xNTcuNzU0NDExLCA1Ni42Nzg5MV0sIFstMTU3LjkxODcxOSwgNTYuNjU3MDAyXSwgWy0xNTcuOTU3MDU4LCA1Ni41MTQ2MDFdLCBbLTE1OC4xMjY4NDMsIDU2LjQ1OTgzMl0sIFstMTU4LjMyOTQ5LCA1Ni40ODE3NF0sIFstMTU4LjQ4ODMyMSwgNTYuMzM5MzM5XSwgWy0xNTguMjA4OTk3LCA1Ni4yOTU1MjRdLCBbLTE1OC41MTAyMjksIDU1Ljk3Nzg2MV0sIFstMTU5LjM3NTU4NSwgNTUuODczNzk5XSwgWy0xNTkuNjE2NTcxLCA1NS41OTQ0NzVdLCBbLTE1OS42NzY4MTcsIDU1LjY1NDcyMl0sIFstMTU5LjY0Mzk1NSwgNTUuODI5OTg0XSwgWy0xNTkuODEzNzQxLCA1NS44NTczNjhdLCBbLTE2MC4wMjczNDEsIDU1Ljc5MTY0NV0sIFstMTYwLjA2MDIwMywgNTUuNzIwNDQ1XSwgWy0xNjAuMzk0Mjk2LCA1NS42MDU0MjldLCBbLTE2MC41MzY2OTcsIDU1LjQ3Mzk4M10sIFstMTYwLjU4MDUxMiwgNTUuNTY3MDkxXSwgWy0xNjAuNjY4MTQzLCA1NS40NTc1NTJdLCBbLTE2MC44NjUzMTMsIDU1LjUyODc1Ml0sIFstMTYxLjIzMjI2OCwgNTUuMzU4OTY3XSwgWy0xNjEuNTA2MTE1LCA1NS4zNjQ0NDRdLCBbLTE2MS40Njc3NzYsIDU1LjQ5NTg5XSwgWy0xNjEuNTg4MjY5LCA1NS42MjE4Nl0sIFstMTYxLjY5NzgwOCwgNTUuNTE3Nzk4XSwgWy0xNjEuNjg2ODU0LCA1NS40MDgyNTldLCBbLTE2Mi4wNTM4MDksIDU1LjA3NDE2Nl0sIFstMTYyLjE3OTc3OSwgNTUuMTU2MzJdLCBbLTE2Mi4yMTgxMTcsIDU1LjAzMDM1XSwgWy0xNjIuNDcwMDU3LCA1NS4wNTIyNThdLCBbLTE2Mi41MDgzOTUsIDU1LjI0OTQyOF0sIFstMTYyLjY2MTc0OSwgNTUuMjkzMjQ0XSwgWy0xNjIuNzE2NTE5LCA1NS4yMjIwNDNdLCBbLTE2Mi41Nzk1OTUsIDU1LjEzNDQxMl0sIFstMTYyLjY0NTMxOSwgNTQuOTk3NDg5XSwgWy0xNjIuODQ3OTY1LCA1NC45MjYyODldLCBbLTE2My4wMDEzMiwgNTUuMDc5NjQzXSwgWy0xNjMuMTg3NTM2LCA1NS4wOTA1OTddLCBbLTE2My4yMjAzOTcsIDU1LjAzMDM1XSwgWy0xNjMuMDM0MTgxLCA1NC45NDI3MTldLCBbLTE2My4zNzM3NTIsIDU0LjgwMDMxOV0sIFstMTYzLjE0MzcyLCA1NC43NjE5OF0sIFstMTYzLjEzODI0MywgNTQuNjk2MjU3XSwgWy0xNjMuMzI5OTM2LCA1NC43NDU1NV0sIFstMTYzLjU4NzM1MiwgNTQuNjE0MTAzXSwgWy0xNjQuMDg1NzU0LCA1NC42MTk1OF0sIFstMTY0LjMzMjIxNiwgNTQuNTMxOTQ5XSwgWy0xNjQuMzU0MTI0LCA1NC40NjYyMjZdLCBbLTE2NC42Mzg5MjUsIDU0LjM4OTU0OF0sIFstMTY0Ljg0NzA0OSwgNTQuNDE2OTMzXSwgWy0xNjQuOTE4MjQ5LCA1NC42MDMxNDldLCBbLTE2NC43MTAxMjUsIDU0LjY2MzM5NV0sIFstMTY0LjU1MTI5NCwgNTQuODg3OTVdLCBbLTE2NC4zNDMxNywgNTQuODkzNDI3XSwgWy0xNjMuODk0MDYxLCA1NS4wNDEzMDRdLCBbLTE2My41MzI1ODMsIDU1LjA0Njc4MV0sIFstMTYzLjM5NTY2LCA1NC45MDQzODFdLCBbLTE2My4yOTE1OTgsIDU1LjAwODQ0M10sIFstMTYzLjMxMzUwNSwgNTUuMTI4OTM1XSwgWy0xNjMuMTA1MzgyLCA1NS4xODM3MDVdLCBbLTE2Mi44ODA4MjcsIDU1LjE4MzcwNV0sIFstMTYyLjU3OTU5NSwgNTUuNDQ2NTk4XSwgWy0xNjIuMjQ1NTAyLCA1NS42ODIxMDZdLCBbLTE2MS44MDczNDcsIDU1Ljg5MDIzXSwgWy0xNjEuMjkyNTE0LCA1NS45ODMzMzhdLCBbLTE2MS4wNzg5MTQsIDU1LjkzOTUyM10sIFstMTYwLjg3MDc5LCA1NS45OTk3NjldLCBbLTE2MC44MTYwMjEsIDU1LjkxMjEzOF0sIFstMTYwLjkzMTAzNiwgNTUuODEzNTUzXSwgWy0xNjAuODA1MDY3LCA1NS43MzY4NzZdLCBbLTE2MC43NjY3MjgsIDU1Ljg1NzM2OF0sIFstMTYwLjUwOTMxMiwgNTUuODY4MzIyXSwgWy0xNjAuNDM4MTEyLCA1NS43OTE2NDVdLCBbLTE2MC4yNzkyOCwgNTUuNzY0MjZdLCBbLTE2MC4yNzM4MDMsIDU1Ljg1NzM2OF0sIFstMTYwLjUzNjY5NywgNTUuOTM5NTIzXSwgWy0xNjAuNTU4NjA0LCA1NS45OTQyOTJdLCBbLTE2MC4zODMzNDIsIDU2LjI1MTcwOF0sIFstMTYwLjE0NzgzNCwgNTYuMzk5NTg2XSwgWy0xNTkuODMwMTcxLCA1Ni41NDE5ODZdLCBbLTE1OS4zMjYyOTMsIDU2LjY2Nzk1Nl0sIFstMTU4Ljk1OTMzOCwgNTYuODQ4Njk1XSwgWy0xNTguNzg0MDc2LCA1Ni43ODI5NzFdLCBbLTE1OC42NDE2NzUsIDU2LjgxMDM1Nl0sIFstMTU4LjcwMTkyMiwgNTYuOTI1MzcyXSwgWy0xNTguNjU4MTA2LCA1Ny4wMzQ5MTFdLCBbLTE1OC4zNzg3ODIsIDU3LjI2NDk0Ml0sIFstMTU3Ljk5NTM5NiwgNTcuNDEyODJdLCBbLTE1Ny42ODg2ODgsIDU3LjYwOTk4OV0sIFstMTU3LjcwNTExOCwgNTcuNzE5NTI4XSwgWy0xNTcuNDU4NjU2LCA1OC40OTcyNTRdLCBbLTE1Ny4wNzUyNywgNTguNzA1Mzc3XSwgWy0xNTcuMTE5MDg2LCA1OC44Njk2ODZdLCBbLTE1OC4wMzkyMTIsIDU4LjYzNDE3N10sIFstMTU4LjMyOTQ5LCA1OC42NjE1NjJdLCBbLTE1OC40MDA2OSwgNTguNzYwMTQ3XSwgWy0xNTguNTY0OTk4LCA1OC44MDM5NjJdLCBbLTE1OC42MTk3NjgsIDU4LjkxMzUwMV0sIFstMTU4Ljc2NzY0NSwgNTguODY0MjA5XSwgWy0xNTguODYwNzUzLCA1OC42OTQ0MjRdLCBbLTE1OC43MDE5MjIsIDU4LjQ4MDgyM10sIFstMTU4Ljg5MzYxNSwgNTguMzg3NzE1XSwgWy0xNTkuMDYzNCwgNTguNDIwNTc3XSwgWy0xNTkuMzkyMDE2LCA1OC43NjAxNDddLCBbLTE1OS42MTY1NzEsIDU4LjkyOTkzMl0sIFstMTU5LjczMTU4NiwgNTguOTI5OTMyXSwgWy0xNTkuODA4MjY0LCA1OC44MDM5NjJdLCBbLTE1OS45MDY4NDgsIDU4Ljc4MjA1NV0sIFstMTYwLjA1NDcyNiwgNTguODg2MTE2XSwgWy0xNjAuMjM1NDY1LCA1OC45MDI1NDddLCBbLTE2MC4zMTc2MTksIDU5LjA3MjMzMl0sIFstMTYwLjg1NDM1OSwgNTguODgwNjRdLCBbLTE2MS4zMzYzMywgNTguNzQzNzE2XSwgWy0xNjEuMzc0NjY5LCA1OC42NjcwMzldLCBbLTE2MS43NTI1NzcsIDU4LjU1MjAyM10sIFstMTYxLjkzODc5MywgNTguNjU2MDg1XSwgWy0xNjEuNzY5MDA4LCA1OC43NzY1NzhdLCBbLTE2MS44MjkyNTUsIDU5LjA2MTM3OV0sIFstMTYxLjk1NTIyNCwgNTkuMzYyNjFdLCBbLTE2MS43MDMyODUsIDU5LjQ4ODU4XSwgWy0xNjEuOTExNDA5LCA1OS43NDA1MTldLCBbLTE2Mi4wOTIxNDgsIDU5Ljg4MjkyXSwgWy0xNjIuMjM0NTQ4LCA2MC4wOTEwNDNdLCBbLTE2Mi40NDgxNDksIDYwLjE3ODY3NF0sIFstMTYyLjUwMjkxOCwgNTkuOTk3OTM1XSwgWy0xNjIuNzYwMzM0LCA1OS45NTk1OTddLCBbLTE2My4xNzExMDUsIDU5Ljg0NDU4MV0sIFstMTYzLjY2NDAzLCA1OS43OTUyODldLCBbLTE2My45MzI0LCA1OS44MDYyNDJdLCBbLTE2NC4xNjI0MzEsIDU5Ljg2NjQ4OV0sIFstMTY0LjE4OTgxNiwgNjAuMDI1MzJdLCBbLTE2NC4zODY5ODYsIDYwLjA3NDYxM10sIFstMTY0LjY5OTE3MSwgNjAuMjkzNjldLCBbLTE2NC45NjIwNjQsIDYwLjMzNzUwNl0sIFstMTY1LjI2ODc3MywgNjAuNTc4NDkxXSwgWy0xNjUuMDYwNjQ5LCA2MC42ODgwM10sIFstMTY1LjAxNjgzNCwgNjAuODkwNjc3XSwgWy0xNjUuMTc1NjY1LCA2MC44NDY4NjFdLCBbLTE2NS4xOTc1NzMsIDYwLjk3MjgzMV0sIFstMTY1LjEyMDg5NiwgNjEuMDc2ODkzXSwgWy0xNjUuMzIzNTQzLCA2MS4xNzAwMDFdLCBbLTE2NS4zNDU0NSwgNjEuMDcxNDE2XSwgWy0xNjUuNTkxOTEzLCA2MS4xMDk3NTRdLCBbLTE2NS42MjQ3NzQsIDYxLjI3OTUzOV0sIFstMTY1LjgxNjQ2NywgNjEuMzAxNDQ3XSwgWy0xNjUuOTIwNTI5LCA2MS40MTY0NjNdLCBbLTE2NS45MTUwNTIsIDYxLjU1ODg2M10sIFstMTY2LjEwNjc0NSwgNjEuNDkzMTRdLCBbLTE2Ni4xMzk2MDcsIDYxLjYzMDA2NF0sIFstMTY1LjkwNDA5OCwgNjEuNjYyOTI1XSwgWy0xNjYuMDk1NzkxLCA2MS44MTYyOF0sIFstMTY1Ljc1NjIyMSwgNjEuODI3MjMzXSwgWy0xNjUuNzU2MjIxLCA2Mi4wMTM0NDldLCBbLTE2NS42NzQwNjcsIDYyLjEzOTQxOV0sIFstMTY1LjA0NDIxOSwgNjIuNTM5MjM2XSwgWy0xNjQuOTEyNzcyLCA2Mi42NTk3MjhdLCBbLTE2NC44MTk2NjQsIDYyLjYzNzgyMV0sIFstMTY0Ljg3NDQzMywgNjIuODA3NjA2XSwgWy0xNjQuNjMzNDQ4LCA2My4wOTc4ODRdLCBbLTE2NC40MjUzMjQsIDYzLjIxMjg5OV0sIFstMTY0LjAzNjQ2MiwgNjMuMjYyMTkyXSwgWy0xNjMuNzM1MjMsIDYzLjIxMjg5OV0sIFstMTYzLjMxMzUwNSwgNjMuMDM3NjM3XSwgWy0xNjMuMDM5NjU4LCA2My4wNTk1NDVdLCBbLTE2Mi42NjE3NDksIDYzLjIyOTMzXSwgWy0xNjIuMjcyODg3LCA2My40ODY3NDZdLCBbLTE2Mi4wNzU3MTcsIDYzLjUxNDEzMV0sIFstMTYyLjAyNjQyNCwgNjMuNDQ4NDA4XSwgWy0xNjEuNTU1NDA4LCA2My40NDg0MDhdLCBbLTE2MS4xMzkxNiwgNjMuNTAzMTc3XSwgWy0xNjAuNzY2NzI4LCA2My43NzE1NDddLCBbLTE2MC43NjY3MjgsIDYzLjgzNzI3MV0sIFstMTYwLjk1Mjk0NCwgNjQuMDg5MjFdLCBbLTE2MC45NzQ4NTIsIDY0LjIzNzA4N10sIFstMTYxLjI2NTEzLCA2NC4zOTU5MThdLCBbLTE2MS4zNzQ2NjksIDY0LjUzMjg0Ml0sIFstMTYxLjA3ODkxNCwgNjQuNDk0NTAzXSwgWy0xNjAuNzk5NTksIDY0LjYwOTUxOV0sIFstMTYwLjc4MzE1OSwgNjQuNzE5MDU4XSwgWy0xNjEuMTQ0NjM3LCA2NC45MjE3MDVdLCBbLTE2MS40MTMwMDcsIDY0Ljc2Mjg3M10sIFstMTYxLjY2NDk0NiwgNjQuNzkwMjU4XSwgWy0xNjEuOTAwNDU1LCA2NC43MDI2MjddLCBbLTE2Mi4xNjg4MjUsIDY0LjY4MDcxOV0sIFstMTYyLjIzNDU0OCwgNjQuNjIwNDczXSwgWy0xNjIuNTQxMjU3LCA2NC41MzI4NDJdLCBbLTE2Mi42MzQzNjUsIDY0LjM4NDk2NV0sIFstMTYyLjc4NzcxOSwgNjQuMzI0NzE4XSwgWy0xNjIuODU4OTE5LCA2NC40OTk5OF0sIFstMTYzLjA0NTEzNSwgNjQuNTM4MzE5XSwgWy0xNjMuMTc2NTgyLCA2NC40MDEzOTVdLCBbLTE2My4yNTMyNTksIDY0LjQ2NzExOV0sIFstMTYzLjU5ODMwNiwgNjQuNTY1NzA0XSwgWy0xNjQuMzA0ODMyLCA2NC41NjAyMjddLCBbLTE2NC44MDg3MSwgNjQuNDUwNjg4XSwgWy0xNjUuMDAwNDAzLCA2NC40MzQyNTddLCBbLTE2NS40MTExNzQsIDY0LjQ5OTk4XSwgWy0xNjYuMTg4ODk5LCA2NC41NzY2NThdLCBbLTE2Ni4zOTE1NDYsIDY0LjYzNjkwNF0sIFstMTY2LjQ4NDY1NCwgNjQuNzM1NDg5XSwgWy0xNjYuNDEzNDU0LCA2NC44NzI0MTJdLCBbLTE2Ni42OTI3NzgsIDY0Ljk4NzQyOF0sIFstMTY2LjYzODAwOCwgNjUuMTEzMzk4XSwgWy0xNjYuNDYyNzQ2LCA2NS4xNzkxMjFdLCBbLTE2Ni41MTc1MTYsIDY1LjMzNzk1Ml0sIFstMTY2Ljc5NjgzOSwgNjUuMzM3OTUyXSwgWy0xNjcuMDI2ODcxLCA2NS4zODE3NjhdLCBbLTE2Ny40NzU5OCwgNjUuNDE0NjI5XSwgWy0xNjcuNzExNDg5LCA2NS40OTY3ODRdLCBbLTE2OC4wNzI5NjcsIDY1LjU3ODkzOF0sIFstMTY4LjEwNTgyOCwgNjUuNjgyOTk5XSwgWy0xNjcuNTQxNzAzLCA2NS44MTk5MjNdLCBbLTE2Ni44Mjk3MDEsIDY2LjA0OTk1NF0sIFstMTY2LjMzMTMsIDY2LjE4Njg3OF0sIFstMTY2LjA0NjQ5OSwgNjYuMTEwMjAxXSwgWy0xNjUuNzU2MjIxLCA2Ni4wOTM3N10sIFstMTY1LjY5MDQ5OCwgNjYuMjAzMzA5XSwgWy0xNjUuODY1NzYsIDY2LjIxOTc0XSwgWy0xNjUuODgyMTksIDY2LjMxMjg0OF0sIFstMTY1LjE4NjYxOSwgNjYuNDY2MjAyXSwgWy0xNjQuNDAzNDE3LCA2Ni41ODEyMThdLCBbLTE2My45ODE2OTIsIDY2LjU5MjE3Ml0sIFstMTYzLjc1MTY2MSwgNjYuNTUzODMzXSwgWy0xNjMuODcyMTUzLCA2Ni4zODk1MjVdLCBbLTE2My44MjgzMzgsIDY2LjI3NDUwOV0sIFstMTYzLjkxNTk2OSwgNjYuMTkyMzU1XSwgWy0xNjMuNzY4MDkxLCA2Ni4wNjA5MDhdLCBbLTE2My40OTQyNDQsIDY2LjA4MjgxNl0sIFstMTYzLjE0OTE5NywgNjYuMDYwOTA4XSwgWy0xNjIuNzQ5MzgxLCA2Ni4wODgyOTNdLCBbLTE2Mi42MzQzNjUsIDY2LjAzOTAwMV0sIFstMTYyLjM3MTQ3MiwgNjYuMDI4MDQ3XSwgWy0xNjIuMTQxNDQsIDY2LjA3NzMzOV0sIFstMTYxLjg0MDIwOCwgNjYuMDIyNTddLCBbLTE2MS41NDk5MzEsIDY2LjI0MTY0N10sIFstMTYxLjM0MTgwNywgNjYuMjUyNjAxXSwgWy0xNjEuMTk5NDA2LCA2Ni4yMDg3ODZdLCBbLTE2MS4xMjgyMDYsIDY2LjMzNDc1NV0sIFstMTYxLjUyODAyMywgNjYuMzk1MDAyXSwgWy0xNjEuOTExNDA5LCA2Ni4zNDU3MDldLCBbLTE2MS44NzMwNywgNjYuNTEwMDE3XSwgWy0xNjIuMTc0MzAyLCA2Ni42ODUyOF0sIFstMTYyLjUwMjkxOCwgNjYuNzQwMDQ5XSwgWy0xNjIuNjAxNTAzLCA2Ni44OTg4OF0sIFstMTYyLjM0NDA4NywgNjYuOTM3MjE5XSwgWy0xNjIuMDE1NDcxLCA2Ni43NzgzODhdLCBbLTE2Mi4wNzU3MTcsIDY2LjY1MjQxOF0sIFstMTYxLjkxNjg4NiwgNjYuNTUzODMzXSwgWy0xNjEuNTcxODM4LCA2Ni40Mzg4MTddLCBbLTE2MS40ODk2ODQsIDY2LjU1OTMxXSwgWy0xNjEuODg0MDI0LCA2Ni43MTgxNDFdLCBbLTE2MS43MTQyMzksIDY3LjAwMjk0Ml0sIFstMTYxLjg1MTE2MiwgNjcuMDUyMjM1XSwgWy0xNjIuMjQwMDI1LCA2Ni45OTE5ODhdLCBbLTE2Mi42Mzk4NDIsIDY3LjAwODQxOV0sIFstMTYyLjcwMDA4OCwgNjcuMDU3NzEyXSwgWy0xNjIuOTAyNzM1LCA2Ny4wMDg0MTldLCBbLTE2My43NDA3MDcsIDY3LjEyODkxMl0sIFstMTYzLjc1NzEzOCwgNjcuMjU0ODgxXSwgWy0xNjQuMDA5MDc3LCA2Ny41MzQyMDVdLCBbLTE2NC4yMTE3MjQsIDY3LjYzODI2N10sIFstMTY0LjUzNDg2MywgNjcuNzI1ODk4XSwgWy0xNjUuMTkyMDk2LCA2Ny45NjY4ODRdLCBbLTE2NS40OTMzMjgsIDY4LjA1OTk5Ml0sIFstMTY1Ljc5NDU1OSwgNjguMDgxODk5XSwgWy0xNjYuMjQzNjY4LCA2OC4yNDYyMDhdLCBbLTE2Ni42ODE4MjQsIDY4LjMzOTMxNl0sIFstMTY2LjcwMzczMSwgNjguMzcyMTc3XSwgWy0xNjYuMzc1MTE1LCA2OC40MjE0N10sIFstMTY2LjIyNzIzOCwgNjguNTc0ODI0XSwgWy0xNjYuMjE2Mjg0LCA2OC44ODE1MzNdLCBbLTE2NS4zMjkwMTksIDY4Ljg1OTYyNV0sIFstMTY0LjI1NTUzOSwgNjguOTMwODI1XSwgWy0xNjMuOTc2MjE1LCA2OC45ODU1OTVdLCBbLTE2My41MzI1ODMsIDY5LjEzODk0OV0sIFstMTYzLjExMDg1OSwgNjkuMzc0NDU3XSwgWy0xNjMuMDIzMjI4LCA2OS42MDk5NjZdLCBbLTE2Mi44NDI0ODksIDY5LjgxMjYxM10sIFstMTYyLjQ3MDA1NywgNjkuOTgyMzk4XSwgWy0xNjIuMzExMjI1LCA3MC4xMDgzNjddLCBbLTE2MS44NTExNjIsIDcwLjMxMTAxNF0sIFstMTYxLjc3OTk2MiwgNzAuMjU2MjQ1XSwgWy0xNjEuMzk2NTc2LCA3MC4yMzk4MTRdLCBbLTE2MC44Mzc5MjgsIDcwLjM0Mzg3Nl0sIFstMTYwLjQ4NzQwNCwgNzAuNDUzNDE1XSwgWy0xNTkuNjQ5NDMyLCA3MC43OTI5ODVdLCBbLTE1OS4zMzE3NywgNzAuODA5NDE2XSwgWy0xNTkuMjk4OTA4LCA3MC43NjAxMjNdLCBbLTE1OC45NzU3NjksIDcwLjc5ODQ2Ml0sIFstMTU4LjY1ODEwNiwgNzAuNzg3NTA4XSwgWy0xNTguMDMzNzM1LCA3MC44MzEzMjNdLCBbLTE1Ny40MjAzMTgsIDcwLjk3OTIwMV0sIFstMTU2LjgxMjM3NywgNzEuMjg1OTA5XSwgWy0xNTYuNTY1OTE1LCA3MS4zNTE2MzNdLCBbLTE1Ni41MjIwOTksIDcxLjI5Njg2M10sIFstMTU1LjU4NTU0MywgNzEuMTcwODk0XSwgWy0xNTUuNTA4ODY1LCA3MS4wODMyNjNdLCBbLTE1NS44MzIwMDUsIDcwLjk2ODI0N10sIFstMTU1Ljk3OTg4MiwgNzAuOTYyNzddLCBbLTE1NS45NzQ0MDUsIDcwLjgwOTQxNl0sIFstMTU1LjUwMzM4OCwgNzAuODU4NzA4XSwgWy0xNTUuNDc2MDA0LCA3MC45NDA4NjJdLCBbLTE1NS4yNjI0MDMsIDcxLjAxNzUzOV0sIFstMTU1LjE5MTIwMywgNzAuOTczNzI0XSwgWy0xNTUuMDMyMzcyLCA3MS4xNDg5ODZdLCBbLTE1NC41NjY4MzIsIDcwLjk5MDE1NV0sIFstMTU0LjY0MzUwOSwgNzAuODY5NjYyXSwgWy0xNTQuMzUzMjMxLCA3MC44MzY4XSwgWy0xNTQuMTgzNDQ2LCA3MC43NjU2XSwgWy0xNTMuOTMxNTA3LCA3MC44ODA2MTZdLCBbLTE1My40ODc4NzQsIDcwLjg4NjA5M10sIFstMTUzLjIzNTkzNSwgNzAuOTI0NDMxXSwgWy0xNTIuNTg5NjU2LCA3MC44ODYwOTNdLCBbLTE1Mi4yNjEwNCwgNzAuODQyMjc3XSwgWy0xNTIuNDE5ODcxLCA3MC42MDY3NjldLCBbLTE1MS44MTc0MDgsIDcwLjU0NjUyM10sIFstMTUxLjc3MzU5MiwgNzAuNDg2Mjc2XSwgWy0xNTEuMTg3NTU5LCA3MC4zODIyMTRdLCBbLTE1MS4xODIwODIsIDcwLjQzMTUwN10sIFstMTUwLjc2MDM1OCwgNzAuNDk3MjNdLCBbLTE1MC4zNTUwNjQsIDcwLjQ5MTc1M10sIFstMTUwLjM0OTU4OCwgNzAuNDM2OTg0XSwgWy0xNTAuMTE0MDc5LCA3MC40MzE1MDddLCBbLTE0OS44Njc2MTcsIDcwLjUwODE4NF0sIFstMTQ5LjQ2MjMyMywgNzAuNTE5MTM4XSwgWy0xNDkuMTc3NTIyLCA3MC40ODYyNzZdLCBbLTE0OC43ODg2NiwgNzAuNDA0MTIyXSwgWy0xNDguNjA3OTIxLCA3MC40MjA1NTNdLCBbLTE0OC4zNTA1MDQsIDcwLjMwNTUzN10sIFstMTQ4LjIwMjYyNywgNzAuMzQ5MzUzXSwgWy0xNDcuOTYxNjQyLCA3MC4zMTY0OTFdLCBbLTE0Ny43ODYzNzksIDcwLjI0NTI5MV1dXSwgW1tbLTE1Mi45NDAxOCwgNTguMDI2MjM3XSwgWy0xNTIuOTQ1NjU3LCA1Ny45ODI0MjFdLCBbLTE1My4yOTA3MDUsIDU4LjA0ODE0NV0sIFstMTUzLjA0NDI0MiwgNTguMzA1NTYxXSwgWy0xNTIuODE5Njg4LCA1OC4zMjc0NjldLCBbLTE1Mi42NjYzMzMsIDU4LjU2Mjk3N10sIFstMTUyLjQ5NjU0OCwgNTguMzU0ODUzXSwgWy0xNTIuMzU0MTQ4LCA1OC40MjYwNTNdLCBbLTE1Mi4wODAzMDEsIDU4LjMxMTAzOF0sIFstMTUyLjA4MDMwMSwgNTguMTUyMjA2XSwgWy0xNTIuNDgwMTE3LCA1OC4xMzAyOTldLCBbLTE1Mi42NTUzNzksIDU4LjA1OTA5OF0sIFstMTUyLjk0MDE4LCA1OC4wMjYyMzddXV0sIFtbWy0xNTMuOTU4ODkxLCA1Ny41Mzg3ODldLCBbLTE1My42NzQwOSwgNTcuNjcwMjM2XSwgWy0xNTMuOTMxNTA3LCA1Ny42OTc2Ml0sIFstMTUzLjkzNjk4MywgNTcuODEyNjM2XSwgWy0xNTMuNzIzMzgzLCA1Ny44ODkzMTNdLCBbLTE1My41NzAwMjgsIDU3LjgzNDU0NF0sIFstMTUzLjU0ODEyMSwgNTcuNzE5NTI4XSwgWy0xNTMuNDYwNDksIDU3Ljc5NjIwNV0sIFstMTUzLjQ1NTAxMywgNTcuOTY1OTldLCBbLTE1My4yNjg3OTcsIDU3Ljg4OTMxM10sIFstMTUzLjIzNTkzNSwgNTcuOTk4ODUyXSwgWy0xNTMuMDcxNjI3LCA1Ny45MzMxMjldLCBbLTE1Mi44NzQ0NTcsIDU3LjkzMzEyOV0sIFstMTUyLjcyMTEwMywgNTcuOTkzMzc1XSwgWy0xNTIuNDY5MTYzLCA1Ny44ODkzMTNdLCBbLTE1Mi40NjkxNjMsIDU3LjU5OTAzNV0sIFstMTUyLjE1MTUwMSwgNTcuNjIwOTQzXSwgWy0xNTIuMzU5NjI1LCA1Ny40MjkyNV0sIFstMTUyLjc0MzAxLCA1Ny41MDU5MjhdLCBbLTE1Mi42MDA2MSwgNTcuMzc5OTU4XSwgWy0xNTIuNzEwMTQ5LCA1Ny4yNzU4OTZdLCBbLTE1Mi45MDczMTksIDU3LjMyNTE4OF0sIFstMTUyLjkxMjc5NiwgNTcuMTI4MDE5XSwgWy0xNTMuMjE0MDI3LCA1Ny4wNzMyNDldLCBbLTE1My4zMTI2MTIsIDU2Ljk5MTA5NV0sIFstMTUzLjQ5ODgyOCwgNTcuMDY3NzcyXSwgWy0xNTMuNjk1OTk4LCA1Ni44NTk2NDldLCBbLTE1My44NDkzNTIsIDU2LjgzNzc0MV0sIFstMTU0LjAxMzY2MSwgNTYuNzQ0NjMzXSwgWy0xNTQuMDczOTA3LCA1Ni45NjkxODddLCBbLTE1NC4zMDM5MzgsIDU2Ljg0ODY5NV0sIFstMTU0LjMxNDg5MiwgNTYuOTE5ODk1XSwgWy0xNTQuNTIzMDE2LCA1Ni45OTEwOTVdLCBbLTE1NC41Mzk0NDcsIDU3LjE5Mzc0Ml0sIFstMTU0Ljc0MjA5NCwgNTcuMjc1ODk2XSwgWy0xNTQuNjI3MDc4LCA1Ny41MTE0MDRdLCBbLTE1NC4yMjcyNjEsIDU3LjY1OTI4Ml0sIFstMTUzLjk4MDc5OSwgNTcuNjQ4MzI4XSwgWy0xNTMuOTU4ODkxLCA1Ny41Mzg3ODldXV0sIFtbWy0xNTQuNTMzOTcsIDU2LjYwMjIzMl0sIFstMTU0Ljc0MjA5NCwgNTYuMzk5NTg2XSwgWy0xNTQuODA3ODE3LCA1Ni40MzI0NDddLCBbLTE1NC41MzM5NywgNTYuNjAyMjMyXV1dLCBbW1stMTU1LjYzNDgzNSwgNTUuOTIzMDkyXSwgWy0xNTUuNDc2MDA0LCA1NS45MTIxMzhdLCBbLTE1NS41MzA3NzMsIDU1LjcwNDAxNF0sIFstMTU1Ljc5MzY2NiwgNTUuNzMxMzk5XSwgWy0xNTUuODM3NDgyLCA1NS44MDI1OTldLCBbLTE1NS42MzQ4MzUsIDU1LjkyMzA5Ml1dXSwgW1tbLTE1OS44OTA0MTgsIDU1LjI4MjI5XSwgWy0xNTkuOTUwNjY0LCA1NS4wNjg2ODldLCBbLTE2MC4yNTczNzMsIDU0Ljg5MzQyN10sIFstMTYwLjEwOTQ5NSwgNTUuMTYxNzk3XSwgWy0xNjAuMDA1NDMzLCA1NS4xMzQ0MTJdLCBbLTE1OS44OTA0MTgsIDU1LjI4MjI5XV1dLCBbW1stMTYwLjUyMDI2NiwgNTUuMzU4OTY3XSwgWy0xNjAuMzM0MDUsIDU1LjM1ODk2N10sIFstMTYwLjMzOTUyNywgNTUuMjQ5NDI4XSwgWy0xNjAuNTI1NzQzLCA1NS4xMjg5MzVdLCBbLTE2MC42OTAwNTEsIDU1LjIxMTA4OV0sIFstMTYwLjc5NDExMywgNTUuMTM0NDEyXSwgWy0xNjAuODU0MzU5LCA1NS4zMjA2MjhdLCBbLTE2MC43OTk1OSwgNTUuMzgwODc1XSwgWy0xNjAuNTIwMjY2LCA1NS4zNTg5NjddXV0sIFtbWy0xNjIuMjU2NDU2LCA1NC45ODEwNThdLCBbLTE2Mi4yMzQ1NDgsIDU0Ljg5MzQyN10sIFstMTYyLjM0OTU2NCwgNTQuODM4NjU4XSwgWy0xNjIuNDM3MTk1LCA1NC45MzE3NjZdLCBbLTE2Mi4yNTY0NTYsIDU0Ljk4MTA1OF1dXSwgW1tbLTE2Mi40MTUyODcsIDYzLjYzNDYyNF0sIFstMTYyLjU2MzE2NSwgNjMuNTM2MDM5XSwgWy0xNjIuNjEyNDU3LCA2My42MjM2N10sIFstMTYyLjQxNTI4NywgNjMuNjM0NjI0XV1dLCBbW1stMTYyLjgwNDE1LCA1NC40ODgxMzNdLCBbLTE2Mi41OTA1NDksIDU0LjQ0OTc5NV0sIFstMTYyLjYxMjQ1NywgNTQuMzY3NjQxXSwgWy0xNjIuNzgyMjQyLCA1NC4zNzMxMThdLCBbLTE2Mi44MDQxNSwgNTQuNDg4MTMzXV1dLCBbW1stMTY1LjU0ODA5NywgNTQuMjk2NDRdLCBbLTE2NS40NzY4OTcsIDU0LjE4MTQyNV0sIFstMTY1LjYzMDI1MSwgNTQuMTMyMTMyXSwgWy0xNjUuNjg1MDIxLCA1NC4yNTI2MjVdLCBbLTE2NS41NDgwOTcsIDU0LjI5NjQ0XV1dLCBbW1stMTY1LjczOTc5LCA1NC4xNTQwNF0sIFstMTY2LjA0NjQ5OSwgNTQuMDQ0NTAxXSwgWy0xNjYuMTEyMjIyLCA1NC4xMjExNzhdLCBbLTE2NS45ODA3NzUsIDU0LjIxOTc2M10sIFstMTY1LjczOTc5LCA1NC4xNTQwNF1dXSwgW1tbLTE2Ni4zNjQxNjEsIDYwLjM1OTQxM10sIFstMTY2LjEzNDEzLCA2MC4zOTc3NTJdLCBbLTE2Ni4wODQ4MzcsIDYwLjMyNjU1Ml0sIFstMTY1Ljg4MjE5LCA2MC4zNDI5ODNdLCBbLTE2NS42ODUwMjEsIDYwLjI3NzI1OV0sIFstMTY1LjY0NjY4MiwgNTkuOTkyNDU4XSwgWy0xNjUuNzUwNzQ0LCA1OS44OTkzNV0sIFstMTY2LjAwODE2LCA1OS44NDQ1ODFdLCBbLTE2Ni4wNjI5MjksIDU5Ljc0NTk5Nl0sIFstMTY2LjQ0MDgzOCwgNTkuODU1NTM1XSwgWy0xNjYuNjE2MSwgNTkuODUwMDU4XSwgWy0xNjYuOTk0MDA5LCA1OS45OTI0NThdLCBbLTE2Ny4xMjU0NTYsIDU5Ljk5MjQ1OF0sIFstMTY3LjM0NDUzNCwgNjAuMDc0NjEzXSwgWy0xNjcuNDIxMjExLCA2MC4yMDYwNTldLCBbLTE2Ny4zMTE2NzIsIDYwLjIzODkyMV0sIFstMTY2LjkzOTI0LCA2MC4yMDYwNTldLCBbLTE2Ni43NjM5NzgsIDYwLjMxMDEyMV0sIFstMTY2LjU3Nzc2MiwgNjAuMzIxMDc1XSwgWy0xNjYuNDk1NjA4LCA2MC4zOTIyNzVdLCBbLTE2Ni4zNjQxNjEsIDYwLjM1OTQxM11dXSwgW1tbLTE2Ni4zNzUxMTUsIDU0LjAxMTY0XSwgWy0xNjYuMjEwODA3LCA1My45MzQ5NjJdLCBbLTE2Ni41NDQ5LCA1My43NDg3NDZdLCBbLTE2Ni41Mzk0MjMsIDUzLjcxNTg4NV0sIFstMTY2LjExNzY5OSwgNTMuODUyODA4XSwgWy0xNjYuMTEyMjIyLCA1My43NzYxMzFdLCBbLTE2Ni4yODIwMDcsIDUzLjY4MzAyM10sIFstMTY2LjU1NTg1NCwgNTMuNjIyNzc3XSwgWy0xNjYuNTgzMjM5LCA1My41Mjk2NjldLCBbLTE2Ni44Nzg5OTQsIDUzLjQzMTA4NF0sIFstMTY3LjEzNjQxLCA1My40MjU2MDddLCBbLTE2Ny4zMDYxOTUsIDUzLjMzMjQ5OV0sIFstMTY3LjYyMzg1NywgNTMuMjUwMzQ1XSwgWy0xNjcuNzkzNjQzLCA1My4zMzc5NzZdLCBbLTE2Ny40NTk1NDksIDUzLjQ0MjAzOF0sIFstMTY3LjM1NTQ4NywgNTMuNDI1NjA3XSwgWy0xNjcuMTAzNTQ4LCA1My41MTMyMzhdLCBbLTE2Ny4xNjM3OTQsIDUzLjYxMTgyM10sIFstMTY3LjAyMTM5NCwgNTMuNzE1ODg1XSwgWy0xNjYuODA3NzkzLCA1My42NjY1OTJdLCBbLTE2Ni43ODU4ODYsIDUzLjczMjMxNl0sIFstMTY3LjAxNTkxNywgNTMuNzU0MjIzXSwgWy0xNjcuMTQxODg3LCA1My44MjU0MjRdLCBbLTE2Ny4wMzIzNDgsIDUzLjk0NTkxNl0sIFstMTY2LjY0MzQ4NSwgNTQuMDE3MTE2XSwgWy0xNjYuNTYxMzMxLCA1My44ODAxOTNdLCBbLTE2Ni4zNzUxMTUsIDU0LjAxMTY0XV1dLCBbW1stMTY4Ljc5MDQ0NiwgNTMuMTU3MjM3XSwgWy0xNjguNDA3MDYsIDUzLjM0ODkzXSwgWy0xNjguMzg1MTUyLCA1My40MzEwODRdLCBbLTE2OC4yMzcyNzUsIDUzLjUyNDE5Ml0sIFstMTY4LjAwNzI0MywgNTMuNTY4MDA3XSwgWy0xNjcuODg2NzUxLCA1My41MTg3MTVdLCBbLTE2Ny44NDI5MzUsIDUzLjM4NzI2OF0sIFstMTY4LjI3MDEzNiwgNTMuMjQ0ODY4XSwgWy0xNjguNTAwMTY4LCA1My4wMzY3NDRdLCBbLTE2OC42ODYzODQsIDUyLjk2NTU0NF0sIFstMTY4Ljc5MDQ0NiwgNTMuMTU3MjM3XV1dLCBbW1stMTY5Ljc0ODkxLCA1Mi44OTQzNDRdLCBbLTE2OS43MDUwOTUsIDUyLjc5NTc1OV0sIFstMTY5Ljk2MjUxMSwgNTIuNzkwMjgyXSwgWy0xNjkuOTg5ODk2LCA1Mi44NTYwMDVdLCBbLTE2OS43NDg5MSwgNTIuODk0MzQ0XV1dLCBbW1stMTcwLjE0ODcyNywgNTcuMjIxMTI3XSwgWy0xNzAuMjg1NjUsIDU3LjEyODAxOV0sIFstMTcwLjMxMzAzNSwgNTcuMjIxMTI3XSwgWy0xNzAuMTQ4NzI3LCA1Ny4yMjExMjddXV0sIFtbWy0xNzAuNjY5MDM2LCA1Mi42OTcxNzRdLCBbLTE3MC42MDMzMTMsIDUyLjYwNDA2Nl0sIFstMTcwLjc4OTUyOSwgNTIuNTM4MzQzXSwgWy0xNzAuODE2OTE0LCA1Mi42MzY5MjhdLCBbLTE3MC42NjkwMzYsIDUyLjY5NzE3NF1dXSwgW1tbLTE3MS43NDI1MTcsIDYzLjcxNjc3OF0sIFstMTcwLjk0ODM2LCA2My41Njg5XSwgWy0xNzAuNDg4Mjk3LCA2My42OTQ4N10sIFstMTcwLjI4MDE3NCwgNjMuNjgzOTE2XSwgWy0xNzAuMDkzOTU4LCA2My42MTI3MTZdLCBbLTE3MC4wNDQ2NjUsIDYzLjQ5MjIyM10sIFstMTY5LjY0NDg0OCwgNjMuNDI2NV0sIFstMTY5LjUxODg3OSwgNjMuMzY2MjU0XSwgWy0xNjguOTk4NTcsIDYzLjMzODg2OV0sIFstMTY4LjY4NjM4NCwgNjMuMjk1MDUzXSwgWy0xNjguODU2MTY5LCA2My4xNDcxNzZdLCBbLTE2OS4xMDgxMDgsIDYzLjE4MDAzOF0sIFstMTY5LjM3NjQ3OCwgNjMuMTUyNjUzXSwgWy0xNjkuNTEzNDAyLCA2My4wODY5M10sIFstMTY5LjYzOTM3MiwgNjIuOTM5MDUyXSwgWy0xNjkuODMxMDY0LCA2My4wNzU5NzZdLCBbLTE3MC4wNTU2MTksIDYzLjE2OTA4NF0sIFstMTcwLjI2Mzc0MywgNjMuMTgwMDM4XSwgWy0xNzAuMzYyMzI4LCA2My4yODQxXSwgWy0xNzAuODY2MjA2LCA2My40MTU1NDZdLCBbLTE3MS4xMDE3MTUsIDYzLjQyMTAyM10sIFstMTcxLjQ2MzE5MywgNjMuMzA2MDA3XSwgWy0xNzEuNzM3MDQsIDYzLjM2NjI1NF0sIFstMTcxLjg1MjA1NSwgNjMuNDg2NzQ2XSwgWy0xNzEuNzQyNTE3LCA2My43MTY3NzhdXV0sIFtbWy0xNzIuNDMyNjExLCA1Mi4zOTA0NjVdLCBbLTE3Mi40MTYxOCwgNTIuMjc1NDQ5XSwgWy0xNzIuNjA3ODczLCA1Mi4yNTM1NDJdLCBbLTE3Mi41Njk1MzUsIDUyLjM1MjEyN10sIFstMTcyLjQzMjYxMSwgNTIuMzkwNDY1XV1dLCBbW1stMTczLjYyNjU4NCwgNTIuMTQ5NDhdLCBbLTE3My40OTUxMzgsIDUyLjEwNTY2NF0sIFstMTczLjEyMjcwNiwgNTIuMTExMTQxXSwgWy0xNzMuMTA2Mjc1LCA1Mi4wNzgyOF0sIFstMTczLjU0OTkwNywgNTIuMDI4OTg3XSwgWy0xNzMuNjI2NTg0LCA1Mi4xNDk0OF1dXSwgW1tbLTE3NC4zMjIxNTYsIDUyLjI4MDkyNl0sIFstMTc0LjMyNzYzMiwgNTIuMzc5NTExXSwgWy0xNzQuMTg1MjMyLCA1Mi40MTc4NV0sIFstMTczLjk4MjU4NSwgNTIuMzE5MjY1XSwgWy0xNzQuMDU5MjYyLCA1Mi4yMjYxNTddLCBbLTE3NC4xNzk3NTUsIDUyLjIzMTYzNF0sIFstMTc0LjE0MTQxNywgNTIuMTI3NTcyXSwgWy0xNzQuMzMzMTA5LCA1Mi4xMTY2MThdLCBbLTE3NC43Mzg0MDMsIDUyLjAwNzA3OV0sIFstMTc0Ljk2ODQzNSwgNTIuMDM5OTQxXSwgWy0xNzQuOTAyNzExLCA1Mi4xMTY2MThdLCBbLTE3NC42NTYyNDksIDUyLjEwNTY2NF0sIFstMTc0LjMyMjE1NiwgNTIuMjgwOTI2XV1dLCBbW1stMTc2LjQ2OTExNiwgNTEuODUzNzI1XSwgWy0xNzYuMjg4Mzc3LCA1MS44NzAxNTZdLCBbLTE3Ni4yODgzNzcsIDUxLjc0NDE4Nl0sIFstMTc2LjUxODQwOSwgNTEuNzYwNjE3XSwgWy0xNzYuODAzMjEsIDUxLjYxMjc0XSwgWy0xNzYuOTEyNzQ4LCA1MS44MDk5MV0sIFstMTc2Ljc5MjI1NiwgNTEuODE1Mzg2XSwgWy0xNzYuNzc1ODI1LCA1MS45NjMyNjRdLCBbLTE3Ni42Mjc5NDcsIDUxLjk2ODc0MV0sIFstMTc2LjYyNzk0NywgNTEuODU5MjAyXSwgWy0xNzYuNDY5MTE2LCA1MS44NTM3MjVdXV0sIFtbWy0xNzcuMTUzNzM0LCA1MS45NDY4MzNdLCBbLTE3Ny4wNDQxOTUsIDUxLjg5NzU0MV0sIFstMTc3LjEyMDg3MiwgNTEuNzI3NzU1XSwgWy0xNzcuMjc0MjI2LCA1MS42Nzg0NjNdLCBbLTE3Ny4yNzk3MDMsIDUxLjc4MjUyNV0sIFstMTc3LjE1MzczNCwgNTEuOTQ2ODMzXV1dLCBbW1stMTc4LjEyMzE1MiwgNTEuOTE5NDQ4XSwgWy0xNzcuOTUzMzY3LCA1MS45MTM5NzFdLCBbLTE3Ny44MDAwMTMsIDUxLjc5MzQ3OV0sIFstMTc3Ljk2NDMyMSwgNTEuNjUxMDc4XSwgWy0xNzguMTIzMTUyLCA1MS45MTk0NDhdXV0sIFtbWzE3My4xMDc1NTcsIDUyLjk5MjkyOV0sIFsxNzMuMjkzNzczLCA1Mi45MjcyMDVdLCBbMTczLjMwNDcyNiwgNTIuODIzMTQzXSwgWzE3Mi45MDQ5MSwgNTIuNzYyODk3XSwgWzE3Mi42NDIwMTcsIDUyLjkyNzIwNV0sIFsxNzIuNjQyMDE3LCA1My4wMDM4ODNdLCBbMTczLjEwNzU1NywgNTIuOTkyOTI5XV1dXSwgInR5cGUiOiAiTXVsdGlQb2x5Z29uIn0sICJpZCI6ICJBSyIsICJwcm9wZXJ0aWVzIjogeyJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiQWxhc2thIiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiIzc4YzY3OSIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbLTEwOS4wNDI1MDMsIDM3LjAwMDI2M10sIFstMTA5LjA0Nzk4LCAzMS4zMzE2MjldLCBbLTExMS4wNzQ0NDgsIDMxLjMzMTYyOV0sIFstMTEyLjI0NjUxMywgMzEuNzA0MDYxXSwgWy0xMTQuODE1MTk4LCAzMi40OTI3NDFdLCBbLTExNC43MjIwOSwgMzIuNzE3Mjk1XSwgWy0xMTQuNTI0OTIxLCAzMi43NTU2MzRdLCBbLTExNC40NzAxNTEsIDMyLjg0MzI2NV0sIFstMTE0LjUyNDkyMSwgMzMuMDI5NDgxXSwgWy0xMTQuNjYxODQ0LCAzMy4wMzQ5NThdLCBbLTExNC43Mjc1NjcsIDMzLjQwNzM5XSwgWy0xMTQuNTI0OTIxLCAzMy41NDk3OV0sIFstMTE0LjQ5NzUzNiwgMzMuNjk3NjY4XSwgWy0xMTQuNTM1ODc0LCAzMy45MzMxNzZdLCBbLTExNC40MTUzODIsIDM0LjEwODQzOF0sIFstMTE0LjI1NjU1MSwgMzQuMTc0MTYyXSwgWy0xMTQuMTM2MDU4LCAzNC4zMDU2MDhdLCBbLTExNC4zMzMyMjgsIDM0LjQ0ODAwOV0sIFstMTE0LjQ3MDE1MSwgMzQuNzEwOTAyXSwgWy0xMTQuNjM0NDU5LCAzNC44NzUyMV0sIFstMTE0LjYzNDQ1OSwgMzUuMDAxMThdLCBbLTExNC41NzQyMTMsIDM1LjEzODEwM10sIFstMTE0LjU5NjEyMSwgMzUuMzI0MzE5XSwgWy0xMTQuNjc4Mjc1LCAzNS41MTYwMTJdLCBbLTExNC43Mzg1MjEsIDM2LjEwMjA0NV0sIFstMTE0LjM3MTU2NiwgMzYuMTQwMzgzXSwgWy0xMTQuMjUxMDc0LCAzNi4wMTk4OV0sIFstMTE0LjE1MjQ4OSwgMzYuMDI1MzY3XSwgWy0xMTQuMDQ4NDI3LCAzNi4xOTUxNTNdLCBbLTExNC4wNDg0MjcsIDM3LjAwMDI2M10sIFstMTEwLjQ5OTM2OSwgMzcuMDA1NzRdLCBbLTEwOS4wNDI1MDMsIDM3LjAwMDI2M11dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiQVoiLCAicHJvcGVydGllcyI6IHsiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIkFyaXpvbmEiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICIjMzFhMzU0IiwgImZpbGxPcGFjaXR5IjogMC42LCAib3BhY2l0eSI6IDEsICJ3ZWlnaHQiOiAxfX0sICJ0eXBlIjogIkZlYXR1cmUifSwgeyJnZW9tZXRyeSI6IHsiY29vcmRpbmF0ZXMiOiBbW1stOTQuNDczODQyLCAzNi41MDE4NjFdLCBbLTkwLjE1MjUzNiwgMzYuNDk2Mzg0XSwgWy05MC4wNjQ5MDUsIDM2LjMwNDY5MV0sIFstOTAuMjE4MjU5LCAzNi4xODQxOTldLCBbLTkwLjM3NzA5MSwgMzUuOTk3OTgzXSwgWy04OS43MzA4MTIsIDM1Ljk5Nzk4M10sIFstODkuNzYzNjczLCAzNS44MTE3NjddLCBbLTg5LjkxMTU1MSwgMzUuNzU2OTk3XSwgWy04OS45NDQ0MTIsIDM1LjYwMzY0M10sIFstOTAuMTMwNjI4LCAzNS40MzkzMzVdLCBbLTkwLjExNDE5NywgMzUuMTk4MzQ5XSwgWy05MC4yMTI3ODIsIDM1LjAyMzA4N10sIFstOTAuMzExMzY3LCAzNC45OTU3MDNdLCBbLTkwLjI1MTEyMSwgMzQuOTA4MDcyXSwgWy05MC40MDk5NTIsIDM0LjgzMTM5NF0sIFstOTAuNDgxMTUyLCAzNC42NjE2MDldLCBbLTkwLjU4NTIxNCwgMzQuNjE3Nzk0XSwgWy05MC41Njg3ODMsIDM0LjQyMDYyNF0sIFstOTAuNzQ5NTIyLCAzNC4zNjU4NTRdLCBbLTkwLjc0NDA0NiwgMzQuMzAwMTMxXSwgWy05MC45NTIxNjksIDM0LjEzNTgyM10sIFstOTAuODkxOTIzLCAzNC4wMjYyODRdLCBbLTkxLjA3MjY2MiwgMzMuODY3NDUzXSwgWy05MS4yMzE0OTMsIDMzLjU2MDc0NF0sIFstOTEuMDU2MjMxLCAzMy40MjkyOThdLCBbLTkxLjE0Mzg2MiwgMzMuMzQ3MTQ0XSwgWy05MS4wODkwOTMsIDMzLjEzOTAyXSwgWy05MS4xNjU3NywgMzMuMDAyMDk2XSwgWy05My42MDg0ODUsIDMzLjAxODUyN10sIFstOTQuMDQxMTY0LCAzMy4wMTg1MjddLCBbLTk0LjA0MTE2NCwgMzMuNTQ5NzldLCBbLTk0LjE4MzU2NCwgMzMuNTkzNjA2XSwgWy05NC4zODA3MzQsIDMzLjU0NDMxM10sIFstOTQuNDg0Nzk2LCAzMy42Mzc0MjFdLCBbLTk0LjQzMDAyNiwgMzUuMzk1NTE5XSwgWy05NC42MTYyNDIsIDM2LjUwMTg2MV0sIFstOTQuNDczODQyLCAzNi41MDE4NjFdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIkFSIiwgInByb3BlcnRpZXMiOiB7ImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJBcmthbnNhcyIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiM3OGM2NzkiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWy0xMjMuMjMzMjU2LCA0Mi4wMDYxODZdLCBbLTEyMi4zNzg4NTMsIDQyLjAxMTY2M10sIFstMTIxLjAzNzAwMywgNDEuOTk1MjMyXSwgWy0xMjAuMDAxODYxLCA0MS45OTUyMzJdLCBbLTExOS45OTYzODQsIDQwLjI2NDUxOV0sIFstMTIwLjAwMTg2MSwgMzguOTk5MzQ2XSwgWy0xMTguNzE0NzgsIDM4LjEwMTEyOF0sIFstMTE3LjQ5ODg5OSwgMzcuMjE5MzRdLCBbLTExNi41NDA0MzUsIDM2LjUwMTg2MV0sIFstMTE1Ljg1MDM0LCAzNS45NzA1OThdLCBbLTExNC42MzQ0NTksIDM1LjAwMTE4XSwgWy0xMTQuNjM0NDU5LCAzNC44NzUyMV0sIFstMTE0LjQ3MDE1MSwgMzQuNzEwOTAyXSwgWy0xMTQuMzMzMjI4LCAzNC40NDgwMDldLCBbLTExNC4xMzYwNTgsIDM0LjMwNTYwOF0sIFstMTE0LjI1NjU1MSwgMzQuMTc0MTYyXSwgWy0xMTQuNDE1MzgyLCAzNC4xMDg0MzhdLCBbLTExNC41MzU4NzQsIDMzLjkzMzE3Nl0sIFstMTE0LjQ5NzUzNiwgMzMuNjk3NjY4XSwgWy0xMTQuNTI0OTIxLCAzMy41NDk3OV0sIFstMTE0LjcyNzU2NywgMzMuNDA3MzldLCBbLTExNC42NjE4NDQsIDMzLjAzNDk1OF0sIFstMTE0LjUyNDkyMSwgMzMuMDI5NDgxXSwgWy0xMTQuNDcwMTUxLCAzMi44NDMyNjVdLCBbLTExNC41MjQ5MjEsIDMyLjc1NTYzNF0sIFstMTE0LjcyMjA5LCAzMi43MTcyOTVdLCBbLTExNi4wNDc1MSwgMzIuNjI0MTg3XSwgWy0xMTcuMTI2NDY3LCAzMi41MzY1NTZdLCBbLTExNy4yNDY5NiwgMzIuNjY4MDAzXSwgWy0xMTcuMjUyNDM3LCAzMi44NzYxMjddLCBbLTExNy4zMjkxMTQsIDMzLjEyMjU4OV0sIFstMTE3LjQ3MTUxNSwgMzMuMjk3ODUxXSwgWy0xMTcuNzgzNywgMzMuNTM4ODM2XSwgWy0xMTguMTgzNTE3LCAzMy43NjMzOTFdLCBbLTExOC4yNjAxOTQsIDMzLjcwMzE0NV0sIFstMTE4LjQxMzU0OCwgMzMuNzQxNDgzXSwgWy0xMTguMzkxNjQxLCAzMy44NDAwNjhdLCBbLTExOC41NjY5MDMsIDM0LjA0MjcxNV0sIFstMTE4LjgwMjQxMSwgMzMuOTk4ODk5XSwgWy0xMTkuMjE4NjU5LCAzNC4xNDY3NzddLCBbLTExOS4yNzg5MDUsIDM0LjI2NzI3XSwgWy0xMTkuNTU4MjI5LCAzNC40MTUxNDddLCBbLTExOS44NzU4OTEsIDM0LjQwOTY3XSwgWy0xMjAuMTM4Nzg0LCAzNC40NzUzOTNdLCBbLTEyMC40NzI4NzgsIDM0LjQ0ODAwOV0sIFstMTIwLjY0ODE0LCAzNC41Nzk0NTVdLCBbLTEyMC42MDk4MDEsIDM0Ljg1ODc3OV0sIFstMTIwLjY3MDA0OCwgMzQuOTAyNTk1XSwgWy0xMjAuNjMxNzA5LCAzNS4wOTk3NjRdLCBbLTEyMC44OTQ2MDIsIDM1LjI0NzY0Ml0sIFstMTIwLjkwNTU1NiwgMzUuNDUwMjg5XSwgWy0xMjEuMDA0MTQxLCAzNS40NjEyNDNdLCBbLTEyMS4xNjg0NDksIDM1LjYzNjUwNV0sIFstMTIxLjI4MzQ2NSwgMzUuNjc0ODQzXSwgWy0xMjEuMzMyNzU3LCAzNS43ODQzODJdLCBbLTEyMS43MTYxNDMsIDM2LjE5NTE1M10sIFstMTIxLjg5Njg4MiwgMzYuMzE1NjQ1XSwgWy0xMjEuOTM1MjIxLCAzNi42Mzg3ODVdLCBbLTEyMS44NTg1NDQsIDM2LjYxMTRdLCBbLTEyMS43ODczNDQsIDM2LjgwMzA5M10sIFstMTIxLjkyOTc0NCwgMzYuOTc4MzU1XSwgWy0xMjIuMTA1MDA2LCAzNi45NTY0NDddLCBbLTEyMi4zMzUwMzgsIDM3LjExNTI3OV0sIFstMTIyLjQxNzE5MiwgMzcuMjQxMjQ4XSwgWy0xMjIuNDAwNzYxLCAzNy4zNjE3NDFdLCBbLTEyMi41MTU3NzcsIDM3LjUyMDU3Ml0sIFstMTIyLjUxNTc3NywgMzcuNzgzNDY1XSwgWy0xMjIuMzI5NTYxLCAzNy43ODM0NjVdLCBbLTEyMi40MDYyMzgsIDM4LjE1MDQyXSwgWy0xMjIuNDg4MzkyLCAzOC4xMTIwODJdLCBbLTEyMi41MDQ4MjMsIDM3LjkzMTM0M10sIFstMTIyLjcwMTk5MywgMzcuODkzMDA0XSwgWy0xMjIuOTM3NTAxLCAzOC4wMjk5MjhdLCBbLTEyMi45NzU4NCwgMzguMjY1NDM2XSwgWy0xMjMuMTI5MTk0LCAzOC40NTE2NTJdLCBbLTEyMy4zMzE4NDEsIDM4LjU2NjY2OF0sIFstMTIzLjQ0MTM4LCAzOC42OTgxMTRdLCBbLTEyMy43MzcxMzQsIDM4Ljk1NTUzXSwgWy0xMjMuNjg3ODQyLCAzOS4wMzIyMDhdLCBbLTEyMy44MjQ3NjUsIDM5LjM2NjMwMV0sIFstMTIzLjc2NDUxOSwgMzkuNTUyNTE3XSwgWy0xMjMuODUyMTUsIDM5LjgzMTg0MV0sIFstMTI0LjEwOTU2NiwgNDAuMTA1Njg4XSwgWy0xMjQuMzYxNTA2LCA0MC4yNTkwNDJdLCBbLTEyNC40MTA3OTgsIDQwLjQzOTc4MV0sIFstMTI0LjE1ODg1OSwgNDAuODc3OTM3XSwgWy0xMjQuMTA5NTY2LCA0MS4wMjU4MTRdLCBbLTEyNC4xNTg4NTksIDQxLjE0MDgzXSwgWy0xMjQuMDY1NzUxLCA0MS40NDIwNjFdLCBbLTEyNC4xNDc5MDUsIDQxLjcxNTkwOF0sIFstMTI0LjI1NzQ0NCwgNDEuNzgxNjMyXSwgWy0xMjQuMjEzNjI4LCA0Mi4wMDA3MDldLCBbLTEyMy4yMzMyNTYsIDQyLjAwNjE4Nl1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiQ0EiLCAicHJvcGVydGllcyI6IHsiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIkNhbGlmb3JuaWEiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICIjMDA2ODM3IiwgImZpbGxPcGFjaXR5IjogMC42LCAib3BhY2l0eSI6IDEsICJ3ZWlnaHQiOiAxfX0sICJ0eXBlIjogIkZlYXR1cmUifSwgeyJnZW9tZXRyeSI6IHsiY29vcmRpbmF0ZXMiOiBbW1stMTA3LjkxOTczMSwgNDEuMDAzOTA2XSwgWy0xMDUuNzI4OTU0LCA0MC45OTg0MjldLCBbLTEwNC4wNTMwMTEsIDQxLjAwMzkwNl0sIFstMTAyLjA1MzkyNywgNDEuMDAzOTA2XSwgWy0xMDIuMDUzOTI3LCA0MC4wMDE2MjZdLCBbLTEwMi4wNDI5NzQsIDM2Ljk5NDc4Nl0sIFstMTAzLjAwMTQzOCwgMzcuMDAwMjYzXSwgWy0xMDQuMzM3ODEyLCAzNi45OTQ3ODZdLCBbLTEwNi44NjgxNTgsIDM2Ljk5NDc4Nl0sIFstMTA3LjQyMTMyOSwgMzcuMDAwMjYzXSwgWy0xMDkuMDQyNTAzLCAzNy4wMDAyNjNdLCBbLTEwOS4wNDI1MDMsIDM4LjE2Njg1MV0sIFstMTA5LjA1ODkzNCwgMzguMjc2MzldLCBbLTEwOS4wNTM0NTcsIDM5LjEyNTMxNl0sIFstMTA5LjA0Nzk4LCA0MC45OTg0MjldLCBbLTEwNy45MTk3MzEsIDQxLjAwMzkwNl1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiQ08iLCAicHJvcGVydGllcyI6IHsiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIkNvbG9yYWRvIiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiIzc4YzY3OSIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbLTczLjA1MzUyOCwgNDIuMDM5MDQ4XSwgWy03MS43OTkzMDksIDQyLjAyMjYxN10sIFstNzEuNzk5MzA5LCA0Mi4wMDYxODZdLCBbLTcxLjc5OTMwOSwgNDEuNDE0Njc3XSwgWy03MS44NTk1NTUsIDQxLjMyMTU2OV0sIFstNzEuOTQ3MTg2LCA0MS4zMzhdLCBbLTcyLjM4NTM0MSwgNDEuMjYxMzIyXSwgWy03Mi45MDU2NTEsIDQxLjI4MzIzXSwgWy03My4xMzAyMDUsIDQxLjE0NjMwN10sIFstNzMuMzcxMTkxLCA0MS4xMDI0OTFdLCBbLTczLjY1NTk5MiwgNDAuOTg3NDc1XSwgWy03My43MjcxOTIsIDQxLjEwMjQ5MV0sIFstNzMuNDgwNzMsIDQxLjIxMjAzXSwgWy03My41NTE5MywgNDEuMjk0MTg0XSwgWy03My40ODYyMDYsIDQyLjA1MDAwMl0sIFstNzMuMDUzNTI4LCA0Mi4wMzkwNDhdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIkNUIiwgInByb3BlcnRpZXMiOiB7ImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJDb25uZWN0aWN1dCIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiMzMWEzNTQiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWy03NS40MTQwODksIDM5LjgwNDQ1Nl0sIFstNzUuNTA3MTk3LCAzOS42ODM5NjRdLCBbLTc1LjYxMTI1OSwgMzkuNjE4MjRdLCBbLTc1LjU4OTM1MiwgMzkuNDU5NDA5XSwgWy03NS40NDE0NzQsIDM5LjMxMTUzMl0sIFstNzUuNDAzMTM2LCAzOS4wNjUwNjldLCBbLTc1LjE4OTUzNSwgMzguODA3NjUzXSwgWy03NS4wOTA5NSwgMzguNzk2Njk5XSwgWy03NS4wNDcxMzQsIDM4LjQ1MTY1Ml0sIFstNzUuNjkzNDEzLCAzOC40NjI2MDZdLCBbLTc1Ljc4NjUyMSwgMzkuNzIyMzAyXSwgWy03NS42MTY3MzYsIDM5LjgzMTg0MV0sIFstNzUuNDE0MDg5LCAzOS44MDQ0NTZdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIkRFIiwgInByb3BlcnRpZXMiOiB7ImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJEZWxhd2FyZSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiM3OGM2NzkiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWy04NS40OTcxMzcsIDMwLjk5NzUzNl0sIFstODUuMDA0MjEyLCAzMS4wMDMwMTNdLCBbLTg0Ljg2NzI4OSwgMzAuNzEyNzM1XSwgWy04My40OTgwNTMsIDMwLjY0NzAxMl0sIFstODIuMjE2NDQ5LCAzMC41NzAzMzVdLCBbLTgyLjE2NzE1NywgMzAuMzU2NzM0XSwgWy04Mi4wNDY2NjQsIDMwLjM2MjIxMV0sIFstODIuMDAyODQ5LCAzMC41NjQ4NThdLCBbLTgyLjA0MTE4NywgMzAuNzUxMDc0XSwgWy04MS45NDgwNzksIDMwLjgyNzc1MV0sIFstODEuNzE4MDQ4LCAzMC43NDU1OTddLCBbLTgxLjQ0NDIwMSwgMzAuNzA3MjU4XSwgWy04MS4zODM5NTQsIDMwLjI3NDU4XSwgWy04MS4yNTc5ODUsIDI5Ljc4NzEzMl0sIFstODAuOTY3NzA3LCAyOS4xNDYzM10sIFstODAuNTI0MDc1LCAyOC40NjE3MTNdLCBbLTgwLjU4OTc5OCwgMjguNDEyNDJdLCBbLTgwLjU2Nzg5LCAyOC4wOTQ3NThdLCBbLTgwLjM4MTY3NCwgMjcuNzM4NzU3XSwgWy04MC4wOTEzOTcsIDI3LjAyMTI3N10sIFstODAuMDMxMTUsIDI2Ljc5NjcyM10sIFstODAuMDM2NjI3LCAyNi41NjY2OTFdLCBbLTgwLjE0NjE2NiwgMjUuNzM5NjczXSwgWy04MC4yMzkyNzQsIDI1LjcyMzI0M10sIFstODAuMzM3ODU5LCAyNS40NjU4MjZdLCBbLTgwLjMwNDk5NywgMjUuMzgzNjcyXSwgWy04MC40OTY2OSwgMjUuMTk3NDU2XSwgWy04MC41NzMzNjcsIDI1LjI0MTI3Ml0sIFstODAuNzU5NTgzLCAyNS4xNjQ1OTVdLCBbLTgxLjA3NzI0NiwgMjUuMTIwNzc5XSwgWy04MS4xNzAzNTQsIDI1LjIyNDg0MV0sIFstODEuMTI2NTM4LCAyNS4zNzgxOTVdLCBbLTgxLjM1MTA5MywgMjUuODIxODI3XSwgWy04MS41MjYzNTUsIDI1LjkwMzk4Ml0sIFstODEuNjc5NzA5LCAyNS44NDM3MzVdLCBbLTgxLjgwMDIwMiwgMjYuMDkwMTk4XSwgWy04MS44MzMwNjQsIDI2LjI5Mjg0NF0sIFstODIuMDQxMTg3LCAyNi41MTczOTldLCBbLTgyLjA5MDQ4LCAyNi42NjUyNzZdLCBbLTgyLjA1NzYxOCwgMjYuODc4ODc3XSwgWy04Mi4xNzI2MzQsIDI2LjkxNzIxNl0sIFstODIuMTQ1MjQ5LCAyNi43OTEyNDZdLCBbLTgyLjI0OTMxMSwgMjYuNzU4Mzg0XSwgWy04Mi41NjY5NzQsIDI3LjMwMDYwMV0sIFstODIuNjkyOTQzLCAyNy40Mzc1MjVdLCBbLTgyLjM5MTcxMSwgMjcuODM3MzQyXSwgWy04Mi41ODg4ODEsIDI3LjgxNTQzNF0sIFstODIuNzIwMzI4LCAyNy42ODk0NjRdLCBbLTgyLjg1MTc3NCwgMjcuODg2NjM0XSwgWy04Mi42NzY1MTIsIDI4LjQzNDMyOF0sIFstODIuNjQzNjUxLCAyOC44ODg5MTRdLCBbLTgyLjc2NDE0MywgMjguOTk4NDUzXSwgWy04Mi44MDI0ODIsIDI5LjE0NjMzXSwgWy04Mi45OTQxNzUsIDI5LjE3OTE5Ml0sIFstODMuMjE4NzI5LCAyOS40MjAxNzddLCBbLTgzLjM5OTQ2OSwgMjkuNTE4NzYyXSwgWy04My40MTA0MjIsIDI5LjY2NjY0XSwgWy04My41MzYzOTIsIDI5LjcyMTQwOV0sIFstODMuNjQwNDU0LCAyOS44ODU3MTddLCBbLTg0LjAyMzg0LCAzMC4xMDQ3OTVdLCBbLTg0LjM1NzkzMywgMzAuMDU1NTAyXSwgWy04NC4zNDE1MDIsIDI5LjkwMjE0OF0sIFstODQuNDUxMDQxLCAyOS45Mjk1MzNdLCBbLTg0Ljg2NzI4OSwgMjkuNzQzMzE3XSwgWy04NS4zMTA5MjEsIDI5LjY5OTUwMV0sIFstODUuMjk5OTY3LCAyOS44MDkwNF0sIFstODUuNDA0MDI5LCAyOS45NDA0ODddLCBbLTg1LjkyNDMzOCwgMzAuMjM2MjQxXSwgWy04Ni4yOTY3NywgMzAuMzYyMjExXSwgWy04Ni42MzA4NjMsIDMwLjM5NTA3M10sIFstODYuOTEwMTg3LCAzMC4zNzMxNjVdLCBbLTg3LjUxODEyOCwgMzAuMjgwMDU3XSwgWy04Ny4zNzAyNSwgMzAuNDI3OTM0XSwgWy04Ny40NDY5MjcsIDMwLjUxMDA4OF0sIFstODcuNDA4NTg5LCAzMC42NzQzOTddLCBbLTg3LjYzMzE0MywgMzAuODY2MDldLCBbLTg3LjYwMDI4MiwgMzAuOTk3NTM2XSwgWy04NS40OTcxMzcsIDMwLjk5NzUzNl1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiRkwiLCAicHJvcGVydGllcyI6IHsiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIkZsb3JpZGEiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICIjMzFhMzU0IiwgImZpbGxPcGFjaXR5IjogMC42LCAib3BhY2l0eSI6IDEsICJ3ZWlnaHQiOiAxfX0sICJ0eXBlIjogIkZlYXR1cmUifSwgeyJnZW9tZXRyeSI6IHsiY29vcmRpbmF0ZXMiOiBbW1stODMuMTA5MTkxLCAzNS4wMDExOF0sIFstODMuMzIyNzkxLCAzNC43ODc1NzldLCBbLTgzLjMzOTIyMiwgMzQuNjgzNTE3XSwgWy04My4wMDUxMjksIDM0LjQ2OTkxNl0sIFstODIuOTAxMDY3LCAzNC40ODYzNDddLCBbLTgyLjc0NzcxMywgMzQuMjY3MjddLCBbLTgyLjcxNDg1MSwgMzQuMTUyMjU0XSwgWy04Mi41NTYwMiwgMzMuOTQ0MTNdLCBbLTgyLjMyNTk4OCwgMzMuODE4MTZdLCBbLTgyLjE5NDU0MiwgMzMuNjMxOTQ0XSwgWy04MS45MjYxNzIsIDMzLjQ2MjE1OV0sIFstODEuOTM3MTI1LCAzMy4zNDcxNDRdLCBbLTgxLjc2MTg2MywgMzMuMTYwOTI4XSwgWy04MS40OTM0OTMsIDMzLjAwNzU3M10sIFstODEuNDI3NzcsIDMyLjg0MzI2NV0sIFstODEuNDE2ODE2LCAzMi42Mjk2NjRdLCBbLTgxLjI3OTg5MywgMzIuNTU4NDY0XSwgWy04MS4xMjEwNjEsIDMyLjI5MDA5NF0sIFstODEuMTE1NTg0LCAzMi4xMjAzMDldLCBbLTgwLjg4NTU1MywgMzIuMDMyNjc4XSwgWy04MS4xMzIwMTUsIDMxLjY5MzEwOF0sIFstODEuMTc1ODMxLCAzMS41MTc4NDVdLCBbLTgxLjI3OTg5MywgMzEuMzY0NDkxXSwgWy04MS4yOTA4NDYsIDMxLjIwNTY2XSwgWy04MS40MDAzODUsIDMxLjEzNDQ2XSwgWy04MS40NDQyMDEsIDMwLjcwNzI1OF0sIFstODEuNzE4MDQ4LCAzMC43NDU1OTddLCBbLTgxLjk0ODA3OSwgMzAuODI3NzUxXSwgWy04Mi4wNDExODcsIDMwLjc1MTA3NF0sIFstODIuMDAyODQ5LCAzMC41NjQ4NThdLCBbLTgyLjA0NjY2NCwgMzAuMzYyMjExXSwgWy04Mi4xNjcxNTcsIDMwLjM1NjczNF0sIFstODIuMjE2NDQ5LCAzMC41NzAzMzVdLCBbLTgzLjQ5ODA1MywgMzAuNjQ3MDEyXSwgWy04NC44NjcyODksIDMwLjcxMjczNV0sIFstODUuMDA0MjEyLCAzMS4wMDMwMTNdLCBbLTg1LjExMzc1MSwgMzEuMjc2ODZdLCBbLTg1LjA0MjU1MSwgMzEuNTM5NzUzXSwgWy04NS4xNDExMzYsIDMxLjg0MDk4NV0sIFstODUuMDUzNTA0LCAzMi4wMTA3N10sIFstODUuMDU4OTgxLCAzMi4xMzY3NF0sIFstODQuODg5MTk2LCAzMi4yNjI3MDldLCBbLTg1LjAwNDIxMiwgMzIuMzIyOTU2XSwgWy04NC45NjAzOTcsIDMyLjQyMTU0MV0sIFstODUuMDY5OTM1LCAzMi41ODAzNzJdLCBbLTg1LjE4NDk1MSwgMzIuODU5Njk2XSwgWy04NS40MzE0MTMsIDM0LjEyNDg2OV0sIFstODUuNjA2Njc1LCAzNC45ODQ3NDldLCBbLTg0LjMxOTU5NCwgMzQuOTkwMjI2XSwgWy04My42MTg1NDYsIDM0Ljk4NDc0OV0sIFstODMuMTA5MTkxLCAzNS4wMDExOF1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiR0EiLCAicHJvcGVydGllcyI6IHsiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIkdlb3JnaWEiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICIjMzFhMzU0IiwgImZpbGxPcGFjaXR5IjogMC42LCAib3BhY2l0eSI6IDEsICJ3ZWlnaHQiOiAxfX0sICJ0eXBlIjogIkZlYXR1cmUifSwgeyJnZW9tZXRyeSI6IHsiY29vcmRpbmF0ZXMiOiBbW1tbLTE1NS42MzQ4MzUsIDE4Ljk0ODI2N10sIFstMTU1Ljg4MTI5NywgMTkuMDM1ODk4XSwgWy0xNTUuOTE5NjM2LCAxOS4xMjM1MjldLCBbLTE1NS44ODY3NzQsIDE5LjM0ODA4NF0sIFstMTU2LjA2MjAzNiwgMTkuNzMxNDddLCBbLTE1NS45MjUxMTMsIDE5Ljg1NzQzOV0sIFstMTU1LjgyNjUyOCwgMjAuMDMyNzAyXSwgWy0xNTUuODk3NzI4LCAyMC4xNDc3MTddLCBbLTE1NS44NzU4MiwgMjAuMjY4MjFdLCBbLTE1NS41OTY0OTYsIDIwLjEyNTgxXSwgWy0xNTUuMjg0MzExLCAyMC4wMjE3NDhdLCBbLTE1NS4wOTI2MTgsIDE5Ljg2ODM5M10sIFstMTU1LjA5MjYxOCwgMTkuNzM2OTQ3XSwgWy0xNTQuODA3ODE3LCAxOS41MjMzNDZdLCBbLTE1NC45ODMwNzksIDE5LjM0ODA4NF0sIFstMTU1LjI5NTI2NSwgMTkuMjY1OTNdLCBbLTE1NS41MTQzNDIsIDE5LjEzNDQ4M10sIFstMTU1LjYzNDgzNSwgMTguOTQ4MjY3XV1dLCBbW1stMTU2LjU4NzgyMywgMjEuMDI5NTA1XSwgWy0xNTYuNDcyODA3LCAyMC44OTI1ODFdLCBbLTE1Ni4zMjQ5MjksIDIwLjk1MjgyN10sIFstMTU2LjAwMTc5LCAyMC43OTM5OTZdLCBbLTE1Ni4wNTEwODIsIDIwLjY1MTU5Nl0sIFstMTU2LjM3OTY5OSwgMjAuNTgwMzk2XSwgWy0xNTYuNDQ1NDIyLCAyMC42MDc3OF0sIFstMTU2LjQ2MTg1MywgMjAuNzgzMDQyXSwgWy0xNTYuNjMxNjM4LCAyMC44MjEzODFdLCBbLTE1Ni42OTczNjEsIDIwLjkxOTk2Nl0sIFstMTU2LjU4NzgyMywgMjEuMDI5NTA1XV1dLCBbW1stMTU2Ljk4MjE2MiwgMjEuMjEwMjQ0XSwgWy0xNTcuMDgwNzQ3LCAyMS4xMDYxODJdLCBbLTE1Ny4zMTA3NzksIDIxLjEwNjE4Ml0sIFstMTU3LjIzOTU3OSwgMjEuMjIxMTk4XSwgWy0xNTYuOTgyMTYyLCAyMS4yMTAyNDRdXV0sIFtbWy0xNTcuOTUxNTgxLCAyMS42OTc2OTFdLCBbLTE1Ny44NDIwNDIsIDIxLjQ2MjE4M10sIFstMTU3Ljg5NjgxMSwgMjEuMzI1MjU5XSwgWy0xNTguMTEwNDEyLCAyMS4zMDMzNTJdLCBbLTE1OC4yNTI4MTMsIDIxLjU4MjY3Nl0sIFstMTU4LjEyNjg0MywgMjEuNTg4MTUzXSwgWy0xNTcuOTUxNTgxLCAyMS42OTc2OTFdXV0sIFtbWy0xNTkuNDY4NjkzLCAyMi4yMjg5NTVdLCBbLTE1OS4zNTM2NzgsIDIyLjIxODAwMV0sIFstMTU5LjI5ODkwOCwgMjIuMTEzOTM5XSwgWy0xNTkuMzMxNzcsIDIxLjk2NjA2MV0sIFstMTU5LjQ0Njc4NiwgMjEuODcyOTUzXSwgWy0xNTkuNzY0NDQ4LCAyMS45ODc5NjldLCBbLTE1OS43MjYxMDksIDIyLjE1MjI3N10sIFstMTU5LjQ2ODY5MywgMjIuMjI4OTU1XV1dXSwgInR5cGUiOiAiTXVsdGlQb2x5Z29uIn0sICJpZCI6ICJISSIsICJwcm9wZXJ0aWVzIjogeyJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiSGF3YWlpIiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2Q5ZjBhMyIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbLTExNi4wNDc1MSwgNDkuMDAwMjM5XSwgWy0xMTYuMDQ3NTEsIDQ3Ljk3NjA1MV0sIFstMTE1LjcyNDM3MSwgNDcuNjk2NzI3XSwgWy0xMTUuNzE4ODk0LCA0Ny40MjI4OF0sIFstMTE1LjUyNzIwMSwgNDcuMzAyMzg4XSwgWy0xMTUuMzI0NTU0LCA0Ny4yNTg1NzJdLCBbLTExNS4zMDI2NDYsIDQ3LjE4NzM3Ml0sIFstMTE0LjkzMDIxNCwgNDYuOTE5MDAyXSwgWy0xMTQuODg2Mzk5LCA0Ni44MDk0NjNdLCBbLTExNC42MjM1MDYsIDQ2LjcwNTQwMV0sIFstMTE0LjYxMjU1MiwgNDYuNjM5Njc4XSwgWy0xMTQuMzIyMjc0LCA0Ni42NDUxNTVdLCBbLTExNC40NjQ2NzQsIDQ2LjI3MjcyM10sIFstMTE0LjQ5MjA1OSwgNDYuMDM3MjE0XSwgWy0xMTQuMzg3OTk3LCA0NS44ODM4Nl0sIFstMTE0LjU2ODczNiwgNDUuNzc0MzIxXSwgWy0xMTQuNDk3NTM2LCA0NS42NzAyNTldLCBbLTExNC41NDY4MjgsIDQ1LjU2MDcyMV0sIFstMTE0LjMzMzIyOCwgNDUuNDU2NjU5XSwgWy0xMTQuMDg2NzY1LCA0NS41OTM1ODJdLCBbLTExMy45ODgxOCwgNDUuNzAzMTIxXSwgWy0xMTMuODA3NDQxLCA0NS42MDQ1MzZdLCBbLTExMy44MzQ4MjYsIDQ1LjUyMjM4Ml0sIFstMTEzLjczNjI0MSwgNDUuMzMwNjg5XSwgWy0xMTMuNTcxOTMzLCA0NS4xMjgwNDJdLCBbLTExMy40NTE0NCwgNDUuMDU2ODQyXSwgWy0xMTMuNDU2OTE3LCA0NC44NjUxNDldLCBbLTExMy4zNDE5MDEsIDQ0Ljc4Mjk5NV0sIFstMTEzLjEzMzc3OCwgNDQuNzcyMDQxXSwgWy0xMTMuMDAyMzMxLCA0NC40NDg5MDJdLCBbLTExMi44ODczMTUsIDQ0LjM5NDEzMl0sIFstMTEyLjc4MzI1NCwgNDQuNDg3MjRdLCBbLTExMi40NzEwNjgsIDQ0LjQ4MTc2M10sIFstMTEyLjI0MTAzNiwgNDQuNTY5Mzk0XSwgWy0xMTIuMTA0MTEzLCA0NC41MjAxMDJdLCBbLTExMS44Njg2MDUsIDQ0LjU2MzkxN10sIFstMTExLjgxOTMxMiwgNDQuNTA5MTQ4XSwgWy0xMTEuNjE2NjY1LCA0NC41NDc0ODddLCBbLTExMS4zODY2MzQsIDQ0Ljc1NTYxXSwgWy0xMTEuMjI3ODAzLCA0NC41ODAzNDhdLCBbLTExMS4wNDcwNjMsIDQ0LjQ3NjI4Nl0sIFstMTExLjA0NzA2MywgNDIuMDAwNzA5XSwgWy0xMTIuMTY0MzU5LCA0MS45OTUyMzJdLCBbLTExNC4wNDI5NSwgNDEuOTk1MjMyXSwgWy0xMTcuMDI3ODgyLCA0Mi4wMDA3MDldLCBbLTExNy4wMjc4ODIsIDQzLjgzMDAwN10sIFstMTE2Ljg5NjQzNiwgNDQuMTU4NjI0XSwgWy0xMTYuOTc4NTksIDQ0LjI0MDc3OF0sIFstMTE3LjE3MDI4MywgNDQuMjU3MjA5XSwgWy0xMTcuMjQxNDgzLCA0NC4zOTQxMzJdLCBbLTExNy4wMzg4MzYsIDQ0Ljc1MDEzM10sIFstMTE2LjkzNDc3NCwgNDQuNzgyOTk1XSwgWy0xMTYuODMwNzEzLCA0NC45MzA4NzJdLCBbLTExNi44NDcxNDMsIDQ1LjAyMzk4XSwgWy0xMTYuNzMyMTI4LCA0NS4xNDQ0NzNdLCBbLTExNi42NzE4ODEsIDQ1LjMxOTczNV0sIFstMTE2LjQ2Mzc1OCwgNDUuNjE1NDldLCBbLTExNi41NDU5MTIsIDQ1Ljc1MjQxM10sIFstMTE2Ljc4MTQyLCA0NS44MjM2MTRdLCBbLTExNi45MTgzNDQsIDQ1Ljk5MzM5OV0sIFstMTE2LjkyMzgyLCA0Ni4xNjg2NjFdLCBbLTExNy4wNTUyNjcsIDQ2LjM0MzkyM10sIFstMTE3LjAzODgzNiwgNDYuNDI2MDc3XSwgWy0xMTcuMDQ0MzEzLCA0Ny43NjI0NTFdLCBbLTExNy4wMzMzNTksIDQ5LjAwMDIzOV0sIFstMTE2LjA0NzUxLCA0OS4wMDAyMzldXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIklEIiwgInByb3BlcnRpZXMiOiB7ImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJJZGFobyIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNhZGRkOGUiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWy05MC42Mzk5ODQsIDQyLjUxMDA2NV0sIFstODguNzg4Nzc4LCA0Mi40OTM2MzRdLCBbLTg3LjgwMjkyOSwgNDIuNDkzNjM0XSwgWy04Ny44MzU3OSwgNDIuMzAxOTQxXSwgWy04Ny42ODI0MzYsIDQyLjA3NzM4Nl0sIFstODcuNTIzNjA1LCA0MS43MTA0MzFdLCBbLTg3LjUyOTA4MiwgMzkuMzQ5ODddLCBbLTg3LjYzODYyLCAzOS4xNjkxMzFdLCBbLTg3LjUxMjY1MSwgMzguOTU1NTNdLCBbLTg3LjQ5NjIyLCAzOC43ODAyNjhdLCBbLTg3LjYyMjE5LCAzOC42Mzc4NjhdLCBbLTg3LjY1NTA1MSwgMzguNTA2NDIxXSwgWy04Ny44MzU3OSwgMzguMjkyODIxXSwgWy04Ny45NTA4MDYsIDM4LjI3NjM5XSwgWy04Ny45MjM0MjEsIDM4LjE1MDQyXSwgWy04OC4wMDAwOTgsIDM4LjEwMTEyOF0sIFstODguMDYwMzQ1LCAzNy44NjU2MTldLCBbLTg4LjAyNzQ4MywgMzcuNzk5ODk2XSwgWy04OC4xNTg5MywgMzcuNjU3NDk2XSwgWy04OC4wNjU4MjIsIDM3LjQ4MjIzNF0sIFstODguNDc2NTkyLCAzNy4zODkxMjZdLCBbLTg4LjUxNDkzMSwgMzcuMjg1MDY0XSwgWy04OC40MjE4MjMsIDM3LjE1MzYxN10sIFstODguNTQ3NzkyLCAzNy4wNzE0NjNdLCBbLTg4LjkxNDc0NywgMzcuMjI0ODE3XSwgWy04OS4wMjk3NjMsIDM3LjIxMzg2M10sIFstODkuMTgzMTE4LCAzNy4wMzg2MDFdLCBbLTg5LjEzMzgyNSwgMzYuOTgzODMyXSwgWy04OS4yOTI2NTYsIDM2Ljk5NDc4Nl0sIFstODkuNTE3MjExLCAzNy4yNzk1ODddLCBbLTg5LjQzNTA1NywgMzcuMzQ1MzFdLCBbLTg5LjUxNzIxMSwgMzcuNTM3MDAzXSwgWy04OS41MTcyMTEsIDM3LjY5MDM1N10sIFstODkuODQwMzUsIDM3LjkwMzk1OF0sIFstODkuOTQ5ODg5LCAzNy44ODIwNV0sIFstOTAuMDU5NDI4LCAzOC4wMTM0OTddLCBbLTkwLjM1NTE4MywgMzguMjE2MTQ0XSwgWy05MC4zNDk3MDYsIDM4LjM3NDk3NV0sIFstOTAuMTc5OTIxLCAzOC42MzIzOTFdLCBbLTkwLjIwNzMwNSwgMzguNzI1NDk5XSwgWy05MC4xMDg3MiwgMzguODQ1OTkyXSwgWy05MC4yNTExMjEsIDM4LjkxNzE5Ml0sIFstOTAuNDcwMTk5LCAzOC45NjEwMDddLCBbLTkwLjU4NTIxNCwgMzguODY3ODk5XSwgWy05MC42NjE4OTEsIDM4LjkyODE0Nl0sIFstOTAuNzI3NjE1LCAzOS4yNTY3NjJdLCBbLTkxLjA2MTcwOCwgMzkuNDcwMzYzXSwgWy05MS4zNjg0MTcsIDM5LjcyNzc3OV0sIFstOTEuNDk0Mzg2LCA0MC4wMzQ0ODhdLCBbLTkxLjUwNTM0LCA0MC4yMzcxMzVdLCBbLTkxLjQxNzcwOSwgNDAuMzc5NTM1XSwgWy05MS40MDEyNzgsIDQwLjU2MDI3NF0sIFstOTEuMTIxOTU0LCA0MC42Njk4MTNdLCBbLTkxLjA5NDU3LCA0MC44MjMxNjddLCBbLTkwLjk2MzEyMywgNDAuOTIxNzUyXSwgWy05MC45NDY2OTIsIDQxLjA5NzAxNF0sIFstOTEuMTExMDAxLCA0MS4yMzk0MTVdLCBbLTkxLjA0NTI3NywgNDEuNDE0Njc3XSwgWy05MC42NTY0MTQsIDQxLjQ2Mzk2OV0sIFstOTAuMzQ0MjI5LCA0MS41ODk5MzldLCBbLTkwLjMxMTM2NywgNDEuNzQzMjkzXSwgWy05MC4xNzk5MjEsIDQxLjgwOTAxNl0sIFstOTAuMTQxNTgyLCA0Mi4wMDA3MDldLCBbLTkwLjE2ODk2NywgNDIuMTI2Njc5XSwgWy05MC4zOTM1MjEsIDQyLjIyNTI2NF0sIFstOTAuNDIwOTA2LCA0Mi4zMjkzMjZdLCBbLTkwLjYzOTk4NCwgNDIuNTEwMDY1XV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJJTCIsICJwcm9wZXJ0aWVzIjogeyJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiSWxsaW5vaXMiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICIjMzFhMzU0IiwgImZpbGxPcGFjaXR5IjogMC42LCAib3BhY2l0eSI6IDEsICJ3ZWlnaHQiOiAxfX0sICJ0eXBlIjogIkZlYXR1cmUifSwgeyJnZW9tZXRyeSI6IHsiY29vcmRpbmF0ZXMiOiBbW1stODUuOTkwMDYxLCA0MS43NTk3MjRdLCBbLTg0LjgwNzA0MiwgNDEuNzU5NzI0XSwgWy04NC44MDcwNDIsIDQxLjY5NDAwMV0sIFstODQuODAxNTY1LCA0MC41MDAwMjhdLCBbLTg0LjgxNzk5NiwgMzkuMTAzNDA4XSwgWy04NC44OTQ2NzMsIDM5LjA1OTU5Ml0sIFstODQuODEyNTE5LCAzOC43ODU3NDVdLCBbLTg0Ljk4Nzc4MSwgMzguNzgwMjY4XSwgWy04NS4xNzM5OTcsIDM4LjY4NzE2XSwgWy04NS40MzE0MTMsIDM4LjczMDk3Nl0sIFstODUuNDIwNDYsIDM4LjUzMzgwNl0sIFstODUuNTkwMjQ1LCAzOC40NTE2NTJdLCBbLTg1LjY1NTk2OCwgMzguMzI1NjgyXSwgWy04NS44MzEyMywgMzguMjc2MzldLCBbLTg1LjkyNDMzOCwgMzguMDI0NDUxXSwgWy04Ni4wMzkzNTQsIDM3Ljk1ODcyN10sIFstODYuMjYzOTA4LCAzOC4wNTE4MzVdLCBbLTg2LjMwMjI0NywgMzguMTY2ODUxXSwgWy04Ni41MjEzMjUsIDM4LjA0MDg4MV0sIFstODYuNTA0ODk0LCAzNy45MzEzNDNdLCBbLTg2LjcyOTQ0OCwgMzcuODkzMDA0XSwgWy04Ni43OTUxNzIsIDM3Ljk5MTU4OV0sIFstODcuMDQ3MTExLCAzNy44OTMwMDRdLCBbLTg3LjEyOTI2NSwgMzcuNzg4OTQyXSwgWy04Ny4zODEyMDQsIDM3LjkzNjgyXSwgWy04Ny41MTI2NTEsIDM3LjkwMzk1OF0sIFstODcuNjAwMjgyLCAzNy45NzUxNThdLCBbLTg3LjY4MjQzNiwgMzcuOTAzOTU4XSwgWy04Ny45MzQzNzUsIDM3Ljg5MzAwNF0sIFstODguMDI3NDgzLCAzNy43OTk4OTZdLCBbLTg4LjA2MDM0NSwgMzcuODY1NjE5XSwgWy04OC4wMDAwOTgsIDM4LjEwMTEyOF0sIFstODcuOTIzNDIxLCAzOC4xNTA0Ml0sIFstODcuOTUwODA2LCAzOC4yNzYzOV0sIFstODcuODM1NzksIDM4LjI5MjgyMV0sIFstODcuNjU1MDUxLCAzOC41MDY0MjFdLCBbLTg3LjYyMjE5LCAzOC42Mzc4NjhdLCBbLTg3LjQ5NjIyLCAzOC43ODAyNjhdLCBbLTg3LjUxMjY1MSwgMzguOTU1NTNdLCBbLTg3LjYzODYyLCAzOS4xNjkxMzFdLCBbLTg3LjUyOTA4MiwgMzkuMzQ5ODddLCBbLTg3LjUyMzYwNSwgNDEuNzEwNDMxXSwgWy04Ny40MjUwMiwgNDEuNjQ0NzA4XSwgWy04Ny4xMTgzMTEsIDQxLjY0NDcwOF0sIFstODYuODIyNTU2LCA0MS43NTk3MjRdLCBbLTg1Ljk5MDA2MSwgNDEuNzU5NzI0XV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJJTiIsICJwcm9wZXJ0aWVzIjogeyJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiSW5kaWFuYSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiMzMWEzNTQiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWy05MS4zNjg0MTcsIDQzLjUwMTM5MV0sIFstOTEuMjE1MDYyLCA0My41MDEzOTFdLCBbLTkxLjIwNDEwOSwgNDMuMzUzNTE0XSwgWy05MS4wNTYyMzEsIDQzLjI1NDkyOV0sIFstOTEuMTc2NzI0LCA0My4xMzQ0MzZdLCBbLTkxLjE0Mzg2MiwgNDIuOTA5ODgxXSwgWy05MS4wNjcxODUsIDQyLjc1MTA1XSwgWy05MC43MTExODQsIDQyLjYzNjAzNF0sIFstOTAuNjM5OTg0LCA0Mi41MTAwNjVdLCBbLTkwLjQyMDkwNiwgNDIuMzI5MzI2XSwgWy05MC4zOTM1MjEsIDQyLjIyNTI2NF0sIFstOTAuMTY4OTY3LCA0Mi4xMjY2NzldLCBbLTkwLjE0MTU4MiwgNDIuMDAwNzA5XSwgWy05MC4xNzk5MjEsIDQxLjgwOTAxNl0sIFstOTAuMzExMzY3LCA0MS43NDMyOTNdLCBbLTkwLjM0NDIyOSwgNDEuNTg5OTM5XSwgWy05MC42NTY0MTQsIDQxLjQ2Mzk2OV0sIFstOTEuMDQ1Mjc3LCA0MS40MTQ2NzddLCBbLTkxLjExMTAwMSwgNDEuMjM5NDE1XSwgWy05MC45NDY2OTIsIDQxLjA5NzAxNF0sIFstOTAuOTYzMTIzLCA0MC45MjE3NTJdLCBbLTkxLjA5NDU3LCA0MC44MjMxNjddLCBbLTkxLjEyMTk1NCwgNDAuNjY5ODEzXSwgWy05MS40MDEyNzgsIDQwLjU2MDI3NF0sIFstOTEuNDE3NzA5LCA0MC4zNzk1MzVdLCBbLTkxLjUyNzI0OCwgNDAuNDEyMzk3XSwgWy05MS43Mjk4OTUsIDQwLjYxNTA0M10sIFstOTEuODMzOTU3LCA0MC42MDk1NjZdLCBbLTkzLjI1Nzk2MSwgNDAuNTgyMTgyXSwgWy05NC42MzI2NzMsIDQwLjU3MTIyOF0sIFstOTUuNzY2NCwgNDAuNTg3NjU5XSwgWy05NS44ODE0MTYsIDQwLjcxOTEwNV0sIFstOTUuODI2NjQ2LCA0MC45NzY1MjFdLCBbLTk1LjkyNTIzMSwgNDEuMjAxMDc2XSwgWy05NS45MTk3NTQsIDQxLjQ1MzAxNV0sIFstOTYuMDk1MDE2LCA0MS41NDA2NDZdLCBbLTk2LjEyMjQwMSwgNDEuNjc3NTddLCBbLTk2LjA2MjE1NSwgNDEuNzk4MDYzXSwgWy05Ni4xMjc4NzgsIDQxLjk3MzMyNV0sIFstOTYuMjY0ODAxLCA0Mi4wMzkwNDhdLCBbLTk2LjQ0NTU0LCA0Mi40ODgxNTddLCBbLTk2LjYzMTc1NiwgNDIuNzA3MjM1XSwgWy05Ni41NDQxMjUsIDQyLjg1NTExMl0sIFstOTYuNTExMjY0LCA0My4wNTIyODJdLCBbLTk2LjQzNDU4NywgNDMuMTIzNDgyXSwgWy05Ni41NjA1NTYsIDQzLjIyMjA2N10sIFstOTYuNTI3Njk1LCA0My4zOTczMjldLCBbLTk2LjU4MjQ2NCwgNDMuNDc5NDgzXSwgWy05Ni40NTEwMTcsIDQzLjUwMTM5MV0sIFstOTEuMzY4NDE3LCA0My41MDEzOTFdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIklBIiwgInByb3BlcnRpZXMiOiB7ImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJJb3dhIiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2Q5ZjBhMyIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbLTEwMS45MDYwNSwgNDAuMDAxNjI2XSwgWy05NS4zMDYzMzcsIDQwLjAwMTYyNl0sIFstOTUuMjA3NzUyLCAzOS45MDg1MThdLCBbLTk0Ljg4NDYxMiwgMzkuODMxODQxXSwgWy05NS4xMDkxNjcsIDM5LjU0MTU2M10sIFstOTQuOTgzMTk3LCAzOS40NDI5NzhdLCBbLTk0LjgyNDM2NiwgMzkuMjA3NDddLCBbLTk0LjYxMDc2NSwgMzkuMTU4MTc3XSwgWy05NC42MTYyNDIsIDM3LjAwMDI2M10sIFstMTAwLjA4NzcwNiwgMzcuMDAwMjYzXSwgWy0xMDIuMDQyOTc0LCAzNi45OTQ3ODZdLCBbLTEwMi4wNTM5MjcsIDQwLjAwMTYyNl0sIFstMTAxLjkwNjA1LCA0MC4wMDE2MjZdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIktTIiwgInByb3BlcnRpZXMiOiB7ImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJLYW5zYXMiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICIjYWRkZDhlIiwgImZpbGxPcGFjaXR5IjogMC42LCAib3BhY2l0eSI6IDEsICJ3ZWlnaHQiOiAxfX0sICJ0eXBlIjogIkZlYXR1cmUifSwgeyJnZW9tZXRyeSI6IHsiY29vcmRpbmF0ZXMiOiBbW1stODMuOTAzMzQ3LCAzOC43NjkzMTVdLCBbLTgzLjY3ODc5MiwgMzguNjMyMzkxXSwgWy04My41MTk5NjEsIDM4LjcwMzU5MV0sIFstODMuMTQyMDUyLCAzOC42MjY5MTRdLCBbLTgzLjAzMjUxNCwgMzguNzI1NDk5XSwgWy04Mi44OTAxMTMsIDM4Ljc1ODM2MV0sIFstODIuODQ2Mjk4LCAzOC41ODg1NzVdLCBbLTgyLjczMTI4MiwgMzguNTYxMTkxXSwgWy04Mi41OTQzNTgsIDM4LjQyNDI2N10sIFstODIuNjIxNzQzLCAzOC4xMjMwMzZdLCBbLTgyLjUwMTI1LCAzNy45MzEzNDNdLCBbLTgyLjM0MjQxOSwgMzcuNzgzNDY1XSwgWy04Mi4yOTMxMjcsIDM3LjY2ODQ0OV0sIFstODIuMTAxNDM0LCAzNy41NTM0MzRdLCBbLTgxLjk2OTk4NywgMzcuNTM3MDAzXSwgWy04Mi4zNTMzNzMsIDM3LjI2ODYzM10sIFstODIuNzIwMzI4LCAzNy4xMjA3NTVdLCBbLTgyLjcyMDMyOCwgMzcuMDQ0MDc4XSwgWy04Mi44NjgyMDUsIDM2Ljk3ODM1NV0sIFstODIuODc5MTU5LCAzNi44OTA3MjRdLCBbLTgzLjA3MDg1MiwgMzYuODUyMzg1XSwgWy04My4xMzY1NzUsIDM2Ljc0Mjg0N10sIFstODMuNjczMzE2LCAzNi42MDA0NDZdLCBbLTgzLjY4OTc0NiwgMzYuNTg0MDE1XSwgWy04NC41NDQxNDksIDM2LjU5NDk2OV0sIFstODUuMjg5MDEzLCAzNi42Mjc4MzFdLCBbLTg1LjQ4NjE4MywgMzYuNjE2ODc3XSwgWy04Ni41OTI1MjUsIDM2LjY1NTIxNl0sIFstODcuODUyMjIxLCAzNi42MzMzMDhdLCBbLTg4LjA3MTI5OSwgMzYuNjc3MTIzXSwgWy04OC4wNTQ4NjgsIDM2LjQ5NjM4NF0sIFstODkuMjk4MTMzLCAzNi41MDczMzhdLCBbLTg5LjQxODYyNiwgMzYuNDk2Mzg0XSwgWy04OS4zNjM4NTcsIDM2LjYyMjM1NF0sIFstODkuMjE1OTc5LCAzNi41Nzg1MzhdLCBbLTg5LjEzMzgyNSwgMzYuOTgzODMyXSwgWy04OS4xODMxMTgsIDM3LjAzODYwMV0sIFstODkuMDI5NzYzLCAzNy4yMTM4NjNdLCBbLTg4LjkxNDc0NywgMzcuMjI0ODE3XSwgWy04OC41NDc3OTIsIDM3LjA3MTQ2M10sIFstODguNDIxODIzLCAzNy4xNTM2MTddLCBbLTg4LjUxNDkzMSwgMzcuMjg1MDY0XSwgWy04OC40NzY1OTIsIDM3LjM4OTEyNl0sIFstODguMDY1ODIyLCAzNy40ODIyMzRdLCBbLTg4LjE1ODkzLCAzNy42NTc0OTZdLCBbLTg4LjAyNzQ4MywgMzcuNzk5ODk2XSwgWy04Ny45MzQzNzUsIDM3Ljg5MzAwNF0sIFstODcuNjgyNDM2LCAzNy45MDM5NThdLCBbLTg3LjYwMDI4MiwgMzcuOTc1MTU4XSwgWy04Ny41MTI2NTEsIDM3LjkwMzk1OF0sIFstODcuMzgxMjA0LCAzNy45MzY4Ml0sIFstODcuMTI5MjY1LCAzNy43ODg5NDJdLCBbLTg3LjA0NzExMSwgMzcuODkzMDA0XSwgWy04Ni43OTUxNzIsIDM3Ljk5MTU4OV0sIFstODYuNzI5NDQ4LCAzNy44OTMwMDRdLCBbLTg2LjUwNDg5NCwgMzcuOTMxMzQzXSwgWy04Ni41MjEzMjUsIDM4LjA0MDg4MV0sIFstODYuMzAyMjQ3LCAzOC4xNjY4NTFdLCBbLTg2LjI2MzkwOCwgMzguMDUxODM1XSwgWy04Ni4wMzkzNTQsIDM3Ljk1ODcyN10sIFstODUuOTI0MzM4LCAzOC4wMjQ0NTFdLCBbLTg1LjgzMTIzLCAzOC4yNzYzOV0sIFstODUuNjU1OTY4LCAzOC4zMjU2ODJdLCBbLTg1LjU5MDI0NSwgMzguNDUxNjUyXSwgWy04NS40MjA0NiwgMzguNTMzODA2XSwgWy04NS40MzE0MTMsIDM4LjczMDk3Nl0sIFstODUuMTczOTk3LCAzOC42ODcxNl0sIFstODQuOTg3NzgxLCAzOC43ODAyNjhdLCBbLTg0LjgxMjUxOSwgMzguNzg1NzQ1XSwgWy04NC44OTQ2NzMsIDM5LjA1OTU5Ml0sIFstODQuODE3OTk2LCAzOS4xMDM0MDhdLCBbLTg0LjQzNDYxLCAzOS4xMDM0MDhdLCBbLTg0LjIzMTk2MywgMzguODk1Mjg0XSwgWy04NC4yMTU1MzMsIDM4LjgwNzY1M10sIFstODMuOTAzMzQ3LCAzOC43NjkzMTVdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIktZIiwgInByb3BlcnRpZXMiOiB7ImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJLZW50dWNreSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiMzMWEzNTQiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWy05My42MDg0ODUsIDMzLjAxODUyN10sIFstOTEuMTY1NzcsIDMzLjAwMjA5Nl0sIFstOTEuMDcyNjYyLCAzMi44ODcwODFdLCBbLTkxLjE0Mzg2MiwgMzIuODQzMjY1XSwgWy05MS4xNTQ4MTYsIDMyLjY0MDYxOF0sIFstOTEuMDA2OTM5LCAzMi41MTQ2NDldLCBbLTkwLjk4NTAzMSwgMzIuMjE4ODk0XSwgWy05MS4xMDU1MjQsIDMxLjk4ODg2Ml0sIFstOTEuMzQxMDMyLCAzMS44NDY0NjJdLCBbLTkxLjQwMTI3OCwgMzEuNjIxOTA3XSwgWy05MS40OTk4NjMsIDMxLjY0MzgxNV0sIFstOTEuNTE2Mjk0LCAzMS4yNzY4Nl0sIFstOTEuNjM2Nzg3LCAzMS4yNjU5MDZdLCBbLTkxLjU2NTU4NywgMzEuMDY4NzM2XSwgWy05MS42MzY3ODcsIDMwLjk5NzUzNl0sIFstODkuNzQ3MjQyLCAzMC45OTc1MzZdLCBbLTg5Ljg0NTgyNywgMzAuNjY4OTJdLCBbLTg5LjY4MTUxOSwgMzAuNDQ5ODQyXSwgWy04OS42NDMxODEsIDMwLjI4NTUzNF0sIFstODkuNTIyNjg4LCAzMC4xODE0NzJdLCBbLTg5LjgxODQ0MywgMzAuMDQ0NTQ5XSwgWy04OS44NDAzNSwgMjkuOTQ1OTY0XSwgWy04OS41OTkzNjUsIDI5Ljg4MDI0XSwgWy04OS40OTUzMDMsIDMwLjAzOTA3Ml0sIFstODkuMjg3MTc5LCAyOS44ODAyNF0sIFstODkuMzAzNjEsIDI5Ljc1NDI3MV0sIFstODkuNDI0MTAzLCAyOS42OTk1MDFdLCBbLTg5LjY0ODY1NywgMjkuNzQ4Nzk0XSwgWy04OS42MjEyNzMsIDI5LjY1NTY4Nl0sIFstODkuNjk3OTUsIDI5LjUxMzI4NV0sIFstODkuNTA2MjU3LCAyOS4zODczMTZdLCBbLTg5LjE5OTU0OCwgMjkuMzQ4OTc3XSwgWy04OS4wOTAwMSwgMjkuMjAxMV0sIFstODkuMDAyMzc5LCAyOS4xNzkxOTJdLCBbLTg5LjE2MTIxLCAyOS4wMDk0MDddLCBbLTg5LjMzNjQ3MiwgMjkuMDQyMjY4XSwgWy04OS40ODQzNDksIDI5LjIxNzUzMV0sIFstODkuODUxMzA0LCAyOS4zMTA2MzhdLCBbLTg5Ljg1MTMwNCwgMjkuNDgwNDI0XSwgWy05MC4wMzIwNDMsIDI5LjQyNTY1NF0sIFstOTAuMDIxMDg5LCAyOS4yODMyNTRdLCBbLTkwLjEwMzI0NCwgMjkuMTUxODA3XSwgWy05MC4yMzQ2OSwgMjkuMTI5ODk5XSwgWy05MC4zMzMyNzUsIDI5LjI3Nzc3N10sIFstOTAuNTYzMzA3LCAyOS4yODMyNTRdLCBbLTkwLjY0NTQ2MSwgMjkuMTI5ODk5XSwgWy05MC43OTg4MTUsIDI5LjA4NjA4NF0sIFstOTAuOTYzMTIzLCAyOS4xNzkxOTJdLCBbLTkxLjA5NDU3LCAyOS4xOTAxNDZdLCBbLTkxLjIyMDUzOSwgMjkuNDM2NjA4XSwgWy05MS40NDUwOTQsIDI5LjU0NjE0N10sIFstOTEuNTMyNzI1LCAyOS41Mjk3MTZdLCBbLTkxLjYyMDM1NiwgMjkuNzM3ODRdLCBbLTkxLjg4MzI0OSwgMjkuNzEwNDU1XSwgWy05MS44ODg3MjYsIDI5LjgzNjQyNV0sIFstOTIuMTQ2MTQyLCAyOS43MTU5MzJdLCBbLTkyLjExMzI4MSwgMjkuNjIyODI0XSwgWy05Mi4zMTA0NSwgMjkuNTM1MTkzXSwgWy05Mi42MTcxNTksIDI5LjU3OTAwOV0sIFstOTIuOTczMTYsIDI5LjcxNTkzMl0sIFstOTMuMjI1MSwgMjkuNzc2MTc4XSwgWy05My43NjczMTcsIDI5LjcyNjg4Nl0sIFstOTMuODM4NTE3LCAyOS42ODg1NDddLCBbLTkzLjkyNjE0OCwgMjkuNzg3MTMyXSwgWy05My42OTA2MzksIDMwLjE0MzEzM10sIFstOTMuNzY3MzE3LCAzMC4zMzQ4MjZdLCBbLTkzLjY5NjExNiwgMzAuNDM4ODg4XSwgWy05My43Mjg5NzgsIDMwLjU3NTgxMl0sIFstOTMuNjMwMzkzLCAzMC42Nzk4NzRdLCBbLTkzLjUyNjMzMSwgMzAuOTM3MjldLCBbLTkzLjU0Mjc2MiwgMzEuMTUwODldLCBbLTkzLjgxNjYwOSwgMzEuNTU2MTg0XSwgWy05My44MjIwODYsIDMxLjc3NTI2Ml0sIFstOTQuMDQxMTY0LCAzMS45OTQzMzldLCBbLTk0LjA0MTE2NCwgMzMuMDE4NTI3XSwgWy05My42MDg0ODUsIDMzLjAxODUyN11dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiTEEiLCAicHJvcGVydGllcyI6IHsiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIkxvdWlzaWFuYSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNhZGRkOGUiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWy03MC43MDM5MjEsIDQzLjA1Nzc1OV0sIFstNzAuODI0NDEzLCA0My4xMjg5NTldLCBbLTcwLjgwNzk4MywgNDMuMjI3NTQ0XSwgWy03MC45NjY4MTQsIDQzLjM0MjU2XSwgWy03MS4wMzI1MzcsIDQ0LjY1NzAyNV0sIFstNzEuMDgxODMsIDQ1LjMwMzMwNF0sIFstNzAuNjQ5MTUxLCA0NS40NDAyMjhdLCBbLTcwLjcyMDM1MiwgNDUuNTExNDI4XSwgWy03MC41NTYwNDMsIDQ1LjY2NDc4Ml0sIFstNzAuMzg2MjU4LCA0NS43MzU5ODNdLCBbLTcwLjQxOTEyLCA0NS43OTYyMjldLCBbLTcwLjI2MDI4OSwgNDUuODg5MzM3XSwgWy03MC4zMDk1ODEsIDQ2LjA2NDU5OV0sIFstNzAuMjEwOTk2LCA0Ni4zMjc0OTJdLCBbLTcwLjA1NzY0MiwgNDYuNDE1MTIzXSwgWy02OS45OTczOTUsIDQ2LjY5NDQ0N10sIFstNjkuMjI1MTQ3LCA0Ny40NjEyMTldLCBbLTY5LjA0NDQwOCwgNDcuNDI4MzU3XSwgWy02OS4wMzM0NTQsIDQ3LjI0MjE0MV0sIFstNjguOTAyMDA3LCA0Ny4xNzY0MThdLCBbLTY4LjU3ODg2OCwgNDcuMjg1OTU3XSwgWy02OC4zNzYyMjEsIDQ3LjI4NTk1N10sIFstNjguMjMzODIxLCA0Ny4zNTcxNTddLCBbLTY3Ljk1NDQ5NywgNDcuMTk4MzI2XSwgWy02Ny43OTAxODgsIDQ3LjA2Njg3OV0sIFstNjcuNzc5MjM1LCA0NS45NDQxMDZdLCBbLTY3LjgwMTE0MiwgNDUuNjc1NzM2XSwgWy02Ny40NTYwOTUsIDQ1LjYwNDUzNl0sIFstNjcuNTA1Mzg4LCA0NS40ODk1Ml0sIFstNjcuNDE3NzU3LCA0NS4zNzk5ODJdLCBbLTY3LjQ4ODk1NywgNDUuMjgxMzk3XSwgWy02Ny4zNDY1NTYsIDQ1LjEyODA0Ml0sIFstNjcuMTYwMzQsIDQ1LjE2MDkwNF0sIFstNjYuOTc5NjAxLCA0NC44MDQ5MDNdLCBbLTY3LjE4NzcyNSwgNDQuNjQ2MDcyXSwgWy02Ny4zMDgyMTgsIDQ0LjcwNjMxOF0sIFstNjcuNDA2ODAzLCA0NC41OTY3NzldLCBbLTY3LjU0OTIwMywgNDQuNjI0MTY0XSwgWy02Ny41NjU2MzQsIDQ0LjUzMTA1Nl0sIFstNjcuNzUxODUsIDQ0LjU0MjAxXSwgWy02OC4wNDc2MDUsIDQ0LjMyODQwOV0sIFstNjguMTE4ODA1LCA0NC40NzYyODZdLCBbLTY4LjIyMjg2NywgNDQuNDg3MjRdLCBbLTY4LjE3MzU3NCwgNDQuMzI4NDA5XSwgWy02OC40MDM2MDYsIDQ0LjI1MTczMl0sIFstNjguNDU4Mzc1LCA0NC4zNzc3MDFdLCBbLTY4LjU2NzkxNCwgNDQuMzExOTc4XSwgWy02OC44MjUzMywgNDQuMzExOTc4XSwgWy02OC44MzA4MDcsIDQ0LjQ1OTg1Nl0sIFstNjguOTg0MTYxLCA0NC40MjY5OTRdLCBbLTY4Ljk1Njc3NywgNDQuMzIyOTMyXSwgWy02OS4wOTkxNzcsIDQ0LjEwMzg1NF0sIFstNjkuMDcxNzkzLCA0NC4wNDM2MDhdLCBbLTY5LjI1ODAwOCwgNDMuOTIzMTE1XSwgWy02OS40NDQyMjQsIDQzLjk2NjkzMV0sIFstNjkuNTUzNzYzLCA0My44NDA5NjFdLCBbLTY5LjcwNzExOCwgNDMuODI0NTNdLCBbLTY5LjgzMzA4NywgNDMuNzIwNDY5XSwgWy02OS45ODY0NDIsIDQzLjc0MjM3Nl0sIFstNzAuMDMwMjU3LCA0My44NTE5MTVdLCBbLTcwLjI1NDgxMiwgNDMuNjc2NjUzXSwgWy03MC4xOTQ1NjUsIDQzLjU2NzExNF0sIFstNzAuMzU4ODczLCA0My41Mjg3NzZdLCBbLTcwLjM2OTgyNywgNDMuNDM1NjY4XSwgWy03MC41NTYwNDMsIDQzLjMyMDY1Ml0sIFstNzAuNzAzOTIxLCA0My4wNTc3NTldXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIk1FIiwgInByb3BlcnRpZXMiOiB7ImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJNYWluZSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiM3OGM2NzkiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbW1stNzUuOTk0NjQ1LCAzNy45NTMyNV0sIFstNzYuMDE2NTUzLCAzNy45NTMyNV0sIFstNzYuMDQzOTM4LCAzNy45NTMyNV0sIFstNzUuOTk0NjQ1LCAzNy45NTMyNV1dXSwgW1tbLTc5LjQ3Nzk3OSwgMzkuNzIyMzAyXSwgWy03NS43ODY1MjEsIDM5LjcyMjMwMl0sIFstNzUuNjkzNDEzLCAzOC40NjI2MDZdLCBbLTc1LjA0NzEzNCwgMzguNDUxNjUyXSwgWy03NS4yNDQzMDQsIDM4LjAyOTkyOF0sIFstNzUuMzk3NjU5LCAzOC4wMTM0OTddLCBbLTc1LjY3MTUwNiwgMzcuOTUzMjVdLCBbLTc1Ljg4NTEwNiwgMzcuOTA5NDM1XSwgWy03NS44Nzk2MjksIDM4LjA3Mzc0M10sIFstNzUuOTYxNzgzLCAzOC4xMzk0NjZdLCBbLTc1Ljg0Njc2OCwgMzguMjEwNjY3XSwgWy03Ni4wMDAxMjIsIDM4LjM3NDk3NV0sIFstNzYuMDQ5NDE1LCAzOC4zMDM3NzVdLCBbLTc2LjI1NzUzOCwgMzguMzIwMjA1XSwgWy03Ni4zMjg3MzgsIDM4LjUwMDk0NF0sIFstNzYuMjYzMDE1LCAzOC41MDA5NDRdLCBbLTc2LjI1NzUzOCwgMzguNzM2NDUzXSwgWy03Ni4xOTE4MTUsIDM4LjgyOTU2MV0sIFstNzYuMjc5NDQ2LCAzOS4xNDcyMjNdLCBbLTc2LjE2OTkwNywgMzkuMzMzNDM5XSwgWy03Ni4wMDAxMjIsIDM5LjM2NjMwMV0sIFstNzUuOTcyNzM3LCAzOS41NTc5OTRdLCBbLTc2LjA5ODcwNywgMzkuNTM2MDg2XSwgWy03Ni4xMDQxODQsIDM5LjQzNzUwMV0sIFstNzYuMzY3MDc3LCAzOS4zMTE1MzJdLCBbLTc2LjQ0Mzc1NCwgMzkuMTk2NTE2XSwgWy03Ni40NjAxODUsIDM4LjkwNjIzOF0sIFstNzYuNTU4NzcsIDM4Ljc2OTMxNV0sIFstNzYuNTE0OTU0LCAzOC41MzkyODNdLCBbLTc2LjM4MzUwOCwgMzguMzgwNDUyXSwgWy03Ni4zOTk5MzksIDM4LjI1OTk1OV0sIFstNzYuMzE3Nzg1LCAzOC4xMzk0NjZdLCBbLTc2LjM2MTYsIDM4LjA1NzMxMl0sIFstNzYuNTkxNjMyLCAzOC4yMTYxNDRdLCBbLTc2LjkyMDI0OCwgMzguMjkyODIxXSwgWy03Ny4wMTg4MzMsIDM4LjQ0NjE3NV0sIFstNzcuMjA1MDQ5LCAzOC4zNTg1NDRdLCBbLTc3LjI3NjI0OSwgMzguNDc5MDM3XSwgWy03Ny4xMjgzNzIsIDM4LjYzMjM5MV0sIFstNzcuMDQwNzQxLCAzOC43OTEyMjJdLCBbLTc2LjkwOTI5NCwgMzguODk1Mjg0XSwgWy03Ny4wMzUyNjQsIDM4Ljk5Mzg2OV0sIFstNzcuMTE3NDE4LCAzOC45MzM2MjNdLCBbLTc3LjI0ODg2NCwgMzkuMDI2NzMxXSwgWy03Ny40NTY5ODgsIDM5LjA3NjAyM10sIFstNzcuNDU2OTg4LCAzOS4yMjM5MDFdLCBbLTc3LjU2NjUyNywgMzkuMzA2MDU1XSwgWy03Ny43MTk4ODEsIDM5LjMyMjQ4NV0sIFstNzcuODM0ODk3LCAzOS42MDE4MDldLCBbLTc4LjAwNDY4MiwgMzkuNjAxODA5XSwgWy03OC4xNzQ0NjcsIDM5LjY5NDkxN10sIFstNzguMjY3NTc1LCAzOS42MTgyNF0sIFstNzguNDMxODg0LCAzOS42MjM3MTddLCBbLTc4LjQ3MDIyMiwgMzkuNTE0MTc4XSwgWy03OC43NjU5NzcsIDM5LjU4NTM3OV0sIFstNzguOTYzMTQ3LCAzOS40Mzc1MDFdLCBbLTc5LjA5NDU5MywgMzkuNDcwMzYzXSwgWy03OS4yOTE3NjMsIDM5LjMwMDU3OF0sIFstNzkuNDg4OTMzLCAzOS4yMDc0N10sIFstNzkuNDc3OTc5LCAzOS43MjIzMDJdXV1dLCAidHlwZSI6ICJNdWx0aVBvbHlnb24ifSwgImlkIjogIk1EIiwgInByb3BlcnRpZXMiOiB7ImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJNYXJ5bGFuZCIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiM3OGM2NzkiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWy03MC45MTc1MjEsIDQyLjg4Nzk3NF0sIFstNzAuODE4OTM2LCA0Mi44NzE1NDNdLCBbLTcwLjc4MDU5OCwgNDIuNjk2MjgxXSwgWy03MC44MjQ0MTMsIDQyLjU1Mzg4XSwgWy03MC45ODMyNDUsIDQyLjQyMjQzNF0sIFstNzAuOTg4NzIyLCA0Mi4yNjkwNzldLCBbLTcwLjc2OTY0NCwgNDIuMjQ3MTcyXSwgWy03MC42MzgxOTcsIDQyLjA4ODM0XSwgWy03MC42NjAxMDUsIDQxLjk2MjM3MV0sIFstNzAuNTUwNTY2LCA0MS45Mjk1MDldLCBbLTcwLjUzOTYxMywgNDEuODE0NDkzXSwgWy03MC4yNjAyODksIDQxLjcxNTkwOF0sIFstNjkuOTM3MTQ5LCA0MS44MDkwMTZdLCBbLTcwLjAwODM0OSwgNDEuNjcyMDkzXSwgWy03MC40ODQ4NDMsIDQxLjU1MTZdLCBbLTcwLjY2MDEwNSwgNDEuNTQ2MTIzXSwgWy03MC43NjQxNjcsIDQxLjYzOTIzMV0sIFstNzAuOTI4NDc1LCA0MS42MTE4NDddLCBbLTcwLjkzMzk1MiwgNDEuNTQwNjQ2XSwgWy03MS4xMjAxNjgsIDQxLjQ5NjgzMV0sIFstNzEuMTk2ODQ1LCA0MS42Nzc1N10sIFstNzEuMjI0MjMsIDQxLjcxMDQzMV0sIFstNzEuMzI4MjkyLCA0MS43ODE2MzJdLCBbLTcxLjM4MzA2MSwgNDIuMDE3MTRdLCBbLTcxLjUzMDkzOSwgNDIuMDE3MTRdLCBbLTcxLjc5OTMwOSwgNDIuMDA2MTg2XSwgWy03MS43OTkzMDksIDQyLjAyMjYxN10sIFstNzMuMDUzNTI4LCA0Mi4wMzkwNDhdLCBbLTczLjQ4NjIwNiwgNDIuMDUwMDAyXSwgWy03My41MDgxMTQsIDQyLjA4ODM0XSwgWy03My4yNjcxMjksIDQyLjc0NTU3M10sIFstNzIuNDU2NTQyLCA0Mi43MjkxNDJdLCBbLTcxLjI5NTQzLCA0Mi42OTYyODFdLCBbLTcxLjE4NTg5MSwgNDIuNzg5Mzg5XSwgWy03MC45MTc1MjEsIDQyLjg4Nzk3NF1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiTUEiLCAicHJvcGVydGllcyI6IHsiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIk1hc3NhY2h1c2V0dHMiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICIjYWRkZDhlIiwgImZpbGxPcGFjaXR5IjogMC42LCAib3BhY2l0eSI6IDEsICJ3ZWlnaHQiOiAxfX0sICJ0eXBlIjogIkZlYXR1cmUifSwgeyJnZW9tZXRyeSI6IHsiY29vcmRpbmF0ZXMiOiBbW1tbLTgzLjQ1NDIzOCwgNDEuNzMyMzM5XSwgWy04NC44MDcwNDIsIDQxLjY5NDAwMV0sIFstODQuODA3MDQyLCA0MS43NTk3MjRdLCBbLTg1Ljk5MDA2MSwgNDEuNzU5NzI0XSwgWy04Ni44MjI1NTYsIDQxLjc1OTcyNF0sIFstODYuNjE5OTA5LCA0MS44OTExNzFdLCBbLTg2LjQ4Mjk4NiwgNDIuMTE1NzI1XSwgWy04Ni4zNTcwMTYsIDQyLjI1MjY0OV0sIFstODYuMjYzOTA4LCA0Mi40NDQzNDFdLCBbLTg2LjIwOTEzOSwgNDIuNzE4MTg5XSwgWy04Ni4yMzEwNDcsIDQzLjAxMzk0M10sIFstODYuNTI2ODAxLCA0My41OTQ0OTldLCBbLTg2LjQzMzY5MywgNDMuODEzNTc3XSwgWy04Ni40OTk0MTcsIDQ0LjA3NjQ3XSwgWy04Ni4yNjkzODUsIDQ0LjM0NDg0XSwgWy04Ni4yMjAwOTMsIDQ0LjU2OTM5NF0sIFstODYuMjUyOTU0LCA0NC42ODk4ODddLCBbLTg2LjA4ODY0NiwgNDQuNzM5MThdLCBbLTg2LjA2NjczOCwgNDQuOTAzNDg4XSwgWy04NS44MDkzMjIsIDQ0Ljk0NzMwM10sIFstODUuNjEyMTUyLCA0NS4xMjgwNDJdLCBbLTg1LjYyODU4MywgNDQuNzY2NTY0XSwgWy04NS41MjQ1MjEsIDQ0Ljc1MDEzM10sIFstODUuMzkzMDc1LCA0NC45MzA4NzJdLCBbLTg1LjM4NzU5OCwgNDUuMjM3NTgxXSwgWy04NS4zMDU0NDQsIDQ1LjMxNDI1OF0sIFstODUuMDMxNTk3LCA0NS4zNjM1NTFdLCBbLTg1LjExOTIyOCwgNDUuNTc3MTUxXSwgWy04NC45Mzg0ODksIDQ1Ljc1Nzg5XSwgWy04NC43MTM5MzQsIDQ1Ljc2ODg0NF0sIFstODQuNDYxOTk1LCA0NS42NTM4MjldLCBbLTg0LjIxNTUzMywgNDUuNjM3Mzk4XSwgWy04NC4wOTUwNCwgNDUuNDk0OTk3XSwgWy04My45MDg4MjQsIDQ1LjQ4NDA0M10sIFstODMuNTk2NjM4LCA0NS4zNTI1OTddLCBbLTgzLjQ4NzEsIDQ1LjM1ODA3NF0sIFstODMuMzE3MzE0LCA0NS4xNDQ0NzNdLCBbLTgzLjQ1NDIzOCwgNDUuMDI5NDU3XSwgWy04My4zMjI3OTEsIDQ0Ljg4MTU4XSwgWy04My4yNzM0OTksIDQ0LjcxMTc5NV0sIFstODMuMzMzNzQ1LCA0NC4zMzkzNjNdLCBbLTgzLjUzNjM5MiwgNDQuMjQ2MjU1XSwgWy04My41ODU2ODQsIDQ0LjA1NDU2Ml0sIFstODMuODI2NjcsIDQzLjk4ODgzOV0sIFstODMuOTU4MTE2LCA0My43NTg4MDddLCBbLTgzLjkwODgyNCwgNDMuNjcxMTc2XSwgWy04My42Njc4MzksIDQzLjU4OTAyMl0sIFstODMuNDgxNjIzLCA0My43MTQ5OTJdLCBbLTgzLjI2MjU0NSwgNDMuOTcyNDA4XSwgWy04Mi45MTc0OTgsIDQ0LjA3MDk5M10sIFstODIuNzQ3NzEzLCA0My45OTQzMTZdLCBbLTgyLjY0MzY1MSwgNDMuODUxOTE1XSwgWy04Mi41Mzk1ODksIDQzLjQzNTY2OF0sIFstODIuNTIzMTU4LCA0My4yMjc1NDRdLCBbLTgyLjQxMzYxOSwgNDIuOTc1NjA1XSwgWy04Mi41MTc2ODEsIDQyLjYxNDEyN10sIFstODIuNjgxOTg5LCA0Mi41NTkzNTddLCBbLTgyLjY4NzQ2NiwgNDIuNjkwODA0XSwgWy04Mi43OTcwMDUsIDQyLjY1MjQ2NV0sIFstODIuOTIyOTc1LCA0Mi4zNTEyMzRdLCBbLTgzLjEyNTYyMSwgNDIuMjM2MjE4XSwgWy04My4xODU4NjgsIDQyLjAwNjE4Nl0sIFstODMuNDM3ODA3LCA0MS44MTQ0OTNdLCBbLTgzLjQ1NDIzOCwgNDEuNzMyMzM5XV1dLCBbW1stODUuNTA4MDkxLCA0NS43MzA1MDZdLCBbLTg1LjQ5MTY2LCA0NS42MTAwMTNdLCBbLTg1LjYyMzEwNiwgNDUuNTg4MTA1XSwgWy04NS41NjgzMzcsIDQ1Ljc1Nzg5XSwgWy04NS41MDgwOTEsIDQ1LjczMDUwNl1dXSwgW1tbLTg3LjU4OTMyOCwgNDUuMDk1MTgxXSwgWy04Ny43NDI2ODIsIDQ1LjE5OTI0M10sIFstODcuNjQ5NTc0LCA0NS4zNDE2NDNdLCBbLTg3Ljg4NTA4MywgNDUuMzYzNTUxXSwgWy04Ny43OTE5NzUsIDQ1LjUwMDQ3NF0sIFstODcuNzgxMDIxLCA0NS42NzU3MzZdLCBbLTg3Ljk4OTE0NSwgNDUuNzk2MjI5XSwgWy04OC4xMDQxNiwgNDUuOTIyMTk5XSwgWy04OC41MzEzNjIsIDQ2LjAyMDc4NF0sIFstODguNjYyODA4LCA0NS45ODc5MjJdLCBbLTg5LjA5MDAxLCA0Ni4xMzU3OTldLCBbLTkwLjExOTY3NCwgNDYuMzM4NDQ2XSwgWy05MC4yMjkyMTMsIDQ2LjUwODIzMV0sIFstOTAuNDE1NDI5LCA0Ni41Njg0NzhdLCBbLTkwLjAyNjU2NiwgNDYuNjcyNTM5XSwgWy04OS44NTEzMDQsIDQ2Ljc5MzAzMl0sIFstODkuNDEzMTQ5LCA0Ni44NDIzMjVdLCBbLTg5LjEyODM0OCwgNDYuOTkwMjAyXSwgWy04OC45OTY5MDIsIDQ2Ljk5NTY3OV0sIFstODguODg3MzYzLCA0Ny4wOTk3NDFdLCBbLTg4LjU3NTE3NywgNDcuMjQ3NjE4XSwgWy04OC40MTYzNDYsIDQ3LjM3MzU4OF0sIFstODguMTgwODM3LCA0Ny40NTU3NDJdLCBbLTg3Ljk1NjI4MywgNDcuMzg0NTQyXSwgWy04OC4zNTA2MjMsIDQ3LjA3NzgzM10sIFstODguNDQzNzMxLCA0Ni45NzM3NzFdLCBbLTg4LjQzODI1NCwgNDYuNzg3NTU1XSwgWy04OC4yNDY1NjEsIDQ2LjkyOTk1Nl0sIFstODcuOTAxNTEzLCA0Ni45MDgwNDhdLCBbLTg3LjYzMzE0MywgNDYuODA5NDYzXSwgWy04Ny4zOTIxNTgsIDQ2LjUzNTYxNl0sIFstODcuMjYwNzExLCA0Ni40ODYzMjNdLCBbLTg3LjAwODc3MiwgNDYuNTMwMTM5XSwgWy04Ni45NDg1MjYsIDQ2LjQ2OTg5M10sIFstODYuNjk2NTg3LCA0Ni40MzcwMzFdLCBbLTg2LjE1OTg0NiwgNDYuNjY3MDYzXSwgWy04NS44ODA1MjIsIDQ2LjY4ODk3XSwgWy04NS41MDgwOTEsIDQ2LjY3ODAxNl0sIFstODUuMjU2MTUxLCA0Ni43NTQ2OTRdLCBbLTg1LjA2NDQ1OCwgNDYuNzYwMTcxXSwgWy04NS4wMjYxMiwgNDYuNDgwODQ3XSwgWy04NC44Mjg5NSwgNDYuNDQyNTA4XSwgWy04NC42MzE3OCwgNDYuNDg2MzIzXSwgWy04NC41NDk2MjYsIDQ2LjQyMDZdLCBbLTg0LjQxODE3OSwgNDYuNTAyNzU0XSwgWy04NC4xMjc5MDIsIDQ2LjUzMDEzOV0sIFstODQuMTIyNDI1LCA0Ni4xNzk2MTVdLCBbLTgzLjk5MDk3OCwgNDYuMDMxNzM3XSwgWy04My43OTM4MDgsIDQ1Ljk5MzM5OV0sIFstODMuNzcxOSwgNDYuMDkxOTg0XSwgWy04My41ODAyMDgsIDQ2LjA5MTk4NF0sIFstODMuNDc2MTQ2LCA0NS45ODc5MjJdLCBbLTgzLjU2Mzc3NywgNDUuOTExMjQ1XSwgWy04NC4xMTE0NzEsIDQ1Ljk3Njk2OF0sIFstODQuMzc0MzY0LCA0NS45MzMxNTNdLCBbLTg0LjY1OTE2NSwgNDYuMDUzNjQ1XSwgWy04NC43NDEzMTksIDQ1Ljk0NDEwNl0sIFstODQuNzAyOTgsIDQ1Ljg1MDk5OF0sIFstODQuODI4OTUsIDQ1Ljg3MjkwNl0sIFstODUuMDE1MTY2LCA0Ni4wMDk4M10sIFstODUuMzM4MzA1LCA0Ni4wOTE5ODRdLCBbLTg1LjUwMjYxNCwgNDYuMDk3NDYxXSwgWy04NS42NjE0NDUsIDQ1Ljk2NjAxNF0sIFstODUuOTI0MzM4LCA0NS45MzMxNTNdLCBbLTg2LjIwOTEzOSwgNDUuOTYwNTM3XSwgWy04Ni4zMjQxNTUsIDQ1LjkwNTc2OF0sIFstODYuMzUxNTM5LCA0NS43OTYyMjldLCBbLTg2LjY2MzcyNSwgNDUuNzAzMTIxXSwgWy04Ni42NDcyOTQsIDQ1LjgzNDU2OF0sIFstODYuNzg0MjE4LCA0NS44NjE5NTJdLCBbLTg2LjgzODk4NywgNDUuNzI1MDI5XSwgWy04Ny4wNjkwMTksIDQ1LjcxOTU1Ml0sIFstODcuMTczMDgsIDQ1LjY1OTMwNV0sIFstODcuMzI2NDM1LCA0NS40MjM3OTddLCBbLTg3LjYxMTIzNiwgNDUuMTIyNTY1XSwgWy04Ny41ODkzMjgsIDQ1LjA5NTE4MV1dXSwgW1tbLTg4LjgwNTIwOSwgNDcuOTc2MDUxXSwgWy04OS4wNTcxNDgsIDQ3Ljg1MDA4Ml0sIFstODkuMTg4NTk0LCA0Ny44MzM2NTFdLCBbLTg5LjE3NzY0MSwgNDcuOTM3NzEzXSwgWy04OC41NDc3OTIsIDQ4LjE3MzIyMV0sIFstODguNjY4Mjg1LCA0OC4wMDg5MTNdLCBbLTg4LjgwNTIwOSwgNDcuOTc2MDUxXV1dXSwgInR5cGUiOiAiTXVsdGlQb2x5Z29uIn0sICJpZCI6ICJNSSIsICJwcm9wZXJ0aWVzIjogeyJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiTWljaGlnYW4iLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICIjMzFhMzU0IiwgImZpbGxPcGFjaXR5IjogMC42LCAib3BhY2l0eSI6IDEsICJ3ZWlnaHQiOiAxfX0sICJ0eXBlIjogIkZlYXR1cmUifSwgeyJnZW9tZXRyeSI6IHsiY29vcmRpbmF0ZXMiOiBbW1stOTIuMDE0Njk2LCA0Ni43MDU0MDFdLCBbLTkyLjA5MTM3MywgNDYuNzQ5MjE3XSwgWy05Mi4yOTQwMiwgNDYuNjY3MDYzXSwgWy05Mi4yOTQwMiwgNDYuMDc1NTUzXSwgWy05Mi4zNTQyNjYsIDQ2LjAxNTMwN10sIFstOTIuNjM5MDY3LCA0NS45MzMxNTNdLCBbLTkyLjg2OTA5OCwgNDUuNzE5NTUyXSwgWy05Mi44ODU1MjksIDQ1LjU3NzE1MV0sIFstOTIuNzcwNTEzLCA0NS41NjYxOThdLCBbLTkyLjY0NDU0NCwgNDUuNDQwMjI4XSwgWy05Mi43NTk1NiwgNDUuMjg2ODc0XSwgWy05Mi43Mzc2NTIsIDQ1LjExNzA4OF0sIFstOTIuODA4ODUyLCA0NC43NTAxMzNdLCBbLTkyLjU0NTk1OSwgNDQuNTY5Mzk0XSwgWy05Mi4zMzc4MzUsIDQ0LjU1Mjk2NF0sIFstOTIuMjMzNzczLCA0NC40NDM0MjVdLCBbLTkxLjkyNzA2NSwgNDQuMzMzODg2XSwgWy05MS44Nzc3NzIsIDQ0LjIwMjQzOV0sIFstOTEuNTkyOTcxLCA0NC4wMzI2NTRdLCBbLTkxLjQzNDE0LCA0My45OTQzMTZdLCBbLTkxLjI0MjQ0NywgNDMuNzc1MjM4XSwgWy05MS4yNjk4MzIsIDQzLjYxNjQwN10sIFstOTEuMjE1MDYyLCA0My41MDEzOTFdLCBbLTkxLjM2ODQxNywgNDMuNTAxMzkxXSwgWy05Ni40NTEwMTcsIDQzLjUwMTM5MV0sIFstOTYuNDUxMDE3LCA0NS4yOTc4MjddLCBbLTk2LjY4MTA0OSwgNDUuNDEyODQzXSwgWy05Ni44NTYzMTEsIDQ1LjYwNDUzNl0sIFstOTYuNTgyNDY0LCA0NS44MTgxMzddLCBbLTk2LjU2MDU1NiwgNDUuOTMzMTUzXSwgWy05Ni41OTg4OTUsIDQ2LjMzMjk2OV0sIFstOTYuNzE5Mzg3LCA0Ni40MzcwMzFdLCBbLTk2LjgwMTU0MiwgNDYuNjU2MTA5XSwgWy05Ni43ODUxMTEsIDQ2LjkyNDQ3OV0sIFstOTYuODIzNDQ5LCA0Ni45NjgyOTRdLCBbLTk2Ljg1NjMxMSwgNDcuNjA5MDk2XSwgWy05Ny4wNTM0ODEsIDQ3Ljk0ODY2N10sIFstOTcuMTMwMTU4LCA0OC4xNDAzNTldLCBbLTk3LjE2MzAyLCA0OC41NDU2NTNdLCBbLTk3LjA5NzI5NiwgNDguNjgyNTc3XSwgWy05Ny4yMjg3NDMsIDQ5LjAwMDIzOV0sIFstOTUuMTUyOTgzLCA0OS4wMDAyMzldLCBbLTk1LjE1Mjk4MywgNDkuMzgzNjI1XSwgWy05NC45NTU4MTMsIDQ5LjM3MjY3MV0sIFstOTQuODI0MzY2LCA0OS4yOTU5OTRdLCBbLTk0LjY5MjkyLCA0OC43NzU2ODVdLCBbLTk0LjU4ODg1OCwgNDguNzE1NDM4XSwgWy05NC4yNjAyNDEsIDQ4LjY5OTAwN10sIFstOTQuMjIxOTAzLCA0OC42NDk3MTVdLCBbLTkzLjgzODUxNywgNDguNjI3ODA3XSwgWy05My43OTQ3MDEsIDQ4LjUxODI2OF0sIFstOTMuNDY2MDg1LCA0OC41NDU2NTNdLCBbLTkzLjQ2NjA4NSwgNDguNTg5NDY5XSwgWy05My4yMDg2NjksIDQ4LjY0NDIzOF0sIFstOTIuOTg0MTE0LCA0OC42MjIzM10sIFstOTIuNzI2Njk4LCA0OC41NDAxNzZdLCBbLTkyLjY1NTQ5OCwgNDguNDM2MTE0XSwgWy05Mi41MDc2MiwgNDguNDQ3MDY4XSwgWy05Mi4zNzA2OTcsIDQ4LjIyMjUxNF0sIFstOTIuMzA0OTc0LCA0OC4zMTU2MjJdLCBbLTkyLjA1MzAzNCwgNDguMzU5NDM3XSwgWy05Mi4wMDkyMTksIDQ4LjI2NjMyOV0sIFstOTEuNzEzNDY0LCA0OC4yMDA2MDZdLCBbLTkxLjcxMzQ2NCwgNDguMTEyOTc1XSwgWy05MS41NjU1ODcsIDQ4LjA0MTc3NV0sIFstOTEuMjY0MzU1LCA0OC4wODAxMTNdLCBbLTkxLjA4MzYxNiwgNDguMTc4Njk4XSwgWy05MC44MzcxNTQsIDQ4LjIzODk0NF0sIFstOTAuNzQ5NTIyLCA0OC4wOTEwNjddLCBbLTkwLjU3OTczNywgNDguMTIzOTI5XSwgWy05MC4zNzcwOTEsIDQ4LjA5MTA2N10sIFstOTAuMTQxNTgyLCA0OC4xMTI5NzVdLCBbLTg5Ljg3MzIxMiwgNDcuOTg3MDA1XSwgWy04OS42MTU3OTYsIDQ4LjAwODkxM10sIFstODkuNjM3NzA0LCA0Ny45NTQxNDRdLCBbLTg5Ljk3MTc5NywgNDcuODI4MTc0XSwgWy05MC40MzczMzcsIDQ3LjcyOTU4OV0sIFstOTAuNzM4NTY5LCA0Ny42MjU1MjddLCBbLTkxLjE3MTI0NywgNDcuMzY4MTExXSwgWy05MS4zNTc0NjMsIDQ3LjIwOTI4XSwgWy05MS42NDIyNjQsIDQ3LjAyODU0MV0sIFstOTIuMDkxMzczLCA0Ni43ODc1NTVdLCBbLTkyLjAxNDY5NiwgNDYuNzA1NDAxXV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJNTiIsICJwcm9wZXJ0aWVzIjogeyJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiTWlubmVzb3RhIiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2FkZGQ4ZSIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbLTg4LjQ3MTExNSwgMzQuOTk1NzAzXSwgWy04OC4yMDI3NDUsIDM0Ljk5NTcwM10sIFstODguMDk4NjgzLCAzNC44OTE2NDFdLCBbLTg4LjI0MTA4NCwgMzMuNzk2MjUzXSwgWy04OC40NzExMTUsIDMxLjg5NTc1NF0sIFstODguMzk0NDM4LCAzMC4zNjc2ODhdLCBbLTg4LjUwMzk3NywgMzAuMzIzODcyXSwgWy04OC43NDQ5NjIsIDMwLjM0NTc4XSwgWy04OC44NDM1NDcsIDMwLjQxMTUwNF0sIFstODkuMDg0NTMzLCAzMC4zNjc2ODhdLCBbLTg5LjQxODYyNiwgMzAuMjUyNjcyXSwgWy04OS41MjI2ODgsIDMwLjE4MTQ3Ml0sIFstODkuNjQzMTgxLCAzMC4yODU1MzRdLCBbLTg5LjY4MTUxOSwgMzAuNDQ5ODQyXSwgWy04OS44NDU4MjcsIDMwLjY2ODkyXSwgWy04OS43NDcyNDIsIDMwLjk5NzUzNl0sIFstOTEuNjM2Nzg3LCAzMC45OTc1MzZdLCBbLTkxLjU2NTU4NywgMzEuMDY4NzM2XSwgWy05MS42MzY3ODcsIDMxLjI2NTkwNl0sIFstOTEuNTE2Mjk0LCAzMS4yNzY4Nl0sIFstOTEuNDk5ODYzLCAzMS42NDM4MTVdLCBbLTkxLjQwMTI3OCwgMzEuNjIxOTA3XSwgWy05MS4zNDEwMzIsIDMxLjg0NjQ2Ml0sIFstOTEuMTA1NTI0LCAzMS45ODg4NjJdLCBbLTkwLjk4NTAzMSwgMzIuMjE4ODk0XSwgWy05MS4wMDY5MzksIDMyLjUxNDY0OV0sIFstOTEuMTU0ODE2LCAzMi42NDA2MThdLCBbLTkxLjE0Mzg2MiwgMzIuODQzMjY1XSwgWy05MS4wNzI2NjIsIDMyLjg4NzA4MV0sIFstOTEuMTY1NzcsIDMzLjAwMjA5Nl0sIFstOTEuMDg5MDkzLCAzMy4xMzkwMl0sIFstOTEuMTQzODYyLCAzMy4zNDcxNDRdLCBbLTkxLjA1NjIzMSwgMzMuNDI5Mjk4XSwgWy05MS4yMzE0OTMsIDMzLjU2MDc0NF0sIFstOTEuMDcyNjYyLCAzMy44Njc0NTNdLCBbLTkwLjg5MTkyMywgMzQuMDI2Mjg0XSwgWy05MC45NTIxNjksIDM0LjEzNTgyM10sIFstOTAuNzQ0MDQ2LCAzNC4zMDAxMzFdLCBbLTkwLjc0OTUyMiwgMzQuMzY1ODU0XSwgWy05MC41Njg3ODMsIDM0LjQyMDYyNF0sIFstOTAuNTg1MjE0LCAzNC42MTc3OTRdLCBbLTkwLjQ4MTE1MiwgMzQuNjYxNjA5XSwgWy05MC40MDk5NTIsIDM0LjgzMTM5NF0sIFstOTAuMjUxMTIxLCAzNC45MDgwNzJdLCBbLTkwLjMxMTM2NywgMzQuOTk1NzAzXSwgWy04OC40NzExMTUsIDM0Ljk5NTcwM11dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiTVMiLCAicHJvcGVydGllcyI6IHsiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIk1pc3Npc3NpcHBpIiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiIzMxYTM1NCIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbLTkxLjgzMzk1NywgNDAuNjA5NTY2XSwgWy05MS43Mjk4OTUsIDQwLjYxNTA0M10sIFstOTEuNTI3MjQ4LCA0MC40MTIzOTddLCBbLTkxLjQxNzcwOSwgNDAuMzc5NTM1XSwgWy05MS41MDUzNCwgNDAuMjM3MTM1XSwgWy05MS40OTQzODYsIDQwLjAzNDQ4OF0sIFstOTEuMzY4NDE3LCAzOS43Mjc3NzldLCBbLTkxLjA2MTcwOCwgMzkuNDcwMzYzXSwgWy05MC43Mjc2MTUsIDM5LjI1Njc2Ml0sIFstOTAuNjYxODkxLCAzOC45MjgxNDZdLCBbLTkwLjU4NTIxNCwgMzguODY3ODk5XSwgWy05MC40NzAxOTksIDM4Ljk2MTAwN10sIFstOTAuMjUxMTIxLCAzOC45MTcxOTJdLCBbLTkwLjEwODcyLCAzOC44NDU5OTJdLCBbLTkwLjIwNzMwNSwgMzguNzI1NDk5XSwgWy05MC4xNzk5MjEsIDM4LjYzMjM5MV0sIFstOTAuMzQ5NzA2LCAzOC4zNzQ5NzVdLCBbLTkwLjM1NTE4MywgMzguMjE2MTQ0XSwgWy05MC4wNTk0MjgsIDM4LjAxMzQ5N10sIFstODkuOTQ5ODg5LCAzNy44ODIwNV0sIFstODkuODQwMzUsIDM3LjkwMzk1OF0sIFstODkuNTE3MjExLCAzNy42OTAzNTddLCBbLTg5LjUxNzIxMSwgMzcuNTM3MDAzXSwgWy04OS40MzUwNTcsIDM3LjM0NTMxXSwgWy04OS41MTcyMTEsIDM3LjI3OTU4N10sIFstODkuMjkyNjU2LCAzNi45OTQ3ODZdLCBbLTg5LjEzMzgyNSwgMzYuOTgzODMyXSwgWy04OS4yMTU5NzksIDM2LjU3ODUzOF0sIFstODkuMzYzODU3LCAzNi42MjIzNTRdLCBbLTg5LjQxODYyNiwgMzYuNDk2Mzg0XSwgWy04OS40ODQzNDksIDM2LjQ5NjM4NF0sIFstODkuNTM5MTE5LCAzNi40OTYzODRdLCBbLTg5LjUzMzY0MiwgMzYuMjQ5OTIyXSwgWy04OS43MzA4MTIsIDM1Ljk5Nzk4M10sIFstOTAuMzc3MDkxLCAzNS45OTc5ODNdLCBbLTkwLjIxODI1OSwgMzYuMTg0MTk5XSwgWy05MC4wNjQ5MDUsIDM2LjMwNDY5MV0sIFstOTAuMTUyNTM2LCAzNi40OTYzODRdLCBbLTk0LjQ3Mzg0MiwgMzYuNTAxODYxXSwgWy05NC42MTYyNDIsIDM2LjUwMTg2MV0sIFstOTQuNjE2MjQyLCAzNy4wMDAyNjNdLCBbLTk0LjYxMDc2NSwgMzkuMTU4MTc3XSwgWy05NC44MjQzNjYsIDM5LjIwNzQ3XSwgWy05NC45ODMxOTcsIDM5LjQ0Mjk3OF0sIFstOTUuMTA5MTY3LCAzOS41NDE1NjNdLCBbLTk0Ljg4NDYxMiwgMzkuODMxODQxXSwgWy05NS4yMDc3NTIsIDM5LjkwODUxOF0sIFstOTUuMzA2MzM3LCA0MC4wMDE2MjZdLCBbLTk1LjU1Mjc5OSwgNDAuMjY0NTE5XSwgWy05NS43NjY0LCA0MC41ODc2NTldLCBbLTk0LjYzMjY3MywgNDAuNTcxMjI4XSwgWy05My4yNTc5NjEsIDQwLjU4MjE4Ml0sIFstOTEuODMzOTU3LCA0MC42MDk1NjZdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIk1PIiwgInByb3BlcnRpZXMiOiB7ImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJNaXNzb3VyaSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNhZGRkOGUiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWy0xMDQuMDQ3NTM0LCA0OS4wMDAyMzldLCBbLTEwNC4wNDIwNTcsIDQ3Ljg2MTAzNl0sIFstMTA0LjA0NzUzNCwgNDUuOTQ0MTA2XSwgWy0xMDQuMDQyMDU3LCA0NC45OTY1OTZdLCBbLTEwNC4wNTg0ODgsIDQ0Ljk5NjU5Nl0sIFstMTA1LjkxNTE3LCA0NS4wMDIwNzNdLCBbLTEwOS4wODA4NDIsIDQ1LjAwMjA3M10sIFstMTExLjA1MjU0LCA0NS4wMDIwNzNdLCBbLTExMS4wNDcwNjMsIDQ0LjQ3NjI4Nl0sIFstMTExLjIyNzgwMywgNDQuNTgwMzQ4XSwgWy0xMTEuMzg2NjM0LCA0NC43NTU2MV0sIFstMTExLjYxNjY2NSwgNDQuNTQ3NDg3XSwgWy0xMTEuODE5MzEyLCA0NC41MDkxNDhdLCBbLTExMS44Njg2MDUsIDQ0LjU2MzkxN10sIFstMTEyLjEwNDExMywgNDQuNTIwMTAyXSwgWy0xMTIuMjQxMDM2LCA0NC41NjkzOTRdLCBbLTExMi40NzEwNjgsIDQ0LjQ4MTc2M10sIFstMTEyLjc4MzI1NCwgNDQuNDg3MjRdLCBbLTExMi44ODczMTUsIDQ0LjM5NDEzMl0sIFstMTEzLjAwMjMzMSwgNDQuNDQ4OTAyXSwgWy0xMTMuMTMzNzc4LCA0NC43NzIwNDFdLCBbLTExMy4zNDE5MDEsIDQ0Ljc4Mjk5NV0sIFstMTEzLjQ1NjkxNywgNDQuODY1MTQ5XSwgWy0xMTMuNDUxNDQsIDQ1LjA1Njg0Ml0sIFstMTEzLjU3MTkzMywgNDUuMTI4MDQyXSwgWy0xMTMuNzM2MjQxLCA0NS4zMzA2ODldLCBbLTExMy44MzQ4MjYsIDQ1LjUyMjM4Ml0sIFstMTEzLjgwNzQ0MSwgNDUuNjA0NTM2XSwgWy0xMTMuOTg4MTgsIDQ1LjcwMzEyMV0sIFstMTE0LjA4Njc2NSwgNDUuNTkzNTgyXSwgWy0xMTQuMzMzMjI4LCA0NS40NTY2NTldLCBbLTExNC41NDY4MjgsIDQ1LjU2MDcyMV0sIFstMTE0LjQ5NzUzNiwgNDUuNjcwMjU5XSwgWy0xMTQuNTY4NzM2LCA0NS43NzQzMjFdLCBbLTExNC4zODc5OTcsIDQ1Ljg4Mzg2XSwgWy0xMTQuNDkyMDU5LCA0Ni4wMzcyMTRdLCBbLTExNC40NjQ2NzQsIDQ2LjI3MjcyM10sIFstMTE0LjMyMjI3NCwgNDYuNjQ1MTU1XSwgWy0xMTQuNjEyNTUyLCA0Ni42Mzk2NzhdLCBbLTExNC42MjM1MDYsIDQ2LjcwNTQwMV0sIFstMTE0Ljg4NjM5OSwgNDYuODA5NDYzXSwgWy0xMTQuOTMwMjE0LCA0Ni45MTkwMDJdLCBbLTExNS4zMDI2NDYsIDQ3LjE4NzM3Ml0sIFstMTE1LjMyNDU1NCwgNDcuMjU4NTcyXSwgWy0xMTUuNTI3MjAxLCA0Ny4zMDIzODhdLCBbLTExNS43MTg4OTQsIDQ3LjQyMjg4XSwgWy0xMTUuNzI0MzcxLCA0Ny42OTY3MjddLCBbLTExNi4wNDc1MSwgNDcuOTc2MDUxXSwgWy0xMTYuMDQ3NTEsIDQ5LjAwMDIzOV0sIFstMTExLjUwMTY1LCA0OC45OTQ3NjJdLCBbLTEwOS40NTMyNzQsIDQ5LjAwMDIzOV0sIFstMTA0LjA0NzUzNCwgNDkuMDAwMjM5XV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJNVCIsICJwcm9wZXJ0aWVzIjogeyJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiTW9udGFuYSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNhZGRkOGUiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWy0xMDMuMzI0NTc4LCA0My4wMDI5ODldLCBbLTEwMS42MjY3MjYsIDQyLjk5NzUxMl0sIFstOTguNDk5MzkzLCA0Mi45OTc1MTJdLCBbLTk4LjQ2NjUzMSwgNDIuOTQ4MjJdLCBbLTk3Ljk1MTY5OSwgNDIuNzY3NDgxXSwgWy05Ny44MzEyMDYsIDQyLjg2NjA2Nl0sIFstOTcuNjg4ODA2LCA0Mi44NDQxNThdLCBbLTk3LjIxNzc4OSwgNDIuODQ0MTU4XSwgWy05Ni42OTIwMDMsIDQyLjY1Nzk0Ml0sIFstOTYuNjI2Mjc5LCA0Mi41MTU1NDJdLCBbLTk2LjQ0NTU0LCA0Mi40ODgxNTddLCBbLTk2LjI2NDgwMSwgNDIuMDM5MDQ4XSwgWy05Ni4xMjc4NzgsIDQxLjk3MzMyNV0sIFstOTYuMDYyMTU1LCA0MS43OTgwNjNdLCBbLTk2LjEyMjQwMSwgNDEuNjc3NTddLCBbLTk2LjA5NTAxNiwgNDEuNTQwNjQ2XSwgWy05NS45MTk3NTQsIDQxLjQ1MzAxNV0sIFstOTUuOTI1MjMxLCA0MS4yMDEwNzZdLCBbLTk1LjgyNjY0NiwgNDAuOTc2NTIxXSwgWy05NS44ODE0MTYsIDQwLjcxOTEwNV0sIFstOTUuNzY2NCwgNDAuNTg3NjU5XSwgWy05NS41NTI3OTksIDQwLjI2NDUxOV0sIFstOTUuMzA2MzM3LCA0MC4wMDE2MjZdLCBbLTEwMS45MDYwNSwgNDAuMDAxNjI2XSwgWy0xMDIuMDUzOTI3LCA0MC4wMDE2MjZdLCBbLTEwMi4wNTM5MjcsIDQxLjAwMzkwNl0sIFstMTA0LjA1MzAxMSwgNDEuMDAzOTA2XSwgWy0xMDQuMDUzMDExLCA0My4wMDI5ODldLCBbLTEwMy4zMjQ1NzgsIDQzLjAwMjk4OV1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiTkUiLCAicHJvcGVydGllcyI6IHsiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIk5lYnJhc2thIiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2ZmZmZjYyIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbLTExNy4wMjc4ODIsIDQyLjAwMDcwOV0sIFstMTE0LjA0Mjk1LCA0MS45OTUyMzJdLCBbLTExNC4wNDg0MjcsIDM3LjAwMDI2M10sIFstMTE0LjA0ODQyNywgMzYuMTk1MTUzXSwgWy0xMTQuMTUyNDg5LCAzNi4wMjUzNjddLCBbLTExNC4yNTEwNzQsIDM2LjAxOTg5XSwgWy0xMTQuMzcxNTY2LCAzNi4xNDAzODNdLCBbLTExNC43Mzg1MjEsIDM2LjEwMjA0NV0sIFstMTE0LjY3ODI3NSwgMzUuNTE2MDEyXSwgWy0xMTQuNTk2MTIxLCAzNS4zMjQzMTldLCBbLTExNC41NzQyMTMsIDM1LjEzODEwM10sIFstMTE0LjYzNDQ1OSwgMzUuMDAxMThdLCBbLTExNS44NTAzNCwgMzUuOTcwNTk4XSwgWy0xMTYuNTQwNDM1LCAzNi41MDE4NjFdLCBbLTExNy40OTg4OTksIDM3LjIxOTM0XSwgWy0xMTguNzE0NzgsIDM4LjEwMTEyOF0sIFstMTIwLjAwMTg2MSwgMzguOTk5MzQ2XSwgWy0xMTkuOTk2Mzg0LCA0MC4yNjQ1MTldLCBbLTEyMC4wMDE4NjEsIDQxLjk5NTIzMl0sIFstMTE4LjY5ODM0OSwgNDEuOTg5NzU1XSwgWy0xMTcuMDI3ODgyLCA0Mi4wMDA3MDldXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIk5WIiwgInByb3BlcnRpZXMiOiB7ImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJOZXZhZGEiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICIjMDA2ODM3IiwgImZpbGxPcGFjaXR5IjogMC42LCAib3BhY2l0eSI6IDEsICJ3ZWlnaHQiOiAxfX0sICJ0eXBlIjogIkZlYXR1cmUifSwgeyJnZW9tZXRyeSI6IHsiY29vcmRpbmF0ZXMiOiBbW1stNzEuMDgxODMsIDQ1LjMwMzMwNF0sIFstNzEuMDMyNTM3LCA0NC42NTcwMjVdLCBbLTcwLjk2NjgxNCwgNDMuMzQyNTZdLCBbLTcwLjgwNzk4MywgNDMuMjI3NTQ0XSwgWy03MC44MjQ0MTMsIDQzLjEyODk1OV0sIFstNzAuNzAzOTIxLCA0My4wNTc3NTldLCBbLTcwLjgxODkzNiwgNDIuODcxNTQzXSwgWy03MC45MTc1MjEsIDQyLjg4Nzk3NF0sIFstNzEuMTg1ODkxLCA0Mi43ODkzODldLCBbLTcxLjI5NTQzLCA0Mi42OTYyODFdLCBbLTcyLjQ1NjU0MiwgNDIuNzI5MTQyXSwgWy03Mi41NDQxNzMsIDQyLjgwNTgyXSwgWy03Mi41MzMyMTksIDQyLjk1MzY5N10sIFstNzIuNDQ1NTg4LCA0My4wMDg0NjZdLCBbLTcyLjQ1NjU0MiwgNDMuMTUwODY3XSwgWy03Mi4zNzk4NjQsIDQzLjU3MjU5MV0sIFstNzIuMjA0NjAyLCA0My43Njk3NjFdLCBbLTcyLjExNjk3MSwgNDMuOTk0MzE2XSwgWy03Mi4wMjkzNCwgNDQuMDc2NDddLCBbLTcyLjAzNDgxNywgNDQuMzIyOTMyXSwgWy03MS43MDA3MjQsIDQ0LjQxNjA0XSwgWy03MS41MzY0MTYsIDQ0LjU4NTgyNV0sIFstNzEuNjI5NTI0LCA0NC43NTAxMzNdLCBbLTcxLjQ5MjYsIDQ0LjkxNDQ0Ml0sIFstNzEuNTAzNTU0LCA0NS4wMTMwMjddLCBbLTcxLjM2MTE1NCwgNDUuMjcwNDQzXSwgWy03MS4xMzExMjIsIDQ1LjI0MzA1OF0sIFstNzEuMDgxODMsIDQ1LjMwMzMwNF1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiTkgiLCAicHJvcGVydGllcyI6IHsiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIk5ldyBIYW1wc2hpcmUiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICIjYWRkZDhlIiwgImZpbGxPcGFjaXR5IjogMC42LCAib3BhY2l0eSI6IDEsICJ3ZWlnaHQiOiAxfX0sICJ0eXBlIjogIkZlYXR1cmUifSwgeyJnZW9tZXRyeSI6IHsiY29vcmRpbmF0ZXMiOiBbW1stNzQuMjM2NTQ3LCA0MS4xNDA4M10sIFstNzMuOTAyNDU0LCA0MC45OTg0MjldLCBbLTc0LjAyMjk0NywgNDAuNzA4MTUxXSwgWy03NC4xODcyNTUsIDQwLjY0MjQyOF0sIFstNzQuMjc0ODg2LCA0MC40ODkwNzRdLCBbLTc0LjAwMTAzOSwgNDAuNDEyMzk3XSwgWy03My45NzkxMzEsIDQwLjI5NzM4MV0sIFstNzQuMDk5NjI0LCAzOS43NjA2NDFdLCBbLTc0LjQxMTgwOSwgMzkuMzYwODI0XSwgWy03NC42MTQ0NTYsIDM5LjI0NTgwOF0sIFstNzQuNzk1MTk1LCAzOC45OTM4NjldLCBbLTc0Ljg4ODMwMywgMzkuMTU4MTc3XSwgWy03NS4xNzg1ODEsIDM5LjI0MDMzMV0sIFstNzUuNTM0NTgyLCAzOS40NTk0MDldLCBbLTc1LjU1NjQ5LCAzOS42MDcyODZdLCBbLTc1LjU2MTk2NywgMzkuNjI5MTk0XSwgWy03NS41MDcxOTcsIDM5LjY4Mzk2NF0sIFstNzUuNDE0MDg5LCAzOS44MDQ0NTZdLCBbLTc1LjE0NTcxOSwgMzkuODg2NjFdLCBbLTc1LjEyOTI4OSwgMzkuOTYzMjg4XSwgWy03NC44MjI1OCwgNDAuMTI3NTk2XSwgWy03NC43NzMyODcsIDQwLjIxNTIyN10sIFstNzUuMDU4MDg4LCA0MC40MTc4NzRdLCBbLTc1LjA2OTA0MiwgNDAuNTQzODQzXSwgWy03NS4xOTUwMTIsIDQwLjU3NjcwNV0sIFstNzUuMjA1OTY2LCA0MC42OTE3MjFdLCBbLTc1LjA1MjYxMSwgNDAuODY2OTgzXSwgWy03NS4xMzQ3NjUsIDQwLjk3MTA0NV0sIFstNzQuODgyODI2LCA0MS4xNzkxNjhdLCBbLTc0LjgyODA1NywgNDEuMjg4NzA3XSwgWy03NC42OTY2MSwgNDEuMzU5OTA3XSwgWy03NC4yMzY1NDcsIDQxLjE0MDgzXV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJOSiIsICJwcm9wZXJ0aWVzIjogeyJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiTmV3IEplcnNleSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiMwMDY4MzciLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWy0xMDcuNDIxMzI5LCAzNy4wMDAyNjNdLCBbLTEwNi44NjgxNTgsIDM2Ljk5NDc4Nl0sIFstMTA0LjMzNzgxMiwgMzYuOTk0Nzg2XSwgWy0xMDMuMDAxNDM4LCAzNy4wMDAyNjNdLCBbLTEwMy4wMDE0MzgsIDM2LjUwMTg2MV0sIFstMTAzLjAzOTc3NywgMzYuNTAxODYxXSwgWy0xMDMuMDQ1MjU0LCAzNC4wMTUzM10sIFstMTAzLjA2NzE2MSwgMzMuMDAyMDk2XSwgWy0xMDMuMDY3MTYxLCAzMS45OTk4MTZdLCBbLTEwNi42MTYyMTksIDMxLjk5OTgxNl0sIFstMTA2LjY0MzYwMywgMzEuOTAxMjMxXSwgWy0xMDYuNTI4NTg4LCAzMS43ODYyMTZdLCBbLTEwOC4yMTAwMDgsIDMxLjc4NjIxNl0sIFstMTA4LjIxMDAwOCwgMzEuMzMxNjI5XSwgWy0xMDkuMDQ3OTgsIDMxLjMzMTYyOV0sIFstMTA5LjA0MjUwMywgMzcuMDAwMjYzXSwgWy0xMDcuNDIxMzI5LCAzNy4wMDAyNjNdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIk5NIiwgInByb3BlcnRpZXMiOiB7ImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJOZXcgTWV4aWNvIiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiIzc4YzY3OSIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbLTczLjM0MzgwNiwgNDUuMDEzMDI3XSwgWy03My4zMzI4NTIsIDQ0LjgwNDkwM10sIFstNzMuMzg3NjIyLCA0NC42MTg2ODddLCBbLTczLjI5NDUxNCwgNDQuNDM3OTQ4XSwgWy03My4zMjE4OTgsIDQ0LjI0NjI1NV0sIFstNzMuNDM2OTE0LCA0NC4wNDM2MDhdLCBbLTczLjM0OTI4MywgNDMuNzY5NzYxXSwgWy03My40MDQwNTIsIDQzLjY4NzYwN10sIFstNzMuMjQ1MjIxLCA0My41MjMyOTldLCBbLTczLjI3ODA4MywgNDIuODMzMjA0XSwgWy03My4yNjcxMjksIDQyLjc0NTU3M10sIFstNzMuNTA4MTE0LCA0Mi4wODgzNF0sIFstNzMuNDg2MjA2LCA0Mi4wNTAwMDJdLCBbLTczLjU1MTkzLCA0MS4yOTQxODRdLCBbLTczLjQ4MDczLCA0MS4yMTIwM10sIFstNzMuNzI3MTkyLCA0MS4xMDI0OTFdLCBbLTczLjY1NTk5MiwgNDAuOTg3NDc1XSwgWy03My4yMjg3OSwgNDAuOTA1MzIxXSwgWy03My4xNDExNTksIDQwLjk2NTU2OF0sIFstNzIuNzc0MjA0LCA0MC45NjU1NjhdLCBbLTcyLjU4Nzk4OCwgNDAuOTk4NDI5XSwgWy03Mi4yODEyOCwgNDEuMTU3MjYxXSwgWy03Mi4yNTkzNzIsIDQxLjA0MjI0NV0sIFstNzIuMTAwNTQxLCA0MC45OTI5NTJdLCBbLTcyLjQ2NzQ5NiwgNDAuODQ1MDc1XSwgWy03My4yMzk3NDQsIDQwLjYyNTk5N10sIFstNzMuNTYyODg0LCA0MC41ODIxODJdLCBbLTczLjc3NjQ4NCwgNDAuNTkzMTM2XSwgWy03My45MzUzMTYsIDQwLjU0Mzg0M10sIFstNzQuMDIyOTQ3LCA0MC43MDgxNTFdLCBbLTczLjkwMjQ1NCwgNDAuOTk4NDI5XSwgWy03NC4yMzY1NDcsIDQxLjE0MDgzXSwgWy03NC42OTY2MSwgNDEuMzU5OTA3XSwgWy03NC43NDA0MjYsIDQxLjQzMTEwOF0sIFstNzQuODkzNzgsIDQxLjQzNjU4NF0sIFstNzUuMDc0NTE5LCA0MS42MDYzN10sIFstNzUuMDUyNjExLCA0MS43NTQyNDddLCBbLTc1LjE3MzEwNCwgNDEuODY5MjYzXSwgWy03NS4yNDk3ODEsIDQxLjg2Mzc4Nl0sIFstNzUuMzU5MzIsIDQyLjAwMDcwOV0sIFstNzkuNzYyNzgsIDQyLjAwMDcwOV0sIFstNzkuNzYyNzgsIDQyLjI1MjY0OV0sIFstNzkuNzYyNzgsIDQyLjI2OTA3OV0sIFstNzkuMTQ5MzYzLCA0Mi41NTM4OF0sIFstNzkuMDUwNzc4LCA0Mi42OTA4MDRdLCBbLTc4Ljg1MzYwOCwgNDIuNzgzOTEyXSwgWy03OC45MzAyODUsIDQyLjk1MzY5N10sIFstNzkuMDEyNDM5LCA0Mi45ODY1NTldLCBbLTc5LjA3MjY4NiwgNDMuMjYwNDA2XSwgWy03OC40ODY2NTMsIDQzLjM3NTQyMV0sIFstNzcuOTY2MzQ0LCA0My4zNjk5NDRdLCBbLTc3Ljc1ODIyLCA0My4zNDI1Nl0sIFstNzcuNTMzNjY1LCA0My4yMzMwMjFdLCBbLTc3LjM5MTI2NSwgNDMuMjc2ODM2XSwgWy03Ni45NTg1ODcsIDQzLjI3MTM1OV0sIFstNzYuNjk1NjkzLCA0My4zNDI1Nl0sIFstNzYuNDE2MzcsIDQzLjUyMzI5OV0sIFstNzYuMjM1NjMxLCA0My41Mjg3NzZdLCBbLTc2LjIzMDE1NCwgNDMuODAyNjIzXSwgWy03Ni4xMzcwNDYsIDQzLjk2MTQ1NF0sIFstNzYuMzYxNiwgNDQuMDcwOTkzXSwgWy03Ni4zMTIzMDgsIDQ0LjE5Njk2Ml0sIFstNzUuOTEyNDkxLCA0NC4zNjY3NDhdLCBbLTc1Ljc2NDYxNCwgNDQuNTE0NjI1XSwgWy03NS4yODI2NDMsIDQ0Ljg0ODcxOF0sIFstNzQuODI4MDU3LCA0NS4wMTg1MDNdLCBbLTc0LjE0ODkxNiwgNDQuOTkxMTE5XSwgWy03My4zNDM4MDYsIDQ1LjAxMzAyN11dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiTlkiLCAicHJvcGVydGllcyI6IHsiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIk5ldyBZb3JrIiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiIzMxYTM1NCIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbLTgwLjk3ODY2MSwgMzYuNTYyMTA4XSwgWy04MC4yOTQwNDMsIDM2LjU0NTY3N10sIFstNzkuNTEwODQxLCAzNi41NDAyXSwgWy03NS44Njg2NzYsIDM2LjU1MTE1NF0sIFstNzUuNzUzNjYsIDM2LjE1MTMzN10sIFstNzYuMDMyOTg0LCAzNi4xODk2NzZdLCBbLTc2LjA3MTMyMiwgMzYuMTQwMzgzXSwgWy03Ni40MTA4OTMsIDM2LjA4MDEzN10sIFstNzYuNDYwMTg1LCAzNi4wMjUzNjddLCBbLTc2LjY4NDc0LCAzNi4wMDg5MzddLCBbLTc2LjY3Mzc4NiwgMzUuOTM3NzM2XSwgWy03Ni4zOTk5MzksIDM1Ljk4NzAyOV0sIFstNzYuMzYxNiwgMzUuOTQzMjEzXSwgWy03Ni4wNjAzNjgsIDM1Ljk5MjUwNl0sIFstNzUuOTYxNzgzLCAzNS44OTkzOThdLCBbLTc1Ljc4MTA0NCwgMzUuOTM3NzM2XSwgWy03NS43MTUzMjEsIDM1LjY5Njc1MV0sIFstNzUuNzc1NTY4LCAzNS41ODE3MzVdLCBbLTc1Ljg5NjA2LCAzNS41NzA3ODFdLCBbLTc2LjE0Nzk5OSwgMzUuMzI0MzE5XSwgWy03Ni40ODIwOTMsIDM1LjMxMzM2NV0sIFstNzYuNTM2ODYyLCAzNS4xNDM1OF0sIFstNzYuMzk0NDYyLCAzNC45NzM3OTVdLCBbLTc2LjI3OTQ0NiwgMzQuOTQwOTMzXSwgWy03Ni40OTMwNDcsIDM0LjY2MTYwOV0sIFstNzYuNjczNzg2LCAzNC42OTQ0NzFdLCBbLTc2Ljk5MTQ0OCwgMzQuNjY3MDg2XSwgWy03Ny4yMTA1MjYsIDM0LjYwNjg0XSwgWy03Ny41NTU1NzMsIDM0LjQxNTE0N10sIFstNzcuODI5NDIsIDM0LjE2MzIwOF0sIFstNzcuOTcxODIxLCAzMy44NDU1NDVdLCBbLTc4LjE3OTk0NCwgMzMuOTE2NzQ1XSwgWy03OC41NDE0MjIsIDMzLjg1MTAyMl0sIFstNzkuNjc1MTQ5LCAzNC44MDQwMV0sIFstODAuNzk3OTIyLCAzNC44MjA0NDFdLCBbLTgwLjc4MTQ5MSwgMzQuOTM1NDU2XSwgWy04MC45MzQ4NDUsIDM1LjEwNTI0MV0sIFstODEuMDM4OTA3LCAzNS4wNDQ5OTVdLCBbLTgxLjA0NDM4NCwgMzUuMTQ5MDU3XSwgWy04Mi4yNzY2OTYsIDM1LjE5ODM0OV0sIFstODIuNTUwNTQzLCAzNS4xNjAwMTFdLCBbLTgyLjc2NDE0MywgMzUuMDY2OTAzXSwgWy04My4xMDkxOTEsIDM1LjAwMTE4XSwgWy04My42MTg1NDYsIDM0Ljk4NDc0OV0sIFstODQuMzE5NTk0LCAzNC45OTAyMjZdLCBbLTg0LjI5MjIxLCAzNS4yMjU3MzRdLCBbLTg0LjA5NTA0LCAzNS4yNDc2NDJdLCBbLTg0LjAxODM2MywgMzUuNDExOTVdLCBbLTgzLjc3MTksIDM1LjU1OTgyN10sIFstODMuNDk4MDUzLCAzNS41NjUzMDRdLCBbLTgzLjI1MTU5MSwgMzUuNzE4NjU5XSwgWy04Mi45OTQxNzUsIDM1Ljc3MzQyOF0sIFstODIuNzc1MDk3LCAzNS45OTc5ODNdLCBbLTgyLjYzODE3NCwgMzYuMDYzNzA2XSwgWy04Mi42MTA3ODksIDM1Ljk2NTEyMV0sIFstODIuMjE2NDQ5LCAzNi4xNTY4MTRdLCBbLTgyLjAzNTcxLCAzNi4xMTg0NzVdLCBbLTgxLjkwOTc0MSwgMzYuMzA0NjkxXSwgWy04MS43MjM1MjUsIDM2LjM1Mzk4NF0sIFstODEuNjc5NzA5LCAzNi41ODk0OTJdLCBbLTgwLjk3ODY2MSwgMzYuNTYyMTA4XV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJOQyIsICJwcm9wZXJ0aWVzIjogeyJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiTm9ydGggQ2Fyb2xpbmEiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICIjMDA2ODM3IiwgImZpbGxPcGFjaXR5IjogMC42LCAib3BhY2l0eSI6IDEsICJ3ZWlnaHQiOiAxfX0sICJ0eXBlIjogIkZlYXR1cmUifSwgeyJnZW9tZXRyeSI6IHsiY29vcmRpbmF0ZXMiOiBbW1stOTcuMjI4NzQzLCA0OS4wMDAyMzldLCBbLTk3LjA5NzI5NiwgNDguNjgyNTc3XSwgWy05Ny4xNjMwMiwgNDguNTQ1NjUzXSwgWy05Ny4xMzAxNTgsIDQ4LjE0MDM1OV0sIFstOTcuMDUzNDgxLCA0Ny45NDg2NjddLCBbLTk2Ljg1NjMxMSwgNDcuNjA5MDk2XSwgWy05Ni44MjM0NDksIDQ2Ljk2ODI5NF0sIFstOTYuNzg1MTExLCA0Ni45MjQ0NzldLCBbLTk2LjgwMTU0MiwgNDYuNjU2MTA5XSwgWy05Ni43MTkzODcsIDQ2LjQzNzAzMV0sIFstOTYuNTk4ODk1LCA0Ni4zMzI5NjldLCBbLTk2LjU2MDU1NiwgNDUuOTMzMTUzXSwgWy0xMDQuMDQ3NTM0LCA0NS45NDQxMDZdLCBbLTEwNC4wNDIwNTcsIDQ3Ljg2MTAzNl0sIFstMTA0LjA0NzUzNCwgNDkuMDAwMjM5XSwgWy05Ny4yMjg3NDMsIDQ5LjAwMDIzOV1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiTkQiLCAicHJvcGVydGllcyI6IHsiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIk5vcnRoIERha290YSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNmZmZmY2MiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWy04MC41MTg1OTgsIDQxLjk3ODgwMl0sIFstODAuNTE4NTk4LCA0MC42MzY5NTFdLCBbLTgwLjY2NjQ3NSwgNDAuNTgyMTgyXSwgWy04MC41OTUyNzUsIDQwLjQ3MjY0M10sIFstODAuNjAwNzUyLCA0MC4zMTkyODldLCBbLTgwLjczNzY3NSwgNDAuMDc4MzAzXSwgWy04MC44MzA3ODMsIDM5LjcxMTM0OF0sIFstODEuMjE5NjQ2LCAzOS4zODgyMDldLCBbLTgxLjM0NTYxNiwgMzkuMzQ0MzkzXSwgWy04MS40NTUxNTUsIDM5LjQxMDExN10sIFstODEuNTcwMTcsIDM5LjI2NzcxNl0sIFstODEuNjg1MTg2LCAzOS4yNzMxOTNdLCBbLTgxLjgxMTE1NiwgMzkuMDgxNV0sIFstODEuNzgzNzcxLCAzOC45NjY0ODRdLCBbLTgxLjg4NzgzMywgMzguODczMzc2XSwgWy04Mi4wMzU3MSwgMzkuMDI2NzMxXSwgWy04Mi4yMjE5MjYsIDM4Ljc4NTc0NV0sIFstODIuMTcyNjM0LCAzOC42MzIzOTFdLCBbLTgyLjI5MzEyNywgMzguNTc3NjIyXSwgWy04Mi4zMzE0NjUsIDM4LjQ0NjE3NV0sIFstODIuNTk0MzU4LCAzOC40MjQyNjddLCBbLTgyLjczMTI4MiwgMzguNTYxMTkxXSwgWy04Mi44NDYyOTgsIDM4LjU4ODU3NV0sIFstODIuODkwMTEzLCAzOC43NTgzNjFdLCBbLTgzLjAzMjUxNCwgMzguNzI1NDk5XSwgWy04My4xNDIwNTIsIDM4LjYyNjkxNF0sIFstODMuNTE5OTYxLCAzOC43MDM1OTFdLCBbLTgzLjY3ODc5MiwgMzguNjMyMzkxXSwgWy04My45MDMzNDcsIDM4Ljc2OTMxNV0sIFstODQuMjE1NTMzLCAzOC44MDc2NTNdLCBbLTg0LjIzMTk2MywgMzguODk1Mjg0XSwgWy04NC40MzQ2MSwgMzkuMTAzNDA4XSwgWy04NC44MTc5OTYsIDM5LjEwMzQwOF0sIFstODQuODAxNTY1LCA0MC41MDAwMjhdLCBbLTg0LjgwNzA0MiwgNDEuNjk0MDAxXSwgWy04My40NTQyMzgsIDQxLjczMjMzOV0sIFstODMuMDY1Mzc1LCA0MS41OTU0MTZdLCBbLTgyLjkzMzkyOSwgNDEuNTEzMjYyXSwgWy04Mi44MzUzNDQsIDQxLjU4OTkzOV0sIFstODIuNjE2MjY2LCA0MS40MzExMDhdLCBbLTgyLjQ3OTM0MywgNDEuMzgxODE1XSwgWy04Mi4wMTM4MDMsIDQxLjUxMzI2Ml0sIFstODEuNzM5OTU2LCA0MS40ODU4NzddLCBbLTgxLjQ0NDIwMSwgNDEuNjcyMDkzXSwgWy04MS4wMTE1MjMsIDQxLjg1MjgzMl0sIFstODAuNTE4NTk4LCA0MS45Nzg4MDJdLCBbLTgwLjUxODU5OCwgNDEuOTc4ODAyXV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJPSCIsICJwcm9wZXJ0aWVzIjogeyJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiT2hpbyIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiM3OGM2NzkiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWy0xMDAuMDg3NzA2LCAzNy4wMDAyNjNdLCBbLTk0LjYxNjI0MiwgMzcuMDAwMjYzXSwgWy05NC42MTYyNDIsIDM2LjUwMTg2MV0sIFstOTQuNDMwMDI2LCAzNS4zOTU1MTldLCBbLTk0LjQ4NDc5NiwgMzMuNjM3NDIxXSwgWy05NC44NjgxODIsIDMzLjc0Njk2XSwgWy05NC45NjY3NjcsIDMzLjg2MTk3Nl0sIFstOTUuMjI0MTgzLCAzMy45NjA1NjFdLCBbLTk1LjI4OTkwNiwgMzMuODcyOTNdLCBbLTk1LjU0NzMyMiwgMzMuODc4NDA3XSwgWy05NS42MDIwOTIsIDMzLjkzMzE3Nl0sIFstOTUuODM3NiwgMzMuODM0NTkxXSwgWy05NS45MzYxODUsIDMzLjg4OTM2MV0sIFstOTYuMTQ5Nzg2LCAzMy44NDAwNjhdLCBbLTk2LjM0Njk1NiwgMzMuNjg2NzE0XSwgWy05Ni40MjM2MzMsIDMzLjc3NDM0NV0sIFstOTYuNjMxNzU2LCAzMy44NDU1NDVdLCBbLTk2Ljg1MDgzNCwgMzMuODQ1NTQ1XSwgWy05Ni45MjIwMzQsIDMzLjk2MDU2MV0sIFstOTcuMTczOTc0LCAzMy43MzYwMDZdLCBbLTk3LjI1NjEyOCwgMzMuODYxOTc2XSwgWy05Ny4zNzExNDMsIDMzLjgyMzYzN10sIFstOTcuNDU4Nzc0LCAzMy45MDU3OTFdLCBbLTk3LjY5NDI4MywgMzMuOTgyNDY5XSwgWy05Ny44Njk1NDUsIDMzLjg1MTAyMl0sIFstOTcuOTQ2MjIyLCAzMy45ODc5NDZdLCBbLTk4LjA4ODYyMywgMzQuMDA0Mzc2XSwgWy05OC4xNzA3NzcsIDM0LjExMzkxNV0sIFstOTguMzYyNDcsIDM0LjE1NzczMV0sIFstOTguNDg4NDM5LCAzNC4wNjQ2MjNdLCBbLTk4LjU3MDU5MywgMzQuMTQ2Nzc3XSwgWy05OC43Njc3NjMsIDM0LjEzNTgyM10sIFstOTguOTg2ODQxLCAzNC4yMjM0NTRdLCBbLTk5LjE4OTQ4OCwgMzQuMjEyNV0sIFstOTkuMjYwNjg4LCAzNC40MDQxOTNdLCBbLTk5LjU3ODM1LCAzNC40MTUxNDddLCBbLTk5LjY5ODg0MywgMzQuMzgyMjg1XSwgWy05OS45MjMzOTgsIDM0LjU3Mzk3OF0sIFstMTAwLjAwMDA3NSwgMzQuNTYzMDI0XSwgWy0xMDAuMDAwMDc1LCAzNi41MDE4NjFdLCBbLTEwMS44MTI5NDIsIDM2LjUwMTg2MV0sIFstMTAzLjAwMTQzOCwgMzYuNTAxODYxXSwgWy0xMDMuMDAxNDM4LCAzNy4wMDAyNjNdLCBbLTEwMi4wNDI5NzQsIDM2Ljk5NDc4Nl0sIFstMTAwLjA4NzcwNiwgMzcuMDAwMjYzXV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJPSyIsICJwcm9wZXJ0aWVzIjogeyJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiT2tsYWhvbWEiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICIjZDlmMGEzIiwgImZpbGxPcGFjaXR5IjogMC42LCAib3BhY2l0eSI6IDEsICJ3ZWlnaHQiOiAxfX0sICJ0eXBlIjogIkZlYXR1cmUifSwgeyJnZW9tZXRyeSI6IHsiY29vcmRpbmF0ZXMiOiBbW1stMTIzLjIxMTM0OCwgNDYuMTc0MTM4XSwgWy0xMjMuMTE4MjQsIDQ2LjE4NTA5Ml0sIFstMTIyLjkwNDYzOSwgNDYuMDgxMDNdLCBbLTEyMi44MTE1MzEsIDQ1Ljk2MDUzN10sIFstMTIyLjc2MjIzOSwgNDUuNjU5MzA1XSwgWy0xMjIuMjQ3NDA3LCA0NS41NDk3NjddLCBbLTEyMS44MDkyNTEsIDQ1LjcwODU5OF0sIFstMTIxLjUzNTQwNCwgNDUuNzI1MDI5XSwgWy0xMjEuMjE3NzQyLCA0NS42NzAyNTldLCBbLTEyMS4xODQ4OCwgNDUuNjA0NTM2XSwgWy0xMjAuNjM3MTg2LCA0NS43NDY5MzddLCBbLTEyMC41MDU3MzksIDQ1LjY5NzY0NF0sIFstMTIwLjIwOTk4NSwgNDUuNzI1MDI5XSwgWy0xMTkuOTYzNTIyLCA0NS44MjM2MTRdLCBbLTExOS41MjUzNjcsIDQ1LjkxMTI0NV0sIFstMTE5LjEyNTU1MSwgNDUuOTMzMTUzXSwgWy0xMTguOTg4NjI3LCA0NS45OTg4NzZdLCBbLTExNi45MTgzNDQsIDQ1Ljk5MzM5OV0sIFstMTE2Ljc4MTQyLCA0NS44MjM2MTRdLCBbLTExNi41NDU5MTIsIDQ1Ljc1MjQxM10sIFstMTE2LjQ2Mzc1OCwgNDUuNjE1NDldLCBbLTExNi42NzE4ODEsIDQ1LjMxOTczNV0sIFstMTE2LjczMjEyOCwgNDUuMTQ0NDczXSwgWy0xMTYuODQ3MTQzLCA0NS4wMjM5OF0sIFstMTE2LjgzMDcxMywgNDQuOTMwODcyXSwgWy0xMTYuOTM0Nzc0LCA0NC43ODI5OTVdLCBbLTExNy4wMzg4MzYsIDQ0Ljc1MDEzM10sIFstMTE3LjI0MTQ4MywgNDQuMzk0MTMyXSwgWy0xMTcuMTcwMjgzLCA0NC4yNTcyMDldLCBbLTExNi45Nzg1OSwgNDQuMjQwNzc4XSwgWy0xMTYuODk2NDM2LCA0NC4xNTg2MjRdLCBbLTExNy4wMjc4ODIsIDQzLjgzMDAwN10sIFstMTE3LjAyNzg4MiwgNDIuMDAwNzA5XSwgWy0xMTguNjk4MzQ5LCA0MS45ODk3NTVdLCBbLTEyMC4wMDE4NjEsIDQxLjk5NTIzMl0sIFstMTIxLjAzNzAwMywgNDEuOTk1MjMyXSwgWy0xMjIuMzc4ODUzLCA0Mi4wMTE2NjNdLCBbLTEyMy4yMzMyNTYsIDQyLjAwNjE4Nl0sIFstMTI0LjIxMzYyOCwgNDIuMDAwNzA5XSwgWy0xMjQuMzU2MDI5LCA0Mi4xMTU3MjVdLCBbLTEyNC40MzI3MDYsIDQyLjQzODg2NV0sIFstMTI0LjQxNjI3NSwgNDIuNjYzNDE5XSwgWy0xMjQuNTUzMTk4LCA0Mi44Mzg2ODFdLCBbLTEyNC40NTQ2MTMsIDQzLjAwMjk4OV0sIFstMTI0LjM4MzQxMywgNDMuMjcxMzU5XSwgWy0xMjQuMjM1NTM2LCA0My41NTYxNl0sIFstMTI0LjE2OTgxMywgNDMuODA4MV0sIFstMTI0LjA2MDI3NCwgNDQuNjU3MDI1XSwgWy0xMjQuMDc2NzA1LCA0NC43NzIwNDFdLCBbLTEyMy45NzgxMiwgNDUuMTQ0NDczXSwgWy0xMjMuOTM5NzgxLCA0NS42NTkzMDVdLCBbLTEyMy45OTQ1NTEsIDQ1Ljk0NDEwNl0sIFstMTIzLjk0NTI1OCwgNDYuMTEzODkyXSwgWy0xMjMuNTQ1NDQxLCA0Ni4yNjE3NjldLCBbLTEyMy4zNzAxNzksIDQ2LjE0Njc1M10sIFstMTIzLjIxMTM0OCwgNDYuMTc0MTM4XV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJPUiIsICJwcm9wZXJ0aWVzIjogeyJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiT3JlZ29uIiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiIzMxYTM1NCIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbLTc5Ljc2Mjc4LCA0Mi4yNTI2NDldLCBbLTc5Ljc2Mjc4LCA0Mi4wMDA3MDldLCBbLTc1LjM1OTMyLCA0Mi4wMDA3MDldLCBbLTc1LjI0OTc4MSwgNDEuODYzNzg2XSwgWy03NS4xNzMxMDQsIDQxLjg2OTI2M10sIFstNzUuMDUyNjExLCA0MS43NTQyNDddLCBbLTc1LjA3NDUxOSwgNDEuNjA2MzddLCBbLTc0Ljg5Mzc4LCA0MS40MzY1ODRdLCBbLTc0Ljc0MDQyNiwgNDEuNDMxMTA4XSwgWy03NC42OTY2MSwgNDEuMzU5OTA3XSwgWy03NC44MjgwNTcsIDQxLjI4ODcwN10sIFstNzQuODgyODI2LCA0MS4xNzkxNjhdLCBbLTc1LjEzNDc2NSwgNDAuOTcxMDQ1XSwgWy03NS4wNTI2MTEsIDQwLjg2Njk4M10sIFstNzUuMjA1OTY2LCA0MC42OTE3MjFdLCBbLTc1LjE5NTAxMiwgNDAuNTc2NzA1XSwgWy03NS4wNjkwNDIsIDQwLjU0Mzg0M10sIFstNzUuMDU4MDg4LCA0MC40MTc4NzRdLCBbLTc0Ljc3MzI4NywgNDAuMjE1MjI3XSwgWy03NC44MjI1OCwgNDAuMTI3NTk2XSwgWy03NS4xMjkyODksIDM5Ljk2MzI4OF0sIFstNzUuMTQ1NzE5LCAzOS44ODY2MV0sIFstNzUuNDE0MDg5LCAzOS44MDQ0NTZdLCBbLTc1LjYxNjczNiwgMzkuODMxODQxXSwgWy03NS43ODY1MjEsIDM5LjcyMjMwMl0sIFstNzkuNDc3OTc5LCAzOS43MjIzMDJdLCBbLTgwLjUxODU5OCwgMzkuNzIyMzAyXSwgWy04MC41MTg1OTgsIDQwLjYzNjk1MV0sIFstODAuNTE4NTk4LCA0MS45Nzg4MDJdLCBbLTgwLjUxODU5OCwgNDEuOTc4ODAyXSwgWy04MC4zMzIzODIsIDQyLjAzMzU3MV0sIFstNzkuNzYyNzgsIDQyLjI2OTA3OV0sIFstNzkuNzYyNzgsIDQyLjI1MjY0OV1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiUEEiLCAicHJvcGVydGllcyI6IHsiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlBlbm5zeWx2YW5pYSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiMzMWEzNTQiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbW1stNzEuMTk2ODQ1LCA0MS42Nzc1N10sIFstNzEuMTIwMTY4LCA0MS40OTY4MzFdLCBbLTcxLjMxNzMzOCwgNDEuNDc0OTIzXSwgWy03MS4xOTY4NDUsIDQxLjY3NzU3XV1dLCBbW1stNzEuNTMwOTM5LCA0Mi4wMTcxNF0sIFstNzEuMzgzMDYxLCA0Mi4wMTcxNF0sIFstNzEuMzI4MjkyLCA0MS43ODE2MzJdLCBbLTcxLjIyNDIzLCA0MS43MTA0MzFdLCBbLTcxLjM0NDcyMywgNDEuNzI2ODYyXSwgWy03MS40NDg3ODUsIDQxLjU3ODk4NV0sIFstNzEuNDgxNjQ2LCA0MS4zNzA4NjFdLCBbLTcxLjg1OTU1NSwgNDEuMzIxNTY5XSwgWy03MS43OTkzMDksIDQxLjQxNDY3N10sIFstNzEuNzk5MzA5LCA0Mi4wMDYxODZdLCBbLTcxLjUzMDkzOSwgNDIuMDE3MTRdXV1dLCAidHlwZSI6ICJNdWx0aVBvbHlnb24ifSwgImlkIjogIlJJIiwgInByb3BlcnRpZXMiOiB7ImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJSaG9kZSBJc2xhbmQiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICIjMDA2ODM3IiwgImZpbGxPcGFjaXR5IjogMC42LCAib3BhY2l0eSI6IDEsICJ3ZWlnaHQiOiAxfX0sICJ0eXBlIjogIkZlYXR1cmUifSwgeyJnZW9tZXRyeSI6IHsiY29vcmRpbmF0ZXMiOiBbW1stODIuNzY0MTQzLCAzNS4wNjY5MDNdLCBbLTgyLjU1MDU0MywgMzUuMTYwMDExXSwgWy04Mi4yNzY2OTYsIDM1LjE5ODM0OV0sIFstODEuMDQ0Mzg0LCAzNS4xNDkwNTddLCBbLTgxLjAzODkwNywgMzUuMDQ0OTk1XSwgWy04MC45MzQ4NDUsIDM1LjEwNTI0MV0sIFstODAuNzgxNDkxLCAzNC45MzU0NTZdLCBbLTgwLjc5NzkyMiwgMzQuODIwNDQxXSwgWy03OS42NzUxNDksIDM0LjgwNDAxXSwgWy03OC41NDE0MjIsIDMzLjg1MTAyMl0sIFstNzguNzE2Njg0LCAzMy44MDE3M10sIFstNzguOTM1NzYyLCAzMy42Mzc0MjFdLCBbLTc5LjE0OTM2MywgMzMuMzgwMDA1XSwgWy03OS4xODc3MDEsIDMzLjE3MTg4MV0sIFstNzkuMzU3NDg3LCAzMy4wMDc1NzNdLCBbLTc5LjU4MjA0MSwgMzMuMDA3NTczXSwgWy03OS42MzEzMzQsIDMyLjg4NzA4MV0sIFstNzkuODY2ODQyLCAzMi43NTU2MzRdLCBbLTc5Ljk5ODI4OSwgMzIuNjEzMjM0XSwgWy04MC4yMDY0MTIsIDMyLjU1Mjk4N10sIFstODAuNDMwOTY3LCAzMi4zOTk2MzNdLCBbLTgwLjQ1Mjg3NSwgMzIuMzI4NDMzXSwgWy04MC42NjA5OTgsIDMyLjI0NjI3OV0sIFstODAuODg1NTUzLCAzMi4wMzI2NzhdLCBbLTgxLjExNTU4NCwgMzIuMTIwMzA5XSwgWy04MS4xMjEwNjEsIDMyLjI5MDA5NF0sIFstODEuMjc5ODkzLCAzMi41NTg0NjRdLCBbLTgxLjQxNjgxNiwgMzIuNjI5NjY0XSwgWy04MS40Mjc3NywgMzIuODQzMjY1XSwgWy04MS40OTM0OTMsIDMzLjAwNzU3M10sIFstODEuNzYxODYzLCAzMy4xNjA5MjhdLCBbLTgxLjkzNzEyNSwgMzMuMzQ3MTQ0XSwgWy04MS45MjYxNzIsIDMzLjQ2MjE1OV0sIFstODIuMTk0NTQyLCAzMy42MzE5NDRdLCBbLTgyLjMyNTk4OCwgMzMuODE4MTZdLCBbLTgyLjU1NjAyLCAzMy45NDQxM10sIFstODIuNzE0ODUxLCAzNC4xNTIyNTRdLCBbLTgyLjc0NzcxMywgMzQuMjY3MjddLCBbLTgyLjkwMTA2NywgMzQuNDg2MzQ3XSwgWy04My4wMDUxMjksIDM0LjQ2OTkxNl0sIFstODMuMzM5MjIyLCAzNC42ODM1MTddLCBbLTgzLjMyMjc5MSwgMzQuNzg3NTc5XSwgWy04My4xMDkxOTEsIDM1LjAwMTE4XSwgWy04Mi43NjQxNDMsIDM1LjA2NjkwM11dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiU0MiLCAicHJvcGVydGllcyI6IHsiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlNvdXRoIENhcm9saW5hIiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiIzMxYTM1NCIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbLTEwNC4wNDc1MzQsIDQ1Ljk0NDEwNl0sIFstOTYuNTYwNTU2LCA0NS45MzMxNTNdLCBbLTk2LjU4MjQ2NCwgNDUuODE4MTM3XSwgWy05Ni44NTYzMTEsIDQ1LjYwNDUzNl0sIFstOTYuNjgxMDQ5LCA0NS40MTI4NDNdLCBbLTk2LjQ1MTAxNywgNDUuMjk3ODI3XSwgWy05Ni40NTEwMTcsIDQzLjUwMTM5MV0sIFstOTYuNTgyNDY0LCA0My40Nzk0ODNdLCBbLTk2LjUyNzY5NSwgNDMuMzk3MzI5XSwgWy05Ni41NjA1NTYsIDQzLjIyMjA2N10sIFstOTYuNDM0NTg3LCA0My4xMjM0ODJdLCBbLTk2LjUxMTI2NCwgNDMuMDUyMjgyXSwgWy05Ni41NDQxMjUsIDQyLjg1NTExMl0sIFstOTYuNjMxNzU2LCA0Mi43MDcyMzVdLCBbLTk2LjQ0NTU0LCA0Mi40ODgxNTddLCBbLTk2LjYyNjI3OSwgNDIuNTE1NTQyXSwgWy05Ni42OTIwMDMsIDQyLjY1Nzk0Ml0sIFstOTcuMjE3Nzg5LCA0Mi44NDQxNThdLCBbLTk3LjY4ODgwNiwgNDIuODQ0MTU4XSwgWy05Ny44MzEyMDYsIDQyLjg2NjA2Nl0sIFstOTcuOTUxNjk5LCA0Mi43Njc0ODFdLCBbLTk4LjQ2NjUzMSwgNDIuOTQ4MjJdLCBbLTk4LjQ5OTM5MywgNDIuOTk3NTEyXSwgWy0xMDEuNjI2NzI2LCA0Mi45OTc1MTJdLCBbLTEwMy4zMjQ1NzgsIDQzLjAwMjk4OV0sIFstMTA0LjA1MzAxMSwgNDMuMDAyOTg5XSwgWy0xMDQuMDU4NDg4LCA0NC45OTY1OTZdLCBbLTEwNC4wNDIwNTcsIDQ0Ljk5NjU5Nl0sIFstMTA0LjA0NzUzNCwgNDUuOTQ0MTA2XV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJTRCIsICJwcm9wZXJ0aWVzIjogeyJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiU291dGggRGFrb3RhIiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2Q5ZjBhMyIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbLTg4LjA1NDg2OCwgMzYuNDk2Mzg0XSwgWy04OC4wNzEyOTksIDM2LjY3NzEyM10sIFstODcuODUyMjIxLCAzNi42MzMzMDhdLCBbLTg2LjU5MjUyNSwgMzYuNjU1MjE2XSwgWy04NS40ODYxODMsIDM2LjYxNjg3N10sIFstODUuMjg5MDEzLCAzNi42Mjc4MzFdLCBbLTg0LjU0NDE0OSwgMzYuNTk0OTY5XSwgWy04My42ODk3NDYsIDM2LjU4NDAxNV0sIFstODMuNjczMzE2LCAzNi42MDA0NDZdLCBbLTgxLjY3OTcwOSwgMzYuNTg5NDkyXSwgWy04MS43MjM1MjUsIDM2LjM1Mzk4NF0sIFstODEuOTA5NzQxLCAzNi4zMDQ2OTFdLCBbLTgyLjAzNTcxLCAzNi4xMTg0NzVdLCBbLTgyLjIxNjQ0OSwgMzYuMTU2ODE0XSwgWy04Mi42MTA3ODksIDM1Ljk2NTEyMV0sIFstODIuNjM4MTc0LCAzNi4wNjM3MDZdLCBbLTgyLjc3NTA5NywgMzUuOTk3OTgzXSwgWy04Mi45OTQxNzUsIDM1Ljc3MzQyOF0sIFstODMuMjUxNTkxLCAzNS43MTg2NTldLCBbLTgzLjQ5ODA1MywgMzUuNTY1MzA0XSwgWy04My43NzE5LCAzNS41NTk4MjddLCBbLTg0LjAxODM2MywgMzUuNDExOTVdLCBbLTg0LjA5NTA0LCAzNS4yNDc2NDJdLCBbLTg0LjI5MjIxLCAzNS4yMjU3MzRdLCBbLTg0LjMxOTU5NCwgMzQuOTkwMjI2XSwgWy04NS42MDY2NzUsIDM0Ljk4NDc0OV0sIFstODcuMzU5Mjk2LCAzNS4wMDExOF0sIFstODguMjAyNzQ1LCAzNC45OTU3MDNdLCBbLTg4LjQ3MTExNSwgMzQuOTk1NzAzXSwgWy05MC4zMTEzNjcsIDM0Ljk5NTcwM10sIFstOTAuMjEyNzgyLCAzNS4wMjMwODddLCBbLTkwLjExNDE5NywgMzUuMTk4MzQ5XSwgWy05MC4xMzA2MjgsIDM1LjQzOTMzNV0sIFstODkuOTQ0NDEyLCAzNS42MDM2NDNdLCBbLTg5LjkxMTU1MSwgMzUuNzU2OTk3XSwgWy04OS43NjM2NzMsIDM1LjgxMTc2N10sIFstODkuNzMwODEyLCAzNS45OTc5ODNdLCBbLTg5LjUzMzY0MiwgMzYuMjQ5OTIyXSwgWy04OS41MzkxMTksIDM2LjQ5NjM4NF0sIFstODkuNDg0MzQ5LCAzNi40OTYzODRdLCBbLTg5LjQxODYyNiwgMzYuNDk2Mzg0XSwgWy04OS4yOTgxMzMsIDM2LjUwNzMzOF0sIFstODguMDU0ODY4LCAzNi40OTYzODRdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlROIiwgInByb3BlcnRpZXMiOiB7ImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJUZW5uZXNzZWUiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICIjNzhjNjc5IiwgImZpbGxPcGFjaXR5IjogMC42LCAib3BhY2l0eSI6IDEsICJ3ZWlnaHQiOiAxfX0sICJ0eXBlIjogIkZlYXR1cmUifSwgeyJnZW9tZXRyeSI6IHsiY29vcmRpbmF0ZXMiOiBbW1stMTAxLjgxMjk0MiwgMzYuNTAxODYxXSwgWy0xMDAuMDAwMDc1LCAzNi41MDE4NjFdLCBbLTEwMC4wMDAwNzUsIDM0LjU2MzAyNF0sIFstOTkuOTIzMzk4LCAzNC41NzM5NzhdLCBbLTk5LjY5ODg0MywgMzQuMzgyMjg1XSwgWy05OS41NzgzNSwgMzQuNDE1MTQ3XSwgWy05OS4yNjA2ODgsIDM0LjQwNDE5M10sIFstOTkuMTg5NDg4LCAzNC4yMTI1XSwgWy05OC45ODY4NDEsIDM0LjIyMzQ1NF0sIFstOTguNzY3NzYzLCAzNC4xMzU4MjNdLCBbLTk4LjU3MDU5MywgMzQuMTQ2Nzc3XSwgWy05OC40ODg0MzksIDM0LjA2NDYyM10sIFstOTguMzYyNDcsIDM0LjE1NzczMV0sIFstOTguMTcwNzc3LCAzNC4xMTM5MTVdLCBbLTk4LjA4ODYyMywgMzQuMDA0Mzc2XSwgWy05Ny45NDYyMjIsIDMzLjk4Nzk0Nl0sIFstOTcuODY5NTQ1LCAzMy44NTEwMjJdLCBbLTk3LjY5NDI4MywgMzMuOTgyNDY5XSwgWy05Ny40NTg3NzQsIDMzLjkwNTc5MV0sIFstOTcuMzcxMTQzLCAzMy44MjM2MzddLCBbLTk3LjI1NjEyOCwgMzMuODYxOTc2XSwgWy05Ny4xNzM5NzQsIDMzLjczNjAwNl0sIFstOTYuOTIyMDM0LCAzMy45NjA1NjFdLCBbLTk2Ljg1MDgzNCwgMzMuODQ1NTQ1XSwgWy05Ni42MzE3NTYsIDMzLjg0NTU0NV0sIFstOTYuNDIzNjMzLCAzMy43NzQzNDVdLCBbLTk2LjM0Njk1NiwgMzMuNjg2NzE0XSwgWy05Ni4xNDk3ODYsIDMzLjg0MDA2OF0sIFstOTUuOTM2MTg1LCAzMy44ODkzNjFdLCBbLTk1LjgzNzYsIDMzLjgzNDU5MV0sIFstOTUuNjAyMDkyLCAzMy45MzMxNzZdLCBbLTk1LjU0NzMyMiwgMzMuODc4NDA3XSwgWy05NS4yODk5MDYsIDMzLjg3MjkzXSwgWy05NS4yMjQxODMsIDMzLjk2MDU2MV0sIFstOTQuOTY2NzY3LCAzMy44NjE5NzZdLCBbLTk0Ljg2ODE4MiwgMzMuNzQ2OTZdLCBbLTk0LjQ4NDc5NiwgMzMuNjM3NDIxXSwgWy05NC4zODA3MzQsIDMzLjU0NDMxM10sIFstOTQuMTgzNTY0LCAzMy41OTM2MDZdLCBbLTk0LjA0MTE2NCwgMzMuNTQ5NzldLCBbLTk0LjA0MTE2NCwgMzMuMDE4NTI3XSwgWy05NC4wNDExNjQsIDMxLjk5NDMzOV0sIFstOTMuODIyMDg2LCAzMS43NzUyNjJdLCBbLTkzLjgxNjYwOSwgMzEuNTU2MTg0XSwgWy05My41NDI3NjIsIDMxLjE1MDg5XSwgWy05My41MjYzMzEsIDMwLjkzNzI5XSwgWy05My42MzAzOTMsIDMwLjY3OTg3NF0sIFstOTMuNzI4OTc4LCAzMC41NzU4MTJdLCBbLTkzLjY5NjExNiwgMzAuNDM4ODg4XSwgWy05My43NjczMTcsIDMwLjMzNDgyNl0sIFstOTMuNjkwNjM5LCAzMC4xNDMxMzNdLCBbLTkzLjkyNjE0OCwgMjkuNzg3MTMyXSwgWy05My44Mzg1MTcsIDI5LjY4ODU0N10sIFstOTQuMDAyODI1LCAyOS42ODMwN10sIFstOTQuNTIzMTM0LCAyOS41NDYxNDddLCBbLTk0LjcwOTM1LCAyOS42MjI4MjRdLCBbLTk0Ljc0MjIxMiwgMjkuNzg3MTMyXSwgWy05NC44NzM2NTksIDI5LjY3MjExN10sIFstOTQuOTY2NzY3LCAyOS42OTk1MDFdLCBbLTk1LjAxNjA1OSwgMjkuNTU3MTAxXSwgWy05NC45MTE5OTcsIDI5LjQ5Njg1NF0sIFstOTQuODk1NTY2LCAyOS4zMTA2MzhdLCBbLTk1LjA4MTc4MiwgMjkuMTEzNDY5XSwgWy05NS4zODMwMTQsIDI4Ljg2NzAwNl0sIFstOTUuOTg1NDc3LCAyOC42MDQxMTNdLCBbLTk2LjA0NTcyNCwgMjguNjQ3OTI5XSwgWy05Ni4yMjY0NjMsIDI4LjU4MjIwNV0sIFstOTYuMjMxOTQsIDI4LjY0MjQ1Ml0sIFstOTYuNDc4NDAyLCAyOC41OTg2MzZdLCBbLTk2LjU5MzQxOCwgMjguNzI0NjA2XSwgWy05Ni42NjQ2MTgsIDI4LjY5NzIyMV0sIFstOTYuNDAxNzI1LCAyOC40Mzk4MDVdLCBbLTk2LjU5MzQxOCwgMjguMzU3NjUxXSwgWy05Ni43NzQxNTcsIDI4LjQwNjk0M10sIFstOTYuODAxNTQyLCAyOC4yMjYyMDRdLCBbLTk3LjAyNjA5NiwgMjguMDM5OTg4XSwgWy05Ny4yNTYxMjgsIDI3LjY5NDk0MV0sIFstOTcuNDA0MDA1LCAyNy4zMzM0NjNdLCBbLTk3LjUxMzU0NCwgMjcuMzYwODQ4XSwgWy05Ny41NDA5MjksIDI3LjIyOTQwMV0sIFstOTcuNDI1OTEzLCAyNy4yNjIyNjNdLCBbLTk3LjQ4MDY4MiwgMjYuOTk5MzddLCBbLTk3LjU1NzM1OSwgMjYuOTg4NDE2XSwgWy05Ny41NjI4MzYsIDI2Ljg0MDUzOF0sIFstOTcuNDY5NzI4LCAyNi43NTgzODRdLCBbLTk3LjQ0MjM0NCwgMjYuNDU3MTUzXSwgWy05Ny4zMzI4MDUsIDI2LjM1MzA5MV0sIFstOTcuMzA1NDIsIDI2LjE2MTM5OF0sIFstOTcuMjE3Nzg5LCAyNS45OTE2MTNdLCBbLTk3LjUyNDQ5OCwgMjUuODg3NTUxXSwgWy05Ny42NTA0NjcsIDI2LjAxODk5N10sIFstOTcuODg1OTc2LCAyNi4wNjgyOV0sIFstOTguMTk4MTYxLCAyNi4wNTczMzZdLCBbLTk4LjQ2NjUzMSwgMjYuMjIxNjQ0XSwgWy05OC42NjkxNzgsIDI2LjIzODA3NV0sIFstOTguODIyNTMzLCAyNi4zNjk1MjJdLCBbLTk5LjAzMDY1NiwgMjYuNDEzMzM3XSwgWy05OS4xNzMwNTcsIDI2LjUzOTMwN10sIFstOTkuMjY2MTY1LCAyNi44NDA1MzhdLCBbLTk5LjQ0NjkwNCwgMjcuMDIxMjc3XSwgWy05OS40MjQ5OTYsIDI3LjE3NDYzMl0sIFstOTkuNTA3MTUsIDI3LjMzODk0XSwgWy05OS40Nzk3NjUsIDI3LjQ4MTM0XSwgWy05OS42MDU3MzUsIDI3LjY0MDE3Ml0sIFstOTkuNzA5Nzk3LCAyNy42NTY2MDNdLCBbLTk5Ljg3OTU4MiwgMjcuNzk5MDAzXSwgWy05OS45MzQzNTEsIDI3Ljk3OTc0Ml0sIFstMTAwLjA4MjIyOSwgMjguMTQ0MDVdLCBbLTEwMC4yOTU4MywgMjguMjgwOTc0XSwgWy0xMDAuMzk5ODkxLCAyOC41ODIyMDVdLCBbLTEwMC40OTg0NzYsIDI4LjY2NDM2XSwgWy0xMDAuNjI5OTIzLCAyOC45MDUzNDVdLCBbLTEwMC42NzM3MzgsIDI5LjEwMjUxNV0sIFstMTAwLjc5OTcwOCwgMjkuMjQ0OTE1XSwgWy0xMDEuMDEzMzA5LCAyOS4zNzA4ODVdLCBbLTEwMS4wNjI2MDEsIDI5LjQ1ODUxNl0sIFstMTAxLjI1OTc3MSwgMjkuNTM1MTkzXSwgWy0xMDEuNDEzMTI1LCAyOS43NTQyNzFdLCBbLTEwMS44NTEyODEsIDI5LjgwMzU2M10sIFstMTAyLjExNDE3NCwgMjkuNzkyNjA5XSwgWy0xMDIuMzM4NzI4LCAyOS44NjkyODZdLCBbLTEwMi4zODgwMjEsIDI5Ljc2NTIyNV0sIFstMTAyLjYyOTAwNiwgMjkuNzMyMzYzXSwgWy0xMDIuODA5NzQ1LCAyOS41MjQyMzldLCBbLTEwMi45MTkyODQsIDI5LjE5MDE0Nl0sIFstMTAyLjk3OTUzLCAyOS4xODQ2NjldLCBbLTEwMy4xMTY0NTQsIDI4Ljk4NzQ5OV0sIFstMTAzLjI4MDc2MiwgMjguOTgyMDIyXSwgWy0xMDMuNTI3MjI0LCAyOS4xMzUzNzZdLCBbLTEwNC4xNDYxMTksIDI5LjM4MTgzOV0sIFstMTA0LjI2NjYxMSwgMjkuNTEzMjg1XSwgWy0xMDQuNTA3NTk3LCAyOS42MzkyNTVdLCBbLTEwNC42NzczODIsIDI5LjkyNDA1Nl0sIFstMTA0LjY4ODMzNiwgMzAuMTgxNDcyXSwgWy0xMDQuODU4MTIxLCAzMC4zODk1OTZdLCBbLTEwNC44OTY0NTksIDMwLjU3MDMzNV0sIFstMTA1LjAwNTk5OCwgMzAuNjg1MzUxXSwgWy0xMDUuMzk0ODYxLCAzMC44NTUxMzZdLCBbLTEwNS42MDI5ODUsIDMxLjA4NTE2N10sIFstMTA1Ljc3Mjc3LCAzMS4xNjczMjFdLCBbLTEwNS45NTM1MDksIDMxLjM2NDQ5MV0sIFstMTA2LjIwNTQ0OCwgMzEuNDY4NTUzXSwgWy0xMDYuMzgwNzEsIDMxLjczMTQ0Nl0sIFstMTA2LjUyODU4OCwgMzEuNzg2MjE2XSwgWy0xMDYuNjQzNjAzLCAzMS45MDEyMzFdLCBbLTEwNi42MTYyMTksIDMxLjk5OTgxNl0sIFstMTAzLjA2NzE2MSwgMzEuOTk5ODE2XSwgWy0xMDMuMDY3MTYxLCAzMy4wMDIwOTZdLCBbLTEwMy4wNDUyNTQsIDM0LjAxNTMzXSwgWy0xMDMuMDM5Nzc3LCAzNi41MDE4NjFdLCBbLTEwMy4wMDE0MzgsIDM2LjUwMTg2MV0sIFstMTAxLjgxMjk0MiwgMzYuNTAxODYxXV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJUWCIsICJwcm9wZXJ0aWVzIjogeyJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiVGV4YXMiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICIjYWRkZDhlIiwgImZpbGxPcGFjaXR5IjogMC42LCAib3BhY2l0eSI6IDEsICJ3ZWlnaHQiOiAxfX0sICJ0eXBlIjogIkZlYXR1cmUifSwgeyJnZW9tZXRyeSI6IHsiY29vcmRpbmF0ZXMiOiBbW1stMTEyLjE2NDM1OSwgNDEuOTk1MjMyXSwgWy0xMTEuMDQ3MDYzLCA0Mi4wMDA3MDldLCBbLTExMS4wNDcwNjMsIDQwLjk5ODQyOV0sIFstMTA5LjA0Nzk4LCA0MC45OTg0MjldLCBbLTEwOS4wNTM0NTcsIDM5LjEyNTMxNl0sIFstMTA5LjA1ODkzNCwgMzguMjc2MzldLCBbLTEwOS4wNDI1MDMsIDM4LjE2Njg1MV0sIFstMTA5LjA0MjUwMywgMzcuMDAwMjYzXSwgWy0xMTAuNDk5MzY5LCAzNy4wMDU3NF0sIFstMTE0LjA0ODQyNywgMzcuMDAwMjYzXSwgWy0xMTQuMDQyOTUsIDQxLjk5NTIzMl0sIFstMTEyLjE2NDM1OSwgNDEuOTk1MjMyXV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJVVCIsICJwcm9wZXJ0aWVzIjogeyJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiVXRhaCIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNkOWYwYTMiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWy03MS41MDM1NTQsIDQ1LjAxMzAyN10sIFstNzEuNDkyNiwgNDQuOTE0NDQyXSwgWy03MS42Mjk1MjQsIDQ0Ljc1MDEzM10sIFstNzEuNTM2NDE2LCA0NC41ODU4MjVdLCBbLTcxLjcwMDcyNCwgNDQuNDE2MDRdLCBbLTcyLjAzNDgxNywgNDQuMzIyOTMyXSwgWy03Mi4wMjkzNCwgNDQuMDc2NDddLCBbLTcyLjExNjk3MSwgNDMuOTk0MzE2XSwgWy03Mi4yMDQ2MDIsIDQzLjc2OTc2MV0sIFstNzIuMzc5ODY0LCA0My41NzI1OTFdLCBbLTcyLjQ1NjU0MiwgNDMuMTUwODY3XSwgWy03Mi40NDU1ODgsIDQzLjAwODQ2Nl0sIFstNzIuNTMzMjE5LCA0Mi45NTM2OTddLCBbLTcyLjU0NDE3MywgNDIuODA1ODJdLCBbLTcyLjQ1NjU0MiwgNDIuNzI5MTQyXSwgWy03My4yNjcxMjksIDQyLjc0NTU3M10sIFstNzMuMjc4MDgzLCA0Mi44MzMyMDRdLCBbLTczLjI0NTIyMSwgNDMuNTIzMjk5XSwgWy03My40MDQwNTIsIDQzLjY4NzYwN10sIFstNzMuMzQ5MjgzLCA0My43Njk3NjFdLCBbLTczLjQzNjkxNCwgNDQuMDQzNjA4XSwgWy03My4zMjE4OTgsIDQ0LjI0NjI1NV0sIFstNzMuMjk0NTE0LCA0NC40Mzc5NDhdLCBbLTczLjM4NzYyMiwgNDQuNjE4Njg3XSwgWy03My4zMzI4NTIsIDQ0LjgwNDkwM10sIFstNzMuMzQzODA2LCA0NS4wMTMwMjddLCBbLTcyLjMwODY2NCwgNDUuMDAyMDczXSwgWy03MS41MDM1NTQsIDQ1LjAxMzAyN11dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiVlQiLCAicHJvcGVydGllcyI6IHsiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlZlcm1vbnQiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICIjZDlmMGEzIiwgImZpbGxPcGFjaXR5IjogMC42LCAib3BhY2l0eSI6IDEsICJ3ZWlnaHQiOiAxfX0sICJ0eXBlIjogIkZlYXR1cmUifSwgeyJnZW9tZXRyeSI6IHsiY29vcmRpbmF0ZXMiOiBbW1tbLTc1LjM5NzY1OSwgMzguMDEzNDk3XSwgWy03NS4yNDQzMDQsIDM4LjAyOTkyOF0sIFstNzUuMzc1NzUxLCAzNy44NjAxNDJdLCBbLTc1LjUxMjY3NCwgMzcuNzk5ODk2XSwgWy03NS41OTQ4MjgsIDM3LjU2OTg2NV0sIFstNzUuODAyOTUyLCAzNy4xOTc0MzNdLCBbLTc1Ljk3MjczNywgMzcuMTIwNzU1XSwgWy03Ni4wMjc1MDcsIDM3LjI1NzY3OV0sIFstNzUuOTM5ODc2LCAzNy41NjQzODhdLCBbLTc1LjY3MTUwNiwgMzcuOTUzMjVdLCBbLTc1LjM5NzY1OSwgMzguMDEzNDk3XV1dLCBbW1stNzYuMDE2NTUzLCAzNy45NTMyNV0sIFstNzUuOTk0NjQ1LCAzNy45NTMyNV0sIFstNzYuMDQzOTM4LCAzNy45NTMyNV0sIFstNzYuMDE2NTUzLCAzNy45NTMyNV1dXSwgW1tbLTc4LjM0OTcyOSwgMzkuNDY0ODg2XSwgWy03Ny44Mjk0MiwgMzkuMTMwNzkzXSwgWy03Ny43MTk4ODEsIDM5LjMyMjQ4NV0sIFstNzcuNTY2NTI3LCAzOS4zMDYwNTVdLCBbLTc3LjQ1Njk4OCwgMzkuMjIzOTAxXSwgWy03Ny40NTY5ODgsIDM5LjA3NjAyM10sIFstNzcuMjQ4ODY0LCAzOS4wMjY3MzFdLCBbLTc3LjExNzQxOCwgMzguOTMzNjIzXSwgWy03Ny4wNDA3NDEsIDM4Ljc5MTIyMl0sIFstNzcuMTI4MzcyLCAzOC42MzIzOTFdLCBbLTc3LjI0ODg2NCwgMzguNTg4NTc1XSwgWy03Ny4zMjU1NDIsIDM4LjQ0NjE3NV0sIFstNzcuMjgxNzI2LCAzOC4zNDIxMTNdLCBbLTc3LjAxMzM1NiwgMzguMzc0OTc1XSwgWy03Ni45NjQwNjQsIDM4LjIxNjE0NF0sIFstNzYuNjEzNTM5LCAzOC4xNTA0Ml0sIFstNzYuNTE0OTU0LCAzOC4wMjQ0NTFdLCBbLTc2LjIzNTYzMSwgMzcuODg3NTI3XSwgWy03Ni4zNjE2LCAzNy42MDgyMDNdLCBbLTc2LjI0NjU4NCwgMzcuMzg5MTI2XSwgWy03Ni4zODM1MDgsIDM3LjI4NTA2NF0sIFstNzYuMzk5OTM5LCAzNy4xNTkwOTRdLCBbLTc2LjI3Mzk2OSwgMzcuMDgyNDE3XSwgWy03Ni40MTA4OTMsIDM2Ljk2MTkyNF0sIFstNzYuNjE5MDE2LCAzNy4xMjA3NTVdLCBbLTc2LjY2ODMwOSwgMzcuMDY1OTg2XSwgWy03Ni40ODc1NywgMzYuOTUwOTddLCBbLTc1Ljk5NDY0NSwgMzYuOTIzNTg2XSwgWy03NS44Njg2NzYsIDM2LjU1MTE1NF0sIFstNzkuNTEwODQxLCAzNi41NDAyXSwgWy04MC4yOTQwNDMsIDM2LjU0NTY3N10sIFstODAuOTc4NjYxLCAzNi41NjIxMDhdLCBbLTgxLjY3OTcwOSwgMzYuNTg5NDkyXSwgWy04My42NzMzMTYsIDM2LjYwMDQ0Nl0sIFstODMuMTM2NTc1LCAzNi43NDI4NDddLCBbLTgzLjA3MDg1MiwgMzYuODUyMzg1XSwgWy04Mi44NzkxNTksIDM2Ljg5MDcyNF0sIFstODIuODY4MjA1LCAzNi45NzgzNTVdLCBbLTgyLjcyMDMyOCwgMzcuMDQ0MDc4XSwgWy04Mi43MjAzMjgsIDM3LjEyMDc1NV0sIFstODIuMzUzMzczLCAzNy4yNjg2MzNdLCBbLTgxLjk2OTk4NywgMzcuNTM3MDAzXSwgWy04MS45ODY0MTgsIDM3LjQ1NDg0OV0sIFstODEuODQ5NDk0LCAzNy4yODUwNjRdLCBbLTgxLjY3OTcwOSwgMzcuMjAyOTFdLCBbLTgxLjU1Mzc0LCAzNy4yMDgzODddLCBbLTgxLjM2MjA0NywgMzcuMzM5ODMzXSwgWy04MS4yMjUxMjMsIDM3LjIzNTc3MV0sIFstODAuOTY3NzA3LCAzNy4yOTA1NDFdLCBbLTgwLjUxMzEyMSwgMzcuNDgyMjM0XSwgWy04MC40NzQ3ODIsIDM3LjQyMTk4N10sIFstODAuMjk5NTIsIDM3LjUwOTYxOF0sIFstODAuMjk0MDQzLCAzNy42OTAzNTddLCBbLTgwLjE4NDUwNSwgMzcuODQ5MTg5XSwgWy03OS45OTgyODksIDM3Ljk5NzA2Nl0sIFstNzkuOTIxNjExLCAzOC4xNzc4MDVdLCBbLTc5LjcyNDQ0MiwgMzguMzY0MDIxXSwgWy03OS42NDc3NjQsIDM4LjU5NDA1Ml0sIFstNzkuNDc3OTc5LCAzOC40NTcxMjldLCBbLTc5LjMxMzY3MSwgMzguNDEzMzEzXSwgWy03OS4yMDk2MDksIDM4LjQ5NTQ2N10sIFstNzguOTk2MDA4LCAzOC44NTE0NjldLCBbLTc4Ljg3MDAzOSwgMzguNzYzODM4XSwgWy03OC40MDQ0OTksIDM5LjE2OTEzMV0sIFstNzguMzQ5NzI5LCAzOS40NjQ4ODZdXV1dLCAidHlwZSI6ICJNdWx0aVBvbHlnb24ifSwgImlkIjogIlZBIiwgInByb3BlcnRpZXMiOiB7ImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJWaXJnaW5pYSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNhZGRkOGUiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbW1stMTE3LjAzMzM1OSwgNDkuMDAwMjM5XSwgWy0xMTcuMDQ0MzEzLCA0Ny43NjI0NTFdLCBbLTExNy4wMzg4MzYsIDQ2LjQyNjA3N10sIFstMTE3LjA1NTI2NywgNDYuMzQzOTIzXSwgWy0xMTYuOTIzODIsIDQ2LjE2ODY2MV0sIFstMTE2LjkxODM0NCwgNDUuOTkzMzk5XSwgWy0xMTguOTg4NjI3LCA0NS45OTg4NzZdLCBbLTExOS4xMjU1NTEsIDQ1LjkzMzE1M10sIFstMTE5LjUyNTM2NywgNDUuOTExMjQ1XSwgWy0xMTkuOTYzNTIyLCA0NS44MjM2MTRdLCBbLTEyMC4yMDk5ODUsIDQ1LjcyNTAyOV0sIFstMTIwLjUwNTczOSwgNDUuNjk3NjQ0XSwgWy0xMjAuNjM3MTg2LCA0NS43NDY5MzddLCBbLTEyMS4xODQ4OCwgNDUuNjA0NTM2XSwgWy0xMjEuMjE3NzQyLCA0NS42NzAyNTldLCBbLTEyMS41MzU0MDQsIDQ1LjcyNTAyOV0sIFstMTIxLjgwOTI1MSwgNDUuNzA4NTk4XSwgWy0xMjIuMjQ3NDA3LCA0NS41NDk3NjddLCBbLTEyMi43NjIyMzksIDQ1LjY1OTMwNV0sIFstMTIyLjgxMTUzMSwgNDUuOTYwNTM3XSwgWy0xMjIuOTA0NjM5LCA0Ni4wODEwM10sIFstMTIzLjExODI0LCA0Ni4xODUwOTJdLCBbLTEyMy4yMTEzNDgsIDQ2LjE3NDEzOF0sIFstMTIzLjM3MDE3OSwgNDYuMTQ2NzUzXSwgWy0xMjMuNTQ1NDQxLCA0Ni4yNjE3NjldLCBbLTEyMy43MjYxOCwgNDYuMzAwMTA4XSwgWy0xMjMuODc0MDU4LCA0Ni4yMzk4NjFdLCBbLTEyNC4wNjU3NTEsIDQ2LjMyNzQ5Ml0sIFstMTI0LjAyNzQxMiwgNDYuNDY0NDE2XSwgWy0xMjMuODk1OTY2LCA0Ni41MzU2MTZdLCBbLTEyNC4wOTg2MTIsIDQ2Ljc0Mzc0XSwgWy0xMjQuMjM1NTM2LCA0Ny4yODU5NTddLCBbLTEyNC4zMTc2OSwgNDcuMzU3MTU3XSwgWy0xMjQuNDI3MjI5LCA0Ny43NDA1NDNdLCBbLTEyNC42MjQzOTksIDQ3Ljg4ODQyXSwgWy0xMjQuNzA2NTUzLCA0OC4xODQxNzVdLCBbLTEyNC41OTcwMTQsIDQ4LjM4MTM0NV0sIFstMTI0LjM5NDM2NywgNDguMjg4MjM3XSwgWy0xMjMuOTgzNTk3LCA0OC4xNjIyNjddLCBbLTEyMy43MDQyNzMsIDQ4LjE2Nzc0NF0sIFstMTIzLjQyNDk0OSwgNDguMTE4NDUyXSwgWy0xMjMuMTYyMDU2LCA0OC4xNjc3NDRdLCBbLTEyMy4wMzYwODYsIDQ4LjA4MDExM10sIFstMTIyLjgwMDU3OCwgNDguMDg1NTldLCBbLTEyMi42MzYyNjksIDQ3Ljg2NjUxMl0sIFstMTIyLjUxNTc3NywgNDcuODgyOTQzXSwgWy0xMjIuNDkzODY5LCA0Ny41ODcxODldLCBbLTEyMi40MjI2NjksIDQ3LjMxODgxOF0sIFstMTIyLjMyNDA4NCwgNDcuMzQ2MjAzXSwgWy0xMjIuNDIyNjY5LCA0Ny41NzYyMzVdLCBbLTEyMi4zOTUyODQsIDQ3LjgwMDc4OV0sIFstMTIyLjIzMDk3NiwgNDguMDMwODIxXSwgWy0xMjIuMzYyNDIyLCA0OC4xMjM5MjldLCBbLTEyMi4zNzMzNzYsIDQ4LjI4ODIzN10sIFstMTIyLjQ3MTk2MSwgNDguNDY4OTc2XSwgWy0xMjIuNDIyNjY5LCA0OC42MDA0MjJdLCBbLTEyMi40ODgzOTIsIDQ4Ljc1Mzc3N10sIFstMTIyLjY0NzIyMywgNDguNzc1Njg1XSwgWy0xMjIuNzk1MTAxLCA0OC44OTA3XSwgWy0xMjIuNzU2NzYyLCA0OS4wMDAyMzldLCBbLTExNy4wMzMzNTksIDQ5LjAwMDIzOV1dXSwgW1tbLTEyMi43MTg0MjMsIDQ4LjMxMDE0NV0sIFstMTIyLjU4Njk3NywgNDguMzUzOTZdLCBbLTEyMi42MDg4ODUsIDQ4LjE1MTMxM10sIFstMTIyLjc2NzcxNiwgNDguMjI3OTkxXSwgWy0xMjIuNzE4NDIzLCA0OC4zMTAxNDVdXV0sIFtbWy0xMjMuMDI1MTMyLCA0OC41ODM5OTJdLCBbLTEyMi45MTU1OTMsIDQ4LjcxNTQzOF0sIFstMTIyLjc2NzcxNiwgNDguNTU2NjA3XSwgWy0xMjIuODExNTMxLCA0OC40MTk2ODNdLCBbLTEyMy4wNDE1NjMsIDQ4LjQ1ODAyMl0sIFstMTIzLjAyNTEzMiwgNDguNTgzOTkyXV1dXSwgInR5cGUiOiAiTXVsdGlQb2x5Z29uIn0sICJpZCI6ICJXQSIsICJwcm9wZXJ0aWVzIjogeyJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiV2FzaGluZ3RvbiIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiM3OGM2NzkiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWy04MC41MTg1OTgsIDQwLjYzNjk1MV0sIFstODAuNTE4NTk4LCAzOS43MjIzMDJdLCBbLTc5LjQ3Nzk3OSwgMzkuNzIyMzAyXSwgWy03OS40ODg5MzMsIDM5LjIwNzQ3XSwgWy03OS4yOTE3NjMsIDM5LjMwMDU3OF0sIFstNzkuMDk0NTkzLCAzOS40NzAzNjNdLCBbLTc4Ljk2MzE0NywgMzkuNDM3NTAxXSwgWy03OC43NjU5NzcsIDM5LjU4NTM3OV0sIFstNzguNDcwMjIyLCAzOS41MTQxNzhdLCBbLTc4LjQzMTg4NCwgMzkuNjIzNzE3XSwgWy03OC4yNjc1NzUsIDM5LjYxODI0XSwgWy03OC4xNzQ0NjcsIDM5LjY5NDkxN10sIFstNzguMDA0NjgyLCAzOS42MDE4MDldLCBbLTc3LjgzNDg5NywgMzkuNjAxODA5XSwgWy03Ny43MTk4ODEsIDM5LjMyMjQ4NV0sIFstNzcuODI5NDIsIDM5LjEzMDc5M10sIFstNzguMzQ5NzI5LCAzOS40NjQ4ODZdLCBbLTc4LjQwNDQ5OSwgMzkuMTY5MTMxXSwgWy03OC44NzAwMzksIDM4Ljc2MzgzOF0sIFstNzguOTk2MDA4LCAzOC44NTE0NjldLCBbLTc5LjIwOTYwOSwgMzguNDk1NDY3XSwgWy03OS4zMTM2NzEsIDM4LjQxMzMxM10sIFstNzkuNDc3OTc5LCAzOC40NTcxMjldLCBbLTc5LjY0Nzc2NCwgMzguNTk0MDUyXSwgWy03OS43MjQ0NDIsIDM4LjM2NDAyMV0sIFstNzkuOTIxNjExLCAzOC4xNzc4MDVdLCBbLTc5Ljk5ODI4OSwgMzcuOTk3MDY2XSwgWy04MC4xODQ1MDUsIDM3Ljg0OTE4OV0sIFstODAuMjk0MDQzLCAzNy42OTAzNTddLCBbLTgwLjI5OTUyLCAzNy41MDk2MThdLCBbLTgwLjQ3NDc4MiwgMzcuNDIxOTg3XSwgWy04MC41MTMxMjEsIDM3LjQ4MjIzNF0sIFstODAuOTY3NzA3LCAzNy4yOTA1NDFdLCBbLTgxLjIyNTEyMywgMzcuMjM1NzcxXSwgWy04MS4zNjIwNDcsIDM3LjMzOTgzM10sIFstODEuNTUzNzQsIDM3LjIwODM4N10sIFstODEuNjc5NzA5LCAzNy4yMDI5MV0sIFstODEuODQ5NDk0LCAzNy4yODUwNjRdLCBbLTgxLjk4NjQxOCwgMzcuNDU0ODQ5XSwgWy04MS45Njk5ODcsIDM3LjUzNzAwM10sIFstODIuMTAxNDM0LCAzNy41NTM0MzRdLCBbLTgyLjI5MzEyNywgMzcuNjY4NDQ5XSwgWy04Mi4zNDI0MTksIDM3Ljc4MzQ2NV0sIFstODIuNTAxMjUsIDM3LjkzMTM0M10sIFstODIuNjIxNzQzLCAzOC4xMjMwMzZdLCBbLTgyLjU5NDM1OCwgMzguNDI0MjY3XSwgWy04Mi4zMzE0NjUsIDM4LjQ0NjE3NV0sIFstODIuMjkzMTI3LCAzOC41Nzc2MjJdLCBbLTgyLjE3MjYzNCwgMzguNjMyMzkxXSwgWy04Mi4yMjE5MjYsIDM4Ljc4NTc0NV0sIFstODIuMDM1NzEsIDM5LjAyNjczMV0sIFstODEuODg3ODMzLCAzOC44NzMzNzZdLCBbLTgxLjc4Mzc3MSwgMzguOTY2NDg0XSwgWy04MS44MTExNTYsIDM5LjA4MTVdLCBbLTgxLjY4NTE4NiwgMzkuMjczMTkzXSwgWy04MS41NzAxNywgMzkuMjY3NzE2XSwgWy04MS40NTUxNTUsIDM5LjQxMDExN10sIFstODEuMzQ1NjE2LCAzOS4zNDQzOTNdLCBbLTgxLjIxOTY0NiwgMzkuMzg4MjA5XSwgWy04MC44MzA3ODMsIDM5LjcxMTM0OF0sIFstODAuNzM3Njc1LCA0MC4wNzgzMDNdLCBbLTgwLjYwMDc1MiwgNDAuMzE5Mjg5XSwgWy04MC41OTUyNzUsIDQwLjQ3MjY0M10sIFstODAuNjY2NDc1LCA0MC41ODIxODJdLCBbLTgwLjUxODU5OCwgNDAuNjM2OTUxXV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJXViIsICJwcm9wZXJ0aWVzIjogeyJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiV2VzdCBWaXJnaW5pYSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiM3OGM2NzkiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWy05MC40MTU0MjksIDQ2LjU2ODQ3OF0sIFstOTAuMjI5MjEzLCA0Ni41MDgyMzFdLCBbLTkwLjExOTY3NCwgNDYuMzM4NDQ2XSwgWy04OS4wOTAwMSwgNDYuMTM1Nzk5XSwgWy04OC42NjI4MDgsIDQ1Ljk4NzkyMl0sIFstODguNTMxMzYyLCA0Ni4wMjA3ODRdLCBbLTg4LjEwNDE2LCA0NS45MjIxOTldLCBbLTg3Ljk4OTE0NSwgNDUuNzk2MjI5XSwgWy04Ny43ODEwMjEsIDQ1LjY3NTczNl0sIFstODcuNzkxOTc1LCA0NS41MDA0NzRdLCBbLTg3Ljg4NTA4MywgNDUuMzYzNTUxXSwgWy04Ny42NDk1NzQsIDQ1LjM0MTY0M10sIFstODcuNzQyNjgyLCA0NS4xOTkyNDNdLCBbLTg3LjU4OTMyOCwgNDUuMDk1MTgxXSwgWy04Ny42Mjc2NjYsIDQ0Ljk3NDY4OF0sIFstODcuODE5MzU5LCA0NC45NTI3OF0sIFstODcuOTgzNjY4LCA0NC43MjI3NDldLCBbLTg4LjA0MzkxNCwgNDQuNTYzOTE3XSwgWy04Ny45Mjg4OTgsIDQ0LjUzNjUzM10sIFstODcuNzc1NTQ0LCA0NC42NDA1OTVdLCBbLTg3LjYxMTIzNiwgNDQuODM3NzY0XSwgWy04Ny40MDMxMTIsIDQ0LjkxNDQ0Ml0sIFstODcuMjM4ODA0LCA0NS4xNjYzODFdLCBbLTg3LjAzMDY4LCA0NS4yMjExNV0sIFstODcuMDQ3MTExLCA0NS4wODk3MDRdLCBbLTg3LjE4OTUxMSwgNDQuOTY5MjExXSwgWy04Ny40Njg4MzUsIDQ0LjU1Mjk2NF0sIFstODcuNTQ1NTEyLCA0NC4zMjI5MzJdLCBbLTg3LjU0MDAzNSwgNDQuMTU4NjI0XSwgWy04Ny42NDQwOTcsIDQ0LjEwMzg1NF0sIFstODcuNzM3MjA1LCA0My44NzkzXSwgWy04Ny43MDQzNDQsIDQzLjY4NzYwN10sIFstODcuNzkxOTc1LCA0My41NjE2MzddLCBbLTg3LjkxMjQ2NywgNDMuMjQ5NDUyXSwgWy04Ny44ODUwODMsIDQzLjAwMjk4OV0sIFstODcuNzY0NTksIDQyLjc4MzkxMl0sIFstODcuODAyOTI5LCA0Mi40OTM2MzRdLCBbLTg4Ljc4ODc3OCwgNDIuNDkzNjM0XSwgWy05MC42Mzk5ODQsIDQyLjUxMDA2NV0sIFstOTAuNzExMTg0LCA0Mi42MzYwMzRdLCBbLTkxLjA2NzE4NSwgNDIuNzUxMDVdLCBbLTkxLjE0Mzg2MiwgNDIuOTA5ODgxXSwgWy05MS4xNzY3MjQsIDQzLjEzNDQzNl0sIFstOTEuMDU2MjMxLCA0My4yNTQ5MjldLCBbLTkxLjIwNDEwOSwgNDMuMzUzNTE0XSwgWy05MS4yMTUwNjIsIDQzLjUwMTM5MV0sIFstOTEuMjY5ODMyLCA0My42MTY0MDddLCBbLTkxLjI0MjQ0NywgNDMuNzc1MjM4XSwgWy05MS40MzQxNCwgNDMuOTk0MzE2XSwgWy05MS41OTI5NzEsIDQ0LjAzMjY1NF0sIFstOTEuODc3NzcyLCA0NC4yMDI0MzldLCBbLTkxLjkyNzA2NSwgNDQuMzMzODg2XSwgWy05Mi4yMzM3NzMsIDQ0LjQ0MzQyNV0sIFstOTIuMzM3ODM1LCA0NC41NTI5NjRdLCBbLTkyLjU0NTk1OSwgNDQuNTY5Mzk0XSwgWy05Mi44MDg4NTIsIDQ0Ljc1MDEzM10sIFstOTIuNzM3NjUyLCA0NS4xMTcwODhdLCBbLTkyLjc1OTU2LCA0NS4yODY4NzRdLCBbLTkyLjY0NDU0NCwgNDUuNDQwMjI4XSwgWy05Mi43NzA1MTMsIDQ1LjU2NjE5OF0sIFstOTIuODg1NTI5LCA0NS41NzcxNTFdLCBbLTkyLjg2OTA5OCwgNDUuNzE5NTUyXSwgWy05Mi42MzkwNjcsIDQ1LjkzMzE1M10sIFstOTIuMzU0MjY2LCA0Ni4wMTUzMDddLCBbLTkyLjI5NDAyLCA0Ni4wNzU1NTNdLCBbLTkyLjI5NDAyLCA0Ni42NjcwNjNdLCBbLTkyLjA5MTM3MywgNDYuNzQ5MjE3XSwgWy05Mi4wMTQ2OTYsIDQ2LjcwNTQwMV0sIFstOTEuNzkwMTQxLCA0Ni42OTQ0NDddLCBbLTkxLjA5NDU3LCA0Ni44NjQyMzJdLCBbLTkwLjgzNzE1NCwgNDYuOTU3MzRdLCBbLTkwLjc0OTUyMiwgNDYuODg2MTRdLCBbLTkwLjg4NjQ0NiwgNDYuNzU0Njk0XSwgWy05MC41NTc4MywgNDYuNTg0OTA4XSwgWy05MC40MTU0MjksIDQ2LjU2ODQ3OF1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiV0kiLCAicHJvcGVydGllcyI6IHsiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIldpc2NvbnNpbiIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiM3OGM2NzkiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWy0xMDkuMDgwODQyLCA0NS4wMDIwNzNdLCBbLTEwNS45MTUxNywgNDUuMDAyMDczXSwgWy0xMDQuMDU4NDg4LCA0NC45OTY1OTZdLCBbLTEwNC4wNTMwMTEsIDQzLjAwMjk4OV0sIFstMTA0LjA1MzAxMSwgNDEuMDAzOTA2XSwgWy0xMDUuNzI4OTU0LCA0MC45OTg0MjldLCBbLTEwNy45MTk3MzEsIDQxLjAwMzkwNl0sIFstMTA5LjA0Nzk4LCA0MC45OTg0MjldLCBbLTExMS4wNDcwNjMsIDQwLjk5ODQyOV0sIFstMTExLjA0NzA2MywgNDIuMDAwNzA5XSwgWy0xMTEuMDQ3MDYzLCA0NC40NzYyODZdLCBbLTExMS4wNTI1NCwgNDUuMDAyMDczXSwgWy0xMDkuMDgwODQyLCA0NS4wMDIwNzNdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIldZIiwgInByb3BlcnRpZXMiOiB7ImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJXeW9taW5nIiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2Q5ZjBhMyIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn1dLCAidHlwZSI6ICJGZWF0dXJlQ29sbGVjdGlvbiJ9CiAgICAgICAgICAgIAogICAgICAgICAgICApLmFkZFRvKGNob3JvcGxldGhfODNkYmNmYTFiOTA4NGQ1ZmI1ZTgwOTgzNGI3Mzc5ZWMpOwogICAgICAgIGdlb19qc29uX2NiODA5YTgwOGI4NTQ1ZWM4MTExYjRiYTdjMDg5N2ViLnNldFN0eWxlKGZ1bmN0aW9uKGZlYXR1cmUpIHtyZXR1cm4gZmVhdHVyZS5wcm9wZXJ0aWVzLnN0eWxlO30pOwogICAgICAgIAogICAgCiAgICB2YXIgY29sb3JfbWFwXzg2NjcwOTNmYTY1MzRkZTFiYzdlZjJhODBhNmI2NmVmID0ge307CgogICAgCiAgICBjb2xvcl9tYXBfODY2NzA5M2ZhNjUzNGRlMWJjN2VmMmE4MGE2YjY2ZWYuY29sb3IgPSBkMy5zY2FsZS50aHJlc2hvbGQoKQogICAgICAgICAgICAgIC5kb21haW4oWzMuMiwgMy4yMTQyMjg0NTY5MTM4MjgsIDMuMjI4NDU2OTEzODI3NjU1MywgMy4yNDI2ODUzNzA3NDE0ODMsIDMuMjU2OTEzODI3NjU1MzExLCAzLjI3MTE0MjI4NDU2OTEzODIsIDMuMjg1MzcwNzQxNDgyOTY2LCAzLjI5OTU5OTE5ODM5Njc5NCwgMy4zMTM4Mjc2NTUzMTA2MjE2LCAzLjMyODA1NjExMjIyNDQ0OSwgMy4zNDIyODQ1NjkxMzgyNzY4LCAzLjM1NjUxMzAyNjA1MjEwNDYsIDMuMzcwNzQxNDgyOTY1OTMyLCAzLjM4NDk2OTkzOTg3OTc1OTcsIDMuMzk5MTk4Mzk2NzkzNTg3NSwgMy40MTM0MjY4NTM3MDc0MTUsIDMuNDI3NjU1MzEwNjIxMjQyNiwgMy40NDE4ODM3Njc1MzUwNzA0LCAzLjQ1NjExMjIyNDQ0ODg5NzgsIDMuNDcwMzQwNjgxMzYyNzI1NiwgMy40ODQ1NjkxMzgyNzY1NTMzLCAzLjQ5ODc5NzU5NTE5MDM4MSwgMy41MTMwMjYwNTIxMDQyMDg1LCAzLjUyNzI1NDUwOTAxODAzNjMsIDMuNTQxNDgyOTY1OTMxODY0LCAzLjU1NTcxMTQyMjg0NTY5MTQsIDMuNTY5OTM5ODc5NzU5NTE5LCAzLjU4NDE2ODMzNjY3MzM0NywgMy41OTgzOTY3OTM1ODcxNzUsIDMuNjEyNjI1MjUwNTAxMDAyLCAzLjYyNjg1MzcwNzQxNDgzLCAzLjY0MTA4MjE2NDMyODY1NzcsIDMuNjU1MzEwNjIxMjQyNDg1LCAzLjY2OTUzOTA3ODE1NjMxMywgMy42ODM3Njc1MzUwNzAxNDA3LCAzLjY5Nzk5NTk5MTk4Mzk2OCwgMy43MTIyMjQ0NDg4OTc3OTYsIDMuNzI2NDUyOTA1ODExNjIzNiwgMy43NDA2ODEzNjI3MjU0NTEsIDMuNzU0OTA5ODE5NjM5Mjc4NywgMy43NjkxMzgyNzY1NTMxMDY1LCAzLjc4MzM2NjczMzQ2NjkzNDMsIDMuNzk3NTk1MTkwMzgwNzYxNywgMy44MTE4MjM2NDcyOTQ1ODk0LCAzLjgyNjA1MjEwNDIwODQxNzIsIDMuODQwMjgwNTYxMTIyMjQ0NiwgMy44NTQ1MDkwMTgwMzYwNzI0LCAzLjg2ODczNzQ3NDk0OTksIDMuODgyOTY1OTMxODYzNzI3NSwgMy44OTcxOTQzODg3Nzc1NTUzLCAzLjkxMTQyMjg0NTY5MTM4MywgMy45MjU2NTEzMDI2MDUyMTA0LCAzLjkzOTg3OTc1OTUxOTAzODIsIDMuOTU0MTA4MjE2NDMyODY2LCAzLjk2ODMzNjY3MzM0NjY5NCwgMy45ODI1NjUxMzAyNjA1MjEsIDMuOTk2NzkzNTg3MTc0MzQ5LCA0LjAxMTAyMjA0NDA4ODE3NiwgNC4wMjUyNTA1MDEwMDIwMDQsIDQuMDM5NDc4OTU3OTE1ODMyLCA0LjA1MzcwNzQxNDgyOTY2LCA0LjA2NzkzNTg3MTc0MzQ4NzUsIDQuMDgyMTY0MzI4NjU3MzE1LCA0LjA5NjM5Mjc4NTU3MTE0MiwgNC4xMTA2MjEyNDI0ODQ5NywgNC4xMjQ4NDk2OTkzOTg3OTgsIDQuMTM5MDc4MTU2MzEyNjI1NSwgNC4xNTMzMDY2MTMyMjY0NTMsIDQuMTY3NTM1MDcwMTQwMjgxLCA0LjE4MTc2MzUyNzA1NDEwOSwgNC4xOTU5OTE5ODM5Njc5MzYsIDQuMjEwMjIwNDQwODgxNzY0LCA0LjIyNDQ0ODg5Nzc5NTU5MSwgNC4yMzg2NzczNTQ3MDk0MTksIDQuMjUyOTA1ODExNjIzMjQ3LCA0LjI2NzEzNDI2ODUzNzA3NCwgNC4yODEzNjI3MjU0NTA5MDMsIDQuMjk1NTkxMTgyMzY0NzI5NSwgNC4zMDk4MTk2MzkyNzg1NTcsIDQuMzI0MDQ4MDk2MTkyMzg1LCA0LjMzODI3NjU1MzEwNjIxMywgNC4zNTI1MDUwMTAwMjAwNDEsIDQuMzY2NzMzNDY2OTMzODY3NSwgNC4zODA5NjE5MjM4NDc2OTYsIDQuMzk1MTkwMzgwNzYxNTIzLCA0LjQwOTQxODgzNzY3NTM1MSwgNC40MjM2NDcyOTQ1ODkxNzksIDQuNDM3ODc1NzUxNTAzMDA2NSwgNC40NTIxMDQyMDg0MTY4MzQsIDQuNDY2MzMyNjY1MzMwNjYxLCA0LjQ4MDU2MTEyMjI0NDQ4OSwgNC40OTQ3ODk1NzkxNTgzMTcsIDQuNTA5MDE4MDM2MDcyMTQ1LCA0LjUyMzI0NjQ5Mjk4NTk3MiwgNC41Mzc0NzQ5NDk4OTk4LCA0LjU1MTcwMzQwNjgxMzYyOCwgNC41NjU5MzE4NjM3Mjc0NTUsIDQuNTgwMTYwMzIwNjQxMjgzLCA0LjU5NDM4ODc3NzU1NTExLCA0LjYwODYxNzIzNDQ2ODkzOCwgNC42MjI4NDU2OTEzODI3NjYsIDQuNjM3MDc0MTQ4Mjk2NTkzLCA0LjY1MTMwMjYwNTIxMDQyMiwgNC42NjU1MzEwNjIxMjQyNDg1LCA0LjY3OTc1OTUxOTAzODA3NiwgNC42OTM5ODc5NzU5NTE5MDQsIDQuNzA4MjE2NDMyODY1NzMyLCA0LjcyMjQ0NDg4OTc3OTU2LCA0LjczNjY3MzM0NjY5MzM4NywgNC43NTA5MDE4MDM2MDcyMTUsIDQuNzY1MTMwMjYwNTIxMDQyLCA0Ljc3OTM1ODcxNzQzNDg3LCA0Ljc5MzU4NzE3NDM0ODY5OCwgNC44MDc4MTU2MzEyNjI1MjU1LCA0LjgyMjA0NDA4ODE3NjM1MywgNC44MzYyNzI1NDUwOTAxOCwgNC44NTA1MDEwMDIwMDQwMDgsIDQuODY0NzI5NDU4OTE3ODM2LCA0Ljg3ODk1NzkxNTgzMTY2NCwgNC44OTMxODYzNzI3NDU0OTEsIDQuOTA3NDE0ODI5NjU5MzE5LCA0LjkyMTY0MzI4NjU3MzE0NywgNC45MzU4NzE3NDM0ODY5NzQsIDQuOTUwMTAwMjAwNDAwODAyLCA0Ljk2NDMyODY1NzMxNDYyOTUsIDQuOTc4NTU3MTE0MjI4NDU3LCA0Ljk5Mjc4NTU3MTE0MjI4NSwgNS4wMDcwMTQwMjgwNTYxMTIsIDUuMDIxMjQyNDg0OTY5OTQxLCA1LjAzNTQ3MDk0MTg4Mzc2NzUsIDUuMDQ5Njk5Mzk4Nzk3NTk1LCA1LjA2MzkyNzg1NTcxMTQyMywgNS4wNzgxNTYzMTI2MjUyNTEsIDUuMDkyMzg0NzY5NTM5MDc5LCA1LjEwNjYxMzIyNjQ1MjkwNjUsIDUuMTIwODQxNjgzMzY2NzM0LCA1LjEzNTA3MDE0MDI4MDU2MSwgNS4xNDkyOTg1OTcxOTQzODksIDUuMTYzNTI3MDU0MTA4MjE3LCA1LjE3Nzc1NTUxMTAyMjA0NSwgNS4xOTE5ODM5Njc5MzU4NzIsIDUuMjA2MjEyNDI0ODQ5Njk5LCA1LjIyMDQ0MDg4MTc2MzUyOCwgNS4yMzQ2NjkzMzg2NzczNTUsIDUuMjQ4ODk3Nzk1NTkxMTgzLCA1LjI2MzEyNjI1MjUwNTAxLCA1LjI3NzM1NDcwOTQxODgzOCwgNS4yOTE1ODMxNjYzMzI2NjYsIDUuMzA1ODExNjIzMjQ2NDkzLCA1LjMyMDA0MDA4MDE2MDMyMiwgNS4zMzQyNjg1MzcwNzQxNDg1LCA1LjM0ODQ5Njk5Mzk4Nzk3NiwgNS4zNjI3MjU0NTA5MDE4MDQsIDUuMzc2OTUzOTA3ODE1NjMyLCA1LjM5MTE4MjM2NDcyOTQ2LCA1LjQwNTQxMDgyMTY0MzI4NywgNS40MTk2MzkyNzg1NTcxMTUsIDUuNDMzODY3NzM1NDcwOTQyLCA1LjQ0ODA5NjE5MjM4NDc3LCA1LjQ2MjMyNDY0OTI5ODU5OCwgNS40NzY1NTMxMDYyMTI0MjUsIDUuNDkwNzgxNTYzMTI2MjUzLCA1LjUwNTAxMDAyMDA0MDA4LCA1LjUxOTIzODQ3Njk1MzkwOSwgNS41MzM0NjY5MzM4Njc3MzYsIDUuNTQ3Njk1MzkwNzgxNTYzLCA1LjU2MTkyMzg0NzY5NTM5MSwgNS41NzYxNTIzMDQ2MDkyMTgsIDUuNTkwMzgwNzYxNTIzMDQ3LCA1LjYwNDYwOTIxODQzNjg3NCwgNS42MTg4Mzc2NzUzNTA3MDIsIDUuNjMzMDY2MTMyMjY0NTI5NSwgNS42NDcyOTQ1ODkxNzgzNTYsIDUuNjYxNTIzMDQ2MDkyMTg1LCA1LjY3NTc1MTUwMzAwNjAxMiwgNS42ODk5Nzk5NTk5MTk4NCwgNS43MDQyMDg0MTY4MzM2Njc1LCA1LjcxODQzNjg3Mzc0NzQ5NSwgNS43MzI2NjUzMzA2NjEzMjMsIDUuNzQ2ODkzNzg3NTc1MTUsIDUuNzYxMTIyMjQ0NDg4OTc5LCA1Ljc3NTM1MDcwMTQwMjgwNiwgNS43ODk1NzkxNTgzMTY2MzMsIDUuODAzODA3NjE1MjMwNDYxLCA1LjgxODAzNjA3MjE0NDI4OSwgNS44MzIyNjQ1MjkwNTgxMTcsIDUuODQ2NDkyOTg1OTcxOTQ1LCA1Ljg2MDcyMTQ0Mjg4NTc3MiwgNS44NzQ5NDk4OTk3OTk1OTksIDUuODg5MTc4MzU2NzEzNDI3LCA1LjkwMzQwNjgxMzYyNzI1NSwgNS45MTc2MzUyNzA1NDEwODMsIDUuOTMxODYzNzI3NDU0OTEsIDUuOTQ2MDkyMTg0MzY4NzM4LCA1Ljk2MDMyMDY0MTI4MjU2NiwgNS45NzQ1NDkwOTgxOTYzOTMsIDUuOTg4Nzc3NTU1MTEwMjIxLCA2LjAwMzAwNjAxMjAyNDA0ODUsIDYuMDE3MjM0NDY4OTM3ODc2LCA2LjAzMTQ2MjkyNTg1MTcwNCwgNi4wNDU2OTEzODI3NjU1MzEsIDYuMDU5OTE5ODM5Njc5MzYsIDYuMDc0MTQ4Mjk2NTkzMTg3LCA2LjA4ODM3Njc1MzUwNzAxNCwgNi4xMDI2MDUyMTA0MjA4NDIsIDYuMTE2ODMzNjY3MzM0NjY5LCA2LjEzMTA2MjEyNDI0ODQ5OCwgNi4xNDUyOTA1ODExNjIzMjUsIDYuMTU5NTE5MDM4MDc2MTUzLCA2LjE3Mzc0NzQ5NDk4OTk4LCA2LjE4Nzk3NTk1MTkwMzgwOCwgNi4yMDIyMDQ0MDg4MTc2MzYsIDYuMjE2NDMyODY1NzMxNDYzLCA2LjIzMDY2MTMyMjY0NTI5MSwgNi4yNDQ4ODk3Nzk1NTkxMTgsIDYuMjU5MTE4MjM2NDcyOTQ3LCA2LjI3MzM0NjY5MzM4Njc3NCwgNi4yODc1NzUxNTAzMDA2MDIsIDYuMzAxODAzNjA3MjE0NDI5NCwgNi4zMTYwMzIwNjQxMjgyNTYsIDYuMzMwMjYwNTIxMDQyMDg1LCA2LjM0NDQ4ODk3Nzk1NTkxMiwgNi4zNTg3MTc0MzQ4Njk3NCwgNi4zNzI5NDU4OTE3ODM1Njc1LCA2LjM4NzE3NDM0ODY5NzM5NSwgNi40MDE0MDI4MDU2MTEyMjMsIDYuNDE1NjMxMjYyNTI1MDUsIDYuNDI5ODU5NzE5NDM4ODc5LCA2LjQ0NDA4ODE3NjM1MjcwNiwgNi40NTgzMTY2MzMyNjY1MzMsIDYuNDcyNTQ1MDkwMTgwMzYxLCA2LjQ4Njc3MzU0NzA5NDE4OSwgNi41MDEwMDIwMDQwMDgwMTcsIDYuNTE1MjMwNDYwOTIxODQ0LCA2LjUyOTQ1ODkxNzgzNTY3MiwgNi41NDM2ODczNzQ3NDk0OTksIDYuNTU3OTE1ODMxNjYzMzI3LCA2LjU3MjE0NDI4ODU3NzE1NSwgNi41ODYzNzI3NDU0OTA5ODMsIDYuNjAwNjAxMjAyNDA0ODEsIDYuNjE0ODI5NjU5MzE4NjM3LCA2LjYyOTA1ODExNjIzMjQ2NiwgNi42NDMyODY1NzMxNDYyOTMsIDYuNjU3NTE1MDMwMDYwMTIxLCA2LjY3MTc0MzQ4Njk3Mzk0ODUsIDYuNjg1OTcxOTQzODg3Nzc2LCA2LjcwMDIwMDQwMDgwMTYwNCwgNi43MTQ0Mjg4NTc3MTU0MzEsIDYuNzI4NjU3MzE0NjI5MjYsIDYuNzQyODg1NzcxNTQzMDg2NSwgNi43NTcxMTQyMjg0NTY5MTQsIDYuNzcxMzQyNjg1MzcwNzQyLCA2Ljc4NTU3MTE0MjI4NDU2OSwgNi43OTk3OTk1OTkxOTgzOTgsIDYuODE0MDI4MDU2MTEyMjI1LCA2LjgyODI1NjUxMzAyNjA1MywgNi44NDI0ODQ5Njk5Mzk4OCwgNi44NTY3MTM0MjY4NTM3MDcsIDYuODcwOTQxODgzNzY3NTM2LCA2Ljg4NTE3MDM0MDY4MTM2MywgNi44OTkzOTg3OTc1OTUxOTEsIDYuOTEzNjI3MjU0NTA5MDE4LCA2LjkyNzg1NTcxMTQyMjg0NiwgNi45NDIwODQxNjgzMzY2NzQsIDYuOTU2MzEyNjI1MjUwNTAxLCA2Ljk3MDU0MTA4MjE2NDMyOSwgNi45ODQ3Njk1MzkwNzgxNTYsIDYuOTk4OTk3OTk1OTkxOTg0LCA3LjAxMzIyNjQ1MjkwNTgxMiwgNy4wMjc0NTQ5MDk4MTk2NCwgNy4wNDE2ODMzNjY3MzM0Njc1LCA3LjA1NTkxMTgyMzY0NzI5NCwgNy4wNzAxNDAyODA1NjExMjMsIDcuMDg0MzY4NzM3NDc0OTUsIDcuMDk4NTk3MTk0Mzg4Nzc4LCA3LjExMjgyNTY1MTMwMjYwNiwgNy4xMjcwNTQxMDgyMTY0MzMsIDcuMTQxMjgyNTY1MTMwMjYxLCA3LjE1NTUxMTAyMjA0NDA4OCwgNy4xNjk3Mzk0Nzg5NTc5MTcsIDcuMTgzOTY3OTM1ODcxNzQ0LCA3LjE5ODE5NjM5Mjc4NTU3MSwgNy4yMTI0MjQ4NDk2OTkzOTksIDcuMjI2NjUzMzA2NjEzMjI3LCA3LjI0MDg4MTc2MzUyNzA1NSwgNy4yNTUxMTAyMjA0NDA4ODMsIDcuMjY5MzM4Njc3MzU0NzA5NSwgNy4yODM1NjcxMzQyNjg1MzcsIDcuMjk3Nzk1NTkxMTgyMzY1LCA3LjMxMjAyNDA0ODA5NjE5MywgNy4zMjYyNTI1MDUwMTAwMiwgNy4zNDA0ODA5NjE5MjM4NDg1LCA3LjM1NDcwOTQxODgzNzY3NiwgNy4zNjg5Mzc4NzU3NTE1MDMsIDcuMzgzMTY2MzMyNjY1MzMxLCA3LjM5NzM5NDc4OTU3OTE1OSwgNy40MTE2MjMyNDY0OTI5ODY1LCA3LjQyNTg1MTcwMzQwNjgxNCwgNy40NDAwODAxNjAzMjA2NDIsIDcuNDU0MzA4NjE3MjM0NDY5LCA3LjQ2ODUzNzA3NDE0ODI5NywgNy40ODI3NjU1MzEwNjIxMjU1LCA3LjQ5Njk5Mzk4Nzk3NTk1MiwgNy41MTEyMjI0NDQ4ODk3OCwgNy41MjU0NTA5MDE4MDM2MDgsIDcuNTM5Njc5MzU4NzE3NDM1LCA3LjU1MzkwNzgxNTYzMTI2NCwgNy41NjgxMzYyNzI1NDUwOTA1LCA3LjU4MjM2NDcyOTQ1ODkxOCwgNy41OTY1OTMxODYzNzI3NDYsIDcuNjEwODIxNjQzMjg2NTczLCA3LjYyNTA1MDEwMDIwMDQwMiwgNy42MzkyNzg1NTcxMTQyMjksIDcuNjUzNTA3MDE0MDI4MDU2LCA3LjY2NzczNTQ3MDk0MTg4NCwgNy42ODE5NjM5Mjc4NTU3MTIsIDcuNjk2MTkyMzg0NzY5NTQsIDcuNzEwNDIwODQxNjgzMzY3NSwgNy43MjQ2NDkyOTg1OTcxOTUsIDcuNzM4ODc3NzU1NTExMDIyLCA3Ljc1MzEwNjIxMjQyNDg1LCA3Ljc2NzMzNDY2OTMzODY3OSwgNy43ODE1NjMxMjYyNTI1MDYsIDcuNzk1NzkxNTgzMTY2MzMzLCA3LjgxMDAyMDA0MDA4MDE2LCA3LjgyNDI0ODQ5Njk5Mzk4OCwgNy44Mzg0NzY5NTM5MDc4MTcsIDcuODUyNzA1NDEwODIxNjQ0LCA3Ljg2NjkzMzg2NzczNTQ3MSwgNy44ODExNjIzMjQ2NDkyOTksIDcuODk1MzkwNzgxNTYzMTI2LCA3LjkwOTYxOTIzODQ3Njk1NSwgNy45MjM4NDc2OTUzOTA3ODMsIDcuOTM4MDc2MTUyMzA0NjA5NSwgNy45NTIzMDQ2MDkyMTg0MzcsIDcuOTY2NTMzMDY2MTMyMjY1LCA3Ljk4MDc2MTUyMzA0NjA5MywgNy45OTQ5ODk5Nzk5NTk5MjEsIDguMDA5MjE4NDM2ODczNzQ4LCA4LjAyMzQ0Njg5Mzc4NzU3NSwgOC4wMzc2NzUzNTA3MDE0MDIsIDguMDUxOTAzODA3NjE1MjMxLCA4LjA2NjEzMjI2NDUyOTA2LCA4LjA4MDM2MDcyMTQ0Mjg4NywgOC4wOTQ1ODkxNzgzNTY3MTMsIDguMTA4ODE3NjM1MjcwNTQsIDguMTIzMDQ2MDkyMTg0MzcsIDguMTM3Mjc0NTQ5MDk4MTk4LCA4LjE1MTUwMzAwNjAxMjAyNSwgOC4xNjU3MzE0NjI5MjU4NTIsIDguMTc5OTU5OTE5ODM5Njc4LCA4LjE5NDE4ODM3Njc1MzUwOSwgOC4yMDg0MTY4MzM2NjczMzYsIDguMjIyNjQ1MjkwNTgxMTYzLCA4LjIzNjg3Mzc0NzQ5NDk5LCA4LjI1MTEwMjIwNDQwODgxOCwgOC4yNjUzMzA2NjEzMjI2NDcsIDguMjc5NTU5MTE4MjM2NDc0LCA4LjI5Mzc4NzU3NTE1MDMsIDguMzA4MDE2MDMyMDY0MTI4LCA4LjMyMjI0NDQ4ODk3Nzk1NiwgOC4zMzY0NzI5NDU4OTE3ODUsIDguMzUwNzAxNDAyODA1NjEyLCA4LjM2NDkyOTg1OTcxOTQzOSwgOC4zNzkxNTgzMTY2MzMyNjYsIDguMzkzMzg2NzczNTQ3MDk0LCA4LjQwNzYxNTIzMDQ2MDkyMywgOC40MjE4NDM2ODczNzQ3NSwgOC40MzYwNzIxNDQyODg1NzcsIDguNDUwMzAwNjAxMjAyNDA2LCA4LjQ2NDUyOTA1ODExNjIzMiwgOC40Nzg3NTc1MTUwMzAwNjEsIDguNDkyOTg1OTcxOTQzODg4LCA4LjUwNzIxNDQyODg1NzcxNSwgOC41MjE0NDI4ODU3NzE1NDQsIDguNTM1NjcxMzQyNjg1MzcsIDguNTQ5ODk5Nzk5NTk5MiwgOC41NjQxMjgyNTY1MTMwMjYsIDguNTc4MzU2NzEzNDI2ODUzLCA4LjU5MjU4NTE3MDM0MDY4MiwgOC42MDY4MTM2MjcyNTQ1MSwgOC42MjEwNDIwODQxNjgzMzcsIDguNjM1MjcwNTQxMDgyMTY0LCA4LjY0OTQ5ODk5Nzk5NTk5MywgOC42NjM3Mjc0NTQ5MDk4MiwgOC42Nzc5NTU5MTE4MjM2NDgsIDguNjkyMTg0MzY4NzM3NDc1LCA4LjcwNjQxMjgyNTY1MTMwMiwgOC43MjA2NDEyODI1NjUxMzEsIDguNzM0ODY5NzM5NDc4OTU4LCA4Ljc0OTA5ODE5NjM5Mjc4NywgOC43NjMzMjY2NTMzMDY2MTMsIDguNzc3NTU1MTEwMjIwNDQsIDguNzkxNzgzNTY3MTM0MjY5LCA4LjgwNjAxMjAyNDA0ODA5OCwgOC44MjAyNDA0ODA5NjE5MjUsIDguODM0NDY4OTM3ODc1NzUxLCA4Ljg0ODY5NzM5NDc4OTU4LCA4Ljg2MjkyNTg1MTcwMzQwNywgOC44NzcxNTQzMDg2MTcyMzYsIDguODkxMzgyNzY1NTMxMDYzLCA4LjkwNTYxMTIyMjQ0NDg5LCA4LjkxOTgzOTY3OTM1ODcxOCwgOC45MzQwNjgxMzYyNzI1NDcsIDguOTQ4Mjk2NTkzMTg2Mzc0LCA4Ljk2MjUyNTA1MDEwMDIsIDguOTc2NzUzNTA3MDE0MDI4LCA4Ljk5MDk4MTk2MzkyNzg1NiwgOS4wMDUyMTA0MjA4NDE2ODUsIDkuMDE5NDM4ODc3NzU1NTEyLCA5LjAzMzY2NzMzNDY2OTMzOSwgOS4wNDc4OTU3OTE1ODMxNjcsIDkuMDYyMTI0MjQ4NDk2OTk0LCA5LjA3NjM1MjcwNTQxMDgyMywgOS4wOTA1ODExNjIzMjQ2NSwgOS4xMDQ4MDk2MTkyMzg0NzcsIDkuMTE5MDM4MDc2MTUyMzA2LCA5LjEzMzI2NjUzMzA2NjEzNCwgOS4xNDc0OTQ5ODk5Nzk5NjEsIDkuMTYxNzIzNDQ2ODkzNzg4LCA5LjE3NTk1MTkwMzgwNzYxNSwgOS4xOTAxODAzNjA3MjE0NDQsIDkuMjA0NDA4ODE3NjM1MjcyLCA5LjIxODYzNzI3NDU0OTEsIDkuMjMyODY1NzMxNDYyOTI2LCA5LjI0NzA5NDE4ODM3Njc1MywgOS4yNjEzMjI2NDUyOTA1ODIsIDkuMjc1NTUxMTAyMjA0NDEsIDkuMjg5Nzc5NTU5MTE4MjM3LCA5LjMwNDAwODAxNjAzMjA2NCwgOS4zMTgyMzY0NzI5NDU4OTMsIDkuMzMyNDY0OTI5ODU5NzIyLCA5LjM0NjY5MzM4Njc3MzU0OCwgOS4zNjA5MjE4NDM2ODczNzUsIDkuMzc1MTUwMzAwNjAxMjAyLCA5LjM4OTM3ODc1NzUxNTAzMSwgOS40MDM2MDcyMTQ0Mjg4NiwgOS40MTc4MzU2NzEzNDI2ODcsIDkuNDMyMDY0MTI4MjU2NTEzLCA5LjQ0NjI5MjU4NTE3MDM0LCA5LjQ2MDUyMTA0MjA4NDE2OSwgOS40NzQ3NDk0OTg5OTc5OTgsIDkuNDg4OTc3OTU1OTExODI1LCA5LjUwMzIwNjQxMjgyNTY1MSwgOS41MTc0MzQ4Njk3Mzk0NzgsIDkuNTMxNjYzMzI2NjUzMzA5LCA5LjU0NTg5MTc4MzU2NzEzNiwgOS41NjAxMjAyNDA0ODA5NjMsIDkuNTc0MzQ4Njk3Mzk0NzksIDkuNTg4NTc3MTU0MzA4NjE2LCA5LjYwMjgwNTYxMTIyMjQ0NywgOS42MTcwMzQwNjgxMzYyNzQsIDkuNjMxMjYyNTI1MDUwMSwgOS42NDU0OTA5ODE5NjM5MjgsIDkuNjU5NzE5NDM4ODc3NzU2LCA5LjY3Mzk0Nzg5NTc5MTU4NSwgOS42ODgxNzYzNTI3MDU0MTIsIDkuNzAyNDA0ODA5NjE5MjM5LCA5LjcxNjYzMzI2NjUzMzA2NiwgOS43MzA4NjE3MjM0NDY4OTQsIDkuNzQ1MDkwMTgwMzYwNzIzLCA5Ljc1OTMxODYzNzI3NDU1LCA5Ljc3MzU0NzA5NDE4ODM3NywgOS43ODc3NzU1NTExMDIyMDQsIDkuODAyMDA0MDA4MDE2MDMyLCA5LjgxNjIzMjQ2NDkyOTg2MSwgOS44MzA0NjA5MjE4NDM2ODgsIDkuODQ0Njg5Mzc4NzU3NTE1LCA5Ljg1ODkxNzgzNTY3MTM0NCwgOS44NzMxNDYyOTI1ODUxNywgOS44ODczNzQ3NDk0OTksIDkuOTAxNjAzMjA2NDEyODI2LCA5LjkxNTgzMTY2MzMyNjY1MywgOS45MzAwNjAxMjAyNDA0ODIsIDkuOTQ0Mjg4NTc3MTU0MzA5LCA5Ljk1ODUxNzAzNDA2ODEzNywgOS45NzI3NDU0OTA5ODE5NjQsIDkuOTg2OTczOTQ3ODk1NzkxLCAxMC4wMDEyMDI0MDQ4MDk2MiwgMTAuMDE1NDMwODYxNzIzNDQ4LCAxMC4wMjk2NTkzMTg2MzcyNzUsIDEwLjA0Mzg4Nzc3NTU1MTEwMiwgMTAuMDU4MTE2MjMyNDY0OTMxLCAxMC4wNzIzNDQ2ODkzNzg3NTgsIDEwLjA4NjU3MzE0NjI5MjU4NywgMTAuMTAwODAxNjAzMjA2NDEzLCAxMC4xMTUwMzAwNjAxMjAyNCwgMTAuMTI5MjU4NTE3MDM0MDY5LCAxMC4xNDM0ODY5NzM5NDc4OTYsIDEwLjE1NzcxNTQzMDg2MTcyNSwgMTAuMTcxOTQzODg3Nzc1NTUxLCAxMC4xODYxNzIzNDQ2ODkzNzgsIDEwLjIwMDQwMDgwMTYwMzIwNywgMTAuMjE0NjI5MjU4NTE3MDM2LCAxMC4yMjg4NTc3MTU0MzA4NjMsIDEwLjI0MzA4NjE3MjM0NDY5LCAxMC4yNTczMTQ2MjkyNTg1MTgsIDEwLjI3MTU0MzA4NjE3MjM0NSwgMTAuMjg1NzcxNTQzMDg2MTc0LCAxMC4zXSkKICAgICAgICAgICAgICAucmFuZ2UoWycjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZDlmMGEzJywgJyNkOWYwYTMnLCAnI2Q5ZjBhMycsICcjZDlmMGEzJywgJyNkOWYwYTMnLCAnI2Q5ZjBhMycsICcjZDlmMGEzJywgJyNkOWYwYTMnLCAnI2Q5ZjBhMycsICcjZDlmMGEzJywgJyNkOWYwYTMnLCAnI2Q5ZjBhMycsICcjZDlmMGEzJywgJyNkOWYwYTMnLCAnI2Q5ZjBhMycsICcjZDlmMGEzJywgJyNkOWYwYTMnLCAnI2Q5ZjBhMycsICcjZDlmMGEzJywgJyNkOWYwYTMnLCAnI2Q5ZjBhMycsICcjZDlmMGEzJywgJyNkOWYwYTMnLCAnI2Q5ZjBhMycsICcjZDlmMGEzJywgJyNkOWYwYTMnLCAnI2Q5ZjBhMycsICcjZDlmMGEzJywgJyNkOWYwYTMnLCAnI2Q5ZjBhMycsICcjZDlmMGEzJywgJyNkOWYwYTMnLCAnI2Q5ZjBhMycsICcjZDlmMGEzJywgJyNkOWYwYTMnLCAnI2Q5ZjBhMycsICcjZDlmMGEzJywgJyNkOWYwYTMnLCAnI2Q5ZjBhMycsICcjZDlmMGEzJywgJyNkOWYwYTMnLCAnI2Q5ZjBhMycsICcjZDlmMGEzJywgJyNkOWYwYTMnLCAnI2Q5ZjBhMycsICcjZDlmMGEzJywgJyNkOWYwYTMnLCAnI2Q5ZjBhMycsICcjZDlmMGEzJywgJyNkOWYwYTMnLCAnI2Q5ZjBhMycsICcjZDlmMGEzJywgJyNkOWYwYTMnLCAnI2Q5ZjBhMycsICcjZDlmMGEzJywgJyNkOWYwYTMnLCAnI2Q5ZjBhMycsICcjZDlmMGEzJywgJyNkOWYwYTMnLCAnI2Q5ZjBhMycsICcjZDlmMGEzJywgJyNkOWYwYTMnLCAnI2Q5ZjBhMycsICcjZDlmMGEzJywgJyNkOWYwYTMnLCAnI2Q5ZjBhMycsICcjZDlmMGEzJywgJyNkOWYwYTMnLCAnI2Q5ZjBhMycsICcjZDlmMGEzJywgJyNkOWYwYTMnLCAnI2Q5ZjBhMycsICcjZDlmMGEzJywgJyNkOWYwYTMnLCAnI2Q5ZjBhMycsICcjZDlmMGEzJywgJyNkOWYwYTMnLCAnI2Q5ZjBhMycsICcjZDlmMGEzJywgJyNkOWYwYTMnLCAnI2Q5ZjBhMycsICcjZDlmMGEzJywgJyNkOWYwYTMnLCAnI2FkZGQ4ZScsICcjYWRkZDhlJywgJyNhZGRkOGUnLCAnI2FkZGQ4ZScsICcjYWRkZDhlJywgJyNhZGRkOGUnLCAnI2FkZGQ4ZScsICcjYWRkZDhlJywgJyNhZGRkOGUnLCAnI2FkZGQ4ZScsICcjYWRkZDhlJywgJyNhZGRkOGUnLCAnI2FkZGQ4ZScsICcjYWRkZDhlJywgJyNhZGRkOGUnLCAnI2FkZGQ4ZScsICcjYWRkZDhlJywgJyNhZGRkOGUnLCAnI2FkZGQ4ZScsICcjYWRkZDhlJywgJyNhZGRkOGUnLCAnI2FkZGQ4ZScsICcjYWRkZDhlJywgJyNhZGRkOGUnLCAnI2FkZGQ4ZScsICcjYWRkZDhlJywgJyNhZGRkOGUnLCAnI2FkZGQ4ZScsICcjYWRkZDhlJywgJyNhZGRkOGUnLCAnI2FkZGQ4ZScsICcjYWRkZDhlJywgJyNhZGRkOGUnLCAnI2FkZGQ4ZScsICcjYWRkZDhlJywgJyNhZGRkOGUnLCAnI2FkZGQ4ZScsICcjYWRkZDhlJywgJyNhZGRkOGUnLCAnI2FkZGQ4ZScsICcjYWRkZDhlJywgJyNhZGRkOGUnLCAnI2FkZGQ4ZScsICcjYWRkZDhlJywgJyNhZGRkOGUnLCAnI2FkZGQ4ZScsICcjYWRkZDhlJywgJyNhZGRkOGUnLCAnI2FkZGQ4ZScsICcjYWRkZDhlJywgJyNhZGRkOGUnLCAnI2FkZGQ4ZScsICcjYWRkZDhlJywgJyNhZGRkOGUnLCAnI2FkZGQ4ZScsICcjYWRkZDhlJywgJyNhZGRkOGUnLCAnI2FkZGQ4ZScsICcjYWRkZDhlJywgJyNhZGRkOGUnLCAnI2FkZGQ4ZScsICcjYWRkZDhlJywgJyNhZGRkOGUnLCAnI2FkZGQ4ZScsICcjYWRkZDhlJywgJyNhZGRkOGUnLCAnI2FkZGQ4ZScsICcjYWRkZDhlJywgJyNhZGRkOGUnLCAnI2FkZGQ4ZScsICcjYWRkZDhlJywgJyNhZGRkOGUnLCAnI2FkZGQ4ZScsICcjYWRkZDhlJywgJyNhZGRkOGUnLCAnI2FkZGQ4ZScsICcjYWRkZDhlJywgJyNhZGRkOGUnLCAnI2FkZGQ4ZScsICcjYWRkZDhlJywgJyNhZGRkOGUnLCAnI2FkZGQ4ZScsICcjYWRkZDhlJywgJyM3OGM2NzknLCAnIzc4YzY3OScsICcjNzhjNjc5JywgJyM3OGM2NzknLCAnIzc4YzY3OScsICcjNzhjNjc5JywgJyM3OGM2NzknLCAnIzc4YzY3OScsICcjNzhjNjc5JywgJyM3OGM2NzknLCAnIzc4YzY3OScsICcjNzhjNjc5JywgJyM3OGM2NzknLCAnIzc4YzY3OScsICcjNzhjNjc5JywgJyM3OGM2NzknLCAnIzc4YzY3OScsICcjNzhjNjc5JywgJyM3OGM2NzknLCAnIzc4YzY3OScsICcjNzhjNjc5JywgJyM3OGM2NzknLCAnIzc4YzY3OScsICcjNzhjNjc5JywgJyM3OGM2NzknLCAnIzc4YzY3OScsICcjNzhjNjc5JywgJyM3OGM2NzknLCAnIzc4YzY3OScsICcjNzhjNjc5JywgJyM3OGM2NzknLCAnIzc4YzY3OScsICcjNzhjNjc5JywgJyM3OGM2NzknLCAnIzc4YzY3OScsICcjNzhjNjc5JywgJyM3OGM2NzknLCAnIzc4YzY3OScsICcjNzhjNjc5JywgJyM3OGM2NzknLCAnIzc4YzY3OScsICcjNzhjNjc5JywgJyM3OGM2NzknLCAnIzc4YzY3OScsICcjNzhjNjc5JywgJyM3OGM2NzknLCAnIzc4YzY3OScsICcjNzhjNjc5JywgJyM3OGM2NzknLCAnIzc4YzY3OScsICcjNzhjNjc5JywgJyM3OGM2NzknLCAnIzc4YzY3OScsICcjNzhjNjc5JywgJyM3OGM2NzknLCAnIzc4YzY3OScsICcjNzhjNjc5JywgJyM3OGM2NzknLCAnIzc4YzY3OScsICcjNzhjNjc5JywgJyM3OGM2NzknLCAnIzc4YzY3OScsICcjNzhjNjc5JywgJyM3OGM2NzknLCAnIzc4YzY3OScsICcjNzhjNjc5JywgJyM3OGM2NzknLCAnIzc4YzY3OScsICcjNzhjNjc5JywgJyM3OGM2NzknLCAnIzc4YzY3OScsICcjNzhjNjc5JywgJyM3OGM2NzknLCAnIzc4YzY3OScsICcjNzhjNjc5JywgJyM3OGM2NzknLCAnIzc4YzY3OScsICcjNzhjNjc5JywgJyM3OGM2NzknLCAnIzc4YzY3OScsICcjNzhjNjc5JywgJyM3OGM2NzknLCAnIzc4YzY3OScsICcjMzFhMzU0JywgJyMzMWEzNTQnLCAnIzMxYTM1NCcsICcjMzFhMzU0JywgJyMzMWEzNTQnLCAnIzMxYTM1NCcsICcjMzFhMzU0JywgJyMzMWEzNTQnLCAnIzMxYTM1NCcsICcjMzFhMzU0JywgJyMzMWEzNTQnLCAnIzMxYTM1NCcsICcjMzFhMzU0JywgJyMzMWEzNTQnLCAnIzMxYTM1NCcsICcjMzFhMzU0JywgJyMzMWEzNTQnLCAnIzMxYTM1NCcsICcjMzFhMzU0JywgJyMzMWEzNTQnLCAnIzMxYTM1NCcsICcjMzFhMzU0JywgJyMzMWEzNTQnLCAnIzMxYTM1NCcsICcjMzFhMzU0JywgJyMzMWEzNTQnLCAnIzMxYTM1NCcsICcjMzFhMzU0JywgJyMzMWEzNTQnLCAnIzMxYTM1NCcsICcjMzFhMzU0JywgJyMzMWEzNTQnLCAnIzMxYTM1NCcsICcjMzFhMzU0JywgJyMzMWEzNTQnLCAnIzMxYTM1NCcsICcjMzFhMzU0JywgJyMzMWEzNTQnLCAnIzMxYTM1NCcsICcjMzFhMzU0JywgJyMzMWEzNTQnLCAnIzMxYTM1NCcsICcjMzFhMzU0JywgJyMzMWEzNTQnLCAnIzMxYTM1NCcsICcjMzFhMzU0JywgJyMzMWEzNTQnLCAnIzMxYTM1NCcsICcjMzFhMzU0JywgJyMzMWEzNTQnLCAnIzMxYTM1NCcsICcjMzFhMzU0JywgJyMzMWEzNTQnLCAnIzMxYTM1NCcsICcjMzFhMzU0JywgJyMzMWEzNTQnLCAnIzMxYTM1NCcsICcjMzFhMzU0JywgJyMzMWEzNTQnLCAnIzMxYTM1NCcsICcjMzFhMzU0JywgJyMzMWEzNTQnLCAnIzMxYTM1NCcsICcjMzFhMzU0JywgJyMzMWEzNTQnLCAnIzMxYTM1NCcsICcjMzFhMzU0JywgJyMzMWEzNTQnLCAnIzMxYTM1NCcsICcjMzFhMzU0JywgJyMzMWEzNTQnLCAnIzMxYTM1NCcsICcjMzFhMzU0JywgJyMzMWEzNTQnLCAnIzMxYTM1NCcsICcjMzFhMzU0JywgJyMzMWEzNTQnLCAnIzMxYTM1NCcsICcjMzFhMzU0JywgJyMzMWEzNTQnLCAnIzMxYTM1NCcsICcjMzFhMzU0JywgJyMzMWEzNTQnLCAnIzAwNjgzNycsICcjMDA2ODM3JywgJyMwMDY4MzcnLCAnIzAwNjgzNycsICcjMDA2ODM3JywgJyMwMDY4MzcnLCAnIzAwNjgzNycsICcjMDA2ODM3JywgJyMwMDY4MzcnLCAnIzAwNjgzNycsICcjMDA2ODM3JywgJyMwMDY4MzcnLCAnIzAwNjgzNycsICcjMDA2ODM3JywgJyMwMDY4MzcnLCAnIzAwNjgzNycsICcjMDA2ODM3JywgJyMwMDY4MzcnLCAnIzAwNjgzNycsICcjMDA2ODM3JywgJyMwMDY4MzcnLCAnIzAwNjgzNycsICcjMDA2ODM3JywgJyMwMDY4MzcnLCAnIzAwNjgzNycsICcjMDA2ODM3JywgJyMwMDY4MzcnLCAnIzAwNjgzNycsICcjMDA2ODM3JywgJyMwMDY4MzcnLCAnIzAwNjgzNycsICcjMDA2ODM3JywgJyMwMDY4MzcnLCAnIzAwNjgzNycsICcjMDA2ODM3JywgJyMwMDY4MzcnLCAnIzAwNjgzNycsICcjMDA2ODM3JywgJyMwMDY4MzcnLCAnIzAwNjgzNycsICcjMDA2ODM3JywgJyMwMDY4MzcnLCAnIzAwNjgzNycsICcjMDA2ODM3JywgJyMwMDY4MzcnLCAnIzAwNjgzNycsICcjMDA2ODM3JywgJyMwMDY4MzcnLCAnIzAwNjgzNycsICcjMDA2ODM3JywgJyMwMDY4MzcnLCAnIzAwNjgzNycsICcjMDA2ODM3JywgJyMwMDY4MzcnLCAnIzAwNjgzNycsICcjMDA2ODM3JywgJyMwMDY4MzcnLCAnIzAwNjgzNycsICcjMDA2ODM3JywgJyMwMDY4MzcnLCAnIzAwNjgzNycsICcjMDA2ODM3JywgJyMwMDY4MzcnLCAnIzAwNjgzNycsICcjMDA2ODM3JywgJyMwMDY4MzcnLCAnIzAwNjgzNycsICcjMDA2ODM3JywgJyMwMDY4MzcnLCAnIzAwNjgzNycsICcjMDA2ODM3JywgJyMwMDY4MzcnLCAnIzAwNjgzNycsICcjMDA2ODM3JywgJyMwMDY4MzcnLCAnIzAwNjgzNycsICcjMDA2ODM3JywgJyMwMDY4MzcnLCAnIzAwNjgzNycsICcjMDA2ODM3JywgJyMwMDY4MzcnLCAnIzAwNjgzNycsICcjMDA2ODM3JywgJyMwMDY4MzcnXSk7CiAgICAKCiAgICBjb2xvcl9tYXBfODY2NzA5M2ZhNjUzNGRlMWJjN2VmMmE4MGE2YjY2ZWYueCA9IGQzLnNjYWxlLmxpbmVhcigpCiAgICAgICAgICAgICAgLmRvbWFpbihbMy4yLCAxMC4zXSkKICAgICAgICAgICAgICAucmFuZ2UoWzAsIDQwMF0pOwoKICAgIGNvbG9yX21hcF84NjY3MDkzZmE2NTM0ZGUxYmM3ZWYyYTgwYTZiNjZlZi5sZWdlbmQgPSBMLmNvbnRyb2woe3Bvc2l0aW9uOiAndG9wcmlnaHQnfSk7CiAgICBjb2xvcl9tYXBfODY2NzA5M2ZhNjUzNGRlMWJjN2VmMmE4MGE2YjY2ZWYubGVnZW5kLm9uQWRkID0gZnVuY3Rpb24gKG1hcCkge3ZhciBkaXYgPSBMLkRvbVV0aWwuY3JlYXRlKCdkaXYnLCAnbGVnZW5kJyk7IHJldHVybiBkaXZ9OwogICAgY29sb3JfbWFwXzg2NjcwOTNmYTY1MzRkZTFiYzdlZjJhODBhNmI2NmVmLmxlZ2VuZC5hZGRUbyhtYXBfNTQxNzEwOWNmMTE5NDdhZjk2ODc5ZDAwZTQ4ZDgyMTYpOwoKICAgIGNvbG9yX21hcF84NjY3MDkzZmE2NTM0ZGUxYmM3ZWYyYTgwYTZiNjZlZi54QXhpcyA9IGQzLnN2Zy5heGlzKCkKICAgICAgICAuc2NhbGUoY29sb3JfbWFwXzg2NjcwOTNmYTY1MzRkZTFiYzdlZjJhODBhNmI2NmVmLngpCiAgICAgICAgLm9yaWVudCgidG9wIikKICAgICAgICAudGlja1NpemUoMSkKICAgICAgICAudGlja1ZhbHVlcyhbMy4yLCA0LjM4MzMzMzMzMzMzMzMzNCwgNS41NjY2NjY2NjY2NjY2NjYsIDYuNzUsIDcuOTMzMzMzMzMzMzMzMzM0LCA5LjExNjY2NjY2NjY2NjY2NywgMTAuM10pOwoKICAgIGNvbG9yX21hcF84NjY3MDkzZmE2NTM0ZGUxYmM3ZWYyYTgwYTZiNjZlZi5zdmcgPSBkMy5zZWxlY3QoIi5sZWdlbmQubGVhZmxldC1jb250cm9sIikuYXBwZW5kKCJzdmciKQogICAgICAgIC5hdHRyKCJpZCIsICdsZWdlbmQnKQogICAgICAgIC5hdHRyKCJ3aWR0aCIsIDQ1MCkKICAgICAgICAuYXR0cigiaGVpZ2h0IiwgNDApOwoKICAgIGNvbG9yX21hcF84NjY3MDkzZmE2NTM0ZGUxYmM3ZWYyYTgwYTZiNjZlZi5nID0gY29sb3JfbWFwXzg2NjcwOTNmYTY1MzRkZTFiYzdlZjJhODBhNmI2NmVmLnN2Zy5hcHBlbmQoImciKQogICAgICAgIC5hdHRyKCJjbGFzcyIsICJrZXkiKQogICAgICAgIC5hdHRyKCJ0cmFuc2Zvcm0iLCAidHJhbnNsYXRlKDI1LDE2KSIpOwoKICAgIGNvbG9yX21hcF84NjY3MDkzZmE2NTM0ZGUxYmM3ZWYyYTgwYTZiNjZlZi5nLnNlbGVjdEFsbCgicmVjdCIpCiAgICAgICAgLmRhdGEoY29sb3JfbWFwXzg2NjcwOTNmYTY1MzRkZTFiYzdlZjJhODBhNmI2NmVmLmNvbG9yLnJhbmdlKCkubWFwKGZ1bmN0aW9uKGQsIGkpIHsKICAgICAgICAgIHJldHVybiB7CiAgICAgICAgICAgIHgwOiBpID8gY29sb3JfbWFwXzg2NjcwOTNmYTY1MzRkZTFiYzdlZjJhODBhNmI2NmVmLngoY29sb3JfbWFwXzg2NjcwOTNmYTY1MzRkZTFiYzdlZjJhODBhNmI2NmVmLmNvbG9yLmRvbWFpbigpW2kgLSAxXSkgOiBjb2xvcl9tYXBfODY2NzA5M2ZhNjUzNGRlMWJjN2VmMmE4MGE2YjY2ZWYueC5yYW5nZSgpWzBdLAogICAgICAgICAgICB4MTogaSA8IGNvbG9yX21hcF84NjY3MDkzZmE2NTM0ZGUxYmM3ZWYyYTgwYTZiNjZlZi5jb2xvci5kb21haW4oKS5sZW5ndGggPyBjb2xvcl9tYXBfODY2NzA5M2ZhNjUzNGRlMWJjN2VmMmE4MGE2YjY2ZWYueChjb2xvcl9tYXBfODY2NzA5M2ZhNjUzNGRlMWJjN2VmMmE4MGE2YjY2ZWYuY29sb3IuZG9tYWluKClbaV0pIDogY29sb3JfbWFwXzg2NjcwOTNmYTY1MzRkZTFiYzdlZjJhODBhNmI2NmVmLngucmFuZ2UoKVsxXSwKICAgICAgICAgICAgejogZAogICAgICAgICAgfTsKICAgICAgICB9KSkKICAgICAgLmVudGVyKCkuYXBwZW5kKCJyZWN0IikKICAgICAgICAuYXR0cigiaGVpZ2h0IiwgMTApCiAgICAgICAgLmF0dHIoIngiLCBmdW5jdGlvbihkKSB7IHJldHVybiBkLngwOyB9KQogICAgICAgIC5hdHRyKCJ3aWR0aCIsIGZ1bmN0aW9uKGQpIHsgcmV0dXJuIGQueDEgLSBkLngwOyB9KQogICAgICAgIC5zdHlsZSgiZmlsbCIsIGZ1bmN0aW9uKGQpIHsgcmV0dXJuIGQuejsgfSk7CgogICAgY29sb3JfbWFwXzg2NjcwOTNmYTY1MzRkZTFiYzdlZjJhODBhNmI2NmVmLmcuY2FsbChjb2xvcl9tYXBfODY2NzA5M2ZhNjUzNGRlMWJjN2VmMmE4MGE2YjY2ZWYueEF4aXMpLmFwcGVuZCgidGV4dCIpCiAgICAgICAgLmF0dHIoImNsYXNzIiwgImNhcHRpb24iKQogICAgICAgIC5hdHRyKCJ5IiwgMjEpCiAgICAgICAgLnRleHQoJ1VuZW1wbG95bWVudCBSYXRlICglKScpOwo8L3NjcmlwdD4=" style="position:absolute;width:100%;height:100%;left:0;top:0;border:none !important;" allowfullscreen webkitallowfullscreen mozallowfullscreen></iframe></div></div>



# 범죄율에 대한 지도 시각화


```python
import json
geo_path = '../data/02. skorea_municipalities_geo_simple.json'
geo_str = json.load(open(geo_path, encoding='utf-8'))
```


```python
map = folium.Map(location=[37.5502, 126.982], zoom_start=11, 
                 tiles='Stamen Toner')

map.choropleth(geo_data = geo_str,
               data = crime_anal_norm['살인'],
               columns = [crime_anal_norm.index, crime_anal_norm['살인']],
               fill_color = 'PuRd', #PuRd, YlGnBu
               key_on = 'feature.id')
map
```

    C:\ProgramData\Anaconda3\lib\site-packages\folium\folium.py:432: FutureWarning: The choropleth  method has been deprecated. Instead use the new Choropleth class, which has the same arguments. See the example notebook 'GeoJSON_and_choropleth' for how to do this.
      FutureWarning
    




<div style="width:100%;"><div style="position:relative;width:100%;height:0;padding-bottom:60%;"><iframe src="data:text/html;charset=utf-8;base64,PCFET0NUWVBFIGh0bWw+CjxoZWFkPiAgICAKICAgIDxtZXRhIGh0dHAtZXF1aXY9ImNvbnRlbnQtdHlwZSIgY29udGVudD0idGV4dC9odG1sOyBjaGFyc2V0PVVURi04IiAvPgogICAgPHNjcmlwdD5MX1BSRUZFUl9DQU5WQVM9ZmFsc2U7IExfTk9fVE9VQ0g9ZmFsc2U7IExfRElTQUJMRV8zRD1mYWxzZTs8L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmpzIj48L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2FqYXguZ29vZ2xlYXBpcy5jb20vYWpheC9saWJzL2pxdWVyeS8xLjExLjEvanF1ZXJ5Lm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvanMvYm9vdHN0cmFwLm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9jZG5qcy5jbG91ZGZsYXJlLmNvbS9hamF4L2xpYnMvTGVhZmxldC5hd2Vzb21lLW1hcmtlcnMvMi4wLjIvbGVhZmxldC5hd2Vzb21lLW1hcmtlcnMuanMiPjwvc2NyaXB0PgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL21heGNkbi5ib290c3RyYXBjZG4uY29tL2Jvb3RzdHJhcC8zLjIuMC9jc3MvYm9vdHN0cmFwLm1pbi5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvY3NzL2Jvb3RzdHJhcC10aGVtZS5taW4uY3NzIi8+CiAgICA8bGluayByZWw9InN0eWxlc2hlZXQiIGhyZWY9Imh0dHBzOi8vbWF4Y2RuLmJvb3RzdHJhcGNkbi5jb20vZm9udC1hd2Vzb21lLzQuNi4zL2Nzcy9mb250LWF3ZXNvbWUubWluLmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2NkbmpzLmNsb3VkZmxhcmUuY29tL2FqYXgvbGlicy9MZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy8yLjAuMi9sZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9yYXdjZG4uZ2l0aGFjay5jb20vcHl0aG9uLXZpc3VhbGl6YXRpb24vZm9saXVtL21hc3Rlci9mb2xpdW0vdGVtcGxhdGVzL2xlYWZsZXQuYXdlc29tZS5yb3RhdGUuY3NzIi8+CiAgICA8c3R5bGU+aHRtbCwgYm9keSB7d2lkdGg6IDEwMCU7aGVpZ2h0OiAxMDAlO21hcmdpbjogMDtwYWRkaW5nOiAwO308L3N0eWxlPgogICAgPHN0eWxlPiNtYXAge3Bvc2l0aW9uOmFic29sdXRlO3RvcDowO2JvdHRvbTowO3JpZ2h0OjA7bGVmdDowO308L3N0eWxlPgogICAgCiAgICA8bWV0YSBuYW1lPSJ2aWV3cG9ydCIgY29udGVudD0id2lkdGg9ZGV2aWNlLXdpZHRoLAogICAgICAgIGluaXRpYWwtc2NhbGU9MS4wLCBtYXhpbXVtLXNjYWxlPTEuMCwgdXNlci1zY2FsYWJsZT1ubyIgLz4KICAgIDxzdHlsZT4jbWFwXzI2NTkwNjA0YmVlODQ3N2Q5MTg5OTBjNGI3ZDA0ODU0IHsKICAgICAgICBwb3NpdGlvbjogcmVsYXRpdmU7CiAgICAgICAgd2lkdGg6IDEwMC4wJTsKICAgICAgICBoZWlnaHQ6IDEwMC4wJTsKICAgICAgICBsZWZ0OiAwLjAlOwogICAgICAgIHRvcDogMC4wJTsKICAgICAgICB9CiAgICA8L3N0eWxlPgogICAgPHNjcmlwdCBzcmM9Imh0dHBzOi8vY2RuanMuY2xvdWRmbGFyZS5jb20vYWpheC9saWJzL2QzLzMuNS41L2QzLm1pbi5qcyI+PC9zY3JpcHQ+CjwvaGVhZD4KPGJvZHk+ICAgIAogICAgCiAgICA8ZGl2IGNsYXNzPSJmb2xpdW0tbWFwIiBpZD0ibWFwXzI2NTkwNjA0YmVlODQ3N2Q5MTg5OTBjNGI3ZDA0ODU0IiA+PC9kaXY+CjwvYm9keT4KPHNjcmlwdD4gICAgCiAgICAKICAgIAogICAgICAgIHZhciBib3VuZHMgPSBudWxsOwogICAgCgogICAgdmFyIG1hcF8yNjU5MDYwNGJlZTg0NzdkOTE4OTkwYzRiN2QwNDg1NCA9IEwubWFwKAogICAgICAgICdtYXBfMjY1OTA2MDRiZWU4NDc3ZDkxODk5MGM0YjdkMDQ4NTQnLCB7CiAgICAgICAgY2VudGVyOiBbMzcuNTUwMiwgMTI2Ljk4Ml0sCiAgICAgICAgem9vbTogMTEsCiAgICAgICAgbWF4Qm91bmRzOiBib3VuZHMsCiAgICAgICAgbGF5ZXJzOiBbXSwKICAgICAgICB3b3JsZENvcHlKdW1wOiBmYWxzZSwKICAgICAgICBjcnM6IEwuQ1JTLkVQU0czODU3LAogICAgICAgIHpvb21Db250cm9sOiB0cnVlLAogICAgICAgIH0pOwoKICAgIAogICAgCiAgICB2YXIgdGlsZV9sYXllcl9kODU1NjNmNjc1MDg0MTRiOGM2OGI5YmJkMjA2YmZkMiA9IEwudGlsZUxheWVyKAogICAgICAgICdodHRwczovL3N0YW1lbi10aWxlcy17c30uYS5zc2wuZmFzdGx5Lm5ldC90b25lci97en0ve3h9L3t5fS5wbmcnLAogICAgICAgIHsKICAgICAgICAiYXR0cmlidXRpb24iOiBudWxsLAogICAgICAgICJkZXRlY3RSZXRpbmEiOiBmYWxzZSwKICAgICAgICAibWF4TmF0aXZlWm9vbSI6IDE4LAogICAgICAgICJtYXhab29tIjogMTgsCiAgICAgICAgIm1pblpvb20iOiAwLAogICAgICAgICJub1dyYXAiOiBmYWxzZSwKICAgICAgICAib3BhY2l0eSI6IDEsCiAgICAgICAgInN1YmRvbWFpbnMiOiAiYWJjIiwKICAgICAgICAidG1zIjogZmFsc2UKfSkuYWRkVG8obWFwXzI2NTkwNjA0YmVlODQ3N2Q5MTg5OTBjNGI3ZDA0ODU0KTsKICAgIAogICAgICAgICAgICB2YXIgY2hvcm9wbGV0aF84NWRlYTA4ZDNkYTU0NTE4OGU4MGJhMGRlNzA5ZjJhYyA9IEwuZmVhdHVyZUdyb3VwKAogICAgICAgICAgICAgICAgKS5hZGRUbyhtYXBfMjY1OTA2MDRiZWU4NDc3ZDkxODk5MGM0YjdkMDQ4NTQpOwogICAgICAgIAogICAgCiAgICAgICAgCiAgICAgICAgdmFyIGdlb19qc29uX2U0NTJmODBjZDM0MjQzNTc4OTZhZTQ3MGI1NTQzZGM0ID0gTC5nZW9Kc29uKAogICAgICAgICAgICB7ImZlYXR1cmVzIjogW3siZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI3LjExNTE5NTg0OTgxNjA2LCAzNy41NTc1MzMxODA3MDQ5MTVdLCBbMTI3LjE2NjgzMTg0MzY2MTI5LCAzNy41NzY3MjQ4NzM4ODYyN10sIFsxMjcuMTg0MDg3OTIzMzAxNTIsIDM3LjU1ODE0MjgwMzY5NTc1XSwgWzEyNy4xNjUzMDk4NDMwNzQ0NywgMzcuNTQyMjE4NTEyNTg2OTNdLCBbMTI3LjE0NjcyODA2ODIzNTAyLCAzNy41MTQxNTY4MDY4MDI5MV0sIFsxMjcuMTIxMjMxNjU3MTk2MTUsIDM3LjUyNTI4MjcwMDg5XSwgWzEyNy4xMTE2NzY0MjAzNjA4LCAzNy41NDA2Njk5NTUzMjQ5NjVdLCBbMTI3LjExNTE5NTg0OTgxNjA2LCAzNy41NTc1MzMxODA3MDQ5MTVdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YWMxNVx1YjNkOVx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMjUwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWFjMTVcdWIzZDlcdWFkNmMiLCAibmFtZV9lbmciOiAiR2FuZ2RvbmctZ3UiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICIjZDRiOWRhIiwgImZpbGxPcGFjaXR5IjogMC42LCAib3BhY2l0eSI6IDEsICJ3ZWlnaHQiOiAxfX0sICJ0eXBlIjogIkZlYXR1cmUifSwgeyJnZW9tZXRyeSI6IHsiY29vcmRpbmF0ZXMiOiBbW1sxMjcuMDY5MDY5ODEzMDM3MiwgMzcuNTIyMjc5NDIzNTA1MDI2XSwgWzEyNy4xMDA4NzUxOTc5MTk2MiwgMzcuNTI0ODQxMjIwMTY3MDU1XSwgWzEyNy4xMTE2NzY0MjAzNjA4LCAzNy41NDA2Njk5NTUzMjQ5NjVdLCBbMTI3LjEyMTIzMTY1NzE5NjE1LCAzNy41MjUyODI3MDA4OV0sIFsxMjcuMTQ2NzI4MDY4MjM1MDIsIDM3LjUxNDE1NjgwNjgwMjkxXSwgWzEyNy4xNjM0OTQ0MjE1NzY1LCAzNy40OTc0NDU0MDYwOTc0ODRdLCBbMTI3LjE0MjA2MDU4NDEzMjc0LCAzNy40NzA4OTgxOTA5ODUwMV0sIFsxMjcuMTI0NDA1NzEwODA4OTMsIDM3LjQ2MjQwNDQ1NTg3MDQ4XSwgWzEyNy4xMTExNzA4NTIwMTIzOCwgMzcuNDg1NzA4MzgxNTEyNDQ1XSwgWzEyNy4wNzE5MTQ2MDAwNzI0LCAzNy41MDIyNDAxMzU4NzY2OV0sIFsxMjcuMDY5MDY5ODEzMDM3MiwgMzcuNTIyMjc5NDIzNTA1MDI2XV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJcdWMxYTFcdWQzMGNcdWFkNmMiLCAicHJvcGVydGllcyI6IHsiYmFzZV95ZWFyIjogIjIwMTMiLCAiY29kZSI6ICIxMTI0MCIsICJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiXHVjMWExXHVkMzBjXHVhZDZjIiwgIm5hbWVfZW5nIjogIlNvbmdwYS1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNkZDFjNzciLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wNTg2NzM1OTI4ODM5OCwgMzcuNTI2Mjk5NzQ5MjI1NjhdLCBbMTI3LjA2OTA2OTgxMzAzNzIsIDM3LjUyMjI3OTQyMzUwNTAyNl0sIFsxMjcuMDcxOTE0NjAwMDcyNCwgMzcuNTAyMjQwMTM1ODc2NjldLCBbMTI3LjExMTE3MDg1MjAxMjM4LCAzNy40ODU3MDgzODE1MTI0NDVdLCBbMTI3LjEyNDQwNTcxMDgwODkzLCAzNy40NjI0MDQ0NTU4NzA0OF0sIFsxMjcuMDk4NDI3NTkzMTg3NTEsIDM3LjQ1ODYyMjUzODU3NDYxXSwgWzEyNy4wODY0MDQ0MDU3ODE1NiwgMzcuNDcyNjk3OTM1MTg0NjU1XSwgWzEyNy4wNTU5MTcwNDgxOTA0LCAzNy40NjU5MjI4OTE0MDc3XSwgWzEyNy4wMzYyMTkxNTA5ODc5OCwgMzcuNDgxNzU4MDI0Mjc2MDNdLCBbMTI3LjAxMzk3MTE5NjY3NTEzLCAzNy41MjUwMzk4ODI4OTY2OV0sIFsxMjcuMDIzMDI4MzE4OTA1NTksIDM3LjUzMjMxODk5NTgyNjYzXSwgWzEyNy4wNTg2NzM1OTI4ODM5OCwgMzcuNTI2Mjk5NzQ5MjI1NjhdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YWMxNVx1YjBhOFx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMjMwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWFjMTVcdWIwYThcdWFkNmMiLCAibmFtZV9lbmciOiAiR2FuZ25hbS1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiM5ODAwNDMiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wMTM5NzExOTY2NzUxMywgMzcuNTI1MDM5ODgyODk2NjldLCBbMTI3LjAzNjIxOTE1MDk4Nzk4LCAzNy40ODE3NTgwMjQyNzYwM10sIFsxMjcuMDU1OTE3MDQ4MTkwNCwgMzcuNDY1OTIyODkxNDA3N10sIFsxMjcuMDg2NDA0NDA1NzgxNTYsIDM3LjQ3MjY5NzkzNTE4NDY1NV0sIFsxMjcuMDk4NDI3NTkzMTg3NTEsIDM3LjQ1ODYyMjUzODU3NDYxXSwgWzEyNy4wOTA0NjkyODU2NTk1MSwgMzcuNDQyOTY4MjYxMTQxODVdLCBbMTI3LjA2Nzc4MTA3NjA1NDMzLCAzNy40MjYxOTc0MjQwNTczMTRdLCBbMTI3LjA0OTU3MjMyOTg3MTQyLCAzNy40MjgwNTgzNjg0NTY5NF0sIFsxMjcuMDM4ODE3ODI1OTc5MjIsIDM3LjQ1MzgyMDM5ODUxNzE1XSwgWzEyNi45OTA3MjA3MzE5NTQ2MiwgMzcuNDU1MzI2MTQzMzEwMDI1XSwgWzEyNi45ODM2NzY2ODI5MTgwMiwgMzcuNDczODU2NDkyNjkyMDg2XSwgWzEyNi45ODIyMzgwNzkxNjA4MSwgMzcuNTA5MzE0OTY2NzcwMzI2XSwgWzEyNy4wMTM5NzExOTY2NzUxMywgMzcuNTI1MDM5ODgyODk2NjldXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzExY1x1Y2QwOFx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMjIwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWMxMWNcdWNkMDhcdWFkNmMiLCAibmFtZV9lbmciOiAiU2VvY2hvLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2RmNjViMCIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2Ljk4MzY3NjY4MjkxODAyLCAzNy40NzM4NTY0OTI2OTIwODZdLCBbMTI2Ljk5MDcyMDczMTk1NDYyLCAzNy40NTUzMjYxNDMzMTAwMjVdLCBbMTI2Ljk2NTIwNDM5MDg1MTQzLCAzNy40MzgyNDk3ODQwMDYyNDZdLCBbMTI2Ljk1MDAwMDAxMDEwMTgyLCAzNy40MzYxMzQ1MTE2NTcxOV0sIFsxMjYuOTMwODQ0MDgwNTY1MjUsIDM3LjQ0NzM4MjkyODMzMzk5NF0sIFsxMjYuOTE2NzcyODE0NjYwMSwgMzcuNDU0OTA1NjY0MjM3ODldLCBbMTI2LjkwMTU2MDk0MTI5ODk1LCAzNy40Nzc1Mzg0Mjc4OTkwMV0sIFsxMjYuOTA1MzE5NzU4MDE4MTIsIDM3LjQ4MjE4MDg3NTc1NDI5XSwgWzEyNi45NDkyMjY2MTM4OTUwOCwgMzcuNDkxMjU0Mzc0OTU2NDldLCBbMTI2Ljk3MjU4OTE4NTA2NjIsIDM3LjQ3MjU2MTM2MzI3ODEyNV0sIFsxMjYuOTgzNjc2NjgyOTE4MDIsIDM3LjQ3Mzg1NjQ5MjY5MjA4Nl1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVhZDAwXHVjNTQ1XHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEyMTAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YWQwMFx1YzU0NVx1YWQ2YyIsICJuYW1lX2VuZyI6ICJHd2FuYWstZ3UiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICIjZGY2NWIwIiwgImZpbGxPcGFjaXR5IjogMC42LCAib3BhY2l0eSI6IDEsICJ3ZWlnaHQiOiAxfX0sICJ0eXBlIjogIkZlYXR1cmUifSwgeyJnZW9tZXRyeSI6IHsiY29vcmRpbmF0ZXMiOiBbW1sxMjYuOTgyMjM4MDc5MTYwODEsIDM3LjUwOTMxNDk2Njc3MDMyNl0sIFsxMjYuOTgzNjc2NjgyOTE4MDIsIDM3LjQ3Mzg1NjQ5MjY5MjA4Nl0sIFsxMjYuOTcyNTg5MTg1MDY2MiwgMzcuNDcyNTYxMzYzMjc4MTI1XSwgWzEyNi45NDkyMjY2MTM4OTUwOCwgMzcuNDkxMjU0Mzc0OTU2NDldLCBbMTI2LjkwNTMxOTc1ODAxODEyLCAzNy40ODIxODA4NzU3NTQyOV0sIFsxMjYuOTIxNzc4OTMxNzQ4MjUsIDM3LjQ5NDg4OTg3NzQxNTE3Nl0sIFsxMjYuOTI4MTA2Mjg4MjgyNzksIDM3LjUxMzI5NTk1NzMyMDE1XSwgWzEyNi45NTI0OTk5MDI5ODE1OSwgMzcuNTE3MjI1MDA3NDE4MTNdLCBbMTI2Ljk4MjIzODA3OTE2MDgxLCAzNy41MDkzMTQ5NjY3NzAzMjZdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YjNkOVx1Yzc5MVx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMjAwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWIzZDlcdWM3OTFcdWFkNmMiLCAibmFtZV9lbmciOiAiRG9uZ2phay1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNkNGI5ZGEiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNi44OTE4NDY2Mzg2Mjc2NCwgMzcuNTQ3MzczOTc0OTk3MTE0XSwgWzEyNi45NDU2NjczMzA4MzIxMiwgMzcuNTI2NjE3NTQyNDUzMzY2XSwgWzEyNi45NTI0OTk5MDI5ODE1OSwgMzcuNTE3MjI1MDA3NDE4MTNdLCBbMTI2LjkyODEwNjI4ODI4Mjc5LCAzNy41MTMyOTU5NTczMjAxNV0sIFsxMjYuOTIxNzc4OTMxNzQ4MjUsIDM3LjQ5NDg4OTg3NzQxNTE3Nl0sIFsxMjYuOTA1MzE5NzU4MDE4MTIsIDM3LjQ4MjE4MDg3NTc1NDI5XSwgWzEyNi44OTU5NDc3Njc4MjQ4NSwgMzcuNTA0Njc1MjgxMzA5MTc2XSwgWzEyNi44ODE1NjQwMjM1Mzg2MiwgMzcuNTEzOTcwMDM0NzY1Njg0XSwgWzEyNi44ODgyNTc1Nzg2MDA5OSwgMzcuNTQwNzk3MzM2MzAyMzJdLCBbMTI2Ljg5MTg0NjYzODYyNzY0LCAzNy41NDczNzM5NzQ5OTcxMTRdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzYwMVx1YjRmMVx1ZDNlY1x1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMTkwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWM2MDFcdWI0ZjFcdWQzZWNcdWFkNmMiLCAibmFtZV9lbmciOiAiWWVvbmdkZXVuZ3BvLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiIzk4MDA0MyIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2LjkwMTU2MDk0MTI5ODk1LCAzNy40Nzc1Mzg0Mjc4OTkwMV0sIFsxMjYuOTE2NzcyODE0NjYwMSwgMzcuNDU0OTA1NjY0MjM3ODldLCBbMTI2LjkzMDg0NDA4MDU2NTI1LCAzNy40NDczODI5MjgzMzM5OTRdLCBbMTI2LjkwMjU4MzE3MTE2OTcsIDM3LjQzNDU0OTM2NjM0OTEyNF0sIFsxMjYuODc2ODMyNzE1MDI0MjgsIDM3LjQ4MjU3NjU5MTYwNzMwNV0sIFsxMjYuOTAxNTYwOTQxMjk4OTUsIDM3LjQ3NzUzODQyNzg5OTAxXV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJcdWFlMDhcdWNjOWNcdWFkNmMiLCAicHJvcGVydGllcyI6IHsiYmFzZV95ZWFyIjogIjIwMTMiLCAiY29kZSI6ICIxMTE4MCIsICJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiXHVhZTA4XHVjYzljXHVhZDZjIiwgIm5hbWVfZW5nIjogIkdldW1jaGVvbi1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNmMWVlZjYiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNi44MjY4ODA4MTUxNzMxNCwgMzcuNTA1NDg5NzIyMzI4OTZdLCBbMTI2Ljg4MTU2NDAyMzUzODYyLCAzNy41MTM5NzAwMzQ3NjU2ODRdLCBbMTI2Ljg5NTk0Nzc2NzgyNDg1LCAzNy41MDQ2NzUyODEzMDkxNzZdLCBbMTI2LjkwNTMxOTc1ODAxODEyLCAzNy40ODIxODA4NzU3NTQyOV0sIFsxMjYuOTAxNTYwOTQxMjk4OTUsIDM3LjQ3NzUzODQyNzg5OTAxXSwgWzEyNi44NzY4MzI3MTUwMjQyOCwgMzcuNDgyNTc2NTkxNjA3MzA1XSwgWzEyNi44NDc2MjY3NjA1NDk1MywgMzcuNDcxNDY3MjM5MzYzMjNdLCBbMTI2LjgzNTQ5NDg1MDc2MTk2LCAzNy40NzQwOTgyMzY5NzUwOTVdLCBbMTI2LjgyMjY0Nzk2NzkxMzQ4LCAzNy40ODc4NDc2NDkyMTQ3XSwgWzEyNi44MjUwNDczNjMzMTQwNiwgMzcuNTAzMDI2MTI2NDA0NDNdLCBbMTI2LjgyNjg4MDgxNTE3MzE0LCAzNy41MDU0ODk3MjIzMjg5Nl1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVhZDZjXHViODVjXHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTExNzAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YWQ2Y1x1Yjg1Y1x1YWQ2YyIsICJuYW1lX2VuZyI6ICJHdXJvLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2RmNjViMCIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2Ljc5NTc1NzY4NTUyOTA3LCAzNy41Nzg4MTA4NzYzMzIwMl0sIFsxMjYuODA3MDIxMTUwMjM1OTcsIDM3LjYwMTIzMDAxMDEzMjI4XSwgWzEyNi44MjI1MTQzODQ3NzEwNSwgMzcuNTg4MDQzMDgxMDA4Ml0sIFsxMjYuODU5ODQxOTkzOTk2NjcsIDM3LjU3MTg0Nzg1NTI5Mjc0NV0sIFsxMjYuODkxODQ2NjM4NjI3NjQsIDM3LjU0NzM3Mzk3NDk5NzExNF0sIFsxMjYuODg4MjU3NTc4NjAwOTksIDM3LjU0MDc5NzMzNjMwMjMyXSwgWzEyNi44NjYzNzQ2NDMyMTIzOCwgMzcuNTQ4NTkxOTEwOTQ4MjNdLCBbMTI2Ljg2NjEwMDczNDc2Mzk1LCAzNy41MjY5OTk2NDE0NDY2OV0sIFsxMjYuODQyNTcyOTE5NDMxNTMsIDM3LjUyMzczNzA3ODA1NTk2XSwgWzEyNi44MjQyMzMxNDI2NzIyLCAzNy41Mzc4ODA3ODc1MzI0OF0sIFsxMjYuNzczMjQ0MTc3MTc3MDMsIDM3LjU0NTkxMjM0NTA1NTRdLCBbMTI2Ljc2OTc5MTgwNTc5MzUyLCAzNy41NTEzOTE4MzAwODgwOV0sIFsxMjYuNzk1NzU3Njg1NTI5MDcsIDM3LjU3ODgxMDg3NjMzMjAyXV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJcdWFjMTVcdWMxMWNcdWFkNmMiLCAicHJvcGVydGllcyI6IHsiYmFzZV95ZWFyIjogIjIwMTMiLCAiY29kZSI6ICIxMTE2MCIsICJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiXHVhYzE1XHVjMTFjXHVhZDZjIiwgIm5hbWVfZW5nIjogIkdhbmdzZW8tZ3UiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICJibGFjayIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2LjgyNDIzMzE0MjY3MjIsIDM3LjUzNzg4MDc4NzUzMjQ4XSwgWzEyNi44NDI1NzI5MTk0MzE1MywgMzcuNTIzNzM3MDc4MDU1OTZdLCBbMTI2Ljg2NjEwMDczNDc2Mzk1LCAzNy41MjY5OTk2NDE0NDY2OV0sIFsxMjYuODY2Mzc0NjQzMjEyMzgsIDM3LjU0ODU5MTkxMDk0ODIzXSwgWzEyNi44ODgyNTc1Nzg2MDA5OSwgMzcuNTQwNzk3MzM2MzAyMzJdLCBbMTI2Ljg4MTU2NDAyMzUzODYyLCAzNy41MTM5NzAwMzQ3NjU2ODRdLCBbMTI2LjgyNjg4MDgxNTE3MzE0LCAzNy41MDU0ODk3MjIzMjg5Nl0sIFsxMjYuODI0MjMzMTQyNjcyMiwgMzcuNTM3ODgwNzg3NTMyNDhdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzU5MVx1Y2M5Y1x1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMTUwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWM1OTFcdWNjOWNcdWFkNmMiLCAibmFtZV9lbmciOiAiWWFuZ2NoZW9uLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2RkMWM3NyIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2LjkwNTIyMDY1ODMxMDUzLCAzNy41NzQwOTcwMDUyMjU3NF0sIFsxMjYuOTM4OTgxNjE3OTg5NzMsIDM3LjU1MjMxMDAwMzcyODEyNF0sIFsxMjYuOTYzNTgyMjY3MTA4MTIsIDM3LjU1NjA1NjM1NDc1MTU0XSwgWzEyNi45NjQ0ODU3MDU1MzA1NSwgMzcuNTQ4NzA1NjkyMDIxNjM1XSwgWzEyNi45NDU2NjczMzA4MzIxMiwgMzcuNTI2NjE3NTQyNDUzMzY2XSwgWzEyNi44OTE4NDY2Mzg2Mjc2NCwgMzcuNTQ3MzczOTc0OTk3MTE0XSwgWzEyNi44NTk4NDE5OTM5OTY2NywgMzcuNTcxODQ3ODU1MjkyNzQ1XSwgWzEyNi44ODQzMzI4NDc3MzI4OCwgMzcuNTg4MTQzMzIyODgwNTI2XSwgWzEyNi45MDUyMjA2NTgzMTA1MywgMzcuNTc0MDk3MDA1MjI1NzRdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YjljOFx1ZDNlY1x1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMTQwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWI5YzhcdWQzZWNcdWFkNmMiLCAibmFtZV9lbmciOiAiTWFwby1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNkZjY1YjAiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNi45NTI0NzUyMDMwNTcyLCAzNy42MDUwODY5MjczNzA0NV0sIFsxMjYuOTU1NjU0MjU4NDY0NjMsIDM3LjU3NjA4MDc5MDg4MTQ1Nl0sIFsxMjYuOTY4NzM2MzMyNzkwNzUsIDM3LjU2MzEzNjA0NjkwODI3XSwgWzEyNi45NjM1ODIyNjcxMDgxMiwgMzcuNTU2MDU2MzU0NzUxNTRdLCBbMTI2LjkzODk4MTYxNzk4OTczLCAzNy41NTIzMTAwMDM3MjgxMjRdLCBbMTI2LjkwNTIyMDY1ODMxMDUzLCAzNy41NzQwOTcwMDUyMjU3NF0sIFsxMjYuOTUyNDc1MjAzMDU3MiwgMzcuNjA1MDg2OTI3MzcwNDVdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzExY1x1YjMwMFx1YmIzOFx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMTMwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWMxMWNcdWIzMDBcdWJiMzhcdWFkNmMiLCAibmFtZV9lbmciOiAiU2VvZGFlbXVuLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2YxZWVmNiIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2Ljk3Mzg4NjQxMjg3MDIsIDM3LjYyOTQ5NjM0Nzg2ODg4XSwgWzEyNi45NTQyNzAxNzAwNjEyOSwgMzcuNjIyMDMzNDMxMzM5NDI1XSwgWzEyNi45NTI0NzUyMDMwNTcyLCAzNy42MDUwODY5MjczNzA0NV0sIFsxMjYuOTA1MjIwNjU4MzEwNTMsIDM3LjU3NDA5NzAwNTIyNTc0XSwgWzEyNi44ODQzMzI4NDc3MzI4OCwgMzcuNTg4MTQzMzIyODgwNTI2XSwgWzEyNi45MDM5NjY4MTAwMzU5NSwgMzcuNTkyMjc0MDM0MTk5NDJdLCBbMTI2LjkwMzAzMDY2MTc3NjY4LCAzNy42MDk5Nzc5MTE0MDEzNDRdLCBbMTI2LjkxNDU1NDgxNDI5NjQ4LCAzNy42NDE1MDA1MDk5NjkzNV0sIFsxMjYuOTU2NDczNzk3Mzg3LCAzNy42NTI0ODA3MzczMzk0NDVdLCBbMTI2Ljk3Mzg4NjQxMjg3MDIsIDM3LjYyOTQ5NjM0Nzg2ODg4XV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJcdWM3NDBcdWQzYzlcdWFkNmMiLCAicHJvcGVydGllcyI6IHsiYmFzZV95ZWFyIjogIjIwMTMiLCAiY29kZSI6ICIxMTEyMCIsICJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiXHVjNzQwXHVkM2M5XHVhZDZjIiwgIm5hbWVfZW5nIjogIkV1bnB5ZW9uZy1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNmMWVlZjYiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wODM4NzUyNzAzMTk1LCAzNy42OTM1OTUzNDIwMjAzNF0sIFsxMjcuMDk3MDYzOTEzMDk2OTUsIDM3LjY4NjM4MzcxOTM3MjI5NF0sIFsxMjcuMDk0NDA3NjYyOTg3MTcsIDM3LjY0NzEzNDkwNDczMDQ1XSwgWzEyNy4xMTMyNjc5NTg1NTE5OSwgMzcuNjM5NjIyOTA1MzE1OTI1XSwgWzEyNy4xMDc4MjI3NzY4ODEyOSwgMzcuNjE4MDQyNDQyNDEwNjldLCBbMTI3LjA3MzUxMjQzODI1Mjc4LCAzNy42MTI4MzY2MDM0MjMxM10sIFsxMjcuMDUyMDkzNzM1Njg2MTksIDM3LjYyMTY0MDY1NDg3NzgyXSwgWzEyNy4wNDM1ODgwMDg5NTYwOSwgMzcuNjI4NDg5MzEyOTg3MTVdLCBbMTI3LjA1ODAwMDc1MjIwMDkxLCAzNy42NDMxODI2Mzg3ODI3Nl0sIFsxMjcuMDUyODg0Nzk3MTA0ODUsIDM3LjY4NDIzODU3MDg0MzQ3XSwgWzEyNy4wODM4NzUyNzAzMTk1LCAzNy42OTM1OTUzNDIwMjAzNF1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHViMTc4XHVjNmQwXHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTExMTAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YjE3OFx1YzZkMFx1YWQ2YyIsICJuYW1lX2VuZyI6ICJOb3dvbi1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNkZDFjNzciLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wNTI4ODQ3OTcxMDQ4NSwgMzcuNjg0MjM4NTcwODQzNDddLCBbMTI3LjA1ODAwMDc1MjIwMDkxLCAzNy42NDMxODI2Mzg3ODI3Nl0sIFsxMjcuMDQzNTg4MDA4OTU2MDksIDM3LjYyODQ4OTMxMjk4NzE1XSwgWzEyNy4wMTQ2NTkzNTg5MjQ2NiwgMzcuNjQ5NDM2ODc0OTY4MTJdLCBbMTI3LjAyMDYyMTE2MTQxMzg5LCAzNy42NjcxNzM1NzU5NzEyMDVdLCBbMTI3LjAxMDM5NjY2MDQyMDcxLCAzNy42ODE4OTQ1ODk2MDM1OTRdLCBbMTI3LjAxNzk1MDk5MjAzNDMyLCAzNy42OTgyNDQxMjc3NTY2Ml0sIFsxMjcuMDUyODg0Nzk3MTA0ODUsIDM3LjY4NDIzODU3MDg0MzQ3XV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJcdWIzYzRcdWJkMDlcdWFkNmMiLCAicHJvcGVydGllcyI6IHsiYmFzZV95ZWFyIjogIjIwMTMiLCAiY29kZSI6ICIxMTEwMCIsICJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiXHViM2M0XHViZDA5XHVhZDZjIiwgIm5hbWVfZW5nIjogIkRvYm9uZy1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNmMWVlZjYiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNi45OTM4MzkwMzQyNCwgMzcuNjc2NjgxNzYxMTk5MDg1XSwgWzEyNy4wMTAzOTY2NjA0MjA3MSwgMzcuNjgxODk0NTg5NjAzNTk0XSwgWzEyNy4wMjA2MjExNjE0MTM4OSwgMzcuNjY3MTczNTc1OTcxMjA1XSwgWzEyNy4wMTQ2NTkzNTg5MjQ2NiwgMzcuNjQ5NDM2ODc0OTY4MTJdLCBbMTI3LjA0MzU4ODAwODk1NjA5LCAzNy42Mjg0ODkzMTI5ODcxNV0sIFsxMjcuMDUyMDkzNzM1Njg2MTksIDM3LjYyMTY0MDY1NDg3NzgyXSwgWzEyNy4wMzg5MjQwMDk5MjMwMSwgMzcuNjA5NzE1NjExMDIzODE2XSwgWzEyNy4wMTI4MTU0NzQ5NTIzLCAzNy42MTM2NTIyNDM0NzAyNTZdLCBbMTI2Ljk4NjcyNzA1NTEzODY5LCAzNy42MzM3NzY0MTI4ODE5Nl0sIFsxMjYuOTgxNzQ1MjY3NjU1MSwgMzcuNjUyMDk3NjkzODc3NzZdLCBbMTI2Ljk5MzgzOTAzNDI0LCAzNy42NzY2ODE3NjExOTkwODVdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YWMxNVx1YmQ4MVx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMDkwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWFjMTVcdWJkODFcdWFkNmMiLCAibmFtZV9lbmciOiAiR2FuZ2J1ay1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNjOTk0YzciLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNi45NzcxNzU0MDY0MTYsIDM3LjYyODU5NzE1NDAwMzg4XSwgWzEyNi45ODY3MjcwNTUxMzg2OSwgMzcuNjMzNzc2NDEyODgxOTZdLCBbMTI3LjAxMjgxNTQ3NDk1MjMsIDM3LjYxMzY1MjI0MzQ3MDI1Nl0sIFsxMjcuMDM4OTI0MDA5OTIzMDEsIDM3LjYwOTcxNTYxMTAyMzgxNl0sIFsxMjcuMDUyMDkzNzM1Njg2MTksIDM3LjYyMTY0MDY1NDg3NzgyXSwgWzEyNy4wNzM1MTI0MzgyNTI3OCwgMzcuNjEyODM2NjAzNDIzMTNdLCBbMTI3LjA3MzgyNzA3MDk5MjI3LCAzNy42MDQwMTkyODk4NjQxOV0sIFsxMjcuMDQyNzA1MjIyMDk0LCAzNy41OTIzOTQzNzU5MzM5MV0sIFsxMjcuMDI1MjcyNTQ1MjgwMDMsIDM3LjU3NTI0NjE2MjQ1MjQ5XSwgWzEyNi45OTM0ODI5MzM1ODMxNCwgMzcuNTg4NTY1NDU3MjE2MTU2XSwgWzEyNi45ODg3OTg2NTk5MjM4NCwgMzcuNjExODkyNzMxOTc1Nl0sIFsxMjYuOTc3MTc1NDA2NDE2LCAzNy42Mjg1OTcxNTQwMDM4OF1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVjMTMxXHViZDgxXHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEwODAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YzEzMVx1YmQ4MVx1YWQ2YyIsICJuYW1lX2VuZyI6ICJTZW9uZ2J1ay1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNkNGI5ZGEiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wNzM1MTI0MzgyNTI3OCwgMzcuNjEyODM2NjAzNDIzMTNdLCBbMTI3LjEwNzgyMjc3Njg4MTI5LCAzNy42MTgwNDI0NDI0MTA2OV0sIFsxMjcuMTIwMTI0NjAyMDExNCwgMzcuNjAxNzg0NTc1OTgxODhdLCBbMTI3LjEwMzA0MTc0MjQ5MjE0LCAzNy41NzA3NjM0MjI5MDk1NV0sIFsxMjcuMDgwNjg1NDEyODA0MDMsIDM3LjU2OTA2NDI1NTE5MDE3XSwgWzEyNy4wNzM4MjcwNzA5OTIyNywgMzcuNjA0MDE5Mjg5ODY0MTldLCBbMTI3LjA3MzUxMjQzODI1Mjc4LCAzNy42MTI4MzY2MDM0MjMxM11dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVjOTExXHViNzkxXHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEwNzAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YzkxMVx1Yjc5MVx1YWQ2YyIsICJuYW1lX2VuZyI6ICJKdW5nbmFuZy1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiM5ODAwNDMiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wMjUyNzI1NDUyODAwMywgMzcuNTc1MjQ2MTYyNDUyNDldLCBbMTI3LjA0MjcwNTIyMjA5NCwgMzcuNTkyMzk0Mzc1OTMzOTFdLCBbMTI3LjA3MzgyNzA3MDk5MjI3LCAzNy42MDQwMTkyODk4NjQxOV0sIFsxMjcuMDgwNjg1NDEyODA0MDMsIDM3LjU2OTA2NDI1NTE5MDE3XSwgWzEyNy4wNzQyMTA1MzAyNDM2MiwgMzcuNTU3MjQ3Njk3MTIwODVdLCBbMTI3LjA1MDA1NjAxMDgxNTY3LCAzNy41Njc1Nzc2MTI1OTA4NDZdLCBbMTI3LjAyNTQ3MjY2MzQ5OTc2LCAzNy41Njg5NDM1NTIyMzc3MzRdLCBbMTI3LjAyNTI3MjU0NTI4MDAzLCAzNy41NzUyNDYxNjI0NTI0OV1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHViM2Q5XHViMzAwXHViYjM4XHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEwNjAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YjNkOVx1YjMwMFx1YmIzOFx1YWQ2YyIsICJuYW1lX2VuZyI6ICJEb25nZGFlbXVuLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2Q0YjlkYSIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI3LjA4MDY4NTQxMjgwNDAzLCAzNy41NjkwNjQyNTUxOTAxN10sIFsxMjcuMTAzMDQxNzQyNDkyMTQsIDM3LjU3MDc2MzQyMjkwOTU1XSwgWzEyNy4xMTUxOTU4NDk4MTYwNiwgMzcuNTU3NTMzMTgwNzA0OTE1XSwgWzEyNy4xMTE2NzY0MjAzNjA4LCAzNy41NDA2Njk5NTUzMjQ5NjVdLCBbMTI3LjEwMDg3NTE5NzkxOTYyLCAzNy41MjQ4NDEyMjAxNjcwNTVdLCBbMTI3LjA2OTA2OTgxMzAzNzIsIDM3LjUyMjI3OTQyMzUwNTAyNl0sIFsxMjcuMDU4NjczNTkyODgzOTgsIDM3LjUyNjI5OTc0OTIyNTY4XSwgWzEyNy4wNzQyMTA1MzAyNDM2MiwgMzcuNTU3MjQ3Njk3MTIwODVdLCBbMTI3LjA4MDY4NTQxMjgwNDAzLCAzNy41NjkwNjQyNTUxOTAxN11dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVhZDExXHVjOWM0XHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEwNTAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YWQxMVx1YzljNFx1YWQ2YyIsICJuYW1lX2VuZyI6ICJHd2FuZ2ppbi1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNkNGI5ZGEiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wMjU0NzI2NjM0OTk3NiwgMzcuNTY4OTQzNTUyMjM3NzM0XSwgWzEyNy4wNTAwNTYwMTA4MTU2NywgMzcuNTY3NTc3NjEyNTkwODQ2XSwgWzEyNy4wNzQyMTA1MzAyNDM2MiwgMzcuNTU3MjQ3Njk3MTIwODVdLCBbMTI3LjA1ODY3MzU5Mjg4Mzk4LCAzNy41MjYyOTk3NDkyMjU2OF0sIFsxMjcuMDIzMDI4MzE4OTA1NTksIDM3LjUzMjMxODk5NTgyNjYzXSwgWzEyNy4wMTA3MDg5NDE3NzQ4MiwgMzcuNTQxMTgwNDg5NjQ3NjJdLCBbMTI3LjAyNTQ3MjY2MzQ5OTc2LCAzNy41Njg5NDM1NTIyMzc3MzRdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzEzMVx1YjNkOVx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMDQwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWMxMzFcdWIzZDlcdWFkNmMiLCAibmFtZV9lbmciOiAiU2Vvbmdkb25nLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2Q0YjlkYSIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI3LjAxMDcwODk0MTc3NDgyLCAzNy41NDExODA0ODk2NDc2Ml0sIFsxMjcuMDIzMDI4MzE4OTA1NTksIDM3LjUzMjMxODk5NTgyNjYzXSwgWzEyNy4wMTM5NzExOTY2NzUxMywgMzcuNTI1MDM5ODgyODk2NjldLCBbMTI2Ljk4MjIzODA3OTE2MDgxLCAzNy41MDkzMTQ5NjY3NzAzMjZdLCBbMTI2Ljk1MjQ5OTkwMjk4MTU5LCAzNy41MTcyMjUwMDc0MTgxM10sIFsxMjYuOTQ1NjY3MzMwODMyMTIsIDM3LjUyNjYxNzU0MjQ1MzM2Nl0sIFsxMjYuOTY0NDg1NzA1NTMwNTUsIDM3LjU0ODcwNTY5MjAyMTYzNV0sIFsxMjYuOTg3NTI5OTY5MDMzMjgsIDM3LjU1MDk0ODE4ODA3MTM5XSwgWzEyNy4wMTA3MDg5NDE3NzQ4MiwgMzcuNTQxMTgwNDg5NjQ3NjJdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzZhOVx1YzBiMFx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMDMwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWM2YTlcdWMwYjBcdWFkNmMiLCAibmFtZV9lbmciOiAiWW9uZ3Nhbi1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNkNGI5ZGEiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wMjU0NzI2NjM0OTk3NiwgMzcuNTY4OTQzNTUyMjM3NzM0XSwgWzEyNy4wMTA3MDg5NDE3NzQ4MiwgMzcuNTQxMTgwNDg5NjQ3NjJdLCBbMTI2Ljk4NzUyOTk2OTAzMzI4LCAzNy41NTA5NDgxODgwNzEzOV0sIFsxMjYuOTY0NDg1NzA1NTMwNTUsIDM3LjU0ODcwNTY5MjAyMTYzNV0sIFsxMjYuOTYzNTgyMjY3MTA4MTIsIDM3LjU1NjA1NjM1NDc1MTU0XSwgWzEyNi45Njg3MzYzMzI3OTA3NSwgMzcuNTYzMTM2MDQ2OTA4MjddLCBbMTI3LjAyNTQ3MjY2MzQ5OTc2LCAzNy41Njg5NDM1NTIyMzc3MzRdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzkxMVx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMDIwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWM5MTFcdWFkNmMiLCAibmFtZV9lbmciOiAiSnVuZy1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNmMWVlZjYiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNi45NzM4ODY0MTI4NzAyLCAzNy42Mjk0OTYzNDc4Njg4OF0sIFsxMjYuOTc3MTc1NDA2NDE2LCAzNy42Mjg1OTcxNTQwMDM4OF0sIFsxMjYuOTg4Nzk4NjU5OTIzODQsIDM3LjYxMTg5MjczMTk3NTZdLCBbMTI2Ljk5MzQ4MjkzMzU4MzE0LCAzNy41ODg1NjU0NTcyMTYxNTZdLCBbMTI3LjAyNTI3MjU0NTI4MDAzLCAzNy41NzUyNDYxNjI0NTI0OV0sIFsxMjcuMDI1NDcyNjYzNDk5NzYsIDM3LjU2ODk0MzU1MjIzNzczNF0sIFsxMjYuOTY4NzM2MzMyNzkwNzUsIDM3LjU2MzEzNjA0NjkwODI3XSwgWzEyNi45NTU2NTQyNTg0NjQ2MywgMzcuNTc2MDgwNzkwODgxNDU2XSwgWzEyNi45NTI0NzUyMDMwNTcyLCAzNy42MDUwODY5MjczNzA0NV0sIFsxMjYuOTU0MjcwMTcwMDYxMjksIDM3LjYyMjAzMzQzMTMzOTQyNV0sIFsxMjYuOTczODg2NDEyODcwMiwgMzcuNjI5NDk2MzQ3ODY4ODhdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1Yzg4NVx1Yjg1Y1x1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMDEwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWM4ODVcdWI4NWNcdWFkNmMiLCAibmFtZV9lbmciOiAiSm9uZ25vLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2M5OTRjNyIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn1dLCAidHlwZSI6ICJGZWF0dXJlQ29sbGVjdGlvbiJ9CiAgICAgICAgICAgIAogICAgICAgICAgICApLmFkZFRvKGNob3JvcGxldGhfODVkZWEwOGQzZGE1NDUxODhlODBiYTBkZTcwOWYyYWMpOwogICAgICAgIGdlb19qc29uX2U0NTJmODBjZDM0MjQzNTc4OTZhZTQ3MGI1NTQzZGM0LnNldFN0eWxlKGZ1bmN0aW9uKGZlYXR1cmUpIHtyZXR1cm4gZmVhdHVyZS5wcm9wZXJ0aWVzLnN0eWxlO30pOwogICAgICAgIAogICAgCiAgICB2YXIgY29sb3JfbWFwX2JiMDdhMGI2ZDE0NjQ0MWI5NWM0OTU1YWNiNGVhMmFlID0ge307CgogICAgCiAgICBjb2xvcl9tYXBfYmIwN2EwYjZkMTQ2NDQxYjk1YzQ5NTVhY2I0ZWEyYWUuY29sb3IgPSBkMy5zY2FsZS50aHJlc2hvbGQoKQogICAgICAgICAgICAgIC5kb21haW4oWzAuMCwgMC4wMDIwMDQwMDgwMTYwMzIwNjQsIDAuMDA0MDA4MDE2MDMyMDY0MTI4LCAwLjAwNjAxMjAyNDA0ODA5NjE5MTUsIDAuMDA4MDE2MDMyMDY0MTI4MjU2LCAwLjAxMDAyMDA0MDA4MDE2MDMxOSwgMC4wMTIwMjQwNDgwOTYxOTIzODMsIDAuMDE0MDI4MDU2MTEyMjI0NDQ3LCAwLjAxNjAzMjA2NDEyODI1NjUxLCAwLjAxODAzNjA3MjE0NDI4ODU3NCwgMC4wMjAwNDAwODAxNjAzMjA2MzcsIDAuMDIyMDQ0MDg4MTc2MzUyNzAzLCAwLjAyNDA0ODA5NjE5MjM4NDc2NiwgMC4wMjYwNTIxMDQyMDg0MTY4MywgMC4wMjgwNTYxMTIyMjQ0NDg4OTUsIDAuMDMwMDYwMTIwMjQwNDgwOTU3LCAwLjAzMjA2NDEyODI1NjUxMzAyLCAwLjAzNDA2ODEzNjI3MjU0NTA4LCAwLjAzNjA3MjE0NDI4ODU3NzE1LCAwLjAzODA3NjE1MjMwNDYwOTIxLCAwLjA0MDA4MDE2MDMyMDY0MTI3NCwgMC4wNDIwODQxNjgzMzY2NzMzNCwgMC4wNDQwODgxNzYzNTI3MDU0MDYsIDAuMDQ2MDkyMTg0MzY4NzM3NDY1LCAwLjA0ODA5NjE5MjM4NDc2OTUzLCAwLjA1MDEwMDIwMDQwMDgwMTYsIDAuMDUyMTA0MjA4NDE2ODMzNjYsIDAuMDU0MTA4MjE2NDMyODY1NzIsIDAuMDU2MTEyMjI0NDQ4ODk3NzksIDAuMDU4MTE2MjMyNDY0OTI5ODU1LCAwLjA2MDEyMDI0MDQ4MDk2MTkxNSwgMC4wNjIxMjQyNDg0OTY5OTM5OCwgMC4wNjQxMjgyNTY1MTMwMjYwNSwgMC4wNjYxMzIyNjQ1MjkwNTgxLCAwLjA2ODEzNjI3MjU0NTA5MDE3LCAwLjA3MDE0MDI4MDU2MTEyMjIzLCAwLjA3MjE0NDI4ODU3NzE1NDMsIDAuMDc0MTQ4Mjk2NTkzMTg2MzYsIDAuMDc2MTUyMzA0NjA5MjE4NDIsIDAuMDc4MTU2MzEyNjI1MjUwNDgsIDAuMDgwMTYwMzIwNjQxMjgyNTUsIDAuMDgyMTY0MzI4NjU3MzE0NjEsIDAuMDg0MTY4MzM2NjczMzQ2NjgsIDAuMDg2MTcyMzQ0Njg5Mzc4NzUsIDAuMDg4MTc2MzUyNzA1NDEwODEsIDAuMDkwMTgwMzYwNzIxNDQyODYsIDAuMDkyMTg0MzY4NzM3NDc0OTMsIDAuMDk0MTg4Mzc2NzUzNTA3LCAwLjA5NjE5MjM4NDc2OTUzOTA2LCAwLjA5ODE5NjM5Mjc4NTU3MTEzLCAwLjEwMDIwMDQwMDgwMTYwMzIsIDAuMTAyMjA0NDA4ODE3NjM1MjYsIDAuMTA0MjA4NDE2ODMzNjY3MzEsIDAuMTA2MjEyNDI0ODQ5Njk5MzgsIDAuMTA4MjE2NDMyODY1NzMxNDUsIDAuMTEwMjIwNDQwODgxNzYzNTEsIDAuMTEyMjI0NDQ4ODk3Nzk1NTgsIDAuMTE0MjI4NDU2OTEzODI3NjQsIDAuMTE2MjMyNDY0OTI5ODU5NzEsIDAuMTE4MjM2NDcyOTQ1ODkxNzYsIDAuMTIwMjQwNDgwOTYxOTIzODMsIDAuMTIyMjQ0NDg4OTc3OTU1OSwgMC4xMjQyNDg0OTY5OTM5ODc5NiwgMC4xMjYyNTI1MDUwMTAwMjAwMywgMC4xMjgyNTY1MTMwMjYwNTIxLCAwLjEzMDI2MDUyMTA0MjA4NDEzLCAwLjEzMjI2NDUyOTA1ODExNjIsIDAuMTM0MjY4NTM3MDc0MTQ4MjYsIDAuMTM2MjcyNTQ1MDkwMTgwMzMsIDAuMTM4Mjc2NTUzMTA2MjEyNCwgMC4xNDAyODA1NjExMjIyNDQ0NiwgMC4xNDIyODQ1NjkxMzgyNzY1MywgMC4xNDQyODg1NzcxNTQzMDg2LCAwLjE0NjI5MjU4NTE3MDM0MDY2LCAwLjE0ODI5NjU5MzE4NjM3MjczLCAwLjE1MDMwMDYwMTIwMjQwNDgsIDAuMTUyMzA0NjA5MjE4NDM2ODMsIDAuMTU0MzA4NjE3MjM0NDY4OSwgMC4xNTYzMTI2MjUyNTA1MDA5NiwgMC4xNTgzMTY2MzMyNjY1MzMwMywgMC4xNjAzMjA2NDEyODI1NjUxLCAwLjE2MjMyNDY0OTI5ODU5NzE2LCAwLjE2NDMyODY1NzMxNDYyOTIzLCAwLjE2NjMzMjY2NTMzMDY2MTMsIDAuMTY4MzM2NjczMzQ2NjkzMzYsIDAuMTcwMzQwNjgxMzYyNzI1NDMsIDAuMTcyMzQ0Njg5Mzc4NzU3NSwgMC4xNzQzNDg2OTczOTQ3ODk1NiwgMC4xNzYzNTI3MDU0MTA4MjE2MywgMC4xNzgzNTY3MTM0MjY4NTM3LCAwLjE4MDM2MDcyMTQ0Mjg4NTczLCAwLjE4MjM2NDcyOTQ1ODkxNzgsIDAuMTg0MzY4NzM3NDc0OTQ5ODYsIDAuMTg2MzcyNzQ1NDkwOTgxOTMsIDAuMTg4Mzc2NzUzNTA3MDE0LCAwLjE5MDM4MDc2MTUyMzA0NjA2LCAwLjE5MjM4NDc2OTUzOTA3ODEzLCAwLjE5NDM4ODc3NzU1NTExMDIsIDAuMTk2MzkyNzg1NTcxMTQyMjYsIDAuMTk4Mzk2NzkzNTg3MTc0MzIsIDAuMjAwNDAwODAxNjAzMjA2NCwgMC4yMDI0MDQ4MDk2MTkyMzg0NiwgMC4yMDQ0MDg4MTc2MzUyNzA1MiwgMC4yMDY0MTI4MjU2NTEzMDI2LCAwLjIwODQxNjgzMzY2NzMzNDYzLCAwLjIxMDQyMDg0MTY4MzM2NjcsIDAuMjEyNDI0ODQ5Njk5Mzk4NzYsIDAuMjE0NDI4ODU3NzE1NDMwODMsIDAuMjE2NDMyODY1NzMxNDYyOSwgMC4yMTg0MzY4NzM3NDc0OTQ5NiwgMC4yMjA0NDA4ODE3NjM1MjcwMiwgMC4yMjI0NDQ4ODk3Nzk1NTkxLCAwLjIyNDQ0ODg5Nzc5NTU5MTE2LCAwLjIyNjQ1MjkwNTgxMTYyMzIyLCAwLjIyODQ1NjkxMzgyNzY1NTMsIDAuMjMwNDYwOTIxODQzNjg3MzYsIDAuMjMyNDY0OTI5ODU5NzE5NDIsIDAuMjM0NDY4OTM3ODc1NzUxNSwgMC4yMzY0NzI5NDU4OTE3ODM1MywgMC4yMzg0NzY5NTM5MDc4MTU2LCAwLjI0MDQ4MDk2MTkyMzg0NzY2LCAwLjI0MjQ4NDk2OTkzOTg3OTcyLCAwLjI0NDQ4ODk3Nzk1NTkxMTgsIDAuMjQ2NDkyOTg1OTcxOTQzODYsIDAuMjQ4NDk2OTkzOTg3OTc1OTIsIDAuMjUwNTAxMDAyMDA0MDA4LCAwLjI1MjUwNTAxMDAyMDA0MDA1LCAwLjI1NDUwOTAxODAzNjA3MjEsIDAuMjU2NTEzMDI2MDUyMTA0MiwgMC4yNTg1MTcwMzQwNjgxMzYyLCAwLjI2MDUyMTA0MjA4NDE2ODI2LCAwLjI2MjUyNTA1MDEwMDIwMDMzLCAwLjI2NDUyOTA1ODExNjIzMjQsIDAuMjY2NTMzMDY2MTMyMjY0NDYsIDAuMjY4NTM3MDc0MTQ4Mjk2NTMsIDAuMjcwNTQxMDgyMTY0MzI4NiwgMC4yNzI1NDUwOTAxODAzNjA2NiwgMC4yNzQ1NDkwOTgxOTYzOTI3LCAwLjI3NjU1MzEwNjIxMjQyNDgsIDAuMjc4NTU3MTE0MjI4NDU2ODYsIDAuMjgwNTYxMTIyMjQ0NDg4OSwgMC4yODI1NjUxMzAyNjA1MjEsIDAuMjg0NTY5MTM4Mjc2NTUzMDYsIDAuMjg2NTczMTQ2MjkyNTg1MSwgMC4yODg1NzcxNTQzMDg2MTcyLCAwLjI5MDU4MTE2MjMyNDY0OTI2LCAwLjI5MjU4NTE3MDM0MDY4MTMsIDAuMjk0NTg5MTc4MzU2NzEzNCwgMC4yOTY1OTMxODYzNzI3NDU0NSwgMC4yOTg1OTcxOTQzODg3Nzc1LCAwLjMwMDYwMTIwMjQwNDgwOTYsIDAuMzAyNjA1MjEwNDIwODQxNjUsIDAuMzA0NjA5MjE4NDM2ODczNjYsIDAuMzA2NjEzMjI2NDUyOTA1NzMsIDAuMzA4NjE3MjM0NDY4OTM3OCwgMC4zMTA2MjEyNDI0ODQ5Njk4NiwgMC4zMTI2MjUyNTA1MDEwMDE5MywgMC4zMTQ2MjkyNTg1MTcwMzQsIDAuMzE2NjMzMjY2NTMzMDY2MDYsIDAuMzE4NjM3Mjc0NTQ5MDk4MSwgMC4zMjA2NDEyODI1NjUxMzAyLCAwLjMyMjY0NTI5MDU4MTE2MjI2LCAwLjMyNDY0OTI5ODU5NzE5NDMsIDAuMzI2NjUzMzA2NjEzMjI2NCwgMC4zMjg2NTczMTQ2MjkyNTg0NiwgMC4zMzA2NjEzMjI2NDUyOTA1LCAwLjMzMjY2NTMzMDY2MTMyMjYsIDAuMzM0NjY5MzM4Njc3MzU0NjYsIDAuMzM2NjczMzQ2NjkzMzg2NywgMC4zMzg2NzczNTQ3MDk0MTg4LCAwLjM0MDY4MTM2MjcyNTQ1MDg1LCAwLjM0MjY4NTM3MDc0MTQ4MjksIDAuMzQ0Njg5Mzc4NzU3NTE1LCAwLjM0NjY5MzM4Njc3MzU0NzA1LCAwLjM0ODY5NzM5NDc4OTU3OTEsIDAuMzUwNzAxNDAyODA1NjExMiwgMC4zNTI3MDU0MTA4MjE2NDMyNSwgMC4zNTQ3MDk0MTg4Mzc2NzUzLCAwLjM1NjcxMzQyNjg1MzcwNzQsIDAuMzU4NzE3NDM0ODY5NzM5NDUsIDAuMzYwNzIxNDQyODg1NzcxNDYsIDAuMzYyNzI1NDUwOTAxODAzNSwgMC4zNjQ3Mjk0NTg5MTc4MzU2LCAwLjM2NjczMzQ2NjkzMzg2NzY2LCAwLjM2ODczNzQ3NDk0OTg5OTcsIDAuMzcwNzQxNDgyOTY1OTMxOCwgMC4zNzI3NDU0OTA5ODE5NjM4NiwgMC4zNzQ3NDk0OTg5OTc5OTU5LCAwLjM3Njc1MzUwNzAxNDAyOCwgMC4zNzg3NTc1MTUwMzAwNjAwNSwgMC4zODA3NjE1MjMwNDYwOTIxLCAwLjM4Mjc2NTUzMTA2MjEyNDIsIDAuMzg0NzY5NTM5MDc4MTU2MjUsIDAuMzg2NzczNTQ3MDk0MTg4MywgMC4zODg3Nzc1NTUxMTAyMjA0LCAwLjM5MDc4MTU2MzEyNjI1MjQ1LCAwLjM5Mjc4NTU3MTE0MjI4NDUsIDAuMzk0Nzg5NTc5MTU4MzE2NiwgMC4zOTY3OTM1ODcxNzQzNDg2NSwgMC4zOTg3OTc1OTUxOTAzODA3LCAwLjQwMDgwMTYwMzIwNjQxMjgsIDAuNDAyODA1NjExMjIyNDQ0ODUsIDAuNDA0ODA5NjE5MjM4NDc2OSwgMC40MDY4MTM2MjcyNTQ1MDksIDAuNDA4ODE3NjM1MjcwNTQxMDUsIDAuNDEwODIxNjQzMjg2NTczMSwgMC40MTI4MjU2NTEzMDI2MDUyLCAwLjQxNDgyOTY1OTMxODYzNzI0LCAwLjQxNjgzMzY2NzMzNDY2OTI2LCAwLjQxODgzNzY3NTM1MDcwMTMsIDAuNDIwODQxNjgzMzY2NzMzNCwgMC40MjI4NDU2OTEzODI3NjU0NSwgMC40MjQ4NDk2OTkzOTg3OTc1LCAwLjQyNjg1MzcwNzQxNDgyOTYsIDAuNDI4ODU3NzE1NDMwODYxNjUsIDAuNDMwODYxNzIzNDQ2ODkzNywgMC40MzI4NjU3MzE0NjI5MjU4LCAwLjQzNDg2OTczOTQ3ODk1Nzg1LCAwLjQzNjg3Mzc0NzQ5NDk4OTksIDAuNDM4ODc3NzU1NTExMDIyLCAwLjQ0MDg4MTc2MzUyNzA1NDA1LCAwLjQ0Mjg4NTc3MTU0MzA4NjEsIDAuNDQ0ODg5Nzc5NTU5MTE4MiwgMC40NDY4OTM3ODc1NzUxNTAyNSwgMC40NDg4OTc3OTU1OTExODIzLCAwLjQ1MDkwMTgwMzYwNzIxNDQsIDAuNDUyOTA1ODExNjIzMjQ2NDUsIDAuNDU0OTA5ODE5NjM5Mjc4NSwgMC40NTY5MTM4Mjc2NTUzMTA2LCAwLjQ1ODkxNzgzNTY3MTM0MjY0LCAwLjQ2MDkyMTg0MzY4NzM3NDcsIDAuNDYyOTI1ODUxNzAzNDA2OCwgMC40NjQ5Mjk4NTk3MTk0Mzg4NCwgMC40NjY5MzM4Njc3MzU0NzA5LCAwLjQ2ODkzNzg3NTc1MTUwMywgMC40NzA5NDE4ODM3Njc1MzUsIDAuNDcyOTQ1ODkxNzgzNTY3MDUsIDAuNDc0OTQ5ODk5Nzk5NTk5MSwgMC40NzY5NTM5MDc4MTU2MzEyLCAwLjQ3ODk1NzkxNTgzMTY2MzI1LCAwLjQ4MDk2MTkyMzg0NzY5NTMsIDAuNDgyOTY1OTMxODYzNzI3NCwgMC40ODQ5Njk5Mzk4Nzk3NTk0NSwgMC40ODY5NzM5NDc4OTU3OTE1LCAwLjQ4ODk3Nzk1NTkxMTgyMzYsIDAuNDkwOTgxOTYzOTI3ODU1NjUsIDAuNDkyOTg1OTcxOTQzODg3NywgMC40OTQ5ODk5Nzk5NTk5MTk4LCAwLjQ5Njk5Mzk4Nzk3NTk1MTg1LCAwLjQ5ODk5Nzk5NTk5MTk4MzksIDAuNTAxMDAyMDA0MDA4MDE2LCAwLjUwMzAwNjAxMjAyNDA0OCwgMC41MDUwMTAwMjAwNDAwODAxLCAwLjUwNzAxNDAyODA1NjExMjIsIDAuNTA5MDE4MDM2MDcyMTQ0MiwgMC41MTEwMjIwNDQwODgxNzYzLCAwLjUxMzAyNjA1MjEwNDIwODQsIDAuNTE1MDMwMDYwMTIwMjQwMywgMC41MTcwMzQwNjgxMzYyNzI0LCAwLjUxOTAzODA3NjE1MjMwNDUsIDAuNTIxMDQyMDg0MTY4MzM2NSwgMC41MjMwNDYwOTIxODQzNjg2LCAwLjUyNTA1MDEwMDIwMDQwMDcsIDAuNTI3MDU0MTA4MjE2NDMyNywgMC41MjkwNTgxMTYyMzI0NjQ4LCAwLjUzMTA2MjEyNDI0ODQ5NjksIDAuNTMzMDY2MTMyMjY0NTI4OSwgMC41MzUwNzAxNDAyODA1NjEsIDAuNTM3MDc0MTQ4Mjk2NTkzMSwgMC41MzkwNzgxNTYzMTI2MjUxLCAwLjU0MTA4MjE2NDMyODY1NzIsIDAuNTQzMDg2MTcyMzQ0Njg5MywgMC41NDUwOTAxODAzNjA3MjEzLCAwLjU0NzA5NDE4ODM3Njc1MzQsIDAuNTQ5MDk4MTk2MzkyNzg1NSwgMC41NTExMDIyMDQ0MDg4MTc1LCAwLjU1MzEwNjIxMjQyNDg0OTYsIDAuNTU1MTEwMjIwNDQwODgxNywgMC41NTcxMTQyMjg0NTY5MTM3LCAwLjU1OTExODIzNjQ3Mjk0NTgsIDAuNTYxMTIyMjQ0NDg4OTc3OSwgMC41NjMxMjYyNTI1MDUwMDk5LCAwLjU2NTEzMDI2MDUyMTA0MiwgMC41NjcxMzQyNjg1MzcwNzQsIDAuNTY5MTM4Mjc2NTUzMTA2MSwgMC41NzExNDIyODQ1NjkxMzgyLCAwLjU3MzE0NjI5MjU4NTE3MDIsIDAuNTc1MTUwMzAwNjAxMjAyMywgMC41NzcxNTQzMDg2MTcyMzQ0LCAwLjU3OTE1ODMxNjYzMzI2NjQsIDAuNTgxMTYyMzI0NjQ5Mjk4NSwgMC41ODMxNjYzMzI2NjUzMzA2LCAwLjU4NTE3MDM0MDY4MTM2MjYsIDAuNTg3MTc0MzQ4Njk3Mzk0NywgMC41ODkxNzgzNTY3MTM0MjY4LCAwLjU5MTE4MjM2NDcyOTQ1ODgsIDAuNTkzMTg2MzcyNzQ1NDkwOSwgMC41OTUxOTAzODA3NjE1MjMsIDAuNTk3MTk0Mzg4Nzc3NTU1LCAwLjU5OTE5ODM5Njc5MzU4NzEsIDAuNjAxMjAyNDA0ODA5NjE5MiwgMC42MDMyMDY0MTI4MjU2NTEyLCAwLjYwNTIxMDQyMDg0MTY4MzMsIDAuNjA3MjE0NDI4ODU3NzE1NCwgMC42MDkyMTg0MzY4NzM3NDczLCAwLjYxMTIyMjQ0NDg4OTc3OTQsIDAuNjEzMjI2NDUyOTA1ODExNSwgMC42MTUyMzA0NjA5MjE4NDM1LCAwLjYxNzIzNDQ2ODkzNzg3NTYsIDAuNjE5MjM4NDc2OTUzOTA3NywgMC42MjEyNDI0ODQ5Njk5Mzk3LCAwLjYyMzI0NjQ5Mjk4NTk3MTgsIDAuNjI1MjUwNTAxMDAyMDAzOSwgMC42MjcyNTQ1MDkwMTgwMzU5LCAwLjYyOTI1ODUxNzAzNDA2OCwgMC42MzEyNjI1MjUwNTAxLCAwLjYzMzI2NjUzMzA2NjEzMjEsIDAuNjM1MjcwNTQxMDgyMTY0MiwgMC42MzcyNzQ1NDkwOTgxOTYzLCAwLjYzOTI3ODU1NzExNDIyODMsIDAuNjQxMjgyNTY1MTMwMjYwNCwgMC42NDMyODY1NzMxNDYyOTI1LCAwLjY0NTI5MDU4MTE2MjMyNDUsIDAuNjQ3Mjk0NTg5MTc4MzU2NiwgMC42NDkyOTg1OTcxOTQzODg2LCAwLjY1MTMwMjYwNTIxMDQyMDcsIDAuNjUzMzA2NjEzMjI2NDUyOCwgMC42NTUzMTA2MjEyNDI0ODQ4LCAwLjY1NzMxNDYyOTI1ODUxNjksIDAuNjU5MzE4NjM3Mjc0NTQ5LCAwLjY2MTMyMjY0NTI5MDU4MSwgMC42NjMzMjY2NTMzMDY2MTMxLCAwLjY2NTMzMDY2MTMyMjY0NTIsIDAuNjY3MzM0NjY5MzM4Njc3MiwgMC42NjkzMzg2NzczNTQ3MDkzLCAwLjY3MTM0MjY4NTM3MDc0MTQsIDAuNjczMzQ2NjkzMzg2NzczNCwgMC42NzUzNTA3MDE0MDI4MDU1LCAwLjY3NzM1NDcwOTQxODgzNzYsIDAuNjc5MzU4NzE3NDM0ODY5NiwgMC42ODEzNjI3MjU0NTA5MDE3LCAwLjY4MzM2NjczMzQ2NjkzMzgsIDAuNjg1MzcwNzQxNDgyOTY1OCwgMC42ODczNzQ3NDk0OTg5OTc5LCAwLjY4OTM3ODc1NzUxNTAzLCAwLjY5MTM4Mjc2NTUzMTA2MiwgMC42OTMzODY3NzM1NDcwOTQxLCAwLjY5NTM5MDc4MTU2MzEyNjIsIDAuNjk3Mzk0Nzg5NTc5MTU4MiwgMC42OTkzOTg3OTc1OTUxOTAzLCAwLjcwMTQwMjgwNTYxMTIyMjQsIDAuNzAzNDA2ODEzNjI3MjU0NCwgMC43MDU0MTA4MjE2NDMyODY1LCAwLjcwNzQxNDgyOTY1OTMxODYsIDAuNzA5NDE4ODM3Njc1MzUwNiwgMC43MTE0MjI4NDU2OTEzODI3LCAwLjcxMzQyNjg1MzcwNzQxNDgsIDAuNzE1NDMwODYxNzIzNDQ2OCwgMC43MTc0MzQ4Njk3Mzk0Nzg5LCAwLjcxOTQzODg3Nzc1NTUxMDksIDAuNzIxNDQyODg1NzcxNTQyOSwgMC43MjM0NDY4OTM3ODc1NzUsIDAuNzI1NDUwOTAxODAzNjA3LCAwLjcyNzQ1NDkwOTgxOTYzOTEsIDAuNzI5NDU4OTE3ODM1NjcxMiwgMC43MzE0NjI5MjU4NTE3MDMyLCAwLjczMzQ2NjkzMzg2NzczNTMsIDAuNzM1NDcwOTQxODgzNzY3NCwgMC43Mzc0NzQ5NDk4OTk3OTk0LCAwLjczOTQ3ODk1NzkxNTgzMTUsIDAuNzQxNDgyOTY1OTMxODYzNiwgMC43NDM0ODY5NzM5NDc4OTU2LCAwLjc0NTQ5MDk4MTk2MzkyNzcsIDAuNzQ3NDk0OTg5OTc5OTU5OCwgMC43NDk0OTg5OTc5OTU5OTE4LCAwLjc1MTUwMzAwNjAxMjAyMzksIDAuNzUzNTA3MDE0MDI4MDU2LCAwLjc1NTUxMTAyMjA0NDA4OCwgMC43NTc1MTUwMzAwNjAxMjAxLCAwLjc1OTUxOTAzODA3NjE1MjIsIDAuNzYxNTIzMDQ2MDkyMTg0MiwgMC43NjM1MjcwNTQxMDgyMTYzLCAwLjc2NTUzMTA2MjEyNDI0ODQsIDAuNzY3NTM1MDcwMTQwMjgwNCwgMC43Njk1MzkwNzgxNTYzMTI1LCAwLjc3MTU0MzA4NjE3MjM0NDYsIDAuNzczNTQ3MDk0MTg4Mzc2NiwgMC43NzU1NTExMDIyMDQ0MDg3LCAwLjc3NzU1NTExMDIyMDQ0MDgsIDAuNzc5NTU5MTE4MjM2NDcyOCwgMC43ODE1NjMxMjYyNTI1MDQ5LCAwLjc4MzU2NzEzNDI2ODUzNywgMC43ODU1NzExNDIyODQ1NjksIDAuNzg3NTc1MTUwMzAwNjAxMSwgMC43ODk1NzkxNTgzMTY2MzMyLCAwLjc5MTU4MzE2NjMzMjY2NTIsIDAuNzkzNTg3MTc0MzQ4Njk3MywgMC43OTU1OTExODIzNjQ3Mjk0LCAwLjc5NzU5NTE5MDM4MDc2MTQsIDAuNzk5NTk5MTk4Mzk2NzkzNSwgMC44MDE2MDMyMDY0MTI4MjU2LCAwLjgwMzYwNzIxNDQyODg1NzYsIDAuODA1NjExMjIyNDQ0ODg5NywgMC44MDc2MTUyMzA0NjA5MjE4LCAwLjgwOTYxOTIzODQ3Njk1MzgsIDAuODExNjIzMjQ2NDkyOTg1OSwgMC44MTM2MjcyNTQ1MDkwMTgsIDAuODE1NjMxMjYyNTI1MDUsIDAuODE3NjM1MjcwNTQxMDgyMSwgMC44MTk2MzkyNzg1NTcxMTQyLCAwLjgyMTY0MzI4NjU3MzE0NjIsIDAuODIzNjQ3Mjk0NTg5MTc4MywgMC44MjU2NTEzMDI2MDUyMTA0LCAwLjgyNzY1NTMxMDYyMTI0MjQsIDAuODI5NjU5MzE4NjM3Mjc0NSwgMC44MzE2NjMzMjY2NTMzMDY0LCAwLjgzMzY2NzMzNDY2OTMzODUsIDAuODM1NjcxMzQyNjg1MzcwNiwgMC44Mzc2NzUzNTA3MDE0MDI2LCAwLjgzOTY3OTM1ODcxNzQzNDcsIDAuODQxNjgzMzY2NzMzNDY2OCwgMC44NDM2ODczNzQ3NDk0OTg4LCAwLjg0NTY5MTM4Mjc2NTUzMDksIDAuODQ3Njk1MzkwNzgxNTYzLCAwLjg0OTY5OTM5ODc5NzU5NSwgMC44NTE3MDM0MDY4MTM2MjcxLCAwLjg1MzcwNzQxNDgyOTY1OTIsIDAuODU1NzExNDIyODQ1NjkxMiwgMC44NTc3MTU0MzA4NjE3MjMzLCAwLjg1OTcxOTQzODg3Nzc1NTQsIDAuODYxNzIzNDQ2ODkzNzg3NCwgMC44NjM3Mjc0NTQ5MDk4MTk1LCAwLjg2NTczMTQ2MjkyNTg1MTYsIDAuODY3NzM1NDcwOTQxODgzNiwgMC44Njk3Mzk0Nzg5NTc5MTU3LCAwLjg3MTc0MzQ4Njk3Mzk0NzgsIDAuODczNzQ3NDk0OTg5OTc5OCwgMC44NzU3NTE1MDMwMDYwMTE5LCAwLjg3Nzc1NTUxMTAyMjA0NCwgMC44Nzk3NTk1MTkwMzgwNzYsIDAuODgxNzYzNTI3MDU0MTA4MSwgMC44ODM3Njc1MzUwNzAxNDAyLCAwLjg4NTc3MTU0MzA4NjE3MjIsIDAuODg3Nzc1NTUxMTAyMjA0MywgMC44ODk3Nzk1NTkxMTgyMzY0LCAwLjg5MTc4MzU2NzEzNDI2ODQsIDAuODkzNzg3NTc1MTUwMzAwNSwgMC44OTU3OTE1ODMxNjYzMzI2LCAwLjg5Nzc5NTU5MTE4MjM2NDYsIDAuODk5Nzk5NTk5MTk4Mzk2NywgMC45MDE4MDM2MDcyMTQ0Mjg4LCAwLjkwMzgwNzYxNTIzMDQ2MDgsIDAuOTA1ODExNjIzMjQ2NDkyOSwgMC45MDc4MTU2MzEyNjI1MjUsIDAuOTA5ODE5NjM5Mjc4NTU3LCAwLjkxMTgyMzY0NzI5NDU4OTEsIDAuOTEzODI3NjU1MzEwNjIxMiwgMC45MTU4MzE2NjMzMjY2NTMyLCAwLjkxNzgzNTY3MTM0MjY4NTMsIDAuOTE5ODM5Njc5MzU4NzE3NCwgMC45MjE4NDM2ODczNzQ3NDk0LCAwLjkyMzg0NzY5NTM5MDc4MTUsIDAuOTI1ODUxNzAzNDA2ODEzNiwgMC45Mjc4NTU3MTE0MjI4NDU2LCAwLjkyOTg1OTcxOTQzODg3NzcsIDAuOTMxODYzNzI3NDU0OTA5OCwgMC45MzM4Njc3MzU0NzA5NDE4LCAwLjkzNTg3MTc0MzQ4Njk3MzksIDAuOTM3ODc1NzUxNTAzMDA2LCAwLjkzOTg3OTc1OTUxOTAzOCwgMC45NDE4ODM3Njc1MzUwNywgMC45NDM4ODc3NzU1NTExMDIsIDAuOTQ1ODkxNzgzNTY3MTM0MSwgMC45NDc4OTU3OTE1ODMxNjYyLCAwLjk0OTg5OTc5OTU5OTE5ODIsIDAuOTUxOTAzODA3NjE1MjMwMywgMC45NTM5MDc4MTU2MzEyNjI0LCAwLjk1NTkxMTgyMzY0NzI5NDQsIDAuOTU3OTE1ODMxNjYzMzI2NSwgMC45NTk5MTk4Mzk2NzkzNTg2LCAwLjk2MTkyMzg0NzY5NTM5MDYsIDAuOTYzOTI3ODU1NzExNDIyNywgMC45NjU5MzE4NjM3Mjc0NTQ4LCAwLjk2NzkzNTg3MTc0MzQ4NjgsIDAuOTY5OTM5ODc5NzU5NTE4OSwgMC45NzE5NDM4ODc3NzU1NTEsIDAuOTczOTQ3ODk1NzkxNTgzLCAwLjk3NTk1MTkwMzgwNzYxNTEsIDAuOTc3OTU1OTExODIzNjQ3MiwgMC45Nzk5NTk5MTk4Mzk2NzkyLCAwLjk4MTk2MzkyNzg1NTcxMTMsIDAuOTgzOTY3OTM1ODcxNzQzNCwgMC45ODU5NzE5NDM4ODc3NzU0LCAwLjk4Nzk3NTk1MTkwMzgwNzUsIDAuOTg5OTc5OTU5OTE5ODM5NiwgMC45OTE5ODM5Njc5MzU4NzE2LCAwLjk5Mzk4Nzk3NTk1MTkwMzcsIDAuOTk1OTkxOTgzOTY3OTM1OCwgMC45OTc5OTU5OTE5ODM5Njc4LCAwLjk5OTk5OTk5OTk5OTk5OTldKQogICAgICAgICAgICAgIC5yYW5nZShbJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MyddKTsKICAgIAoKICAgIGNvbG9yX21hcF9iYjA3YTBiNmQxNDY0NDFiOTVjNDk1NWFjYjRlYTJhZS54ID0gZDMuc2NhbGUubGluZWFyKCkKICAgICAgICAgICAgICAuZG9tYWluKFswLjAsIDAuOTk5OTk5OTk5OTk5OTk5OV0pCiAgICAgICAgICAgICAgLnJhbmdlKFswLCA0MDBdKTsKCiAgICBjb2xvcl9tYXBfYmIwN2EwYjZkMTQ2NDQxYjk1YzQ5NTVhY2I0ZWEyYWUubGVnZW5kID0gTC5jb250cm9sKHtwb3NpdGlvbjogJ3RvcHJpZ2h0J30pOwogICAgY29sb3JfbWFwX2JiMDdhMGI2ZDE0NjQ0MWI5NWM0OTU1YWNiNGVhMmFlLmxlZ2VuZC5vbkFkZCA9IGZ1bmN0aW9uIChtYXApIHt2YXIgZGl2ID0gTC5Eb21VdGlsLmNyZWF0ZSgnZGl2JywgJ2xlZ2VuZCcpOyByZXR1cm4gZGl2fTsKICAgIGNvbG9yX21hcF9iYjA3YTBiNmQxNDY0NDFiOTVjNDk1NWFjYjRlYTJhZS5sZWdlbmQuYWRkVG8obWFwXzI2NTkwNjA0YmVlODQ3N2Q5MTg5OTBjNGI3ZDA0ODU0KTsKCiAgICBjb2xvcl9tYXBfYmIwN2EwYjZkMTQ2NDQxYjk1YzQ5NTVhY2I0ZWEyYWUueEF4aXMgPSBkMy5zdmcuYXhpcygpCiAgICAgICAgLnNjYWxlKGNvbG9yX21hcF9iYjA3YTBiNmQxNDY0NDFiOTVjNDk1NWFjYjRlYTJhZS54KQogICAgICAgIC5vcmllbnQoInRvcCIpCiAgICAgICAgLnRpY2tTaXplKDEpCiAgICAgICAgLnRpY2tWYWx1ZXMoWzAuMCwgMC4xNjY2NjY2NjY2NjY2NjY2NiwgMC4zMzMzMzMzMzMzMzMzMzMzLCAwLjUsIDAuNjY2NjY2NjY2NjY2NjY2NiwgMC44MzMzMzMzMzMzMzMzMzMzLCAwLjk5OTk5OTk5OTk5OTk5OTldKTsKCiAgICBjb2xvcl9tYXBfYmIwN2EwYjZkMTQ2NDQxYjk1YzQ5NTVhY2I0ZWEyYWUuc3ZnID0gZDMuc2VsZWN0KCIubGVnZW5kLmxlYWZsZXQtY29udHJvbCIpLmFwcGVuZCgic3ZnIikKICAgICAgICAuYXR0cigiaWQiLCAnbGVnZW5kJykKICAgICAgICAuYXR0cigid2lkdGgiLCA0NTApCiAgICAgICAgLmF0dHIoImhlaWdodCIsIDQwKTsKCiAgICBjb2xvcl9tYXBfYmIwN2EwYjZkMTQ2NDQxYjk1YzQ5NTVhY2I0ZWEyYWUuZyA9IGNvbG9yX21hcF9iYjA3YTBiNmQxNDY0NDFiOTVjNDk1NWFjYjRlYTJhZS5zdmcuYXBwZW5kKCJnIikKICAgICAgICAuYXR0cigiY2xhc3MiLCAia2V5IikKICAgICAgICAuYXR0cigidHJhbnNmb3JtIiwgInRyYW5zbGF0ZSgyNSwxNikiKTsKCiAgICBjb2xvcl9tYXBfYmIwN2EwYjZkMTQ2NDQxYjk1YzQ5NTVhY2I0ZWEyYWUuZy5zZWxlY3RBbGwoInJlY3QiKQogICAgICAgIC5kYXRhKGNvbG9yX21hcF9iYjA3YTBiNmQxNDY0NDFiOTVjNDk1NWFjYjRlYTJhZS5jb2xvci5yYW5nZSgpLm1hcChmdW5jdGlvbihkLCBpKSB7CiAgICAgICAgICByZXR1cm4gewogICAgICAgICAgICB4MDogaSA/IGNvbG9yX21hcF9iYjA3YTBiNmQxNDY0NDFiOTVjNDk1NWFjYjRlYTJhZS54KGNvbG9yX21hcF9iYjA3YTBiNmQxNDY0NDFiOTVjNDk1NWFjYjRlYTJhZS5jb2xvci5kb21haW4oKVtpIC0gMV0pIDogY29sb3JfbWFwX2JiMDdhMGI2ZDE0NjQ0MWI5NWM0OTU1YWNiNGVhMmFlLngucmFuZ2UoKVswXSwKICAgICAgICAgICAgeDE6IGkgPCBjb2xvcl9tYXBfYmIwN2EwYjZkMTQ2NDQxYjk1YzQ5NTVhY2I0ZWEyYWUuY29sb3IuZG9tYWluKCkubGVuZ3RoID8gY29sb3JfbWFwX2JiMDdhMGI2ZDE0NjQ0MWI5NWM0OTU1YWNiNGVhMmFlLngoY29sb3JfbWFwX2JiMDdhMGI2ZDE0NjQ0MWI5NWM0OTU1YWNiNGVhMmFlLmNvbG9yLmRvbWFpbigpW2ldKSA6IGNvbG9yX21hcF9iYjA3YTBiNmQxNDY0NDFiOTVjNDk1NWFjYjRlYTJhZS54LnJhbmdlKClbMV0sCiAgICAgICAgICAgIHo6IGQKICAgICAgICAgIH07CiAgICAgICAgfSkpCiAgICAgIC5lbnRlcigpLmFwcGVuZCgicmVjdCIpCiAgICAgICAgLmF0dHIoImhlaWdodCIsIDEwKQogICAgICAgIC5hdHRyKCJ4IiwgZnVuY3Rpb24oZCkgeyByZXR1cm4gZC54MDsgfSkKICAgICAgICAuYXR0cigid2lkdGgiLCBmdW5jdGlvbihkKSB7IHJldHVybiBkLngxIC0gZC54MDsgfSkKICAgICAgICAuc3R5bGUoImZpbGwiLCBmdW5jdGlvbihkKSB7IHJldHVybiBkLno7IH0pOwoKICAgIGNvbG9yX21hcF9iYjA3YTBiNmQxNDY0NDFiOTVjNDk1NWFjYjRlYTJhZS5nLmNhbGwoY29sb3JfbWFwX2JiMDdhMGI2ZDE0NjQ0MWI5NWM0OTU1YWNiNGVhMmFlLnhBeGlzKS5hcHBlbmQoInRleHQiKQogICAgICAgIC5hdHRyKCJjbGFzcyIsICJjYXB0aW9uIikKICAgICAgICAuYXR0cigieSIsIDIxKQogICAgICAgIC50ZXh0KCcnKTsKPC9zY3JpcHQ+" style="position:absolute;width:100%;height:100%;left:0;top:0;border:none !important;" allowfullscreen webkitallowfullscreen mozallowfullscreen></iframe></div></div>




```python
map = folium.Map(location=[37.5502, 126.982], zoom_start=11, 
                 tiles='Stamen Toner')

map.choropleth(geo_data = geo_str,
               data = crime_anal_norm['강간'],
               columns = [crime_anal_norm.index, crime_anal_norm['강간']],
               fill_color = 'PuRd', #PuRd, YlGnBu
               key_on = 'feature.id')
map
```

    C:\ProgramData\Anaconda3\lib\site-packages\folium\folium.py:432: FutureWarning: The choropleth  method has been deprecated. Instead use the new Choropleth class, which has the same arguments. See the example notebook 'GeoJSON_and_choropleth' for how to do this.
      FutureWarning
    




<div style="width:100%;"><div style="position:relative;width:100%;height:0;padding-bottom:60%;"><iframe src="data:text/html;charset=utf-8;base64,PCFET0NUWVBFIGh0bWw+CjxoZWFkPiAgICAKICAgIDxtZXRhIGh0dHAtZXF1aXY9ImNvbnRlbnQtdHlwZSIgY29udGVudD0idGV4dC9odG1sOyBjaGFyc2V0PVVURi04IiAvPgogICAgPHNjcmlwdD5MX1BSRUZFUl9DQU5WQVM9ZmFsc2U7IExfTk9fVE9VQ0g9ZmFsc2U7IExfRElTQUJMRV8zRD1mYWxzZTs8L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmpzIj48L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2FqYXguZ29vZ2xlYXBpcy5jb20vYWpheC9saWJzL2pxdWVyeS8xLjExLjEvanF1ZXJ5Lm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvanMvYm9vdHN0cmFwLm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9jZG5qcy5jbG91ZGZsYXJlLmNvbS9hamF4L2xpYnMvTGVhZmxldC5hd2Vzb21lLW1hcmtlcnMvMi4wLjIvbGVhZmxldC5hd2Vzb21lLW1hcmtlcnMuanMiPjwvc2NyaXB0PgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL21heGNkbi5ib290c3RyYXBjZG4uY29tL2Jvb3RzdHJhcC8zLjIuMC9jc3MvYm9vdHN0cmFwLm1pbi5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvY3NzL2Jvb3RzdHJhcC10aGVtZS5taW4uY3NzIi8+CiAgICA8bGluayByZWw9InN0eWxlc2hlZXQiIGhyZWY9Imh0dHBzOi8vbWF4Y2RuLmJvb3RzdHJhcGNkbi5jb20vZm9udC1hd2Vzb21lLzQuNi4zL2Nzcy9mb250LWF3ZXNvbWUubWluLmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2NkbmpzLmNsb3VkZmxhcmUuY29tL2FqYXgvbGlicy9MZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy8yLjAuMi9sZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9yYXdjZG4uZ2l0aGFjay5jb20vcHl0aG9uLXZpc3VhbGl6YXRpb24vZm9saXVtL21hc3Rlci9mb2xpdW0vdGVtcGxhdGVzL2xlYWZsZXQuYXdlc29tZS5yb3RhdGUuY3NzIi8+CiAgICA8c3R5bGU+aHRtbCwgYm9keSB7d2lkdGg6IDEwMCU7aGVpZ2h0OiAxMDAlO21hcmdpbjogMDtwYWRkaW5nOiAwO308L3N0eWxlPgogICAgPHN0eWxlPiNtYXAge3Bvc2l0aW9uOmFic29sdXRlO3RvcDowO2JvdHRvbTowO3JpZ2h0OjA7bGVmdDowO308L3N0eWxlPgogICAgCiAgICA8bWV0YSBuYW1lPSJ2aWV3cG9ydCIgY29udGVudD0id2lkdGg9ZGV2aWNlLXdpZHRoLAogICAgICAgIGluaXRpYWwtc2NhbGU9MS4wLCBtYXhpbXVtLXNjYWxlPTEuMCwgdXNlci1zY2FsYWJsZT1ubyIgLz4KICAgIDxzdHlsZT4jbWFwX2YwYzg3ZGUzZTc2ODQ3M2E4NjhiMzQxZDljYTk1MDFhIHsKICAgICAgICBwb3NpdGlvbjogcmVsYXRpdmU7CiAgICAgICAgd2lkdGg6IDEwMC4wJTsKICAgICAgICBoZWlnaHQ6IDEwMC4wJTsKICAgICAgICBsZWZ0OiAwLjAlOwogICAgICAgIHRvcDogMC4wJTsKICAgICAgICB9CiAgICA8L3N0eWxlPgogICAgPHNjcmlwdCBzcmM9Imh0dHBzOi8vY2RuanMuY2xvdWRmbGFyZS5jb20vYWpheC9saWJzL2QzLzMuNS41L2QzLm1pbi5qcyI+PC9zY3JpcHQ+CjwvaGVhZD4KPGJvZHk+ICAgIAogICAgCiAgICA8ZGl2IGNsYXNzPSJmb2xpdW0tbWFwIiBpZD0ibWFwX2YwYzg3ZGUzZTc2ODQ3M2E4NjhiMzQxZDljYTk1MDFhIiA+PC9kaXY+CjwvYm9keT4KPHNjcmlwdD4gICAgCiAgICAKICAgIAogICAgICAgIHZhciBib3VuZHMgPSBudWxsOwogICAgCgogICAgdmFyIG1hcF9mMGM4N2RlM2U3Njg0NzNhODY4YjM0MWQ5Y2E5NTAxYSA9IEwubWFwKAogICAgICAgICdtYXBfZjBjODdkZTNlNzY4NDczYTg2OGIzNDFkOWNhOTUwMWEnLCB7CiAgICAgICAgY2VudGVyOiBbMzcuNTUwMiwgMTI2Ljk4Ml0sCiAgICAgICAgem9vbTogMTEsCiAgICAgICAgbWF4Qm91bmRzOiBib3VuZHMsCiAgICAgICAgbGF5ZXJzOiBbXSwKICAgICAgICB3b3JsZENvcHlKdW1wOiBmYWxzZSwKICAgICAgICBjcnM6IEwuQ1JTLkVQU0czODU3LAogICAgICAgIHpvb21Db250cm9sOiB0cnVlLAogICAgICAgIH0pOwoKICAgIAogICAgCiAgICB2YXIgdGlsZV9sYXllcl83M2U3MWVjMThmNDA0ZTAzODc2NWQwZGM1ODNmZDU0OCA9IEwudGlsZUxheWVyKAogICAgICAgICdodHRwczovL3N0YW1lbi10aWxlcy17c30uYS5zc2wuZmFzdGx5Lm5ldC90b25lci97en0ve3h9L3t5fS5wbmcnLAogICAgICAgIHsKICAgICAgICAiYXR0cmlidXRpb24iOiBudWxsLAogICAgICAgICJkZXRlY3RSZXRpbmEiOiBmYWxzZSwKICAgICAgICAibWF4TmF0aXZlWm9vbSI6IDE4LAogICAgICAgICJtYXhab29tIjogMTgsCiAgICAgICAgIm1pblpvb20iOiAwLAogICAgICAgICJub1dyYXAiOiBmYWxzZSwKICAgICAgICAib3BhY2l0eSI6IDEsCiAgICAgICAgInN1YmRvbWFpbnMiOiAiYWJjIiwKICAgICAgICAidG1zIjogZmFsc2UKfSkuYWRkVG8obWFwX2YwYzg3ZGUzZTc2ODQ3M2E4NjhiMzQxZDljYTk1MDFhKTsKICAgIAogICAgICAgICAgICB2YXIgY2hvcm9wbGV0aF8wODI4YWQ3YzE1M2Q0ZTJjYmEzNzg1M2Q0ZGQ4YTQ3NiA9IEwuZmVhdHVyZUdyb3VwKAogICAgICAgICAgICAgICAgKS5hZGRUbyhtYXBfZjBjODdkZTNlNzY4NDczYTg2OGIzNDFkOWNhOTUwMWEpOwogICAgICAgIAogICAgCiAgICAgICAgCiAgICAgICAgdmFyIGdlb19qc29uX2FmNWM2ZjQ5OGY4MzRhN2U4MWQxMjJmMzk3YjYxZmEyID0gTC5nZW9Kc29uKAogICAgICAgICAgICB7ImZlYXR1cmVzIjogW3siZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI3LjExNTE5NTg0OTgxNjA2LCAzNy41NTc1MzMxODA3MDQ5MTVdLCBbMTI3LjE2NjgzMTg0MzY2MTI5LCAzNy41NzY3MjQ4NzM4ODYyN10sIFsxMjcuMTg0MDg3OTIzMzAxNTIsIDM3LjU1ODE0MjgwMzY5NTc1XSwgWzEyNy4xNjUzMDk4NDMwNzQ0NywgMzcuNTQyMjE4NTEyNTg2OTNdLCBbMTI3LjE0NjcyODA2ODIzNTAyLCAzNy41MTQxNTY4MDY4MDI5MV0sIFsxMjcuMTIxMjMxNjU3MTk2MTUsIDM3LjUyNTI4MjcwMDg5XSwgWzEyNy4xMTE2NzY0MjAzNjA4LCAzNy41NDA2Njk5NTUzMjQ5NjVdLCBbMTI3LjExNTE5NTg0OTgxNjA2LCAzNy41NTc1MzMxODA3MDQ5MTVdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YWMxNVx1YjNkOVx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMjUwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWFjMTVcdWIzZDlcdWFkNmMiLCAibmFtZV9lbmciOiAiR2FuZ2RvbmctZ3UiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICIjZjFlZWY2IiwgImZpbGxPcGFjaXR5IjogMC42LCAib3BhY2l0eSI6IDEsICJ3ZWlnaHQiOiAxfX0sICJ0eXBlIjogIkZlYXR1cmUifSwgeyJnZW9tZXRyeSI6IHsiY29vcmRpbmF0ZXMiOiBbW1sxMjcuMDY5MDY5ODEzMDM3MiwgMzcuNTIyMjc5NDIzNTA1MDI2XSwgWzEyNy4xMDA4NzUxOTc5MTk2MiwgMzcuNTI0ODQxMjIwMTY3MDU1XSwgWzEyNy4xMTE2NzY0MjAzNjA4LCAzNy41NDA2Njk5NTUzMjQ5NjVdLCBbMTI3LjEyMTIzMTY1NzE5NjE1LCAzNy41MjUyODI3MDA4OV0sIFsxMjcuMTQ2NzI4MDY4MjM1MDIsIDM3LjUxNDE1NjgwNjgwMjkxXSwgWzEyNy4xNjM0OTQ0MjE1NzY1LCAzNy40OTc0NDU0MDYwOTc0ODRdLCBbMTI3LjE0MjA2MDU4NDEzMjc0LCAzNy40NzA4OTgxOTA5ODUwMV0sIFsxMjcuMTI0NDA1NzEwODA4OTMsIDM3LjQ2MjQwNDQ1NTg3MDQ4XSwgWzEyNy4xMTExNzA4NTIwMTIzOCwgMzcuNDg1NzA4MzgxNTEyNDQ1XSwgWzEyNy4wNzE5MTQ2MDAwNzI0LCAzNy41MDIyNDAxMzU4NzY2OV0sIFsxMjcuMDY5MDY5ODEzMDM3MiwgMzcuNTIyMjc5NDIzNTA1MDI2XV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJcdWMxYTFcdWQzMGNcdWFkNmMiLCAicHJvcGVydGllcyI6IHsiYmFzZV95ZWFyIjogIjIwMTMiLCAiY29kZSI6ICIxMTI0MCIsICJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiXHVjMWExXHVkMzBjXHVhZDZjIiwgIm5hbWVfZW5nIjogIlNvbmdwYS1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNjOTk0YzciLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wNTg2NzM1OTI4ODM5OCwgMzcuNTI2Mjk5NzQ5MjI1NjhdLCBbMTI3LjA2OTA2OTgxMzAzNzIsIDM3LjUyMjI3OTQyMzUwNTAyNl0sIFsxMjcuMDcxOTE0NjAwMDcyNCwgMzcuNTAyMjQwMTM1ODc2NjldLCBbMTI3LjExMTE3MDg1MjAxMjM4LCAzNy40ODU3MDgzODE1MTI0NDVdLCBbMTI3LjEyNDQwNTcxMDgwODkzLCAzNy40NjI0MDQ0NTU4NzA0OF0sIFsxMjcuMDk4NDI3NTkzMTg3NTEsIDM3LjQ1ODYyMjUzODU3NDYxXSwgWzEyNy4wODY0MDQ0MDU3ODE1NiwgMzcuNDcyNjk3OTM1MTg0NjU1XSwgWzEyNy4wNTU5MTcwNDgxOTA0LCAzNy40NjU5MjI4OTE0MDc3XSwgWzEyNy4wMzYyMTkxNTA5ODc5OCwgMzcuNDgxNzU4MDI0Mjc2MDNdLCBbMTI3LjAxMzk3MTE5NjY3NTEzLCAzNy41MjUwMzk4ODI4OTY2OV0sIFsxMjcuMDIzMDI4MzE4OTA1NTksIDM3LjUzMjMxODk5NTgyNjYzXSwgWzEyNy4wNTg2NzM1OTI4ODM5OCwgMzcuNTI2Mjk5NzQ5MjI1NjhdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YWMxNVx1YjBhOFx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMjMwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWFjMTVcdWIwYThcdWFkNmMiLCAibmFtZV9lbmciOiAiR2FuZ25hbS1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiM5ODAwNDMiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wMTM5NzExOTY2NzUxMywgMzcuNTI1MDM5ODgyODk2NjldLCBbMTI3LjAzNjIxOTE1MDk4Nzk4LCAzNy40ODE3NTgwMjQyNzYwM10sIFsxMjcuMDU1OTE3MDQ4MTkwNCwgMzcuNDY1OTIyODkxNDA3N10sIFsxMjcuMDg2NDA0NDA1NzgxNTYsIDM3LjQ3MjY5NzkzNTE4NDY1NV0sIFsxMjcuMDk4NDI3NTkzMTg3NTEsIDM3LjQ1ODYyMjUzODU3NDYxXSwgWzEyNy4wOTA0NjkyODU2NTk1MSwgMzcuNDQyOTY4MjYxMTQxODVdLCBbMTI3LjA2Nzc4MTA3NjA1NDMzLCAzNy40MjYxOTc0MjQwNTczMTRdLCBbMTI3LjA0OTU3MjMyOTg3MTQyLCAzNy40MjgwNTgzNjg0NTY5NF0sIFsxMjcuMDM4ODE3ODI1OTc5MjIsIDM3LjQ1MzgyMDM5ODUxNzE1XSwgWzEyNi45OTA3MjA3MzE5NTQ2MiwgMzcuNDU1MzI2MTQzMzEwMDI1XSwgWzEyNi45ODM2NzY2ODI5MTgwMiwgMzcuNDczODU2NDkyNjkyMDg2XSwgWzEyNi45ODIyMzgwNzkxNjA4MSwgMzcuNTA5MzE0OTY2NzcwMzI2XSwgWzEyNy4wMTM5NzExOTY2NzUxMywgMzcuNTI1MDM5ODgyODk2NjldXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzExY1x1Y2QwOFx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMjIwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWMxMWNcdWNkMDhcdWFkNmMiLCAibmFtZV9lbmciOiAiU2VvY2hvLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiIzk4MDA0MyIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2Ljk4MzY3NjY4MjkxODAyLCAzNy40NzM4NTY0OTI2OTIwODZdLCBbMTI2Ljk5MDcyMDczMTk1NDYyLCAzNy40NTUzMjYxNDMzMTAwMjVdLCBbMTI2Ljk2NTIwNDM5MDg1MTQzLCAzNy40MzgyNDk3ODQwMDYyNDZdLCBbMTI2Ljk1MDAwMDAxMDEwMTgyLCAzNy40MzYxMzQ1MTE2NTcxOV0sIFsxMjYuOTMwODQ0MDgwNTY1MjUsIDM3LjQ0NzM4MjkyODMzMzk5NF0sIFsxMjYuOTE2NzcyODE0NjYwMSwgMzcuNDU0OTA1NjY0MjM3ODldLCBbMTI2LjkwMTU2MDk0MTI5ODk1LCAzNy40Nzc1Mzg0Mjc4OTkwMV0sIFsxMjYuOTA1MzE5NzU4MDE4MTIsIDM3LjQ4MjE4MDg3NTc1NDI5XSwgWzEyNi45NDkyMjY2MTM4OTUwOCwgMzcuNDkxMjU0Mzc0OTU2NDldLCBbMTI2Ljk3MjU4OTE4NTA2NjIsIDM3LjQ3MjU2MTM2MzI3ODEyNV0sIFsxMjYuOTgzNjc2NjgyOTE4MDIsIDM3LjQ3Mzg1NjQ5MjY5MjA4Nl1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVhZDAwXHVjNTQ1XHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEyMTAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YWQwMFx1YzU0NVx1YWQ2YyIsICJuYW1lX2VuZyI6ICJHd2FuYWstZ3UiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICIjZGY2NWIwIiwgImZpbGxPcGFjaXR5IjogMC42LCAib3BhY2l0eSI6IDEsICJ3ZWlnaHQiOiAxfX0sICJ0eXBlIjogIkZlYXR1cmUifSwgeyJnZW9tZXRyeSI6IHsiY29vcmRpbmF0ZXMiOiBbW1sxMjYuOTgyMjM4MDc5MTYwODEsIDM3LjUwOTMxNDk2Njc3MDMyNl0sIFsxMjYuOTgzNjc2NjgyOTE4MDIsIDM3LjQ3Mzg1NjQ5MjY5MjA4Nl0sIFsxMjYuOTcyNTg5MTg1MDY2MiwgMzcuNDcyNTYxMzYzMjc4MTI1XSwgWzEyNi45NDkyMjY2MTM4OTUwOCwgMzcuNDkxMjU0Mzc0OTU2NDldLCBbMTI2LjkwNTMxOTc1ODAxODEyLCAzNy40ODIxODA4NzU3NTQyOV0sIFsxMjYuOTIxNzc4OTMxNzQ4MjUsIDM3LjQ5NDg4OTg3NzQxNTE3Nl0sIFsxMjYuOTI4MTA2Mjg4MjgyNzksIDM3LjUxMzI5NTk1NzMyMDE1XSwgWzEyNi45NTI0OTk5MDI5ODE1OSwgMzcuNTE3MjI1MDA3NDE4MTNdLCBbMTI2Ljk4MjIzODA3OTE2MDgxLCAzNy41MDkzMTQ5NjY3NzAzMjZdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YjNkOVx1Yzc5MVx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMjAwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWIzZDlcdWM3OTFcdWFkNmMiLCAibmFtZV9lbmciOiAiRG9uZ2phay1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNkZjY1YjAiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNi44OTE4NDY2Mzg2Mjc2NCwgMzcuNTQ3MzczOTc0OTk3MTE0XSwgWzEyNi45NDU2NjczMzA4MzIxMiwgMzcuNTI2NjE3NTQyNDUzMzY2XSwgWzEyNi45NTI0OTk5MDI5ODE1OSwgMzcuNTE3MjI1MDA3NDE4MTNdLCBbMTI2LjkyODEwNjI4ODI4Mjc5LCAzNy41MTMyOTU5NTczMjAxNV0sIFsxMjYuOTIxNzc4OTMxNzQ4MjUsIDM3LjQ5NDg4OTg3NzQxNTE3Nl0sIFsxMjYuOTA1MzE5NzU4MDE4MTIsIDM3LjQ4MjE4MDg3NTc1NDI5XSwgWzEyNi44OTU5NDc3Njc4MjQ4NSwgMzcuNTA0Njc1MjgxMzA5MTc2XSwgWzEyNi44ODE1NjQwMjM1Mzg2MiwgMzcuNTEzOTcwMDM0NzY1Njg0XSwgWzEyNi44ODgyNTc1Nzg2MDA5OSwgMzcuNTQwNzk3MzM2MzAyMzJdLCBbMTI2Ljg5MTg0NjYzODYyNzY0LCAzNy41NDczNzM5NzQ5OTcxMTRdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzYwMVx1YjRmMVx1ZDNlY1x1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMTkwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWM2MDFcdWI0ZjFcdWQzZWNcdWFkNmMiLCAibmFtZV9lbmciOiAiWWVvbmdkZXVuZ3BvLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2RmNjViMCIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2LjkwMTU2MDk0MTI5ODk1LCAzNy40Nzc1Mzg0Mjc4OTkwMV0sIFsxMjYuOTE2NzcyODE0NjYwMSwgMzcuNDU0OTA1NjY0MjM3ODldLCBbMTI2LjkzMDg0NDA4MDU2NTI1LCAzNy40NDczODI5MjgzMzM5OTRdLCBbMTI2LjkwMjU4MzE3MTE2OTcsIDM3LjQzNDU0OTM2NjM0OTEyNF0sIFsxMjYuODc2ODMyNzE1MDI0MjgsIDM3LjQ4MjU3NjU5MTYwNzMwNV0sIFsxMjYuOTAxNTYwOTQxMjk4OTUsIDM3LjQ3NzUzODQyNzg5OTAxXV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJcdWFlMDhcdWNjOWNcdWFkNmMiLCAicHJvcGVydGllcyI6IHsiYmFzZV95ZWFyIjogIjIwMTMiLCAiY29kZSI6ICIxMTE4MCIsICJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiXHVhZTA4XHVjYzljXHVhZDZjIiwgIm5hbWVfZW5nIjogIkdldW1jaGVvbi1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNmMWVlZjYiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNi44MjY4ODA4MTUxNzMxNCwgMzcuNTA1NDg5NzIyMzI4OTZdLCBbMTI2Ljg4MTU2NDAyMzUzODYyLCAzNy41MTM5NzAwMzQ3NjU2ODRdLCBbMTI2Ljg5NTk0Nzc2NzgyNDg1LCAzNy41MDQ2NzUyODEzMDkxNzZdLCBbMTI2LjkwNTMxOTc1ODAxODEyLCAzNy40ODIxODA4NzU3NTQyOV0sIFsxMjYuOTAxNTYwOTQxMjk4OTUsIDM3LjQ3NzUzODQyNzg5OTAxXSwgWzEyNi44NzY4MzI3MTUwMjQyOCwgMzcuNDgyNTc2NTkxNjA3MzA1XSwgWzEyNi44NDc2MjY3NjA1NDk1MywgMzcuNDcxNDY3MjM5MzYzMjNdLCBbMTI2LjgzNTQ5NDg1MDc2MTk2LCAzNy40NzQwOTgyMzY5NzUwOTVdLCBbMTI2LjgyMjY0Nzk2NzkxMzQ4LCAzNy40ODc4NDc2NDkyMTQ3XSwgWzEyNi44MjUwNDczNjMzMTQwNiwgMzcuNTAzMDI2MTI2NDA0NDNdLCBbMTI2LjgyNjg4MDgxNTE3MzE0LCAzNy41MDU0ODk3MjIzMjg5Nl1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVhZDZjXHViODVjXHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTExNzAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YWQ2Y1x1Yjg1Y1x1YWQ2YyIsICJuYW1lX2VuZyI6ICJHdXJvLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2RmNjViMCIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2Ljc5NTc1NzY4NTUyOTA3LCAzNy41Nzg4MTA4NzYzMzIwMl0sIFsxMjYuODA3MDIxMTUwMjM1OTcsIDM3LjYwMTIzMDAxMDEzMjI4XSwgWzEyNi44MjI1MTQzODQ3NzEwNSwgMzcuNTg4MDQzMDgxMDA4Ml0sIFsxMjYuODU5ODQxOTkzOTk2NjcsIDM3LjU3MTg0Nzg1NTI5Mjc0NV0sIFsxMjYuODkxODQ2NjM4NjI3NjQsIDM3LjU0NzM3Mzk3NDk5NzExNF0sIFsxMjYuODg4MjU3NTc4NjAwOTksIDM3LjU0MDc5NzMzNjMwMjMyXSwgWzEyNi44NjYzNzQ2NDMyMTIzOCwgMzcuNTQ4NTkxOTEwOTQ4MjNdLCBbMTI2Ljg2NjEwMDczNDc2Mzk1LCAzNy41MjY5OTk2NDE0NDY2OV0sIFsxMjYuODQyNTcyOTE5NDMxNTMsIDM3LjUyMzczNzA3ODA1NTk2XSwgWzEyNi44MjQyMzMxNDI2NzIyLCAzNy41Mzc4ODA3ODc1MzI0OF0sIFsxMjYuNzczMjQ0MTc3MTc3MDMsIDM3LjU0NTkxMjM0NTA1NTRdLCBbMTI2Ljc2OTc5MTgwNTc5MzUyLCAzNy41NTEzOTE4MzAwODgwOV0sIFsxMjYuNzk1NzU3Njg1NTI5MDcsIDM3LjU3ODgxMDg3NjMzMjAyXV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJcdWFjMTVcdWMxMWNcdWFkNmMiLCAicHJvcGVydGllcyI6IHsiYmFzZV95ZWFyIjogIjIwMTMiLCAiY29kZSI6ICIxMTE2MCIsICJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiXHVhYzE1XHVjMTFjXHVhZDZjIiwgIm5hbWVfZW5nIjogIkdhbmdzZW8tZ3UiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICJibGFjayIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2LjgyNDIzMzE0MjY3MjIsIDM3LjUzNzg4MDc4NzUzMjQ4XSwgWzEyNi44NDI1NzI5MTk0MzE1MywgMzcuNTIzNzM3MDc4MDU1OTZdLCBbMTI2Ljg2NjEwMDczNDc2Mzk1LCAzNy41MjY5OTk2NDE0NDY2OV0sIFsxMjYuODY2Mzc0NjQzMjEyMzgsIDM3LjU0ODU5MTkxMDk0ODIzXSwgWzEyNi44ODgyNTc1Nzg2MDA5OSwgMzcuNTQwNzk3MzM2MzAyMzJdLCBbMTI2Ljg4MTU2NDAyMzUzODYyLCAzNy41MTM5NzAwMzQ3NjU2ODRdLCBbMTI2LjgyNjg4MDgxNTE3MzE0LCAzNy41MDU0ODk3MjIzMjg5Nl0sIFsxMjYuODI0MjMzMTQyNjcyMiwgMzcuNTM3ODgwNzg3NTMyNDhdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzU5MVx1Y2M5Y1x1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMTUwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWM1OTFcdWNjOWNcdWFkNmMiLCAibmFtZV9lbmciOiAiWWFuZ2NoZW9uLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2RkMWM3NyIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2LjkwNTIyMDY1ODMxMDUzLCAzNy41NzQwOTcwMDUyMjU3NF0sIFsxMjYuOTM4OTgxNjE3OTg5NzMsIDM3LjU1MjMxMDAwMzcyODEyNF0sIFsxMjYuOTYzNTgyMjY3MTA4MTIsIDM3LjU1NjA1NjM1NDc1MTU0XSwgWzEyNi45NjQ0ODU3MDU1MzA1NSwgMzcuNTQ4NzA1NjkyMDIxNjM1XSwgWzEyNi45NDU2NjczMzA4MzIxMiwgMzcuNTI2NjE3NTQyNDUzMzY2XSwgWzEyNi44OTE4NDY2Mzg2Mjc2NCwgMzcuNTQ3MzczOTc0OTk3MTE0XSwgWzEyNi44NTk4NDE5OTM5OTY2NywgMzcuNTcxODQ3ODU1MjkyNzQ1XSwgWzEyNi44ODQzMzI4NDc3MzI4OCwgMzcuNTg4MTQzMzIyODgwNTI2XSwgWzEyNi45MDUyMjA2NTgzMTA1MywgMzcuNTc0MDk3MDA1MjI1NzRdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YjljOFx1ZDNlY1x1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMTQwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWI5YzhcdWQzZWNcdWFkNmMiLCAibmFtZV9lbmciOiAiTWFwby1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNkZjY1YjAiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNi45NTI0NzUyMDMwNTcyLCAzNy42MDUwODY5MjczNzA0NV0sIFsxMjYuOTU1NjU0MjU4NDY0NjMsIDM3LjU3NjA4MDc5MDg4MTQ1Nl0sIFsxMjYuOTY4NzM2MzMyNzkwNzUsIDM3LjU2MzEzNjA0NjkwODI3XSwgWzEyNi45NjM1ODIyNjcxMDgxMiwgMzcuNTU2MDU2MzU0NzUxNTRdLCBbMTI2LjkzODk4MTYxNzk4OTczLCAzNy41NTIzMTAwMDM3MjgxMjRdLCBbMTI2LjkwNTIyMDY1ODMxMDUzLCAzNy41NzQwOTcwMDUyMjU3NF0sIFsxMjYuOTUyNDc1MjAzMDU3MiwgMzcuNjA1MDg2OTI3MzcwNDVdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzExY1x1YjMwMFx1YmIzOFx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMTMwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWMxMWNcdWIzMDBcdWJiMzhcdWFkNmMiLCAibmFtZV9lbmciOiAiU2VvZGFlbXVuLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2YxZWVmNiIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2Ljk3Mzg4NjQxMjg3MDIsIDM3LjYyOTQ5NjM0Nzg2ODg4XSwgWzEyNi45NTQyNzAxNzAwNjEyOSwgMzcuNjIyMDMzNDMxMzM5NDI1XSwgWzEyNi45NTI0NzUyMDMwNTcyLCAzNy42MDUwODY5MjczNzA0NV0sIFsxMjYuOTA1MjIwNjU4MzEwNTMsIDM3LjU3NDA5NzAwNTIyNTc0XSwgWzEyNi44ODQzMzI4NDc3MzI4OCwgMzcuNTg4MTQzMzIyODgwNTI2XSwgWzEyNi45MDM5NjY4MTAwMzU5NSwgMzcuNTkyMjc0MDM0MTk5NDJdLCBbMTI2LjkwMzAzMDY2MTc3NjY4LCAzNy42MDk5Nzc5MTE0MDEzNDRdLCBbMTI2LjkxNDU1NDgxNDI5NjQ4LCAzNy42NDE1MDA1MDk5NjkzNV0sIFsxMjYuOTU2NDczNzk3Mzg3LCAzNy42NTI0ODA3MzczMzk0NDVdLCBbMTI2Ljk3Mzg4NjQxMjg3MDIsIDM3LjYyOTQ5NjM0Nzg2ODg4XV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJcdWM3NDBcdWQzYzlcdWFkNmMiLCAicHJvcGVydGllcyI6IHsiYmFzZV95ZWFyIjogIjIwMTMiLCAiY29kZSI6ICIxMTEyMCIsICJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiXHVjNzQwXHVkM2M5XHVhZDZjIiwgIm5hbWVfZW5nIjogIkV1bnB5ZW9uZy1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNkNGI5ZGEiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wODM4NzUyNzAzMTk1LCAzNy42OTM1OTUzNDIwMjAzNF0sIFsxMjcuMDk3MDYzOTEzMDk2OTUsIDM3LjY4NjM4MzcxOTM3MjI5NF0sIFsxMjcuMDk0NDA3NjYyOTg3MTcsIDM3LjY0NzEzNDkwNDczMDQ1XSwgWzEyNy4xMTMyNjc5NTg1NTE5OSwgMzcuNjM5NjIyOTA1MzE1OTI1XSwgWzEyNy4xMDc4MjI3NzY4ODEyOSwgMzcuNjE4MDQyNDQyNDEwNjldLCBbMTI3LjA3MzUxMjQzODI1Mjc4LCAzNy42MTI4MzY2MDM0MjMxM10sIFsxMjcuMDUyMDkzNzM1Njg2MTksIDM3LjYyMTY0MDY1NDg3NzgyXSwgWzEyNy4wNDM1ODgwMDg5NTYwOSwgMzcuNjI4NDg5MzEyOTg3MTVdLCBbMTI3LjA1ODAwMDc1MjIwMDkxLCAzNy42NDMxODI2Mzg3ODI3Nl0sIFsxMjcuMDUyODg0Nzk3MTA0ODUsIDM3LjY4NDIzODU3MDg0MzQ3XSwgWzEyNy4wODM4NzUyNzAzMTk1LCAzNy42OTM1OTUzNDIwMjAzNF1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHViMTc4XHVjNmQwXHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTExMTAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YjE3OFx1YzZkMFx1YWQ2YyIsICJuYW1lX2VuZyI6ICJOb3dvbi1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNkNGI5ZGEiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wNTI4ODQ3OTcxMDQ4NSwgMzcuNjg0MjM4NTcwODQzNDddLCBbMTI3LjA1ODAwMDc1MjIwMDkxLCAzNy42NDMxODI2Mzg3ODI3Nl0sIFsxMjcuMDQzNTg4MDA4OTU2MDksIDM3LjYyODQ4OTMxMjk4NzE1XSwgWzEyNy4wMTQ2NTkzNTg5MjQ2NiwgMzcuNjQ5NDM2ODc0OTY4MTJdLCBbMTI3LjAyMDYyMTE2MTQxMzg5LCAzNy42NjcxNzM1NzU5NzEyMDVdLCBbMTI3LjAxMDM5NjY2MDQyMDcxLCAzNy42ODE4OTQ1ODk2MDM1OTRdLCBbMTI3LjAxNzk1MDk5MjAzNDMyLCAzNy42OTgyNDQxMjc3NTY2Ml0sIFsxMjcuMDUyODg0Nzk3MTA0ODUsIDM3LjY4NDIzODU3MDg0MzQ3XV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJcdWIzYzRcdWJkMDlcdWFkNmMiLCAicHJvcGVydGllcyI6IHsiYmFzZV95ZWFyIjogIjIwMTMiLCAiY29kZSI6ICIxMTEwMCIsICJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiXHViM2M0XHViZDA5XHVhZDZjIiwgIm5hbWVfZW5nIjogIkRvYm9uZy1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNmMWVlZjYiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNi45OTM4MzkwMzQyNCwgMzcuNjc2NjgxNzYxMTk5MDg1XSwgWzEyNy4wMTAzOTY2NjA0MjA3MSwgMzcuNjgxODk0NTg5NjAzNTk0XSwgWzEyNy4wMjA2MjExNjE0MTM4OSwgMzcuNjY3MTczNTc1OTcxMjA1XSwgWzEyNy4wMTQ2NTkzNTg5MjQ2NiwgMzcuNjQ5NDM2ODc0OTY4MTJdLCBbMTI3LjA0MzU4ODAwODk1NjA5LCAzNy42Mjg0ODkzMTI5ODcxNV0sIFsxMjcuMDUyMDkzNzM1Njg2MTksIDM3LjYyMTY0MDY1NDg3NzgyXSwgWzEyNy4wMzg5MjQwMDk5MjMwMSwgMzcuNjA5NzE1NjExMDIzODE2XSwgWzEyNy4wMTI4MTU0NzQ5NTIzLCAzNy42MTM2NTIyNDM0NzAyNTZdLCBbMTI2Ljk4NjcyNzA1NTEzODY5LCAzNy42MzM3NzY0MTI4ODE5Nl0sIFsxMjYuOTgxNzQ1MjY3NjU1MSwgMzcuNjUyMDk3NjkzODc3NzZdLCBbMTI2Ljk5MzgzOTAzNDI0LCAzNy42NzY2ODE3NjExOTkwODVdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YWMxNVx1YmQ4MVx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMDkwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWFjMTVcdWJkODFcdWFkNmMiLCAibmFtZV9lbmciOiAiR2FuZ2J1ay1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNmMWVlZjYiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNi45NzcxNzU0MDY0MTYsIDM3LjYyODU5NzE1NDAwMzg4XSwgWzEyNi45ODY3MjcwNTUxMzg2OSwgMzcuNjMzNzc2NDEyODgxOTZdLCBbMTI3LjAxMjgxNTQ3NDk1MjMsIDM3LjYxMzY1MjI0MzQ3MDI1Nl0sIFsxMjcuMDM4OTI0MDA5OTIzMDEsIDM3LjYwOTcxNTYxMTAyMzgxNl0sIFsxMjcuMDUyMDkzNzM1Njg2MTksIDM3LjYyMTY0MDY1NDg3NzgyXSwgWzEyNy4wNzM1MTI0MzgyNTI3OCwgMzcuNjEyODM2NjAzNDIzMTNdLCBbMTI3LjA3MzgyNzA3MDk5MjI3LCAzNy42MDQwMTkyODk4NjQxOV0sIFsxMjcuMDQyNzA1MjIyMDk0LCAzNy41OTIzOTQzNzU5MzM5MV0sIFsxMjcuMDI1MjcyNTQ1MjgwMDMsIDM3LjU3NTI0NjE2MjQ1MjQ5XSwgWzEyNi45OTM0ODI5MzM1ODMxNCwgMzcuNTg4NTY1NDU3MjE2MTU2XSwgWzEyNi45ODg3OTg2NTk5MjM4NCwgMzcuNjExODkyNzMxOTc1Nl0sIFsxMjYuOTc3MTc1NDA2NDE2LCAzNy42Mjg1OTcxNTQwMDM4OF1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVjMTMxXHViZDgxXHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEwODAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YzEzMVx1YmQ4MVx1YWQ2YyIsICJuYW1lX2VuZyI6ICJTZW9uZ2J1ay1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNmMWVlZjYiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wNzM1MTI0MzgyNTI3OCwgMzcuNjEyODM2NjAzNDIzMTNdLCBbMTI3LjEwNzgyMjc3Njg4MTI5LCAzNy42MTgwNDI0NDI0MTA2OV0sIFsxMjcuMTIwMTI0NjAyMDExNCwgMzcuNjAxNzg0NTc1OTgxODhdLCBbMTI3LjEwMzA0MTc0MjQ5MjE0LCAzNy41NzA3NjM0MjI5MDk1NV0sIFsxMjcuMDgwNjg1NDEyODA0MDMsIDM3LjU2OTA2NDI1NTE5MDE3XSwgWzEyNy4wNzM4MjcwNzA5OTIyNywgMzcuNjA0MDE5Mjg5ODY0MTldLCBbMTI3LjA3MzUxMjQzODI1Mjc4LCAzNy42MTI4MzY2MDM0MjMxM11dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVjOTExXHViNzkxXHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEwNzAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YzkxMVx1Yjc5MVx1YWQ2YyIsICJuYW1lX2VuZyI6ICJKdW5nbmFuZy1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNkNGI5ZGEiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wMjUyNzI1NDUyODAwMywgMzcuNTc1MjQ2MTYyNDUyNDldLCBbMTI3LjA0MjcwNTIyMjA5NCwgMzcuNTkyMzk0Mzc1OTMzOTFdLCBbMTI3LjA3MzgyNzA3MDk5MjI3LCAzNy42MDQwMTkyODk4NjQxOV0sIFsxMjcuMDgwNjg1NDEyODA0MDMsIDM3LjU2OTA2NDI1NTE5MDE3XSwgWzEyNy4wNzQyMTA1MzAyNDM2MiwgMzcuNTU3MjQ3Njk3MTIwODVdLCBbMTI3LjA1MDA1NjAxMDgxNTY3LCAzNy41Njc1Nzc2MTI1OTA4NDZdLCBbMTI3LjAyNTQ3MjY2MzQ5OTc2LCAzNy41Njg5NDM1NTIyMzc3MzRdLCBbMTI3LjAyNTI3MjU0NTI4MDAzLCAzNy41NzUyNDYxNjI0NTI0OV1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHViM2Q5XHViMzAwXHViYjM4XHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEwNjAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YjNkOVx1YjMwMFx1YmIzOFx1YWQ2YyIsICJuYW1lX2VuZyI6ICJEb25nZGFlbXVuLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2Q0YjlkYSIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI3LjA4MDY4NTQxMjgwNDAzLCAzNy41NjkwNjQyNTUxOTAxN10sIFsxMjcuMTAzMDQxNzQyNDkyMTQsIDM3LjU3MDc2MzQyMjkwOTU1XSwgWzEyNy4xMTUxOTU4NDk4MTYwNiwgMzcuNTU3NTMzMTgwNzA0OTE1XSwgWzEyNy4xMTE2NzY0MjAzNjA4LCAzNy41NDA2Njk5NTUzMjQ5NjVdLCBbMTI3LjEwMDg3NTE5NzkxOTYyLCAzNy41MjQ4NDEyMjAxNjcwNTVdLCBbMTI3LjA2OTA2OTgxMzAzNzIsIDM3LjUyMjI3OTQyMzUwNTAyNl0sIFsxMjcuMDU4NjczNTkyODgzOTgsIDM3LjUyNjI5OTc0OTIyNTY4XSwgWzEyNy4wNzQyMTA1MzAyNDM2MiwgMzcuNTU3MjQ3Njk3MTIwODVdLCBbMTI3LjA4MDY4NTQxMjgwNDAzLCAzNy41NjkwNjQyNTUxOTAxN11dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVhZDExXHVjOWM0XHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEwNTAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YWQxMVx1YzljNFx1YWQ2YyIsICJuYW1lX2VuZyI6ICJHd2FuZ2ppbi1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNjOTk0YzciLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wMjU0NzI2NjM0OTk3NiwgMzcuNTY4OTQzNTUyMjM3NzM0XSwgWzEyNy4wNTAwNTYwMTA4MTU2NywgMzcuNTY3NTc3NjEyNTkwODQ2XSwgWzEyNy4wNzQyMTA1MzAyNDM2MiwgMzcuNTU3MjQ3Njk3MTIwODVdLCBbMTI3LjA1ODY3MzU5Mjg4Mzk4LCAzNy41MjYyOTk3NDkyMjU2OF0sIFsxMjcuMDIzMDI4MzE4OTA1NTksIDM3LjUzMjMxODk5NTgyNjYzXSwgWzEyNy4wMTA3MDg5NDE3NzQ4MiwgMzcuNTQxMTgwNDg5NjQ3NjJdLCBbMTI3LjAyNTQ3MjY2MzQ5OTc2LCAzNy41Njg5NDM1NTIyMzc3MzRdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzEzMVx1YjNkOVx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMDQwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWMxMzFcdWIzZDlcdWFkNmMiLCAibmFtZV9lbmciOiAiU2Vvbmdkb25nLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2YxZWVmNiIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI3LjAxMDcwODk0MTc3NDgyLCAzNy41NDExODA0ODk2NDc2Ml0sIFsxMjcuMDIzMDI4MzE4OTA1NTksIDM3LjUzMjMxODk5NTgyNjYzXSwgWzEyNy4wMTM5NzExOTY2NzUxMywgMzcuNTI1MDM5ODgyODk2NjldLCBbMTI2Ljk4MjIzODA3OTE2MDgxLCAzNy41MDkzMTQ5NjY3NzAzMjZdLCBbMTI2Ljk1MjQ5OTkwMjk4MTU5LCAzNy41MTcyMjUwMDc0MTgxM10sIFsxMjYuOTQ1NjY3MzMwODMyMTIsIDM3LjUyNjYxNzU0MjQ1MzM2Nl0sIFsxMjYuOTY0NDg1NzA1NTMwNTUsIDM3LjU0ODcwNTY5MjAyMTYzNV0sIFsxMjYuOTg3NTI5OTY5MDMzMjgsIDM3LjU1MDk0ODE4ODA3MTM5XSwgWzEyNy4wMTA3MDg5NDE3NzQ4MiwgMzcuNTQxMTgwNDg5NjQ3NjJdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzZhOVx1YzBiMFx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMDMwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWM2YTlcdWMwYjBcdWFkNmMiLCAibmFtZV9lbmciOiAiWW9uZ3Nhbi1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNkNGI5ZGEiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wMjU0NzI2NjM0OTk3NiwgMzcuNTY4OTQzNTUyMjM3NzM0XSwgWzEyNy4wMTA3MDg5NDE3NzQ4MiwgMzcuNTQxMTgwNDg5NjQ3NjJdLCBbMTI2Ljk4NzUyOTk2OTAzMzI4LCAzNy41NTA5NDgxODgwNzEzOV0sIFsxMjYuOTY0NDg1NzA1NTMwNTUsIDM3LjU0ODcwNTY5MjAyMTYzNV0sIFsxMjYuOTYzNTgyMjY3MTA4MTIsIDM3LjU1NjA1NjM1NDc1MTU0XSwgWzEyNi45Njg3MzYzMzI3OTA3NSwgMzcuNTYzMTM2MDQ2OTA4MjddLCBbMTI3LjAyNTQ3MjY2MzQ5OTc2LCAzNy41Njg5NDM1NTIyMzc3MzRdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzkxMVx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMDIwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWM5MTFcdWFkNmMiLCAibmFtZV9lbmciOiAiSnVuZy1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNkNGI5ZGEiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNi45NzM4ODY0MTI4NzAyLCAzNy42Mjk0OTYzNDc4Njg4OF0sIFsxMjYuOTc3MTc1NDA2NDE2LCAzNy42Mjg1OTcxNTQwMDM4OF0sIFsxMjYuOTg4Nzk4NjU5OTIzODQsIDM3LjYxMTg5MjczMTk3NTZdLCBbMTI2Ljk5MzQ4MjkzMzU4MzE0LCAzNy41ODg1NjU0NTcyMTYxNTZdLCBbMTI3LjAyNTI3MjU0NTI4MDAzLCAzNy41NzUyNDYxNjI0NTI0OV0sIFsxMjcuMDI1NDcyNjYzNDk5NzYsIDM3LjU2ODk0MzU1MjIzNzczNF0sIFsxMjYuOTY4NzM2MzMyNzkwNzUsIDM3LjU2MzEzNjA0NjkwODI3XSwgWzEyNi45NTU2NTQyNTg0NjQ2MywgMzcuNTc2MDgwNzkwODgxNDU2XSwgWzEyNi45NTI0NzUyMDMwNTcyLCAzNy42MDUwODY5MjczNzA0NV0sIFsxMjYuOTU0MjcwMTcwMDYxMjksIDM3LjYyMjAzMzQzMTMzOTQyNV0sIFsxMjYuOTczODg2NDEyODcwMiwgMzcuNjI5NDk2MzQ3ODY4ODhdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1Yzg4NVx1Yjg1Y1x1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMDEwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWM4ODVcdWI4NWNcdWFkNmMiLCAibmFtZV9lbmciOiAiSm9uZ25vLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2Q0YjlkYSIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn1dLCAidHlwZSI6ICJGZWF0dXJlQ29sbGVjdGlvbiJ9CiAgICAgICAgICAgIAogICAgICAgICAgICApLmFkZFRvKGNob3JvcGxldGhfMDgyOGFkN2MxNTNkNGUyY2JhMzc4NTNkNGRkOGE0NzYpOwogICAgICAgIGdlb19qc29uX2FmNWM2ZjQ5OGY4MzRhN2U4MWQxMjJmMzk3YjYxZmEyLnNldFN0eWxlKGZ1bmN0aW9uKGZlYXR1cmUpIHtyZXR1cm4gZmVhdHVyZS5wcm9wZXJ0aWVzLnN0eWxlO30pOwogICAgICAgIAogICAgCiAgICB2YXIgY29sb3JfbWFwXzNkMTM0MGQ4ZmY4NTRkOGViY2VhNjI3MDY0MWRjMjRmID0ge307CgogICAgCiAgICBjb2xvcl9tYXBfM2QxMzQwZDhmZjg1NGQ4ZWJjZWE2MjcwNjQxZGMyNGYuY29sb3IgPSBkMy5zY2FsZS50aHJlc2hvbGQoKQogICAgICAgICAgICAgIC5kb21haW4oWzAuMCwgMC4wMDIwMDQwMDgwMTYwMzIwNjM1LCAwLjAwNDAwODAxNjAzMjA2NDEyNywgMC4wMDYwMTIwMjQwNDgwOTYxOTEsIDAuMDA4MDE2MDMyMDY0MTI4MjU0LCAwLjAxMDAyMDA0MDA4MDE2MDMxOSwgMC4wMTIwMjQwNDgwOTYxOTIzODEsIDAuMDE0MDI4MDU2MTEyMjI0NDQ2LCAwLjAxNjAzMjA2NDEyODI1NjUwOCwgMC4wMTgwMzYwNzIxNDQyODg1NzQsIDAuMDIwMDQwMDgwMTYwMzIwNjM3LCAwLjAyMjA0NDA4ODE3NjM1MjcwMywgMC4wMjQwNDgwOTYxOTIzODQ3NjIsIDAuMDI2MDUyMTA0MjA4NDE2ODI1LCAwLjAyODA1NjExMjIyNDQ0ODg5LCAwLjAzMDA2MDEyMDI0MDQ4MDk1NCwgMC4wMzIwNjQxMjgyNTY1MTMwMTYsIDAuMDM0MDY4MTM2MjcyNTQ1MDgsIDAuMDM2MDcyMTQ0Mjg4NTc3MTUsIDAuMDM4MDc2MTUyMzA0NjA5MjEsIDAuMDQwMDgwMTYwMzIwNjQxMjc0LCAwLjA0MjA4NDE2ODMzNjY3MzM0LCAwLjA0NDA4ODE3NjM1MjcwNTQwNiwgMC4wNDYwOTIxODQzNjg3Mzc0NjUsIDAuMDQ4MDk2MTkyMzg0NzY5NTI1LCAwLjA1MDEwMDIwMDQwMDgwMTU5LCAwLjA1MjEwNDIwODQxNjgzMzY1LCAwLjA1NDEwODIxNjQzMjg2NTcxNiwgMC4wNTYxMTIyMjQ0NDg4OTc3OCwgMC4wNTgxMTYyMzI0NjQ5Mjk4NSwgMC4wNjAxMjAyNDA0ODA5NjE5MSwgMC4wNjIxMjQyNDg0OTY5OTM5NzQsIDAuMDY0MTI4MjU2NTEzMDI2MDMsIDAuMDY2MTMyMjY0NTI5MDU4MSwgMC4wNjgxMzYyNzI1NDUwOTAxNywgMC4wNzAxNDAyODA1NjExMjIyMywgMC4wNzIxNDQyODg1NzcxNTQzLCAwLjA3NDE0ODI5NjU5MzE4NjM2LCAwLjA3NjE1MjMwNDYwOTIxODQyLCAwLjA3ODE1NjMxMjYyNTI1MDQ4LCAwLjA4MDE2MDMyMDY0MTI4MjU1LCAwLjA4MjE2NDMyODY1NzMxNDYxLCAwLjA4NDE2ODMzNjY3MzM0NjY4LCAwLjA4NjE3MjM0NDY4OTM3ODc1LCAwLjA4ODE3NjM1MjcwNTQxMDgxLCAwLjA5MDE4MDM2MDcyMTQ0Mjg2LCAwLjA5MjE4NDM2ODczNzQ3NDkzLCAwLjA5NDE4ODM3Njc1MzUwNywgMC4wOTYxOTIzODQ3Njk1MzkwNSwgMC4wOTgxOTYzOTI3ODU1NzExMiwgMC4xMDAyMDA0MDA4MDE2MDMxOCwgMC4xMDIyMDQ0MDg4MTc2MzUyNSwgMC4xMDQyMDg0MTY4MzM2NjczLCAwLjEwNjIxMjQyNDg0OTY5OTM3LCAwLjEwODIxNjQzMjg2NTczMTQzLCAwLjExMDIyMDQ0MDg4MTc2MzUsIDAuMTEyMjI0NDQ4ODk3Nzk1NTYsIDAuMTE0MjI4NDU2OTEzODI3NjMsIDAuMTE2MjMyNDY0OTI5ODU5NywgMC4xMTgyMzY0NzI5NDU4OTE3NSwgMC4xMjAyNDA0ODA5NjE5MjM4MiwgMC4xMjIyNDQ0ODg5Nzc5NTU4OCwgMC4xMjQyNDg0OTY5OTM5ODc5NSwgMC4xMjYyNTI1MDUwMTAwMiwgMC4xMjgyNTY1MTMwMjYwNTIwNywgMC4xMzAyNjA1MjEwNDIwODQxMywgMC4xMzIyNjQ1MjkwNTgxMTYyLCAwLjEzNDI2ODUzNzA3NDE0ODI2LCAwLjEzNjI3MjU0NTA5MDE4MDMzLCAwLjEzODI3NjU1MzEwNjIxMjQsIDAuMTQwMjgwNTYxMTIyMjQ0NDYsIDAuMTQyMjg0NTY5MTM4Mjc2NTMsIDAuMTQ0Mjg4NTc3MTU0MzA4NiwgMC4xNDYyOTI1ODUxNzAzNDA2NiwgMC4xNDgyOTY1OTMxODYzNzI3MywgMC4xNTAzMDA2MDEyMDI0MDQ4LCAwLjE1MjMwNDYwOTIxODQzNjgzLCAwLjE1NDMwODYxNzIzNDQ2ODksIDAuMTU2MzEyNjI1MjUwNTAwOTYsIDAuMTU4MzE2NjMzMjY2NTMzMDMsIDAuMTYwMzIwNjQxMjgyNTY1MSwgMC4xNjIzMjQ2NDkyOTg1OTcxNiwgMC4xNjQzMjg2NTczMTQ2MjkyMywgMC4xNjYzMzI2NjUzMzA2NjEzLCAwLjE2ODMzNjY3MzM0NjY5MzM2LCAwLjE3MDM0MDY4MTM2MjcyNTQzLCAwLjE3MjM0NDY4OTM3ODc1NzUsIDAuMTc0MzQ4Njk3Mzk0Nzg5NTYsIDAuMTc2MzUyNzA1NDEwODIxNjMsIDAuMTc4MzU2NzEzNDI2ODUzNywgMC4xODAzNjA3MjE0NDI4ODU3MywgMC4xODIzNjQ3Mjk0NTg5MTc4LCAwLjE4NDM2ODczNzQ3NDk0OTg2LCAwLjE4NjM3Mjc0NTQ5MDk4MTkzLCAwLjE4ODM3Njc1MzUwNzAxNCwgMC4xOTAzODA3NjE1MjMwNDYwNiwgMC4xOTIzODQ3Njk1MzkwNzgxLCAwLjE5NDM4ODc3NzU1NTExMDE2LCAwLjE5NjM5Mjc4NTU3MTE0MjIzLCAwLjE5ODM5Njc5MzU4NzE3NDMsIDAuMjAwNDAwODAxNjAzMjA2MzYsIDAuMjAyNDA0ODA5NjE5MjM4NDMsIDAuMjA0NDA4ODE3NjM1MjcwNSwgMC4yMDY0MTI4MjU2NTEzMDI1NiwgMC4yMDg0MTY4MzM2NjczMzQ2LCAwLjIxMDQyMDg0MTY4MzM2NjY3LCAwLjIxMjQyNDg0OTY5OTM5ODczLCAwLjIxNDQyODg1NzcxNTQzMDgsIDAuMjE2NDMyODY1NzMxNDYyODYsIDAuMjE4NDM2ODczNzQ3NDk0OTMsIDAuMjIwNDQwODgxNzYzNTI3LCAwLjIyMjQ0NDg4OTc3OTU1OTA2LCAwLjIyNDQ0ODg5Nzc5NTU5MTEzLCAwLjIyNjQ1MjkwNTgxMTYyMzIsIDAuMjI4NDU2OTEzODI3NjU1MjYsIDAuMjMwNDYwOTIxODQzNjg3MzMsIDAuMjMyNDY0OTI5ODU5NzE5NCwgMC4yMzQ0Njg5Mzc4NzU3NTE0NiwgMC4yMzY0NzI5NDU4OTE3ODM1LCAwLjIzODQ3Njk1MzkwNzgxNTU2LCAwLjI0MDQ4MDk2MTkyMzg0NzYzLCAwLjI0MjQ4NDk2OTkzOTg3OTcsIDAuMjQ0NDg4OTc3OTU1OTExNzYsIDAuMjQ2NDkyOTg1OTcxOTQzODMsIDAuMjQ4NDk2OTkzOTg3OTc1OSwgMC4yNTA1MDEwMDIwMDQwMDc5MywgMC4yNTI1MDUwMTAwMjAwNCwgMC4yNTQ1MDkwMTgwMzYwNzIwNywgMC4yNTY1MTMwMjYwNTIxMDQxMywgMC4yNTg1MTcwMzQwNjgxMzYyLCAwLjI2MDUyMTA0MjA4NDE2ODI2LCAwLjI2MjUyNTA1MDEwMDIwMDMzLCAwLjI2NDUyOTA1ODExNjIzMjQsIDAuMjY2NTMzMDY2MTMyMjY0NDYsIDAuMjY4NTM3MDc0MTQ4Mjk2NTMsIDAuMjcwNTQxMDgyMTY0MzI4NiwgMC4yNzI1NDUwOTAxODAzNjA2NiwgMC4yNzQ1NDkwOTgxOTYzOTI3LCAwLjI3NjU1MzEwNjIxMjQyNDgsIDAuMjc4NTU3MTE0MjI4NDU2ODYsIDAuMjgwNTYxMTIyMjQ0NDg4OSwgMC4yODI1NjUxMzAyNjA1MjEsIDAuMjg0NTY5MTM4Mjc2NTUzMDYsIDAuMjg2NTczMTQ2MjkyNTg1MSwgMC4yODg1NzcxNTQzMDg2MTcyLCAwLjI5MDU4MTE2MjMyNDY0OTI2LCAwLjI5MjU4NTE3MDM0MDY4MTMsIDAuMjk0NTg5MTc4MzU2NzEzNCwgMC4yOTY1OTMxODYzNzI3NDU0NSwgMC4yOTg1OTcxOTQzODg3Nzc1LCAwLjMwMDYwMTIwMjQwNDgwOTYsIDAuMzAyNjA1MjEwNDIwODQxNjUsIDAuMzA0NjA5MjE4NDM2ODczNjYsIDAuMzA2NjEzMjI2NDUyOTA1NzMsIDAuMzA4NjE3MjM0NDY4OTM3OCwgMC4zMTA2MjEyNDI0ODQ5Njk4NiwgMC4zMTI2MjUyNTA1MDEwMDE5MywgMC4zMTQ2MjkyNTg1MTcwMzQsIDAuMzE2NjMzMjY2NTMzMDY2MDYsIDAuMzE4NjM3Mjc0NTQ5MDk4MSwgMC4zMjA2NDEyODI1NjUxMzAyLCAwLjMyMjY0NTI5MDU4MTE2MjI2LCAwLjMyNDY0OTI5ODU5NzE5NDMsIDAuMzI2NjUzMzA2NjEzMjI2NCwgMC4zMjg2NTczMTQ2MjkyNTg0NiwgMC4zMzA2NjEzMjI2NDUyOTA1LCAwLjMzMjY2NTMzMDY2MTMyMjYsIDAuMzM0NjY5MzM4Njc3MzU0NjYsIDAuMzM2NjczMzQ2NjkzMzg2NywgMC4zMzg2NzczNTQ3MDk0MTg4LCAwLjM0MDY4MTM2MjcyNTQ1MDg1LCAwLjM0MjY4NTM3MDc0MTQ4MjksIDAuMzQ0Njg5Mzc4NzU3NTE1LCAwLjM0NjY5MzM4Njc3MzU0NzA1LCAwLjM0ODY5NzM5NDc4OTU3OTEsIDAuMzUwNzAxNDAyODA1NjExMiwgMC4zNTI3MDU0MTA4MjE2NDMyNSwgMC4zNTQ3MDk0MTg4Mzc2NzUzLCAwLjM1NjcxMzQyNjg1MzcwNzQsIDAuMzU4NzE3NDM0ODY5NzM5NDUsIDAuMzYwNzIxNDQyODg1NzcxNDYsIDAuMzYyNzI1NDUwOTAxODAzNSwgMC4zNjQ3Mjk0NTg5MTc4MzU2LCAwLjM2NjczMzQ2NjkzMzg2NzY2LCAwLjM2ODczNzQ3NDk0OTg5OTcsIDAuMzcwNzQxNDgyOTY1OTMxOCwgMC4zNzI3NDU0OTA5ODE5NjM4NiwgMC4zNzQ3NDk0OTg5OTc5OTU5LCAwLjM3Njc1MzUwNzAxNDAyOCwgMC4zNzg3NTc1MTUwMzAwNjAwNSwgMC4zODA3NjE1MjMwNDYwOTIxLCAwLjM4Mjc2NTUzMTA2MjEyNDIsIDAuMzg0NzY5NTM5MDc4MTU2MiwgMC4zODY3NzM1NDcwOTQxODgyNiwgMC4zODg3Nzc1NTUxMTAyMjAzMywgMC4zOTA3ODE1NjMxMjYyNTI0LCAwLjM5Mjc4NTU3MTE0MjI4NDQ2LCAwLjM5NDc4OTU3OTE1ODMxNjUzLCAwLjM5Njc5MzU4NzE3NDM0ODYsIDAuMzk4Nzk3NTk1MTkwMzgwNjYsIDAuNDAwODAxNjAzMjA2NDEyNywgMC40MDI4MDU2MTEyMjI0NDQ4LCAwLjQwNDgwOTYxOTIzODQ3Njg2LCAwLjQwNjgxMzYyNzI1NDUwODksIDAuNDA4ODE3NjM1MjcwNTQxLCAwLjQxMDgyMTY0MzI4NjU3MzA2LCAwLjQxMjgyNTY1MTMwMjYwNTEsIDAuNDE0ODI5NjU5MzE4NjM3MTMsIDAuNDE2ODMzNjY3MzM0NjY5MiwgMC40MTg4Mzc2NzUzNTA3MDEyNywgMC40MjA4NDE2ODMzNjY3MzMzMywgMC40MjI4NDU2OTEzODI3NjU0LCAwLjQyNDg0OTY5OTM5ODc5NzQ2LCAwLjQyNjg1MzcwNzQxNDgyOTUzLCAwLjQyODg1NzcxNTQzMDg2MTYsIDAuNDMwODYxNzIzNDQ2ODkzNjYsIDAuNDMyODY1NzMxNDYyOTI1NzMsIDAuNDM0ODY5NzM5NDc4OTU3OCwgMC40MzY4NzM3NDc0OTQ5ODk4NiwgMC40Mzg4Nzc3NTU1MTEwMjE5LCAwLjQ0MDg4MTc2MzUyNzA1NCwgMC40NDI4ODU3NzE1NDMwODYwNiwgMC40NDQ4ODk3Nzk1NTkxMTgxLCAwLjQ0Njg5Mzc4NzU3NTE1MDIsIDAuNDQ4ODk3Nzk1NTkxMTgyMjYsIDAuNDUwOTAxODAzNjA3MjE0MywgMC40NTI5MDU4MTE2MjMyNDY0LCAwLjQ1NDkwOTgxOTYzOTI3ODQ2LCAwLjQ1NjkxMzgyNzY1NTMxMDUsIDAuNDU4OTE3ODM1NjcxMzQyNiwgMC40NjA5MjE4NDM2ODczNzQ2NSwgMC40NjI5MjU4NTE3MDM0MDY3LCAwLjQ2NDkyOTg1OTcxOTQzODgsIDAuNDY2OTMzODY3NzM1NDcwODUsIDAuNDY4OTM3ODc1NzUxNTAyOSwgMC40NzA5NDE4ODM3Njc1MzQ5MywgMC40NzI5NDU4OTE3ODM1NjcsIDAuNDc0OTQ5ODk5Nzk5NTk5MDYsIDAuNDc2OTUzOTA3ODE1NjMxMTMsIDAuNDc4OTU3OTE1ODMxNjYzMiwgMC40ODA5NjE5MjM4NDc2OTUyNiwgMC40ODI5NjU5MzE4NjM3MjczLCAwLjQ4NDk2OTkzOTg3OTc1OTQsIDAuNDg2OTczOTQ3ODk1NzkxNDYsIDAuNDg4OTc3OTU1OTExODIzNSwgMC40OTA5ODE5NjM5Mjc4NTU2LCAwLjQ5Mjk4NTk3MTk0Mzg4NzY2LCAwLjQ5NDk4OTk3OTk1OTkxOTcsIDAuNDk2OTkzOTg3OTc1OTUxOCwgMC40OTg5OTc5OTU5OTE5ODM4NiwgMC41MDEwMDIwMDQwMDgwMTU5LCAwLjUwMzAwNjAxMjAyNDA0NzksIDAuNTA1MDEwMDIwMDQwMDgsIDAuNTA3MDE0MDI4MDU2MTEyMSwgMC41MDkwMTgwMzYwNzIxNDQxLCAwLjUxMTAyMjA0NDA4ODE3NjIsIDAuNTEzMDI2MDUyMTA0MjA4MywgMC41MTUwMzAwNjAxMjAyNDAzLCAwLjUxNzAzNDA2ODEzNjI3MjQsIDAuNTE5MDM4MDc2MTUyMzA0NSwgMC41MjEwNDIwODQxNjgzMzY1LCAwLjUyMzA0NjA5MjE4NDM2ODYsIDAuNTI1MDUwMTAwMjAwNDAwNywgMC41MjcwNTQxMDgyMTY0MzI3LCAwLjUyOTA1ODExNjIzMjQ2NDgsIDAuNTMxMDYyMTI0MjQ4NDk2OSwgMC41MzMwNjYxMzIyNjQ1Mjg5LCAwLjUzNTA3MDE0MDI4MDU2MSwgMC41MzcwNzQxNDgyOTY1OTMxLCAwLjUzOTA3ODE1NjMxMjYyNTEsIDAuNTQxMDgyMTY0MzI4NjU3MiwgMC41NDMwODYxNzIzNDQ2ODkzLCAwLjU0NTA5MDE4MDM2MDcyMTMsIDAuNTQ3MDk0MTg4Mzc2NzUzNCwgMC41NDkwOTgxOTYzOTI3ODU1LCAwLjU1MTEwMjIwNDQwODgxNzUsIDAuNTUzMTA2MjEyNDI0ODQ5NiwgMC41NTUxMTAyMjA0NDA4ODE3LCAwLjU1NzExNDIyODQ1NjkxMzcsIDAuNTU5MTE4MjM2NDcyOTQ1OCwgMC41NjExMjIyNDQ0ODg5Nzc5LCAwLjU2MzEyNjI1MjUwNTAwOTksIDAuNTY1MTMwMjYwNTIxMDQyLCAwLjU2NzEzNDI2ODUzNzA3NCwgMC41NjkxMzgyNzY1NTMxMDYxLCAwLjU3MTE0MjI4NDU2OTEzODIsIDAuNTczMTQ2MjkyNTg1MTcwMiwgMC41NzUxNTAzMDA2MDEyMDIzLCAwLjU3NzE1NDMwODYxNzIzNDQsIDAuNTc5MTU4MzE2NjMzMjY2NCwgMC41ODExNjIzMjQ2NDkyOTg1LCAwLjU4MzE2NjMzMjY2NTMzMDYsIDAuNTg1MTcwMzQwNjgxMzYyNiwgMC41ODcxNzQzNDg2OTczOTQ3LCAwLjU4OTE3ODM1NjcxMzQyNjgsIDAuNTkxMTgyMzY0NzI5NDU4OCwgMC41OTMxODYzNzI3NDU0OTA5LCAwLjU5NTE5MDM4MDc2MTUyMywgMC41OTcxOTQzODg3Nzc1NTUsIDAuNTk5MTk4Mzk2NzkzNTg3MSwgMC42MDEyMDI0MDQ4MDk2MTkyLCAwLjYwMzIwNjQxMjgyNTY1MTIsIDAuNjA1MjEwNDIwODQxNjgzMywgMC42MDcyMTQ0Mjg4NTc3MTU0LCAwLjYwOTIxODQzNjg3Mzc0NzMsIDAuNjExMjIyNDQ0ODg5Nzc5NCwgMC42MTMyMjY0NTI5MDU4MTE1LCAwLjYxNTIzMDQ2MDkyMTg0MzUsIDAuNjE3MjM0NDY4OTM3ODc1NiwgMC42MTkyMzg0NzY5NTM5MDc3LCAwLjYyMTI0MjQ4NDk2OTkzOTcsIDAuNjIzMjQ2NDkyOTg1OTcxOCwgMC42MjUyNTA1MDEwMDIwMDM5LCAwLjYyNzI1NDUwOTAxODAzNTksIDAuNjI5MjU4NTE3MDM0MDY4LCAwLjYzMTI2MjUyNTA1MDEsIDAuNjMzMjY2NTMzMDY2MTMyMSwgMC42MzUyNzA1NDEwODIxNjQyLCAwLjYzNzI3NDU0OTA5ODE5NjMsIDAuNjM5Mjc4NTU3MTE0MjI4MywgMC42NDEyODI1NjUxMzAyNjA0LCAwLjY0MzI4NjU3MzE0NjI5MjUsIDAuNjQ1MjkwNTgxMTYyMzI0NSwgMC42NDcyOTQ1ODkxNzgzNTY2LCAwLjY0OTI5ODU5NzE5NDM4ODYsIDAuNjUxMzAyNjA1MjEwNDIwNywgMC42NTMzMDY2MTMyMjY0NTI4LCAwLjY1NTMxMDYyMTI0MjQ4NDgsIDAuNjU3MzE0NjI5MjU4NTE2OSwgMC42NTkzMTg2MzcyNzQ1NDksIDAuNjYxMzIyNjQ1MjkwNTgxLCAwLjY2MzMyNjY1MzMwNjYxMzEsIDAuNjY1MzMwNjYxMzIyNjQ1MiwgMC42NjczMzQ2NjkzMzg2NzcyLCAwLjY2OTMzODY3NzM1NDcwOTMsIDAuNjcxMzQyNjg1MzcwNzQxNCwgMC42NzMzNDY2OTMzODY3NzM0LCAwLjY3NTM1MDcwMTQwMjgwNTUsIDAuNjc3MzU0NzA5NDE4ODM3NiwgMC42NzkzNTg3MTc0MzQ4Njk2LCAwLjY4MTM2MjcyNTQ1MDkwMTcsIDAuNjgzMzY2NzMzNDY2OTMzOCwgMC42ODUzNzA3NDE0ODI5NjU4LCAwLjY4NzM3NDc0OTQ5ODk5NzksIDAuNjg5Mzc4NzU3NTE1MDMsIDAuNjkxMzgyNzY1NTMxMDYyLCAwLjY5MzM4Njc3MzU0NzA5NDEsIDAuNjk1MzkwNzgxNTYzMTI2MiwgMC42OTczOTQ3ODk1NzkxNTgyLCAwLjY5OTM5ODc5NzU5NTE5MDMsIDAuNzAxNDAyODA1NjExMjIyNCwgMC43MDM0MDY4MTM2MjcyNTQ0LCAwLjcwNTQxMDgyMTY0MzI4NjUsIDAuNzA3NDE0ODI5NjU5MzE4NiwgMC43MDk0MTg4Mzc2NzUzNTA2LCAwLjcxMTQyMjg0NTY5MTM4MjcsIDAuNzEzNDI2ODUzNzA3NDE0OCwgMC43MTU0MzA4NjE3MjM0NDY4LCAwLjcxNzQzNDg2OTczOTQ3ODksIDAuNzE5NDM4ODc3NzU1NTEwOSwgMC43MjE0NDI4ODU3NzE1NDI5LCAwLjcyMzQ0Njg5Mzc4NzU3NSwgMC43MjU0NTA5MDE4MDM2MDcsIDAuNzI3NDU0OTA5ODE5NjM5MSwgMC43Mjk0NTg5MTc4MzU2NzEyLCAwLjczMTQ2MjkyNTg1MTcwMzIsIDAuNzMzNDY2OTMzODY3NzM1MywgMC43MzU0NzA5NDE4ODM3Njc0LCAwLjczNzQ3NDk0OTg5OTc5OTQsIDAuNzM5NDc4OTU3OTE1ODMxNSwgMC43NDE0ODI5NjU5MzE4NjM2LCAwLjc0MzQ4Njk3Mzk0Nzg5NTYsIDAuNzQ1NDkwOTgxOTYzOTI3NywgMC43NDc0OTQ5ODk5Nzk5NTk4LCAwLjc0OTQ5ODk5Nzk5NTk5MTgsIDAuNzUxNTAzMDA2MDEyMDIzOSwgMC43NTM1MDcwMTQwMjgwNTYsIDAuNzU1NTExMDIyMDQ0MDg4LCAwLjc1NzUxNTAzMDA2MDEyMDEsIDAuNzU5NTE5MDM4MDc2MTUyMiwgMC43NjE1MjMwNDYwOTIxODQyLCAwLjc2MzUyNzA1NDEwODIxNjMsIDAuNzY1NTMxMDYyMTI0MjQ4NCwgMC43Njc1MzUwNzAxNDAyODA0LCAwLjc2OTUzOTA3ODE1NjMxMjQsIDAuNzcxNTQzMDg2MTcyMzQ0NSwgMC43NzM1NDcwOTQxODgzNzY1LCAwLjc3NTU1MTEwMjIwNDQwODYsIDAuNzc3NTU1MTEwMjIwNDQwNywgMC43Nzk1NTkxMTgyMzY0NzI3LCAwLjc4MTU2MzEyNjI1MjUwNDgsIDAuNzgzNTY3MTM0MjY4NTM2OSwgMC43ODU1NzExNDIyODQ1Njg5LCAwLjc4NzU3NTE1MDMwMDYwMSwgMC43ODk1NzkxNTgzMTY2MzMxLCAwLjc5MTU4MzE2NjMzMjY2NTEsIDAuNzkzNTg3MTc0MzQ4Njk3MiwgMC43OTU1OTExODIzNjQ3MjkzLCAwLjc5NzU5NTE5MDM4MDc2MTMsIDAuNzk5NTk5MTk4Mzk2NzkzNCwgMC44MDE2MDMyMDY0MTI4MjU1LCAwLjgwMzYwNzIxNDQyODg1NzUsIDAuODA1NjExMjIyNDQ0ODg5NiwgMC44MDc2MTUyMzA0NjA5MjE3LCAwLjgwOTYxOTIzODQ3Njk1MzcsIDAuODExNjIzMjQ2NDkyOTg1OCwgMC44MTM2MjcyNTQ1MDkwMTc4LCAwLjgxNTYzMTI2MjUyNTA0OTksIDAuODE3NjM1MjcwNTQxMDgyLCAwLjgxOTYzOTI3ODU1NzExNCwgMC44MjE2NDMyODY1NzMxNDYxLCAwLjgyMzY0NzI5NDU4OTE3ODIsIDAuODI1NjUxMzAyNjA1MjEwMiwgMC44Mjc2NTUzMTA2MjEyNDIyLCAwLjgyOTY1OTMxODYzNzI3NDMsIDAuODMxNjYzMzI2NjUzMzA2MywgMC44MzM2NjczMzQ2NjkzMzg0LCAwLjgzNTY3MTM0MjY4NTM3MDUsIDAuODM3Njc1MzUwNzAxNDAyNSwgMC44Mzk2NzkzNTg3MTc0MzQ2LCAwLjg0MTY4MzM2NjczMzQ2NjcsIDAuODQzNjg3Mzc0NzQ5NDk4NywgMC44NDU2OTEzODI3NjU1MzA4LCAwLjg0NzY5NTM5MDc4MTU2MjksIDAuODQ5Njk5Mzk4Nzk3NTk0OSwgMC44NTE3MDM0MDY4MTM2MjcsIDAuODUzNzA3NDE0ODI5NjU5MSwgMC44NTU3MTE0MjI4NDU2OTExLCAwLjg1NzcxNTQzMDg2MTcyMzIsIDAuODU5NzE5NDM4ODc3NzU1MywgMC44NjE3MjM0NDY4OTM3ODczLCAwLjg2MzcyNzQ1NDkwOTgxOTQsIDAuODY1NzMxNDYyOTI1ODUxNSwgMC44Njc3MzU0NzA5NDE4ODM1LCAwLjg2OTczOTQ3ODk1NzkxNTYsIDAuODcxNzQzNDg2OTczOTQ3NywgMC44NzM3NDc0OTQ5ODk5Nzk3LCAwLjg3NTc1MTUwMzAwNjAxMTgsIDAuODc3NzU1NTExMDIyMDQzOSwgMC44Nzk3NTk1MTkwMzgwNzU5LCAwLjg4MTc2MzUyNzA1NDEwOCwgMC44ODM3Njc1MzUwNzAxNCwgMC44ODU3NzE1NDMwODYxNzIxLCAwLjg4Nzc3NTU1MTEwMjIwNDIsIDAuODg5Nzc5NTU5MTE4MjM2MywgMC44OTE3ODM1NjcxMzQyNjgzLCAwLjg5Mzc4NzU3NTE1MDMwMDQsIDAuODk1NzkxNTgzMTY2MzMyNSwgMC44OTc3OTU1OTExODIzNjQ1LCAwLjg5OTc5OTU5OTE5ODM5NjYsIDAuOTAxODAzNjA3MjE0NDI4NiwgMC45MDM4MDc2MTUyMzA0NjA3LCAwLjkwNTgxMTYyMzI0NjQ5MjgsIDAuOTA3ODE1NjMxMjYyNTI0OCwgMC45MDk4MTk2MzkyNzg1NTY5LCAwLjkxMTgyMzY0NzI5NDU4OSwgMC45MTM4Mjc2NTUzMTA2MjEsIDAuOTE1ODMxNjYzMzI2NjUzMSwgMC45MTc4MzU2NzEzNDI2ODUyLCAwLjkxOTgzOTY3OTM1ODcxNzIsIDAuOTIxODQzNjg3Mzc0NzQ5MywgMC45MjM4NDc2OTUzOTA3ODE0LCAwLjkyNTg1MTcwMzQwNjgxMzQsIDAuOTI3ODU1NzExNDIyODQ1NSwgMC45Mjk4NTk3MTk0Mzg4Nzc2LCAwLjkzMTg2MzcyNzQ1NDkwOTYsIDAuOTMzODY3NzM1NDcwOTQxNywgMC45MzU4NzE3NDM0ODY5NzM4LCAwLjkzNzg3NTc1MTUwMzAwNTgsIDAuOTM5ODc5NzU5NTE5MDM3OCwgMC45NDE4ODM3Njc1MzUwNjk5LCAwLjk0Mzg4Nzc3NTU1MTEwMTksIDAuOTQ1ODkxNzgzNTY3MTM0LCAwLjk0Nzg5NTc5MTU4MzE2NjEsIDAuOTQ5ODk5Nzk5NTk5MTk4MSwgMC45NTE5MDM4MDc2MTUyMzAyLCAwLjk1MzkwNzgxNTYzMTI2MjMsIDAuOTU1OTExODIzNjQ3Mjk0MywgMC45NTc5MTU4MzE2NjMzMjY0LCAwLjk1OTkxOTgzOTY3OTM1ODUsIDAuOTYxOTIzODQ3Njk1MzkwNSwgMC45NjM5Mjc4NTU3MTE0MjI2LCAwLjk2NTkzMTg2MzcyNzQ1NDcsIDAuOTY3OTM1ODcxNzQzNDg2NywgMC45Njk5Mzk4Nzk3NTk1MTg4LCAwLjk3MTk0Mzg4Nzc3NTU1MDksIDAuOTczOTQ3ODk1NzkxNTgyOSwgMC45NzU5NTE5MDM4MDc2MTUsIDAuOTc3OTU1OTExODIzNjQ3LCAwLjk3OTk1OTkxOTgzOTY3OTEsIDAuOTgxOTYzOTI3ODU1NzExMiwgMC45ODM5Njc5MzU4NzE3NDMyLCAwLjk4NTk3MTk0Mzg4Nzc3NTMsIDAuOTg3OTc1OTUxOTAzODA3NCwgMC45ODk5Nzk5NTk5MTk4Mzk0LCAwLjk5MTk4Mzk2NzkzNTg3MTUsIDAuOTkzOTg3OTc1OTUxOTAzNiwgMC45OTU5OTE5ODM5Njc5MzU2LCAwLjk5Nzk5NTk5MTk4Mzk2NzcsIDAuOTk5OTk5OTk5OTk5OTk5OF0pCiAgICAgICAgICAgICAgLnJhbmdlKFsnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJ10pOwogICAgCgogICAgY29sb3JfbWFwXzNkMTM0MGQ4ZmY4NTRkOGViY2VhNjI3MDY0MWRjMjRmLnggPSBkMy5zY2FsZS5saW5lYXIoKQogICAgICAgICAgICAgIC5kb21haW4oWzAuMCwgMC45OTk5OTk5OTk5OTk5OTk4XSkKICAgICAgICAgICAgICAucmFuZ2UoWzAsIDQwMF0pOwoKICAgIGNvbG9yX21hcF8zZDEzNDBkOGZmODU0ZDhlYmNlYTYyNzA2NDFkYzI0Zi5sZWdlbmQgPSBMLmNvbnRyb2woe3Bvc2l0aW9uOiAndG9wcmlnaHQnfSk7CiAgICBjb2xvcl9tYXBfM2QxMzQwZDhmZjg1NGQ4ZWJjZWE2MjcwNjQxZGMyNGYubGVnZW5kLm9uQWRkID0gZnVuY3Rpb24gKG1hcCkge3ZhciBkaXYgPSBMLkRvbVV0aWwuY3JlYXRlKCdkaXYnLCAnbGVnZW5kJyk7IHJldHVybiBkaXZ9OwogICAgY29sb3JfbWFwXzNkMTM0MGQ4ZmY4NTRkOGViY2VhNjI3MDY0MWRjMjRmLmxlZ2VuZC5hZGRUbyhtYXBfZjBjODdkZTNlNzY4NDczYTg2OGIzNDFkOWNhOTUwMWEpOwoKICAgIGNvbG9yX21hcF8zZDEzNDBkOGZmODU0ZDhlYmNlYTYyNzA2NDFkYzI0Zi54QXhpcyA9IGQzLnN2Zy5heGlzKCkKICAgICAgICAuc2NhbGUoY29sb3JfbWFwXzNkMTM0MGQ4ZmY4NTRkOGViY2VhNjI3MDY0MWRjMjRmLngpCiAgICAgICAgLm9yaWVudCgidG9wIikKICAgICAgICAudGlja1NpemUoMSkKICAgICAgICAudGlja1ZhbHVlcyhbMC4wLCAwLjE2NjY2NjY2NjY2NjY2NjYzLCAwLjMzMzMzMzMzMzMzMzMzMzI2LCAwLjQ5OTk5OTk5OTk5OTk5OTksIDAuNjY2NjY2NjY2NjY2NjY2NSwgMC44MzMzMzMzMzMzMzMzMzMxLCAwLjk5OTk5OTk5OTk5OTk5OThdKTsKCiAgICBjb2xvcl9tYXBfM2QxMzQwZDhmZjg1NGQ4ZWJjZWE2MjcwNjQxZGMyNGYuc3ZnID0gZDMuc2VsZWN0KCIubGVnZW5kLmxlYWZsZXQtY29udHJvbCIpLmFwcGVuZCgic3ZnIikKICAgICAgICAuYXR0cigiaWQiLCAnbGVnZW5kJykKICAgICAgICAuYXR0cigid2lkdGgiLCA0NTApCiAgICAgICAgLmF0dHIoImhlaWdodCIsIDQwKTsKCiAgICBjb2xvcl9tYXBfM2QxMzQwZDhmZjg1NGQ4ZWJjZWE2MjcwNjQxZGMyNGYuZyA9IGNvbG9yX21hcF8zZDEzNDBkOGZmODU0ZDhlYmNlYTYyNzA2NDFkYzI0Zi5zdmcuYXBwZW5kKCJnIikKICAgICAgICAuYXR0cigiY2xhc3MiLCAia2V5IikKICAgICAgICAuYXR0cigidHJhbnNmb3JtIiwgInRyYW5zbGF0ZSgyNSwxNikiKTsKCiAgICBjb2xvcl9tYXBfM2QxMzQwZDhmZjg1NGQ4ZWJjZWE2MjcwNjQxZGMyNGYuZy5zZWxlY3RBbGwoInJlY3QiKQogICAgICAgIC5kYXRhKGNvbG9yX21hcF8zZDEzNDBkOGZmODU0ZDhlYmNlYTYyNzA2NDFkYzI0Zi5jb2xvci5yYW5nZSgpLm1hcChmdW5jdGlvbihkLCBpKSB7CiAgICAgICAgICByZXR1cm4gewogICAgICAgICAgICB4MDogaSA/IGNvbG9yX21hcF8zZDEzNDBkOGZmODU0ZDhlYmNlYTYyNzA2NDFkYzI0Zi54KGNvbG9yX21hcF8zZDEzNDBkOGZmODU0ZDhlYmNlYTYyNzA2NDFkYzI0Zi5jb2xvci5kb21haW4oKVtpIC0gMV0pIDogY29sb3JfbWFwXzNkMTM0MGQ4ZmY4NTRkOGViY2VhNjI3MDY0MWRjMjRmLngucmFuZ2UoKVswXSwKICAgICAgICAgICAgeDE6IGkgPCBjb2xvcl9tYXBfM2QxMzQwZDhmZjg1NGQ4ZWJjZWE2MjcwNjQxZGMyNGYuY29sb3IuZG9tYWluKCkubGVuZ3RoID8gY29sb3JfbWFwXzNkMTM0MGQ4ZmY4NTRkOGViY2VhNjI3MDY0MWRjMjRmLngoY29sb3JfbWFwXzNkMTM0MGQ4ZmY4NTRkOGViY2VhNjI3MDY0MWRjMjRmLmNvbG9yLmRvbWFpbigpW2ldKSA6IGNvbG9yX21hcF8zZDEzNDBkOGZmODU0ZDhlYmNlYTYyNzA2NDFkYzI0Zi54LnJhbmdlKClbMV0sCiAgICAgICAgICAgIHo6IGQKICAgICAgICAgIH07CiAgICAgICAgfSkpCiAgICAgIC5lbnRlcigpLmFwcGVuZCgicmVjdCIpCiAgICAgICAgLmF0dHIoImhlaWdodCIsIDEwKQogICAgICAgIC5hdHRyKCJ4IiwgZnVuY3Rpb24oZCkgeyByZXR1cm4gZC54MDsgfSkKICAgICAgICAuYXR0cigid2lkdGgiLCBmdW5jdGlvbihkKSB7IHJldHVybiBkLngxIC0gZC54MDsgfSkKICAgICAgICAuc3R5bGUoImZpbGwiLCBmdW5jdGlvbihkKSB7IHJldHVybiBkLno7IH0pOwoKICAgIGNvbG9yX21hcF8zZDEzNDBkOGZmODU0ZDhlYmNlYTYyNzA2NDFkYzI0Zi5nLmNhbGwoY29sb3JfbWFwXzNkMTM0MGQ4ZmY4NTRkOGViY2VhNjI3MDY0MWRjMjRmLnhBeGlzKS5hcHBlbmQoInRleHQiKQogICAgICAgIC5hdHRyKCJjbGFzcyIsICJjYXB0aW9uIikKICAgICAgICAuYXR0cigieSIsIDIxKQogICAgICAgIC50ZXh0KCcnKTsKPC9zY3JpcHQ+" style="position:absolute;width:100%;height:100%;left:0;top:0;border:none !important;" allowfullscreen webkitallowfullscreen mozallowfullscreen></iframe></div></div>




```python
map = folium.Map(location=[37.5502, 126.982], zoom_start=11, 
                 tiles='Stamen Toner')

map.choropleth(geo_data = geo_str,
               data = crime_anal_norm['범죄'],
               columns = [crime_anal_norm.index, crime_anal_norm['범죄']],
               fill_color = 'PuRd', #PuRd, YlGnBu
               key_on = 'feature.id')
map
```

    C:\ProgramData\Anaconda3\lib\site-packages\folium\folium.py:432: FutureWarning: The choropleth  method has been deprecated. Instead use the new Choropleth class, which has the same arguments. See the example notebook 'GeoJSON_and_choropleth' for how to do this.
      FutureWarning
    




<div style="width:100%;"><div style="position:relative;width:100%;height:0;padding-bottom:60%;"><iframe src="data:text/html;charset=utf-8;base64,PCFET0NUWVBFIGh0bWw+CjxoZWFkPiAgICAKICAgIDxtZXRhIGh0dHAtZXF1aXY9ImNvbnRlbnQtdHlwZSIgY29udGVudD0idGV4dC9odG1sOyBjaGFyc2V0PVVURi04IiAvPgogICAgPHNjcmlwdD5MX1BSRUZFUl9DQU5WQVM9ZmFsc2U7IExfTk9fVE9VQ0g9ZmFsc2U7IExfRElTQUJMRV8zRD1mYWxzZTs8L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmpzIj48L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2FqYXguZ29vZ2xlYXBpcy5jb20vYWpheC9saWJzL2pxdWVyeS8xLjExLjEvanF1ZXJ5Lm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvanMvYm9vdHN0cmFwLm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9jZG5qcy5jbG91ZGZsYXJlLmNvbS9hamF4L2xpYnMvTGVhZmxldC5hd2Vzb21lLW1hcmtlcnMvMi4wLjIvbGVhZmxldC5hd2Vzb21lLW1hcmtlcnMuanMiPjwvc2NyaXB0PgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL21heGNkbi5ib290c3RyYXBjZG4uY29tL2Jvb3RzdHJhcC8zLjIuMC9jc3MvYm9vdHN0cmFwLm1pbi5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvY3NzL2Jvb3RzdHJhcC10aGVtZS5taW4uY3NzIi8+CiAgICA8bGluayByZWw9InN0eWxlc2hlZXQiIGhyZWY9Imh0dHBzOi8vbWF4Y2RuLmJvb3RzdHJhcGNkbi5jb20vZm9udC1hd2Vzb21lLzQuNi4zL2Nzcy9mb250LWF3ZXNvbWUubWluLmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2NkbmpzLmNsb3VkZmxhcmUuY29tL2FqYXgvbGlicy9MZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy8yLjAuMi9sZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9yYXdjZG4uZ2l0aGFjay5jb20vcHl0aG9uLXZpc3VhbGl6YXRpb24vZm9saXVtL21hc3Rlci9mb2xpdW0vdGVtcGxhdGVzL2xlYWZsZXQuYXdlc29tZS5yb3RhdGUuY3NzIi8+CiAgICA8c3R5bGU+aHRtbCwgYm9keSB7d2lkdGg6IDEwMCU7aGVpZ2h0OiAxMDAlO21hcmdpbjogMDtwYWRkaW5nOiAwO308L3N0eWxlPgogICAgPHN0eWxlPiNtYXAge3Bvc2l0aW9uOmFic29sdXRlO3RvcDowO2JvdHRvbTowO3JpZ2h0OjA7bGVmdDowO308L3N0eWxlPgogICAgCiAgICA8bWV0YSBuYW1lPSJ2aWV3cG9ydCIgY29udGVudD0id2lkdGg9ZGV2aWNlLXdpZHRoLAogICAgICAgIGluaXRpYWwtc2NhbGU9MS4wLCBtYXhpbXVtLXNjYWxlPTEuMCwgdXNlci1zY2FsYWJsZT1ubyIgLz4KICAgIDxzdHlsZT4jbWFwXzJlYjM5NDA1YmUwMDQ4NTc5MzEzNGYzZjNlZWJkZTRmIHsKICAgICAgICBwb3NpdGlvbjogcmVsYXRpdmU7CiAgICAgICAgd2lkdGg6IDEwMC4wJTsKICAgICAgICBoZWlnaHQ6IDEwMC4wJTsKICAgICAgICBsZWZ0OiAwLjAlOwogICAgICAgIHRvcDogMC4wJTsKICAgICAgICB9CiAgICA8L3N0eWxlPgogICAgPHNjcmlwdCBzcmM9Imh0dHBzOi8vY2RuanMuY2xvdWRmbGFyZS5jb20vYWpheC9saWJzL2QzLzMuNS41L2QzLm1pbi5qcyI+PC9zY3JpcHQ+CjwvaGVhZD4KPGJvZHk+ICAgIAogICAgCiAgICA8ZGl2IGNsYXNzPSJmb2xpdW0tbWFwIiBpZD0ibWFwXzJlYjM5NDA1YmUwMDQ4NTc5MzEzNGYzZjNlZWJkZTRmIiA+PC9kaXY+CjwvYm9keT4KPHNjcmlwdD4gICAgCiAgICAKICAgIAogICAgICAgIHZhciBib3VuZHMgPSBudWxsOwogICAgCgogICAgdmFyIG1hcF8yZWIzOTQwNWJlMDA0ODU3OTMxMzRmM2YzZWViZGU0ZiA9IEwubWFwKAogICAgICAgICdtYXBfMmViMzk0MDViZTAwNDg1NzkzMTM0ZjNmM2VlYmRlNGYnLCB7CiAgICAgICAgY2VudGVyOiBbMzcuNTUwMiwgMTI2Ljk4Ml0sCiAgICAgICAgem9vbTogMTEsCiAgICAgICAgbWF4Qm91bmRzOiBib3VuZHMsCiAgICAgICAgbGF5ZXJzOiBbXSwKICAgICAgICB3b3JsZENvcHlKdW1wOiBmYWxzZSwKICAgICAgICBjcnM6IEwuQ1JTLkVQU0czODU3LAogICAgICAgIHpvb21Db250cm9sOiB0cnVlLAogICAgICAgIH0pOwoKICAgIAogICAgCiAgICB2YXIgdGlsZV9sYXllcl9jY2UyZjJlY2M0MTk0MzJlYjQ0NmVkNGY2YmVkYjUxMSA9IEwudGlsZUxheWVyKAogICAgICAgICdodHRwczovL3N0YW1lbi10aWxlcy17c30uYS5zc2wuZmFzdGx5Lm5ldC90b25lci97en0ve3h9L3t5fS5wbmcnLAogICAgICAgIHsKICAgICAgICAiYXR0cmlidXRpb24iOiBudWxsLAogICAgICAgICJkZXRlY3RSZXRpbmEiOiBmYWxzZSwKICAgICAgICAibWF4TmF0aXZlWm9vbSI6IDE4LAogICAgICAgICJtYXhab29tIjogMTgsCiAgICAgICAgIm1pblpvb20iOiAwLAogICAgICAgICJub1dyYXAiOiBmYWxzZSwKICAgICAgICAib3BhY2l0eSI6IDEsCiAgICAgICAgInN1YmRvbWFpbnMiOiAiYWJjIiwKICAgICAgICAidG1zIjogZmFsc2UKfSkuYWRkVG8obWFwXzJlYjM5NDA1YmUwMDQ4NTc5MzEzNGYzZjNlZWJkZTRmKTsKICAgIAogICAgICAgICAgICB2YXIgY2hvcm9wbGV0aF8xYTI4ZmY5NzYzNjE0MTNhOWU5NTc0MDhkZjA3OGQxZCA9IEwuZmVhdHVyZUdyb3VwKAogICAgICAgICAgICAgICAgKS5hZGRUbyhtYXBfMmViMzk0MDViZTAwNDg1NzkzMTM0ZjNmM2VlYmRlNGYpOwogICAgICAgIAogICAgCiAgICAgICAgCiAgICAgICAgdmFyIGdlb19qc29uX2RhMjk2Nzg1YmNlMzRmMmU4NDk5ZDk3NDRlZjFkMzAxID0gTC5nZW9Kc29uKAogICAgICAgICAgICB7ImZlYXR1cmVzIjogW3siZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI3LjExNTE5NTg0OTgxNjA2LCAzNy41NTc1MzMxODA3MDQ5MTVdLCBbMTI3LjE2NjgzMTg0MzY2MTI5LCAzNy41NzY3MjQ4NzM4ODYyN10sIFsxMjcuMTg0MDg3OTIzMzAxNTIsIDM3LjU1ODE0MjgwMzY5NTc1XSwgWzEyNy4xNjUzMDk4NDMwNzQ0NywgMzcuNTQyMjE4NTEyNTg2OTNdLCBbMTI3LjE0NjcyODA2ODIzNTAyLCAzNy41MTQxNTY4MDY4MDI5MV0sIFsxMjcuMTIxMjMxNjU3MTk2MTUsIDM3LjUyNTI4MjcwMDg5XSwgWzEyNy4xMTE2NzY0MjAzNjA4LCAzNy41NDA2Njk5NTUzMjQ5NjVdLCBbMTI3LjExNTE5NTg0OTgxNjA2LCAzNy41NTc1MzMxODA3MDQ5MTVdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YWMxNVx1YjNkOVx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMjUwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWFjMTVcdWIzZDlcdWFkNmMiLCAibmFtZV9lbmciOiAiR2FuZ2RvbmctZ3UiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICIjZDRiOWRhIiwgImZpbGxPcGFjaXR5IjogMC42LCAib3BhY2l0eSI6IDEsICJ3ZWlnaHQiOiAxfX0sICJ0eXBlIjogIkZlYXR1cmUifSwgeyJnZW9tZXRyeSI6IHsiY29vcmRpbmF0ZXMiOiBbW1sxMjcuMDY5MDY5ODEzMDM3MiwgMzcuNTIyMjc5NDIzNTA1MDI2XSwgWzEyNy4xMDA4NzUxOTc5MTk2MiwgMzcuNTI0ODQxMjIwMTY3MDU1XSwgWzEyNy4xMTE2NzY0MjAzNjA4LCAzNy41NDA2Njk5NTUzMjQ5NjVdLCBbMTI3LjEyMTIzMTY1NzE5NjE1LCAzNy41MjUyODI3MDA4OV0sIFsxMjcuMTQ2NzI4MDY4MjM1MDIsIDM3LjUxNDE1NjgwNjgwMjkxXSwgWzEyNy4xNjM0OTQ0MjE1NzY1LCAzNy40OTc0NDU0MDYwOTc0ODRdLCBbMTI3LjE0MjA2MDU4NDEzMjc0LCAzNy40NzA4OTgxOTA5ODUwMV0sIFsxMjcuMTI0NDA1NzEwODA4OTMsIDM3LjQ2MjQwNDQ1NTg3MDQ4XSwgWzEyNy4xMTExNzA4NTIwMTIzOCwgMzcuNDg1NzA4MzgxNTEyNDQ1XSwgWzEyNy4wNzE5MTQ2MDAwNzI0LCAzNy41MDIyNDAxMzU4NzY2OV0sIFsxMjcuMDY5MDY5ODEzMDM3MiwgMzcuNTIyMjc5NDIzNTA1MDI2XV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJcdWMxYTFcdWQzMGNcdWFkNmMiLCAicHJvcGVydGllcyI6IHsiYmFzZV95ZWFyIjogIjIwMTMiLCAiY29kZSI6ICIxMTI0MCIsICJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiXHVjMWExXHVkMzBjXHVhZDZjIiwgIm5hbWVfZW5nIjogIlNvbmdwYS1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNkZjY1YjAiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wNTg2NzM1OTI4ODM5OCwgMzcuNTI2Mjk5NzQ5MjI1NjhdLCBbMTI3LjA2OTA2OTgxMzAzNzIsIDM3LjUyMjI3OTQyMzUwNTAyNl0sIFsxMjcuMDcxOTE0NjAwMDcyNCwgMzcuNTAyMjQwMTM1ODc2NjldLCBbMTI3LjExMTE3MDg1MjAxMjM4LCAzNy40ODU3MDgzODE1MTI0NDVdLCBbMTI3LjEyNDQwNTcxMDgwODkzLCAzNy40NjI0MDQ0NTU4NzA0OF0sIFsxMjcuMDk4NDI3NTkzMTg3NTEsIDM3LjQ1ODYyMjUzODU3NDYxXSwgWzEyNy4wODY0MDQ0MDU3ODE1NiwgMzcuNDcyNjk3OTM1MTg0NjU1XSwgWzEyNy4wNTU5MTcwNDgxOTA0LCAzNy40NjU5MjI4OTE0MDc3XSwgWzEyNy4wMzYyMTkxNTA5ODc5OCwgMzcuNDgxNzU4MDI0Mjc2MDNdLCBbMTI3LjAxMzk3MTE5NjY3NTEzLCAzNy41MjUwMzk4ODI4OTY2OV0sIFsxMjcuMDIzMDI4MzE4OTA1NTksIDM3LjUzMjMxODk5NTgyNjYzXSwgWzEyNy4wNTg2NzM1OTI4ODM5OCwgMzcuNTI2Mjk5NzQ5MjI1NjhdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YWMxNVx1YjBhOFx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMjMwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWFjMTVcdWIwYThcdWFkNmMiLCAibmFtZV9lbmciOiAiR2FuZ25hbS1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiM5ODAwNDMiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wMTM5NzExOTY2NzUxMywgMzcuNTI1MDM5ODgyODk2NjldLCBbMTI3LjAzNjIxOTE1MDk4Nzk4LCAzNy40ODE3NTgwMjQyNzYwM10sIFsxMjcuMDU1OTE3MDQ4MTkwNCwgMzcuNDY1OTIyODkxNDA3N10sIFsxMjcuMDg2NDA0NDA1NzgxNTYsIDM3LjQ3MjY5NzkzNTE4NDY1NV0sIFsxMjcuMDk4NDI3NTkzMTg3NTEsIDM3LjQ1ODYyMjUzODU3NDYxXSwgWzEyNy4wOTA0NjkyODU2NTk1MSwgMzcuNDQyOTY4MjYxMTQxODVdLCBbMTI3LjA2Nzc4MTA3NjA1NDMzLCAzNy40MjYxOTc0MjQwNTczMTRdLCBbMTI3LjA0OTU3MjMyOTg3MTQyLCAzNy40MjgwNTgzNjg0NTY5NF0sIFsxMjcuMDM4ODE3ODI1OTc5MjIsIDM3LjQ1MzgyMDM5ODUxNzE1XSwgWzEyNi45OTA3MjA3MzE5NTQ2MiwgMzcuNDU1MzI2MTQzMzEwMDI1XSwgWzEyNi45ODM2NzY2ODI5MTgwMiwgMzcuNDczODU2NDkyNjkyMDg2XSwgWzEyNi45ODIyMzgwNzkxNjA4MSwgMzcuNTA5MzE0OTY2NzcwMzI2XSwgWzEyNy4wMTM5NzExOTY2NzUxMywgMzcuNTI1MDM5ODgyODk2NjldXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzExY1x1Y2QwOFx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMjIwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWMxMWNcdWNkMDhcdWFkNmMiLCAibmFtZV9lbmciOiAiU2VvY2hvLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2M5OTRjNyIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2Ljk4MzY3NjY4MjkxODAyLCAzNy40NzM4NTY0OTI2OTIwODZdLCBbMTI2Ljk5MDcyMDczMTk1NDYyLCAzNy40NTUzMjYxNDMzMTAwMjVdLCBbMTI2Ljk2NTIwNDM5MDg1MTQzLCAzNy40MzgyNDk3ODQwMDYyNDZdLCBbMTI2Ljk1MDAwMDAxMDEwMTgyLCAzNy40MzYxMzQ1MTE2NTcxOV0sIFsxMjYuOTMwODQ0MDgwNTY1MjUsIDM3LjQ0NzM4MjkyODMzMzk5NF0sIFsxMjYuOTE2NzcyODE0NjYwMSwgMzcuNDU0OTA1NjY0MjM3ODldLCBbMTI2LjkwMTU2MDk0MTI5ODk1LCAzNy40Nzc1Mzg0Mjc4OTkwMV0sIFsxMjYuOTA1MzE5NzU4MDE4MTIsIDM3LjQ4MjE4MDg3NTc1NDI5XSwgWzEyNi45NDkyMjY2MTM4OTUwOCwgMzcuNDkxMjU0Mzc0OTU2NDldLCBbMTI2Ljk3MjU4OTE4NTA2NjIsIDM3LjQ3MjU2MTM2MzI3ODEyNV0sIFsxMjYuOTgzNjc2NjgyOTE4MDIsIDM3LjQ3Mzg1NjQ5MjY5MjA4Nl1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVhZDAwXHVjNTQ1XHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEyMTAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YWQwMFx1YzU0NVx1YWQ2YyIsICJuYW1lX2VuZyI6ICJHd2FuYWstZ3UiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICIjZGY2NWIwIiwgImZpbGxPcGFjaXR5IjogMC42LCAib3BhY2l0eSI6IDEsICJ3ZWlnaHQiOiAxfX0sICJ0eXBlIjogIkZlYXR1cmUifSwgeyJnZW9tZXRyeSI6IHsiY29vcmRpbmF0ZXMiOiBbW1sxMjYuOTgyMjM4MDc5MTYwODEsIDM3LjUwOTMxNDk2Njc3MDMyNl0sIFsxMjYuOTgzNjc2NjgyOTE4MDIsIDM3LjQ3Mzg1NjQ5MjY5MjA4Nl0sIFsxMjYuOTcyNTg5MTg1MDY2MiwgMzcuNDcyNTYxMzYzMjc4MTI1XSwgWzEyNi45NDkyMjY2MTM4OTUwOCwgMzcuNDkxMjU0Mzc0OTU2NDldLCBbMTI2LjkwNTMxOTc1ODAxODEyLCAzNy40ODIxODA4NzU3NTQyOV0sIFsxMjYuOTIxNzc4OTMxNzQ4MjUsIDM3LjQ5NDg4OTg3NzQxNTE3Nl0sIFsxMjYuOTI4MTA2Mjg4MjgyNzksIDM3LjUxMzI5NTk1NzMyMDE1XSwgWzEyNi45NTI0OTk5MDI5ODE1OSwgMzcuNTE3MjI1MDA3NDE4MTNdLCBbMTI2Ljk4MjIzODA3OTE2MDgxLCAzNy41MDkzMTQ5NjY3NzAzMjZdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YjNkOVx1Yzc5MVx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMjAwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWIzZDlcdWM3OTFcdWFkNmMiLCAibmFtZV9lbmciOiAiRG9uZ2phay1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNkNGI5ZGEiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNi44OTE4NDY2Mzg2Mjc2NCwgMzcuNTQ3MzczOTc0OTk3MTE0XSwgWzEyNi45NDU2NjczMzA4MzIxMiwgMzcuNTI2NjE3NTQyNDUzMzY2XSwgWzEyNi45NTI0OTk5MDI5ODE1OSwgMzcuNTE3MjI1MDA3NDE4MTNdLCBbMTI2LjkyODEwNjI4ODI4Mjc5LCAzNy41MTMyOTU5NTczMjAxNV0sIFsxMjYuOTIxNzc4OTMxNzQ4MjUsIDM3LjQ5NDg4OTg3NzQxNTE3Nl0sIFsxMjYuOTA1MzE5NzU4MDE4MTIsIDM3LjQ4MjE4MDg3NTc1NDI5XSwgWzEyNi44OTU5NDc3Njc4MjQ4NSwgMzcuNTA0Njc1MjgxMzA5MTc2XSwgWzEyNi44ODE1NjQwMjM1Mzg2MiwgMzcuNTEzOTcwMDM0NzY1Njg0XSwgWzEyNi44ODgyNTc1Nzg2MDA5OSwgMzcuNTQwNzk3MzM2MzAyMzJdLCBbMTI2Ljg5MTg0NjYzODYyNzY0LCAzNy41NDczNzM5NzQ5OTcxMTRdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzYwMVx1YjRmMVx1ZDNlY1x1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMTkwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWM2MDFcdWI0ZjFcdWQzZWNcdWFkNmMiLCAibmFtZV9lbmciOiAiWWVvbmdkZXVuZ3BvLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2RkMWM3NyIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2LjkwMTU2MDk0MTI5ODk1LCAzNy40Nzc1Mzg0Mjc4OTkwMV0sIFsxMjYuOTE2NzcyODE0NjYwMSwgMzcuNDU0OTA1NjY0MjM3ODldLCBbMTI2LjkzMDg0NDA4MDU2NTI1LCAzNy40NDczODI5MjgzMzM5OTRdLCBbMTI2LjkwMjU4MzE3MTE2OTcsIDM3LjQzNDU0OTM2NjM0OTEyNF0sIFsxMjYuODc2ODMyNzE1MDI0MjgsIDM3LjQ4MjU3NjU5MTYwNzMwNV0sIFsxMjYuOTAxNTYwOTQxMjk4OTUsIDM3LjQ3NzUzODQyNzg5OTAxXV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJcdWFlMDhcdWNjOWNcdWFkNmMiLCAicHJvcGVydGllcyI6IHsiYmFzZV95ZWFyIjogIjIwMTMiLCAiY29kZSI6ICIxMTE4MCIsICJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiXHVhZTA4XHVjYzljXHVhZDZjIiwgIm5hbWVfZW5nIjogIkdldW1jaGVvbi1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNmMWVlZjYiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNi44MjY4ODA4MTUxNzMxNCwgMzcuNTA1NDg5NzIyMzI4OTZdLCBbMTI2Ljg4MTU2NDAyMzUzODYyLCAzNy41MTM5NzAwMzQ3NjU2ODRdLCBbMTI2Ljg5NTk0Nzc2NzgyNDg1LCAzNy41MDQ2NzUyODEzMDkxNzZdLCBbMTI2LjkwNTMxOTc1ODAxODEyLCAzNy40ODIxODA4NzU3NTQyOV0sIFsxMjYuOTAxNTYwOTQxMjk4OTUsIDM3LjQ3NzUzODQyNzg5OTAxXSwgWzEyNi44NzY4MzI3MTUwMjQyOCwgMzcuNDgyNTc2NTkxNjA3MzA1XSwgWzEyNi44NDc2MjY3NjA1NDk1MywgMzcuNDcxNDY3MjM5MzYzMjNdLCBbMTI2LjgzNTQ5NDg1MDc2MTk2LCAzNy40NzQwOTgyMzY5NzUwOTVdLCBbMTI2LjgyMjY0Nzk2NzkxMzQ4LCAzNy40ODc4NDc2NDkyMTQ3XSwgWzEyNi44MjUwNDczNjMzMTQwNiwgMzcuNTAzMDI2MTI2NDA0NDNdLCBbMTI2LjgyNjg4MDgxNTE3MzE0LCAzNy41MDU0ODk3MjIzMjg5Nl1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVhZDZjXHViODVjXHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTExNzAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YWQ2Y1x1Yjg1Y1x1YWQ2YyIsICJuYW1lX2VuZyI6ICJHdXJvLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2RmNjViMCIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2Ljc5NTc1NzY4NTUyOTA3LCAzNy41Nzg4MTA4NzYzMzIwMl0sIFsxMjYuODA3MDIxMTUwMjM1OTcsIDM3LjYwMTIzMDAxMDEzMjI4XSwgWzEyNi44MjI1MTQzODQ3NzEwNSwgMzcuNTg4MDQzMDgxMDA4Ml0sIFsxMjYuODU5ODQxOTkzOTk2NjcsIDM3LjU3MTg0Nzg1NTI5Mjc0NV0sIFsxMjYuODkxODQ2NjM4NjI3NjQsIDM3LjU0NzM3Mzk3NDk5NzExNF0sIFsxMjYuODg4MjU3NTc4NjAwOTksIDM3LjU0MDc5NzMzNjMwMjMyXSwgWzEyNi44NjYzNzQ2NDMyMTIzOCwgMzcuNTQ4NTkxOTEwOTQ4MjNdLCBbMTI2Ljg2NjEwMDczNDc2Mzk1LCAzNy41MjY5OTk2NDE0NDY2OV0sIFsxMjYuODQyNTcyOTE5NDMxNTMsIDM3LjUyMzczNzA3ODA1NTk2XSwgWzEyNi44MjQyMzMxNDI2NzIyLCAzNy41Mzc4ODA3ODc1MzI0OF0sIFsxMjYuNzczMjQ0MTc3MTc3MDMsIDM3LjU0NTkxMjM0NTA1NTRdLCBbMTI2Ljc2OTc5MTgwNTc5MzUyLCAzNy41NTEzOTE4MzAwODgwOV0sIFsxMjYuNzk1NzU3Njg1NTI5MDcsIDM3LjU3ODgxMDg3NjMzMjAyXV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJcdWFjMTVcdWMxMWNcdWFkNmMiLCAicHJvcGVydGllcyI6IHsiYmFzZV95ZWFyIjogIjIwMTMiLCAiY29kZSI6ICIxMTE2MCIsICJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiXHVhYzE1XHVjMTFjXHVhZDZjIiwgIm5hbWVfZW5nIjogIkdhbmdzZW8tZ3UiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICJibGFjayIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2LjgyNDIzMzE0MjY3MjIsIDM3LjUzNzg4MDc4NzUzMjQ4XSwgWzEyNi44NDI1NzI5MTk0MzE1MywgMzcuNTIzNzM3MDc4MDU1OTZdLCBbMTI2Ljg2NjEwMDczNDc2Mzk1LCAzNy41MjY5OTk2NDE0NDY2OV0sIFsxMjYuODY2Mzc0NjQzMjEyMzgsIDM3LjU0ODU5MTkxMDk0ODIzXSwgWzEyNi44ODgyNTc1Nzg2MDA5OSwgMzcuNTQwNzk3MzM2MzAyMzJdLCBbMTI2Ljg4MTU2NDAyMzUzODYyLCAzNy41MTM5NzAwMzQ3NjU2ODRdLCBbMTI2LjgyNjg4MDgxNTE3MzE0LCAzNy41MDU0ODk3MjIzMjg5Nl0sIFsxMjYuODI0MjMzMTQyNjcyMiwgMzcuNTM3ODgwNzg3NTMyNDhdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzU5MVx1Y2M5Y1x1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMTUwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWM1OTFcdWNjOWNcdWFkNmMiLCAibmFtZV9lbmciOiAiWWFuZ2NoZW9uLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiIzk4MDA0MyIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2LjkwNTIyMDY1ODMxMDUzLCAzNy41NzQwOTcwMDUyMjU3NF0sIFsxMjYuOTM4OTgxNjE3OTg5NzMsIDM3LjU1MjMxMDAwMzcyODEyNF0sIFsxMjYuOTYzNTgyMjY3MTA4MTIsIDM3LjU1NjA1NjM1NDc1MTU0XSwgWzEyNi45NjQ0ODU3MDU1MzA1NSwgMzcuNTQ4NzA1NjkyMDIxNjM1XSwgWzEyNi45NDU2NjczMzA4MzIxMiwgMzcuNTI2NjE3NTQyNDUzMzY2XSwgWzEyNi44OTE4NDY2Mzg2Mjc2NCwgMzcuNTQ3MzczOTc0OTk3MTE0XSwgWzEyNi44NTk4NDE5OTM5OTY2NywgMzcuNTcxODQ3ODU1MjkyNzQ1XSwgWzEyNi44ODQzMzI4NDc3MzI4OCwgMzcuNTg4MTQzMzIyODgwNTI2XSwgWzEyNi45MDUyMjA2NTgzMTA1MywgMzcuNTc0MDk3MDA1MjI1NzRdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YjljOFx1ZDNlY1x1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMTQwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWI5YzhcdWQzZWNcdWFkNmMiLCAibmFtZV9lbmciOiAiTWFwby1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNkZjY1YjAiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNi45NTI0NzUyMDMwNTcyLCAzNy42MDUwODY5MjczNzA0NV0sIFsxMjYuOTU1NjU0MjU4NDY0NjMsIDM3LjU3NjA4MDc5MDg4MTQ1Nl0sIFsxMjYuOTY4NzM2MzMyNzkwNzUsIDM3LjU2MzEzNjA0NjkwODI3XSwgWzEyNi45NjM1ODIyNjcxMDgxMiwgMzcuNTU2MDU2MzU0NzUxNTRdLCBbMTI2LjkzODk4MTYxNzk4OTczLCAzNy41NTIzMTAwMDM3MjgxMjRdLCBbMTI2LjkwNTIyMDY1ODMxMDUzLCAzNy41NzQwOTcwMDUyMjU3NF0sIFsxMjYuOTUyNDc1MjAzMDU3MiwgMzcuNjA1MDg2OTI3MzcwNDVdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzExY1x1YjMwMFx1YmIzOFx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMTMwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWMxMWNcdWIzMDBcdWJiMzhcdWFkNmMiLCAibmFtZV9lbmciOiAiU2VvZGFlbXVuLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2YxZWVmNiIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2Ljk3Mzg4NjQxMjg3MDIsIDM3LjYyOTQ5NjM0Nzg2ODg4XSwgWzEyNi45NTQyNzAxNzAwNjEyOSwgMzcuNjIyMDMzNDMxMzM5NDI1XSwgWzEyNi45NTI0NzUyMDMwNTcyLCAzNy42MDUwODY5MjczNzA0NV0sIFsxMjYuOTA1MjIwNjU4MzEwNTMsIDM3LjU3NDA5NzAwNTIyNTc0XSwgWzEyNi44ODQzMzI4NDc3MzI4OCwgMzcuNTg4MTQzMzIyODgwNTI2XSwgWzEyNi45MDM5NjY4MTAwMzU5NSwgMzcuNTkyMjc0MDM0MTk5NDJdLCBbMTI2LjkwMzAzMDY2MTc3NjY4LCAzNy42MDk5Nzc5MTE0MDEzNDRdLCBbMTI2LjkxNDU1NDgxNDI5NjQ4LCAzNy42NDE1MDA1MDk5NjkzNV0sIFsxMjYuOTU2NDczNzk3Mzg3LCAzNy42NTI0ODA3MzczMzk0NDVdLCBbMTI2Ljk3Mzg4NjQxMjg3MDIsIDM3LjYyOTQ5NjM0Nzg2ODg4XV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJcdWM3NDBcdWQzYzlcdWFkNmMiLCAicHJvcGVydGllcyI6IHsiYmFzZV95ZWFyIjogIjIwMTMiLCAiY29kZSI6ICIxMTEyMCIsICJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiXHVjNzQwXHVkM2M5XHVhZDZjIiwgIm5hbWVfZW5nIjogIkV1bnB5ZW9uZy1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNkNGI5ZGEiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wODM4NzUyNzAzMTk1LCAzNy42OTM1OTUzNDIwMjAzNF0sIFsxMjcuMDk3MDYzOTEzMDk2OTUsIDM3LjY4NjM4MzcxOTM3MjI5NF0sIFsxMjcuMDk0NDA3NjYyOTg3MTcsIDM3LjY0NzEzNDkwNDczMDQ1XSwgWzEyNy4xMTMyNjc5NTg1NTE5OSwgMzcuNjM5NjIyOTA1MzE1OTI1XSwgWzEyNy4xMDc4MjI3NzY4ODEyOSwgMzcuNjE4MDQyNDQyNDEwNjldLCBbMTI3LjA3MzUxMjQzODI1Mjc4LCAzNy42MTI4MzY2MDM0MjMxM10sIFsxMjcuMDUyMDkzNzM1Njg2MTksIDM3LjYyMTY0MDY1NDg3NzgyXSwgWzEyNy4wNDM1ODgwMDg5NTYwOSwgMzcuNjI4NDg5MzEyOTg3MTVdLCBbMTI3LjA1ODAwMDc1MjIwMDkxLCAzNy42NDMxODI2Mzg3ODI3Nl0sIFsxMjcuMDUyODg0Nzk3MTA0ODUsIDM3LjY4NDIzODU3MDg0MzQ3XSwgWzEyNy4wODM4NzUyNzAzMTk1LCAzNy42OTM1OTUzNDIwMjAzNF1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHViMTc4XHVjNmQwXHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTExMTAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YjE3OFx1YzZkMFx1YWQ2YyIsICJuYW1lX2VuZyI6ICJOb3dvbi1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNjOTk0YzciLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wNTI4ODQ3OTcxMDQ4NSwgMzcuNjg0MjM4NTcwODQzNDddLCBbMTI3LjA1ODAwMDc1MjIwMDkxLCAzNy42NDMxODI2Mzg3ODI3Nl0sIFsxMjcuMDQzNTg4MDA4OTU2MDksIDM3LjYyODQ4OTMxMjk4NzE1XSwgWzEyNy4wMTQ2NTkzNTg5MjQ2NiwgMzcuNjQ5NDM2ODc0OTY4MTJdLCBbMTI3LjAyMDYyMTE2MTQxMzg5LCAzNy42NjcxNzM1NzU5NzEyMDVdLCBbMTI3LjAxMDM5NjY2MDQyMDcxLCAzNy42ODE4OTQ1ODk2MDM1OTRdLCBbMTI3LjAxNzk1MDk5MjAzNDMyLCAzNy42OTgyNDQxMjc3NTY2Ml0sIFsxMjcuMDUyODg0Nzk3MTA0ODUsIDM3LjY4NDIzODU3MDg0MzQ3XV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJcdWIzYzRcdWJkMDlcdWFkNmMiLCAicHJvcGVydGllcyI6IHsiYmFzZV95ZWFyIjogIjIwMTMiLCAiY29kZSI6ICIxMTEwMCIsICJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiXHViM2M0XHViZDA5XHVhZDZjIiwgIm5hbWVfZW5nIjogIkRvYm9uZy1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNmMWVlZjYiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNi45OTM4MzkwMzQyNCwgMzcuNjc2NjgxNzYxMTk5MDg1XSwgWzEyNy4wMTAzOTY2NjA0MjA3MSwgMzcuNjgxODk0NTg5NjAzNTk0XSwgWzEyNy4wMjA2MjExNjE0MTM4OSwgMzcuNjY3MTczNTc1OTcxMjA1XSwgWzEyNy4wMTQ2NTkzNTg5MjQ2NiwgMzcuNjQ5NDM2ODc0OTY4MTJdLCBbMTI3LjA0MzU4ODAwODk1NjA5LCAzNy42Mjg0ODkzMTI5ODcxNV0sIFsxMjcuMDUyMDkzNzM1Njg2MTksIDM3LjYyMTY0MDY1NDg3NzgyXSwgWzEyNy4wMzg5MjQwMDk5MjMwMSwgMzcuNjA5NzE1NjExMDIzODE2XSwgWzEyNy4wMTI4MTU0NzQ5NTIzLCAzNy42MTM2NTIyNDM0NzAyNTZdLCBbMTI2Ljk4NjcyNzA1NTEzODY5LCAzNy42MzM3NzY0MTI4ODE5Nl0sIFsxMjYuOTgxNzQ1MjY3NjU1MSwgMzcuNjUyMDk3NjkzODc3NzZdLCBbMTI2Ljk5MzgzOTAzNDI0LCAzNy42NzY2ODE3NjExOTkwODVdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YWMxNVx1YmQ4MVx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMDkwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWFjMTVcdWJkODFcdWFkNmMiLCAibmFtZV9lbmciOiAiR2FuZ2J1ay1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNkNGI5ZGEiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNi45NzcxNzU0MDY0MTYsIDM3LjYyODU5NzE1NDAwMzg4XSwgWzEyNi45ODY3MjcwNTUxMzg2OSwgMzcuNjMzNzc2NDEyODgxOTZdLCBbMTI3LjAxMjgxNTQ3NDk1MjMsIDM3LjYxMzY1MjI0MzQ3MDI1Nl0sIFsxMjcuMDM4OTI0MDA5OTIzMDEsIDM3LjYwOTcxNTYxMTAyMzgxNl0sIFsxMjcuMDUyMDkzNzM1Njg2MTksIDM3LjYyMTY0MDY1NDg3NzgyXSwgWzEyNy4wNzM1MTI0MzgyNTI3OCwgMzcuNjEyODM2NjAzNDIzMTNdLCBbMTI3LjA3MzgyNzA3MDk5MjI3LCAzNy42MDQwMTkyODk4NjQxOV0sIFsxMjcuMDQyNzA1MjIyMDk0LCAzNy41OTIzOTQzNzU5MzM5MV0sIFsxMjcuMDI1MjcyNTQ1MjgwMDMsIDM3LjU3NTI0NjE2MjQ1MjQ5XSwgWzEyNi45OTM0ODI5MzM1ODMxNCwgMzcuNTg4NTY1NDU3MjE2MTU2XSwgWzEyNi45ODg3OTg2NTk5MjM4NCwgMzcuNjExODkyNzMxOTc1Nl0sIFsxMjYuOTc3MTc1NDA2NDE2LCAzNy42Mjg1OTcxNTQwMDM4OF1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVjMTMxXHViZDgxXHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEwODAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YzEzMVx1YmQ4MVx1YWQ2YyIsICJuYW1lX2VuZyI6ICJTZW9uZ2J1ay1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNmMWVlZjYiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wNzM1MTI0MzgyNTI3OCwgMzcuNjEyODM2NjAzNDIzMTNdLCBbMTI3LjEwNzgyMjc3Njg4MTI5LCAzNy42MTgwNDI0NDI0MTA2OV0sIFsxMjcuMTIwMTI0NjAyMDExNCwgMzcuNjAxNzg0NTc1OTgxODhdLCBbMTI3LjEwMzA0MTc0MjQ5MjE0LCAzNy41NzA3NjM0MjI5MDk1NV0sIFsxMjcuMDgwNjg1NDEyODA0MDMsIDM3LjU2OTA2NDI1NTE5MDE3XSwgWzEyNy4wNzM4MjcwNzA5OTIyNywgMzcuNjA0MDE5Mjg5ODY0MTldLCBbMTI3LjA3MzUxMjQzODI1Mjc4LCAzNy42MTI4MzY2MDM0MjMxM11dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVjOTExXHViNzkxXHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEwNzAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YzkxMVx1Yjc5MVx1YWQ2YyIsICJuYW1lX2VuZyI6ICJKdW5nbmFuZy1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNjOTk0YzciLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wMjUyNzI1NDUyODAwMywgMzcuNTc1MjQ2MTYyNDUyNDldLCBbMTI3LjA0MjcwNTIyMjA5NCwgMzcuNTkyMzk0Mzc1OTMzOTFdLCBbMTI3LjA3MzgyNzA3MDk5MjI3LCAzNy42MDQwMTkyODk4NjQxOV0sIFsxMjcuMDgwNjg1NDEyODA0MDMsIDM3LjU2OTA2NDI1NTE5MDE3XSwgWzEyNy4wNzQyMTA1MzAyNDM2MiwgMzcuNTU3MjQ3Njk3MTIwODVdLCBbMTI3LjA1MDA1NjAxMDgxNTY3LCAzNy41Njc1Nzc2MTI1OTA4NDZdLCBbMTI3LjAyNTQ3MjY2MzQ5OTc2LCAzNy41Njg5NDM1NTIyMzc3MzRdLCBbMTI3LjAyNTI3MjU0NTI4MDAzLCAzNy41NzUyNDYxNjI0NTI0OV1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHViM2Q5XHViMzAwXHViYjM4XHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEwNjAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YjNkOVx1YjMwMFx1YmIzOFx1YWQ2YyIsICJuYW1lX2VuZyI6ICJEb25nZGFlbXVuLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2Q0YjlkYSIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI3LjA4MDY4NTQxMjgwNDAzLCAzNy41NjkwNjQyNTUxOTAxN10sIFsxMjcuMTAzMDQxNzQyNDkyMTQsIDM3LjU3MDc2MzQyMjkwOTU1XSwgWzEyNy4xMTUxOTU4NDk4MTYwNiwgMzcuNTU3NTMzMTgwNzA0OTE1XSwgWzEyNy4xMTE2NzY0MjAzNjA4LCAzNy41NDA2Njk5NTUzMjQ5NjVdLCBbMTI3LjEwMDg3NTE5NzkxOTYyLCAzNy41MjQ4NDEyMjAxNjcwNTVdLCBbMTI3LjA2OTA2OTgxMzAzNzIsIDM3LjUyMjI3OTQyMzUwNTAyNl0sIFsxMjcuMDU4NjczNTkyODgzOTgsIDM3LjUyNjI5OTc0OTIyNTY4XSwgWzEyNy4wNzQyMTA1MzAyNDM2MiwgMzcuNTU3MjQ3Njk3MTIwODVdLCBbMTI3LjA4MDY4NTQxMjgwNDAzLCAzNy41NjkwNjQyNTUxOTAxN11dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVhZDExXHVjOWM0XHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEwNTAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YWQxMVx1YzljNFx1YWQ2YyIsICJuYW1lX2VuZyI6ICJHd2FuZ2ppbi1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNjOTk0YzciLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wMjU0NzI2NjM0OTk3NiwgMzcuNTY4OTQzNTUyMjM3NzM0XSwgWzEyNy4wNTAwNTYwMTA4MTU2NywgMzcuNTY3NTc3NjEyNTkwODQ2XSwgWzEyNy4wNzQyMTA1MzAyNDM2MiwgMzcuNTU3MjQ3Njk3MTIwODVdLCBbMTI3LjA1ODY3MzU5Mjg4Mzk4LCAzNy41MjYyOTk3NDkyMjU2OF0sIFsxMjcuMDIzMDI4MzE4OTA1NTksIDM3LjUzMjMxODk5NTgyNjYzXSwgWzEyNy4wMTA3MDg5NDE3NzQ4MiwgMzcuNTQxMTgwNDg5NjQ3NjJdLCBbMTI3LjAyNTQ3MjY2MzQ5OTc2LCAzNy41Njg5NDM1NTIyMzc3MzRdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzEzMVx1YjNkOVx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMDQwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWMxMzFcdWIzZDlcdWFkNmMiLCAibmFtZV9lbmciOiAiU2Vvbmdkb25nLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2YxZWVmNiIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI3LjAxMDcwODk0MTc3NDgyLCAzNy41NDExODA0ODk2NDc2Ml0sIFsxMjcuMDIzMDI4MzE4OTA1NTksIDM3LjUzMjMxODk5NTgyNjYzXSwgWzEyNy4wMTM5NzExOTY2NzUxMywgMzcuNTI1MDM5ODgyODk2NjldLCBbMTI2Ljk4MjIzODA3OTE2MDgxLCAzNy41MDkzMTQ5NjY3NzAzMjZdLCBbMTI2Ljk1MjQ5OTkwMjk4MTU5LCAzNy41MTcyMjUwMDc0MTgxM10sIFsxMjYuOTQ1NjY3MzMwODMyMTIsIDM3LjUyNjYxNzU0MjQ1MzM2Nl0sIFsxMjYuOTY0NDg1NzA1NTMwNTUsIDM3LjU0ODcwNTY5MjAyMTYzNV0sIFsxMjYuOTg3NTI5OTY5MDMzMjgsIDM3LjU1MDk0ODE4ODA3MTM5XSwgWzEyNy4wMTA3MDg5NDE3NzQ4MiwgMzcuNTQxMTgwNDg5NjQ3NjJdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzZhOVx1YzBiMFx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMDMwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWM2YTlcdWMwYjBcdWFkNmMiLCAibmFtZV9lbmciOiAiWW9uZ3Nhbi1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNkNGI5ZGEiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wMjU0NzI2NjM0OTk3NiwgMzcuNTY4OTQzNTUyMjM3NzM0XSwgWzEyNy4wMTA3MDg5NDE3NzQ4MiwgMzcuNTQxMTgwNDg5NjQ3NjJdLCBbMTI2Ljk4NzUyOTk2OTAzMzI4LCAzNy41NTA5NDgxODgwNzEzOV0sIFsxMjYuOTY0NDg1NzA1NTMwNTUsIDM3LjU0ODcwNTY5MjAyMTYzNV0sIFsxMjYuOTYzNTgyMjY3MTA4MTIsIDM3LjU1NjA1NjM1NDc1MTU0XSwgWzEyNi45Njg3MzYzMzI3OTA3NSwgMzcuNTYzMTM2MDQ2OTA4MjddLCBbMTI3LjAyNTQ3MjY2MzQ5OTc2LCAzNy41Njg5NDM1NTIyMzc3MzRdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzkxMVx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMDIwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWM5MTFcdWFkNmMiLCAibmFtZV9lbmciOiAiSnVuZy1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNkNGI5ZGEiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNi45NzM4ODY0MTI4NzAyLCAzNy42Mjk0OTYzNDc4Njg4OF0sIFsxMjYuOTc3MTc1NDA2NDE2LCAzNy42Mjg1OTcxNTQwMDM4OF0sIFsxMjYuOTg4Nzk4NjU5OTIzODQsIDM3LjYxMTg5MjczMTk3NTZdLCBbMTI2Ljk5MzQ4MjkzMzU4MzE0LCAzNy41ODg1NjU0NTcyMTYxNTZdLCBbMTI3LjAyNTI3MjU0NTI4MDAzLCAzNy41NzUyNDYxNjI0NTI0OV0sIFsxMjcuMDI1NDcyNjYzNDk5NzYsIDM3LjU2ODk0MzU1MjIzNzczNF0sIFsxMjYuOTY4NzM2MzMyNzkwNzUsIDM3LjU2MzEzNjA0NjkwODI3XSwgWzEyNi45NTU2NTQyNTg0NjQ2MywgMzcuNTc2MDgwNzkwODgxNDU2XSwgWzEyNi45NTI0NzUyMDMwNTcyLCAzNy42MDUwODY5MjczNzA0NV0sIFsxMjYuOTU0MjcwMTcwMDYxMjksIDM3LjYyMjAzMzQzMTMzOTQyNV0sIFsxMjYuOTczODg2NDEyODcwMiwgMzcuNjI5NDk2MzQ3ODY4ODhdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1Yzg4NVx1Yjg1Y1x1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMDEwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWM4ODVcdWI4NWNcdWFkNmMiLCAibmFtZV9lbmciOiAiSm9uZ25vLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2Q0YjlkYSIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn1dLCAidHlwZSI6ICJGZWF0dXJlQ29sbGVjdGlvbiJ9CiAgICAgICAgICAgIAogICAgICAgICAgICApLmFkZFRvKGNob3JvcGxldGhfMWEyOGZmOTc2MzYxNDEzYTllOTU3NDA4ZGYwNzhkMWQpOwogICAgICAgIGdlb19qc29uX2RhMjk2Nzg1YmNlMzRmMmU4NDk5ZDk3NDRlZjFkMzAxLnNldFN0eWxlKGZ1bmN0aW9uKGZlYXR1cmUpIHtyZXR1cm4gZmVhdHVyZS5wcm9wZXJ0aWVzLnN0eWxlO30pOwogICAgICAgIAogICAgCiAgICB2YXIgY29sb3JfbWFwX2Y4MWEyYmI2YmQ3ZDRlMTI4NGI2NDQ2MzM1MzUyYWVmID0ge307CgogICAgCiAgICBjb2xvcl9tYXBfZjgxYTJiYjZiZDdkNGUxMjg0YjY0NDYzMzUzNTJhZWYuY29sb3IgPSBkMy5zY2FsZS50aHJlc2hvbGQoKQogICAgICAgICAgICAgIC5kb21haW4oWzAuMDYzNzI1NDkwMTk2MDc4NDMsIDAuMDY1MzkwNDQ5NjQxNTQ4MywgMC4wNjcwNTU0MDkwODcwMTgxNywgMC4wNjg3MjAzNjg1MzI0ODgwNCwgMC4wNzAzODUzMjc5Nzc5NTc5LCAwLjA3MjA1MDI4NzQyMzQyNzc4LCAwLjA3MzcxNTI0Njg2ODg5NzY2LCAwLjA3NTM4MDIwNjMxNDM2NzUyLCAwLjA3NzA0NTE2NTc1OTgzNzQsIDAuMDc4NzEwMTI1MjA1MzA3MjYsIDAuMDgwMzc1MDg0NjUwNzc3MTQsIDAuMDgyMDQwMDQ0MDk2MjQ3MDIsIDAuMDgzNzA1MDAzNTQxNzE2ODgsIDAuMDg1MzY5OTYyOTg3MTg2NzYsIDAuMDg3MDM0OTIyNDMyNjU2NjMsIDAuMDg4Njk5ODgxODc4MTI2NSwgMC4wOTAzNjQ4NDEzMjM1OTYzNywgMC4wOTIwMjk4MDA3NjkwNjYyNSwgMC4wOTM2OTQ3NjAyMTQ1MzYxMSwgMC4wOTUzNTk3MTk2NjAwMDU5OSwgMC4wOTcwMjQ2NzkxMDU0NzU4NSwgMC4wOTg2ODk2Mzg1NTA5NDU3MywgMC4xMDAzNTQ1OTc5OTY0MTU2LCAwLjEwMjAxOTU1NzQ0MTg4NTQ4LCAwLjEwMzY4NDUxNjg4NzM1NTMzLCAwLjEwNTM0OTQ3NjMzMjgyNTIxLCAwLjEwNzAxNDQzNTc3ODI5NTA5LCAwLjEwODY3OTM5NTIyMzc2NDk2LCAwLjExMDM0NDM1NDY2OTIzNDg0LCAwLjExMjAwOTMxNDExNDcwNDcsIDAuMTEzNjc0MjczNTYwMTc0NTcsIDAuMTE1MzM5MjMzMDA1NjQ0NDQsIDAuMTE3MDA0MTkyNDUxMTE0MzIsIDAuMTE4NjY5MTUxODk2NTg0MiwgMC4xMjAzMzQxMTEzNDIwNTQwNiwgMC4xMjE5OTkwNzA3ODc1MjM5NCwgMC4xMjM2NjQwMzAyMzI5OTM4LCAwLjEyNTMyODk4OTY3ODQ2MzY4LCAwLjEyNjk5Mzk0OTEyMzkzMzU1LCAwLjEyODY1ODkwODU2OTQwMzQzLCAwLjEzMDMyMzg2ODAxNDg3MzI4LCAwLjEzMTk4ODgyNzQ2MDM0MzE4LCAwLjEzMzY1Mzc4NjkwNTgxMzAzLCAwLjEzNTMxODc0NjM1MTI4Mjg4LCAwLjEzNjk4MzcwNTc5Njc1Mjc5LCAwLjEzODY0ODY2NTI0MjIyMjY0LCAwLjE0MDMxMzYyNDY4NzY5MjU0LCAwLjE0MTk3ODU4NDEzMzE2MjQsIDAuMTQzNjQzNTQzNTc4NjMyMjQsIDAuMTQ1MzA4NTAzMDI0MTAyMTQsIDAuMTQ2OTczNDYyNDY5NTcyLCAwLjE0ODYzODQyMTkxNTA0MTksIDAuMTUwMzAzMzgxMzYwNTExNzUsIDAuMTUxOTY4MzQwODA1OTgxNjIsIDAuMTUzNjMzMzAwMjUxNDUxNSwgMC4xNTUyOTgyNTk2OTY5MjEzNSwgMC4xNTY5NjMyMTkxNDIzOTEyNSwgMC4xNTg2MjgxNzg1ODc4NjExLCAwLjE2MDI5MzEzODAzMzMzMDk4LCAwLjE2MTk1ODA5NzQ3ODgwMDg2LCAwLjE2MzYyMzA1NjkyNDI3MDcsIDAuMTY1Mjg4MDE2MzY5NzQwNiwgMC4xNjY5NTI5NzU4MTUyMTA0NiwgMC4xNjg2MTc5MzUyNjA2ODAzMywgMC4xNzAyODI4OTQ3MDYxNTAyLCAwLjE3MTk0Nzg1NDE1MTYyMDA2LCAwLjE3MzYxMjgxMzU5NzA4OTk3LCAwLjE3NTI3Nzc3MzA0MjU1OTgxLCAwLjE3Njk0MjczMjQ4ODAyOTcsIDAuMTc4NjA3NjkxOTMzNDk5NTcsIDAuMTgwMjcyNjUxMzc4OTY5NDUsIDAuMTgxOTM3NjEwODI0NDM5MzIsIDAuMTgzNjAyNTcwMjY5OTA5MTcsIDAuMTg1MjY3NTI5NzE1Mzc5MDUsIDAuMTg2OTMyNDg5MTYwODQ4OTIsIDAuMTg4NTk3NDQ4NjA2MzE4OCwgMC4xOTAyNjI0MDgwNTE3ODg2OCwgMC4xOTE5MjczNjc0OTcyNTg1MywgMC4xOTM1OTIzMjY5NDI3Mjg0LCAwLjE5NTI1NzI4NjM4ODE5ODI4LCAwLjE5NjkyMjI0NTgzMzY2ODE2LCAwLjE5ODU4NzIwNTI3OTEzODAzLCAwLjIwMDI1MjE2NDcyNDYwNzksIDAuMjAxOTE3MTI0MTcwMDc3OCwgMC4yMDM1ODIwODM2MTU1NDc2NCwgMC4yMDUyNDcwNDMwNjEwMTc1LCAwLjIwNjkxMjAwMjUwNjQ4NzM2LCAwLjIwODU3Njk2MTk1MTk1NzI0LCAwLjIxMDI0MTkyMTM5NzQyNzEyLCAwLjIxMTkwNjg4MDg0Mjg5NywgMC4yMTM1NzE4NDAyODgzNjY4NywgMC4yMTUyMzY3OTk3MzM4MzY3NSwgMC4yMTY5MDE3NTkxNzkzMDY2MiwgMC4yMTg1NjY3MTg2MjQ3NzY1LCAwLjIyMDIzMTY3ODA3MDI0NjM1LCAwLjIyMTg5NjYzNzUxNTcxNjIzLCAwLjIyMzU2MTU5Njk2MTE4NjA4LCAwLjIyNTIyNjU1NjQwNjY1NTk1LCAwLjIyNjg5MTUxNTg1MjEyNTgzLCAwLjIyODU1NjQ3NTI5NzU5NTcsIDAuMjMwMjIxNDM0NzQzMDY1NTgsIDAuMjMxODg2Mzk0MTg4NTM1NDYsIDAuMjMzNTUxMzUzNjM0MDA1MzQsIDAuMjM1MjE2MzEzMDc5NDc1MjEsIDAuMjM2ODgxMjcyNTI0OTQ1MDYsIDAuMjM4NTQ2MjMxOTcwNDE0OTQsIDAuMjQwMjExMTkxNDE1ODg0ODIsIDAuMjQxODc2MTUwODYxMzU0NjcsIDAuMjQzNTQxMTEwMzA2ODI0NTQsIDAuMjQ1MjA2MDY5NzUyMjk0NDIsIDAuMjQ2ODcxMDI5MTk3NzY0MywgMC4yNDg1MzU5ODg2NDMyMzQxNywgMC4yNTAyMDA5NDgwODg3MDQxLCAwLjI1MTg2NTkwNzUzNDE3MzksIDAuMjUzNTMwODY2OTc5NjQzOCwgMC4yNTUxOTU4MjY0MjUxMTM2LCAwLjI1Njg2MDc4NTg3MDU4MzUzLCAwLjI1ODUyNTc0NTMxNjA1MzQzLCAwLjI2MDE5MDcwNDc2MTUyMzMsIDAuMjYxODU1NjY0MjA2OTkzMTMsIDAuMjYzNTIwNjIzNjUyNDYzLCAwLjI2NTE4NTU4MzA5NzkzMjksIDAuMjY2ODUwNTQyNTQzNDAyOCwgMC4yNjg1MTU1MDE5ODg4NzI2NCwgMC4yNzAxODA0NjE0MzQzNDI1LCAwLjI3MTg0NTQyMDg3OTgxMjM0LCAwLjI3MzUxMDM4MDMyNTI4MjI0LCAwLjI3NTE3NTMzOTc3MDc1MjE1LCAwLjI3Njg0MDI5OTIxNjIyMiwgMC4yNzg1MDUyNTg2NjE2OTE4NSwgMC4yODAxNzAyMTgxMDcxNjE3LCAwLjI4MTgzNTE3NzU1MjYzMTYsIDAuMjgzNTAwMTM2OTk4MTAxNSwgMC4yODUxNjUwOTY0NDM1NzEzNSwgMC4yODY4MzAwNTU4ODkwNDEyLCAwLjI4ODQ5NTAxNTMzNDUxMTA1LCAwLjI5MDE1OTk3NDc3OTk4MDk2LCAwLjI5MTgyNDkzNDIyNTQ1MDg2LCAwLjI5MzQ4OTg5MzY3MDkyMDcsIDAuMjk1MTU0ODUzMTE2MzkwNTYsIDAuMjk2ODE5ODEyNTYxODYwNDYsIDAuMjk4NDg0NzcyMDA3MzMwMywgMC4zMDAxNDk3MzE0NTI4MDAyLCAwLjMwMTgxNDY5MDg5ODI3MDA3LCAwLjMwMzQ3OTY1MDM0MzczOTksIDAuMzA1MTQ0NjA5Nzg5MjA5NzYsIDAuMzA2ODA5NTY5MjM0Njc5NjcsIDAuMzA4NDc0NTI4NjgwMTQ5NiwgMC4zMTAxMzk0ODgxMjU2MTk0LCAwLjMxMTgwNDQ0NzU3MTA4OTI3LCAwLjMxMzQ2OTQwNzAxNjU1OTIsIDAuMzE1MTM0MzY2NDYyMDI5LCAwLjMxNjc5OTMyNTkwNzQ5ODkzLCAwLjMxODQ2NDI4NTM1Mjk2ODgsIDAuMzIwMTI5MjQ0Nzk4NDM4NjMsIDAuMzIxNzk0MjA0MjQzOTA4NTMsIDAuMzIzNDU5MTYzNjg5Mzc4NCwgMC4zMjUxMjQxMjMxMzQ4NDgzLCAwLjMyNjc4OTA4MjU4MDMxODE0LCAwLjMyODQ1NDA0MjAyNTc4OCwgMC4zMzAxMTkwMDE0NzEyNTc5LCAwLjMzMTc4Mzk2MDkxNjcyNzc0LCAwLjMzMzQ0ODkyMDM2MjE5NzY0LCAwLjMzNTExMzg3OTgwNzY2NzUsIDAuMzM2Nzc4ODM5MjUzMTM3NCwgMC4zMzg0NDM3OTg2OTg2MDcyNSwgMC4zNDAxMDg3NTgxNDQwNzcxNSwgMC4zNDE3NzM3MTc1ODk1NDcsIDAuMzQzNDM4Njc3MDM1MDE2ODUsIDAuMzQ1MTAzNjM2NDgwNDg2NywgMC4zNDY3Njg1OTU5MjU5NTY1NSwgMC4zNDg0MzM1NTUzNzE0MjY0NSwgMC4zNTAwOTg1MTQ4MTY4OTYzLCAwLjM1MTc2MzQ3NDI2MjM2NjIsIDAuMzUzNDI4NDMzNzA3ODM2MDUsIDAuMzU1MDkzMzkzMTUzMzA1OTYsIDAuMzU2NzU4MzUyNTk4Nzc1OCwgMC4zNTg0MjMzMTIwNDQyNDU3LCAwLjM2MDA4ODI3MTQ4OTcxNTU2LCAwLjM2MTc1MzIzMDkzNTE4NTQsIDAuMzYzNDE4MTkwMzgwNjU1MywgMC4zNjUwODMxNDk4MjYxMjUxNiwgMC4zNjY3NDgxMDkyNzE1OTUwNywgMC4zNjg0MTMwNjg3MTcwNjQ5LCAwLjM3MDA3ODAyODE2MjUzNDgsIDAuMzcxNzQyOTg3NjA4MDA0NjcsIDAuMzczNDA3OTQ3MDUzNDc0NiwgMC4zNzUwNzI5MDY0OTg5NDQ0LCAwLjM3NjczNzg2NTk0NDQxNDMsIDAuMzc4NDAyODI1Mzg5ODg0MiwgMC4zODAwNjc3ODQ4MzUzNTQwMywgMC4zODE3MzI3NDQyODA4MjM5MywgMC4zODMzOTc3MDM3MjYyOTM3LCAwLjM4NTA2MjY2MzE3MTc2MzYzLCAwLjM4NjcyNzYyMjYxNzIzMzUsIDAuMzg4MzkyNTgyMDYyNzAzNCwgMC4zOTAwNTc1NDE1MDgxNzMyMywgMC4zOTE3MjI1MDA5NTM2NDMxLCAwLjM5MzM4NzQ2MDM5OTExMywgMC4zOTUwNTI0MTk4NDQ1ODI4NCwgMC4zOTY3MTczNzkyOTAwNTI3NCwgMC4zOTgzODIzMzg3MzU1MjI2LCAwLjQwMDA0NzI5ODE4MDk5MjUsIDAuNDAxNzEyMjU3NjI2NDYyMzQsIDAuNDAzMzc3MjE3MDcxOTMyMjUsIDAuNDA1MDQyMTc2NTE3NDAyMSwgMC40MDY3MDcxMzU5NjI4NzIsIDAuNDA4MzcyMDk1NDA4MzQxODUsIDAuNDEwMDM3MDU0ODUzODExNywgMC40MTE3MDIwMTQyOTkyODE2LCAwLjQxMzM2Njk3Mzc0NDc1MTQ1LCAwLjQxNTAzMTkzMzE5MDIyMTM2LCAwLjQxNjY5Njg5MjYzNTY5MTIsIDAuNDE4MzYxODUyMDgxMTYxMSwgMC40MjAwMjY4MTE1MjY2MzA5LCAwLjQyMTY5MTc3MDk3MjEwMDgsIDAuNDIzMzU2NzMwNDE3NTcwNjYsIDAuNDI1MDIxNjg5ODYzMDQwNSwgMC40MjY2ODY2NDkzMDg1MTA0LCAwLjQyODM1MTYwODc1Mzk4MDI2LCAwLjQzMDAxNjU2ODE5OTQ1MDE3LCAwLjQzMTY4MTUyNzY0NDkyLCAwLjQzMzM0NjQ4NzA5MDM4OTksIDAuNDM1MDExNDQ2NTM1ODU5NzcsIDAuNDM2Njc2NDA1OTgxMzI5NywgMC40MzgzNDEzNjU0MjY3OTk1LCAwLjQ0MDAwNjMyNDg3MjI2OTQsIDAuNDQxNjcxMjg0MzE3NzM5MywgMC40NDMzMzYyNDM3NjMyMDkxLCAwLjQ0NTAwMTIwMzIwODY3OTAzLCAwLjQ0NjY2NjE2MjY1NDE0ODksIDAuNDQ4MzMxMTIyMDk5NjE4OCwgMC40NDk5OTYwODE1NDUwODg2MywgMC40NTE2NjEwNDA5OTA1NTg1NCwgMC40NTMzMjYwMDA0MzYwMjg0LCAwLjQ1NDk5MDk1OTg4MTQ5ODMsIDAuNDU2NjU1OTE5MzI2OTY4MTQsIDAuNDU4MzIwODc4NzcyNDM3OTMsIDAuNDU5OTg1ODM4MjE3OTA3ODQsIDAuNDYxNjUwNzk3NjYzMzc3NywgMC40NjMzMTU3NTcxMDg4NDc2LCAwLjQ2NDk4MDcxNjU1NDMxNzQ0LCAwLjQ2NjY0NTY3NTk5OTc4NzM1LCAwLjQ2ODMxMDYzNTQ0NTI1NzIsIDAuNDY5OTc1NTk0ODkwNzI3MSwgMC40NzE2NDA1NTQzMzYxOTY5NSwgMC40NzMzMDU1MTM3ODE2NjY4NSwgMC40NzQ5NzA0NzMyMjcxMzY3LCAwLjQ3NjYzNTQzMjY3MjYwNjU1LCAwLjQ3ODMwMDM5MjExODA3NjQ2LCAwLjQ3OTk2NTM1MTU2MzU0NjMsIDAuNDgxNjMwMzExMDA5MDE2MiwgMC40ODMyOTUyNzA0NTQ0ODYwNiwgMC40ODQ5NjAyMjk4OTk5NTU5NiwgMC40ODY2MjUxODkzNDU0MjU4LCAwLjQ4ODI5MDE0ODc5MDg5NTcsIDAuNDg5OTU1MTA4MjM2MzY1NTcsIDAuNDkxNjIwMDY3NjgxODM1NCwgMC40OTMyODUwMjcxMjczMDUzLCAwLjQ5NDk0OTk4NjU3Mjc3NTEsIDAuNDk2NjE0OTQ2MDE4MjQ1LCAwLjQ5ODI3OTkwNTQ2MzcxNDg3LCAwLjQ5OTk0NDg2NDkwOTE4NDc3LCAwLjUwMTYwOTgyNDM1NDY1NDYsIDAuNTAzMjc0NzgzODAwMTI0NiwgMC41MDQ5Mzk3NDMyNDU1OTQ0LCAwLjUwNjYwNDcwMjY5MTA2NDMsIDAuNTA4MjY5NjYyMTM2NTM0MSwgMC41MDk5MzQ2MjE1ODIwMDQsIDAuNTExNTk5NTgxMDI3NDczOCwgMC41MTMyNjQ1NDA0NzI5NDM3LCAwLjUxNDkyOTQ5OTkxODQxMzYsIDAuNTE2NTk0NDU5MzYzODgzNSwgMC41MTgyNTk0MTg4MDkzNTM0LCAwLjUxOTkyNDM3ODI1NDgyMzMsIDAuNTIxNTg5MzM3NzAwMjkzMSwgMC41MjMyNTQyOTcxNDU3NjMsIDAuNTI0OTE5MjU2NTkxMjMyOCwgMC41MjY1ODQyMTYwMzY3MDI3LCAwLjUyODI0OTE3NTQ4MjE3MjUsIDAuNTI5OTE0MTM0OTI3NjQyNSwgMC41MzE1NzkwOTQzNzMxMTIzLCAwLjUzMzI0NDA1MzgxODU4MjIsIDAuNTM0OTA5MDEzMjY0MDUyLCAwLjUzNjU3Mzk3MjcwOTUyMiwgMC41MzgyMzg5MzIxNTQ5OTE5LCAwLjUzOTkwMzg5MTYwMDQ2MTcsIDAuNTQxNTY4ODUxMDQ1OTMxNiwgMC41NDMyMzM4MTA0OTE0MDE0LCAwLjU0NDg5ODc2OTkzNjg3MTMsIDAuNTQ2NTYzNzI5MzgyMzQxMSwgMC41NDgyMjg2ODg4Mjc4MTExLCAwLjU0OTg5MzY0ODI3MzI4MDksIDAuNTUxNTU4NjA3NzE4NzUwOSwgMC41NTMyMjM1NjcxNjQyMjA3LCAwLjU1NDg4ODUyNjYwOTY5MDYsIDAuNTU2NTUzNDg2MDU1MTYwNCwgMC41NTgyMTg0NDU1MDA2MzAzLCAwLjU1OTg4MzQwNDk0NjEwMDEsIDAuNTYxNTQ4MzY0MzkxNTcsIDAuNTYzMjEzMzIzODM3MDM5OSwgMC41NjQ4NzgyODMyODI1MDk4LCAwLjU2NjU0MzI0MjcyNzk3OTYsIDAuNTY4MjA4MjAyMTczNDQ5NSwgMC41Njk4NzMxNjE2MTg5MTk0LCAwLjU3MTUzODEyMTA2NDM4OTMsIDAuNTczMjAzMDgwNTA5ODU5MSwgMC41NzQ4NjgwMzk5NTUzMjksIDAuNTc2NTMyOTk5NDAwNzk4OCwgMC41NzgxOTc5NTg4NDYyNjg4LCAwLjU3OTg2MjkxODI5MTczODYsIDAuNTgxNTI3ODc3NzM3MjA4NSwgMC41ODMxOTI4MzcxODI2NzgzLCAwLjU4NDg1Nzc5NjYyODE0ODMsIDAuNTg2NTIyNzU2MDczNjE4MSwgMC41ODgxODc3MTU1MTkwODc5LCAwLjU4OTg1MjY3NDk2NDU1NzgsIDAuNTkxNTE3NjM0NDEwMDI3NywgMC41OTMxODI1OTM4NTU0OTc1LCAwLjU5NDg0NzU1MzMwMDk2NzQsIDAuNTk2NTEyNTEyNzQ2NDM3NCwgMC41OTgxNzc0NzIxOTE5MDcyLCAwLjU5OTg0MjQzMTYzNzM3NywgMC42MDE1MDczOTEwODI4NDY5LCAwLjYwMzE3MjM1MDUyODMxNjksIDAuNjA0ODM3MzA5OTczNzg2NiwgMC42MDY1MDIyNjk0MTkyNTY2LCAwLjYwODE2NzIyODg2NDcyNjQsIDAuNjA5ODMyMTg4MzEwMTk2NCwgMC42MTE0OTcxNDc3NTU2NjYxLCAwLjYxMzE2MjEwNzIwMTEzNjEsIDAuNjE0ODI3MDY2NjQ2NjA1OSwgMC42MTY0OTIwMjYwOTIwNzU5LCAwLjYxODE1Njk4NTUzNzU0NTYsIDAuNjE5ODIxOTQ0OTgzMDE1NiwgMC42MjE0ODY5MDQ0Mjg0ODU0LCAwLjYyMzE1MTg2Mzg3Mzk1NTMsIDAuNjI0ODE2ODIzMzE5NDI1MSwgMC42MjY0ODE3ODI3NjQ4OTUsIDAuNjI4MTQ2NzQyMjEwMzY0OSwgMC42Mjk4MTE3MDE2NTU4MzQ3LCAwLjYzMTQ3NjY2MTEwMTMwNDYsIDAuNjMzMTQxNjIwNTQ2Nzc0NSwgMC42MzQ4MDY1Nzk5OTIyNDQ0LCAwLjYzNjQ3MTUzOTQzNzcxNDIsIDAuNjM4MTM2NDk4ODgzMTg0MSwgMC42Mzk4MDE0NTgzMjg2NTQsIDAuNjQxNDY2NDE3Nzc0MTIzOCwgMC42NDMxMzEzNzcyMTk1OTM3LCAwLjY0NDc5NjMzNjY2NTA2MzYsIDAuNjQ2NDYxMjk2MTEwNTMzNSwgMC42NDgxMjYyNTU1NTYwMDMzLCAwLjY0OTc5MTIxNTAwMTQ3MzIsIDAuNjUxNDU2MTc0NDQ2OTQzMSwgMC42NTMxMjExMzM4OTI0MTMsIDAuNjU0Nzg2MDkzMzM3ODgyOCwgMC42NTY0NTEwNTI3ODMzNTI3LCAwLjY1ODExNjAxMjIyODgyMjcsIDAuNjU5NzgwOTcxNjc0MjkyNCwgMC42NjE0NDU5MzExMTk3NjI0LCAwLjY2MzExMDg5MDU2NTIzMjIsIDAuNjY0Nzc1ODUwMDEwNzAyLCAwLjY2NjQ0MDgwOTQ1NjE3MTksIDAuNjY4MTA1NzY4OTAxNjQxOCwgMC42Njk3NzA3MjgzNDcxMTE3LCAwLjY3MTQzNTY4Nzc5MjU4MTQsIDAuNjczMTAwNjQ3MjM4MDUxNCwgMC42NzQ3NjU2MDY2ODM1MjEzLCAwLjY3NjQzMDU2NjEyODk5MTIsIDAuNjc4MDk1NTI1NTc0NDYxLCAwLjY3OTc2MDQ4NTAxOTkzMDksIDAuNjgxNDI1NDQ0NDY1NDAwOCwgMC42ODMwOTA0MDM5MTA4NzA3LCAwLjY4NDc1NTM2MzM1NjM0MDUsIDAuNjg2NDIwMzIyODAxODEwNCwgMC42ODgwODUyODIyNDcyODAzLCAwLjY4OTc1MDI0MTY5Mjc1MDEsIDAuNjkxNDE1MjAxMTM4MjIsIDAuNjkzMDgwMTYwNTgzNjg5OSwgMC42OTQ3NDUxMjAwMjkxNTk4LCAwLjY5NjQxMDA3OTQ3NDYyOTYsIDAuNjk4MDc1MDM4OTIwMDk5NSwgMC42OTk3Mzk5OTgzNjU1Njk0LCAwLjcwMTQwNDk1NzgxMTAzOTMsIDAuNzAzMDY5OTE3MjU2NTA5LCAwLjcwNDczNDg3NjcwMTk3OSwgMC43MDYzOTk4MzYxNDc0NDg4LCAwLjcwODA2NDc5NTU5MjkxODcsIDAuNzA5NzI5NzU1MDM4Mzg4NSwgMC43MTEzOTQ3MTQ0ODM4NTg1LCAwLjcxMzA1OTY3MzkyOTMyODMsIDAuNzE0NzI0NjMzMzc0Nzk4MiwgMC43MTYzODk1OTI4MjAyNjgsIDAuNzE4MDU0NTUyMjY1NzM4LCAwLjcxOTcxOTUxMTcxMTIwNzcsIDAuNzIxMzg0NDcxMTU2Njc3NywgMC43MjMwNDk0MzA2MDIxNDc1LCAwLjcyNDcxNDM5MDA0NzYxNzUsIDAuNzI2Mzc5MzQ5NDkzMDg3MiwgMC43MjgwNDQzMDg5Mzg1NTcyLCAwLjcyOTcwOTI2ODM4NDAyNywgMC43MzEzNzQyMjc4Mjk0OTcsIDAuNzMzMDM5MTg3Mjc0OTY2OCwgMC43MzQ3MDQxNDY3MjA0MzY3LCAwLjczNjM2OTEwNjE2NTkwNjYsIDAuNzM4MDM0MDY1NjExMzc2MywgMC43Mzk2OTkwMjUwNTY4NDYzLCAwLjc0MTM2Mzk4NDUwMjMxNjEsIDAuNzQzMDI4OTQzOTQ3Nzg2MSwgMC43NDQ2OTM5MDMzOTMyNTU4LCAwLjc0NjM1ODg2MjgzODcyNTgsIDAuNzQ4MDIzODIyMjg0MTk1NiwgMC43NDk2ODg3ODE3Mjk2NjU2LCAwLjc1MTM1Mzc0MTE3NTEzNTMsIDAuNzUzMDE4NzAwNjIwNjA1MywgMC43NTQ2ODM2NjAwNjYwNzUxLCAwLjc1NjM0ODYxOTUxMTU0NSwgMC43NTgwMTM1Nzg5NTcwMTQ4LCAwLjc1OTY3ODUzODQwMjQ4NDgsIDAuNzYxMzQzNDk3ODQ3OTU0NiwgMC43NjMwMDg0NTcyOTM0MjQ1LCAwLjc2NDY3MzQxNjczODg5NDMsIDAuNzY2MzM4Mzc2MTg0MzY0MywgMC43NjgwMDMzMzU2Mjk4MzQxLCAwLjc2OTY2ODI5NTA3NTMwNCwgMC43NzEzMzMyNTQ1MjA3NzM4LCAwLjc3Mjk5ODIxMzk2NjI0MzgsIDAuNzc0NjYzMTczNDExNzEzNSwgMC43NzYzMjgxMzI4NTcxODM0LCAwLjc3Nzk5MzA5MjMwMjY1MzMsIDAuNzc5NjU4MDUxNzQ4MTIzMiwgMC43ODEzMjMwMTExOTM1OTMsIDAuNzgyOTg3OTcwNjM5MDYyOSwgMC43ODQ2NTI5MzAwODQ1MzI5LCAwLjc4NjMxNzg4OTUzMDAwMjYsIDAuNzg3OTgyODQ4OTc1NDcyNSwgMC43ODk2NDc4MDg0MjA5NDI0LCAwLjc5MTMxMjc2Nzg2NjQxMjQsIDAuNzkyOTc3NzI3MzExODgyMSwgMC43OTQ2NDI2ODY3NTczNTIxLCAwLjc5NjMwNzY0NjIwMjgyMTksIDAuNzk3OTcyNjA1NjQ4MjkxOSwgMC43OTk2Mzc1NjUwOTM3NjE2LCAwLjgwMTMwMjUyNDUzOTIzMTYsIDAuODAyOTY3NDgzOTg0NzAxNCwgMC44MDQ2MzI0NDM0MzAxNzEzLCAwLjgwNjI5NzQwMjg3NTY0MTEsIDAuODA3OTYyMzYyMzIxMTExMSwgMC44MDk2MjczMjE3NjY1ODA5LCAwLjgxMTI5MjI4MTIxMjA1MDgsIDAuODEyOTU3MjQwNjU3NTIwNiwgMC44MTQ2MjIyMDAxMDI5OTA1LCAwLjgxNjI4NzE1OTU0ODQ2MDQsIDAuODE3OTUyMTE4OTkzOTMwMiwgMC44MTk2MTcwNzg0Mzk0MDAxLCAwLjgyMTI4MjAzNzg4NDg3LCAwLjgyMjk0Njk5NzMzMDMzOTgsIDAuODI0NjExOTU2Nzc1ODA5NywgMC44MjYyNzY5MTYyMjEyNzk2LCAwLjgyNzk0MTg3NTY2Njc0OTUsIDAuODI5NjA2ODM1MTEyMjE5MywgMC44MzEyNzE3OTQ1NTc2ODkyLCAwLjgzMjkzNjc1NDAwMzE1OTEsIDAuODM0NjAxNzEzNDQ4NjI4OSwgMC44MzYyNjY2NzI4OTQwOTg4LCAwLjgzNzkzMTYzMjMzOTU2ODcsIDAuODM5NTk2NTkxNzg1MDM4NiwgMC44NDEyNjE1NTEyMzA1MDg0LCAwLjg0MjkyNjUxMDY3NTk3ODMsIDAuODQ0NTkxNDcwMTIxNDQ4MiwgMC44NDYyNTY0Mjk1NjY5MTgyLCAwLjg0NzkyMTM4OTAxMjM4NzksIDAuODQ5NTg2MzQ4NDU3ODU3OSwgMC44NTEyNTEzMDc5MDMzMjc3LCAwLjg1MjkxNjI2NzM0ODc5NzQsIDAuODU0NTgxMjI2Nzk0MjY3NCwgMC44NTYyNDYxODYyMzk3MzczLCAwLjg1NzkxMTE0NTY4NTIwNzIsIDAuODU5NTc2MTA1MTMwNjc3LCAwLjg2MTI0MTA2NDU3NjE0NjksIDAuODYyOTA2MDI0MDIxNjE2OCwgMC44NjQ1NzA5ODM0NjcwODY3LCAwLjg2NjIzNTk0MjkxMjU1NjUsIDAuODY3OTAwOTAyMzU4MDI2NCwgMC44Njk1NjU4NjE4MDM0OTYzLCAwLjg3MTIzMDgyMTI0ODk2NjEsIDAuODcyODk1NzgwNjk0NDM2LCAwLjg3NDU2MDc0MDEzOTkwNTksIDAuODc2MjI1Njk5NTg1Mzc1OCwgMC44Nzc4OTA2NTkwMzA4NDU2LCAwLjg3OTU1NTYxODQ3NjMxNTUsIDAuODgxMjIwNTc3OTIxNzg1NCwgMC44ODI4ODU1MzczNjcyNTUzLCAwLjg4NDU1MDQ5NjgxMjcyNTEsIDAuODg2MjE1NDU2MjU4MTk1LCAwLjg4Nzg4MDQxNTcwMzY2NDksIDAuODg5NTQ1Mzc1MTQ5MTM0NywgMC44OTEyMTAzMzQ1OTQ2MDQ1LCAwLjg5Mjg3NTI5NDA0MDA3NDUsIDAuODk0NTQwMjUzNDg1NTQ0M10pCiAgICAgICAgICAgICAgLnJhbmdlKFsnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJ10pOwogICAgCgogICAgY29sb3JfbWFwX2Y4MWEyYmI2YmQ3ZDRlMTI4NGI2NDQ2MzM1MzUyYWVmLnggPSBkMy5zY2FsZS5saW5lYXIoKQogICAgICAgICAgICAgIC5kb21haW4oWzAuMDYzNzI1NDkwMTk2MDc4NDMsIDAuODk0NTQwMjUzNDg1NTQ0M10pCiAgICAgICAgICAgICAgLnJhbmdlKFswLCA0MDBdKTsKCiAgICBjb2xvcl9tYXBfZjgxYTJiYjZiZDdkNGUxMjg0YjY0NDYzMzUzNTJhZWYubGVnZW5kID0gTC5jb250cm9sKHtwb3NpdGlvbjogJ3RvcHJpZ2h0J30pOwogICAgY29sb3JfbWFwX2Y4MWEyYmI2YmQ3ZDRlMTI4NGI2NDQ2MzM1MzUyYWVmLmxlZ2VuZC5vbkFkZCA9IGZ1bmN0aW9uIChtYXApIHt2YXIgZGl2ID0gTC5Eb21VdGlsLmNyZWF0ZSgnZGl2JywgJ2xlZ2VuZCcpOyByZXR1cm4gZGl2fTsKICAgIGNvbG9yX21hcF9mODFhMmJiNmJkN2Q0ZTEyODRiNjQ0NjMzNTM1MmFlZi5sZWdlbmQuYWRkVG8obWFwXzJlYjM5NDA1YmUwMDQ4NTc5MzEzNGYzZjNlZWJkZTRmKTsKCiAgICBjb2xvcl9tYXBfZjgxYTJiYjZiZDdkNGUxMjg0YjY0NDYzMzUzNTJhZWYueEF4aXMgPSBkMy5zdmcuYXhpcygpCiAgICAgICAgLnNjYWxlKGNvbG9yX21hcF9mODFhMmJiNmJkN2Q0ZTEyODRiNjQ0NjMzNTM1MmFlZi54KQogICAgICAgIC5vcmllbnQoInRvcCIpCiAgICAgICAgLnRpY2tTaXplKDEpCiAgICAgICAgLnRpY2tWYWx1ZXMoWzAuMDYzNzI1NDkwMTk2MDc4NDMsIDAuMjAyMTk0NjE3NDEwOTg5NCwgMC4zNDA2NjM3NDQ2MjU5MDA0LCAwLjQ3OTEzMjg3MTg0MDgxMTMsIDAuNjE3NjAxOTk5MDU1NzIyMywgMC43NTYwNzExMjYyNzA2MzMzLCAwLjg5NDU0MDI1MzQ4NTU0NDNdKTsKCiAgICBjb2xvcl9tYXBfZjgxYTJiYjZiZDdkNGUxMjg0YjY0NDYzMzUzNTJhZWYuc3ZnID0gZDMuc2VsZWN0KCIubGVnZW5kLmxlYWZsZXQtY29udHJvbCIpLmFwcGVuZCgic3ZnIikKICAgICAgICAuYXR0cigiaWQiLCAnbGVnZW5kJykKICAgICAgICAuYXR0cigid2lkdGgiLCA0NTApCiAgICAgICAgLmF0dHIoImhlaWdodCIsIDQwKTsKCiAgICBjb2xvcl9tYXBfZjgxYTJiYjZiZDdkNGUxMjg0YjY0NDYzMzUzNTJhZWYuZyA9IGNvbG9yX21hcF9mODFhMmJiNmJkN2Q0ZTEyODRiNjQ0NjMzNTM1MmFlZi5zdmcuYXBwZW5kKCJnIikKICAgICAgICAuYXR0cigiY2xhc3MiLCAia2V5IikKICAgICAgICAuYXR0cigidHJhbnNmb3JtIiwgInRyYW5zbGF0ZSgyNSwxNikiKTsKCiAgICBjb2xvcl9tYXBfZjgxYTJiYjZiZDdkNGUxMjg0YjY0NDYzMzUzNTJhZWYuZy5zZWxlY3RBbGwoInJlY3QiKQogICAgICAgIC5kYXRhKGNvbG9yX21hcF9mODFhMmJiNmJkN2Q0ZTEyODRiNjQ0NjMzNTM1MmFlZi5jb2xvci5yYW5nZSgpLm1hcChmdW5jdGlvbihkLCBpKSB7CiAgICAgICAgICByZXR1cm4gewogICAgICAgICAgICB4MDogaSA/IGNvbG9yX21hcF9mODFhMmJiNmJkN2Q0ZTEyODRiNjQ0NjMzNTM1MmFlZi54KGNvbG9yX21hcF9mODFhMmJiNmJkN2Q0ZTEyODRiNjQ0NjMzNTM1MmFlZi5jb2xvci5kb21haW4oKVtpIC0gMV0pIDogY29sb3JfbWFwX2Y4MWEyYmI2YmQ3ZDRlMTI4NGI2NDQ2MzM1MzUyYWVmLngucmFuZ2UoKVswXSwKICAgICAgICAgICAgeDE6IGkgPCBjb2xvcl9tYXBfZjgxYTJiYjZiZDdkNGUxMjg0YjY0NDYzMzUzNTJhZWYuY29sb3IuZG9tYWluKCkubGVuZ3RoID8gY29sb3JfbWFwX2Y4MWEyYmI2YmQ3ZDRlMTI4NGI2NDQ2MzM1MzUyYWVmLngoY29sb3JfbWFwX2Y4MWEyYmI2YmQ3ZDRlMTI4NGI2NDQ2MzM1MzUyYWVmLmNvbG9yLmRvbWFpbigpW2ldKSA6IGNvbG9yX21hcF9mODFhMmJiNmJkN2Q0ZTEyODRiNjQ0NjMzNTM1MmFlZi54LnJhbmdlKClbMV0sCiAgICAgICAgICAgIHo6IGQKICAgICAgICAgIH07CiAgICAgICAgfSkpCiAgICAgIC5lbnRlcigpLmFwcGVuZCgicmVjdCIpCiAgICAgICAgLmF0dHIoImhlaWdodCIsIDEwKQogICAgICAgIC5hdHRyKCJ4IiwgZnVuY3Rpb24oZCkgeyByZXR1cm4gZC54MDsgfSkKICAgICAgICAuYXR0cigid2lkdGgiLCBmdW5jdGlvbihkKSB7IHJldHVybiBkLngxIC0gZC54MDsgfSkKICAgICAgICAuc3R5bGUoImZpbGwiLCBmdW5jdGlvbihkKSB7IHJldHVybiBkLno7IH0pOwoKICAgIGNvbG9yX21hcF9mODFhMmJiNmJkN2Q0ZTEyODRiNjQ0NjMzNTM1MmFlZi5nLmNhbGwoY29sb3JfbWFwX2Y4MWEyYmI2YmQ3ZDRlMTI4NGI2NDQ2MzM1MzUyYWVmLnhBeGlzKS5hcHBlbmQoInRleHQiKQogICAgICAgIC5hdHRyKCJjbGFzcyIsICJjYXB0aW9uIikKICAgICAgICAuYXR0cigieSIsIDIxKQogICAgICAgIC50ZXh0KCcnKTsKPC9zY3JpcHQ+" style="position:absolute;width:100%;height:100%;left:0;top:0;border:none !important;" allowfullscreen webkitallowfullscreen mozallowfullscreen></iframe></div></div>




```python
tmp_criminal = crime_anal_norm['살인'] /  crime_anal_norm['인구수'] * 1000000

map = folium.Map(location=[37.5502, 126.982], zoom_start=11, 
                 tiles='Stamen Toner')

map.choropleth(geo_data = geo_str,
               data = tmp_criminal,
               columns = [crime_anal.index, tmp_criminal],
               fill_color = 'PuRd', #PuRd, YlGnBu
               key_on = 'feature.id')
map
```

    C:\ProgramData\Anaconda3\lib\site-packages\folium\folium.py:432: FutureWarning: The choropleth  method has been deprecated. Instead use the new Choropleth class, which has the same arguments. See the example notebook 'GeoJSON_and_choropleth' for how to do this.
      FutureWarning
    




<div style="width:100%;"><div style="position:relative;width:100%;height:0;padding-bottom:60%;"><iframe src="data:text/html;charset=utf-8;base64,PCFET0NUWVBFIGh0bWw+CjxoZWFkPiAgICAKICAgIDxtZXRhIGh0dHAtZXF1aXY9ImNvbnRlbnQtdHlwZSIgY29udGVudD0idGV4dC9odG1sOyBjaGFyc2V0PVVURi04IiAvPgogICAgPHNjcmlwdD5MX1BSRUZFUl9DQU5WQVM9ZmFsc2U7IExfTk9fVE9VQ0g9ZmFsc2U7IExfRElTQUJMRV8zRD1mYWxzZTs8L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmpzIj48L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2FqYXguZ29vZ2xlYXBpcy5jb20vYWpheC9saWJzL2pxdWVyeS8xLjExLjEvanF1ZXJ5Lm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvanMvYm9vdHN0cmFwLm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9jZG5qcy5jbG91ZGZsYXJlLmNvbS9hamF4L2xpYnMvTGVhZmxldC5hd2Vzb21lLW1hcmtlcnMvMi4wLjIvbGVhZmxldC5hd2Vzb21lLW1hcmtlcnMuanMiPjwvc2NyaXB0PgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL21heGNkbi5ib290c3RyYXBjZG4uY29tL2Jvb3RzdHJhcC8zLjIuMC9jc3MvYm9vdHN0cmFwLm1pbi5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvY3NzL2Jvb3RzdHJhcC10aGVtZS5taW4uY3NzIi8+CiAgICA8bGluayByZWw9InN0eWxlc2hlZXQiIGhyZWY9Imh0dHBzOi8vbWF4Y2RuLmJvb3RzdHJhcGNkbi5jb20vZm9udC1hd2Vzb21lLzQuNi4zL2Nzcy9mb250LWF3ZXNvbWUubWluLmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2NkbmpzLmNsb3VkZmxhcmUuY29tL2FqYXgvbGlicy9MZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy8yLjAuMi9sZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9yYXdjZG4uZ2l0aGFjay5jb20vcHl0aG9uLXZpc3VhbGl6YXRpb24vZm9saXVtL21hc3Rlci9mb2xpdW0vdGVtcGxhdGVzL2xlYWZsZXQuYXdlc29tZS5yb3RhdGUuY3NzIi8+CiAgICA8c3R5bGU+aHRtbCwgYm9keSB7d2lkdGg6IDEwMCU7aGVpZ2h0OiAxMDAlO21hcmdpbjogMDtwYWRkaW5nOiAwO308L3N0eWxlPgogICAgPHN0eWxlPiNtYXAge3Bvc2l0aW9uOmFic29sdXRlO3RvcDowO2JvdHRvbTowO3JpZ2h0OjA7bGVmdDowO308L3N0eWxlPgogICAgCiAgICA8bWV0YSBuYW1lPSJ2aWV3cG9ydCIgY29udGVudD0id2lkdGg9ZGV2aWNlLXdpZHRoLAogICAgICAgIGluaXRpYWwtc2NhbGU9MS4wLCBtYXhpbXVtLXNjYWxlPTEuMCwgdXNlci1zY2FsYWJsZT1ubyIgLz4KICAgIDxzdHlsZT4jbWFwXzJkMDdjMjU1OTg5ODQ2OTc4NTUyNmY2M2RiZjAzMDgwIHsKICAgICAgICBwb3NpdGlvbjogcmVsYXRpdmU7CiAgICAgICAgd2lkdGg6IDEwMC4wJTsKICAgICAgICBoZWlnaHQ6IDEwMC4wJTsKICAgICAgICBsZWZ0OiAwLjAlOwogICAgICAgIHRvcDogMC4wJTsKICAgICAgICB9CiAgICA8L3N0eWxlPgogICAgPHNjcmlwdCBzcmM9Imh0dHBzOi8vY2RuanMuY2xvdWRmbGFyZS5jb20vYWpheC9saWJzL2QzLzMuNS41L2QzLm1pbi5qcyI+PC9zY3JpcHQ+CjwvaGVhZD4KPGJvZHk+ICAgIAogICAgCiAgICA8ZGl2IGNsYXNzPSJmb2xpdW0tbWFwIiBpZD0ibWFwXzJkMDdjMjU1OTg5ODQ2OTc4NTUyNmY2M2RiZjAzMDgwIiA+PC9kaXY+CjwvYm9keT4KPHNjcmlwdD4gICAgCiAgICAKICAgIAogICAgICAgIHZhciBib3VuZHMgPSBudWxsOwogICAgCgogICAgdmFyIG1hcF8yZDA3YzI1NTk4OTg0Njk3ODU1MjZmNjNkYmYwMzA4MCA9IEwubWFwKAogICAgICAgICdtYXBfMmQwN2MyNTU5ODk4NDY5Nzg1NTI2ZjYzZGJmMDMwODAnLCB7CiAgICAgICAgY2VudGVyOiBbMzcuNTUwMiwgMTI2Ljk4Ml0sCiAgICAgICAgem9vbTogMTEsCiAgICAgICAgbWF4Qm91bmRzOiBib3VuZHMsCiAgICAgICAgbGF5ZXJzOiBbXSwKICAgICAgICB3b3JsZENvcHlKdW1wOiBmYWxzZSwKICAgICAgICBjcnM6IEwuQ1JTLkVQU0czODU3LAogICAgICAgIHpvb21Db250cm9sOiB0cnVlLAogICAgICAgIH0pOwoKICAgIAogICAgCiAgICB2YXIgdGlsZV9sYXllcl8zOTg0NzBjZGFlODE0YTI2YTllOWYzZGYxZTk0ODFjMiA9IEwudGlsZUxheWVyKAogICAgICAgICdodHRwczovL3N0YW1lbi10aWxlcy17c30uYS5zc2wuZmFzdGx5Lm5ldC90b25lci97en0ve3h9L3t5fS5wbmcnLAogICAgICAgIHsKICAgICAgICAiYXR0cmlidXRpb24iOiBudWxsLAogICAgICAgICJkZXRlY3RSZXRpbmEiOiBmYWxzZSwKICAgICAgICAibWF4TmF0aXZlWm9vbSI6IDE4LAogICAgICAgICJtYXhab29tIjogMTgsCiAgICAgICAgIm1pblpvb20iOiAwLAogICAgICAgICJub1dyYXAiOiBmYWxzZSwKICAgICAgICAib3BhY2l0eSI6IDEsCiAgICAgICAgInN1YmRvbWFpbnMiOiAiYWJjIiwKICAgICAgICAidG1zIjogZmFsc2UKfSkuYWRkVG8obWFwXzJkMDdjMjU1OTg5ODQ2OTc4NTUyNmY2M2RiZjAzMDgwKTsKICAgIAogICAgICAgICAgICB2YXIgY2hvcm9wbGV0aF8yYmI1ODZiMzkzYmI0ZDdmYWQwYWI0YWEwMDU3YjU3NCA9IEwuZmVhdHVyZUdyb3VwKAogICAgICAgICAgICAgICAgKS5hZGRUbyhtYXBfMmQwN2MyNTU5ODk4NDY5Nzg1NTI2ZjYzZGJmMDMwODApOwogICAgICAgIAogICAgCiAgICAgICAgCiAgICAgICAgdmFyIGdlb19qc29uX2M0YTdmN2RhZjM4MjQ4MTVhZmIxY2VjM2MzODBhZjM3ID0gTC5nZW9Kc29uKAogICAgICAgICAgICB7ImZlYXR1cmVzIjogW3siZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI3LjExNTE5NTg0OTgxNjA2LCAzNy41NTc1MzMxODA3MDQ5MTVdLCBbMTI3LjE2NjgzMTg0MzY2MTI5LCAzNy41NzY3MjQ4NzM4ODYyN10sIFsxMjcuMTg0MDg3OTIzMzAxNTIsIDM3LjU1ODE0MjgwMzY5NTc1XSwgWzEyNy4xNjUzMDk4NDMwNzQ0NywgMzcuNTQyMjE4NTEyNTg2OTNdLCBbMTI3LjE0NjcyODA2ODIzNTAyLCAzNy41MTQxNTY4MDY4MDI5MV0sIFsxMjcuMTIxMjMxNjU3MTk2MTUsIDM3LjUyNTI4MjcwMDg5XSwgWzEyNy4xMTE2NzY0MjAzNjA4LCAzNy41NDA2Njk5NTUzMjQ5NjVdLCBbMTI3LjExNTE5NTg0OTgxNjA2LCAzNy41NTc1MzMxODA3MDQ5MTVdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YWMxNVx1YjNkOVx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMjUwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWFjMTVcdWIzZDlcdWFkNmMiLCAibmFtZV9lbmciOiAiR2FuZ2RvbmctZ3UiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICIjZjFlZWY2IiwgImZpbGxPcGFjaXR5IjogMC42LCAib3BhY2l0eSI6IDEsICJ3ZWlnaHQiOiAxfX0sICJ0eXBlIjogIkZlYXR1cmUifSwgeyJnZW9tZXRyeSI6IHsiY29vcmRpbmF0ZXMiOiBbW1sxMjcuMDY5MDY5ODEzMDM3MiwgMzcuNTIyMjc5NDIzNTA1MDI2XSwgWzEyNy4xMDA4NzUxOTc5MTk2MiwgMzcuNTI0ODQxMjIwMTY3MDU1XSwgWzEyNy4xMTE2NzY0MjAzNjA4LCAzNy41NDA2Njk5NTUzMjQ5NjVdLCBbMTI3LjEyMTIzMTY1NzE5NjE1LCAzNy41MjUyODI3MDA4OV0sIFsxMjcuMTQ2NzI4MDY4MjM1MDIsIDM3LjUxNDE1NjgwNjgwMjkxXSwgWzEyNy4xNjM0OTQ0MjE1NzY1LCAzNy40OTc0NDU0MDYwOTc0ODRdLCBbMTI3LjE0MjA2MDU4NDEzMjc0LCAzNy40NzA4OTgxOTA5ODUwMV0sIFsxMjcuMTI0NDA1NzEwODA4OTMsIDM3LjQ2MjQwNDQ1NTg3MDQ4XSwgWzEyNy4xMTExNzA4NTIwMTIzOCwgMzcuNDg1NzA4MzgxNTEyNDQ1XSwgWzEyNy4wNzE5MTQ2MDAwNzI0LCAzNy41MDIyNDAxMzU4NzY2OV0sIFsxMjcuMDY5MDY5ODEzMDM3MiwgMzcuNTIyMjc5NDIzNTA1MDI2XV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJcdWMxYTFcdWQzMGNcdWFkNmMiLCAicHJvcGVydGllcyI6IHsiYmFzZV95ZWFyIjogIjIwMTMiLCAiY29kZSI6ICIxMTI0MCIsICJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiXHVjMWExXHVkMzBjXHVhZDZjIiwgIm5hbWVfZW5nIjogIlNvbmdwYS1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNjOTk0YzciLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wNTg2NzM1OTI4ODM5OCwgMzcuNTI2Mjk5NzQ5MjI1NjhdLCBbMTI3LjA2OTA2OTgxMzAzNzIsIDM3LjUyMjI3OTQyMzUwNTAyNl0sIFsxMjcuMDcxOTE0NjAwMDcyNCwgMzcuNTAyMjQwMTM1ODc2NjldLCBbMTI3LjExMTE3MDg1MjAxMjM4LCAzNy40ODU3MDgzODE1MTI0NDVdLCBbMTI3LjEyNDQwNTcxMDgwODkzLCAzNy40NjI0MDQ0NTU4NzA0OF0sIFsxMjcuMDk4NDI3NTkzMTg3NTEsIDM3LjQ1ODYyMjUzODU3NDYxXSwgWzEyNy4wODY0MDQ0MDU3ODE1NiwgMzcuNDcyNjk3OTM1MTg0NjU1XSwgWzEyNy4wNTU5MTcwNDgxOTA0LCAzNy40NjU5MjI4OTE0MDc3XSwgWzEyNy4wMzYyMTkxNTA5ODc5OCwgMzcuNDgxNzU4MDI0Mjc2MDNdLCBbMTI3LjAxMzk3MTE5NjY3NTEzLCAzNy41MjUwMzk4ODI4OTY2OV0sIFsxMjcuMDIzMDI4MzE4OTA1NTksIDM3LjUzMjMxODk5NTgyNjYzXSwgWzEyNy4wNTg2NzM1OTI4ODM5OCwgMzcuNTI2Mjk5NzQ5MjI1NjhdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YWMxNVx1YjBhOFx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMjMwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWFjMTVcdWIwYThcdWFkNmMiLCAibmFtZV9lbmciOiAiR2FuZ25hbS1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNkZjY1YjAiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wMTM5NzExOTY2NzUxMywgMzcuNTI1MDM5ODgyODk2NjldLCBbMTI3LjAzNjIxOTE1MDk4Nzk4LCAzNy40ODE3NTgwMjQyNzYwM10sIFsxMjcuMDU1OTE3MDQ4MTkwNCwgMzcuNDY1OTIyODkxNDA3N10sIFsxMjcuMDg2NDA0NDA1NzgxNTYsIDM3LjQ3MjY5NzkzNTE4NDY1NV0sIFsxMjcuMDk4NDI3NTkzMTg3NTEsIDM3LjQ1ODYyMjUzODU3NDYxXSwgWzEyNy4wOTA0NjkyODU2NTk1MSwgMzcuNDQyOTY4MjYxMTQxODVdLCBbMTI3LjA2Nzc4MTA3NjA1NDMzLCAzNy40MjYxOTc0MjQwNTczMTRdLCBbMTI3LjA0OTU3MjMyOTg3MTQyLCAzNy40MjgwNTgzNjg0NTY5NF0sIFsxMjcuMDM4ODE3ODI1OTc5MjIsIDM3LjQ1MzgyMDM5ODUxNzE1XSwgWzEyNi45OTA3MjA3MzE5NTQ2MiwgMzcuNDU1MzI2MTQzMzEwMDI1XSwgWzEyNi45ODM2NzY2ODI5MTgwMiwgMzcuNDczODU2NDkyNjkyMDg2XSwgWzEyNi45ODIyMzgwNzkxNjA4MSwgMzcuNTA5MzE0OTY2NzcwMzI2XSwgWzEyNy4wMTM5NzExOTY2NzUxMywgMzcuNTI1MDM5ODgyODk2NjldXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzExY1x1Y2QwOFx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMjIwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWMxMWNcdWNkMDhcdWFkNmMiLCAibmFtZV9lbmciOiAiU2VvY2hvLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2M5OTRjNyIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2Ljk4MzY3NjY4MjkxODAyLCAzNy40NzM4NTY0OTI2OTIwODZdLCBbMTI2Ljk5MDcyMDczMTk1NDYyLCAzNy40NTUzMjYxNDMzMTAwMjVdLCBbMTI2Ljk2NTIwNDM5MDg1MTQzLCAzNy40MzgyNDk3ODQwMDYyNDZdLCBbMTI2Ljk1MDAwMDAxMDEwMTgyLCAzNy40MzYxMzQ1MTE2NTcxOV0sIFsxMjYuOTMwODQ0MDgwNTY1MjUsIDM3LjQ0NzM4MjkyODMzMzk5NF0sIFsxMjYuOTE2NzcyODE0NjYwMSwgMzcuNDU0OTA1NjY0MjM3ODldLCBbMTI2LjkwMTU2MDk0MTI5ODk1LCAzNy40Nzc1Mzg0Mjc4OTkwMV0sIFsxMjYuOTA1MzE5NzU4MDE4MTIsIDM3LjQ4MjE4MDg3NTc1NDI5XSwgWzEyNi45NDkyMjY2MTM4OTUwOCwgMzcuNDkxMjU0Mzc0OTU2NDldLCBbMTI2Ljk3MjU4OTE4NTA2NjIsIDM3LjQ3MjU2MTM2MzI3ODEyNV0sIFsxMjYuOTgzNjc2NjgyOTE4MDIsIDM3LjQ3Mzg1NjQ5MjY5MjA4Nl1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVhZDAwXHVjNTQ1XHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEyMTAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YWQwMFx1YzU0NVx1YWQ2YyIsICJuYW1lX2VuZyI6ICJHd2FuYWstZ3UiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICIjYzk5NGM3IiwgImZpbGxPcGFjaXR5IjogMC42LCAib3BhY2l0eSI6IDEsICJ3ZWlnaHQiOiAxfX0sICJ0eXBlIjogIkZlYXR1cmUifSwgeyJnZW9tZXRyeSI6IHsiY29vcmRpbmF0ZXMiOiBbW1sxMjYuOTgyMjM4MDc5MTYwODEsIDM3LjUwOTMxNDk2Njc3MDMyNl0sIFsxMjYuOTgzNjc2NjgyOTE4MDIsIDM3LjQ3Mzg1NjQ5MjY5MjA4Nl0sIFsxMjYuOTcyNTg5MTg1MDY2MiwgMzcuNDcyNTYxMzYzMjc4MTI1XSwgWzEyNi45NDkyMjY2MTM4OTUwOCwgMzcuNDkxMjU0Mzc0OTU2NDldLCBbMTI2LjkwNTMxOTc1ODAxODEyLCAzNy40ODIxODA4NzU3NTQyOV0sIFsxMjYuOTIxNzc4OTMxNzQ4MjUsIDM3LjQ5NDg4OTg3NzQxNTE3Nl0sIFsxMjYuOTI4MTA2Mjg4MjgyNzksIDM3LjUxMzI5NTk1NzMyMDE1XSwgWzEyNi45NTI0OTk5MDI5ODE1OSwgMzcuNTE3MjI1MDA3NDE4MTNdLCBbMTI2Ljk4MjIzODA3OTE2MDgxLCAzNy41MDkzMTQ5NjY3NzAzMjZdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YjNkOVx1Yzc5MVx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMjAwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWIzZDlcdWM3OTFcdWFkNmMiLCAibmFtZV9lbmciOiAiRG9uZ2phay1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNkNGI5ZGEiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNi44OTE4NDY2Mzg2Mjc2NCwgMzcuNTQ3MzczOTc0OTk3MTE0XSwgWzEyNi45NDU2NjczMzA4MzIxMiwgMzcuNTI2NjE3NTQyNDUzMzY2XSwgWzEyNi45NTI0OTk5MDI5ODE1OSwgMzcuNTE3MjI1MDA3NDE4MTNdLCBbMTI2LjkyODEwNjI4ODI4Mjc5LCAzNy41MTMyOTU5NTczMjAxNV0sIFsxMjYuOTIxNzc4OTMxNzQ4MjUsIDM3LjQ5NDg4OTg3NzQxNTE3Nl0sIFsxMjYuOTA1MzE5NzU4MDE4MTIsIDM3LjQ4MjE4MDg3NTc1NDI5XSwgWzEyNi44OTU5NDc3Njc4MjQ4NSwgMzcuNTA0Njc1MjgxMzA5MTc2XSwgWzEyNi44ODE1NjQwMjM1Mzg2MiwgMzcuNTEzOTcwMDM0NzY1Njg0XSwgWzEyNi44ODgyNTc1Nzg2MDA5OSwgMzcuNTQwNzk3MzM2MzAyMzJdLCBbMTI2Ljg5MTg0NjYzODYyNzY0LCAzNy41NDczNzM5NzQ5OTcxMTRdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzYwMVx1YjRmMVx1ZDNlY1x1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMTkwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWM2MDFcdWI0ZjFcdWQzZWNcdWFkNmMiLCAibmFtZV9lbmciOiAiWWVvbmdkZXVuZ3BvLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiIzk4MDA0MyIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2LjkwMTU2MDk0MTI5ODk1LCAzNy40Nzc1Mzg0Mjc4OTkwMV0sIFsxMjYuOTE2NzcyODE0NjYwMSwgMzcuNDU0OTA1NjY0MjM3ODldLCBbMTI2LjkzMDg0NDA4MDU2NTI1LCAzNy40NDczODI5MjgzMzM5OTRdLCBbMTI2LjkwMjU4MzE3MTE2OTcsIDM3LjQzNDU0OTM2NjM0OTEyNF0sIFsxMjYuODc2ODMyNzE1MDI0MjgsIDM3LjQ4MjU3NjU5MTYwNzMwNV0sIFsxMjYuOTAxNTYwOTQxMjk4OTUsIDM3LjQ3NzUzODQyNzg5OTAxXV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJcdWFlMDhcdWNjOWNcdWFkNmMiLCAicHJvcGVydGllcyI6IHsiYmFzZV95ZWFyIjogIjIwMTMiLCAiY29kZSI6ICIxMTE4MCIsICJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiXHVhZTA4XHVjYzljXHVhZDZjIiwgIm5hbWVfZW5nIjogIkdldW1jaGVvbi1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNmMWVlZjYiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNi44MjY4ODA4MTUxNzMxNCwgMzcuNTA1NDg5NzIyMzI4OTZdLCBbMTI2Ljg4MTU2NDAyMzUzODYyLCAzNy41MTM5NzAwMzQ3NjU2ODRdLCBbMTI2Ljg5NTk0Nzc2NzgyNDg1LCAzNy41MDQ2NzUyODEzMDkxNzZdLCBbMTI2LjkwNTMxOTc1ODAxODEyLCAzNy40ODIxODA4NzU3NTQyOV0sIFsxMjYuOTAxNTYwOTQxMjk4OTUsIDM3LjQ3NzUzODQyNzg5OTAxXSwgWzEyNi44NzY4MzI3MTUwMjQyOCwgMzcuNDgyNTc2NTkxNjA3MzA1XSwgWzEyNi44NDc2MjY3NjA1NDk1MywgMzcuNDcxNDY3MjM5MzYzMjNdLCBbMTI2LjgzNTQ5NDg1MDc2MTk2LCAzNy40NzQwOTgyMzY5NzUwOTVdLCBbMTI2LjgyMjY0Nzk2NzkxMzQ4LCAzNy40ODc4NDc2NDkyMTQ3XSwgWzEyNi44MjUwNDczNjMzMTQwNiwgMzcuNTAzMDI2MTI2NDA0NDNdLCBbMTI2LjgyNjg4MDgxNTE3MzE0LCAzNy41MDU0ODk3MjIzMjg5Nl1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVhZDZjXHViODVjXHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTExNzAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YWQ2Y1x1Yjg1Y1x1YWQ2YyIsICJuYW1lX2VuZyI6ICJHdXJvLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2M5OTRjNyIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2Ljc5NTc1NzY4NTUyOTA3LCAzNy41Nzg4MTA4NzYzMzIwMl0sIFsxMjYuODA3MDIxMTUwMjM1OTcsIDM3LjYwMTIzMDAxMDEzMjI4XSwgWzEyNi44MjI1MTQzODQ3NzEwNSwgMzcuNTg4MDQzMDgxMDA4Ml0sIFsxMjYuODU5ODQxOTkzOTk2NjcsIDM3LjU3MTg0Nzg1NTI5Mjc0NV0sIFsxMjYuODkxODQ2NjM4NjI3NjQsIDM3LjU0NzM3Mzk3NDk5NzExNF0sIFsxMjYuODg4MjU3NTc4NjAwOTksIDM3LjU0MDc5NzMzNjMwMjMyXSwgWzEyNi44NjYzNzQ2NDMyMTIzOCwgMzcuNTQ4NTkxOTEwOTQ4MjNdLCBbMTI2Ljg2NjEwMDczNDc2Mzk1LCAzNy41MjY5OTk2NDE0NDY2OV0sIFsxMjYuODQyNTcyOTE5NDMxNTMsIDM3LjUyMzczNzA3ODA1NTk2XSwgWzEyNi44MjQyMzMxNDI2NzIyLCAzNy41Mzc4ODA3ODc1MzI0OF0sIFsxMjYuNzczMjQ0MTc3MTc3MDMsIDM3LjU0NTkxMjM0NTA1NTRdLCBbMTI2Ljc2OTc5MTgwNTc5MzUyLCAzNy41NTEzOTE4MzAwODgwOV0sIFsxMjYuNzk1NzU3Njg1NTI5MDcsIDM3LjU3ODgxMDg3NjMzMjAyXV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJcdWFjMTVcdWMxMWNcdWFkNmMiLCAicHJvcGVydGllcyI6IHsiYmFzZV95ZWFyIjogIjIwMTMiLCAiY29kZSI6ICIxMTE2MCIsICJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiXHVhYzE1XHVjMTFjXHVhZDZjIiwgIm5hbWVfZW5nIjogIkdhbmdzZW8tZ3UiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICJibGFjayIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2LjgyNDIzMzE0MjY3MjIsIDM3LjUzNzg4MDc4NzUzMjQ4XSwgWzEyNi44NDI1NzI5MTk0MzE1MywgMzcuNTIzNzM3MDc4MDU1OTZdLCBbMTI2Ljg2NjEwMDczNDc2Mzk1LCAzNy41MjY5OTk2NDE0NDY2OV0sIFsxMjYuODY2Mzc0NjQzMjEyMzgsIDM3LjU0ODU5MTkxMDk0ODIzXSwgWzEyNi44ODgyNTc1Nzg2MDA5OSwgMzcuNTQwNzk3MzM2MzAyMzJdLCBbMTI2Ljg4MTU2NDAyMzUzODYyLCAzNy41MTM5NzAwMzQ3NjU2ODRdLCBbMTI2LjgyNjg4MDgxNTE3MzE0LCAzNy41MDU0ODk3MjIzMjg5Nl0sIFsxMjYuODI0MjMzMTQyNjcyMiwgMzcuNTM3ODgwNzg3NTMyNDhdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzU5MVx1Y2M5Y1x1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMTUwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWM1OTFcdWNjOWNcdWFkNmMiLCAibmFtZV9lbmciOiAiWWFuZ2NoZW9uLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2RmNjViMCIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2LjkwNTIyMDY1ODMxMDUzLCAzNy41NzQwOTcwMDUyMjU3NF0sIFsxMjYuOTM4OTgxNjE3OTg5NzMsIDM3LjU1MjMxMDAwMzcyODEyNF0sIFsxMjYuOTYzNTgyMjY3MTA4MTIsIDM3LjU1NjA1NjM1NDc1MTU0XSwgWzEyNi45NjQ0ODU3MDU1MzA1NSwgMzcuNTQ4NzA1NjkyMDIxNjM1XSwgWzEyNi45NDU2NjczMzA4MzIxMiwgMzcuNTI2NjE3NTQyNDUzMzY2XSwgWzEyNi44OTE4NDY2Mzg2Mjc2NCwgMzcuNTQ3MzczOTc0OTk3MTE0XSwgWzEyNi44NTk4NDE5OTM5OTY2NywgMzcuNTcxODQ3ODU1MjkyNzQ1XSwgWzEyNi44ODQzMzI4NDc3MzI4OCwgMzcuNTg4MTQzMzIyODgwNTI2XSwgWzEyNi45MDUyMjA2NTgzMTA1MywgMzcuNTc0MDk3MDA1MjI1NzRdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YjljOFx1ZDNlY1x1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMTQwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWI5YzhcdWQzZWNcdWFkNmMiLCAibmFtZV9lbmciOiAiTWFwby1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNkZjY1YjAiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNi45NTI0NzUyMDMwNTcyLCAzNy42MDUwODY5MjczNzA0NV0sIFsxMjYuOTU1NjU0MjU4NDY0NjMsIDM3LjU3NjA4MDc5MDg4MTQ1Nl0sIFsxMjYuOTY4NzM2MzMyNzkwNzUsIDM3LjU2MzEzNjA0NjkwODI3XSwgWzEyNi45NjM1ODIyNjcxMDgxMiwgMzcuNTU2MDU2MzU0NzUxNTRdLCBbMTI2LjkzODk4MTYxNzk4OTczLCAzNy41NTIzMTAwMDM3MjgxMjRdLCBbMTI2LjkwNTIyMDY1ODMxMDUzLCAzNy41NzQwOTcwMDUyMjU3NF0sIFsxMjYuOTUyNDc1MjAzMDU3MiwgMzcuNjA1MDg2OTI3MzcwNDVdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzExY1x1YjMwMFx1YmIzOFx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMTMwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWMxMWNcdWIzMDBcdWJiMzhcdWFkNmMiLCAibmFtZV9lbmciOiAiU2VvZGFlbXVuLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2YxZWVmNiIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2Ljk3Mzg4NjQxMjg3MDIsIDM3LjYyOTQ5NjM0Nzg2ODg4XSwgWzEyNi45NTQyNzAxNzAwNjEyOSwgMzcuNjIyMDMzNDMxMzM5NDI1XSwgWzEyNi45NTI0NzUyMDMwNTcyLCAzNy42MDUwODY5MjczNzA0NV0sIFsxMjYuOTA1MjIwNjU4MzEwNTMsIDM3LjU3NDA5NzAwNTIyNTc0XSwgWzEyNi44ODQzMzI4NDc3MzI4OCwgMzcuNTg4MTQzMzIyODgwNTI2XSwgWzEyNi45MDM5NjY4MTAwMzU5NSwgMzcuNTkyMjc0MDM0MTk5NDJdLCBbMTI2LjkwMzAzMDY2MTc3NjY4LCAzNy42MDk5Nzc5MTE0MDEzNDRdLCBbMTI2LjkxNDU1NDgxNDI5NjQ4LCAzNy42NDE1MDA1MDk5NjkzNV0sIFsxMjYuOTU2NDczNzk3Mzg3LCAzNy42NTI0ODA3MzczMzk0NDVdLCBbMTI2Ljk3Mzg4NjQxMjg3MDIsIDM3LjYyOTQ5NjM0Nzg2ODg4XV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJcdWM3NDBcdWQzYzlcdWFkNmMiLCAicHJvcGVydGllcyI6IHsiYmFzZV95ZWFyIjogIjIwMTMiLCAiY29kZSI6ICIxMTEyMCIsICJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiXHVjNzQwXHVkM2M5XHVhZDZjIiwgIm5hbWVfZW5nIjogIkV1bnB5ZW9uZy1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNmMWVlZjYiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wODM4NzUyNzAzMTk1LCAzNy42OTM1OTUzNDIwMjAzNF0sIFsxMjcuMDk3MDYzOTEzMDk2OTUsIDM3LjY4NjM4MzcxOTM3MjI5NF0sIFsxMjcuMDk0NDA3NjYyOTg3MTcsIDM3LjY0NzEzNDkwNDczMDQ1XSwgWzEyNy4xMTMyNjc5NTg1NTE5OSwgMzcuNjM5NjIyOTA1MzE1OTI1XSwgWzEyNy4xMDc4MjI3NzY4ODEyOSwgMzcuNjE4MDQyNDQyNDEwNjldLCBbMTI3LjA3MzUxMjQzODI1Mjc4LCAzNy42MTI4MzY2MDM0MjMxM10sIFsxMjcuMDUyMDkzNzM1Njg2MTksIDM3LjYyMTY0MDY1NDg3NzgyXSwgWzEyNy4wNDM1ODgwMDg5NTYwOSwgMzcuNjI4NDg5MzEyOTg3MTVdLCBbMTI3LjA1ODAwMDc1MjIwMDkxLCAzNy42NDMxODI2Mzg3ODI3Nl0sIFsxMjcuMDUyODg0Nzk3MTA0ODUsIDM3LjY4NDIzODU3MDg0MzQ3XSwgWzEyNy4wODM4NzUyNzAzMTk1LCAzNy42OTM1OTUzNDIwMjAzNF1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHViMTc4XHVjNmQwXHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTExMTAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YjE3OFx1YzZkMFx1YWQ2YyIsICJuYW1lX2VuZyI6ICJOb3dvbi1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNjOTk0YzciLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wNTI4ODQ3OTcxMDQ4NSwgMzcuNjg0MjM4NTcwODQzNDddLCBbMTI3LjA1ODAwMDc1MjIwMDkxLCAzNy42NDMxODI2Mzg3ODI3Nl0sIFsxMjcuMDQzNTg4MDA4OTU2MDksIDM3LjYyODQ4OTMxMjk4NzE1XSwgWzEyNy4wMTQ2NTkzNTg5MjQ2NiwgMzcuNjQ5NDM2ODc0OTY4MTJdLCBbMTI3LjAyMDYyMTE2MTQxMzg5LCAzNy42NjcxNzM1NzU5NzEyMDVdLCBbMTI3LjAxMDM5NjY2MDQyMDcxLCAzNy42ODE4OTQ1ODk2MDM1OTRdLCBbMTI3LjAxNzk1MDk5MjAzNDMyLCAzNy42OTgyNDQxMjc3NTY2Ml0sIFsxMjcuMDUyODg0Nzk3MTA0ODUsIDM3LjY4NDIzODU3MDg0MzQ3XV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJcdWIzYzRcdWJkMDlcdWFkNmMiLCAicHJvcGVydGllcyI6IHsiYmFzZV95ZWFyIjogIjIwMTMiLCAiY29kZSI6ICIxMTEwMCIsICJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiXHViM2M0XHViZDA5XHVhZDZjIiwgIm5hbWVfZW5nIjogIkRvYm9uZy1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNmMWVlZjYiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNi45OTM4MzkwMzQyNCwgMzcuNjc2NjgxNzYxMTk5MDg1XSwgWzEyNy4wMTAzOTY2NjA0MjA3MSwgMzcuNjgxODk0NTg5NjAzNTk0XSwgWzEyNy4wMjA2MjExNjE0MTM4OSwgMzcuNjY3MTczNTc1OTcxMjA1XSwgWzEyNy4wMTQ2NTkzNTg5MjQ2NiwgMzcuNjQ5NDM2ODc0OTY4MTJdLCBbMTI3LjA0MzU4ODAwODk1NjA5LCAzNy42Mjg0ODkzMTI5ODcxNV0sIFsxMjcuMDUyMDkzNzM1Njg2MTksIDM3LjYyMTY0MDY1NDg3NzgyXSwgWzEyNy4wMzg5MjQwMDk5MjMwMSwgMzcuNjA5NzE1NjExMDIzODE2XSwgWzEyNy4wMTI4MTU0NzQ5NTIzLCAzNy42MTM2NTIyNDM0NzAyNTZdLCBbMTI2Ljk4NjcyNzA1NTEzODY5LCAzNy42MzM3NzY0MTI4ODE5Nl0sIFsxMjYuOTgxNzQ1MjY3NjU1MSwgMzcuNjUyMDk3NjkzODc3NzZdLCBbMTI2Ljk5MzgzOTAzNDI0LCAzNy42NzY2ODE3NjExOTkwODVdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YWMxNVx1YmQ4MVx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMDkwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWFjMTVcdWJkODFcdWFkNmMiLCAibmFtZV9lbmciOiAiR2FuZ2J1ay1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNkZjY1YjAiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNi45NzcxNzU0MDY0MTYsIDM3LjYyODU5NzE1NDAwMzg4XSwgWzEyNi45ODY3MjcwNTUxMzg2OSwgMzcuNjMzNzc2NDEyODgxOTZdLCBbMTI3LjAxMjgxNTQ3NDk1MjMsIDM3LjYxMzY1MjI0MzQ3MDI1Nl0sIFsxMjcuMDM4OTI0MDA5OTIzMDEsIDM3LjYwOTcxNTYxMTAyMzgxNl0sIFsxMjcuMDUyMDkzNzM1Njg2MTksIDM3LjYyMTY0MDY1NDg3NzgyXSwgWzEyNy4wNzM1MTI0MzgyNTI3OCwgMzcuNjEyODM2NjAzNDIzMTNdLCBbMTI3LjA3MzgyNzA3MDk5MjI3LCAzNy42MDQwMTkyODk4NjQxOV0sIFsxMjcuMDQyNzA1MjIyMDk0LCAzNy41OTIzOTQzNzU5MzM5MV0sIFsxMjcuMDI1MjcyNTQ1MjgwMDMsIDM3LjU3NTI0NjE2MjQ1MjQ5XSwgWzEyNi45OTM0ODI5MzM1ODMxNCwgMzcuNTg4NTY1NDU3MjE2MTU2XSwgWzEyNi45ODg3OTg2NTk5MjM4NCwgMzcuNjExODkyNzMxOTc1Nl0sIFsxMjYuOTc3MTc1NDA2NDE2LCAzNy42Mjg1OTcxNTQwMDM4OF1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVjMTMxXHViZDgxXHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEwODAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YzEzMVx1YmQ4MVx1YWQ2YyIsICJuYW1lX2VuZyI6ICJTZW9uZ2J1ay1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNkNGI5ZGEiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wNzM1MTI0MzgyNTI3OCwgMzcuNjEyODM2NjAzNDIzMTNdLCBbMTI3LjEwNzgyMjc3Njg4MTI5LCAzNy42MTgwNDI0NDI0MTA2OV0sIFsxMjcuMTIwMTI0NjAyMDExNCwgMzcuNjAxNzg0NTc1OTgxODhdLCBbMTI3LjEwMzA0MTc0MjQ5MjE0LCAzNy41NzA3NjM0MjI5MDk1NV0sIFsxMjcuMDgwNjg1NDEyODA0MDMsIDM3LjU2OTA2NDI1NTE5MDE3XSwgWzEyNy4wNzM4MjcwNzA5OTIyNywgMzcuNjA0MDE5Mjg5ODY0MTldLCBbMTI3LjA3MzUxMjQzODI1Mjc4LCAzNy42MTI4MzY2MDM0MjMxM11dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVjOTExXHViNzkxXHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEwNzAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YzkxMVx1Yjc5MVx1YWQ2YyIsICJuYW1lX2VuZyI6ICJKdW5nbmFuZy1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiM5ODAwNDMiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wMjUyNzI1NDUyODAwMywgMzcuNTc1MjQ2MTYyNDUyNDldLCBbMTI3LjA0MjcwNTIyMjA5NCwgMzcuNTkyMzk0Mzc1OTMzOTFdLCBbMTI3LjA3MzgyNzA3MDk5MjI3LCAzNy42MDQwMTkyODk4NjQxOV0sIFsxMjcuMDgwNjg1NDEyODA0MDMsIDM3LjU2OTA2NDI1NTE5MDE3XSwgWzEyNy4wNzQyMTA1MzAyNDM2MiwgMzcuNTU3MjQ3Njk3MTIwODVdLCBbMTI3LjA1MDA1NjAxMDgxNTY3LCAzNy41Njc1Nzc2MTI1OTA4NDZdLCBbMTI3LjAyNTQ3MjY2MzQ5OTc2LCAzNy41Njg5NDM1NTIyMzc3MzRdLCBbMTI3LjAyNTI3MjU0NTI4MDAzLCAzNy41NzUyNDYxNjI0NTI0OV1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHViM2Q5XHViMzAwXHViYjM4XHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEwNjAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YjNkOVx1YjMwMFx1YmIzOFx1YWQ2YyIsICJuYW1lX2VuZyI6ICJEb25nZGFlbXVuLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2Q0YjlkYSIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI3LjA4MDY4NTQxMjgwNDAzLCAzNy41NjkwNjQyNTUxOTAxN10sIFsxMjcuMTAzMDQxNzQyNDkyMTQsIDM3LjU3MDc2MzQyMjkwOTU1XSwgWzEyNy4xMTUxOTU4NDk4MTYwNiwgMzcuNTU3NTMzMTgwNzA0OTE1XSwgWzEyNy4xMTE2NzY0MjAzNjA4LCAzNy41NDA2Njk5NTUzMjQ5NjVdLCBbMTI3LjEwMDg3NTE5NzkxOTYyLCAzNy41MjQ4NDEyMjAxNjcwNTVdLCBbMTI3LjA2OTA2OTgxMzAzNzIsIDM3LjUyMjI3OTQyMzUwNTAyNl0sIFsxMjcuMDU4NjczNTkyODgzOTgsIDM3LjUyNjI5OTc0OTIyNTY4XSwgWzEyNy4wNzQyMTA1MzAyNDM2MiwgMzcuNTU3MjQ3Njk3MTIwODVdLCBbMTI3LjA4MDY4NTQxMjgwNDAzLCAzNy41NjkwNjQyNTUxOTAxN11dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVhZDExXHVjOWM0XHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEwNTAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YWQxMVx1YzljNFx1YWQ2YyIsICJuYW1lX2VuZyI6ICJHd2FuZ2ppbi1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNkNGI5ZGEiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wMjU0NzI2NjM0OTk3NiwgMzcuNTY4OTQzNTUyMjM3NzM0XSwgWzEyNy4wNTAwNTYwMTA4MTU2NywgMzcuNTY3NTc3NjEyNTkwODQ2XSwgWzEyNy4wNzQyMTA1MzAyNDM2MiwgMzcuNTU3MjQ3Njk3MTIwODVdLCBbMTI3LjA1ODY3MzU5Mjg4Mzk4LCAzNy41MjYyOTk3NDkyMjU2OF0sIFsxMjcuMDIzMDI4MzE4OTA1NTksIDM3LjUzMjMxODk5NTgyNjYzXSwgWzEyNy4wMTA3MDg5NDE3NzQ4MiwgMzcuNTQxMTgwNDg5NjQ3NjJdLCBbMTI3LjAyNTQ3MjY2MzQ5OTc2LCAzNy41Njg5NDM1NTIyMzc3MzRdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzEzMVx1YjNkOVx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMDQwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWMxMzFcdWIzZDlcdWFkNmMiLCAibmFtZV9lbmciOiAiU2Vvbmdkb25nLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2Q0YjlkYSIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI3LjAxMDcwODk0MTc3NDgyLCAzNy41NDExODA0ODk2NDc2Ml0sIFsxMjcuMDIzMDI4MzE4OTA1NTksIDM3LjUzMjMxODk5NTgyNjYzXSwgWzEyNy4wMTM5NzExOTY2NzUxMywgMzcuNTI1MDM5ODgyODk2NjldLCBbMTI2Ljk4MjIzODA3OTE2MDgxLCAzNy41MDkzMTQ5NjY3NzAzMjZdLCBbMTI2Ljk1MjQ5OTkwMjk4MTU5LCAzNy41MTcyMjUwMDc0MTgxM10sIFsxMjYuOTQ1NjY3MzMwODMyMTIsIDM3LjUyNjYxNzU0MjQ1MzM2Nl0sIFsxMjYuOTY0NDg1NzA1NTMwNTUsIDM3LjU0ODcwNTY5MjAyMTYzNV0sIFsxMjYuOTg3NTI5OTY5MDMzMjgsIDM3LjU1MDk0ODE4ODA3MTM5XSwgWzEyNy4wMTA3MDg5NDE3NzQ4MiwgMzcuNTQxMTgwNDg5NjQ3NjJdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzZhOVx1YzBiMFx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMDMwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWM2YTlcdWMwYjBcdWFkNmMiLCAibmFtZV9lbmciOiAiWW9uZ3Nhbi1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNjOTk0YzciLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wMjU0NzI2NjM0OTk3NiwgMzcuNTY4OTQzNTUyMjM3NzM0XSwgWzEyNy4wMTA3MDg5NDE3NzQ4MiwgMzcuNTQxMTgwNDg5NjQ3NjJdLCBbMTI2Ljk4NzUyOTk2OTAzMzI4LCAzNy41NTA5NDgxODgwNzEzOV0sIFsxMjYuOTY0NDg1NzA1NTMwNTUsIDM3LjU0ODcwNTY5MjAyMTYzNV0sIFsxMjYuOTYzNTgyMjY3MTA4MTIsIDM3LjU1NjA1NjM1NDc1MTU0XSwgWzEyNi45Njg3MzYzMzI3OTA3NSwgMzcuNTYzMTM2MDQ2OTA4MjddLCBbMTI3LjAyNTQ3MjY2MzQ5OTc2LCAzNy41Njg5NDM1NTIyMzc3MzRdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzkxMVx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMDIwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWM5MTFcdWFkNmMiLCAibmFtZV9lbmciOiAiSnVuZy1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNkNGI5ZGEiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNi45NzM4ODY0MTI4NzAyLCAzNy42Mjk0OTYzNDc4Njg4OF0sIFsxMjYuOTc3MTc1NDA2NDE2LCAzNy42Mjg1OTcxNTQwMDM4OF0sIFsxMjYuOTg4Nzk4NjU5OTIzODQsIDM3LjYxMTg5MjczMTk3NTZdLCBbMTI2Ljk5MzQ4MjkzMzU4MzE0LCAzNy41ODg1NjU0NTcyMTYxNTZdLCBbMTI3LjAyNTI3MjU0NTI4MDAzLCAzNy41NzUyNDYxNjI0NTI0OV0sIFsxMjcuMDI1NDcyNjYzNDk5NzYsIDM3LjU2ODk0MzU1MjIzNzczNF0sIFsxMjYuOTY4NzM2MzMyNzkwNzUsIDM3LjU2MzEzNjA0NjkwODI3XSwgWzEyNi45NTU2NTQyNTg0NjQ2MywgMzcuNTc2MDgwNzkwODgxNDU2XSwgWzEyNi45NTI0NzUyMDMwNTcyLCAzNy42MDUwODY5MjczNzA0NV0sIFsxMjYuOTU0MjcwMTcwMDYxMjksIDM3LjYyMjAzMzQzMTMzOTQyNV0sIFsxMjYuOTczODg2NDEyODcwMiwgMzcuNjI5NDk2MzQ3ODY4ODhdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1Yzg4NVx1Yjg1Y1x1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMDEwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWM4ODVcdWI4NWNcdWFkNmMiLCAibmFtZV9lbmciOiAiSm9uZ25vLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2RkMWM3NyIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn1dLCAidHlwZSI6ICJGZWF0dXJlQ29sbGVjdGlvbiJ9CiAgICAgICAgICAgIAogICAgICAgICAgICApLmFkZFRvKGNob3JvcGxldGhfMmJiNTg2YjM5M2JiNGQ3ZmFkMGFiNGFhMDA1N2I1NzQpOwogICAgICAgIGdlb19qc29uX2M0YTdmN2RhZjM4MjQ4MTVhZmIxY2VjM2MzODBhZjM3LnNldFN0eWxlKGZ1bmN0aW9uKGZlYXR1cmUpIHtyZXR1cm4gZmVhdHVyZS5wcm9wZXJ0aWVzLnN0eWxlO30pOwogICAgICAgIAogICAgCiAgICB2YXIgY29sb3JfbWFwX2FjNTI1OTQyMDAxNzQzNjQ5NjM4OTUxYTIxNWNmOTE0ID0ge307CgogICAgCiAgICBjb2xvcl9tYXBfYWM1MjU5NDIwMDE3NDM2NDk2Mzg5NTFhMjE1Y2Y5MTQuY29sb3IgPSBkMy5zY2FsZS50aHJlc2hvbGQoKQogICAgICAgICAgICAgIC5kb21haW4oWzAuMCwgMC4wMDQ5NzI5MDk3MDE0MzMyMTUsIDAuMDA5OTQ1ODE5NDAyODY2NDMsIDAuMDE0OTE4NzI5MTA0Mjk5NjQ2LCAwLjAxOTg5MTYzODgwNTczMjg2LCAwLjAyNDg2NDU0ODUwNzE2NjA3MywgMC4wMjk4Mzc0NTgyMDg1OTkyOSwgMC4wMzQ4MTAzNjc5MTAwMzI1LCAwLjAzOTc4MzI3NzYxMTQ2NTcyLCAwLjA0NDc1NjE4NzMxMjg5ODk0LCAwLjA0OTcyOTA5NzAxNDMzMjE0NiwgMC4wNTQ3MDIwMDY3MTU3NjUzNywgMC4wNTk2NzQ5MTY0MTcxOTg1OCwgMC4wNjQ2NDc4MjYxMTg2MzE4LCAwLjA2OTYyMDczNTgyMDA2NSwgMC4wNzQ1OTM2NDU1MjE0OTgyMiwgMC4wNzk1NjY1NTUyMjI5MzE0MywgMC4wODQ1Mzk0NjQ5MjQzNjQ2NiwgMC4wODk1MTIzNzQ2MjU3OTc4OCwgMC4wOTQ0ODUyODQzMjcyMzEwOCwgMC4wOTk0NTgxOTQwMjg2NjQyOSwgMC4xMDQ0MzExMDM3MzAwOTc1MSwgMC4xMDk0MDQwMTM0MzE1MzA3NCwgMC4xMTQzNzY5MjMxMzI5NjM5NSwgMC4xMTkzNDk4MzI4MzQzOTcxNywgMC4xMjQzMjI3NDI1MzU4MzAzNywgMC4xMjkyOTU2NTIyMzcyNjM2LCAwLjEzNDI2ODU2MTkzODY5NjgsIDAuMTM5MjQxNDcxNjQwMTMsIDAuMTQ0MjE0MzgxMzQxNTYzMjIsIDAuMTQ5MTg3MjkxMDQyOTk2NDQsIDAuMTU0MTYwMjAwNzQ0NDI5NjUsIDAuMTU5MTMzMTEwNDQ1ODYyODcsIDAuMTY0MTA2MDIwMTQ3Mjk2MDgsIDAuMTY5MDc4OTI5ODQ4NzI5MzMsIDAuMTc0MDUxODM5NTUwMTYyNTQsIDAuMTc5MDI0NzQ5MjUxNTk1NzYsIDAuMTgzOTk3NjU4OTUzMDI4OTcsIDAuMTg4OTcwNTY4NjU0NDYyMTYsIDAuMTkzOTQzNDc4MzU1ODk1MzcsIDAuMTk4OTE2Mzg4MDU3MzI4NTksIDAuMjAzODg5Mjk3NzU4NzYxOCwgMC4yMDg4NjIyMDc0NjAxOTUwMiwgMC4yMTM4MzUxMTcxNjE2MjgyNiwgMC4yMTg4MDgwMjY4NjMwNjE0NywgMC4yMjM3ODA5MzY1NjQ0OTQ3LCAwLjIyODc1Mzg0NjI2NTkyNzksIDAuMjMzNzI2NzU1OTY3MzYxMTIsIDAuMjM4Njk5NjY1NjY4Nzk0MzMsIDAuMjQzNjcyNTc1MzcwMjI3NTIsIDAuMjQ4NjQ1NDg1MDcxNjYwNzMsIDAuMjUzNjE4Mzk0NzczMDkzOTUsIDAuMjU4NTkxMzA0NDc0NTI3MiwgMC4yNjM1NjQyMTQxNzU5NjA0MywgMC4yNjg1MzcxMjM4NzczOTM2LCAwLjI3MzUxMDAzMzU3ODgyNjgsIDAuMjc4NDgyOTQzMjgwMjYsIDAuMjgzNDU1ODUyOTgxNjkzMjMsIDAuMjg4NDI4NzYyNjgzMTI2NDUsIDAuMjkzNDAxNjcyMzg0NTU5NjYsIDAuMjk4Mzc0NTgyMDg1OTkyOSwgMC4zMDMzNDc0OTE3ODc0MjYxLCAwLjMwODMyMDQwMTQ4ODg1OTMsIDAuMzEzMjkzMzExMTkwMjkyNSwgMC4zMTgyNjYyMjA4OTE3MjU3NCwgMC4zMjMyMzkxMzA1OTMxNTg5NSwgMC4zMjgyMTIwNDAyOTQ1OTIxNywgMC4zMzMxODQ5NDk5OTYwMjU0NCwgMC4zMzgxNTc4NTk2OTc0NTg2NSwgMC4zNDMxMzA3NjkzOTg4OTE4NywgMC4zNDgxMDM2NzkxMDAzMjUxLCAwLjM1MzA3NjU4ODgwMTc1ODMsIDAuMzU4MDQ5NDk4NTAzMTkxNSwgMC4zNjMwMjI0MDgyMDQ2MjQ3LCAwLjM2Nzk5NTMxNzkwNjA1Nzk0LCAwLjM3Mjk2ODIyNzYwNzQ5MTEsIDAuMzc3OTQxMTM3MzA4OTI0MywgMC4zODI5MTQwNDcwMTAzNTc1MywgMC4zODc4ODY5NTY3MTE3OTA3NCwgMC4zOTI4NTk4NjY0MTMyMjM5NiwgMC4zOTc4MzI3NzYxMTQ2NTcxNywgMC40MDI4MDU2ODU4MTYwOTA0LCAwLjQwNzc3ODU5NTUxNzUyMzYsIDAuNDEyNzUxNTA1MjE4OTU2OCwgMC40MTc3MjQ0MTQ5MjAzOTAwMywgMC40MjI2OTczMjQ2MjE4MjMyNCwgMC40Mjc2NzAyMzQzMjMyNTY1LCAwLjQzMjY0MzE0NDAyNDY4OTczLCAwLjQzNzYxNjA1MzcyNjEyMjk0LCAwLjQ0MjU4ODk2MzQyNzU1NjE2LCAwLjQ0NzU2MTg3MzEyODk4OTQsIDAuNDUyNTM0NzgyODMwNDIyNiwgMC40NTc1MDc2OTI1MzE4NTU4LCAwLjQ2MjQ4MDYwMjIzMzI4OSwgMC40Njc0NTM1MTE5MzQ3MjIyMywgMC40NzI0MjY0MjE2MzYxNTU0NSwgMC40NzczOTkzMzEzMzc1ODg2NiwgMC40ODIzNzIyNDEwMzkwMjE4LCAwLjQ4NzM0NTE1MDc0MDQ1NTA0LCAwLjQ5MjMxODA2MDQ0MTg4ODI1LCAwLjQ5NzI5MDk3MDE0MzMyMTQ2LCAwLjUwMjI2Mzg3OTg0NDc1NDcsIDAuNTA3MjM2Nzg5NTQ2MTg3OSwgMC41MTIyMDk2OTkyNDc2MjEyLCAwLjUxNzE4MjYwODk0OTA1NDQsIDAuNTIyMTU1NTE4NjUwNDg3NiwgMC41MjcxMjg0MjgzNTE5MjA5LCAwLjUzMjEwMTMzODA1MzM1NCwgMC41MzcwNzQyNDc3NTQ3ODcyLCAwLjU0MjA0NzE1NzQ1NjIyMDUsIDAuNTQ3MDIwMDY3MTU3NjUzNiwgMC41NTE5OTI5NzY4NTkwODY5LCAwLjU1Njk2NTg4NjU2MDUyLCAwLjU2MTkzODc5NjI2MTk1MzMsIDAuNTY2OTExNzA1OTYzMzg2NSwgMC41NzE4ODQ2MTU2NjQ4MTk3LCAwLjU3Njg1NzUyNTM2NjI1MjksIDAuNTgxODMwNDM1MDY3Njg2MiwgMC41ODY4MDMzNDQ3NjkxMTkzLCAwLjU5MTc3NjI1NDQ3MDU1MjYsIDAuNTk2NzQ5MTY0MTcxOTg1OCwgMC42MDE3MjIwNzM4NzM0MTksIDAuNjA2Njk0OTgzNTc0ODUyMiwgMC42MTE2Njc4OTMyNzYyODU1LCAwLjYxNjY0MDgwMjk3NzcxODYsIDAuNjIxNjEzNzEyNjc5MTUxOSwgMC42MjY1ODY2MjIzODA1ODUsIDAuNjMxNTU5NTMyMDgyMDE4MywgMC42MzY1MzI0NDE3ODM0NTE1LCAwLjY0MTUwNTM1MTQ4NDg4NDYsIDAuNjQ2NDc4MjYxMTg2MzE3OSwgMC42NTE0NTExNzA4ODc3NTExLCAwLjY1NjQyNDA4MDU4OTE4NDMsIDAuNjYxMzk2OTkwMjkwNjE3NiwgMC42NjYzNjk4OTk5OTIwNTA5LCAwLjY3MTM0MjgwOTY5MzQ4NCwgMC42NzYzMTU3MTkzOTQ5MTczLCAwLjY4MTI4ODYyOTA5NjM1MDUsIDAuNjg2MjYxNTM4Nzk3NzgzNywgMC42OTEyMzQ0NDg0OTkyMTY5LCAwLjY5NjIwNzM1ODIwMDY1MDIsIDAuNzAxMTgwMjY3OTAyMDgzMywgMC43MDYxNTMxNzc2MDM1MTY2LCAwLjcxMTEyNjA4NzMwNDk0OTcsIDAuNzE2MDk4OTk3MDA2MzgzLCAwLjcyMTA3MTkwNjcwNzgxNjIsIDAuNzI2MDQ0ODE2NDA5MjQ5NCwgMC43MzEwMTc3MjYxMTA2ODI2LCAwLjczNTk5MDYzNTgxMjExNTksIDAuNzQwOTYzNTQ1NTEzNTQ5LCAwLjc0NTkzNjQ1NTIxNDk4MjIsIDAuNzUwOTA5MzY0OTE2NDE1NSwgMC43NTU4ODIyNzQ2MTc4NDg2LCAwLjc2MDg1NTE4NDMxOTI4MTksIDAuNzY1ODI4MDk0MDIwNzE1MSwgMC43NzA4MDEwMDM3MjIxNDgzLCAwLjc3NTc3MzkxMzQyMzU4MTUsIDAuNzgwNzQ2ODIzMTI1MDE0OCwgMC43ODU3MTk3MzI4MjY0NDc5LCAwLjc5MDY5MjY0MjUyNzg4MTIsIDAuNzk1NjY1NTUyMjI5MzE0MywgMC44MDA2Mzg0NjE5MzA3NDc2LCAwLjgwNTYxMTM3MTYzMjE4MDgsIDAuODEwNTg0MjgxMzMzNjE0LCAwLjgxNTU1NzE5MTAzNTA0NzIsIDAuODIwNTMwMTAwNzM2NDgwNSwgMC44MjU1MDMwMTA0Mzc5MTM2LCAwLjgzMDQ3NTkyMDEzOTM0NjksIDAuODM1NDQ4ODI5ODQwNzgwMSwgMC44NDA0MjE3Mzk1NDIyMTMzLCAwLjg0NTM5NDY0OTI0MzY0NjUsIDAuODUwMzY3NTU4OTQ1MDc5OCwgMC44NTUzNDA0Njg2NDY1MTMsIDAuODYwMzEzMzc4MzQ3OTQ2MiwgMC44NjUyODYyODgwNDkzNzk1LCAwLjg3MDI1OTE5Nzc1MDgxMjYsIDAuODc1MjMyMTA3NDUyMjQ1OSwgMC44ODAyMDUwMTcxNTM2NzksIDAuODg1MTc3OTI2ODU1MTEyMywgMC44OTAxNTA4MzY1NTY1NDU1LCAwLjg5NTEyMzc0NjI1Nzk3ODcsIDAuOTAwMDk2NjU1OTU5NDExOSwgMC45MDUwNjk1NjU2NjA4NDUyLCAwLjkxMDA0MjQ3NTM2MjI3ODMsIDAuOTE1MDE1Mzg1MDYzNzExNiwgMC45MTk5ODgyOTQ3NjUxNDQ4LCAwLjkyNDk2MTIwNDQ2NjU3OCwgMC45Mjk5MzQxMTQxNjgwMTEyLCAwLjkzNDkwNzAyMzg2OTQ0NDUsIDAuOTM5ODc5OTMzNTcwODc3NiwgMC45NDQ4NTI4NDMyNzIzMTA5LCAwLjk0OTgyNTc1Mjk3Mzc0NCwgMC45NTQ3OTg2NjI2NzUxNzczLCAwLjk1OTc3MTU3MjM3NjYxMDUsIDAuOTY0NzQ0NDgyMDc4MDQzNiwgMC45Njk3MTczOTE3Nzk0NzY5LCAwLjk3NDY5MDMwMTQ4MDkxMDEsIDAuOTc5NjYzMjExMTgyMzQzMywgMC45ODQ2MzYxMjA4ODM3NzY1LCAwLjk4OTYwOTAzMDU4NTIwOTgsIDAuOTk0NTgxOTQwMjg2NjQyOSwgMC45OTk1NTQ4NDk5ODgwNzYyLCAxLjAwNDUyNzc1OTY4OTUwOTUsIDEuMDA5NTAwNjY5MzkwOTQyNywgMS4wMTQ0NzM1NzkwOTIzNzU4LCAxLjAxOTQ0NjQ4ODc5MzgwOSwgMS4wMjQ0MTkzOTg0OTUyNDIzLCAxLjAyOTM5MjMwODE5NjY3NTYsIDEuMDM0MzY1MjE3ODk4MTA4OSwgMS4wMzkzMzgxMjc1OTk1NDIsIDEuMDQ0MzExMDM3MzAwOTc1MiwgMS4wNDkyODM5NDcwMDI0MDg1LCAxLjA1NDI1Njg1NjcwMzg0MTcsIDEuMDU5MjI5NzY2NDA1Mjc0OCwgMS4wNjQyMDI2NzYxMDY3MDgsIDEuMDY5MTc1NTg1ODA4MTQxMywgMS4wNzQxNDg0OTU1MDk1NzQ0LCAxLjA3OTEyMTQwNTIxMTAwNzYsIDEuMDg0MDk0MzE0OTEyNDQxLCAxLjA4OTA2NzIyNDYxMzg3NDIsIDEuMDk0MDQwMTM0MzE1MzA3MiwgMS4wOTkwMTMwNDQwMTY3NDA1LCAxLjEwMzk4NTk1MzcxODE3MzgsIDEuMTA4OTU4ODYzNDE5NjA3LCAxLjExMzkzMTc3MzEyMTA0LCAxLjExODkwNDY4MjgyMjQ3MzQsIDEuMTIzODc3NTkyNTIzOTA2NiwgMS4xMjg4NTA1MDIyMjUzNCwgMS4xMzM4MjM0MTE5MjY3NzMsIDEuMTM4Nzk2MzIxNjI4MjA2MiwgMS4xNDM3NjkyMzEzMjk2Mzk1LCAxLjE0ODc0MjE0MTAzMTA3MjcsIDEuMTUzNzE1MDUwNzMyNTA1OCwgMS4xNTg2ODc5NjA0MzM5MzksIDEuMTYzNjYwODcwMTM1MzcyMywgMS4xNjg2MzM3Nzk4MzY4MDU0LCAxLjE3MzYwNjY4OTUzODIzODcsIDEuMTc4NTc5NTk5MjM5NjcyLCAxLjE4MzU1MjUwODk0MTEwNTIsIDEuMTg4NTI1NDE4NjQyNTM4MiwgMS4xOTM0OTgzMjgzNDM5NzE1LCAxLjE5ODQ3MTIzODA0NTQwNDgsIDEuMjAzNDQ0MTQ3NzQ2ODM4LCAxLjIwODQxNzA1NzQ0ODI3MSwgMS4yMTMzODk5NjcxNDk3MDQ0LCAxLjIxODM2Mjg3Njg1MTEzNzYsIDEuMjIzMzM1Nzg2NTUyNTcxLCAxLjIyODMwODY5NjI1NDAwNCwgMS4yMzMyODE2MDU5NTU0MzcyLCAxLjIzODI1NDUxNTY1Njg3MDUsIDEuMjQzMjI3NDI1MzU4MzAzOCwgMS4yNDgyMDAzMzUwNTk3MzY4LCAxLjI1MzE3MzI0NDc2MTE3LCAxLjI1ODE0NjE1NDQ2MjYwMzQsIDEuMjYzMTE5MDY0MTY0MDM2NiwgMS4yNjgwOTE5NzM4NjU0Njk3LCAxLjI3MzA2NDg4MzU2NjkwMywgMS4yNzgwMzc3OTMyNjgzMzYyLCAxLjI4MzAxMDcwMjk2OTc2OTMsIDEuMjg3OTgzNjEyNjcxMjAyNSwgMS4yOTI5NTY1MjIzNzI2MzU4LCAxLjI5NzkyOTQzMjA3NDA2OSwgMS4zMDI5MDIzNDE3NzU1MDIxLCAxLjMwNzg3NTI1MTQ3NjkzNTQsIDEuMzEyODQ4MTYxMTc4MzY4NywgMS4zMTc4MjEwNzA4Nzk4MDIyLCAxLjMyMjc5Mzk4MDU4MTIzNTIsIDEuMzI3NzY2ODkwMjgyNjY4NSwgMS4zMzI3Mzk3OTk5ODQxMDE3LCAxLjMzNzcxMjcwOTY4NTUzNSwgMS4zNDI2ODU2MTkzODY5NjgsIDEuMzQ3NjU4NTI5MDg4NDAxMywgMS4zNTI2MzE0Mzg3ODk4MzQ2LCAxLjM1NzYwNDM0ODQ5MTI2NzksIDEuMzYyNTc3MjU4MTkyNzAxLCAxLjM2NzU1MDE2Nzg5NDEzNDIsIDEuMzcyNTIzMDc3NTk1NTY3NSwgMS4zNzc0OTU5ODcyOTcwMDA3LCAxLjM4MjQ2ODg5Njk5ODQzMzgsIDEuMzg3NDQxODA2Njk5ODY3LCAxLjM5MjQxNDcxNjQwMTMwMDMsIDEuMzk3Mzg3NjI2MTAyNzMzNCwgMS40MDIzNjA1MzU4MDQxNjY2LCAxLjQwNzMzMzQ0NTUwNTYsIDEuNDEyMzA2MzU1MjA3MDMzMiwgMS40MTcyNzkyNjQ5MDg0NjYyLCAxLjQyMjI1MjE3NDYwOTg5OTUsIDEuNDI3MjI1MDg0MzExMzMyOCwgMS40MzIxOTc5OTQwMTI3NjYsIDEuNDM3MTcwOTAzNzE0MTk5LCAxLjQ0MjE0MzgxMzQxNTYzMjQsIDEuNDQ3MTE2NzIzMTE3MDY1NiwgMS40NTIwODk2MzI4MTg0OTksIDEuNDU3MDYyNTQyNTE5OTMyLCAxLjQ2MjAzNTQ1MjIyMTM2NTIsIDEuNDY3MDA4MzYxOTIyNzk4NSwgMS40NzE5ODEyNzE2MjQyMzE4LCAxLjQ3Njk1NDE4MTMyNTY2NDgsIDEuNDgxOTI3MDkxMDI3MDk4LCAxLjQ4NjkwMDAwMDcyODUzMTMsIDEuNDkxODcyOTEwNDI5OTY0NCwgMS40OTY4NDU4MjAxMzEzOTc3LCAxLjUwMTgxODcyOTgzMjgzMSwgMS41MDY3OTE2Mzk1MzQyNjQyLCAxLjUxMTc2NDU0OTIzNTY5NzMsIDEuNTE2NzM3NDU4OTM3MTMwNSwgMS41MjE3MTAzNjg2Mzg1NjM4LCAxLjUyNjY4MzI3ODMzOTk5NywgMS41MzE2NTYxODgwNDE0MzAxLCAxLjUzNjYyOTA5Nzc0Mjg2MzQsIDEuNTQxNjAyMDA3NDQ0Mjk2NywgMS41NDY1NzQ5MTcxNDU3MywgMS41NTE1NDc4MjY4NDcxNjMsIDEuNTU2NTIwNzM2NTQ4NTk2MiwgMS41NjE0OTM2NDYyNTAwMjk1LCAxLjU2NjQ2NjU1NTk1MTQ2MjgsIDEuNTcxNDM5NDY1NjUyODk1OCwgMS41NzY0MTIzNzUzNTQzMjksIDEuNTgxMzg1Mjg1MDU1NzYyNCwgMS41ODYzNTgxOTQ3NTcxOTU2LCAxLjU5MTMzMTEwNDQ1ODYyODcsIDEuNTk2MzA0MDE0MTYwMDYyLCAxLjYwMTI3NjkyMzg2MTQ5NTIsIDEuNjA2MjQ5ODMzNTYyOTI4MywgMS42MTEyMjI3NDMyNjQzNjE1LCAxLjYxNjE5NTY1Mjk2NTc5NDgsIDEuNjIxMTY4NTYyNjY3MjI4LCAxLjYyNjE0MTQ3MjM2ODY2MTEsIDEuNjMxMTE0MzgyMDcwMDk0NCwgMS42MzYwODcyOTE3NzE1Mjc3LCAxLjY0MTA2MDIwMTQ3Mjk2MSwgMS42NDYwMzMxMTExNzQzOTQsIDEuNjUxMDA2MDIwODc1ODI3MywgMS42NTU5Nzg5MzA1NzcyNjA1LCAxLjY2MDk1MTg0MDI3ODY5MzgsIDEuNjY1OTI0NzQ5OTgwMTI2OSwgMS42NzA4OTc2NTk2ODE1NjAxLCAxLjY3NTg3MDU2OTM4Mjk5MzQsIDEuNjgwODQzNDc5MDg0NDI2NywgMS42ODU4MTYzODg3ODU4NTk3LCAxLjY5MDc4OTI5ODQ4NzI5MywgMS42OTU3NjIyMDgxODg3MjY1LCAxLjcwMDczNTExNzg5MDE1OTUsIDEuNzA1NzA4MDI3NTkxNTkyOCwgMS43MTA2ODA5MzcyOTMwMjYsIDEuNzE1NjUzODQ2OTk0NDU5MywgMS43MjA2MjY3NTY2OTU4OTI0LCAxLjcyNTU5OTY2NjM5NzMyNTYsIDEuNzMwNTcyNTc2MDk4NzU5LCAxLjczNTU0NTQ4NTgwMDE5MjIsIDEuNzQwNTE4Mzk1NTAxNjI1MiwgMS43NDU0OTEzMDUyMDMwNTg1LCAxLjc1MDQ2NDIxNDkwNDQ5MTgsIDEuNzU1NDM3MTI0NjA1OTI1LCAxLjc2MDQxMDAzNDMwNzM1OCwgMS43NjUzODI5NDQwMDg3OTE0LCAxLjc3MDM1NTg1MzcxMDIyNDYsIDEuNzc1MzI4NzYzNDExNjU4LCAxLjc4MDMwMTY3MzExMzA5MSwgMS43ODUyNzQ1ODI4MTQ1MjQyLCAxLjc5MDI0NzQ5MjUxNTk1NzUsIDEuNzk1MjIwNDAyMjE3MzkwOCwgMS44MDAxOTMzMTE5MTg4MjM4LCAxLjgwNTE2NjIyMTYyMDI1NywgMS44MTAxMzkxMzEzMjE2OTA0LCAxLjgxNTExMjA0MTAyMzEyMzQsIDEuODIwMDg0OTUwNzI0NTU2NywgMS44MjUwNTc4NjA0MjU5OSwgMS44MzAwMzA3NzAxMjc0MjMyLCAxLjgzNTAwMzY3OTgyODg1NjMsIDEuODM5OTc2NTg5NTMwMjg5NSwgMS44NDQ5NDk0OTkyMzE3MjI4LCAxLjg0OTkyMjQwODkzMzE1NiwgMS44NTQ4OTUzMTg2MzQ1ODkxLCAxLjg1OTg2ODIyODMzNjAyMjQsIDEuODY0ODQxMTM4MDM3NDU1NywgMS44Njk4MTQwNDc3Mzg4ODksIDEuODc0Nzg2OTU3NDQwMzIyLCAxLjg3OTc1OTg2NzE0MTc1NTIsIDEuODg0NzMyNzc2ODQzMTg4NSwgMS44ODk3MDU2ODY1NDQ2MjE4LCAxLjg5NDY3ODU5NjI0NjA1NDgsIDEuODk5NjUxNTA1OTQ3NDg4LCAxLjkwNDYyNDQxNTY0ODkyMTQsIDEuOTA5NTk3MzI1MzUwMzU0NiwgMS45MTQ1NzAyMzUwNTE3ODc3LCAxLjkxOTU0MzE0NDc1MzIyMSwgMS45MjQ1MTYwNTQ0NTQ2NTQyLCAxLjkyOTQ4ODk2NDE1NjA4NzMsIDEuOTM0NDYxODczODU3NTIwNiwgMS45Mzk0MzQ3ODM1NTg5NTM4LCAxLjk0NDQwNzY5MzI2MDM4NywgMS45NDkzODA2MDI5NjE4MjAxLCAxLjk1NDM1MzUxMjY2MzI1MzQsIDEuOTU5MzI2NDIyMzY0Njg2NywgMS45NjQyOTkzMzIwNjYxMiwgMS45NjkyNzIyNDE3Njc1NTMsIDEuOTc0MjQ1MTUxNDY4OTg2MywgMS45NzkyMTgwNjExNzA0MTk1LCAxLjk4NDE5MDk3MDg3MTg1MjgsIDEuOTg5MTYzODgwNTczMjg1OSwgMS45OTQxMzY3OTAyNzQ3MTkxLCAxLjk5OTEwOTY5OTk3NjE1MjQsIDIuMDA0MDgyNjA5Njc3NTg1NywgMi4wMDkwNTU1MTkzNzkwMTksIDIuMDE0MDI4NDI5MDgwNDUyLCAyLjAxOTAwMTMzODc4MTg4NTUsIDIuMDIzOTc0MjQ4NDgzMzE4MywgMi4wMjg5NDcxNTgxODQ3NTE2LCAyLjAzMzkyMDA2Nzg4NjE4NSwgMi4wMzg4OTI5Nzc1ODc2MTgsIDIuMDQzODY1ODg3Mjg5MDUxNCwgMi4wNDg4Mzg3OTY5OTA0ODQ3LCAyLjA1MzgxMTcwNjY5MTkxNzUsIDIuMDU4Nzg0NjE2MzkzMzUxLCAyLjA2Mzc1NzUyNjA5NDc4NCwgMi4wNjg3MzA0MzU3OTYyMTc3LCAyLjA3MzcwMzM0NTQ5NzY1MDYsIDIuMDc4Njc2MjU1MTk5MDg0LCAyLjA4MzY0OTE2NDkwMDUxNywgMi4wODg2MjIwNzQ2MDE5NTA0LCAyLjA5MzU5NDk4NDMwMzM4MywgMi4wOTg1Njc4OTQwMDQ4MTcsIDIuMTAzNTQwODAzNzA2MjQ5NywgMi4xMDg1MTM3MTM0MDc2ODM1LCAyLjExMzQ4NjYyMzEwOTExNjMsIDIuMTE4NDU5NTMyODEwNTQ5NiwgMi4xMjM0MzI0NDI1MTE5ODMsIDIuMTI4NDA1MzUyMjEzNDE2LCAyLjEzMzM3ODI2MTkxNDg0OSwgMi4xMzgzNTExNzE2MTYyODI2LCAyLjE0MzMyNDA4MTMxNzcxNTUsIDIuMTQ4Mjk2OTkxMDE5MTQ4NywgMi4xNTMyNjk5MDA3MjA1ODIsIDIuMTU4MjQyODEwNDIyMDE1MywgMi4xNjMyMTU3MjAxMjM0NDg1LCAyLjE2ODE4ODYyOTgyNDg4MiwgMi4xNzMxNjE1Mzk1MjYzMTQ2LCAyLjE3ODEzNDQ0OTIyNzc0ODMsIDIuMTgzMTA3MzU4OTI5MTgxLCAyLjE4ODA4MDI2ODYzMDYxNDQsIDIuMTkzMDUzMTc4MzMyMDQ3NywgMi4xOTgwMjYwODgwMzM0ODEsIDIuMjAyOTk4OTk3NzM0OTE0MywgMi4yMDc5NzE5MDc0MzYzNDc1LCAyLjIxMjk0NDgxNzEzNzc4MDQsIDIuMjE3OTE3NzI2ODM5MjE0LCAyLjIyMjg5MDYzNjU0MDY0NywgMi4yMjc4NjM1NDYyNDIwOCwgMi4yMzI4MzY0NTU5NDM1MTM0LCAyLjIzNzgwOTM2NTY0NDk0NjcsIDIuMjQyNzgyMjc1MzQ2Mzc5NSwgMi4yNDc3NTUxODUwNDc4MTMyLCAyLjI1MjcyODA5NDc0OTI0NiwgMi4yNTc3MDEwMDQ0NTA2OCwgMi4yNjI2NzM5MTQxNTIxMTMsIDIuMjY3NjQ2ODIzODUzNTQ2LCAyLjI3MjYxOTczMzU1NDk3OTYsIDIuMjc3NTkyNjQzMjU2NDEyNCwgMi4yODI1NjU1NTI5NTc4NDU3LCAyLjI4NzUzODQ2MjY1OTI3OSwgMi4yOTI1MTEzNzIzNjA3MTIyLCAyLjI5NzQ4NDI4MjA2MjE0NTUsIDIuMzAyNDU3MTkxNzYzNTc4OCwgMi4zMDc0MzAxMDE0NjUwMTE2LCAyLjMxMjQwMzAxMTE2NjQ0NTMsIDIuMzE3Mzc1OTIwODY3ODc4LCAyLjMyMjM0ODgzMDU2OTMxMTQsIDIuMzI3MzIxNzQwMjcwNzQ0NywgMi4zMzIyOTQ2NDk5NzIxNzgsIDIuMzM3MjY3NTU5NjczNjEwOCwgMi4zNDIyNDA0NjkzNzUwNDQ1LCAyLjM0NzIxMzM3OTA3NjQ3NzMsIDIuMzUyMTg2Mjg4Nzc3OTExLCAyLjM1NzE1OTE5ODQ3OTM0NCwgMi4zNjIxMzIxMDgxODA3NzcsIDIuMzY3MTA1MDE3ODgyMjEwNCwgMi4zNzIwNzc5Mjc1ODM2NDM3LCAyLjM3NzA1MDgzNzI4NTA3NjUsIDIuMzgyMDIzNzQ2OTg2NTEsIDIuMzg2OTk2NjU2Njg3OTQzLCAyLjM5MTk2OTU2NjM4OTM3NjcsIDIuMzk2OTQyNDc2MDkwODA5NiwgMi40MDE5MTUzODU3OTIyNDMsIDIuNDA2ODg4Mjk1NDkzNjc2LCAyLjQxMTg2MTIwNTE5NTEwOTQsIDIuNDE2ODM0MTE0ODk2NTQyLCAyLjQyMTgwNzAyNDU5Nzk3NiwgMi40MjY3Nzk5MzQyOTk0MDg3LCAyLjQzMTc1Mjg0NDAwMDg0MjUsIDIuNDM2NzI1NzUzNzAyMjc1MywgMi40NDE2OTg2NjM0MDM3MDg2LCAyLjQ0NjY3MTU3MzEwNTE0MiwgMi40NTE2NDQ0ODI4MDY1NzUsIDIuNDU2NjE3MzkyNTA4MDA4LCAyLjQ2MTU5MDMwMjIwOTQ0MTYsIDIuNDY2NTYzMjExOTEwODc0NSwgMi40NzE1MzYxMjE2MTIzMDc3LCAyLjQ3NjUwOTAzMTMxMzc0MSwgMi40ODE0ODE5NDEwMTUxNzQzXSkKICAgICAgICAgICAgICAucmFuZ2UoWycjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnXSk7CiAgICAKCiAgICBjb2xvcl9tYXBfYWM1MjU5NDIwMDE3NDM2NDk2Mzg5NTFhMjE1Y2Y5MTQueCA9IGQzLnNjYWxlLmxpbmVhcigpCiAgICAgICAgICAgICAgLmRvbWFpbihbMC4wLCAyLjQ4MTQ4MTk0MTAxNTE3NDNdKQogICAgICAgICAgICAgIC5yYW5nZShbMCwgNDAwXSk7CgogICAgY29sb3JfbWFwX2FjNTI1OTQyMDAxNzQzNjQ5NjM4OTUxYTIxNWNmOTE0LmxlZ2VuZCA9IEwuY29udHJvbCh7cG9zaXRpb246ICd0b3ByaWdodCd9KTsKICAgIGNvbG9yX21hcF9hYzUyNTk0MjAwMTc0MzY0OTYzODk1MWEyMTVjZjkxNC5sZWdlbmQub25BZGQgPSBmdW5jdGlvbiAobWFwKSB7dmFyIGRpdiA9IEwuRG9tVXRpbC5jcmVhdGUoJ2RpdicsICdsZWdlbmQnKTsgcmV0dXJuIGRpdn07CiAgICBjb2xvcl9tYXBfYWM1MjU5NDIwMDE3NDM2NDk2Mzg5NTFhMjE1Y2Y5MTQubGVnZW5kLmFkZFRvKG1hcF8yZDA3YzI1NTk4OTg0Njk3ODU1MjZmNjNkYmYwMzA4MCk7CgogICAgY29sb3JfbWFwX2FjNTI1OTQyMDAxNzQzNjQ5NjM4OTUxYTIxNWNmOTE0LnhBeGlzID0gZDMuc3ZnLmF4aXMoKQogICAgICAgIC5zY2FsZShjb2xvcl9tYXBfYWM1MjU5NDIwMDE3NDM2NDk2Mzg5NTFhMjE1Y2Y5MTQueCkKICAgICAgICAub3JpZW50KCJ0b3AiKQogICAgICAgIC50aWNrU2l6ZSgxKQogICAgICAgIC50aWNrVmFsdWVzKFswLjAsIDAuNDEzNTgwMzIzNTAyNTI5MDUsIDAuODI3MTYwNjQ3MDA1MDU4MSwgMS4yNDA3NDA5NzA1MDc1ODcxLCAxLjY1NDMyMTI5NDAxMDExNjIsIDIuMDY3OTAxNjE3NTEyNjQ1NSwgMi40ODE0ODE5NDEwMTUxNzQzXSk7CgogICAgY29sb3JfbWFwX2FjNTI1OTQyMDAxNzQzNjQ5NjM4OTUxYTIxNWNmOTE0LnN2ZyA9IGQzLnNlbGVjdCgiLmxlZ2VuZC5sZWFmbGV0LWNvbnRyb2wiKS5hcHBlbmQoInN2ZyIpCiAgICAgICAgLmF0dHIoImlkIiwgJ2xlZ2VuZCcpCiAgICAgICAgLmF0dHIoIndpZHRoIiwgNDUwKQogICAgICAgIC5hdHRyKCJoZWlnaHQiLCA0MCk7CgogICAgY29sb3JfbWFwX2FjNTI1OTQyMDAxNzQzNjQ5NjM4OTUxYTIxNWNmOTE0LmcgPSBjb2xvcl9tYXBfYWM1MjU5NDIwMDE3NDM2NDk2Mzg5NTFhMjE1Y2Y5MTQuc3ZnLmFwcGVuZCgiZyIpCiAgICAgICAgLmF0dHIoImNsYXNzIiwgImtleSIpCiAgICAgICAgLmF0dHIoInRyYW5zZm9ybSIsICJ0cmFuc2xhdGUoMjUsMTYpIik7CgogICAgY29sb3JfbWFwX2FjNTI1OTQyMDAxNzQzNjQ5NjM4OTUxYTIxNWNmOTE0Lmcuc2VsZWN0QWxsKCJyZWN0IikKICAgICAgICAuZGF0YShjb2xvcl9tYXBfYWM1MjU5NDIwMDE3NDM2NDk2Mzg5NTFhMjE1Y2Y5MTQuY29sb3IucmFuZ2UoKS5tYXAoZnVuY3Rpb24oZCwgaSkgewogICAgICAgICAgcmV0dXJuIHsKICAgICAgICAgICAgeDA6IGkgPyBjb2xvcl9tYXBfYWM1MjU5NDIwMDE3NDM2NDk2Mzg5NTFhMjE1Y2Y5MTQueChjb2xvcl9tYXBfYWM1MjU5NDIwMDE3NDM2NDk2Mzg5NTFhMjE1Y2Y5MTQuY29sb3IuZG9tYWluKClbaSAtIDFdKSA6IGNvbG9yX21hcF9hYzUyNTk0MjAwMTc0MzY0OTYzODk1MWEyMTVjZjkxNC54LnJhbmdlKClbMF0sCiAgICAgICAgICAgIHgxOiBpIDwgY29sb3JfbWFwX2FjNTI1OTQyMDAxNzQzNjQ5NjM4OTUxYTIxNWNmOTE0LmNvbG9yLmRvbWFpbigpLmxlbmd0aCA/IGNvbG9yX21hcF9hYzUyNTk0MjAwMTc0MzY0OTYzODk1MWEyMTVjZjkxNC54KGNvbG9yX21hcF9hYzUyNTk0MjAwMTc0MzY0OTYzODk1MWEyMTVjZjkxNC5jb2xvci5kb21haW4oKVtpXSkgOiBjb2xvcl9tYXBfYWM1MjU5NDIwMDE3NDM2NDk2Mzg5NTFhMjE1Y2Y5MTQueC5yYW5nZSgpWzFdLAogICAgICAgICAgICB6OiBkCiAgICAgICAgICB9OwogICAgICAgIH0pKQogICAgICAuZW50ZXIoKS5hcHBlbmQoInJlY3QiKQogICAgICAgIC5hdHRyKCJoZWlnaHQiLCAxMCkKICAgICAgICAuYXR0cigieCIsIGZ1bmN0aW9uKGQpIHsgcmV0dXJuIGQueDA7IH0pCiAgICAgICAgLmF0dHIoIndpZHRoIiwgZnVuY3Rpb24oZCkgeyByZXR1cm4gZC54MSAtIGQueDA7IH0pCiAgICAgICAgLnN0eWxlKCJmaWxsIiwgZnVuY3Rpb24oZCkgeyByZXR1cm4gZC56OyB9KTsKCiAgICBjb2xvcl9tYXBfYWM1MjU5NDIwMDE3NDM2NDk2Mzg5NTFhMjE1Y2Y5MTQuZy5jYWxsKGNvbG9yX21hcF9hYzUyNTk0MjAwMTc0MzY0OTYzODk1MWEyMTVjZjkxNC54QXhpcykuYXBwZW5kKCJ0ZXh0IikKICAgICAgICAuYXR0cigiY2xhc3MiLCAiY2FwdGlvbiIpCiAgICAgICAgLmF0dHIoInkiLCAyMSkKICAgICAgICAudGV4dCgnJyk7Cjwvc2NyaXB0Pg==" style="position:absolute;width:100%;height:100%;left:0;top:0;border:none !important;" allowfullscreen webkitallowfullscreen mozallowfullscreen></iframe></div></div>




```python
tmp_criminal = crime_anal_norm['범죄'] /  crime_anal_norm['인구수'] * 1000000

map = folium.Map(location=[37.5502, 126.982], zoom_start=11, 
                 tiles='Stamen Toner')

map.choropleth(geo_data = geo_str,
               data = tmp_criminal,
               columns = [crime_anal.index, tmp_criminal],
               fill_color = 'PuRd', #PuRd, YlGnBu
               key_on = 'feature.id')
map
```

    C:\ProgramData\Anaconda3\lib\site-packages\folium\folium.py:432: FutureWarning: The choropleth  method has been deprecated. Instead use the new Choropleth class, which has the same arguments. See the example notebook 'GeoJSON_and_choropleth' for how to do this.
      FutureWarning
    




<div style="width:100%;"><div style="position:relative;width:100%;height:0;padding-bottom:60%;"><iframe src="data:text/html;charset=utf-8;base64,PCFET0NUWVBFIGh0bWw+CjxoZWFkPiAgICAKICAgIDxtZXRhIGh0dHAtZXF1aXY9ImNvbnRlbnQtdHlwZSIgY29udGVudD0idGV4dC9odG1sOyBjaGFyc2V0PVVURi04IiAvPgogICAgPHNjcmlwdD5MX1BSRUZFUl9DQU5WQVM9ZmFsc2U7IExfTk9fVE9VQ0g9ZmFsc2U7IExfRElTQUJMRV8zRD1mYWxzZTs8L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmpzIj48L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2FqYXguZ29vZ2xlYXBpcy5jb20vYWpheC9saWJzL2pxdWVyeS8xLjExLjEvanF1ZXJ5Lm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvanMvYm9vdHN0cmFwLm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9jZG5qcy5jbG91ZGZsYXJlLmNvbS9hamF4L2xpYnMvTGVhZmxldC5hd2Vzb21lLW1hcmtlcnMvMi4wLjIvbGVhZmxldC5hd2Vzb21lLW1hcmtlcnMuanMiPjwvc2NyaXB0PgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL21heGNkbi5ib290c3RyYXBjZG4uY29tL2Jvb3RzdHJhcC8zLjIuMC9jc3MvYm9vdHN0cmFwLm1pbi5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvY3NzL2Jvb3RzdHJhcC10aGVtZS5taW4uY3NzIi8+CiAgICA8bGluayByZWw9InN0eWxlc2hlZXQiIGhyZWY9Imh0dHBzOi8vbWF4Y2RuLmJvb3RzdHJhcGNkbi5jb20vZm9udC1hd2Vzb21lLzQuNi4zL2Nzcy9mb250LWF3ZXNvbWUubWluLmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2NkbmpzLmNsb3VkZmxhcmUuY29tL2FqYXgvbGlicy9MZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy8yLjAuMi9sZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9yYXdjZG4uZ2l0aGFjay5jb20vcHl0aG9uLXZpc3VhbGl6YXRpb24vZm9saXVtL21hc3Rlci9mb2xpdW0vdGVtcGxhdGVzL2xlYWZsZXQuYXdlc29tZS5yb3RhdGUuY3NzIi8+CiAgICA8c3R5bGU+aHRtbCwgYm9keSB7d2lkdGg6IDEwMCU7aGVpZ2h0OiAxMDAlO21hcmdpbjogMDtwYWRkaW5nOiAwO308L3N0eWxlPgogICAgPHN0eWxlPiNtYXAge3Bvc2l0aW9uOmFic29sdXRlO3RvcDowO2JvdHRvbTowO3JpZ2h0OjA7bGVmdDowO308L3N0eWxlPgogICAgCiAgICA8bWV0YSBuYW1lPSJ2aWV3cG9ydCIgY29udGVudD0id2lkdGg9ZGV2aWNlLXdpZHRoLAogICAgICAgIGluaXRpYWwtc2NhbGU9MS4wLCBtYXhpbXVtLXNjYWxlPTEuMCwgdXNlci1zY2FsYWJsZT1ubyIgLz4KICAgIDxzdHlsZT4jbWFwXzhhZTU3MTJlNDdhYjQ2NDliOGFiOTQ0ODM5YjA0NjIyIHsKICAgICAgICBwb3NpdGlvbjogcmVsYXRpdmU7CiAgICAgICAgd2lkdGg6IDEwMC4wJTsKICAgICAgICBoZWlnaHQ6IDEwMC4wJTsKICAgICAgICBsZWZ0OiAwLjAlOwogICAgICAgIHRvcDogMC4wJTsKICAgICAgICB9CiAgICA8L3N0eWxlPgogICAgPHNjcmlwdCBzcmM9Imh0dHBzOi8vY2RuanMuY2xvdWRmbGFyZS5jb20vYWpheC9saWJzL2QzLzMuNS41L2QzLm1pbi5qcyI+PC9zY3JpcHQ+CjwvaGVhZD4KPGJvZHk+ICAgIAogICAgCiAgICA8ZGl2IGNsYXNzPSJmb2xpdW0tbWFwIiBpZD0ibWFwXzhhZTU3MTJlNDdhYjQ2NDliOGFiOTQ0ODM5YjA0NjIyIiA+PC9kaXY+CjwvYm9keT4KPHNjcmlwdD4gICAgCiAgICAKICAgIAogICAgICAgIHZhciBib3VuZHMgPSBudWxsOwogICAgCgogICAgdmFyIG1hcF84YWU1NzEyZTQ3YWI0NjQ5YjhhYjk0NDgzOWIwNDYyMiA9IEwubWFwKAogICAgICAgICdtYXBfOGFlNTcxMmU0N2FiNDY0OWI4YWI5NDQ4MzliMDQ2MjInLCB7CiAgICAgICAgY2VudGVyOiBbMzcuNTUwMiwgMTI2Ljk4Ml0sCiAgICAgICAgem9vbTogMTEsCiAgICAgICAgbWF4Qm91bmRzOiBib3VuZHMsCiAgICAgICAgbGF5ZXJzOiBbXSwKICAgICAgICB3b3JsZENvcHlKdW1wOiBmYWxzZSwKICAgICAgICBjcnM6IEwuQ1JTLkVQU0czODU3LAogICAgICAgIHpvb21Db250cm9sOiB0cnVlLAogICAgICAgIH0pOwoKICAgIAogICAgCiAgICB2YXIgdGlsZV9sYXllcl84NDE4MTcxZTdlMjU0N2ZiYWMyOGQ2ZDM5OWQ0MjRkNiA9IEwudGlsZUxheWVyKAogICAgICAgICdodHRwczovL3N0YW1lbi10aWxlcy17c30uYS5zc2wuZmFzdGx5Lm5ldC90b25lci97en0ve3h9L3t5fS5wbmcnLAogICAgICAgIHsKICAgICAgICAiYXR0cmlidXRpb24iOiBudWxsLAogICAgICAgICJkZXRlY3RSZXRpbmEiOiBmYWxzZSwKICAgICAgICAibWF4TmF0aXZlWm9vbSI6IDE4LAogICAgICAgICJtYXhab29tIjogMTgsCiAgICAgICAgIm1pblpvb20iOiAwLAogICAgICAgICJub1dyYXAiOiBmYWxzZSwKICAgICAgICAib3BhY2l0eSI6IDEsCiAgICAgICAgInN1YmRvbWFpbnMiOiAiYWJjIiwKICAgICAgICAidG1zIjogZmFsc2UKfSkuYWRkVG8obWFwXzhhZTU3MTJlNDdhYjQ2NDliOGFiOTQ0ODM5YjA0NjIyKTsKICAgIAogICAgICAgICAgICB2YXIgY2hvcm9wbGV0aF8wNjFiNmY3MzU2MTM0ZGI1ODI0NjhmZTQ2ODM1OGI5OSA9IEwuZmVhdHVyZUdyb3VwKAogICAgICAgICAgICAgICAgKS5hZGRUbyhtYXBfOGFlNTcxMmU0N2FiNDY0OWI4YWI5NDQ4MzliMDQ2MjIpOwogICAgICAgIAogICAgCiAgICAgICAgCiAgICAgICAgdmFyIGdlb19qc29uXzJhNzIzOTE0NjRlZDQzODZiYWJkMjJkMTQ3NDE4N2QxID0gTC5nZW9Kc29uKAogICAgICAgICAgICB7ImZlYXR1cmVzIjogW3siZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI3LjExNTE5NTg0OTgxNjA2LCAzNy41NTc1MzMxODA3MDQ5MTVdLCBbMTI3LjE2NjgzMTg0MzY2MTI5LCAzNy41NzY3MjQ4NzM4ODYyN10sIFsxMjcuMTg0MDg3OTIzMzAxNTIsIDM3LjU1ODE0MjgwMzY5NTc1XSwgWzEyNy4xNjUzMDk4NDMwNzQ0NywgMzcuNTQyMjE4NTEyNTg2OTNdLCBbMTI3LjE0NjcyODA2ODIzNTAyLCAzNy41MTQxNTY4MDY4MDI5MV0sIFsxMjcuMTIxMjMxNjU3MTk2MTUsIDM3LjUyNTI4MjcwMDg5XSwgWzEyNy4xMTE2NzY0MjAzNjA4LCAzNy41NDA2Njk5NTUzMjQ5NjVdLCBbMTI3LjExNTE5NTg0OTgxNjA2LCAzNy41NTc1MzMxODA3MDQ5MTVdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YWMxNVx1YjNkOVx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMjUwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWFjMTVcdWIzZDlcdWFkNmMiLCAibmFtZV9lbmciOiAiR2FuZ2RvbmctZ3UiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICIjZDRiOWRhIiwgImZpbGxPcGFjaXR5IjogMC42LCAib3BhY2l0eSI6IDEsICJ3ZWlnaHQiOiAxfX0sICJ0eXBlIjogIkZlYXR1cmUifSwgeyJnZW9tZXRyeSI6IHsiY29vcmRpbmF0ZXMiOiBbW1sxMjcuMDY5MDY5ODEzMDM3MiwgMzcuNTIyMjc5NDIzNTA1MDI2XSwgWzEyNy4xMDA4NzUxOTc5MTk2MiwgMzcuNTI0ODQxMjIwMTY3MDU1XSwgWzEyNy4xMTE2NzY0MjAzNjA4LCAzNy41NDA2Njk5NTUzMjQ5NjVdLCBbMTI3LjEyMTIzMTY1NzE5NjE1LCAzNy41MjUyODI3MDA4OV0sIFsxMjcuMTQ2NzI4MDY4MjM1MDIsIDM3LjUxNDE1NjgwNjgwMjkxXSwgWzEyNy4xNjM0OTQ0MjE1NzY1LCAzNy40OTc0NDU0MDYwOTc0ODRdLCBbMTI3LjE0MjA2MDU4NDEzMjc0LCAzNy40NzA4OTgxOTA5ODUwMV0sIFsxMjcuMTI0NDA1NzEwODA4OTMsIDM3LjQ2MjQwNDQ1NTg3MDQ4XSwgWzEyNy4xMTExNzA4NTIwMTIzOCwgMzcuNDg1NzA4MzgxNTEyNDQ1XSwgWzEyNy4wNzE5MTQ2MDAwNzI0LCAzNy41MDIyNDAxMzU4NzY2OV0sIFsxMjcuMDY5MDY5ODEzMDM3MiwgMzcuNTIyMjc5NDIzNTA1MDI2XV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJcdWMxYTFcdWQzMGNcdWFkNmMiLCAicHJvcGVydGllcyI6IHsiYmFzZV95ZWFyIjogIjIwMTMiLCAiY29kZSI6ICIxMTI0MCIsICJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiXHVjMWExXHVkMzBjXHVhZDZjIiwgIm5hbWVfZW5nIjogIlNvbmdwYS1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNjOTk0YzciLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wNTg2NzM1OTI4ODM5OCwgMzcuNTI2Mjk5NzQ5MjI1NjhdLCBbMTI3LjA2OTA2OTgxMzAzNzIsIDM3LjUyMjI3OTQyMzUwNTAyNl0sIFsxMjcuMDcxOTE0NjAwMDcyNCwgMzcuNTAyMjQwMTM1ODc2NjldLCBbMTI3LjExMTE3MDg1MjAxMjM4LCAzNy40ODU3MDgzODE1MTI0NDVdLCBbMTI3LjEyNDQwNTcxMDgwODkzLCAzNy40NjI0MDQ0NTU4NzA0OF0sIFsxMjcuMDk4NDI3NTkzMTg3NTEsIDM3LjQ1ODYyMjUzODU3NDYxXSwgWzEyNy4wODY0MDQ0MDU3ODE1NiwgMzcuNDcyNjk3OTM1MTg0NjU1XSwgWzEyNy4wNTU5MTcwNDgxOTA0LCAzNy40NjU5MjI4OTE0MDc3XSwgWzEyNy4wMzYyMTkxNTA5ODc5OCwgMzcuNDgxNzU4MDI0Mjc2MDNdLCBbMTI3LjAxMzk3MTE5NjY3NTEzLCAzNy41MjUwMzk4ODI4OTY2OV0sIFsxMjcuMDIzMDI4MzE4OTA1NTksIDM3LjUzMjMxODk5NTgyNjYzXSwgWzEyNy4wNTg2NzM1OTI4ODM5OCwgMzcuNTI2Mjk5NzQ5MjI1NjhdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YWMxNVx1YjBhOFx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMjMwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWFjMTVcdWIwYThcdWFkNmMiLCAibmFtZV9lbmciOiAiR2FuZ25hbS1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNkZDFjNzciLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wMTM5NzExOTY2NzUxMywgMzcuNTI1MDM5ODgyODk2NjldLCBbMTI3LjAzNjIxOTE1MDk4Nzk4LCAzNy40ODE3NTgwMjQyNzYwM10sIFsxMjcuMDU1OTE3MDQ4MTkwNCwgMzcuNDY1OTIyODkxNDA3N10sIFsxMjcuMDg2NDA0NDA1NzgxNTYsIDM3LjQ3MjY5NzkzNTE4NDY1NV0sIFsxMjcuMDk4NDI3NTkzMTg3NTEsIDM3LjQ1ODYyMjUzODU3NDYxXSwgWzEyNy4wOTA0NjkyODU2NTk1MSwgMzcuNDQyOTY4MjYxMTQxODVdLCBbMTI3LjA2Nzc4MTA3NjA1NDMzLCAzNy40MjYxOTc0MjQwNTczMTRdLCBbMTI3LjA0OTU3MjMyOTg3MTQyLCAzNy40MjgwNTgzNjg0NTY5NF0sIFsxMjcuMDM4ODE3ODI1OTc5MjIsIDM3LjQ1MzgyMDM5ODUxNzE1XSwgWzEyNi45OTA3MjA3MzE5NTQ2MiwgMzcuNDU1MzI2MTQzMzEwMDI1XSwgWzEyNi45ODM2NzY2ODI5MTgwMiwgMzcuNDczODU2NDkyNjkyMDg2XSwgWzEyNi45ODIyMzgwNzkxNjA4MSwgMzcuNTA5MzE0OTY2NzcwMzI2XSwgWzEyNy4wMTM5NzExOTY2NzUxMywgMzcuNTI1MDM5ODgyODk2NjldXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzExY1x1Y2QwOFx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMjIwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWMxMWNcdWNkMDhcdWFkNmMiLCAibmFtZV9lbmciOiAiU2VvY2hvLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2M5OTRjNyIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2Ljk4MzY3NjY4MjkxODAyLCAzNy40NzM4NTY0OTI2OTIwODZdLCBbMTI2Ljk5MDcyMDczMTk1NDYyLCAzNy40NTUzMjYxNDMzMTAwMjVdLCBbMTI2Ljk2NTIwNDM5MDg1MTQzLCAzNy40MzgyNDk3ODQwMDYyNDZdLCBbMTI2Ljk1MDAwMDAxMDEwMTgyLCAzNy40MzYxMzQ1MTE2NTcxOV0sIFsxMjYuOTMwODQ0MDgwNTY1MjUsIDM3LjQ0NzM4MjkyODMzMzk5NF0sIFsxMjYuOTE2NzcyODE0NjYwMSwgMzcuNDU0OTA1NjY0MjM3ODldLCBbMTI2LjkwMTU2MDk0MTI5ODk1LCAzNy40Nzc1Mzg0Mjc4OTkwMV0sIFsxMjYuOTA1MzE5NzU4MDE4MTIsIDM3LjQ4MjE4MDg3NTc1NDI5XSwgWzEyNi45NDkyMjY2MTM4OTUwOCwgMzcuNDkxMjU0Mzc0OTU2NDldLCBbMTI2Ljk3MjU4OTE4NTA2NjIsIDM3LjQ3MjU2MTM2MzI3ODEyNV0sIFsxMjYuOTgzNjc2NjgyOTE4MDIsIDM3LjQ3Mzg1NjQ5MjY5MjA4Nl1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVhZDAwXHVjNTQ1XHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEyMTAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YWQwMFx1YzU0NVx1YWQ2YyIsICJuYW1lX2VuZyI6ICJHd2FuYWstZ3UiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICIjYzk5NGM3IiwgImZpbGxPcGFjaXR5IjogMC42LCAib3BhY2l0eSI6IDEsICJ3ZWlnaHQiOiAxfX0sICJ0eXBlIjogIkZlYXR1cmUifSwgeyJnZW9tZXRyeSI6IHsiY29vcmRpbmF0ZXMiOiBbW1sxMjYuOTgyMjM4MDc5MTYwODEsIDM3LjUwOTMxNDk2Njc3MDMyNl0sIFsxMjYuOTgzNjc2NjgyOTE4MDIsIDM3LjQ3Mzg1NjQ5MjY5MjA4Nl0sIFsxMjYuOTcyNTg5MTg1MDY2MiwgMzcuNDcyNTYxMzYzMjc4MTI1XSwgWzEyNi45NDkyMjY2MTM4OTUwOCwgMzcuNDkxMjU0Mzc0OTU2NDldLCBbMTI2LjkwNTMxOTc1ODAxODEyLCAzNy40ODIxODA4NzU3NTQyOV0sIFsxMjYuOTIxNzc4OTMxNzQ4MjUsIDM3LjQ5NDg4OTg3NzQxNTE3Nl0sIFsxMjYuOTI4MTA2Mjg4MjgyNzksIDM3LjUxMzI5NTk1NzMyMDE1XSwgWzEyNi45NTI0OTk5MDI5ODE1OSwgMzcuNTE3MjI1MDA3NDE4MTNdLCBbMTI2Ljk4MjIzODA3OTE2MDgxLCAzNy41MDkzMTQ5NjY3NzAzMjZdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YjNkOVx1Yzc5MVx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMjAwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWIzZDlcdWM3OTFcdWFkNmMiLCAibmFtZV9lbmciOiAiRG9uZ2phay1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNkNGI5ZGEiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNi44OTE4NDY2Mzg2Mjc2NCwgMzcuNTQ3MzczOTc0OTk3MTE0XSwgWzEyNi45NDU2NjczMzA4MzIxMiwgMzcuNTI2NjE3NTQyNDUzMzY2XSwgWzEyNi45NTI0OTk5MDI5ODE1OSwgMzcuNTE3MjI1MDA3NDE4MTNdLCBbMTI2LjkyODEwNjI4ODI4Mjc5LCAzNy41MTMyOTU5NTczMjAxNV0sIFsxMjYuOTIxNzc4OTMxNzQ4MjUsIDM3LjQ5NDg4OTg3NzQxNTE3Nl0sIFsxMjYuOTA1MzE5NzU4MDE4MTIsIDM3LjQ4MjE4MDg3NTc1NDI5XSwgWzEyNi44OTU5NDc3Njc4MjQ4NSwgMzcuNTA0Njc1MjgxMzA5MTc2XSwgWzEyNi44ODE1NjQwMjM1Mzg2MiwgMzcuNTEzOTcwMDM0NzY1Njg0XSwgWzEyNi44ODgyNTc1Nzg2MDA5OSwgMzcuNTQwNzk3MzM2MzAyMzJdLCBbMTI2Ljg5MTg0NjYzODYyNzY0LCAzNy41NDczNzM5NzQ5OTcxMTRdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzYwMVx1YjRmMVx1ZDNlY1x1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMTkwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWM2MDFcdWI0ZjFcdWQzZWNcdWFkNmMiLCAibmFtZV9lbmciOiAiWWVvbmdkZXVuZ3BvLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiIzk4MDA0MyIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2LjkwMTU2MDk0MTI5ODk1LCAzNy40Nzc1Mzg0Mjc4OTkwMV0sIFsxMjYuOTE2NzcyODE0NjYwMSwgMzcuNDU0OTA1NjY0MjM3ODldLCBbMTI2LjkzMDg0NDA4MDU2NTI1LCAzNy40NDczODI5MjgzMzM5OTRdLCBbMTI2LjkwMjU4MzE3MTE2OTcsIDM3LjQzNDU0OTM2NjM0OTEyNF0sIFsxMjYuODc2ODMyNzE1MDI0MjgsIDM3LjQ4MjU3NjU5MTYwNzMwNV0sIFsxMjYuOTAxNTYwOTQxMjk4OTUsIDM3LjQ3NzUzODQyNzg5OTAxXV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJcdWFlMDhcdWNjOWNcdWFkNmMiLCAicHJvcGVydGllcyI6IHsiYmFzZV95ZWFyIjogIjIwMTMiLCAiY29kZSI6ICIxMTE4MCIsICJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiXHVhZTA4XHVjYzljXHVhZDZjIiwgIm5hbWVfZW5nIjogIkdldW1jaGVvbi1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNmMWVlZjYiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNi44MjY4ODA4MTUxNzMxNCwgMzcuNTA1NDg5NzIyMzI4OTZdLCBbMTI2Ljg4MTU2NDAyMzUzODYyLCAzNy41MTM5NzAwMzQ3NjU2ODRdLCBbMTI2Ljg5NTk0Nzc2NzgyNDg1LCAzNy41MDQ2NzUyODEzMDkxNzZdLCBbMTI2LjkwNTMxOTc1ODAxODEyLCAzNy40ODIxODA4NzU3NTQyOV0sIFsxMjYuOTAxNTYwOTQxMjk4OTUsIDM3LjQ3NzUzODQyNzg5OTAxXSwgWzEyNi44NzY4MzI3MTUwMjQyOCwgMzcuNDgyNTc2NTkxNjA3MzA1XSwgWzEyNi44NDc2MjY3NjA1NDk1MywgMzcuNDcxNDY3MjM5MzYzMjNdLCBbMTI2LjgzNTQ5NDg1MDc2MTk2LCAzNy40NzQwOTgyMzY5NzUwOTVdLCBbMTI2LjgyMjY0Nzk2NzkxMzQ4LCAzNy40ODc4NDc2NDkyMTQ3XSwgWzEyNi44MjUwNDczNjMzMTQwNiwgMzcuNTAzMDI2MTI2NDA0NDNdLCBbMTI2LjgyNjg4MDgxNTE3MzE0LCAzNy41MDU0ODk3MjIzMjg5Nl1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVhZDZjXHViODVjXHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTExNzAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YWQ2Y1x1Yjg1Y1x1YWQ2YyIsICJuYW1lX2VuZyI6ICJHdXJvLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2RmNjViMCIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2Ljc5NTc1NzY4NTUyOTA3LCAzNy41Nzg4MTA4NzYzMzIwMl0sIFsxMjYuODA3MDIxMTUwMjM1OTcsIDM3LjYwMTIzMDAxMDEzMjI4XSwgWzEyNi44MjI1MTQzODQ3NzEwNSwgMzcuNTg4MDQzMDgxMDA4Ml0sIFsxMjYuODU5ODQxOTkzOTk2NjcsIDM3LjU3MTg0Nzg1NTI5Mjc0NV0sIFsxMjYuODkxODQ2NjM4NjI3NjQsIDM3LjU0NzM3Mzk3NDk5NzExNF0sIFsxMjYuODg4MjU3NTc4NjAwOTksIDM3LjU0MDc5NzMzNjMwMjMyXSwgWzEyNi44NjYzNzQ2NDMyMTIzOCwgMzcuNTQ4NTkxOTEwOTQ4MjNdLCBbMTI2Ljg2NjEwMDczNDc2Mzk1LCAzNy41MjY5OTk2NDE0NDY2OV0sIFsxMjYuODQyNTcyOTE5NDMxNTMsIDM3LjUyMzczNzA3ODA1NTk2XSwgWzEyNi44MjQyMzMxNDI2NzIyLCAzNy41Mzc4ODA3ODc1MzI0OF0sIFsxMjYuNzczMjQ0MTc3MTc3MDMsIDM3LjU0NTkxMjM0NTA1NTRdLCBbMTI2Ljc2OTc5MTgwNTc5MzUyLCAzNy41NTEzOTE4MzAwODgwOV0sIFsxMjYuNzk1NzU3Njg1NTI5MDcsIDM3LjU3ODgxMDg3NjMzMjAyXV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJcdWFjMTVcdWMxMWNcdWFkNmMiLCAicHJvcGVydGllcyI6IHsiYmFzZV95ZWFyIjogIjIwMTMiLCAiY29kZSI6ICIxMTE2MCIsICJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiXHVhYzE1XHVjMTFjXHVhZDZjIiwgIm5hbWVfZW5nIjogIkdhbmdzZW8tZ3UiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICJibGFjayIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2LjgyNDIzMzE0MjY3MjIsIDM3LjUzNzg4MDc4NzUzMjQ4XSwgWzEyNi44NDI1NzI5MTk0MzE1MywgMzcuNTIzNzM3MDc4MDU1OTZdLCBbMTI2Ljg2NjEwMDczNDc2Mzk1LCAzNy41MjY5OTk2NDE0NDY2OV0sIFsxMjYuODY2Mzc0NjQzMjEyMzgsIDM3LjU0ODU5MTkxMDk0ODIzXSwgWzEyNi44ODgyNTc1Nzg2MDA5OSwgMzcuNTQwNzk3MzM2MzAyMzJdLCBbMTI2Ljg4MTU2NDAyMzUzODYyLCAzNy41MTM5NzAwMzQ3NjU2ODRdLCBbMTI2LjgyNjg4MDgxNTE3MzE0LCAzNy41MDU0ODk3MjIzMjg5Nl0sIFsxMjYuODI0MjMzMTQyNjcyMiwgMzcuNTM3ODgwNzg3NTMyNDhdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzU5MVx1Y2M5Y1x1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMTUwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWM1OTFcdWNjOWNcdWFkNmMiLCAibmFtZV9lbmciOiAiWWFuZ2NoZW9uLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiIzk4MDA0MyIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2LjkwNTIyMDY1ODMxMDUzLCAzNy41NzQwOTcwMDUyMjU3NF0sIFsxMjYuOTM4OTgxNjE3OTg5NzMsIDM3LjU1MjMxMDAwMzcyODEyNF0sIFsxMjYuOTYzNTgyMjY3MTA4MTIsIDM3LjU1NjA1NjM1NDc1MTU0XSwgWzEyNi45NjQ0ODU3MDU1MzA1NSwgMzcuNTQ4NzA1NjkyMDIxNjM1XSwgWzEyNi45NDU2NjczMzA4MzIxMiwgMzcuNTI2NjE3NTQyNDUzMzY2XSwgWzEyNi44OTE4NDY2Mzg2Mjc2NCwgMzcuNTQ3MzczOTc0OTk3MTE0XSwgWzEyNi44NTk4NDE5OTM5OTY2NywgMzcuNTcxODQ3ODU1MjkyNzQ1XSwgWzEyNi44ODQzMzI4NDc3MzI4OCwgMzcuNTg4MTQzMzIyODgwNTI2XSwgWzEyNi45MDUyMjA2NTgzMTA1MywgMzcuNTc0MDk3MDA1MjI1NzRdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YjljOFx1ZDNlY1x1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMTQwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWI5YzhcdWQzZWNcdWFkNmMiLCAibmFtZV9lbmciOiAiTWFwby1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNkZjY1YjAiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNi45NTI0NzUyMDMwNTcyLCAzNy42MDUwODY5MjczNzA0NV0sIFsxMjYuOTU1NjU0MjU4NDY0NjMsIDM3LjU3NjA4MDc5MDg4MTQ1Nl0sIFsxMjYuOTY4NzM2MzMyNzkwNzUsIDM3LjU2MzEzNjA0NjkwODI3XSwgWzEyNi45NjM1ODIyNjcxMDgxMiwgMzcuNTU2MDU2MzU0NzUxNTRdLCBbMTI2LjkzODk4MTYxNzk4OTczLCAzNy41NTIzMTAwMDM3MjgxMjRdLCBbMTI2LjkwNTIyMDY1ODMxMDUzLCAzNy41NzQwOTcwMDUyMjU3NF0sIFsxMjYuOTUyNDc1MjAzMDU3MiwgMzcuNjA1MDg2OTI3MzcwNDVdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzExY1x1YjMwMFx1YmIzOFx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMTMwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWMxMWNcdWIzMDBcdWJiMzhcdWFkNmMiLCAibmFtZV9lbmciOiAiU2VvZGFlbXVuLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2YxZWVmNiIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2Ljk3Mzg4NjQxMjg3MDIsIDM3LjYyOTQ5NjM0Nzg2ODg4XSwgWzEyNi45NTQyNzAxNzAwNjEyOSwgMzcuNjIyMDMzNDMxMzM5NDI1XSwgWzEyNi45NTI0NzUyMDMwNTcyLCAzNy42MDUwODY5MjczNzA0NV0sIFsxMjYuOTA1MjIwNjU4MzEwNTMsIDM3LjU3NDA5NzAwNTIyNTc0XSwgWzEyNi44ODQzMzI4NDc3MzI4OCwgMzcuNTg4MTQzMzIyODgwNTI2XSwgWzEyNi45MDM5NjY4MTAwMzU5NSwgMzcuNTkyMjc0MDM0MTk5NDJdLCBbMTI2LjkwMzAzMDY2MTc3NjY4LCAzNy42MDk5Nzc5MTE0MDEzNDRdLCBbMTI2LjkxNDU1NDgxNDI5NjQ4LCAzNy42NDE1MDA1MDk5NjkzNV0sIFsxMjYuOTU2NDczNzk3Mzg3LCAzNy42NTI0ODA3MzczMzk0NDVdLCBbMTI2Ljk3Mzg4NjQxMjg3MDIsIDM3LjYyOTQ5NjM0Nzg2ODg4XV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJcdWM3NDBcdWQzYzlcdWFkNmMiLCAicHJvcGVydGllcyI6IHsiYmFzZV95ZWFyIjogIjIwMTMiLCAiY29kZSI6ICIxMTEyMCIsICJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiXHVjNzQwXHVkM2M5XHVhZDZjIiwgIm5hbWVfZW5nIjogIkV1bnB5ZW9uZy1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNmMWVlZjYiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wODM4NzUyNzAzMTk1LCAzNy42OTM1OTUzNDIwMjAzNF0sIFsxMjcuMDk3MDYzOTEzMDk2OTUsIDM3LjY4NjM4MzcxOTM3MjI5NF0sIFsxMjcuMDk0NDA3NjYyOTg3MTcsIDM3LjY0NzEzNDkwNDczMDQ1XSwgWzEyNy4xMTMyNjc5NTg1NTE5OSwgMzcuNjM5NjIyOTA1MzE1OTI1XSwgWzEyNy4xMDc4MjI3NzY4ODEyOSwgMzcuNjE4MDQyNDQyNDEwNjldLCBbMTI3LjA3MzUxMjQzODI1Mjc4LCAzNy42MTI4MzY2MDM0MjMxM10sIFsxMjcuMDUyMDkzNzM1Njg2MTksIDM3LjYyMTY0MDY1NDg3NzgyXSwgWzEyNy4wNDM1ODgwMDg5NTYwOSwgMzcuNjI4NDg5MzEyOTg3MTVdLCBbMTI3LjA1ODAwMDc1MjIwMDkxLCAzNy42NDMxODI2Mzg3ODI3Nl0sIFsxMjcuMDUyODg0Nzk3MTA0ODUsIDM3LjY4NDIzODU3MDg0MzQ3XSwgWzEyNy4wODM4NzUyNzAzMTk1LCAzNy42OTM1OTUzNDIwMjAzNF1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHViMTc4XHVjNmQwXHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTExMTAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YjE3OFx1YzZkMFx1YWQ2YyIsICJuYW1lX2VuZyI6ICJOb3dvbi1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNkNGI5ZGEiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wNTI4ODQ3OTcxMDQ4NSwgMzcuNjg0MjM4NTcwODQzNDddLCBbMTI3LjA1ODAwMDc1MjIwMDkxLCAzNy42NDMxODI2Mzg3ODI3Nl0sIFsxMjcuMDQzNTg4MDA4OTU2MDksIDM3LjYyODQ4OTMxMjk4NzE1XSwgWzEyNy4wMTQ2NTkzNTg5MjQ2NiwgMzcuNjQ5NDM2ODc0OTY4MTJdLCBbMTI3LjAyMDYyMTE2MTQxMzg5LCAzNy42NjcxNzM1NzU5NzEyMDVdLCBbMTI3LjAxMDM5NjY2MDQyMDcxLCAzNy42ODE4OTQ1ODk2MDM1OTRdLCBbMTI3LjAxNzk1MDk5MjAzNDMyLCAzNy42OTgyNDQxMjc3NTY2Ml0sIFsxMjcuMDUyODg0Nzk3MTA0ODUsIDM3LjY4NDIzODU3MDg0MzQ3XV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJcdWIzYzRcdWJkMDlcdWFkNmMiLCAicHJvcGVydGllcyI6IHsiYmFzZV95ZWFyIjogIjIwMTMiLCAiY29kZSI6ICIxMTEwMCIsICJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiXHViM2M0XHViZDA5XHVhZDZjIiwgIm5hbWVfZW5nIjogIkRvYm9uZy1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNmMWVlZjYiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNi45OTM4MzkwMzQyNCwgMzcuNjc2NjgxNzYxMTk5MDg1XSwgWzEyNy4wMTAzOTY2NjA0MjA3MSwgMzcuNjgxODk0NTg5NjAzNTk0XSwgWzEyNy4wMjA2MjExNjE0MTM4OSwgMzcuNjY3MTczNTc1OTcxMjA1XSwgWzEyNy4wMTQ2NTkzNTg5MjQ2NiwgMzcuNjQ5NDM2ODc0OTY4MTJdLCBbMTI3LjA0MzU4ODAwODk1NjA5LCAzNy42Mjg0ODkzMTI5ODcxNV0sIFsxMjcuMDUyMDkzNzM1Njg2MTksIDM3LjYyMTY0MDY1NDg3NzgyXSwgWzEyNy4wMzg5MjQwMDk5MjMwMSwgMzcuNjA5NzE1NjExMDIzODE2XSwgWzEyNy4wMTI4MTU0NzQ5NTIzLCAzNy42MTM2NTIyNDM0NzAyNTZdLCBbMTI2Ljk4NjcyNzA1NTEzODY5LCAzNy42MzM3NzY0MTI4ODE5Nl0sIFsxMjYuOTgxNzQ1MjY3NjU1MSwgMzcuNjUyMDk3NjkzODc3NzZdLCBbMTI2Ljk5MzgzOTAzNDI0LCAzNy42NzY2ODE3NjExOTkwODVdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YWMxNVx1YmQ4MVx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMDkwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWFjMTVcdWJkODFcdWFkNmMiLCAibmFtZV9lbmciOiAiR2FuZ2J1ay1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNjOTk0YzciLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNi45NzcxNzU0MDY0MTYsIDM3LjYyODU5NzE1NDAwMzg4XSwgWzEyNi45ODY3MjcwNTUxMzg2OSwgMzcuNjMzNzc2NDEyODgxOTZdLCBbMTI3LjAxMjgxNTQ3NDk1MjMsIDM3LjYxMzY1MjI0MzQ3MDI1Nl0sIFsxMjcuMDM4OTI0MDA5OTIzMDEsIDM3LjYwOTcxNTYxMTAyMzgxNl0sIFsxMjcuMDUyMDkzNzM1Njg2MTksIDM3LjYyMTY0MDY1NDg3NzgyXSwgWzEyNy4wNzM1MTI0MzgyNTI3OCwgMzcuNjEyODM2NjAzNDIzMTNdLCBbMTI3LjA3MzgyNzA3MDk5MjI3LCAzNy42MDQwMTkyODk4NjQxOV0sIFsxMjcuMDQyNzA1MjIyMDk0LCAzNy41OTIzOTQzNzU5MzM5MV0sIFsxMjcuMDI1MjcyNTQ1MjgwMDMsIDM3LjU3NTI0NjE2MjQ1MjQ5XSwgWzEyNi45OTM0ODI5MzM1ODMxNCwgMzcuNTg4NTY1NDU3MjE2MTU2XSwgWzEyNi45ODg3OTg2NTk5MjM4NCwgMzcuNjExODkyNzMxOTc1Nl0sIFsxMjYuOTc3MTc1NDA2NDE2LCAzNy42Mjg1OTcxNTQwMDM4OF1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVjMTMxXHViZDgxXHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEwODAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YzEzMVx1YmQ4MVx1YWQ2YyIsICJuYW1lX2VuZyI6ICJTZW9uZ2J1ay1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNmMWVlZjYiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wNzM1MTI0MzgyNTI3OCwgMzcuNjEyODM2NjAzNDIzMTNdLCBbMTI3LjEwNzgyMjc3Njg4MTI5LCAzNy42MTgwNDI0NDI0MTA2OV0sIFsxMjcuMTIwMTI0NjAyMDExNCwgMzcuNjAxNzg0NTc1OTgxODhdLCBbMTI3LjEwMzA0MTc0MjQ5MjE0LCAzNy41NzA3NjM0MjI5MDk1NV0sIFsxMjcuMDgwNjg1NDEyODA0MDMsIDM3LjU2OTA2NDI1NTE5MDE3XSwgWzEyNy4wNzM4MjcwNzA5OTIyNywgMzcuNjA0MDE5Mjg5ODY0MTldLCBbMTI3LjA3MzUxMjQzODI1Mjc4LCAzNy42MTI4MzY2MDM0MjMxM11dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVjOTExXHViNzkxXHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEwNzAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YzkxMVx1Yjc5MVx1YWQ2YyIsICJuYW1lX2VuZyI6ICJKdW5nbmFuZy1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNkZjY1YjAiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wMjUyNzI1NDUyODAwMywgMzcuNTc1MjQ2MTYyNDUyNDldLCBbMTI3LjA0MjcwNTIyMjA5NCwgMzcuNTkyMzk0Mzc1OTMzOTFdLCBbMTI3LjA3MzgyNzA3MDk5MjI3LCAzNy42MDQwMTkyODk4NjQxOV0sIFsxMjcuMDgwNjg1NDEyODA0MDMsIDM3LjU2OTA2NDI1NTE5MDE3XSwgWzEyNy4wNzQyMTA1MzAyNDM2MiwgMzcuNTU3MjQ3Njk3MTIwODVdLCBbMTI3LjA1MDA1NjAxMDgxNTY3LCAzNy41Njc1Nzc2MTI1OTA4NDZdLCBbMTI3LjAyNTQ3MjY2MzQ5OTc2LCAzNy41Njg5NDM1NTIyMzc3MzRdLCBbMTI3LjAyNTI3MjU0NTI4MDAzLCAzNy41NzUyNDYxNjI0NTI0OV1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHViM2Q5XHViMzAwXHViYjM4XHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEwNjAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YjNkOVx1YjMwMFx1YmIzOFx1YWQ2YyIsICJuYW1lX2VuZyI6ICJEb25nZGFlbXVuLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2M5OTRjNyIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI3LjA4MDY4NTQxMjgwNDAzLCAzNy41NjkwNjQyNTUxOTAxN10sIFsxMjcuMTAzMDQxNzQyNDkyMTQsIDM3LjU3MDc2MzQyMjkwOTU1XSwgWzEyNy4xMTUxOTU4NDk4MTYwNiwgMzcuNTU3NTMzMTgwNzA0OTE1XSwgWzEyNy4xMTE2NzY0MjAzNjA4LCAzNy41NDA2Njk5NTUzMjQ5NjVdLCBbMTI3LjEwMDg3NTE5NzkxOTYyLCAzNy41MjQ4NDEyMjAxNjcwNTVdLCBbMTI3LjA2OTA2OTgxMzAzNzIsIDM3LjUyMjI3OTQyMzUwNTAyNl0sIFsxMjcuMDU4NjczNTkyODgzOTgsIDM3LjUyNjI5OTc0OTIyNTY4XSwgWzEyNy4wNzQyMTA1MzAyNDM2MiwgMzcuNTU3MjQ3Njk3MTIwODVdLCBbMTI3LjA4MDY4NTQxMjgwNDAzLCAzNy41NjkwNjQyNTUxOTAxN11dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVhZDExXHVjOWM0XHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEwNTAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YWQxMVx1YzljNFx1YWQ2YyIsICJuYW1lX2VuZyI6ICJHd2FuZ2ppbi1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNkZjY1YjAiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wMjU0NzI2NjM0OTk3NiwgMzcuNTY4OTQzNTUyMjM3NzM0XSwgWzEyNy4wNTAwNTYwMTA4MTU2NywgMzcuNTY3NTc3NjEyNTkwODQ2XSwgWzEyNy4wNzQyMTA1MzAyNDM2MiwgMzcuNTU3MjQ3Njk3MTIwODVdLCBbMTI3LjA1ODY3MzU5Mjg4Mzk4LCAzNy41MjYyOTk3NDkyMjU2OF0sIFsxMjcuMDIzMDI4MzE4OTA1NTksIDM3LjUzMjMxODk5NTgyNjYzXSwgWzEyNy4wMTA3MDg5NDE3NzQ4MiwgMzcuNTQxMTgwNDg5NjQ3NjJdLCBbMTI3LjAyNTQ3MjY2MzQ5OTc2LCAzNy41Njg5NDM1NTIyMzc3MzRdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzEzMVx1YjNkOVx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMDQwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWMxMzFcdWIzZDlcdWFkNmMiLCAibmFtZV9lbmciOiAiU2Vvbmdkb25nLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2YxZWVmNiIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI3LjAxMDcwODk0MTc3NDgyLCAzNy41NDExODA0ODk2NDc2Ml0sIFsxMjcuMDIzMDI4MzE4OTA1NTksIDM3LjUzMjMxODk5NTgyNjYzXSwgWzEyNy4wMTM5NzExOTY2NzUxMywgMzcuNTI1MDM5ODgyODk2NjldLCBbMTI2Ljk4MjIzODA3OTE2MDgxLCAzNy41MDkzMTQ5NjY3NzAzMjZdLCBbMTI2Ljk1MjQ5OTkwMjk4MTU5LCAzNy41MTcyMjUwMDc0MTgxM10sIFsxMjYuOTQ1NjY3MzMwODMyMTIsIDM3LjUyNjYxNzU0MjQ1MzM2Nl0sIFsxMjYuOTY0NDg1NzA1NTMwNTUsIDM3LjU0ODcwNTY5MjAyMTYzNV0sIFsxMjYuOTg3NTI5OTY5MDMzMjgsIDM3LjU1MDk0ODE4ODA3MTM5XSwgWzEyNy4wMTA3MDg5NDE3NzQ4MiwgMzcuNTQxMTgwNDg5NjQ3NjJdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzZhOVx1YzBiMFx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMDMwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWM2YTlcdWMwYjBcdWFkNmMiLCAibmFtZV9lbmciOiAiWW9uZ3Nhbi1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNkZjY1YjAiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wMjU0NzI2NjM0OTk3NiwgMzcuNTY4OTQzNTUyMjM3NzM0XSwgWzEyNy4wMTA3MDg5NDE3NzQ4MiwgMzcuNTQxMTgwNDg5NjQ3NjJdLCBbMTI2Ljk4NzUyOTk2OTAzMzI4LCAzNy41NTA5NDgxODgwNzEzOV0sIFsxMjYuOTY0NDg1NzA1NTMwNTUsIDM3LjU0ODcwNTY5MjAyMTYzNV0sIFsxMjYuOTYzNTgyMjY3MTA4MTIsIDM3LjU1NjA1NjM1NDc1MTU0XSwgWzEyNi45Njg3MzYzMzI3OTA3NSwgMzcuNTYzMTM2MDQ2OTA4MjddLCBbMTI3LjAyNTQ3MjY2MzQ5OTc2LCAzNy41Njg5NDM1NTIyMzc3MzRdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzkxMVx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMDIwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWM5MTFcdWFkNmMiLCAibmFtZV9lbmciOiAiSnVuZy1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiM5ODAwNDMiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNi45NzM4ODY0MTI4NzAyLCAzNy42Mjk0OTYzNDc4Njg4OF0sIFsxMjYuOTc3MTc1NDA2NDE2LCAzNy42Mjg1OTcxNTQwMDM4OF0sIFsxMjYuOTg4Nzk4NjU5OTIzODQsIDM3LjYxMTg5MjczMTk3NTZdLCBbMTI2Ljk5MzQ4MjkzMzU4MzE0LCAzNy41ODg1NjU0NTcyMTYxNTZdLCBbMTI3LjAyNTI3MjU0NTI4MDAzLCAzNy41NzUyNDYxNjI0NTI0OV0sIFsxMjcuMDI1NDcyNjYzNDk5NzYsIDM3LjU2ODk0MzU1MjIzNzczNF0sIFsxMjYuOTY4NzM2MzMyNzkwNzUsIDM3LjU2MzEzNjA0NjkwODI3XSwgWzEyNi45NTU2NTQyNTg0NjQ2MywgMzcuNTc2MDgwNzkwODgxNDU2XSwgWzEyNi45NTI0NzUyMDMwNTcyLCAzNy42MDUwODY5MjczNzA0NV0sIFsxMjYuOTU0MjcwMTcwMDYxMjksIDM3LjYyMjAzMzQzMTMzOTQyNV0sIFsxMjYuOTczODg2NDEyODcwMiwgMzcuNjI5NDk2MzQ3ODY4ODhdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1Yzg4NVx1Yjg1Y1x1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMDEwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWM4ODVcdWI4NWNcdWFkNmMiLCAibmFtZV9lbmciOiAiSm9uZ25vLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiIzk4MDA0MyIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn1dLCAidHlwZSI6ICJGZWF0dXJlQ29sbGVjdGlvbiJ9CiAgICAgICAgICAgIAogICAgICAgICAgICApLmFkZFRvKGNob3JvcGxldGhfMDYxYjZmNzM1NjEzNGRiNTgyNDY4ZmU0NjgzNThiOTkpOwogICAgICAgIGdlb19qc29uXzJhNzIzOTE0NjRlZDQzODZiYWJkMjJkMTQ3NDE4N2QxLnNldFN0eWxlKGZ1bmN0aW9uKGZlYXR1cmUpIHtyZXR1cm4gZmVhdHVyZS5wcm9wZXJ0aWVzLnN0eWxlO30pOwogICAgICAgIAogICAgCiAgICB2YXIgY29sb3JfbWFwXzAzZjc3NzYyODIyZjQzMTlhNmJlOWY0ZTZiZGRhM2QyID0ge307CgogICAgCiAgICBjb2xvcl9tYXBfMDNmNzc3NjI4MjJmNDMxOWE2YmU5ZjRlNmJkZGEzZDIuY29sb3IgPSBkMy5zY2FsZS50aHJlc2hvbGQoKQogICAgICAgICAgICAgIC5kb21haW4oWzAuMTgyNzc5OTI2MzMyMzc4NDcsIDAuMTg2Mjg5NDQ3MDE1NTI4NTIsIDAuMTg5Nzk4OTY3Njk4Njc4NiwgMC4xOTMzMDg0ODgzODE4Mjg2NSwgMC4xOTY4MTgwMDkwNjQ5Nzg3LCAwLjIwMDMyNzUyOTc0ODEyODc5LCAwLjIwMzgzNzA1MDQzMTI3ODg0LCAwLjIwNzM0NjU3MTExNDQyODksIDAuMjEwODU2MDkxNzk3NTc4OTcsIDAuMjE0MzY1NjEyNDgwNzI5MDIsIDAuMjE3ODc1MTMzMTYzODc5MDgsIDAuMjIxMzg0NjUzODQ3MDI5MTYsIDAuMjI0ODk0MTc0NTMwMTc5MiwgMC4yMjg0MDM2OTUyMTMzMjkzLCAwLjIzMTkxMzIxNTg5NjQ3OTM0LCAwLjIzNTQyMjczNjU3OTYyOTQyLCAwLjIzODkzMjI1NzI2Mjc3OTQ3LCAwLjI0MjQ0MTc3Nzk0NTkyOTUzLCAwLjI0NTk1MTI5ODYyOTA3OTYsIDAuMjQ5NDYwODE5MzEyMjI5NjYsIDAuMjUyOTcwMzM5OTk1Mzc5NywgMC4yNTY0Nzk4NjA2Nzg1Mjk4LCAwLjI1OTk4OTM4MTM2MTY3OTgsIDAuMjYzNDk4OTAyMDQ0ODI5OSwgMC4yNjcwMDg0MjI3Mjc5OCwgMC4yNzA1MTc5NDM0MTExMywgMC4yNzQwMjc0NjQwOTQyODAxLCAwLjI3NzUzNjk4NDc3NzQzMDE2LCAwLjI4MTA0NjUwNTQ2MDU4MDIsIDAuMjg0NTU2MDI2MTQzNzMwMjcsIDAuMjg4MDY1NTQ2ODI2ODgwMzUsIDAuMjkxNTc1MDY3NTEwMDMwNCwgMC4yOTUwODQ1ODgxOTMxODA0NSwgMC4yOTg1OTQxMDg4NzYzMzA1MywgMC4zMDIxMDM2Mjk1NTk0ODA1NiwgMC4zMDU2MTMxNTAyNDI2MzA2NCwgMC4zMDkxMjI2NzA5MjU3ODA3LCAwLjMxMjYzMjE5MTYwODkzMDc0LCAwLjMxNjE0MTcxMjI5MjA4MDksIDAuMzE5NjUxMjMyOTc1MjMwOSwgMC4zMjMxNjA3NTM2NTgzODA5MywgMC4zMjY2NzAyNzQzNDE1MzEsIDAuMzMwMTc5Nzk1MDI0NjgxMSwgMC4zMzM2ODkzMTU3MDc4MzExLCAwLjMzNzE5ODgzNjM5MDk4MTI1LCAwLjM0MDcwODM1NzA3NDEzMTMsIDAuMzQ0MjE3ODc3NzU3MjgxMzUsIDAuMzQ3NzI3Mzk4NDQwNDMxNCwgMC4zNTEyMzY5MTkxMjM1ODE0NiwgMC4zNTQ3NDY0Mzk4MDY3MzE1LCAwLjM1ODI1NTk2MDQ4OTg4MTYsIDAuMzYxNzY1NDgxMTczMDMxNjQsIDAuMzY1Mjc1MDAxODU2MTgxNywgMC4zNjg3ODQ1MjI1MzkzMzE4LCAwLjM3MjI5NDA0MzIyMjQ4MTgzLCAwLjM3NTgwMzU2MzkwNTYzMTg1LCAwLjM3OTMxMzA4NDU4ODc4MiwgMC4zODI4MjI2MDUyNzE5MzIsIDAuMzg2MzMyMTI1OTU1MDgyMSwgMC4zODk4NDE2NDY2MzgyMzIyLCAwLjM5MzM1MTE2NzMyMTM4MjIsIDAuMzk2ODYwNjg4MDA0NTMyMiwgMC40MDAzNzAyMDg2ODc2ODIzNiwgMC40MDM4Nzk3MjkzNzA4MzI0LCAwLjQwNzM4OTI1MDA1Mzk4MjQ3LCAwLjQxMDg5ODc3MDczNzEzMjU1LCAwLjQxNDQwODI5MTQyMDI4MjU3LCAwLjQxNzkxNzgxMjEwMzQzMjY1LCAwLjQyMTQyNzMzMjc4NjU4MjczLCAwLjQyNDkzNjg1MzQ2OTczMjc2LCAwLjQyODQ0NjM3NDE1Mjg4Mjg0LCAwLjQzMTk1NTg5NDgzNjAzMjksIDAuNDM1NDY1NDE1NTE5MTgyOTQsIDAuNDM4OTc0OTM2MjAyMzMzMSwgMC40NDI0ODQ0NTY4ODU0ODMxLCAwLjQ0NTk5Mzk3NzU2ODYzMzEsIDAuNDQ5NTAzNDk4MjUxNzgzMjYsIDAuNDUzMDEzMDE4OTM0OTMzMywgMC40NTY1MjI1Mzk2MTgwODMzLCAwLjQ2MDAzMjA2MDMwMTIzMzQ1LCAwLjQ2MzU0MTU4MDk4NDM4MzQ3LCAwLjQ2NzA1MTEwMTY2NzUzMzUsIDAuNDcwNTYwNjIyMzUwNjgzNSwgMC40NzQwNzAxNDMwMzM4MzM2NiwgMC40Nzc1Nzk2NjM3MTY5ODM3LCAwLjQ4MTA4OTE4NDQwMDEzMzgsIDAuNDg0NTk4NzA1MDgzMjgzODQsIDAuNDg4MTA4MjI1NzY2NDMzODcsIDAuNDkxNjE3NzQ2NDQ5NTg0LCAwLjQ5NTEyNzI2NzEzMjczNDAzLCAwLjQ5ODYzNjc4NzgxNTg4NDA1LCAwLjUwMjE0NjMwODQ5OTAzNDIsIDAuNTA1NjU1ODI5MTgyMTg0MiwgMC41MDkxNjUzNDk4NjUzMzQyLCAwLjUxMjY3NDg3MDU0ODQ4NDMsIDAuNTE2MTg0MzkxMjMxNjM0NCwgMC41MTk2OTM5MTE5MTQ3ODQ0LCAwLjUyMzIwMzQzMjU5NzkzNDYsIDAuNTI2NzEyOTUzMjgxMDg0NiwgMC41MzAyMjI0NzM5NjQyMzQ3LCAwLjUzMzczMTk5NDY0NzM4NDcsIDAuNTM3MjQxNTE1MzMwNTM0OCwgMC41NDA3NTEwMzYwMTM2ODQ4LCAwLjU0NDI2MDU1NjY5NjgzNDksIDAuNTQ3NzcwMDc3Mzc5OTg1LCAwLjU1MTI3OTU5ODA2MzEzNSwgMC41NTQ3ODkxMTg3NDYyODUxLCAwLjU1ODI5ODYzOTQyOTQzNTEsIDAuNTYxODA4MTYwMTEyNTg1MiwgMC41NjUzMTc2ODA3OTU3MzUzLCAwLjU2ODgyNzIwMTQ3ODg4NTMsIDAuNTcyMzM2NzIyMTYyMDM1MywgMC41NzU4NDYyNDI4NDUxODU1LCAwLjU3OTM1NTc2MzUyODMzNTUsIDAuNTgyODY1Mjg0MjExNDg1NSwgMC41ODYzNzQ4MDQ4OTQ2MzU2LCAwLjU4OTg4NDMyNTU3Nzc4NTcsIDAuNTkzMzkzODQ2MjYwOTM1NywgMC41OTY5MDMzNjY5NDQwODU5LCAwLjYwMDQxMjg4NzYyNzIzNTksIDAuNjAzOTIyNDA4MzEwMzg2LCAwLjYwNzQzMTkyODk5MzUzNiwgMC42MTA5NDE0NDk2NzY2ODYxLCAwLjYxNDQ1MDk3MDM1OTgzNjEsIDAuNjE3OTYwNDkxMDQyOTg2MiwgMC42MjE0NzAwMTE3MjYxMzYzLCAwLjYyNDk3OTUzMjQwOTI4NjMsIDAuNjI4NDg5MDUzMDkyNDM2NCwgMC42MzE5OTg1NzM3NzU1ODY0LCAwLjYzNTUwODA5NDQ1ODczNjUsIDAuNjM5MDE3NjE1MTQxODg2NiwgMC42NDI1MjcxMzU4MjUwMzY2LCAwLjY0NjAzNjY1NjUwODE4NjgsIDAuNjQ5NTQ2MTc3MTkxMzM2OCwgMC42NTMwNTU2OTc4NzQ0ODY4LCAwLjY1NjU2NTIxODU1NzYzNjgsIDAuNjYwMDc0NzM5MjQwNzg3LCAwLjY2MzU4NDI1OTkyMzkzNywgMC42NjcwOTM3ODA2MDcwODcsIDAuNjcwNjAzMzAxMjkwMjM3MiwgMC42NzQxMTI4MjE5NzMzODcyLCAwLjY3NzYyMjM0MjY1NjUzNzMsIDAuNjgxMTMxODYzMzM5Njg3MywgMC42ODQ2NDEzODQwMjI4Mzc0LCAwLjY4ODE1MDkwNDcwNTk4NzUsIDAuNjkxNjYwNDI1Mzg5MTM3NSwgMC42OTUxNjk5NDYwNzIyODc3LCAwLjY5ODY3OTQ2Njc1NTQzNzcsIDAuNzAyMTg4OTg3NDM4NTg3NywgMC43MDU2OTg1MDgxMjE3Mzc3LCAwLjcwOTIwODAyODgwNDg4NzksIDAuNzEyNzE3NTQ5NDg4MDM3OSwgMC43MTYyMjcwNzAxNzExODgsIDAuNzE5NzM2NTkwODU0MzM4LCAwLjcyMzI0NjExMTUzNzQ4ODEsIDAuNzI2NzU1NjMyMjIwNjM4MSwgMC43MzAyNjUxNTI5MDM3ODgyLCAwLjczMzc3NDY3MzU4NjkzODMsIDAuNzM3Mjg0MTk0MjcwMDg4NCwgMC43NDA3OTM3MTQ5NTMyMzg0LCAwLjc0NDMwMzIzNTYzNjM4ODQsIDAuNzQ3ODEyNzU2MzE5NTM4NiwgMC43NTEzMjIyNzcwMDI2ODg2LCAwLjc1NDgzMTc5NzY4NTgzODYsIDAuNzU4MzQxMzE4MzY4OTg4NywgMC43NjE4NTA4MzkwNTIxMzg3LCAwLjc2NTM2MDM1OTczNTI4ODksIDAuNzY4ODY5ODgwNDE4NDM5LCAwLjc3MjM3OTQwMTEwMTU4OSwgMC43NzU4ODg5MjE3ODQ3MzksIDAuNzc5Mzk4NDQyNDY3ODg5MSwgMC43ODI5MDc5NjMxNTEwMzkyLCAwLjc4NjQxNzQ4MzgzNDE4OTIsIDAuNzg5OTI3MDA0NTE3MzM5MiwgMC43OTM0MzY1MjUyMDA0ODk0LCAwLjc5Njk0NjA0NTg4MzYzOTUsIDAuODAwNDU1NTY2NTY2Nzg5NSwgMC44MDM5NjUwODcyNDk5Mzk1LCAwLjgwNzQ3NDYwNzkzMzA4OTYsIDAuODEwOTg0MTI4NjE2MjM5NiwgMC44MTQ0OTM2NDkyOTkzODk3LCAwLjgxODAwMzE2OTk4MjUzOTcsIDAuODIxNTEyNjkwNjY1Njg5OSwgMC44MjUwMjIyMTEzNDg4Mzk5LCAwLjgyODUzMTczMjAzMTk5LCAwLjgzMjA0MTI1MjcxNTE0MDEsIDAuODM1NTUwNzczMzk4MjkwMSwgMC44MzkwNjAyOTQwODE0NDAxLCAwLjg0MjU2OTgxNDc2NDU5MDEsIDAuODQ2MDc5MzM1NDQ3NzQwMywgMC44NDk1ODg4NTYxMzA4OTA0LCAwLjg1MzA5ODM3NjgxNDA0MDQsIDAuODU2NjA3ODk3NDk3MTkwNSwgMC44NjAxMTc0MTgxODAzNDA1LCAwLjg2MzYyNjkzODg2MzQ5MDYsIDAuODY3MTM2NDU5NTQ2NjQwNiwgMC44NzA2NDU5ODAyMjk3OTA3LCAwLjg3NDE1NTUwMDkxMjk0MDcsIDAuODc3NjY1MDIxNTk2MDkwOSwgMC44ODExNzQ1NDIyNzkyNDEsIDAuODg0Njg0MDYyOTYyMzkxLCAwLjg4ODE5MzU4MzY0NTU0MSwgMC44OTE3MDMxMDQzMjg2OTEsIDAuODk1MjEyNjI1MDExODQxMiwgMC44OTg3MjIxNDU2OTQ5OTEyLCAwLjkwMjIzMTY2NjM3ODE0MTIsIDAuOTA1NzQxMTg3MDYxMjkxNCwgMC45MDkyNTA3MDc3NDQ0NDE0LCAwLjkxMjc2MDIyODQyNzU5MTUsIDAuOTE2MjY5NzQ5MTEwNzQxNSwgMC45MTk3NzkyNjk3OTM4OTE2LCAwLjkyMzI4ODc5MDQ3NzA0MTYsIDAuOTI2Nzk4MzExMTYwMTkxNywgMC45MzAzMDc4MzE4NDMzNDE4LCAwLjkzMzgxNzM1MjUyNjQ5MTksIDAuOTM3MzI2ODczMjA5NjQxOSwgMC45NDA4MzYzOTM4OTI3OTIsIDAuOTQ0MzQ1OTE0NTc1OTQyMSwgMC45NDc4NTU0MzUyNTkwOTIxLCAwLjk1MTM2NDk1NTk0MjI0MjEsIDAuOTU0ODc0NDc2NjI1MzkyMiwgMC45NTgzODM5OTczMDg1NDIyLCAwLjk2MTg5MzUxNzk5MTY5MjMsIDAuOTY1NDAzMDM4Njc0ODQyNSwgMC45Njg5MTI1NTkzNTc5OTI1LCAwLjk3MjQyMjA4MDA0MTE0MjUsIDAuOTc1OTMxNjAwNzI0MjkyNiwgMC45Nzk0NDExMjE0MDc0NDI3LCAwLjk4Mjk1MDY0MjA5MDU5MjcsIDAuOTg2NDYwMTYyNzczNzQyNywgMC45ODk5Njk2ODM0NTY4OTI3LCAwLjk5MzQ3OTIwNDE0MDA0MywgMC45OTY5ODg3MjQ4MjMxOTMsIDEuMDAwNDk4MjQ1NTA2MzQzLCAxLjAwNDAwNzc2NjE4OTQ5MywgMS4wMDc1MTcyODY4NzI2NDMsIDEuMDExMDI2ODA3NTU1NzkzLCAxLjAxNDUzNjMyODIzODk0MzIsIDEuMDE4MDQ1ODQ4OTIyMDkzMiwgMS4wMjE1NTUzNjk2MDUyNDMzLCAxLjAyNTA2NDg5MDI4ODM5MzQsIDEuMDI4NTc0NDEwOTcxNTQzNiwgMS4wMzIwODM5MzE2NTQ2OTM1LCAxLjAzNTU5MzQ1MjMzNzg0MzYsIDEuMDM5MTAyOTczMDIwOTkzNSwgMS4wNDI2MTI0OTM3MDQxNDM3LCAxLjA0NjEyMjAxNDM4NzI5MzgsIDEuMDQ5NjMxNTM1MDcwNDQ0LCAxLjA1MzE0MTA1NTc1MzU5MzgsIDEuMDU2NjUwNTc2NDM2NzQ0LCAxLjA2MDE2MDA5NzExOTg5NDEsIDEuMDYzNjY5NjE3ODAzMDQ0LCAxLjA2NzE3OTEzODQ4NjE5NDIsIDEuMDcwNjg4NjU5MTY5MzQ0LCAxLjA3NDE5ODE3OTg1MjQ5NDIsIDEuMDc3NzA3NzAwNTM1NjQ0NCwgMS4wODEyMTcyMjEyMTg3OTQ1LCAxLjA4NDcyNjc0MTkwMTk0NDQsIDEuMDg4MjM2MjYyNTg1MDk0NSwgMS4wOTE3NDU3ODMyNjgyNDQ3LCAxLjA5NTI1NTMwMzk1MTM5NDYsIDEuMDk4NzY0ODI0NjM0NTQ0NywgMS4xMDIyNzQzNDUzMTc2OTQ2LCAxLjEwNTc4Mzg2NjAwMDg0NSwgMS4xMDkyOTMzODY2ODM5OTUsIDEuMTEyODAyOTA3MzY3MTQ1LCAxLjExNjMxMjQyODA1MDI5NSwgMS4xMTk4MjE5NDg3MzM0NDUsIDEuMTIzMzMxNDY5NDE2NTk1MiwgMS4xMjY4NDA5OTAwOTk3NDUxLCAxLjEzMDM1MDUxMDc4Mjg5NTMsIDEuMTMzODYwMDMxNDY2MDQ1NCwgMS4xMzczNjk1NTIxNDkxOTUzLCAxLjE0MDg3OTA3MjgzMjM0NTUsIDEuMTQ0Mzg4NTkzNTE1NDk1NiwgMS4xNDc4OTgxMTQxOTg2NDU1LCAxLjE1MTQwNzYzNDg4MTc5NTYsIDEuMTU0OTE3MTU1NTY0OTQ1OCwgMS4xNTg0MjY2NzYyNDgwOTU3LCAxLjE2MTkzNjE5NjkzMTI0NTgsIDEuMTY1NDQ1NzE3NjE0Mzk2LCAxLjE2ODk1NTIzODI5NzU0NTksIDEuMTcyNDY0NzU4OTgwNjk2LCAxLjE3NTk3NDI3OTY2Mzg0NjIsIDEuMTc5NDgzODAwMzQ2OTk2LCAxLjE4Mjk5MzMyMTAzMDE0NjIsIDEuMTg2NTAyODQxNzEzMjk2MSwgMS4xOTAwMTIzNjIzOTY0NDYzLCAxLjE5MzUyMTg4MzA3OTU5NjQsIDEuMTk3MDMxNDAzNzYyNzQ2MywgMS4yMDA1NDA5MjQ0NDU4OTY0LCAxLjIwNDA1MDQ0NTEyOTA0NjYsIDEuMjA3NTU5OTY1ODEyMTk2NywgMS4yMTEwNjk0ODY0OTUzNDY2LCAxLjIxNDU3OTAwNzE3ODQ5NjgsIDEuMjE4MDg4NTI3ODYxNjQ3LCAxLjIyMTU5ODA0ODU0NDc5NjgsIDEuMjI1MTA3NTY5MjI3OTQ3LCAxLjIyODYxNzA4OTkxMTA5NjksIDEuMjMyMTI2NjEwNTk0MjQ3LCAxLjIzNTYzNjEzMTI3NzM5NzEsIDEuMjM5MTQ1NjUxOTYwNTQ3LCAxLjI0MjY1NTE3MjY0MzY5NzIsIDEuMjQ2MTY0NjkzMzI2ODQ3MywgMS4yNDk2NzQyMTQwMDk5OTc1LCAxLjI1MzE4MzczNDY5MzE0NzQsIDEuMjU2NjkzMjU1Mzc2Mjk3NSwgMS4yNjAyMDI3NzYwNTk0NDc2LCAxLjI2MzcxMjI5Njc0MjU5NzYsIDEuMjY3MjIxODE3NDI1NzQ3NywgMS4yNzA3MzEzMzgxMDg4OTc2LCAxLjI3NDI0MDg1ODc5MjA0NzcsIDEuMjc3NzUwMzc5NDc1MTk3OSwgMS4yODEyNTk5MDAxNTgzNDgsIDEuMjg0NzY5NDIwODQxNDk4LCAxLjI4ODI3ODk0MTUyNDY0OCwgMS4yOTE3ODg0NjIyMDc3OTgyLCAxLjI5NTI5Nzk4Mjg5MDk0OCwgMS4yOTg4MDc1MDM1NzQwOTgyLCAxLjMwMjMxNzAyNDI1NzI0ODIsIDEuMzA1ODI2NTQ0OTQwMzk4MywgMS4zMDkzMzYwNjU2MjM1NDgyLCAxLjMxMjg0NTU4NjMwNjY5ODYsIDEuMzE2MzU1MTA2OTg5ODQ4NywgMS4zMTk4NjQ2Mjc2NzI5OTg2LCAxLjMyMzM3NDE0ODM1NjE0ODcsIDEuMzI2ODgzNjY5MDM5Mjk4NywgMS4zMzAzOTMxODk3MjI0NDg4LCAxLjMzMzkwMjcxMDQwNTU5ODcsIDEuMzM3NDEyMjMxMDg4NzQ4OCwgMS4zNDA5MjE3NTE3NzE4OTg4LCAxLjM0NDQzMTI3MjQ1NTA0OTEsIDEuMzQ3OTQwNzkzMTM4MTk5MywgMS4zNTE0NTAzMTM4MjEzNDkyLCAxLjM1NDk1OTgzNDUwNDQ5OTMsIDEuMzU4NDY5MzU1MTg3NjQ5MiwgMS4zNjE5Nzg4NzU4NzA3OTk0LCAxLjM2NTQ4ODM5NjU1Mzk0OTMsIDEuMzY4OTk3OTE3MjM3MDk5NCwgMS4zNzI1MDc0Mzc5MjAyNDkzLCAxLjM3NjAxNjk1ODYwMzM5OTcsIDEuMzc5NTI2NDc5Mjg2NTQ5OCwgMS4zODMwMzU5OTk5Njk2OTk3LCAxLjM4NjU0NTUyMDY1Mjg0OTksIDEuMzkwMDU1MDQxMzM1OTk5OCwgMS4zOTM1NjQ1NjIwMTkxNSwgMS4zOTcwNzQwODI3MDIyOTk4LCAxLjQwMDU4MzYwMzM4NTQ1LCAxLjQwNDA5MzEyNDA2ODYsIDEuNDA3NjAyNjQ0NzUxNzUsIDEuNDExMTEyMTY1NDM0OTAwNCwgMS40MTQ2MjE2ODYxMTgwNTAzLCAxLjQxODEzMTIwNjgwMTIwMDQsIDEuNDIxNjQwNzI3NDg0MzUwMywgMS40MjUxNTAyNDgxNjc1MDA1LCAxLjQyODY1OTc2ODg1MDY1MDQsIDEuNDMyMTY5Mjg5NTMzODAwNSwgMS40MzU2Nzg4MTAyMTY5NTA3LCAxLjQzOTE4ODMzMDkwMDEwMDYsIDEuNDQyNjk3ODUxNTgzMjUxLCAxLjQ0NjIwNzM3MjI2NjQwMDgsIDEuNDQ5NzE2ODkyOTQ5NTUxLCAxLjQ1MzIyNjQxMzYzMjcwMDksIDEuNDU2NzM1OTM0MzE1ODUxLCAxLjQ2MDI0NTQ1NDk5OTAwMTIsIDEuNDYzNzU0OTc1NjgyMTUxLCAxLjQ2NzI2NDQ5NjM2NTMwMTIsIDEuNDcwNzc0MDE3MDQ4NDUxMSwgMS40NzQyODM1Mzc3MzE2MDE1LCAxLjQ3Nzc5MzA1ODQxNDc1MTQsIDEuNDgxMzAyNTc5MDk3OTAxNSwgMS40ODQ4MTIwOTk3ODEwNTE0LCAxLjQ4ODMyMTYyMDQ2NDIwMTYsIDEuNDkxODMxMTQxMTQ3MzUxNywgMS40OTUzNDA2NjE4MzA1MDE2LCAxLjQ5ODg1MDE4MjUxMzY1MTgsIDEuNTAyMzU5NzAzMTk2ODAxNywgMS41MDU4NjkyMjM4Nzk5NTE4LCAxLjUwOTM3ODc0NDU2MzEwMiwgMS41MTI4ODgyNjUyNDYyNTIsIDEuNTE2Mzk3Nzg1OTI5NDAyMiwgMS41MTk5MDczMDY2MTI1NTIxLCAxLjUyMzQxNjgyNzI5NTcwMjMsIDEuNTI2OTI2MzQ3OTc4ODUyMiwgMS41MzA0MzU4Njg2NjIwMDIzLCAxLjUzMzk0NTM4OTM0NTE1MjIsIDEuNTM3NDU0OTEwMDI4MzAyNCwgMS41NDA5NjQ0MzA3MTE0NTI1LCAxLjU0NDQ3Mzk1MTM5NDYwMjYsIDEuNTQ3OTgzNDcyMDc3NzUyOCwgMS41NTE0OTI5OTI3NjA5MDI3LCAxLjU1NTAwMjUxMzQ0NDA1MjgsIDEuNTU4NTEyMDM0MTI3MjAyNywgMS41NjIwMjE1NTQ4MTAzNTI5LCAxLjU2NTUzMTA3NTQ5MzUwMjgsIDEuNTY5MDQwNTk2MTc2NjUzLCAxLjU3MjU1MDExNjg1OTgwMzMsIDEuNTc2MDU5NjM3NTQyOTUzMiwgMS41Nzk1NjkxNTgyMjYxMDMzLCAxLjU4MzA3ODY3ODkwOTI1MzIsIDEuNTg2NTg4MTk5NTkyNDAzNCwgMS41OTAwOTc3MjAyNzU1NTMzLCAxLjU5MzYwNzI0MDk1ODcwMzQsIDEuNTk3MTE2NzYxNjQxODUzMywgMS42MDA2MjYyODIzMjUwMDM1LCAxLjYwNDEzNTgwMzAwODE1MzgsIDEuNjA3NjQ1MzIzNjkxMzAzOCwgMS42MTExNTQ4NDQzNzQ0NTM5LCAxLjYxNDY2NDM2NTA1NzYwMzgsIDEuNjE4MTczODg1NzQwNzU0LCAxLjYyMTY4MzQwNjQyMzkwMzksIDEuNjI1MTkyOTI3MTA3MDU0LCAxLjYyODcwMjQ0Nzc5MDIwNDEsIDEuNjMyMjExOTY4NDczMzU0LCAxLjYzNTcyMTQ4OTE1NjUwNDIsIDEuNjM5MjMxMDA5ODM5NjU0MywgMS42NDI3NDA1MzA1MjI4MDQ0LCAxLjY0NjI1MDA1MTIwNTk1NDQsIDEuNjQ5NzU5NTcxODg5MTA0NSwgMS42NTMyNjkwOTI1NzIyNTQ0LCAxLjY1Njc3ODYxMzI1NTQwNDUsIDEuNjYwMjg4MTMzOTM4NTU0NywgMS42NjM3OTc2NTQ2MjE3MDQ2LCAxLjY2NzMwNzE3NTMwNDg1NDcsIDEuNjcwODE2Njk1OTg4MDA0OSwgMS42NzQzMjYyMTY2NzExNTUsIDEuNjc3ODM1NzM3MzU0MzA1LCAxLjY4MTM0NTI1ODAzNzQ1NSwgMS42ODQ4NTQ3Nzg3MjA2MDUyLCAxLjY4ODM2NDI5OTQwMzc1NSwgMS42OTE4NzM4MjAwODY5MDUyLCAxLjY5NTM4MzM0MDc3MDA1NTEsIDEuNjk4ODkyODYxNDUzMjA1MywgMS43MDI0MDIzODIxMzYzNTU0LCAxLjcwNTkxMTkwMjgxOTUwNTYsIDEuNzA5NDIxNDIzNTAyNjU1NSwgMS43MTI5MzA5NDQxODU4MDU2LCAxLjcxNjQ0MDQ2NDg2ODk1NTcsIDEuNzE5OTQ5OTg1NTUyMTA1NywgMS43MjM0NTk1MDYyMzUyNTU4LCAxLjcyNjk2OTAyNjkxODQwNTcsIDEuNzMwNDc4NTQ3NjAxNTU1OCwgMS43MzM5ODgwNjgyODQ3MDU4LCAxLjczNzQ5NzU4ODk2Nzg1NiwgMS43NDEwMDcxMDk2NTEwMDYsIDEuNzQ0NTE2NjMwMzM0MTU2MiwgMS43NDgwMjYxNTEwMTczMDYzLCAxLjc1MTUzNTY3MTcwMDQ1NjIsIDEuNzU1MDQ1MTkyMzgzNjA2MywgMS43NTg1NTQ3MTMwNjY3NTYzLCAxLjc2MjA2NDIzMzc0OTkwNjQsIDEuNzY1NTczNzU0NDMzMDU2MywgMS43NjkwODMyNzUxMTYyMDY3LCAxLjc3MjU5Mjc5NTc5OTM1NjgsIDEuNzc2MTAyMzE2NDgyNTA2NywgMS43Nzk2MTE4MzcxNjU2NTY5LCAxLjc4MzEyMTM1Nzg0ODgwNjgsIDEuNzg2NjMwODc4NTMxOTU3LCAxLjc5MDE0MDM5OTIxNTEwNjgsIDEuNzkzNjQ5OTE5ODk4MjU3LCAxLjc5NzE1OTQ0MDU4MTQwNjksIDEuODAwNjY4OTYxMjY0NTU3MiwgMS44MDQxNzg0ODE5NDc3MDc0LCAxLjgwNzY4ODAwMjYzMDg1NzMsIDEuODExMTk3NTIzMzE0MDA3NCwgMS44MTQ3MDcwNDM5OTcxNTczLCAxLjgxODIxNjU2NDY4MDMwNzUsIDEuODIxNzI2MDg1MzYzNDU3NCwgMS44MjUyMzU2MDYwNDY2MDc1LCAxLjgyODc0NTEyNjcyOTc1NzYsIDEuODMyMjU0NjQ3NDEyOTA3NiwgMS44MzU3NjQxNjgwOTYwNTgsIDEuODM5MjczNjg4Nzc5MjA3OCwgMS44NDI3ODMyMDk0NjIzNTgsIDEuODQ2MjkyNzMwMTQ1NTA3OSwgMS44NDk4MDIyNTA4Mjg2NTgsIDEuODUzMzExNzcxNTExODA4LCAxLjg1NjgyMTI5MjE5NDk1OCwgMS44NjAzMzA4MTI4NzgxMDgyLCAxLjg2Mzg0MDMzMzU2MTI1ODEsIDEuODY3MzQ5ODU0MjQ0NDA4NSwgMS44NzA4NTkzNzQ5Mjc1NTg0LCAxLjg3NDM2ODg5NTYxMDcwODUsIDEuODc3ODc4NDE2MjkzODU4NCwgMS44ODEzODc5MzY5NzcwMDg2LCAxLjg4NDg5NzQ1NzY2MDE1ODcsIDEuODg4NDA2OTc4MzQzMzA4NiwgMS44OTE5MTY0OTkwMjY0NTg4LCAxLjg5NTQyNjAxOTcwOTYwODcsIDEuODk4OTM1NTQwMzkyNzU5LCAxLjkwMjQ0NTA2MTA3NTkwOSwgMS45MDU5NTQ1ODE3NTkwNTksIDEuOTA5NDY0MTAyNDQyMjA5LCAxLjkxMjk3MzYyMzEyNTM1OTEsIDEuOTE2NDgzMTQzODA4NTA5MywgMS45MTk5OTI2NjQ0OTE2NTkyLCAxLjkyMzUwMjE4NTE3NDgwOTMsIDEuOTI3MDExNzA1ODU3OTU5MiwgMS45MzA1MjEyMjY1NDExMDk0LCAxLjkzNDAzMDc0NzIyNDI1OTVdKQogICAgICAgICAgICAgIC5yYW5nZShbJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MyddKTsKICAgIAoKICAgIGNvbG9yX21hcF8wM2Y3Nzc2MjgyMmY0MzE5YTZiZTlmNGU2YmRkYTNkMi54ID0gZDMuc2NhbGUubGluZWFyKCkKICAgICAgICAgICAgICAuZG9tYWluKFswLjE4Mjc3OTkyNjMzMjM3ODQ3LCAxLjkzNDAzMDc0NzIyNDI1OTVdKQogICAgICAgICAgICAgIC5yYW5nZShbMCwgNDAwXSk7CgogICAgY29sb3JfbWFwXzAzZjc3NzYyODIyZjQzMTlhNmJlOWY0ZTZiZGRhM2QyLmxlZ2VuZCA9IEwuY29udHJvbCh7cG9zaXRpb246ICd0b3ByaWdodCd9KTsKICAgIGNvbG9yX21hcF8wM2Y3Nzc2MjgyMmY0MzE5YTZiZTlmNGU2YmRkYTNkMi5sZWdlbmQub25BZGQgPSBmdW5jdGlvbiAobWFwKSB7dmFyIGRpdiA9IEwuRG9tVXRpbC5jcmVhdGUoJ2RpdicsICdsZWdlbmQnKTsgcmV0dXJuIGRpdn07CiAgICBjb2xvcl9tYXBfMDNmNzc3NjI4MjJmNDMxOWE2YmU5ZjRlNmJkZGEzZDIubGVnZW5kLmFkZFRvKG1hcF84YWU1NzEyZTQ3YWI0NjQ5YjhhYjk0NDgzOWIwNDYyMik7CgogICAgY29sb3JfbWFwXzAzZjc3NzYyODIyZjQzMTlhNmJlOWY0ZTZiZGRhM2QyLnhBeGlzID0gZDMuc3ZnLmF4aXMoKQogICAgICAgIC5zY2FsZShjb2xvcl9tYXBfMDNmNzc3NjI4MjJmNDMxOWE2YmU5ZjRlNmJkZGEzZDIueCkKICAgICAgICAub3JpZW50KCJ0b3AiKQogICAgICAgIC50aWNrU2l6ZSgxKQogICAgICAgIC50aWNrVmFsdWVzKFswLjE4Mjc3OTkyNjMzMjM3ODQ3LCAwLjQ3NDY1NTA2MzE0NzY5MiwgMC43NjY1MzAxOTk5NjMwMDU1LCAxLjA1ODQwNTMzNjc3ODMxOSwgMS4zNTAyODA0NzM1OTM2MzI1LCAxLjY0MjE1NTYxMDQwODk0NTksIDEuOTM0MDMwNzQ3MjI0MjU5NV0pOwoKICAgIGNvbG9yX21hcF8wM2Y3Nzc2MjgyMmY0MzE5YTZiZTlmNGU2YmRkYTNkMi5zdmcgPSBkMy5zZWxlY3QoIi5sZWdlbmQubGVhZmxldC1jb250cm9sIikuYXBwZW5kKCJzdmciKQogICAgICAgIC5hdHRyKCJpZCIsICdsZWdlbmQnKQogICAgICAgIC5hdHRyKCJ3aWR0aCIsIDQ1MCkKICAgICAgICAuYXR0cigiaGVpZ2h0IiwgNDApOwoKICAgIGNvbG9yX21hcF8wM2Y3Nzc2MjgyMmY0MzE5YTZiZTlmNGU2YmRkYTNkMi5nID0gY29sb3JfbWFwXzAzZjc3NzYyODIyZjQzMTlhNmJlOWY0ZTZiZGRhM2QyLnN2Zy5hcHBlbmQoImciKQogICAgICAgIC5hdHRyKCJjbGFzcyIsICJrZXkiKQogICAgICAgIC5hdHRyKCJ0cmFuc2Zvcm0iLCAidHJhbnNsYXRlKDI1LDE2KSIpOwoKICAgIGNvbG9yX21hcF8wM2Y3Nzc2MjgyMmY0MzE5YTZiZTlmNGU2YmRkYTNkMi5nLnNlbGVjdEFsbCgicmVjdCIpCiAgICAgICAgLmRhdGEoY29sb3JfbWFwXzAzZjc3NzYyODIyZjQzMTlhNmJlOWY0ZTZiZGRhM2QyLmNvbG9yLnJhbmdlKCkubWFwKGZ1bmN0aW9uKGQsIGkpIHsKICAgICAgICAgIHJldHVybiB7CiAgICAgICAgICAgIHgwOiBpID8gY29sb3JfbWFwXzAzZjc3NzYyODIyZjQzMTlhNmJlOWY0ZTZiZGRhM2QyLngoY29sb3JfbWFwXzAzZjc3NzYyODIyZjQzMTlhNmJlOWY0ZTZiZGRhM2QyLmNvbG9yLmRvbWFpbigpW2kgLSAxXSkgOiBjb2xvcl9tYXBfMDNmNzc3NjI4MjJmNDMxOWE2YmU5ZjRlNmJkZGEzZDIueC5yYW5nZSgpWzBdLAogICAgICAgICAgICB4MTogaSA8IGNvbG9yX21hcF8wM2Y3Nzc2MjgyMmY0MzE5YTZiZTlmNGU2YmRkYTNkMi5jb2xvci5kb21haW4oKS5sZW5ndGggPyBjb2xvcl9tYXBfMDNmNzc3NjI4MjJmNDMxOWE2YmU5ZjRlNmJkZGEzZDIueChjb2xvcl9tYXBfMDNmNzc3NjI4MjJmNDMxOWE2YmU5ZjRlNmJkZGEzZDIuY29sb3IuZG9tYWluKClbaV0pIDogY29sb3JfbWFwXzAzZjc3NzYyODIyZjQzMTlhNmJlOWY0ZTZiZGRhM2QyLngucmFuZ2UoKVsxXSwKICAgICAgICAgICAgejogZAogICAgICAgICAgfTsKICAgICAgICB9KSkKICAgICAgLmVudGVyKCkuYXBwZW5kKCJyZWN0IikKICAgICAgICAuYXR0cigiaGVpZ2h0IiwgMTApCiAgICAgICAgLmF0dHIoIngiLCBmdW5jdGlvbihkKSB7IHJldHVybiBkLngwOyB9KQogICAgICAgIC5hdHRyKCJ3aWR0aCIsIGZ1bmN0aW9uKGQpIHsgcmV0dXJuIGQueDEgLSBkLngwOyB9KQogICAgICAgIC5zdHlsZSgiZmlsbCIsIGZ1bmN0aW9uKGQpIHsgcmV0dXJuIGQuejsgfSk7CgogICAgY29sb3JfbWFwXzAzZjc3NzYyODIyZjQzMTlhNmJlOWY0ZTZiZGRhM2QyLmcuY2FsbChjb2xvcl9tYXBfMDNmNzc3NjI4MjJmNDMxOWE2YmU5ZjRlNmJkZGEzZDIueEF4aXMpLmFwcGVuZCgidGV4dCIpCiAgICAgICAgLmF0dHIoImNsYXNzIiwgImNhcHRpb24iKQogICAgICAgIC5hdHRyKCJ5IiwgMjEpCiAgICAgICAgLnRleHQoJycpOwo8L3NjcmlwdD4=" style="position:absolute;width:100%;height:100%;left:0;top:0;border:none !important;" allowfullscreen webkitallowfullscreen mozallowfullscreen></iframe></div></div>




```python
map = folium.Map(location=[37.5502, 126.982], zoom_start=11, 
                 tiles='Stamen Toner')

map.choropleth(geo_data = geo_str,
               data = crime_anal_norm['검거'],
               columns = [crime_anal_norm.index, crime_anal_norm['검거']],
               fill_color = 'YlGnBu', #PuRd, YlGnBu
               key_on = 'feature.id')
map
```

    C:\ProgramData\Anaconda3\lib\site-packages\folium\folium.py:432: FutureWarning: The choropleth  method has been deprecated. Instead use the new Choropleth class, which has the same arguments. See the example notebook 'GeoJSON_and_choropleth' for how to do this.
      FutureWarning
    




<div style="width:100%;"><div style="position:relative;width:100%;height:0;padding-bottom:60%;"><iframe src="data:text/html;charset=utf-8;base64,PCFET0NUWVBFIGh0bWw+CjxoZWFkPiAgICAKICAgIDxtZXRhIGh0dHAtZXF1aXY9ImNvbnRlbnQtdHlwZSIgY29udGVudD0idGV4dC9odG1sOyBjaGFyc2V0PVVURi04IiAvPgogICAgPHNjcmlwdD5MX1BSRUZFUl9DQU5WQVM9ZmFsc2U7IExfTk9fVE9VQ0g9ZmFsc2U7IExfRElTQUJMRV8zRD1mYWxzZTs8L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmpzIj48L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2FqYXguZ29vZ2xlYXBpcy5jb20vYWpheC9saWJzL2pxdWVyeS8xLjExLjEvanF1ZXJ5Lm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvanMvYm9vdHN0cmFwLm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9jZG5qcy5jbG91ZGZsYXJlLmNvbS9hamF4L2xpYnMvTGVhZmxldC5hd2Vzb21lLW1hcmtlcnMvMi4wLjIvbGVhZmxldC5hd2Vzb21lLW1hcmtlcnMuanMiPjwvc2NyaXB0PgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL21heGNkbi5ib290c3RyYXBjZG4uY29tL2Jvb3RzdHJhcC8zLjIuMC9jc3MvYm9vdHN0cmFwLm1pbi5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvY3NzL2Jvb3RzdHJhcC10aGVtZS5taW4uY3NzIi8+CiAgICA8bGluayByZWw9InN0eWxlc2hlZXQiIGhyZWY9Imh0dHBzOi8vbWF4Y2RuLmJvb3RzdHJhcGNkbi5jb20vZm9udC1hd2Vzb21lLzQuNi4zL2Nzcy9mb250LWF3ZXNvbWUubWluLmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2NkbmpzLmNsb3VkZmxhcmUuY29tL2FqYXgvbGlicy9MZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy8yLjAuMi9sZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9yYXdjZG4uZ2l0aGFjay5jb20vcHl0aG9uLXZpc3VhbGl6YXRpb24vZm9saXVtL21hc3Rlci9mb2xpdW0vdGVtcGxhdGVzL2xlYWZsZXQuYXdlc29tZS5yb3RhdGUuY3NzIi8+CiAgICA8c3R5bGU+aHRtbCwgYm9keSB7d2lkdGg6IDEwMCU7aGVpZ2h0OiAxMDAlO21hcmdpbjogMDtwYWRkaW5nOiAwO308L3N0eWxlPgogICAgPHN0eWxlPiNtYXAge3Bvc2l0aW9uOmFic29sdXRlO3RvcDowO2JvdHRvbTowO3JpZ2h0OjA7bGVmdDowO308L3N0eWxlPgogICAgCiAgICA8bWV0YSBuYW1lPSJ2aWV3cG9ydCIgY29udGVudD0id2lkdGg9ZGV2aWNlLXdpZHRoLAogICAgICAgIGluaXRpYWwtc2NhbGU9MS4wLCBtYXhpbXVtLXNjYWxlPTEuMCwgdXNlci1zY2FsYWJsZT1ubyIgLz4KICAgIDxzdHlsZT4jbWFwX2M5ZTMzZjAxM2VjMTRlZmE4OGRlY2U2NmZiNWRiMWM3IHsKICAgICAgICBwb3NpdGlvbjogcmVsYXRpdmU7CiAgICAgICAgd2lkdGg6IDEwMC4wJTsKICAgICAgICBoZWlnaHQ6IDEwMC4wJTsKICAgICAgICBsZWZ0OiAwLjAlOwogICAgICAgIHRvcDogMC4wJTsKICAgICAgICB9CiAgICA8L3N0eWxlPgogICAgPHNjcmlwdCBzcmM9Imh0dHBzOi8vY2RuanMuY2xvdWRmbGFyZS5jb20vYWpheC9saWJzL2QzLzMuNS41L2QzLm1pbi5qcyI+PC9zY3JpcHQ+CjwvaGVhZD4KPGJvZHk+ICAgIAogICAgCiAgICA8ZGl2IGNsYXNzPSJmb2xpdW0tbWFwIiBpZD0ibWFwX2M5ZTMzZjAxM2VjMTRlZmE4OGRlY2U2NmZiNWRiMWM3IiA+PC9kaXY+CjwvYm9keT4KPHNjcmlwdD4gICAgCiAgICAKICAgIAogICAgICAgIHZhciBib3VuZHMgPSBudWxsOwogICAgCgogICAgdmFyIG1hcF9jOWUzM2YwMTNlYzE0ZWZhODhkZWNlNjZmYjVkYjFjNyA9IEwubWFwKAogICAgICAgICdtYXBfYzllMzNmMDEzZWMxNGVmYTg4ZGVjZTY2ZmI1ZGIxYzcnLCB7CiAgICAgICAgY2VudGVyOiBbMzcuNTUwMiwgMTI2Ljk4Ml0sCiAgICAgICAgem9vbTogMTEsCiAgICAgICAgbWF4Qm91bmRzOiBib3VuZHMsCiAgICAgICAgbGF5ZXJzOiBbXSwKICAgICAgICB3b3JsZENvcHlKdW1wOiBmYWxzZSwKICAgICAgICBjcnM6IEwuQ1JTLkVQU0czODU3LAogICAgICAgIHpvb21Db250cm9sOiB0cnVlLAogICAgICAgIH0pOwoKICAgIAogICAgCiAgICB2YXIgdGlsZV9sYXllcl9mNmU3OTQzNTdkNDE0MDVhODc1OTI5M2M3ZGQ0NTM0NSA9IEwudGlsZUxheWVyKAogICAgICAgICdodHRwczovL3N0YW1lbi10aWxlcy17c30uYS5zc2wuZmFzdGx5Lm5ldC90b25lci97en0ve3h9L3t5fS5wbmcnLAogICAgICAgIHsKICAgICAgICAiYXR0cmlidXRpb24iOiBudWxsLAogICAgICAgICJkZXRlY3RSZXRpbmEiOiBmYWxzZSwKICAgICAgICAibWF4TmF0aXZlWm9vbSI6IDE4LAogICAgICAgICJtYXhab29tIjogMTgsCiAgICAgICAgIm1pblpvb20iOiAwLAogICAgICAgICJub1dyYXAiOiBmYWxzZSwKICAgICAgICAib3BhY2l0eSI6IDEsCiAgICAgICAgInN1YmRvbWFpbnMiOiAiYWJjIiwKICAgICAgICAidG1zIjogZmFsc2UKfSkuYWRkVG8obWFwX2M5ZTMzZjAxM2VjMTRlZmE4OGRlY2U2NmZiNWRiMWM3KTsKICAgIAogICAgICAgICAgICB2YXIgY2hvcm9wbGV0aF82MjMxZmYzYzIxZTI0N2M2YTk5ZGY4NzM3YTA1NGMwMyA9IEwuZmVhdHVyZUdyb3VwKAogICAgICAgICAgICAgICAgKS5hZGRUbyhtYXBfYzllMzNmMDEzZWMxNGVmYTg4ZGVjZTY2ZmI1ZGIxYzcpOwogICAgICAgIAogICAgCiAgICAgICAgCiAgICAgICAgdmFyIGdlb19qc29uX2Q2MGMxYzViZWQ3MjQ4ODE5MTZlZDQ5ZjIxMDU2ZjhiID0gTC5nZW9Kc29uKAogICAgICAgICAgICB7ImZlYXR1cmVzIjogW3siZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI3LjExNTE5NTg0OTgxNjA2LCAzNy41NTc1MzMxODA3MDQ5MTVdLCBbMTI3LjE2NjgzMTg0MzY2MTI5LCAzNy41NzY3MjQ4NzM4ODYyN10sIFsxMjcuMTg0MDg3OTIzMzAxNTIsIDM3LjU1ODE0MjgwMzY5NTc1XSwgWzEyNy4xNjUzMDk4NDMwNzQ0NywgMzcuNTQyMjE4NTEyNTg2OTNdLCBbMTI3LjE0NjcyODA2ODIzNTAyLCAzNy41MTQxNTY4MDY4MDI5MV0sIFsxMjcuMTIxMjMxNjU3MTk2MTUsIDM3LjUyNTI4MjcwMDg5XSwgWzEyNy4xMTE2NzY0MjAzNjA4LCAzNy41NDA2Njk5NTUzMjQ5NjVdLCBbMTI3LjExNTE5NTg0OTgxNjA2LCAzNy41NTc1MzMxODA3MDQ5MTVdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YWMxNVx1YjNkOVx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMjUwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWFjMTVcdWIzZDlcdWFkNmMiLCAibmFtZV9lbmciOiAiR2FuZ2RvbmctZ3UiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICIjN2ZjZGJiIiwgImZpbGxPcGFjaXR5IjogMC42LCAib3BhY2l0eSI6IDEsICJ3ZWlnaHQiOiAxfX0sICJ0eXBlIjogIkZlYXR1cmUifSwgeyJnZW9tZXRyeSI6IHsiY29vcmRpbmF0ZXMiOiBbW1sxMjcuMDY5MDY5ODEzMDM3MiwgMzcuNTIyMjc5NDIzNTA1MDI2XSwgWzEyNy4xMDA4NzUxOTc5MTk2MiwgMzcuNTI0ODQxMjIwMTY3MDU1XSwgWzEyNy4xMTE2NzY0MjAzNjA4LCAzNy41NDA2Njk5NTUzMjQ5NjVdLCBbMTI3LjEyMTIzMTY1NzE5NjE1LCAzNy41MjUyODI3MDA4OV0sIFsxMjcuMTQ2NzI4MDY4MjM1MDIsIDM3LjUxNDE1NjgwNjgwMjkxXSwgWzEyNy4xNjM0OTQ0MjE1NzY1LCAzNy40OTc0NDU0MDYwOTc0ODRdLCBbMTI3LjE0MjA2MDU4NDEzMjc0LCAzNy40NzA4OTgxOTA5ODUwMV0sIFsxMjcuMTI0NDA1NzEwODA4OTMsIDM3LjQ2MjQwNDQ1NTg3MDQ4XSwgWzEyNy4xMTExNzA4NTIwMTIzOCwgMzcuNDg1NzA4MzgxNTEyNDQ1XSwgWzEyNy4wNzE5MTQ2MDAwNzI0LCAzNy41MDIyNDAxMzU4NzY2OV0sIFsxMjcuMDY5MDY5ODEzMDM3MiwgMzcuNTIyMjc5NDIzNTA1MDI2XV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJcdWMxYTFcdWQzMGNcdWFkNmMiLCAicHJvcGVydGllcyI6IHsiYmFzZV95ZWFyIjogIjIwMTMiLCAiY29kZSI6ICIxMTI0MCIsICJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiXHVjMWExXHVkMzBjXHVhZDZjIiwgIm5hbWVfZW5nIjogIlNvbmdwYS1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiM3ZmNkYmIiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wNTg2NzM1OTI4ODM5OCwgMzcuNTI2Mjk5NzQ5MjI1NjhdLCBbMTI3LjA2OTA2OTgxMzAzNzIsIDM3LjUyMjI3OTQyMzUwNTAyNl0sIFsxMjcuMDcxOTE0NjAwMDcyNCwgMzcuNTAyMjQwMTM1ODc2NjldLCBbMTI3LjExMTE3MDg1MjAxMjM4LCAzNy40ODU3MDgzODE1MTI0NDVdLCBbMTI3LjEyNDQwNTcxMDgwODkzLCAzNy40NjI0MDQ0NTU4NzA0OF0sIFsxMjcuMDk4NDI3NTkzMTg3NTEsIDM3LjQ1ODYyMjUzODU3NDYxXSwgWzEyNy4wODY0MDQ0MDU3ODE1NiwgMzcuNDcyNjk3OTM1MTg0NjU1XSwgWzEyNy4wNTU5MTcwNDgxOTA0LCAzNy40NjU5MjI4OTE0MDc3XSwgWzEyNy4wMzYyMTkxNTA5ODc5OCwgMzcuNDgxNzU4MDI0Mjc2MDNdLCBbMTI3LjAxMzk3MTE5NjY3NTEzLCAzNy41MjUwMzk4ODI4OTY2OV0sIFsxMjcuMDIzMDI4MzE4OTA1NTksIDM3LjUzMjMxODk5NTgyNjYzXSwgWzEyNy4wNTg2NzM1OTI4ODM5OCwgMzcuNTI2Mjk5NzQ5MjI1NjhdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YWMxNVx1YjBhOFx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMjMwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWFjMTVcdWIwYThcdWFkNmMiLCAibmFtZV9lbmciOiAiR2FuZ25hbS1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiM3ZmNkYmIiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wMTM5NzExOTY2NzUxMywgMzcuNTI1MDM5ODgyODk2NjldLCBbMTI3LjAzNjIxOTE1MDk4Nzk4LCAzNy40ODE3NTgwMjQyNzYwM10sIFsxMjcuMDU1OTE3MDQ4MTkwNCwgMzcuNDY1OTIyODkxNDA3N10sIFsxMjcuMDg2NDA0NDA1NzgxNTYsIDM3LjQ3MjY5NzkzNTE4NDY1NV0sIFsxMjcuMDk4NDI3NTkzMTg3NTEsIDM3LjQ1ODYyMjUzODU3NDYxXSwgWzEyNy4wOTA0NjkyODU2NTk1MSwgMzcuNDQyOTY4MjYxMTQxODVdLCBbMTI3LjA2Nzc4MTA3NjA1NDMzLCAzNy40MjYxOTc0MjQwNTczMTRdLCBbMTI3LjA0OTU3MjMyOTg3MTQyLCAzNy40MjgwNTgzNjg0NTY5NF0sIFsxMjcuMDM4ODE3ODI1OTc5MjIsIDM3LjQ1MzgyMDM5ODUxNzE1XSwgWzEyNi45OTA3MjA3MzE5NTQ2MiwgMzcuNDU1MzI2MTQzMzEwMDI1XSwgWzEyNi45ODM2NzY2ODI5MTgwMiwgMzcuNDczODU2NDkyNjkyMDg2XSwgWzEyNi45ODIyMzgwNzkxNjA4MSwgMzcuNTA5MzE0OTY2NzcwMzI2XSwgWzEyNy4wMTM5NzExOTY2NzUxMywgMzcuNTI1MDM5ODgyODk2NjldXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzExY1x1Y2QwOFx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMjIwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWMxMWNcdWNkMDhcdWFkNmMiLCAibmFtZV9lbmciOiAiU2VvY2hvLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2ZmZmZjYyIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2Ljk4MzY3NjY4MjkxODAyLCAzNy40NzM4NTY0OTI2OTIwODZdLCBbMTI2Ljk5MDcyMDczMTk1NDYyLCAzNy40NTUzMjYxNDMzMTAwMjVdLCBbMTI2Ljk2NTIwNDM5MDg1MTQzLCAzNy40MzgyNDk3ODQwMDYyNDZdLCBbMTI2Ljk1MDAwMDAxMDEwMTgyLCAzNy40MzYxMzQ1MTE2NTcxOV0sIFsxMjYuOTMwODQ0MDgwNTY1MjUsIDM3LjQ0NzM4MjkyODMzMzk5NF0sIFsxMjYuOTE2NzcyODE0NjYwMSwgMzcuNDU0OTA1NjY0MjM3ODldLCBbMTI2LjkwMTU2MDk0MTI5ODk1LCAzNy40Nzc1Mzg0Mjc4OTkwMV0sIFsxMjYuOTA1MzE5NzU4MDE4MTIsIDM3LjQ4MjE4MDg3NTc1NDI5XSwgWzEyNi45NDkyMjY2MTM4OTUwOCwgMzcuNDkxMjU0Mzc0OTU2NDldLCBbMTI2Ljk3MjU4OTE4NTA2NjIsIDM3LjQ3MjU2MTM2MzI3ODEyNV0sIFsxMjYuOTgzNjc2NjgyOTE4MDIsIDM3LjQ3Mzg1NjQ5MjY5MjA4Nl1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVhZDAwXHVjNTQ1XHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEyMTAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YWQwMFx1YzU0NVx1YWQ2YyIsICJuYW1lX2VuZyI6ICJHd2FuYWstZ3UiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICIjN2ZjZGJiIiwgImZpbGxPcGFjaXR5IjogMC42LCAib3BhY2l0eSI6IDEsICJ3ZWlnaHQiOiAxfX0sICJ0eXBlIjogIkZlYXR1cmUifSwgeyJnZW9tZXRyeSI6IHsiY29vcmRpbmF0ZXMiOiBbW1sxMjYuOTgyMjM4MDc5MTYwODEsIDM3LjUwOTMxNDk2Njc3MDMyNl0sIFsxMjYuOTgzNjc2NjgyOTE4MDIsIDM3LjQ3Mzg1NjQ5MjY5MjA4Nl0sIFsxMjYuOTcyNTg5MTg1MDY2MiwgMzcuNDcyNTYxMzYzMjc4MTI1XSwgWzEyNi45NDkyMjY2MTM4OTUwOCwgMzcuNDkxMjU0Mzc0OTU2NDldLCBbMTI2LjkwNTMxOTc1ODAxODEyLCAzNy40ODIxODA4NzU3NTQyOV0sIFsxMjYuOTIxNzc4OTMxNzQ4MjUsIDM3LjQ5NDg4OTg3NzQxNTE3Nl0sIFsxMjYuOTI4MTA2Mjg4MjgyNzksIDM3LjUxMzI5NTk1NzMyMDE1XSwgWzEyNi45NTI0OTk5MDI5ODE1OSwgMzcuNTE3MjI1MDA3NDE4MTNdLCBbMTI2Ljk4MjIzODA3OTE2MDgxLCAzNy41MDkzMTQ5NjY3NzAzMjZdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YjNkOVx1Yzc5MVx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMjAwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWIzZDlcdWM3OTFcdWFkNmMiLCAibmFtZV9lbmciOiAiRG9uZ2phay1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNmZmZmY2MiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNi44OTE4NDY2Mzg2Mjc2NCwgMzcuNTQ3MzczOTc0OTk3MTE0XSwgWzEyNi45NDU2NjczMzA4MzIxMiwgMzcuNTI2NjE3NTQyNDUzMzY2XSwgWzEyNi45NTI0OTk5MDI5ODE1OSwgMzcuNTE3MjI1MDA3NDE4MTNdLCBbMTI2LjkyODEwNjI4ODI4Mjc5LCAzNy41MTMyOTU5NTczMjAxNV0sIFsxMjYuOTIxNzc4OTMxNzQ4MjUsIDM3LjQ5NDg4OTg3NzQxNTE3Nl0sIFsxMjYuOTA1MzE5NzU4MDE4MTIsIDM3LjQ4MjE4MDg3NTc1NDI5XSwgWzEyNi44OTU5NDc3Njc4MjQ4NSwgMzcuNTA0Njc1MjgxMzA5MTc2XSwgWzEyNi44ODE1NjQwMjM1Mzg2MiwgMzcuNTEzOTcwMDM0NzY1Njg0XSwgWzEyNi44ODgyNTc1Nzg2MDA5OSwgMzcuNTQwNzk3MzM2MzAyMzJdLCBbMTI2Ljg5MTg0NjYzODYyNzY0LCAzNy41NDczNzM5NzQ5OTcxMTRdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzYwMVx1YjRmMVx1ZDNlY1x1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMTkwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWM2MDFcdWI0ZjFcdWQzZWNcdWFkNmMiLCAibmFtZV9lbmciOiAiWWVvbmdkZXVuZ3BvLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2M3ZTliNCIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2LjkwMTU2MDk0MTI5ODk1LCAzNy40Nzc1Mzg0Mjc4OTkwMV0sIFsxMjYuOTE2NzcyODE0NjYwMSwgMzcuNDU0OTA1NjY0MjM3ODldLCBbMTI2LjkzMDg0NDA4MDU2NTI1LCAzNy40NDczODI5MjgzMzM5OTRdLCBbMTI2LjkwMjU4MzE3MTE2OTcsIDM3LjQzNDU0OTM2NjM0OTEyNF0sIFsxMjYuODc2ODMyNzE1MDI0MjgsIDM3LjQ4MjU3NjU5MTYwNzMwNV0sIFsxMjYuOTAxNTYwOTQxMjk4OTUsIDM3LjQ3NzUzODQyNzg5OTAxXV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJcdWFlMDhcdWNjOWNcdWFkNmMiLCAicHJvcGVydGllcyI6IHsiYmFzZV95ZWFyIjogIjIwMTMiLCAiY29kZSI6ICIxMTE4MCIsICJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiXHVhZTA4XHVjYzljXHVhZDZjIiwgIm5hbWVfZW5nIjogIkdldW1jaGVvbi1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiMyNTM0OTQiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNi44MjY4ODA4MTUxNzMxNCwgMzcuNTA1NDg5NzIyMzI4OTZdLCBbMTI2Ljg4MTU2NDAyMzUzODYyLCAzNy41MTM5NzAwMzQ3NjU2ODRdLCBbMTI2Ljg5NTk0Nzc2NzgyNDg1LCAzNy41MDQ2NzUyODEzMDkxNzZdLCBbMTI2LjkwNTMxOTc1ODAxODEyLCAzNy40ODIxODA4NzU3NTQyOV0sIFsxMjYuOTAxNTYwOTQxMjk4OTUsIDM3LjQ3NzUzODQyNzg5OTAxXSwgWzEyNi44NzY4MzI3MTUwMjQyOCwgMzcuNDgyNTc2NTkxNjA3MzA1XSwgWzEyNi44NDc2MjY3NjA1NDk1MywgMzcuNDcxNDY3MjM5MzYzMjNdLCBbMTI2LjgzNTQ5NDg1MDc2MTk2LCAzNy40NzQwOTgyMzY5NzUwOTVdLCBbMTI2LjgyMjY0Nzk2NzkxMzQ4LCAzNy40ODc4NDc2NDkyMTQ3XSwgWzEyNi44MjUwNDczNjMzMTQwNiwgMzcuNTAzMDI2MTI2NDA0NDNdLCBbMTI2LjgyNjg4MDgxNTE3MzE0LCAzNy41MDU0ODk3MjIzMjg5Nl1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVhZDZjXHViODVjXHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTExNzAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YWQ2Y1x1Yjg1Y1x1YWQ2YyIsICJuYW1lX2VuZyI6ICJHdXJvLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2ZmZmZjYyIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2Ljc5NTc1NzY4NTUyOTA3LCAzNy41Nzg4MTA4NzYzMzIwMl0sIFsxMjYuODA3MDIxMTUwMjM1OTcsIDM3LjYwMTIzMDAxMDEzMjI4XSwgWzEyNi44MjI1MTQzODQ3NzEwNSwgMzcuNTg4MDQzMDgxMDA4Ml0sIFsxMjYuODU5ODQxOTkzOTk2NjcsIDM3LjU3MTg0Nzg1NTI5Mjc0NV0sIFsxMjYuODkxODQ2NjM4NjI3NjQsIDM3LjU0NzM3Mzk3NDk5NzExNF0sIFsxMjYuODg4MjU3NTc4NjAwOTksIDM3LjU0MDc5NzMzNjMwMjMyXSwgWzEyNi44NjYzNzQ2NDMyMTIzOCwgMzcuNTQ4NTkxOTEwOTQ4MjNdLCBbMTI2Ljg2NjEwMDczNDc2Mzk1LCAzNy41MjY5OTk2NDE0NDY2OV0sIFsxMjYuODQyNTcyOTE5NDMxNTMsIDM3LjUyMzczNzA3ODA1NTk2XSwgWzEyNi44MjQyMzMxNDI2NzIyLCAzNy41Mzc4ODA3ODc1MzI0OF0sIFsxMjYuNzczMjQ0MTc3MTc3MDMsIDM3LjU0NTkxMjM0NTA1NTRdLCBbMTI2Ljc2OTc5MTgwNTc5MzUyLCAzNy41NTEzOTE4MzAwODgwOV0sIFsxMjYuNzk1NzU3Njg1NTI5MDcsIDM3LjU3ODgxMDg3NjMzMjAyXV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJcdWFjMTVcdWMxMWNcdWFkNmMiLCAicHJvcGVydGllcyI6IHsiYmFzZV95ZWFyIjogIjIwMTMiLCAiY29kZSI6ICIxMTE2MCIsICJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiXHVhYzE1XHVjMTFjXHVhZDZjIiwgIm5hbWVfZW5nIjogIkdhbmdzZW8tZ3UiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICJibGFjayIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2LjgyNDIzMzE0MjY3MjIsIDM3LjUzNzg4MDc4NzUzMjQ4XSwgWzEyNi44NDI1NzI5MTk0MzE1MywgMzcuNTIzNzM3MDc4MDU1OTZdLCBbMTI2Ljg2NjEwMDczNDc2Mzk1LCAzNy41MjY5OTk2NDE0NDY2OV0sIFsxMjYuODY2Mzc0NjQzMjEyMzgsIDM3LjU0ODU5MTkxMDk0ODIzXSwgWzEyNi44ODgyNTc1Nzg2MDA5OSwgMzcuNTQwNzk3MzM2MzAyMzJdLCBbMTI2Ljg4MTU2NDAyMzUzODYyLCAzNy41MTM5NzAwMzQ3NjU2ODRdLCBbMTI2LjgyNjg4MDgxNTE3MzE0LCAzNy41MDU0ODk3MjIzMjg5Nl0sIFsxMjYuODI0MjMzMTQyNjcyMiwgMzcuNTM3ODgwNzg3NTMyNDhdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzU5MVx1Y2M5Y1x1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMTUwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWM1OTFcdWNjOWNcdWFkNmMiLCAibmFtZV9lbmciOiAiWWFuZ2NoZW9uLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiIzQxYjZjNCIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2LjkwNTIyMDY1ODMxMDUzLCAzNy41NzQwOTcwMDUyMjU3NF0sIFsxMjYuOTM4OTgxNjE3OTg5NzMsIDM3LjU1MjMxMDAwMzcyODEyNF0sIFsxMjYuOTYzNTgyMjY3MTA4MTIsIDM3LjU1NjA1NjM1NDc1MTU0XSwgWzEyNi45NjQ0ODU3MDU1MzA1NSwgMzcuNTQ4NzA1NjkyMDIxNjM1XSwgWzEyNi45NDU2NjczMzA4MzIxMiwgMzcuNTI2NjE3NTQyNDUzMzY2XSwgWzEyNi44OTE4NDY2Mzg2Mjc2NCwgMzcuNTQ3MzczOTc0OTk3MTE0XSwgWzEyNi44NTk4NDE5OTM5OTY2NywgMzcuNTcxODQ3ODU1MjkyNzQ1XSwgWzEyNi44ODQzMzI4NDc3MzI4OCwgMzcuNTg4MTQzMzIyODgwNTI2XSwgWzEyNi45MDUyMjA2NTgzMTA1MywgMzcuNTc0MDk3MDA1MjI1NzRdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YjljOFx1ZDNlY1x1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMTQwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWI5YzhcdWQzZWNcdWFkNmMiLCAibmFtZV9lbmciOiAiTWFwby1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiM3ZmNkYmIiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNi45NTI0NzUyMDMwNTcyLCAzNy42MDUwODY5MjczNzA0NV0sIFsxMjYuOTU1NjU0MjU4NDY0NjMsIDM3LjU3NjA4MDc5MDg4MTQ1Nl0sIFsxMjYuOTY4NzM2MzMyNzkwNzUsIDM3LjU2MzEzNjA0NjkwODI3XSwgWzEyNi45NjM1ODIyNjcxMDgxMiwgMzcuNTU2MDU2MzU0NzUxNTRdLCBbMTI2LjkzODk4MTYxNzk4OTczLCAzNy41NTIzMTAwMDM3MjgxMjRdLCBbMTI2LjkwNTIyMDY1ODMxMDUzLCAzNy41NzQwOTcwMDUyMjU3NF0sIFsxMjYuOTUyNDc1MjAzMDU3MiwgMzcuNjA1MDg2OTI3MzcwNDVdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzExY1x1YjMwMFx1YmIzOFx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMTMwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWMxMWNcdWIzMDBcdWJiMzhcdWFkNmMiLCAibmFtZV9lbmciOiAiU2VvZGFlbXVuLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiIzQxYjZjNCIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2Ljk3Mzg4NjQxMjg3MDIsIDM3LjYyOTQ5NjM0Nzg2ODg4XSwgWzEyNi45NTQyNzAxNzAwNjEyOSwgMzcuNjIyMDMzNDMxMzM5NDI1XSwgWzEyNi45NTI0NzUyMDMwNTcyLCAzNy42MDUwODY5MjczNzA0NV0sIFsxMjYuOTA1MjIwNjU4MzEwNTMsIDM3LjU3NDA5NzAwNTIyNTc0XSwgWzEyNi44ODQzMzI4NDc3MzI4OCwgMzcuNTg4MTQzMzIyODgwNTI2XSwgWzEyNi45MDM5NjY4MTAwMzU5NSwgMzcuNTkyMjc0MDM0MTk5NDJdLCBbMTI2LjkwMzAzMDY2MTc3NjY4LCAzNy42MDk5Nzc5MTE0MDEzNDRdLCBbMTI2LjkxNDU1NDgxNDI5NjQ4LCAzNy42NDE1MDA1MDk5NjkzNV0sIFsxMjYuOTU2NDczNzk3Mzg3LCAzNy42NTI0ODA3MzczMzk0NDVdLCBbMTI2Ljk3Mzg4NjQxMjg3MDIsIDM3LjYyOTQ5NjM0Nzg2ODg4XV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJcdWM3NDBcdWQzYzlcdWFkNmMiLCAicHJvcGVydGllcyI6IHsiYmFzZV95ZWFyIjogIjIwMTMiLCAiY29kZSI6ICIxMTEyMCIsICJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiXHVjNzQwXHVkM2M5XHVhZDZjIiwgIm5hbWVfZW5nIjogIkV1bnB5ZW9uZy1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiM3ZmNkYmIiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wODM4NzUyNzAzMTk1LCAzNy42OTM1OTUzNDIwMjAzNF0sIFsxMjcuMDk3MDYzOTEzMDk2OTUsIDM3LjY4NjM4MzcxOTM3MjI5NF0sIFsxMjcuMDk0NDA3NjYyOTg3MTcsIDM3LjY0NzEzNDkwNDczMDQ1XSwgWzEyNy4xMTMyNjc5NTg1NTE5OSwgMzcuNjM5NjIyOTA1MzE1OTI1XSwgWzEyNy4xMDc4MjI3NzY4ODEyOSwgMzcuNjE4MDQyNDQyNDEwNjldLCBbMTI3LjA3MzUxMjQzODI1Mjc4LCAzNy42MTI4MzY2MDM0MjMxM10sIFsxMjcuMDUyMDkzNzM1Njg2MTksIDM3LjYyMTY0MDY1NDg3NzgyXSwgWzEyNy4wNDM1ODgwMDg5NTYwOSwgMzcuNjI4NDg5MzEyOTg3MTVdLCBbMTI3LjA1ODAwMDc1MjIwMDkxLCAzNy42NDMxODI2Mzg3ODI3Nl0sIFsxMjcuMDUyODg0Nzk3MTA0ODUsIDM3LjY4NDIzODU3MDg0MzQ3XSwgWzEyNy4wODM4NzUyNzAzMTk1LCAzNy42OTM1OTUzNDIwMjAzNF1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHViMTc4XHVjNmQwXHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTExMTAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YjE3OFx1YzZkMFx1YWQ2YyIsICJuYW1lX2VuZyI6ICJOb3dvbi1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiM0MWI2YzQiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wNTI4ODQ3OTcxMDQ4NSwgMzcuNjg0MjM4NTcwODQzNDddLCBbMTI3LjA1ODAwMDc1MjIwMDkxLCAzNy42NDMxODI2Mzg3ODI3Nl0sIFsxMjcuMDQzNTg4MDA4OTU2MDksIDM3LjYyODQ4OTMxMjk4NzE1XSwgWzEyNy4wMTQ2NTkzNTg5MjQ2NiwgMzcuNjQ5NDM2ODc0OTY4MTJdLCBbMTI3LjAyMDYyMTE2MTQxMzg5LCAzNy42NjcxNzM1NzU5NzEyMDVdLCBbMTI3LjAxMDM5NjY2MDQyMDcxLCAzNy42ODE4OTQ1ODk2MDM1OTRdLCBbMTI3LjAxNzk1MDk5MjAzNDMyLCAzNy42OTgyNDQxMjc3NTY2Ml0sIFsxMjcuMDUyODg0Nzk3MTA0ODUsIDM3LjY4NDIzODU3MDg0MzQ3XV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJcdWIzYzRcdWJkMDlcdWFkNmMiLCAicHJvcGVydGllcyI6IHsiYmFzZV95ZWFyIjogIjIwMTMiLCAiY29kZSI6ICIxMTEwMCIsICJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiXHViM2M0XHViZDA5XHVhZDZjIiwgIm5hbWVfZW5nIjogIkRvYm9uZy1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiMyNTM0OTQiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNi45OTM4MzkwMzQyNCwgMzcuNjc2NjgxNzYxMTk5MDg1XSwgWzEyNy4wMTAzOTY2NjA0MjA3MSwgMzcuNjgxODk0NTg5NjAzNTk0XSwgWzEyNy4wMjA2MjExNjE0MTM4OSwgMzcuNjY3MTczNTc1OTcxMjA1XSwgWzEyNy4wMTQ2NTkzNTg5MjQ2NiwgMzcuNjQ5NDM2ODc0OTY4MTJdLCBbMTI3LjA0MzU4ODAwODk1NjA5LCAzNy42Mjg0ODkzMTI5ODcxNV0sIFsxMjcuMDUyMDkzNzM1Njg2MTksIDM3LjYyMTY0MDY1NDg3NzgyXSwgWzEyNy4wMzg5MjQwMDk5MjMwMSwgMzcuNjA5NzE1NjExMDIzODE2XSwgWzEyNy4wMTI4MTU0NzQ5NTIzLCAzNy42MTM2NTIyNDM0NzAyNTZdLCBbMTI2Ljk4NjcyNzA1NTEzODY5LCAzNy42MzM3NzY0MTI4ODE5Nl0sIFsxMjYuOTgxNzQ1MjY3NjU1MSwgMzcuNjUyMDk3NjkzODc3NzZdLCBbMTI2Ljk5MzgzOTAzNDI0LCAzNy42NzY2ODE3NjExOTkwODVdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YWMxNVx1YmQ4MVx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMDkwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWFjMTVcdWJkODFcdWFkNmMiLCAibmFtZV9lbmciOiAiR2FuZ2J1ay1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiMyYzdmYjgiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNi45NzcxNzU0MDY0MTYsIDM3LjYyODU5NzE1NDAwMzg4XSwgWzEyNi45ODY3MjcwNTUxMzg2OSwgMzcuNjMzNzc2NDEyODgxOTZdLCBbMTI3LjAxMjgxNTQ3NDk1MjMsIDM3LjYxMzY1MjI0MzQ3MDI1Nl0sIFsxMjcuMDM4OTI0MDA5OTIzMDEsIDM3LjYwOTcxNTYxMTAyMzgxNl0sIFsxMjcuMDUyMDkzNzM1Njg2MTksIDM3LjYyMTY0MDY1NDg3NzgyXSwgWzEyNy4wNzM1MTI0MzgyNTI3OCwgMzcuNjEyODM2NjAzNDIzMTNdLCBbMTI3LjA3MzgyNzA3MDk5MjI3LCAzNy42MDQwMTkyODk4NjQxOV0sIFsxMjcuMDQyNzA1MjIyMDk0LCAzNy41OTIzOTQzNzU5MzM5MV0sIFsxMjcuMDI1MjcyNTQ1MjgwMDMsIDM3LjU3NTI0NjE2MjQ1MjQ5XSwgWzEyNi45OTM0ODI5MzM1ODMxNCwgMzcuNTg4NTY1NDU3MjE2MTU2XSwgWzEyNi45ODg3OTg2NTk5MjM4NCwgMzcuNjExODkyNzMxOTc1Nl0sIFsxMjYuOTc3MTc1NDA2NDE2LCAzNy42Mjg1OTcxNTQwMDM4OF1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVjMTMxXHViZDgxXHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEwODAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YzEzMVx1YmQ4MVx1YWQ2YyIsICJuYW1lX2VuZyI6ICJTZW9uZ2J1ay1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiM0MWI2YzQiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wNzM1MTI0MzgyNTI3OCwgMzcuNjEyODM2NjAzNDIzMTNdLCBbMTI3LjEwNzgyMjc3Njg4MTI5LCAzNy42MTgwNDI0NDI0MTA2OV0sIFsxMjcuMTIwMTI0NjAyMDExNCwgMzcuNjAxNzg0NTc1OTgxODhdLCBbMTI3LjEwMzA0MTc0MjQ5MjE0LCAzNy41NzA3NjM0MjI5MDk1NV0sIFsxMjcuMDgwNjg1NDEyODA0MDMsIDM3LjU2OTA2NDI1NTE5MDE3XSwgWzEyNy4wNzM4MjcwNzA5OTIyNywgMzcuNjA0MDE5Mjg5ODY0MTldLCBbMTI3LjA3MzUxMjQzODI1Mjc4LCAzNy42MTI4MzY2MDM0MjMxM11dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVjOTExXHViNzkxXHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEwNzAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YzkxMVx1Yjc5MVx1YWQ2YyIsICJuYW1lX2VuZyI6ICJKdW5nbmFuZy1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiM0MWI2YzQiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wMjUyNzI1NDUyODAwMywgMzcuNTc1MjQ2MTYyNDUyNDldLCBbMTI3LjA0MjcwNTIyMjA5NCwgMzcuNTkyMzk0Mzc1OTMzOTFdLCBbMTI3LjA3MzgyNzA3MDk5MjI3LCAzNy42MDQwMTkyODk4NjQxOV0sIFsxMjcuMDgwNjg1NDEyODA0MDMsIDM3LjU2OTA2NDI1NTE5MDE3XSwgWzEyNy4wNzQyMTA1MzAyNDM2MiwgMzcuNTU3MjQ3Njk3MTIwODVdLCBbMTI3LjA1MDA1NjAxMDgxNTY3LCAzNy41Njc1Nzc2MTI1OTA4NDZdLCBbMTI3LjAyNTQ3MjY2MzQ5OTc2LCAzNy41Njg5NDM1NTIyMzc3MzRdLCBbMTI3LjAyNTI3MjU0NTI4MDAzLCAzNy41NzUyNDYxNjI0NTI0OV1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHViM2Q5XHViMzAwXHViYjM4XHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEwNjAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YjNkOVx1YjMwMFx1YmIzOFx1YWQ2YyIsICJuYW1lX2VuZyI6ICJEb25nZGFlbXVuLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiIzJjN2ZiOCIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI3LjA4MDY4NTQxMjgwNDAzLCAzNy41NjkwNjQyNTUxOTAxN10sIFsxMjcuMTAzMDQxNzQyNDkyMTQsIDM3LjU3MDc2MzQyMjkwOTU1XSwgWzEyNy4xMTUxOTU4NDk4MTYwNiwgMzcuNTU3NTMzMTgwNzA0OTE1XSwgWzEyNy4xMTE2NzY0MjAzNjA4LCAzNy41NDA2Njk5NTUzMjQ5NjVdLCBbMTI3LjEwMDg3NTE5NzkxOTYyLCAzNy41MjQ4NDEyMjAxNjcwNTVdLCBbMTI3LjA2OTA2OTgxMzAzNzIsIDM3LjUyMjI3OTQyMzUwNTAyNl0sIFsxMjcuMDU4NjczNTkyODgzOTgsIDM3LjUyNjI5OTc0OTIyNTY4XSwgWzEyNy4wNzQyMTA1MzAyNDM2MiwgMzcuNTU3MjQ3Njk3MTIwODVdLCBbMTI3LjA4MDY4NTQxMjgwNDAzLCAzNy41NjkwNjQyNTUxOTAxN11dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVhZDExXHVjOWM0XHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEwNTAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YWQxMVx1YzljNFx1YWQ2YyIsICJuYW1lX2VuZyI6ICJHd2FuZ2ppbi1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiMyNTM0OTQiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wMjU0NzI2NjM0OTk3NiwgMzcuNTY4OTQzNTUyMjM3NzM0XSwgWzEyNy4wNTAwNTYwMTA4MTU2NywgMzcuNTY3NTc3NjEyNTkwODQ2XSwgWzEyNy4wNzQyMTA1MzAyNDM2MiwgMzcuNTU3MjQ3Njk3MTIwODVdLCBbMTI3LjA1ODY3MzU5Mjg4Mzk4LCAzNy41MjYyOTk3NDkyMjU2OF0sIFsxMjcuMDIzMDI4MzE4OTA1NTksIDM3LjUzMjMxODk5NTgyNjYzXSwgWzEyNy4wMTA3MDg5NDE3NzQ4MiwgMzcuNTQxMTgwNDg5NjQ3NjJdLCBbMTI3LjAyNTQ3MjY2MzQ5OTc2LCAzNy41Njg5NDM1NTIyMzc3MzRdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzEzMVx1YjNkOVx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMDQwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWMxMzFcdWIzZDlcdWFkNmMiLCAibmFtZV9lbmciOiAiU2Vvbmdkb25nLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiIzJjN2ZiOCIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI3LjAxMDcwODk0MTc3NDgyLCAzNy41NDExODA0ODk2NDc2Ml0sIFsxMjcuMDIzMDI4MzE4OTA1NTksIDM3LjUzMjMxODk5NTgyNjYzXSwgWzEyNy4wMTM5NzExOTY2NzUxMywgMzcuNTI1MDM5ODgyODk2NjldLCBbMTI2Ljk4MjIzODA3OTE2MDgxLCAzNy41MDkzMTQ5NjY3NzAzMjZdLCBbMTI2Ljk1MjQ5OTkwMjk4MTU5LCAzNy41MTcyMjUwMDc0MTgxM10sIFsxMjYuOTQ1NjY3MzMwODMyMTIsIDM3LjUyNjYxNzU0MjQ1MzM2Nl0sIFsxMjYuOTY0NDg1NzA1NTMwNTUsIDM3LjU0ODcwNTY5MjAyMTYzNV0sIFsxMjYuOTg3NTI5OTY5MDMzMjgsIDM3LjU1MDk0ODE4ODA3MTM5XSwgWzEyNy4wMTA3MDg5NDE3NzQ4MiwgMzcuNTQxMTgwNDg5NjQ3NjJdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzZhOVx1YzBiMFx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMDMwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWM2YTlcdWMwYjBcdWFkNmMiLCAibmFtZV9lbmciOiAiWW9uZ3Nhbi1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiMyYzdmYjgiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wMjU0NzI2NjM0OTk3NiwgMzcuNTY4OTQzNTUyMjM3NzM0XSwgWzEyNy4wMTA3MDg5NDE3NzQ4MiwgMzcuNTQxMTgwNDg5NjQ3NjJdLCBbMTI2Ljk4NzUyOTk2OTAzMzI4LCAzNy41NTA5NDgxODgwNzEzOV0sIFsxMjYuOTY0NDg1NzA1NTMwNTUsIDM3LjU0ODcwNTY5MjAyMTYzNV0sIFsxMjYuOTYzNTgyMjY3MTA4MTIsIDM3LjU1NjA1NjM1NDc1MTU0XSwgWzEyNi45Njg3MzYzMzI3OTA3NSwgMzcuNTYzMTM2MDQ2OTA4MjddLCBbMTI3LjAyNTQ3MjY2MzQ5OTc2LCAzNy41Njg5NDM1NTIyMzc3MzRdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzkxMVx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMDIwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWM5MTFcdWFkNmMiLCAibmFtZV9lbmciOiAiSnVuZy1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNmZmZmY2MiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNi45NzM4ODY0MTI4NzAyLCAzNy42Mjk0OTYzNDc4Njg4OF0sIFsxMjYuOTc3MTc1NDA2NDE2LCAzNy42Mjg1OTcxNTQwMDM4OF0sIFsxMjYuOTg4Nzk4NjU5OTIzODQsIDM3LjYxMTg5MjczMTk3NTZdLCBbMTI2Ljk5MzQ4MjkzMzU4MzE0LCAzNy41ODg1NjU0NTcyMTYxNTZdLCBbMTI3LjAyNTI3MjU0NTI4MDAzLCAzNy41NzUyNDYxNjI0NTI0OV0sIFsxMjcuMDI1NDcyNjYzNDk5NzYsIDM3LjU2ODk0MzU1MjIzNzczNF0sIFsxMjYuOTY4NzM2MzMyNzkwNzUsIDM3LjU2MzEzNjA0NjkwODI3XSwgWzEyNi45NTU2NTQyNTg0NjQ2MywgMzcuNTc2MDgwNzkwODgxNDU2XSwgWzEyNi45NTI0NzUyMDMwNTcyLCAzNy42MDUwODY5MjczNzA0NV0sIFsxMjYuOTU0MjcwMTcwMDYxMjksIDM3LjYyMjAzMzQzMTMzOTQyNV0sIFsxMjYuOTczODg2NDEyODcwMiwgMzcuNjI5NDk2MzQ3ODY4ODhdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1Yzg4NVx1Yjg1Y1x1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMDEwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWM4ODVcdWI4NWNcdWFkNmMiLCAibmFtZV9lbmciOiAiSm9uZ25vLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiIzdmY2RiYiIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn1dLCAidHlwZSI6ICJGZWF0dXJlQ29sbGVjdGlvbiJ9CiAgICAgICAgICAgIAogICAgICAgICAgICApLmFkZFRvKGNob3JvcGxldGhfNjIzMWZmM2MyMWUyNDdjNmE5OWRmODczN2EwNTRjMDMpOwogICAgICAgIGdlb19qc29uX2Q2MGMxYzViZWQ3MjQ4ODE5MTZlZDQ5ZjIxMDU2ZjhiLnNldFN0eWxlKGZ1bmN0aW9uKGZlYXR1cmUpIHtyZXR1cm4gZmVhdHVyZS5wcm9wZXJ0aWVzLnN0eWxlO30pOwogICAgICAgIAogICAgCiAgICB2YXIgY29sb3JfbWFwX2Q4NmU3MWM3ZDIzNjQ2YmU4NTc4YjhhZWU5ZDM1YjlkID0ge307CgogICAgCiAgICBjb2xvcl9tYXBfZDg2ZTcxYzdkMjM2NDZiZTg1NzhiOGFlZTlkMzViOWQuY29sb3IgPSBkMy5zY2FsZS50aHJlc2hvbGQoKQogICAgICAgICAgICAgIC5kb21haW4oWzc0LjEyMjY1MDAzNzkwOTUxLCA3NC4xNzQ1MDg0NTQ2NjcyLCA3NC4yMjYzNjY4NzE0MjQ5LCA3NC4yNzgyMjUyODgxODI2LCA3NC4zMzAwODM3MDQ5NDAzLCA3NC4zODE5NDIxMjE2OTc5OSwgNzQuNDMzODAwNTM4NDU1NjksIDc0LjQ4NTY1ODk1NTIxMzM4LCA3NC41Mzc1MTczNzE5NzEwOCwgNzQuNTg5Mzc1Nzg4NzI4NzcsIDc0LjY0MTIzNDIwNTQ4NjQ3LCA3NC42OTMwOTI2MjIyNDQxNywgNzQuNzQ0OTUxMDM5MDAxODYsIDc0Ljc5NjgwOTQ1NTc1OTU2LCA3NC44NDg2Njc4NzI1MTcyNSwgNzQuOTAwNTI2Mjg5Mjc0OTUsIDc0Ljk1MjM4NDcwNjAzMjY1LCA3NS4wMDQyNDMxMjI3OTAzNCwgNzUuMDU2MTAxNTM5NTQ4MDQsIDc1LjEwNzk1OTk1NjMwNTczLCA3NS4xNTk4MTgzNzMwNjM0MywgNzUuMjExNjc2Nzg5ODIxMTMsIDc1LjI2MzUzNTIwNjU3ODgyLCA3NS4zMTUzOTM2MjMzMzY1MiwgNzUuMzY3MjUyMDQwMDk0MjEsIDc1LjQxOTExMDQ1Njg1MTkxLCA3NS40NzA5Njg4NzM2MDk2MiwgNzUuNTIyODI3MjkwMzY3MzIsIDc1LjU3NDY4NTcwNzEyNTAxLCA3NS42MjY1NDQxMjM4ODI3MSwgNzUuNjc4NDAyNTQwNjQwNCwgNzUuNzMwMjYwOTU3Mzk4MSwgNzUuNzgyMTE5Mzc0MTU1OCwgNzUuODMzOTc3NzkwOTEzNSwgNzUuODg1ODM2MjA3NjcxMTksIDc1LjkzNzY5NDYyNDQyODg5LCA3NS45ODk1NTMwNDExODY1OCwgNzYuMDQxNDExNDU3OTQ0MjgsIDc2LjA5MzI2OTg3NDcwMTk3LCA3Ni4xNDUxMjgyOTE0NTk2NywgNzYuMTk2OTg2NzA4MjE3MzcsIDc2LjI0ODg0NTEyNDk3NTA2LCA3Ni4zMDA3MDM1NDE3MzI3NiwgNzYuMzUyNTYxOTU4NDkwNDUsIDc2LjQwNDQyMDM3NTI0ODE1LCA3Ni40NTYyNzg3OTIwMDU4NSwgNzYuNTA4MTM3MjA4NzYzNTQsIDc2LjU1OTk5NTYyNTUyMTI0LCA3Ni42MTE4NTQwNDIyNzg5NCwgNzYuNjYzNzEyNDU5MDM2NjMsIDc2LjcxNTU3MDg3NTc5NDMzLCA3Ni43Njc0MjkyOTI1NTIwMiwgNzYuODE5Mjg3NzA5MzA5NzIsIDc2Ljg3MTE0NjEyNjA2NzQyLCA3Ni45MjMwMDQ1NDI4MjUxMSwgNzYuOTc0ODYyOTU5NTgyODEsIDc3LjAyNjcyMTM3NjM0MDUsIDc3LjA3ODU3OTc5MzA5ODIsIDc3LjEzMDQzODIwOTg1NTksIDc3LjE4MjI5NjYyNjYxMzU5LCA3Ny4yMzQxNTUwNDMzNzEyOSwgNzcuMjg2MDEzNDYwMTI4OTgsIDc3LjMzNzg3MTg3Njg4NjY4LCA3Ny4zODk3MzAyOTM2NDQzOCwgNzcuNDQxNTg4NzEwNDAyMDcsIDc3LjQ5MzQ0NzEyNzE1OTc3LCA3Ny41NDUzMDU1NDM5MTc0NiwgNzcuNTk3MTYzOTYwNjc1MTYsIDc3LjY0OTAyMjM3NzQzMjg2LCA3Ny43MDA4ODA3OTQxOTA1NSwgNzcuNzUyNzM5MjEwOTQ4MjUsIDc3LjgwNDU5NzYyNzcwNTk1LCA3Ny44NTY0NTYwNDQ0NjM2NCwgNzcuOTA4MzE0NDYxMjIxMzQsIDc3Ljk2MDE3Mjg3Nzk3OTAzLCA3OC4wMTIwMzEyOTQ3MzY3NCwgNzguMDYzODg5NzExNDk0NDQsIDc4LjExNTc0ODEyODI1MjE0LCA3OC4xNjc2MDY1NDUwMDk4MywgNzguMjE5NDY0OTYxNzY3NTMsIDc4LjI3MTMyMzM3ODUyNTIyLCA3OC4zMjMxODE3OTUyODI5MiwgNzguMzc1MDQwMjEyMDQwNjIsIDc4LjQyNjg5ODYyODc5ODMxLCA3OC40Nzg3NTcwNDU1NTYwMSwgNzguNTMwNjE1NDYyMzEzNywgNzguNTgyNDczODc5MDcxNCwgNzguNjM0MzMyMjk1ODI5MSwgNzguNjg2MTkwNzEyNTg2OCwgNzguNzM4MDQ5MTI5MzQ0NDksIDc4Ljc4OTkwNzU0NjEwMjE5LCA3OC44NDE3NjU5NjI4NTk4OCwgNzguODkzNjI0Mzc5NjE3NTgsIDc4Ljk0NTQ4Mjc5NjM3NTI3LCA3OC45OTczNDEyMTMxMzI5NywgNzkuMDQ5MTk5NjI5ODkwNjcsIDc5LjEwMTA1ODA0NjY0ODM2LCA3OS4xNTI5MTY0NjM0MDYwNiwgNzkuMjA0Nzc0ODgwMTYzNzUsIDc5LjI1NjYzMzI5NjkyMTQ1LCA3OS4zMDg0OTE3MTM2NzkxNSwgNzkuMzYwMzUwMTMwNDM2ODQsIDc5LjQxMjIwODU0NzE5NDU0LCA3OS40NjQwNjY5NjM5NTIyMywgNzkuNTE1OTI1MzgwNzA5OTMsIDc5LjU2Nzc4Mzc5NzQ2NzYzLCA3OS42MTk2NDIyMTQyMjUzMiwgNzkuNjcxNTAwNjMwOTgzMDIsIDc5LjcyMzM1OTA0Nzc0MDcxLCA3OS43NzUyMTc0NjQ0OTg0MSwgNzkuODI3MDc1ODgxMjU2MSwgNzkuODc4OTM0Mjk4MDEzOCwgNzkuOTMwNzkyNzE0NzcxNSwgNzkuOTgyNjUxMTMxNTI5MiwgODAuMDM0NTA5NTQ4Mjg2ODksIDgwLjA4NjM2Nzk2NTA0NDU5LCA4MC4xMzgyMjYzODE4MDIyOCwgODAuMTkwMDg0Nzk4NTU5OTgsIDgwLjI0MTk0MzIxNTMxNzY4LCA4MC4yOTM4MDE2MzIwNzUzNywgODAuMzQ1NjYwMDQ4ODMzMDcsIDgwLjM5NzUxODQ2NTU5MDc2LCA4MC40NDkzNzY4ODIzNDg0NiwgODAuNTAxMjM1Mjk5MTA2MTYsIDgwLjU1MzA5MzcxNTg2Mzg1LCA4MC42MDQ5NTIxMzI2MjE1NSwgODAuNjU2ODEwNTQ5Mzc5MjYsIDgwLjcwODY2ODk2NjEzNjk0LCA4MC43NjA1MjczODI4OTQ2NSwgODAuODEyMzg1Nzk5NjUyMzUsIDgwLjg2NDI0NDIxNjQxMDA0LCA4MC45MTYxMDI2MzMxNjc3NCwgODAuOTY3OTYxMDQ5OTI1NDQsIDgxLjAxOTgxOTQ2NjY4MzEzLCA4MS4wNzE2Nzc4ODM0NDA4MywgODEuMTIzNTM2MzAwMTk4NTIsIDgxLjE3NTM5NDcxNjk1NjIyLCA4MS4yMjcyNTMxMzM3MTM5MiwgODEuMjc5MTExNTUwNDcxNjEsIDgxLjMzMDk2OTk2NzIyOTMxLCA4MS4zODI4MjgzODM5ODcsIDgxLjQzNDY4NjgwMDc0NDcsIDgxLjQ4NjU0NTIxNzUwMjQsIDgxLjUzODQwMzYzNDI2MDA5LCA4MS41OTAyNjIwNTEwMTc3OSwgODEuNjQyMTIwNDY3Nzc1NDgsIDgxLjY5Mzk3ODg4NDUzMzE4LCA4MS43NDU4MzczMDEyOTA4OCwgODEuNzk3Njk1NzE4MDQ4NTcsIDgxLjg0OTU1NDEzNDgwNjI3LCA4MS45MDE0MTI1NTE1NjM5NiwgODEuOTUzMjcwOTY4MzIxNjYsIDgyLjAwNTEyOTM4NTA3OTM2LCA4Mi4wNTY5ODc4MDE4MzcwNSwgODIuMTA4ODQ2MjE4NTk0NzUsIDgyLjE2MDcwNDYzNTM1MjQ1LCA4Mi4yMTI1NjMwNTIxMTAxNCwgODIuMjY0NDIxNDY4ODY3ODQsIDgyLjMxNjI3OTg4NTYyNTUzLCA4Mi4zNjgxMzgzMDIzODMyMywgODIuNDE5OTk2NzE5MTQwOTMsIDgyLjQ3MTg1NTEzNTg5ODYyLCA4Mi41MjM3MTM1NTI2NTYzMiwgODIuNTc1NTcxOTY5NDE0MDEsIDgyLjYyNzQzMDM4NjE3MTcxLCA4Mi42NzkyODg4MDI5Mjk0LCA4Mi43MzExNDcyMTk2ODcxLCA4Mi43ODMwMDU2MzY0NDQ4LCA4Mi44MzQ4NjQwNTMyMDI1LCA4Mi44ODY3MjI0Njk5NjAxOSwgODIuOTM4NTgwODg2NzE3ODksIDgyLjk5MDQzOTMwMzQ3NTU4LCA4My4wNDIyOTc3MjAyMzMyOCwgODMuMDk0MTU2MTM2OTkwOTksIDgzLjE0NjAxNDU1Mzc0ODY3LCA4My4xOTc4NzI5NzA1MDYzOCwgODMuMjQ5NzMxMzg3MjY0MDYsIDgzLjMwMTU4OTgwNDAyMTc3LCA4My4zNTM0NDgyMjA3Nzk0NiwgODMuNDA1MzA2NjM3NTM3MTcsIDgzLjQ1NzE2NTA1NDI5NDg2LCA4My41MDkwMjM0NzEwNTI1NiwgODMuNTYwODgxODg3ODEwMjUsIDgzLjYxMjc0MDMwNDU2Nzk1LCA4My42NjQ1OTg3MjEzMjU2NSwgODMuNzE2NDU3MTM4MDgzMzQsIDgzLjc2ODMxNTU1NDg0MTA0LCA4My44MjAxNzM5NzE1OTg3MywgODMuODcyMDMyMzg4MzU2NDMsIDgzLjkyMzg5MDgwNTExNDEzLCA4My45NzU3NDkyMjE4NzE4MiwgODQuMDI3NjA3NjM4NjI5NTIsIDg0LjA3OTQ2NjA1NTM4NzIxLCA4NC4xMzEzMjQ0NzIxNDQ5MSwgODQuMTgzMTgyODg4OTAyNiwgODQuMjM1MDQxMzA1NjYwMywgODQuMjg2ODk5NzIyNDE4LCA4NC4zMzg3NTgxMzkxNzU3LCA4NC4zOTA2MTY1NTU5MzMzOSwgODQuNDQyNDc0OTcyNjkxMDksIDg0LjQ5NDMzMzM4OTQ0ODc4LCA4NC41NDYxOTE4MDYyMDY0OCwgODQuNTk4MDUwMjIyOTY0MTgsIDg0LjY0OTkwODYzOTcyMTg3LCA4NC43MDE3NjcwNTY0Nzk1NywgODQuNzUzNjI1NDczMjM3MjYsIDg0LjgwNTQ4Mzg4OTk5NDk2LCA4NC44NTczNDIzMDY3NTI2NiwgODQuOTA5MjAwNzIzNTEwMzUsIDg0Ljk2MTA1OTE0MDI2ODA1LCA4NS4wMTI5MTc1NTcwMjU3NCwgODUuMDY0Nzc1OTczNzgzNDQsIDg1LjExNjYzNDM5MDU0MTE0LCA4NS4xNjg0OTI4MDcyOTg4MywgODUuMjIwMzUxMjI0MDU2NTMsIDg1LjI3MjIwOTY0MDgxNDIyLCA4NS4zMjQwNjgwNTc1NzE5MiwgODUuMzc1OTI2NDc0MzI5NjIsIDg1LjQyNzc4NDg5MTA4NzMxLCA4NS40Nzk2NDMzMDc4NDUwMSwgODUuNTMxNTAxNzI0NjAyNywgODUuNTgzMzYwMTQxMzYwNCwgODUuNjM1MjE4NTU4MTE4MTEsIDg1LjY4NzA3Njk3NDg3NTgsIDg1LjczODkzNTM5MTYzMzUsIDg1Ljc5MDc5MzgwODM5MTE5LCA4NS44NDI2NTIyMjUxNDg5LCA4NS44OTQ1MTA2NDE5MDY1OCwgODUuOTQ2MzY5MDU4NjY0MjksIDg1Ljk5ODIyNzQ3NTQyMTk4LCA4Ni4wNTAwODU4OTIxNzk2OCwgODYuMTAxOTQ0MzA4OTM3MzgsIDg2LjE1MzgwMjcyNTY5NTA3LCA4Ni4yMDU2NjExNDI0NTI3NywgODYuMjU3NTE5NTU5MjEwNDYsIDg2LjMwOTM3Nzk3NTk2ODE2LCA4Ni4zNjEyMzYzOTI3MjU4NiwgODYuNDEzMDk0ODA5NDgzNTUsIDg2LjQ2NDk1MzIyNjI0MTI1LCA4Ni41MTY4MTE2NDI5OTg5NSwgODYuNTY4NjcwMDU5NzU2NjQsIDg2LjYyMDUyODQ3NjUxNDM0LCA4Ni42NzIzODY4OTMyNzIwMywgODYuNzI0MjQ1MzEwMDI5NzMsIDg2Ljc3NjEwMzcyNjc4NzQzLCA4Ni44Mjc5NjIxNDM1NDUxMiwgODYuODc5ODIwNTYwMzAyODIsIDg2LjkzMTY3ODk3NzA2MDUxLCA4Ni45ODM1MzczOTM4MTgyMSwgODcuMDM1Mzk1ODEwNTc1OSwgODcuMDg3MjU0MjI3MzMzNiwgODcuMTM5MTEyNjQ0MDkxMywgODcuMTkwOTcxMDYwODQ5LCA4Ny4yNDI4Mjk0Nzc2MDY2OSwgODcuMjk0Njg3ODk0MzY0MzksIDg3LjM0NjU0NjMxMTEyMjA4LCA4Ny4zOTg0MDQ3Mjc4Nzk3OCwgODcuNDUwMjYzMTQ0NjM3NDcsIDg3LjUwMjEyMTU2MTM5NTE3LCA4Ny41NTM5Nzk5NzgxNTI4NywgODcuNjA1ODM4Mzk0OTEwNTYsIDg3LjY1NzY5NjgxMTY2ODI2LCA4Ny43MDk1NTUyMjg0MjU5NiwgODcuNzYxNDEzNjQ1MTgzNjUsIDg3LjgxMzI3MjA2MTk0MTM1LCA4Ny44NjUxMzA0Nzg2OTkwNCwgODcuOTE2OTg4ODk1NDU2NzQsIDg3Ljk2ODg0NzMxMjIxNDQ0LCA4OC4wMjA3MDU3Mjg5NzIxMywgODguMDcyNTY0MTQ1NzI5ODMsIDg4LjEyNDQyMjU2MjQ4NzUyLCA4OC4xNzYyODA5NzkyNDUyMywgODguMjI4MTM5Mzk2MDAyOTIsIDg4LjI3OTk5NzgxMjc2MDYzLCA4OC4zMzE4NTYyMjk1MTgzMSwgODguMzgzNzE0NjQ2Mjc2MDIsIDg4LjQzNTU3MzA2MzAzMzcxLCA4OC40ODc0MzE0Nzk3OTE0MSwgODguNTM5Mjg5ODk2NTQ5MDksIDg4LjU5MTE0ODMxMzMwNjgsIDg4LjY0MzAwNjczMDA2NDUsIDg4LjY5NDg2NTE0NjgyMjIsIDg4Ljc0NjcyMzU2MzU3OTg5LCA4OC43OTg1ODE5ODAzMzc1OSwgODguODUwNDQwMzk3MDk1MjgsIDg4LjkwMjI5ODgxMzg1Mjk4LCA4OC45NTQxNTcyMzA2MTA2OCwgODkuMDA2MDE1NjQ3MzY4MzcsIDg5LjA1Nzg3NDA2NDEyNjA3LCA4OS4xMDk3MzI0ODA4ODM3NiwgODkuMTYxNTkwODk3NjQxNDYsIDg5LjIxMzQ0OTMxNDM5OTE2LCA4OS4yNjUzMDc3MzExNTY4NSwgODkuMzE3MTY2MTQ3OTE0NTUsIDg5LjM2OTAyNDU2NDY3MjI0LCA4OS40MjA4ODI5ODE0Mjk5NCwgODkuNDcyNzQxMzk4MTg3NjQsIDg5LjUyNDU5OTgxNDk0NTMzLCA4OS41NzY0NTgyMzE3MDMwMywgODkuNjI4MzE2NjQ4NDYwNzIsIDg5LjY4MDE3NTA2NTIxODQyLCA4OS43MzIwMzM0ODE5NzYxMiwgODkuNzgzODkxODk4NzMzODEsIDg5LjgzNTc1MDMxNTQ5MTUxLCA4OS44ODc2MDg3MzIyNDkyLCA4OS45Mzk0NjcxNDkwMDY5LCA4OS45OTEzMjU1NjU3NjQ2LCA5MC4wNDMxODM5ODI1MjIzLCA5MC4wOTUwNDIzOTkyNzk5OSwgOTAuMTQ2OTAwODE2MDM3NjksIDkwLjE5ODc1OTIzMjc5NTM4LCA5MC4yNTA2MTc2NDk1NTMwOCwgOTAuMzAyNDc2MDY2MzEwNzcsIDkwLjM1NDMzNDQ4MzA2ODQ3LCA5MC40MDYxOTI4OTk4MjYxNywgOTAuNDU4MDUxMzE2NTgzODYsIDkwLjUwOTkwOTczMzM0MTU2LCA5MC41NjE3NjgxNTAwOTkyNSwgOTAuNjEzNjI2NTY2ODU2OTUsIDkwLjY2NTQ4NDk4MzYxNDY1LCA5MC43MTczNDM0MDAzNzIzNCwgOTAuNzY5MjAxODE3MTMwMDQsIDkwLjgyMTA2MDIzMzg4Nzc1LCA5MC44NzI5MTg2NTA2NDU0MywgOTAuOTI0Nzc3MDY3NDAzMTQsIDkwLjk3NjYzNTQ4NDE2MDgyLCA5MS4wMjg0OTM5MDA5MTg1MywgOTEuMDgwMzUyMzE3Njc2MjIsIDkxLjEzMjIxMDczNDQzMzkzLCA5MS4xODQwNjkxNTExOTE2MSwgOTEuMjM1OTI3NTY3OTQ5MzIsIDkxLjI4Nzc4NTk4NDcwNzAxLCA5MS4zMzk2NDQ0MDE0NjQ3MSwgOTEuMzkxNTAyODE4MjIyNCwgOTEuNDQzMzYxMjM0OTgwMSwgOTEuNDk1MjE5NjUxNzM3NzgsIDkxLjU0NzA3ODA2ODQ5NTUsIDkxLjU5ODkzNjQ4NTI1MzE5LCA5MS42NTA3OTQ5MDIwMTA4OSwgOTEuNzAyNjUzMzE4NzY4NTgsIDkxLjc1NDUxMTczNTUyNjI4LCA5MS44MDYzNzAxNTIyODM5OCwgOTEuODU4MjI4NTY5MDQxNjcsIDkxLjkxMDA4Njk4NTc5OTM3LCA5MS45NjE5NDU0MDI1NTcwNiwgOTIuMDEzODAzODE5MzE0NzYsIDkyLjA2NTY2MjIzNjA3MjQ2LCA5Mi4xMTc1MjA2NTI4MzAxNSwgOTIuMTY5Mzc5MDY5NTg3ODUsIDkyLjIyMTIzNzQ4NjM0NTU0LCA5Mi4yNzMwOTU5MDMxMDMyNCwgOTIuMzI0OTU0MzE5ODYwOTQsIDkyLjM3NjgxMjczNjYxODYzLCA5Mi40Mjg2NzExNTMzNzYzMywgOTIuNDgwNTI5NTcwMTM0MDIsIDkyLjUzMjM4Nzk4Njg5MTcyLCA5Mi41ODQyNDY0MDM2NDk0MiwgOTIuNjM2MTA0ODIwNDA3MTEsIDkyLjY4Nzk2MzIzNzE2NDgxLCA5Mi43Mzk4MjE2NTM5MjI1LCA5Mi43OTE2ODAwNzA2ODAyLCA5Mi44NDM1Mzg0ODc0Mzc5LCA5Mi44OTUzOTY5MDQxOTU2LCA5Mi45NDcyNTUzMjA5NTMyOSwgOTIuOTk5MTEzNzM3NzEwOTksIDkzLjA1MDk3MjE1NDQ2ODY4LCA5My4xMDI4MzA1NzEyMjYzOCwgOTMuMTU0Njg4OTg3OTg0MDksIDkzLjIwNjU0NzQwNDc0MTc3LCA5My4yNTg0MDU4MjE0OTk0OCwgOTMuMzEwMjY0MjM4MjU3MTYsIDkzLjM2MjEyMjY1NTAxNDg2LCA5My40MTM5ODEwNzE3NzI1NSwgOTMuNDY1ODM5NDg4NTMwMjYsIDkzLjUxNzY5NzkwNTI4Nzk1LCA5My41Njk1NTYzMjIwNDU2NiwgOTMuNjIxNDE0NzM4ODAzMzQsIDkzLjY3MzI3MzE1NTU2MTA1LCA5My43MjUxMzE1NzIzMTg3MywgOTMuNzc2OTg5OTg5MDc2NDQsIDkzLjgyODg0ODQwNTgzNDEyLCA5My44ODA3MDY4MjI1OTE4MywgOTMuOTMyNTY1MjM5MzQ5NTMsIDkzLjk4NDQyMzY1NjEwNzIzLCA5NC4wMzYyODIwNzI4NjQ5MiwgOTQuMDg4MTQwNDg5NjIyNjIsIDk0LjEzOTk5ODkwNjM4MDMxLCA5NC4xOTE4NTczMjMxMzgwMSwgOTQuMjQzNzE1NzM5ODk1NywgOTQuMjk1NTc0MTU2NjUzNCwgOTQuMzQ3NDMyNTczNDExMSwgOTQuMzk5MjkwOTkwMTY4OCwgOTQuNDUxMTQ5NDA2OTI2NDksIDk0LjUwMzAwNzgyMzY4NDE5LCA5NC41NTQ4NjYyNDA0NDE4OCwgOTQuNjA2NzI0NjU3MTk5NTgsIDk0LjY1ODU4MzA3Mzk1NzI3LCA5NC43MTA0NDE0OTA3MTQ5NywgOTQuNzYyMjk5OTA3NDcyNjcsIDk0LjgxNDE1ODMyNDIzMDM2LCA5NC44NjYwMTY3NDA5ODgwNiwgOTQuOTE3ODc1MTU3NzQ1NzUsIDk0Ljk2OTczMzU3NDUwMzQ1LCA5NS4wMjE1OTE5OTEyNjExNSwgOTUuMDczNDUwNDA4MDE4ODQsIDk1LjEyNTMwODgyNDc3NjU0LCA5NS4xNzcxNjcyNDE1MzQyNCwgOTUuMjI5MDI1NjU4MjkxOTMsIDk1LjI4MDg4NDA3NTA0OTYzLCA5NS4zMzI3NDI0OTE4MDczMiwgOTUuMzg0NjAwOTA4NTY1MDIsIDk1LjQzNjQ1OTMyNTMyMjcyLCA5NS40ODgzMTc3NDIwODA0MSwgOTUuNTQwMTc2MTU4ODM4MTEsIDk1LjU5MjAzNDU3NTU5NTgsIDk1LjY0Mzg5Mjk5MjM1MzUsIDk1LjY5NTc1MTQwOTExMTIsIDk1Ljc0NzYwOTgyNTg2ODg5LCA5NS43OTk0NjgyNDI2MjY2LCA5NS44NTEzMjY2NTkzODQyOCwgOTUuOTAzMTg1MDc2MTQyLCA5NS45NTUwNDM0OTI4OTk2OCwgOTYuMDA2OTAxOTA5NjU3MzksIDk2LjA1ODc2MDMyNjQxNTA3LCA5Ni4xMTA2MTg3NDMxNzI3OCwgOTYuMTYyNDc3MTU5OTMwNDYsIDk2LjIxNDMzNTU3NjY4ODE3LCA5Ni4yNjYxOTM5OTM0NDU4NywgOTYuMzE4MDUyNDEwMjAzNTYsIDk2LjM2OTkxMDgyNjk2MTI2LCA5Ni40MjE3NjkyNDM3MTg5NiwgOTYuNDczNjI3NjYwNDc2NjQsIDk2LjUyNTQ4NjA3NzIzNDM1LCA5Ni41NzczNDQ0OTM5OTIwNCwgOTYuNjI5MjAyOTEwNzQ5NzQsIDk2LjY4MTA2MTMyNzUwNzQ0LCA5Ni43MzI5MTk3NDQyNjUxMywgOTYuNzg0Nzc4MTYxMDIyODMsIDk2LjgzNjYzNjU3Nzc4MDUyLCA5Ni44ODg0OTQ5OTQ1MzgyMiwgOTYuOTQwMzUzNDExMjk1OTIsIDk2Ljk5MjIxMTgyODA1MzYxLCA5Ny4wNDQwNzAyNDQ4MTEzMSwgOTcuMDk1OTI4NjYxNTY5LCA5Ny4xNDc3ODcwNzgzMjY3LCA5Ny4xOTk2NDU0OTUwODQ0LCA5Ny4yNTE1MDM5MTE4NDIxLCA5Ny4zMDMzNjIzMjg1OTk3OSwgOTcuMzU1MjIwNzQ1MzU3NDksIDk3LjQwNzA3OTE2MjExNTE4LCA5Ny40NTg5Mzc1Nzg4NzI4OCwgOTcuNTEwNzk1OTk1NjMwNTcsIDk3LjU2MjY1NDQxMjM4ODI3LCA5Ny42MTQ1MTI4MjkxNDU5NywgOTcuNjY2MzcxMjQ1OTAzNjYsIDk3LjcxODIyOTY2MjY2MTM2LCA5Ny43NzAwODgwNzk0MTkwNSwgOTcuODIxOTQ2NDk2MTc2NzUsIDk3Ljg3MzgwNDkxMjkzNDQ1LCA5Ny45MjU2NjMzMjk2OTIxNCwgOTcuOTc3NTIxNzQ2NDQ5ODQsIDk4LjAyOTM4MDE2MzIwNzUzLCA5OC4wODEyMzg1Nzk5NjUyMywgOTguMTMzMDk2OTk2NzIyOTMsIDk4LjE4NDk1NTQxMzQ4MDYyLCA5OC4yMzY4MTM4MzAyMzgzMiwgOTguMjg4NjcyMjQ2OTk2MDEsIDk4LjM0MDUzMDY2Mzc1MzcxLCA5OC4zOTIzODkwODA1MTE0LCA5OC40NDQyNDc0OTcyNjkxLCA5OC40OTYxMDU5MTQwMjY4LCA5OC41NDc5NjQzMzA3ODQ1MSwgOTguNTk5ODIyNzQ3NTQyMTksIDk4LjY1MTY4MTE2NDI5OTksIDk4LjcwMzUzOTU4MTA1NzU4LCA5OC43NTUzOTc5OTc4MTUzLCA5OC44MDcyNTY0MTQ1NzI5OCwgOTguODU5MTE0ODMxMzMwNjksIDk4LjkxMDk3MzI0ODA4ODM3LCA5OC45NjI4MzE2NjQ4NDYwOCwgOTkuMDE0NjkwMDgxNjAzNzcsIDk5LjA2NjU0ODQ5ODM2MTQ3LCA5OS4xMTg0MDY5MTUxMTkxNywgOTkuMTcwMjY1MzMxODc2ODYsIDk5LjIyMjEyMzc0ODYzNDU0LCA5OS4yNzM5ODIxNjUzOTIyNSwgOTkuMzI1ODQwNTgyMTQ5OTUsIDk5LjM3NzY5ODk5ODkwNzY1LCA5OS40Mjk1NTc0MTU2NjUzNCwgOTkuNDgxNDE1ODMyNDIzMDQsIDk5LjUzMzI3NDI0OTE4MDc0LCA5OS41ODUxMzI2NjU5Mzg0MywgOTkuNjM2OTkxMDgyNjk2MTMsIDk5LjY4ODg0OTQ5OTQ1MzgyLCA5OS43NDA3MDc5MTYyMTE1MiwgOTkuNzkyNTY2MzMyOTY5MjIsIDk5Ljg0NDQyNDc0OTcyNjkxLCA5OS44OTYyODMxNjY0ODQ2MSwgOTkuOTQ4MTQxNTgzMjQyMywgMTAwLjBdKQogICAgICAgICAgICAgIC5yYW5nZShbJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNmZmZmY2MnLCAnI2ZmZmZjYycsICcjZmZmZmNjJywgJyNjN2U5YjQnLCAnI2M3ZTliNCcsICcjYzdlOWI0JywgJyNjN2U5YjQnLCAnI2M3ZTliNCcsICcjYzdlOWI0JywgJyNjN2U5YjQnLCAnI2M3ZTliNCcsICcjYzdlOWI0JywgJyNjN2U5YjQnLCAnI2M3ZTliNCcsICcjYzdlOWI0JywgJyNjN2U5YjQnLCAnI2M3ZTliNCcsICcjYzdlOWI0JywgJyNjN2U5YjQnLCAnI2M3ZTliNCcsICcjYzdlOWI0JywgJyNjN2U5YjQnLCAnI2M3ZTliNCcsICcjYzdlOWI0JywgJyNjN2U5YjQnLCAnI2M3ZTliNCcsICcjYzdlOWI0JywgJyNjN2U5YjQnLCAnI2M3ZTliNCcsICcjYzdlOWI0JywgJyNjN2U5YjQnLCAnI2M3ZTliNCcsICcjYzdlOWI0JywgJyNjN2U5YjQnLCAnI2M3ZTliNCcsICcjYzdlOWI0JywgJyNjN2U5YjQnLCAnI2M3ZTliNCcsICcjYzdlOWI0JywgJyNjN2U5YjQnLCAnI2M3ZTliNCcsICcjYzdlOWI0JywgJyNjN2U5YjQnLCAnI2M3ZTliNCcsICcjYzdlOWI0JywgJyNjN2U5YjQnLCAnI2M3ZTliNCcsICcjYzdlOWI0JywgJyNjN2U5YjQnLCAnI2M3ZTliNCcsICcjYzdlOWI0JywgJyNjN2U5YjQnLCAnI2M3ZTliNCcsICcjYzdlOWI0JywgJyNjN2U5YjQnLCAnI2M3ZTliNCcsICcjYzdlOWI0JywgJyNjN2U5YjQnLCAnI2M3ZTliNCcsICcjYzdlOWI0JywgJyNjN2U5YjQnLCAnI2M3ZTliNCcsICcjYzdlOWI0JywgJyNjN2U5YjQnLCAnI2M3ZTliNCcsICcjYzdlOWI0JywgJyNjN2U5YjQnLCAnI2M3ZTliNCcsICcjYzdlOWI0JywgJyNjN2U5YjQnLCAnI2M3ZTliNCcsICcjYzdlOWI0JywgJyNjN2U5YjQnLCAnI2M3ZTliNCcsICcjYzdlOWI0JywgJyNjN2U5YjQnLCAnI2M3ZTliNCcsICcjYzdlOWI0JywgJyNjN2U5YjQnLCAnI2M3ZTliNCcsICcjYzdlOWI0JywgJyNjN2U5YjQnLCAnI2M3ZTliNCcsICcjYzdlOWI0JywgJyNjN2U5YjQnLCAnI2M3ZTliNCcsICcjN2ZjZGJiJywgJyM3ZmNkYmInLCAnIzdmY2RiYicsICcjN2ZjZGJiJywgJyM3ZmNkYmInLCAnIzdmY2RiYicsICcjN2ZjZGJiJywgJyM3ZmNkYmInLCAnIzdmY2RiYicsICcjN2ZjZGJiJywgJyM3ZmNkYmInLCAnIzdmY2RiYicsICcjN2ZjZGJiJywgJyM3ZmNkYmInLCAnIzdmY2RiYicsICcjN2ZjZGJiJywgJyM3ZmNkYmInLCAnIzdmY2RiYicsICcjN2ZjZGJiJywgJyM3ZmNkYmInLCAnIzdmY2RiYicsICcjN2ZjZGJiJywgJyM3ZmNkYmInLCAnIzdmY2RiYicsICcjN2ZjZGJiJywgJyM3ZmNkYmInLCAnIzdmY2RiYicsICcjN2ZjZGJiJywgJyM3ZmNkYmInLCAnIzdmY2RiYicsICcjN2ZjZGJiJywgJyM3ZmNkYmInLCAnIzdmY2RiYicsICcjN2ZjZGJiJywgJyM3ZmNkYmInLCAnIzdmY2RiYicsICcjN2ZjZGJiJywgJyM3ZmNkYmInLCAnIzdmY2RiYicsICcjN2ZjZGJiJywgJyM3ZmNkYmInLCAnIzdmY2RiYicsICcjN2ZjZGJiJywgJyM3ZmNkYmInLCAnIzdmY2RiYicsICcjN2ZjZGJiJywgJyM3ZmNkYmInLCAnIzdmY2RiYicsICcjN2ZjZGJiJywgJyM3ZmNkYmInLCAnIzdmY2RiYicsICcjN2ZjZGJiJywgJyM3ZmNkYmInLCAnIzdmY2RiYicsICcjN2ZjZGJiJywgJyM3ZmNkYmInLCAnIzdmY2RiYicsICcjN2ZjZGJiJywgJyM3ZmNkYmInLCAnIzdmY2RiYicsICcjN2ZjZGJiJywgJyM3ZmNkYmInLCAnIzdmY2RiYicsICcjN2ZjZGJiJywgJyM3ZmNkYmInLCAnIzdmY2RiYicsICcjN2ZjZGJiJywgJyM3ZmNkYmInLCAnIzdmY2RiYicsICcjN2ZjZGJiJywgJyM3ZmNkYmInLCAnIzdmY2RiYicsICcjN2ZjZGJiJywgJyM3ZmNkYmInLCAnIzdmY2RiYicsICcjN2ZjZGJiJywgJyM3ZmNkYmInLCAnIzdmY2RiYicsICcjN2ZjZGJiJywgJyM3ZmNkYmInLCAnIzdmY2RiYicsICcjN2ZjZGJiJywgJyM3ZmNkYmInLCAnIzQxYjZjNCcsICcjNDFiNmM0JywgJyM0MWI2YzQnLCAnIzQxYjZjNCcsICcjNDFiNmM0JywgJyM0MWI2YzQnLCAnIzQxYjZjNCcsICcjNDFiNmM0JywgJyM0MWI2YzQnLCAnIzQxYjZjNCcsICcjNDFiNmM0JywgJyM0MWI2YzQnLCAnIzQxYjZjNCcsICcjNDFiNmM0JywgJyM0MWI2YzQnLCAnIzQxYjZjNCcsICcjNDFiNmM0JywgJyM0MWI2YzQnLCAnIzQxYjZjNCcsICcjNDFiNmM0JywgJyM0MWI2YzQnLCAnIzQxYjZjNCcsICcjNDFiNmM0JywgJyM0MWI2YzQnLCAnIzQxYjZjNCcsICcjNDFiNmM0JywgJyM0MWI2YzQnLCAnIzQxYjZjNCcsICcjNDFiNmM0JywgJyM0MWI2YzQnLCAnIzQxYjZjNCcsICcjNDFiNmM0JywgJyM0MWI2YzQnLCAnIzQxYjZjNCcsICcjNDFiNmM0JywgJyM0MWI2YzQnLCAnIzQxYjZjNCcsICcjNDFiNmM0JywgJyM0MWI2YzQnLCAnIzQxYjZjNCcsICcjNDFiNmM0JywgJyM0MWI2YzQnLCAnIzQxYjZjNCcsICcjNDFiNmM0JywgJyM0MWI2YzQnLCAnIzQxYjZjNCcsICcjNDFiNmM0JywgJyM0MWI2YzQnLCAnIzQxYjZjNCcsICcjNDFiNmM0JywgJyM0MWI2YzQnLCAnIzQxYjZjNCcsICcjNDFiNmM0JywgJyM0MWI2YzQnLCAnIzQxYjZjNCcsICcjNDFiNmM0JywgJyM0MWI2YzQnLCAnIzQxYjZjNCcsICcjNDFiNmM0JywgJyM0MWI2YzQnLCAnIzQxYjZjNCcsICcjNDFiNmM0JywgJyM0MWI2YzQnLCAnIzQxYjZjNCcsICcjNDFiNmM0JywgJyM0MWI2YzQnLCAnIzQxYjZjNCcsICcjNDFiNmM0JywgJyM0MWI2YzQnLCAnIzQxYjZjNCcsICcjNDFiNmM0JywgJyM0MWI2YzQnLCAnIzQxYjZjNCcsICcjNDFiNmM0JywgJyM0MWI2YzQnLCAnIzQxYjZjNCcsICcjNDFiNmM0JywgJyM0MWI2YzQnLCAnIzQxYjZjNCcsICcjNDFiNmM0JywgJyM0MWI2YzQnLCAnIzQxYjZjNCcsICcjNDFiNmM0JywgJyMyYzdmYjgnLCAnIzJjN2ZiOCcsICcjMmM3ZmI4JywgJyMyYzdmYjgnLCAnIzJjN2ZiOCcsICcjMmM3ZmI4JywgJyMyYzdmYjgnLCAnIzJjN2ZiOCcsICcjMmM3ZmI4JywgJyMyYzdmYjgnLCAnIzJjN2ZiOCcsICcjMmM3ZmI4JywgJyMyYzdmYjgnLCAnIzJjN2ZiOCcsICcjMmM3ZmI4JywgJyMyYzdmYjgnLCAnIzJjN2ZiOCcsICcjMmM3ZmI4JywgJyMyYzdmYjgnLCAnIzJjN2ZiOCcsICcjMmM3ZmI4JywgJyMyYzdmYjgnLCAnIzJjN2ZiOCcsICcjMmM3ZmI4JywgJyMyYzdmYjgnLCAnIzJjN2ZiOCcsICcjMmM3ZmI4JywgJyMyYzdmYjgnLCAnIzJjN2ZiOCcsICcjMmM3ZmI4JywgJyMyYzdmYjgnLCAnIzJjN2ZiOCcsICcjMmM3ZmI4JywgJyMyYzdmYjgnLCAnIzJjN2ZiOCcsICcjMmM3ZmI4JywgJyMyYzdmYjgnLCAnIzJjN2ZiOCcsICcjMmM3ZmI4JywgJyMyYzdmYjgnLCAnIzJjN2ZiOCcsICcjMmM3ZmI4JywgJyMyYzdmYjgnLCAnIzJjN2ZiOCcsICcjMmM3ZmI4JywgJyMyYzdmYjgnLCAnIzJjN2ZiOCcsICcjMmM3ZmI4JywgJyMyYzdmYjgnLCAnIzJjN2ZiOCcsICcjMmM3ZmI4JywgJyMyYzdmYjgnLCAnIzJjN2ZiOCcsICcjMmM3ZmI4JywgJyMyYzdmYjgnLCAnIzJjN2ZiOCcsICcjMmM3ZmI4JywgJyMyYzdmYjgnLCAnIzJjN2ZiOCcsICcjMmM3ZmI4JywgJyMyYzdmYjgnLCAnIzJjN2ZiOCcsICcjMmM3ZmI4JywgJyMyYzdmYjgnLCAnIzJjN2ZiOCcsICcjMmM3ZmI4JywgJyMyYzdmYjgnLCAnIzJjN2ZiOCcsICcjMmM3ZmI4JywgJyMyYzdmYjgnLCAnIzJjN2ZiOCcsICcjMmM3ZmI4JywgJyMyYzdmYjgnLCAnIzJjN2ZiOCcsICcjMmM3ZmI4JywgJyMyYzdmYjgnLCAnIzJjN2ZiOCcsICcjMmM3ZmI4JywgJyMyYzdmYjgnLCAnIzJjN2ZiOCcsICcjMmM3ZmI4JywgJyMyYzdmYjgnLCAnIzJjN2ZiOCcsICcjMjUzNDk0JywgJyMyNTM0OTQnLCAnIzI1MzQ5NCcsICcjMjUzNDk0JywgJyMyNTM0OTQnLCAnIzI1MzQ5NCcsICcjMjUzNDk0JywgJyMyNTM0OTQnLCAnIzI1MzQ5NCcsICcjMjUzNDk0JywgJyMyNTM0OTQnLCAnIzI1MzQ5NCcsICcjMjUzNDk0JywgJyMyNTM0OTQnLCAnIzI1MzQ5NCcsICcjMjUzNDk0JywgJyMyNTM0OTQnLCAnIzI1MzQ5NCcsICcjMjUzNDk0JywgJyMyNTM0OTQnLCAnIzI1MzQ5NCcsICcjMjUzNDk0JywgJyMyNTM0OTQnLCAnIzI1MzQ5NCcsICcjMjUzNDk0JywgJyMyNTM0OTQnLCAnIzI1MzQ5NCcsICcjMjUzNDk0JywgJyMyNTM0OTQnLCAnIzI1MzQ5NCcsICcjMjUzNDk0JywgJyMyNTM0OTQnLCAnIzI1MzQ5NCcsICcjMjUzNDk0JywgJyMyNTM0OTQnLCAnIzI1MzQ5NCcsICcjMjUzNDk0JywgJyMyNTM0OTQnLCAnIzI1MzQ5NCcsICcjMjUzNDk0JywgJyMyNTM0OTQnLCAnIzI1MzQ5NCcsICcjMjUzNDk0JywgJyMyNTM0OTQnLCAnIzI1MzQ5NCcsICcjMjUzNDk0JywgJyMyNTM0OTQnLCAnIzI1MzQ5NCcsICcjMjUzNDk0JywgJyMyNTM0OTQnLCAnIzI1MzQ5NCcsICcjMjUzNDk0JywgJyMyNTM0OTQnLCAnIzI1MzQ5NCcsICcjMjUzNDk0JywgJyMyNTM0OTQnLCAnIzI1MzQ5NCcsICcjMjUzNDk0JywgJyMyNTM0OTQnLCAnIzI1MzQ5NCcsICcjMjUzNDk0JywgJyMyNTM0OTQnLCAnIzI1MzQ5NCcsICcjMjUzNDk0JywgJyMyNTM0OTQnLCAnIzI1MzQ5NCcsICcjMjUzNDk0JywgJyMyNTM0OTQnLCAnIzI1MzQ5NCcsICcjMjUzNDk0JywgJyMyNTM0OTQnLCAnIzI1MzQ5NCcsICcjMjUzNDk0JywgJyMyNTM0OTQnLCAnIzI1MzQ5NCcsICcjMjUzNDk0JywgJyMyNTM0OTQnLCAnIzI1MzQ5NCcsICcjMjUzNDk0JywgJyMyNTM0OTQnLCAnIzI1MzQ5NCcsICcjMjUzNDk0JywgJyMyNTM0OTQnLCAnIzI1MzQ5NCddKTsKICAgIAoKICAgIGNvbG9yX21hcF9kODZlNzFjN2QyMzY0NmJlODU3OGI4YWVlOWQzNWI5ZC54ID0gZDMuc2NhbGUubGluZWFyKCkKICAgICAgICAgICAgICAuZG9tYWluKFs3NC4xMjI2NTAwMzc5MDk1MSwgMTAwLjBdKQogICAgICAgICAgICAgIC5yYW5nZShbMCwgNDAwXSk7CgogICAgY29sb3JfbWFwX2Q4NmU3MWM3ZDIzNjQ2YmU4NTc4YjhhZWU5ZDM1YjlkLmxlZ2VuZCA9IEwuY29udHJvbCh7cG9zaXRpb246ICd0b3ByaWdodCd9KTsKICAgIGNvbG9yX21hcF9kODZlNzFjN2QyMzY0NmJlODU3OGI4YWVlOWQzNWI5ZC5sZWdlbmQub25BZGQgPSBmdW5jdGlvbiAobWFwKSB7dmFyIGRpdiA9IEwuRG9tVXRpbC5jcmVhdGUoJ2RpdicsICdsZWdlbmQnKTsgcmV0dXJuIGRpdn07CiAgICBjb2xvcl9tYXBfZDg2ZTcxYzdkMjM2NDZiZTg1NzhiOGFlZTlkMzViOWQubGVnZW5kLmFkZFRvKG1hcF9jOWUzM2YwMTNlYzE0ZWZhODhkZWNlNjZmYjVkYjFjNyk7CgogICAgY29sb3JfbWFwX2Q4NmU3MWM3ZDIzNjQ2YmU4NTc4YjhhZWU5ZDM1YjlkLnhBeGlzID0gZDMuc3ZnLmF4aXMoKQogICAgICAgIC5zY2FsZShjb2xvcl9tYXBfZDg2ZTcxYzdkMjM2NDZiZTg1NzhiOGFlZTlkMzViOWQueCkKICAgICAgICAub3JpZW50KCJ0b3AiKQogICAgICAgIC50aWNrU2l6ZSgxKQogICAgICAgIC50aWNrVmFsdWVzKFs3NC4xMjI2NTAwMzc5MDk1MSwgNzguNDM1NTQxNjk4MjU3OTIsIDgyLjc0ODQzMzM1ODYwNjMzLCA4Ny4wNjEzMjUwMTg5NTQ3NiwgOTEuMzc0MjE2Njc5MzAzMTcsIDk1LjY4NzEwODMzOTY1MTU5LCAxMDAuMF0pOwoKICAgIGNvbG9yX21hcF9kODZlNzFjN2QyMzY0NmJlODU3OGI4YWVlOWQzNWI5ZC5zdmcgPSBkMy5zZWxlY3QoIi5sZWdlbmQubGVhZmxldC1jb250cm9sIikuYXBwZW5kKCJzdmciKQogICAgICAgIC5hdHRyKCJpZCIsICdsZWdlbmQnKQogICAgICAgIC5hdHRyKCJ3aWR0aCIsIDQ1MCkKICAgICAgICAuYXR0cigiaGVpZ2h0IiwgNDApOwoKICAgIGNvbG9yX21hcF9kODZlNzFjN2QyMzY0NmJlODU3OGI4YWVlOWQzNWI5ZC5nID0gY29sb3JfbWFwX2Q4NmU3MWM3ZDIzNjQ2YmU4NTc4YjhhZWU5ZDM1YjlkLnN2Zy5hcHBlbmQoImciKQogICAgICAgIC5hdHRyKCJjbGFzcyIsICJrZXkiKQogICAgICAgIC5hdHRyKCJ0cmFuc2Zvcm0iLCAidHJhbnNsYXRlKDI1LDE2KSIpOwoKICAgIGNvbG9yX21hcF9kODZlNzFjN2QyMzY0NmJlODU3OGI4YWVlOWQzNWI5ZC5nLnNlbGVjdEFsbCgicmVjdCIpCiAgICAgICAgLmRhdGEoY29sb3JfbWFwX2Q4NmU3MWM3ZDIzNjQ2YmU4NTc4YjhhZWU5ZDM1YjlkLmNvbG9yLnJhbmdlKCkubWFwKGZ1bmN0aW9uKGQsIGkpIHsKICAgICAgICAgIHJldHVybiB7CiAgICAgICAgICAgIHgwOiBpID8gY29sb3JfbWFwX2Q4NmU3MWM3ZDIzNjQ2YmU4NTc4YjhhZWU5ZDM1YjlkLngoY29sb3JfbWFwX2Q4NmU3MWM3ZDIzNjQ2YmU4NTc4YjhhZWU5ZDM1YjlkLmNvbG9yLmRvbWFpbigpW2kgLSAxXSkgOiBjb2xvcl9tYXBfZDg2ZTcxYzdkMjM2NDZiZTg1NzhiOGFlZTlkMzViOWQueC5yYW5nZSgpWzBdLAogICAgICAgICAgICB4MTogaSA8IGNvbG9yX21hcF9kODZlNzFjN2QyMzY0NmJlODU3OGI4YWVlOWQzNWI5ZC5jb2xvci5kb21haW4oKS5sZW5ndGggPyBjb2xvcl9tYXBfZDg2ZTcxYzdkMjM2NDZiZTg1NzhiOGFlZTlkMzViOWQueChjb2xvcl9tYXBfZDg2ZTcxYzdkMjM2NDZiZTg1NzhiOGFlZTlkMzViOWQuY29sb3IuZG9tYWluKClbaV0pIDogY29sb3JfbWFwX2Q4NmU3MWM3ZDIzNjQ2YmU4NTc4YjhhZWU5ZDM1YjlkLngucmFuZ2UoKVsxXSwKICAgICAgICAgICAgejogZAogICAgICAgICAgfTsKICAgICAgICB9KSkKICAgICAgLmVudGVyKCkuYXBwZW5kKCJyZWN0IikKICAgICAgICAuYXR0cigiaGVpZ2h0IiwgMTApCiAgICAgICAgLmF0dHIoIngiLCBmdW5jdGlvbihkKSB7IHJldHVybiBkLngwOyB9KQogICAgICAgIC5hdHRyKCJ3aWR0aCIsIGZ1bmN0aW9uKGQpIHsgcmV0dXJuIGQueDEgLSBkLngwOyB9KQogICAgICAgIC5zdHlsZSgiZmlsbCIsIGZ1bmN0aW9uKGQpIHsgcmV0dXJuIGQuejsgfSk7CgogICAgY29sb3JfbWFwX2Q4NmU3MWM3ZDIzNjQ2YmU4NTc4YjhhZWU5ZDM1YjlkLmcuY2FsbChjb2xvcl9tYXBfZDg2ZTcxYzdkMjM2NDZiZTg1NzhiOGFlZTlkMzViOWQueEF4aXMpLmFwcGVuZCgidGV4dCIpCiAgICAgICAgLmF0dHIoImNsYXNzIiwgImNhcHRpb24iKQogICAgICAgIC5hdHRyKCJ5IiwgMjEpCiAgICAgICAgLnRleHQoJycpOwo8L3NjcmlwdD4=" style="position:absolute;width:100%;height:100%;left:0;top:0;border:none !important;" allowfullscreen webkitallowfullscreen mozallowfullscreen></iframe></div></div>



# 경찰서별 검거현황과 구별 범죄발생 현황을 표현하기


```python
crime_anal_raw['lat'] = station_lat
crime_anal_raw['lng'] = station_lng

col = ['살인 검거', '강도 검거', '강간 검거', '절도 검거', '폭력 검거']
tmp = crime_anal_raw[col] / crime_anal_raw[col].max()
    
crime_anal_raw['검거'] = np.sum(tmp, axis=1)

crime_anal_raw.head()
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
      <th>관서명</th>
      <th>살인 발생</th>
      <th>살인 검거</th>
      <th>강도 발생</th>
      <th>강도 검거</th>
      <th>강간 발생</th>
      <th>강간 검거</th>
      <th>절도 발생</th>
      <th>절도 검거</th>
      <th>폭력 발생</th>
      <th>폭력 검거</th>
      <th>구별</th>
      <th>lat</th>
      <th>lng</th>
      <th>검거</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>중부서</td>
      <td>2</td>
      <td>2</td>
      <td>3</td>
      <td>2</td>
      <td>105</td>
      <td>65</td>
      <td>1395</td>
      <td>477</td>
      <td>1355</td>
      <td>1170</td>
      <td>중구</td>
      <td>37.563646</td>
      <td>126.989580</td>
      <td>1.275416</td>
    </tr>
    <tr>
      <th>1</th>
      <td>종로서</td>
      <td>3</td>
      <td>3</td>
      <td>6</td>
      <td>5</td>
      <td>115</td>
      <td>98</td>
      <td>1070</td>
      <td>413</td>
      <td>1278</td>
      <td>1070</td>
      <td>종로구</td>
      <td>37.575558</td>
      <td>126.984867</td>
      <td>1.523847</td>
    </tr>
    <tr>
      <th>2</th>
      <td>남대문서</td>
      <td>1</td>
      <td>0</td>
      <td>6</td>
      <td>4</td>
      <td>65</td>
      <td>46</td>
      <td>1153</td>
      <td>382</td>
      <td>869</td>
      <td>794</td>
      <td>중구</td>
      <td>37.554758</td>
      <td>126.973498</td>
      <td>0.907372</td>
    </tr>
    <tr>
      <th>3</th>
      <td>서대문서</td>
      <td>2</td>
      <td>2</td>
      <td>5</td>
      <td>4</td>
      <td>154</td>
      <td>124</td>
      <td>1812</td>
      <td>738</td>
      <td>2056</td>
      <td>1711</td>
      <td>서대문구</td>
      <td>37.564785</td>
      <td>126.966776</td>
      <td>1.978299</td>
    </tr>
    <tr>
      <th>4</th>
      <td>혜화서</td>
      <td>3</td>
      <td>2</td>
      <td>5</td>
      <td>4</td>
      <td>96</td>
      <td>63</td>
      <td>1114</td>
      <td>424</td>
      <td>1015</td>
      <td>861</td>
      <td>종로구</td>
      <td>37.571853</td>
      <td>126.998914</td>
      <td>1.198382</td>
    </tr>
  </tbody>
</table>
</div>




```python
map = folium.Map(location=[37.5502, 126.982], zoom_start=11)

for n in crime_anal_raw.index:
    folium.Marker([crime_anal_raw['lat'][n], 
                   crime_anal_raw['lng'][n]]).add_to(map)
    
map
```




<div style="width:100%;"><div style="position:relative;width:100%;height:0;padding-bottom:60%;"><iframe src="data:text/html;charset=utf-8;base64,PCFET0NUWVBFIGh0bWw+CjxoZWFkPiAgICAKICAgIDxtZXRhIGh0dHAtZXF1aXY9ImNvbnRlbnQtdHlwZSIgY29udGVudD0idGV4dC9odG1sOyBjaGFyc2V0PVVURi04IiAvPgogICAgPHNjcmlwdD5MX1BSRUZFUl9DQU5WQVM9ZmFsc2U7IExfTk9fVE9VQ0g9ZmFsc2U7IExfRElTQUJMRV8zRD1mYWxzZTs8L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmpzIj48L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2FqYXguZ29vZ2xlYXBpcy5jb20vYWpheC9saWJzL2pxdWVyeS8xLjExLjEvanF1ZXJ5Lm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvanMvYm9vdHN0cmFwLm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9jZG5qcy5jbG91ZGZsYXJlLmNvbS9hamF4L2xpYnMvTGVhZmxldC5hd2Vzb21lLW1hcmtlcnMvMi4wLjIvbGVhZmxldC5hd2Vzb21lLW1hcmtlcnMuanMiPjwvc2NyaXB0PgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL21heGNkbi5ib290c3RyYXBjZG4uY29tL2Jvb3RzdHJhcC8zLjIuMC9jc3MvYm9vdHN0cmFwLm1pbi5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvY3NzL2Jvb3RzdHJhcC10aGVtZS5taW4uY3NzIi8+CiAgICA8bGluayByZWw9InN0eWxlc2hlZXQiIGhyZWY9Imh0dHBzOi8vbWF4Y2RuLmJvb3RzdHJhcGNkbi5jb20vZm9udC1hd2Vzb21lLzQuNi4zL2Nzcy9mb250LWF3ZXNvbWUubWluLmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2NkbmpzLmNsb3VkZmxhcmUuY29tL2FqYXgvbGlicy9MZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy8yLjAuMi9sZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9yYXdjZG4uZ2l0aGFjay5jb20vcHl0aG9uLXZpc3VhbGl6YXRpb24vZm9saXVtL21hc3Rlci9mb2xpdW0vdGVtcGxhdGVzL2xlYWZsZXQuYXdlc29tZS5yb3RhdGUuY3NzIi8+CiAgICA8c3R5bGU+aHRtbCwgYm9keSB7d2lkdGg6IDEwMCU7aGVpZ2h0OiAxMDAlO21hcmdpbjogMDtwYWRkaW5nOiAwO308L3N0eWxlPgogICAgPHN0eWxlPiNtYXAge3Bvc2l0aW9uOmFic29sdXRlO3RvcDowO2JvdHRvbTowO3JpZ2h0OjA7bGVmdDowO308L3N0eWxlPgogICAgCiAgICA8bWV0YSBuYW1lPSJ2aWV3cG9ydCIgY29udGVudD0id2lkdGg9ZGV2aWNlLXdpZHRoLAogICAgICAgIGluaXRpYWwtc2NhbGU9MS4wLCBtYXhpbXVtLXNjYWxlPTEuMCwgdXNlci1zY2FsYWJsZT1ubyIgLz4KICAgIDxzdHlsZT4jbWFwXzBmNDQ0OTg1MGE0YTQ5OGNiMzdlNDgyZjE3NWZhMjA5IHsKICAgICAgICBwb3NpdGlvbjogcmVsYXRpdmU7CiAgICAgICAgd2lkdGg6IDEwMC4wJTsKICAgICAgICBoZWlnaHQ6IDEwMC4wJTsKICAgICAgICBsZWZ0OiAwLjAlOwogICAgICAgIHRvcDogMC4wJTsKICAgICAgICB9CiAgICA8L3N0eWxlPgo8L2hlYWQ+Cjxib2R5PiAgICAKICAgIAogICAgPGRpdiBjbGFzcz0iZm9saXVtLW1hcCIgaWQ9Im1hcF8wZjQ0NDk4NTBhNGE0OThjYjM3ZTQ4MmYxNzVmYTIwOSIgPjwvZGl2Pgo8L2JvZHk+CjxzY3JpcHQ+ICAgIAogICAgCiAgICAKICAgICAgICB2YXIgYm91bmRzID0gbnVsbDsKICAgIAoKICAgIHZhciBtYXBfMGY0NDQ5ODUwYTRhNDk4Y2IzN2U0ODJmMTc1ZmEyMDkgPSBMLm1hcCgKICAgICAgICAnbWFwXzBmNDQ0OTg1MGE0YTQ5OGNiMzdlNDgyZjE3NWZhMjA5JywgewogICAgICAgIGNlbnRlcjogWzM3LjU1MDIsIDEyNi45ODJdLAogICAgICAgIHpvb206IDExLAogICAgICAgIG1heEJvdW5kczogYm91bmRzLAogICAgICAgIGxheWVyczogW10sCiAgICAgICAgd29ybGRDb3B5SnVtcDogZmFsc2UsCiAgICAgICAgY3JzOiBMLkNSUy5FUFNHMzg1NywKICAgICAgICB6b29tQ29udHJvbDogdHJ1ZSwKICAgICAgICB9KTsKCiAgICAKICAgIAogICAgdmFyIHRpbGVfbGF5ZXJfNjMwYjIyNjliY2M4NDhmMWE0YWYyYjVkZjc0NGM2YWMgPSBMLnRpbGVMYXllcigKICAgICAgICAnaHR0cHM6Ly97c30udGlsZS5vcGVuc3RyZWV0bWFwLm9yZy97en0ve3h9L3t5fS5wbmcnLAogICAgICAgIHsKICAgICAgICAiYXR0cmlidXRpb24iOiBudWxsLAogICAgICAgICJkZXRlY3RSZXRpbmEiOiBmYWxzZSwKICAgICAgICAibWF4TmF0aXZlWm9vbSI6IDE4LAogICAgICAgICJtYXhab29tIjogMTgsCiAgICAgICAgIm1pblpvb20iOiAwLAogICAgICAgICJub1dyYXAiOiBmYWxzZSwKICAgICAgICAib3BhY2l0eSI6IDEsCiAgICAgICAgInN1YmRvbWFpbnMiOiAiYWJjIiwKICAgICAgICAidG1zIjogZmFsc2UKfSkuYWRkVG8obWFwXzBmNDQ0OTg1MGE0YTQ5OGNiMzdlNDgyZjE3NWZhMjA5KTsKICAgIAogICAgICAgIHZhciBtYXJrZXJfNGU2MmQ4OTEwZDFkNDFkYTgxY2Y1ODg3OGEyZDY4MjIgPSBMLm1hcmtlcigKICAgICAgICAgICAgWzM3LjU2MzY0NjUsIDEyNi45ODk1Nzk2XSwKICAgICAgICAgICAgewogICAgICAgICAgICAgICAgaWNvbjogbmV3IEwuSWNvbi5EZWZhdWx0KCkKICAgICAgICAgICAgICAgIH0KICAgICAgICAgICAgKS5hZGRUbyhtYXBfMGY0NDQ5ODUwYTRhNDk4Y2IzN2U0ODJmMTc1ZmEyMDkpOwogICAgICAgIAogICAgCiAgICAgICAgdmFyIG1hcmtlcl85MmE0NjUzMDAyNTE0MGI1OTVkNDk3ZTdjMTExMGEwMiA9IEwubWFya2VyKAogICAgICAgICAgICBbMzcuNTc1NTU3OCwgMTI2Ljk4NDg2NzRdLAogICAgICAgICAgICB7CiAgICAgICAgICAgICAgICBpY29uOiBuZXcgTC5JY29uLkRlZmF1bHQoKQogICAgICAgICAgICAgICAgfQogICAgICAgICAgICApLmFkZFRvKG1hcF8wZjQ0NDk4NTBhNGE0OThjYjM3ZTQ4MmYxNzVmYTIwOSk7CiAgICAgICAgCiAgICAKICAgICAgICB2YXIgbWFya2VyXzA5YjE5NmRhNjQzMDQ1ZDk4YmMwMTcyNDNmMmYwMWMwID0gTC5tYXJrZXIoCiAgICAgICAgICAgIFszNy41NTQ3NTg0LCAxMjYuOTczNDk4MV0sCiAgICAgICAgICAgIHsKICAgICAgICAgICAgICAgIGljb246IG5ldyBMLkljb24uRGVmYXVsdCgpCiAgICAgICAgICAgICAgICB9CiAgICAgICAgICAgICkuYWRkVG8obWFwXzBmNDQ0OTg1MGE0YTQ5OGNiMzdlNDgyZjE3NWZhMjA5KTsKICAgICAgICAKICAgIAogICAgICAgIHZhciBtYXJrZXJfZjE0YzIxN2M5OTY0NDM4YjhlODg2ZjAzZjkyMDYzMTkgPSBMLm1hcmtlcigKICAgICAgICAgICAgWzM3LjU2NDc4NDgsIDEyNi45NjY3NzYyXSwKICAgICAgICAgICAgewogICAgICAgICAgICAgICAgaWNvbjogbmV3IEwuSWNvbi5EZWZhdWx0KCkKICAgICAgICAgICAgICAgIH0KICAgICAgICAgICAgKS5hZGRUbyhtYXBfMGY0NDQ5ODUwYTRhNDk4Y2IzN2U0ODJmMTc1ZmEyMDkpOwogICAgICAgIAogICAgCiAgICAgICAgdmFyIG1hcmtlcl8yMjg5MjIzZWY4NzU0YTkyYWJjNzgzYjA0YmUzYWI4YiA9IEwubWFya2VyKAogICAgICAgICAgICBbMzcuNTcxODUyOSwgMTI2Ljk5ODkxNDNdLAogICAgICAgICAgICB7CiAgICAgICAgICAgICAgICBpY29uOiBuZXcgTC5JY29uLkRlZmF1bHQoKQogICAgICAgICAgICAgICAgfQogICAgICAgICAgICApLmFkZFRvKG1hcF8wZjQ0NDk4NTBhNGE0OThjYjM3ZTQ4MmYxNzVmYTIwOSk7CiAgICAgICAgCiAgICAKICAgICAgICB2YXIgbWFya2VyXzQzYzY0ODEzZjljYTRjODRiNDM5Y2I0OWM3YTg3OGVkID0gTC5tYXJrZXIoCiAgICAgICAgICAgIFszNy41Mzg3MDk5LCAxMjYuOTY1OTE4M10sCiAgICAgICAgICAgIHsKICAgICAgICAgICAgICAgIGljb246IG5ldyBMLkljb24uRGVmYXVsdCgpCiAgICAgICAgICAgICAgICB9CiAgICAgICAgICAgICkuYWRkVG8obWFwXzBmNDQ0OTg1MGE0YTQ5OGNiMzdlNDgyZjE3NWZhMjA5KTsKICAgICAgICAKICAgIAogICAgICAgIHZhciBtYXJrZXJfNDlhZmZlYWM3ZWMxNGI2OTgzNzM4NTVmZWJhYmY1ODkgPSBMLm1hcmtlcigKICAgICAgICAgICAgWzM3LjU4OTc0ODIsIDEyNy4wMTYxMzUzXSwKICAgICAgICAgICAgewogICAgICAgICAgICAgICAgaWNvbjogbmV3IEwuSWNvbi5EZWZhdWx0KCkKICAgICAgICAgICAgICAgIH0KICAgICAgICAgICAgKS5hZGRUbyhtYXBfMGY0NDQ5ODUwYTRhNDk4Y2IzN2U0ODJmMTc1ZmEyMDkpOwogICAgICAgIAogICAgCiAgICAgICAgdmFyIG1hcmtlcl84NmI4ZGM5M2Q2MWE0ODE3YmZhZDllMDA0MzBjZDA4ZSA9IEwubWFya2VyKAogICAgICAgICAgICBbMzcuNTg1MDYxNDk5OTk5OTksIDEyNy4wNDU3Njc5XSwKICAgICAgICAgICAgewogICAgICAgICAgICAgICAgaWNvbjogbmV3IEwuSWNvbi5EZWZhdWx0KCkKICAgICAgICAgICAgICAgIH0KICAgICAgICAgICAgKS5hZGRUbyhtYXBfMGY0NDQ5ODUwYTRhNDk4Y2IzN2U0ODJmMTc1ZmEyMDkpOwogICAgICAgIAogICAgCiAgICAgICAgdmFyIG1hcmtlcl81YmZkNGJmOGY0NTc0ZDY5YmZhMGQyOTgxZTM1MGQ5YiA9IEwubWFya2VyKAogICAgICAgICAgICBbMzcuNTUwODE0LCAxMjYuOTU0MDI4XSwKICAgICAgICAgICAgewogICAgICAgICAgICAgICAgaWNvbjogbmV3IEwuSWNvbi5EZWZhdWx0KCkKICAgICAgICAgICAgICAgIH0KICAgICAgICAgICAgKS5hZGRUbyhtYXBfMGY0NDQ5ODUwYTRhNDk4Y2IzN2U0ODJmMTc1ZmEyMDkpOwogICAgICAgIAogICAgCiAgICAgICAgdmFyIG1hcmtlcl84MTE0YzU5MTY4YmQ0ZjQ2YTcwMTdkNmZiZDljYWNkZiA9IEwubWFya2VyKAogICAgICAgICAgICBbMzcuNTE3OTgzOSwgMTI2LjkwNzUwNTZdLAogICAgICAgICAgICB7CiAgICAgICAgICAgICAgICBpY29uOiBuZXcgTC5JY29uLkRlZmF1bHQoKQogICAgICAgICAgICAgICAgfQogICAgICAgICAgICApLmFkZFRvKG1hcF8wZjQ0NDk4NTBhNGE0OThjYjM3ZTQ4MmYxNzVmYTIwOSk7CiAgICAgICAgCiAgICAKICAgICAgICB2YXIgbWFya2VyXzI4OWNlZjkyZGI0NTQ5Yjg5OTU0ZDVhNjYyMDYyMTk4ID0gTC5tYXJrZXIoCiAgICAgICAgICAgIFszNy41NjE3MzA5LCAxMjcuMDM2MzgwNl0sCiAgICAgICAgICAgIHsKICAgICAgICAgICAgICAgIGljb246IG5ldyBMLkljb24uRGVmYXVsdCgpCiAgICAgICAgICAgICAgICB9CiAgICAgICAgICAgICkuYWRkVG8obWFwXzBmNDQ0OTg1MGE0YTQ5OGNiMzdlNDgyZjE3NWZhMjA5KTsKICAgICAgICAKICAgIAogICAgICAgIHZhciBtYXJrZXJfZWNmYmVlOGY4NjZmNDE2ZmIwYzI1MjExODRkMjFhOTUgPSBMLm1hcmtlcigKICAgICAgICAgICAgWzM3LjUxMzA4NjYsIDEyNi45NDI4NDk4XSwKICAgICAgICAgICAgewogICAgICAgICAgICAgICAgaWNvbjogbmV3IEwuSWNvbi5EZWZhdWx0KCkKICAgICAgICAgICAgICAgIH0KICAgICAgICAgICAgKS5hZGRUbyhtYXBfMGY0NDQ5ODUwYTRhNDk4Y2IzN2U0ODJmMTc1ZmEyMDkpOwogICAgICAgIAogICAgCiAgICAgICAgdmFyIG1hcmtlcl84Y2M2ZjIxNjczNzA0NDU1YWQ0YzA2N2I5YTI1OTNkZCA9IEwubWFya2VyKAogICAgICAgICAgICBbMzcuNTQyODczLCAxMjcuMDgzODIxXSwKICAgICAgICAgICAgewogICAgICAgICAgICAgICAgaWNvbjogbmV3IEwuSWNvbi5EZWZhdWx0KCkKICAgICAgICAgICAgICAgIH0KICAgICAgICAgICAgKS5hZGRUbyhtYXBfMGY0NDQ5ODUwYTRhNDk4Y2IzN2U0ODJmMTc1ZmEyMDkpOwogICAgICAgIAogICAgCiAgICAgICAgdmFyIG1hcmtlcl81MjM3ZmIwNmFmMGY0MmQyYjQ5OGQ5ZDIyOWY0ZWM1ZiA9IEwubWFya2VyKAogICAgICAgICAgICBbMzcuNjEyODYxMSwgMTI2LjkyNzQ5NTFdLAogICAgICAgICAgICB7CiAgICAgICAgICAgICAgICBpY29uOiBuZXcgTC5JY29uLkRlZmF1bHQoKQogICAgICAgICAgICAgICAgfQogICAgICAgICAgICApLmFkZFRvKG1hcF8wZjQ0NDk4NTBhNGE0OThjYjM3ZTQ4MmYxNzVmYTIwOSk7CiAgICAgICAgCiAgICAKICAgICAgICB2YXIgbWFya2VyXzM4N2E4NjA0ZGFlNzQyZWRhOWQwMGI3MjNiYTFjMDFiID0gTC5tYXJrZXIoCiAgICAgICAgICAgIFszNy42MzczMDM5MDAwMDAwMSwgMTI3LjAyNzMzOTldLAogICAgICAgICAgICB7CiAgICAgICAgICAgICAgICBpY29uOiBuZXcgTC5JY29uLkRlZmF1bHQoKQogICAgICAgICAgICAgICAgfQogICAgICAgICAgICApLmFkZFRvKG1hcF8wZjQ0NDk4NTBhNGE0OThjYjM3ZTQ4MmYxNzVmYTIwOSk7CiAgICAgICAgCiAgICAKICAgICAgICB2YXIgbWFya2VyX2ViNDk0MDE5ZjhhZTQxMTliOWY4YjczYWI1MGJmZDQ1ID0gTC5tYXJrZXIoCiAgICAgICAgICAgIFszNy40NTY4NzIyLCAxMjYuODk3MDQyOV0sCiAgICAgICAgICAgIHsKICAgICAgICAgICAgICAgIGljb246IG5ldyBMLkljb24uRGVmYXVsdCgpCiAgICAgICAgICAgICAgICB9CiAgICAgICAgICAgICkuYWRkVG8obWFwXzBmNDQ0OTg1MGE0YTQ5OGNiMzdlNDgyZjE3NWZhMjA5KTsKICAgICAgICAKICAgIAogICAgICAgIHZhciBtYXJrZXJfMmU2Y2FmMDEzZGM1NGY0N2FhMjgzYWI0NzkyNTM2MDAgPSBMLm1hcmtlcigKICAgICAgICAgICAgWzM3LjYxODY5MiwgMTI3LjEwNDcxMzZdLAogICAgICAgICAgICB7CiAgICAgICAgICAgICAgICBpY29uOiBuZXcgTC5JY29uLkRlZmF1bHQoKQogICAgICAgICAgICAgICAgfQogICAgICAgICAgICApLmFkZFRvKG1hcF8wZjQ0NDk4NTBhNGE0OThjYjM3ZTQ4MmYxNzVmYTIwOSk7CiAgICAgICAgCiAgICAKICAgICAgICB2YXIgbWFya2VyXzY0ZmEwZjRkMmViODQ0NGU5ZDIzMzY1ZjhiNWVmYzQzID0gTC5tYXJrZXIoCiAgICAgICAgICAgIFszNy41MDk0MzUyLCAxMjcuMDY2OTU3OF0sCiAgICAgICAgICAgIHsKICAgICAgICAgICAgICAgIGljb246IG5ldyBMLkljb24uRGVmYXVsdCgpCiAgICAgICAgICAgICAgICB9CiAgICAgICAgICAgICkuYWRkVG8obWFwXzBmNDQ0OTg1MGE0YTQ5OGNiMzdlNDgyZjE3NWZhMjA5KTsKICAgICAgICAKICAgIAogICAgICAgIHZhciBtYXJrZXJfZGQ3MjkzZjQ5MmQyNDY3ZTgzOGYzYWE3Yjg2NTUyMDYgPSBMLm1hcmtlcigKICAgICAgICAgICAgWzM3LjQ3NDM3ODksIDEyNi45NTA5NzQ4XSwKICAgICAgICAgICAgewogICAgICAgICAgICAgICAgaWNvbjogbmV3IEwuSWNvbi5EZWZhdWx0KCkKICAgICAgICAgICAgICAgIH0KICAgICAgICAgICAgKS5hZGRUbyhtYXBfMGY0NDQ5ODUwYTRhNDk4Y2IzN2U0ODJmMTc1ZmEyMDkpOwogICAgICAgIAogICAgCiAgICAgICAgdmFyIG1hcmtlcl9jOTRjM2ZkMjNlYjQ0MzdkYjQ0NTVhMWZjMDQ1NGNlMiA9IEwubWFya2VyKAogICAgICAgICAgICBbMzcuNTM5NzgyNywgMTI2LjgyOTk5NjhdLAogICAgICAgICAgICB7CiAgICAgICAgICAgICAgICBpY29uOiBuZXcgTC5JY29uLkRlZmF1bHQoKQogICAgICAgICAgICAgICAgfQogICAgICAgICAgICApLmFkZFRvKG1hcF8wZjQ0NDk4NTBhNGE0OThjYjM3ZTQ4MmYxNzVmYTIwOSk7CiAgICAgICAgCiAgICAKICAgICAgICB2YXIgbWFya2VyXzI5YjY3MjdhNjViNjQ3OGRhYjEzYjg1ZDc4ZjI1MmM4ID0gTC5tYXJrZXIoCiAgICAgICAgICAgIFszNy41Mjg1MTEsIDEyNy4xMjY4MjI0XSwKICAgICAgICAgICAgewogICAgICAgICAgICAgICAgaWNvbjogbmV3IEwuSWNvbi5EZWZhdWx0KCkKICAgICAgICAgICAgICAgIH0KICAgICAgICAgICAgKS5hZGRUbyhtYXBfMGY0NDQ5ODUwYTRhNDk4Y2IzN2U0ODJmMTc1ZmEyMDkpOwogICAgICAgIAogICAgCiAgICAgICAgdmFyIG1hcmtlcl8xZjNjNTBiMDBlYjQ0ZWJkOTMyODEwOTAxZWJlODc2OCA9IEwubWFya2VyKAogICAgICAgICAgICBbMzcuNjAyMDU5MiwgMTI3LjAzMjE1NzddLAogICAgICAgICAgICB7CiAgICAgICAgICAgICAgICBpY29uOiBuZXcgTC5JY29uLkRlZmF1bHQoKQogICAgICAgICAgICAgICAgfQogICAgICAgICAgICApLmFkZFRvKG1hcF8wZjQ0NDk4NTBhNGE0OThjYjM3ZTQ4MmYxNzVmYTIwOSk7CiAgICAgICAgCiAgICAKICAgICAgICB2YXIgbWFya2VyXzExYzg0NTUxZTFkZTQzYWFhYjI5ZTFlYzhmYzUyNWEwID0gTC5tYXJrZXIoCiAgICAgICAgICAgIFszNy40OTQ5MzEsIDEyNi44ODY3MzFdLAogICAgICAgICAgICB7CiAgICAgICAgICAgICAgICBpY29uOiBuZXcgTC5JY29uLkRlZmF1bHQoKQogICAgICAgICAgICAgICAgfQogICAgICAgICAgICApLmFkZFRvKG1hcF8wZjQ0NDk4NTBhNGE0OThjYjM3ZTQ4MmYxNzVmYTIwOSk7CiAgICAgICAgCiAgICAKICAgICAgICB2YXIgbWFya2VyXzkzMDk5NGM2YjI1NDRjYWNhNWI3ZTNkNDQ0ZTU5ZWVkID0gTC5tYXJrZXIoCiAgICAgICAgICAgIFszNy40OTU2MDU0LCAxMjcuMDA1MjUwNF0sCiAgICAgICAgICAgIHsKICAgICAgICAgICAgICAgIGljb246IG5ldyBMLkljb24uRGVmYXVsdCgpCiAgICAgICAgICAgICAgICB9CiAgICAgICAgICAgICkuYWRkVG8obWFwXzBmNDQ0OTg1MGE0YTQ5OGNiMzdlNDgyZjE3NWZhMjA5KTsKICAgICAgICAKICAgIAogICAgICAgIHZhciBtYXJrZXJfN2I0YWJjN2M3NzdhNDUzOWIyZDExNTQyNzM3NWJkNjYgPSBMLm1hcmtlcigKICAgICAgICAgICAgWzM3LjUxNjU2NjcsIDEyNi44NjU2NzYzXSwKICAgICAgICAgICAgewogICAgICAgICAgICAgICAgaWNvbjogbmV3IEwuSWNvbi5EZWZhdWx0KCkKICAgICAgICAgICAgICAgIH0KICAgICAgICAgICAgKS5hZGRUbyhtYXBfMGY0NDQ5ODUwYTRhNDk4Y2IzN2U0ODJmMTc1ZmEyMDkpOwogICAgICAgIAogICAgCiAgICAgICAgdmFyIG1hcmtlcl9lMjg1Njk0ZmIzMTg0ZDRlYTUwMTA2YzY3Mjk1ZDliZiA9IEwubWFya2VyKAogICAgICAgICAgICBbMzcuNTAxOTA2NSwgMTI3LjEyNzE1MTNdLAogICAgICAgICAgICB7CiAgICAgICAgICAgICAgICBpY29uOiBuZXcgTC5JY29uLkRlZmF1bHQoKQogICAgICAgICAgICAgICAgfQogICAgICAgICAgICApLmFkZFRvKG1hcF8wZjQ0NDk4NTBhNGE0OThjYjM3ZTQ4MmYxNzVmYTIwOSk7CiAgICAgICAgCiAgICAKICAgICAgICB2YXIgbWFya2VyXzNhMDdiYmVjZWM2MDQ4M2RiY2IwNDdlOWRjMWE1ZDdiID0gTC5tYXJrZXIoCiAgICAgICAgICAgIFszNy42NDIzNjA1LCAxMjcuMDcxNDAyN10sCiAgICAgICAgICAgIHsKICAgICAgICAgICAgICAgIGljb246IG5ldyBMLkljb24uRGVmYXVsdCgpCiAgICAgICAgICAgICAgICB9CiAgICAgICAgICAgICkuYWRkVG8obWFwXzBmNDQ0OTg1MGE0YTQ5OGNiMzdlNDgyZjE3NWZhMjA5KTsKICAgICAgICAKICAgIAogICAgICAgIHZhciBtYXJrZXJfZGU4MTU4NmFkNzEyNDA5OTk5ZTRhNGE4N2FhMmE0N2UgPSBMLm1hcmtlcigKICAgICAgICAgICAgWzM3LjQ5NDU5NTksIDEyNi45ODMxMjc5XSwKICAgICAgICAgICAgewogICAgICAgICAgICAgICAgaWNvbjogbmV3IEwuSWNvbi5EZWZhdWx0KCkKICAgICAgICAgICAgICAgIH0KICAgICAgICAgICAgKS5hZGRUbyhtYXBfMGY0NDQ5ODUwYTRhNDk4Y2IzN2U0ODJmMTc1ZmEyMDkpOwogICAgICAgIAogICAgCiAgICAgICAgdmFyIG1hcmtlcl84ZjBjYWVlZTIyY2Q0NzA3OTQzODA2ZmFiYjlhNDQyYiA9IEwubWFya2VyKAogICAgICAgICAgICBbMzcuNjI4MzU5NywgMTI2LjkyODcyMjZdLAogICAgICAgICAgICB7CiAgICAgICAgICAgICAgICBpY29uOiBuZXcgTC5JY29uLkRlZmF1bHQoKQogICAgICAgICAgICAgICAgfQogICAgICAgICAgICApLmFkZFRvKG1hcF8wZjQ0NDk4NTBhNGE0OThjYjM3ZTQ4MmYxNzVmYTIwOSk7CiAgICAgICAgCiAgICAKICAgICAgICB2YXIgbWFya2VyXzlmM2FlMzRmNDAyMjRhNzdhNGQ0MTM0OWYwNDNjMzM2ID0gTC5tYXJrZXIoCiAgICAgICAgICAgIFszNy42NTMzNTg5LCAxMjcuMDUyNjgyXSwKICAgICAgICAgICAgewogICAgICAgICAgICAgICAgaWNvbjogbmV3IEwuSWNvbi5EZWZhdWx0KCkKICAgICAgICAgICAgICAgIH0KICAgICAgICAgICAgKS5hZGRUbyhtYXBfMGY0NDQ5ODUwYTRhNDk4Y2IzN2U0ODJmMTc1ZmEyMDkpOwogICAgICAgIAogICAgCiAgICAgICAgdmFyIG1hcmtlcl9hYWMxM2E4MmZkNGE0ZDJmYjY5ZWE1YWI0NTc4MGQ1MyA9IEwubWFya2VyKAogICAgICAgICAgICBbMzcuNDkzNDksIDEyNy4wNzcyMTE5XSwKICAgICAgICAgICAgewogICAgICAgICAgICAgICAgaWNvbjogbmV3IEwuSWNvbi5EZWZhdWx0KCkKICAgICAgICAgICAgICAgIH0KICAgICAgICAgICAgKS5hZGRUbyhtYXBfMGY0NDQ5ODUwYTRhNDk4Y2IzN2U0ODJmMTc1ZmEyMDkpOwogICAgICAgIAo8L3NjcmlwdD4=" style="position:absolute;width:100%;height:100%;left:0;top:0;border:none !important;" allowfullscreen webkitallowfullscreen mozallowfullscreen></iframe></div></div>




```python
map = folium.Map(location=[37.5502, 126.982], zoom_start=11)

for n in crime_anal_raw.index:
    folium.CircleMarker([crime_anal_raw['lat'][n], crime_anal_raw['lng'][n]], 
                        radius = crime_anal_raw['검거'][n]*10, 
                        color='#3186cc', fill_color='#3186cc', fill=True).add_to(map)
    
map
```




<div style="width:100%;"><div style="position:relative;width:100%;height:0;padding-bottom:60%;"><iframe src="data:text/html;charset=utf-8;base64,PCFET0NUWVBFIGh0bWw+CjxoZWFkPiAgICAKICAgIDxtZXRhIGh0dHAtZXF1aXY9ImNvbnRlbnQtdHlwZSIgY29udGVudD0idGV4dC9odG1sOyBjaGFyc2V0PVVURi04IiAvPgogICAgPHNjcmlwdD5MX1BSRUZFUl9DQU5WQVM9ZmFsc2U7IExfTk9fVE9VQ0g9ZmFsc2U7IExfRElTQUJMRV8zRD1mYWxzZTs8L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmpzIj48L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2FqYXguZ29vZ2xlYXBpcy5jb20vYWpheC9saWJzL2pxdWVyeS8xLjExLjEvanF1ZXJ5Lm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvanMvYm9vdHN0cmFwLm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9jZG5qcy5jbG91ZGZsYXJlLmNvbS9hamF4L2xpYnMvTGVhZmxldC5hd2Vzb21lLW1hcmtlcnMvMi4wLjIvbGVhZmxldC5hd2Vzb21lLW1hcmtlcnMuanMiPjwvc2NyaXB0PgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL21heGNkbi5ib290c3RyYXBjZG4uY29tL2Jvb3RzdHJhcC8zLjIuMC9jc3MvYm9vdHN0cmFwLm1pbi5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvY3NzL2Jvb3RzdHJhcC10aGVtZS5taW4uY3NzIi8+CiAgICA8bGluayByZWw9InN0eWxlc2hlZXQiIGhyZWY9Imh0dHBzOi8vbWF4Y2RuLmJvb3RzdHJhcGNkbi5jb20vZm9udC1hd2Vzb21lLzQuNi4zL2Nzcy9mb250LWF3ZXNvbWUubWluLmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2NkbmpzLmNsb3VkZmxhcmUuY29tL2FqYXgvbGlicy9MZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy8yLjAuMi9sZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9yYXdjZG4uZ2l0aGFjay5jb20vcHl0aG9uLXZpc3VhbGl6YXRpb24vZm9saXVtL21hc3Rlci9mb2xpdW0vdGVtcGxhdGVzL2xlYWZsZXQuYXdlc29tZS5yb3RhdGUuY3NzIi8+CiAgICA8c3R5bGU+aHRtbCwgYm9keSB7d2lkdGg6IDEwMCU7aGVpZ2h0OiAxMDAlO21hcmdpbjogMDtwYWRkaW5nOiAwO308L3N0eWxlPgogICAgPHN0eWxlPiNtYXAge3Bvc2l0aW9uOmFic29sdXRlO3RvcDowO2JvdHRvbTowO3JpZ2h0OjA7bGVmdDowO308L3N0eWxlPgogICAgCiAgICA8bWV0YSBuYW1lPSJ2aWV3cG9ydCIgY29udGVudD0id2lkdGg9ZGV2aWNlLXdpZHRoLAogICAgICAgIGluaXRpYWwtc2NhbGU9MS4wLCBtYXhpbXVtLXNjYWxlPTEuMCwgdXNlci1zY2FsYWJsZT1ubyIgLz4KICAgIDxzdHlsZT4jbWFwXzhmMjExMTExNzc3YzQ5OTE4NjJhMGM4YmNjMzg3NTZkIHsKICAgICAgICBwb3NpdGlvbjogcmVsYXRpdmU7CiAgICAgICAgd2lkdGg6IDEwMC4wJTsKICAgICAgICBoZWlnaHQ6IDEwMC4wJTsKICAgICAgICBsZWZ0OiAwLjAlOwogICAgICAgIHRvcDogMC4wJTsKICAgICAgICB9CiAgICA8L3N0eWxlPgo8L2hlYWQ+Cjxib2R5PiAgICAKICAgIAogICAgPGRpdiBjbGFzcz0iZm9saXVtLW1hcCIgaWQ9Im1hcF84ZjIxMTExMTc3N2M0OTkxODYyYTBjOGJjYzM4NzU2ZCIgPjwvZGl2Pgo8L2JvZHk+CjxzY3JpcHQ+ICAgIAogICAgCiAgICAKICAgICAgICB2YXIgYm91bmRzID0gbnVsbDsKICAgIAoKICAgIHZhciBtYXBfOGYyMTExMTE3NzdjNDk5MTg2MmEwYzhiY2MzODc1NmQgPSBMLm1hcCgKICAgICAgICAnbWFwXzhmMjExMTExNzc3YzQ5OTE4NjJhMGM4YmNjMzg3NTZkJywgewogICAgICAgIGNlbnRlcjogWzM3LjU1MDIsIDEyNi45ODJdLAogICAgICAgIHpvb206IDExLAogICAgICAgIG1heEJvdW5kczogYm91bmRzLAogICAgICAgIGxheWVyczogW10sCiAgICAgICAgd29ybGRDb3B5SnVtcDogZmFsc2UsCiAgICAgICAgY3JzOiBMLkNSUy5FUFNHMzg1NywKICAgICAgICB6b29tQ29udHJvbDogdHJ1ZSwKICAgICAgICB9KTsKCiAgICAKICAgIAogICAgdmFyIHRpbGVfbGF5ZXJfMDhjN2JkYmJiOTE4NDkwOWI3ODE3Y2YyMGE2OThlODAgPSBMLnRpbGVMYXllcigKICAgICAgICAnaHR0cHM6Ly97c30udGlsZS5vcGVuc3RyZWV0bWFwLm9yZy97en0ve3h9L3t5fS5wbmcnLAogICAgICAgIHsKICAgICAgICAiYXR0cmlidXRpb24iOiBudWxsLAogICAgICAgICJkZXRlY3RSZXRpbmEiOiBmYWxzZSwKICAgICAgICAibWF4TmF0aXZlWm9vbSI6IDE4LAogICAgICAgICJtYXhab29tIjogMTgsCiAgICAgICAgIm1pblpvb20iOiAwLAogICAgICAgICJub1dyYXAiOiBmYWxzZSwKICAgICAgICAib3BhY2l0eSI6IDEsCiAgICAgICAgInN1YmRvbWFpbnMiOiAiYWJjIiwKICAgICAgICAidG1zIjogZmFsc2UKfSkuYWRkVG8obWFwXzhmMjExMTExNzc3YzQ5OTE4NjJhMGM4YmNjMzg3NTZkKTsKICAgIAogICAgICAgICAgICB2YXIgY2lyY2xlX21hcmtlcl9hZDk1NWVkOTExZWY0NWRiYTIxNTM0YTA3MDU1ZGVhOCA9IEwuY2lyY2xlTWFya2VyKAogICAgICAgICAgICAgICAgWzM3LjU2MzY0NjUsIDEyNi45ODk1Nzk2XSwKICAgICAgICAgICAgICAgIHsKICAiYnViYmxpbmdNb3VzZUV2ZW50cyI6IHRydWUsCiAgImNvbG9yIjogIiMzMTg2Y2MiLAogICJkYXNoQXJyYXkiOiBudWxsLAogICJkYXNoT2Zmc2V0IjogbnVsbCwKICAiZmlsbCI6IHRydWUsCiAgImZpbGxDb2xvciI6ICIjMzE4NmNjIiwKICAiZmlsbE9wYWNpdHkiOiAwLjIsCiAgImZpbGxSdWxlIjogImV2ZW5vZGQiLAogICJsaW5lQ2FwIjogInJvdW5kIiwKICAibGluZUpvaW4iOiAicm91bmQiLAogICJvcGFjaXR5IjogMS4wLAogICJyYWRpdXMiOiAxMi43NTQxNjEzMTM5OTAxNzYsCiAgInN0cm9rZSI6IHRydWUsCiAgIndlaWdodCI6IDMKfQogICAgICAgICAgICAgICAgKQogICAgICAgICAgICAgICAgLmFkZFRvKG1hcF84ZjIxMTExMTc3N2M0OTkxODYyYTBjOGJjYzM4NzU2ZCk7CiAgICAgICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBjaXJjbGVfbWFya2VyXzYxMmQ2ZjM2ZWM0YzQ4ZDI4NDA4MzAzYWY0MzQzMjU5ID0gTC5jaXJjbGVNYXJrZXIoCiAgICAgICAgICAgICAgICBbMzcuNTc1NTU3OCwgMTI2Ljk4NDg2NzRdLAogICAgICAgICAgICAgICAgewogICJidWJibGluZ01vdXNlRXZlbnRzIjogdHJ1ZSwKICAiY29sb3IiOiAiIzMxODZjYyIsCiAgImRhc2hBcnJheSI6IG51bGwsCiAgImRhc2hPZmZzZXQiOiBudWxsLAogICJmaWxsIjogdHJ1ZSwKICAiZmlsbENvbG9yIjogIiMzMTg2Y2MiLAogICJmaWxsT3BhY2l0eSI6IDAuMiwKICAiZmlsbFJ1bGUiOiAiZXZlbm9kZCIsCiAgImxpbmVDYXAiOiAicm91bmQiLAogICJsaW5lSm9pbiI6ICJyb3VuZCIsCiAgIm9wYWNpdHkiOiAxLjAsCiAgInJhZGl1cyI6IDE1LjIzODQ3NDgxOTgyMDExMiwKICAic3Ryb2tlIjogdHJ1ZSwKICAid2VpZ2h0IjogMwp9CiAgICAgICAgICAgICAgICApCiAgICAgICAgICAgICAgICAuYWRkVG8obWFwXzhmMjExMTExNzc3YzQ5OTE4NjJhMGM4YmNjMzg3NTZkKTsKICAgICAgICAgICAgCiAgICAKICAgICAgICAgICAgdmFyIGNpcmNsZV9tYXJrZXJfMDY0ZDZhNGVjMmNkNDQ4ZGFkNDViY2EwZjk0MDg2MDggPSBMLmNpcmNsZU1hcmtlcigKICAgICAgICAgICAgICAgIFszNy41NTQ3NTg0LCAxMjYuOTczNDk4MV0sCiAgICAgICAgICAgICAgICB7CiAgImJ1YmJsaW5nTW91c2VFdmVudHMiOiB0cnVlLAogICJjb2xvciI6ICIjMzE4NmNjIiwKICAiZGFzaEFycmF5IjogbnVsbCwKICAiZGFzaE9mZnNldCI6IG51bGwsCiAgImZpbGwiOiB0cnVlLAogICJmaWxsQ29sb3IiOiAiIzMxODZjYyIsCiAgImZpbGxPcGFjaXR5IjogMC4yLAogICJmaWxsUnVsZSI6ICJldmVub2RkIiwKICAibGluZUNhcCI6ICJyb3VuZCIsCiAgImxpbmVKb2luIjogInJvdW5kIiwKICAib3BhY2l0eSI6IDEuMCwKICAicmFkaXVzIjogOS4wNzM3MjIyODg5OTM1OTQsCiAgInN0cm9rZSI6IHRydWUsCiAgIndlaWdodCI6IDMKfQogICAgICAgICAgICAgICAgKQogICAgICAgICAgICAgICAgLmFkZFRvKG1hcF84ZjIxMTExMTc3N2M0OTkxODYyYTBjOGJjYzM4NzU2ZCk7CiAgICAgICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBjaXJjbGVfbWFya2VyXzdhZDA0Mzc5MWI5YTQxNGZiYzc0OTVjY2YxOTA2ZGM0ID0gTC5jaXJjbGVNYXJrZXIoCiAgICAgICAgICAgICAgICBbMzcuNTY0Nzg0OCwgMTI2Ljk2Njc3NjJdLAogICAgICAgICAgICAgICAgewogICJidWJibGluZ01vdXNlRXZlbnRzIjogdHJ1ZSwKICAiY29sb3IiOiAiIzMxODZjYyIsCiAgImRhc2hBcnJheSI6IG51bGwsCiAgImRhc2hPZmZzZXQiOiBudWxsLAogICJmaWxsIjogdHJ1ZSwKICAiZmlsbENvbG9yIjogIiMzMTg2Y2MiLAogICJmaWxsT3BhY2l0eSI6IDAuMiwKICAiZmlsbFJ1bGUiOiAiZXZlbm9kZCIsCiAgImxpbmVDYXAiOiAicm91bmQiLAogICJsaW5lSm9pbiI6ICJyb3VuZCIsCiAgIm9wYWNpdHkiOiAxLjAsCiAgInJhZGl1cyI6IDE5Ljc4Mjk5NDI3NDg5MjAxNSwKICAic3Ryb2tlIjogdHJ1ZSwKICAid2VpZ2h0IjogMwp9CiAgICAgICAgICAgICAgICApCiAgICAgICAgICAgICAgICAuYWRkVG8obWFwXzhmMjExMTExNzc3YzQ5OTE4NjJhMGM4YmNjMzg3NTZkKTsKICAgICAgICAgICAgCiAgICAKICAgICAgICAgICAgdmFyIGNpcmNsZV9tYXJrZXJfMWNlOGM0ODUzNTI5NDU1NTkzODQ2ZDNlYjNkNDgxZjEgPSBMLmNpcmNsZU1hcmtlcigKICAgICAgICAgICAgICAgIFszNy41NzE4NTI5LCAxMjYuOTk4OTE0M10sCiAgICAgICAgICAgICAgICB7CiAgImJ1YmJsaW5nTW91c2VFdmVudHMiOiB0cnVlLAogICJjb2xvciI6ICIjMzE4NmNjIiwKICAiZGFzaEFycmF5IjogbnVsbCwKICAiZGFzaE9mZnNldCI6IG51bGwsCiAgImZpbGwiOiB0cnVlLAogICJmaWxsQ29sb3IiOiAiIzMxODZjYyIsCiAgImZpbGxPcGFjaXR5IjogMC4yLAogICJmaWxsUnVsZSI6ICJldmVub2RkIiwKICAibGluZUNhcCI6ICJyb3VuZCIsCiAgImxpbmVKb2luIjogInJvdW5kIiwKICAib3BhY2l0eSI6IDEuMCwKICAicmFkaXVzIjogMTEuOTgzODE4ODIxNzQ1NTgzLAogICJzdHJva2UiOiB0cnVlLAogICJ3ZWlnaHQiOiAzCn0KICAgICAgICAgICAgICAgICkKICAgICAgICAgICAgICAgIC5hZGRUbyhtYXBfOGYyMTExMTE3NzdjNDk5MTg2MmEwYzhiY2MzODc1NmQpOwogICAgICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgY2lyY2xlX21hcmtlcl9kZjY2ZjQxYTg5YzM0MjUwOWYyYTcwMmMwZjJmNWEwYiA9IEwuY2lyY2xlTWFya2VyKAogICAgICAgICAgICAgICAgWzM3LjUzODcwOTksIDEyNi45NjU5MTgzXSwKICAgICAgICAgICAgICAgIHsKICAiYnViYmxpbmdNb3VzZUV2ZW50cyI6IHRydWUsCiAgImNvbG9yIjogIiMzMTg2Y2MiLAogICJkYXNoQXJyYXkiOiBudWxsLAogICJkYXNoT2Zmc2V0IjogbnVsbCwKICAiZmlsbCI6IHRydWUsCiAgImZpbGxDb2xvciI6ICIjMzE4NmNjIiwKICAiZmlsbE9wYWNpdHkiOiAwLjIsCiAgImZpbGxSdWxlIjogImV2ZW5vZGQiLAogICJsaW5lQ2FwIjogInJvdW5kIiwKICAibGluZUpvaW4iOiAicm91bmQiLAogICJvcGFjaXR5IjogMS4wLAogICJyYWRpdXMiOiAyNi45MDY4NTQyMzkxMDQ3MjgsCiAgInN0cm9rZSI6IHRydWUsCiAgIndlaWdodCI6IDMKfQogICAgICAgICAgICAgICAgKQogICAgICAgICAgICAgICAgLmFkZFRvKG1hcF84ZjIxMTExMTc3N2M0OTkxODYyYTBjOGJjYzM4NzU2ZCk7CiAgICAgICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBjaXJjbGVfbWFya2VyXzY0ZjgwZTM0NjAwYTQ1YzJhMzQ2ZWQ4MDQ2OTcyNzkzID0gTC5jaXJjbGVNYXJrZXIoCiAgICAgICAgICAgICAgICBbMzcuNTg5NzQ4MiwgMTI3LjAxNjEzNTNdLAogICAgICAgICAgICAgICAgewogICJidWJibGluZ01vdXNlRXZlbnRzIjogdHJ1ZSwKICAiY29sb3IiOiAiIzMxODZjYyIsCiAgImRhc2hBcnJheSI6IG51bGwsCiAgImRhc2hPZmZzZXQiOiBudWxsLAogICJmaWxsIjogdHJ1ZSwKICAiZmlsbENvbG9yIjogIiMzMTg2Y2MiLAogICJmaWxsT3BhY2l0eSI6IDAuMiwKICAiZmlsbFJ1bGUiOiAiZXZlbm9kZCIsCiAgImxpbmVDYXAiOiAicm91bmQiLAogICJsaW5lSm9pbiI6ICJyb3VuZCIsCiAgIm9wYWNpdHkiOiAxLjAsCiAgInJhZGl1cyI6IDExLjU1NjQ5MTA2Njc3NzcyLAogICJzdHJva2UiOiB0cnVlLAogICJ3ZWlnaHQiOiAzCn0KICAgICAgICAgICAgICAgICkKICAgICAgICAgICAgICAgIC5hZGRUbyhtYXBfOGYyMTExMTE3NzdjNDk5MTg2MmEwYzhiY2MzODc1NmQpOwogICAgICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgY2lyY2xlX21hcmtlcl81ZmVjZGJkMWY4M2Y0ZDMxOWYyNTQ5NmNjNmRiZGFjZSA9IEwuY2lyY2xlTWFya2VyKAogICAgICAgICAgICAgICAgWzM3LjU4NTA2MTQ5OTk5OTk5LCAxMjcuMDQ1NzY3OV0sCiAgICAgICAgICAgICAgICB7CiAgImJ1YmJsaW5nTW91c2VFdmVudHMiOiB0cnVlLAogICJjb2xvciI6ICIjMzE4NmNjIiwKICAiZGFzaEFycmF5IjogbnVsbCwKICAiZGFzaE9mZnNldCI6IG51bGwsCiAgImZpbGwiOiB0cnVlLAogICJmaWxsQ29sb3IiOiAiIzMxODZjYyIsCiAgImZpbGxPcGFjaXR5IjogMC4yLAogICJmaWxsUnVsZSI6ICJldmVub2RkIiwKICAibGluZUNhcCI6ICJyb3VuZCIsCiAgImxpbmVKb2luIjogInJvdW5kIiwKICAib3BhY2l0eSI6IDEuMCwKICAicmFkaXVzIjogMjguOTczMDIwMzc0OTQyNjY0LAogICJzdHJva2UiOiB0cnVlLAogICJ3ZWlnaHQiOiAzCn0KICAgICAgICAgICAgICAgICkKICAgICAgICAgICAgICAgIC5hZGRUbyhtYXBfOGYyMTExMTE3NzdjNDk5MTg2MmEwYzhiY2MzODc1NmQpOwogICAgICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgY2lyY2xlX21hcmtlcl80MWRkODFlMDY1MTg0MWYwYmMxOTg3NmM0NGQ1ZDI1ZCA9IEwuY2lyY2xlTWFya2VyKAogICAgICAgICAgICAgICAgWzM3LjU1MDgxNCwgMTI2Ljk1NDAyOF0sCiAgICAgICAgICAgICAgICB7CiAgImJ1YmJsaW5nTW91c2VFdmVudHMiOiB0cnVlLAogICJjb2xvciI6ICIjMzE4NmNjIiwKICAiZGFzaEFycmF5IjogbnVsbCwKICAiZGFzaE9mZnNldCI6IG51bGwsCiAgImZpbGwiOiB0cnVlLAogICJmaWxsQ29sb3IiOiAiIzMxODZjYyIsCiAgImZpbGxPcGFjaXR5IjogMC4yLAogICJmaWxsUnVsZSI6ICJldmVub2RkIiwKICAibGluZUNhcCI6ICJyb3VuZCIsCiAgImxpbmVKb2luIjogInJvdW5kIiwKICAib3BhY2l0eSI6IDEuMCwKICAicmFkaXVzIjogMzUuMzg2NTY1NTIwMDIzMjU0LAogICJzdHJva2UiOiB0cnVlLAogICJ3ZWlnaHQiOiAzCn0KICAgICAgICAgICAgICAgICkKICAgICAgICAgICAgICAgIC5hZGRUbyhtYXBfOGYyMTExMTE3NzdjNDk5MTg2MmEwYzhiY2MzODc1NmQpOwogICAgICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgY2lyY2xlX21hcmtlcl9lNjBkM2E4ZTgzZTE0NzIwODgwOWJjZTkwMzU3ODZhZiA9IEwuY2lyY2xlTWFya2VyKAogICAgICAgICAgICAgICAgWzM3LjUxNzk4MzksIDEyNi45MDc1MDU2XSwKICAgICAgICAgICAgICAgIHsKICAiYnViYmxpbmdNb3VzZUV2ZW50cyI6IHRydWUsCiAgImNvbG9yIjogIiMzMTg2Y2MiLAogICJkYXNoQXJyYXkiOiBudWxsLAogICJkYXNoT2Zmc2V0IjogbnVsbCwKICAiZmlsbCI6IHRydWUsCiAgImZpbGxDb2xvciI6ICIjMzE4NmNjIiwKICAiZmlsbE9wYWNpdHkiOiAwLjIsCiAgImZpbGxSdWxlIjogImV2ZW5vZGQiLAogICJsaW5lQ2FwIjogInJvdW5kIiwKICAibGluZUpvaW4iOiAicm91bmQiLAogICJvcGFjaXR5IjogMS4wLAogICJyYWRpdXMiOiA0Mi43NTk3ODkzNTk1NTAzMSwKICAic3Ryb2tlIjogdHJ1ZSwKICAid2VpZ2h0IjogMwp9CiAgICAgICAgICAgICAgICApCiAgICAgICAgICAgICAgICAuYWRkVG8obWFwXzhmMjExMTExNzc3YzQ5OTE4NjJhMGM4YmNjMzg3NTZkKTsKICAgICAgICAgICAgCiAgICAKICAgICAgICAgICAgdmFyIGNpcmNsZV9tYXJrZXJfNjc3OTBmY2M0Mjg4NGYyZGJhNWVkZjJjY2QwYTIzOWYgPSBMLmNpcmNsZU1hcmtlcigKICAgICAgICAgICAgICAgIFszNy41NjE3MzA5LCAxMjcuMDM2MzgwNl0sCiAgICAgICAgICAgICAgICB7CiAgImJ1YmJsaW5nTW91c2VFdmVudHMiOiB0cnVlLAogICJjb2xvciI6ICIjMzE4NmNjIiwKICAiZGFzaEFycmF5IjogbnVsbCwKICAiZGFzaE9mZnNldCI6IG51bGwsCiAgImZpbGwiOiB0cnVlLAogICJmaWxsQ29sb3IiOiAiIzMxODZjYyIsCiAgImZpbGxPcGFjaXR5IjogMC4yLAogICJmaWxsUnVsZSI6ICJldmVub2RkIiwKICAibGluZUNhcCI6ICJyb3VuZCIsCiAgImxpbmVKb2luIjogInJvdW5kIiwKICAib3BhY2l0eSI6IDEuMCwKICAicmFkaXVzIjogMjAuNjE0MzM1OTUzMTgwNDA4LAogICJzdHJva2UiOiB0cnVlLAogICJ3ZWlnaHQiOiAzCn0KICAgICAgICAgICAgICAgICkKICAgICAgICAgICAgICAgIC5hZGRUbyhtYXBfOGYyMTExMTE3NzdjNDk5MTg2MmEwYzhiY2MzODc1NmQpOwogICAgICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgY2lyY2xlX21hcmtlcl85YzkwYTVhM2UwOTk0MTAwOTM0MjM5NDM4YmQ5Y2E0ZiA9IEwuY2lyY2xlTWFya2VyKAogICAgICAgICAgICAgICAgWzM3LjUxMzA4NjYsIDEyNi45NDI4NDk4XSwKICAgICAgICAgICAgICAgIHsKICAiYnViYmxpbmdNb3VzZUV2ZW50cyI6IHRydWUsCiAgImNvbG9yIjogIiMzMTg2Y2MiLAogICJkYXNoQXJyYXkiOiBudWxsLAogICJkYXNoT2Zmc2V0IjogbnVsbCwKICAiZmlsbCI6IHRydWUsCiAgImZpbGxDb2xvciI6ICIjMzE4NmNjIiwKICAiZmlsbE9wYWNpdHkiOiAwLjIsCiAgImZpbGxSdWxlIjogImV2ZW5vZGQiLAogICJsaW5lQ2FwIjogInJvdW5kIiwKICAibGluZUpvaW4iOiAicm91bmQiLAogICJvcGFjaXR5IjogMS4wLAogICJyYWRpdXMiOiAyMi4yNTMxNDM5NDQ0OTIxMiwKICAic3Ryb2tlIjogdHJ1ZSwKICAid2VpZ2h0IjogMwp9CiAgICAgICAgICAgICAgICApCiAgICAgICAgICAgICAgICAuYWRkVG8obWFwXzhmMjExMTExNzc3YzQ5OTE4NjJhMGM4YmNjMzg3NTZkKTsKICAgICAgICAgICAgCiAgICAKICAgICAgICAgICAgdmFyIGNpcmNsZV9tYXJrZXJfYmZlZDYxNDg4OGQ2NGI2ZDlkNDM4ZDk5NDQyYzZhNGUgPSBMLmNpcmNsZU1hcmtlcigKICAgICAgICAgICAgICAgIFszNy41NDI4NzMsIDEyNy4wODM4MjFdLAogICAgICAgICAgICAgICAgewogICJidWJibGluZ01vdXNlRXZlbnRzIjogdHJ1ZSwKICAiY29sb3IiOiAiIzMxODZjYyIsCiAgImRhc2hBcnJheSI6IG51bGwsCiAgImRhc2hPZmZzZXQiOiBudWxsLAogICJmaWxsIjogdHJ1ZSwKICAiZmlsbENvbG9yIjogIiMzMTg2Y2MiLAogICJmaWxsT3BhY2l0eSI6IDAuMiwKICAiZmlsbFJ1bGUiOiAiZXZlbm9kZCIsCiAgImxpbmVDYXAiOiAicm91bmQiLAogICJsaW5lSm9pbiI6ICJyb3VuZCIsCiAgIm9wYWNpdHkiOiAxLjAsCiAgInJhZGl1cyI6IDM5LjYwMjU5MzQ5OTU2OTk4NCwKICAic3Ryb2tlIjogdHJ1ZSwKICAid2VpZ2h0IjogMwp9CiAgICAgICAgICAgICAgICApCiAgICAgICAgICAgICAgICAuYWRkVG8obWFwXzhmMjExMTExNzc3YzQ5OTE4NjJhMGM4YmNjMzg3NTZkKTsKICAgICAgICAgICAgCiAgICAKICAgICAgICAgICAgdmFyIGNpcmNsZV9tYXJrZXJfM2M0MDQ5NzQ2ODU5NDdiZjlhMjFkZmFjYWQ2NjFmM2YgPSBMLmNpcmNsZU1hcmtlcigKICAgICAgICAgICAgICAgIFszNy42MTI4NjExLCAxMjYuOTI3NDk1MV0sCiAgICAgICAgICAgICAgICB7CiAgImJ1YmJsaW5nTW91c2VFdmVudHMiOiB0cnVlLAogICJjb2xvciI6ICIjMzE4NmNjIiwKICAiZGFzaEFycmF5IjogbnVsbCwKICAiZGFzaE9mZnNldCI6IG51bGwsCiAgImZpbGwiOiB0cnVlLAogICJmaWxsQ29sb3IiOiAiIzMxODZjYyIsCiAgImZpbGxPcGFjaXR5IjogMC4yLAogICJmaWxsUnVsZSI6ICJldmVub2RkIiwKICAibGluZUNhcCI6ICJyb3VuZCIsCiAgImxpbmVKb2luIjogInJvdW5kIiwKICAib3BhY2l0eSI6IDEuMCwKICAicmFkaXVzIjogMTAuMjM5OTU4NTU0NjA2MjgyLAogICJzdHJva2UiOiB0cnVlLAogICJ3ZWlnaHQiOiAzCn0KICAgICAgICAgICAgICAgICkKICAgICAgICAgICAgICAgIC5hZGRUbyhtYXBfOGYyMTExMTE3NzdjNDk5MTg2MmEwYzhiY2MzODc1NmQpOwogICAgICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgY2lyY2xlX21hcmtlcl9mNDQxOWNhMjI3MDE0NzBjYWVjMGE1ZjYyZjE3NTkxNyA9IEwuY2lyY2xlTWFya2VyKAogICAgICAgICAgICAgICAgWzM3LjYzNzMwMzkwMDAwMDAxLCAxMjcuMDI3MzM5OV0sCiAgICAgICAgICAgICAgICB7CiAgImJ1YmJsaW5nTW91c2VFdmVudHMiOiB0cnVlLAogICJjb2xvciI6ICIjMzE4NmNjIiwKICAiZGFzaEFycmF5IjogbnVsbCwKICAiZGFzaE9mZnNldCI6IG51bGwsCiAgImZpbGwiOiB0cnVlLAogICJmaWxsQ29sb3IiOiAiIzMxODZjYyIsCiAgImZpbGxPcGFjaXR5IjogMC4yLAogICJmaWxsUnVsZSI6ICJldmVub2RkIiwKICAibGluZUNhcCI6ICJyb3VuZCIsCiAgImxpbmVKb2luIjogInJvdW5kIiwKICAib3BhY2l0eSI6IDEuMCwKICAicmFkaXVzIjogMjkuNTM3MTAyMjA1MTkxNTIsCiAgInN0cm9rZSI6IHRydWUsCiAgIndlaWdodCI6IDMKfQogICAgICAgICAgICAgICAgKQogICAgICAgICAgICAgICAgLmFkZFRvKG1hcF84ZjIxMTExMTc3N2M0OTkxODYyYTBjOGJjYzM4NzU2ZCk7CiAgICAgICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBjaXJjbGVfbWFya2VyX2RjMDA3YmM1ODQxYzQ2NDI4MjBmZDBmNzA5NWQ2OGZkID0gTC5jaXJjbGVNYXJrZXIoCiAgICAgICAgICAgICAgICBbMzcuNDU2ODcyMiwgMTI2Ljg5NzA0MjldLAogICAgICAgICAgICAgICAgewogICJidWJibGluZ01vdXNlRXZlbnRzIjogdHJ1ZSwKICAiY29sb3IiOiAiIzMxODZjYyIsCiAgImRhc2hBcnJheSI6IG51bGwsCiAgImRhc2hPZmZzZXQiOiBudWxsLAogICJmaWxsIjogdHJ1ZSwKICAiZmlsbENvbG9yIjogIiMzMTg2Y2MiLAogICJmaWxsT3BhY2l0eSI6IDAuMiwKICAiZmlsbFJ1bGUiOiAiZXZlbm9kZCIsCiAgImxpbmVDYXAiOiAicm91bmQiLAogICJsaW5lSm9pbiI6ICJyb3VuZCIsCiAgIm9wYWNpdHkiOiAxLjAsCiAgInJhZGl1cyI6IDIzLjUzMjA2ODUxNzYxMjU3MiwKICAic3Ryb2tlIjogdHJ1ZSwKICAid2VpZ2h0IjogMwp9CiAgICAgICAgICAgICAgICApCiAgICAgICAgICAgICAgICAuYWRkVG8obWFwXzhmMjExMTExNzc3YzQ5OTE4NjJhMGM4YmNjMzg3NTZkKTsKICAgICAgICAgICAgCiAgICAKICAgICAgICAgICAgdmFyIGNpcmNsZV9tYXJrZXJfMzhlOGNlN2NjZTRiNGEzMGI1NjZhZTVhYjllYzViNWEgPSBMLmNpcmNsZU1hcmtlcigKICAgICAgICAgICAgICAgIFszNy42MTg2OTIsIDEyNy4xMDQ3MTM2XSwKICAgICAgICAgICAgICAgIHsKICAiYnViYmxpbmdNb3VzZUV2ZW50cyI6IHRydWUsCiAgImNvbG9yIjogIiMzMTg2Y2MiLAogICJkYXNoQXJyYXkiOiBudWxsLAogICJkYXNoT2Zmc2V0IjogbnVsbCwKICAiZmlsbCI6IHRydWUsCiAgImZpbGxDb2xvciI6ICIjMzE4NmNjIiwKICAiZmlsbE9wYWNpdHkiOiAwLjIsCiAgImZpbGxSdWxlIjogImV2ZW5vZGQiLAogICJsaW5lQ2FwIjogInJvdW5kIiwKICAibGluZUpvaW4iOiAicm91bmQiLAogICJvcGFjaXR5IjogMS4wLAogICJyYWRpdXMiOiAzNC4wNzQyMjkzNjg3NDEzNywKICAic3Ryb2tlIjogdHJ1ZSwKICAid2VpZ2h0IjogMwp9CiAgICAgICAgICAgICAgICApCiAgICAgICAgICAgICAgICAuYWRkVG8obWFwXzhmMjExMTExNzc3YzQ5OTE4NjJhMGM4YmNjMzg3NTZkKTsKICAgICAgICAgICAgCiAgICAKICAgICAgICAgICAgdmFyIGNpcmNsZV9tYXJrZXJfMTIwYWI0YjA2NzVkNDgwYWE2ODNhZmFlYjk2MDM1ODEgPSBMLmNpcmNsZU1hcmtlcigKICAgICAgICAgICAgICAgIFszNy41MDk0MzUyLCAxMjcuMDY2OTU3OF0sCiAgICAgICAgICAgICAgICB7CiAgImJ1YmJsaW5nTW91c2VFdmVudHMiOiB0cnVlLAogICJjb2xvciI6ICIjMzE4NmNjIiwKICAiZGFzaEFycmF5IjogbnVsbCwKICAiZGFzaE9mZnNldCI6IG51bGwsCiAgImZpbGwiOiB0cnVlLAogICJmaWxsQ29sb3IiOiAiIzMxODZjYyIsCiAgImZpbGxPcGFjaXR5IjogMC4yLAogICJmaWxsUnVsZSI6ICJldmVub2RkIiwKICAibGluZUNhcCI6ICJyb3VuZCIsCiAgImxpbmVKb2luIjogInJvdW5kIiwKICAib3BhY2l0eSI6IDEuMCwKICAicmFkaXVzIjogMzEuMTc3ODA3NzY1MTc1NzE0LAogICJzdHJva2UiOiB0cnVlLAogICJ3ZWlnaHQiOiAzCn0KICAgICAgICAgICAgICAgICkKICAgICAgICAgICAgICAgIC5hZGRUbyhtYXBfOGYyMTExMTE3NzdjNDk5MTg2MmEwYzhiY2MzODc1NmQpOwogICAgICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgY2lyY2xlX21hcmtlcl9lMGM1Mzg3YzBlN2Q0NzlkOGMyOTcyNjQ0ZWExN2E0MCA9IEwuY2lyY2xlTWFya2VyKAogICAgICAgICAgICAgICAgWzM3LjQ3NDM3ODksIDEyNi45NTA5NzQ4XSwKICAgICAgICAgICAgICAgIHsKICAiYnViYmxpbmdNb3VzZUV2ZW50cyI6IHRydWUsCiAgImNvbG9yIjogIiMzMTg2Y2MiLAogICJkYXNoQXJyYXkiOiBudWxsLAogICJkYXNoT2Zmc2V0IjogbnVsbCwKICAiZmlsbCI6IHRydWUsCiAgImZpbGxDb2xvciI6ICIjMzE4NmNjIiwKICAiZmlsbE9wYWNpdHkiOiAwLjIsCiAgImZpbGxSdWxlIjogImV2ZW5vZGQiLAogICJsaW5lQ2FwIjogInJvdW5kIiwKICAibGluZUpvaW4iOiAicm91bmQiLAogICJvcGFjaXR5IjogMS4wLAogICJyYWRpdXMiOiAzNi4zOTc0Mjc2MzIwNTQyLAogICJzdHJva2UiOiB0cnVlLAogICJ3ZWlnaHQiOiAzCn0KICAgICAgICAgICAgICAgICkKICAgICAgICAgICAgICAgIC5hZGRUbyhtYXBfOGYyMTExMTE3NzdjNDk5MTg2MmEwYzhiY2MzODc1NmQpOwogICAgICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgY2lyY2xlX21hcmtlcl8yMzE5MTA4NzlmYmM0NWMxYjRmZTI3ZWUyZTBkOTM5YyA9IEwuY2lyY2xlTWFya2VyKAogICAgICAgICAgICAgICAgWzM3LjUzOTc4MjcsIDEyNi44Mjk5OTY4XSwKICAgICAgICAgICAgICAgIHsKICAiYnViYmxpbmdNb3VzZUV2ZW50cyI6IHRydWUsCiAgImNvbG9yIjogIiMzMTg2Y2MiLAogICJkYXNoQXJyYXkiOiBudWxsLAogICJkYXNoT2Zmc2V0IjogbnVsbCwKICAiZmlsbCI6IHRydWUsCiAgImZpbGxDb2xvciI6ICIjMzE4NmNjIiwKICAiZmlsbE9wYWNpdHkiOiAwLjIsCiAgImZpbGxSdWxlIjogImV2ZW5vZGQiLAogICJsaW5lQ2FwIjogInJvdW5kIiwKICAibGluZUpvaW4iOiAicm91bmQiLAogICJvcGFjaXR5IjogMS4wLAogICJyYWRpdXMiOiAzOC40NDU2NjY5ODUzNTMxLAogICJzdHJva2UiOiB0cnVlLAogICJ3ZWlnaHQiOiAzCn0KICAgICAgICAgICAgICAgICkKICAgICAgICAgICAgICAgIC5hZGRUbyhtYXBfOGYyMTExMTE3NzdjNDk5MTg2MmEwYzhiY2MzODc1NmQpOwogICAgICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgY2lyY2xlX21hcmtlcl8yN2E4ZmM5YmVjODY0Y2IwOWZhNjA4YzdhOGMzNjkxMiA9IEwuY2lyY2xlTWFya2VyKAogICAgICAgICAgICAgICAgWzM3LjUyODUxMSwgMTI3LjEyNjgyMjRdLAogICAgICAgICAgICAgICAgewogICJidWJibGluZ01vdXNlRXZlbnRzIjogdHJ1ZSwKICAiY29sb3IiOiAiIzMxODZjYyIsCiAgImRhc2hBcnJheSI6IG51bGwsCiAgImRhc2hPZmZzZXQiOiBudWxsLAogICJmaWxsIjogdHJ1ZSwKICAiZmlsbENvbG9yIjogIiMzMTg2Y2MiLAogICJmaWxsT3BhY2l0eSI6IDAuMiwKICAiZmlsbFJ1bGUiOiAiZXZlbm9kZCIsCiAgImxpbmVDYXAiOiAicm91bmQiLAogICJsaW5lSm9pbiI6ICJyb3VuZCIsCiAgIm9wYWNpdHkiOiAxLjAsCiAgInJhZGl1cyI6IDI0LjMyNzI1MzM0Mjg2OTg4OCwKICAic3Ryb2tlIjogdHJ1ZSwKICAid2VpZ2h0IjogMwp9CiAgICAgICAgICAgICAgICApCiAgICAgICAgICAgICAgICAuYWRkVG8obWFwXzhmMjExMTExNzc3YzQ5OTE4NjJhMGM4YmNjMzg3NTZkKTsKICAgICAgICAgICAgCiAgICAKICAgICAgICAgICAgdmFyIGNpcmNsZV9tYXJrZXJfY2Y2YWNiODY1NmVkNDIxMmE4ZDZlMzYxNTcwZDMyNDMgPSBMLmNpcmNsZU1hcmtlcigKICAgICAgICAgICAgICAgIFszNy42MDIwNTkyLCAxMjcuMDMyMTU3N10sCiAgICAgICAgICAgICAgICB7CiAgImJ1YmJsaW5nTW91c2VFdmVudHMiOiB0cnVlLAogICJjb2xvciI6ICIjMzE4NmNjIiwKICAiZGFzaEFycmF5IjogbnVsbCwKICAiZGFzaE9mZnNldCI6IG51bGwsCiAgImZpbGwiOiB0cnVlLAogICJmaWxsQ29sb3IiOiAiIzMxODZjYyIsCiAgImZpbGxPcGFjaXR5IjogMC4yLAogICJmaWxsUnVsZSI6ICJldmVub2RkIiwKICAibGluZUNhcCI6ICJyb3VuZCIsCiAgImxpbmVKb2luIjogInJvdW5kIiwKICAib3BhY2l0eSI6IDEuMCwKICAicmFkaXVzIjogMTEuMjM2MzE3OTU3MjUzMjYsCiAgInN0cm9rZSI6IHRydWUsCiAgIndlaWdodCI6IDMKfQogICAgICAgICAgICAgICAgKQogICAgICAgICAgICAgICAgLmFkZFRvKG1hcF84ZjIxMTExMTc3N2M0OTkxODYyYTBjOGJjYzM4NzU2ZCk7CiAgICAgICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBjaXJjbGVfbWFya2VyXzZlM2NlMjQxZjNmZDRlODM4NDI5ODMyMzVkZDMzNjJhID0gTC5jaXJjbGVNYXJrZXIoCiAgICAgICAgICAgICAgICBbMzcuNDk0OTMxLCAxMjYuODg2NzMxXSwKICAgICAgICAgICAgICAgIHsKICAiYnViYmxpbmdNb3VzZUV2ZW50cyI6IHRydWUsCiAgImNvbG9yIjogIiMzMTg2Y2MiLAogICJkYXNoQXJyYXkiOiBudWxsLAogICJkYXNoT2Zmc2V0IjogbnVsbCwKICAiZmlsbCI6IHRydWUsCiAgImZpbGxDb2xvciI6ICIjMzE4NmNjIiwKICAiZmlsbE9wYWNpdHkiOiAwLjIsCiAgImZpbGxSdWxlIjogImV2ZW5vZGQiLAogICJsaW5lQ2FwIjogInJvdW5kIiwKICAibGluZUpvaW4iOiAicm91bmQiLAogICJvcGFjaXR5IjogMS4wLAogICJyYWRpdXMiOiAzMS4wNDU1MTU1NjY2MDE0MzgsCiAgInN0cm9rZSI6IHRydWUsCiAgIndlaWdodCI6IDMKfQogICAgICAgICAgICAgICAgKQogICAgICAgICAgICAgICAgLmFkZFRvKG1hcF84ZjIxMTExMTc3N2M0OTkxODYyYTBjOGJjYzM4NzU2ZCk7CiAgICAgICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBjaXJjbGVfbWFya2VyXzQwNTcwYjllYmIxODQ1ODViZWJiNTFhNzRmYWZlYTVjID0gTC5jaXJjbGVNYXJrZXIoCiAgICAgICAgICAgICAgICBbMzcuNDk1NjA1NCwgMTI3LjAwNTI1MDRdLAogICAgICAgICAgICAgICAgewogICJidWJibGluZ01vdXNlRXZlbnRzIjogdHJ1ZSwKICAiY29sb3IiOiAiIzMxODZjYyIsCiAgImRhc2hBcnJheSI6IG51bGwsCiAgImRhc2hPZmZzZXQiOiBudWxsLAogICJmaWxsIjogdHJ1ZSwKICAiZmlsbENvbG9yIjogIiMzMTg2Y2MiLAogICJmaWxsT3BhY2l0eSI6IDAuMiwKICAiZmlsbFJ1bGUiOiAiZXZlbm9kZCIsCiAgImxpbmVDYXAiOiAicm91bmQiLAogICJsaW5lSm9pbiI6ICJyb3VuZCIsCiAgIm9wYWNpdHkiOiAxLjAsCiAgInJhZGl1cyI6IDI1LjU4NDMxODQ0Njc0OTczNCwKICAic3Ryb2tlIjogdHJ1ZSwKICAid2VpZ2h0IjogMwp9CiAgICAgICAgICAgICAgICApCiAgICAgICAgICAgICAgICAuYWRkVG8obWFwXzhmMjExMTExNzc3YzQ5OTE4NjJhMGM4YmNjMzg3NTZkKTsKICAgICAgICAgICAgCiAgICAKICAgICAgICAgICAgdmFyIGNpcmNsZV9tYXJrZXJfMDY0YmY5OWIyNDNhNDM1ZGFjNjY1NzVhYWY2NDA3MGEgPSBMLmNpcmNsZU1hcmtlcigKICAgICAgICAgICAgICAgIFszNy41MTY1NjY3LCAxMjYuODY1Njc2M10sCiAgICAgICAgICAgICAgICB7CiAgImJ1YmJsaW5nTW91c2VFdmVudHMiOiB0cnVlLAogICJjb2xvciI6ICIjMzE4NmNjIiwKICAiZGFzaEFycmF5IjogbnVsbCwKICAiZGFzaE9mZnNldCI6IG51bGwsCiAgImZpbGwiOiB0cnVlLAogICJmaWxsQ29sb3IiOiAiIzMxODZjYyIsCiAgImZpbGxPcGFjaXR5IjogMC4yLAogICJmaWxsUnVsZSI6ICJldmVub2RkIiwKICAibGluZUNhcCI6ICJyb3VuZCIsCiAgImxpbmVKb2luIjogInJvdW5kIiwKICAib3BhY2l0eSI6IDEuMCwKICAicmFkaXVzIjogMjEuNjg5NjUwNTIyNzk4NTM0LAogICJzdHJva2UiOiB0cnVlLAogICJ3ZWlnaHQiOiAzCn0KICAgICAgICAgICAgICAgICkKICAgICAgICAgICAgICAgIC5hZGRUbyhtYXBfOGYyMTExMTE3NzdjNDk5MTg2MmEwYzhiY2MzODc1NmQpOwogICAgICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgY2lyY2xlX21hcmtlcl9iYWU1MDUzMDVjY2M0OWI1OTBhYmM0ZGE0ZDBlMDI2OCA9IEwuY2lyY2xlTWFya2VyKAogICAgICAgICAgICAgICAgWzM3LjUwMTkwNjUsIDEyNy4xMjcxNTEzXSwKICAgICAgICAgICAgICAgIHsKICAiYnViYmxpbmdNb3VzZUV2ZW50cyI6IHRydWUsCiAgImNvbG9yIjogIiMzMTg2Y2MiLAogICJkYXNoQXJyYXkiOiBudWxsLAogICJkYXNoT2Zmc2V0IjogbnVsbCwKICAiZmlsbCI6IHRydWUsCiAgImZpbGxDb2xvciI6ICIjMzE4NmNjIiwKICAiZmlsbE9wYWNpdHkiOiAwLjIsCiAgImZpbGxSdWxlIjogImV2ZW5vZGQiLAogICJsaW5lQ2FwIjogInJvdW5kIiwKICAibGluZUpvaW4iOiAicm91bmQiLAogICJvcGFjaXR5IjogMS4wLAogICJyYWRpdXMiOiAzNy42MzU5ODIwMzY0ODc2NiwKICAic3Ryb2tlIjogdHJ1ZSwKICAid2VpZ2h0IjogMwp9CiAgICAgICAgICAgICAgICApCiAgICAgICAgICAgICAgICAuYWRkVG8obWFwXzhmMjExMTExNzc3YzQ5OTE4NjJhMGM4YmNjMzg3NTZkKTsKICAgICAgICAgICAgCiAgICAKICAgICAgICAgICAgdmFyIGNpcmNsZV9tYXJrZXJfMTc0MjA1YjQyY2MxNGRmN2I4NzQ3OWI0MTUzOTgyMzUgPSBMLmNpcmNsZU1hcmtlcigKICAgICAgICAgICAgICAgIFszNy42NDIzNjA1LCAxMjcuMDcxNDAyN10sCiAgICAgICAgICAgICAgICB7CiAgImJ1YmJsaW5nTW91c2VFdmVudHMiOiB0cnVlLAogICJjb2xvciI6ICIjMzE4NmNjIiwKICAiZGFzaEFycmF5IjogbnVsbCwKICAiZGFzaE9mZnNldCI6IG51bGwsCiAgImZpbGwiOiB0cnVlLAogICJmaWxsQ29sb3IiOiAiIzMxODZjYyIsCiAgImZpbGxPcGFjaXR5IjogMC4yLAogICJmaWxsUnVsZSI6ICJldmVub2RkIiwKICAibGluZUNhcCI6ICJyb3VuZCIsCiAgImxpbmVKb2luIjogInJvdW5kIiwKICAib3BhY2l0eSI6IDEuMCwKICAicmFkaXVzIjogMzAuMDYyNTI2MTA1NDI4MzA4LAogICJzdHJva2UiOiB0cnVlLAogICJ3ZWlnaHQiOiAzCn0KICAgICAgICAgICAgICAgICkKICAgICAgICAgICAgICAgIC5hZGRUbyhtYXBfOGYyMTExMTE3NzdjNDk5MTg2MmEwYzhiY2MzODc1NmQpOwogICAgICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgY2lyY2xlX21hcmtlcl9hNTRhNmE3MDU1OTI0MWE4YjhhMTRlYThkNmEzNjNhNSA9IEwuY2lyY2xlTWFya2VyKAogICAgICAgICAgICAgICAgWzM3LjQ5NDU5NTksIDEyNi45ODMxMjc5XSwKICAgICAgICAgICAgICAgIHsKICAiYnViYmxpbmdNb3VzZUV2ZW50cyI6IHRydWUsCiAgImNvbG9yIjogIiMzMTg2Y2MiLAogICJkYXNoQXJyYXkiOiBudWxsLAogICJkYXNoT2Zmc2V0IjogbnVsbCwKICAiZmlsbCI6IHRydWUsCiAgImZpbGxDb2xvciI6ICIjMzE4NmNjIiwKICAiZmlsbE9wYWNpdHkiOiAwLjIsCiAgImZpbGxSdWxlIjogImV2ZW5vZGQiLAogICJsaW5lQ2FwIjogInJvdW5kIiwKICAibGluZUpvaW4iOiAicm91bmQiLAogICJvcGFjaXR5IjogMS4wLAogICJyYWRpdXMiOiA3LjQzMzI1MDg2NDg1NzE3MiwKICAic3Ryb2tlIjogdHJ1ZSwKICAid2VpZ2h0IjogMwp9CiAgICAgICAgICAgICAgICApCiAgICAgICAgICAgICAgICAuYWRkVG8obWFwXzhmMjExMTExNzc3YzQ5OTE4NjJhMGM4YmNjMzg3NTZkKTsKICAgICAgICAgICAgCiAgICAKICAgICAgICAgICAgdmFyIGNpcmNsZV9tYXJrZXJfMWQ5ODVmNDNkODJhNDgyNzhiYWJiMjJhOTczZGEyOTIgPSBMLmNpcmNsZU1hcmtlcigKICAgICAgICAgICAgICAgIFszNy42MjgzNTk3LCAxMjYuOTI4NzIyNl0sCiAgICAgICAgICAgICAgICB7CiAgImJ1YmJsaW5nTW91c2VFdmVudHMiOiB0cnVlLAogICJjb2xvciI6ICIjMzE4NmNjIiwKICAiZGFzaEFycmF5IjogbnVsbCwKICAiZGFzaE9mZnNldCI6IG51bGwsCiAgImZpbGwiOiB0cnVlLAogICJmaWxsQ29sb3IiOiAiIzMxODZjYyIsCiAgImZpbGxPcGFjaXR5IjogMC4yLAogICJmaWxsUnVsZSI6ICJldmVub2RkIiwKICAibGluZUNhcCI6ICJyb3VuZCIsCiAgImxpbmVKb2luIjogInJvdW5kIiwKICAib3BhY2l0eSI6IDEuMCwKICAicmFkaXVzIjogMTMuNjMxODgyMTUwNzM0OTUzLAogICJzdHJva2UiOiB0cnVlLAogICJ3ZWlnaHQiOiAzCn0KICAgICAgICAgICAgICAgICkKICAgICAgICAgICAgICAgIC5hZGRUbyhtYXBfOGYyMTExMTE3NzdjNDk5MTg2MmEwYzhiY2MzODc1NmQpOwogICAgICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgY2lyY2xlX21hcmtlcl84NTAzYzVmZTExYTE0NDBmYTA1MWQ5ZGIwOTg4NjZkMiA9IEwuY2lyY2xlTWFya2VyKAogICAgICAgICAgICAgICAgWzM3LjY1MzM1ODksIDEyNy4wNTI2ODJdLAogICAgICAgICAgICAgICAgewogICJidWJibGluZ01vdXNlRXZlbnRzIjogdHJ1ZSwKICAiY29sb3IiOiAiIzMxODZjYyIsCiAgImRhc2hBcnJheSI6IG51bGwsCiAgImRhc2hPZmZzZXQiOiBudWxsLAogICJmaWxsIjogdHJ1ZSwKICAiZmlsbENvbG9yIjogIiMzMTg2Y2MiLAogICJmaWxsT3BhY2l0eSI6IDAuMiwKICAiZmlsbFJ1bGUiOiAiZXZlbm9kZCIsCiAgImxpbmVDYXAiOiAicm91bmQiLAogICJsaW5lSm9pbiI6ICJyb3VuZCIsCiAgIm9wYWNpdHkiOiAxLjAsCiAgInJhZGl1cyI6IDE4Ljc4MTM0MDE4Mjk4MTg1NywKICAic3Ryb2tlIjogdHJ1ZSwKICAid2VpZ2h0IjogMwp9CiAgICAgICAgICAgICAgICApCiAgICAgICAgICAgICAgICAuYWRkVG8obWFwXzhmMjExMTExNzc3YzQ5OTE4NjJhMGM4YmNjMzg3NTZkKTsKICAgICAgICAgICAgCiAgICAKICAgICAgICAgICAgdmFyIGNpcmNsZV9tYXJrZXJfNWJjMTNjMzNjNDM1NGNlYmIyNWI2YmZiZjk1YzEyYTQgPSBMLmNpcmNsZU1hcmtlcigKICAgICAgICAgICAgICAgIFszNy40OTM0OSwgMTI3LjA3NzIxMTldLAogICAgICAgICAgICAgICAgewogICJidWJibGluZ01vdXNlRXZlbnRzIjogdHJ1ZSwKICAiY29sb3IiOiAiIzMxODZjYyIsCiAgImRhc2hBcnJheSI6IG51bGwsCiAgImRhc2hPZmZzZXQiOiBudWxsLAogICJmaWxsIjogdHJ1ZSwKICAiZmlsbENvbG9yIjogIiMzMTg2Y2MiLAogICJmaWxsT3BhY2l0eSI6IDAuMiwKICAiZmlsbFJ1bGUiOiAiZXZlbm9kZCIsCiAgImxpbmVDYXAiOiAicm91bmQiLAogICJsaW5lSm9pbiI6ICJyb3VuZCIsCiAgIm9wYWNpdHkiOiAxLjAsCiAgInJhZGl1cyI6IDIzLjY0MTczMDE2NjQ0MjUxNCwKICAic3Ryb2tlIjogdHJ1ZSwKICAid2VpZ2h0IjogMwp9CiAgICAgICAgICAgICAgICApCiAgICAgICAgICAgICAgICAuYWRkVG8obWFwXzhmMjExMTExNzc3YzQ5OTE4NjJhMGM4YmNjMzg3NTZkKTsKICAgICAgICAgICAgCjwvc2NyaXB0Pg==" style="position:absolute;width:100%;height:100%;left:0;top:0;border:none !important;" allowfullscreen webkitallowfullscreen mozallowfullscreen></iframe></div></div>




```python
map = folium.Map(location=[37.5502, 126.982], zoom_start=11)

map.choropleth(geo_data = geo_str,
               data = crime_anal_norm['범죄'],
               columns = [crime_anal_norm.index, crime_anal_norm['범죄']],
               fill_color = 'PuRd', #PuRd, YlGnBu
               key_on = 'feature.id')

for n in crime_anal_raw.index:
    folium.CircleMarker([crime_anal_raw['lat'][n], crime_anal_raw['lng'][n]], 
                        radius = crime_anal_raw['검거'][n]*10, 
                        color='#3186cc', fill_color='#3186cc', fill=True).add_to(map)
    
map
```

    C:\ProgramData\Anaconda3\lib\site-packages\folium\folium.py:432: FutureWarning: The choropleth  method has been deprecated. Instead use the new Choropleth class, which has the same arguments. See the example notebook 'GeoJSON_and_choropleth' for how to do this.
      FutureWarning
    




<div style="width:100%;"><div style="position:relative;width:100%;height:0;padding-bottom:60%;"><iframe src="data:text/html;charset=utf-8;base64,PCFET0NUWVBFIGh0bWw+CjxoZWFkPiAgICAKICAgIDxtZXRhIGh0dHAtZXF1aXY9ImNvbnRlbnQtdHlwZSIgY29udGVudD0idGV4dC9odG1sOyBjaGFyc2V0PVVURi04IiAvPgogICAgPHNjcmlwdD5MX1BSRUZFUl9DQU5WQVM9ZmFsc2U7IExfTk9fVE9VQ0g9ZmFsc2U7IExfRElTQUJMRV8zRD1mYWxzZTs8L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmpzIj48L3NjcmlwdD4KICAgIDxzY3JpcHQgc3JjPSJodHRwczovL2FqYXguZ29vZ2xlYXBpcy5jb20vYWpheC9saWJzL2pxdWVyeS8xLjExLjEvanF1ZXJ5Lm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvanMvYm9vdHN0cmFwLm1pbi5qcyI+PC9zY3JpcHQ+CiAgICA8c2NyaXB0IHNyYz0iaHR0cHM6Ly9jZG5qcy5jbG91ZGZsYXJlLmNvbS9hamF4L2xpYnMvTGVhZmxldC5hd2Vzb21lLW1hcmtlcnMvMi4wLjIvbGVhZmxldC5hd2Vzb21lLW1hcmtlcnMuanMiPjwvc2NyaXB0PgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2Nkbi5qc2RlbGl2ci5uZXQvbnBtL2xlYWZsZXRAMS4zLjQvZGlzdC9sZWFmbGV0LmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL21heGNkbi5ib290c3RyYXBjZG4uY29tL2Jvb3RzdHJhcC8zLjIuMC9jc3MvYm9vdHN0cmFwLm1pbi5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9tYXhjZG4uYm9vdHN0cmFwY2RuLmNvbS9ib290c3RyYXAvMy4yLjAvY3NzL2Jvb3RzdHJhcC10aGVtZS5taW4uY3NzIi8+CiAgICA8bGluayByZWw9InN0eWxlc2hlZXQiIGhyZWY9Imh0dHBzOi8vbWF4Y2RuLmJvb3RzdHJhcGNkbi5jb20vZm9udC1hd2Vzb21lLzQuNi4zL2Nzcy9mb250LWF3ZXNvbWUubWluLmNzcyIvPgogICAgPGxpbmsgcmVsPSJzdHlsZXNoZWV0IiBocmVmPSJodHRwczovL2NkbmpzLmNsb3VkZmxhcmUuY29tL2FqYXgvbGlicy9MZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy8yLjAuMi9sZWFmbGV0LmF3ZXNvbWUtbWFya2Vycy5jc3MiLz4KICAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgaHJlZj0iaHR0cHM6Ly9yYXdjZG4uZ2l0aGFjay5jb20vcHl0aG9uLXZpc3VhbGl6YXRpb24vZm9saXVtL21hc3Rlci9mb2xpdW0vdGVtcGxhdGVzL2xlYWZsZXQuYXdlc29tZS5yb3RhdGUuY3NzIi8+CiAgICA8c3R5bGU+aHRtbCwgYm9keSB7d2lkdGg6IDEwMCU7aGVpZ2h0OiAxMDAlO21hcmdpbjogMDtwYWRkaW5nOiAwO308L3N0eWxlPgogICAgPHN0eWxlPiNtYXAge3Bvc2l0aW9uOmFic29sdXRlO3RvcDowO2JvdHRvbTowO3JpZ2h0OjA7bGVmdDowO308L3N0eWxlPgogICAgCiAgICA8bWV0YSBuYW1lPSJ2aWV3cG9ydCIgY29udGVudD0id2lkdGg9ZGV2aWNlLXdpZHRoLAogICAgICAgIGluaXRpYWwtc2NhbGU9MS4wLCBtYXhpbXVtLXNjYWxlPTEuMCwgdXNlci1zY2FsYWJsZT1ubyIgLz4KICAgIDxzdHlsZT4jbWFwXzU1YmU3M2ViMDcxZTQwNjViOGEwOTdjNjI0YjJkNGMwIHsKICAgICAgICBwb3NpdGlvbjogcmVsYXRpdmU7CiAgICAgICAgd2lkdGg6IDEwMC4wJTsKICAgICAgICBoZWlnaHQ6IDEwMC4wJTsKICAgICAgICBsZWZ0OiAwLjAlOwogICAgICAgIHRvcDogMC4wJTsKICAgICAgICB9CiAgICA8L3N0eWxlPgogICAgPHNjcmlwdCBzcmM9Imh0dHBzOi8vY2RuanMuY2xvdWRmbGFyZS5jb20vYWpheC9saWJzL2QzLzMuNS41L2QzLm1pbi5qcyI+PC9zY3JpcHQ+CjwvaGVhZD4KPGJvZHk+ICAgIAogICAgCiAgICA8ZGl2IGNsYXNzPSJmb2xpdW0tbWFwIiBpZD0ibWFwXzU1YmU3M2ViMDcxZTQwNjViOGEwOTdjNjI0YjJkNGMwIiA+PC9kaXY+CjwvYm9keT4KPHNjcmlwdD4gICAgCiAgICAKICAgIAogICAgICAgIHZhciBib3VuZHMgPSBudWxsOwogICAgCgogICAgdmFyIG1hcF81NWJlNzNlYjA3MWU0MDY1YjhhMDk3YzYyNGIyZDRjMCA9IEwubWFwKAogICAgICAgICdtYXBfNTViZTczZWIwNzFlNDA2NWI4YTA5N2M2MjRiMmQ0YzAnLCB7CiAgICAgICAgY2VudGVyOiBbMzcuNTUwMiwgMTI2Ljk4Ml0sCiAgICAgICAgem9vbTogMTEsCiAgICAgICAgbWF4Qm91bmRzOiBib3VuZHMsCiAgICAgICAgbGF5ZXJzOiBbXSwKICAgICAgICB3b3JsZENvcHlKdW1wOiBmYWxzZSwKICAgICAgICBjcnM6IEwuQ1JTLkVQU0czODU3LAogICAgICAgIHpvb21Db250cm9sOiB0cnVlLAogICAgICAgIH0pOwoKICAgIAogICAgCiAgICB2YXIgdGlsZV9sYXllcl83OTg5NTBiMTBiN2U0ZTAyYTZkOTViYWIxMmNhYTc4NCA9IEwudGlsZUxheWVyKAogICAgICAgICdodHRwczovL3tzfS50aWxlLm9wZW5zdHJlZXRtYXAub3JnL3t6fS97eH0ve3l9LnBuZycsCiAgICAgICAgewogICAgICAgICJhdHRyaWJ1dGlvbiI6IG51bGwsCiAgICAgICAgImRldGVjdFJldGluYSI6IGZhbHNlLAogICAgICAgICJtYXhOYXRpdmVab29tIjogMTgsCiAgICAgICAgIm1heFpvb20iOiAxOCwKICAgICAgICAibWluWm9vbSI6IDAsCiAgICAgICAgIm5vV3JhcCI6IGZhbHNlLAogICAgICAgICJvcGFjaXR5IjogMSwKICAgICAgICAic3ViZG9tYWlucyI6ICJhYmMiLAogICAgICAgICJ0bXMiOiBmYWxzZQp9KS5hZGRUbyhtYXBfNTViZTczZWIwNzFlNDA2NWI4YTA5N2M2MjRiMmQ0YzApOwogICAgCiAgICAgICAgICAgIHZhciBjaG9yb3BsZXRoXzdhYTI3NGI5NGRiNjQ4ODQ5MmQ4NGMyMjlkY2VlNWZjID0gTC5mZWF0dXJlR3JvdXAoCiAgICAgICAgICAgICAgICApLmFkZFRvKG1hcF81NWJlNzNlYjA3MWU0MDY1YjhhMDk3YzYyNGIyZDRjMCk7CiAgICAgICAgCiAgICAKICAgICAgICAKICAgICAgICB2YXIgZ2VvX2pzb25fNmYyY2E0YWU5OWMwNDczOThhMjE4NDg4NjNiMzJiNzIgPSBMLmdlb0pzb24oCiAgICAgICAgICAgIHsiZmVhdHVyZXMiOiBbeyJnZW9tZXRyeSI6IHsiY29vcmRpbmF0ZXMiOiBbW1sxMjcuMTE1MTk1ODQ5ODE2MDYsIDM3LjU1NzUzMzE4MDcwNDkxNV0sIFsxMjcuMTY2ODMxODQzNjYxMjksIDM3LjU3NjcyNDg3Mzg4NjI3XSwgWzEyNy4xODQwODc5MjMzMDE1MiwgMzcuNTU4MTQyODAzNjk1NzVdLCBbMTI3LjE2NTMwOTg0MzA3NDQ3LCAzNy41NDIyMTg1MTI1ODY5M10sIFsxMjcuMTQ2NzI4MDY4MjM1MDIsIDM3LjUxNDE1NjgwNjgwMjkxXSwgWzEyNy4xMjEyMzE2NTcxOTYxNSwgMzcuNTI1MjgyNzAwODldLCBbMTI3LjExMTY3NjQyMDM2MDgsIDM3LjU0MDY2OTk1NTMyNDk2NV0sIFsxMjcuMTE1MTk1ODQ5ODE2MDYsIDM3LjU1NzUzMzE4MDcwNDkxNV1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVhYzE1XHViM2Q5XHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEyNTAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YWMxNVx1YjNkOVx1YWQ2YyIsICJuYW1lX2VuZyI6ICJHYW5nZG9uZy1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNkNGI5ZGEiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNy4wNjkwNjk4MTMwMzcyLCAzNy41MjIyNzk0MjM1MDUwMjZdLCBbMTI3LjEwMDg3NTE5NzkxOTYyLCAzNy41MjQ4NDEyMjAxNjcwNTVdLCBbMTI3LjExMTY3NjQyMDM2MDgsIDM3LjU0MDY2OTk1NTMyNDk2NV0sIFsxMjcuMTIxMjMxNjU3MTk2MTUsIDM3LjUyNTI4MjcwMDg5XSwgWzEyNy4xNDY3MjgwNjgyMzUwMiwgMzcuNTE0MTU2ODA2ODAyOTFdLCBbMTI3LjE2MzQ5NDQyMTU3NjUsIDM3LjQ5NzQ0NTQwNjA5NzQ4NF0sIFsxMjcuMTQyMDYwNTg0MTMyNzQsIDM3LjQ3MDg5ODE5MDk4NTAxXSwgWzEyNy4xMjQ0MDU3MTA4MDg5MywgMzcuNDYyNDA0NDU1ODcwNDhdLCBbMTI3LjExMTE3MDg1MjAxMjM4LCAzNy40ODU3MDgzODE1MTI0NDVdLCBbMTI3LjA3MTkxNDYwMDA3MjQsIDM3LjUwMjI0MDEzNTg3NjY5XSwgWzEyNy4wNjkwNjk4MTMwMzcyLCAzNy41MjIyNzk0MjM1MDUwMjZdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YzFhMVx1ZDMwY1x1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMjQwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWMxYTFcdWQzMGNcdWFkNmMiLCAibmFtZV9lbmciOiAiU29uZ3BhLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2RmNjViMCIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI3LjA1ODY3MzU5Mjg4Mzk4LCAzNy41MjYyOTk3NDkyMjU2OF0sIFsxMjcuMDY5MDY5ODEzMDM3MiwgMzcuNTIyMjc5NDIzNTA1MDI2XSwgWzEyNy4wNzE5MTQ2MDAwNzI0LCAzNy41MDIyNDAxMzU4NzY2OV0sIFsxMjcuMTExMTcwODUyMDEyMzgsIDM3LjQ4NTcwODM4MTUxMjQ0NV0sIFsxMjcuMTI0NDA1NzEwODA4OTMsIDM3LjQ2MjQwNDQ1NTg3MDQ4XSwgWzEyNy4wOTg0Mjc1OTMxODc1MSwgMzcuNDU4NjIyNTM4NTc0NjFdLCBbMTI3LjA4NjQwNDQwNTc4MTU2LCAzNy40NzI2OTc5MzUxODQ2NTVdLCBbMTI3LjA1NTkxNzA0ODE5MDQsIDM3LjQ2NTkyMjg5MTQwNzddLCBbMTI3LjAzNjIxOTE1MDk4Nzk4LCAzNy40ODE3NTgwMjQyNzYwM10sIFsxMjcuMDEzOTcxMTk2Njc1MTMsIDM3LjUyNTAzOTg4Mjg5NjY5XSwgWzEyNy4wMjMwMjgzMTg5MDU1OSwgMzcuNTMyMzE4OTk1ODI2NjNdLCBbMTI3LjA1ODY3MzU5Mjg4Mzk4LCAzNy41MjYyOTk3NDkyMjU2OF1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVhYzE1XHViMGE4XHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEyMzAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YWMxNVx1YjBhOFx1YWQ2YyIsICJuYW1lX2VuZyI6ICJHYW5nbmFtLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiIzk4MDA0MyIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI3LjAxMzk3MTE5NjY3NTEzLCAzNy41MjUwMzk4ODI4OTY2OV0sIFsxMjcuMDM2MjE5MTUwOTg3OTgsIDM3LjQ4MTc1ODAyNDI3NjAzXSwgWzEyNy4wNTU5MTcwNDgxOTA0LCAzNy40NjU5MjI4OTE0MDc3XSwgWzEyNy4wODY0MDQ0MDU3ODE1NiwgMzcuNDcyNjk3OTM1MTg0NjU1XSwgWzEyNy4wOTg0Mjc1OTMxODc1MSwgMzcuNDU4NjIyNTM4NTc0NjFdLCBbMTI3LjA5MDQ2OTI4NTY1OTUxLCAzNy40NDI5NjgyNjExNDE4NV0sIFsxMjcuMDY3NzgxMDc2MDU0MzMsIDM3LjQyNjE5NzQyNDA1NzMxNF0sIFsxMjcuMDQ5NTcyMzI5ODcxNDIsIDM3LjQyODA1ODM2ODQ1Njk0XSwgWzEyNy4wMzg4MTc4MjU5NzkyMiwgMzcuNDUzODIwMzk4NTE3MTVdLCBbMTI2Ljk5MDcyMDczMTk1NDYyLCAzNy40NTUzMjYxNDMzMTAwMjVdLCBbMTI2Ljk4MzY3NjY4MjkxODAyLCAzNy40NzM4NTY0OTI2OTIwODZdLCBbMTI2Ljk4MjIzODA3OTE2MDgxLCAzNy41MDkzMTQ5NjY3NzAzMjZdLCBbMTI3LjAxMzk3MTE5NjY3NTEzLCAzNy41MjUwMzk4ODI4OTY2OV1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVjMTFjXHVjZDA4XHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEyMjAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YzExY1x1Y2QwOFx1YWQ2YyIsICJuYW1lX2VuZyI6ICJTZW9jaG8tZ3UiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICIjYzk5NGM3IiwgImZpbGxPcGFjaXR5IjogMC42LCAib3BhY2l0eSI6IDEsICJ3ZWlnaHQiOiAxfX0sICJ0eXBlIjogIkZlYXR1cmUifSwgeyJnZW9tZXRyeSI6IHsiY29vcmRpbmF0ZXMiOiBbW1sxMjYuOTgzNjc2NjgyOTE4MDIsIDM3LjQ3Mzg1NjQ5MjY5MjA4Nl0sIFsxMjYuOTkwNzIwNzMxOTU0NjIsIDM3LjQ1NTMyNjE0MzMxMDAyNV0sIFsxMjYuOTY1MjA0MzkwODUxNDMsIDM3LjQzODI0OTc4NDAwNjI0Nl0sIFsxMjYuOTUwMDAwMDEwMTAxODIsIDM3LjQzNjEzNDUxMTY1NzE5XSwgWzEyNi45MzA4NDQwODA1NjUyNSwgMzcuNDQ3MzgyOTI4MzMzOTk0XSwgWzEyNi45MTY3NzI4MTQ2NjAxLCAzNy40NTQ5MDU2NjQyMzc4OV0sIFsxMjYuOTAxNTYwOTQxMjk4OTUsIDM3LjQ3NzUzODQyNzg5OTAxXSwgWzEyNi45MDUzMTk3NTgwMTgxMiwgMzcuNDgyMTgwODc1NzU0MjldLCBbMTI2Ljk0OTIyNjYxMzg5NTA4LCAzNy40OTEyNTQzNzQ5NTY0OV0sIFsxMjYuOTcyNTg5MTg1MDY2MiwgMzcuNDcyNTYxMzYzMjc4MTI1XSwgWzEyNi45ODM2NzY2ODI5MTgwMiwgMzcuNDczODU2NDkyNjkyMDg2XV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJcdWFkMDBcdWM1NDVcdWFkNmMiLCAicHJvcGVydGllcyI6IHsiYmFzZV95ZWFyIjogIjIwMTMiLCAiY29kZSI6ICIxMTIxMCIsICJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiXHVhZDAwXHVjNTQ1XHVhZDZjIiwgIm5hbWVfZW5nIjogIkd3YW5hay1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogIiNkZjY1YjAiLCAiZmlsbE9wYWNpdHkiOiAwLjYsICJvcGFjaXR5IjogMSwgIndlaWdodCI6IDF9fSwgInR5cGUiOiAiRmVhdHVyZSJ9LCB7Imdlb21ldHJ5IjogeyJjb29yZGluYXRlcyI6IFtbWzEyNi45ODIyMzgwNzkxNjA4MSwgMzcuNTA5MzE0OTY2NzcwMzI2XSwgWzEyNi45ODM2NzY2ODI5MTgwMiwgMzcuNDczODU2NDkyNjkyMDg2XSwgWzEyNi45NzI1ODkxODUwNjYyLCAzNy40NzI1NjEzNjMyNzgxMjVdLCBbMTI2Ljk0OTIyNjYxMzg5NTA4LCAzNy40OTEyNTQzNzQ5NTY0OV0sIFsxMjYuOTA1MzE5NzU4MDE4MTIsIDM3LjQ4MjE4MDg3NTc1NDI5XSwgWzEyNi45MjE3Nzg5MzE3NDgyNSwgMzcuNDk0ODg5ODc3NDE1MTc2XSwgWzEyNi45MjgxMDYyODgyODI3OSwgMzcuNTEzMjk1OTU3MzIwMTVdLCBbMTI2Ljk1MjQ5OTkwMjk4MTU5LCAzNy41MTcyMjUwMDc0MTgxM10sIFsxMjYuOTgyMjM4MDc5MTYwODEsIDM3LjUwOTMxNDk2Njc3MDMyNl1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHViM2Q5XHVjNzkxXHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEyMDAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YjNkOVx1Yzc5MVx1YWQ2YyIsICJuYW1lX2VuZyI6ICJEb25namFrLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2Q0YjlkYSIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2Ljg5MTg0NjYzODYyNzY0LCAzNy41NDczNzM5NzQ5OTcxMTRdLCBbMTI2Ljk0NTY2NzMzMDgzMjEyLCAzNy41MjY2MTc1NDI0NTMzNjZdLCBbMTI2Ljk1MjQ5OTkwMjk4MTU5LCAzNy41MTcyMjUwMDc0MTgxM10sIFsxMjYuOTI4MTA2Mjg4MjgyNzksIDM3LjUxMzI5NTk1NzMyMDE1XSwgWzEyNi45MjE3Nzg5MzE3NDgyNSwgMzcuNDk0ODg5ODc3NDE1MTc2XSwgWzEyNi45MDUzMTk3NTgwMTgxMiwgMzcuNDgyMTgwODc1NzU0MjldLCBbMTI2Ljg5NTk0Nzc2NzgyNDg1LCAzNy41MDQ2NzUyODEzMDkxNzZdLCBbMTI2Ljg4MTU2NDAyMzUzODYyLCAzNy41MTM5NzAwMzQ3NjU2ODRdLCBbMTI2Ljg4ODI1NzU3ODYwMDk5LCAzNy41NDA3OTczMzYzMDIzMl0sIFsxMjYuODkxODQ2NjM4NjI3NjQsIDM3LjU0NzM3Mzk3NDk5NzExNF1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVjNjAxXHViNGYxXHVkM2VjXHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTExOTAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YzYwMVx1YjRmMVx1ZDNlY1x1YWQ2YyIsICJuYW1lX2VuZyI6ICJZZW9uZ2RldW5ncG8tZ3UiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICIjZGQxYzc3IiwgImZpbGxPcGFjaXR5IjogMC42LCAib3BhY2l0eSI6IDEsICJ3ZWlnaHQiOiAxfX0sICJ0eXBlIjogIkZlYXR1cmUifSwgeyJnZW9tZXRyeSI6IHsiY29vcmRpbmF0ZXMiOiBbW1sxMjYuOTAxNTYwOTQxMjk4OTUsIDM3LjQ3NzUzODQyNzg5OTAxXSwgWzEyNi45MTY3NzI4MTQ2NjAxLCAzNy40NTQ5MDU2NjQyMzc4OV0sIFsxMjYuOTMwODQ0MDgwNTY1MjUsIDM3LjQ0NzM4MjkyODMzMzk5NF0sIFsxMjYuOTAyNTgzMTcxMTY5NywgMzcuNDM0NTQ5MzY2MzQ5MTI0XSwgWzEyNi44NzY4MzI3MTUwMjQyOCwgMzcuNDgyNTc2NTkxNjA3MzA1XSwgWzEyNi45MDE1NjA5NDEyOTg5NSwgMzcuNDc3NTM4NDI3ODk5MDFdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YWUwOFx1Y2M5Y1x1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMTgwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWFlMDhcdWNjOWNcdWFkNmMiLCAibmFtZV9lbmciOiAiR2V1bWNoZW9uLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2YxZWVmNiIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2LjgyNjg4MDgxNTE3MzE0LCAzNy41MDU0ODk3MjIzMjg5Nl0sIFsxMjYuODgxNTY0MDIzNTM4NjIsIDM3LjUxMzk3MDAzNDc2NTY4NF0sIFsxMjYuODk1OTQ3NzY3ODI0ODUsIDM3LjUwNDY3NTI4MTMwOTE3Nl0sIFsxMjYuOTA1MzE5NzU4MDE4MTIsIDM3LjQ4MjE4MDg3NTc1NDI5XSwgWzEyNi45MDE1NjA5NDEyOTg5NSwgMzcuNDc3NTM4NDI3ODk5MDFdLCBbMTI2Ljg3NjgzMjcxNTAyNDI4LCAzNy40ODI1NzY1OTE2MDczMDVdLCBbMTI2Ljg0NzYyNjc2MDU0OTUzLCAzNy40NzE0NjcyMzkzNjMyM10sIFsxMjYuODM1NDk0ODUwNzYxOTYsIDM3LjQ3NDA5ODIzNjk3NTA5NV0sIFsxMjYuODIyNjQ3OTY3OTEzNDgsIDM3LjQ4Nzg0NzY0OTIxNDddLCBbMTI2LjgyNTA0NzM2MzMxNDA2LCAzNy41MDMwMjYxMjY0MDQ0M10sIFsxMjYuODI2ODgwODE1MTczMTQsIDM3LjUwNTQ4OTcyMjMyODk2XV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJcdWFkNmNcdWI4NWNcdWFkNmMiLCAicHJvcGVydGllcyI6IHsiYmFzZV95ZWFyIjogIjIwMTMiLCAiY29kZSI6ICIxMTE3MCIsICJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiXHVhZDZjXHViODVjXHVhZDZjIiwgIm5hbWVfZW5nIjogIkd1cm8tZ3UiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICIjZGY2NWIwIiwgImZpbGxPcGFjaXR5IjogMC42LCAib3BhY2l0eSI6IDEsICJ3ZWlnaHQiOiAxfX0sICJ0eXBlIjogIkZlYXR1cmUifSwgeyJnZW9tZXRyeSI6IHsiY29vcmRpbmF0ZXMiOiBbW1sxMjYuNzk1NzU3Njg1NTI5MDcsIDM3LjU3ODgxMDg3NjMzMjAyXSwgWzEyNi44MDcwMjExNTAyMzU5NywgMzcuNjAxMjMwMDEwMTMyMjhdLCBbMTI2LjgyMjUxNDM4NDc3MTA1LCAzNy41ODgwNDMwODEwMDgyXSwgWzEyNi44NTk4NDE5OTM5OTY2NywgMzcuNTcxODQ3ODU1MjkyNzQ1XSwgWzEyNi44OTE4NDY2Mzg2Mjc2NCwgMzcuNTQ3MzczOTc0OTk3MTE0XSwgWzEyNi44ODgyNTc1Nzg2MDA5OSwgMzcuNTQwNzk3MzM2MzAyMzJdLCBbMTI2Ljg2NjM3NDY0MzIxMjM4LCAzNy41NDg1OTE5MTA5NDgyM10sIFsxMjYuODY2MTAwNzM0NzYzOTUsIDM3LjUyNjk5OTY0MTQ0NjY5XSwgWzEyNi44NDI1NzI5MTk0MzE1MywgMzcuNTIzNzM3MDc4MDU1OTZdLCBbMTI2LjgyNDIzMzE0MjY3MjIsIDM3LjUzNzg4MDc4NzUzMjQ4XSwgWzEyNi43NzMyNDQxNzcxNzcwMywgMzcuNTQ1OTEyMzQ1MDU1NF0sIFsxMjYuNzY5NzkxODA1NzkzNTIsIDM3LjU1MTM5MTgzMDA4ODA5XSwgWzEyNi43OTU3NTc2ODU1MjkwNywgMzcuNTc4ODEwODc2MzMyMDJdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YWMxNVx1YzExY1x1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMTYwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWFjMTVcdWMxMWNcdWFkNmMiLCAibmFtZV9lbmciOiAiR2FuZ3Nlby1ndSIsICJzdHlsZSI6IHsiY29sb3IiOiAiYmxhY2siLCAiZmlsbENvbG9yIjogImJsYWNrIiwgImZpbGxPcGFjaXR5IjogMC42LCAib3BhY2l0eSI6IDEsICJ3ZWlnaHQiOiAxfX0sICJ0eXBlIjogIkZlYXR1cmUifSwgeyJnZW9tZXRyeSI6IHsiY29vcmRpbmF0ZXMiOiBbW1sxMjYuODI0MjMzMTQyNjcyMiwgMzcuNTM3ODgwNzg3NTMyNDhdLCBbMTI2Ljg0MjU3MjkxOTQzMTUzLCAzNy41MjM3MzcwNzgwNTU5Nl0sIFsxMjYuODY2MTAwNzM0NzYzOTUsIDM3LjUyNjk5OTY0MTQ0NjY5XSwgWzEyNi44NjYzNzQ2NDMyMTIzOCwgMzcuNTQ4NTkxOTEwOTQ4MjNdLCBbMTI2Ljg4ODI1NzU3ODYwMDk5LCAzNy41NDA3OTczMzYzMDIzMl0sIFsxMjYuODgxNTY0MDIzNTM4NjIsIDM3LjUxMzk3MDAzNDc2NTY4NF0sIFsxMjYuODI2ODgwODE1MTczMTQsIDM3LjUwNTQ4OTcyMjMyODk2XSwgWzEyNi44MjQyMzMxNDI2NzIyLCAzNy41Mzc4ODA3ODc1MzI0OF1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVjNTkxXHVjYzljXHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTExNTAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YzU5MVx1Y2M5Y1x1YWQ2YyIsICJuYW1lX2VuZyI6ICJZYW5nY2hlb24tZ3UiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICIjOTgwMDQzIiwgImZpbGxPcGFjaXR5IjogMC42LCAib3BhY2l0eSI6IDEsICJ3ZWlnaHQiOiAxfX0sICJ0eXBlIjogIkZlYXR1cmUifSwgeyJnZW9tZXRyeSI6IHsiY29vcmRpbmF0ZXMiOiBbW1sxMjYuOTA1MjIwNjU4MzEwNTMsIDM3LjU3NDA5NzAwNTIyNTc0XSwgWzEyNi45Mzg5ODE2MTc5ODk3MywgMzcuNTUyMzEwMDAzNzI4MTI0XSwgWzEyNi45NjM1ODIyNjcxMDgxMiwgMzcuNTU2MDU2MzU0NzUxNTRdLCBbMTI2Ljk2NDQ4NTcwNTUzMDU1LCAzNy41NDg3MDU2OTIwMjE2MzVdLCBbMTI2Ljk0NTY2NzMzMDgzMjEyLCAzNy41MjY2MTc1NDI0NTMzNjZdLCBbMTI2Ljg5MTg0NjYzODYyNzY0LCAzNy41NDczNzM5NzQ5OTcxMTRdLCBbMTI2Ljg1OTg0MTk5Mzk5NjY3LCAzNy41NzE4NDc4NTUyOTI3NDVdLCBbMTI2Ljg4NDMzMjg0NzczMjg4LCAzNy41ODgxNDMzMjI4ODA1MjZdLCBbMTI2LjkwNTIyMDY1ODMxMDUzLCAzNy41NzQwOTcwMDUyMjU3NF1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHViOWM4XHVkM2VjXHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTExNDAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YjljOFx1ZDNlY1x1YWQ2YyIsICJuYW1lX2VuZyI6ICJNYXBvLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2RmNjViMCIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2Ljk1MjQ3NTIwMzA1NzIsIDM3LjYwNTA4NjkyNzM3MDQ1XSwgWzEyNi45NTU2NTQyNTg0NjQ2MywgMzcuNTc2MDgwNzkwODgxNDU2XSwgWzEyNi45Njg3MzYzMzI3OTA3NSwgMzcuNTYzMTM2MDQ2OTA4MjddLCBbMTI2Ljk2MzU4MjI2NzEwODEyLCAzNy41NTYwNTYzNTQ3NTE1NF0sIFsxMjYuOTM4OTgxNjE3OTg5NzMsIDM3LjU1MjMxMDAwMzcyODEyNF0sIFsxMjYuOTA1MjIwNjU4MzEwNTMsIDM3LjU3NDA5NzAwNTIyNTc0XSwgWzEyNi45NTI0NzUyMDMwNTcyLCAzNy42MDUwODY5MjczNzA0NV1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVjMTFjXHViMzAwXHViYjM4XHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTExMzAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YzExY1x1YjMwMFx1YmIzOFx1YWQ2YyIsICJuYW1lX2VuZyI6ICJTZW9kYWVtdW4tZ3UiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICIjZjFlZWY2IiwgImZpbGxPcGFjaXR5IjogMC42LCAib3BhY2l0eSI6IDEsICJ3ZWlnaHQiOiAxfX0sICJ0eXBlIjogIkZlYXR1cmUifSwgeyJnZW9tZXRyeSI6IHsiY29vcmRpbmF0ZXMiOiBbW1sxMjYuOTczODg2NDEyODcwMiwgMzcuNjI5NDk2MzQ3ODY4ODhdLCBbMTI2Ljk1NDI3MDE3MDA2MTI5LCAzNy42MjIwMzM0MzEzMzk0MjVdLCBbMTI2Ljk1MjQ3NTIwMzA1NzIsIDM3LjYwNTA4NjkyNzM3MDQ1XSwgWzEyNi45MDUyMjA2NTgzMTA1MywgMzcuNTc0MDk3MDA1MjI1NzRdLCBbMTI2Ljg4NDMzMjg0NzczMjg4LCAzNy41ODgxNDMzMjI4ODA1MjZdLCBbMTI2LjkwMzk2NjgxMDAzNTk1LCAzNy41OTIyNzQwMzQxOTk0Ml0sIFsxMjYuOTAzMDMwNjYxNzc2NjgsIDM3LjYwOTk3NzkxMTQwMTM0NF0sIFsxMjYuOTE0NTU0ODE0Mjk2NDgsIDM3LjY0MTUwMDUwOTk2OTM1XSwgWzEyNi45NTY0NzM3OTczODcsIDM3LjY1MjQ4MDczNzMzOTQ0NV0sIFsxMjYuOTczODg2NDEyODcwMiwgMzcuNjI5NDk2MzQ3ODY4ODhdXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1Yzc0MFx1ZDNjOVx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMTIwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWM3NDBcdWQzYzlcdWFkNmMiLCAibmFtZV9lbmciOiAiRXVucHllb25nLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2Q0YjlkYSIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI3LjA4Mzg3NTI3MDMxOTUsIDM3LjY5MzU5NTM0MjAyMDM0XSwgWzEyNy4wOTcwNjM5MTMwOTY5NSwgMzcuNjg2MzgzNzE5MzcyMjk0XSwgWzEyNy4wOTQ0MDc2NjI5ODcxNywgMzcuNjQ3MTM0OTA0NzMwNDVdLCBbMTI3LjExMzI2Nzk1ODU1MTk5LCAzNy42Mzk2MjI5MDUzMTU5MjVdLCBbMTI3LjEwNzgyMjc3Njg4MTI5LCAzNy42MTgwNDI0NDI0MTA2OV0sIFsxMjcuMDczNTEyNDM4MjUyNzgsIDM3LjYxMjgzNjYwMzQyMzEzXSwgWzEyNy4wNTIwOTM3MzU2ODYxOSwgMzcuNjIxNjQwNjU0ODc3ODJdLCBbMTI3LjA0MzU4ODAwODk1NjA5LCAzNy42Mjg0ODkzMTI5ODcxNV0sIFsxMjcuMDU4MDAwNzUyMjAwOTEsIDM3LjY0MzE4MjYzODc4Mjc2XSwgWzEyNy4wNTI4ODQ3OTcxMDQ4NSwgMzcuNjg0MjM4NTcwODQzNDddLCBbMTI3LjA4Mzg3NTI3MDMxOTUsIDM3LjY5MzU5NTM0MjAyMDM0XV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJcdWIxNzhcdWM2ZDBcdWFkNmMiLCAicHJvcGVydGllcyI6IHsiYmFzZV95ZWFyIjogIjIwMTMiLCAiY29kZSI6ICIxMTExMCIsICJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiXHViMTc4XHVjNmQwXHVhZDZjIiwgIm5hbWVfZW5nIjogIk5vd29uLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2M5OTRjNyIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI3LjA1Mjg4NDc5NzEwNDg1LCAzNy42ODQyMzg1NzA4NDM0N10sIFsxMjcuMDU4MDAwNzUyMjAwOTEsIDM3LjY0MzE4MjYzODc4Mjc2XSwgWzEyNy4wNDM1ODgwMDg5NTYwOSwgMzcuNjI4NDg5MzEyOTg3MTVdLCBbMTI3LjAxNDY1OTM1ODkyNDY2LCAzNy42NDk0MzY4NzQ5NjgxMl0sIFsxMjcuMDIwNjIxMTYxNDEzODksIDM3LjY2NzE3MzU3NTk3MTIwNV0sIFsxMjcuMDEwMzk2NjYwNDIwNzEsIDM3LjY4MTg5NDU4OTYwMzU5NF0sIFsxMjcuMDE3OTUwOTkyMDM0MzIsIDM3LjY5ODI0NDEyNzc1NjYyXSwgWzEyNy4wNTI4ODQ3OTcxMDQ4NSwgMzcuNjg0MjM4NTcwODQzNDddXV0sICJ0eXBlIjogIlBvbHlnb24ifSwgImlkIjogIlx1YjNjNFx1YmQwOVx1YWQ2YyIsICJwcm9wZXJ0aWVzIjogeyJiYXNlX3llYXIiOiAiMjAxMyIsICJjb2RlIjogIjExMTAwIiwgImhpZ2hsaWdodCI6IHt9LCAibmFtZSI6ICJcdWIzYzRcdWJkMDlcdWFkNmMiLCAibmFtZV9lbmciOiAiRG9ib25nLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2YxZWVmNiIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2Ljk5MzgzOTAzNDI0LCAzNy42NzY2ODE3NjExOTkwODVdLCBbMTI3LjAxMDM5NjY2MDQyMDcxLCAzNy42ODE4OTQ1ODk2MDM1OTRdLCBbMTI3LjAyMDYyMTE2MTQxMzg5LCAzNy42NjcxNzM1NzU5NzEyMDVdLCBbMTI3LjAxNDY1OTM1ODkyNDY2LCAzNy42NDk0MzY4NzQ5NjgxMl0sIFsxMjcuMDQzNTg4MDA4OTU2MDksIDM3LjYyODQ4OTMxMjk4NzE1XSwgWzEyNy4wNTIwOTM3MzU2ODYxOSwgMzcuNjIxNjQwNjU0ODc3ODJdLCBbMTI3LjAzODkyNDAwOTkyMzAxLCAzNy42MDk3MTU2MTEwMjM4MTZdLCBbMTI3LjAxMjgxNTQ3NDk1MjMsIDM3LjYxMzY1MjI0MzQ3MDI1Nl0sIFsxMjYuOTg2NzI3MDU1MTM4NjksIDM3LjYzMzc3NjQxMjg4MTk2XSwgWzEyNi45ODE3NDUyNjc2NTUxLCAzNy42NTIwOTc2OTM4Nzc3Nl0sIFsxMjYuOTkzODM5MDM0MjQsIDM3LjY3NjY4MTc2MTE5OTA4NV1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVhYzE1XHViZDgxXHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEwOTAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YWMxNVx1YmQ4MVx1YWQ2YyIsICJuYW1lX2VuZyI6ICJHYW5nYnVrLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2Q0YjlkYSIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2Ljk3NzE3NTQwNjQxNiwgMzcuNjI4NTk3MTU0MDAzODhdLCBbMTI2Ljk4NjcyNzA1NTEzODY5LCAzNy42MzM3NzY0MTI4ODE5Nl0sIFsxMjcuMDEyODE1NDc0OTUyMywgMzcuNjEzNjUyMjQzNDcwMjU2XSwgWzEyNy4wMzg5MjQwMDk5MjMwMSwgMzcuNjA5NzE1NjExMDIzODE2XSwgWzEyNy4wNTIwOTM3MzU2ODYxOSwgMzcuNjIxNjQwNjU0ODc3ODJdLCBbMTI3LjA3MzUxMjQzODI1Mjc4LCAzNy42MTI4MzY2MDM0MjMxM10sIFsxMjcuMDczODI3MDcwOTkyMjcsIDM3LjYwNDAxOTI4OTg2NDE5XSwgWzEyNy4wNDI3MDUyMjIwOTQsIDM3LjU5MjM5NDM3NTkzMzkxXSwgWzEyNy4wMjUyNzI1NDUyODAwMywgMzcuNTc1MjQ2MTYyNDUyNDldLCBbMTI2Ljk5MzQ4MjkzMzU4MzE0LCAzNy41ODg1NjU0NTcyMTYxNTZdLCBbMTI2Ljk4ODc5ODY1OTkyMzg0LCAzNy42MTE4OTI3MzE5NzU2XSwgWzEyNi45NzcxNzU0MDY0MTYsIDM3LjYyODU5NzE1NDAwMzg4XV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJcdWMxMzFcdWJkODFcdWFkNmMiLCAicHJvcGVydGllcyI6IHsiYmFzZV95ZWFyIjogIjIwMTMiLCAiY29kZSI6ICIxMTA4MCIsICJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiXHVjMTMxXHViZDgxXHVhZDZjIiwgIm5hbWVfZW5nIjogIlNlb25nYnVrLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2YxZWVmNiIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI3LjA3MzUxMjQzODI1Mjc4LCAzNy42MTI4MzY2MDM0MjMxM10sIFsxMjcuMTA3ODIyNzc2ODgxMjksIDM3LjYxODA0MjQ0MjQxMDY5XSwgWzEyNy4xMjAxMjQ2MDIwMTE0LCAzNy42MDE3ODQ1NzU5ODE4OF0sIFsxMjcuMTAzMDQxNzQyNDkyMTQsIDM3LjU3MDc2MzQyMjkwOTU1XSwgWzEyNy4wODA2ODU0MTI4MDQwMywgMzcuNTY5MDY0MjU1MTkwMTddLCBbMTI3LjA3MzgyNzA3MDk5MjI3LCAzNy42MDQwMTkyODk4NjQxOV0sIFsxMjcuMDczNTEyNDM4MjUyNzgsIDM3LjYxMjgzNjYwMzQyMzEzXV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJcdWM5MTFcdWI3OTFcdWFkNmMiLCAicHJvcGVydGllcyI6IHsiYmFzZV95ZWFyIjogIjIwMTMiLCAiY29kZSI6ICIxMTA3MCIsICJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiXHVjOTExXHViNzkxXHVhZDZjIiwgIm5hbWVfZW5nIjogIkp1bmduYW5nLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2M5OTRjNyIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI3LjAyNTI3MjU0NTI4MDAzLCAzNy41NzUyNDYxNjI0NTI0OV0sIFsxMjcuMDQyNzA1MjIyMDk0LCAzNy41OTIzOTQzNzU5MzM5MV0sIFsxMjcuMDczODI3MDcwOTkyMjcsIDM3LjYwNDAxOTI4OTg2NDE5XSwgWzEyNy4wODA2ODU0MTI4MDQwMywgMzcuNTY5MDY0MjU1MTkwMTddLCBbMTI3LjA3NDIxMDUzMDI0MzYyLCAzNy41NTcyNDc2OTcxMjA4NV0sIFsxMjcuMDUwMDU2MDEwODE1NjcsIDM3LjU2NzU3NzYxMjU5MDg0Nl0sIFsxMjcuMDI1NDcyNjYzNDk5NzYsIDM3LjU2ODk0MzU1MjIzNzczNF0sIFsxMjcuMDI1MjcyNTQ1MjgwMDMsIDM3LjU3NTI0NjE2MjQ1MjQ5XV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJcdWIzZDlcdWIzMDBcdWJiMzhcdWFkNmMiLCAicHJvcGVydGllcyI6IHsiYmFzZV95ZWFyIjogIjIwMTMiLCAiY29kZSI6ICIxMTA2MCIsICJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiXHViM2Q5XHViMzAwXHViYjM4XHVhZDZjIiwgIm5hbWVfZW5nIjogIkRvbmdkYWVtdW4tZ3UiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICIjZDRiOWRhIiwgImZpbGxPcGFjaXR5IjogMC42LCAib3BhY2l0eSI6IDEsICJ3ZWlnaHQiOiAxfX0sICJ0eXBlIjogIkZlYXR1cmUifSwgeyJnZW9tZXRyeSI6IHsiY29vcmRpbmF0ZXMiOiBbW1sxMjcuMDgwNjg1NDEyODA0MDMsIDM3LjU2OTA2NDI1NTE5MDE3XSwgWzEyNy4xMDMwNDE3NDI0OTIxNCwgMzcuNTcwNzYzNDIyOTA5NTVdLCBbMTI3LjExNTE5NTg0OTgxNjA2LCAzNy41NTc1MzMxODA3MDQ5MTVdLCBbMTI3LjExMTY3NjQyMDM2MDgsIDM3LjU0MDY2OTk1NTMyNDk2NV0sIFsxMjcuMTAwODc1MTk3OTE5NjIsIDM3LjUyNDg0MTIyMDE2NzA1NV0sIFsxMjcuMDY5MDY5ODEzMDM3MiwgMzcuNTIyMjc5NDIzNTA1MDI2XSwgWzEyNy4wNTg2NzM1OTI4ODM5OCwgMzcuNTI2Mjk5NzQ5MjI1NjhdLCBbMTI3LjA3NDIxMDUzMDI0MzYyLCAzNy41NTcyNDc2OTcxMjA4NV0sIFsxMjcuMDgwNjg1NDEyODA0MDMsIDM3LjU2OTA2NDI1NTE5MDE3XV1dLCAidHlwZSI6ICJQb2x5Z29uIn0sICJpZCI6ICJcdWFkMTFcdWM5YzRcdWFkNmMiLCAicHJvcGVydGllcyI6IHsiYmFzZV95ZWFyIjogIjIwMTMiLCAiY29kZSI6ICIxMTA1MCIsICJoaWdobGlnaHQiOiB7fSwgIm5hbWUiOiAiXHVhZDExXHVjOWM0XHVhZDZjIiwgIm5hbWVfZW5nIjogIkd3YW5namluLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2M5OTRjNyIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI3LjAyNTQ3MjY2MzQ5OTc2LCAzNy41Njg5NDM1NTIyMzc3MzRdLCBbMTI3LjA1MDA1NjAxMDgxNTY3LCAzNy41Njc1Nzc2MTI1OTA4NDZdLCBbMTI3LjA3NDIxMDUzMDI0MzYyLCAzNy41NTcyNDc2OTcxMjA4NV0sIFsxMjcuMDU4NjczNTkyODgzOTgsIDM3LjUyNjI5OTc0OTIyNTY4XSwgWzEyNy4wMjMwMjgzMTg5MDU1OSwgMzcuNTMyMzE4OTk1ODI2NjNdLCBbMTI3LjAxMDcwODk0MTc3NDgyLCAzNy41NDExODA0ODk2NDc2Ml0sIFsxMjcuMDI1NDcyNjYzNDk5NzYsIDM3LjU2ODk0MzU1MjIzNzczNF1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVjMTMxXHViM2Q5XHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEwNDAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YzEzMVx1YjNkOVx1YWQ2YyIsICJuYW1lX2VuZyI6ICJTZW9uZ2RvbmctZ3UiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICIjZjFlZWY2IiwgImZpbGxPcGFjaXR5IjogMC42LCAib3BhY2l0eSI6IDEsICJ3ZWlnaHQiOiAxfX0sICJ0eXBlIjogIkZlYXR1cmUifSwgeyJnZW9tZXRyeSI6IHsiY29vcmRpbmF0ZXMiOiBbW1sxMjcuMDEwNzA4OTQxNzc0ODIsIDM3LjU0MTE4MDQ4OTY0NzYyXSwgWzEyNy4wMjMwMjgzMTg5MDU1OSwgMzcuNTMyMzE4OTk1ODI2NjNdLCBbMTI3LjAxMzk3MTE5NjY3NTEzLCAzNy41MjUwMzk4ODI4OTY2OV0sIFsxMjYuOTgyMjM4MDc5MTYwODEsIDM3LjUwOTMxNDk2Njc3MDMyNl0sIFsxMjYuOTUyNDk5OTAyOTgxNTksIDM3LjUxNzIyNTAwNzQxODEzXSwgWzEyNi45NDU2NjczMzA4MzIxMiwgMzcuNTI2NjE3NTQyNDUzMzY2XSwgWzEyNi45NjQ0ODU3MDU1MzA1NSwgMzcuNTQ4NzA1NjkyMDIxNjM1XSwgWzEyNi45ODc1Mjk5NjkwMzMyOCwgMzcuNTUwOTQ4MTg4MDcxMzldLCBbMTI3LjAxMDcwODk0MTc3NDgyLCAzNy41NDExODA0ODk2NDc2Ml1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVjNmE5XHVjMGIwXHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEwMzAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YzZhOVx1YzBiMFx1YWQ2YyIsICJuYW1lX2VuZyI6ICJZb25nc2FuLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2Q0YjlkYSIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI3LjAyNTQ3MjY2MzQ5OTc2LCAzNy41Njg5NDM1NTIyMzc3MzRdLCBbMTI3LjAxMDcwODk0MTc3NDgyLCAzNy41NDExODA0ODk2NDc2Ml0sIFsxMjYuOTg3NTI5OTY5MDMzMjgsIDM3LjU1MDk0ODE4ODA3MTM5XSwgWzEyNi45NjQ0ODU3MDU1MzA1NSwgMzcuNTQ4NzA1NjkyMDIxNjM1XSwgWzEyNi45NjM1ODIyNjcxMDgxMiwgMzcuNTU2MDU2MzU0NzUxNTRdLCBbMTI2Ljk2ODczNjMzMjc5MDc1LCAzNy41NjMxMzYwNDY5MDgyN10sIFsxMjcuMDI1NDcyNjYzNDk5NzYsIDM3LjU2ODk0MzU1MjIzNzczNF1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVjOTExXHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEwMjAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1YzkxMVx1YWQ2YyIsICJuYW1lX2VuZyI6ICJKdW5nLWd1IiwgInN0eWxlIjogeyJjb2xvciI6ICJibGFjayIsICJmaWxsQ29sb3IiOiAiI2Q0YjlkYSIsICJmaWxsT3BhY2l0eSI6IDAuNiwgIm9wYWNpdHkiOiAxLCAid2VpZ2h0IjogMX19LCAidHlwZSI6ICJGZWF0dXJlIn0sIHsiZ2VvbWV0cnkiOiB7ImNvb3JkaW5hdGVzIjogW1tbMTI2Ljk3Mzg4NjQxMjg3MDIsIDM3LjYyOTQ5NjM0Nzg2ODg4XSwgWzEyNi45NzcxNzU0MDY0MTYsIDM3LjYyODU5NzE1NDAwMzg4XSwgWzEyNi45ODg3OTg2NTk5MjM4NCwgMzcuNjExODkyNzMxOTc1Nl0sIFsxMjYuOTkzNDgyOTMzNTgzMTQsIDM3LjU4ODU2NTQ1NzIxNjE1Nl0sIFsxMjcuMDI1MjcyNTQ1MjgwMDMsIDM3LjU3NTI0NjE2MjQ1MjQ5XSwgWzEyNy4wMjU0NzI2NjM0OTk3NiwgMzcuNTY4OTQzNTUyMjM3NzM0XSwgWzEyNi45Njg3MzYzMzI3OTA3NSwgMzcuNTYzMTM2MDQ2OTA4MjddLCBbMTI2Ljk1NTY1NDI1ODQ2NDYzLCAzNy41NzYwODA3OTA4ODE0NTZdLCBbMTI2Ljk1MjQ3NTIwMzA1NzIsIDM3LjYwNTA4NjkyNzM3MDQ1XSwgWzEyNi45NTQyNzAxNzAwNjEyOSwgMzcuNjIyMDMzNDMxMzM5NDI1XSwgWzEyNi45NzM4ODY0MTI4NzAyLCAzNy42Mjk0OTYzNDc4Njg4OF1dXSwgInR5cGUiOiAiUG9seWdvbiJ9LCAiaWQiOiAiXHVjODg1XHViODVjXHVhZDZjIiwgInByb3BlcnRpZXMiOiB7ImJhc2VfeWVhciI6ICIyMDEzIiwgImNvZGUiOiAiMTEwMTAiLCAiaGlnaGxpZ2h0Ijoge30sICJuYW1lIjogIlx1Yzg4NVx1Yjg1Y1x1YWQ2YyIsICJuYW1lX2VuZyI6ICJKb25nbm8tZ3UiLCAic3R5bGUiOiB7ImNvbG9yIjogImJsYWNrIiwgImZpbGxDb2xvciI6ICIjZDRiOWRhIiwgImZpbGxPcGFjaXR5IjogMC42LCAib3BhY2l0eSI6IDEsICJ3ZWlnaHQiOiAxfX0sICJ0eXBlIjogIkZlYXR1cmUifV0sICJ0eXBlIjogIkZlYXR1cmVDb2xsZWN0aW9uIn0KICAgICAgICAgICAgCiAgICAgICAgICAgICkuYWRkVG8oY2hvcm9wbGV0aF83YWEyNzRiOTRkYjY0ODg0OTJkODRjMjI5ZGNlZTVmYyk7CiAgICAgICAgZ2VvX2pzb25fNmYyY2E0YWU5OWMwNDczOThhMjE4NDg4NjNiMzJiNzIuc2V0U3R5bGUoZnVuY3Rpb24oZmVhdHVyZSkge3JldHVybiBmZWF0dXJlLnByb3BlcnRpZXMuc3R5bGU7fSk7CiAgICAgICAgCiAgICAKICAgIHZhciBjb2xvcl9tYXBfMGU0YTViYzdjYjU2NGM2NDk2MDBkYzM4Zjc2MDY0NDEgPSB7fTsKCiAgICAKICAgIGNvbG9yX21hcF8wZTRhNWJjN2NiNTY0YzY0OTYwMGRjMzhmNzYwNjQ0MS5jb2xvciA9IGQzLnNjYWxlLnRocmVzaG9sZCgpCiAgICAgICAgICAgICAgLmRvbWFpbihbMC4wNjM3MjU0OTAxOTYwNzg0MywgMC4wNjUzOTA0NDk2NDE1NDgzLCAwLjA2NzA1NTQwOTA4NzAxODE3LCAwLjA2ODcyMDM2ODUzMjQ4ODA0LCAwLjA3MDM4NTMyNzk3Nzk1NzksIDAuMDcyMDUwMjg3NDIzNDI3NzgsIDAuMDczNzE1MjQ2ODY4ODk3NjYsIDAuMDc1MzgwMjA2MzE0MzY3NTIsIDAuMDc3MDQ1MTY1NzU5ODM3NCwgMC4wNzg3MTAxMjUyMDUzMDcyNiwgMC4wODAzNzUwODQ2NTA3NzcxNCwgMC4wODIwNDAwNDQwOTYyNDcwMiwgMC4wODM3MDUwMDM1NDE3MTY4OCwgMC4wODUzNjk5NjI5ODcxODY3NiwgMC4wODcwMzQ5MjI0MzI2NTY2MywgMC4wODg2OTk4ODE4NzgxMjY1LCAwLjA5MDM2NDg0MTMyMzU5NjM3LCAwLjA5MjAyOTgwMDc2OTA2NjI1LCAwLjA5MzY5NDc2MDIxNDUzNjExLCAwLjA5NTM1OTcxOTY2MDAwNTk5LCAwLjA5NzAyNDY3OTEwNTQ3NTg1LCAwLjA5ODY4OTYzODU1MDk0NTczLCAwLjEwMDM1NDU5Nzk5NjQxNTYsIDAuMTAyMDE5NTU3NDQxODg1NDgsIDAuMTAzNjg0NTE2ODg3MzU1MzMsIDAuMTA1MzQ5NDc2MzMyODI1MjEsIDAuMTA3MDE0NDM1Nzc4Mjk1MDksIDAuMTA4Njc5Mzk1MjIzNzY0OTYsIDAuMTEwMzQ0MzU0NjY5MjM0ODQsIDAuMTEyMDA5MzE0MTE0NzA0NywgMC4xMTM2NzQyNzM1NjAxNzQ1NywgMC4xMTUzMzkyMzMwMDU2NDQ0NCwgMC4xMTcwMDQxOTI0NTExMTQzMiwgMC4xMTg2NjkxNTE4OTY1ODQyLCAwLjEyMDMzNDExMTM0MjA1NDA2LCAwLjEyMTk5OTA3MDc4NzUyMzk0LCAwLjEyMzY2NDAzMDIzMjk5MzgsIDAuMTI1MzI4OTg5Njc4NDYzNjgsIDAuMTI2OTkzOTQ5MTIzOTMzNTUsIDAuMTI4NjU4OTA4NTY5NDAzNDMsIDAuMTMwMzIzODY4MDE0ODczMjgsIDAuMTMxOTg4ODI3NDYwMzQzMTgsIDAuMTMzNjUzNzg2OTA1ODEzMDMsIDAuMTM1MzE4NzQ2MzUxMjgyODgsIDAuMTM2OTgzNzA1Nzk2NzUyNzksIDAuMTM4NjQ4NjY1MjQyMjIyNjQsIDAuMTQwMzEzNjI0Njg3NjkyNTQsIDAuMTQxOTc4NTg0MTMzMTYyNCwgMC4xNDM2NDM1NDM1Nzg2MzIyNCwgMC4xNDUzMDg1MDMwMjQxMDIxNCwgMC4xNDY5NzM0NjI0Njk1NzIsIDAuMTQ4NjM4NDIxOTE1MDQxOSwgMC4xNTAzMDMzODEzNjA1MTE3NSwgMC4xNTE5NjgzNDA4MDU5ODE2MiwgMC4xNTM2MzMzMDAyNTE0NTE1LCAwLjE1NTI5ODI1OTY5NjkyMTM1LCAwLjE1Njk2MzIxOTE0MjM5MTI1LCAwLjE1ODYyODE3ODU4Nzg2MTEsIDAuMTYwMjkzMTM4MDMzMzMwOTgsIDAuMTYxOTU4MDk3NDc4ODAwODYsIDAuMTYzNjIzMDU2OTI0MjcwNywgMC4xNjUyODgwMTYzNjk3NDA2LCAwLjE2Njk1Mjk3NTgxNTIxMDQ2LCAwLjE2ODYxNzkzNTI2MDY4MDMzLCAwLjE3MDI4Mjg5NDcwNjE1MDIsIDAuMTcxOTQ3ODU0MTUxNjIwMDYsIDAuMTczNjEyODEzNTk3MDg5OTcsIDAuMTc1Mjc3NzczMDQyNTU5ODEsIDAuMTc2OTQyNzMyNDg4MDI5NywgMC4xNzg2MDc2OTE5MzM0OTk1NywgMC4xODAyNzI2NTEzNzg5Njk0NSwgMC4xODE5Mzc2MTA4MjQ0MzkzMiwgMC4xODM2MDI1NzAyNjk5MDkxNywgMC4xODUyNjc1Mjk3MTUzNzkwNSwgMC4xODY5MzI0ODkxNjA4NDg5MiwgMC4xODg1OTc0NDg2MDYzMTg4LCAwLjE5MDI2MjQwODA1MTc4ODY4LCAwLjE5MTkyNzM2NzQ5NzI1ODUzLCAwLjE5MzU5MjMyNjk0MjcyODQsIDAuMTk1MjU3Mjg2Mzg4MTk4MjgsIDAuMTk2OTIyMjQ1ODMzNjY4MTYsIDAuMTk4NTg3MjA1Mjc5MTM4MDMsIDAuMjAwMjUyMTY0NzI0NjA3OSwgMC4yMDE5MTcxMjQxNzAwNzc4LCAwLjIwMzU4MjA4MzYxNTU0NzY0LCAwLjIwNTI0NzA0MzA2MTAxNzUsIDAuMjA2OTEyMDAyNTA2NDg3MzYsIDAuMjA4NTc2OTYxOTUxOTU3MjQsIDAuMjEwMjQxOTIxMzk3NDI3MTIsIDAuMjExOTA2ODgwODQyODk3LCAwLjIxMzU3MTg0MDI4ODM2Njg3LCAwLjIxNTIzNjc5OTczMzgzNjc1LCAwLjIxNjkwMTc1OTE3OTMwNjYyLCAwLjIxODU2NjcxODYyNDc3NjUsIDAuMjIwMjMxNjc4MDcwMjQ2MzUsIDAuMjIxODk2NjM3NTE1NzE2MjMsIDAuMjIzNTYxNTk2OTYxMTg2MDgsIDAuMjI1MjI2NTU2NDA2NjU1OTUsIDAuMjI2ODkxNTE1ODUyMTI1ODMsIDAuMjI4NTU2NDc1Mjk3NTk1NywgMC4yMzAyMjE0MzQ3NDMwNjU1OCwgMC4yMzE4ODYzOTQxODg1MzU0NiwgMC4yMzM1NTEzNTM2MzQwMDUzNCwgMC4yMzUyMTYzMTMwNzk0NzUyMSwgMC4yMzY4ODEyNzI1MjQ5NDUwNiwgMC4yMzg1NDYyMzE5NzA0MTQ5NCwgMC4yNDAyMTExOTE0MTU4ODQ4MiwgMC4yNDE4NzYxNTA4NjEzNTQ2NywgMC4yNDM1NDExMTAzMDY4MjQ1NCwgMC4yNDUyMDYwNjk3NTIyOTQ0MiwgMC4yNDY4NzEwMjkxOTc3NjQzLCAwLjI0ODUzNTk4ODY0MzIzNDE3LCAwLjI1MDIwMDk0ODA4ODcwNDEsIDAuMjUxODY1OTA3NTM0MTczOSwgMC4yNTM1MzA4NjY5Nzk2NDM4LCAwLjI1NTE5NTgyNjQyNTExMzYsIDAuMjU2ODYwNzg1ODcwNTgzNTMsIDAuMjU4NTI1NzQ1MzE2MDUzNDMsIDAuMjYwMTkwNzA0NzYxNTIzMywgMC4yNjE4NTU2NjQyMDY5OTMxMywgMC4yNjM1MjA2MjM2NTI0NjMsIDAuMjY1MTg1NTgzMDk3OTMyOSwgMC4yNjY4NTA1NDI1NDM0MDI4LCAwLjI2ODUxNTUwMTk4ODg3MjY0LCAwLjI3MDE4MDQ2MTQzNDM0MjUsIDAuMjcxODQ1NDIwODc5ODEyMzQsIDAuMjczNTEwMzgwMzI1MjgyMjQsIDAuMjc1MTc1MzM5NzcwNzUyMTUsIDAuMjc2ODQwMjk5MjE2MjIyLCAwLjI3ODUwNTI1ODY2MTY5MTg1LCAwLjI4MDE3MDIxODEwNzE2MTcsIDAuMjgxODM1MTc3NTUyNjMxNiwgMC4yODM1MDAxMzY5OTgxMDE1LCAwLjI4NTE2NTA5NjQ0MzU3MTM1LCAwLjI4NjgzMDA1NTg4OTA0MTIsIDAuMjg4NDk1MDE1MzM0NTExMDUsIDAuMjkwMTU5OTc0Nzc5OTgwOTYsIDAuMjkxODI0OTM0MjI1NDUwODYsIDAuMjkzNDg5ODkzNjcwOTIwNywgMC4yOTUxNTQ4NTMxMTYzOTA1NiwgMC4yOTY4MTk4MTI1NjE4NjA0NiwgMC4yOTg0ODQ3NzIwMDczMzAzLCAwLjMwMDE0OTczMTQ1MjgwMDIsIDAuMzAxODE0NjkwODk4MjcwMDcsIDAuMzAzNDc5NjUwMzQzNzM5OSwgMC4zMDUxNDQ2MDk3ODkyMDk3NiwgMC4zMDY4MDk1NjkyMzQ2Nzk2NywgMC4zMDg0NzQ1Mjg2ODAxNDk2LCAwLjMxMDEzOTQ4ODEyNTYxOTQsIDAuMzExODA0NDQ3NTcxMDg5MjcsIDAuMzEzNDY5NDA3MDE2NTU5MiwgMC4zMTUxMzQzNjY0NjIwMjksIDAuMzE2Nzk5MzI1OTA3NDk4OTMsIDAuMzE4NDY0Mjg1MzUyOTY4OCwgMC4zMjAxMjkyNDQ3OTg0Mzg2MywgMC4zMjE3OTQyMDQyNDM5MDg1MywgMC4zMjM0NTkxNjM2ODkzNzg0LCAwLjMyNTEyNDEyMzEzNDg0ODMsIDAuMzI2Nzg5MDgyNTgwMzE4MTQsIDAuMzI4NDU0MDQyMDI1Nzg4LCAwLjMzMDExOTAwMTQ3MTI1NzksIDAuMzMxNzgzOTYwOTE2NzI3NzQsIDAuMzMzNDQ4OTIwMzYyMTk3NjQsIDAuMzM1MTEzODc5ODA3NjY3NSwgMC4zMzY3Nzg4MzkyNTMxMzc0LCAwLjMzODQ0Mzc5ODY5ODYwNzI1LCAwLjM0MDEwODc1ODE0NDA3NzE1LCAwLjM0MTc3MzcxNzU4OTU0NywgMC4zNDM0Mzg2NzcwMzUwMTY4NSwgMC4zNDUxMDM2MzY0ODA0ODY3LCAwLjM0Njc2ODU5NTkyNTk1NjU1LCAwLjM0ODQzMzU1NTM3MTQyNjQ1LCAwLjM1MDA5ODUxNDgxNjg5NjMsIDAuMzUxNzYzNDc0MjYyMzY2MiwgMC4zNTM0Mjg0MzM3MDc4MzYwNSwgMC4zNTUwOTMzOTMxNTMzMDU5NiwgMC4zNTY3NTgzNTI1OTg3NzU4LCAwLjM1ODQyMzMxMjA0NDI0NTcsIDAuMzYwMDg4MjcxNDg5NzE1NTYsIDAuMzYxNzUzMjMwOTM1MTg1NCwgMC4zNjM0MTgxOTAzODA2NTUzLCAwLjM2NTA4MzE0OTgyNjEyNTE2LCAwLjM2Njc0ODEwOTI3MTU5NTA3LCAwLjM2ODQxMzA2ODcxNzA2NDksIDAuMzcwMDc4MDI4MTYyNTM0OCwgMC4zNzE3NDI5ODc2MDgwMDQ2NywgMC4zNzM0MDc5NDcwNTM0NzQ2LCAwLjM3NTA3MjkwNjQ5ODk0NDQsIDAuMzc2NzM3ODY1OTQ0NDE0MywgMC4zNzg0MDI4MjUzODk4ODQyLCAwLjM4MDA2Nzc4NDgzNTM1NDAzLCAwLjM4MTczMjc0NDI4MDgyMzkzLCAwLjM4MzM5NzcwMzcyNjI5MzcsIDAuMzg1MDYyNjYzMTcxNzYzNjMsIDAuMzg2NzI3NjIyNjE3MjMzNSwgMC4zODgzOTI1ODIwNjI3MDM0LCAwLjM5MDA1NzU0MTUwODE3MzIzLCAwLjM5MTcyMjUwMDk1MzY0MzEsIDAuMzkzMzg3NDYwMzk5MTEzLCAwLjM5NTA1MjQxOTg0NDU4Mjg0LCAwLjM5NjcxNzM3OTI5MDA1Mjc0LCAwLjM5ODM4MjMzODczNTUyMjYsIDAuNDAwMDQ3Mjk4MTgwOTkyNSwgMC40MDE3MTIyNTc2MjY0NjIzNCwgMC40MDMzNzcyMTcwNzE5MzIyNSwgMC40MDUwNDIxNzY1MTc0MDIxLCAwLjQwNjcwNzEzNTk2Mjg3MiwgMC40MDgzNzIwOTU0MDgzNDE4NSwgMC40MTAwMzcwNTQ4NTM4MTE3LCAwLjQxMTcwMjAxNDI5OTI4MTYsIDAuNDEzMzY2OTczNzQ0NzUxNDUsIDAuNDE1MDMxOTMzMTkwMjIxMzYsIDAuNDE2Njk2ODkyNjM1NjkxMiwgMC40MTgzNjE4NTIwODExNjExLCAwLjQyMDAyNjgxMTUyNjYzMDksIDAuNDIxNjkxNzcwOTcyMTAwOCwgMC40MjMzNTY3MzA0MTc1NzA2NiwgMC40MjUwMjE2ODk4NjMwNDA1LCAwLjQyNjY4NjY0OTMwODUxMDQsIDAuNDI4MzUxNjA4NzUzOTgwMjYsIDAuNDMwMDE2NTY4MTk5NDUwMTcsIDAuNDMxNjgxNTI3NjQ0OTIsIDAuNDMzMzQ2NDg3MDkwMzg5OSwgMC40MzUwMTE0NDY1MzU4NTk3NywgMC40MzY2NzY0MDU5ODEzMjk3LCAwLjQzODM0MTM2NTQyNjc5OTUsIDAuNDQwMDA2MzI0ODcyMjY5NCwgMC40NDE2NzEyODQzMTc3MzkzLCAwLjQ0MzMzNjI0Mzc2MzIwOTEsIDAuNDQ1MDAxMjAzMjA4Njc5MDMsIDAuNDQ2NjY2MTYyNjU0MTQ4OSwgMC40NDgzMzExMjIwOTk2MTg4LCAwLjQ0OTk5NjA4MTU0NTA4ODYzLCAwLjQ1MTY2MTA0MDk5MDU1ODU0LCAwLjQ1MzMyNjAwMDQzNjAyODQsIDAuNDU0OTkwOTU5ODgxNDk4MywgMC40NTY2NTU5MTkzMjY5NjgxNCwgMC40NTgzMjA4Nzg3NzI0Mzc5MywgMC40NTk5ODU4MzgyMTc5MDc4NCwgMC40NjE2NTA3OTc2NjMzNzc3LCAwLjQ2MzMxNTc1NzEwODg0NzYsIDAuNDY0OTgwNzE2NTU0MzE3NDQsIDAuNDY2NjQ1Njc1OTk5Nzg3MzUsIDAuNDY4MzEwNjM1NDQ1MjU3MiwgMC40Njk5NzU1OTQ4OTA3MjcxLCAwLjQ3MTY0MDU1NDMzNjE5Njk1LCAwLjQ3MzMwNTUxMzc4MTY2Njg1LCAwLjQ3NDk3MDQ3MzIyNzEzNjcsIDAuNDc2NjM1NDMyNjcyNjA2NTUsIDAuNDc4MzAwMzkyMTE4MDc2NDYsIDAuNDc5OTY1MzUxNTYzNTQ2MywgMC40ODE2MzAzMTEwMDkwMTYyLCAwLjQ4MzI5NTI3MDQ1NDQ4NjA2LCAwLjQ4NDk2MDIyOTg5OTk1NTk2LCAwLjQ4NjYyNTE4OTM0NTQyNTgsIDAuNDg4MjkwMTQ4NzkwODk1NywgMC40ODk5NTUxMDgyMzYzNjU1NywgMC40OTE2MjAwNjc2ODE4MzU0LCAwLjQ5MzI4NTAyNzEyNzMwNTMsIDAuNDk0OTQ5OTg2NTcyNzc1MSwgMC40OTY2MTQ5NDYwMTgyNDUsIDAuNDk4Mjc5OTA1NDYzNzE0ODcsIDAuNDk5OTQ0ODY0OTA5MTg0NzcsIDAuNTAxNjA5ODI0MzU0NjU0NiwgMC41MDMyNzQ3ODM4MDAxMjQ2LCAwLjUwNDkzOTc0MzI0NTU5NDQsIDAuNTA2NjA0NzAyNjkxMDY0MywgMC41MDgyNjk2NjIxMzY1MzQxLCAwLjUwOTkzNDYyMTU4MjAwNCwgMC41MTE1OTk1ODEwMjc0NzM4LCAwLjUxMzI2NDU0MDQ3Mjk0MzcsIDAuNTE0OTI5NDk5OTE4NDEzNiwgMC41MTY1OTQ0NTkzNjM4ODM1LCAwLjUxODI1OTQxODgwOTM1MzQsIDAuNTE5OTI0Mzc4MjU0ODIzMywgMC41MjE1ODkzMzc3MDAyOTMxLCAwLjUyMzI1NDI5NzE0NTc2MywgMC41MjQ5MTkyNTY1OTEyMzI4LCAwLjUyNjU4NDIxNjAzNjcwMjcsIDAuNTI4MjQ5MTc1NDgyMTcyNSwgMC41Mjk5MTQxMzQ5Mjc2NDI1LCAwLjUzMTU3OTA5NDM3MzExMjMsIDAuNTMzMjQ0MDUzODE4NTgyMiwgMC41MzQ5MDkwMTMyNjQwNTIsIDAuNTM2NTczOTcyNzA5NTIyLCAwLjUzODIzODkzMjE1NDk5MTksIDAuNTM5OTAzODkxNjAwNDYxNywgMC41NDE1Njg4NTEwNDU5MzE2LCAwLjU0MzIzMzgxMDQ5MTQwMTQsIDAuNTQ0ODk4NzY5OTM2ODcxMywgMC41NDY1NjM3MjkzODIzNDExLCAwLjU0ODIyODY4ODgyNzgxMTEsIDAuNTQ5ODkzNjQ4MjczMjgwOSwgMC41NTE1NTg2MDc3MTg3NTA5LCAwLjU1MzIyMzU2NzE2NDIyMDcsIDAuNTU0ODg4NTI2NjA5NjkwNiwgMC41NTY1NTM0ODYwNTUxNjA0LCAwLjU1ODIxODQ0NTUwMDYzMDMsIDAuNTU5ODgzNDA0OTQ2MTAwMSwgMC41NjE1NDgzNjQzOTE1NywgMC41NjMyMTMzMjM4MzcwMzk5LCAwLjU2NDg3ODI4MzI4MjUwOTgsIDAuNTY2NTQzMjQyNzI3OTc5NiwgMC41NjgyMDgyMDIxNzM0NDk1LCAwLjU2OTg3MzE2MTYxODkxOTQsIDAuNTcxNTM4MTIxMDY0Mzg5MywgMC41NzMyMDMwODA1MDk4NTkxLCAwLjU3NDg2ODAzOTk1NTMyOSwgMC41NzY1MzI5OTk0MDA3OTg4LCAwLjU3ODE5Nzk1ODg0NjI2ODgsIDAuNTc5ODYyOTE4MjkxNzM4NiwgMC41ODE1Mjc4Nzc3MzcyMDg1LCAwLjU4MzE5MjgzNzE4MjY3ODMsIDAuNTg0ODU3Nzk2NjI4MTQ4MywgMC41ODY1MjI3NTYwNzM2MTgxLCAwLjU4ODE4NzcxNTUxOTA4NzksIDAuNTg5ODUyNjc0OTY0NTU3OCwgMC41OTE1MTc2MzQ0MTAwMjc3LCAwLjU5MzE4MjU5Mzg1NTQ5NzUsIDAuNTk0ODQ3NTUzMzAwOTY3NCwgMC41OTY1MTI1MTI3NDY0Mzc0LCAwLjU5ODE3NzQ3MjE5MTkwNzIsIDAuNTk5ODQyNDMxNjM3Mzc3LCAwLjYwMTUwNzM5MTA4Mjg0NjksIDAuNjAzMTcyMzUwNTI4MzE2OSwgMC42MDQ4MzczMDk5NzM3ODY2LCAwLjYwNjUwMjI2OTQxOTI1NjYsIDAuNjA4MTY3MjI4ODY0NzI2NCwgMC42MDk4MzIxODgzMTAxOTY0LCAwLjYxMTQ5NzE0Nzc1NTY2NjEsIDAuNjEzMTYyMTA3MjAxMTM2MSwgMC42MTQ4MjcwNjY2NDY2MDU5LCAwLjYxNjQ5MjAyNjA5MjA3NTksIDAuNjE4MTU2OTg1NTM3NTQ1NiwgMC42MTk4MjE5NDQ5ODMwMTU2LCAwLjYyMTQ4NjkwNDQyODQ4NTQsIDAuNjIzMTUxODYzODczOTU1MywgMC42MjQ4MTY4MjMzMTk0MjUxLCAwLjYyNjQ4MTc4Mjc2NDg5NSwgMC42MjgxNDY3NDIyMTAzNjQ5LCAwLjYyOTgxMTcwMTY1NTgzNDcsIDAuNjMxNDc2NjYxMTAxMzA0NiwgMC42MzMxNDE2MjA1NDY3NzQ1LCAwLjYzNDgwNjU3OTk5MjI0NDQsIDAuNjM2NDcxNTM5NDM3NzE0MiwgMC42MzgxMzY0OTg4ODMxODQxLCAwLjYzOTgwMTQ1ODMyODY1NCwgMC42NDE0NjY0MTc3NzQxMjM4LCAwLjY0MzEzMTM3NzIxOTU5MzcsIDAuNjQ0Nzk2MzM2NjY1MDYzNiwgMC42NDY0NjEyOTYxMTA1MzM1LCAwLjY0ODEyNjI1NTU1NjAwMzMsIDAuNjQ5NzkxMjE1MDAxNDczMiwgMC42NTE0NTYxNzQ0NDY5NDMxLCAwLjY1MzEyMTEzMzg5MjQxMywgMC42NTQ3ODYwOTMzMzc4ODI4LCAwLjY1NjQ1MTA1Mjc4MzM1MjcsIDAuNjU4MTE2MDEyMjI4ODIyNywgMC42NTk3ODA5NzE2NzQyOTI0LCAwLjY2MTQ0NTkzMTExOTc2MjQsIDAuNjYzMTEwODkwNTY1MjMyMiwgMC42NjQ3NzU4NTAwMTA3MDIsIDAuNjY2NDQwODA5NDU2MTcxOSwgMC42NjgxMDU3Njg5MDE2NDE4LCAwLjY2OTc3MDcyODM0NzExMTcsIDAuNjcxNDM1Njg3NzkyNTgxNCwgMC42NzMxMDA2NDcyMzgwNTE0LCAwLjY3NDc2NTYwNjY4MzUyMTMsIDAuNjc2NDMwNTY2MTI4OTkxMiwgMC42NzgwOTU1MjU1NzQ0NjEsIDAuNjc5NzYwNDg1MDE5OTMwOSwgMC42ODE0MjU0NDQ0NjU0MDA4LCAwLjY4MzA5MDQwMzkxMDg3MDcsIDAuNjg0NzU1MzYzMzU2MzQwNSwgMC42ODY0MjAzMjI4MDE4MTA0LCAwLjY4ODA4NTI4MjI0NzI4MDMsIDAuNjg5NzUwMjQxNjkyNzUwMSwgMC42OTE0MTUyMDExMzgyMiwgMC42OTMwODAxNjA1ODM2ODk5LCAwLjY5NDc0NTEyMDAyOTE1OTgsIDAuNjk2NDEwMDc5NDc0NjI5NiwgMC42OTgwNzUwMzg5MjAwOTk1LCAwLjY5OTczOTk5ODM2NTU2OTQsIDAuNzAxNDA0OTU3ODExMDM5MywgMC43MDMwNjk5MTcyNTY1MDksIDAuNzA0NzM0ODc2NzAxOTc5LCAwLjcwNjM5OTgzNjE0NzQ0ODgsIDAuNzA4MDY0Nzk1NTkyOTE4NywgMC43MDk3Mjk3NTUwMzgzODg1LCAwLjcxMTM5NDcxNDQ4Mzg1ODUsIDAuNzEzMDU5NjczOTI5MzI4MywgMC43MTQ3MjQ2MzMzNzQ3OTgyLCAwLjcxNjM4OTU5MjgyMDI2OCwgMC43MTgwNTQ1NTIyNjU3MzgsIDAuNzE5NzE5NTExNzExMjA3NywgMC43MjEzODQ0NzExNTY2Nzc3LCAwLjcyMzA0OTQzMDYwMjE0NzUsIDAuNzI0NzE0MzkwMDQ3NjE3NSwgMC43MjYzNzkzNDk0OTMwODcyLCAwLjcyODA0NDMwODkzODU1NzIsIDAuNzI5NzA5MjY4Mzg0MDI3LCAwLjczMTM3NDIyNzgyOTQ5NywgMC43MzMwMzkxODcyNzQ5NjY4LCAwLjczNDcwNDE0NjcyMDQzNjcsIDAuNzM2MzY5MTA2MTY1OTA2NiwgMC43MzgwMzQwNjU2MTEzNzYzLCAwLjczOTY5OTAyNTA1Njg0NjMsIDAuNzQxMzYzOTg0NTAyMzE2MSwgMC43NDMwMjg5NDM5NDc3ODYxLCAwLjc0NDY5MzkwMzM5MzI1NTgsIDAuNzQ2MzU4ODYyODM4NzI1OCwgMC43NDgwMjM4MjIyODQxOTU2LCAwLjc0OTY4ODc4MTcyOTY2NTYsIDAuNzUxMzUzNzQxMTc1MTM1MywgMC43NTMwMTg3MDA2MjA2MDUzLCAwLjc1NDY4MzY2MDA2NjA3NTEsIDAuNzU2MzQ4NjE5NTExNTQ1LCAwLjc1ODAxMzU3ODk1NzAxNDgsIDAuNzU5Njc4NTM4NDAyNDg0OCwgMC43NjEzNDM0OTc4NDc5NTQ2LCAwLjc2MzAwODQ1NzI5MzQyNDUsIDAuNzY0NjczNDE2NzM4ODk0MywgMC43NjYzMzgzNzYxODQzNjQzLCAwLjc2ODAwMzMzNTYyOTgzNDEsIDAuNzY5NjY4Mjk1MDc1MzA0LCAwLjc3MTMzMzI1NDUyMDc3MzgsIDAuNzcyOTk4MjEzOTY2MjQzOCwgMC43NzQ2NjMxNzM0MTE3MTM1LCAwLjc3NjMyODEzMjg1NzE4MzQsIDAuNzc3OTkzMDkyMzAyNjUzMywgMC43Nzk2NTgwNTE3NDgxMjMyLCAwLjc4MTMyMzAxMTE5MzU5MywgMC43ODI5ODc5NzA2MzkwNjI5LCAwLjc4NDY1MjkzMDA4NDUzMjksIDAuNzg2MzE3ODg5NTMwMDAyNiwgMC43ODc5ODI4NDg5NzU0NzI1LCAwLjc4OTY0NzgwODQyMDk0MjQsIDAuNzkxMzEyNzY3ODY2NDEyNCwgMC43OTI5Nzc3MjczMTE4ODIxLCAwLjc5NDY0MjY4Njc1NzM1MjEsIDAuNzk2MzA3NjQ2MjAyODIxOSwgMC43OTc5NzI2MDU2NDgyOTE5LCAwLjc5OTYzNzU2NTA5Mzc2MTYsIDAuODAxMzAyNTI0NTM5MjMxNiwgMC44MDI5Njc0ODM5ODQ3MDE0LCAwLjgwNDYzMjQ0MzQzMDE3MTMsIDAuODA2Mjk3NDAyODc1NjQxMSwgMC44MDc5NjIzNjIzMjExMTExLCAwLjgwOTYyNzMyMTc2NjU4MDksIDAuODExMjkyMjgxMjEyMDUwOCwgMC44MTI5NTcyNDA2NTc1MjA2LCAwLjgxNDYyMjIwMDEwMjk5MDUsIDAuODE2Mjg3MTU5NTQ4NDYwNCwgMC44MTc5NTIxMTg5OTM5MzAyLCAwLjgxOTYxNzA3ODQzOTQwMDEsIDAuODIxMjgyMDM3ODg0ODcsIDAuODIyOTQ2OTk3MzMwMzM5OCwgMC44MjQ2MTE5NTY3NzU4MDk3LCAwLjgyNjI3NjkxNjIyMTI3OTYsIDAuODI3OTQxODc1NjY2NzQ5NSwgMC44Mjk2MDY4MzUxMTIyMTkzLCAwLjgzMTI3MTc5NDU1NzY4OTIsIDAuODMyOTM2NzU0MDAzMTU5MSwgMC44MzQ2MDE3MTM0NDg2Mjg5LCAwLjgzNjI2NjY3Mjg5NDA5ODgsIDAuODM3OTMxNjMyMzM5NTY4NywgMC44Mzk1OTY1OTE3ODUwMzg2LCAwLjg0MTI2MTU1MTIzMDUwODQsIDAuODQyOTI2NTEwNjc1OTc4MywgMC44NDQ1OTE0NzAxMjE0NDgyLCAwLjg0NjI1NjQyOTU2NjkxODIsIDAuODQ3OTIxMzg5MDEyMzg3OSwgMC44NDk1ODYzNDg0NTc4NTc5LCAwLjg1MTI1MTMwNzkwMzMyNzcsIDAuODUyOTE2MjY3MzQ4Nzk3NCwgMC44NTQ1ODEyMjY3OTQyNjc0LCAwLjg1NjI0NjE4NjIzOTczNzMsIDAuODU3OTExMTQ1Njg1MjA3MiwgMC44NTk1NzYxMDUxMzA2NzcsIDAuODYxMjQxMDY0NTc2MTQ2OSwgMC44NjI5MDYwMjQwMjE2MTY4LCAwLjg2NDU3MDk4MzQ2NzA4NjcsIDAuODY2MjM1OTQyOTEyNTU2NSwgMC44Njc5MDA5MDIzNTgwMjY0LCAwLjg2OTU2NTg2MTgwMzQ5NjMsIDAuODcxMjMwODIxMjQ4OTY2MSwgMC44NzI4OTU3ODA2OTQ0MzYsIDAuODc0NTYwNzQwMTM5OTA1OSwgMC44NzYyMjU2OTk1ODUzNzU4LCAwLjg3Nzg5MDY1OTAzMDg0NTYsIDAuODc5NTU1NjE4NDc2MzE1NSwgMC44ODEyMjA1Nzc5MjE3ODU0LCAwLjg4Mjg4NTUzNzM2NzI1NTMsIDAuODg0NTUwNDk2ODEyNzI1MSwgMC44ODYyMTU0NTYyNTgxOTUsIDAuODg3ODgwNDE1NzAzNjY0OSwgMC44ODk1NDUzNzUxNDkxMzQ3LCAwLjg5MTIxMDMzNDU5NDYwNDUsIDAuODkyODc1Mjk0MDQwMDc0NSwgMC44OTQ1NDAyNTM0ODU1NDQzXSkKICAgICAgICAgICAgICAucmFuZ2UoWycjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZjFlZWY2JywgJyNmMWVlZjYnLCAnI2YxZWVmNicsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2Q0YjlkYScsICcjZDRiOWRhJywgJyNkNGI5ZGEnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNjOTk0YzcnLCAnI2M5OTRjNycsICcjYzk5NGM3JywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGY2NWIwJywgJyNkZjY1YjAnLCAnI2RmNjViMCcsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnI2RkMWM3NycsICcjZGQxYzc3JywgJyNkZDFjNzcnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnLCAnIzk4MDA0MycsICcjOTgwMDQzJywgJyM5ODAwNDMnXSk7CiAgICAKCiAgICBjb2xvcl9tYXBfMGU0YTViYzdjYjU2NGM2NDk2MDBkYzM4Zjc2MDY0NDEueCA9IGQzLnNjYWxlLmxpbmVhcigpCiAgICAgICAgICAgICAgLmRvbWFpbihbMC4wNjM3MjU0OTAxOTYwNzg0MywgMC44OTQ1NDAyNTM0ODU1NDQzXSkKICAgICAgICAgICAgICAucmFuZ2UoWzAsIDQwMF0pOwoKICAgIGNvbG9yX21hcF8wZTRhNWJjN2NiNTY0YzY0OTYwMGRjMzhmNzYwNjQ0MS5sZWdlbmQgPSBMLmNvbnRyb2woe3Bvc2l0aW9uOiAndG9wcmlnaHQnfSk7CiAgICBjb2xvcl9tYXBfMGU0YTViYzdjYjU2NGM2NDk2MDBkYzM4Zjc2MDY0NDEubGVnZW5kLm9uQWRkID0gZnVuY3Rpb24gKG1hcCkge3ZhciBkaXYgPSBMLkRvbVV0aWwuY3JlYXRlKCdkaXYnLCAnbGVnZW5kJyk7IHJldHVybiBkaXZ9OwogICAgY29sb3JfbWFwXzBlNGE1YmM3Y2I1NjRjNjQ5NjAwZGMzOGY3NjA2NDQxLmxlZ2VuZC5hZGRUbyhtYXBfNTViZTczZWIwNzFlNDA2NWI4YTA5N2M2MjRiMmQ0YzApOwoKICAgIGNvbG9yX21hcF8wZTRhNWJjN2NiNTY0YzY0OTYwMGRjMzhmNzYwNjQ0MS54QXhpcyA9IGQzLnN2Zy5heGlzKCkKICAgICAgICAuc2NhbGUoY29sb3JfbWFwXzBlNGE1YmM3Y2I1NjRjNjQ5NjAwZGMzOGY3NjA2NDQxLngpCiAgICAgICAgLm9yaWVudCgidG9wIikKICAgICAgICAudGlja1NpemUoMSkKICAgICAgICAudGlja1ZhbHVlcyhbMC4wNjM3MjU0OTAxOTYwNzg0MywgMC4yMDIxOTQ2MTc0MTA5ODk0LCAwLjM0MDY2Mzc0NDYyNTkwMDQsIDAuNDc5MTMyODcxODQwODExMywgMC42MTc2MDE5OTkwNTU3MjIzLCAwLjc1NjA3MTEyNjI3MDYzMzMsIDAuODk0NTQwMjUzNDg1NTQ0M10pOwoKICAgIGNvbG9yX21hcF8wZTRhNWJjN2NiNTY0YzY0OTYwMGRjMzhmNzYwNjQ0MS5zdmcgPSBkMy5zZWxlY3QoIi5sZWdlbmQubGVhZmxldC1jb250cm9sIikuYXBwZW5kKCJzdmciKQogICAgICAgIC5hdHRyKCJpZCIsICdsZWdlbmQnKQogICAgICAgIC5hdHRyKCJ3aWR0aCIsIDQ1MCkKICAgICAgICAuYXR0cigiaGVpZ2h0IiwgNDApOwoKICAgIGNvbG9yX21hcF8wZTRhNWJjN2NiNTY0YzY0OTYwMGRjMzhmNzYwNjQ0MS5nID0gY29sb3JfbWFwXzBlNGE1YmM3Y2I1NjRjNjQ5NjAwZGMzOGY3NjA2NDQxLnN2Zy5hcHBlbmQoImciKQogICAgICAgIC5hdHRyKCJjbGFzcyIsICJrZXkiKQogICAgICAgIC5hdHRyKCJ0cmFuc2Zvcm0iLCAidHJhbnNsYXRlKDI1LDE2KSIpOwoKICAgIGNvbG9yX21hcF8wZTRhNWJjN2NiNTY0YzY0OTYwMGRjMzhmNzYwNjQ0MS5nLnNlbGVjdEFsbCgicmVjdCIpCiAgICAgICAgLmRhdGEoY29sb3JfbWFwXzBlNGE1YmM3Y2I1NjRjNjQ5NjAwZGMzOGY3NjA2NDQxLmNvbG9yLnJhbmdlKCkubWFwKGZ1bmN0aW9uKGQsIGkpIHsKICAgICAgICAgIHJldHVybiB7CiAgICAgICAgICAgIHgwOiBpID8gY29sb3JfbWFwXzBlNGE1YmM3Y2I1NjRjNjQ5NjAwZGMzOGY3NjA2NDQxLngoY29sb3JfbWFwXzBlNGE1YmM3Y2I1NjRjNjQ5NjAwZGMzOGY3NjA2NDQxLmNvbG9yLmRvbWFpbigpW2kgLSAxXSkgOiBjb2xvcl9tYXBfMGU0YTViYzdjYjU2NGM2NDk2MDBkYzM4Zjc2MDY0NDEueC5yYW5nZSgpWzBdLAogICAgICAgICAgICB4MTogaSA8IGNvbG9yX21hcF8wZTRhNWJjN2NiNTY0YzY0OTYwMGRjMzhmNzYwNjQ0MS5jb2xvci5kb21haW4oKS5sZW5ndGggPyBjb2xvcl9tYXBfMGU0YTViYzdjYjU2NGM2NDk2MDBkYzM4Zjc2MDY0NDEueChjb2xvcl9tYXBfMGU0YTViYzdjYjU2NGM2NDk2MDBkYzM4Zjc2MDY0NDEuY29sb3IuZG9tYWluKClbaV0pIDogY29sb3JfbWFwXzBlNGE1YmM3Y2I1NjRjNjQ5NjAwZGMzOGY3NjA2NDQxLngucmFuZ2UoKVsxXSwKICAgICAgICAgICAgejogZAogICAgICAgICAgfTsKICAgICAgICB9KSkKICAgICAgLmVudGVyKCkuYXBwZW5kKCJyZWN0IikKICAgICAgICAuYXR0cigiaGVpZ2h0IiwgMTApCiAgICAgICAgLmF0dHIoIngiLCBmdW5jdGlvbihkKSB7IHJldHVybiBkLngwOyB9KQogICAgICAgIC5hdHRyKCJ3aWR0aCIsIGZ1bmN0aW9uKGQpIHsgcmV0dXJuIGQueDEgLSBkLngwOyB9KQogICAgICAgIC5zdHlsZSgiZmlsbCIsIGZ1bmN0aW9uKGQpIHsgcmV0dXJuIGQuejsgfSk7CgogICAgY29sb3JfbWFwXzBlNGE1YmM3Y2I1NjRjNjQ5NjAwZGMzOGY3NjA2NDQxLmcuY2FsbChjb2xvcl9tYXBfMGU0YTViYzdjYjU2NGM2NDk2MDBkYzM4Zjc2MDY0NDEueEF4aXMpLmFwcGVuZCgidGV4dCIpCiAgICAgICAgLmF0dHIoImNsYXNzIiwgImNhcHRpb24iKQogICAgICAgIC5hdHRyKCJ5IiwgMjEpCiAgICAgICAgLnRleHQoJycpOwogICAgCiAgICAgICAgICAgIHZhciBjaXJjbGVfbWFya2VyXzk1ZDFiY2Q1OGU1MDQxNTliNzI5ZDJhNDM0OGEzMjRlID0gTC5jaXJjbGVNYXJrZXIoCiAgICAgICAgICAgICAgICBbMzcuNTYzNjQ2NSwgMTI2Ljk4OTU3OTZdLAogICAgICAgICAgICAgICAgewogICJidWJibGluZ01vdXNlRXZlbnRzIjogdHJ1ZSwKICAiY29sb3IiOiAiIzMxODZjYyIsCiAgImRhc2hBcnJheSI6IG51bGwsCiAgImRhc2hPZmZzZXQiOiBudWxsLAogICJmaWxsIjogdHJ1ZSwKICAiZmlsbENvbG9yIjogIiMzMTg2Y2MiLAogICJmaWxsT3BhY2l0eSI6IDAuMiwKICAiZmlsbFJ1bGUiOiAiZXZlbm9kZCIsCiAgImxpbmVDYXAiOiAicm91bmQiLAogICJsaW5lSm9pbiI6ICJyb3VuZCIsCiAgIm9wYWNpdHkiOiAxLjAsCiAgInJhZGl1cyI6IDEyLjc1NDE2MTMxMzk5MDE3NiwKICAic3Ryb2tlIjogdHJ1ZSwKICAid2VpZ2h0IjogMwp9CiAgICAgICAgICAgICAgICApCiAgICAgICAgICAgICAgICAuYWRkVG8obWFwXzU1YmU3M2ViMDcxZTQwNjViOGEwOTdjNjI0YjJkNGMwKTsKICAgICAgICAgICAgCiAgICAKICAgICAgICAgICAgdmFyIGNpcmNsZV9tYXJrZXJfMzE5NTQ2YjVkMjNhNDE4NWE4YjM5NGI1NDE1ODNmNjQgPSBMLmNpcmNsZU1hcmtlcigKICAgICAgICAgICAgICAgIFszNy41NzU1NTc4LCAxMjYuOTg0ODY3NF0sCiAgICAgICAgICAgICAgICB7CiAgImJ1YmJsaW5nTW91c2VFdmVudHMiOiB0cnVlLAogICJjb2xvciI6ICIjMzE4NmNjIiwKICAiZGFzaEFycmF5IjogbnVsbCwKICAiZGFzaE9mZnNldCI6IG51bGwsCiAgImZpbGwiOiB0cnVlLAogICJmaWxsQ29sb3IiOiAiIzMxODZjYyIsCiAgImZpbGxPcGFjaXR5IjogMC4yLAogICJmaWxsUnVsZSI6ICJldmVub2RkIiwKICAibGluZUNhcCI6ICJyb3VuZCIsCiAgImxpbmVKb2luIjogInJvdW5kIiwKICAib3BhY2l0eSI6IDEuMCwKICAicmFkaXVzIjogMTUuMjM4NDc0ODE5ODIwMTEyLAogICJzdHJva2UiOiB0cnVlLAogICJ3ZWlnaHQiOiAzCn0KICAgICAgICAgICAgICAgICkKICAgICAgICAgICAgICAgIC5hZGRUbyhtYXBfNTViZTczZWIwNzFlNDA2NWI4YTA5N2M2MjRiMmQ0YzApOwogICAgICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgY2lyY2xlX21hcmtlcl85Y2Y1YzU3NDFhZjI0OGY4YjI2MmJmZWY3YWZkNzk4OSA9IEwuY2lyY2xlTWFya2VyKAogICAgICAgICAgICAgICAgWzM3LjU1NDc1ODQsIDEyNi45NzM0OTgxXSwKICAgICAgICAgICAgICAgIHsKICAiYnViYmxpbmdNb3VzZUV2ZW50cyI6IHRydWUsCiAgImNvbG9yIjogIiMzMTg2Y2MiLAogICJkYXNoQXJyYXkiOiBudWxsLAogICJkYXNoT2Zmc2V0IjogbnVsbCwKICAiZmlsbCI6IHRydWUsCiAgImZpbGxDb2xvciI6ICIjMzE4NmNjIiwKICAiZmlsbE9wYWNpdHkiOiAwLjIsCiAgImZpbGxSdWxlIjogImV2ZW5vZGQiLAogICJsaW5lQ2FwIjogInJvdW5kIiwKICAibGluZUpvaW4iOiAicm91bmQiLAogICJvcGFjaXR5IjogMS4wLAogICJyYWRpdXMiOiA5LjA3MzcyMjI4ODk5MzU5NCwKICAic3Ryb2tlIjogdHJ1ZSwKICAid2VpZ2h0IjogMwp9CiAgICAgICAgICAgICAgICApCiAgICAgICAgICAgICAgICAuYWRkVG8obWFwXzU1YmU3M2ViMDcxZTQwNjViOGEwOTdjNjI0YjJkNGMwKTsKICAgICAgICAgICAgCiAgICAKICAgICAgICAgICAgdmFyIGNpcmNsZV9tYXJrZXJfOTI0YmYwYTI1Mjg5NDNiZTk3MGJhMTBmOTg5OTc1MzQgPSBMLmNpcmNsZU1hcmtlcigKICAgICAgICAgICAgICAgIFszNy41NjQ3ODQ4LCAxMjYuOTY2Nzc2Ml0sCiAgICAgICAgICAgICAgICB7CiAgImJ1YmJsaW5nTW91c2VFdmVudHMiOiB0cnVlLAogICJjb2xvciI6ICIjMzE4NmNjIiwKICAiZGFzaEFycmF5IjogbnVsbCwKICAiZGFzaE9mZnNldCI6IG51bGwsCiAgImZpbGwiOiB0cnVlLAogICJmaWxsQ29sb3IiOiAiIzMxODZjYyIsCiAgImZpbGxPcGFjaXR5IjogMC4yLAogICJmaWxsUnVsZSI6ICJldmVub2RkIiwKICAibGluZUNhcCI6ICJyb3VuZCIsCiAgImxpbmVKb2luIjogInJvdW5kIiwKICAib3BhY2l0eSI6IDEuMCwKICAicmFkaXVzIjogMTkuNzgyOTk0Mjc0ODkyMDE1LAogICJzdHJva2UiOiB0cnVlLAogICJ3ZWlnaHQiOiAzCn0KICAgICAgICAgICAgICAgICkKICAgICAgICAgICAgICAgIC5hZGRUbyhtYXBfNTViZTczZWIwNzFlNDA2NWI4YTA5N2M2MjRiMmQ0YzApOwogICAgICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgY2lyY2xlX21hcmtlcl82YTI1YTc0OGY0ODk0ZTk1ODE0N2UwODljYzMwYzk5MSA9IEwuY2lyY2xlTWFya2VyKAogICAgICAgICAgICAgICAgWzM3LjU3MTg1MjksIDEyNi45OTg5MTQzXSwKICAgICAgICAgICAgICAgIHsKICAiYnViYmxpbmdNb3VzZUV2ZW50cyI6IHRydWUsCiAgImNvbG9yIjogIiMzMTg2Y2MiLAogICJkYXNoQXJyYXkiOiBudWxsLAogICJkYXNoT2Zmc2V0IjogbnVsbCwKICAiZmlsbCI6IHRydWUsCiAgImZpbGxDb2xvciI6ICIjMzE4NmNjIiwKICAiZmlsbE9wYWNpdHkiOiAwLjIsCiAgImZpbGxSdWxlIjogImV2ZW5vZGQiLAogICJsaW5lQ2FwIjogInJvdW5kIiwKICAibGluZUpvaW4iOiAicm91bmQiLAogICJvcGFjaXR5IjogMS4wLAogICJyYWRpdXMiOiAxMS45ODM4MTg4MjE3NDU1ODMsCiAgInN0cm9rZSI6IHRydWUsCiAgIndlaWdodCI6IDMKfQogICAgICAgICAgICAgICAgKQogICAgICAgICAgICAgICAgLmFkZFRvKG1hcF81NWJlNzNlYjA3MWU0MDY1YjhhMDk3YzYyNGIyZDRjMCk7CiAgICAgICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBjaXJjbGVfbWFya2VyXzc4NmU5MWNkMWYzYzQxNDU4MWU1MjhmNTZkYzEwMTc3ID0gTC5jaXJjbGVNYXJrZXIoCiAgICAgICAgICAgICAgICBbMzcuNTM4NzA5OSwgMTI2Ljk2NTkxODNdLAogICAgICAgICAgICAgICAgewogICJidWJibGluZ01vdXNlRXZlbnRzIjogdHJ1ZSwKICAiY29sb3IiOiAiIzMxODZjYyIsCiAgImRhc2hBcnJheSI6IG51bGwsCiAgImRhc2hPZmZzZXQiOiBudWxsLAogICJmaWxsIjogdHJ1ZSwKICAiZmlsbENvbG9yIjogIiMzMTg2Y2MiLAogICJmaWxsT3BhY2l0eSI6IDAuMiwKICAiZmlsbFJ1bGUiOiAiZXZlbm9kZCIsCiAgImxpbmVDYXAiOiAicm91bmQiLAogICJsaW5lSm9pbiI6ICJyb3VuZCIsCiAgIm9wYWNpdHkiOiAxLjAsCiAgInJhZGl1cyI6IDI2LjkwNjg1NDIzOTEwNDcyOCwKICAic3Ryb2tlIjogdHJ1ZSwKICAid2VpZ2h0IjogMwp9CiAgICAgICAgICAgICAgICApCiAgICAgICAgICAgICAgICAuYWRkVG8obWFwXzU1YmU3M2ViMDcxZTQwNjViOGEwOTdjNjI0YjJkNGMwKTsKICAgICAgICAgICAgCiAgICAKICAgICAgICAgICAgdmFyIGNpcmNsZV9tYXJrZXJfOGQxNDg5ZDI5NzQ5NDk5MGExMDAzODVjZjY5NDk2YjUgPSBMLmNpcmNsZU1hcmtlcigKICAgICAgICAgICAgICAgIFszNy41ODk3NDgyLCAxMjcuMDE2MTM1M10sCiAgICAgICAgICAgICAgICB7CiAgImJ1YmJsaW5nTW91c2VFdmVudHMiOiB0cnVlLAogICJjb2xvciI6ICIjMzE4NmNjIiwKICAiZGFzaEFycmF5IjogbnVsbCwKICAiZGFzaE9mZnNldCI6IG51bGwsCiAgImZpbGwiOiB0cnVlLAogICJmaWxsQ29sb3IiOiAiIzMxODZjYyIsCiAgImZpbGxPcGFjaXR5IjogMC4yLAogICJmaWxsUnVsZSI6ICJldmVub2RkIiwKICAibGluZUNhcCI6ICJyb3VuZCIsCiAgImxpbmVKb2luIjogInJvdW5kIiwKICAib3BhY2l0eSI6IDEuMCwKICAicmFkaXVzIjogMTEuNTU2NDkxMDY2Nzc3NzIsCiAgInN0cm9rZSI6IHRydWUsCiAgIndlaWdodCI6IDMKfQogICAgICAgICAgICAgICAgKQogICAgICAgICAgICAgICAgLmFkZFRvKG1hcF81NWJlNzNlYjA3MWU0MDY1YjhhMDk3YzYyNGIyZDRjMCk7CiAgICAgICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBjaXJjbGVfbWFya2VyX2VlNzVjZWI3YjUxNjRhYjZiZWNjYzM1YmVkNTRiYWZhID0gTC5jaXJjbGVNYXJrZXIoCiAgICAgICAgICAgICAgICBbMzcuNTg1MDYxNDk5OTk5OTksIDEyNy4wNDU3Njc5XSwKICAgICAgICAgICAgICAgIHsKICAiYnViYmxpbmdNb3VzZUV2ZW50cyI6IHRydWUsCiAgImNvbG9yIjogIiMzMTg2Y2MiLAogICJkYXNoQXJyYXkiOiBudWxsLAogICJkYXNoT2Zmc2V0IjogbnVsbCwKICAiZmlsbCI6IHRydWUsCiAgImZpbGxDb2xvciI6ICIjMzE4NmNjIiwKICAiZmlsbE9wYWNpdHkiOiAwLjIsCiAgImZpbGxSdWxlIjogImV2ZW5vZGQiLAogICJsaW5lQ2FwIjogInJvdW5kIiwKICAibGluZUpvaW4iOiAicm91bmQiLAogICJvcGFjaXR5IjogMS4wLAogICJyYWRpdXMiOiAyOC45NzMwMjAzNzQ5NDI2NjQsCiAgInN0cm9rZSI6IHRydWUsCiAgIndlaWdodCI6IDMKfQogICAgICAgICAgICAgICAgKQogICAgICAgICAgICAgICAgLmFkZFRvKG1hcF81NWJlNzNlYjA3MWU0MDY1YjhhMDk3YzYyNGIyZDRjMCk7CiAgICAgICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBjaXJjbGVfbWFya2VyXzE3YTJlYTQ1ZGQyNTQ1Y2FiOGJjMDU0ODU2MGJlMTUyID0gTC5jaXJjbGVNYXJrZXIoCiAgICAgICAgICAgICAgICBbMzcuNTUwODE0LCAxMjYuOTU0MDI4XSwKICAgICAgICAgICAgICAgIHsKICAiYnViYmxpbmdNb3VzZUV2ZW50cyI6IHRydWUsCiAgImNvbG9yIjogIiMzMTg2Y2MiLAogICJkYXNoQXJyYXkiOiBudWxsLAogICJkYXNoT2Zmc2V0IjogbnVsbCwKICAiZmlsbCI6IHRydWUsCiAgImZpbGxDb2xvciI6ICIjMzE4NmNjIiwKICAiZmlsbE9wYWNpdHkiOiAwLjIsCiAgImZpbGxSdWxlIjogImV2ZW5vZGQiLAogICJsaW5lQ2FwIjogInJvdW5kIiwKICAibGluZUpvaW4iOiAicm91bmQiLAogICJvcGFjaXR5IjogMS4wLAogICJyYWRpdXMiOiAzNS4zODY1NjU1MjAwMjMyNTQsCiAgInN0cm9rZSI6IHRydWUsCiAgIndlaWdodCI6IDMKfQogICAgICAgICAgICAgICAgKQogICAgICAgICAgICAgICAgLmFkZFRvKG1hcF81NWJlNzNlYjA3MWU0MDY1YjhhMDk3YzYyNGIyZDRjMCk7CiAgICAgICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBjaXJjbGVfbWFya2VyXzNmOWVlYjQ5Nzg0ZDQ0ZWQ4ZTNjZWRhZTBhZjIyYWE1ID0gTC5jaXJjbGVNYXJrZXIoCiAgICAgICAgICAgICAgICBbMzcuNTE3OTgzOSwgMTI2LjkwNzUwNTZdLAogICAgICAgICAgICAgICAgewogICJidWJibGluZ01vdXNlRXZlbnRzIjogdHJ1ZSwKICAiY29sb3IiOiAiIzMxODZjYyIsCiAgImRhc2hBcnJheSI6IG51bGwsCiAgImRhc2hPZmZzZXQiOiBudWxsLAogICJmaWxsIjogdHJ1ZSwKICAiZmlsbENvbG9yIjogIiMzMTg2Y2MiLAogICJmaWxsT3BhY2l0eSI6IDAuMiwKICAiZmlsbFJ1bGUiOiAiZXZlbm9kZCIsCiAgImxpbmVDYXAiOiAicm91bmQiLAogICJsaW5lSm9pbiI6ICJyb3VuZCIsCiAgIm9wYWNpdHkiOiAxLjAsCiAgInJhZGl1cyI6IDQyLjc1OTc4OTM1OTU1MDMxLAogICJzdHJva2UiOiB0cnVlLAogICJ3ZWlnaHQiOiAzCn0KICAgICAgICAgICAgICAgICkKICAgICAgICAgICAgICAgIC5hZGRUbyhtYXBfNTViZTczZWIwNzFlNDA2NWI4YTA5N2M2MjRiMmQ0YzApOwogICAgICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgY2lyY2xlX21hcmtlcl81MTYzMjQ2MmM3YzA0OTY3YmE3YjljNjhhYWE1YTk1YSA9IEwuY2lyY2xlTWFya2VyKAogICAgICAgICAgICAgICAgWzM3LjU2MTczMDksIDEyNy4wMzYzODA2XSwKICAgICAgICAgICAgICAgIHsKICAiYnViYmxpbmdNb3VzZUV2ZW50cyI6IHRydWUsCiAgImNvbG9yIjogIiMzMTg2Y2MiLAogICJkYXNoQXJyYXkiOiBudWxsLAogICJkYXNoT2Zmc2V0IjogbnVsbCwKICAiZmlsbCI6IHRydWUsCiAgImZpbGxDb2xvciI6ICIjMzE4NmNjIiwKICAiZmlsbE9wYWNpdHkiOiAwLjIsCiAgImZpbGxSdWxlIjogImV2ZW5vZGQiLAogICJsaW5lQ2FwIjogInJvdW5kIiwKICAibGluZUpvaW4iOiAicm91bmQiLAogICJvcGFjaXR5IjogMS4wLAogICJyYWRpdXMiOiAyMC42MTQzMzU5NTMxODA0MDgsCiAgInN0cm9rZSI6IHRydWUsCiAgIndlaWdodCI6IDMKfQogICAgICAgICAgICAgICAgKQogICAgICAgICAgICAgICAgLmFkZFRvKG1hcF81NWJlNzNlYjA3MWU0MDY1YjhhMDk3YzYyNGIyZDRjMCk7CiAgICAgICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBjaXJjbGVfbWFya2VyXzdiNjZmYmZlNTcxZjQwNDE5ZjcyZDU4MTEyNjc2ZDQ1ID0gTC5jaXJjbGVNYXJrZXIoCiAgICAgICAgICAgICAgICBbMzcuNTEzMDg2NiwgMTI2Ljk0Mjg0OThdLAogICAgICAgICAgICAgICAgewogICJidWJibGluZ01vdXNlRXZlbnRzIjogdHJ1ZSwKICAiY29sb3IiOiAiIzMxODZjYyIsCiAgImRhc2hBcnJheSI6IG51bGwsCiAgImRhc2hPZmZzZXQiOiBudWxsLAogICJmaWxsIjogdHJ1ZSwKICAiZmlsbENvbG9yIjogIiMzMTg2Y2MiLAogICJmaWxsT3BhY2l0eSI6IDAuMiwKICAiZmlsbFJ1bGUiOiAiZXZlbm9kZCIsCiAgImxpbmVDYXAiOiAicm91bmQiLAogICJsaW5lSm9pbiI6ICJyb3VuZCIsCiAgIm9wYWNpdHkiOiAxLjAsCiAgInJhZGl1cyI6IDIyLjI1MzE0Mzk0NDQ5MjEyLAogICJzdHJva2UiOiB0cnVlLAogICJ3ZWlnaHQiOiAzCn0KICAgICAgICAgICAgICAgICkKICAgICAgICAgICAgICAgIC5hZGRUbyhtYXBfNTViZTczZWIwNzFlNDA2NWI4YTA5N2M2MjRiMmQ0YzApOwogICAgICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgY2lyY2xlX21hcmtlcl8zNmRkYzkyMjU2ZTI0YjFkYjJlMTMzZGUwMmQyYjE1NiA9IEwuY2lyY2xlTWFya2VyKAogICAgICAgICAgICAgICAgWzM3LjU0Mjg3MywgMTI3LjA4MzgyMV0sCiAgICAgICAgICAgICAgICB7CiAgImJ1YmJsaW5nTW91c2VFdmVudHMiOiB0cnVlLAogICJjb2xvciI6ICIjMzE4NmNjIiwKICAiZGFzaEFycmF5IjogbnVsbCwKICAiZGFzaE9mZnNldCI6IG51bGwsCiAgImZpbGwiOiB0cnVlLAogICJmaWxsQ29sb3IiOiAiIzMxODZjYyIsCiAgImZpbGxPcGFjaXR5IjogMC4yLAogICJmaWxsUnVsZSI6ICJldmVub2RkIiwKICAibGluZUNhcCI6ICJyb3VuZCIsCiAgImxpbmVKb2luIjogInJvdW5kIiwKICAib3BhY2l0eSI6IDEuMCwKICAicmFkaXVzIjogMzkuNjAyNTkzNDk5NTY5OTg0LAogICJzdHJva2UiOiB0cnVlLAogICJ3ZWlnaHQiOiAzCn0KICAgICAgICAgICAgICAgICkKICAgICAgICAgICAgICAgIC5hZGRUbyhtYXBfNTViZTczZWIwNzFlNDA2NWI4YTA5N2M2MjRiMmQ0YzApOwogICAgICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgY2lyY2xlX21hcmtlcl9jNzA2M2JhYmM5ZjA0OWNlYTgzMDRjNmFmNDkyOTIwNyA9IEwuY2lyY2xlTWFya2VyKAogICAgICAgICAgICAgICAgWzM3LjYxMjg2MTEsIDEyNi45Mjc0OTUxXSwKICAgICAgICAgICAgICAgIHsKICAiYnViYmxpbmdNb3VzZUV2ZW50cyI6IHRydWUsCiAgImNvbG9yIjogIiMzMTg2Y2MiLAogICJkYXNoQXJyYXkiOiBudWxsLAogICJkYXNoT2Zmc2V0IjogbnVsbCwKICAiZmlsbCI6IHRydWUsCiAgImZpbGxDb2xvciI6ICIjMzE4NmNjIiwKICAiZmlsbE9wYWNpdHkiOiAwLjIsCiAgImZpbGxSdWxlIjogImV2ZW5vZGQiLAogICJsaW5lQ2FwIjogInJvdW5kIiwKICAibGluZUpvaW4iOiAicm91bmQiLAogICJvcGFjaXR5IjogMS4wLAogICJyYWRpdXMiOiAxMC4yMzk5NTg1NTQ2MDYyODIsCiAgInN0cm9rZSI6IHRydWUsCiAgIndlaWdodCI6IDMKfQogICAgICAgICAgICAgICAgKQogICAgICAgICAgICAgICAgLmFkZFRvKG1hcF81NWJlNzNlYjA3MWU0MDY1YjhhMDk3YzYyNGIyZDRjMCk7CiAgICAgICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBjaXJjbGVfbWFya2VyXzM0MTVmM2EzODA2NjQ3NWE4YmM4ZDc4OTBmOGNjMzQyID0gTC5jaXJjbGVNYXJrZXIoCiAgICAgICAgICAgICAgICBbMzcuNjM3MzAzOTAwMDAwMDEsIDEyNy4wMjczMzk5XSwKICAgICAgICAgICAgICAgIHsKICAiYnViYmxpbmdNb3VzZUV2ZW50cyI6IHRydWUsCiAgImNvbG9yIjogIiMzMTg2Y2MiLAogICJkYXNoQXJyYXkiOiBudWxsLAogICJkYXNoT2Zmc2V0IjogbnVsbCwKICAiZmlsbCI6IHRydWUsCiAgImZpbGxDb2xvciI6ICIjMzE4NmNjIiwKICAiZmlsbE9wYWNpdHkiOiAwLjIsCiAgImZpbGxSdWxlIjogImV2ZW5vZGQiLAogICJsaW5lQ2FwIjogInJvdW5kIiwKICAibGluZUpvaW4iOiAicm91bmQiLAogICJvcGFjaXR5IjogMS4wLAogICJyYWRpdXMiOiAyOS41MzcxMDIyMDUxOTE1MiwKICAic3Ryb2tlIjogdHJ1ZSwKICAid2VpZ2h0IjogMwp9CiAgICAgICAgICAgICAgICApCiAgICAgICAgICAgICAgICAuYWRkVG8obWFwXzU1YmU3M2ViMDcxZTQwNjViOGEwOTdjNjI0YjJkNGMwKTsKICAgICAgICAgICAgCiAgICAKICAgICAgICAgICAgdmFyIGNpcmNsZV9tYXJrZXJfOGVjOWRjZTFkODM1NDY4M2E1NTQwMzU1ZmYzNGZiNjMgPSBMLmNpcmNsZU1hcmtlcigKICAgICAgICAgICAgICAgIFszNy40NTY4NzIyLCAxMjYuODk3MDQyOV0sCiAgICAgICAgICAgICAgICB7CiAgImJ1YmJsaW5nTW91c2VFdmVudHMiOiB0cnVlLAogICJjb2xvciI6ICIjMzE4NmNjIiwKICAiZGFzaEFycmF5IjogbnVsbCwKICAiZGFzaE9mZnNldCI6IG51bGwsCiAgImZpbGwiOiB0cnVlLAogICJmaWxsQ29sb3IiOiAiIzMxODZjYyIsCiAgImZpbGxPcGFjaXR5IjogMC4yLAogICJmaWxsUnVsZSI6ICJldmVub2RkIiwKICAibGluZUNhcCI6ICJyb3VuZCIsCiAgImxpbmVKb2luIjogInJvdW5kIiwKICAib3BhY2l0eSI6IDEuMCwKICAicmFkaXVzIjogMjMuNTMyMDY4NTE3NjEyNTcyLAogICJzdHJva2UiOiB0cnVlLAogICJ3ZWlnaHQiOiAzCn0KICAgICAgICAgICAgICAgICkKICAgICAgICAgICAgICAgIC5hZGRUbyhtYXBfNTViZTczZWIwNzFlNDA2NWI4YTA5N2M2MjRiMmQ0YzApOwogICAgICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgY2lyY2xlX21hcmtlcl9jY2FjNGEyOWM3ODM0MDk5YmYyNGY3MmRiMzZmOWExNyA9IEwuY2lyY2xlTWFya2VyKAogICAgICAgICAgICAgICAgWzM3LjYxODY5MiwgMTI3LjEwNDcxMzZdLAogICAgICAgICAgICAgICAgewogICJidWJibGluZ01vdXNlRXZlbnRzIjogdHJ1ZSwKICAiY29sb3IiOiAiIzMxODZjYyIsCiAgImRhc2hBcnJheSI6IG51bGwsCiAgImRhc2hPZmZzZXQiOiBudWxsLAogICJmaWxsIjogdHJ1ZSwKICAiZmlsbENvbG9yIjogIiMzMTg2Y2MiLAogICJmaWxsT3BhY2l0eSI6IDAuMiwKICAiZmlsbFJ1bGUiOiAiZXZlbm9kZCIsCiAgImxpbmVDYXAiOiAicm91bmQiLAogICJsaW5lSm9pbiI6ICJyb3VuZCIsCiAgIm9wYWNpdHkiOiAxLjAsCiAgInJhZGl1cyI6IDM0LjA3NDIyOTM2ODc0MTM3LAogICJzdHJva2UiOiB0cnVlLAogICJ3ZWlnaHQiOiAzCn0KICAgICAgICAgICAgICAgICkKICAgICAgICAgICAgICAgIC5hZGRUbyhtYXBfNTViZTczZWIwNzFlNDA2NWI4YTA5N2M2MjRiMmQ0YzApOwogICAgICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgY2lyY2xlX21hcmtlcl9iMTZmNTQzZGE2YTA0MWM3YmI1MGVhMDlhNTM4ODc3OSA9IEwuY2lyY2xlTWFya2VyKAogICAgICAgICAgICAgICAgWzM3LjUwOTQzNTIsIDEyNy4wNjY5NTc4XSwKICAgICAgICAgICAgICAgIHsKICAiYnViYmxpbmdNb3VzZUV2ZW50cyI6IHRydWUsCiAgImNvbG9yIjogIiMzMTg2Y2MiLAogICJkYXNoQXJyYXkiOiBudWxsLAogICJkYXNoT2Zmc2V0IjogbnVsbCwKICAiZmlsbCI6IHRydWUsCiAgImZpbGxDb2xvciI6ICIjMzE4NmNjIiwKICAiZmlsbE9wYWNpdHkiOiAwLjIsCiAgImZpbGxSdWxlIjogImV2ZW5vZGQiLAogICJsaW5lQ2FwIjogInJvdW5kIiwKICAibGluZUpvaW4iOiAicm91bmQiLAogICJvcGFjaXR5IjogMS4wLAogICJyYWRpdXMiOiAzMS4xNzc4MDc3NjUxNzU3MTQsCiAgInN0cm9rZSI6IHRydWUsCiAgIndlaWdodCI6IDMKfQogICAgICAgICAgICAgICAgKQogICAgICAgICAgICAgICAgLmFkZFRvKG1hcF81NWJlNzNlYjA3MWU0MDY1YjhhMDk3YzYyNGIyZDRjMCk7CiAgICAgICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBjaXJjbGVfbWFya2VyX2VkOWM2N2EzMzg5ZjRjZDFiNDRlYTY0ZWRlYjVmYmFmID0gTC5jaXJjbGVNYXJrZXIoCiAgICAgICAgICAgICAgICBbMzcuNDc0Mzc4OSwgMTI2Ljk1MDk3NDhdLAogICAgICAgICAgICAgICAgewogICJidWJibGluZ01vdXNlRXZlbnRzIjogdHJ1ZSwKICAiY29sb3IiOiAiIzMxODZjYyIsCiAgImRhc2hBcnJheSI6IG51bGwsCiAgImRhc2hPZmZzZXQiOiBudWxsLAogICJmaWxsIjogdHJ1ZSwKICAiZmlsbENvbG9yIjogIiMzMTg2Y2MiLAogICJmaWxsT3BhY2l0eSI6IDAuMiwKICAiZmlsbFJ1bGUiOiAiZXZlbm9kZCIsCiAgImxpbmVDYXAiOiAicm91bmQiLAogICJsaW5lSm9pbiI6ICJyb3VuZCIsCiAgIm9wYWNpdHkiOiAxLjAsCiAgInJhZGl1cyI6IDM2LjM5NzQyNzYzMjA1NDIsCiAgInN0cm9rZSI6IHRydWUsCiAgIndlaWdodCI6IDMKfQogICAgICAgICAgICAgICAgKQogICAgICAgICAgICAgICAgLmFkZFRvKG1hcF81NWJlNzNlYjA3MWU0MDY1YjhhMDk3YzYyNGIyZDRjMCk7CiAgICAgICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBjaXJjbGVfbWFya2VyX2I1NjU4NTYwYWMxZTRkZWZiNGM1YTM3MGU2YTY0N2NjID0gTC5jaXJjbGVNYXJrZXIoCiAgICAgICAgICAgICAgICBbMzcuNTM5NzgyNywgMTI2LjgyOTk5NjhdLAogICAgICAgICAgICAgICAgewogICJidWJibGluZ01vdXNlRXZlbnRzIjogdHJ1ZSwKICAiY29sb3IiOiAiIzMxODZjYyIsCiAgImRhc2hBcnJheSI6IG51bGwsCiAgImRhc2hPZmZzZXQiOiBudWxsLAogICJmaWxsIjogdHJ1ZSwKICAiZmlsbENvbG9yIjogIiMzMTg2Y2MiLAogICJmaWxsT3BhY2l0eSI6IDAuMiwKICAiZmlsbFJ1bGUiOiAiZXZlbm9kZCIsCiAgImxpbmVDYXAiOiAicm91bmQiLAogICJsaW5lSm9pbiI6ICJyb3VuZCIsCiAgIm9wYWNpdHkiOiAxLjAsCiAgInJhZGl1cyI6IDM4LjQ0NTY2Njk4NTM1MzEsCiAgInN0cm9rZSI6IHRydWUsCiAgIndlaWdodCI6IDMKfQogICAgICAgICAgICAgICAgKQogICAgICAgICAgICAgICAgLmFkZFRvKG1hcF81NWJlNzNlYjA3MWU0MDY1YjhhMDk3YzYyNGIyZDRjMCk7CiAgICAgICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBjaXJjbGVfbWFya2VyX2ZkNDM0NmQ4NDY0ODRiNmE5YTRiNTI2ODVmZjVhOTg0ID0gTC5jaXJjbGVNYXJrZXIoCiAgICAgICAgICAgICAgICBbMzcuNTI4NTExLCAxMjcuMTI2ODIyNF0sCiAgICAgICAgICAgICAgICB7CiAgImJ1YmJsaW5nTW91c2VFdmVudHMiOiB0cnVlLAogICJjb2xvciI6ICIjMzE4NmNjIiwKICAiZGFzaEFycmF5IjogbnVsbCwKICAiZGFzaE9mZnNldCI6IG51bGwsCiAgImZpbGwiOiB0cnVlLAogICJmaWxsQ29sb3IiOiAiIzMxODZjYyIsCiAgImZpbGxPcGFjaXR5IjogMC4yLAogICJmaWxsUnVsZSI6ICJldmVub2RkIiwKICAibGluZUNhcCI6ICJyb3VuZCIsCiAgImxpbmVKb2luIjogInJvdW5kIiwKICAib3BhY2l0eSI6IDEuMCwKICAicmFkaXVzIjogMjQuMzI3MjUzMzQyODY5ODg4LAogICJzdHJva2UiOiB0cnVlLAogICJ3ZWlnaHQiOiAzCn0KICAgICAgICAgICAgICAgICkKICAgICAgICAgICAgICAgIC5hZGRUbyhtYXBfNTViZTczZWIwNzFlNDA2NWI4YTA5N2M2MjRiMmQ0YzApOwogICAgICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgY2lyY2xlX21hcmtlcl80NzdmOTBkMmZhZTY0NTY0YmQ2MDM3MWZkMGJjZDQ4MiA9IEwuY2lyY2xlTWFya2VyKAogICAgICAgICAgICAgICAgWzM3LjYwMjA1OTIsIDEyNy4wMzIxNTc3XSwKICAgICAgICAgICAgICAgIHsKICAiYnViYmxpbmdNb3VzZUV2ZW50cyI6IHRydWUsCiAgImNvbG9yIjogIiMzMTg2Y2MiLAogICJkYXNoQXJyYXkiOiBudWxsLAogICJkYXNoT2Zmc2V0IjogbnVsbCwKICAiZmlsbCI6IHRydWUsCiAgImZpbGxDb2xvciI6ICIjMzE4NmNjIiwKICAiZmlsbE9wYWNpdHkiOiAwLjIsCiAgImZpbGxSdWxlIjogImV2ZW5vZGQiLAogICJsaW5lQ2FwIjogInJvdW5kIiwKICAibGluZUpvaW4iOiAicm91bmQiLAogICJvcGFjaXR5IjogMS4wLAogICJyYWRpdXMiOiAxMS4yMzYzMTc5NTcyNTMyNiwKICAic3Ryb2tlIjogdHJ1ZSwKICAid2VpZ2h0IjogMwp9CiAgICAgICAgICAgICAgICApCiAgICAgICAgICAgICAgICAuYWRkVG8obWFwXzU1YmU3M2ViMDcxZTQwNjViOGEwOTdjNjI0YjJkNGMwKTsKICAgICAgICAgICAgCiAgICAKICAgICAgICAgICAgdmFyIGNpcmNsZV9tYXJrZXJfZTE2ZGJlYjMyMmE5NDMwMDhkNWIyMDNjNzZjYjIxYmEgPSBMLmNpcmNsZU1hcmtlcigKICAgICAgICAgICAgICAgIFszNy40OTQ5MzEsIDEyNi44ODY3MzFdLAogICAgICAgICAgICAgICAgewogICJidWJibGluZ01vdXNlRXZlbnRzIjogdHJ1ZSwKICAiY29sb3IiOiAiIzMxODZjYyIsCiAgImRhc2hBcnJheSI6IG51bGwsCiAgImRhc2hPZmZzZXQiOiBudWxsLAogICJmaWxsIjogdHJ1ZSwKICAiZmlsbENvbG9yIjogIiMzMTg2Y2MiLAogICJmaWxsT3BhY2l0eSI6IDAuMiwKICAiZmlsbFJ1bGUiOiAiZXZlbm9kZCIsCiAgImxpbmVDYXAiOiAicm91bmQiLAogICJsaW5lSm9pbiI6ICJyb3VuZCIsCiAgIm9wYWNpdHkiOiAxLjAsCiAgInJhZGl1cyI6IDMxLjA0NTUxNTU2NjYwMTQzOCwKICAic3Ryb2tlIjogdHJ1ZSwKICAid2VpZ2h0IjogMwp9CiAgICAgICAgICAgICAgICApCiAgICAgICAgICAgICAgICAuYWRkVG8obWFwXzU1YmU3M2ViMDcxZTQwNjViOGEwOTdjNjI0YjJkNGMwKTsKICAgICAgICAgICAgCiAgICAKICAgICAgICAgICAgdmFyIGNpcmNsZV9tYXJrZXJfZmIwZGZhN2Q2M2ZlNDMyZWEyNjI0ZmYxOWQ1N2Q5YWMgPSBMLmNpcmNsZU1hcmtlcigKICAgICAgICAgICAgICAgIFszNy40OTU2MDU0LCAxMjcuMDA1MjUwNF0sCiAgICAgICAgICAgICAgICB7CiAgImJ1YmJsaW5nTW91c2VFdmVudHMiOiB0cnVlLAogICJjb2xvciI6ICIjMzE4NmNjIiwKICAiZGFzaEFycmF5IjogbnVsbCwKICAiZGFzaE9mZnNldCI6IG51bGwsCiAgImZpbGwiOiB0cnVlLAogICJmaWxsQ29sb3IiOiAiIzMxODZjYyIsCiAgImZpbGxPcGFjaXR5IjogMC4yLAogICJmaWxsUnVsZSI6ICJldmVub2RkIiwKICAibGluZUNhcCI6ICJyb3VuZCIsCiAgImxpbmVKb2luIjogInJvdW5kIiwKICAib3BhY2l0eSI6IDEuMCwKICAicmFkaXVzIjogMjUuNTg0MzE4NDQ2NzQ5NzM0LAogICJzdHJva2UiOiB0cnVlLAogICJ3ZWlnaHQiOiAzCn0KICAgICAgICAgICAgICAgICkKICAgICAgICAgICAgICAgIC5hZGRUbyhtYXBfNTViZTczZWIwNzFlNDA2NWI4YTA5N2M2MjRiMmQ0YzApOwogICAgICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgY2lyY2xlX21hcmtlcl83MjdhYjM3OTQ5MTU0MDIzOTY0NTI4NTVjZWI0MjAxZiA9IEwuY2lyY2xlTWFya2VyKAogICAgICAgICAgICAgICAgWzM3LjUxNjU2NjcsIDEyNi44NjU2NzYzXSwKICAgICAgICAgICAgICAgIHsKICAiYnViYmxpbmdNb3VzZUV2ZW50cyI6IHRydWUsCiAgImNvbG9yIjogIiMzMTg2Y2MiLAogICJkYXNoQXJyYXkiOiBudWxsLAogICJkYXNoT2Zmc2V0IjogbnVsbCwKICAiZmlsbCI6IHRydWUsCiAgImZpbGxDb2xvciI6ICIjMzE4NmNjIiwKICAiZmlsbE9wYWNpdHkiOiAwLjIsCiAgImZpbGxSdWxlIjogImV2ZW5vZGQiLAogICJsaW5lQ2FwIjogInJvdW5kIiwKICAibGluZUpvaW4iOiAicm91bmQiLAogICJvcGFjaXR5IjogMS4wLAogICJyYWRpdXMiOiAyMS42ODk2NTA1MjI3OTg1MzQsCiAgInN0cm9rZSI6IHRydWUsCiAgIndlaWdodCI6IDMKfQogICAgICAgICAgICAgICAgKQogICAgICAgICAgICAgICAgLmFkZFRvKG1hcF81NWJlNzNlYjA3MWU0MDY1YjhhMDk3YzYyNGIyZDRjMCk7CiAgICAgICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBjaXJjbGVfbWFya2VyX2QyNGUwZDg1MTBhMDQ1OTg4Nzg1NmJiMTMwY2Y2NWU2ID0gTC5jaXJjbGVNYXJrZXIoCiAgICAgICAgICAgICAgICBbMzcuNTAxOTA2NSwgMTI3LjEyNzE1MTNdLAogICAgICAgICAgICAgICAgewogICJidWJibGluZ01vdXNlRXZlbnRzIjogdHJ1ZSwKICAiY29sb3IiOiAiIzMxODZjYyIsCiAgImRhc2hBcnJheSI6IG51bGwsCiAgImRhc2hPZmZzZXQiOiBudWxsLAogICJmaWxsIjogdHJ1ZSwKICAiZmlsbENvbG9yIjogIiMzMTg2Y2MiLAogICJmaWxsT3BhY2l0eSI6IDAuMiwKICAiZmlsbFJ1bGUiOiAiZXZlbm9kZCIsCiAgImxpbmVDYXAiOiAicm91bmQiLAogICJsaW5lSm9pbiI6ICJyb3VuZCIsCiAgIm9wYWNpdHkiOiAxLjAsCiAgInJhZGl1cyI6IDM3LjYzNTk4MjAzNjQ4NzY2LAogICJzdHJva2UiOiB0cnVlLAogICJ3ZWlnaHQiOiAzCn0KICAgICAgICAgICAgICAgICkKICAgICAgICAgICAgICAgIC5hZGRUbyhtYXBfNTViZTczZWIwNzFlNDA2NWI4YTA5N2M2MjRiMmQ0YzApOwogICAgICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgY2lyY2xlX21hcmtlcl8zZTcwZTI0MDJhZGY0ZmIyOTNlYzczYjdiZmZlYmI4MiA9IEwuY2lyY2xlTWFya2VyKAogICAgICAgICAgICAgICAgWzM3LjY0MjM2MDUsIDEyNy4wNzE0MDI3XSwKICAgICAgICAgICAgICAgIHsKICAiYnViYmxpbmdNb3VzZUV2ZW50cyI6IHRydWUsCiAgImNvbG9yIjogIiMzMTg2Y2MiLAogICJkYXNoQXJyYXkiOiBudWxsLAogICJkYXNoT2Zmc2V0IjogbnVsbCwKICAiZmlsbCI6IHRydWUsCiAgImZpbGxDb2xvciI6ICIjMzE4NmNjIiwKICAiZmlsbE9wYWNpdHkiOiAwLjIsCiAgImZpbGxSdWxlIjogImV2ZW5vZGQiLAogICJsaW5lQ2FwIjogInJvdW5kIiwKICAibGluZUpvaW4iOiAicm91bmQiLAogICJvcGFjaXR5IjogMS4wLAogICJyYWRpdXMiOiAzMC4wNjI1MjYxMDU0MjgzMDgsCiAgInN0cm9rZSI6IHRydWUsCiAgIndlaWdodCI6IDMKfQogICAgICAgICAgICAgICAgKQogICAgICAgICAgICAgICAgLmFkZFRvKG1hcF81NWJlNzNlYjA3MWU0MDY1YjhhMDk3YzYyNGIyZDRjMCk7CiAgICAgICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBjaXJjbGVfbWFya2VyXzIzYTQ0MDYwNmQzNjQ1NmViYTk0MTZmOWNlZTdjMzhjID0gTC5jaXJjbGVNYXJrZXIoCiAgICAgICAgICAgICAgICBbMzcuNDk0NTk1OSwgMTI2Ljk4MzEyNzldLAogICAgICAgICAgICAgICAgewogICJidWJibGluZ01vdXNlRXZlbnRzIjogdHJ1ZSwKICAiY29sb3IiOiAiIzMxODZjYyIsCiAgImRhc2hBcnJheSI6IG51bGwsCiAgImRhc2hPZmZzZXQiOiBudWxsLAogICJmaWxsIjogdHJ1ZSwKICAiZmlsbENvbG9yIjogIiMzMTg2Y2MiLAogICJmaWxsT3BhY2l0eSI6IDAuMiwKICAiZmlsbFJ1bGUiOiAiZXZlbm9kZCIsCiAgImxpbmVDYXAiOiAicm91bmQiLAogICJsaW5lSm9pbiI6ICJyb3VuZCIsCiAgIm9wYWNpdHkiOiAxLjAsCiAgInJhZGl1cyI6IDcuNDMzMjUwODY0ODU3MTcyLAogICJzdHJva2UiOiB0cnVlLAogICJ3ZWlnaHQiOiAzCn0KICAgICAgICAgICAgICAgICkKICAgICAgICAgICAgICAgIC5hZGRUbyhtYXBfNTViZTczZWIwNzFlNDA2NWI4YTA5N2M2MjRiMmQ0YzApOwogICAgICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgY2lyY2xlX21hcmtlcl85MDU2MGE2OWE5YTg0MjhmYjI3OGZjNDg5OTMzN2M0OCA9IEwuY2lyY2xlTWFya2VyKAogICAgICAgICAgICAgICAgWzM3LjYyODM1OTcsIDEyNi45Mjg3MjI2XSwKICAgICAgICAgICAgICAgIHsKICAiYnViYmxpbmdNb3VzZUV2ZW50cyI6IHRydWUsCiAgImNvbG9yIjogIiMzMTg2Y2MiLAogICJkYXNoQXJyYXkiOiBudWxsLAogICJkYXNoT2Zmc2V0IjogbnVsbCwKICAiZmlsbCI6IHRydWUsCiAgImZpbGxDb2xvciI6ICIjMzE4NmNjIiwKICAiZmlsbE9wYWNpdHkiOiAwLjIsCiAgImZpbGxSdWxlIjogImV2ZW5vZGQiLAogICJsaW5lQ2FwIjogInJvdW5kIiwKICAibGluZUpvaW4iOiAicm91bmQiLAogICJvcGFjaXR5IjogMS4wLAogICJyYWRpdXMiOiAxMy42MzE4ODIxNTA3MzQ5NTMsCiAgInN0cm9rZSI6IHRydWUsCiAgIndlaWdodCI6IDMKfQogICAgICAgICAgICAgICAgKQogICAgICAgICAgICAgICAgLmFkZFRvKG1hcF81NWJlNzNlYjA3MWU0MDY1YjhhMDk3YzYyNGIyZDRjMCk7CiAgICAgICAgICAgIAogICAgCiAgICAgICAgICAgIHZhciBjaXJjbGVfbWFya2VyXzhiZWM2NDUyODkxMDRlMjJhOTkyZTdjN2IxYmRmMzI3ID0gTC5jaXJjbGVNYXJrZXIoCiAgICAgICAgICAgICAgICBbMzcuNjUzMzU4OSwgMTI3LjA1MjY4Ml0sCiAgICAgICAgICAgICAgICB7CiAgImJ1YmJsaW5nTW91c2VFdmVudHMiOiB0cnVlLAogICJjb2xvciI6ICIjMzE4NmNjIiwKICAiZGFzaEFycmF5IjogbnVsbCwKICAiZGFzaE9mZnNldCI6IG51bGwsCiAgImZpbGwiOiB0cnVlLAogICJmaWxsQ29sb3IiOiAiIzMxODZjYyIsCiAgImZpbGxPcGFjaXR5IjogMC4yLAogICJmaWxsUnVsZSI6ICJldmVub2RkIiwKICAibGluZUNhcCI6ICJyb3VuZCIsCiAgImxpbmVKb2luIjogInJvdW5kIiwKICAib3BhY2l0eSI6IDEuMCwKICAicmFkaXVzIjogMTguNzgxMzQwMTgyOTgxODU3LAogICJzdHJva2UiOiB0cnVlLAogICJ3ZWlnaHQiOiAzCn0KICAgICAgICAgICAgICAgICkKICAgICAgICAgICAgICAgIC5hZGRUbyhtYXBfNTViZTczZWIwNzFlNDA2NWI4YTA5N2M2MjRiMmQ0YzApOwogICAgICAgICAgICAKICAgIAogICAgICAgICAgICB2YXIgY2lyY2xlX21hcmtlcl8zNzdkMjg2MWUyMjg0MTU4YTBmNjU0ZWFkYjY2MTU5YiA9IEwuY2lyY2xlTWFya2VyKAogICAgICAgICAgICAgICAgWzM3LjQ5MzQ5LCAxMjcuMDc3MjExOV0sCiAgICAgICAgICAgICAgICB7CiAgImJ1YmJsaW5nTW91c2VFdmVudHMiOiB0cnVlLAogICJjb2xvciI6ICIjMzE4NmNjIiwKICAiZGFzaEFycmF5IjogbnVsbCwKICAiZGFzaE9mZnNldCI6IG51bGwsCiAgImZpbGwiOiB0cnVlLAogICJmaWxsQ29sb3IiOiAiIzMxODZjYyIsCiAgImZpbGxPcGFjaXR5IjogMC4yLAogICJmaWxsUnVsZSI6ICJldmVub2RkIiwKICAibGluZUNhcCI6ICJyb3VuZCIsCiAgImxpbmVKb2luIjogInJvdW5kIiwKICAib3BhY2l0eSI6IDEuMCwKICAicmFkaXVzIjogMjMuNjQxNzMwMTY2NDQyNTE0LAogICJzdHJva2UiOiB0cnVlLAogICJ3ZWlnaHQiOiAzCn0KICAgICAgICAgICAgICAgICkKICAgICAgICAgICAgICAgIC5hZGRUbyhtYXBfNTViZTczZWIwNzFlNDA2NWI4YTA5N2M2MjRiMmQ0YzApOwogICAgICAgICAgICAKPC9zY3JpcHQ+" style="position:absolute;width:100%;height:100%;left:0;top:0;border:none !important;" allowfullscreen webkitallowfullscreen mozallowfullscreen></iframe></div></div>




```python

```
