class PasswordCheck {
  String? status;
  String? message;
  int? data;

  PasswordCheck({this.status, this.message, this.data});

  PasswordCheck.fromJson(Map<String, dynamic> json) {
    status = json['status'];
    message = json['message'];
    data = json['data'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['status'] = status;
    data['message'] = message;
    data['data'] = data;
    return data;
  }
}
