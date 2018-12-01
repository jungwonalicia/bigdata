factorInput <- read.csv("file:///C:/r-working/Test/factor_test.csv")
factorInput

factorBlood <- factor(factorInput$blood)
factorBlood
summary(factorBlood)

objects() #ls()와 동일 
vvarList <- objects()
rm(vvarList)

varList <- objects()
ls()
varList
objects()

rm(varList)
objects()


totalList <- ls()
rm(totalList)

objects()

rm(list = ls()) #전체 삭제 
objects()
#########################################################

#문자는 따옴표 
class(2018-08-12) #숫자 

class('2018-08-12') #문자 

class("2018-08-12")  #문자 


#
sum(1,2,3) #더하기 

sum(1, NA, 2) # NA가 끼어 있어서 연산 불가 

sum(1,NULL, 2) # 대문자NULL인 경우, 무시하고 연산 

x <- c(4, NULL, 8)
mean(x)

sum(1, NA, 2, na.rm = TRUE) #NA인 경우, 무시하고 연산하라고 설정 


################################################
################################################

address <- c("서울", "대구", "부산", "군산")
address
summary(address)
summary(as.factor(address))
class(address) #변수 타입 프린트 
str(address) #변수 타입과 값 프린트 


#
date1 <- '2018-08-15'
class(date1)
substr(date1, 4, 6) #인덱스는 1부터 사용 

date2 <- as.POSIXct('2018-08-15')
date3 <- as.POSIXct('2018-07-15')

date2-date3

#
install.packages("lubridate")

library(lubridate)
date <- now()
year(date)
month(date)
day(date)
wday(date) #0=일요일 
wday(date, label = T)

#
date <- now()
wday(date, label = F)
date <- date - days(2)
date

date <- date + years(1)
date

date <- date + months(1)
date

date <- date + hours(1)
date

date <- date + minutes(1)
date

date <- date + seconds(1)
date

date <- hm("22:30")
date

date <- hms("22:30:15")
date

