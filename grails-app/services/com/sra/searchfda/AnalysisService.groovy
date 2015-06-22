package com.sra.searchfda

import grails.converters.JSON
import grails.transaction.Transactional

import java.util.zip.ZipEntry
import java.util.zip.ZipFile

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.S3Object

@Transactional
class AnalysisService {

	String bucket="fda-datasets"
	String localDatasets="c:/fda/datasets"
	
	List<Map> datasets=[
		[path:"device/enforcement",group:"recalls"],
		[path:"device/event",group:"events"],
		[path:"drug/enforcement",group:"recalls"],
		[path:"drug/event",group:"events"],
		[path:"drug/label",group:"labels"],
		[path:"food/enforcement",group:"recalls"]
	]
	
	private Map getDataset(String path) {
		return(datasets.grep{it.path==path}.first())
	}
	
	private File getZipFile(String dataset) {
		Map ds=getDataset(dataset)
		String shortname=ds.path.replace("/","_")+".zip"
		String filename=localDatasets+"/"+shortname
		File file=new File(filename)
		if (file.exists()) return(file)
		println("Retrieving data archive...")
		AmazonS3Client s3 = new AmazonS3Client()
		S3Object obj=s3.getObject(bucket,shortname)
		InputStream in0=obj.getObjectContent()
		int bufsize=4096
		byte[] buf=new byte[bufsize]
		int len=-1
		File outfile=new File(filename)
		FileOutputStream fout=new FileOutputStream(outfile)
		while((len=in0.read(buf,0,bufsize))>-1) {
			if (len>0) {
				fout.write(buf,0,len)
			}
		}
		fout.close()
		return(file)
	} 
	
	def test() {
		return(analyzeDataset("food/enforcement"))
	}
	
    def analyzeDataset(String dataset) {
		ZipFile zipFile=new ZipFile(getZipFile(dataset))
		for(ZipEntry entry:zipFile.entries()) {
			println("Processing ${entry.getName()}")
			try {
			def contents=zipFile.getInputStream(entry).text
			println(contents.size())
			def js=JSON.parse(contents)
			println(js.meta.results.total)
			} catch (Exception e) {}
		}
		return("OK")
    }
}
