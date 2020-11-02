package com.example.a301pro;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.githang.statusbar.StatusBarCompat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


/**
 * Allow user to view all the owned books, search for a book, fetch books by status,
 * add a new book, and edit a existed book
 *
 * current process
 *
 * success:
 * 可以add/edit, 并且更新到数据库和list view
 *
 * fail:
 * 目前还是静态添加book, 还没实现从数据库抓取数据添加到list view,
 * 所以重启app的时候只能显示hardcode上去的book
 *
 * bug:
 * onActivityResult()里的resultCode 有问题, 导致从addEdit点击Back返回出现闪退
 * delete book弹窗问题未修复
 */
public class mybookfragment extends Fragment implements ComfirmDialog.OnFragmentInteractionListenerComfirm {
    ListView bookList;
    ArrayAdapter<Book> bookAdapter;
    ArrayList<Book> bookDataList;

    public static final String Evaluate_DIALOG = "evaluate_dialog";
    public static final int REQUEST_ADD = 0;
    public static final int REQUEST_EDIT = 1;

    public mybookfragment() {
    }

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_book_fragment,container,false);
        StatusBarCompat.setStatusBarColor(getActivity(),getResources().getColor(R.color.menuBackground),false);
        bookList = view.findViewById(R.id.my_book_list);
        bookDataList = new ArrayList<>();
        bookAdapter = new CustomList_mybook(getContext(),bookDataList);
        bookList.setAdapter(bookAdapter);
        final Button filterBtn = view.findViewById(R.id.filter);
        final ImageButton mesBtn = view.findViewById(R.id.message_center);
        final FloatingActionButton addBookbtn = view.findViewById(R.id.add_book_button);

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("Users").document(getUserID()).collection("MyBooks");


        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@NonNull QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                bookAdapter.clear();
                for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {

                    String imageID = (String) doc.getData().get("imageID");
                    String bookName= (String) doc.getData().get("book_name");
                    String author = (String) doc.getData().get("author");
                    String ISBN =  (String) doc.getData().get("isbn");
                    String description = (String) doc.getData().get("description");
                    String status = (String) doc.getData().get("status");
                    String bookid = doc.getId();
                    String borrower = (String) doc.getData().get("borrower_name");
                    String owner = (String) doc.getData().get("owner");
                    bookDataList.add((new Book(imageID,bookName,author,ISBN,description,status,bookid,borrower,owner)));
                }
                bookAdapter.notifyDataSetChanged();
            }
        });

// click on message button to check message
        mesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mesBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_announcement_24));
                Toast.makeText(getContext(),getUserID(),Toast.LENGTH_SHORT).show();
            }
        });

        // click on filter button to filter out item
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(filterBtn);
            }
        });

        // click circle button to add an item
        addBookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddEditIntent.class);
                startActivityForResult(intent, REQUEST_ADD);
            }
        });

        // click an item to edit
        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book myBook = bookAdapter.getItem(position);
                Intent intent = new Intent(getActivity(),AddEditIntent.class);
                intent.putExtra("BOOK", myBook);
                intent.putExtra("POS", position);
                startActivityForResult(intent, REQUEST_EDIT);
            }
        });

        // long click item to delete
        bookList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Book book = bookAdapter.getItem(position);
                ComfirmDialog dialog = new ComfirmDialog(book);
                //dialog.show(getFragmentManager(),Comfirm_DIALOG);
                //注意setTargetFragment

                dialog.show(getFragmentManager(),"show mes");
                return true;
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD) {
            if (resultCode == RESULT_OK) {
                Book myBook = (Book) data.getSerializableExtra("BOOK");
                bookAdapter.add(myBook);
            }
        } else if (requestCode == REQUEST_EDIT) {
            if (resultCode == RESULT_OK) {
                Book myBook = (Book) data.getSerializableExtra("BOOK");
                int pos = data.getIntExtra("POS",-1);
                bookDataList.set(pos, myBook);
                bookAdapter.notifyDataSetChanged();
            }
        }
    }

    private void showPopupMenu(View view) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.filter_menu, popupMenu.getMenu());
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        // PopupMenu关闭事件
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                Toast.makeText(getContext(), "关闭PopupMenu", Toast.LENGTH_SHORT).show();
            }
        });

        popupMenu.show();
    }

    @Override
    public void onOkPressed(Book book) {
        bookAdapter.remove(book);
    }

    protected String getUserID() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    protected String generateBookID(String uid, String isbn) {
        return uid +"-"+isbn;
    }

}
