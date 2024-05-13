class ItemInfo {
  int? setId;
  String? setName;
  List<Item>? item;

  ItemInfo({this.setId, this.setName, this.item});

  ItemInfo.fromJson(Map<String, dynamic> json) {
    setId = json['setId'];
    setName = json['setName'];
    if (json['item'] != null) {
      item = <Item>[];
      json['item'].forEach((v) {
        item!.add(new Item.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['setId'] = this.setId;
    data['setName'] = this.setName;
    if (this.item != null) {
      data['item'] = this.item!.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class Item {
  int? itemId;
  String? itemName;
  String? category;

  Item({this.itemId, this.itemName, this.category});

  Item.fromJson(Map<String, dynamic> json) {
    itemId = json['itemId'];
    itemName = json['itemName'];
    category = json['category'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['itemId'] = this.itemId;
    data['itemName'] = this.itemName;
    data['category'] = this.category;
    return data;
  }
}
