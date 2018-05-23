postscript("HV.Boxplot.eps", horizontal=FALSE, onefile=FALSE, height=8, width=12, pointsize=10)
resultDirectory<-"../data"
qIndicator <- function(indicator, problem)
{
fileSMSEMOA<-paste(resultDirectory, "SMSEMOA", sep="/")
fileSMSEMOA<-paste(fileSMSEMOA, problem, sep="/")
fileSMSEMOA<-paste(fileSMSEMOA, indicator, sep="/")
SMSEMOA<-scan(fileSMSEMOA)

filePAES<-paste(resultDirectory, "PAES", sep="/")
filePAES<-paste(filePAES, problem, sep="/")
filePAES<-paste(filePAES, indicator, sep="/")
PAES<-scan(filePAES)

algs<-c("SMSEMOA","PAES")
boxplot(SMSEMOA,PAES,names=algs, notch = FALSE)
titulo <-paste(indicator, problem, sep=":")
title(main=titulo)
}
par(mfrow=c(1,1))
indicator<-"HV"
qIndicator(indicator, "MyProblemIntegerExternalViaJAR")
