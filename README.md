## Tóm lược về Tink

---
Tink là một thư viện mã hóa open-source, được viết bởi các kỹ sư và chuyên gia bảo mật
của Google. Tink hỗ trợ nhiều ngôn ngữ (Java/Android, C++, Obj-C, Go, Python), cung cấp các API
đơn giản giúp người dùng có thể sử dụng, mà không cần phải có kiến thức sâu rộng về mã hóa.

Tink cung cấp `newKey(...)` method để tạo mới một key, nhưng để tránh việc lộ thông tin
nhạy cảm liên quan đến key, mình không nên để code gen key và code sử dụng key ở cùng một nơi.
Tốt hơn ta có thể sử dụng [Tinkey](https://developers.google.com/tink/tinkey-overview) - một cmd
line utility giúp thực hiện các tác vụ cơ bản liên quan đến key.

Tink cũng hỗ trợ plugin `tink-awskms` cho AWS KMS và `tink-gcpkms` cho Google Cloud KMS, giúp xài
Tink cùng với Key Management System (KMS).

Quản lý key với KMS được khuyến nghị nên dùng. Nhưng tất
nhiên bạn cũng có thể lưu chúng trên thiết bị của bạn. Keyset có thể lưu dưới dạng file văn bản rõ
ràng nhưng tốt hơn ta nên mã hóa file này.

Tink hỗ trợ đa dạng loại mã hóa, bạn có thể tham khảo
tại [đây](https://developers.google.com/tink/supported-key-types).

## Về Tinkz

Tinkz là một project nhỏ mình viết để đơn giản hóa hơn nữa các API Tink cung cấp.

## References

Ở đây mình liệt kê một số nguồn tham khảo hữu ích.

- https://github.com/google/tink/blob/master/docs/JAVA-HOWTO.md
- https://www.baeldung.com/google-tink
- https://developers.google.com/tink
- https://medium.com/coinmonks/cryptography-with-google-tink-33a70d71918d
- https://latacora.micro.blog/2018/04/03/cryptographic-right-answers.html