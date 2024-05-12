// 아이템 구매
class ItemPurchase {
  String? status;
  String? message;
  Item? data;

  ItemPurchase({this.status, this.message, this.data});

  ItemPurchase.fromJson(Map<String, dynamic> json) {
    status = json['status'];
    message = json['message'];
    data = json['data'] != null ? Item.fromJson(json['data']) : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['status'] = status;
    data['message'] = message;
    if (this.data != null) {
      data['data'] = this.data!.toJson();
    }
    return data;
  }
}

class Item {
  int? id;
  String? category;
  bool? isEquip;
  bool? isHave;

  Item({this.id, this.category, this.isEquip, this.isHave});

  Item.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    category = json['category'];
    isEquip = json['isEquip'];
    isHave = json['isHave'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['category'] = category;
    data['isEquip'] = isEquip;
    data['isHave'] = isHave;
    return data;
  }
}
