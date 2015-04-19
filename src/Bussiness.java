import java.util.List;


public class Bussiness {

	Repository _re=new Repository();
	public void Ins(String[] value)
	{

		String name=value[0];
		String price=value[1];
		String url=value[2];
		_re.InsertNew(name, price,url);
		
	}
	public List<CellPhone> GetAllInfo()
	{
		return _re.GetAllInfo();
	}
	public List<CellPhone> Search(String keyWord){
		return _re.SingleQuery(keyWord);
	}
}
