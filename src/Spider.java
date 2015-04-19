
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.table.DefaultTableModel;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.filters.StringFilter;
import org.htmlparser.nodes.RemarkNode;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.Bullet;
import org.htmlparser.tags.BulletList;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.FormTag;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.Span;
import org.htmlparser.tags.TitleTag;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.mongodb.DBCursor;

public class Spider implements Runnable {
	int total = 0;

	boolean search_key_words = false;
	int count = 0;
	int limitsite = 10;
	int countsite = 1;
	String keyword = "movie";
	Parser parser = new Parser();
	String result[]=new String[3];
	
	String startsite = "";
	SearchResultBean srb;
	List<SearchResultBean> resultlist = new ArrayList<SearchResultBean>();
	List<String> searchedsite = new ArrayList<String>();
	Queue<String> linklist = new LinkedList<String>();
	HashMap<String, ArrayList<String>> disallowListCache = new HashMap<String, ArrayList<String>>();
	private int totalNum = 1;

	public Spider(String keyword, String startsite) {
		this.keyword = keyword;
		this.startsite = startsite;
		linklist.add(startsite);
		srb = new SearchResultBean();
		result[2]=startsite;
	}

	public void run() {

		search(linklist);
	}
	public String[] getResult(){
	
		return result;
	}

	public void search(Queue<String> queue) {
		String url = "";
		while (!queue.isEmpty()) {
			url = queue.peek().toString();
			try {
				if (!isSearched(searchedsite, url)) {
				
						processHtml(url);
	
				}
			} catch (Exception ex) {
				//System.out.print(ex);
			}
			queue.remove();

		}

	}


	public void processHtml(String url) throws ParserException, Exception {
		searchedsite.add(url);
		count = 0;
		// System.out.println("searching ... :" + url);
		saveContent("D:/temp/", url);
		parser.setURL(url);
		parser.setEncoding("GBK");
		URLConnection uc = parser.getConnection();
		uc.connect();
		// uc.getLastModified();
		NodeIterator nit = parser.elements();

		while (nit.hasMoreNodes()) {
			Node node = nit.nextNode();
			parserNode(node);
		}
		srb.setKeywords(keyword);
		srb.setUrl(url);
		srb.setCount_key_words(count);
		resultlist.add(srb);
		// System.out.println("count keywords is :" + count);
		// System.out.println("----------------------------------------------");
	}

	private void saveContent(String path, String url) {
		// TODO Auto-generated method stub
		try {
			URL urlStd = new URL(url);
			BufferedReader br = new BufferedReader(new InputStreamReader(urlStd
					.openStream()));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(
					new FileOutputStream(path + "New" + totalNum
							+ ".html")), true);
			String in = null;
			while ((in = br.readLine()) != null) {
				pw.println(in);
				// System.out.println(in);
			}
			totalNum++;
			pw.close();
			br.close();

		} catch (MalformedURLException x) {
			System.out.println("address error");
		} catch (IOException xx) {
			//System.out.println("Write error");
		}

	}


	public void dealTag(Tag tag) throws Exception {
		NodeList list = tag.getChildren();
		if (list != null) {
			NodeIterator it = list.elements();
			while (it.hasMoreNodes()) {
				Node node = it.nextNode();
				parserNode(node);
			}
		}
	}

	
	public void parserNode(Node node) throws Exception {
	    
		if (node instanceof TextNode) {
			TextNode sNode = (TextNode) node;
			StringFilter sf = new StringFilter(keyword, false);
			search_key_words = sf.accept(sNode);
			if (search_key_words) {
				count++;
			}

			String text = sNode.getText().trim();
			// if (text.equals("product£º") || text.equals("product:")) {
			// String name = sNode.getNextSibling().getText().trim();
			// Node next = null;
			// while (!name.equals("product:")) {
			// System.out.println(name);
			// next = next.getNextSibling();
			// name = next.getText().trim();
			// }
			// }
			if (text != "" && text != null && text.length() > 0) {
				// System.out.println("TextNode : " + text);
				// if(text.equals("Second:")){
				// status = 0;
				// } else if(text.equals("New:")){
				// status = 1;
				// }
			}
		} else if (node instanceof Tag) {
			Tag atag = (Tag) node;
			if (atag.getRawTagName().matches("body")) {
//				NodeList TL = atag.getChildren();
//				Div Body = null;
//				for (int i = 0; i < TL.size(); i++) {
//					if (TL.elementAt(i) instanceof Div) {
//						Div TT = (Div) TL.elementAt(i);
//						String id=TT.getAttribute("id");
////						System.out.println(id);
////						System.out.println(i);
//						
//						String Body1="Body";
////						if(id==Body1)
////							{System.out.println("123");}
////						else{
////							System.out.println("321");
////						}
////						if(TT.getAttribute("id").matches("Body"))
////						{
////							System.out.println(TT.getAttribute("id"));
////						}
//
//					}
//					Body=(Div)TL.elementAt(9);
//				}
//				Div Body2 = (Div) this.findNextNode(Body, "id", "LeftCenterBottomPanelDF");
//				Div CenterWrapper=(Div)this.findNextNode(Body2, "id", "CenterWrapper");
//				Div Center=(Div)this.findNextNode(CenterWrapper, "id","Center");
//				Div CenterPanel=(Div)Center.getChild(1);
//				Div CenterPanelInner=(Div)this.findNextNode(CenterPanel, "id","CenterPanelInner");
//				Div Results=(Div)this.findNextNode(CenterPanelInner, "id","Results");
//				Div rsTabs=(Div)this.findNextNode(Results, "class","rst");
//				Div t225=(Div)this.findNextNode(rsTabs, "class","t225");
//				Div e1166=(Div)this.findNextNode(t225, "id","e1-166");
//				Div ResultSetItems=(Div)this.findNextNode(e1166, "id","ResultSetItems");
////			    System.out.println(rsTabs.getChildCount());
////				int tempindex=0;
//				BulletList tempL=null;
//				for (int i = 0; i < ResultSetItems.getChildCount(); i++) {
//					
//					if(ResultSetItems.getChild(i) instanceof BulletList)
//						{
//						
//						BulletList ListView=(BulletList)ResultSetItems.getChild(i);
//						tempL=ListView;
//
//						}
//				}
//				List<String> URLList=new ArrayList<String>();
//				int j=0;
//			for (int i = 0; i < tempL.getChildCount(); i++) {
//				if(j<5)
//				{
//					if(tempL.getChild(i) instanceof Bullet)
//						{
//						j=j+1;
//						Bullet tempB=(Bullet)tempL.getChild(i);
//						for(int k=0;k<tempB.getChildCount();k++)
//						{
//							if(tempB.getChild(k) instanceof Tag)
//							{
//								Tag tempBB=(Tag)tempB.getChild(k);
//								String name=tempBB.getAttribute("class");
//								if(name.matches("lvtitle"))
//								{
//									NodeList finalT=tempBB.getChildren();
//									for(int m=0;m<tempBB.getChildren().size();m++)
//									{
//										if(finalT.elementAt(m) instanceof LinkTag)
//										{
//											LinkTag Url=(LinkTag)finalT.elementAt(m);
//											//System.out.println(Url.getLink());
//											URLList.add(Url.getLink());
//										}
//									}
//								}
//							}
//						}
//
//						}
//				}
//				}
//			for(int i=0;i<URLList.size();i++)
//			{
//				Spider ph = new Spider("?", URLList.get(i));
//				try {
//					// ph.processHtml();
//					Thread search = new Thread(ph);
//					search.start();
//					search.join();
//					System.out.println(ph.result[0]);
//					System.out.println(ph.result[1]);
//					
//				} catch (Exception ex) {
//				}
//			}
           

				
			}
			if (atag instanceof TitleTag) {
				// System.out.println("Title : " + atag.getText());
				srb.setTitle(atag.getText());
			}
			if (atag instanceof LinkTag) {
				LinkTag linkatag = (LinkTag) atag;
				// System.out.println("Link : " + atag.getText());
				checkLink(linkatag.getLink(), linklist);
			}
			if (atag instanceof Div) {
				Div div = (Div) atag;
				//System.out.println(div.getAttribute("id"));
				String id = div.getAttribute("id");
				//String dataFeatureName=div.getAttribute("data-feature-name");
				if (id.matches("Body")) {
				//if (id=="a-page") {
					for (int i = 0; i < div.getChildCount(); i++){
						Node temp=div.getChild(i);
					//	 System.out.println(temp.toHtml());
					//	Node testd=div.getLastChild();
				//		Tag test23=(Tag)testd;
					//	System.out.println("123");
						if(temp instanceof Tag)
						{
							Tag tempTag=(Tag)temp;
							// System.out.println(tempTag.toHtml());
							//System.out.println(tempTag.getTagName());
							  // String tagname1=tempTag.getTagName();
							if (tempTag instanceof Div){
								Div tempDiv=(Div)tempTag;
								String tempId=tempDiv.getAttribute("id");
								//System.out.println(tempId);
//								   if(id.matches(null))
//								   {
//									   Tag Test1=(Tag)tempDiv.getFirstChild();
//									   System.out.println(Test1.getTagName());
//								   }
								if(tempId.matches("CenterPanelDF"))
								{   
								  //System.out.println(tempDiv.getChildCount());
									for(int j=0;j<tempDiv.getChildCount();j++)
									{
										Node tempNode=tempDiv.getChild(j);
									    Tag Test23=(Tag)tempNode;
										Div out=(Div)Test23;
										
										//System.out.println(out.getAttribute("id"));
										if(Test23 instanceof Div)
										{
											Div tempDiv2=(Div)tempNode;
											String TempDivName=tempDiv2.getAttribute("id");
											if(TempDivName.matches("CenterPanel"))
											{
												//System.out.println("sdaa");
												for(int k=0;k<tempDiv2.getChildCount();k++)
												{
													Node tempNodeCentrelCol=tempDiv2.getChild(k);
													if(tempNodeCentrelCol instanceof Div)
													{
														Div cenColDiv=(Div)tempNodeCentrelCol;
														String TempCenDivName=cenColDiv.getAttribute("id");
														//System.out.println(TempCenDivName);
														if(TempCenDivName.matches("CenterPanelInternal")){
															
															for(int m=0;m<cenColDiv.getChildCount();m++)
															{
																//System.out.println(m);
																Node internal=cenColDiv.getChild(m);
																//System.out.println(internal.getText());
															if(internal instanceof Span)
																{
																Span testS=(Span)internal;
																if(testS.getAttribute("id").matches("vi-lkhdr-itmTitl"))
																{
																System.out.println(testS.getStringText());
																result[0]=testS.getStringText();
																}
																}
																

															}
															Node internal=cenColDiv.getChild(13);													
															Div  intag=(Div)internal;
															Div is=(Div)intag.getFirstChild().getNextSibling();
															//System.out.println(is.getAttribute("style"));
															for(int s=1;s<is.getChildCount();s++)
															{
																
																Node isN=is.getChild(s);
																Tag isT=(Tag)isN;
															    FormTag isF=(FormTag)isT;
																if(isT.getTagName().matches("FORM"))
																	{
																	// System.out.println("FORM");
																	  for(int in=1;in<isF.getChildCount();in++){
																		  Node fNode=isF.getChild(in);
																		  if(fNode instanceof Div)
																		  {
																			  Div fDiv=(Div)fNode;
																			  if(fDiv.getAttribute("class").matches("c-std vi-ds3cont-box-marpad"))
																			  {
																				//  System.out.println("c-std vi-ds3cont-box-marpad");
																				  for(int in2=1;in2<fDiv.getChildCount();in2++)
																				  {
																					  Node cNode=fDiv.getChild(in2);
																					  if(cNode instanceof Div)
																					  {
																						  Div cDiv=(Div)cNode;
																						  if(cDiv.getAttribute("class").matches("actPanel  vi-noborder "))
																						  {
																							  int index=0;
																							 // System.out.println("actPanel  vi-noborder ");
																							  int count1=cDiv.getChildCount();
																							  for(int actI=0;actI<count1;actI++)
																							  {
																								 // System.out.println(actI);
																								  Node actN=cDiv.getChild(actI);
																								  if(actN instanceof Div)
																								  {
																									  Div actDT=(Div)actN;
																									  String acTName=actDT.getAttribute("class");
																									  if(acTName!=null&&acTName.equals("u-cb"))
																									  {index=actI;}
																									 
																								  }
																							  }
																							    //System.out.println(index);
																							     Div actD=(Div)cDiv.getChild(index);
																							   //  System.out.println(actD.getAttribute("class"));
																							     for(int in3=0;in3<actD.getChildCount();in3++)
																							     {
																							    	 Node uNode=actD.getChild(in3);
																							    	 if(uNode instanceof Div)
																							    	 {
																							    		 //System.out.println(in3);
																							    		 Div uDiv=(Div)uNode;
																							    		if(uDiv.getAttribute("class").matches("u-flL w29 vi-price"))
																							    		{
																							    			//System.out.println("u-flL w29 vi-price");
																							    			for(int in4=0;in4<uDiv.getChildCount();in4++)
																							    			{
																							    				Node pNode=uDiv.getChild(in4);
																							    				if(pNode instanceof Span)
																							    				{
																							    				   Span pSpan=(Span)pNode;
																							    				   if(pSpan.getAttribute("class").matches("notranslate"))
																							    				   {
																							    					   System.out.println(pSpan.getStringText());
																							    					   result[1]=pSpan.getStringText();
																							    				   }
																							    				   
																							    				}
																							    			}
																							    		}
																							    	 }
																							     }
																						
																						  }
																					  }
																				  }
																			  }
																		  }
																	  }
																	
																	
																	}
															}
															//System.out.println(is.getAttribute("class"));
															//System.out.println(TempCenDivName);
														
															
														
														}
													}
														}
													}
												}
											}
										}
									}
								}
								
							}
				}


			}
			// else if (atag instanceof ImageTag) {
			// ImageTag imageTag = (ImageTag) atag;
			// String imageUrl = imageTag.getImageURL();
			// System.out.println("Image URL : " + imageUrl);
			// }
			dealTag(atag);
		} else if (node instanceof RemarkNode) {
			// System.out.println("this is remark");
		}
	}

    public Object findNextNode(Object div, String Attribute, String key)
    {
    	Div temp=(Div)div;
    	Div result=null;
    	for(int i=0;i<temp.getChildCount();i++)
    	{
    		if(temp.getChild(i) instanceof Div)
    		{
    			Div childD=(Div)temp.getChild(i);
    			//System.out.println(childD.getAttribute(Attribute));
    			if(childD.getAttribute(Attribute).matches(key))
    				result=childD;
    		}
    	}
    	if(result==null)
    	System.out.println("No Div found");
    	return result;
    }
	public void checkLink(String link, Queue<String> queue) {
		if (link != null && !link.equals("") && link.indexOf("#") == -1) {
			if (!link.startsWith("http://") && !link.startsWith("ftp://")
					&& !link.startsWith("www.")) {
				link = "file:///" + link;
			} else if (link.startsWith("www.")) {
				link = "http://" + link;
			}
			if (queue.isEmpty())
				queue.add(link);
			else {
				String link_end_ = link.endsWith("/") ? link.substring(0, link
						.lastIndexOf("/")) : (link + "/");
				if (link.startsWith("http://www.ebay.com/itm/Apple-iPhone-6-Plus-Latest-Model-16GB-Space-Grey-Unlocked-Smartphone-/321689910765?pt=LH_DefaultDomain_0&hash=item4ae6367ded")
						&& !queue.contains(link) && !queue.contains(link_end_)) {
				
					queue.add(link);
				}
			}
		}
	}


	public boolean isSearched(List<String> list, String url) {
		String url_end_ = "";
		if (url.endsWith("/")) {
			url_end_ = url.substring(0, url.lastIndexOf("/"));
		} else {
			url_end_ = url + "/";
		}
		if (list.size() > 0) {
			if (list.indexOf(url) != -1 || list.indexOf(url_end_) != -1) {
				return true;
			}
		}
		return false;
	}


	private boolean isRobotAllowed(URL urlToCheck) {
		String host = urlToCheck.getHost().toLowerCase();

		ArrayList<String> disallowList = disallowListCache.get(host);

		if (disallowList == null) {
			disallowList = new ArrayList<String>();
			try {
				URL robotsFileUrl = new URL("http://" + host + "/robots.txt");
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(robotsFileUrl.openStream()));
				
				String line;
				while ((line = reader.readLine()) != null) {
					if (line.indexOf("Disallow:") == 0) {
						String disallowPath = line.substring("Disallow:"
								.length());
						
						int commentIndex = disallowPath.indexOf("#");
						if (commentIndex != -1) {
							disallowPath = disallowPath.substring(0,
									commentIndex);
						}
						disallowPath = disallowPath.trim();
						disallowList.add(disallowPath);
					}
				}
				for (Iterator<String> it = disallowList.iterator(); it
						.hasNext();) {
					System.out.println("Disallow is :" + it.next());
				}
				
				disallowListCache.put(host, disallowList);
			} catch (Exception e) {
				return true;
			}
		}
		String file = urlToCheck.getFile();
		for (int i = 0; i < disallowList.size(); i++) {
			String disallow = disallowList.get(i);
			if (file.startsWith(disallow)) {
				return false;
			}
		}
		return true;
	}



	public static void main(String[] args) {
		Spider ph = new Spider("?", "http://www.ebay.com/itm/HTC-One-M7-PN07120-32GB-AT-T-Unlocked-Android-Smartphone-Black-Used-/281602625491?pt=LH_DefaultDomain_0&hash=item4190d30fd3");
		try {
			// ph.processHtml();
			Thread search = new Thread(ph);
			search.start();

			
		} catch (Exception ex) {
		}
		
//		DBCursor dbCursor=(DBCursor)DataAccess.FindAll();
//		while(dbCursor.hasNext())System.out.println(dbCursor.next());
//	}
//		List<CellPhone> test=r.SingleQuery("Iphone");
//		Repository r=new Repository();
//		List<CellPhone> test=r.SingleQuery("Iphone");
//		for(CellPhone p:test)
//		{
//			System.out.println(p.getName());
//			System.out.println(p.getPrice());
//			}
//		}
//		UI test=new UI();
//		String[] clomnName={"sd","ads","ads"};
//		String[][] data={{"sd","ads","ads"},{"213","231","asdasd"}};
//		CellPhone t=new CellPhone();
//		t.setName("asd");
//		t.setPrice("sda");
//		t.setURL("sadas");
//		CellPhone t2=new CellPhone();
//		t2.setName("asdsadas22");
//		t2.setPrice("sda222");
//		t2.setURL("sadas333");
//		List<CellPhone> test1=new ArrayList<CellPhone>();
//		test1.add(t);
//		test1.add(t2);
//		test.updateTable(test1);
		

	}	
}