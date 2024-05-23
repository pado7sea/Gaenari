// 세트 상세조회
class ItemList {
  String? status;
  String? message;
  Set? data;

  ItemList({this.status, this.message, this.data});

  ItemList.fromJson(Map<String, dynamic> json) {
    status = json['status'];
    message = json['message'];
    data = json['data'] != null ? Set.fromJson(json['data']) : null;
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

class Set {
  int? setId;
  int? itemCnt;
  List<Items>? items;

  Set({this.setId, this.itemCnt, this.items});

  Set.fromJson(Map<String, dynamic> json) {
    setId = json['setId'];
    itemCnt = json['itemCnt'];
    if (json['items'] != null) {
      items = <Items>[];
      json['items'].forEach((v) {
        items!.add(Items.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['setId'] = setId;
    data['itemCnt'] = itemCnt;
    if (items != null) {
      data['items'] = items!.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class Items {
  int? id;
  String? category;
  bool? isEquip;
  bool? isHave;

  Items({this.id, this.category, this.isEquip, this.isHave});

  Items.fromJson(Map<String, dynamic> json) {
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
