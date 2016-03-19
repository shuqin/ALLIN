class GoodsPrice extends React.Component {
  render() {
    return <font color="red">{this.props.price}</font>;
  }
}

class GoodsTitle extends React.Component {
  render() {
    return <p>{this.props.title}</p>;
  }
}

class GoodsDesc extends React.Component {
  render() {
    return <p>{this.props.desc}</p>;
  }
}

class GoodsService extends React.Component {
  render() {
    return <p>服务: {this.props.service}</p>;
  }
}

class GoodsChoice extends React.Component {
  render() {
    return <p>选择：{this.props.choice}</p>;
  }
}

class BookButton extends React.Component {
  constructor(props) {
    super(props);
    this.handleClick = this.handleClick.bind(this);
  }

  handleClick() {
    /*
    $.ajax({
      async: false,
      type : "get",
      url : "/api/creatives/list",
      data: {},
      datatype : 'json',
      success : function(data) {

		  alert(data);

      }.bind(this),

	  error: function(jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + jqXHR.responseText);
	  }
    });*/
  }

  render() {
    return <button onClick={this.handleClick}>下单</button>
  }

}

function App(props) {
    return (
        <div>
            <GoodsPrice price={props.price}/>
            <GoodsTitle title={props.title}/>
            <GoodsDesc  desc={props.desc}/>
            <GoodsService service={props.service}/>
            <GoodsChoice choice={props.choice}/>
            <BookButton />
        </div>
    );
}

ReactDOM.render(
    <App price={"￥89"}
         title={"认养一头牛法式原味酸奶200g*12盒*2箱"}
         desc={"法国益生菌发酵/生牛乳发酵/优质奶源/浓郁醇厚/日期新鲜"}
         service={"收货后结算 * 支持退换 * 快递发货"}
         choice={"200g*12盒*2箱"}/>,
    document.getElementById('example')
);