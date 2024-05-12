//보관함 내 펫 리스트 불러오기
class PetList {
  String? status;
  String? message;
  List<Pet>? data;

  PetList({this.status, this.message, this.data});

  PetList.fromJson(Map<String, dynamic> json) {
    status = json['status'];
    message = json['message'];
    if (json['data'] != null) {
      data = <Pet>[];
      json['data'].forEach((v) {
        data!.add(Pet.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['status'] = status;
    data['message'] = message;
    if (this.data != null) {
      data['data'] = this.data!.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class Pet {
  bool? isHave;
  Pets? pets;

  Pet({this.isHave, this.pets});

  Pet.fromJson(Map<String, dynamic> json) {
    isHave = json['isHave'];
    pets = json['pets'] != null ? Pets.fromJson(json['pets']) : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['isHave'] = isHave;
    if (pets != null) {
      data['pets'] = pets!.toJson();
    }
    return data;
  }
}

class Pets {
  int? id;
  String? name;
  int? affection;
  String? tier;
  bool? isPartner;
  int? price;

  Pets(
      {this.id,
      this.name,
      this.affection,
      this.tier,
      this.isPartner,
      this.price});

  Pets.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'];
    affection = json['affection'];
    tier = json['tier'];
    isPartner = json['isPartner'];
    price = json['price'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['name'] = name;
    data['affection'] = affection;
    data['tier'] = tier;
    data['isPartner'] = isPartner;
    data['price'] = price;
    return data;
  }
}
